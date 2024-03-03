package com.easy.query.api.proxy.entity.delete;

import com.easy.query.core.basic.api.delete.Deletable;
import com.easy.query.core.basic.api.internal.ConfigureVersionable;
import com.easy.query.core.basic.api.internal.SQLBatchExecute;
import com.easy.query.core.proxy.ProxyEntity;

/**
 * @author xuejiaming
 * @FileName: EasyEntityDeletable.java
 * @Description: 文件说明
 * @Date: 2023/3/6 13:07
 */
public interface EntityDeletable<TProxy extends ProxyEntity<TProxy, T>, T> extends Deletable<T, EntityDeletable<TProxy,T>>, ConfigureVersionable<EntityDeletable<TProxy,T>>, SQLBatchExecute<EntityDeletable<TProxy,T>> {
}
