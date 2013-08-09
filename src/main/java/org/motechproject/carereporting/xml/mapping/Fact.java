package org.motechproject.carereporting.xml.mapping;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "fact")
public class Fact {

    private String name;

    private WhereGroup whereGroup;

    private GroupedBy groupedBy;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @XmlElement(name = "where-group")
    public WhereGroup getWhereGroup() {
        return whereGroup;
    }

    public void setWhereGroup(WhereGroup whereGroup) {
        this.whereGroup = whereGroup;
    }

    @XmlElement(name = "groupedByDimensionFactRowsNumber")
    public GroupedBy getGroupedBy() {
        return groupedBy;
    }

    public void setGroupedBy(GroupedBy groupedBy) {
        this.groupedBy = groupedBy;
    }
}
