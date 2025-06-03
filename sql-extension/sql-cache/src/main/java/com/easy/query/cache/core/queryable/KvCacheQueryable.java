package com.easy.query.cache.core.queryable;


import com.easy.query.cache.core.CacheEntity;

/**
 * create time 2023/5/16 10:12
 * 文件说明
 *
 * @author xuejiaming
 */
public interface KvCacheQueryable<TEntity extends CacheEntity> extends SingleCacheQueryable<TEntity>, CacheFilterInterceptorQueryable<TEntity,KvCacheQueryable<TEntity>> {
}
