package com.easy.query.api.proxy.select.extension.queryable2.sql.impl;

import com.easy.query.api.proxy.select.extension.queryable2.sql.MultiProxyOrderSelector2;
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
public class MultiProxyOrderSelector2Impl<T1Proxy extends ProxyEntity<T1Proxy, T1>, T1,
        T2Proxy extends ProxyEntity<T2Proxy, T2>, T2> implements MultiProxyOrderSelector2<T1Proxy, T2Proxy> {

    private final OrderSelector orderSelector;
    private final T1Proxy t;
    private final T2Proxy t1;

    public MultiProxyOrderSelector2Impl(OrderSelector orderSelector, T1Proxy t, T2Proxy t1) {
        this.orderSelector = orderSelector;
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
