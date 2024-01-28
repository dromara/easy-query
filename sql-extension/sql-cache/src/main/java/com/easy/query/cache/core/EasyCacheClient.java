package com.easy.query.cache.core;


import com.easy.query.cache.core.base.ClearParameter;
import com.easy.query.cache.core.queryable.AllCacheQueryable;
import com.easy.query.cache.core.queryable.KvCacheQueryable;
import com.easy.query.cache.core.queryable.MultiCacheQueryable;

/**
 * create time 2023/5/16 10:11
 * 文件说明
 *
 * @author xuejiaming
 */
public interface EasyCacheClient {
    <T> T getService(Class<T> serviceType);
    <T extends CacheKvEntity> KvCacheQueryable<T> kvStorage(Class<T> entityClass);
    <T extends CacheMultiEntity> MultiCacheQueryable<T> multiStorage(Class<T> entityClass);
    <T extends CacheAllEntity> AllCacheQueryable<T> allStorage(Class<T> entityClass);

}
