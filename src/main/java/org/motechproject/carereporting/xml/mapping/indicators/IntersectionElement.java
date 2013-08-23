package org.motechproject.carereporting.xml.mapping.indicators;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "intersection-element")
public class IntersectionElement {

    private Integer indicatorId;

    private DwQuery dwQuery;

    @XmlElement(name = "indicator-id")
    public Integer getIndicatorId() {
        return indicatorId;
    }

    public void setIndicatorId(Integer indicatorId) {
        this.indicatorId = indicatorId;
    }

    @XmlElement(name = "dw-query")
    public DwQuery getDwQuery() {
        return dwQuery;
    }

    public void setDwQuery(DwQuery dwQuery) {
        this.dwQuery = dwQuery;
    }
}
