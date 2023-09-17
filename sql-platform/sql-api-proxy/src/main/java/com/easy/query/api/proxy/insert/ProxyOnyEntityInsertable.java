package com.easy.query.api.proxy.insert;

import com.easy.query.core.basic.api.insert.Insertable;
import com.easy.query.core.proxy.ProxyEntity;

import java.util.Collection;

/**
 * create time 2023/6/25 17:24
 * 文件说明
 *
 * @author xuejiaming
 */
public interface ProxyOnyEntityInsertable<T> extends Insertable<T, ProxyOnyEntityInsertable<T>> {
    @Override
    ProxyOnyEntityInsertable<T> insert(T entity);

    @Override
    ProxyOnyEntityInsertable<T> insert(Collection<T> entities);

    <TProxy extends ProxyEntity<TProxy,T>> ProxyEntityInsertable<TProxy, T> useProxy(TProxy proxy);
}
