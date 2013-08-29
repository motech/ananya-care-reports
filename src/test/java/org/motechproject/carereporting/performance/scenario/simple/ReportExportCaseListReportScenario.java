package org.motechproject.carereporting.performance.scenario.simple;

import org.motechproject.carereporting.performance.scenario.AbstractScenario;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

public class ReportExportCaseListReportScenario extends AbstractScenario {

    {
        addRequest(
                get("/api/indicator/indicatorId/export/caselistreport")
                        .param("indicatorId", "1")
                        .param("fromDate", "01/06/2013")
                        .param("toDate", "01/08/2013")
                        .param("areaId", "1")
        );
    }
}
