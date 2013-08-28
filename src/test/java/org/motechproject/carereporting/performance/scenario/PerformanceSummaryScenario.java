package org.motechproject.carereporting.performance.scenario;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

public class PerformanceSummaryScenario extends AbstractScenario {
    {
        addRequests(
                get("/api/languages/messages/plain/messages.properties"),
                get("/api/languages"),
                get("/api/users/logged_in/language"),
                get("/api/indicator/calculator/frequencies"),
                get("/api/users/logged_in/area"),
                get("/api/dashboards"),
                get("/api/indicator/category"),
                get("/api/languages/messages/plain/messages.properties"),
                get("/api/dashboards/user-areas/1"),
                get("/api/languages/messages/plain/messages_df.properties"),
                get("/api/trend?startDate=22/02/2012&endDate=22/02/2013&frequencyId=1&areaId=1")
        );
    }
}
