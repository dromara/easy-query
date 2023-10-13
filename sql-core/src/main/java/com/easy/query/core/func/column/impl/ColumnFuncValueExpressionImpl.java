package com.easy.query.core.func.column.impl;

import com.easy.query.core.func.column.ColumnFuncValueExpression;

/**
 * create time 2023/10/12 14:19
 * 文件说明
 *
 * @author xuejiaming
 */
public class ColumnFuncValueExpressionImpl implements ColumnFuncValueExpression {
    private final Object val;

    public ColumnFuncValueExpressionImpl(Object val){

        this.val = val;
    }
    @Override
    public Object getValue() {
        return val;
    }
}
