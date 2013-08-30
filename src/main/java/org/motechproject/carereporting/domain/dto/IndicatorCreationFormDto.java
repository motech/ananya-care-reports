package org.motechproject.carereporting.domain.dto;

import com.fasterxml.jackson.annotation.JsonView;
import org.motechproject.carereporting.domain.ComplexConditionEntity;
import org.motechproject.carereporting.domain.FormEntity;
import org.motechproject.carereporting.domain.IndicatorCategoryEntity;
import org.motechproject.carereporting.domain.ReportTypeEntity;
import org.motechproject.carereporting.domain.RoleEntity;
import org.motechproject.carereporting.domain.views.IndicatorJsonView;

import java.util.Set;

public class IndicatorCreationFormDto {

    @JsonView({ IndicatorJsonView.CreationForm.class })
    private Set<IndicatorCategoryEntity> categories;

    @JsonView({ IndicatorJsonView.CreationForm.class })
    private Set<RoleEntity> roles;

    @JsonView({ IndicatorJsonView.CreationForm.class })
    private Set<FormEntity> forms;

    @JsonView({ IndicatorJsonView.CreationForm.class })
    private Set<ComplexConditionEntity> complexConditions;

    @JsonView({ IndicatorJsonView.CreationForm.class })
    private Set<ReportTypeEntity> reportTypes;

    public IndicatorCreationFormDto() {

    }

    public IndicatorCreationFormDto(Set<IndicatorCategoryEntity> categories,
                                    Set<RoleEntity> roles,
                                    Set<FormEntity> forms,
                                    Set<ComplexConditionEntity> complexConditions,
                                    Set<ReportTypeEntity> reportTypes) {
        this.categories = categories;
        this.roles = roles;
        this.forms = forms;
        this.complexConditions = complexConditions;
        this.reportTypes = reportTypes;
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

    public Set<ReportTypeEntity> getReportTypes() {
        return reportTypes;
    }

    public void setReportTypes(Set<ReportTypeEntity> reportTypes) {
        this.reportTypes = reportTypes;
    }
}
