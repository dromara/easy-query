package com.easy.query.core.expression.segment.scec.expression;

import com.easy.query.core.context.QueryRuntimeContext;

/**
 * create time 2023/9/12 19:05
 * 文件说明
 *
 * @author xuejiaming
 */
public interface ColumnPropertyAsAliasParamExpression extends ParamExpression{
    String toSQL(QueryRuntimeContext runtimeContext);
}
