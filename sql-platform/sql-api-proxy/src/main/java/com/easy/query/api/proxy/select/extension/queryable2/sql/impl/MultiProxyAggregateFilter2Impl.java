package com.easy.query.api.proxy.select.extension.queryable2.sql.impl;

import com.easy.query.api.proxy.select.extension.queryable2.sql.MultiProxyAggregateFilter2;
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
public class MultiProxyAggregateFilter2Impl<T1Proxy extends ProxyEntity<T1Proxy, T1>, T1,
        T2Proxy extends ProxyEntity<T2Proxy, T2>, T2> implements MultiProxyAggregateFilter2<T1Proxy, T2Proxy> {

    private final AggregateFilter aggregateFilter;
    private final T1Proxy t;
    private final T2Proxy t1;

    public MultiProxyAggregateFilter2Impl(AggregateFilter aggregateFilter, T1Proxy t, T2Proxy t1) {
        this.aggregateFilter = aggregateFilter;
        this.t = t;
        this.t1 = t1;
    }
    @Override
    public T1Proxy t() {
        return t;
    }

    @Override
    public T2Proxy t1() {
        return t1;
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
    public ProxyAggregateFilter castChain() {
        return this;
    }
}
