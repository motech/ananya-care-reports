package org.motechproject.carereporting.indicator.calculator;

import org.jooq.SelectQuery;
import org.motechproject.carereporting.domain.AreaEntity;
import org.motechproject.carereporting.domain.IndicatorEntity;
import org.motechproject.carereporting.indicator.query.CalculatorQueryBuilder;
import org.motechproject.carereporting.service.FormsService;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class PercentageIndicatorValueCalculator extends AbstractIndicatorValueCalculator {

    public PercentageIndicatorValueCalculator(DataSource dataSource, IndicatorEntity indicator,
                                              FormsService formsService) {
        super(dataSource, indicator, formsService);
    }

    @Override
    public BigDecimal calculateIndicatorValueForArea(AreaEntity area) {
        SelectQuery queryCount = calculatorQueryBuilder
                .withIndicator(indicator)
                .withComplexCondition(indicator.getComplexCondition())
                .withArea(area)
                .withFrequency(indicator.getFrequency())
                .withOperation(CalculatorQueryBuilder.OperationType.Count)
                .build();

        SelectQuery queryTotal = calculatorQueryBuilder.reset()
                .withIndicator(indicator)
                .withArea(area)
                .withFrequency(indicator.getFrequency())
                .withOperation(CalculatorQueryBuilder.OperationType.Count)
                .build();

        BigDecimal countNum = new BigDecimal(queryCount.fetch().getValue(0, 0).toString())
                .multiply(BigDecimal.valueOf(100.0));
        BigDecimal totalNum = new BigDecimal(queryTotal.fetch().getValue(0, 0).toString());

        BigDecimal percentage = (totalNum.compareTo(BigDecimal.ZERO) != 0)
                ? countNum.divide(totalNum, 1, RoundingMode.HALF_UP)
                : null;

        return percentage;
    }

}
