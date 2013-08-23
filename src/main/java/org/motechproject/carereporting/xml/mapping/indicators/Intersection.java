package org.motechproject.carereporting.xml.mapping.indicators;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "intersection")
public class Intersection {

    private List<IntersectionElement> intersectionElements;

    @XmlElementWrapper(name = "intersection-elements")
    @XmlElement(name = "intersection-element")
    public List<IntersectionElement> getIntersectionElements() {
        return intersectionElements;
    }

    public void setIntersectionElements(List<IntersectionElement> intersectionElements) {
        this.intersectionElements = intersectionElements;
    }
}
