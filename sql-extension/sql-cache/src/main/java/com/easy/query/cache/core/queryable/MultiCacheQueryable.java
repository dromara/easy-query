package com.easy.query.cache.core.queryable;

import com.easy.query.cache.core.CacheMultiEntity;
import com.easy.query.cache.core.exceptions.EasyQueryCacheFirstNotNullException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * create time 2023/5/16 10:13
 * 文件说明
 *
 * @author xuejiaming
 */
public interface MultiCacheQueryable<TEntity extends CacheMultiEntity> extends CacheQueryable,CacheFilterQueryable<TEntity,MultiCacheQueryable<TEntity>> {
    /**
     * 不存在就返回null
     * @param aggregateId
     * @return
     */
    TEntity firstOrNull(String aggregateId, String id);
    boolean any(String aggregateId,String id);
    boolean any(String aggregateId);

    default TEntity firstOrNull(boolean condition, String aggregateId, String id){
        if(condition){
            return firstOrNull(aggregateId,id);
        }
        return null;
    }
    default TEntity firstNotNull(String aggregateId, String id, String error){
        return firstNotNull(aggregateId,id,error, null);
    }
    default TEntity firstNotNull(String aggregateId, String id, String error, String code){
        TEntity one = firstOrNull(aggregateId,id);
        if(null==one)
        {
            throw new EasyQueryCacheFirstNotNullException(code,error);
        }
        return one;
    }
    /**
     * 不存在返回def
     * @param id
     * @param def
     * @return
     */
    TEntity firstOrDefault(String aggregateId, String id, TEntity def);
    List<TEntity> getIn(String aggregateId, Collection<String> ids);
    List<TEntity> getAll(String aggregateId);
   default List<TEntity> getAll(Collection<String> aggregateIds){
       ArrayList<TEntity> result = new ArrayList<>();
       for (String aggregateId : aggregateIds) {
           result.addAll(getAll(aggregateId));
       }
       return result;
   }
    List<String> getAllIndex(String aggregateId);
}
