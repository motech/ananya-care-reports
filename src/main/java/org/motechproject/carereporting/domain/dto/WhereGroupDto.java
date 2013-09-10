package org.motechproject.carereporting.domain.dto;

import java.util.ArrayList;
import java.util.List;

public class WhereGroupDto {

    private List<WhereGroupDto> groups;

    private List<WhereConditionDto> conditions;

    private String operator;

    public WhereGroupDto() {
        this.groups = new ArrayList<>();
        this.conditions = new ArrayList<>();
    }

    public WhereGroupDto(List<WhereGroupDto> groups, List<WhereConditionDto> conditions, String operator) {
        this.groups = groups;
        this.conditions = conditions;
        this.operator = operator;
    }

    public List<WhereGroupDto> getGroups() {
        return groups;
    }

    public void setGroups(List<WhereGroupDto> groups) {
        this.groups = groups;
    }

    public List<WhereConditionDto> getConditions() {
        return conditions;
    }

    public void setConditions(List<WhereConditionDto> conditions) {
        this.conditions = conditions;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }
}
