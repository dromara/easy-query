package com.easy.query.mysql.config;

import com.easy.query.core.bootstrapper.DatabaseConfiguration;
import com.easy.query.core.configuration.dialect.Dialect;
import com.easy.query.core.expression.sql.expression.factory.ExpressionFactory;
import com.easy.query.core.func.SQLFunc;
import com.easy.query.core.inject.ServiceCollection;
import com.easy.query.mysql.expression.MySQLExpressionFactory;
import com.easy.query.mysql.func.MySQLFuncImpl;

/**
 * create time 2023/5/10 13:40
 * 文件说明
 *
 * @author xuejiaming
 */
public class MySQLDatabaseConfiguration implements DatabaseConfiguration {
    @Override
    public void configure(ServiceCollection services) {
        services.addService(Dialect.class, MySQLDialect.class);
        services.addService(ExpressionFactory.class, MySQLExpressionFactory.class);
        services.addService(SQLFunc.class, MySQLFuncImpl.class);
    }
}
