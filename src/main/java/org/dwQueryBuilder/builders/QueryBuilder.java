package org.dwQueryBuilder.builders;

import org.apache.commons.lang.NotImplementedException;
import org.dwQueryBuilder.data.DwQueryCombination;
import org.dwQueryBuilder.data.Fact;
import org.dwQueryBuilder.data.GroupBy;
import org.dwQueryBuilder.data.SelectColumn;
import org.dwQueryBuilder.data.conditions.HavingCondition;
import org.dwQueryBuilder.data.conditions.where.ValueComparison;
import org.dwQueryBuilder.data.conditions.where.WhereCondition;
import org.dwQueryBuilder.data.conditions.where.WhereConditionGroup;
import org.dwQueryBuilder.data.enums.OperatorType;
import org.dwQueryBuilder.data.queries.ComplexDwQuery;
import org.dwQueryBuilder.data.queries.DwQuery;
import org.dwQueryBuilder.data.queries.SimpleDwQuery;
import org.dwQueryBuilder.exceptions.QueryBuilderException;
import org.jooq.AggregateFunction;
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.jooq.Field;
import org.jooq.Param;
import org.jooq.SQLDialect;
import org.jooq.Select;
import org.jooq.SelectConditionStep;
import org.jooq.SelectHavingConditionStep;
import org.jooq.SelectHavingStep;
import org.jooq.SelectJoinStep;
import org.jooq.SelectSelectStep;
import org.jooq.conf.ParamType;
import org.jooq.conf.Settings;
import org.jooq.conf.StatementType;
import org.jooq.impl.DSL;

import java.math.BigDecimal;

import static org.jooq.impl.DSL.avg;
import static org.jooq.impl.DSL.count;
import static org.jooq.impl.DSL.field;
import static org.jooq.impl.DSL.fieldByName;
import static org.jooq.impl.DSL.max;
import static org.jooq.impl.DSL.min;
import static org.jooq.impl.DSL.sum;
import static org.jooq.impl.DSL.tableByName;
import static org.jooq.impl.DSL.trueCondition;
import static org.jooq.impl.DSL.val;

@SuppressWarnings("unchecked")
public final class QueryBuilder {

    private static final String WILDCARD = "*";
    private static final String FACT_ALIAS = "facts";

    private static String schemaName;
    private static DSLContext create;

    private QueryBuilder() {

    }

    public static String getDwQueryAsSQLString(final SQLDialect sqlDialect,
                                               final String schemaName, final DwQuery dwQuery,
                                               final Boolean renderFormatted) {
        QueryBuilder.schemaName = schemaName;

        Settings settings = new Settings().withRenderFormatted(renderFormatted)
                .withStatementType(StatementType.STATIC_STATEMENT);
        QueryBuilder.create = DSL.using(sqlDialect, settings);

        return buildFromDwQuery(dwQuery).getSQL(ParamType.INLINED);
    }

    private static Select buildFromDwQuery(final DwQuery dwQuery) {
        try {
            SelectSelectStep selectSelectStep = QueryBuilder.create.select();
            buildSelectColumns(selectSelectStep, dwQuery);

            SelectHavingConditionStep selectHavingConditionStep = null;
            if (dwQuery.getGroupBy() != null) {
                selectHavingConditionStep = buildGroupBy(selectSelectStep, dwQuery.getGroupBy());
            }

            SelectConditionStep selectConditionStep = null;
            selectSelectStep = (selectHavingConditionStep != null)
                    ? (SelectSelectStep) selectHavingConditionStep
                    : selectSelectStep;
            Select select = null;
            if (dwQuery instanceof SimpleDwQuery) {
                selectConditionStep = buildFromSimpleDwQuery(selectSelectStep, (SimpleDwQuery) dwQuery);
            } else if (dwQuery instanceof ComplexDwQuery) {
                select = buildFromComplexDwQuery(selectSelectStep, (ComplexDwQuery) dwQuery);
            }

            if (select == null) {
                if (selectConditionStep != null) {
                    select = selectConditionStep;
                } else if (selectHavingConditionStep != null) {
                    select = selectHavingConditionStep;
                } else {
                    select = selectSelectStep;
                }
            }

            if (dwQuery.getCombineWith() != null) {
                select = buildCombineWith(select, dwQuery);
            }

            return select;
        } catch (Exception e) {
            throw new QueryBuilderException(e);
        }
    }

