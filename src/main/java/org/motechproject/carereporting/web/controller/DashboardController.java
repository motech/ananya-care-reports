package org.motechproject.carereporting.web.controller;

import org.motechproject.carereporting.domain.AreaEntity;
import org.motechproject.carereporting.domain.DashboardEntity;
import org.motechproject.carereporting.domain.UserEntity;
import org.motechproject.carereporting.service.AreaService;
import org.motechproject.carereporting.service.DashboardService;
import org.motechproject.carereporting.service.UserService;
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

    @Autowired
    private UserService userService;

    @Autowired
    private AreaService areaService;

    @RequestMapping(method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE })
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Set<DashboardEntity> getAllDashboards() {
        return dashboardService.findAllDashboards();
    }

    @RequestMapping(value = "/user-areas", method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE })
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Set<AreaEntity> getCurrentUserAvailableAreas() {
        UserEntity user = userService.findCurrentlyLoggedUser();
        Set<AreaEntity> userAreas = areaService.findAllChildAreasByParentAreaId(user.getArea().getId());
        userAreas.add(user.getArea());
        return userAreas;
    }

}
