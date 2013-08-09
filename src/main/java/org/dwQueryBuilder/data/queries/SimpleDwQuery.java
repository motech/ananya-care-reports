package org.dwQueryBuilder.data.queries;

import org.dwQueryBuilder.data.DwQueryCombination;
import org.dwQueryBuilder.data.GroupBy;
import org.dwQueryBuilder.data.SelectColumn;
import org.dwQueryBuilder.data.conditions.where.WhereConditionGroup;

import java.util.Set;

public class SimpleDwQuery extends DwQuery {
    private String tableName;

    public SimpleDwQuery() {
        super();
    }

    public SimpleDwQuery(Set<SelectColumn> selectColumns, String tableName) {
        super(selectColumns);

        this.tableName = tableName;
    }

    public SimpleDwQuery(Set<SelectColumn> selectColumns, String tableName,
                         GroupBy groupedBy) {
        super(selectColumns, groupedBy);

        this.tableName = tableName;
    }

    public SimpleDwQuery(Set<SelectColumn> selectColumns, String tableName,
                         GroupBy groupedBy, DwQueryCombination combineWith) {
        super(selectColumns, groupedBy, combineWith);

        this.tableName = tableName;
    }

    public SimpleDwQuery(Set<SelectColumn> selectColumns, String tableName,
                         GroupBy groupedBy, WhereConditionGroup whereConditionGroup) {
        super(selectColumns, groupedBy, whereConditionGroup);

        this.tableName = tableName;
    }

    public SimpleDwQuery(Set<SelectColumn> selectColumns, String tableName,
                         GroupBy groupedBy, WhereConditionGroup whereConditionGroup,
                         DwQueryCombination combineWith) {
        super(selectColumns, groupedBy, whereConditionGroup, combineWith);

        this.tableName = tableName;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }
}
