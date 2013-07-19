package org.motechproject.carereporting.domain;

import org.codehaus.jackson.annotate.JsonIgnore;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "field_operation")
@AttributeOverrides({
        @AttributeOverride(name = "id", column = @Column(name = "field_operation_id"))
})
public class FieldOperationEntity extends AbstractEntity {

    @NotNull
    @ManyToOne
    @JoinColumn(name = "field_1_id")
    private FieldEntity field1;

    @ManyToOne
    @JoinColumn(name = "field_2_id")
    private FieldEntity field2;

    @ManyToOne
    @JoinColumn(name = "computed_field_id")
    private ComputedFieldEntity computedField;

    @ManyToOne
    @JoinColumn(name = "operator_type_id")
    private OperatorTypeEntity operatorType;

    public FieldOperationEntity() {

    }

    public FieldOperationEntity(FieldEntity field1) {
        this.field1 = field1;
    }

    public FieldEntity getField1() {
        return field1;
    }

    public void setField1(FieldEntity field1) {
        this.field1 = field1;
    }

    public FieldEntity getField2() {
        return field2;
    }

    public void setField2(FieldEntity field2) {
        this.field2 = field2;
    }

    @JsonIgnore
    public ComputedFieldEntity getComputedField() {
        return computedField;
    }

    public void setComputedField(ComputedFieldEntity computedField) {
        this.computedField = computedField;
    }

    public OperatorTypeEntity getOperatorType() {
        return operatorType;
    }

    public void setOperatorType(OperatorTypeEntity operatorType) {
        this.operatorType = operatorType;
    }
}
