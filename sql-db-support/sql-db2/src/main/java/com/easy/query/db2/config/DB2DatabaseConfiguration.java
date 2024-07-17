package com.easy.query.db2.config;

import com.easy.query.core.bootstrapper.DatabaseConfiguration;
import com.easy.query.core.configuration.dialect.SQLKeyword;
import com.easy.query.core.expression.sql.expression.factory.ExpressionFactory;
import com.easy.query.core.func.SQLFunc;
import com.easy.query.core.inject.ServiceCollection;
import com.easy.query.db2.expression.DB2ExpressionFactory;
import com.easy.query.db2.func.DB2FuncImpl;

/**
 * create time 2023/5/10 13:40
 * 文件说明
 *
 * @author xuejiaming
 */
public class DB2DatabaseConfiguration implements DatabaseConfiguration {
    @Override
    public void configure(ServiceCollection services) {
        services.addService(SQLKeyword.class, DB2SQLKeyword.class);
        services.addService(ExpressionFactory.class, DB2ExpressionFactory.class);
        services.addService(SQLFunc.class, DB2FuncImpl.class);
    }
}
