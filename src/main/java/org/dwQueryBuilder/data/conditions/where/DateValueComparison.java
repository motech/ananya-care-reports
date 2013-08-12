package org.dwQueryBuilder.data.conditions.where;

import org.dwQueryBuilder.data.enums.OperatorType;

public class DateValueComparison extends WhereCondition {
    private OperatorType operator;
    private String value;
    private Integer offset;

    public DateValueComparison() {
        super();
    }

    public DateValueComparison(String tableName, String fieldName,
                               OperatorType operator, String value) {
        super(tableName, fieldName);

        this.operator = operator;
        this.value = value;
        this.offset = 0;
    }

    /**
     * @param offset Field offset (in days).
     */
    public DateValueComparison(String tableName, String fieldName,
                               OperatorType operator, String value,
                               Integer offset) {
        super(tableName, fieldName);

        this.operator = operator;
        this.value = value;
        this.offset = offset;
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

    public Integer getOffset() {
        return offset;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }
}
