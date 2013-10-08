package org.motechproject.carereporting.domain;

import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.annotation.JsonView;
import org.motechproject.carereporting.domain.types.ConditionType;
import org.motechproject.carereporting.domain.views.ComplexConditionJsonView;
import org.motechproject.carereporting.domain.views.IndicatorJsonView;
import org.motechproject.carereporting.domain.views.QueryJsonView;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "enum_range_comparison")
@AttributeOverrides({
        @AttributeOverride(name = "id", column = @Column(name = "enum_range_comparison_id"))
})
@JsonTypeName(value = "enumRange")
public class EnumRangeComparisonConditionEntity extends ConditionEntity {

    @NotNull
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name = "enum_range_enum_range_value",
            joinColumns = @JoinColumn(name = "enum_range_id"),
            inverseJoinColumns = @JoinColumn(name = "enum_range_value_id")
    )
    @JsonView({ IndicatorJsonView.IndicatorDetails.class, ComplexConditionJsonView.ComplexConditionDetails.class,
        QueryJsonView.EditForm.class })
    private Set<EnumRangeComparisonConditionValueEntity> values;

    public EnumRangeComparisonConditionEntity() {
        super();
    }

    @Override
    public String getType() {
        return ConditionType.EnumRangeComparison.getValue();
    }

    public EnumRangeComparisonConditionEntity(EnumRangeComparisonConditionEntity conditionEntity) {
        super(conditionEntity);

        this.values = conditionEntity.getValues();
    }

    public Set<EnumRangeComparisonConditionValueEntity> getValues() {
        return values;
    }

    public void setValues(Set<EnumRangeComparisonConditionValueEntity> values) {
        this.values = values;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        EnumRangeComparisonConditionEntity enumRangeComparisonConditionEntity = new EnumRangeComparisonConditionEntity();

        enumRangeComparisonConditionEntity.setField1(this.getField1());
        enumRangeComparisonConditionEntity.setValues(new LinkedHashSet<EnumRangeComparisonConditionValueEntity>());
        for (EnumRangeComparisonConditionValueEntity value : enumRangeComparisonConditionEntity.getValues()) {
            enumRangeComparisonConditionEntity.getValues().add((EnumRangeComparisonConditionValueEntity) value.clone());
        }

        return enumRangeComparisonConditionEntity;
    }
}
