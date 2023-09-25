package com.easy.query.api.proxy.select.extension.queryable.sql.impl;

import com.easy.query.api.proxy.select.extension.queryable.sql.MultiProxyOrderSelector1;
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
public class MultiProxyOrderSelector1Impl<T1Proxy extends ProxyEntity<T1Proxy, T1>, T1> implements MultiProxyOrderSelector1<T1Proxy> {

    private final OrderSelector orderSelector;
    private final T1Proxy t;

    public MultiProxyOrderSelector1Impl(OrderSelector orderSelector, T1Proxy t) {
        this.orderSelector = orderSelector;
        this.t = t;
    }
    @Override
    public T1Proxy t() {
        return t;
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
