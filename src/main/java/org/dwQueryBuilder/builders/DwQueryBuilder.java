package org.dwQueryBuilder.builders;

import org.dwQueryBuilder.data.DwQuery;
import org.dwQueryBuilder.data.DwQueryCombination;
import org.dwQueryBuilder.data.GroupBy;
import org.dwQueryBuilder.data.SelectColumn;
import org.dwQueryBuilder.data.conditions.where.WhereConditionGroup;

import java.util.LinkedHashSet;
import java.util.Set;

public class DwQueryBuilder {
    private Set<SelectColumn> selectColumns;
    private String tableName;
    private GroupBy groupBy;
    private Set<DwQueryCombination> combineWith;
    private WhereConditionGroup whereConditionGroup;
    private Integer limit;

    public DwQueryBuilder withSelectColumn(SelectColumn selectColumn) {
        if (this.selectColumns == null) {
            this.selectColumns = new LinkedHashSet<>();
        }

        this.selectColumns.add(selectColumn);
        return this;
    }

    public DwQueryBuilder withSelectColumn(SelectColumnBuilder selectColumnBuilder) {
        if (this.selectColumns == null) {
            this.selectColumns = new LinkedHashSet<>();
        }

        this.selectColumns.add(selectColumnBuilder.build());
        return this;
    }

    public DwQueryBuilder withSelectColumns(Set<SelectColumn> selectColumns) {
        this.selectColumns = new LinkedHashSet<>(selectColumns);
        return this;
    }

    public DwQueryBuilder withTableName(String tableName) {
        this.tableName = tableName;
        return this;
    }

    public DwQueryBuilder withGroupBy(GroupBy groupBy) {
        this.groupBy = groupBy;
        return this;
    }

    public DwQueryBuilder withGroupBy(GroupByConditionBuilder groupByConditionBuilder) {
        this.groupBy = groupByConditionBuilder.build();
        return this;
    }

    public DwQueryBuilder withCombination(DwQueryCombination combination) {
        if (this.combineWith == null) {
            this.combineWith = new LinkedHashSet<>();
        }

        this.combineWith.add(combination);
        return this;
    }

    public DwQueryBuilder withCombination(DwQueryCombinationBuilder combinationBuilder) {
        if (this.combineWith == null) {
            this.combineWith = new LinkedHashSet<>();
        }

        this.combineWith.add(combinationBuilder.build());
        return this;
    }

    public DwQueryBuilder withCombinations(Set<DwQueryCombination> combinations) {
        this.combineWith = combinations;
        return this;
    }

    public DwQueryBuilder withWhereConditionGroup(WhereConditionGroup whereConditionGroup) {
        this.whereConditionGroup = whereConditionGroup;
        return this;
    }

    public DwQueryBuilder withWhereConditionGroup(WhereConditionGroupBuilder whereConditionGroup) {
        this.whereConditionGroup = whereConditionGroup.build();
        return this;
    }

    public DwQueryBuilder withLimit(Integer limit) {
        this.limit = limit;
        return this;
    }

    public DwQuery build() {
        return new DwQuery(selectColumns, tableName, groupBy, combineWith, whereConditionGroup, limit);
    }
}
