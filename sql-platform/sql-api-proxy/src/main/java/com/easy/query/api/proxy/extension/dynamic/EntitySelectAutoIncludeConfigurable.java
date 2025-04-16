package com.easy.query.api.proxy.extension.dynamic;

import com.easy.query.api.proxy.entity.EntityQueryProxyManager;
import com.easy.query.api.proxy.entity.select.EntityQueryable;
import com.easy.query.api.proxy.entity.select.impl.EasyEntityQueryable;
import com.easy.query.core.api.dynamic.executor.query.ConfigureArgument;
import com.easy.query.core.api.dynamic.executor.query.SelectAutoIncludeConfigurable;
import com.easy.query.core.basic.api.select.ClientQueryable;
import com.easy.query.core.proxy.ProxyEntity;
import com.easy.query.core.proxy.ProxyEntityAvailable;
import com.easy.query.core.util.EasyObjectUtil;

/**
 * create time 2025/2/5 20:57
 * 对象模式配置自动包含解耦画的一些配置
 *
 * @author xuejiaming
 */
@Deprecated
public interface EntitySelectAutoIncludeConfigurable<TEntity extends ProxyEntityAvailable<TEntity, TProxy>, TProxy extends ProxyEntity<TProxy, TEntity>> extends SelectAutoIncludeConfigurable {

    EntityQueryable<TProxy, TEntity> entityConfigure(EntityQueryable<TProxy, TEntity> queryable, ConfigureArgument configureArgument);

    @Override
    default <T> ClientQueryable<T> configure(ClientQueryable<T> queryable, ConfigureArgument configureArgument) {
        Object tEntityProxy = EntityQueryProxyManager.create(EasyObjectUtil.typeCastNullable(queryable.queryClass()));
        Object proxy = new EasyEntityQueryable<>(EasyObjectUtil.typeCastNullable(tEntityProxy), queryable);
        EntityQueryable<TProxy, TEntity> entityQueryable = entityConfigure(EasyObjectUtil.typeCastNullable(proxy), configureArgument);
        return EasyObjectUtil.typeCastNullable(entityQueryable.getClientQueryable());
    }
}
