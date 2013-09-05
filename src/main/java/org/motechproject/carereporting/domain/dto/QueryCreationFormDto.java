package org.motechproject.carereporting.domain.dto;

import com.fasterxml.jackson.annotation.JsonView;
import org.motechproject.carereporting.domain.FormEntity;
import org.motechproject.carereporting.domain.views.QueryJsonView;

import java.util.Set;

public class QueryCreationFormDto {

    @JsonView({ QueryJsonView.CreationForm.class })
    private Set<FormEntity> forms;

    public QueryCreationFormDto() {

    }

    public QueryCreationFormDto(Set<FormEntity> forms) {
        this.forms = forms;
    }

    public Set<FormEntity> getForms() {
        return forms;
    }

    public void setForms(Set<FormEntity> forms) {
        this.forms = forms;
    }
}
