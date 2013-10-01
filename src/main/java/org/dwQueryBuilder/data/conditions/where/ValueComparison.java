package org.dwQueryBuilder.data.conditions.where;

import org.dwQueryBuilder.data.SelectColumn;
import org.dwQueryBuilder.data.enums.ComparisonType;

public class ValueComparison extends WhereCondition {
    private ComparisonType operator;
    private String value;

    public ValueComparison() {
        super();
    }

    public ValueComparison(SelectColumn selectColumn1,
                           ComparisonType operator, String value) {
        super(selectColumn1);

        this.operator = operator;
        this.value = value;
    }

    public ComparisonType getOperator() {
        return operator;
    }

    public void setOperator(ComparisonType operator) {
        this.operator = operator;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
