package com.easy.query.gauss.db.config;

import com.easy.query.core.bootstrapper.DatabaseConfiguration;
import com.easy.query.core.configuration.dialect.SQLKeyword;
import com.easy.query.core.expression.sql.expression.factory.ExpressionFactory;
import com.easy.query.core.func.SQLFunc;
import com.easy.query.core.inject.ServiceCollection;
import com.easy.query.core.migration.DatabaseMigrationProvider;
import com.easy.query.core.migration.MigrationEntityParser;
import com.easy.query.gauss.db.expression.GaussDBExpressionFactory;
import com.easy.query.gauss.db.func.GaussDBFuncImpl;
import com.easy.query.gauss.db.migration.GaussDBDatabaseMigrationProvider;
import com.easy.query.gauss.db.migration.GaussDBMigrationEntityParser;

/**
 * create time 2024/1/19 22:36
 * 华为高斯数据库
 *
 * @author xuejiaming
 */
public class GaussDBDatabaseConfiguration implements DatabaseConfiguration {
    @Override
    public void configure(ServiceCollection services) {

        services.addService(SQLKeyword.class, GaussDBSQLKeyword.class);
        services.addService(ExpressionFactory.class, GaussDBExpressionFactory.class);
        services.addService(SQLFunc.class, GaussDBFuncImpl.class);
        services.addService(DatabaseMigrationProvider.class, GaussDBDatabaseMigrationProvider.class);
        services.addService(MigrationEntityParser.class, GaussDBMigrationEntityParser.class);
    }
}
