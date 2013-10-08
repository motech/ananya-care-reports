package org.motechproject.carereporting.domain.dto;

import com.fasterxml.jackson.annotation.JsonView;
import org.motechproject.carereporting.domain.ComputedFieldEntity;
import org.motechproject.carereporting.domain.views.QueryJsonView;

public class SelectColumnDto {

    @JsonView({ QueryJsonView.EditForm.class })
    private ComputedFieldEntity field;

    @JsonView({ QueryJsonView.EditForm.class })
    private String function;

    @JsonView({ QueryJsonView.EditForm.class })
    private String nullValue;

    public SelectColumnDto() {

    }

    public SelectColumnDto(ComputedFieldEntity field, String function, String nullValue) {
        this.field = field;
        this.function = function;
        this.nullValue = nullValue;
    }

    public ComputedFieldEntity getField() {
        return field;
    }

    public void setField(ComputedFieldEntity field) {
        this.field = field;
    }

    public String getFunction() {
        return function;
    }

    public void setFunction(String function) {
        this.function = function;
    }

    public String getNullValue() {
        return nullValue;
    }

    public void setNullValue(String nullValue) {
        this.nullValue = nullValue;
    }
}
