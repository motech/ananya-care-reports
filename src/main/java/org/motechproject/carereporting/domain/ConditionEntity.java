package org.motechproject.carereporting.domain;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonView;
import org.motechproject.carereporting.domain.views.ComplexConditionJsonView;
import org.motechproject.carereporting.domain.views.IndicatorJsonView;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "condition")
@AttributeOverrides({
        @AttributeOverride(name = "id", column = @Column(name = "condition_id"))
})
@Inheritance(strategy = InheritanceType.JOINED)
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(name = "field", value = FieldComparisonConditionEntity.class),
        @JsonSubTypes.Type(name = "value", value = ValueComparisonConditionEntity.class),
        @JsonSubTypes.Type(name = "dateDiff", value = DateDiffComparisonConditionEntity.class),
        @JsonSubTypes.Type(name = "dateRange", value = DateRangeComparisonConditionEntity.class),
        @JsonSubTypes.Type(name = "dateValue", value = DateValueComparisonConditionEntity.class),
        @JsonSubTypes.Type(name = "enumRange", value = EnumRangeComparisonConditionEntity.class)
})
public abstract class ConditionEntity extends AbstractEntity {

    @ManyToOne
    @JoinColumn(name = "field_1_id")
    @JsonView({ IndicatorJsonView.IndicatorDetails.class, ComplexConditionJsonView.ComplexConditionDetails.class })
    private ComputedFieldEntity field1;

    protected ConditionEntity() {
    }

    public ConditionEntity(ConditionEntity conditionEntity) {
        field1 = conditionEntity.getField1();
    }

    public ComputedFieldEntity getField1() {
        return field1;
    }

    public void setField1(ComputedFieldEntity field1) {
        this.field1 = field1;
    }

}
