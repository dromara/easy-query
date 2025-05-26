package com.easy.query.api.proxy.entity.select.impl;

import com.easy.query.api.proxy.entity.select.EntityQueryable3;
import com.easy.query.api.proxy.entity.select.abstraction.AbstractEntityQueryable3;
import com.easy.query.core.basic.api.select.ClientQueryable3;
import com.easy.query.core.proxy.ProxyEntity;
import org.jetbrains.annotations.NotNull;

/**
 * create time 2023/6/23 23:22
 * 文件说明
 *
 * @author xuejiaming
 */
public class EasyEntityQueryable3<T1Proxy extends ProxyEntity<T1Proxy, T1>, T1, T2Proxy extends ProxyEntity<T2Proxy, T2>, T2, T3Proxy extends ProxyEntity<T3Proxy, T3>, T3>
        extends AbstractEntityQueryable3<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3> {
    public EasyEntityQueryable3(T1Proxy t1Proxy, T2Proxy t2Proxy, T3Proxy t3Proxy, ClientQueryable3<T1, T2,T3> entityQueryable) {
        super(t1Proxy, t2Proxy,t3Proxy, entityQueryable);
    }

    @NotNull
    @Override
    public EntityQueryable3<T1Proxy, T1,T2Proxy, T2,T3Proxy, T3> cloneQueryable() {
        return new EasyEntityQueryable3<>(get1Proxy(),get2Proxy(),get3Proxy(),getClientQueryable3().cloneQueryable());
    }
}
