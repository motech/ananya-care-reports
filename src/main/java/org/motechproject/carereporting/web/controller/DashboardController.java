package org.motechproject.carereporting.web.controller;

import org.motechproject.carereporting.domain.DashboardEntity;
import org.motechproject.carereporting.domain.dto.DashboardPositionDto;
import org.motechproject.carereporting.domain.views.DashboardJsonView;
import org.motechproject.carereporting.service.AreaService;
import org.motechproject.carereporting.service.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

@RequestMapping("api/dashboards")
@Controller
public class DashboardController extends BaseController {

    @Autowired
    private DashboardService dashboardService;

    @Autowired
    private AreaService areaService;

    @RequestMapping(method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE })
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    @Transactional
    public String getAllDashboards(HttpServletRequest request) {
        Set<DashboardEntity> dashboards = dashboardService.getAllDashboards();
        filterDashboardsByUserRoles(request, dashboards);
        return writeAsString(DashboardJsonView.class,
                dashboards);
    }

    @RequestMapping(value = "/dto", method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE })
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public String getAllDashboardsFromDto() {
        return writeAsString(DashboardJsonView.class,
                dashboardService.getAllDashboardsFromDto());
    }

    private void filterDashboardsByUserRoles(HttpServletRequest request, Set<DashboardEntity> dashboards) {
        if (!request.isUserInRole("CAN_VIEW_MAP_REPORT")) {
            removeFromDashboardsByName(dashboards, "Map report");
        }
        if (!request.isUserInRole("CAN_VIEW_PERFORMANCE_SUMMARY")) {
            removeFromDashboardsByName(dashboards, "Performance summary");
        }
    }

    private void removeFromDashboardsByName(Set<DashboardEntity> dashboards, String name) {
        Iterator<DashboardEntity> iter = dashboards.iterator();
        while (iter.hasNext()) {
            DashboardEntity dashboard = iter.next();
            if (name.equals(dashboard.getName())) {
                iter.remove();
                return;
            }
        }
    }

    @RequestMapping(value = "/user-areas/{areaId}", method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE })
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public String getCurrentUserAvailableAreas(@PathVariable Integer areaId) {
        return writeAsString(DashboardJsonView.class, areaService.getAllAreasByParentAreaId(areaId));
    }

    @RequestMapping(value = "/save-positions", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void saveDashboardsPositions(@RequestBody List<DashboardPositionDto> dashboardsPositions) {
        dashboardService.saveDashboardsPositions(dashboardsPositions);
    }



}
