package com.easy.query.core.proxy.core.accpet;

import com.easy.query.core.expression.builder.AggregateFilter;

/**
 * create time 2023/12/8 22:17
 * 文件说明
 *
 * @author xuejiaming
 */
public interface AggregatePredicateEntityExpressionAccept extends EntityExpressionAccept {
    AggregateFilter getAggregateFilter();
    void nextOr(boolean or);
}
