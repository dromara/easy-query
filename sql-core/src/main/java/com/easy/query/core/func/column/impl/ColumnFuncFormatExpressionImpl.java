package com.easy.query.core.func.column.impl;

import com.easy.query.core.func.column.ColumnFuncFormatExpression;

/**
 * create time 2023/10/12 14:19
 * 文件说明
 *
 * @author xuejiaming
 */
public class ColumnFuncFormatExpressionImpl implements ColumnFuncFormatExpression {
    private final Object format;

    public ColumnFuncFormatExpressionImpl(Object format){

        this.format = format;
    }
    @Override
    public Object getFormat() {
        return format;
    }
}
