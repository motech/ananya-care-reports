package org.dwQueryBuilder.builders.query.helpers;

import org.apache.commons.lang.NotImplementedException;
import org.dwQueryBuilder.data.ComputedColumn;
import org.dwQueryBuilder.data.ComputedColumnOperation;
import org.dwQueryBuilder.data.DwQueryCombination;
import org.dwQueryBuilder.data.SelectColumn;
import org.dwQueryBuilder.data.DwQuery;
import org.dwQueryBuilder.exceptions.QueryBuilderRuntimeException;
import org.jooq.AggregateFunction;
import org.jooq.Field;
import org.jooq.SelectSelectStep;

import static org.jooq.impl.DSL.avg;
import static org.jooq.impl.DSL.count;
import static org.jooq.impl.DSL.field;
import static org.jooq.impl.DSL.fieldByName;
import static org.jooq.impl.DSL.function;
import static org.jooq.impl.DSL.max;
import static org.jooq.impl.DSL.min;
import static org.jooq.impl.DSL.stddevPop;
import static org.jooq.impl.DSL.sum;

@SuppressWarnings("unchecked")
public final class SelectColumnHelper {

    private static final String WILDCARD = "*";

    private SelectColumnHelper() {

    }

    public static SelectSelectStep buildSelectColumns(String schemaName, SelectSelectStep selectSelectStep,
                                                      DwQuery dwQuery) {
        SelectSelectStep step = selectSelectStep;

        for (SelectColumn selectColumn : dwQuery.getSelectColumns()) {
            step = step.select(resolveSelectColumn(schemaName, dwQuery, selectColumn, true, true));
        }

        return selectSelectStep;
    }

    public static Field resolveSelectColumn(String schemaName,
                                            DwQuery dwQuery,
                                            SelectColumn selectColumn,
                                            Boolean useSchemaName,
                                            Boolean useAlias) {
        Field field;

        if (selectColumn.getComputedColumn().hasOperations()
                && selectColumn.getComputedColumn().getFieldName().equals(WILDCARD)) {
            selectColumn.getComputedColumn().getOperations().clear();
        }

        if (selectColumn.getFunction() != null) {
            field = resolveAggregateFunction(schemaName, dwQuery, selectColumn, useSchemaName);
        } else {

            if (WILDCARD.equals(selectColumn.getComputedColumn().getFieldName())) {
                field = resolveWildcard(schemaName, selectColumn.getComputedColumn(), useSchemaName);
            } else {
                field = resolveComputedColumn(schemaName, dwQuery, selectColumn.getComputedColumn(), useSchemaName);
            }

            if (selectColumn.hasNullValue()) {
                field = field.nvl(selectColumn.getNullValue());
            }

        }

        if (selectColumn.getValueToLowerCase()) {
            field = field.lower();
        }

        if (useAlias && selectColumn.hasAlias()) {
            field = field.as(selectColumn.getAlias());
        }


        return field;
    }

    public static Field resolveWildcard(String schemaName,
                                        ComputedColumn computedColumn,
                                        Boolean useSchemaName) {
        if (computedColumn.getTableName() == null && computedColumn.getFieldName().equals(WILDCARD)) {
            return field(WILDCARD);
        } else if (computedColumn.getFieldName().equals(WILDCARD)) {
            if (useSchemaName) {
                return field("\"" + schemaName + "\".\"" + computedColumn.getTableName() + "\"." + WILDCARD);
            } else {
                return field("\"" + computedColumn.getTableName() + "\"." + WILDCARD);
            }
        }

        return null;
    }

    public static Field resolveAggregateFunction(String schemaName,
                                                 DwQuery dwQuery,
                                                 SelectColumn selectColumn,
                                                 Boolean useSchemaName) {
        Field aggregateFunction;

        if (selectColumn.getComputedColumn().getFieldName().equals(WILDCARD)) {
            aggregateFunction = buildSelectColumnFunctionForWildcard(schemaName, selectColumn, useSchemaName);
        } else {
            aggregateFunction = buildSelectColumnFunction(schemaName, dwQuery, selectColumn, useSchemaName);
        }

        return aggregateFunction;
    }

    public static Field resolveComputedColumn(String schemaName,
                                              DwQuery dwQuery,
                                              ComputedColumn computedColumn,
                                              Boolean useSchemaName) {
        Field field;
        Boolean useSchema = useSchemaName;

        if (computedColumn.getTableName() != null) {
            if (dwQuery.getCombineWith() != null) {
                for (DwQueryCombination combination : dwQuery.getCombineWith()) {
                    if (computedColumn.getTableName().equals(combination.getDwQuery().getTableName())) {
                        useSchema = false;
                        break;
                    }
                }
            }
        }

        if (useSchema) {
            field = fieldByName(schemaName, computedColumn.getTableName(), computedColumn.getFieldName());
        } else {
            field = fieldByName(computedColumn.getTableName(), computedColumn.getFieldName());
        }

        if (computedColumn.hasOperations()) {
            for (ComputedColumnOperation operation : computedColumn.getOperations()) {
                Field field2 = resolveComputedColumn(schemaName, dwQuery, operation.getComputedColumn(), useSchemaName);

                switch (operation.getOperatorType()) {
                    case Add:
                        field = field.add(field2);
                        break;
                    case Divide:
                        field = field.divide(field2);
                        break;
                    case Multiply:
                        field = field.multiply(field2);
                        break;
                    case Subtract:
                        field = field.subtract(field2);
                        break;
                    default:
                        break;
                }
            }
        }

        return field;
    }

    public static Field buildSelectColumnFunction(String schemaName,
                                                  DwQuery dwQuery,
                                                  SelectColumn selectColumn,
                                                  Boolean useSchemaName) {
        try {
            Field field = resolveComputedColumn(schemaName, dwQuery, selectColumn.getComputedColumn(), useSchemaName);
            if (selectColumn.hasNullValue()) {
                field = field.nvl(selectColumn.getNullValue());
            }

            switch (selectColumn.getFunction()) {
                case Average:
                    return avg(field);
                case Count:
                    return count(field);
                case Max:
                    return max(field);
                case Min:
                    return min(field);
                case Sum:
                    return sum(field);
                case Mode:
                    return function("mode", Integer.class, field);
                case Median:
                    return function("median", Integer.class, field);
                case StandardDeviation:
                    return stddevPop(field);
                default:
                    throw new NotImplementedException();
            }
        } catch (Exception e) {
            throw new QueryBuilderRuntimeException(e);
        }
    }

    public static AggregateFunction buildSelectColumnFunctionForWildcard(String schemaName,
                                                                         SelectColumn selectColumn,
                                                                         Boolean useSchemaName) {
        try {
            switch (selectColumn.getFunction()) {
                case Count:
                    return count(resolveWildcard(schemaName, selectColumn.getComputedColumn(), useSchemaName));
                default:
                    throw new NotImplementedException();
            }
        } catch (Exception e) {
            throw new QueryBuilderRuntimeException(e);
        }
    }

}
