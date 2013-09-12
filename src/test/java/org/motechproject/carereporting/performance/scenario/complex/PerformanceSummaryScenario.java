package org.motechproject.carereporting.performance.scenario.complex;

import org.motechproject.carereporting.performance.scenario.AbstractScenario;
import org.motechproject.carereporting.performance.scenario.simple.DashboardGetDashboardDtoScenario;
import org.motechproject.carereporting.performance.scenario.simple.IndicatorCategoryGetCategoriesScenario;
import org.motechproject.carereporting.performance.scenario.simple.IndicatorGetIndicatorsScenario;
import org.motechproject.carereporting.performance.scenario.simple.ReportGetTrendScenario;

public class PerformanceSummaryScenario extends AbstractScenario {

   {
       addRequests(
               new IndicatorGetIndicatorsScenario(),
               new DashboardGetDashboardDtoScenario(),
               new IndicatorCategoryGetCategoriesScenario(),
               new ReportGetTrendScenario()
       );
   }
}
