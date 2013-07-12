package org.motechproject.carereporting.domain;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.map.annotate.JsonView;
import org.hibernate.validator.constraints.NotEmpty;
import org.motechproject.carereporting.domain.views.IndicatorJsonView;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Entity
@Table(name = "indicator")
@AttributeOverrides({
        @AttributeOverride(name = "id", column = @Column(name = "indicator_id"))
})
public class IndicatorEntity extends AbstractEntity {

    private static final String INDICATOR_ID_COLUMN_NAME = "indicator_id";

    @NotNull
    @ManyToOne
    @JoinColumn(name = "type_id", referencedColumnName = "indicator_type_id", nullable = false)
    private IndicatorTypeEntity indicatorType;

    @NotNull
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "indicator_indicator_category", joinColumns = { @JoinColumn(name = INDICATOR_ID_COLUMN_NAME) },
            inverseJoinColumns = { @JoinColumn(name = "indicator_category_id") })
    private Set<IndicatorCategoryEntity> categories;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "area_id", referencedColumnName = "area_id", nullable = false)
    private AreaEntity area;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "computed_field_id")
    private ComputedFieldEntity computedField;

    @ManyToOne
    @JoinColumn(name = "complex_condition_id")
    private ComplexConditionEntity complexCondition;

    @NotNull
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "indicator_user", joinColumns = { @JoinColumn(name = INDICATOR_ID_COLUMN_NAME) },
            inverseJoinColumns = { @JoinColumn(name = "user_id") })
    private Set<UserEntity> owners;

    @OneToMany(mappedBy = "indicator", cascade = CascadeType.ALL)
    private Set<IndicatorValueEntity> values;

    @NotNull
    @Column(name = "frequency", nullable = false)
    private Integer frequency;

    @NotNull
    @NotEmpty
    @Column(name = "name", unique = true, nullable = false)
    @JsonView({ IndicatorJsonView.ListIndicatorNames.class })
    private String name;

    public IndicatorEntity() {

    }

    public IndicatorEntity(Integer id) {
        this.id = id;
    }

    public IndicatorEntity(IndicatorTypeEntity indicatorType, Set<IndicatorCategoryEntity> categories,
                           AreaEntity area, Set<UserEntity> owners, ComputedFieldEntity computedField,
                           ComplexConditionEntity complexCondition, Set<IndicatorValueEntity> values,
                           Integer frequency, String name) {
        this.indicatorType = indicatorType;
        this.categories = categories;
        this.area = area;
        this.owners = owners;
        this.computedField = computedField;
        this.complexCondition = complexCondition;
        this.values = values;
        this.frequency = frequency;
        this.name = name;
    }

    public IndicatorTypeEntity getIndicatorType() {
        return indicatorType;
    }

    public void setIndicatorType(IndicatorTypeEntity indicatorType) {
        this.indicatorType = indicatorType;
    }

    public Set<IndicatorCategoryEntity> getCategories() {
        return categories;
    }

    public void setCategories(Set<IndicatorCategoryEntity> categories) {
        this.categories = categories;
    }

    public AreaEntity getArea() {
        return area;
    }

    public void setArea(AreaEntity area) {
        this.area = area;
    }

    public ComputedFieldEntity getComputedField() {
        return computedField;
    }

    public void setComputedField(ComputedFieldEntity computedField) {
        this.computedField = computedField;
    }

    @JsonIgnore
    public ComplexConditionEntity getComplexCondition() {
        return complexCondition;
    }

    public void setComplexCondition(ComplexConditionEntity complexCondition) {
        this.complexCondition = complexCondition;
    }

    @JsonIgnore
    public Set<UserEntity> getOwners() {
        return owners;
    }

    public void setOwners(Set<UserEntity> owners) {
        this.owners = owners;
    }

    @JsonIgnore
    public Set<IndicatorValueEntity> getValues() {
        return values;
    }

    public void setValues(Set<IndicatorValueEntity> values) {
        this.values = values;
    }

    public Integer getFrequency() {
        return frequency;
    }

    public void setFrequency(Integer frequency) {
        this.frequency = frequency;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
