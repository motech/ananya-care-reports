package org.motechproject.carereporting.indicator;

import org.motechproject.carereporting.domain.AreaEntity;
import org.motechproject.carereporting.domain.IndicatorEntity;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

import javax.sql.DataSource;
import java.math.BigDecimal;

public class AverageIndicatorValueCalculator extends AbstractIndicatorValueCalculator {

    private static final String AVERAGE_QUERY_WITHOUT_CONDITIONS = "SELECT avg(%s) FROM " + REPORT_DB_NAME + ".%s "
            + TABLE_ALIAS + " " + FLW_JOIN + " WHERE " + AREA_WHERE_CLAUSE + " AND " + FREQUENCY_WHERE_CLAUSE;
    private static final String AVERAGE_QUERY_WITH_CONDITIONS = AVERAGE_QUERY_WITHOUT_CONDITIONS + " AND %s";

    public AverageIndicatorValueCalculator(DataSource dataSource, IndicatorEntity indicator) {
        super(dataSource, indicator);
    }

    @Override
    public BigDecimal calculateIndicatorValueForArea(AreaEntity area) {

        String averageQuery = prepareQuery(
                getTableName(),
                getField(),
                buildWhereClause());

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("areaName", area.getName());
        params.addValue("frequency", indicator.getFrequency());

        return executeQuery(averageQuery, params);
    }

    private String prepareQuery(String tableName, String fieldName, String conditionsWhereClause) {
        if (conditionsWhereClause == null) {
            return String.format(AVERAGE_QUERY_WITHOUT_CONDITIONS, fieldName, tableName, conditionsWhereClause);
        }
        return String.format(AVERAGE_QUERY_WITH_CONDITIONS, fieldName, tableName, conditionsWhereClause);
    }
}
