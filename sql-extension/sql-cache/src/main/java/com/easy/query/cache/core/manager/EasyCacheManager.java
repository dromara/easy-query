package com.easy.query.cache.core.manager;

import com.easy.query.cache.core.common.CacheItem;
import com.easy.query.cache.core.common.CacheKey;
import org.jetbrains.annotations.Nullable;

/**
 * create time 2025/7/5 14:57
 * 文件说明
 *
 * @author xuejiaming
 */
public interface EasyCacheManager {
    @Nullable
    CacheItem getCacheItem(String cacheKey, String conditionKey, Class<?> entityClass);

    void setCacheItem(String cacheKey, String conditionKey, CacheItem cacheItem, Class<?> entityClass, long expireMillisSeconds);

    <T> String toJson(T object);

    <T> T fromJson(String json, Class<T> clazz);

    void deleteBy(CacheKey cacheKey);
}
