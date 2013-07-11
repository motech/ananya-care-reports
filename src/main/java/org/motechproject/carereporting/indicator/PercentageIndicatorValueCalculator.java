package org.motechproject.carereporting.indicator;

import org.motechproject.carereporting.domain.AreaEntity;
import org.motechproject.carereporting.domain.IndicatorEntity;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

import javax.sql.DataSource;
import java.math.BigDecimal;

public class PercentageIndicatorValueCalculator extends AbstractIndicatorValueCalculator {

    private static final String QUERY_WITHOUT_CONDITIONS = "SELECT count(%s) FROM care.%s " + TABLE_ALIAS + " " + FLW_JOIN +
                                                            " WHERE " + AREA_WHERE_CLAUSE + " AND "+ FREQUENCY_WHERE_CLAUSE;

    private static final String QUERY_WITH_CONDITIONS = QUERY_WITHOUT_CONDITIONS + " AND (%s)";

    public PercentageIndicatorValueCalculator(DataSource dataSource, IndicatorEntity indicator) {
        super(dataSource, indicator);
    }

    @Override
    public BigDecimal calculateIndicatorValueForArea(AreaEntity area) {

        String queryWithConditions = String.format(
                QUERY_WITH_CONDITIONS,
                getTableName(),
                getField(),
                buildWhereClause());

        String queryWithoutConditions = String.format(
                QUERY_WITHOUT_CONDITIONS,
                getField(),
                getTableName());

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("areaName", area.getName());
        params.addValue("frequency", indicator.getFrequency());

        BigDecimal resultWithConditions = executeQuery(queryWithConditions, params);
        BigDecimal resultWithoutConditions = executeQuery(queryWithoutConditions, params);

        return resultWithConditions.divide(resultWithoutConditions);
    }

}