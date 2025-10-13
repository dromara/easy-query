package com.easy.query.cache.core.queryable;


import com.easy.query.cache.core.CacheEntity;
import com.easy.query.core.expression.lambda.SQLActionExpression1;
import com.easy.query.core.proxy.ProxyEntity;
import com.easy.query.core.proxy.ProxyEntityAvailable;

/**
 * create time 2023/5/16 10:12
 * 文件说明
 *
 * @author xuejiaming
 */
public interface KvCacheQueryable<T1Proxy extends ProxyEntity<T1Proxy, TEntity>, TEntity extends CacheEntity> extends SingleCacheQueryable<TEntity>, CacheFilterInterceptorQueryable<TEntity, KvCacheQueryable<T1Proxy, TEntity>> {

    KvCacheQueryable<T1Proxy, TEntity> where(SQLActionExpression1<T1Proxy> whereExpression);

    KvCacheQueryable<T1Proxy, TEntity> where(boolean condition, SQLActionExpression1<T1Proxy> whereExpression);
}
