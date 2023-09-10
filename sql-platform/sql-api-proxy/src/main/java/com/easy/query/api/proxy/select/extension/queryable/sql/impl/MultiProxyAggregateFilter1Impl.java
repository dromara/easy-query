package com.easy.query.api.proxy.select.extension.queryable.sql.impl;

import com.easy.query.api.proxy.select.extension.queryable.sql.MultiProxyAggregateFilter1;
import com.easy.query.api.proxy.sql.ProxyAggregateFilter;
import com.easy.query.core.expression.builder.AggregateFilter;
import com.easy.query.core.expression.builder.core.SQLNative;
import com.easy.query.core.proxy.ProxyEntity;
import com.easy.query.core.util.EasyObjectUtil;

/**
 * create time 2023/9/10 10:47
 * 文件说明
 *
 * @author xuejiaming
 */
public class MultiProxyAggregateFilter1Impl<T1Proxy extends ProxyEntity<T1Proxy, T1>, T1> implements MultiProxyAggregateFilter1<T1Proxy> {

    private final AggregateFilter aggregateFilter;
    private final T1Proxy t;


    public MultiProxyAggregateFilter1Impl(AggregateFilter aggregateFilter, T1Proxy t) {
        this.aggregateFilter = aggregateFilter;
        this.t = t;
    }
    @Override
    public T1Proxy t() {
        return t;
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
