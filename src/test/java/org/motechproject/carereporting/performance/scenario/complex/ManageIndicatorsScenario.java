package org.motechproject.carereporting.performance.scenario.complex;

import org.motechproject.carereporting.performance.scenario.AbstractScenario;
import org.motechproject.carereporting.performance.scenario.simple.IndicatorCategoryGetCategoriesScenario;
import org.motechproject.carereporting.performance.scenario.simple.IndicatorFilterIndicatorsScenario;
import org.motechproject.carereporting.performance.scenario.simple.IndicatorGetIndicatorsScenario;

public class ManageIndicatorsScenario extends AbstractScenario {

    {
        addRequests(
                new IndicatorCategoryGetCategoriesScenario(),
                new IndicatorGetIndicatorsScenario(),
                new IndicatorFilterIndicatorsScenario()
        );
    }

}
