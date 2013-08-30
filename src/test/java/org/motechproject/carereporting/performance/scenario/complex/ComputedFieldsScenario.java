package org.motechproject.carereporting.performance.scenario.complex;

import org.motechproject.carereporting.performance.scenario.AbstractScenario;
import org.motechproject.carereporting.performance.scenario.simple.ComplexConditionGetOperatorTypeScenario;
import org.motechproject.carereporting.performance.scenario.simple.ComputedFieldGetComputedFieldsScenario;
import org.motechproject.carereporting.performance.scenario.simple.FormGetComputedFieldScenario;
import org.motechproject.carereporting.performance.scenario.simple.FormGetFormsScenario;

public class ComputedFieldsScenario extends AbstractScenario {

    {
        addRequests(
                new ComputedFieldGetComputedFieldsScenario(),
                new FormGetFormsScenario(),
                new ComplexConditionGetOperatorTypeScenario(),
                new FormGetComputedFieldScenario()
        );
    }

}
