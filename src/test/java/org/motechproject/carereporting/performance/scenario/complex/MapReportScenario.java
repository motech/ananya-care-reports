package org.motechproject.carereporting.performance.scenario.complex;

import org.motechproject.carereporting.performance.scenario.AbstractScenario;
import org.motechproject.carereporting.performance.scenario.simple.IndicatorFilterIndicatorsScenario;
import org.motechproject.carereporting.performance.scenario.simple.MapReportGetMapScenario;

public class MapReportScenario extends AbstractScenario {

    {
        for (int i = 0; i < 2; i++) {
            addRequests(new IndicatorFilterIndicatorsScenario());
            addRequests(new MapReportGetMapScenario());
        }
    }

}
