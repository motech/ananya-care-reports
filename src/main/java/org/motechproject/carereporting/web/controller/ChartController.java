package org.motechproject.carereporting.web.controller;

import org.motechproject.carereporting.domain.IndicatorEntity;
import org.motechproject.carereporting.domain.IndicatorValueEntity;
import org.motechproject.carereporting.service.IndicatorService;
import org.motechproject.carereporting.service.ReportService;
import org.motechproject.carereporting.service.UserService;
import org.motechproject.carereporting.web.chart.Chart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Calendar;
import java.util.Date;
import java.util.List;


@RequestMapping("api/chart")
@Controller
public class ChartController {

    @Autowired
    private IndicatorService indicatorService;

    @Autowired
    private ReportService reportService;

    @Autowired
    private UserService userService;

    @RequestMapping(method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE })
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Chart getChartData(@RequestParam Integer indicatorId, @RequestParam(required = false) Integer areaId, @RequestParam String chartType) {

        Integer area = areaId != null ? areaId :
                userService.getCurrentlyLoggedUser().getArea().getId();

        IndicatorEntity indicator = indicatorService.getIndicatorById(indicatorId);
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.add(Calendar.DAY_OF_YEAR, -indicator.getFrequency());
        List<IndicatorValueEntity> indicatorValues =
                indicatorService.getIndicatorValuesForArea(indicatorId, area, c.getTime());

        return reportService.prepareChart(indicator, chartType, indicatorValues);
    }

    @RequestMapping(value = "/data", method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE })
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List<IndicatorValueEntity> getChartValues(@RequestParam Integer indicatorId,
            @RequestParam(required = false) Integer areaId) {

        Integer area = areaId != null ? areaId :
                userService.getCurrentlyLoggedUser().getArea().getId();

        IndicatorEntity indicator = indicatorService.getIndicatorById(indicatorId);
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.add(Calendar.DAY_OF_YEAR, -indicator.getFrequency());
        List<IndicatorValueEntity> indicatorValues =
                indicatorService.getIndicatorValuesForArea(indicatorId, area, c.getTime());

        return indicatorValues;
    }

}
