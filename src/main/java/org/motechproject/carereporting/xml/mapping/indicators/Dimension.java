package org.motechproject.carereporting.xml.mapping.indicators;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "dimension")
public class Dimension {

    private String name;

    @XmlAttribute(name = "tableName")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
