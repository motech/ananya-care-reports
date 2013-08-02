package org.motechproject.carereporting.domain.dto;

import org.motechproject.carereporting.domain.types.FieldType;

public class FieldDto {
    private String name;
    private FieldType type;

    public FieldDto(String name, FieldType type) {
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public FieldType getType() {
        return type;
    }

    public void setType(FieldType type) {
        this.type = type;
    }
}
