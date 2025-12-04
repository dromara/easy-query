package com.easy.query.tsdb.config;

import com.easy.query.core.bootstrapper.DatabaseConfiguration;
import com.easy.query.core.configuration.dialect.SQLKeyword;
import com.easy.query.core.expression.sql.expression.factory.ExpressionFactory;
import com.easy.query.core.func.SQLFunc;
import com.easy.query.core.inject.ServiceCollection;
import com.easy.query.core.migration.DatabaseMigrationProvider;
import com.easy.query.core.migration.MigrationEntityParser;
import com.easy.query.tsdb.expression.TSDBExpressionFactory;
import com.easy.query.tsdb.func.TSDBFuncImpl;
import com.easy.query.tsdb.migration.TSDBDatabaseMigrationProvider;
import com.easy.query.tsdb.migration.TSDBMigrationEntityParser;

import java.util.UUID;

/**
 * create time 2023/5/10 13:40
 * 文件说明
 *
 * @author xuejiaming
 */
public class TSDBDatabaseConfiguration implements DatabaseConfiguration {
    @Override
    public void configure(ServiceCollection services) {
        services.addService(SQLKeyword.class, TSDBSQLKeyword.class);
        services.addService(ExpressionFactory.class, TSDBExpressionFactory.class);
        services.addService(SQLFunc.class, TSDBFuncImpl.class);
        services.addService(DatabaseMigrationProvider.class, TSDBDatabaseMigrationProvider.class);
        services.addService(MigrationEntityParser.class, TSDBMigrationEntityParser.class);
    }
}
