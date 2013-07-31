package org.motechproject.carereporting.indicator.calculator;

import org.jooq.SelectQuery;
import org.motechproject.carereporting.domain.AreaEntity;
import org.motechproject.carereporting.domain.IndicatorEntity;
import org.motechproject.carereporting.indicator.query.CalculatorQueryBuilder;
import org.motechproject.carereporting.service.FormsService;

import javax.sql.DataSource;
import java.math.BigDecimal;

public class AverageIndicatorValueCalculator extends AbstractIndicatorValueCalculator {

    public AverageIndicatorValueCalculator(DataSource dataSource, IndicatorEntity indicator,
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
                .withOperation(CalculatorQueryBuilder.OperationType.Average)
                .build();

        Object resultObj = query.fetch().getValue(0, 0);
        String resultObjStr = (resultObj != null) ? resultObj.toString() : "0.0";

        return new BigDecimal(resultObjStr);
    }

}
