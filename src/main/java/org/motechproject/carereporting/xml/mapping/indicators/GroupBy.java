package org.motechproject.carereporting.xml.mapping.indicators;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "groupBy")
public class GroupBy {

    private String tableName;

    private String fieldName;

    private Having having;

    @XmlAttribute
    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    @XmlAttribute
    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    @XmlElement(required = false)
    public Having getHaving() {
        return having;
    }

    public void setHaving(Having having) {
        this.having = having;
    }
}
