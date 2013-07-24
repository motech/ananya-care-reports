package org.motechproject.carereporting.indicator;

import org.motechproject.carereporting.domain.AreaEntity;
import org.motechproject.carereporting.domain.IndicatorEntity;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

import javax.sql.DataSource;
import java.math.BigDecimal;

public class PercentageIndicatorValueCalculator extends AbstractIndicatorValueCalculator {

    private static final String FREQUENCY_WHERE_CLAUSE = "(DATE_PART('day', current_date - %s.%s.time_end) < :frequency)";
    private static final String FLW_JOIN = "INNER JOIN " + REPORT_DB_NAME + ".flw flw ON %s.%s.user_id = flw.id";
    private static final String QUERY_WITH_CONDITIONS =
            "SELECT"
                + " ROUND(100.0 * (ROUND(%s.count, 2)"
                + "/ ROUND(CASE %s.total WHEN 0 THEN 1 ELSE %s.total END, 2)), 1) AS percentage"
            + " FROM"
            + " (SELECT"
                + " (SELECT COUNT (%s) FROM %s.%s"
                    + " %s WHERE %s AND %s AND (%s)"
                + " ) AS count,"
                + " COUNT (%s) AS total"
            + " FROM %s.%s"
            + " %s WHERE %s AND %s"
            + ") AS %s";

    public PercentageIndicatorValueCalculator(DataSource dataSource, IndicatorEntity indicator) {
        super(dataSource, indicator);
    }

    @Override
    public BigDecimal calculateIndicatorValueForArea(AreaEntity area) {

        String flwJoin = String.format(FLW_JOIN, REPORT_DB_NAME, getTableName());
        String frequencyClause = String.format(FREQUENCY_WHERE_CLAUSE, REPORT_DB_NAME, getTableName());

        String queryWithConditions = String.format(
                QUERY_WITH_CONDITIONS,
                TABLE_ALIAS,
                TABLE_ALIAS,
                TABLE_ALIAS,
                getField(),
                REPORT_DB_NAME,
                getTableName(),
                flwJoin,
                AREA_WHERE_CLAUSE,
                frequencyClause,
                buildWhereClause(),
                getField(),
                REPORT_DB_NAME,
                getTableName(),
                flwJoin,
                AREA_WHERE_CLAUSE,
                frequencyClause,
                TABLE_ALIAS);

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("areaName", area.getName());
        params.addValue("frequency", indicator.getFrequency());

        return executeQuery(queryWithConditions, params);
    }

}
