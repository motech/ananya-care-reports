package org.dwQueryBuilder.builders;

import org.dwQueryBuilder.data.Fact;
import org.dwQueryBuilder.data.enums.CombineType;
import org.dwQueryBuilder.data.queries.SimpleDwQuery;

public class FactBuilder {
    private SimpleDwQuery table;
    private CombineType combineType;

    public FactBuilder withTable(SimpleDwQuery table) {
        this.table = table;
        return this;
    }

    public FactBuilder withTable(SimpleDwQueryBuilder simpleDwQueryBuilder) {
        this.table = simpleDwQueryBuilder.build();
        return this;
    }

    public FactBuilder withCombineType(CombineType combineType) {
        this.combineType = combineType;
        return this;
    }

    public Fact build() {
        return new Fact(table, combineType);
    }
}
