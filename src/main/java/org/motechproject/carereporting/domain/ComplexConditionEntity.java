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
}
