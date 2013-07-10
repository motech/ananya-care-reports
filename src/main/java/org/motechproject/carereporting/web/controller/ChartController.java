package org.motechproject.carereporting.web.controller;

import org.motechproject.carereporting.web.chart.Chart;
import org.motechproject.carereporting.web.chart.ChartBuilder;
import org.motechproject.carereporting.web.chart.Serie;
import org.motechproject.carereporting.web.chart.SerieBuilder;
import org.motechproject.carereporting.web.chart.SettingsBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Random;


@SuppressWarnings("PMD.UnusedFormalParameter")
@RequestMapping("api/chart")
@Controller
public class ChartController {

    @RequestMapping(method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE })
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Chart getChartData(@RequestParam Integer indicatorId, @RequestParam String chartType) {

        switch (chartType) {
            case "pie":
                return createPieChartData(indicatorId);
            case "bar":
                return createBarChartData(indicatorId);
            case "line":
                return createLineChartData(indicatorId);
            default:
                return null;
        }
    }

    private Chart createLineChartData(Integer indicatorId) {

        return new ChartBuilder()
            .param("title", "% mothers migrated out after first PNC")
            .param("HtmlText", false)
            .param("xaxis", new SettingsBuilder()
                    .param("minorTickFreq", 4))
            .param("grid", new SettingsBuilder()
                    .param("minorVerticalLines", true))
            .param("legend", new SettingsBuilder()
                    .param("position", "se")
                    .param("backgroundColor", "#D2E8FF"))
            .serie(createRandomSerie("Area 1"))
            .serie(createRandomSerie("Area 2"))
            .build();
    }

    private Serie createRandomSerie(String label) {
        Random random = new Random();
        SerieBuilder serieBuilder = new SerieBuilder()
                .label(label);
        for (int i = 0; i<20; i+=1) {
            serieBuilder.point(i, i + random.nextInt(6) - 3);
        }
        return serieBuilder.build();
    }

    private Chart createPieChartData(Integer indicatorId) {
        Random random = new Random();
        return new ChartBuilder()
            .param("title", "% frontline workers visiting as scheduled")
            .param("HtmlText", false)
            .param("grid", new SettingsBuilder()
                    .param("verticalLines", false)
                    .param("horizontalLines", false))
            .param("xaxis", new SettingsBuilder()
                    .param("showLabels", false))
            .param("yaxis", new SettingsBuilder()
                    .param("showLabels", false))
            .param("pie", new SettingsBuilder()
                    .param("show", true)
                    .param("explode", 6))
            .param("mouse", new SettingsBuilder()
                    .param("track", true))
            .param("legend", new SettingsBuilder()
                    .param("position", "se")
                    .param("backgroundColor", "#D2E8FF"))
            .serie(new SerieBuilder()
                    .label("Visiting as scheduled")
                    .point(0, random.nextInt(4) + 1))
            .serie(new SerieBuilder()
                    .label("Not visiting as scheduled")
                    .point(0, random.nextInt(4) + 1))
            .build();
    }

    private Chart createBarChartData(Integer indicatorId) {
        double barWidth = .5;
        Random random = new Random();
        return new ChartBuilder()
            .param("title", "% of actual contacts vs scheduled contacts in the continuum of care")
            .param("HtmlText", false)
            .param("bars", new SettingsBuilder()
                    .param("show", true)
                    .param("horizontal", true)
                    .param("shadowSize", 0)
                    .param("barWidth", barWidth))
            .param("mouse", new SettingsBuilder()
                    .param("track", true)
                    .param("relative", true))
            .param("xaxis", new SettingsBuilder()
                    .param("min", 0)
                    .param("autoScaleMargin", 1))
            .param("legend", new SettingsBuilder()
                    .param("position", "se")
                    .param("backgroundColor", "#D2E8FF"))
            .serie(new SerieBuilder()
                    .label("Actual")
                    .point(random.nextInt(50) + 11, 1)
                    .point(random.nextInt(50) + 11, 3))
            .serie(new SerieBuilder()
                    .label("Scheduled")
                    .point(random.nextInt(50) + 11, 1+barWidth)
                    .point(random.nextInt(50) + 11, 3+barWidth))
            .build();
    }

}