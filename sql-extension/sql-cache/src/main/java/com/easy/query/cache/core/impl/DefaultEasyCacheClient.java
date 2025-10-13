package com.easy.query.cache.core.impl;

import com.easy.query.api.proxy.entity.EntityQueryProxyManager;
import com.easy.query.cache.core.CacheAllEntity;
import com.easy.query.cache.core.CacheKvEntity;
import com.easy.query.cache.core.EasyCacheClient;
import com.easy.query.cache.core.common.CacheKey;
import com.easy.query.cache.core.provider.EasyCacheProvider;
import com.easy.query.cache.core.CacheRuntimeContext;
import com.easy.query.cache.core.impl.all.DefaultAllCacheQueryable;
import com.easy.query.cache.core.impl.kv.DefaultKvCacheQueryable;
import com.easy.query.cache.core.queryable.AllCacheQueryable;
import com.easy.query.cache.core.queryable.KvCacheQueryable;
import com.easy.query.core.inject.ServiceProvider;
import com.easy.query.core.proxy.ProxyEntity;
import com.easy.query.core.proxy.ProxyEntityAvailable;

/**
 * create time 2024/1/24 22:00
 * 文件说明
 *
 * @author xuejiaming
 */
public class DefaultEasyCacheClient implements EasyCacheClient {
    private final CacheRuntimeContext cacheRuntimeContext;
    private final EasyCacheProvider easyCacheProvider;
    private final ServiceProvider serviceProvider;


    public DefaultEasyCacheClient(CacheRuntimeContext cacheRuntimeContext, EasyCacheProvider easyCacheProvider, ServiceProvider serviceProvider) {

        this.cacheRuntimeContext = cacheRuntimeContext;
        this.easyCacheProvider = easyCacheProvider;
        this.serviceProvider = serviceProvider;
    }

    @Override
    public <T> T getService(Class<T> serviceType) {
        return serviceProvider.getService(serviceType);
    }

    @Override
    public <T1Proxy extends ProxyEntity<T1Proxy, TEntity>, TEntity extends ProxyEntityAvailable<TEntity, T1Proxy> & CacheKvEntity> KvCacheQueryable<T1Proxy, TEntity> kvStorage(Class<TEntity> entityClass) {
        T1Proxy t1Proxy = EntityQueryProxyManager.create(entityClass);
        return new DefaultKvCacheQueryable<>(cacheRuntimeContext, entityClass, t1Proxy);
    }

    @Override
    public <T1Proxy extends ProxyEntity<T1Proxy, TEntity>, TEntity extends ProxyEntityAvailable<TEntity, T1Proxy> & CacheAllEntity> AllCacheQueryable<T1Proxy, TEntity> allStorage(Class<TEntity> entityClass) {
        T1Proxy t1Proxy = EntityQueryProxyManager.create(entityClass);
        return new DefaultAllCacheQueryable<>(cacheRuntimeContext, entityClass, t1Proxy);
    }

    @Override
    public <T1Proxy extends ProxyEntity<T1Proxy, TEntity>, TEntity extends CacheKvEntity> KvCacheQueryable<T1Proxy, TEntity> kvStorage(T1Proxy t1Proxy) {
        return new DefaultKvCacheQueryable<>(cacheRuntimeContext, t1Proxy.getEntityClass(), t1Proxy);
    }

    @Override
    public <T1Proxy extends ProxyEntity<T1Proxy, TEntity>, TEntity extends CacheAllEntity> AllCacheQueryable<T1Proxy, TEntity> allStorage(T1Proxy t1Proxy) {
        return new DefaultAllCacheQueryable<>(cacheRuntimeContext, t1Proxy.getEntityClass(), t1Proxy);
    }

    @Override
    public void deleteBy(CacheKey cacheKey) {
        easyCacheProvider.deleteBy(cacheKey);
    }
}
