package com.easy.query.api.proxy.sql.impl;

import com.easy.query.api.proxy.sql.ProxyAsSelector;
import com.easy.query.core.expression.builder.AsSelector;
import com.easy.query.core.expression.builder.core.SQLNative;
import com.easy.query.core.proxy.ProxyEntity;
import com.easy.query.core.util.EasyObjectUtil;

/**
 * create time 2023/6/23 23:00
 * 文件说明
 *
 * @author xuejiaming
 */
public class ProxyAsSelectorImpl<TRProxy extends ProxyEntity<TRProxy, TR>, TR> implements ProxyAsSelector<TRProxy,TR> {
    private final TRProxy trProxy;
    private final AsSelector asSelector;

    public ProxyAsSelectorImpl(TRProxy trProxy,AsSelector asSelector){
        this.trProxy = trProxy;

        this.asSelector = asSelector;
    }

    @Override
    public TRProxy getTRProxy() {
        return trProxy;
    }

    @Override
    public AsSelector getAsSelector() {
        return asSelector;
    }

    @Override
    public <T> SQLNative<T> getSQLNative() {
        return EasyObjectUtil.typeCastNullable(asSelector);
    }

    @Override
    public ProxyAsSelector<TRProxy, TR> castTChain() {
        return this;
    }
}
