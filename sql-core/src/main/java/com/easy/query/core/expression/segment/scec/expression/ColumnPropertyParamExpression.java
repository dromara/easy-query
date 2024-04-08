package com.easy.query.core.expression.segment.scec.expression;

import com.easy.query.core.basic.jdbc.parameter.ToSQLContext;
import com.easy.query.core.expression.sql.builder.ExpressionContext;

/**
 * create time 2023/7/29 19:42
 * 文件说明
 *
 * @author xuejiaming
 */
public interface ColumnPropertyParamExpression extends ParamExpression {
    String toSQL(ExpressionContext expressionContext, ToSQLContext toSQLContext);
}
