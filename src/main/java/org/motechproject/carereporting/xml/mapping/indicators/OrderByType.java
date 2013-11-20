package org.motechproject.carereporting.xml.mapping.indicators;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;

@XmlEnum
public enum OrderByType {

    @XmlEnumValue("ascending") Ascending("Ascending"),
    @XmlEnumValue("descending") Descending("Descending");

    private String value;

    private OrderByType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
