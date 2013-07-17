package org.motechproject.carereporting.indicator;

import org.apache.log4j.Logger;
import org.motechproject.carereporting.domain.AreaEntity;
import org.motechproject.carereporting.domain.IndicatorEntity;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

import javax.sql.DataSource;
import java.math.BigDecimal;

public class SumIndicatorValueCalculator extends AbstractIndicatorValueCalculator {

    private static final Logger LOGGER = Logger.getLogger(SumIndicatorValueCalculator.class);

    private static final String SUM_QUERY_WITHOUT_CONDITIONS = "SELECT sum(%s) FROM care.%s " + TABLE_ALIAS + " "
            + FLW_JOIN + " WHERE " + AREA_WHERE_CLAUSE + " AND "+ FREQUENCY_WHERE_CLAUSE;
    private static final String SUM_QUERY_WITH_CONDITIONS = SUM_QUERY_WITHOUT_CONDITIONS + " AND (%s)";

    public SumIndicatorValueCalculator(DataSource dataSource, IndicatorEntity indicator) {
        super(dataSource, indicator);
    }

    @Override
    public BigDecimal calculateIndicatorValueForArea(AreaEntity area) {

        String sumQuery = prepareQuery(
                getTableName(),
                getField(),
                buildWhereClause());

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("areaName", area.getName());
        params.addValue("frequency", indicator.getFrequency());

        return executeQuery(sumQuery, params);
    }

    private String prepareQuery(String tableName, String fieldName, String conditionsWhereClause) {
        if (conditionsWhereClause == null) {
            String query = String.format(SUM_QUERY_WITHOUT_CONDITIONS, fieldName, tableName);
            LOGGER.info(query);

            return query;
        } else {
            String query = String.format(SUM_QUERY_WITH_CONDITIONS, fieldName, tableName, conditionsWhereClause);
            LOGGER.info(query);

            return query;
        }
    }

}
