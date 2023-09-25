package com.easy.query.api.proxy.select.extension.queryable5.sql.impl;

import com.easy.query.api.proxy.select.extension.queryable5.sql.MultiProxySelector5;
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
public class MultiProxySelector5Impl<T1Proxy extends ProxyEntity<T1Proxy, T1>, T1,
        T2Proxy extends ProxyEntity<T2Proxy, T2>, T2,
        T3Proxy extends ProxyEntity<T3Proxy, T3>, T3,
        T4Proxy extends ProxyEntity<T4Proxy, T4>, T4,
        T5Proxy extends ProxyEntity<T5Proxy, T5>, T5> implements MultiProxySelector5<T1Proxy, T2Proxy, T3Proxy, T4Proxy, T5Proxy> {

    private final Selector selector;
    private final T1Proxy t;
    private final T2Proxy t1;
    private final T3Proxy t2;
    private final T4Proxy t3;
    private final T5Proxy t4;

    public MultiProxySelector5Impl(Selector selector, T1Proxy t, T2Proxy t1, T3Proxy t2, T4Proxy t3, T5Proxy t4) {
        this.selector = selector;
        this.t = t;
        this.t1 = t1;
        this.t2 = t2;
        this.t3 = t3;
        this.t4 = t4;
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
    public T5Proxy t4() {
        return t4;
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
    public ProxySelector castChain() {
        return this;
    }
}
