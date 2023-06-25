package com.easy.query.api.proxy.update.impl;

import com.easy.query.api.proxy.update.ProxyEntityUpdatable;
import com.easy.query.api.proxy.update.abstraction.AbstractProxyOnlyEntityUpdatable;
import com.easy.query.core.basic.api.update.ClientEntityUpdatable;
import com.easy.query.core.proxy.ProxyEntity;

/**
 * create time 2023/6/25 21:45
 * 文件说明
 *
 * @author xuejiaming
 */
public class EasyProxyOnlyEntityUpdatable<T> extends AbstractProxyOnlyEntityUpdatable<T> {
    public EasyProxyOnlyEntityUpdatable(ClientEntityUpdatable<T> clientEntityUpdatable) {
        super(clientEntityUpdatable);
    }

    @Override
    public <TProxy extends ProxyEntity<TProxy, T>> ProxyEntityUpdatable<TProxy, T> useProxy(TProxy proxy) {
        return new EasyProxyEntityUpdatable<>(proxy,getClientUpdate());
    }
}
