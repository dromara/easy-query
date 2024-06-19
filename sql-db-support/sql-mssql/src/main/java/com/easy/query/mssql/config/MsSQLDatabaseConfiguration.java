package com.easy.query.mssql.config;

import com.easy.query.core.bootstrapper.DatabaseConfiguration;
import com.easy.query.core.configuration.dialect.SQLKeyword;
import com.easy.query.core.expression.sql.expression.factory.ExpressionFactory;
import com.easy.query.core.func.SQLFunc;
import com.easy.query.core.inject.ServiceCollection;
import com.easy.query.mssql.expression.MsSQLExpressionFactory;
import com.easy.query.mssql.func.MsSQLFuncImpl;

/**
 * create time 2023/5/10 13:40
 * 文件说明
 *
 * @author xuejiaming
 */
public class MsSQLDatabaseConfiguration implements DatabaseConfiguration {
    @Override
    public void configure(ServiceCollection services) {
        services.addService(SQLKeyword.class, MsSQLSQLKeyword.class);
        services.addService(ExpressionFactory.class, MsSQLExpressionFactory.class);
        services.addService(SQLFunc.class, MsSQLFuncImpl.class);
    }
}
