package com.easy.query.api.proxy.update.impl;

import com.easy.query.api.proxy.update.abstraction.AbstractProxyExpressionUpdatable;
import com.easy.query.core.basic.api.update.ClientExpressionUpdatable;
import com.easy.query.core.proxy.ProxyEntity;

/**
 * create time 2023/6/25 21:37
 * 文件说明
 *
 * @author xuejiaming
 */
public class EasyProxyExpressionUpdatable<TProxy extends ProxyEntity<TProxy, T>, T> extends AbstractProxyExpressionUpdatable<TProxy,T> {

    public EasyProxyExpressionUpdatable(TProxy proxy, ClientExpressionUpdatable<T> expressionObjectUpdatable) {
        super(proxy, expressionObjectUpdatable);
    }
}
