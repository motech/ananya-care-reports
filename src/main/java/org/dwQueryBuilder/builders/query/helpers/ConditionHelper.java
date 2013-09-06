package org.dwQueryBuilder.builders.query.helpers;

import org.apache.commons.lang.NotImplementedException;
import org.dwQueryBuilder.data.SelectColumn;
import org.dwQueryBuilder.data.conditions.where.DateDiffComparison;
import org.dwQueryBuilder.data.conditions.where.DateRangeComparison;
import org.dwQueryBuilder.data.conditions.where.DateValueComparison;
import org.dwQueryBuilder.data.conditions.where.EnumRangeComparison;
import org.dwQueryBuilder.data.conditions.where.FieldComparison;
import org.dwQueryBuilder.data.conditions.where.ValueComparison;
import org.dwQueryBuilder.data.conditions.where.WhereCondition;
import org.dwQueryBuilder.data.conditions.where.WhereConditionGroup;
import org.dwQueryBuilder.data.enums.ComparisonType;
import org.dwQueryBuilder.data.enums.WhereConditionJoinType;
import org.dwQueryBuilder.exceptions.QueryBuilderRuntimeException;
import org.jooq.AggregateFunction;
import org.jooq.Condition;
import org.jooq.Field;
import org.jooq.Param;
import org.jooq.SelectConditionStep;
import org.jooq.types.DayToSecond;

import java.util.Set;

import static org.jooq.impl.DSL.dateDiff;
import static org.jooq.impl.DSL.falseCondition;
import static org.jooq.impl.DSL.trueCondition;
import static org.jooq.impl.DSL.val;

@SuppressWarnings("unchecked")
public final class ConditionHelper {

    private ConditionHelper() {

    }

    public static SelectConditionStep buildWhereConditionGroup(String schemaName,
                                                               SelectConditionStep selectConditionStep,
                                                               WhereConditionGroup group,
                                                               Boolean useSchemaName,
                                                               Boolean useAlias) {
        SelectConditionStep step = selectConditionStep;

        if (group != null) {
            step = step.and(getConditionsRecursive(schemaName, group, useSchemaName, useAlias));
        }

        return step;
    }

    public static Condition getConditionsRecursive(String schemaName,
                                                   WhereConditionGroup whereConditionGroup,
                                                   Boolean useSchemaName,
                                                   Boolean useAlias) {
        Condition condition = null;

        if (whereConditionGroup == null) {
            return falseCondition();
        }

        if (whereConditionGroup.getConditionGroups() != null) {
            for (WhereConditionGroup group : whereConditionGroup.getConditionGroups()) {
                Condition subCondition = getConditionsRecursive(schemaName, group, useSchemaName, useAlias);

                if (condition == null) {
                    condition = subCondition;
                } else {
                    if (group.getJoinType() == null || group.getJoinType().equals(WhereConditionJoinType.AND)) {
                        condition = condition.and(subCondition);
                    } else if (group.getJoinType().equals(WhereConditionJoinType.OR)) {
                        condition = condition.or(subCondition);
                    }
                }
            }
        }

        if (whereConditionGroup.getConditions() != null) {
            for (WhereCondition whereCondition : whereConditionGroup.getConditions()) {
                condition = buildWhereConditionAnd(schemaName, whereCondition, condition, useSchemaName, useAlias);
            }
        }

        return condition;
    }

    public static Condition buildWhereConditionAnd(String schemaName,
                                                   WhereCondition whereCondition,
                                                   Condition condition,
                                                   Boolean useSchemaName,
                                                   Boolean useAlias) {
        Condition newCondition = condition;

        if (condition == null) {
            newCondition = buildWhereCondition(schemaName, whereCondition, useSchemaName, useAlias);
        } else {
            newCondition = newCondition.and(buildWhereCondition(schemaName, whereCondition, useSchemaName, useAlias));
        }

        return newCondition;
    }

    public static Condition buildWhereCondition(String schemaName,
                                                WhereCondition whereCondition,
                                                Boolean useSchemaName,
                                                Boolean useAlias) {
        Condition condition = null;
        Field field1 = SelectColumnHelper.resolveSelectColumn(
                schemaName, whereCondition.getSelectColumn1(), useSchemaName, useAlias);

        if (whereCondition instanceof ValueComparison) {

            ValueComparison valueComparison = (ValueComparison) whereCondition;
            condition = buildCondition(field1, valueComparison.getOperator(), valueComparison.getValue());

        } else if (whereCondition instanceof DateRangeComparison) {

            condition = buildDateRangeCondition(field1, (DateRangeComparison) whereCondition);

        } else if (whereCondition instanceof DateValueComparison) {

            condition = buildDateValueCondition(field1, (DateValueComparison) whereCondition);

        } else if (whereCondition instanceof DateDiffComparison) {

            DateDiffComparison dateDiffComparison = (DateDiffComparison) whereCondition;
            Field field2 = SelectColumnHelper.resolveSelectColumn(
                    schemaName, dateDiffComparison.getSelectColumn2(), useSchemaName, useAlias);
            condition = buildDateDiffCondition(field1, dateDiffComparison.getOperator(),
                    field2, dateDiffComparison.getValue(), dateDiffComparison.getColumn1Offset(),
                    dateDiffComparison.getColumn2Offset());

        } else if (whereCondition instanceof FieldComparison) {

            FieldComparison fieldComparison = (FieldComparison) whereCondition;
            Field field2 = SelectColumnHelper.resolveSelectColumn(
                    schemaName, fieldComparison.getSelectColumn2(), useSchemaName, useAlias);
            condition = buildCondition(
                    field1,
                    Integer.parseInt(fieldComparison.getColumn1Offset()),
                    fieldComparison.getOperator(),
                    field2,
                    Integer.parseInt(fieldComparison.getColumn2Offset()));

        } else if (whereCondition instanceof EnumRangeComparison) {

            EnumRangeComparison enumRangeComparison = (EnumRangeComparison) whereCondition;
            condition = buildEnumRangeCondition(field1, enumRangeComparison.getValues());
        }

        return condition;
    }

