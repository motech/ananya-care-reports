package org.motechproject.carereporting.xml.mapping.indicators;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;
import java.util.List;

@XmlRootElement(name = "where-condition")
public class WhereCondition {

    private WhereConditionType type;

    private String field;

    private String secondField;

    private String operator;

    private String value;

    private Integer offset1;

    private Integer offset2;

    private Date date1;

    private Date date2;

    private String tableName;

    private List<String> values;

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

    @XmlElement(name = "offset")
    public Integer getOffset1() {
        return offset1;
    }

    public void setOffset1(Integer offset1) {
        this.offset1 = offset1;
    }

    @XmlElement(name = "second-offset")
    public Integer getOffset2() {
        return offset2;
    }

    public void setOffset2(Integer offset2) {
        this.offset2 = offset2;
    }

    @XmlElement(name = "date")
    public Date getDate1() {
        return date1;
    }

    public void setDate1(Date date1) {
        this.date1 = date1;
    }

    @XmlElement(name = "second-date")
    public Date getDate2() {
        return date2;
    }

    public void setDate2(Date date2) {
        this.date2 = date2;
    }

    @XmlElement(name = "table-name")
    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public List<String> getValues() {
        return values;
    }

    public void setValues(List<String> values) {
        this.values = values;
    }
}
