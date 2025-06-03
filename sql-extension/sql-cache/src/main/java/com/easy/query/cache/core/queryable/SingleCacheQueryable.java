package com.easy.query.cache.core.queryable;



import com.easy.query.cache.core.CacheEntity;
import com.easy.query.core.expression.parser.core.available.RuntimeContextAvailable;

import java.util.Collection;
import java.util.List;

/**
 * create time 2023/5/16 10:35
 * 文件说明
 *
 * @author xuejiaming
 */
public interface SingleCacheQueryable<TEntity extends CacheEntity> extends CacheQueryable, RuntimeContextAvailable {

    /**
     * 不存在就返回null
     *
     * @param id
     * @return
     */
    TEntity singleOrNull(String id);


    /**
     * 条件成立查询否则返回null
     *
     * @param condition
     * @param id
     * @return
     */
    default TEntity singleOrNull(boolean condition, String id) {
        if (condition) {
            return singleOrNull(id);
        }
        return null;
    }

    default TEntity singleNotNull(String id, String error) {
        return singleNotNull(id, error, null);
    }

    default TEntity singleNotNull(String id, String error, String code) {
        TEntity one = singleOrNull(id);
        if (null == one) {
            throw getRuntimeContext().getAssertExceptionFactory().createSingleNotNullException(null, error, code);
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
    TEntity singleOrDefault(String id, TEntity def);

    List<TEntity> toList(Collection<String> ids);

    boolean any(String id);
}
