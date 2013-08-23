package org.motechproject.carereporting.xml.mapping.indicators;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "dw-query")
public class DwQuery {

    private Dimension dimension;

    private List<Fact> facts;

    private WhereGroup whereGroup;

    private CombineWith combineWith;

    private List<SelectColumn> selectColumns;

    private String dimensionKey;

    private String factKey;

    public Dimension getDimension() {
        return dimension;
    }

    public void setDimension(Dimension dimension) {
        this.dimension = dimension;
    }

    @XmlElementWrapper(name = "facts")
    @XmlElement(name = "fact")
    public List<Fact> getFacts() {
        return facts;
    }

    public void setFacts(List<Fact> facts) {
        this.facts = facts;
    }

    @XmlElement(name = "where-group")
    public WhereGroup getWhereGroup() {
        return whereGroup;
    }

    public void setWhereGroup(WhereGroup whereGroup) {
        this.whereGroup = whereGroup;
    }

    @XmlElementWrapper(name = "select-columns")
    @XmlElement(name = "select-column")
    public List<SelectColumn> getSelectColumns() {
        return selectColumns;
    }

    public void setSelectColumns(List<SelectColumn> selectColumns) {
        this.selectColumns = selectColumns;
    }

    @XmlAttribute(name = "dimension-key")
    public String getDimensionKey() {
        return dimensionKey;
    }

    public void setDimensionKey(String dimensionKey) {
        this.dimensionKey = dimensionKey;
    }

    @XmlAttribute(name = "fact-key")
    public String getFactKey() {
        return factKey;
    }

    public void setFactKey(String factKey) {
        this.factKey = factKey;
    }

    @XmlElement(name = "combineWith")
    public CombineWith getCombineWith() {
        return combineWith;
    }

    public void setCombineWith(CombineWith combineWith) {
        this.combineWith = combineWith;
    }
}
