package com.easy.query.core.expression.segment.scec.expression;

import com.easy.query.core.basic.jdbc.parameter.ToSQLContext;

/**
 * create time 2023/8/16 21:50
 * 文件说明
 *
 * @author xuejiaming
 */
public interface SubQueryParamExpression extends ParamExpression{
    String toSQL(ToSQLContext toSQLContext);
}
