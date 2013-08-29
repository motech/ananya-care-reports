package org.motechproject.carereporting.domain.dto;

import com.fasterxml.jackson.annotation.JsonView;
import org.motechproject.carereporting.domain.AreaEntity;
import org.motechproject.carereporting.domain.ComplexConditionEntity;
import org.motechproject.carereporting.domain.FormEntity;
import org.motechproject.carereporting.domain.IndicatorCategoryEntity;
import org.motechproject.carereporting.domain.RoleEntity;
import org.motechproject.carereporting.domain.views.IndicatorJsonView;

import java.util.Set;

public class IndicatorCreationFormDto {

    @JsonView({ IndicatorJsonView.CreationForm.class })
    private Set<IndicatorCategoryEntity> categories;

    @JsonView({ IndicatorJsonView.CreationForm.class })
    private Set<RoleEntity> roles;

    @JsonView({ IndicatorJsonView.CreationForm.class })
    private Set<AreaEntity> areas;

    @JsonView({ IndicatorJsonView.CreationForm.class })
    private Set<FormEntity> forms;

    @JsonView({ IndicatorJsonView.CreationForm.class })
    private Set<ComplexConditionEntity> complexConditions;

    public IndicatorCreationFormDto() {

    }

    public IndicatorCreationFormDto(Set<IndicatorCategoryEntity> categories,
                                    Set<RoleEntity> roles,
                                    Set<AreaEntity> areas,
                                    Set<FormEntity> forms,
                                    Set<ComplexConditionEntity> complexConditions) {
        this.categories = categories;
        this.roles = roles;
        this.areas = areas;
        this.forms = forms;
        this.complexConditions = complexConditions;
    }

    public Set<IndicatorCategoryEntity> getCategories() {
        return categories;
    }

    public void setCategories(Set<IndicatorCategoryEntity> categories) {
        this.categories = categories;
    }

    public Set<RoleEntity> getRoles() {
        return roles;
    }

    public void setRoles(Set<RoleEntity> roles) {
        this.roles = roles;
    }

    public Set<AreaEntity> getAreas() {
        return areas;
    }

    public void setAreas(Set<AreaEntity> areas) {
        this.areas = areas;
    }

    public Set<FormEntity> getForms() {
        return forms;
    }

    public void setForms(Set<FormEntity> forms) {
        this.forms = forms;
    }

    public Set<ComplexConditionEntity> getComplexConditions() {
        return complexConditions;
    }

    public void setComplexConditions(Set<ComplexConditionEntity> complexConditions) {
        this.complexConditions = complexConditions;
    }
}
