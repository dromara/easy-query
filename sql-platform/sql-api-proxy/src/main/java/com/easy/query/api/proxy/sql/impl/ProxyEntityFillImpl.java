package com.easy.query.api.proxy.sql.impl;

import com.easy.query.api.proxy.entity.EntityQueryProxyManager;
import com.easy.query.api.proxy.entity.select.EntityQueryable;
import com.easy.query.api.proxy.entity.select.impl.EasyEntityQueryable;
import com.easy.query.api.proxy.sql.ProxyEntityFill;
import com.easy.query.core.expression.parser.core.base.FillSelector;
import com.easy.query.core.proxy.ProxyEntity;
import com.easy.query.core.proxy.ProxyEntityAvailable;

/**
 * create time 2023/9/9 14:53
 * 文件说明
 *
 * @author xuejiaming
 */
public class ProxyEntityFillImpl<TRProxyEntity extends ProxyEntity<TRProxyEntity, TREntity>, TREntity extends ProxyEntityAvailable<TREntity, TRProxyEntity>> implements ProxyEntityFill<TRProxyEntity,TREntity> {
    private final FillSelector fillSelector;

    public ProxyEntityFillImpl(FillSelector fillSelector){

        this.fillSelector = fillSelector;
    }
    @Override
    public EntityQueryable<TRProxyEntity, TREntity> with(Class<TREntity> subEntityClass) {
        TRProxyEntity trProxy = EntityQueryProxyManager.create(subEntityClass);
        return new EasyEntityQueryable<>(trProxy,fillSelector.with(subEntityClass));
    }

    @Override
    public ProxyEntityFill<TRProxyEntity,TREntity> consumeNull(boolean consumeNull) {
        fillSelector.consumeNull(consumeNull);
        return this;
    }
}
