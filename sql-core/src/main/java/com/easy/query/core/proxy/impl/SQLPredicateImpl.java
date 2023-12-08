package com.easy.query.core.proxy.impl;

import com.easy.query.core.expression.builder.Filter;
import com.easy.query.core.proxy.SQLPredicateExpression;

import java.util.function.Consumer;

/**
 * create time 2023/7/12 21:48
 * 文件说明
 *
 * @author xuejiaming
 */
public class SQLPredicateImpl implements SQLPredicateExpression {


    protected final Consumer<Filter> filterConsumer;

    public SQLPredicateImpl(Consumer<Filter> filterConsumer){
        this.filterConsumer = filterConsumer;
    }

    @Override
    public void accept(Filter f) {
        filterConsumer.accept(f);
    }
}
