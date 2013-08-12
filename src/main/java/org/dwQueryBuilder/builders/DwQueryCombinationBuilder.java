package org.dwQueryBuilder.builders;

import org.dwQueryBuilder.data.DwQueryCombination;
import org.dwQueryBuilder.data.enums.CombineType;
import org.dwQueryBuilder.data.queries.DwQuery;

public class DwQueryCombinationBuilder {
    private CombineType combineType;
    private DwQuery dwQuery;
    private String foreignKeyFieldName;
    private String referencedFieldName;

    public DwQueryCombinationBuilder withCombineType(CombineType combineType) {
        this.combineType = combineType;
        return this;
    }

    public DwQueryCombinationBuilder withDwQuery(DwQuery dwQuery) {
        this.dwQuery = dwQuery;
        return this;
    }

    public DwQueryCombinationBuilder withDwQuery(SimpleDwQueryBuilder dwQueryBuilder) {
        this.dwQuery = dwQueryBuilder.build();
        return this;
    }

    public DwQueryCombinationBuilder withDwQuery(ComplexDwQueryBuilder dwQueryBuilder) {
        this.dwQuery = dwQueryBuilder.build();
        return this;
    }

    public DwQueryCombinationBuilder withKeys(String foreignKeyFieldName, String referencedFieldName) {
        this.foreignKeyFieldName = foreignKeyFieldName;
        this.referencedFieldName = referencedFieldName;
        return this;
    }

    public DwQueryCombination build() {
        return new DwQueryCombination(combineType, dwQuery, foreignKeyFieldName, referencedFieldName);
    }
}
