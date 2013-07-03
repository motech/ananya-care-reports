package org.motechproject.carereporting.domain.forms;

import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Set;

public class ComplexConditionFormObject implements Serializable {

    protected static final long serialVersionUID = 0L;

    private Integer id;

    @NotNull
    @NotEmpty
    private String field;

    @NotNull
    private BigDecimal comparisonValue;

    @NotNull
    private Integer operatorType;

    @NotNull
    private Integer form;

    @NotNull
    private Integer comparisonSymbol;

    private Set<Integer> indicators;

    public ComplexConditionFormObject() {

    }

    public ComplexConditionFormObject(String field, BigDecimal comparisonValue,
            Integer operatorType, Integer form,
            Integer comparisonSymbol, Set<Integer> indicators) {
        this.field = field;
        this.comparisonValue = comparisonValue;
        this.operatorType = operatorType;
        this.form = form;
        this.comparisonSymbol = comparisonSymbol;
        this.indicators = indicators;
    }

    public String getField() {
        return field;
    }

    public BigDecimal getComparisonValue() {
        return comparisonValue;
    }

    public Integer getOperatorType() {
        return operatorType;
    }

    public Integer getForm() {
        return form;
    }

    public Integer getComparisonSymbol() {
        return comparisonSymbol;
    }

    public Set<Integer> getIndicators() {
        return indicators;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
