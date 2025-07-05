package com.easy.query.cache.core.impl;

import com.easy.query.cache.core.CacheAllEntity;
import com.easy.query.cache.core.CacheKvEntity;
import com.easy.query.cache.core.EasyCacheClient;
import com.easy.query.cache.core.common.CacheKey;
import com.easy.query.cache.core.provider.EasyCacheProvider;
import com.easy.query.cache.core.CacheRuntimeContext;
import com.easy.query.cache.core.base.ClearParameter;
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


    public DefaultEasyCacheClient(CacheRuntimeContext cacheRuntimeContext, EasyCacheProvider easyCacheProvider, ServiceProvider serviceProvider){

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
        return new DefaultKvCacheQueryable<>(cacheRuntimeContext,entityClass);
    }

    @Override
    public <T1Proxy extends ProxyEntity<T1Proxy, TEntity>, TEntity extends ProxyEntityAvailable<TEntity, T1Proxy> & CacheAllEntity> AllCacheQueryable<T1Proxy, TEntity> allStorage(Class<TEntity> entityClass) {
        return new DefaultAllCacheQueryable<>(cacheRuntimeContext,entityClass);
    }

//    @Override
//    public <T extends CacheKvEntity> KvCacheQueryable<T> kvStorage(Class<T> entityClass) {
//        return new DefaultKvCacheQueryable<>(cacheRuntimeContext,entityClass);
//    }
//
////    @Override
////    public <T extends CacheMultiEntity> MultiCacheQueryable<T> multiStorage(Class<T> entityClass) {
////        return new DefaultMultiCacheQueryable<>(easyCacheStorageOption,entityClass);
////    }
//
//    @Override
//    public <T extends CacheAllEntity> AllCacheQueryable<T> allStorage(Class<T> entityClass) {
//        return new DefaultAllCacheQueryable<>(cacheRuntimeContext,entityClass);
//    }
    @Override
    public void deleteBy(CacheKey cacheKey) {
        easyCacheProvider.deleteBy(cacheKey);
    }
}
