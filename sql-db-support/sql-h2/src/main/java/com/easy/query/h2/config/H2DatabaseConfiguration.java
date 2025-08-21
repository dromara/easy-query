package com.easy.query.h2.config;

import com.easy.query.core.basic.jdbc.types.EasyJdbcTypeHandlerManager;
import com.easy.query.core.basic.jdbc.types.JdbcTypeHandlerManager;
import com.easy.query.core.bootstrapper.DatabaseConfiguration;
import com.easy.query.core.configuration.dialect.SQLKeyword;
import com.easy.query.core.expression.sql.expression.factory.ExpressionFactory;
import com.easy.query.core.inject.ServiceCollection;
import com.easy.query.core.migration.DatabaseMigrationProvider;
import com.easy.query.core.migration.MigrationEntityParser;
import com.easy.query.h2.expression.H2ExpressionFactory;
import com.easy.query.h2.migration.H2DatabaseMigrationProvider;
import com.easy.query.h2.migration.H2MigrationEntityParser;
import com.easy.query.h2.types.UUIDH2SQLTypeHandler;

import java.util.UUID;

/**
 * create time 2023/5/10 13:40
 * 文件说明
 *
 * @author xuejiaming
 */
public class H2DatabaseConfiguration implements DatabaseConfiguration {
    @Override
    public void configure(ServiceCollection services) {
        services.addService(SQLKeyword.class, H2SQLKeyword.class);
        services.addService(ExpressionFactory.class, H2ExpressionFactory.class);
        services.addService(DatabaseMigrationProvider.class, H2DatabaseMigrationProvider.class);
        services.addService(MigrationEntityParser.class, H2MigrationEntityParser.class);
        services.addServiceFactory(JdbcTypeHandlerManager.class, s->{
            EasyJdbcTypeHandlerManager easyJdbcTypeHandlerManager = new EasyJdbcTypeHandlerManager();
            easyJdbcTypeHandlerManager.appendHandler(UUID.class, UUIDH2SQLTypeHandler.INSTANCE,true);
            return easyJdbcTypeHandlerManager;
        });
    }
}