    public static Condition buildCondition(Field field, ComparisonType comparisonType, String value) {
        try {
            Param param = val(value);

            switch (comparisonType) {
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
            throw new QueryBuilderRuntimeException(e);
        }
    }

    public static Condition buildCondition(Field field, Integer field1Offset, ComparisonType comparisonType,
                                           Field value, Integer field2Offset) {
        Field field1 = resolveOffsetForField(field, field1Offset);
        Field field2 = resolveOffsetForField(value, field2Offset);

        try {
            switch (comparisonType) {
                case Less:
                    return field1.lessThan(field2);
                case LessEqual:
                    return field1.lessOrEqual(field2);
                case Equal:
                    return field1.equal(field2);
                case NotEqual:
                    return field1.notEqual(field2);
                case Greater:
                    return field1.greaterThan(field2);
                case GreaterEqual:
                    return field1.greaterOrEqual(field2);
                default:
                    return null;
            }
        } catch (Exception e) {
            throw new QueryBuilderRuntimeException(e);
        }
    }

    private static Field resolveOffsetForField(Field field, Integer fieldOffset) {
        if (fieldOffset > 0) {
            return field.add(fieldOffset);
        } else if (fieldOffset < 0) {
            return field.sub(Math.abs(fieldOffset));
        }

        return field;
    }

    public static Condition buildCondition(String schemaName,
                                           SelectColumn selectColumn,
                                           ComparisonType comparisonType,
                                           String value,
                                           Boolean useSchemaName) {
        try {
            Param param = val(value);

            if (selectColumn.getFunction() != null) {
                AggregateFunction aggregateFunction = SelectColumnHelper.resolveAggregateFunction(
                        schemaName, selectColumn, useSchemaName);

                switch (comparisonType) {
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
                Field field = SelectColumnHelper.resolveSelectColumn(schemaName, selectColumn, false, false);
                return buildCondition(field, comparisonType, value);
            }
        } catch (Exception e) {
            throw new QueryBuilderRuntimeException(e);
        }
    }

    public static Condition buildDateDiffCondition(Field date1, ComparisonType comparisonType,
                                                    Field date2, String value,
                                                    String date1Offset, String date2Offset) {
        try {
            Param param = val(value);
            Param offset1 = val(date1Offset);
            Param offset2 = val(date2Offset);

            switch (comparisonType) {
                case Less:
                    return dateDiff(date1.add(offset1), date2.add(offset2)).lessThan(param);
                case LessEqual:
                    return dateDiff(date1.add(offset1), date2.add(offset2)).lessOrEqual(param);
                case Equal:
                    return dateDiff(date1.add(offset1), date2.add(offset2)).equal(param);
                case NotEqual:
                    return dateDiff(date1.add(offset1), date2.add(offset2)).notEqual(param);
                case Greater:
                    return dateDiff(date1.add(offset1), date2.add(offset2)).greaterThan(param);
                case GreaterEqual:
                    return dateDiff(date1.add(offset1), date2.add(offset2)).greaterOrEqual(param);
                default:
                    return null;
            }
        } catch (Exception e) {
            throw new QueryBuilderRuntimeException(e);
        }
    }

    public static Condition buildDateValueCondition(Field field, DateValueComparison comparison) {
        try {
            DayToSecond dayToSecond = new DayToSecond(Math.abs(comparison.getOffset()));
            Field newField = field;
            String value = comparison.getValue();

            if (comparison.getOffset() >= 0) {
                newField = field.add(dayToSecond);
            } else {
                newField = field.sub(dayToSecond);
            }

            switch (comparison.getOperator()) {
                case Less:
                    return newField.lessThan(value);
                case LessEqual:
                    return newField.lessOrEqual(value);
                case Equal:
                    return newField.equal(value);
                case NotEqual:
                    return newField.notEqual(value);
                case Greater:
                    return newField.greaterThan(value);
                case GreaterEqual:
                    return newField.greaterOrEqual(value);
                default:
                    return null;
            }
        } catch (Exception e) {
            throw new QueryBuilderRuntimeException(e);
        }
    }

    public static Condition buildDateRangeCondition(Field field, DateRangeComparison whereCondition) {
        try {
            DayToSecond dayToSecond = new DayToSecond(whereCondition.getColumn1Offset());

            Param date1 = val(whereCondition.getDate1());
            Param date2 = val(whereCondition.getDate2());

            return field.add(dayToSecond).greaterOrEqual(date1).and(field.add(dayToSecond).lessThan(date2));

        } catch (Exception e) {
            throw new QueryBuilderRuntimeException(e);
        }
    }

    public static Condition buildEnumRangeCondition(Field field1, Set<String> values) {
        try {
            return field1.in(values);
        } catch (Exception e) {
            throw new QueryBuilderRuntimeException(e);
        }
    }

}
