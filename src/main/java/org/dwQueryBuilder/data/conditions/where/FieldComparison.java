package org.dwQueryBuilder.data.conditions.where;

import org.dwQueryBuilder.data.SelectColumn;
import org.dwQueryBuilder.data.enums.ComparisonType;

public class FieldComparison extends WhereCondition {

    private ComparisonType operator;
    private SelectColumn selectColumn2;

    public FieldComparison() {
        super();
    }

    public FieldComparison(String table1Name, String field1Name,
                           ComparisonType operator,
                           String table2Name, String field2Name) {
        super(table1Name, field1Name);

        this.operator = operator;
        this.selectColumn2 = new SelectColumn(table2Name, field2Name);
    }

    public FieldComparison(SelectColumn selectColumn1,
                           ComparisonType operator,
                           SelectColumn selectColumn2) {
        super(selectColumn1);

        this.operator = operator;
        this.selectColumn2 = selectColumn2;
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
}
