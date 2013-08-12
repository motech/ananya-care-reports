package org.motechproject.carereporting.domain;

import org.codehaus.jackson.map.annotate.JsonView;
import org.motechproject.carereporting.domain.views.ComplexConditionJsonView;
import org.motechproject.carereporting.domain.views.IndicatorJsonView;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

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

    public ComplexConditionEntity() {

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

}
