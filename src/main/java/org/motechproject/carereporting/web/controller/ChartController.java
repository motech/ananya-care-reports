package org.motechproject.carereporting.web.controller;

import org.motechproject.carereporting.web.chart.builder.AxisBuilder;
import org.motechproject.carereporting.web.chart.builder.BarsBuilder;
import org.motechproject.carereporting.web.chart.Chart;
import org.motechproject.carereporting.web.chart.builder.ChartBuilder;
import org.motechproject.carereporting.web.chart.builder.GridBuilder;
import org.motechproject.carereporting.web.chart.builder.LegendBuilder;
import org.motechproject.carereporting.web.chart.builder.MouseBuilder;
import org.motechproject.carereporting.web.chart.builder.PieBuilder;
import org.motechproject.carereporting.web.chart.Serie;
import org.motechproject.carereporting.web.chart.builder.SerieBuilder;
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
        return createTemplateChart("% mothers migrated out after first PNC")
            .xAxis(new AxisBuilder()
                    .minorTickFreq(4))
            .grid(new GridBuilder()
                    .minorVerticalLines(true))
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
        return createTemplateChart("% frontline workers visiting as scheduled")
            .grid(new GridBuilder()
                    .verticalLines(false)
                    .horizontalLines(false))
            .xAxis(new AxisBuilder()
                    .showLabels(false))
            .yAxis(new AxisBuilder()
                    .showLabels(false))
            .pie(new PieBuilder()
                    .show(true)
                    .explode(6))
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
        return createTemplateChart("% of actual contacts vs scheduled contacts in the continuum of care")
            .bars(new BarsBuilder()
                    .show(true)
                    .horizontal(true)
                    .shadowSize(0)
                    .barWidth(barWidth))
            .xAxis(new AxisBuilder()
                    .min(0)
                    .autoScaleMargin(1))
            .serie(new SerieBuilder()
                    .label("Actual")
                    .point(random.nextInt(50) + 11, 1)
                    .point(random.nextInt(50) + 11, 3))
            .serie(new SerieBuilder()
                    .label("Scheduled")
                    .point(random.nextInt(50) + 11, 1 + barWidth)
                    .point(random.nextInt(50) + 11, 3 + barWidth))
            .build();
    }

    private ChartBuilder createTemplateChart(String title) {
        return new ChartBuilder()
            .title(title)
            .htmlText(false)
            .legend(new LegendBuilder()
                .position("se")
                .backgroundColor("#D2E8FF"))
            .mouse(new MouseBuilder()
                .track(true)
                .relative(true));
    }

}