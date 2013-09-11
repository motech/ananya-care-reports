package org.motechproject.carereporting.web.chart;

import org.apache.commons.lang.time.DateUtils;
import org.motechproject.carereporting.domain.IndicatorEntity;
import org.motechproject.carereporting.domain.ReportEntity;
import org.motechproject.carereporting.domain.dto.CategorizedValueDto;
import org.motechproject.carereporting.domain.dto.IndicatorValueDto;
import org.motechproject.carereporting.domain.types.ReportType;
import org.motechproject.carereporting.service.ReportService;
import org.motechproject.carereporting.web.chart.builder.AxisBuilder;
import org.motechproject.carereporting.web.chart.builder.BarsBuilder;
import org.motechproject.carereporting.web.chart.builder.ChartBuilder;
import org.motechproject.carereporting.web.chart.builder.GridBuilder;
import org.motechproject.carereporting.web.chart.builder.LegendBuilder;
import org.motechproject.carereporting.web.chart.builder.MouseBuilder;
import org.motechproject.carereporting.web.chart.builder.PieBuilder;
import org.motechproject.carereporting.web.chart.builder.SelectionBuilder;
import org.motechproject.carereporting.web.chart.builder.SerieBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public final class ChartFactory {

    @Autowired
    private ReportService reportService;

    private static final int PERCENT = 5;
    private static final int HOURS = 22;
    private static final int SCALE = 4;
    private static final int CREATE_LINE_CHART_VARIABLE = 4;
    private static final int EXPLOSION_NUMBER = 6;
    private static final int SELECTION_FPS = 30;

    // for 1.0 - there is no space between clusters
    private static final double BAR_CLUSTER_SCALE = 0.5;

    private SerieBuilder createSerieForIndicatorValues(List<IndicatorValueDto> values) {
        SerieBuilder serieBuilder = new SerieBuilder();

        for (IndicatorValueDto value: values) {
            Date date = DateUtils.addHours(value.getDate(), -HOURS);
            serieBuilder.point(BigDecimal.valueOf(date.getTime()), value.getValue());
        }

        return serieBuilder;
    }

    public Chart createLineOrBarChart(IndicatorEntity indicator, List<IndicatorValueDto> values, ReportType type) {
        ReportEntity reportEntity = reportService.getReportByTypeAndIndicatorId(
                type, indicator.getId());
        String labelX = (reportEntity.getLabelX() == null) ? "" : reportEntity.getLabelX();
        String labelY = (reportEntity.getLabelY() == null) ? "" : reportEntity.getLabelY();

        double [] yMinAndMax = null;

        if (ReportType.LineChart.equals(type)) {
            yMinAndMax = ChartHelper.getMinAndMaxMarginFromValue(values, 3, true);
        } else if (ReportType.BarChart.equals(type)) {
            yMinAndMax = ChartHelper.getMinAndMaxMarginFromValue(values, PERCENT, false);
        }

        double[] xMinAndMax = ChartHelper.getMinAndMaxMarginFromDate(values);

        ChartBuilder chart = createTemplateChart(indicator.getName())
                .xAxis(new AxisBuilder()
                        .title(labelX)
                        .timeformat("%m/%d/%y")
                        .mode(AxisBuilder.Mode.TIME)
                        .minorTickFreq(CREATE_LINE_CHART_VARIABLE)
                        .min(xMinAndMax[0])
                        .max(xMinAndMax[1]))
                .yAxis(new AxisBuilder()
                        .title(labelY)
                        .min(yMinAndMax[0])
                        .max(yMinAndMax[1]))
                .grid(new GridBuilder()
                        .minorVerticalLines(true))
                .serie(createSerieForIndicatorValues(values))
                .selection(new SelectionBuilder()
                        .fps(SELECTION_FPS)
                        .mode(SelectionBuilder.Mode.X));

        if (ReportType.BarChart.equals(type)) {
            chart.bars(new BarsBuilder()
                    .show(true)
                    .horizontal(false)
                    .shadowSize(0));
        }

        return chart.build();
    }

    public Chart createPieChart(IndicatorEntity indicator, List<IndicatorValueDto> values) {
        BigDecimal indicatorNumeratorsCombined = BigDecimal.ZERO;
        BigDecimal indicatorDenominatorsCombined = BigDecimal.ZERO;
        for (IndicatorValueDto value : values) {
            indicatorNumeratorsCombined = indicatorNumeratorsCombined.add(value.getNumerator());
            indicatorDenominatorsCombined = indicatorDenominatorsCombined.add(value.getDenominator());
        }
        BigDecimal chartValue = indicatorNumeratorsCombined.divide(indicatorDenominatorsCombined, SCALE, RoundingMode.HALF_UP);

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
                        .point(BigDecimal.ZERO, chartValue))
                .serie(new SerieBuilder()
                        .point(BigDecimal.ZERO, BigDecimal.ONE.subtract(chartValue)))
                .build();
    }

    private ChartBuilder createTemplateChart(String title) {
        return new ChartBuilder()
                .title(title)
                .htmlText(false)
                .legend(new LegendBuilder()
                        .position(LegendBuilder.Position.BOTTOM_LEFT)
                        .backgroundColor("#D2E8FF"))
                .mouse(new MouseBuilder()
                        .track(true)
                        .relative(true)
                        .position("s"));
    }

    public Chart createClusteredChart(IndicatorEntity indicator, List<CategorizedValueDto> values, ReportType type) {
        double[] xMinAndMax = ChartHelper.getMinAndMaxMarginFromDateCategorized(values);

        double [] yMinAndMax = null;
        if (ReportType.LineChart.equals(type)) {
            yMinAndMax = ChartHelper.getMinAndMaxMarginFromValueCategorized(values, 3, true);
        } else if (ReportType.BarChart.equals(type)) {
            yMinAndMax = ChartHelper.getMinAndMaxMarginFromValueCategorized(values, PERCENT, false);
        }

        double barWidth = 0;
        if (ReportType.BarChart.equals(type)) {
            barWidth = ChartHelper.getBarWidthForCategorizedChart(values, xMinAndMax);
        }

        ReportEntity reportEntity = reportService.getReportByTypeAndIndicatorId(
                ReportType.BarChart, indicator.getId());
        String labelX = (reportEntity.getLabelX() == null) ? "" : reportEntity.getLabelX();
        String labelY = (reportEntity.getLabelY() == null) ? "" : reportEntity.getLabelY();

        List<SerieBuilder> serieBuilders = new ArrayList<SerieBuilder>();
        double xPosition = values.size() % 2 != 0 ? 0 : barWidth * BAR_CLUSTER_SCALE;
        for (CategorizedValueDto categorizedValue : values) {
            SerieBuilder serieBuilder = new SerieBuilder();
            for (IndicatorValueDto value : categorizedValue.getValues()) {
                Date date = DateUtils.addHours(value.getDate(), -HOURS);
                serieBuilder.point(BigDecimal.valueOf(date.getTime() + xPosition), value.getValue());
                serieBuilder.label(categorizedValue.getCategoryName());
            }
            xPosition = xPosition > 0 ? -xPosition : xPosition + barWidth;
            serieBuilders.add(serieBuilder);
        }

        ChartBuilder chart = createTemplateChart(indicator.getName())
                .legend(new LegendBuilder()
                        .show(true)
                        .backgroundColor("#FFFFFF")
                        .labelBoxMargin(0)
                        .labelBoxBorderColor("#FFFFFF")
                        .margin(2)
                        .backgroundOpacity(0))
                .selection(new SelectionBuilder()
                        .fps(SELECTION_FPS)
                        .mode(SelectionBuilder.Mode.X));

        createGrid(chart, type);
        createXAxis(chart, type, xMinAndMax, labelX);
        createYAxis(chart, type, yMinAndMax, labelY);
        createBarsOrPie(chart, type, barWidth);

        for (SerieBuilder serieBuilder : serieBuilders) {
            chart.serie(serieBuilder);
        }

        return chart.build();
    }

    private void createGrid(ChartBuilder chart, ReportType type) {
        GridBuilder gridBuilder = new GridBuilder();
        if (type.equals(ReportType.PieChart)) {
            gridBuilder
                    .horizontalLines(false)
                    .verticalLines(false);
        }
        chart.grid(gridBuilder
                .minorVerticalLines(true));
    }

    private void createXAxis(ChartBuilder chart, ReportType type, double[] xMinAndMax, String labelX) {
        AxisBuilder axisBuilder = new AxisBuilder();
        if (type.equals(ReportType.PieChart)) {
            axisBuilder.showLabels(false);
        }
        chart.xAxis(axisBuilder
                .title(labelX)
                .timeformat("%m/%d/%y")
                .mode(AxisBuilder.Mode.TIME)
                .minorTickFreq(CREATE_LINE_CHART_VARIABLE)
                .min(xMinAndMax[0])
                .max(xMinAndMax[1]));
    }

    private void createYAxis(ChartBuilder chart, ReportType type, double[] yMinAndMax, String labelY) {
        if (type.equals(ReportType.PieChart)) {
            chart.yAxis(new AxisBuilder().showLabels(false));
        } else {
            chart.yAxis(new AxisBuilder()
                    .title(labelY)
                    .min(yMinAndMax[0]));
        }
    }


    private void createBarsOrPie(ChartBuilder chart, ReportType type, double barWidth) {
        if (ReportType.BarChart.equals(type)) {
            chart.bars(new BarsBuilder()
                    .show(true)
                    .barWidth(barWidth));
        } else if (ReportType.PieChart.equals(type)) {
            chart.pie(new PieBuilder().show(true));
        }
    }

}
