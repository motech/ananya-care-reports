package org.motechproject.carereporting.xml.mapping.indicators;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;

@XmlEnum
public enum Operator {

    @XmlEnumValue("<") Less("<"),
    @XmlEnumValue("<=") LessEqual("<="),
    @XmlEnumValue("=") Equal("="),
    @XmlEnumValue("<>") NotEqual("<>"),
    @XmlEnumValue(">") Greater(">"),
    @XmlEnumValue(">=") GreaterEqual(">=");

    private String value;

    private Operator(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
