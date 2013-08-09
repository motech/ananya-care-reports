package org.motechproject.carereporting.xml.mapping;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "groupedByDimensionFactRowsNumber")
public class GroupedBy {

    private String comparisonSymbol;

    private String value;

    @XmlAttribute(name = "compSymbol")
    public String getComparisonSymbol() {
        return comparisonSymbol;
    }

    public void setComparisonSymbol(String comparisonSymbol) {
        this.comparisonSymbol = comparisonSymbol;
    }

    @XmlAttribute
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
