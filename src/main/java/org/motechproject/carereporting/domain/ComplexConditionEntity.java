package org.motechproject.carereporting.domain;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.map.annotate.JsonView;
import org.motechproject.carereporting.domain.views.ComplexConditionJsonView;
import org.motechproject.carereporting.domain.views.IndicatorJsonView;

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
@Table(name = "complex_condition")
@AttributeOverrides({
        @AttributeOverride(name = "id", column = @Column(name = "complex_condition_id"))
})
public class ComplexConditionEntity extends AbstractEntity {

    @NotNull
    @Column (name = "name")
    @JsonView({ IndicatorJsonView.IndicatorDetails.class, ComplexConditionJsonView.ListComplexConditions.class,
        ComplexConditionJsonView.ComplexConditionDetails.class })
    private String name;

    @NotNull
    @OneToMany(mappedBy = "complexCondition", cascade = CascadeType.ALL)
    @JsonView({ IndicatorJsonView.IndicatorDetails.class, ComplexConditionJsonView.ComplexConditionDetails.class })
    private Set<ConditionEntity> conditions;

    @OneToMany(mappedBy = "complexCondition")
    private Set<IndicatorEntity> indicators;

    public ComplexConditionEntity() {

    }

    public ComplexConditionEntity(String name, Set<ConditionEntity> conditions) {
        this.name = name;
        this.conditions = conditions;

        for (ConditionEntity conditionEntity : conditions) {
            if (conditionEntity.getComplexCondition() == null) {
                conditionEntity.setComplexCondition(this);
            }
        }
    }

    public ComplexConditionEntity(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

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
