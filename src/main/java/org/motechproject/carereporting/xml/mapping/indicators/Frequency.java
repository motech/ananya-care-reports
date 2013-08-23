package org.motechproject.carereporting.xml.mapping.indicators;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;

@XmlEnum
public enum Frequency {
    @XmlEnumValue("Daily") DAILY(5),
    @XmlEnumValue("Weekly") WEEKLY(4),
    @XmlEnumValue("Monthly") MONTHLY(3),
    @XmlEnumValue("Quarterly") QUARTERLY(2),
    @XmlEnumValue("Yearly") YEARLY(1);

    private int value;

    private Frequency(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
