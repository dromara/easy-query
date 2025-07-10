package com.easy.query.sqlite.config;

import com.easy.query.core.bootstrapper.DatabaseConfiguration;
import com.easy.query.core.configuration.EasyQueryOptionBuilder;
import com.easy.query.core.configuration.dialect.SQLKeyword;
import com.easy.query.core.expression.sql.expression.factory.ExpressionFactory;
import com.easy.query.core.func.SQLFunc;
import com.easy.query.core.inject.ServiceCollection;
import com.easy.query.core.migration.DatabaseMigrationProvider;
import com.easy.query.core.migration.MigrationEntityParser;
import com.easy.query.sqlite.expression.SQLiteExpressionFactory;
import com.easy.query.sqlite.func.SQLiteFuncImpl;
import com.easy.query.sqlite.migration.SQLiteDatabaseMigrationProvider;
import com.easy.query.sqlite.migration.SQLiteMigrationEntityParser;

/**
 * create time 2023/5/10 13:40
 * 文件说明
 *
 * @author xuejiaming
 */
public class SQLiteDatabaseConfiguration implements DatabaseConfiguration {
    @Override
    public void configure(ServiceCollection services) {
        services.addService(SQLKeyword.class, SQLiteSQLKeyword.class);
        services.addService(ExpressionFactory.class, SQLiteExpressionFactory.class);
        services.addService(SQLFunc.class, SQLiteFuncImpl.class);
        services.addService(DatabaseMigrationProvider.class, SQLiteDatabaseMigrationProvider.class);
        services.addService(MigrationEntityParser.class, SQLiteMigrationEntityParser.class);
    }

    @Override
    public void optionConfigure(EasyQueryOptionBuilder easyQueryOptionBuilder) {
        if (easyQueryOptionBuilder.getMaxInClauseSize() == EasyQueryOptionBuilder.MAX_IN_CLAUSE_SIZE) {
            easyQueryOptionBuilder.setMaxInClauseSize(999);
        }
    }
}
