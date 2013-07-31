package org.motechproject.carereporting.indicator.calculator;

import org.jooq.SelectQuery;
import org.motechproject.carereporting.domain.AreaEntity;
import org.motechproject.carereporting.domain.IndicatorEntity;
import org.motechproject.carereporting.indicator.query.CalculatorQueryBuilder;
import org.motechproject.carereporting.service.FormsService;

import javax.sql.DataSource;
import java.math.BigDecimal;

public class CountIndicatorValueCalculator extends AbstractIndicatorValueCalculator {

    public CountIndicatorValueCalculator(DataSource dataSource, IndicatorEntity indicator,
                                         FormsService formsService) {
        super(dataSource, indicator, formsService);
    }

    @Override
    public BigDecimal calculateIndicatorValueForArea(AreaEntity area) {
        SelectQuery query = calculatorQueryBuilder
                .withIndicator(indicator)
                .withComplexCondition(indicator.getComplexCondition())
                .withArea(area)
                .withFrequency(indicator.getFrequency())
                .withOperation(CalculatorQueryBuilder.OperationType.Count)
                .build();

        return new BigDecimal((Integer) query.fetch().getValue(0, 0));
    }

}
