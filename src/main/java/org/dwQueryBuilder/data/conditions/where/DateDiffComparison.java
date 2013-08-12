package org.dwQueryBuilder.data.conditions.where;

import org.dwQueryBuilder.data.enums.OperatorType;

public class DateDiffComparison extends WhereCondition {
    private OperatorType operator;
    private String table2Name;
    private String field2Name;
    private String value;
    private String field1Offset;
    private String field2Offset;

    public DateDiffComparison() {
        super();
    }

    /**
     * Compares two dates with the possibility to specify an offset.
     * @param value The date with which to compare the first date (in seconds).
     */
    public DateDiffComparison(String table1Name, String field1Name,
                              OperatorType operator,
                              String table2Name, String field2Name,
                              String value) {
        super(table1Name, field1Name);

        this.operator = operator;
        this.table2Name = table2Name;
        this.field2Name = field2Name;
        this.value = value;
        this.field1Offset = "0";
        this.field2Offset = "0";
    }

    /**
     * Compares two dates with the possibility to specify an offset.
     * @param value The date with which to compare the first date (in seconds).
     * @param field1Offset First date offset (in seconds).
     */
    public DateDiffComparison(String table1Name, String field1Name,
                              OperatorType operator,
                              String table2Name, String field2Name,
                              String value, String field1Offset,
                              String field2Offset) {
        super(table1Name, field1Name);

        this.operator = operator;
        this.table2Name = table2Name;
        this.field2Name = field2Name;
        this.value = value;
        this.field1Offset = field1Offset;
        this.field2Offset = field2Offset;
    }

    public OperatorType getOperator() {
        return operator;
    }

    public void setOperator(OperatorType operator) {
        this.operator = operator;
    }

    public String getTable2Name() {
        return table2Name;
    }

    public void setTable2Name(String table2Name) {
        this.table2Name = table2Name;
    }

    public String getField2Name() {
        return field2Name;
    }

    public void setField2Name(String field2Name) {
        this.field2Name = field2Name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getField1Offset() {
        return field1Offset;
    }

    public void setField1Offset(String field1Offset) {
        this.field1Offset = field1Offset;
    }

    public String getField2Offset() {
        return field2Offset;
    }

    public void setField2Offset(String field2Offset) {
        this.field2Offset = field2Offset;
    }
}
