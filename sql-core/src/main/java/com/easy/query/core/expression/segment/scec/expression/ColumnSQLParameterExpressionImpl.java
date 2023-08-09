package com.easy.query.core.expression.segment.scec.expression;

import com.easy.query.core.basic.jdbc.parameter.SQLParameter;
import com.easy.query.core.basic.jdbc.parameter.ToSQLContext;
import com.easy.query.core.util.EasySQLUtil;

/**
 * create time 2023/8/4 22:42
 * 文件说明
 *
 * @author xuejiaming
 */
public class ColumnSQLParameterExpressionImpl implements ColumnParamExpression {
    private final SQLParameter sqlParameter;

    public ColumnSQLParameterExpressionImpl(SQLParameter sqlParameter){

        this.sqlParameter = sqlParameter;
    }
    @Override
    public void addParams(ToSQLContext toSQLContext) {
        EasySQLUtil.addParameter(toSQLContext,sqlParameter);
    }
}
