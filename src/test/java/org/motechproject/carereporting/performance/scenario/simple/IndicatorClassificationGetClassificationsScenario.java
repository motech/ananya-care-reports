package org.motechproject.carereporting.performance.scenario.simple;

import org.motechproject.carereporting.performance.scenario.AbstractScenario;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

public class IndicatorClassificationGetClassificationsScenario extends AbstractScenario {

    {
        addRequest(
                get("/api/indicator/classification")
        );
    }

}
