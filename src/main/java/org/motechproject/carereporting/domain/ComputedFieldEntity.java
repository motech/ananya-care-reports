package org.motechproject.carereporting.domain;

import org.codehaus.jackson.annotate.JsonIgnore;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Entity
@Table(name = "computed_field")
@AttributeOverrides({
        @AttributeOverride(name = "id", column = @Column(name = "computed_field_id"))
})
public class ComputedFieldEntity extends AbstractEntity {

    @NotNull
    @Column(name = "name")
    private String name;

    @NotNull
    @OneToMany(mappedBy = "computedField", cascade = CascadeType.PERSIST)
    private Set<FieldOperationEntity> fieldOperations;

    @OneToMany(mappedBy = "computedField")
    private Set<ConditionEntity> conditions;

    @OneToMany(mappedBy = "computedField")
    private Set<IndicatorEntity> indicators;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<FieldOperationEntity> getFieldOperations() {
        return fieldOperations;
    }

    public void setFieldOperations(Set<FieldOperationEntity> fieldOperations) {
        this.fieldOperations = fieldOperations;
    }

    @JsonIgnore
    public Set<ConditionEntity> getConditions() {
        return conditions;
    }

    public void setConditions(Set<ConditionEntity> conditions) {
        this.conditions = conditions;
    }

    @JsonIgnore
    public Set<IndicatorEntity> getIndicators() {
        return indicators;
    }

    public void setIndicators(Set<IndicatorEntity> indicators) {
        this.indicators = indicators;
    }
}
