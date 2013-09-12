package org.dwQueryBuilder.data;

import org.apache.commons.lang.StringUtils;
import org.dwQueryBuilder.data.enums.SelectColumnFunctionType;

public class SelectColumn {

    private ComputedColumn computedColumn;
    private SelectColumnFunctionType function;
    private String alias;
    private String nullValue;
    private boolean valueToLowerCase;

    public SelectColumn() {

    }

    public SelectColumn(String tableName, String fieldName) {
        this.computedColumn = new ComputedColumn(tableName, fieldName);
    }

    public SelectColumn(String tableName, String fieldName, String alias) {
        this.computedColumn = new ComputedColumn(tableName, fieldName);
        this.alias = alias;
    }

    public SelectColumn(String tableName, String fieldName, String alias, boolean valueToLowerCase) {
        this.computedColumn = new ComputedColumn(tableName, fieldName);
        this.alias = alias;
        this.valueToLowerCase = valueToLowerCase;
    }

    public SelectColumn(ComputedColumn computedColumn, String alias) {
        this.computedColumn = computedColumn;
        this.alias = alias;
    }

    public SelectColumn(ComputedColumn computedColumn, String alias, boolean valueToLowerCase) {
        this.computedColumn = computedColumn;
        this.alias = alias;
        this.valueToLowerCase = valueToLowerCase;
    }

    public SelectColumn(String tableName, String fieldName, String alias, SelectColumnFunctionType function) {
        this.computedColumn = new ComputedColumn(tableName, fieldName);
        this.function = function;
        this.alias = alias;
    }

    public SelectColumn(String tableName, String fieldName, SelectColumnFunctionType function,
                        String nullValue) {
        this.computedColumn = new ComputedColumn(tableName, fieldName);
        this.function = function;
        this.nullValue = nullValue;
    }

    public SelectColumn(String tableName, String fieldName, String alias, SelectColumnFunctionType function,
                        String nullValue) {
        this.computedColumn = new ComputedColumn(tableName, fieldName);
        this.function = function;
        this.nullValue = nullValue;
        this.alias = alias;
    }

    public SelectColumn(String tableName, String fieldName, String alias, SelectColumnFunctionType function,
                        String nullValue, boolean valueToLowerCase) {
        this.computedColumn = new ComputedColumn(tableName, fieldName);
        this.function = function;
        this.nullValue = nullValue;
        this.alias = alias;
        this.valueToLowerCase = valueToLowerCase;
    }

    public SelectColumn(ComputedColumn computedColumn, SelectColumnFunctionType function) {
        this.computedColumn = computedColumn;
        this.function = function;
    }

    public SelectColumn(ComputedColumn computedColumn, String alias, SelectColumnFunctionType function) {
        this.computedColumn = computedColumn;
        this.function = function;
        this.alias = alias;
    }

    public SelectColumn(ComputedColumn computedColumn, SelectColumnFunctionType function,
                        String nullValue) {
        this.computedColumn = computedColumn;
        this.function = function;
        this.nullValue = nullValue;
    }

    public SelectColumn(ComputedColumn computedColumn, String alias, SelectColumnFunctionType function,
                        String nullValue) {
        this.computedColumn = computedColumn;
        this.function = function;
        this.nullValue = nullValue;
        this.alias = alias;
    }

    public SelectColumn(ComputedColumn computedColumn, String alias, SelectColumnFunctionType function,
                        String nullValue, boolean valueToLowerCase) {
        this.computedColumn = computedColumn;
        this.function = function;
        this.nullValue = nullValue;
        this.alias = alias;
        this.valueToLowerCase = valueToLowerCase;
    }

    public ComputedColumn getComputedColumn() {
        return computedColumn;
    }

    public void setComputedColumn(ComputedColumn computedColumn) {
        this.computedColumn = computedColumn;
    }

    public boolean hasFunction() {
        return (function != null);
    }

    public SelectColumnFunctionType getFunction() {
        return function;
    }

    public void setFunction(SelectColumnFunctionType function) {
        this.function = function;
    }

    public boolean hasNullValue() {
        return (nullValue != null);
    }

    public boolean hasAlias() {
        return StringUtils.isNotBlank(alias);
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getNullValue() {
        return nullValue;
    }

    public void setNullValue(String nullValue) {
        this.nullValue = nullValue;
    }

    public boolean getValueToLowerCase() {
        return valueToLowerCase;
    }

    public void setValueToLowerCase(boolean valueToLowerCase) {
        this.valueToLowerCase = valueToLowerCase;
    }
}
