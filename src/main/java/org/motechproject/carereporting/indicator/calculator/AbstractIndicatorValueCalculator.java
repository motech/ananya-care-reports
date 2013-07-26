package org.motechproject.carereporting.indicator.calculator;

import org.apache.commons.lang.text.StrSubstitutor;
import org.apache.log4j.Logger;
import org.motechproject.carereporting.domain.AreaEntity;
import org.motechproject.carereporting.domain.ConditionEntity;
import org.motechproject.carereporting.domain.DateDiffComparisonConditionEntity;
import org.motechproject.carereporting.domain.FieldComparisonConditionEntity;
import org.motechproject.carereporting.domain.IndicatorEntity;
import org.motechproject.carereporting.domain.ValueComparisonConditionEntity;
import org.motechproject.carereporting.indicator.condition.AbstractWhereCondition;
import org.motechproject.carereporting.indicator.condition.DateDiffComparisonWhereCondition;
import org.motechproject.carereporting.indicator.condition.FieldComparisonWhereCondition;
import org.motechproject.carereporting.indicator.condition.ValueComparisonWhereCondition;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

public abstract class AbstractIndicatorValueCalculator {

    private static final Logger LOG = Logger.getLogger(AbstractIndicatorValueCalculator.class);

    private static final String REPORT_DB_NAME = "report";
    private static final String AREA_WHERE_CLAUSE = "(flw.%(levelName) = :areaName)";
    private static final String FREQUENCY_WHERE_CLAUSE = "(DATE_PART('day', current_date - " + REPORT_DB_NAME + ".%(tableName).time_end) < :frequency)";
    private static final String FLW_JOIN = "INNER JOIN " + REPORT_DB_NAME + ".flw flw ON %(tableName).user_id = flw.id";

    private final DataSource dataSource;

    protected final IndicatorEntity indicator;

    public AbstractIndicatorValueCalculator(DataSource dataSource, IndicatorEntity indicator) {
        this.dataSource = dataSource;
        this.indicator = indicator;
    }

    /* Unformatted query string */
    protected abstract String getQuery();

    /* Format params are params defined as %(paramName). They are replaced before query is submitted */
    protected Map<String, String> getQueryFormatParams(AreaEntity area) {
        Map<String, String> values = new LinkedHashMap<>();
        values.put("fieldName", indicator.getComputedField().getFieldSql());
        values.put("tableName", indicator.getComputedField().getForm().getTableName());
        values.put("conditionsWhere", buildWhereClause());
        values.put("levelName", area.getLevel().getName());
        values.put("reportDbName", REPORT_DB_NAME);
        values.put("areaWhereClause", AREA_WHERE_CLAUSE);
        values.put("frequencyWhereClause", FREQUENCY_WHERE_CLAUSE);
        values.put("flwJoin", FLW_JOIN);
        return values;
    }

    private String buildWhereClause() {
        StringBuilder sb = new StringBuilder();

        if (indicator.getComplexCondition() == null ||
                indicator.getComplexCondition().getConditions().size() == 0) {
            return "true";
        }

        Iterator<ConditionEntity> iterator = indicator.getComplexCondition().getConditions().iterator();
        while (iterator.hasNext()) {
            sb.append(buildConditionWhereClause(iterator.next()));
            if (iterator.hasNext()) {
                sb.append(" AND ");
            }
        }
        return sb.toString();
    }

    private String buildConditionWhereClause(ConditionEntity condition) {
        return getWhereCondition(condition).getSql();
    }

    private AbstractWhereCondition getWhereCondition(ConditionEntity condition) {
        if (condition instanceof FieldComparisonConditionEntity) {
            return new FieldComparisonWhereCondition(indicator.getComputedField(),
                    (FieldComparisonConditionEntity) condition);
        } else if (condition instanceof ValueComparisonConditionEntity) {
            return new ValueComparisonWhereCondition(indicator.getComputedField(),
                    (ValueComparisonConditionEntity) condition);
        } else if (condition instanceof DateDiffComparisonConditionEntity) {
            return new DateDiffComparisonWhereCondition(indicator.getComputedField(),
                    (DateDiffComparisonConditionEntity) condition);
        }
        throw new UnsupportedOperationException("Condition type " +
                condition.getClass().getName() + " not supported.");
    }

    /* Bind params are params defined as :paramName. They are sent along with query instead of being replaced in code */
    protected MapSqlParameterSource getQueryBindParams(AreaEntity area) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("areaName", area.getName());
        params.addValue("frequency", indicator.getFrequency());
        return params;
    }

    public BigDecimal calculateIndicatorValueForArea(AreaEntity area) {
        Map<String, String> formatParams = getQueryFormatParams(area);
        String query = format(getQuery(), formatParams);
        return executeQuery(query, getQueryBindParams(area));
    };

    private String format(String strToFormat, Map<String, String> params) {
        return new StrSubstitutor(params, "%(", ")").replace(strToFormat);
    }

    private BigDecimal executeQuery(String query, MapSqlParameterSource params) {
        LOG.info("Executing query: " + query);
        for (Map.Entry<String, Object> param: params.getValues().entrySet()) {
            LOG.info("param: " + param.getKey() + " = " + param.getValue());
        }
        NamedParameterJdbcTemplate jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        return jdbcTemplate.queryForObject(query.toString(), params, BigDecimal.class);
    }

}
