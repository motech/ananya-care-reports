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
import java.util.Random;

public final class ChartFactory {

    private ChartFactory() {
    }

    public static Chart createLineChart(IndicatorEntity indicator, List<IndicatorValueEntity> values) {
        return createTemplateChart(indicator.getName())
                .xAxis(new AxisBuilder()
                        .minorTickFreq(4)
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
        double barWidth = .5;
        Random random = new Random();
        return createTemplateChart(indicator.getName())
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

    public static Chart createPieChart(IndicatorEntity indicator, List<IndicatorValueEntity> values) {
        BigDecimal averageValueFromAllIndicatorValues = BigDecimal.ZERO;

        if (values.size() != 0) {
            for (IndicatorValueEntity value: values) {
                averageValueFromAllIndicatorValues =
                        averageValueFromAllIndicatorValues.add(value.getValue());
            }
            averageValueFromAllIndicatorValues =
                    averageValueFromAllIndicatorValues.divide(BigDecimal.valueOf(values.size()));
        }
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
                        .explode(6))
                .serie(new SerieBuilder()
                        .label("Option1")
                        .point(BigDecimal.ZERO, averageValueFromAllIndicatorValues))
                .serie(new SerieBuilder()
                        .label("Option2")
                        .point(BigDecimal.ZERO, BigDecimal.ONE.subtract(averageValueFromAllIndicatorValues)))
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
