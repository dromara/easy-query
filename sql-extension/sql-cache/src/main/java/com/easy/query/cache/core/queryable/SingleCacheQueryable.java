package com.easy.query.cache.core.queryable;


import com.easy.query.cache.core.CacheEntity;
import com.easy.query.core.expression.parser.core.available.RuntimeContextAvailable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.List;
import java.util.Map;

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
    @Nullable
    TEntity singleOrNull(String id);

    @Deprecated
    @Nullable
    default TEntity firstOrNull(String id) {
        return singleOrNull(id);
    }

    @NotNull
    default TEntity singleNotNull(String id, String error) {
        return singleNotNull(id, error, null);
    }

    @NotNull
    default TEntity firstNotNull(String id, String error) {
        return singleNotNull(id, error, null);
    }

    default TEntity singleNotNull(String id, String error, String code) {
        TEntity one = singleOrNull(id);
        if (null == one) {
            throw getRuntimeContext().getAssertExceptionFactory().createSingleNotNullException(null, error, code);
        }
        return one;
    }

    @NotNull
    @Deprecated
    default TEntity firstNotNull(String id, String error, String code) {
        return singleNotNull(id, error, code);
    }


    /**
     * 不存在返回def
     *
     * @param id
     * @param def
     * @return
     */
    TEntity singleOrDefault(String id, TEntity def);

    @Deprecated
    default TEntity firstOrDefault(String id, TEntity def) {
        return singleOrDefault(id, def);
    }

    List<TEntity> toList(Collection<String> ids);
    Map<String,TEntity> toMap(Collection<String> ids);
    Map<String,TEntity> toLinkedMap(Collection<String> ids);

    boolean any(String id);
}
