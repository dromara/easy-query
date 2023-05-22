package com.easy.query.h2.config;

import com.easy.query.core.bootstrapper.DatabaseConfiguration;
import com.easy.query.core.configuration.dialect.DefaultDialect;
import com.easy.query.core.configuration.dialect.Dialect;
import com.easy.query.core.expression.sql.expression.factory.ExpressionFactory;
import com.easy.query.core.inject.ServiceCollection;
import com.easy.query.h2.expression.H2ExpressionFactory;

/**
 * create time 2023/5/10 13:40
 * 文件说明
 *
 * @author xuejiaming
 */
public class H2DatabaseConfiguration implements DatabaseConfiguration {
    @Override
    public void configure(ServiceCollection services) {
        services.addService(Dialect.class, DefaultDialect.class);
        services.addService(ExpressionFactory.class, H2ExpressionFactory.class);
    }
}
