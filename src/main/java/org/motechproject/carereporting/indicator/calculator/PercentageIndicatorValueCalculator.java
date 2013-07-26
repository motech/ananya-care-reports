package org.motechproject.carereporting.indicator.calculator;

import org.motechproject.carereporting.domain.IndicatorEntity;
import javax.sql.DataSource;

public class PercentageIndicatorValueCalculator extends AbstractIndicatorValueCalculator {

    private static final String PERCENTAGE_QUERY =
            "SELECT ROUND(100.0 * (ROUND(t.count, 2) / ROUND(CASE t.total WHEN 0 THEN 1 ELSE t.total END, 2)), 1) as percentage" +
            " FROM (SELECT" +
                " (SELECT COUNT (%(fieldName))" +
                    " FROM %(reportDbName).%(tableName)" +
                    " %(flwJoin)" +
                    " WHERE %(areaWhereClause)" +
                    " AND %(frequencyWhereClause)" +
                    " AND %(conditionsWhere)" +
                ") AS count," +
            " COUNT(*) as total" +
                " FROM %(reportDbName).%(tableName)" +
                " %(flwJoin)" +
                " WHERE %(areaWhereClause)" +
                " AND %(frequencyWhereClause)" +
                " ) AS t";

    public PercentageIndicatorValueCalculator(DataSource dataSource, IndicatorEntity indicator) {
        super(dataSource, indicator);
    }

    @Override
    protected String getQuery() {
        return PERCENTAGE_QUERY;
    }

}
