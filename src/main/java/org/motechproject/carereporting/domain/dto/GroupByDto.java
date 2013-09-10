package org.motechproject.carereporting.domain.dto;

public class GroupByDto {

    private String tableName;

    private String fieldName;

    private Integer fieldId;

    private HavingDto having;

    public GroupByDto() {

    }

    public GroupByDto(String tableName, String fieldName, Integer fieldId, HavingDto having) {
        this.tableName = tableName;
        this.fieldName = fieldName;
        this.fieldId = fieldId;
        this.having = having;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public Integer getFieldId() {
        return fieldId;
    }

    public void setFieldId(Integer fieldId) {
        this.fieldId = fieldId;
    }

    public HavingDto getHaving() {
        return having;
    }

    public void setHaving(HavingDto having) {
        this.having = having;
    }
}
