package com.easy.query.mysql;

import com.easy.query.core.abstraction.EasyQueryRuntimeContext;
import com.easy.query.core.expression.sql.expression.EasyAnonymousQuerySqlExpression;
import com.easy.query.core.expression.sql.expression.EasyDeleteSqlExpression;
import com.easy.query.core.expression.sql.expression.EasyInsertSqlExpression;
import com.easy.query.core.expression.sql.expression.EasyQuerySqlExpression;
import com.easy.query.core.expression.sql.expression.EasyTableSqlExpression;
import com.easy.query.core.expression.sql.expression.EasyUpdateSqlExpression;
import com.easy.query.core.expression.sql.expression.impl.AnonymousQuerySqlExpression;
import com.easy.query.core.expression.sql.factory.EasyExpressionFactory;
import com.easy.query.mysql.expression.MySqlDeleteSqlExpression;
import com.easy.query.mysql.expression.MySqlInsertSqlExpression;
import com.easy.query.mysql.expression.MySqlQuerySqlExpression;
import com.easy.query.mysql.expression.MySqlUpdateSqlExpression;

/**
 * create time 2023/4/23 13:14
 * 文件说明
 *
 * @author xuejiaming
 */
public class MySqlExpressionFactory implements EasyExpressionFactory {
    @Override
    public EasyQuerySqlExpression createEasyQuerySqlExpression(EasyQueryRuntimeContext runtimeContext) {
        return new MySqlQuerySqlExpression(runtimeContext);
    }

    @Override
    public EasyInsertSqlExpression createEasyInsertSqlExpression(EasyQueryRuntimeContext runtimeContext, EasyTableSqlExpression tableSqlExpression) {
        return new MySqlInsertSqlExpression(runtimeContext,tableSqlExpression);
    }

    @Override
    public EasyUpdateSqlExpression createEasyUpdateSqlExpression(EasyQueryRuntimeContext runtimeContext, EasyTableSqlExpression tableSqlExpression) {
        return new MySqlUpdateSqlExpression(runtimeContext,tableSqlExpression);
    }

    @Override
    public EasyDeleteSqlExpression createEasyDeleteSqlExpression(EasyQueryRuntimeContext runtimeContext, EasyTableSqlExpression tableSqlExpression) {
        return new MySqlDeleteSqlExpression(runtimeContext,tableSqlExpression);
    }

    @Override
    public EasyAnonymousQuerySqlExpression createEasyAnonymousQuerySqlExpression(EasyQueryRuntimeContext runtimeContext, String sql) {
        return new AnonymousQuerySqlExpression(runtimeContext,sql);
    }
}
