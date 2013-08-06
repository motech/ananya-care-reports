package org.motechproject.carereporting.indicator.calculator;

import org.motechproject.carereporting.domain.IndicatorEntity;
import org.motechproject.carereporting.indicator.query.CalculatorQueryBuilder;
import org.motechproject.carereporting.service.FormsService;
import org.motechproject.carereporting.service.IndicatorService;

import javax.sql.DataSource;

public class AverageIndicatorValueCalculator extends AbstractIndicatorValueCalculator {

    public AverageIndicatorValueCalculator(DataSource dataSource, IndicatorEntity indicator,
                                           IndicatorService indicatorService, FormsService formsService) {
        super(dataSource, indicator, indicatorService, formsService);
        nominatorOperationType = CalculatorQueryBuilder.OperationType.Sum;
    }

}
