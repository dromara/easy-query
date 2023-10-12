package com.easy.query.core.func.concat.impl;

import com.easy.query.core.func.concat.ConcatFormatExpression;

/**
 * create time 2023/10/12 14:19
 * 文件说明
 *
 * @author xuejiaming
 */
public class ConcatFormatExpressionImpl implements ConcatFormatExpression {
    private final Object format;

    public ConcatFormatExpressionImpl(Object format){

        this.format = format;
    }
    @Override
    public Object getFormat() {
        return format;
    }
}
