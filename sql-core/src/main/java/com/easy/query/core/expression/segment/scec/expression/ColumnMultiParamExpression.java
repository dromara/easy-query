package com.easy.query.core.expression.segment.scec.expression;

import com.easy.query.core.basic.jdbc.parameter.ToSQLContext;

/**
 * create time 2023/10/13 08:13
 * 文件说明
 *
 * @author xuejiaming
 */
public interface ColumnMultiParamExpression extends ParamExpression {
    int getParamSize();
    void addParams(ToSQLContext toSQLContext);
}

