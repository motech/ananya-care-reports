package org.motechproject.carereporting.domain.dto;

public class HavingDto {

    private String function;

    private String operator;

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
