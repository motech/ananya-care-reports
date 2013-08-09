package org.motechproject.carereporting.xml.mapping;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;

@XmlEnum
public enum Level {
    @XmlEnumValue("State") STATE,
    @XmlEnumValue("District") DISTRICT,
    @XmlEnumValue("Block") BLOCK
}
