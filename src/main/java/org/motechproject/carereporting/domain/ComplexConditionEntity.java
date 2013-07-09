package org.motechproject.carereporting.domain;

import org.codehaus.jackson.annotate.JsonIgnore;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Set;

@Entity
@Table(name = "complex_condition")
@AttributeOverrides({
        @AttributeOverride(name = "id", column = @Column(name = "complex_condition_id"))
})
public class ComplexConditionEntity extends AbstractEntity {

    @NotNull
    @ManyToMany
    @JoinTable(name = "complex_condition_field", joinColumns = { @JoinColumn(name = "complex_condition_id") },
            inverseJoinColumns = { @JoinColumn(name = "field_id") })
    private Set<FieldEntity> fields;

    @NotNull
    @Column(name = "comparison_value", nullable = false)
    private BigDecimal comparisonValue;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "operator_type_id", nullable = false)
    private OperatorTypeEntity operatorType;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "form_id", nullable = false)
    private FormEntity form;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "comparison_symbol_id", nullable = false)
    private ComparisonSymbolEntity comparisonSymbol;

    @ManyToMany(mappedBy = "complexConditions")
    private Set<IndicatorEntity> indicators;

    public ComplexConditionEntity() {

    }

    public ComplexConditionEntity(Set<FieldEntity> fields, BigDecimal comparisonValue,
            OperatorTypeEntity operatorType, FormEntity form,
            ComparisonSymbolEntity comparisonSymbol, Set<IndicatorEntity> indicators) {
        this.fields = fields;
        this.comparisonValue = comparisonValue;
        this.operatorType = operatorType;
        this.form = form;
        this.comparisonSymbol = comparisonSymbol;
        this.indicators = indicators;
    }

    @JsonIgnore
    public Set<FieldEntity> getFields() {
        return fields;
    }

    public void setFields(Set<FieldEntity> fields) {
        this.fields = fields;
    }

    public BigDecimal getComparisonValue() {
        return comparisonValue;
    }

    public void setComparisonValue(BigDecimal comparisonValue) {
        this.comparisonValue = comparisonValue;
    }

    public OperatorTypeEntity getOperatorType() {
        return operatorType;
    }

    public void setOperatorType(OperatorTypeEntity operatorType) {
        this.operatorType = operatorType;
    }

    public FormEntity getForm() {
        return form;
    }

    public void setForm(FormEntity form) {
        this.form = form;
    }

    public ComparisonSymbolEntity getComparisonSymbol() {
        return comparisonSymbol;
    }

    public void setComparisonSymbol(ComparisonSymbolEntity comparisonSymbol) {
        this.comparisonSymbol = comparisonSymbol;
    }

    @JsonIgnore
    public Set<IndicatorEntity> getIndicators() {
        return indicators;
    }

    public void setIndicators(Set<IndicatorEntity> indicators) {
        this.indicators = indicators;
    }
}
