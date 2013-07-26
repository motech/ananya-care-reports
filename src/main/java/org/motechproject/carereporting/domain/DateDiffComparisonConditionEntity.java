package org.motechproject.carereporting.domain;

import org.codehaus.jackson.annotate.JsonTypeName;
import org.codehaus.jackson.map.annotate.JsonView;
import org.motechproject.carereporting.domain.views.ComplexConditionJsonView;
import org.motechproject.carereporting.domain.views.IndicatorJsonView;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "date_diff_comparison")
@AttributeOverrides({
        @AttributeOverride(name = "id", column = @Column(name = "date_diff_comparison_id"))
})
@JsonTypeName(value = "date")
public class DateDiffComparisonConditionEntity extends ConditionEntity {

    @NotNull
    @ManyToOne
    @JoinColumn(name = "field_2_id")
    @JsonView({ IndicatorJsonView.IndicatorDetails.class, ComplexConditionJsonView.ComplexConditionDetails.class })
    private ComputedFieldEntity field2;

    @NotNull
    @Column(name = "value")
    @JsonView({ IndicatorJsonView.IndicatorDetails.class, ComplexConditionJsonView.ComplexConditionDetails.class })
    private Integer value;

    public DateDiffComparisonConditionEntity() {
        super();
    }

    public ComputedFieldEntity getField2() {
        return field2;
    }

    public void setField2(ComputedFieldEntity field2) {
        this.field2 = field2;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }
}
