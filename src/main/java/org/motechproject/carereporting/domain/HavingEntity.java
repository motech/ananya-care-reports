package org.motechproject.carereporting.domain;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "having")
@AttributeOverrides({
        @AttributeOverride(name = "id", column = @Column(name = "having_id"))
})
public class HavingEntity extends AbstractEntity {

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "select_column_id", referencedColumnName = "select_column_id")
    private SelectColumnEntity selectColumnEntity;

    @Column(name = "operator")
    private String operator;

    @Column(name = "value")
    private String value;

    public SelectColumnEntity getSelectColumnEntity() {
        return selectColumnEntity;
    }

    public void setSelectColumnEntity(SelectColumnEntity selectColumnEntity) {
        this.selectColumnEntity = selectColumnEntity;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
