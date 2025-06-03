package com.easy.query.cache.core.impl;

import com.easy.query.cache.core.CacheAllEntity;
import com.easy.query.cache.core.CacheKvEntity;
import com.easy.query.cache.core.EasyCacheClient;
import com.easy.query.cache.core.EasyCacheManager;
import com.easy.query.cache.core.CacheRuntimeContext;
import com.easy.query.cache.core.base.ClearParameter;
import com.easy.query.cache.core.impl.all.DefaultAllCacheQueryable;
import com.easy.query.cache.core.impl.kv.DefaultKvCacheQueryable;
import com.easy.query.cache.core.queryable.AllCacheQueryable;
import com.easy.query.cache.core.queryable.KvCacheQueryable;
import com.easy.query.core.inject.ServiceProvider;

/**
 * create time 2024/1/24 22:00
 * 文件说明
 *
 * @author xuejiaming
 */
public class DefaultEasyCacheClient implements EasyCacheClient {
    private final CacheRuntimeContext cacheRuntimeContext;
    private final EasyCacheManager easyCacheManager;
    private final ServiceProvider serviceProvider;


    public DefaultEasyCacheClient(CacheRuntimeContext cacheRuntimeContext, EasyCacheManager easyCacheManager, ServiceProvider serviceProvider){

        this.cacheRuntimeContext = cacheRuntimeContext;
        this.easyCacheManager = easyCacheManager;
        this.serviceProvider = serviceProvider;
    }

    @Override
    public <T> T getService(Class<T> serviceType) {
        return serviceProvider.getService(serviceType);
    }

    @Override
    public <T extends CacheKvEntity> KvCacheQueryable<T> kvStorage(Class<T> entityClass) {
        return new DefaultKvCacheQueryable<>(cacheRuntimeContext,entityClass);
    }

//    @Override
//    public <T extends CacheMultiEntity> MultiCacheQueryable<T> multiStorage(Class<T> entityClass) {
//        return new DefaultMultiCacheQueryable<>(easyCacheStorageOption,entityClass);
//    }

    @Override
    public <T extends CacheAllEntity> AllCacheQueryable<T> allStorage(Class<T> entityClass) {
        return new DefaultAllCacheQueryable<>(cacheRuntimeContext,entityClass);
    }
    @Override
    public void clear(ClearParameter clearParameter) {
        easyCacheManager.clear(clearParameter);
    }
}
