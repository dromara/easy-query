package com.easy.query.core.proxy;

import com.easy.query.core.expression.builder.AggregateFilter;
import com.easy.query.core.proxy.impl.SQLAggregatePredicateImpl;

/**
 * create time 2023/12/3 00:39
 * 文件说明
 *
 * @author xuejiaming
 */
public interface SQLAggregatePredicate extends SQLPredicate {
    SQLAggregatePredicate and(SQLAggregatePredicate predicate);
    SQLAggregatePredicate or(SQLAggregatePredicate predicate);
    void accept(AggregateFilter f);

    SQLAggregatePredicate empty=new SQLAggregatePredicateImpl(f->{},f->{});
}
