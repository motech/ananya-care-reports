package org.motechproject.carereporting.domain;

import com.fasterxml.jackson.annotation.JsonView;
import org.motechproject.carereporting.domain.views.QueryJsonView;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "select_column")
@AttributeOverrides({
        @AttributeOverride(name = "id", column = @Column(name = "select_column_id"))
})
public class SelectColumnEntity extends AbstractEntity implements Cloneable {

    @Column(name = "function")
    @JsonView({ QueryJsonView.EditForm.class })
    private String functionName;

    @ManyToOne
    @JoinColumn(name = "computed_field_id")
    @JsonView({ QueryJsonView.EditForm.class })
    private ComputedFieldEntity computedField;

    @Column(name = "null_value")
    @JsonView({ QueryJsonView.EditForm.class })
    private String nullValue;

    public SelectColumnEntity() {
    }

    public SelectColumnEntity(SelectColumnEntity selectColumnEntity) {
        functionName = selectColumnEntity.getFunctionName();
        computedField = selectColumnEntity.getComputedField();
    }

    public String getFunctionName() {
        return functionName;
    }

    public void setFunctionName(String functionName) {
        this.functionName = functionName;
    }

    public ComputedFieldEntity getComputedField() {
        return computedField;
    }

    public void setComputedField(ComputedFieldEntity computedField) {
        this.computedField = computedField;
    }

    public String getNullValue() {
        return nullValue;
    }

    public void setNullValue(String nullValue) {
        this.nullValue = nullValue;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        SelectColumnEntity selectColumnEntity = new SelectColumnEntity();

        selectColumnEntity.setFunctionName(this.getFunctionName());
        selectColumnEntity.setComputedField(this.getComputedField());
        selectColumnEntity.setNullValue(this.getNullValue());

        return selectColumnEntity;
    }
}
