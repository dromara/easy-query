package com.easy.query.core.expression.segment;

import com.easy.query.core.basic.jdbc.parameter.ToSQLContext;
import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.expression.sql.builder.ExpressionContext;

/**
 * create time 2024/2/28 06:29
 * 文件说明
 *
 * @author xuejiaming
 */
public interface SQLLazySegement {
    String toSQL(QueryRuntimeContext runtimeContext, ExpressionContext expressionContext, ToSQLContext toSQLContext);
}
