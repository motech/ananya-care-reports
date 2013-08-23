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
import org.dwQueryBuilder.data.enums.OperatorType;
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
import static org.jooq.impl.DSL.fieldByName;
import static org.jooq.impl.DSL.trueCondition;
import static org.jooq.impl.DSL.val;

public final class ConditionHelper {

    private ConditionHelper() {

    }

    public static SelectConditionStep buildWhereConditionGroup(SelectConditionStep selectConditionStep,
                                                                WhereConditionGroup group) {
        SelectConditionStep step = selectConditionStep;

        if (group != null) {
            step = step.and(getConditionsRecursive(group));
        }

        return step;
    }

    public static Condition getConditionsRecursive(WhereConditionGroup whereConditionGroup) {
        Condition condition = null;

        if (whereConditionGroup == null) {
            return falseCondition();
        }

        if (whereConditionGroup.getConditionGroups() != null) {
            for (WhereConditionGroup group : whereConditionGroup.getConditionGroups()) {
                Condition subCondition = getConditionsRecursive(group);

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
                condition = buildWhereConditionAnd(whereCondition, condition);
            }
        }

        return condition;
    }

    public static Condition buildWhereConditionAnd(WhereCondition whereCondition, Condition condition) {
        Condition newCondition = condition;

        if (condition == null) {
            newCondition = buildWhereCondition(whereCondition);
        } else {
            newCondition = newCondition.and(buildWhereCondition(whereCondition));
        }

        return newCondition;
    }

    public static Condition buildWhereCondition(WhereCondition whereCondition) {
        Condition condition = null;
        Field field1 = fieldByName(
                whereCondition.getTable1Name(),
                whereCondition.getField1Name());

        if (whereCondition instanceof ValueComparison) {

            ValueComparison valueComparison = (ValueComparison) whereCondition;
            condition = buildCondition(field1, valueComparison.getOperator(), valueComparison.getValue());

        } else if (whereCondition instanceof DateRangeComparison) {

            condition = buildDateRangeCondition((DateRangeComparison) whereCondition);

        } else if (whereCondition instanceof DateValueComparison) {

            condition = buildDateValueCondition((DateValueComparison) whereCondition);

        } else if (whereCondition instanceof DateDiffComparison) {

            DateDiffComparison dateDiffComparison = (DateDiffComparison) whereCondition;
            Field field2 = fieldByName(dateDiffComparison.getTable2Name(), dateDiffComparison.getField2Name());
            condition = buildDateDiffCondition(field1, dateDiffComparison.getOperator(),
                    field2, dateDiffComparison.getValue(), dateDiffComparison.getField1Offset(),
                    dateDiffComparison.getField2Offset());

        } else if (whereCondition instanceof FieldComparison) {

            FieldComparison fieldComparison = (FieldComparison) whereCondition;
            Field field2 = fieldByName(fieldComparison.getTable2Name(), fieldComparison.getField2Name());
            condition = buildCondition(field1, fieldComparison.getOperator(), field2);

        } else if (whereCondition instanceof EnumRangeComparison) {

            EnumRangeComparison enumRangeComparison = (EnumRangeComparison) whereCondition;
            condition = buildEnumRangeCondition(field1, enumRangeComparison.getValues());
        }

        return condition;
    }

    public static Condition buildCondition(Field field, OperatorType operatorType, String value) {
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
            throw new QueryBuilderRuntimeException(e);
        }
    }

    public static Condition buildCondition(Field field, OperatorType operatorType, Field value) {
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
            throw new QueryBuilderRuntimeException(e);
        }
    }

    public static Condition buildCondition(String schemaName,
                                           SelectColumn selectColumn,
                                           OperatorType operatorType,
                                           String value) {
        try {
            Param param = val(value);

            if (selectColumn.getFunction() != null) {
                AggregateFunction aggregateFunction = SelectColumnHelper.buildSelectColumnFunction(
                        schemaName, selectColumn);

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
            throw new QueryBuilderRuntimeException(e);
        }
    }

    public static Condition buildDateDiffCondition(Field date1, OperatorType operatorType,
                                                    Field date2, String value,
                                                    String date1Offset, String date2Offset) {
        try {
            Param param = val(value);
            Param offset1 = val(date1Offset);
            Param offset2 = val(date2Offset);

            switch (operatorType) {
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

    public static Condition buildDateValueCondition(DateValueComparison comparison) {
        try {
            DayToSecond dayToSecond = new DayToSecond(Math.abs(comparison.getOffset()));

            Field field = fieldByName(comparison.getTable1Name(), comparison.getField1Name());
            String value = comparison.getValue();

            if (comparison.getOffset() >= 0) {
                field = field.add(dayToSecond);
            } else {
                field = field.sub(dayToSecond);
            }

            switch (comparison.getOperator()) {
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
            throw new QueryBuilderRuntimeException(e);
        }
    }

    public static Condition buildDateRangeCondition(DateRangeComparison whereCondition) {
        try {
            DayToSecond dayToSecond = new DayToSecond(whereCondition.getField1Offset());

            Field field = fieldByName(whereCondition.getTable1Name(), whereCondition.getField1Name());
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
