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
@Table(name = "operator_type")
@AttributeOverrides({
        @AttributeOverride(name = "id", column = @Column(name = "operator_type_id"))
})
public class OperatorTypeEntity extends AbstractEntity {

    @NotNull
    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "operatorType", cascade = CascadeType.ALL)
    private Set<ComplexConditionEntity> complexConditions;

    public OperatorTypeEntity() {

    }

    public OperatorTypeEntity(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @JsonIgnore
    public Set<ComplexConditionEntity> getComplexConditions() {
        return complexConditions;
    }

    public void setComplexConditions(Set<ComplexConditionEntity> complexConditions) {
        this.complexConditions = complexConditions;
    }
}
