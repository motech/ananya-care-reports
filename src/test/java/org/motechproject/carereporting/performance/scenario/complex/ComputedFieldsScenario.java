package org.motechproject.carereporting.performance.scenario.complex;

import org.motechproject.carereporting.performance.scenario.AbstractScenario;
import org.motechproject.carereporting.performance.scenario.simple.ComputedFieldGetComputedFieldsWithoutOriginScenario;
import org.motechproject.carereporting.performance.scenario.simple.ComputedFieldGetOperatorTypeScenario;
import org.motechproject.carereporting.performance.scenario.simple.FormGetComputedFieldScenario;
import org.motechproject.carereporting.performance.scenario.simple.FormGetFormsScenario;

public class ComputedFieldsScenario extends AbstractScenario {

    {
        addRequests(
                new ComputedFieldGetComputedFieldsWithoutOriginScenario(),
                new FormGetFormsScenario(),
                new ComputedFieldGetOperatorTypeScenario(),
                new FormGetComputedFieldScenario()
        );
    }

}
