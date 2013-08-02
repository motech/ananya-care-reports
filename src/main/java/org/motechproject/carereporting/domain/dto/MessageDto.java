package org.motechproject.carereporting.domain.dto;

import org.hibernate.validator.constraints.NotEmpty;

import java.io.Serializable;

public class MessageDto implements Serializable {

    protected static final long serialVersionUID = 0L;

    @NotEmpty
    private String code;

    @NotEmpty
    private String value;

    public MessageDto() {

    }

    public MessageDto(final String code, final String value) {
        this.code = code;
        this.value = value;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String toPropertyString() {
        return String.format("%s=%s", code, value);
    }
}
