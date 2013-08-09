package org.dwQueryBuilder.builders;

import org.dwQueryBuilder.data.GroupBy;
import org.dwQueryBuilder.data.conditions.HavingCondition;

public class GroupByConditionBuilder {
    private String tableName;
    private String fieldName;
    private HavingCondition having;

    public GroupByConditionBuilder withField(String tableName, String fieldName) {
        this.tableName = tableName;
        this.fieldName = fieldName;
        return this;
    }

    public GroupByConditionBuilder withHaving(HavingCondition having) {
        this.having = having;
        return this;
    }

    public GroupByConditionBuilder withHaving(HavingConditionBuilder havingBuilder) {
        this.having = havingBuilder.build();
        return this;
    }

    public GroupBy build() {
        return new GroupBy(tableName, fieldName, having);
    }
}
