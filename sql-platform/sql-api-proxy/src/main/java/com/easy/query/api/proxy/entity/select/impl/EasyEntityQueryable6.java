package com.easy.query.api.proxy.entity.select.impl;

import com.easy.query.api.proxy.entity.select.EntityQueryable6;
import com.easy.query.api.proxy.entity.select.abstraction.AbstractEntityQueryable6;
import com.easy.query.core.basic.api.select.ClientQueryable6;
import com.easy.query.core.proxy.ProxyEntity;
import org.jetbrains.annotations.NotNull;

/**
 * create time 2023/6/23 23:22
 * 文件说明
 *
 * @author xuejiaming
 */
public class EasyEntityQueryable6<T1Proxy extends ProxyEntity<T1Proxy, T1>, T1,
        T2Proxy extends ProxyEntity<T2Proxy, T2>, T2,
        T3Proxy extends ProxyEntity<T3Proxy, T3>, T3,
        T4Proxy extends ProxyEntity<T4Proxy, T4>, T4,
        T5Proxy extends ProxyEntity<T5Proxy, T5>, T5,
        T6Proxy extends ProxyEntity<T6Proxy, T6>, T6>
        extends AbstractEntityQueryable6<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6> {
    public EasyEntityQueryable6(T1Proxy t1Proxy, T2Proxy t2Proxy, T3Proxy t3Proxy, T4Proxy t4Proxy, T5Proxy t5Proxy, T6Proxy t6Proxy, ClientQueryable6<T1, T2, T3, T4, T5, T6> entityQueryable) {
        super(t1Proxy, t2Proxy, t3Proxy, t4Proxy,t5Proxy,t6Proxy, entityQueryable);
    }

    @NotNull
    @Override
    public EntityQueryable6<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6> cloneQueryable() {
        return new EasyEntityQueryable6<>(get1Proxy(), get2Proxy(), get3Proxy(),get4Proxy(),get5Proxy(),get6Proxy(), getClientQueryable6().cloneQueryable());
    }
}
