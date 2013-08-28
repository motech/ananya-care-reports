package org.dwQueryBuilder.data.conditions.where;

import org.dwQueryBuilder.data.SelectColumn;
import org.dwQueryBuilder.data.enums.ComparisonType;

public class DateDiffComparison extends WhereCondition {
    private ComparisonType operator;
    private SelectColumn selectColumn2;
    private String value;
    private String column1Offset;
    private String column2Offset;

    public DateDiffComparison() {
        super();
    }

    /**
     * Compares two dates with the possibility to specify an offset.
     * @param value The date with which to compare the first date (in seconds).
     */
    public DateDiffComparison(String table1Name, String field1Name,
                              ComparisonType operator,
                              String table2Name, String field2Name,
                              String value) {
        super(table1Name, field1Name);

        this.operator = operator;
        this.selectColumn2 = new SelectColumn(table2Name, field2Name);
        this.value = value;
        this.column1Offset = "0";
        this.column2Offset = "0";
    }

    /**
     * Compares two dates with the possibility to specify an offset.
     * @param value The date with which to compare the first date (in seconds).
     */
    public DateDiffComparison(SelectColumn selectColumn1,
                              ComparisonType operator,
                              SelectColumn selectColumn2,
                              String value) {
        super(selectColumn1);

        this.operator = operator;
        this.selectColumn2 = selectColumn2;
        this.value = value;
        this.column1Offset = "0";
        this.column2Offset = "0";
    }

    /**
     * Compares two dates with the possibility to specify an offset.
     * @param value The date with which to compare the first date (in seconds).
     * @param column1Offset First date offset (in seconds).
     */
    public DateDiffComparison(String table1Name, String field1Name,
                              ComparisonType operator,
                              String table2Name, String field2Name,
                              String value, String column1Offset,
                              String column2Offset) {
        super(table1Name, field1Name);

        this.operator = operator;
        this.selectColumn2 = new SelectColumn(table2Name, field2Name);
        this.value = value;
        this.column1Offset = column1Offset;
        this.column2Offset = column2Offset;
    }

    /**
     * Compares two dates with the possibility to specify an offset.
     * @param value The date with which to compare the first date (in seconds).
     * @param column1Offset First date offset (in seconds).
     */
    public DateDiffComparison(SelectColumn selectColumn1,
                              ComparisonType operator,
                              SelectColumn selectColumn2,
                              String value, String column1Offset,
                              String column2Offset) {
        super(selectColumn1);

        this.operator = operator;
        this.selectColumn2 = selectColumn2;
        this.value = value;
        this.column1Offset = column1Offset;
        this.column2Offset = column2Offset;
    }

    public ComparisonType getOperator() {
        return operator;
    }

    public void setOperator(ComparisonType operator) {
        this.operator = operator;
    }

    public SelectColumn getSelectColumn2() {
        return selectColumn2;
    }

    public void setSelectColumn2(SelectColumn selectColumn2) {
        this.selectColumn2 = selectColumn2;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getColumn1Offset() {
        return column1Offset;
    }

    public void setColumn1Offset(String column1Offset) {
        this.column1Offset = column1Offset;
    }

    public String getColumn2Offset() {
        return column2Offset;
    }

    public void setColumn2Offset(String column2Offset) {
        this.column2Offset = column2Offset;
    }
}
