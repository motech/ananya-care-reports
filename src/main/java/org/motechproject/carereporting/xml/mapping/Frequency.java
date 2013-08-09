package org.motechproject.carereporting.xml.mapping;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;

@XmlEnum
public enum Frequency {
    @XmlEnumValue("Daily") DAILY(1),
    @XmlEnumValue("Weekly") WEEKLY(7),
    @XmlEnumValue("Monthly") MONTHLY(30),
    @XmlEnumValue("Quarterly") QUARTERLY(90),
    @XmlEnumValue("Yearly") YEARLY(360);

    private int value;

    private Frequency(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
