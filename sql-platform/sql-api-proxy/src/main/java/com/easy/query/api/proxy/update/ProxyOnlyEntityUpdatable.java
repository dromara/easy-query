package com.easy.query.api.proxy.update;

import com.easy.query.core.basic.api.internal.SQLExecuteStrategy;
import com.easy.query.core.basic.api.update.ClientEntityUpdatable;
import com.easy.query.core.basic.api.update.Updatable;
import com.easy.query.core.proxy.ProxyEntity;

/**
 * @author xuejiaming
 * @FileName: EntityUpdate.java
 * @Description: 文件说明
 * @Date: 2023/2/24 23:22
 */
public interface ProxyOnlyEntityUpdatable<T> extends Updatable<T, ProxyOnlyEntityUpdatable<T>>, SQLExecuteStrategy<ProxyOnlyEntityUpdatable<T>> {
    ClientEntityUpdatable<T> getClientUpdate();

   <TProxy extends ProxyEntity<TProxy,T>> ProxyEntityUpdatable<TProxy, T> useProxy(TProxy proxy);

    default String toSQL(Object entity) {
        return getClientUpdate().toSQL(entity);
    }
}
