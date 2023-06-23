package com.easy.query.api.proxy.sql.impl;

import com.easy.query.api.proxy.sql.ProxyAggregateFilter;
import com.easy.query.core.expression.builder.AggregateFilter;

/**
 * create time 2023/6/23 14:25
 * 文件说明
 *
 * @author xuejiaming
 */
public class ProxyAggregateFilterImpl implements ProxyAggregateFilter {
    private final AggregateFilter aggregateFilter;

    public ProxyAggregateFilterImpl(AggregateFilter aggregateFilter){

        this.aggregateFilter = aggregateFilter;
    }
    @Override
    public AggregateFilter getAggregateFilter() {
        return aggregateFilter;
    }
}
