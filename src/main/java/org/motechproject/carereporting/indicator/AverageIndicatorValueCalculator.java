package org.motechproject.carereporting.indicator;

import org.motechproject.carereporting.domain.AreaEntity;
import org.motechproject.carereporting.domain.IndicatorEntity;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

import javax.sql.DataSource;
import java.math.BigDecimal;

public class AverageIndicatorValueCalculator extends AbstractIndicatorValueCalculator {

    private static final String AVERAGE_QUERY = "SELECT avg(%s) FROM care.%s " + TABLE_ALIAS + " " + FLW_JOIN +
            " WHERE " + AREA_WHERE_CLAUSE + " AND " + FREQUENCY_WHERE_CLAUSE + " AND %s";

    public AverageIndicatorValueCalculator(DataSource dataSource, IndicatorEntity indicator) {
        super(dataSource, indicator);
    }

    @Override
    public BigDecimal calculateIndicatorValueForArea(AreaEntity area) {

        String queryWithConditions = String.format(
                AVERAGE_QUERY,
                getTableName(),
                getField(),
                buildWhereClause());

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("areaName", area.getName());
        params.addValue("frequency", indicator.getFrequency());

        return executeQuery(queryWithConditions, params);
    }

}