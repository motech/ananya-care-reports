package org.motechproject.carereporting.performance.scenario;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

public class PerformanceSummaryScenario extends AbstractScenario {

    /*
    public void runScenario() {
        languageController.getPlainMessagesForLanguage("messages.properties");
        languageController.getAllLanguages();
        userController.getCurrentlyLoggedUserLanguageCode();
        indicatorController.getAllFrequencies();
        userController.getCurrentlyLoggedInUserArea();
        dashboardController.getAllDashboards(httpServletRequest);
        indicatorController.getIndicatorCategoryList();
        languageController.getPlainMessagesForLanguage("messages.properties");
        dashboardController.getCurrentUserAvailableAreas(1);
        languageController.getPlainMessagesForLanguage("messages_df.properties");
        trendController.getTrends(new Date(), new Date(), 1, 1);
    }
   */

    {
        addRequests(
                get("/api/dashboards/user-areas/1")
        );
    }

}
