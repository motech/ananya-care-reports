package org.motechproject.carereporting.domain;

import org.motechproject.carereporting.xml.mapping.indicators.OrderByType;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "order_by")
@AttributeOverrides({
        @AttributeOverride(name = "id", column = @Column(name = "order_by_id"))
})
public class OrderByEntity extends AbstractEntity implements Cloneable {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dw_query_id", referencedColumnName = "dw_query_id")
    private DwQueryEntity dwQuery;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "select_column_id", referencedColumnName = "select_column_id", nullable = false)
    private SelectColumnEntity selectColumn;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "type", nullable = false)
    private OrderByType type;

    public OrderByEntity() {

    }

    public OrderByEntity(DwQueryEntity dwQuery,
                         SelectColumnEntity selectColumn,
                         OrderByType type) {
        this.dwQuery = dwQuery;
        this.selectColumn = selectColumn;
        this.type = type;
    }

    public OrderByEntity(OrderByEntity orderByEntity) {
        this.dwQuery = orderByEntity.getDwQuery();
        this.selectColumn = new SelectColumnEntity(orderByEntity.getSelectColumn());
        this.type = orderByEntity.getType();
    }

    public DwQueryEntity getDwQuery() {
        return dwQuery;
    }

    public void setDwQuery(DwQueryEntity dwQuery) {
        this.dwQuery = dwQuery;
    }

    public SelectColumnEntity getSelectColumn() {
        return selectColumn;
    }

    public void setSelectColumn(SelectColumnEntity selectColumn) {
        this.selectColumn = selectColumn;
    }

    public OrderByType getType() {
        return type;
    }

    public void setType(OrderByType type) {
        this.type = type;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        OrderByEntity orderByEntity = new OrderByEntity();
        orderByEntity.setDwQuery(this.getDwQuery());
        orderByEntity.setSelectColumn((SelectColumnEntity) this.getSelectColumn().clone());
        orderByEntity.setType(this.getType());

        return orderByEntity;
    }
}
