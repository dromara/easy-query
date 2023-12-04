package com.easy.query.api.proxy.entity.select.impl;

import com.easy.query.api.proxy.entity.select.abstraction.AbstractEntityQueryable2;
import com.easy.query.core.basic.api.select.ClientQueryable2;
import com.easy.query.core.proxy.ProxyEntity;

/**
 * create time 2023/6/23 23:22
 * 文件说明
 *
 * @author xuejiaming
 */
public class EasyEntityQueryable2<T1Proxy extends ProxyEntity<T1Proxy, T1>, T1, T2Proxy extends ProxyEntity<T2Proxy, T2>, T2> extends AbstractEntityQueryable2<T1Proxy, T1, T2Proxy, T2> {
    public EasyEntityQueryable2(T1Proxy t1Proxy, T2Proxy t2Proxy, ClientQueryable2<T1, T2> entityQueryable) {
        super(t1Proxy, t2Proxy, entityQueryable);
    }

    @Override
    public EasyEntityQueryable2<T1Proxy, T1,T2Proxy,T2> cloneQueryable() {
        return new EasyEntityQueryable2<>(get1Proxy(),get2Proxy(),getClientQueryable2());
    }
}
