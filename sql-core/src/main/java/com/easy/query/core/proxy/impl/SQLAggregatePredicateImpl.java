package com.easy.query.core.proxy.impl;

import com.easy.query.core.expression.builder.AggregateFilter;
import com.easy.query.core.expression.builder.Filter;
import com.easy.query.core.proxy.SQLAggregatePredicateExpression;

import java.util.function.Consumer;

/**
 * create time 2023/12/3 09:07
 * 文件说明
 *
 * @author xuejiaming
 */
public class SQLAggregatePredicateImpl extends SQLPredicateImpl implements SQLAggregatePredicateExpression {
    private final Consumer<AggregateFilter> aggregateFilterConsumer;

    public SQLAggregatePredicateImpl(Consumer<Filter> filterConsumer, Consumer<AggregateFilter> aggregateFilterConsumer) {
        super(filterConsumer);
        this.aggregateFilterConsumer = aggregateFilterConsumer;
    }

    @Override
    public void accept(AggregateFilter f) {
        aggregateFilterConsumer.accept(f);
    }
}
