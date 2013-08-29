package org.motechproject.carereporting.performance;

import org.apache.commons.lang.time.StopWatch;
import org.apache.log4j.Logger;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.motechproject.carereporting.context.ApplicationContextProvider;
import org.motechproject.carereporting.domain.UserEntity;
import org.motechproject.carereporting.performance.helpers.PerformanceTestHelper;
import org.motechproject.carereporting.performance.scenario.AbstractScenario;
import org.motechproject.carereporting.performance.scenario.complex.LoadPageScenario;
import org.motechproject.carereporting.performance.scenario.complex.ManageFormsScenario;
import org.motechproject.carereporting.performance.scenario.complex.ManageIndicatorsScenario;
import org.motechproject.carereporting.performance.scenario.complex.MapReportScenario;
import org.motechproject.carereporting.performance.scenario.complex.PerformanceSummaryScenario;
import org.motechproject.carereporting.performance.scenario.simple.DashboardGetDashboardsScenario;
import org.motechproject.carereporting.performance.scenario.simple.DashboardGetUserAreasScenario;
import org.motechproject.carereporting.performance.scenario.simple.FormGetForeignKeyForTableScenario;
import org.motechproject.carereporting.performance.scenario.simple.FormGetFormsScenario;
import org.motechproject.carereporting.performance.scenario.simple.IndicatorCalculatorGetDailyFrequencyScenario;
import org.motechproject.carereporting.performance.scenario.simple.IndicatorCalculatorGetDepthDateScenario;
import org.motechproject.carereporting.performance.scenario.simple.IndicatorCategoryGetCategoriesScenario;
import org.motechproject.carereporting.performance.scenario.simple.IndicatorCategoryGetCategoryScenario;
import org.motechproject.carereporting.performance.scenario.simple.IndicatorFilterIndicatorsScenario;
import org.motechproject.carereporting.performance.scenario.simple.IndicatorGetIndicatorsScenario;
import org.motechproject.carereporting.performance.scenario.simple.LanguageGetLanguagesScenario;
import org.motechproject.carereporting.performance.scenario.simple.LanguageGetMessagesScenario;
import org.motechproject.carereporting.performance.scenario.simple.LanguageGetPlainMessagesScenario;
import org.motechproject.carereporting.performance.scenario.simple.MapReportGetMapScenario;
import org.motechproject.carereporting.performance.scenario.simple.ReportExportCaseListReportScenario;
import org.motechproject.carereporting.performance.scenario.simple.ReportExportToCsvScenario;
import org.motechproject.carereporting.performance.scenario.simple.ReportGetChartScenario;
import org.motechproject.carereporting.performance.scenario.simple.ReportGetDataScenario;
import org.motechproject.carereporting.performance.scenario.simple.ReportGetTrendScenario;
import org.motechproject.carereporting.performance.scenario.simple.UserGetLoggedUserAreaScenario;
import org.motechproject.carereporting.performance.scenario.simple.UserGetLoggedUserLanguageScenario;
import org.motechproject.carereporting.service.AreaService;
import org.motechproject.carereporting.service.UserService;
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

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;


@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = "classpath:testContext.xml")
public abstract class PhasePT {

    private static final Logger LOGGER = Logger.getLogger(PhasePT.class);

    private static final int PEEK_COEFFICIENT = 4;
    private static final int PARTS = 5;
    private static final int MILLISECONDS_PER_SECOND = 1000;
    private static final String[] MOCK_AUTHORITIES = {"CAN_CREATE_COMPUTED_FIELDS"};

    private long elapsedTime;
    private int reportLookersCount;
    private double avgReqPerSec;
    private double avgWaitTime;
    private double peekWaitTime;
    private MockMvc mockMvc;
    private MockHttpSession session;
    private static TreeMap<Long, String> times;

