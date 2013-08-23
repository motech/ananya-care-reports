package org.dwQueryBuilder.builders.query.helpers;

import org.apache.commons.lang.NotImplementedException;
import org.dwQueryBuilder.data.Fact;
import org.dwQueryBuilder.exceptions.QueryBuilderRuntimeException;
import org.jooq.DSLContext;
import org.jooq.Select;

public final class FactHelper {

    private FactHelper() {

    }

    public static Select buildFact(DSLContext dslContext, String schemaName, Select select, Fact fact) {
        try {
            // TODO : Implement combination types
            switch (fact.getCombineType()) {
                case Union:
                    return select.union(DwQueryHelper.buildFromDwQuery(dslContext, schemaName, fact.getTable()));
                case UnionAll:
                    return select.unionAll(DwQueryHelper.buildFromDwQuery(dslContext, schemaName, fact.getTable()));
                default:
                    throw new NotImplementedException();
            }
        } catch (Exception e) {
            throw new QueryBuilderRuntimeException(e);
        }
    }

}
