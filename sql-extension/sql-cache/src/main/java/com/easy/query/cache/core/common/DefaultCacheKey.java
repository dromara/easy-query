package com.easy.query.cache.core.common;

import com.easy.query.cache.core.base.CacheMethodEnum;

import java.util.Objects;

/**
 * create time 2025/7/5 13:18
 * 文件说明
 *
 * @author xuejiaming
 */
public class DefaultCacheKey implements CacheKey {
    private final CacheMethodEnum cacheMethod;
    private final Class<?> entityClass;
    private final String key;

    public DefaultCacheKey(CacheMethodEnum cacheMethod,Class<?> entityClass, String key) {
        this.cacheMethod = cacheMethod;
        this.entityClass = entityClass;
        this.key = key;
    }

    @Override
    public Class<?> getEntityClass() {
        return entityClass;
    }

    @Override
    public CacheMethodEnum getCacheMethod() {
        return cacheMethod;
    }

    @Override
    public String getKey() {
        return key;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        DefaultCacheKey cacheKey = (DefaultCacheKey) o;
        return cacheMethod == cacheKey.cacheMethod && Objects.equals(entityClass, cacheKey.entityClass) && Objects.equals(key, cacheKey.key);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cacheMethod, entityClass, key);
    }
}
