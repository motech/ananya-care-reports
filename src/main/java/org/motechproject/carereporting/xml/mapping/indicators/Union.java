package org.motechproject.carereporting.xml.mapping.indicators;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "union")
public class Union {

    private List<UnionElement> unionElements;

    @XmlElementWrapper(name = "union-elements")
    @XmlElement(name = "union-element")
    public List<UnionElement> getUnionElements() {
        return unionElements;
    }

    public void setUnionElements(List<UnionElement> unionElements) {
        this.unionElements = unionElements;
    }
}
