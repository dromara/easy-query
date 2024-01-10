package com.easy.query.core.proxy.impl;

import com.easy.query.core.expression.builder.AggregateFilter;
import com.easy.query.core.expression.builder.Filter;
import com.easy.query.core.expression.builder.core.SQLNativeAble;
import com.easy.query.core.proxy.SQLAggregatePredicateExpression;
import com.easy.query.core.proxy.SQLPredicateExpression;

import java.util.function.Consumer;

/**
 * create time 2023/12/3 09:07
 * 文件说明
 *
 * @author xuejiaming
 */
public class SQLAggregateNativeSQLPredicateImpl implements SQLPredicateExpression,SQLAggregatePredicateExpression {

    private final Consumer<SQLNativeAble> sqlNativeAbleConsumer;

    public SQLAggregateNativeSQLPredicateImpl(Consumer<SQLNativeAble> sqlNativeAbleConsumer) {
        this.sqlNativeAbleConsumer = sqlNativeAbleConsumer;
    }

    @Override
    public void accept(AggregateFilter f) {
        sqlNativeAbleConsumer.accept(f);
    }

    @Override
    public void accept(Filter f) {
        sqlNativeAbleConsumer.accept(f);
    }
}
