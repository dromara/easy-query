package com.easy.query.api.proxy.sql;

import com.easy.query.api.proxy.entity.select.EntityQueryable;
import com.easy.query.core.proxy.ProxyEntity;
import com.easy.query.core.proxy.ProxyEntityAvailable;

/**
 * create time 2023/9/9 14:47
 * 文件说明
 *
 * @author xuejiaming
 */
public interface ProxyEntityFill<TRProxyEntity extends ProxyEntity<TRProxyEntity, TREntity>, TREntity extends ProxyEntityAvailable<TREntity, TRProxyEntity>> {
    EntityQueryable<TRProxyEntity, TREntity> with(Class<TREntity> subEntityClass);
    ProxyEntityFill<TRProxyEntity, TREntity> consumeNull(boolean consumeNull);
}
