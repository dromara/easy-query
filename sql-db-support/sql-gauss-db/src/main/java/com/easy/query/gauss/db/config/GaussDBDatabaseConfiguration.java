package com.easy.query.gauss.db.config;

import com.easy.query.core.bootstrapper.DatabaseConfiguration;
import com.easy.query.core.configuration.dialect.Dialect;
import com.easy.query.core.inject.ServiceCollection;

/**
 * create time 2024/1/19 22:36
 * 文件说明
 *
 * @author xuejiaming
 */
public class GaussDBDatabaseConfiguration implements DatabaseConfiguration {
    @Override
    public void configure(ServiceCollection services) {

        services.addService(Dialect.class, GaussDBDialect.class);
//        services.addService(ExpressionFactory.class, KingbaseESExpressionFactory.class);
//        services.addService(SQLFunc.class, KingbaseESSQLFuncImpl.class);
    }
}
