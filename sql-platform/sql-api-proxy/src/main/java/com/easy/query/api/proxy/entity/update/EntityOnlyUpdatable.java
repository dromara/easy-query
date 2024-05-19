package com.easy.query.api.proxy.entity.update;

import com.easy.query.core.basic.api.internal.SQLExecuteStrategy;
import com.easy.query.core.basic.api.update.ClientEntityUpdatable;
import com.easy.query.core.basic.api.update.Updatable;
import com.easy.query.core.proxy.ProxyEntity;

/**
 * create time 2024/5/19 09:03
 * 文件说明
 *
 * @author xuejiaming
 */
public interface EntityOnlyUpdatable<T> extends Updatable<T, EntityOnlyUpdatable<T>>, SQLExecuteStrategy<EntityOnlyUpdatable<T>> {
    ClientEntityUpdatable<T> getClientUpdate();

    <TProxy extends ProxyEntity<TProxy,T>> EntityUpdatable<TProxy, T> useProxy(TProxy proxy);

    default String toSQL(Object entity) {
        return getClientUpdate().toSQL(entity);
    }
}
