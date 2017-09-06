package com.example.jooq;

import org.jooq.ExecuteContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DefaultExecuteListener;
import org.springframework.jdbc.support.SQLErrorCodeSQLExceptionTranslator;
import org.springframework.jdbc.support.SQLExceptionTranslator;
import org.springframework.jdbc.support.SQLStateSQLExceptionTranslator;

public class ExceptionTranslator extends DefaultExecuteListener {

    private static final long serialVersionUID = 1L;

    @Override
    public void exception(ExecuteContext ctx) {
        // Translate only SQLExceptions
        if (ctx.sqlException() != null) {
            SQLDialect dialect = ctx.dialect();
            SQLExceptionTranslator translator = (dialect != null)
                    ? new SQLErrorCodeSQLExceptionTranslator(dialect.thirdParty().springDbName())
                    : new SQLStateSQLExceptionTranslator();
            ctx.exception(translator.translate("jOOQ", ctx.sql(), ctx.sqlException()));
        }
    }
}
