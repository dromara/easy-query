package com.easy.query.core.proxy;

import com.easy.query.core.proxy.impl.SQLColumnImpl;

/**
 * create time 2023/6/25 12:39
 * 文件说明
 *
 * @author xuejiaming
 */
public abstract class AbstractProxyEntity<TProxy extends ProxyEntity<TProxy, TEntity>, TEntity> implements ProxyEntity<TProxy,TEntity> {
    public <TProperty> SQLColumn<TProperty> get(String property){
        return new SQLColumnImpl<>(getTable(),property);
    }
}
