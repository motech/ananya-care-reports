package org.motechproject.carereporting.performance.scenario.complex;

import org.motechproject.carereporting.performance.scenario.AbstractScenario;
import org.motechproject.carereporting.performance.scenario.simple.FormGetAllComputedFieldsScenario;
import org.motechproject.carereporting.performance.scenario.simple.IndicatorGetQueryCreationFormScenario;

public class DefineQueryScenario extends AbstractScenario {

    {
        addRequests(
                new IndicatorGetQueryCreationFormScenario(),
                new FormGetAllComputedFieldsScenario()
        );

    }

}
