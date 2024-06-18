package com.easy.query.sqllite.config;

import com.easy.query.core.bootstrapper.DatabaseConfiguration;
import com.easy.query.core.configuration.dialect.SQLKeyword;
import com.easy.query.core.expression.sql.expression.factory.ExpressionFactory;
import com.easy.query.core.func.SQLFunc;
import com.easy.query.core.inject.ServiceCollection;
import com.easy.query.sqllite.expression.SQLiteExpressionFactory;
import com.easy.query.sqllite.func.SQLiteFuncImpl;

/**
 * create time 2023/5/10 13:40
 * 文件说明
 *
 * @author xuejiaming
 */
public class SQLLiteDatabaseConfiguration implements DatabaseConfiguration {
    @Override
    public void configure(ServiceCollection services) {
        services.addService(SQLKeyword.class, SQLLiteSQLKeyword.class);
        services.addService(ExpressionFactory.class, SQLiteExpressionFactory.class);
        services.addService(SQLFunc.class, SQLiteFuncImpl.class);
    }
}
