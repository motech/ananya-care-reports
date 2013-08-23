package org.dwQueryBuilder.data;

import org.dwQueryBuilder.data.enums.SelectColumnFunctionType;

public class SelectColumn {
    private String tableName;
    private String fieldName;
    private SelectColumnFunctionType function;
    private String nullValue;

    public SelectColumn() {

    }

    public SelectColumn(String tableName, String fieldName) {
        this.tableName = tableName;
        this.fieldName = fieldName;
    }

    public SelectColumn(String tableName, String fieldName, SelectColumnFunctionType function) {
        this.tableName = tableName;
        this.fieldName = fieldName;
        this.function = function;
    }

    public SelectColumn(String tableName, String fieldName, SelectColumnFunctionType function,
                        String nullValue) {
        this.tableName = tableName;
        this.fieldName = fieldName;
        this.function = function;
        this.nullValue = nullValue;
    }

    public void setFunction(SelectColumnFunctionType function) {
        this.function = function;
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

    public SelectColumnFunctionType getFunction() {
        return function;
    }

    public Boolean hasNvl() {
        return (nullValue != null);
    }

    public String getNullValue() {
        return nullValue;
    }

    public void setNullValue(String nullValue) {
        this.nullValue = nullValue;
    }
}
