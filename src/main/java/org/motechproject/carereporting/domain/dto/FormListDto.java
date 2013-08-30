package org.motechproject.carereporting.domain.dto;

import com.fasterxml.jackson.annotation.JsonView;
import org.motechproject.carereporting.domain.FormEntity;
import org.motechproject.carereporting.domain.views.IndicatorJsonView;

import java.util.List;

public class FormListDto {

    @JsonView({ IndicatorJsonView.ListFormNames.class })
    private List<FormEntity> motherForms;

    @JsonView({ IndicatorJsonView.ListFormNames.class })
    private List<FormEntity> childForms;

    @JsonView({ IndicatorJsonView.ListFormNames.class })
    private List<FormEntity> otherForms;

    public FormListDto(List<FormEntity> motherForms, List<FormEntity> childForms, List<FormEntity> otherForms) {
        this.motherForms = motherForms;
        this.childForms = childForms;
        this.otherForms = otherForms;
    }

    public List<FormEntity> getMotherForms() {
        return motherForms;
    }

    public void setMotherForms(List<FormEntity> motherForms) {
        this.motherForms = motherForms;
    }

    public List<FormEntity> getChildForms() {
        return childForms;
    }

    public void setChildForms(List<FormEntity> childForms) {
        this.childForms = childForms;
    }

    public List<FormEntity> getOtherForms() {
        return otherForms;
    }

    public void setOtherForms(List<FormEntity> otherForms) {
        this.otherForms = otherForms;
    }
}
