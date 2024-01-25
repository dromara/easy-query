package com.easy.query.cache.core.queryable;


import com.easy.query.cache.core.CacheEntity;
import com.easy.query.cache.core.base.CachePredicate;

/**
 * create time 2023/6/19 09:39
 * 文件说明
 *
 * @author xuejiaming
 */
public interface CacheFilterQueryable<TEntity  extends CacheEntity,TChain> extends CacheQueryable {
   default TChain filter(CachePredicate<TEntity> predicate){
       return filter(true,predicate);
   }
    TChain filter(boolean condition,CachePredicate<TEntity> predicate);
}
