package com.easy.query.core.proxy.impl;

import com.easy.query.core.expression.builder.AggregateFilter;
import com.easy.query.core.expression.builder.Filter;
import com.easy.query.core.proxy.SQLAggregatePredicate;

import java.util.function.Consumer;

/**
 * create time 2023/12/3 09:07
 * 文件说明
 *
 * @author xuejiaming
 */
public class SQLAggregatePredicateImpl extends SQLPredicateImpl implements SQLAggregatePredicate {
    private final Consumer<AggregateFilter> aggregateFilterConsumer;

    public SQLAggregatePredicateImpl(Consumer<Filter> filterConsumer, Consumer<AggregateFilter> aggregateFilterConsumer) {
        super(filterConsumer);
        this.aggregateFilterConsumer = aggregateFilterConsumer;
    }

    @Override
    public SQLAggregatePredicate and(SQLAggregatePredicate predicate) {
        Consumer<Filter> f1 = f -> f.and(predicate::accept);
        Consumer<AggregateFilter> f2 = f -> f.and(predicate::accept);
        return new SQLAggregatePredicateImpl(x -> {
            filterConsumer.accept(x);
            f1.accept(x);
        }, x -> {
            aggregateFilterConsumer.accept(x);
            f2.accept(x);
        });
    }

    @Override
    public SQLAggregatePredicate or(SQLAggregatePredicate predicate) {
        Consumer<Filter> f1 = f -> f.or(predicate::accept);
        Consumer<AggregateFilter> f2 = f -> f.or(predicate::accept);
        return new SQLAggregatePredicateImpl(x -> {
            filterConsumer.accept(x);
            f1.accept(x);
        }, x -> {
            aggregateFilterConsumer.accept(x);
            f2.accept(x);
        });
    }

    @Override
    public void accept(AggregateFilter f) {
        aggregateFilterConsumer.accept(f);
    }
}
