package com.easy.query.dameng.config;

import com.easy.query.core.bootstrapper.DatabaseConfiguration;
import com.easy.query.core.configuration.dialect.Dialect;
import com.easy.query.core.expression.sql.expression.factory.ExpressionFactory;
import com.easy.query.core.inject.ServiceCollection;
import com.easy.query.dameng.expression.DamengExpressionFactory;

/**
 * create time 2023/7/27 10:31
 * 文件说明
 *
 * @author xuejiaming
 */
public class DamengDatabaseConfiguration implements DatabaseConfiguration {
    @Override
    public void configure(ServiceCollection services) {
        services.addService(Dialect.class, DamengDialect.class);
        services.addService(ExpressionFactory.class, DamengExpressionFactory.class);
    }
}
