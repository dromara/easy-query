package com.easy.query.cache.core.queryable;


import com.easy.query.cache.core.CacheEntity;
import com.easy.query.cache.core.base.CachePredicate;

/**
 * create time 2023/6/19 09:39
 * 文件说明
 *
 * @author xuejiaming
 */
public interface CacheFilterInterceptorQueryable<TEntity  extends CacheEntity,TChain> extends CacheQueryable {

    /**
     * 当前表达式不使用拦截器,并且删除之前手动指定的 {@link #noInterceptor(String name)}、{@link #useInterceptor(String name)}
     * @return
     */
    TChain noInterceptor();

    /**
     * 使用某个拦截器
     * @param name 拦截器名称
     * @return
     */
    TChain useInterceptor(String name);

    /**
     * 不使用某个拦截器
     * @param name 拦截器名称
     * @return
     */
    TChain noInterceptor(String name);

    /**
     * 当前表达式启用拦截器,并且删除之前手动指定的 {@link #noInterceptor(String name)}、{@link #useInterceptor(String name)}
     * @return
     */
    TChain useInterceptor();
    default TChain filter(CachePredicate<TEntity> predicate){
        return filter(true,predicate);
    }
    TChain filter(boolean condition,CachePredicate<TEntity> predicate);
}
