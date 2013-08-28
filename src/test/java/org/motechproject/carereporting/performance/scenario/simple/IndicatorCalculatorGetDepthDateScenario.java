package org.motechproject.carereporting.performance.scenario.simple;

import org.motechproject.carereporting.performance.scenario.AbstractScenario;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

public class IndicatorCalculatorGetDepthDateScenario extends AbstractScenario {

    {
        addRequest(
                get("/api/indicator/calculator/dateDepth")
        );
    }

}
