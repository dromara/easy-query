package com.easy.query.oracle.config;

import com.easy.query.core.bootstrapper.DatabaseConfiguration;
import com.easy.query.core.configuration.dialect.Dialect;
import com.easy.query.core.expression.sql.expression.factory.ExpressionFactory;
import com.easy.query.core.func.SQLFunc;
import com.easy.query.core.inject.ServiceCollection;
import com.easy.query.oracle.expression.OracleExpressionFactory;
import com.easy.query.oracle.func.OracleSQLFuncImpl;

/**
 * create time 2023/8/15 17:17
 * 文件说明
 *
 * @author xuejiaming
 */
public class OracleDatabaseConfiguration  implements DatabaseConfiguration {
    @Override
    public void configure(ServiceCollection services) {
        services.addService(Dialect.class, OracleDialect.class);
        services.addService(ExpressionFactory.class, OracleExpressionFactory.class);
        services.addService(SQLFunc.class, OracleSQLFuncImpl.class);
    }
}
