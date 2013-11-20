package org.dwQueryBuilder.data;

import org.dwQueryBuilder.data.enums.OrderByType;

public class OrderBy {
    private SelectColumn selectColumn;
    private OrderByType type;

    public OrderBy() {

    }

    public OrderBy(SelectColumn selectColumn, OrderByType type) {
        this.selectColumn = selectColumn;
        this.type = type;
    }

    public SelectColumn getSelectColumn() {
        return selectColumn;
    }

    public void setSelectColumn(SelectColumn selectColumn) {
        this.selectColumn = selectColumn;
    }

    public OrderByType getType() {
        return type;
    }

    public void setType(OrderByType type) {
        this.type = type;
    }
}
