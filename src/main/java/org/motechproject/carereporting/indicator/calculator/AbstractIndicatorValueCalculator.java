package org.motechproject.carereporting.indicator.calculator;

import org.motechproject.carereporting.domain.AreaEntity;
import org.motechproject.carereporting.domain.IndicatorEntity;
import org.motechproject.carereporting.indicator.query.CalculatorQueryBuilder;
import org.motechproject.carereporting.service.FormsService;

import javax.sql.DataSource;
import java.math.BigDecimal;

public abstract class AbstractIndicatorValueCalculator {

    protected final DataSource dataSource;
    protected final IndicatorEntity indicator;

    protected CalculatorQueryBuilder calculatorQueryBuilder;

    public AbstractIndicatorValueCalculator(DataSource dataSource, IndicatorEntity indicator,
                                            FormsService formsService) {
        this.dataSource = dataSource;
        this.indicator = indicator;
        this.calculatorQueryBuilder = new CalculatorQueryBuilder(dataSource, formsService);
    }

    public abstract BigDecimal calculateIndicatorValueForArea(AreaEntity area);

}
