package org.motechproject.carereporting.domain.dto;

import com.fasterxml.jackson.annotation.JsonView;
import org.motechproject.carereporting.domain.views.QueryJsonView;

public class HavingDto {

    @JsonView({ QueryJsonView.EditForm.class })
    private String function;

    @JsonView({ QueryJsonView.EditForm.class })
    private String operator;

    @JsonView({ QueryJsonView.EditForm.class })
    private String value;

    public HavingDto() {

    }

    public HavingDto(String function, String operator, String value) {
        this.function = function;
        this.operator = operator;
        this.value = value;
    }

    public String getFunction() {
        return function;
    }

    public void setFunction(String function) {
        this.function = function;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
