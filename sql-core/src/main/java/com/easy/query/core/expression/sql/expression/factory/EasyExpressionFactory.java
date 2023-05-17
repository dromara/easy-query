package com.easy.query.core.expression.sql.expression.factory;

import com.easy.query.core.abstraction.EasyQueryRuntimeContext;
import com.easy.query.core.enums.ExecuteMethodEnum;
import com.easy.query.core.enums.SqlUnionEnum;
import com.easy.query.core.expression.sql.builder.ExpressionContext;
import com.easy.query.core.expression.sql.expression.EasyAnonymousQuerySqlExpression;
import com.easy.query.core.expression.sql.expression.EasyDeleteSqlExpression;
import com.easy.query.core.expression.sql.expression.EasyInsertSqlExpression;
import com.easy.query.core.expression.sql.expression.EasyQuerySqlExpression;
import com.easy.query.core.expression.sql.expression.EasyTableSqlExpression;
import com.easy.query.core.expression.sql.expression.EasyUpdateSqlExpression;

import java.util.List;

/**
 * create time 2023/4/23 13:08
 * 文件说明
 *
 * @author xuejiaming
 */
public interface EasyExpressionFactory {
    EasyQuerySqlExpression createEasyQuerySqlExpression(EasyQueryRuntimeContext runtimeContext);
    EasyInsertSqlExpression createEasyInsertSqlExpression(EasyQueryRuntimeContext runtimeContext, EasyTableSqlExpression tableSqlExpression);
    EasyUpdateSqlExpression createEasyUpdateSqlExpression(EasyQueryRuntimeContext runtimeContext, EasyTableSqlExpression tableSqlExpression);
    EasyDeleteSqlExpression createEasyDeleteSqlExpression(EasyQueryRuntimeContext runtimeContext, EasyTableSqlExpression tableSqlExpression);
    EasyAnonymousQuerySqlExpression createEasyAnonymousQuerySqlExpression(EasyQueryRuntimeContext runtimeContext, String sql);
    EasyAnonymousQuerySqlExpression createEasyAnonymousUnionQuerySqlExpression(EasyQueryRuntimeContext runtimeContext, List<EasyQuerySqlExpression> easyQuerySqlExpressions, SqlUnionEnum sqlUnion);
}
