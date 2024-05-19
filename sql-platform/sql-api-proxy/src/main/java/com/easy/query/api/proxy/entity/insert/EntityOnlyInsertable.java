package com.easy.query.api.proxy.entity.insert;

import com.easy.query.core.basic.api.insert.Insertable;
import com.easy.query.core.proxy.ProxyEntity;

import java.util.Collection;

/**
 * create time 2024/5/19 08:40
 * 文件说明
 *
 * @author xuejiaming
 */
public interface EntityOnlyInsertable<T> extends Insertable<T, EntityOnlyInsertable<T>> {
    @Override
    EntityOnlyInsertable<T> insert(T entity);

    @Override
    EntityOnlyInsertable<T> insert(Collection<T> entities);

    <TProxy extends ProxyEntity<TProxy,T>> EntityInsertable<TProxy, T> useProxy(TProxy proxy);
}
