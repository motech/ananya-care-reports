package org.dwQueryBuilder.builders;

import org.dwQueryBuilder.data.SelectColumn;
import org.dwQueryBuilder.data.conditions.HavingCondition;
import org.dwQueryBuilder.data.enums.OperatorType;

public class HavingConditionBuilder {
    private SelectColumn selectColumn;
    private OperatorType operator;
    private String value;

    public HavingConditionBuilder withSelectColumn(SelectColumn selectColumn) {
        this.selectColumn = selectColumn;
        return this;
    }

    public HavingConditionBuilder withSelectColumn(SelectColumnBuilder selectColumnBuilder) {
        this.selectColumn = selectColumnBuilder.build();
        return this;
    }

    public HavingConditionBuilder withComparison(OperatorType operator, String value) {
        this.operator = operator;
        this.value = value;
        return this;
    }

    public HavingConditionBuilder withOperator(OperatorType operator) {
        this.operator = operator;
        return this;
    }

    public HavingConditionBuilder withValue(String value) {
        this.value = value;
        return this;
    }

    public HavingCondition build() {
        return new HavingCondition(selectColumn, operator, value);
    }
}
