package org.motechproject.carereporting.domain.dto;

import com.fasterxml.jackson.annotation.JsonView;
import org.motechproject.carereporting.domain.views.QueryJsonView;

import java.util.ArrayList;
import java.util.List;

public class WhereGroupDto {

    @JsonView({ QueryJsonView.EditForm.class })
    private List<WhereGroupDto> groups;

    @JsonView({ QueryJsonView.EditForm.class })
    private List<WhereConditionDto> conditions;

    @JsonView({ QueryJsonView.EditForm.class })
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
