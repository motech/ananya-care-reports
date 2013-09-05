package org.motechproject.carereporting.domain.dto;

import com.fasterxml.jackson.annotation.JsonView;
import org.motechproject.carereporting.domain.FormEntity;
import org.motechproject.carereporting.domain.views.IndicatorJsonView;

import java.util.Set;

public class FormListDto {

    @JsonView({ IndicatorJsonView.ListFormNames.class })
    private Set<FormEntity> motherForms;

    @JsonView({ IndicatorJsonView.ListFormNames.class })
    private Set<FormEntity> childForms;

    @JsonView({ IndicatorJsonView.ListFormNames.class })
    private Set<FormEntity> otherForms;

    public FormListDto(Set<FormEntity> motherForms, Set<FormEntity> childForms, Set<FormEntity> otherForms) {
        this.motherForms = motherForms;
        this.childForms = childForms;
        this.otherForms = otherForms;
    }

    public Set<FormEntity> getMotherForms() {
        return motherForms;
    }

    public void setMotherForms(Set<FormEntity> motherForms) {
        this.motherForms = motherForms;
    }

    public Set<FormEntity> getChildForms() {
        return childForms;
    }

    public void setChildForms(Set<FormEntity> childForms) {
        this.childForms = childForms;
    }

    public Set<FormEntity> getOtherForms() {
        return otherForms;
    }

    public void setOtherForms(Set<FormEntity> otherForms) {
        this.otherForms = otherForms;
    }

}
