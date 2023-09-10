package com.easy.query.api.proxy.select.extension.queryable4.sql.impl;

import com.easy.query.api.proxy.select.extension.queryable4.sql.MultiProxyAggregateFilter4;
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
public class MultiProxyAggregateFilter4Impl<T1Proxy extends ProxyEntity<T1Proxy, T1>, T1,
        T2Proxy extends ProxyEntity<T2Proxy, T2>, T2,
        T3Proxy extends ProxyEntity<T3Proxy, T3>, T3,
        T4Proxy extends ProxyEntity<T4Proxy, T4>, T4> implements MultiProxyAggregateFilter4<T1Proxy, T2Proxy, T3Proxy, T4Proxy> {

    private final AggregateFilter aggregateFilter;
    private final T1Proxy t;
    private final T2Proxy t1;
    private final T3Proxy t2;
    private final T4Proxy t3;

    public MultiProxyAggregateFilter4Impl(AggregateFilter aggregateFilter, T1Proxy t, T2Proxy t1, T3Proxy t2, T4Proxy t3) {
        this.aggregateFilter = aggregateFilter;
        this.t = t;
        this.t1 = t1;
        this.t2 = t2;
        this.t3 = t3;
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
    public T3Proxy t2() {
        return t2;
    }

    @Override
    public T4Proxy t3() {
        return t3;
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
