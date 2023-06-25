package com.easy.query.api.proxy.update.impl;

import com.easy.query.api.proxy.update.abstraction.AbstractProxyEntityUpdatable;
import com.easy.query.core.basic.api.update.ClientEntityUpdatable;
import com.easy.query.core.proxy.ProxyEntity;

/**
 * create time 2023/6/25 21:37
 * 文件说明
 *
 * @author xuejiaming
 */
public class EasyProxyEntityUpdatable<TProxy extends ProxyEntity<TProxy, T>, T> extends AbstractProxyEntityUpdatable<TProxy,T> {

    public EasyProxyEntityUpdatable(TProxy proxy, ClientEntityUpdatable<T> clientEntityUpdatable) {
        super(proxy, clientEntityUpdatable);
    }
}
