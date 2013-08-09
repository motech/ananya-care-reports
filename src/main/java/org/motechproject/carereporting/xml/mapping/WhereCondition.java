package org.motechproject.carereporting.xml.mapping;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "where-condition")
public class WhereCondition {

    private WhereConditionType type;

    @XmlAttribute
    public WhereConditionType getType() {
        return type;
    }

    public void setType(WhereConditionType type) {
        this.type = type;
    }
}
