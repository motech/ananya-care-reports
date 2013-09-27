package org.motechproject.carereporting.performance;

import org.apache.commons.lang.time.StopWatch;
import org.apache.log4j.Logger;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.motechproject.carereporting.domain.UserEntity;
import org.motechproject.carereporting.performance.scenario.AbstractScenario;
import org.motechproject.carereporting.performance.scenario.complex.ChartsScenario;
import org.motechproject.carereporting.performance.scenario.complex.ComputedFieldsScenario;
import org.motechproject.carereporting.performance.scenario.complex.DefineIndicatorScenario;
import org.motechproject.carereporting.performance.scenario.complex.DefineOrSelectLanguageScenario;
import org.motechproject.carereporting.performance.scenario.complex.DefineQueryScenario;
import org.motechproject.carereporting.performance.scenario.complex.LoadPageScenario;
import org.motechproject.carereporting.performance.scenario.complex.MapReportScenario;
import org.motechproject.carereporting.performance.scenario.complex.PerformanceSummaryScenario;
import org.motechproject.carereporting.performance.scenario.simple.ComputedFieldGetComputedFieldsScenario;
import org.motechproject.carereporting.performance.scenario.simple.ComputedFieldGetComputedFieldsWithoutOriginScenario;
import org.motechproject.carereporting.performance.scenario.simple.ComputedFieldGetOperatorTypeScenario;
import org.motechproject.carereporting.performance.scenario.simple.DashboardGetDashboardDtoScenario;
import org.motechproject.carereporting.performance.scenario.simple.DashboardGetUserAreasScenario;
import org.motechproject.carereporting.performance.scenario.simple.FormGetComputedFieldScenario;
import org.motechproject.carereporting.performance.scenario.simple.FormGetFormScenario;
import org.motechproject.carereporting.performance.scenario.simple.FormGetFormsFromDtoScenario;
import org.motechproject.carereporting.performance.scenario.simple.FormGetFormsScenario;
import org.motechproject.carereporting.performance.scenario.simple.IndicatorCalculatorGetDailyFrequencyScenario;
import org.motechproject.carereporting.performance.scenario.simple.IndicatorCalculatorGetDepthDateScenario;
import org.motechproject.carereporting.performance.scenario.simple.IndicatorClassificationGetClassificationScenario;
import org.motechproject.carereporting.performance.scenario.simple.IndicatorClassificationGetClassificationsScenario;
import org.motechproject.carereporting.performance.scenario.simple.IndicatorGetCreationFormScenario;
import org.motechproject.carereporting.performance.scenario.simple.IndicatorGetIndicatorsScenario;
import org.motechproject.carereporting.performance.scenario.simple.IndicatorGetQueriesScenario;
import org.motechproject.carereporting.performance.scenario.simple.IndicatorGetQueryCreationFormScenario;
import org.motechproject.carereporting.performance.scenario.simple.LanguageGetLanguagesScenario;
import org.motechproject.carereporting.performance.scenario.simple.LanguageGetMessagesScenario;
import org.motechproject.carereporting.performance.scenario.simple.LanguageGetPlainMessagesScenario;
import org.motechproject.carereporting.performance.scenario.simple.MapReportGetMapScenario;
import org.motechproject.carereporting.performance.scenario.simple.ReportExportCaseListReportScenario;
import org.motechproject.carereporting.performance.scenario.simple.ReportExportToCsvScenario;
import org.motechproject.carereporting.performance.scenario.simple.ReportGetChartScenario;
import org.motechproject.carereporting.performance.scenario.simple.ReportGetDataScenario;
import org.motechproject.carereporting.performance.scenario.simple.ReportGetTrendScenario;
import org.motechproject.carereporting.performance.scenario.simple.UserGetLoggedUserLanguageScenario;
import org.motechproject.carereporting.performance.scenario.simple.UserGetLoggedUserPermissionsScenario;
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

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;


