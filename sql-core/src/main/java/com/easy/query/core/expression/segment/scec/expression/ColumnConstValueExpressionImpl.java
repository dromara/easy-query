package com.easy.query.core.expression.segment.scec.expression;

import com.easy.query.core.basic.jdbc.parameter.EasyConstSQLParameter;
import com.easy.query.core.basic.jdbc.parameter.ToSQLContext;
import com.easy.query.core.util.EasySQLUtil;

/**
 * create time 2023/7/29 19:45
 * 文件说明
 *
 * @author xuejiaming
 */
public class ColumnConstValueExpressionImpl implements ColumnConstValueExpression {
    private final Object val;

    public ColumnConstValueExpressionImpl(Object val){

        this.val = val;
    }
    @Override
    public void addParams(ToSQLContext toSQLContext) {
        EasySQLUtil.addParameter(toSQLContext,new EasyConstSQLParameter(null,null,val));
    }
}
