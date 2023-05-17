package com.easy.query.mysql.config;

import com.easy.query.core.bootstrapper.DatabaseConfiguration;
import com.easy.query.core.inject.ServiceCollection;
import com.easy.query.core.configuration.dialect.Dialect;

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
    }
}
