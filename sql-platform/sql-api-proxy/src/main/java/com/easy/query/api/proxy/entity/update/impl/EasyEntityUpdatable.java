package com.easy.query.api.proxy.entity.update.impl;

import com.easy.query.api.proxy.entity.update.abstraction.AbstractEntityUpdatable;
import com.easy.query.core.basic.api.update.ClientEntityUpdatable;
import com.easy.query.core.proxy.ProxyEntity;

/**
 * create time 2023/12/7 16:37
 * 文件说明
 *
 * @author xuejiaming
 */
public class EasyEntityUpdatable<TProxy extends ProxyEntity<TProxy, T>, T> extends AbstractEntityUpdatable<TProxy,T> {
    public EasyEntityUpdatable(TProxy tProxy, ClientEntityUpdatable<T> clientEntityUpdatable) {
        super(tProxy, clientEntityUpdatable);
    }
}
