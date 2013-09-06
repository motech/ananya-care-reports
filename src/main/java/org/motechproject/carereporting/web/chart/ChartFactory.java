package org.motechproject.carereporting.web.chart;

import org.apache.commons.lang.time.DateUtils;
import org.motechproject.carereporting.domain.IndicatorEntity;
import org.motechproject.carereporting.domain.IndicatorValueEntity;
import org.motechproject.carereporting.domain.ReportEntity;
import org.motechproject.carereporting.domain.types.ReportType;
import org.motechproject.carereporting.exception.CareNoValuesException;
import org.motechproject.carereporting.service.ReportService;
import org.motechproject.carereporting.web.chart.builder.AxisBuilder;
import org.motechproject.carereporting.web.chart.builder.BarsBuilder;
import org.motechproject.carereporting.web.chart.builder.ChartBuilder;
import org.motechproject.carereporting.web.chart.builder.GridBuilder;
import org.motechproject.carereporting.web.chart.builder.LegendBuilder;
import org.motechproject.carereporting.web.chart.builder.MouseBuilder;
import org.motechproject.carereporting.web.chart.builder.PieBuilder;
import org.motechproject.carereporting.web.chart.builder.SerieBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
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

    private SerieBuilder createSerieForIndicatorValues(List<IndicatorValueEntity> values) {
        SerieBuilder serieBuilder = new SerieBuilder();

        for (IndicatorValueEntity value: values) {
            Date date = DateUtils.addHours(value.getDate(), -HOURS);
            serieBuilder.point(BigDecimal.valueOf(date.getTime()), value.getValue());
        }

        return serieBuilder;
    }

    public Chart createLineOrBarChart(IndicatorEntity indicator, List<IndicatorValueEntity> values, ReportType type) {
        if (values.size() == 0) {
            throw new CareNoValuesException();
        }

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
                .serie(createSerieForIndicatorValues(values));

        if (ReportType.BarChart.equals(type)) {
            chart.bars(new BarsBuilder()
                    .show(true)
                    .horizontal(false)
                    .shadowSize(0));
        }

        return chart.build();
    }

    public Chart createPieChart(IndicatorEntity indicator, List<IndicatorValueEntity> values) {
        if (values.size() == 0) {
            throw new CareNoValuesException();
        }

        BigDecimal indicatorNumeratorsCombined = BigDecimal.ZERO;
        BigDecimal indicatorDenominatorsCombined = BigDecimal.ZERO;
        for (IndicatorValueEntity value : values) {
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
}
