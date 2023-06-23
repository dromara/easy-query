package com.easy.query.api.proxy.select.impl;

import com.easy.query.api.proxy.core.ProxyQuery;
import com.easy.query.api.proxy.select.ProxyQueryable;
import com.easy.query.api.proxy.select.abstraction.AbstractProxyQueryable1;
import com.easy.query.core.basic.api.select.ClientQueryable;

/**
 * create time 2023/6/23 22:14
 * 文件说明
 *
 * @author xuejiaming
 */
public class EasyProxyQueryable<T1Proxy extends ProxyQuery<T1Proxy, T1>, T1> extends AbstractProxyQueryable1<T1Proxy,T1> {
    public EasyProxyQueryable(T1Proxy t1Proxy, ClientQueryable<T1> entityQueryable) {
        super(t1Proxy, entityQueryable);
    }

    @Override
    public ProxyQueryable<T1Proxy, T1> cloneQueryable() {
        return new EasyProxyQueryable<>(t1Proxy,entityQueryable.cloneQueryable());
    }
}
