package org.dwQueryBuilder.data;

import org.dwQueryBuilder.data.enums.OperatorType;

public class ComputedColumnOperation {

    private OperatorType operatorType;
    private ComputedColumn computedColumn;

    public ComputedColumnOperation() {

    }

    public ComputedColumnOperation(OperatorType operatorType, ComputedColumn computedColumn) {
        this.operatorType = operatorType;
        this.computedColumn = computedColumn;
    }

    public OperatorType getOperatorType() {
        return operatorType;
    }

    public void setOperatorType(OperatorType operatorType) {
        this.operatorType = operatorType;
    }

    public ComputedColumn getComputedColumn() {
        return computedColumn;
    }

    public void setComputedColumn(ComputedColumn computedColumn) {
        this.computedColumn = computedColumn;
    }
}
