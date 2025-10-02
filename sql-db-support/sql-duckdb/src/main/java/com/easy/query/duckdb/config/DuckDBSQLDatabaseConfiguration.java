package com.easy.query.duckdb.config;

import com.easy.query.core.bootstrapper.DatabaseConfiguration;
import com.easy.query.core.configuration.dialect.SQLKeyword;
import com.easy.query.core.expression.sql.expression.factory.ExpressionFactory;
import com.easy.query.core.func.SQLFunc;
import com.easy.query.core.inject.ServiceCollection;
import com.easy.query.core.migration.DatabaseMigrationProvider;
import com.easy.query.core.migration.MigrationEntityParser;
import com.easy.query.duckdb.expression.DuckDBSQLExpressionFactory;
import com.easy.query.duckdb.func.DuckDBSQLFuncImpl;
import com.easy.query.duckdb.migration.DuckDBSQLDatabaseMigrationProvider;
import com.easy.query.duckdb.migration.DuckDBSQLMigrationEntityParser;

import java.util.UUID;

/**
 * create time 2023/5/10 13:40
 * 文件说明
 *
 * @author xuejiaming
 */
public class DuckDBSQLDatabaseConfiguration implements DatabaseConfiguration {
    @Override
    public void configure(ServiceCollection services) {
        services.addService(SQLKeyword.class, DuckDBSQLKeyword.class);
        services.addService(ExpressionFactory.class, DuckDBSQLExpressionFactory.class);
        services.addService(SQLFunc.class, DuckDBSQLFuncImpl.class);
        services.addService(DatabaseMigrationProvider.class, DuckDBSQLDatabaseMigrationProvider.class);
        services.addService(MigrationEntityParser.class, DuckDBSQLMigrationEntityParser.class);
    }
}
