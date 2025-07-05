package com.easy.query.cache.core.common;

/**
 * create time 2025/7/5 13:18
 * 文件说明
 *
 * @author xuejiaming
 */
public class DefaultCacheKey implements CacheKey {
    private final Class<?> entityClass;
    private final String key;

    public DefaultCacheKey(Class<?> entityClass, String key) {
        this.entityClass = entityClass;
        this.key = key;
    }

    @Override
    public Class<?> getEntityClass() {
        return entityClass;
    }

    @Override
    public String getKey() {
        return key;
    }
}
