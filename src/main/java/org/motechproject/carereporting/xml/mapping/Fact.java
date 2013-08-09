package org.motechproject.carereporting.xml.mapping;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "fact")
public class Fact {

    private String name;

    private List<WhereCondition> conditions;

    private GroupedBy groupedBy;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @XmlElementWrapper(name = "conditions")
    @XmlElement(name = "where-condition")
    public List<WhereCondition> getConditions() {
        return conditions;
    }

    public void setConditions(List<WhereCondition> conditions) {
        this.conditions = conditions;
    }

    @XmlElement(name = "groupedByDimensionFactRowsNumber")
    public GroupedBy getGroupedBy() {
        return groupedBy;
    }

    public void setGroupedBy(GroupedBy groupedBy) {
        this.groupedBy = groupedBy;
    }
}
