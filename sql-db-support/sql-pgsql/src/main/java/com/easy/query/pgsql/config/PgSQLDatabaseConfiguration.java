package com.easy.query.pgsql.config;

import com.easy.query.core.bootstrapper.DatabaseConfiguration;
import com.easy.query.core.configuration.dialect.SQLKeyword;
import com.easy.query.core.expression.sql.expression.factory.ExpressionFactory;
import com.easy.query.core.func.SQLFunc;
import com.easy.query.core.inject.ServiceCollection;
import com.easy.query.pgsql.expression.PostgresSQLExpressionFactory;
import com.easy.query.pgsql.func.PgSQLFuncImpl;

/**
 * create time 2023/5/10 13:40
 * 文件说明
 *
 * @author xuejiaming
 */
public class PgSQLDatabaseConfiguration implements DatabaseConfiguration {
    @Override
    public void configure(ServiceCollection services) {
        services.addService(SQLKeyword.class, PgSQLSQLKeyword.class);
        services.addService(ExpressionFactory.class, PostgresSQLExpressionFactory.class);
        services.addService(SQLFunc.class, PgSQLFuncImpl.class);
    }
}
