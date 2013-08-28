package org.dwQueryBuilder.builders.steps;

import org.dwQueryBuilder.data.ComputedColumn;
import org.dwQueryBuilder.data.ComputedColumnOperation;
import org.dwQueryBuilder.data.enums.OperatorType;

import java.util.LinkedHashSet;
import java.util.Set;

public class ComputedColumnBuilderOperationStep {

    private String fieldName;
    private String tableName;
    private Set<ComputedColumnOperation> operations;

    private ComputedColumnBuilderOperationStep() {
        this.operations = new LinkedHashSet<>();
    }

    public ComputedColumnBuilderOperationStep(String tableName, String fieldName,
                                              Set<ComputedColumnOperation> operations) {
        this.tableName = tableName;
        this.fieldName = fieldName;
        this.operations = operations;
    }

    public ComputedColumnBuilderOperationStep withComputedColumn(OperatorType operatorType,
                                                                String tableName,
                                                                String fieldName) {
        operations.add(new ComputedColumnOperation(operatorType, new ComputedColumn(tableName, fieldName)));
        return this;
    }

    public ComputedColumnBuilderOperationStep withComputedColumn(OperatorType operatorType,
                                                                 ComputedColumn computedColumn) {
        operations.add(new ComputedColumnOperation(operatorType, computedColumn));
        return this;
    }

    public ComputedColumnBuilderOperationStep withComputedColumn(OperatorType operatorType,
                                                                 ComputedColumnBuilderOperationStep computedColumnBuilder) {

        operations.add(new ComputedColumnOperation(operatorType, computedColumnBuilder.build()));
        return this;
    }

    public ComputedColumn build() {
        return new ComputedColumn(tableName, fieldName, operations);
    }

}
