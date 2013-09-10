package org.motechproject.carereporting.domain.dto;

public class SelectColumnDto {

    private Integer field;

    private String function;

    private String nullValue;

    public SelectColumnDto() {

    }

    public SelectColumnDto(Integer computedFieldId, String function, String nullValue) {
        this.field = computedFieldId;
        this.function = function;
        this.nullValue = nullValue;
    }

    public Integer getField() {
        return field;
    }

    public void setField(Integer field) {
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
