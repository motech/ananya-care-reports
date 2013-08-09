package org.dwQueryBuilder.data.conditions.where;

import java.util.Set;

public class WhereConditionGroup {
    private Set<WhereConditionGroup> conditionGroups;
    private Set<WhereCondition> conditions;

    public WhereConditionGroup() {

    }

    public WhereConditionGroup(Set<WhereCondition> conditions) {
        this.conditions = conditions;
    }

    public WhereConditionGroup(Set<WhereCondition> conditions, Set<WhereConditionGroup> conditionGroups) {
        this.conditions = conditions;
        this.conditionGroups = conditionGroups;
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

    public void setConditions(Set<WhereCondition> conditions) {
        this.conditions = conditions;
    }
}
