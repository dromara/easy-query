package com.easy.query.api.proxy.select.impl;

import com.easy.query.api.proxy.select.ProxyQueryable5;
import com.easy.query.api.proxy.select.abstraction.AbstractProxyQueryable5;
import com.easy.query.core.basic.api.select.ClientQueryable5;
import com.easy.query.core.proxy.ProxyEntity;

/**
 * create time 2023/6/23 23:22
 * 文件说明
 *
 * @author xuejiaming
 */
public class EasyProxyQueryable5<T1Proxy extends ProxyEntity<T1Proxy, T1>, T1,
        T2Proxy extends ProxyEntity<T2Proxy, T2>, T2,
        T3Proxy extends ProxyEntity<T3Proxy, T3>, T3,
        T4Proxy extends ProxyEntity<T4Proxy, T4>, T4,
        T5Proxy extends ProxyEntity<T5Proxy, T5>, T5>
        extends AbstractProxyQueryable5<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5> {
    public EasyProxyQueryable5(T1Proxy t1Proxy, T2Proxy t2Proxy, T3Proxy t3Proxy, T4Proxy t4Proxy, T5Proxy t5Proxy, ClientQueryable5<T1, T2, T3, T4, T5> entityQueryable) {
        super(t1Proxy, t2Proxy, t3Proxy, t4Proxy,t5Proxy, entityQueryable);
    }

    @Override
    public ProxyQueryable5<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5> cloneQueryable() {
        return new EasyProxyQueryable5<>(get1Proxy(), get2Proxy(), get3Proxy(),get4Proxy(),get5Proxy(), getClientQueryable5());
    }
}
