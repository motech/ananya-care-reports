package org.motechproject.carereporting.performance.scenario;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

public class ReportsCategoryScenario extends AbstractScenario {
    {
        addRequests(
                get("/api/chart?startDate=..."),
                post("/api/jakis-pos").content("jakis-json")
        );
    }
}
