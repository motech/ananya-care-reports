package org.dwQueryBuilder.builders;

import org.dwQueryBuilder.data.SelectColumn;
import org.dwQueryBuilder.data.enums.SelectColumnFunctionType;

public class SelectColumnBuilder {
    private String tableName;
    private String fieldName;
    private SelectColumnFunctionType function;

    public SelectColumnBuilder withField(String tableName, String fieldName) {
        this.tableName = tableName;
        this.fieldName = fieldName;
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

    public SelectColumn build() {
        return new SelectColumn(tableName, fieldName, function);
    }
}
