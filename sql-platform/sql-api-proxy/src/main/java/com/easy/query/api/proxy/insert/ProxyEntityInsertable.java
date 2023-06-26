package com.easy.query.api.proxy.insert;

import com.easy.query.core.basic.api.insert.Insertable;

import java.util.Collection;

/**
 * create time 2023/6/25 17:24
 * 文件说明
 *
 * @author xuejiaming
 */
public interface ProxyEntityInsertable<T> extends Insertable<T, ProxyEntityInsertable<T>> {
    @Override
    ProxyEntityInsertable<T> insert(T entity);

    @Override
    ProxyEntityInsertable<T> insert(Collection<T> entities);
}
