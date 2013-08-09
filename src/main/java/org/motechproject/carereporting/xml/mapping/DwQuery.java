package org.motechproject.carereporting.xml.mapping;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "dw-query")
public class DwQuery {

    private Dimension dimension;

    private List<Fact> facts;

    private WhereGroup whereGroup;

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
}
