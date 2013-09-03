package org.dwQueryBuilder.data;

import org.dwQueryBuilder.data.DwQueryCombination;
import org.dwQueryBuilder.data.GroupBy;
import org.dwQueryBuilder.data.SelectColumn;
import org.dwQueryBuilder.data.conditions.where.WhereConditionGroup;

import java.util.LinkedHashSet;
import java.util.Set;

public class DwQuery {
    private Set<SelectColumn> selectColumns;
    private String tableName;
    private GroupBy groupBy;
    private Set<DwQueryCombination> combineWith;
    private WhereConditionGroup whereConditionGroup;

    public DwQuery() {
        this.combineWith = new LinkedHashSet<>();
    }

    public DwQuery(Set<SelectColumn> selectColumns,
                   String tableName) {
        this.selectColumns = selectColumns;
        this.tableName = tableName;
        this.combineWith = new LinkedHashSet<>();
    }

    public DwQuery(Set<SelectColumn> selectColumns,
                   String tableName,
                   GroupBy groupBy) {
        this.selectColumns = selectColumns;
        this.tableName = tableName;
        this.groupBy = groupBy;
        this.combineWith = new LinkedHashSet<>();
    }

    public DwQuery(Set<SelectColumn> selectColumns,
                   String tableName,
                   Set<DwQueryCombination> combineWith) {
        this.selectColumns = selectColumns;
        this.tableName = tableName;
        this.combineWith = combineWith;
    }

    public DwQuery(Set<SelectColumn> selectColumns,
                   String tableName,
                   GroupBy groupBy,
                   WhereConditionGroup whereConditionGroup) {
        this.selectColumns = selectColumns;
        this.tableName = tableName;
        this.groupBy = groupBy;
        this.combineWith = new LinkedHashSet<>();
        this.whereConditionGroup = whereConditionGroup;
    }

    public DwQuery(Set<SelectColumn> selectColumns,
                   String tableName,
                   GroupBy groupBy,
                   Set<DwQueryCombination> combineWith) {
        this.selectColumns = selectColumns;
        this.tableName = tableName;
        this.groupBy = groupBy;
        this.combineWith = combineWith;
    }

    public DwQuery(Set<SelectColumn> selectColumns,
                   String tableName,
                   GroupBy groupBy,
                   Set<DwQueryCombination> combineWith,
                   WhereConditionGroup whereConditionGroup) {
        this.selectColumns = selectColumns;
        this.tableName = tableName;
        this.groupBy = groupBy;
        this.combineWith = combineWith;
        this.whereConditionGroup = whereConditionGroup;
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
}
