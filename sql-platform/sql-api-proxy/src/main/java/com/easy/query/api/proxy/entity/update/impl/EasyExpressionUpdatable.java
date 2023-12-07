package com.easy.query.api.proxy.entity.update.impl;

import com.easy.query.api.proxy.entity.update.abstraction.AbstractExpressionUpdatable;
import com.easy.query.core.basic.api.update.ClientExpressionUpdatable;
import com.easy.query.core.proxy.ProxyEntity;

/**
 * create time 2023/12/7 17:11
 * 文件说明
 *
 * @author xuejiaming
 */
public class EasyExpressionUpdatable<TProxy extends ProxyEntity<TProxy, T>, T> extends AbstractExpressionUpdatable<TProxy, T> {
    public EasyExpressionUpdatable(TProxy tProxy, ClientExpressionUpdatable<T> expressionObjectUpdatable) {
        super(tProxy, expressionObjectUpdatable);
    }
}
