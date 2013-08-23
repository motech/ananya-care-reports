package org.motechproject.carereporting.xml.mapping.indicators;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "report")
public class Report {

    private String type;

    private String labelX;

    private String labelY;

    @XmlAttribute
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @XmlAttribute(name = "label-x")
    public String getLabelX() {
        return labelX;
    }

    public void setLabelX(String labelX) {
        this.labelX = labelX;
    }

    @XmlAttribute(name = "label-y")
    public String getLabelY() {
        return labelY;
    }

    public void setLabelY(String labelY) {
        this.labelY = labelY;
    }
}
