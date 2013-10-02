package org.dwQueryBuilder.data.conditions.where;

import org.dwQueryBuilder.data.SelectColumn;
import org.dwQueryBuilder.data.enums.ComparisonType;

public class FieldComparison extends WhereCondition {

    private ComparisonType operator;
    private SelectColumn selectColumn2;
    private String column1Offset;
    private String column2Offset;

    public FieldComparison() {
        super();
    }

    public FieldComparison(SelectColumn selectColumn1,
                           String column1Offset,
                           ComparisonType operator,
                           SelectColumn selectColumn2,
                           String column2Offset) {
        super(selectColumn1);

        this.operator = operator;
        this.selectColumn2 = selectColumn2;
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
