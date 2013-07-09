package org.motechproject.carereporting.web.controller;

import org.motechproject.carereporting.domain.DashboardEntity;
import org.motechproject.carereporting.service.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Set;

@RequestMapping("api/dashboards")
@Controller
public class DashboardController {

    @Autowired
    private DashboardService dashboardService;

    @RequestMapping(method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE })
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Set<DashboardEntity> getAllDashboards() {
        return dashboardService.findAllDashboards();
    }
}
