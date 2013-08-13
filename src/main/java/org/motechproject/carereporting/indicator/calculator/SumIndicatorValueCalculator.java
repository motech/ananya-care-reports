package org.motechproject.carereporting.indicator.calculator;

import org.motechproject.carereporting.domain.AreaEntity;
import org.motechproject.carereporting.domain.IndicatorEntity;
import org.motechproject.carereporting.domain.IndicatorValueEntity;
import org.motechproject.carereporting.indicator.query.CalculatorQueryBuilder;
import org.motechproject.carereporting.service.FormsService;
import org.motechproject.carereporting.service.IndicatorService;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.util.List;

public class SumIndicatorValueCalculator extends AbstractIndicatorValueCalculator {

    public SumIndicatorValueCalculator(DataSource dataSource, IndicatorEntity indicator,
                                       IndicatorService indicatorService, FormsService formsService) {
        super(dataSource, indicator, indicatorService, formsService);
        nominatorOperationType = CalculatorQueryBuilder.OperationType.Sum;
    }

    @Override
    protected BigDecimal getDenominator(AreaEntity areaEntity, List<IndicatorValueEntity> values) {
        return BigDecimal.ONE;
    }

}
