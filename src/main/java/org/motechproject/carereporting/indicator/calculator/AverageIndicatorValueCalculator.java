package org.motechproject.carereporting.indicator.calculator;

import org.motechproject.carereporting.domain.IndicatorEntity;
import javax.sql.DataSource;

public class AverageIndicatorValueCalculator extends AbstractIndicatorValueCalculator {

    private static final String AVERAGE_QUERY = "SELECT avg(%(fieldName)) FROM %(reportDbName).%(tableName)" +
                                                " %(flwJoin)" +
                                                " WHERE %(areaWhereClause)" +
                                                " AND %(frequencyWhereClause)" +
                                                " AND %(conditionsWhere)";

    public AverageIndicatorValueCalculator(DataSource dataSource, IndicatorEntity indicator) {
        super(dataSource, indicator);
    }

    @Override
    protected String getQuery() {
        return AVERAGE_QUERY;
    }

}
