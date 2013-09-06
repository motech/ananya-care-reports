package org.dwQueryBuilder.builders.query.helpers;

import org.apache.commons.lang.NotImplementedException;
import org.dwQueryBuilder.data.DwQueryCombination;
import org.dwQueryBuilder.data.enums.ComparisonType;
import org.dwQueryBuilder.exceptions.QueryBuilderRuntimeException;
import org.jooq.DSLContext;
import org.jooq.Select;
import org.jooq.SelectJoinStep;

import static org.jooq.impl.DSL.fieldByName;

@SuppressWarnings("unchecked")
public final class CombinationHelper {

    private CombinationHelper() {

    }

    public static Select buildCombineWith(DSLContext dslContext,
                                          String schemaName,
                                          Select select,
                                          DwQueryCombination dwQueryCombination,
                                          String referencedTableName) {
        try {
            SelectJoinStep selectJoinStep = (SelectJoinStep) select;

            switch (dwQueryCombination.getCombineType()) {
                case Join:
                    String tableName = dwQueryCombination.getDwQuery().getTableName();
                    return selectJoinStep.join(DwQueryHelper.buildFromDwQuery(
                            dslContext, schemaName, dwQueryCombination.getDwQuery())
                            .asTable(tableName))
                            .on(
                                    ConditionHelper.buildCondition(
                                            fieldByName(
                                                    tableName,
                                                    dwQueryCombination.getForeignKeyFieldName()
                                            ),
                                            0,
                                            ComparisonType.Equal,
                                            fieldByName(
                                                    schemaName,
                                                    referencedTableName,
                                                    dwQueryCombination.getReferencedFieldName()
                                            ),
                                            0
                                    ));
                case Union:
                    return select.union(DwQueryHelper.buildFromDwQuery(
                            dslContext, schemaName, dwQueryCombination.getDwQuery()));
                case UnionAll:
                    return select.unionAll(DwQueryHelper.buildFromDwQuery(
                            dslContext, schemaName, dwQueryCombination.getDwQuery()));
                case Intersect:
                    return select.intersect(DwQueryHelper.buildFromDwQuery(
                            dslContext, schemaName, dwQueryCombination.getDwQuery()));
                case Except:
                    return select.except(DwQueryHelper.buildFromDwQuery(
                            dslContext, schemaName, dwQueryCombination.getDwQuery()));
                default:
                    throw new NotImplementedException();
            }
        } catch (Exception e) {
            throw new QueryBuilderRuntimeException(e);
        }
    }

}
