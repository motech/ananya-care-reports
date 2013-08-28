package org.dwQueryBuilder.data.conditions.where;

import org.dwQueryBuilder.data.SelectColumn;
import org.dwQueryBuilder.data.enums.ComparisonType;

public class DateValueComparison extends WhereCondition {
    private ComparisonType operator;
    private String value;
    private Integer offset;

    public DateValueComparison() {
        super();
    }

    public DateValueComparison(String tableName, String fieldName,
                               ComparisonType operator, String value) {
        super(tableName, fieldName);

        this.operator = operator;
        this.value = value;
        this.offset = 0;
    }

    public DateValueComparison(SelectColumn selectColumn1,
                               ComparisonType operator, String value) {
        super(selectColumn1);

        this.operator = operator;
        this.value = value;
        this.offset = 0;
    }

    /**
     * @param offset Field offset (in days).
     */
    public DateValueComparison(String tableName, String fieldName,
                               ComparisonType operator, String value,
                               Integer offset) {
        super(tableName, fieldName);

        this.operator = operator;
        this.value = value;
        this.offset = offset;
    }

    /**
     * @param offset Field offset (in days).
     */
    public DateValueComparison(SelectColumn selectColumn1,
                               ComparisonType operator, String value,
                               Integer offset) {
        super(selectColumn1);

        this.operator = operator;
        this.value = value;
        this.offset = offset;
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

    public Integer getOffset() {
        return offset;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }
}
