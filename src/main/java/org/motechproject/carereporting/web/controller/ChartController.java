package org.motechproject.carereporting.web.controller;

import org.motechproject.carereporting.domain.FrequencyEntity;
import org.motechproject.carereporting.domain.IndicatorEntity;
import org.motechproject.carereporting.domain.IndicatorValueEntity;
import org.motechproject.carereporting.domain.dto.CategorizedValueDto;
import org.motechproject.carereporting.domain.dto.IndicatorValueDto;
import org.motechproject.carereporting.domain.views.DashboardJsonView;
import org.motechproject.carereporting.service.CronService;
import org.motechproject.carereporting.service.ExportService;
import org.motechproject.carereporting.service.IndicatorService;
import org.motechproject.carereporting.service.ReportService;
import org.motechproject.carereporting.utils.date.DateResolver;
import org.motechproject.carereporting.web.chart.Chart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    private ExportService exportService;

    @RequestMapping(method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE })
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    @Cacheable(value = "chart")
    public Chart getChartData(@RequestParam Integer indicatorId,
                              @RequestParam Integer areaId,
                              @RequestParam Integer frequencyId,
                              @RequestParam String chartType,
                              @RequestParam Date startDate,
                              @RequestParam Date endDate) {

        IndicatorEntity indicator = indicatorService.getIndicatorById(indicatorId);
        List<IndicatorValueEntity> indicatorValues;
        List<IndicatorValueDto> dtos = new ArrayList<IndicatorValueDto>();
        if ("clustered bar chart".equals(chartType)) {
            return reportService.prepareCategorizedChart(indicator, chartType,
                    getCategorizedIndicatorValues(indicatorId, areaId, frequencyId, startDate, endDate));
        } else {
            indicatorValues = getIndicatorValues(indicatorId, areaId, frequencyId, startDate, endDate);
            for (IndicatorValueEntity valueEntity : indicatorValues) {
                dtos.add(valueEntity.toDto());
            }
            return reportService.prepareChart(indicator, chartType, dtos);
        }
    }

    private List<CategorizedValueDto> getCategorizedIndicatorValues(Integer indicatorId, Integer areaId,
                                                                         Integer frequencyId, Date startDate, Date endDate) {
        FrequencyEntity frequencyEntity = cronService.getFrequencyById(frequencyId);
        Date[] dates = DateResolver.resolveDates(frequencyEntity, startDate, endDate);
        List<IndicatorValueEntity> valueEntities = indicatorService.getIndicatorValuesForArea(indicatorId, areaId, frequencyId, dates[0], dates[1], null);
        Map<String, List<IndicatorValueEntity>> categorizedValueEntities = new HashMap<>();
        for (IndicatorValueEntity valueEntity : valueEntities) {
            if (categorizedValueEntities.get(valueEntity.getCategory()) == null) {
                categorizedValueEntities.put(valueEntity.getCategory(), new ArrayList<IndicatorValueEntity>());
            }
            categorizedValueEntities.get(valueEntity.getCategory()).add(valueEntity);
        }
        List<CategorizedValueDto> categorizedValueDtos = new ArrayList<>();
        for (Map.Entry<String, List<IndicatorValueEntity>> entry : categorizedValueEntities.entrySet()) {
            List<IndicatorValueDto> indicatorValueDtos = new ArrayList<>();
            for (IndicatorValueEntity indicatorValueEntity : entry.getValue()) {
                indicatorValueDtos.add(indicatorValueEntity.toDto());
            }
            CategorizedValueDto valueDto = new CategorizedValueDto(entry.getKey(), indicatorValueDtos);
            categorizedValueDtos.add(valueDto);
        }
        return categorizedValueDtos;
    }

    @RequestMapping(value = "/data", method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE })
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public String getChartValues(@RequestParam Integer indicatorId,
                                 @RequestParam Integer areaId,
                                 @RequestParam Integer frequencyId,
                                 @RequestParam Date startDate,
                                 @RequestParam Date endDate) {

        return this.writeAsString(DashboardJsonView.class,
                getIndicatorValues(indicatorId, areaId, frequencyId, startDate, endDate));
    }

    @RequestMapping(value = "/data/export", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<byte[]> exportValuesToCsv(@RequestParam Integer indicatorId,
                                                    @RequestParam Integer areaId,
                                                    @RequestParam Integer frequencyId,
                                                    @RequestParam Date startDate,
                                                    @RequestParam Date endDate) throws IOException, ParseException {

        List<IndicatorValueEntity> indicatorValueEntities = indicatorService.getIndicatorValuesForCsv(indicatorId, areaId, frequencyId, startDate, endDate);
        byte[] bytes = exportService.convertIndicatorValuesToBytes(indicatorValueEntities);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy_HH:mm");
        String filename = indicatorValueEntities.get(0).getIndicator().getName() + "_" + simpleDateFormat.format(new Date()) + ".csv";

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        httpHeaders.setContentDispositionFormData("attachment", filename);

        return new ResponseEntity<>(bytes, httpHeaders, HttpStatus.OK);
    }

    private List<IndicatorValueEntity> getIndicatorValues(Integer indicatorId, Integer areaId, Integer frequencyId, Date startDate, Date endDate) {
        FrequencyEntity frequencyEntity = cronService.getFrequencyById(frequencyId);
        Date[] dates = DateResolver.resolveDates(frequencyEntity, startDate, endDate);
        return indicatorService.getIndicatorValuesForArea(indicatorId, areaId, frequencyId, dates[0], dates[1], null);
    }

}
