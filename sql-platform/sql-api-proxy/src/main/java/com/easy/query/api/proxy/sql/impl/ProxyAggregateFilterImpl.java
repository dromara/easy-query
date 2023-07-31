package com.easy.query.api.proxy.sql.impl;

import com.easy.query.api.proxy.sql.ProxyAggregateFilter;
import com.easy.query.core.expression.builder.AggregateFilter;
import com.easy.query.core.expression.builder.core.SQLNative;
import com.easy.query.core.util.EasyObjectUtil;

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

    @Override
    public <T> SQLNative<T> getSQLNative() {
        return EasyObjectUtil.typeCastNullable(aggregateFilter);
    }

    @Override
    public ProxyAggregateFilter castTChain() {
        return this;
    }
}
