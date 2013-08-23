package org.motechproject.carereporting.xml.mapping.indicators;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "where-group")
public class WhereGroup {

    private List<WhereGroup> groups;

    private List<WhereCondition> conditions;

    private String join;

    @XmlElement(name = "where-group")
    public List<WhereGroup> getGroups() {
        return groups;
    }

    public void setGroups(List<WhereGroup> groups) {
        this.groups = groups;
    }

    @XmlElement(name = "where-condition")
    public List<WhereCondition> getConditions() {
        return conditions;
    }

    public void setConditions(List<WhereCondition> conditions) {
        this.conditions = conditions;
    }

    @XmlAttribute
    public String getJoin() {
        return join;
    }

    public void setJoin(String join) {
        this.join = join;
    }
}
