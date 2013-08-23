package org.dwQueryBuilder.builders.query.helpers;

import org.apache.commons.lang.NotImplementedException;
import org.dwQueryBuilder.data.SelectColumn;
import org.dwQueryBuilder.data.queries.DwQuery;
import org.dwQueryBuilder.exceptions.QueryBuilderRuntimeException;
import org.jooq.AggregateFunction;
import org.jooq.Field;
import org.jooq.SelectSelectStep;

import static org.jooq.impl.DSL.avg;
import static org.jooq.impl.DSL.count;
import static org.jooq.impl.DSL.field;
import static org.jooq.impl.DSL.fieldByName;
import static org.jooq.impl.DSL.max;
import static org.jooq.impl.DSL.min;
import static org.jooq.impl.DSL.sum;

public final class SelectColumnHelper {

    private static final String WILDCARD = "*";

    private SelectColumnHelper() {

    }

    public static SelectSelectStep buildSelectColumns(String schemaName, SelectSelectStep selectSelectStep,
                                                      DwQuery dwQuery) {
        SelectSelectStep step = selectSelectStep;

        for (SelectColumn selectColumn : dwQuery.getSelectColumns()) {
            if (selectColumn.getFunction() != null) {

                AggregateFunction aggregateFunction;
                if (selectColumn.getFieldName().equals(WILDCARD)) {
                    aggregateFunction = buildSelectColumnFunctionForWildcard(selectColumn);
                } else {
                    aggregateFunction = buildSelectColumnFunction(schemaName, selectColumn);
                }
                step = step.select(aggregateFunction);

            } else {

                if (selectColumn.getFieldName().equals(WILDCARD)) {
                    if (selectColumn.getTableName() == null) {
                        step = step.select(field(WILDCARD));
                    } else {
                        step = step.select(field("\"" + selectColumn.getTableName() + "\"." + WILDCARD));
                    }
                } else {
                    Field field = fieldByName(schemaName, selectColumn.getTableName(), selectColumn.getFieldName());
                    if (selectColumn.hasNvl()) {
                        field = field.nvl(selectColumn.getNullValue());
                    }

                    step = step.select(field);
                }

            }
        }

        return selectSelectStep;
    }

    public static AggregateFunction buildSelectColumnFunction(String schemaName, SelectColumn selectColumn) {
        try {
            Field field = fieldByName(schemaName, selectColumn.getTableName(), selectColumn.getFieldName());
            if (selectColumn.hasNvl()) {
                field = field.nvl(selectColumn.getNullValue());
            }

            switch (selectColumn.getFunction()) {
                case Average:
                    return avg(field);
                case Count:
                    if (selectColumn.getFieldName().equals(WILDCARD)) {
                        return count(field(
                                "\"" + schemaName + "\".\"" + selectColumn.getTableName()
                                        + "\"." + WILDCARD));
                    } else {
                        return count(field);
                    }
                case Max:
                    return max(field);
                case Min:
                    return min(field);
                case Nvl:
                    // TODO : Implement NVL
                    throw new NotImplementedException();
                case Sum:
                    return sum(field);
                default:
                    throw new NotImplementedException();
            }
        } catch (Exception e) {
            throw new QueryBuilderRuntimeException(e);
        }
    }

    public static AggregateFunction buildSelectColumnFunctionForWildcard(SelectColumn selectColumn) {
        try {
            switch (selectColumn.getFunction()) {
                case Count:
                    return count(field(WILDCARD));
                default:
                    throw new NotImplementedException();
            }
        } catch (Exception e) {
            throw new QueryBuilderRuntimeException(e);
        }
    }

}
