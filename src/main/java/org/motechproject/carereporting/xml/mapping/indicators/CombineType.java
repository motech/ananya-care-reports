package org.motechproject.carereporting.xml.mapping.indicators;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;

@XmlType(name = "combine-type")
@XmlEnum
public enum CombineType {
    @XmlEnumValue("Join") Joing,
    @XmlEnumValue("Intersection") Intersection,
    @XmlEnumValue("Union") Union,
    @XmlEnumValue("UnionAll") UnionAll
}
