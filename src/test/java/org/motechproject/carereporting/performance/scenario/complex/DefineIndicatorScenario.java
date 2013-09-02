package org.motechproject.carereporting.performance.scenario.complex;

import org.motechproject.carereporting.performance.scenario.AbstractScenario;
import org.motechproject.carereporting.performance.scenario.simple.FormGetComputedFieldScenario;
import org.motechproject.carereporting.performance.scenario.simple.IndicatorCategoryGetCategoriesScenario;
import org.motechproject.carereporting.performance.scenario.simple.IndicatorGetCreationFormScenario;
import org.motechproject.carereporting.performance.scenario.simple.IndicatorGetIndicatorsScenario;

public class DefineIndicatorScenario extends AbstractScenario {

    {
        addRequests(
                new IndicatorGetCreationFormScenario(),
                new IndicatorCategoryGetCategoriesScenario(),
                new IndicatorGetIndicatorsScenario(),
                new FormGetComputedFieldScenario()
        );
    }

}
