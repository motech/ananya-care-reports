package org.dwQueryBuilder.builders.query.helpers;

import org.apache.commons.lang.NotImplementedException;
import org.dwQueryBuilder.data.DwQuery;
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
import org.jooq.Condition;
import org.jooq.Field;
import org.jooq.Param;
import org.jooq.SelectConditionStep;
import org.jooq.types.DayToSecond;
import org.jooq.util.postgres.PostgresDataType;

import java.util.Iterator;
import java.util.LinkedHashSet;
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
                                                               DwQuery dwQuery,
                                                               SelectConditionStep selectConditionStep,
                                                               WhereConditionGroup group,
                                                               Boolean useSchemaName,
                                                               Boolean useAlias) {
        SelectConditionStep step = selectConditionStep;

        if (group != null) {
            step = step.and(getConditionsRecursive(schemaName, dwQuery, group, useSchemaName, useAlias));
        }

        return step;
    }

    public static Condition getConditionsRecursive(String schemaName,
                                                   DwQuery dwQuery,
                                                   WhereConditionGroup whereConditionGroup,
                                                   Boolean useSchemaName,
                                                   Boolean useAlias) {
        Condition condition = null;

        if (whereConditionGroup == null) {
            return falseCondition();
        }

        if (whereConditionGroup.getConditionGroups() != null) {
            for (WhereConditionGroup group : whereConditionGroup.getConditionGroups()) {
                Condition subCondition = getConditionsRecursive(schemaName, dwQuery, group, useSchemaName, useAlias);

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
                condition = buildWhereConditionAnd(schemaName,  dwQuery, whereCondition, condition, useSchemaName, useAlias);
            }
        }

        return condition;
    }

    public static Condition buildWhereConditionAnd(String schemaName,
                                                   DwQuery dwQuery,
                                                   WhereCondition whereCondition,
                                                   Condition condition,
                                                   Boolean useSchemaName,
                                                   Boolean useAlias) {
        Condition newCondition = condition;

        if (condition == null) {
            newCondition = buildWhereCondition(schemaName, dwQuery, whereCondition, useSchemaName, useAlias);
        } else {
            newCondition = newCondition.and(buildWhereCondition(schemaName, dwQuery, whereCondition, useSchemaName, useAlias));
        }

        return newCondition;
    }

    public static Condition buildWhereCondition(String schemaName,
                                                DwQuery dwQuery,
                                                WhereCondition whereCondition,
                                                Boolean useSchemaName,
                                                Boolean useAlias) {
        Condition condition = null;
        Field field1 = SelectColumnHelper.resolveSelectColumn(
                schemaName, dwQuery, whereCondition.getSelectColumn1(), useSchemaName, useAlias);

        if (whereCondition instanceof ValueComparison) {

            ValueComparison valueComparison = (ValueComparison) whereCondition;
            condition = buildCondition(field1, valueComparison.getOperator(), valueComparison.getValue(),
                    whereCondition.getSelectColumn1().getValueToLowerCase());

        } else if (whereCondition instanceof DateRangeComparison) {

            condition = buildDateRangeCondition(field1, (DateRangeComparison) whereCondition);

        } else if (whereCondition instanceof DateValueComparison) {

            condition = buildDateValueCondition(field1, (DateValueComparison) whereCondition);

        } else if (whereCondition instanceof DateDiffComparison) {

            DateDiffComparison dateDiffComparison = (DateDiffComparison) whereCondition;
            Field field2 = SelectColumnHelper.resolveSelectColumn(
                    schemaName, dwQuery, dateDiffComparison.getSelectColumn2(), useSchemaName, useAlias);
            condition = buildDateDiffCondition(field1.cast(PostgresDataType.TIMESTAMPWITHTIMEZONE),
                    dateDiffComparison.getOperator(), field2.cast(PostgresDataType.TIMESTAMPWITHTIMEZONE),
                    dateDiffComparison.getValue(), dateDiffComparison.getColumn1Offset(), dateDiffComparison.getColumn2Offset());

        } else if (whereCondition instanceof FieldComparison) {

            FieldComparison fieldComparison = (FieldComparison) whereCondition;
            Field field2 = SelectColumnHelper.resolveSelectColumn(
                    schemaName, dwQuery, fieldComparison.getSelectColumn2(), useSchemaName, useAlias);
            condition = buildCondition(
                    field1,
                    Integer.parseInt(fieldComparison.getColumn1Offset()),
                    fieldComparison.getOperator(),
                    field2,
                    Integer.parseInt(fieldComparison.getColumn2Offset()));

        } else if (whereCondition instanceof EnumRangeComparison) {

            EnumRangeComparison enumRangeComparison = (EnumRangeComparison) whereCondition;
            condition = buildEnumRangeCondition(field1, enumRangeComparison.getValues(),
                    whereCondition.getSelectColumn1().getValueToLowerCase());
        }

        return condition;
    }

    public static Condition buildCondition(Field field, ComparisonType comparisonType, String value, Boolean useLowerCase) {
        try {
            Param param = val((useLowerCase) ? value.toLowerCase() : value);

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
                                           DwQuery dwQuery,
                                           SelectColumn selectColumn,
                                           ComparisonType comparisonType,
                                           String value,
                                           Boolean useSchemaName) {
        try {
            Param param = val((selectColumn.getValueToLowerCase()) ? value.toLowerCase() : value);

            if (selectColumn.getFunction() != null) {
                Field aggregateFunction = SelectColumnHelper.resolveAggregateFunction(
                        schemaName, dwQuery, selectColumn, useSchemaName);

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
                Field field = SelectColumnHelper.resolveSelectColumn(schemaName, dwQuery, selectColumn, false, false);
                return buildCondition(field, comparisonType, value, selectColumn.getValueToLowerCase());
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
            Field newField;
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

    public static Condition buildEnumRangeCondition(Field field1, Set<String> values, Boolean useLowerCase) {
        try {
            Set<String> newValues = new LinkedHashSet<>(values);
            if (useLowerCase) {
                newValues.clear();
                Iterator<String> iterator = values.iterator();
                while (iterator.hasNext()) {
                    newValues.add(iterator.next().toLowerCase());
                }
            }

            return field1.in(newValues);
        } catch (Exception e) {
            throw new QueryBuilderRuntimeException(e);
        }
    }

}
