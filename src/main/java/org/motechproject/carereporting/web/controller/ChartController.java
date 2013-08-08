package org.motechproject.carereporting.web.controller;

import org.motechproject.carereporting.domain.FrequencyEntity;
import org.motechproject.carereporting.domain.IndicatorEntity;
import org.motechproject.carereporting.domain.IndicatorValueEntity;
import org.motechproject.carereporting.service.CronService;
import org.motechproject.carereporting.service.ExportService;
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

import java.io.IOException;
import java.util.Date;
import java.util.List;

@RequestMapping("api/chart")
@Controller
public class ChartController extends BaseController {

    @Autowired
    private IndicatorService indicatorService;

    @Autowired
    private ReportService reportService;

    @Autowired
    private CronService cronService;

    @Autowired
    private UserService userService;

    @Autowired
    private ExportService exportService;

    @RequestMapping(method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE })
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Chart getChartData(@RequestParam Integer indicatorId,
                              @RequestParam(required = false) Integer areaId,
                              @RequestParam Integer frequencyId,
                              @RequestParam String chartType,
                              @RequestParam Date startDate,
                              @RequestParam Date endDate) {

        IndicatorEntity indicator = indicatorService.getIndicatorById(indicatorId);
        List<IndicatorValueEntity> indicatorValues =
                getIndicatorValues(indicatorId, areaId, frequencyId, startDate, endDate);

        return reportService.prepareChart(indicator, chartType, indicatorValues);
    }

    @RequestMapping(value = "/data", method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE })
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List<IndicatorValueEntity> getChartValues(@RequestParam Integer indicatorId,
                                                     @RequestParam(required = false) Integer areaId,
                                                     @RequestParam Integer frequencyId,
                                                     @RequestParam Date startDate,
                                                     @RequestParam Date endDate) {

        return getIndicatorValues(indicatorId, areaId, frequencyId, startDate, endDate);
    }

    @RequestMapping(value = "/data/export", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public void exportValuesToCsv(@RequestParam Integer indicatorId,
                                  @RequestParam(required = false) Integer areaId,
                                  @RequestParam Integer frequencyId,
                                  @RequestParam Date startDate,
                                  @RequestParam Date endDate) throws IOException {
        List<IndicatorValueEntity> indicatorValueEntities = getIndicatorValues(indicatorId, areaId, frequencyId, startDate, endDate);
        exportService.exportIndicatorValues(indicatorValueEntities);
    }

    private List<IndicatorValueEntity> getIndicatorValues(Integer indicatorId, Integer areaId, Integer frequencyId, Date startDate, Date endDate) {
        Integer area = areaId != null ? areaId : userService.getCurrentlyLoggedUser().getArea().getId();

        FrequencyEntity frequencyEntity = cronService.getFrequencyById(frequencyId);
        Date finalDate = resolveEndDate(frequencyEntity, endDate);
        return indicatorService.getIndicatorValuesForArea(indicatorId, area, frequencyId, startDate, finalDate);
    }

}
