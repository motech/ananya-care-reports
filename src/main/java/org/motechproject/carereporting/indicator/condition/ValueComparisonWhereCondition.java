package org.motechproject.carereporting.indicator.condition;

import org.motechproject.carereporting.domain.ComputedFieldEntity;
import org.motechproject.carereporting.domain.ValueComparisonConditionEntity;

import java.util.LinkedHashMap;
import java.util.Map;

public class ValueComparisonWhereCondition extends AbstractWhereCondition<ValueComparisonConditionEntity> {

    private static final String VALUE_COMPARISON_CONDITION_WHERE_CLAUSE = "(%(firstField) %(comparisonSymbol) %(value))";

    public ValueComparisonWhereCondition(ComputedFieldEntity computedField,
                                         ValueComparisonConditionEntity condition) {
        super(computedField, condition);
    }

    @Override
    protected String getSqlQuery() {
        return VALUE_COMPARISON_CONDITION_WHERE_CLAUSE;
    }

    @Override
    protected Map<String, String> getSqlQueryParams() {
        Map<String, String> params = new LinkedHashMap<>();
        params.put("firstField", getField().getFieldSql());
        params.put("comparisonSymbol", getCondition().getComparisonSymbol().getName());
        params.put("value", getCondition().getValue());
        return params;
    }

}
