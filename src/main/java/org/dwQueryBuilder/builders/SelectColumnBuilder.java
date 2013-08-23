package org.dwQueryBuilder.builders;

import org.dwQueryBuilder.data.SelectColumn;
import org.dwQueryBuilder.data.enums.SelectColumnFunctionType;

public class SelectColumnBuilder {
    private String tableName;
    private String fieldName;
    private SelectColumnFunctionType function;
    private String nullValue;

    public SelectColumnBuilder withField(String tableName, String fieldName) {
        this.tableName = tableName;
        this.fieldName = fieldName;
        return this;
    }

    public SelectColumnBuilder withField(String tableName, String fieldName, String nullValue) {
        this.tableName = tableName;
        this.fieldName = fieldName;
        this.nullValue = nullValue;
        return this;
    }

    public SelectColumnBuilder withFunction(SelectColumnFunctionType function) {
        this.function = function;
        return this;
    }

    public SelectColumnBuilder withFieldAndFunction(String tableName, String fieldName,
                                                    SelectColumnFunctionType function) {
        this.tableName = tableName;
        this.fieldName = fieldName;
        this.function = function;
        return this;
    }

    public SelectColumnBuilder withFieldAndFunction(String tableName, String fieldName,
                                                    SelectColumnFunctionType function,
                                                    String nullValue) {
        this.tableName = tableName;
        this.fieldName = fieldName;
        this.function = function;
        this.nullValue = nullValue;
        return this;
    }

    public SelectColumnBuilder withNullValue(String nullValue) {
        this.nullValue = nullValue;
        return this;
    }

    public SelectColumn build() {
        return new SelectColumn(tableName, fieldName, function, nullValue);
    }
}
