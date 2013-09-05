package org.motechproject.carereporting.xml.mapping.indicators;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;

@XmlType(name = "type")
@XmlEnum
public enum WhereConditionType {
    @XmlEnumValue("valueComparison") VALUE_COMPARISON,
    @XmlEnumValue("dateDiff") DATE_DIFF,
    @XmlEnumValue("fieldComparison") FIELD_COMPARISON,
    @XmlEnumValue("dateWithOffsetDiff") DATE_WITH_OFFSET_DIFF,
    @XmlEnumValue("dateRange") DATE_RANGE,
    @XmlEnumValue("dateValue") DATE_VALUE,
    @XmlEnumValue("enumRange") ENUM_RANGE,
    @XmlEnumValue("period") PERIOD,
    @XmlEnumValue("calculationEndDate") CALCULATE_VALUES_START_DATE
}
