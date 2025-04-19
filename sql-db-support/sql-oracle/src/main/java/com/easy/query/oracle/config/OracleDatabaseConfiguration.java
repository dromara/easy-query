package com.easy.query.oracle.config;

import com.easy.query.core.basic.api.database.DatabaseCodeFirst;
import com.easy.query.core.bootstrapper.DatabaseConfiguration;
import com.easy.query.core.common.SubQueryToGroupJoinTrueFalseProvider;
import com.easy.query.core.configuration.dialect.SQLKeyword;
import com.easy.query.core.expression.sql.expression.factory.ExpressionFactory;
import com.easy.query.core.func.SQLFunc;
import com.easy.query.core.inject.ServiceCollection;
import com.easy.query.core.migration.DatabaseMigrationProvider;
import com.easy.query.oracle.expression.OracleExpressionFactory;
import com.easy.query.oracle.func.OracleSQLFuncImpl;
import com.easy.query.oracle.migration.OracleDatabaseCodeFirst;
import com.easy.query.oracle.migration.OracleDatabaseMigrationProvider;

/**
 * create time 2023/8/15 17:17
 * 文件说明
 *
 * @author xuejiaming
 */
public class OracleDatabaseConfiguration  implements DatabaseConfiguration {
    @Override
    public void configure(ServiceCollection services) {
        services.addService(SQLKeyword.class, OracleSQLKeyword.class);
        services.addService(ExpressionFactory.class, OracleExpressionFactory.class);
        services.addService(SQLFunc.class, OracleSQLFuncImpl.class);
        services.addService(DatabaseMigrationProvider.class, OracleDatabaseMigrationProvider.class);
        services.addService(DatabaseCodeFirst.class, OracleDatabaseCodeFirst.class);
        services.addService(SubQueryToGroupJoinTrueFalseProvider.class, OracleSubQueryToGroupJoinTrueFalseProvider.class);
    }
}
