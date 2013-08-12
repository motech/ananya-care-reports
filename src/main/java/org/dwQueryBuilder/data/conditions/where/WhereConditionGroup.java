package org.dwQueryBuilder.data.conditions.where;

import org.dwQueryBuilder.data.enums.WhereConditionJoinType;

import java.util.LinkedHashSet;
import java.util.Set;

public class WhereConditionGroup {
    private Set<WhereConditionGroup> conditionGroups;
    private Set<WhereCondition> conditions;
    private WhereConditionJoinType joinType;

    public WhereConditionGroup() {
        this.conditions = new LinkedHashSet<>();
    }

    public WhereConditionGroup(Set<WhereCondition> conditions) {
        this.conditions = conditions;
    }

    public WhereConditionGroup(Set<WhereCondition> conditions, Set<WhereConditionGroup> conditionGroups) {
        this.conditions = conditions;
        this.conditionGroups = conditionGroups;
    }

    public WhereConditionGroup(Set<WhereCondition> conditions, Set<WhereConditionGroup> conditionGroups,
                               WhereConditionJoinType joinType) {
        this.conditions = conditions;
        this.conditionGroups = conditionGroups;
        this.joinType = joinType;
    }

    public Set<WhereConditionGroup> getConditionGroups() {
        return conditionGroups;
    }

    public void setConditionGroups(Set<WhereConditionGroup> conditionGroups) {
        this.conditionGroups = conditionGroups;
    }
    
    public void addConditionGroup() {
        
    }

    public Set<WhereCondition> getConditions() {
        return conditions;
    }

    public void addCondition(WhereCondition condition) {
        this.conditions.add(condition);
    }

    public void setConditions(Set<WhereCondition> conditions) {
        this.conditions = conditions;
    }

    public WhereConditionJoinType getJoinType() {
        return joinType;
    }

    public void setJoinType(WhereConditionJoinType joinType) {
        this.joinType = joinType;
    }
}
