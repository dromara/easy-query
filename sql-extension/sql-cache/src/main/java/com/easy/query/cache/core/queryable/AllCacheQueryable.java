package com.easy.query.cache.core.queryable;

import com.easy.query.cache.core.CacheEntity;
import com.easy.query.core.api.pagination.EasyPageResult;
import com.easy.query.core.expression.lambda.SQLActionExpression1;
import com.easy.query.core.proxy.ProxyEntity;
import com.easy.query.core.proxy.ProxyEntityAvailable;

import java.util.List;

/**
 * create time 2023/5/16 10:12
 * 文件说明
 *
 * @author xuejiaming
 */
public interface AllCacheQueryable<T1Proxy extends ProxyEntity<T1Proxy, TEntity>, TEntity extends CacheEntity> extends SingleCacheQueryable<TEntity>, CacheFilterInterceptorQueryable<TEntity,AllCacheQueryable<T1Proxy,TEntity>> {
    List<TEntity> toList();
    List<String> toIndexList();
    int count();
    EasyPageResult<TEntity> toPageResult(int pageIndex, int pageSize);

    AllCacheQueryable<T1Proxy, TEntity> where(SQLActionExpression1<T1Proxy> whereExpression);

    AllCacheQueryable<T1Proxy, TEntity> where(boolean condition, SQLActionExpression1<T1Proxy> whereExpression);
}
