package org.motechproject.carereporting.indicator.condition;

import org.motechproject.carereporting.domain.ComputedFieldEntity;
import org.motechproject.carereporting.domain.FieldComparisonConditionEntity;

import java.util.LinkedHashMap;
import java.util.Map;

public class FieldComparisonWhereCondition extends AbstractWhereCondition<FieldComparisonConditionEntity> {

    private static final String FIELD_COMPARISON_CONDITION_WHERE_CLAUSE = "(%(firstField) %(comparisonSymbol) %(secondField))";

    public FieldComparisonWhereCondition(ComputedFieldEntity computedField,
                                         FieldComparisonConditionEntity condition) {
        super(computedField, condition);
    }

    @Override
    protected String getSqlQuery() {
        return FIELD_COMPARISON_CONDITION_WHERE_CLAUSE;
    }

    @Override
    protected Map<String, String> getSqlQueryParams() {
        Map<String, String> params = new LinkedHashMap<>();
        params.put("firstField", getField().getFieldSql());
        params.put("comparisonSymbol", getCondition().getComparisonSymbol().getName());
        params.put("secondField", getCondition().getField2().getFieldSql());
        return params;
    }

}
