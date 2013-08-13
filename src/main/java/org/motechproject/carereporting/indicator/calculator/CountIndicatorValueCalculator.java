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

public class CountIndicatorValueCalculator extends AbstractIndicatorValueCalculator {

    public CountIndicatorValueCalculator(DataSource dataSource, IndicatorEntity indicator,
                                         IndicatorService indicatorService, FormsService formsService) {
        super(dataSource, indicator, indicatorService, formsService);
        nominatorOperationType = CalculatorQueryBuilder.OperationType.Count;
    }

    @Override
    protected BigDecimal getDenominator(AreaEntity areaEntity, List<IndicatorValueEntity> values) {
        return BigDecimal.ONE;
    }

}
