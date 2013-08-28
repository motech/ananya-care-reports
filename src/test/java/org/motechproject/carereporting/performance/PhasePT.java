package org.motechproject.carereporting.performance;

import org.apache.commons.lang.time.StopWatch;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.motechproject.carereporting.performance.helpers.StatisticsHelper;
import org.motechproject.carereporting.performance.scenario.AbstractScenario;
import org.motechproject.carereporting.performance.scenario.PerformanceSummaryScenario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;


@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = "classpath:testContext.xml")
public abstract class PhasePT {

    private static final Logger LOGGER = Logger.getLogger(PhasePT.class);

    private static final int PEEK_COEFFICIENT = 4;
    private static final int PARTS = 5;
    private static final int MILLISECONDS_PER_SECOND = 1000;
    private static final String[] MOCK_AUTHORITIES = {"CAN_CREATE_COMPUTED_FIELDS"};

    private int reportLookersCount;
    private double avgReqPerSec;
    private double avgWaitTime;
    private double peekWaitTime;
    private MockMvc mockMvc;
    private MockHttpSession session;

    @Autowired
    private WebApplicationContext webApplicationContext;

    protected PhasePT(int reportLookersCount, double avgReqPerSec) {
        this.reportLookersCount = reportLookersCount;
        this.avgReqPerSec = avgReqPerSec;
        avgWaitTime = MILLISECONDS_PER_SECOND / avgReqPerSec;
        peekWaitTime = avgWaitTime / PEEK_COEFFICIENT;
        session = prepareMockHttpSession();
        printHeader();
    }

    private MockHttpSession prepareMockHttpSession() {
        Authentication authentication = new UsernamePasswordAuthenticationToken("principal", "credentials", prepareAuthorities());
        MockHttpSession session = new MockHttpSession();
        session.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY,
                new MockSecurityContext(authentication));
        return session;
    }

    private List<GrantedAuthority> prepareAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (String authority: MOCK_AUTHORITIES) {
            authorities.add(new SimpleGrantedAuthority(authority));
        }
        return authorities;
    }

    private void printHeader() {
        LOGGER.info("Phase: " + getClass().getSimpleName());
        LOGGER.info("Report lookers: " + reportLookersCount);
        LOGGER.info("Avg flw req / sec: " + avgReqPerSec);
        LOGGER.info("Peak req / sec: " + PEEK_COEFFICIENT * avgReqPerSec);
    }

    @Before
    public void setup() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    private void runTest(Class<? extends AbstractScenario> scenario) throws Exception {
        AbstractScenario scenarioInstance = scenario.newInstance();
        StatisticsHelper.printStart(scenarioInstance);
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        Thread[] users = new Thread[reportLookersCount];
        for (int i = 0; i<reportLookersCount; i++) {
            users[i] = new UserThread(scenarioInstance);
            users[i].start();
            if (i > (PARTS / 2) * reportLookersCount / PARTS && i < (PARTS / 2 + 1) * reportLookersCount / PARTS) {
                Thread.sleep((long) peekWaitTime);
            } else {
                Thread.sleep((long) avgWaitTime);
            }
        }
        for (Thread thread: users) {
            thread.join();
        }
        stopWatch.stop();
        StatisticsHelper.printEnd(scenarioInstance);
        StatisticsHelper.printTime(stopWatch.getTime());
    }

    private void performRequest(MockHttpServletRequestBuilder request) throws Exception {
        mockMvc.perform(request.session(session));
    }

    @Test
    public void testPerformanceDashboard() throws Exception {
        runTest(PerformanceSummaryScenario.class);
    }

    private class UserThread extends Thread {

        private AbstractScenario scenario;

        public UserThread(AbstractScenario scenario) {
            this.scenario = scenario;
        }

        @Override
        public void run() {
            for (MockHttpServletRequestBuilder request: scenario.getRequests()) {
                try {
                    performRequest(request);
                } catch (Exception e) {
                    LOGGER.warn("Cannot perform request: " + request.toString());
                }
            }
        }
    }

    public static class MockSecurityContext implements SecurityContext {

        private Authentication authentication;

        public MockSecurityContext(Authentication authentication) {
            this.authentication = authentication;
        }

        @Override
        public Authentication getAuthentication() {
            return this.authentication;
        }

        @Override
        public void setAuthentication(Authentication authentication) {
            this.authentication = authentication;
        }
    }
}
