package com.easy.query.kingbase.es.config;

import com.easy.query.core.bootstrapper.DatabaseConfiguration;
import com.easy.query.core.configuration.dialect.SQLKeyword;
import com.easy.query.core.expression.sql.expression.factory.ExpressionFactory;
import com.easy.query.core.func.SQLFunc;
import com.easy.query.core.inject.ServiceCollection;
import com.easy.query.core.migration.DatabaseMigrationProvider;
import com.easy.query.core.migration.DefaultMigrationEntityParser;
import com.easy.query.core.migration.MigrationEntityParser;
import com.easy.query.kingbase.es.expression.KingbaseESExpressionFactory;
import com.easy.query.kingbase.es.func.KingbaseESSQLFuncImpl;
import com.easy.query.kingbase.es.migration.KingbaseESDatabaseMigrationProvider;
import com.easy.query.kingbase.es.migration.KingbaseESMigrationEntityParser;

/**
 * create time 2023/7/28 21:11
 * 文件说明
 *
 * @author xuejiaming
 */
public class KingbaseESDatabaseConfiguration implements DatabaseConfiguration {
    @Override
    public void configure(ServiceCollection services) {

        services.addService(SQLKeyword.class, KingbaseESSQLKeyword.class);
        services.addService(ExpressionFactory.class, KingbaseESExpressionFactory.class);
        services.addService(SQLFunc.class, KingbaseESSQLFuncImpl.class);
        services.addService(DatabaseMigrationProvider.class, KingbaseESDatabaseMigrationProvider.class);
        services.addService(MigrationEntityParser.class, KingbaseESMigrationEntityParser.class);
    }
}
