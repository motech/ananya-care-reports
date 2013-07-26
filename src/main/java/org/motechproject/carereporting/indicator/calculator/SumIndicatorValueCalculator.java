package org.motechproject.carereporting.indicator.calculator;

import org.motechproject.carereporting.domain.IndicatorEntity;
import javax.sql.DataSource;

public class SumIndicatorValueCalculator extends AbstractIndicatorValueCalculator {

    private static final String SUM_QUERY = "SELECT sum(%(fieldName))" +
                                            " FROM %(reportDbName).%(tableName)" +
                                            " %(flwJoin)" +
                                            " WHERE %(areaWhereClause)" +
                                            " AND %(frequencyWhereClause)" +
                                            " AND %(conditionsWhere)";

    public SumIndicatorValueCalculator(DataSource dataSource, IndicatorEntity indicator) {
        super(dataSource, indicator);
    }

    @Override
    protected String getQuery() {
        return SUM_QUERY;
    }

}
