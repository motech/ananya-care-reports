package org.dwQueryBuilder.data.queries;

import org.dwQueryBuilder.data.DwQueryCombination;
import org.dwQueryBuilder.data.Fact;
import org.dwQueryBuilder.data.GroupBy;
import org.dwQueryBuilder.data.SelectColumn;
import org.dwQueryBuilder.data.conditions.where.WhereConditionGroup;

import java.util.Set;

public class ComplexDwQuery extends DwQuery {
    private String dimension;
    private Set<Fact> facts;
    private String dimensionKey;
    private String factKey;

    public ComplexDwQuery() {
        super();
    }

    public ComplexDwQuery(Set<SelectColumn> selectColumns, String dimension,
                          Set<Fact> facts, String dimensionKey, String factKey) {
        super(selectColumns);

        this.dimension = dimension;
        this.facts = facts;
        this.dimensionKey = dimensionKey;
        this.factKey = factKey;
    }

    public ComplexDwQuery(Set<SelectColumn> selectColumns, String dimension,
                          Set<Fact> facts, String dimensionKey, String factKey, GroupBy groupBy) {
        super(selectColumns, groupBy);

        this.dimension = dimension;
        this.facts = facts;
        this.dimensionKey = dimensionKey;
        this.factKey = factKey;
    }

    public ComplexDwQuery(Set<SelectColumn> selectColumns, String dimension,
                          Set<Fact> facts, String dimensionKey, String factKey, GroupBy groupBy,
                          WhereConditionGroup whereConditionGroup, Set<DwQueryCombination> combineWith) {
        super(selectColumns, groupBy, whereConditionGroup, combineWith);

        this.dimension = dimension;
        this.facts = facts;
        this.dimensionKey = dimensionKey;
        this.factKey = factKey;
    }

    public String getDimension() {
        return dimension;
    }

    public void setDimension(String dimension) {
        this.dimension = dimension;
    }

    public Set<Fact> getFacts() {
        return facts;
    }

    public void setFacts(Set<Fact> facts) {
        this.facts = facts;
    }

    public String getDimensionKey() {
        return dimensionKey;
    }

    public void setDimensionKey(String dimensionKey) {
        this.dimensionKey = dimensionKey;
    }

    public String getFactKey() {
        return factKey;
    }

    public void setFactKey(String factKey) {
        this.factKey = factKey;
    }
}
