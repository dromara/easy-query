package com.easy.query.api.proxy.sql.impl;

import com.easy.query.api.proxy.select.ProxyQueryable;
import com.easy.query.api.proxy.select.impl.EasyProxyQueryable;
import com.easy.query.api.proxy.sql.ProxyFill;
import com.easy.query.core.expression.parser.core.base.FillSelector;
import com.easy.query.core.proxy.ProxyEntity;

/**
 * create time 2023/9/9 14:53
 * 文件说明
 *
 * @author xuejiaming
 */
public class ProxyFillImp implements ProxyFill {
    private final FillSelector fillSelector;

    public ProxyFillImp(FillSelector fillSelector){

        this.fillSelector = fillSelector;
    }
    @Override
    public <TRProxyEntity extends ProxyEntity<TRProxyEntity, TREntity>, TREntity> ProxyQueryable<TRProxyEntity, TREntity> with(TRProxyEntity proxy) {
        return new EasyProxyQueryable<>(proxy,fillSelector.with(proxy.getEntityClass()));
    }

    @Override
    public ProxyFill consumeNull(boolean consumeNull) {
        fillSelector.consumeNull(consumeNull);
        return this;
    }
}
