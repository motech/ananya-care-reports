package org.motechproject.carereporting.domain;

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
@Table(name = "condition")
@AttributeOverrides({
        @AttributeOverride(name = "id", column = @Column(name = "condition_id"))
})
public class ConditionEntity extends AbstractEntity {

    @NotNull
    @Column(name = "comparison_value", nullable = false)
    @JsonView({ IndicatorJsonView.IndicatorDetails.class, ComplexConditionJsonView.ComplexConditionDetails.class })
    private String comparisonValue;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "comparison_symbol_id", nullable = false)
    @JsonView({ IndicatorJsonView.IndicatorDetails.class, ComplexConditionJsonView.ComplexConditionDetails.class })
    private ComparisonSymbolEntity comparisonSymbol;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "computed_field_id")
    @JsonView({ IndicatorJsonView.IndicatorDetails.class, ComplexConditionJsonView.ComplexConditionDetails.class })
    private ComputedFieldEntity computedField;

    @ManyToOne
    @JoinColumn(name = "complex_condition_id")
    private ComplexConditionEntity complexCondition;

    public String getComparisonValue() {
        return comparisonValue;
    }

    public void setComparisonValue(String comparisonValue) {
        this.comparisonValue = comparisonValue;
    }

    public ComparisonSymbolEntity getComparisonSymbol() {
        return comparisonSymbol;
    }

    public void setComparisonSymbol(ComparisonSymbolEntity comparisonSymbol) {
        this.comparisonSymbol = comparisonSymbol;
    }

    public ComputedFieldEntity getComputedField() {
        return computedField;
    }

    public void setComputedField(ComputedFieldEntity computedField) {
        this.computedField = computedField;
    }

    public ComplexConditionEntity getComplexCondition() {
        return complexCondition;
    }

    public void setComplexCondition(ComplexConditionEntity complexCondition) {
        this.complexCondition = complexCondition;
    }
}
