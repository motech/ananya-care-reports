package org.motechproject.carereporting.web.chart;

import org.motechproject.carereporting.domain.IndicatorEntity;
import org.motechproject.carereporting.domain.IndicatorValueEntity;
import org.motechproject.carereporting.web.chart.builder.AxisBuilder;
import org.motechproject.carereporting.web.chart.builder.BarsBuilder;
import org.motechproject.carereporting.web.chart.builder.ChartBuilder;
import org.motechproject.carereporting.web.chart.builder.GridBuilder;
import org.motechproject.carereporting.web.chart.builder.LegendBuilder;
import org.motechproject.carereporting.web.chart.builder.MouseBuilder;
import org.motechproject.carereporting.web.chart.builder.PieBuilder;
import org.motechproject.carereporting.web.chart.builder.SerieBuilder;

import java.math.BigDecimal;
import java.util.List;

public final class ChartFactory {

    private ChartFactory() {
    }

    private static final int CREATE_LINE_CHART_VARIABLE = 4;
    private static final double BAR_WIDTH = .5;
    private static final int EXPLOSION_NUMBER = 6;

    public static Chart createLineChart(IndicatorEntity indicator, List<IndicatorValueEntity> values) {
        return createTemplateChart(indicator.getName())
                .xAxis(new AxisBuilder()
                        .minorTickFreq(CREATE_LINE_CHART_VARIABLE)
                        .mode(AxisBuilder.Mode.TIME)
                        .timeformat("%m/%d/%y"))
                .grid(new GridBuilder()
                        .minorVerticalLines(true))
                .serie(createSerieForIndicatorValues(values))
                .build();
    }

    private static Serie createSerieForIndicatorValues(List<IndicatorValueEntity> values) {
        SerieBuilder serieBuilder = new SerieBuilder();

        for (IndicatorValueEntity value: values) {
            serieBuilder.point(BigDecimal.valueOf(value.getDate().getTime()), value.getValue());
        }

        return serieBuilder.build();
    }

    public static Chart createBarChart(IndicatorEntity indicator, List<IndicatorValueEntity> values) {
            BigDecimal latestIndicatorValue = values.size() != 0
                    ? values.get(values.size() - 1).getValue()
                    : BigDecimal.ZERO;

        return createTemplateChart(indicator.getName())
                .bars(new BarsBuilder()
                        .show(true)
                        .horizontal(true)
                        .shadowSize(0)
                        .barWidth(BAR_WIDTH))
                .xAxis(new AxisBuilder()
                        .min(0)
                        .autoScaleMargin(1))
                .serie(new SerieBuilder()
                        .label("Actual")
                        .point(latestIndicatorValue, BigDecimal.ONE))
                .serie(new SerieBuilder()
                        .label("Scheduled")
                        .point(BigDecimal.ONE.subtract(latestIndicatorValue), 1 + BAR_WIDTH))
                .build();
    }

    public static Chart createPieChart(IndicatorEntity indicator, List<IndicatorValueEntity> values) {
        BigDecimal latestIndicatorValue = values.size() != 0
                ? values.get(values.size() - 1).getValue()
                : BigDecimal.ZERO;

        return createTemplateChart(indicator.getName())
                .grid(new GridBuilder()
                        .verticalLines(false)
                        .horizontalLines(false))
                .xAxis(new AxisBuilder()
                        .showLabels(false))
                .yAxis(new AxisBuilder()
                        .showLabels(false))
                .pie(new PieBuilder()
                        .show(true)
                        .explode(EXPLOSION_NUMBER))
                .serie(new SerieBuilder()
                        .label("Option1")
                        .point(BigDecimal.ZERO, latestIndicatorValue))
                .serie(new SerieBuilder()
                        .label("Option2")
                        .point(BigDecimal.ZERO, BigDecimal.ONE.subtract(latestIndicatorValue)))
                .build();
    }

    private static ChartBuilder createTemplateChart(String title) {
        return new ChartBuilder()
                .title(title)
                .htmlText(false)
                .legend(new LegendBuilder()
                        .position(LegendBuilder.Position.BOTTOM_LEFT)
                        .backgroundColor("#D2E8FF"))
                .mouse(new MouseBuilder()
                        .track(true)
                        .relative(true));
    }
}
