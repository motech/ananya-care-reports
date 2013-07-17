package org.motechproject.carereporting.web.controller;

import org.motechproject.carereporting.domain.AreaEntity;
import org.motechproject.carereporting.domain.DashboardEntity;
import org.motechproject.carereporting.domain.UserEntity;
import org.motechproject.carereporting.domain.forms.DashboardPosition;
import org.motechproject.carereporting.domain.views.DashboardJsonView;
import org.motechproject.carereporting.service.AreaService;
import org.motechproject.carereporting.service.DashboardService;
import org.motechproject.carereporting.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;
import java.util.Set;

@RequestMapping("api/dashboards")
@Controller
public class DashboardController extends BaseController {

    @Autowired
    private DashboardService dashboardService;

    @Autowired
    private UserService userService;

    @Autowired
    private AreaService areaService;

    @RequestMapping(method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE })
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public String getAllDashboards() {
        Set<DashboardEntity> dashboards = dashboardService.findAllDashboards();
        return writeAsString(DashboardJsonView.class,
                dashboards);
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

    @RequestMapping(value = "/save-positions", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void saveDashboardsPositions(@RequestBody List<DashboardPosition> dashboardsPositions) {
        dashboardService.saveDashboardsPositions(dashboardsPositions);
    }

}
