package org.motechproject.carereporting.indicator;

import org.motechproject.carereporting.domain.AreaEntity;
import org.motechproject.carereporting.domain.IndicatorEntity;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

import javax.sql.DataSource;
import java.math.BigDecimal;

public class CountIndicatorValueCalculator extends AbstractIndicatorValueCalculator {

    private static final String COUNT_QUERY = "SELECT count(%s) FROM care.%s " + TABLE_ALIAS + " " + FLW_JOIN +
            " WHERE " + AREA_WHERE_CLAUSE +" AND " + FREQUENCY_WHERE_CLAUSE + " AND (%s)";

    public CountIndicatorValueCalculator(DataSource dataSource, IndicatorEntity indicator) {
        super(dataSource, indicator);
    }

    @Override
    public BigDecimal calculateIndicatorValueForArea(AreaEntity area) {

        String countQuery = prepareQuery(
                getTableName(),
                getField(),
                buildWhereClause());

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("areaName", area.getName());
        params.addValue("frequency", indicator.getFrequency());

        return executeQuery(countQuery, params);
    }

    private String prepareQuery(String tableName, String fieldName, String conditionsWhereClause) {
        return String.format(COUNT_QUERY, fieldName,
                tableName, conditionsWhereClause);
    }

}