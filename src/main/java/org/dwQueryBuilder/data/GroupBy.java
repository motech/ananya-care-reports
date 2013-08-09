package org.dwQueryBuilder.data;

import org.dwQueryBuilder.data.conditions.HavingCondition;

public class GroupBy {
    private String tableName;
    private String fieldName;
    private HavingCondition having;

    public GroupBy() {

    }

    public GroupBy(String tableName, String fieldName) {
        this.tableName = tableName;
        this.fieldName = fieldName;
    }

    public GroupBy(String tableName, String fieldName, HavingCondition having) {
        this.tableName = tableName;
        this.fieldName = fieldName;
        this.having = having;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public HavingCondition getHaving() {
        return having;
    }

    public void setHaving(HavingCondition having) {
        this.having = having;
    }
}
