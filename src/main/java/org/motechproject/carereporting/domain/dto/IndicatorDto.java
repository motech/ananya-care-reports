package org.motechproject.carereporting.domain.dto;

import org.hibernate.validator.constraints.NotEmpty;
import org.motechproject.carereporting.domain.ReportEntity;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Set;

public class IndicatorDto implements Serializable {

    protected static final long serialVersionUID = 0L;

    private Integer id;

    @NotNull
    @Min(1)
    private Integer indicatorType;

    @NotNull
    private Set<Integer> categories;

    @NotNull
    @Min(1)
    private Integer area;

    @NotNull
    private Set<Integer> owners;

    @NotNull
    private Integer computedField;

    private Integer complexCondition;

    private Set<Integer> values;

    @NotNull
    private Set<ReportEntity> reports;

    @NotNull
    @Min(1)
    private Integer frequency;

    @NotNull
    @NotEmpty
    private String name;

    private BigDecimal trend;

    public IndicatorDto() {

    }

    public IndicatorDto(Integer indicatorType, Set<Integer> categories, Integer area, Set<Integer> owners,
                        Integer computedField, Integer complexCondition, Set<Integer> values, Set<ReportEntity> reports,
                        Integer frequency, String name, BigDecimal trend) {
        this.indicatorType = indicatorType;
        this.categories = categories;
        this.area = area;
        this.owners = owners;
        this.computedField = computedField;
        this.complexCondition = complexCondition;
        this.values = values;
        this.reports = reports;
        this.frequency = frequency;
        this.name = name;
        this.trend = trend;
    }

    public BigDecimal getTrend() {
        return trend;
    }

    public void setTrend(BigDecimal trend) {
        this.trend = trend;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIndicatorType() {
        return indicatorType;
    }

    public Set<Integer> getCategories() {
        return categories;
    }

    public Integer getArea() {
        return area;
    }

    public Set<Integer> getOwners() {
        return owners;
    }

    public Integer getComputedField() {
        return computedField;
    }

    public Integer getComplexCondition() {
        return complexCondition;
    }

    public Set<Integer> getValues() {
        return values;
    }

    public Set<ReportEntity> getReports() {
        return reports;
    }

    public Integer getFrequency() {
        return frequency;
    }

    public String getName() {
        return name;
    }
}
