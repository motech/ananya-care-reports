package org.motechproject.carereporting.xml.mapping.reports;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "field")
public class ReportField {

    private String dbName;

    private String displayName;

    @XmlAttribute(name = "db_name", required = true)
    public String getDbName() {
        return dbName;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    @XmlAttribute(name = "display_name", required = true)
    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }
}
