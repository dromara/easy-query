package com.easy.query.api.proxy.select.extension.queryable.sql.impl;

import com.easy.query.api.proxy.select.extension.queryable.sql.MultiProxyAsSelector1;
import com.easy.query.api.proxy.sql.ProxyAsSelector;
import com.easy.query.core.expression.builder.AsSelector;
import com.easy.query.core.expression.builder.core.SQLNative;
import com.easy.query.core.proxy.ProxyEntity;
import com.easy.query.core.util.EasyObjectUtil;

/**
 * create time 2023/9/10 11:01
 * 文件说明
 *
 * @author xuejiaming
 */
public class MultiProxyAsSelector1Impl<T1Proxy extends ProxyEntity<T1Proxy, T1>, T1,
        TRProxy extends ProxyEntity<TRProxy, TR>, TR> implements MultiProxyAsSelector1<T1Proxy, TRProxy, TR> {


    private final AsSelector asSelector;
    private final T1Proxy t;
    private final TRProxy trProxy;

    public MultiProxyAsSelector1Impl(AsSelector asSelector, T1Proxy t, TRProxy trProxy) {
        this.asSelector = asSelector;
        this.t = t;
        this.trProxy = trProxy;
    }
    @Override
    public T1Proxy t() {
        return t;
    }

    @Override
    public TRProxy tr() {
        return trProxy;
    }

    @Override
    public AsSelector getAsSelector() {
        return asSelector;
    }

    @Override
    public <T> SQLNative<T> getSQLNative() {
        return EasyObjectUtil.typeCastNullable(asSelector);
    }

    @Override
    public ProxyAsSelector<TRProxy, TR> castTChain() {
        return this;
    }
}
