package org.motechproject.carereporting.domain;

import org.codehaus.jackson.annotate.JsonIgnore;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
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
    @Column(name = "field")
    private String field;

    @NotNull
    @Column(name = "comparison_value")
    private BigDecimal comparisonValue;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "operator_type_id")
    private OperatorTypeEntity operatorType;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "form_id")
    private FormEntity form;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "comparison_symbol_id")
    private ComparisonSymbolEntity comparisonSymbol;

    @ManyToMany(mappedBy = "complexConditions")
    private Set<IndicatorEntity> indicators;

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public BigDecimal getComparisonValue() {
        return comparisonValue;
    }

    public void setComparisonValue(BigDecimal comparisonValue) {
        this.comparisonValue = comparisonValue;
    }

    @JsonIgnore
    public OperatorTypeEntity getOperatorType() {
        return operatorType;
    }

    public Integer getOperatorTypeId() {
        return operatorType.getId();
    }

    public void setOperatorType(OperatorTypeEntity operatorType) {
        this.operatorType = operatorType;
    }

    @JsonIgnore
    public FormEntity getForm() {
        return form;
    }

    public Integer getFormId() {
        return form.getId();
    }

    public void setForm(FormEntity form) {
        this.form = form;
    }

    @JsonIgnore
    public ComparisonSymbolEntity getComparisonSymbol() {
        return comparisonSymbol;
    }

    public Integer getComparisonSymbolId() {
        return comparisonSymbol.getId();
    }

    public void setComparisonSymbol(ComparisonSymbolEntity comparisonSymbol) {
        this.comparisonSymbol = comparisonSymbol;
    }

    public Set<IndicatorEntity> getIndicators() {
        return indicators;
    }

    public void setIndicators(Set<IndicatorEntity> indicators) {
        this.indicators = indicators;
    }
}
