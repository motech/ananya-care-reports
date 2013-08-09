package org.dwQueryBuilder.data;

import org.dwQueryBuilder.data.enums.CombineType;
import org.dwQueryBuilder.data.queries.SimpleDwQuery;

public class Fact {
    private SimpleDwQuery table;
    private CombineType combineType;

    public Fact() {

    }

    public Fact(SimpleDwQuery table) {
        this.table = table;
    }

    public Fact(SimpleDwQuery table, CombineType combineType) {
        this.table = table;
        this.combineType = combineType;
    }

    public SimpleDwQuery getTable() {
        return table;
    }

    public void setTable(SimpleDwQuery table) {
        this.table = table;
    }

    public CombineType getCombineType() {
        return combineType;
    }

    public void setCombineType(CombineType combineType) {
        this.combineType = combineType;
    }
}
