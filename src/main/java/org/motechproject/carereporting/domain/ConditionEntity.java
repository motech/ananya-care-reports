package org.motechproject.carereporting.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonView;
import org.motechproject.carereporting.domain.types.ConditionType;
import org.motechproject.carereporting.domain.views.BaseView;
import org.motechproject.carereporting.domain.views.ComplexConditionJsonView;
import org.motechproject.carereporting.domain.views.IndicatorJsonView;
import org.motechproject.carereporting.domain.views.QueryJsonView;

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
        @JsonSubTypes.Type(name = ConditionType.FIELD_COMPARISON, value = FieldComparisonConditionEntity.class),
        @JsonSubTypes.Type(name = ConditionType.VALUE_COMPARISON, value = ValueComparisonConditionEntity.class),
        @JsonSubTypes.Type(name = ConditionType.DATE_DIFF_COMPARISON, value = DateDiffComparisonConditionEntity.class),
        @JsonSubTypes.Type(name = ConditionType.DATE_RANGE_COMPARISON, value = DateRangeComparisonConditionEntity.class),
        @JsonSubTypes.Type(name = ConditionType.DATE_VALUE_COMPARISON, value = DateValueComparisonConditionEntity.class),
        @JsonSubTypes.Type(name = ConditionType.ENUM_RANGE_COMPARISON, value = EnumRangeComparisonConditionEntity.class),
        @JsonSubTypes.Type(name = ConditionType.PERIOD, value = PeriodConditionEntity.class),
        @JsonSubTypes.Type(name = ConditionType.CALCULATION_END_DATE, value = CalculationEndDateConditionEntity.class)
})
public abstract class ConditionEntity extends AbstractEntity implements Cloneable {

    @ManyToOne
    @JoinColumn(name = "field_1_id")
    @JsonView({ IndicatorJsonView.IndicatorDetails.class, ComplexConditionJsonView.ComplexConditionDetails.class,
            QueryJsonView.EditForm.class })
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

    @JsonView({ BaseView.class })
    @JsonProperty(value = "type")
    public String getType() {
        return null;
    }

    @Override
    protected abstract Object clone() throws CloneNotSupportedException;
}
