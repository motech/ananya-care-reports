package org.dwQueryBuilder.builders.query.helpers;

import org.dwQueryBuilder.data.DwQueryCombination;
import org.dwQueryBuilder.data.Fact;
import org.dwQueryBuilder.data.enums.CombineType;
import org.dwQueryBuilder.data.enums.OperatorType;
import org.dwQueryBuilder.data.queries.ComplexDwQuery;
import org.dwQueryBuilder.data.queries.DwQuery;
import org.dwQueryBuilder.data.queries.SimpleDwQuery;
import org.dwQueryBuilder.exceptions.QueryBuilderRuntimeException;
import org.jooq.DSLContext;
import org.jooq.Select;
import org.jooq.SelectConditionStep;
import org.jooq.SelectHavingConditionStep;
import org.jooq.SelectJoinStep;
import org.jooq.SelectSelectStep;

import static org.jooq.impl.DSL.fieldByName;
import static org.jooq.impl.DSL.tableByName;

public final class DwQueryHelper {

    private static final String FACT_ALIAS = "facts";

    private DwQueryHelper() {

    }

    public static Select buildFromDwQuery(DSLContext dslContext, String schemaName, DwQuery dwQuery) {
        try {
            SelectSelectStep selectSelectStep = dslContext.select();
            SelectColumnHelper.buildSelectColumns(schemaName, selectSelectStep, dwQuery);
            String tableName = null;

            SelectHavingConditionStep selectHavingConditionStep = null;
            if (dwQuery.getGroupBy() != null) {
                selectHavingConditionStep = GroupByHelper
                        .buildGroupBy(schemaName, selectSelectStep, dwQuery.getGroupBy());
            }

            SelectConditionStep selectConditionStep = null;
            selectSelectStep = (selectHavingConditionStep != null)
                    ? (SelectSelectStep) selectHavingConditionStep
                    : selectSelectStep;
            Select select = null;
            if (dwQuery instanceof SimpleDwQuery) {
                tableName = ((SimpleDwQuery) dwQuery).getTableName();
                selectConditionStep = buildFromSimpleDwQuery(schemaName, selectSelectStep, (SimpleDwQuery) dwQuery);
            } else if (dwQuery instanceof ComplexDwQuery) {
                tableName = ((ComplexDwQuery) dwQuery).getDimension();
                select = buildFromComplexDwQuery(dslContext, schemaName, selectSelectStep, (ComplexDwQuery) dwQuery);
            }

            select = chooseSelectStep(select, selectConditionStep, selectHavingConditionStep, selectSelectStep);
            select = buildCombineWithStep(dslContext, schemaName, dwQuery, select, tableName);

            return select;
        } catch (Exception e) {
            throw new QueryBuilderRuntimeException(e);
        }
    }

    public static Select buildCombineWithStep(DSLContext dslContext,
                                            String schemaName,
                                            DwQuery dwQuery,
                                            Select select,
                                            String tableName) {
        Select newSelect = select;

        if (dwQuery.getCombineWith() != null) {
            for (DwQueryCombination dwQueryCombination : dwQuery.getCombineWith()) {
                if (!dwQueryCombination.getCombineType().equals(CombineType.Join)) {
                    continue;
                }

                newSelect = CombinationHelper.buildCombineWith(
                        dslContext, schemaName, newSelect, dwQueryCombination, tableName);
            }
            for (DwQueryCombination dwQueryCombination : dwQuery.getCombineWith()) {
                if (dwQueryCombination.getCombineType().equals(CombineType.Join)) {
                    continue;
                }

                newSelect = CombinationHelper.buildCombineWith(
                        dslContext, schemaName, newSelect, dwQueryCombination, tableName);
            }
        }

        return newSelect;
    }

    public static Select chooseSelectStep(Select select, SelectConditionStep selectConditionStep,
                                           SelectHavingConditionStep selectHavingConditionStep,
                                           SelectSelectStep selectSelectStep) {
        Select newSelect = select;

        if (newSelect == null) {
            if (selectConditionStep != null) {
                newSelect = selectConditionStep;
            } else if (selectHavingConditionStep != null) {
                newSelect = selectHavingConditionStep;
            } else {
                newSelect = selectSelectStep;
            }
        }

        return newSelect;
    }

    public static SelectConditionStep buildFromSimpleDwQuery(String schemaName,
                                                              SelectSelectStep selectSelectStep,
                                                              SimpleDwQuery simpleDwQuery) {
        SelectJoinStep selectJoinStep = selectSelectStep.from(tableByName(
                schemaName, simpleDwQuery.getTableName()));

        SelectConditionStep selectConditionStep = selectJoinStep.where();
        if (simpleDwQuery.getWhereConditionGroup() != null) {
            selectConditionStep = ConditionHelper.buildWhereConditionGroup(selectConditionStep,
                    simpleDwQuery.getWhereConditionGroup());
        }

        return selectConditionStep;
    }

    public static Select buildFromComplexDwQuery(DSLContext dslContext,
                                                 String schemaName,
                                                 SelectSelectStep selectSelectStep,
                                                 ComplexDwQuery complexDwQuery) {
        SelectJoinStep selectJoinStep = selectSelectStep.from(tableByName(
                schemaName, complexDwQuery.getDimension()));

        Select factSelect = null;
        for (Fact fact : complexDwQuery.getFacts()) {
            if (fact.getCombineType() == null) {
                factSelect = buildFromDwQuery(dslContext, schemaName, fact.getTable());
                continue;
            }

            factSelect = FactHelper.buildFact(dslContext, schemaName, factSelect, fact);
        }
        selectJoinStep = selectJoinStep.join(factSelect.asTable(FACT_ALIAS)).on(
                ConditionHelper.buildCondition(
                        fieldByName(
                                schemaName,
                                complexDwQuery.getDimension(),
                                complexDwQuery.getDimensionKey()),
                        OperatorType.Equal,
                        fieldByName(
                                FACT_ALIAS,
                                complexDwQuery.getFactKey())
                )
        );

        SelectConditionStep selectConditionStep = selectJoinStep.where();
        if (complexDwQuery.getWhereConditionGroup() != null) {
            selectConditionStep = ConditionHelper.buildWhereConditionGroup(selectConditionStep,
                    complexDwQuery.getWhereConditionGroup());
        }

        return selectConditionStep;
    }

    public static String getTableNameForDwQuery(DwQuery dwQuery) {

        if (dwQuery instanceof SimpleDwQuery) {
            return ((SimpleDwQuery) dwQuery).getTableName();
        } else if (dwQuery instanceof ComplexDwQuery) {
            return ((ComplexDwQuery) dwQuery).getDimension();
        }

        return null;
    }

}
