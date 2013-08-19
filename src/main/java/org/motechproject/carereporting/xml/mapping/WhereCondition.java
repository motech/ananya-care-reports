package org.motechproject.carereporting.xml.mapping;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "where-condition")
public class WhereCondition {

    private WhereConditionType type;

    private String field;

    private String secondField;

    private String operator;

    private String value;

    private Integer offset;

    private String tableName;

    @XmlAttribute
    public WhereConditionType getType() {
        return type;
    }

    public void setType(WhereConditionType type) {
        this.type = type;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @XmlElement(name = "second-field")
    public String getSecondField() {
        return secondField;
    }

    public void setSecondField(String secondField) {
        this.secondField = secondField;
    }

    public Integer getOffset() {
        return offset;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    @XmlElement(name = "table-name")
    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }
}
