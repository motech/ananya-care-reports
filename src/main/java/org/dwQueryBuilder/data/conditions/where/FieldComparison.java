package org.dwQueryBuilder.data.conditions.where;

import org.dwQueryBuilder.data.enums.OperatorType;

public class FieldComparison extends WhereCondition {

    private OperatorType operator;
    private String table2Name;
    private String field2Name;

    public FieldComparison() {
        super();
    }

    public FieldComparison(String table1Name, String field1Name,
                           OperatorType operator,
                           String table2Name, String field2Name) {
        super(table1Name, field1Name);

        this.operator = operator;
        this.table2Name = table2Name;
        this.field2Name = field2Name;
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
}
