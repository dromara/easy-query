package com.easy.query.api.proxy.select.extension.queryable.sql.impl;

import com.easy.query.api.proxy.select.extension.queryable.sql.MultiProxySelector1;
import com.easy.query.api.proxy.sql.ProxySelector;
import com.easy.query.core.expression.builder.Selector;
import com.easy.query.core.expression.builder.core.SQLNative;
import com.easy.query.core.proxy.ProxyEntity;
import com.easy.query.core.util.EasyObjectUtil;

/**
 * create time 2023/9/10 13:06
 * 文件说明
 *
 * @author xuejiaming
 */
public class MultiProxySelector1Impl<T1Proxy extends ProxyEntity<T1Proxy, T1>, T1>
        implements MultiProxySelector1<T1Proxy> {

    private final Selector selector;
    private final T1Proxy t;
    public MultiProxySelector1Impl(Selector selector, T1Proxy t) {
        this.selector = selector;
        this.t = t;
    }
    @Override
    public T1Proxy t() {
        return t;
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
