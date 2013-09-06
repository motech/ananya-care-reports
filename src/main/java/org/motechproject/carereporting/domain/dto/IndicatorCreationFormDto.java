package org.motechproject.carereporting.domain.dto;

import com.fasterxml.jackson.annotation.JsonView;
import org.motechproject.carereporting.domain.FormEntity;
import org.motechproject.carereporting.domain.FrequencyEntity;
import org.motechproject.carereporting.domain.IndicatorCategoryEntity;
import org.motechproject.carereporting.domain.LevelEntity;
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
    private Set<LevelEntity> levels;

    @JsonView({ IndicatorJsonView.CreationForm.class })
    private Set<FrequencyEntity> frequencies;

    @JsonView({ IndicatorJsonView.CreationForm.class })
    private Set<ReportTypeEntity> reportTypes;

    public IndicatorCreationFormDto() {

    }

    public IndicatorCreationFormDto(Set<IndicatorCategoryEntity> categories, Set<RoleEntity> roles, Set<FormEntity> forms, Set<LevelEntity> levels, Set<FrequencyEntity> frequencies, Set<ReportTypeEntity> reportTypes) {
        this.categories = categories;
        this.roles = roles;
        this.forms = forms;
        this.levels = levels;
        this.frequencies = frequencies;
        this.reportTypes = reportTypes;
    }

    public Set<LevelEntity> getLevels() {
        return levels;
    }

    public void setLevels(Set<LevelEntity> levels) {
        this.levels = levels;
    }

    public Set<FrequencyEntity> getFrequencies() {
        return frequencies;
    }

    public void setFrequencies(Set<FrequencyEntity> frequencies) {
        this.frequencies = frequencies;
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

    public Set<ReportTypeEntity> getReportTypes() {
        return reportTypes;
    }

    public void setReportTypes(Set<ReportTypeEntity> reportTypes) {
        this.reportTypes = reportTypes;
    }
}
