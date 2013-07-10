package org.motechproject.carereporting.domain;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Entity
@Table(name = "condition")
@AttributeOverrides({
        @AttributeOverride(name = "id", column = @Column(name = "condition_id"))
})
public class ConditionEntity extends AbstractEntity {

    @NotNull
    @Column(name = "comparison_value", nullable = false)
    private BigDecimal comparisonValue;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "comparison_symbol_id", nullable = false)
    private ComparisonSymbolEntity comparisonSymbol;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "computed_field_id")
    private ComputedFieldEntity computedField;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "complex_condition_id")
    private ComplexConditionEntity complexCondition;

    public BigDecimal getComparisonValue() {
        return comparisonValue;
    }

    public void setComparisonValue(BigDecimal comparisonValue) {
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
