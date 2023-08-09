package com.easy.query.core.expression.segment.scec.expression;

import com.easy.query.core.basic.jdbc.parameter.ToSQLContext;

/**
 * create time 2023/8/4 22:06
 * 文件说明
 *
 * @author xuejiaming
 */
public interface ColumnFormatExpression extends ParamExpression {
    void addParams(ToSQLContext toSQLContext);
}
