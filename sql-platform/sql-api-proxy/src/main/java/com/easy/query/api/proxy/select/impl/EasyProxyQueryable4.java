package com.easy.query.api.proxy.select.impl;

import com.easy.query.api.proxy.core.ProxyQuery;
import com.easy.query.api.proxy.select.ProxyQueryable;
import com.easy.query.api.proxy.select.abstraction.AbstractProxyQueryable4;
import com.easy.query.core.basic.api.select.ClientQueryable4;

/**
 * create time 2023/6/23 23:22
 * 文件说明
 *
 * @author xuejiaming
 */
public class EasyProxyQueryable4<T1Proxy extends ProxyQuery<T1Proxy, T1>, T1, T2Proxy extends ProxyQuery<T2Proxy, T2>, T2, T3Proxy extends ProxyQuery<T3Proxy, T3>, T3, T4Proxy extends ProxyQuery<T4Proxy, T4>, T4> extends AbstractProxyQueryable4<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4> {
    public EasyProxyQueryable4(T1Proxy t1Proxy, T2Proxy t2Proxy, T3Proxy t3Proxy, T4Proxy t4Proxy, ClientQueryable4<T1, T2, T3, T4> entityQueryable) {
        super(t1Proxy, t2Proxy, t3Proxy, t4Proxy, entityQueryable);
    }

    @Override
    public ProxyQueryable<T1Proxy, T1> cloneQueryable() {
        return new EasyProxyQueryable4<>(get1Proxy(), get2Proxy(), get3Proxy(),get4Proxy(), getClientQueryable4());
    }
}
