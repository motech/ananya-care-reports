package org.motechproject.carereporting.performance.scenario.complex;

import org.motechproject.carereporting.performance.scenario.AbstractScenario;
import org.motechproject.carereporting.performance.scenario.simple.ReportGetTrendScenario;
import org.motechproject.carereporting.performance.scenario.simple.DashboardGetDashboardsScenario;
import org.motechproject.carereporting.performance.scenario.simple.IndicatorCategoryGetCategoriesScenario;
import org.motechproject.carereporting.performance.scenario.simple.UserGetLoggedUserAreaScenario;
import org.motechproject.carereporting.performance.scenario.simple.DashboardGetUserAreasScenario;
import org.motechproject.carereporting.performance.scenario.simple.IndicatorCalculatorGetFrequenciesScenario;

public class PerformanceSummaryScenario extends AbstractScenario {

   {
       addRequests(
               new IndicatorCalculatorGetFrequenciesScenario(),
               new UserGetLoggedUserAreaScenario(),
               new DashboardGetDashboardsScenario(),
               new IndicatorCategoryGetCategoriesScenario(),
               new DashboardGetUserAreasScenario(),
               new ReportGetTrendScenario()
       );
   }
}
