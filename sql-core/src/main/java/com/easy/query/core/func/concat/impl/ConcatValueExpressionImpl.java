package com.easy.query.core.func.concat.impl;

import com.easy.query.core.func.concat.ConcatValueExpression;

/**
 * create time 2023/10/12 14:19
 * 文件说明
 *
 * @author xuejiaming
 */
public class ConcatValueExpressionImpl implements ConcatValueExpression {
    private final Object val;

    public ConcatValueExpressionImpl(Object val){

        this.val = val;
    }
    @Override
    public Object getValue() {
        return val;
    }
}
