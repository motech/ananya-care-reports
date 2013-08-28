package org.motechproject.carereporting.performance;

import org.apache.commons.lang.time.StopWatch;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.motechproject.carereporting.domain.UserEntity;
import org.motechproject.carereporting.performance.scenario.AbstractScenario;
import org.motechproject.carereporting.service.AreaService;
import org.motechproject.carereporting.service.UserService;
import org.motechproject.carereporting.performance.scenario.complex.PerformanceSummaryScenario;
import org.motechproject.carereporting.performance.scenario.simple.IndicatorCategoryExportCaseListReportScenario;
import org.motechproject.carereporting.performance.scenario.simple.IndicatorCategoryExportToCsvScenario;
import org.motechproject.carereporting.performance.scenario.simple.IndicatorCategoryGetChartScenario;
import org.motechproject.carereporting.performance.scenario.simple.IndicatorCategoryGetDataScenario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.Date;
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

    @Autowired
    private UserService userService;

    @Autowired
    private AreaService areaService;

    protected PhasePT(int reportLookersCount, double avgReqPerSec) {
        this.reportLookersCount = reportLookersCount;
        this.avgReqPerSec = avgReqPerSec;
        avgWaitTime = MILLISECONDS_PER_SECOND / avgReqPerSec;
        peekWaitTime = avgWaitTime / PEEK_COEFFICIENT;
        printHeader();
    }

    @Before
    public void prepareMockSession() {
        session = prepareMockHttpSession();
    }

    private MockHttpSession prepareMockHttpSession() {
        MockHttpSession session = new MockHttpSession();
        session.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY,
                new MockSecurityContext(prepareAuthentication()));
        return session;
    }

    private Authentication prepareAuthentication() {
        return new UsernamePasswordAuthenticationToken(prepareUserEntity(), "credentials", prepareAuthorities());
    }

    private UserEntity prepareUserEntity() {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(Integer.MAX_VALUE);
        userEntity.setRoles(userService.getAllRoles());
        userEntity.setArea(areaService.getAreaById(1));
        userEntity.setArea(areaService.getAreaById(1));
        userEntity.setUsername("test-user");
        return userEntity;
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
        LOGGER.info("Running scenario: " + scenario.getName());
        AbstractScenario scenarioInstance = scenario.newInstance();
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        Thread[] users = new Thread[reportLookersCount];
        for (int i = 0; i<reportLookersCount; i++) {
            users[i] = new UserThread(scenarioInstance, "User-" + i);
            users[i].start();
            if (i < (PARTS - 1) * reportLookersCount / PARTS) {
                Thread.sleep((long) avgWaitTime);
            } else {
                Thread.sleep((long) peekWaitTime);
            }
        }
        for (Thread thread: users) {
            thread.join();
        }
        stopWatch.stop();
        LOGGER.info("Scenario: " + scenario.getName() + " finished. Total time: " +
                stopWatch.getTime() + "ms");
    }

    private void performRequest(MockHttpServletRequestBuilder request) throws Exception {
        SecurityContextHolder.getContext().setAuthentication(prepareAuthentication());
        mockMvc.perform(request.session(session));
    }

    @Test
    public void testPerformanceDashboard() throws Exception {
        runTest(PerformanceSummaryScenario.class);
    }

    @Test
    public void testExportCaseListReport() throws Exception {
        runTest(IndicatorCategoryExportCaseListReportScenario.class);
    }

    @Test
    public void testExportToCsv() throws Exception {
        runTest(IndicatorCategoryExportToCsvScenario.class);
    }

    @Test
    public void testGetChart() throws Exception {
        runTest(IndicatorCategoryGetChartScenario.class);
    }

    @Test
    public void testGetData() throws Exception {
        runTest(IndicatorCategoryGetDataScenario.class);
    }

    private class UserThread extends Thread {

        private AbstractScenario scenario;

        public UserThread(AbstractScenario scenario, String name) {
            super(name);
            this.scenario = scenario;
        }

        @Override
        public void run() {
            LOGGER.info("Running user thread: " + getName());
            Date start = new Date();
            for (MockHttpServletRequestBuilder request: scenario.getRequests()) {
                try {
                    performRequest(request);
                } catch (Exception e) {
                    LOGGER.warn("Cannot perform request", e);
                }
            }
            long timeSpent = new Date().getTime() - start.getTime();
            LOGGER.info("User thread: " + getName() + " finished. Total time: " + timeSpent + "ms");
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
