package com.easy.query.core.expression.sql.expression.factory;

import com.easy.query.core.abstraction.EasyQueryRuntimeContext;
import com.easy.query.core.enums.ExecuteMethodEnum;
import com.easy.query.core.expression.sql.builder.ExpressionContext;
import com.easy.query.core.expression.sql.expression.EasyAnonymousQuerySqlExpression;
import com.easy.query.core.expression.sql.expression.EasyDeleteSqlExpression;
import com.easy.query.core.expression.sql.expression.EasyInsertSqlExpression;
import com.easy.query.core.expression.sql.expression.EasyQuerySqlExpression;
import com.easy.query.core.expression.sql.expression.EasyTableSqlExpression;
import com.easy.query.core.expression.sql.expression.EasyUpdateSqlExpression;
import com.easy.query.core.expression.sql.expression.impl.AnonymousQuerySqlExpression;
import com.easy.query.core.expression.sql.expression.impl.DeleteSqlExpression;
import com.easy.query.core.expression.sql.expression.impl.InsertSqlExpression;
import com.easy.query.core.expression.sql.expression.impl.QuerySqlExpression;
import com.easy.query.core.expression.sql.expression.impl.UpdateSqlExpression;

/**
 * create time 2023/4/26 15:46
 * 文件说明
 *
 * @author xuejiaming
 */
public class DefaultEasyExpressionFactory implements EasyExpressionFactory{
    @Override
    public EasyQuerySqlExpression createEasyQuerySqlExpression(EasyQueryRuntimeContext runtimeContext) {
        return new QuerySqlExpression(runtimeContext);
    }

    @Override
    public EasyInsertSqlExpression createEasyInsertSqlExpression(EasyQueryRuntimeContext runtimeContext, EasyTableSqlExpression tableSqlExpression) {
        return new InsertSqlExpression(runtimeContext,tableSqlExpression);
    }

    @Override
    public EasyUpdateSqlExpression createEasyUpdateSqlExpression(EasyQueryRuntimeContext runtimeContext, EasyTableSqlExpression tableSqlExpression) {
        return new UpdateSqlExpression(runtimeContext,tableSqlExpression);
    }

    @Override
    public EasyDeleteSqlExpression createEasyDeleteSqlExpression(EasyQueryRuntimeContext runtimeContext, EasyTableSqlExpression tableSqlExpression) {
        return new DeleteSqlExpression(runtimeContext,tableSqlExpression);
    }

    @Override
    public EasyAnonymousQuerySqlExpression createEasyAnonymousQuerySqlExpression(EasyQueryRuntimeContext runtimeContext, String sql) {
        return new AnonymousQuerySqlExpression(runtimeContext,sql);
    }
}
