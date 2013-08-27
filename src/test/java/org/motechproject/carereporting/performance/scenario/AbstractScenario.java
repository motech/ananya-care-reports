package org.motechproject.carereporting.performance.scenario;

import org.mockito.Mock;
import org.motechproject.carereporting.context.ApplicationContextProvider;
import org.motechproject.carereporting.web.controller.ChartController;
import org.motechproject.carereporting.web.controller.ComplexConditionController;
import org.motechproject.carereporting.web.controller.ComputedFieldController;
import org.motechproject.carereporting.web.controller.DashboardController;
import org.motechproject.carereporting.web.controller.FormsController;
import org.motechproject.carereporting.web.controller.IndicatorController;
import org.motechproject.carereporting.web.controller.LanguageController;
import org.motechproject.carereporting.web.controller.MapReportController;
import org.motechproject.carereporting.web.controller.ReportController;
import org.motechproject.carereporting.web.controller.TrendController;
import org.motechproject.carereporting.web.controller.UserController;
import org.springframework.context.ApplicationContext;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

public abstract class AbstractScenario implements Runnable {

    protected ApplicationContext applicationContext = ApplicationContextProvider.getApplicationContext();
    protected ChartController chartController = applicationContext.getBean(ChartController.class);
    protected ComplexConditionController complexConditionController = applicationContext.getBean(ComplexConditionController.class);
    protected ComputedFieldController computedFieldController = applicationContext.getBean(ComputedFieldController.class);
    protected DashboardController dashboardController = applicationContext.getBean(DashboardController.class);
    protected FormsController formsController = applicationContext.getBean(FormsController.class);
    protected IndicatorController indicatorController = applicationContext.getBean(IndicatorController.class);
    protected LanguageController languageController = applicationContext.getBean(LanguageController.class);
    protected MapReportController mapReportController = applicationContext.getBean(MapReportController.class);
    protected ReportController reportController = applicationContext.getBean(ReportController.class);
    protected TrendController trendController = applicationContext.getBean(TrendController.class);
    protected UserController userController = applicationContext.getBean(UserController.class);

    @Mock
    protected HttpServletRequest httpServletRequest;

    @PostConstruct
    public void setup() {
        when(httpServletRequest.isUserInRole(anyString())).thenReturn(true);
    }
}
