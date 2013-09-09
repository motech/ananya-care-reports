package org.motechproject.carereporting.web.chart;

import org.apache.commons.lang.time.DateUtils;
import org.motechproject.carereporting.domain.dto.CategorizedValueDto;
import org.motechproject.carereporting.domain.dto.IndicatorValueDto;

import java.util.List;

public final class ChartHelper {

    // I don't know why 22 :(. Then the chart is correct.
    private static final int HOURS = 22;
    private static final int MILISECONDS = 1000;
    private static final int PERCENT = 100;

    private ChartHelper() {

    }

    public static double[] getMinAndMaxMarginFromDateCategorized(List<CategorizedValueDto> values) {
        double min = Double.MAX_VALUE;
        double max = 0;
        for (CategorizedValueDto categorizedValue : values) {
            double[] categoryMinAndMax = getMinAndMaxMarginFromDate(categorizedValue.getValues());
            if (min > categoryMinAndMax[0]) {
                min = categoryMinAndMax[0];
            }
            if (max < categoryMinAndMax[1]) {
                max = categoryMinAndMax[1];
            }
        }
        return new double[] {min, max};
    }

    public static double[] getMinAndMaxMarginFromDate(List<IndicatorValueDto> values) {
        double startTime = DateUtils.addHours(values.get(0).getDate(), -HOURS).getTime();
        double endTime = DateUtils.addHours(values.get(values.size() - 1).getDate(), -HOURS).getTime();
        double delta = endTime - startTime != 0 ? (endTime - startTime) / values.size() - 1 : MILISECONDS;

        return new double[] {startTime - delta, endTime + delta};
    }

    public static double[] getMinAndMaxMarginFromValue(List<IndicatorValueDto> values, int percent, boolean findMin) {
        double min = values.get(0).getValue().doubleValue();
        double max = values.get(0).getValue().doubleValue();

        if (findMin) {
            for (IndicatorValueDto valueEntity : values) {
                if (min > valueEntity.getValue().doubleValue()) {
                    min = valueEntity.getValue().doubleValue();
                }
            }
        } else {
            min = 0;
        }

        for (IndicatorValueDto valueEntity : values) {
            if (max < valueEntity.getValue().doubleValue()) {
                max = valueEntity.getValue().doubleValue();
            }
        }

        double delta = (max - min) * percent / PERCENT;

        return new double[] {findMin ? min - delta : 0, max + delta};
    }

    public static double getBarWidthForCategorizedChart(List<CategorizedValueDto> values) {
        double firstToLast =
                values.get(0).getValues().get(values.get(0).getValues().size() - 1).getDate().getTime() -
                        values.get(0).getValues().get(0).getDate().getTime();

        return firstToLast / values.get(0).getValues().size() / 2;
    }
}
