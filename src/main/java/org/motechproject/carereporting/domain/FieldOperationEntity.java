package org.motechproject.carereporting.domain;

import org.codehaus.jackson.map.annotate.JsonView;
import org.motechproject.carereporting.domain.views.ComputedFieldView;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "field_operation")
@AttributeOverrides({
        @AttributeOverride(name = "id", column = @Column(name = "field_operation_id"))
})
public class FieldOperationEntity extends AbstractEntity {

    private static final int HASH_NUMBER = 31;

    @ManyToOne
    @JoinColumn(name = "field_1_id")
    @JsonView(ComputedFieldView.class)
    private ComputedFieldEntity field1;

    @ManyToOne
    @JoinColumn(name = "field_2_id")
    @JsonView(ComputedFieldView.class)
    private ComputedFieldEntity field2;

    @ManyToOne
    @JoinColumn(name = "operator_type_id")
    @JsonView(ComputedFieldView.class)
    private OperatorTypeEntity operatorType;

    public FieldOperationEntity() {

    }

    public FieldOperationEntity(ComputedFieldEntity field1) {
        this.field1 = field1;
    }

    public ComputedFieldEntity getField1() {
        return field1;
    }

    public void setField1(ComputedFieldEntity field1) {
        this.field1 = field1;
    }

    public ComputedFieldEntity getField2() {
        return field2;
    }

    public void setField2(ComputedFieldEntity field2) {
        this.field2 = field2;
    }

    public OperatorTypeEntity getOperatorType() {
        return operatorType;
    }

    public void setOperatorType(OperatorTypeEntity operatorType) {
        this.operatorType = operatorType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        FieldOperationEntity that = (FieldOperationEntity) o;

        if (!field1.equals(that.field1)) {
            return false;
        }
        if (!field2.equals(that.field2)) {
            return false;
        }
        if (!operatorType.equals(that.operatorType)) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        if (field1 == null || field2 == null || operatorType == null) {
            return 0;
        }
        int result = field1.hashCode();
        result = HASH_NUMBER * result + field2.hashCode();
        result = HASH_NUMBER * result + operatorType.hashCode();
        return result;
    }
}
