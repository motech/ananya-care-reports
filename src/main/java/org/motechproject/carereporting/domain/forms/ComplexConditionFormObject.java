package org.motechproject.carereporting.domain.forms;

import org.motechproject.carereporting.domain.ConditionEntity;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Set;

public class ComplexConditionFormObject implements Serializable {

    protected static final long serialVersionUID = 0L;

    private Integer id;

    @NotNull
    private String name;

    @NotNull
    private Set<ConditionEntity> conditions;

    public ComplexConditionFormObject() {

    }

    public ComplexConditionFormObject(String name, Set<ConditionEntity> conditions) {
        this.name = name;
        this.conditions = conditions;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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
}
