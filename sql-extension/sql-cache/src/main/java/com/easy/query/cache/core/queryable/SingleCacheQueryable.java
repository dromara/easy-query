package com.easy.query.cache.core.queryable;



import com.easy.query.cache.core.CacheEntity;
import com.easy.query.cache.core.exceptions.EasyQueryCacheFirstNotNullException;

import java.util.Collection;
import java.util.List;

/**
 * create time 2023/5/16 10:35
 * 文件说明
 *
 * @author xuejiaming
 */
public interface SingleCacheQueryable<TEntity extends CacheEntity> extends CacheQueryable {

    /**
     * 不存在就返回null
     *
     * @param id
     * @return
     */
    TEntity firstOrNull(String id);

    /**
     * 条件成立查询否则返回null
     *
     * @param condition
     * @param id
     * @return
     */
    default TEntity firstOrNull(boolean condition, String id) {
        if (condition) {
            return firstOrNull(id);
        }
        return null;
    }

    default TEntity firstNotNull(String id, String error) {
        return firstNotNull(id, error, null);
    }

    default TEntity firstNotNull(String id, String error, String code) {
        TEntity one = firstOrNull(id);
        if (null == one) {
            throw new EasyQueryCacheFirstNotNullException(code, error);
        }
        return one;
    }


    /**
     * 不存在返回def
     *
     * @param id
     * @param def
     * @return
     */
    TEntity firstOrDefault(String id, TEntity def);

    List<TEntity> getIn(Collection<String> ids);

    boolean any(String id);
}
