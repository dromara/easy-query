package com.easy.query.api.proxy.select.extension.queryable2.sql.impl;

import com.easy.query.api.proxy.select.extension.queryable10.sql.MultiProxySelector10;
import com.easy.query.api.proxy.select.extension.queryable2.sql.MultiProxySelector2;
import com.easy.query.api.proxy.sql.ProxySelector;
import com.easy.query.core.expression.builder.Selector;
import com.easy.query.core.expression.builder.core.SQLNative;
import com.easy.query.core.proxy.ProxyEntity;
import com.easy.query.core.util.EasyObjectUtil;

/**
 * create time 2023/9/10 13:13
 * 文件说明
 *
 * @author xuejiaming
 */
public class MultiProxySelector2Impl<T1Proxy extends ProxyEntity<T1Proxy, T1>, T1,
        T2Proxy extends ProxyEntity<T2Proxy, T2>, T2> implements MultiProxySelector2<T1Proxy, T2Proxy> {

    private final Selector selector;
    private final T1Proxy t;
    private final T2Proxy t1;

    public MultiProxySelector2Impl(Selector selector, T1Proxy t, T2Proxy t1) {
        this.selector = selector;
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
    public Selector getSelector() {
        return selector;
    }

    @Override
    public <T> SQLNative<T> getSQLNative() {
        return EasyObjectUtil.typeCastNullable(selector);
    }

    @Override
    public ProxySelector castTChain() {
        return this;
    }
}
