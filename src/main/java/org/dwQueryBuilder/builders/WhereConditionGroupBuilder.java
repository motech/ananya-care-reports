package org.dwQueryBuilder.builders;

import org.dwQueryBuilder.data.conditions.where.WhereCondition;
import org.dwQueryBuilder.data.conditions.where.WhereConditionGroup;
import org.dwQueryBuilder.data.enums.WhereConditionJoinType;

import java.util.LinkedHashSet;
import java.util.Set;

public class WhereConditionGroupBuilder {
    private Set<WhereConditionGroup> groups;
    private Set<WhereCondition> conditions;
    private WhereConditionJoinType joinType;

    public WhereConditionGroupBuilder withGroup(WhereConditionGroup group) {
        if (groups == null) {
            groups = new LinkedHashSet<>();
        }

        this.groups.add(group);
        return this;
    }

    public WhereConditionGroupBuilder withGroup(WhereConditionGroupBuilder group) {
        if (groups == null) {
            groups = new LinkedHashSet<>();
        }

        this.groups.add(group.build());
        return this;
    }

    public WhereConditionGroupBuilder withGroups(Set<WhereConditionGroup> groups) {
        this.groups = new LinkedHashSet<>(groups);
        return this;
    }

    public WhereConditionGroupBuilder withCondition(WhereCondition condition) {
        if (conditions == null) {
            conditions = new LinkedHashSet<>();
        }

        conditions.add(condition);
        return this;
    }

    public WhereConditionGroupBuilder withCondition(WhereConditionBuilder condition) {
        if (conditions == null) {
            conditions = new LinkedHashSet<>();
        }

        conditions.add(condition.build());
        return this;
    }

    public WhereConditionGroupBuilder withConditions(Set<WhereCondition> conditions) {
        this.conditions = new LinkedHashSet<>(conditions);
        return this;
    }

    public WhereConditionGroupBuilder withJoinType(WhereConditionJoinType joinType) {
        this.joinType = joinType;
        return this;
    }

    public WhereConditionGroup build() {
        return new WhereConditionGroup(conditions, groups, joinType);
    }
}
