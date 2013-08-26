package org.motechproject.carereporting.domain;

import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.annotation.JsonView;
import org.motechproject.carereporting.domain.views.ComplexConditionJsonView;
import org.motechproject.carereporting.domain.views.IndicatorJsonView;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "value_comparison")
@AttributeOverrides({
        @AttributeOverride(name = "id", column = @Column(name = "value_comparison_id"))
})
@JsonTypeName(value = "value")
public class ValueComparisonConditionEntity extends ConditionEntity {

    @NotNull
    @Column(name = "value")
    @JsonView({ IndicatorJsonView.IndicatorDetails.class, ComplexConditionJsonView.ComplexConditionDetails.class })
    private String value;

    public ValueComparisonConditionEntity() {
        super();
    }

    public ValueComparisonConditionEntity(ValueComparisonConditionEntity conditionEntity) {
        super(conditionEntity);
        value = conditionEntity.getValue();
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
