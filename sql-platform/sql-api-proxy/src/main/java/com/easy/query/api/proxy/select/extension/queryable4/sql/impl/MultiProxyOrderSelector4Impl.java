package com.easy.query.api.proxy.select.extension.queryable4.sql.impl;

import com.easy.query.api.proxy.select.extension.queryable4.sql.MultiProxyOrderSelector4;
import com.easy.query.api.proxy.sql.ProxyOrderSelector;
import com.easy.query.core.expression.builder.OrderSelector;
import com.easy.query.core.expression.builder.core.SQLNative;
import com.easy.query.core.proxy.ProxyEntity;
import com.easy.query.core.util.EasyObjectUtil;

/**
 * create time 2023/9/10 10:42
 * 文件说明
 *
 * @author xuejiaming
 */
public class MultiProxyOrderSelector4Impl<T1Proxy extends ProxyEntity<T1Proxy, T1>, T1,
        T2Proxy extends ProxyEntity<T2Proxy, T2>, T2,
        T3Proxy extends ProxyEntity<T3Proxy, T3>, T3,
        T4Proxy extends ProxyEntity<T4Proxy, T4>, T4> implements MultiProxyOrderSelector4<T1Proxy, T2Proxy, T3Proxy, T4Proxy> {

    private final OrderSelector orderSelector;
    private final T1Proxy t;
    private final T2Proxy t1;
    private final T3Proxy t2;
    private final T4Proxy t3;

    public MultiProxyOrderSelector4Impl(OrderSelector orderSelector, T1Proxy t, T2Proxy t1, T3Proxy t2, T4Proxy t3) {
        this.orderSelector = orderSelector;
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
    public OrderSelector getOrderSelector() {
        return orderSelector;
    }

    @Override
    public <T> SQLNative<T> getSQLNative() {
        return EasyObjectUtil.typeCastNullable(orderSelector);
    }

    @Override
    public ProxyOrderSelector castChain() {
        return this;
    }
}
