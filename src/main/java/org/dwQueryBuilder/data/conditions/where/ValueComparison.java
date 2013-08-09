package org.dwQueryBuilder.data.conditions.where;

import org.dwQueryBuilder.data.enums.OperatorType;

public class ValueComparison extends WhereCondition {
    private OperatorType operator;
    private String value;

    public ValueComparison() {
        super();
    }

    public ValueComparison(String tableName, String fieldName,
                           OperatorType operator, String value) {
        super(tableName, fieldName);

        this.operator = operator;
        this.value = value;
    }

    public OperatorType getOperator() {
        return operator;
    }

    public void setOperator(OperatorType operator) {
        this.operator = operator;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
