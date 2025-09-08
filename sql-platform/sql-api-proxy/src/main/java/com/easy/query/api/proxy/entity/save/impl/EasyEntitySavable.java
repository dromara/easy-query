package com.easy.query.api.proxy.entity.save.impl;

import com.easy.query.api.proxy.entity.save.abstraction.AbstractEntitySavable;
import com.easy.query.core.api.client.EasyQueryClient;
import com.easy.query.core.proxy.ProxyEntity;

import java.util.Collection;

/**
 * create time 2025/9/5 16:52
 * 文件说明
 *
 * @author xuejiaming
 */
public class EasyEntitySavable<TProxy extends ProxyEntity<TProxy, T>, T> extends AbstractEntitySavable<TProxy,T> {
    public EasyEntitySavable(TProxy tProxy,Class<T> clazz, Collection<T> entities, EasyQueryClient easyQueryClient) {
        super(tProxy,clazz, entities, easyQueryClient);
    }
}
