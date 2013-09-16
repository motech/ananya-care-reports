package org.motechproject.carereporting.domain.dto;

import org.hibernate.validator.constraints.NotEmpty;
import org.motechproject.carereporting.domain.ReportEntity;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Set;

public class IndicatorDto implements Serializable {

    protected static final long serialVersionUID = 0L;

    @NotEmpty
    private String name;

    @NotNull
    private Set<Integer> classifications;

    @NotNull
    private Integer level;

    @NotNull
    private Set<Integer> owners;

    @NotNull
    private Integer frequency;

    @NotNull
    private Set<ReportEntity> reports;

    private BigDecimal trend;

    @NotNull
    private Integer numerator;

    private Integer denominator;

    @NotNull
    private Boolean additive;

    @NotNull
    private Boolean categorized;

    public IndicatorDto() {

    }

    public IndicatorDto(String name, Set<Integer> classifications, Integer level, Set<Integer> owners, Integer frequency,
                        Set<ReportEntity> reports, BigDecimal trend, Integer numerator, Integer denominator,
                        Boolean additive, Boolean categorized) {
        this.name = name;
        this.classifications = classifications;
        this.level = level;
        this.owners = owners;
        this.frequency = frequency;
        this.reports = reports;
        this.trend = trend;
        this.numerator = numerator;
        this.denominator = denominator;
        this.additive = additive;
        this.categorized = categorized;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Integer> getClassifications() {
        return classifications;
    }

    public void setClassifications(Set<Integer> classifications) {
        this.classifications = classifications;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Set<Integer> getOwners() {
        return owners;
    }

    public void setOwners(Set<Integer> owners) {
        this.owners = owners;
    }

    public Integer getFrequency() {
        return frequency;
    }

    public void setFrequency(Integer frequency) {
        this.frequency = frequency;
    }

    public Set<ReportEntity> getReports() {
        return reports;
    }

    public void setReports(Set<ReportEntity> reports) {
        this.reports = reports;
    }

    public BigDecimal getTrend() {
        return trend;
    }

    public void setTrend(BigDecimal trend) {
        this.trend = trend;
    }

    public Integer getNumerator() {
        return numerator;
    }

    public void setNumerator(Integer numerator) {
        this.numerator = numerator;
    }

    public Integer getDenominator() {
        return denominator;
    }

    public void setDenominator(Integer denominator) {
        this.denominator = denominator;
    }

    public Boolean isAdditive() {
        return additive;
    }

    public void setAdditive(Boolean additive) {
        this.additive = additive;
    }

    public Boolean isCategorized() {
        return categorized;
    }

    public void setCategorized(Boolean categorized) {
        this.categorized = categorized;
    }
}
