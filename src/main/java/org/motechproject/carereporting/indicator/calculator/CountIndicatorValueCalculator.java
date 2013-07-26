package org.motechproject.carereporting.indicator.calculator;

import org.motechproject.carereporting.domain.IndicatorEntity;
import javax.sql.DataSource;

public class CountIndicatorValueCalculator extends AbstractIndicatorValueCalculator {

    private static final String COUNT_QUERY = "SELECT count(%(fieldName)) FROM %(reportDbName).%(tableName)" +
                                              " %(flwJoin)" +
                                              " WHERE %(areaWhereClause)" +
                                              " AND %(frequencyWhereClause)" +
                                              " AND %(conditionsWhere)";

    public CountIndicatorValueCalculator(DataSource dataSource, IndicatorEntity indicator) {
        super(dataSource, indicator);
    }

    @Override
    protected String getQuery() {
        return COUNT_QUERY;
    }

}