    @Autowired
    private PerformanceTestHelper performanceTestHelper;

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
        times = new TreeMap<>();
        printHeader();
    }

    @PostConstruct
    public void populateDatabase() {
        performanceTestHelper.populateDatabaseWithRandomIndicators(10);
    }

    @Before
    public void setup() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        session = prepareMockHttpSession();
        elapsedTime = 0;
    }

    @AfterClass
    public static void printStatistics() {
        LOGGER.info("Average scenario times: ");
        for (Map.Entry<Long, String> entry : times.entrySet()) {
            LOGGER.info(entry.getValue() + ": " + entry.getKey() + "ms");
        }
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

    private void performRequest(MockHttpServletRequestBuilder request) throws Exception {
        SecurityContextHolder.getContext().setAuthentication(prepareAuthentication());
        mockMvc.perform(request.session(session));
    }

    private void runTest(Class<? extends AbstractScenario> scenario) throws Exception {
        LOGGER.info("Running scenario: " + scenario.getSimpleName());
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
        times.put(elapsedTime / reportLookersCount, scenario.getSimpleName());
        LOGGER.info("Scenario: " + scenario.getSimpleName() + " finished.\n" +
                "Total time: " + stopWatch.getTime() + "ms\n" +
                "Average time: " + elapsedTime / reportLookersCount + "ms");
    }

    private synchronized void addElapsedTime(long time) {
        elapsedTime += time;
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
            StopWatch stopWatch = new StopWatch();
            stopWatch.start();
            for (MockHttpServletRequestBuilder request: scenario.getRequests()) {
                try {
                    performRequest(request);
                } catch (Exception e) {
                    LOGGER.warn("Cannot perform request", e);
                }
            }
            stopWatch.stop();
            addElapsedTime(stopWatch.getTime());
            LOGGER.info("User thread: " + getName() + " finished. Total time: " + stopWatch.getTime() + "ms");
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

    @Test
    public void testGetDashboards() throws Exception {
        runTest(DashboardGetDashboardsScenario.class);
    }

    @Test
    public void testGetUserAreas() throws Exception {
        runTest(DashboardGetUserAreasScenario.class);
    }

    @Test
    public void testGetForeignKeyForTable() throws Exception {
        runTest(FormGetForeignKeyForTableScenario.class);
    }

    @Test
    public void testGetForms() throws Exception {
        runTest(FormGetFormsScenario.class);
    }

    @Test
    public void testGetDailyFrequency() throws Exception {
        runTest(IndicatorCalculatorGetDailyFrequencyScenario.class);
    }

    @Test
    public void testGetDepthDate() throws Exception {
        runTest(IndicatorCalculatorGetDepthDateScenario.class);
    }

    @Test
    public void testGetCategories() throws Exception {
        runTest(IndicatorCategoryGetCategoriesScenario.class);
    }

    @Test
    public void testGetCategory() throws Exception {
        runTest(IndicatorCategoryGetCategoryScenario.class);
    }

    @Test
    public void testFilterIndicators() throws Exception {
        runTest(IndicatorFilterIndicatorsScenario.class);
    }

    @Test
    public void testGetIndicators() throws Exception {
        runTest(IndicatorGetIndicatorsScenario.class);
    }

    @Test
    public void testGetLanguages() throws Exception {
        runTest(LanguageGetLanguagesScenario.class);
    }

    @Test
    public void testGetMessages() throws Exception {
        runTest(LanguageGetMessagesScenario.class);
    }

    @Test
    public void testGetPlainMessages() throws Exception {
        runTest(LanguageGetPlainMessagesScenario.class);
    }

    @Test
    public void testGetMap() throws Exception {
        runTest(MapReportGetMapScenario.class);
    }

    @Test
    public void testExportCaseListReport() throws Exception {
        runTest(ReportExportCaseListReportScenario.class);
    }

    @Test
    public void testExportToCsv() throws Exception {
        runTest(ReportExportToCsvScenario.class);
    }

    @Test
    public void testGetChart() throws Exception {
        runTest(ReportGetChartScenario.class);
    }

    @Test
    public void testGetData() throws Exception {
        runTest(ReportGetDataScenario.class);
    }

    @Test
    public void testGetTrend() throws Exception {
        runTest(ReportGetTrendScenario.class);
    }

    @Test
    public void testGetLoggedUserArea() throws Exception {
        runTest(UserGetLoggedUserAreaScenario.class);
    }

    @Test
    public void testGetLoggedUserLanguage() throws Exception {
        runTest(UserGetLoggedUserLanguageScenario.class);
    }

    @Test
    public void testManageForms() throws Exception {
        runTest(ManageFormsScenario.class);
    }

    @Test
    public void testManageIndicators() throws Exception {
        runTest(ManageIndicatorsScenario.class);
    }

    @Test
    public void testMapReport() throws Exception {
        runTest(MapReportScenario.class);
    }

    @Test
    public void testPerformanceDashboard() throws Exception {
        runTest(PerformanceSummaryScenario.class);
    }

    @Test
    public void testLoadPage() throws Exception {
        runTest(LoadPageScenario.class);
    }

}
