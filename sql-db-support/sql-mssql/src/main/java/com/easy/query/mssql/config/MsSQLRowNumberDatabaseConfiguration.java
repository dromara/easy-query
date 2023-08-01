package com.easy.query.mssql.config;

import com.easy.query.core.expression.sql.expression.factory.ExpressionFactory;
import com.easy.query.core.inject.ServiceCollection;
import com.easy.query.mssql.expression.MsSQLRowNumberExpressionFactory;

/**
 * create time 2023/8/1 09:24
 * 文件说明
 *
 * @author xuejiaming
 */
public class MsSQLRowNumberDatabaseConfiguration extends MsSQLDatabaseConfiguration{
    @Override
    public void configure(ServiceCollection services) {
        super.configure(services);
        services.addService(ExpressionFactory.class, MsSQLRowNumberExpressionFactory.class);
    }
}