@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = "classpath:performanceTestContext.xml")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public abstract class PhasePT {

    private static final Logger LOGGER = Logger.getLogger(PhasePT.class);

    private static final int PEEK_COEFFICIENT = 4;
    private static final int PARTS = 5;
    private static final int MILLISECONDS_PER_SECOND = 1000;
    private static final String[] MOCK_AUTHORITIES = {"CAN_CREATE_COMPUTED_FIELDS", "CAN_EDIT_CLASSIFICATIONS"};

    private long elapsedTime;
    private int reportLookersCount;
    private double avgReqPerSec;
    private double avgWaitTime;
    private double peekWaitTime;
    private MockMvc mockMvc;
    private MockHttpSession session;
    private static List<ValueComparator> times = new LinkedList<ValueComparator>();

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
    public void setup() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        session = prepareMockHttpSession();
        elapsedTime = 0;
    }

    @AfterClass
    public static void printStatistics() {
        Collections.sort(times);
        LOGGER.info("Average scenario time: ");
        for (ValueComparator valueComparator: times) {
            LOGGER.info(valueComparator.getName() + ": " + valueComparator.getTime() + "ms");
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
        for (int i = 0; i < reportLookersCount; i++) {
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
        times.add(new ValueComparator(scenario.getSimpleName(), elapsedTime / reportLookersCount));
        LOGGER.info("Scenario: " + scenario.getSimpleName() + " finished.\n" +
                "Total time: " + stopWatch.getTime() + "ms\n" +
                "Average time: " + elapsedTime / reportLookersCount + "ms");
    }

    private synchronized void addElapsedTime(long time) {
        elapsedTime += time;
    }

    private class ValueComparator implements Comparable<ValueComparator> {

        private String name;
        private Long time;

        private ValueComparator(String name, Long time) {
            this.name = name;
            this.time = time;
        }

        private String getName() {
            return name;
        }

        private Long getTime() {
            return time;
        }

        @Override
        public int compareTo(ValueComparator o) {
            return time.compareTo(o.time);
        }
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
    public void _0_testGetComputedFields() throws Exception {
        runTest(ComputedFieldGetComputedFieldsScenario.class);
    }
    @Test
    public void _01_testGetComputedFieldsWithoutOrigin() throws Exception {
        runTest(ComputedFieldGetComputedFieldsWithoutOriginScenario.class);
    }

    @Test
    public void _010_testGetOperatorTypes() throws Exception {
        runTest(ComputedFieldGetOperatorTypeScenario.class);
    }

    @Test
    public void _011_testGetDashboardDto() throws Exception {
        runTest(DashboardGetDashboardDtoScenario.class);
    }

    @Test
    public void _012_testGetUserAreas() throws Exception {
        runTest(DashboardGetUserAreasScenario.class);
    }

    @Test
    public void _013_testGetFormComputedFields() throws Exception {
        runTest(FormGetComputedFieldScenario.class);
    }

    @Test
    public void _014_testGetForm() throws Exception {
        runTest(FormGetFormScenario.class);
    }

    @Test
    public void _015_testGetFormsFromDto() throws Exception {
        runTest(FormGetFormsFromDtoScenario.class);
    }

    @Test
    public void _016_testGetForms() throws Exception {
        runTest(FormGetFormsScenario.class);
    }

    @Test
    public void _017_testGetDailyFrequency() throws Exception {
        runTest(IndicatorCalculatorGetDailyFrequencyScenario.class);
    }

    @Test
    public void _018_testGetDepthDate() throws Exception {
        runTest(IndicatorCalculatorGetDepthDateScenario.class);
    }

    @Test
    public void _019_testGetClassifications() throws Exception {
        runTest(IndicatorClassificationGetClassificationsScenario.class);
    }

    @Test
    public void _02_testGetClassification() throws Exception {
        runTest(IndicatorClassificationGetClassificationScenario.class);
    }

    @Test
    public void _020_testGetCreationForm() throws Exception {
        runTest(IndicatorGetCreationFormScenario.class);
    }

    @Test
    public void _021_testGetIndicators() throws Exception {
        runTest(IndicatorGetIndicatorsScenario.class);
    }

    @Test
    public void _022_testGetQueries() throws Exception {
        runTest(IndicatorGetQueriesScenario.class);
    }

    @Test
    public void _023_testGetQueryCreationForm() throws Exception {
        runTest(IndicatorGetQueryCreationFormScenario.class);
    }

    @Test
    public void _024_testGetLanguages() throws Exception {
        runTest(LanguageGetLanguagesScenario.class);
    }

    @Test
    public void _025_testGetMessages() throws Exception {
        runTest(LanguageGetMessagesScenario.class);
    }

    @Test
    public void _026_testGetPlainMessages() throws Exception {
        runTest(LanguageGetPlainMessagesScenario.class);
    }

    @Test
    public void _027_testGetMap() throws Exception {
        runTest(MapReportGetMapScenario.class);
    }

    @Test
    public void _028_testExportCaseListReport() throws Exception {
        runTest(ReportExportCaseListReportScenario.class);
    }

    @Test
    public void _029_testExportToCsv() throws Exception {
        runTest(ReportExportToCsvScenario.class);
    }

    @Test
    public void _03_testGetChart() throws Exception {
        runTest(ReportGetChartScenario.class);
    }

    @Test
    public void _031_testGetData() throws Exception {
        runTest(ReportGetDataScenario.class);
    }

    @Test
    public void _032_testGetTrend() throws Exception {
        runTest(ReportGetTrendScenario.class);
    }

    @Test
    public void _033_testGetLoggedUserLanguage() throws Exception {
        runTest(UserGetLoggedUserLanguageScenario.class);
    }

    @Test
    public void _034_testGetLoggedUserPermissions() throws Exception {
        runTest(UserGetLoggedUserPermissionsScenario.class);
    }

    @Test
    public void _10_testCharts() throws Exception {
        runTest(ChartsScenario.class);
    }

    @Test
    public void _11_testComputedFields() throws Exception {
        runTest(ComputedFieldsScenario.class);
    }

    @Test
    public void _12_testDefineIndicator() throws Exception {
        runTest(DefineIndicatorScenario.class);
    }

    @Test
    public void _13_testDefineQuery() throws Exception {
        runTest(DefineQueryScenario.class);
    }

    @Test
    public void _14_testDefineLanguage() throws Exception {
        runTest(DefineOrSelectLanguageScenario.class);
    }

    @Test
    public void _15_testLoadPage() throws Exception {
        runTest(LoadPageScenario.class);
    }

    @Test
    public void _16_testMapReport() throws Exception {
        runTest(MapReportScenario.class);
    }

    @Test
    public void _17_testPerformanceDashboard() throws Exception {
        runTest(PerformanceSummaryScenario.class);
    }

}
