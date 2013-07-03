package org.motechproject.carereporting.domain.forms;

import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

public class IndicatorFormObject implements Serializable {

    protected static final long serialVersionUID = 0L;

    private Integer id;

    @NotNull
    @Min(1)
    private Integer indicatorType;

    @NotNull
    private List<Integer> categories;

    @NotNull
    @Min(1)
    private Integer level;

    @NotNull
    private List<Integer> owners;

    private List<Integer> complexConditions;

    private List<Integer> values;

    @NotNull
    @Min(1)
    private Integer frequency;

    @NotNull
    @NotEmpty
    private String name;

    public IndicatorFormObject() {

    }

    public IndicatorFormObject(final Integer indicatorType, final List<Integer> categories,
            final Integer level, final List<Integer> owners, final List<Integer> complexConditions,
            final List<Integer> values, final Integer frequency,
            final String name) {
        this.indicatorType = indicatorType;
        this.categories = categories;
        this.level = level;
        this.owners = owners;
        this.complexConditions = complexConditions;
        this.values = values;
        this.frequency = frequency;
        this.name = name;
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

    public List<Integer> getCategories() {
        return categories;
    }

    public Integer getLevel() {
        return level;
    }

    public List<Integer> getOwners() {
        return owners;
    }

    public List<Integer> getComplexConditions() {
        return complexConditions;
    }

    public List<Integer> getValues() {
        return values;
    }

    public Integer getFrequency() {
        return frequency;
    }

    public String getName() {
        return name;
    }
}
