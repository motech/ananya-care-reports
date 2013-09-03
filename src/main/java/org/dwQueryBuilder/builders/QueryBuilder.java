package org.dwQueryBuilder.builders;

import org.dwQueryBuilder.builders.query.helpers.DwQueryHelper;
import org.dwQueryBuilder.data.DwQuery;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.Select;
import org.jooq.conf.ParamType;
import org.jooq.conf.Settings;
import org.jooq.conf.StatementType;
import org.jooq.impl.DSL;

import javax.sql.DataSource;

@SuppressWarnings("unchecked")
public final class QueryBuilder {

    private QueryBuilder() {

    }

    public static Select getSelectForQuery(final DataSource dataSource,
                                                   final SQLDialect sqlDialect,
                                                   final String schemaName, final DwQuery dwQuery,
                                                   final Boolean renderFormatted) {
        Settings settings = new Settings().withRenderFormatted(renderFormatted)
                .withStatementType(StatementType.PREPARED_STATEMENT);
        DSLContext dslContext = DSL.using(dataSource, sqlDialect, settings);

        return DwQueryHelper.buildFromDwQuery(dslContext, schemaName, dwQuery);
    }

    public static String getDwQueryAsSQLString(final SQLDialect sqlDialect,
                                               final String schemaName, final DwQuery dwQuery,
                                               final Boolean renderFormatted) {
        Settings settings = new Settings().withRenderFormatted(renderFormatted)
                .withStatementType(StatementType.STATIC_STATEMENT);
        DSLContext dslContext = DSL.using(sqlDialect, settings);

        return DwQueryHelper.buildFromDwQuery(dslContext, schemaName, dwQuery).getSQL(ParamType.INLINED);
    }
}
