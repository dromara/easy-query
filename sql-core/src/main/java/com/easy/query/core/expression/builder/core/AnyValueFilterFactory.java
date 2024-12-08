package com.easy.query.core.expression.builder.core;

/**
 * create time 2024/12/8 18:40
 * 文件说明
 *
 * @author xuejiaming
 */
public class AnyValueFilterFactory implements ValueFilterFactory {
    @Override
    public ValueFilter getExpressionDefaultValueFilter() {
        return AnyValueFilter.DEFAULT;
    }
}
