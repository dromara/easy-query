package com.easy.query.core.expression.segment.scec.expression;

import com.easy.query.core.basic.jdbc.parameter.ToSQLContext;

/**
 * create time 2023/7/29 19:04
 * 文件说明
 *
 * @author xuejiaming
 */
public interface ColumnConstValueExpression extends ConstParamExpression {
    void addParams(ToSQLContext toSQLContext);
}
