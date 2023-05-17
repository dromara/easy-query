package com.easy.query.pgsql;

import com.easy.query.core.bootstrapper.DatabaseConfiguration;
import com.easy.query.core.inject.ServiceCollection;
import com.easy.query.core.configuration.dialect.Dialect;

/**
 * create time 2023/5/10 13:40
 * 文件说明
 *
 * @author xuejiaming
 */
public class PgSQLDatabaseConfiguration implements DatabaseConfiguration {
    @Override
    public void configure(ServiceCollection services) {
        services.addService(Dialect.class, PgSQLDialect.class);
    }
}
