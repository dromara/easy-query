package com.easy.query.core.expression.segment.scec.expression;

import com.easy.query.core.basic.jdbc.parameter.ToSQLContext;
import com.easy.query.core.context.QueryRuntimeContext;

/**
 * create time 2023/7/29 19:42
 * 文件说明
 *
 * @author xuejiaming
 */
public interface ColumnPropertyExpression extends ConstParamExpression{
    String toSQL(QueryRuntimeContext runtimeContext, ToSQLContext toSQLContext);
}
