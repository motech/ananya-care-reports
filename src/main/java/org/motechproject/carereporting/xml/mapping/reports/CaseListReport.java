package org.motechproject.carereporting.xml.mapping.reports;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "caseListReport")
public class CaseListReport {

    private List<ReportField> fields;

    @XmlElement(name = "field")
    public List<ReportField> getFields() {
        return fields;
    }

    public void setFields(List<ReportField> fields) {
        this.fields = fields;
    }
}
