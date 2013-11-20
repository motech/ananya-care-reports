package org.dwQueryBuilder.builders;

import org.dwQueryBuilder.data.DwQuery;
import org.dwQueryBuilder.data.DwQueryCombination;
import org.dwQueryBuilder.data.GroupBy;
import org.dwQueryBuilder.data.OrderBy;
import org.dwQueryBuilder.data.SelectColumn;
import org.dwQueryBuilder.data.conditions.where.WhereConditionGroup;
import org.dwQueryBuilder.data.enums.OrderByType;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class DwQueryBuilder {
    private Set<SelectColumn> selectColumns;
    private String tableName;
    private GroupBy groupBy;
    private Set<DwQueryCombination> combineWith;
    private WhereConditionGroup whereConditionGroup;
    private List<OrderBy> orderBy;
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

    public DwQueryBuilder withWhereConditionGroup(WhereConditionGroup whereConditionGroup) {
        this.whereConditionGroup = whereConditionGroup;
        return this;
    }

    public DwQueryBuilder withWhereConditionGroup(WhereConditionGroupBuilder whereConditionGroup) {
        this.whereConditionGroup = whereConditionGroup.build();
        return this;
    }

    public DwQueryBuilder withOrderBy(OrderBy orderBy) {
        if (this.orderBy == null) {
            this.orderBy = new ArrayList<>();
        }

        this.orderBy.add(orderBy);
        return this;
    }

    public DwQueryBuilder withOrderBy(SelectColumn selectColumn, OrderByType type) {
        if (this.orderBy == null) {
            this.orderBy = new ArrayList<>();
        }

        this.orderBy.add(new OrderBy(selectColumn, type));
        return this;
    }

    public DwQueryBuilder withOrderBy(SelectColumnBuilder selectColumn, OrderByType type) {
        if (this.orderBy == null) {
            this.orderBy = new ArrayList<>();
        }

        this.orderBy.add(new OrderBy(selectColumn.build(), type));
        return this;
    }

    public DwQueryBuilder withOrderBy(List<OrderBy> orderBy) {
        this.orderBy = new ArrayList<>(orderBy);
        return this;
    }

    public DwQueryBuilder withLimit(Integer limit) {
        this.limit = limit;
        return this;
    }

    public DwQuery build() {
        return new DwQuery(selectColumns, tableName, groupBy, combineWith, whereConditionGroup, orderBy, limit);
    }
}
