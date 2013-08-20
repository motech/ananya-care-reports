package org.motechproject.carereporting.xml.mapping;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "fact")
public class Fact {

    private String name;

    private WhereGroup whereGroup;

    private GroupedBy groupedBy;

    private List<SelectColumn> selectColumns;

    private CombineType combineType;

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

    @XmlElementWrapper(name = "select-columns")
    @XmlElement(name = "select-column")
    public List<SelectColumn> getSelectColumns() {
        return selectColumns;
    }

    public void setSelectColumns(List<SelectColumn> selectColumns) {
        this.selectColumns = selectColumns;
    }

    @XmlAttribute(name = "combine-type")
    public CombineType getCombineType() {
        return combineType;
    }

    public void setCombineType(CombineType combineType) {
        this.combineType = combineType;
    }
}
