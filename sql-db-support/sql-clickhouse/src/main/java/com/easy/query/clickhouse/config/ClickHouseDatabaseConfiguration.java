package com.easy.query.clickhouse.config;

import com.easy.query.clickhouse.expression.ClickHouseExpressionFactory;
import com.easy.query.clickhouse.func.ClickHouseFuncImpl;
import com.easy.query.clickhouse.migration.ClickHouseDatabaseMigrationProvider;
import com.easy.query.clickhouse.migration.ClickHouseMigrationEntityParser;
import com.easy.query.core.bootstrapper.DatabaseConfiguration;
import com.easy.query.core.configuration.dialect.SQLKeyword;
import com.easy.query.core.expression.sql.expression.factory.ExpressionFactory;
import com.easy.query.core.func.SQLFunc;
import com.easy.query.core.inject.ServiceCollection;
import com.easy.query.core.migration.DatabaseMigrationProvider;
import com.easy.query.core.migration.MigrationEntityParser;

/**
 * create time 2023/5/10 13:40
 * 文件说明
 *
 * @author xuejiaming
 */
public class ClickHouseDatabaseConfiguration implements DatabaseConfiguration {
    @Override
    public void configure(ServiceCollection services) {
        services.addService(SQLKeyword.class, ClickHouseSQLKeyword.class);
        services.addService(ExpressionFactory.class, ClickHouseExpressionFactory.class);
        services.addService(SQLFunc.class, ClickHouseFuncImpl.class);
        services.addService(DatabaseMigrationProvider.class, ClickHouseDatabaseMigrationProvider.class);
        services.addService(MigrationEntityParser.class, ClickHouseMigrationEntityParser.class);
    }
}
