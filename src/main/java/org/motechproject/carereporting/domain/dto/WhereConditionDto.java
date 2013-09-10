package org.motechproject.carereporting.domain.dto;

import java.util.Date;
import java.util.List;

public class WhereConditionDto {

    private String tableName1;

    private Integer field1;

    private String tableName2;

    private Integer field2;

    private Integer fieldOffset1;

    private Integer fieldOffset2;

    private String operator;

    private String type;

    private String value;

    private List<String> values;

    private Date date1;
    
    private Date date2;
    
    public WhereConditionDto() {
        
    }
    
    public WhereConditionDto(String tableName1, Integer field1, String tableName2, Integer field2, Integer fieldOffset1,
                             Integer fieldOffset2, String operator, String type, String value, List<String> values,
                             Date date1, Date date2) {
        this.tableName1 = tableName1;
        this.field1 = field1;
        this.tableName2 = tableName2;
        this.field2 = field2;
        this.fieldOffset1 = fieldOffset1;
        this.fieldOffset2 = fieldOffset2;
        this.operator = operator;
        this.type = type;
        this.value = value;
        this.values = values;
        this.date1 = date1;
        this.date2 = date2;
    }

    public String getTableName1() {
        return tableName1;
    }

    public void setTableName1(String tableName1) {
        this.tableName1 = tableName1;
    }

    public Integer getField1() {
        return field1;
    }

    public void setField1(Integer field1) {
        this.field1 = field1;
    }

    public String getTableName2() {
        return tableName2;
    }

    public void setTableName2(String tableName2) {
        this.tableName2 = tableName2;
    }

    public Integer getField2() {
        return field2;
    }

    public void setField2(Integer field2) {
        this.field2 = field2;
    }

    public Integer getFieldOffset1() {
        return fieldOffset1;
    }

    public void setFieldOffset1(Integer fieldOffset1) {
        this.fieldOffset1 = fieldOffset1;
    }

    public Integer getFieldOffset2() {
        return fieldOffset2;
    }

    public void setFieldOffset2(Integer fieldOffset2) {
        this.fieldOffset2 = fieldOffset2;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public List<String> getValues() {
        return values;
    }

    public void setValues(List<String> values) {
        this.values = values;
    }

    public Date getDate1() {
        return date1;
    }

    public void setDate1(Date date1) {
        this.date1 = date1;
    }

    public Date getDate2() {
        return date2;
    }

    public void setDate2(Date date2) {
        this.date2 = date2;
    }
}
