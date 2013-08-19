package org.motechproject.carereporting.web.controller;

import org.motechproject.carereporting.domain.AreaEntity;
import org.motechproject.carereporting.domain.UserEntity;
import org.motechproject.carereporting.service.IndicatorService;
import org.motechproject.carereporting.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Date;
import java.util.Map;


@RequestMapping("api/map-report")
@Controller
public class MapReportController extends BaseController {

    @Autowired
    private IndicatorService indicatorService;

    @Autowired
    private UserService userService;

    @RequestMapping(method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE })
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Map<AreaEntity, Integer> getMapReportData(@RequestParam Integer indicatorId, @RequestParam Integer frequencyId,
                                                 @RequestParam Date startDate, @RequestParam Date endDate) {
        UserEntity user = userService.getCurrentlyLoggedUser();
        return indicatorService.getIndicatorTrendForChildAreas(indicatorId, user.getArea().getId(), frequencyId, startDate, endDate);
    }
}
