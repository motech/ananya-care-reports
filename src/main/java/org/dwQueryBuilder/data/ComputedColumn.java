package org.dwQueryBuilder.data;

import java.util.LinkedHashSet;
import java.util.Set;

public class ComputedColumn {

    private String tableName;
    private String fieldName;
    private Set<ComputedColumnOperation> operations;

    public ComputedColumn() {
        this.operations = new LinkedHashSet<>();
    }

    public ComputedColumn(String tableName, String fieldName) {
        this.tableName = tableName;
        this.fieldName = fieldName;
        this.operations = new LinkedHashSet<>();
    }

    public ComputedColumn(String tableName, String fieldName,
                          Set<ComputedColumnOperation> operations) {
        this.tableName = tableName;
        this.fieldName = fieldName;
        this.operations = operations;
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

    public Boolean hasOperations() {
        return (operations != null && operations.size() > 0);
    }

    public Set<ComputedColumnOperation> getOperations() {
        return operations;
    }

    public void setOperations(Set<ComputedColumnOperation> operations) {
        this.operations = operations;
    }
}