    private static SelectConditionStep buildFromSimpleDwQuery(SelectSelectStep selectSelectStep,
                                                              SimpleDwQuery simpleDwQuery) {
        SelectJoinStep selectJoinStep = selectSelectStep.from(tableByName(
                schemaName, simpleDwQuery.getTableName()));

        SelectConditionStep selectConditionStep = selectJoinStep.where();
        if (simpleDwQuery.getWhereConditionGroup() != null) {
            selectConditionStep = buildWhereConditionGroup(selectConditionStep,
                    simpleDwQuery.getWhereConditionGroup());
        }

        return selectConditionStep;
    }

    private static Select buildFromComplexDwQuery(SelectSelectStep selectSelectStep,
                                                  ComplexDwQuery complexDwQuery) {
        SelectJoinStep selectJoinStep = selectSelectStep.from(tableByName(
                schemaName, complexDwQuery.getDimension()));

        Select factSelect = null;
        for (Fact fact : complexDwQuery.getFacts()) {
            if (fact.getCombineType() == null) {
                factSelect = buildFromDwQuery(fact.getTable());
                continue;
            }

            factSelect = buildFact(factSelect, fact);
        }
        selectJoinStep = selectJoinStep.join(factSelect.asTable(FACT_ALIAS)).on(
                buildCondition(
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
            selectConditionStep = buildWhereConditionGroup(selectConditionStep,
                    complexDwQuery.getWhereConditionGroup());
        }

        return selectConditionStep;
    }
    
    private static SelectSelectStep buildSelectColumns(SelectSelectStep selectSelectStep, DwQuery dwQuery) {
        SelectSelectStep step = selectSelectStep;
        
        for (SelectColumn selectColumn : dwQuery.getSelectColumns()) {
            if (selectColumn.getFunction() != null) {
                AggregateFunction aggregateFunction = buildSelectColumnFunction(selectColumn);
                step = step.select(aggregateFunction);
            } else {
                if (selectColumn.getFieldName().equals(WILDCARD)) {
                    step = step.select(field(WILDCARD));
                } else {
                    step = step.select(fieldByName(schemaName,
                            selectColumn.getTableName(), selectColumn.getFieldName()));
                }
            }
        }
        
        return selectSelectStep;
    }

    private static Select buildCombineWith(Select select, DwQuery dwQuery) {
        try {
            SelectJoinStep selectJoinStep = (SelectJoinStep) select;
            DwQueryCombination combineWith = dwQuery.getCombineWith();

            switch (combineWith.getCombineType()) {
                case Join:
                    String referencedTableName = "";
                    if (dwQuery instanceof ComplexDwQuery) {
                        referencedTableName = ((ComplexDwQuery) dwQuery).getDimension();
                    } else if (dwQuery instanceof SimpleDwQuery) {
                        referencedTableName = ((SimpleDwQuery) dwQuery).getTableName();
                    }

                    return selectJoinStep.join(buildFromDwQuery(combineWith.getDwQuery())
                            .asTable(
                                    combineWith.getDwQuery().getTableName()
                            ))
                            .on(
                                    buildCondition(
                                            fieldByName(
                                                    combineWith.getDwQuery().getTableName(),
                                                    combineWith.getForeignKeyFieldName()
                                            ),
                                            OperatorType.Equal,
                                            fieldByName(
                                                    schemaName,
                                                    referencedTableName,
                                                    combineWith.getReferencedFieldName()
                                            )
                            ));
                case Union:
                    return selectJoinStep.union(buildFromDwQuery(combineWith.getDwQuery()));
                case UnionAll:
                    return selectJoinStep.unionAll(buildFromDwQuery(combineWith.getDwQuery()));
                case Intersect:
                    return selectJoinStep.intersect(buildFromDwQuery(combineWith.getDwQuery()));
                default:
                    throw new NotImplementedException();
            }
        } catch (Exception e) {
            throw new QueryBuilderException(e);
        }
    }

    private static SelectConditionStep buildWhereConditionGroup(SelectConditionStep selectConditionStep,
                                                                WhereConditionGroup group) {
        SelectConditionStep step = selectConditionStep;

        if (group.getConditionGroups() != null) {
            for (WhereConditionGroup whereConditionGroup : group.getConditionGroups()) {
                step = buildWhereConditionGroup(step, whereConditionGroup);
            }
        }

        if (group.getConditions() != null) {
            for (WhereCondition whereCondition : group.getConditions()) {
                step = step.and(buildWhereCondition(whereCondition));
            }
        }

        return step;
    }

    private static Condition buildWhereCondition(WhereCondition whereCondition) {
        Condition condition = null;
        Field field1 = fieldByName(
                whereCondition.getTable1Name(),
                whereCondition.getField1Name());

        if (whereCondition instanceof ValueComparison) {
            ValueComparison valueComparison = (ValueComparison) whereCondition;

            condition = buildCondition(field1, valueComparison.getOperator(), valueComparison.getValue());
        }

        return condition;
    }

    private static Select buildFact(Select select, Fact fact) {
        try {
            // TODO : Implement combination types
            switch (fact.getCombineType()) {
                case Union:
                    return select.union(buildFromDwQuery(fact.getTable()));
                case UnionAll:
                    return select.unionAll(buildFromDwQuery(fact.getTable()));
                default:
                    throw new NotImplementedException();
            }
        } catch (Exception e) {
            throw new QueryBuilderException(e);
        }
    }

    private static SelectHavingConditionStep buildGroupBy(SelectSelectStep selectSelectStep, GroupBy groupBy) {
        SelectHavingStep selectHavingStep = selectSelectStep.groupBy(
                fieldByName(schemaName, groupBy.getTableName(), groupBy.getFieldName()));
        if (groupBy.getHaving() != null) {
            HavingCondition havingCondition = groupBy.getHaving();

            return selectSelectStep.having(
                    buildCondition(havingCondition.getSelectColumn(), havingCondition.getOperator(),
                            havingCondition.getValue()));
        }

        return selectHavingStep.having();
    }

    private static AggregateFunction buildSelectColumnFunction(final SelectColumn selectColumn) {
        try {
            switch (selectColumn.getFunction()) {
                case Average:
                    return avg(fieldByName(BigDecimal.class, schemaName, selectColumn.getTableName(),
                            selectColumn.getFieldName()));
                case Count:
                    return count(fieldByName(schemaName, selectColumn.getTableName(),
                            selectColumn.getFieldName()));
                case Max:
                    return max(fieldByName(BigDecimal.class, schemaName, selectColumn.getTableName(),
                            selectColumn.getFieldName()));
                case Min:
                    return min(fieldByName(BigDecimal.class, schemaName, selectColumn.getTableName(),
                            selectColumn.getFieldName()));
                case Nvl:
                    // TODO : Implement NVL
                    throw new NotImplementedException();
                case Sum:
                    return sum(fieldByName(BigDecimal.class, schemaName, selectColumn.getTableName(),
                            selectColumn.getFieldName()));
                default:
                    throw new NotImplementedException();
            }
        } catch (Exception e) {
            throw new QueryBuilderException(e);
        }
    }

    private static Condition buildCondition(Field field, OperatorType operatorType, String value) {
        try {
            Param param = val(value);

            switch (operatorType) {
                case Less:
                    return field.lessThan(param);
                case LessEqual:
                    return field.lessOrEqual(param);
                case Equal:
                    return field.equal(param);
                case NotEqual:
                    return field.notEqual(param);
                case Greater:
                    return field.greaterThan(param);
                case GreaterEqual:
                    return field.greaterOrEqual(param);
                default:
                    throw new NotImplementedException();
            }
        } catch (Exception e) {
            throw new QueryBuilderException(e);
        }
    }

    private static Condition buildCondition(Field field, OperatorType operatorType, Field value) {
        try {
            switch (operatorType) {
                case Less:
                    return field.lessThan(value);
                case LessEqual:
                    return field.lessOrEqual(value);
                case Equal:
                    return field.equal(value);
                case NotEqual:
                    return field.notEqual(value);
                case Greater:
                    return field.greaterThan(value);
                case GreaterEqual:
                    return field.greaterOrEqual(value);
                default:
                    return null;
            }
        } catch (Exception e) {
            throw new QueryBuilderException(e);
        }
    }

    private static Condition buildCondition(SelectColumn selectColumn, OperatorType operatorType, String value) {
        try {
            Param param = val(value);

            if (selectColumn.getFunction() != null) {
                AggregateFunction aggregateFunction = buildSelectColumnFunction(selectColumn);

                switch (operatorType) {
                    case Less:
                        return aggregateFunction.lessThan(param);
                    case LessEqual:
                        return aggregateFunction.lessOrEqual(param);
                    case Equal:
                        return aggregateFunction.equal(param);
                    case NotEqual:
                        return aggregateFunction.notEqual(param);
                    case Greater:
                        return aggregateFunction.greaterThan(param);
                    case GreaterEqual:
                        return aggregateFunction.greaterOrEqual(param);
                    default:
                        return trueCondition();
                }
            } else {
                Field field = fieldByName(schemaName, selectColumn.getTableName(), selectColumn.getFieldName());
                return buildCondition(field, operatorType, value);
            }
        } catch (Exception e) {
            throw new QueryBuilderException(e);
        }
    }
}
