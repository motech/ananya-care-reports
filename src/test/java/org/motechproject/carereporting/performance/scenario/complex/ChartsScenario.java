package org.motechproject.carereporting.performance.scenario.complex;

import org.motechproject.carereporting.performance.scenario.AbstractScenario;
import org.motechproject.carereporting.performance.scenario.simple.DashboardGetUserAreasScenario;
import org.motechproject.carereporting.performance.scenario.simple.ReportGetChartScenario;

public class ChartsScenario extends AbstractScenario {

    {
        for(int i = 0; i < 3; i++) {
            addRequests(
                new DashboardGetUserAreasScenario(),
                new ReportGetChartScenario()
            );
        }
    }
}
