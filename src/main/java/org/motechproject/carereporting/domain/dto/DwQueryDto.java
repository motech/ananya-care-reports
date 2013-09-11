package org.motechproject.carereporting.domain.dto;

import java.util.List;

public class DwQueryDto {

    private String dimension;

    private List<SelectColumnDto> selectColumns;

    private WhereGroupDto whereGroup;

    private String joinType;

    private String key1;

    private String key2;

    private DwQueryDto combineWith;

    private GroupByDto groupBy;

    private String name;

    public DwQueryDto() {

    }

    public DwQueryDto(String dimension, List<SelectColumnDto> selectColumns, WhereGroupDto whereGroup,
                      String joinType, String key1, String key2, DwQueryDto combineWith, GroupByDto groupBy,
                      String name) {
        this.dimension = dimension;
        this.selectColumns = selectColumns;
        this.whereGroup = whereGroup;
        this.joinType = joinType;
        this.key1 = key1;
        this.key2 = key2;
        this.combineWith = combineWith;
        this.groupBy = groupBy;
        this.name = name;
    }

    public String getDimension() {
        return dimension;
    }

    public void setDimension(String dimension) {
        this.dimension = dimension;
    }

    public List<SelectColumnDto> getSelectColumns() {
        return selectColumns;
    }

    public void setSelectColumns(List<SelectColumnDto> selectColumns) {
        this.selectColumns = selectColumns;
    }

    public WhereGroupDto getWhereGroup() {
        return whereGroup;
    }

    public void setWhereGroup(WhereGroupDto whereGroup) {
        this.whereGroup = whereGroup;
    }

    public String getJoinType() {
        return joinType;
    }

    public void setJoinType(String joinType) {
        this.joinType = joinType;
    }

    public String getKey1() {
        return key1;
    }

    public void setKey1(String key1) {
        this.key1 = key1;
    }

    public String getKey2() {
        return key2;
    }

    public void setKey2(String key2) {
        this.key2 = key2;
    }

    public DwQueryDto getCombineWith() {
        return combineWith;
    }

    public void setCombineWith(DwQueryDto combineWith) {
        this.combineWith = combineWith;
    }

    public GroupByDto getGroupBy() {
        return groupBy;
    }

    public void setGroupBy(GroupByDto groupBy) {
        this.groupBy = groupBy;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
