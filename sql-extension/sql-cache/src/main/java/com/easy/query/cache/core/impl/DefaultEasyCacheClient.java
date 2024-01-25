package com.easy.query.cache.core.impl;

import com.easy.query.cache.core.CacheAllEntity;
import com.easy.query.cache.core.CacheKvEntity;
import com.easy.query.cache.core.CacheMultiEntity;
import com.easy.query.cache.core.EasyCacheClient;
import com.easy.query.cache.core.EasyCacheStorageOption;
import com.easy.query.cache.core.impl.all.DefaultAllCacheQueryable;
import com.easy.query.cache.core.impl.kv.DefaultKvCacheQueryable;
import com.easy.query.cache.core.impl.multi.DefaultMultiCacheQueryable;
import com.easy.query.cache.core.queryable.AllCacheQueryable;
import com.easy.query.cache.core.queryable.KvCacheQueryable;
import com.easy.query.cache.core.queryable.MultiCacheQueryable;

/**
 * create time 2024/1/24 22:00
 * 文件说明
 *
 * @author xuejiaming
 */
public class DefaultEasyCacheClient implements EasyCacheClient {
    private final EasyCacheStorageOption easyCacheStorageOption;

    public DefaultEasyCacheClient(EasyCacheStorageOption easyCacheStorageOption){

        this.easyCacheStorageOption = easyCacheStorageOption;
    }
    @Override
    public <T extends CacheKvEntity> KvCacheQueryable<T> kvStorage(Class<T> entityClass) {
        return new DefaultKvCacheQueryable<>(easyCacheStorageOption,entityClass);
    }

    @Override
    public <T extends CacheMultiEntity> MultiCacheQueryable<T> multiStorage(Class<T> entityClass) {
        return new DefaultMultiCacheQueryable<>(easyCacheStorageOption,entityClass);
    }

    @Override
    public <T extends CacheAllEntity> AllCacheQueryable<T> allStorage(Class<T> entityClass) {
        return new DefaultAllCacheQueryable<>(easyCacheStorageOption,entityClass);
    }
}
