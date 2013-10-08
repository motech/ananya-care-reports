package org.motechproject.carereporting.domain;

import com.fasterxml.jackson.annotation.JsonView;
import org.motechproject.carereporting.domain.views.QueryJsonView;

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
public class HavingEntity extends AbstractEntity implements Cloneable {

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "select_column_id", referencedColumnName = "select_column_id")
    @JsonView({ QueryJsonView.EditForm.class })
    private SelectColumnEntity selectColumnEntity;

    @Column(name = "operator")
    @JsonView({ QueryJsonView.EditForm.class })
    private String operator;

    @Column(name = "value")
    @JsonView({ QueryJsonView.EditForm.class })
    private String value;

    public HavingEntity() {
    }

    public HavingEntity(HavingEntity having) {
        selectColumnEntity = new SelectColumnEntity(having.getSelectColumnEntity());
        operator = having.getOperator();
        value = having.getValue();
    }

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

    @Override
    protected Object clone() throws CloneNotSupportedException {
        HavingEntity havingEntity = new HavingEntity();

        havingEntity.setSelectColumnEntity((SelectColumnEntity) this.getSelectColumnEntity().clone());
        havingEntity.setOperator(this.getOperator());
        havingEntity.setValue(this.getValue());

        return havingEntity;
    }
}
