package com.easy.query.dameng.config;

import com.easy.query.core.basic.api.database.DatabaseCodeFirst;
import com.easy.query.core.bootstrapper.DatabaseConfiguration;
import com.easy.query.core.configuration.dialect.SQLKeyword;
import com.easy.query.core.expression.sql.expression.factory.ExpressionFactory;
import com.easy.query.core.func.SQLFunc;
import com.easy.query.core.inject.ServiceCollection;
import com.easy.query.core.migration.DatabaseMigrationProvider;
import com.easy.query.core.migration.MigrationEntityParser;
import com.easy.query.dameng.expression.DamengExpressionFactory;
import com.easy.query.dameng.func.DamengSQLFuncImpl;
import com.easy.query.dameng.migration.DamengDatabaseCodeFirst;
import com.easy.query.dameng.migration.DamengDatabaseMigrationProvider;
import com.easy.query.dameng.migration.DamengMigrationEntityParser;

/**
 * create time 2023/7/27 10:31
 * 文件说明
 *
 * @author xuejiaming
 */
public class DamengDatabaseConfiguration implements DatabaseConfiguration {
    @Override
    public void configure(ServiceCollection services) {
        services.addService(SQLKeyword.class, DamengSQLKeyword.class);
        services.addService(ExpressionFactory.class, DamengExpressionFactory.class);
        services.addService(SQLFunc.class, DamengSQLFuncImpl.class);
        services.addService(DatabaseMigrationProvider.class, DamengDatabaseMigrationProvider.class);
        services.addService(MigrationEntityParser.class, DamengMigrationEntityParser.class);
        services.addService(DatabaseCodeFirst.class, DamengDatabaseCodeFirst.class);
    }
}
