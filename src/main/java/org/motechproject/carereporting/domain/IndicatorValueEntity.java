package org.motechproject.carereporting.domain;

import org.codehaus.jackson.annotate.JsonIgnore;

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
@Table(name = "indicator_value")
@AttributeOverrides({
        @AttributeOverride(name = "id", column = @Column(name = "indicator_value_id"))
})
public class IndicatorValueEntity extends AbstractEntity {

    @NotNull
    @ManyToOne
    @JoinColumn(name = "indicator_id")
    private IndicatorEntity indicator;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "area_id")
    private AreaEntity area;

    @Column(name = "nominator")
    private BigDecimal nominator;

    @Column(name = "denominator")
    private BigDecimal denominator;

    @Column(name = "value")
    private BigDecimal value;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "frequency_id")
    private FrequencyEntity frequency;

    public IndicatorValueEntity() {

    }

    public IndicatorValueEntity(IndicatorEntity indicator, AreaEntity area, BigDecimal value) {
        this.area = area;
        this.indicator = indicator;
        this.value = value;
    }

    public BigDecimal getNominator() {
        return nominator;
    }

    public void setNominator(BigDecimal nominator) {
        this.nominator = nominator;
    }

    public BigDecimal getDenominator() {
        return denominator;
    }

    public void setDenominator(BigDecimal denominator) {
        this.denominator = denominator;
    }

    public FrequencyEntity getFrequency() {
        return frequency;
    }

    public void setFrequency(FrequencyEntity frequency) {
        this.frequency = frequency;
    }

    @JsonIgnore
    public IndicatorEntity getIndicator() {
        return indicator;
    }

    public Integer getIndicatorId() {
        return indicator.getId();
    }

    public void setIndicator(IndicatorEntity indicator) {
        this.indicator = indicator;
    }

    @JsonIgnore
    public AreaEntity getArea() {
        return area;
    }

    public Integer getAreaId() {
        return area.getId();
    }

    public void setArea(AreaEntity area) {
        this.area = area;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }
}
