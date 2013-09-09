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
    private Set<Integer> categories;

    @NotNull
    private Integer level;

    @NotNull
    private Set<Integer> owners;

    @NotNull
    private Integer frequency;

    @NotNull
    private Set<ReportEntity> reports;

    private BigDecimal trend;

    public IndicatorDto() {

    }

    public IndicatorDto(String name, Set<Integer> categories, Integer level, Set<Integer> owners, Integer frequency, Set<ReportEntity> reports, BigDecimal trend) {
        this.name = name;
        this.categories = categories;
        this.level = level;
        this.owners = owners;
        this.frequency = frequency;
        this.reports = reports;
        this.trend = trend;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Integer> getCategories() {
        return categories;
    }

    public void setCategories(Set<Integer> categories) {
        this.categories = categories;
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

}
