package com.easy.query.core.func.column.impl;

import com.easy.query.core.func.column.ColumnMultiValueExpression;

import java.util.Collection;

/**
 * create time 2024/6/12 22:30
 * 文件说明
 *
 * @author xuejiaming
 */
public class ColumnMultiValueExpressionImpl implements ColumnMultiValueExpression {
    private final Collection<?> values;

    public ColumnMultiValueExpressionImpl(Collection<?> values){

        this.values = values;
    }
    @Override
    public Collection<?> getValues() {
        return values;
    }
}
