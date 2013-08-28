package org.motechproject.carereporting.performance.scenario.simple;

import org.motechproject.carereporting.performance.scenario.AbstractScenario;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

public class IndicatorCategoryGetCategoryScenario extends AbstractScenario {

    {
        addRequest(
                get("/api/indicator/category/1")
        );
    }

}
