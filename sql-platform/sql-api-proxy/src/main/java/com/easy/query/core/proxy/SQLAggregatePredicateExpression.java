package com.easy.query.core.proxy;

import com.easy.query.core.expression.builder.AggregateFilter;

/**
 * create time 2023/12/3 00:39
 * 文件说明
 *
 * @author xuejiaming
 */
public interface SQLAggregatePredicateExpression extends SQLPredicateExpression {
    void accept(AggregateFilter f);
}
