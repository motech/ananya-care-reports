package org.dwQueryBuilder.builders;

import org.dwQueryBuilder.builders.steps.ComputedColumnBuilderOperationStep;
import org.dwQueryBuilder.data.ComputedColumn;
import org.dwQueryBuilder.data.ComputedColumnOperation;
import org.dwQueryBuilder.data.enums.OperatorType;

public class ComputedColumnOperationBuilder {

    private OperatorType operatorType;
    private ComputedColumn computedColumn;

    public ComputedColumnOperationBuilder withComputedColumn(String tableName,
                                                             String fieldName) {
        this.computedColumn = new ComputedColumn(tableName, fieldName);
        return this;
    }

    public ComputedColumnOperationBuilder withComputedColumn(OperatorType operatorType,
                                                             String tableName,
                                                             String fieldName) {
        this.operatorType = operatorType;
        this.computedColumn = new ComputedColumn(tableName, fieldName);
        return this;
    }

    public ComputedColumnOperationBuilder withComputedColumn(OperatorType operatorType,
                                                             ComputedColumn computedColumn) {
        this.operatorType = operatorType;
        this.computedColumn = computedColumn;
        return this;
    }

    public ComputedColumnOperationBuilder withComputedColumn(OperatorType operatorType,
                                                             ComputedColumnBuilderOperationStep computedColumnBuilder) {
        this.operatorType = operatorType;
        this.computedColumn = computedColumnBuilder.build();
        return this;
    }

    public ComputedColumnOperation build() {
        return new ComputedColumnOperation(operatorType, computedColumn);
    }
}
