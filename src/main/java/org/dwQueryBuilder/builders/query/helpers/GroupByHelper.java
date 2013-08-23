package org.dwQueryBuilder.builders.query.helpers;

import org.dwQueryBuilder.data.GroupBy;
import org.dwQueryBuilder.data.conditions.HavingCondition;
import org.jooq.SelectHavingConditionStep;
import org.jooq.SelectHavingStep;
import org.jooq.SelectSelectStep;

import static org.jooq.impl.DSL.fieldByName;

public final class GroupByHelper {

    private GroupByHelper() {

    }

    public static SelectHavingConditionStep buildGroupBy(String schemaName,
                                                         SelectSelectStep selectSelectStep,
                                                         GroupBy groupBy) {
        SelectHavingStep selectHavingStep = selectSelectStep.groupBy(
                fieldByName(
                        schemaName,
                        groupBy.getTableName(),
                        groupBy.getFieldName()));

        if (groupBy.getHaving() != null) {
            HavingCondition havingCondition = groupBy.getHaving();

            return selectSelectStep.having(
                    ConditionHelper.buildCondition(
                            schemaName,
                            havingCondition.getSelectColumn(),
                            havingCondition.getOperator(),
                            havingCondition.getValue()));
        }

        return selectHavingStep.having();
    }

}
