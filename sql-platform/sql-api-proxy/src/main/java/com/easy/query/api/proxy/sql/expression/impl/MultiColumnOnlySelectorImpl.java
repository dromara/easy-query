package com.easy.query.api.proxy.sql.expression.impl;

import com.easy.query.api.proxy.sql.ProxyColumnOnlySelector;
import com.easy.query.api.proxy.sql.expression.MultiColumnOnlySelector;
import com.easy.query.core.expression.builder.OnlySelector;
import com.easy.query.core.expression.builder.core.SQLNative;
import com.easy.query.core.proxy.ProxyEntity;
import com.easy.query.core.util.EasyObjectUtil;

/**
 * create time 2023/9/17 21:52
 * 文件说明
 *
 * @author xuejiaming
 */
public class MultiColumnOnlySelectorImpl<T1Proxy extends ProxyEntity<T1Proxy,T1>,T1> implements MultiColumnOnlySelector<T1Proxy,T1> {

    private final T1Proxy t1Proxy;
    private final OnlySelector updateSetSelector;

    public MultiColumnOnlySelectorImpl(T1Proxy t1Proxy, OnlySelector updateSetSelector){

        this.t1Proxy = t1Proxy;
        this.updateSetSelector = updateSetSelector;
    }
    @Override
    public T1Proxy t() {
        return t1Proxy;
    }

    @Override
    public OnlySelector getOnlySelector() {
        return updateSetSelector;
    }

    @Override
    public T1Proxy getProxy() {
        return t1Proxy;
    }

    @Override
    public <T> SQLNative<T> getSQLNative() {
        return EasyObjectUtil.typeCastNullable(updateSetSelector);
    }

    @Override
    public ProxyColumnOnlySelector<T1Proxy, T1> castTChain() {
        return this;
    }
}
