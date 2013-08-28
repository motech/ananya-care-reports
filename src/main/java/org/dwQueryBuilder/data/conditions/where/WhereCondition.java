package org.dwQueryBuilder.data.conditions.where;

import org.dwQueryBuilder.data.SelectColumn;
import org.dwQueryBuilder.data.conditions.BaseCondition;

public abstract class WhereCondition extends BaseCondition {
    private SelectColumn selectColumn1;

    protected WhereCondition() {
        super();
    }

    protected WhereCondition(String table1Name, String field1Name) {
        this.selectColumn1 = new SelectColumn(table1Name, field1Name);
    }

    protected WhereCondition(SelectColumn selectColumn1) {
        this.selectColumn1 = selectColumn1;
    }

    public SelectColumn getSelectColumn1() {
        return selectColumn1;
    }

    public void setSelectColumn1(SelectColumn selectColumn1) {
        this.selectColumn1 = selectColumn1;
    }
}
