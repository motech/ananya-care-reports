package org.motechproject.carereporting.xml.mapping.indicators;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;

@XmlEnum
public enum Function {

    @XmlEnumValue("Average") Average,
    @XmlEnumValue("Count") Count,
    @XmlEnumValue("Max") Max,
    @XmlEnumValue("Min") Min,
    @XmlEnumValue("Sum") Sum

}
