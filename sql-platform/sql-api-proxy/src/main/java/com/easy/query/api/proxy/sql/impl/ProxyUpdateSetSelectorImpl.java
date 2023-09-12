package com.easy.query.api.proxy.sql.impl;

import com.easy.query.api.proxy.sql.ProxyUpdateSetSelector;
import com.easy.query.core.expression.builder.UpdateSetSelector;
import com.easy.query.core.expression.builder.core.SQLNative;
import com.easy.query.core.proxy.ProxyEntity;
import com.easy.query.core.util.EasyObjectUtil;

/**
 * create time 2023/6/25 19:15
 * 文件说明
 *
 * @author xuejiaming
 */
public class ProxyUpdateSetSelectorImpl<TProxy extends ProxyEntity<TProxy,T>,T> implements ProxyUpdateSetSelector<TProxy,T> {
    private final UpdateSetSelector updateSetSelector;

    public ProxyUpdateSetSelectorImpl(UpdateSetSelector updateSetSelector){

        this.updateSetSelector = updateSetSelector;
    }
    @Override
    public UpdateSetSelector getUpdateSetSelector() {
        return updateSetSelector;
    }

    @Override
    public <TEntity> SQLNative<TEntity> getSQLNative() {
        return EasyObjectUtil.typeCastNullable(updateSetSelector);
    }

    @Override
    public ProxyUpdateSetSelector<TProxy,T> castTChain() {
        return this;
    }
}
