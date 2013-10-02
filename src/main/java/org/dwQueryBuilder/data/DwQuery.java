package org.dwQueryBuilder.data;

import org.dwQueryBuilder.data.conditions.where.WhereConditionGroup;

import java.util.Set;

public class DwQuery {
    private Set<SelectColumn> selectColumns;
    private String tableName;
    private GroupBy groupBy;
    private Set<DwQueryCombination> combineWith;
    private WhereConditionGroup whereConditionGroup;
    private Integer limit;

    public DwQuery(Set<SelectColumn> selectColumns,
                   String tableName,
                   GroupBy groupBy,
                   Set<DwQueryCombination> combineWith,
                   WhereConditionGroup whereConditionGroup,
                   Integer limit) {
        this.selectColumns = selectColumns;
        this.tableName = tableName;
        this.groupBy = groupBy;
        this.combineWith = combineWith;
        this.whereConditionGroup = whereConditionGroup;
        this.limit = limit;
    }

    public Set<SelectColumn> getSelectColumns() {
        return selectColumns;
    }

    public void setSelectColumns(Set<SelectColumn> selectColumns) {
        this.selectColumns = selectColumns;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public GroupBy getGroupBy() {
        return groupBy;
    }

    public void setGroupBy(GroupBy groupBy) {
        this.groupBy = groupBy;
    }

    public Set<DwQueryCombination> getCombineWith() {
        return combineWith;
    }

    public void setCombineWith(Set<DwQueryCombination> combineWith) {
        this.combineWith = combineWith;
    }

    public WhereConditionGroup getWhereConditionGroup() {
        return whereConditionGroup;
    }

    public void setWhereConditionGroup(WhereConditionGroup whereConditionGroup) {
        this.whereConditionGroup = whereConditionGroup;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }
}
