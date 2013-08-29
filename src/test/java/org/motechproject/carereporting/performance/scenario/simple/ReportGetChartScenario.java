package org.motechproject.carereporting.performance.scenario.simple;

import org.motechproject.carereporting.performance.scenario.AbstractScenario;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

public class ReportGetChartScenario extends AbstractScenario {

    {
        addRequest(
                get("/api/chart")
                    .param("indicatorId", "1")
                    .param("areaId", "2")
                    .param("frequencyId", "5")
                    .param("chartType", "line chart")
                    .param("startDate", "01/06/2013")
                    .param("endDate", "01/08/2013")
        );
    }



}
