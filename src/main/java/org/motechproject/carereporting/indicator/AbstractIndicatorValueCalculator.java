package org.motechproject.carereporting.indicator;

import org.motechproject.carereporting.domain.AreaEntity;
import org.motechproject.carereporting.domain.ConditionEntity;
import org.motechproject.carereporting.domain.IndicatorEntity;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.util.Iterator;

public abstract class AbstractIndicatorValueCalculator {

    private static final String CONDITION_WHERE_CLAUSE = "(t.%s %s %s)";
    protected static final String REPORT_DB_NAME = "report";
    protected static final String AREA_WHERE_CLAUSE = "(flw.block = :areaName OR flw.district = :areaName OR flw.village = :areaName)";
    protected static final String FREQUENCY_WHERE_CLAUSE = "(DATE_PART('day', now() - t.time_end) < :frequency)";
    protected static final String FLW_JOIN = "INNER JOIN " + REPORT_DB_NAME + ".flw flw ON t.user_id = flw.id";
    protected static final String TABLE_ALIAS = "t";

    private final DataSource dataSource;

    protected final IndicatorEntity indicator;

    public AbstractIndicatorValueCalculator(DataSource dataSource, IndicatorEntity indicator) {
        this.dataSource = dataSource;
        this.indicator = indicator;
    }

    public abstract BigDecimal calculateIndicatorValueForArea(AreaEntity area);

    protected String buildWhereClause() {
        StringBuilder sb = new StringBuilder();

        if (indicator.getComplexCondition() == null) {
            return null;
        }

        Iterator<ConditionEntity> iterator =
                indicator.getComplexCondition().getConditions().iterator();
        while (iterator.hasNext()) {

            sb.append(buildConditionWhereClause(iterator.next()));

            if (iterator.hasNext()) {
                sb.append(" AND ");
            }
        }

        return sb.toString();
    }

    private String buildConditionWhereClause(ConditionEntity condition) {
        String fieldName = getField();
        String comparisonSymbol = condition.getComparisonSymbol().getName();
        String value = condition.getComparisonValue();

        return String.format(CONDITION_WHERE_CLAUSE, fieldName, comparisonSymbol, value);
    }

    protected BigDecimal executeQuery(String query, MapSqlParameterSource params) {
        NamedParameterJdbcTemplate jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        return jdbcTemplate.queryForObject(query.toString(), params, BigDecimal.class);
    }

    protected String getField() {
        //for now assuming that condition field is a regular field, computed fields support will be added later
        return indicator.getComputedField().getRegularField().getName();
    }

    protected String getTableName() {
        return indicator.getComputedField().getForm().getTableName();
    }

}
