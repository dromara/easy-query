package com.easy.query.cache.core.queryable;

import com.easy.query.cache.core.CacheEntity;
import com.easy.query.core.api.pagination.EasyPageResult;

import java.util.List;

/**
 * create time 2023/5/16 10:12
 * 文件说明
 *
 * @author xuejiaming
 */
public interface AllCacheQueryable<TEntity extends CacheEntity> extends SingleCacheQueryable<TEntity>, CacheFilterInterceptorQueryable<TEntity,AllCacheQueryable<TEntity>> {
    List<TEntity> toList();
    List<String> toIndexList();
    int count();
    EasyPageResult<TEntity> toPageResult(int pageIndex, int pageSize);
}
