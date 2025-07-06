package com.easy.query.test.cache;

import com.easy.query.cache.core.CacheAllEntity;
import com.easy.query.cache.core.CacheEntity;
import com.easy.query.cache.core.CacheKvEntity;

/**
 * create time 2023/5/16 15:54
 * 文件说明
 *
 * @author xuejiaming
 */
public class CacheUtil {

    public static boolean isCacheEntity(Class<?> entityClass) {
        return CacheEntity.class.isAssignableFrom(entityClass);
    }
    public static boolean isCacheKvEntity(Class<?> entityClass) {
        return CacheKvEntity.class.isAssignableFrom(entityClass);
    }
    public static boolean isCacheAllEntity(Class<?> entityClass) {
        return CacheAllEntity.class.isAssignableFrom(entityClass);
    }
    public static boolean isMultiCacheEntity(Class<?> entityClass) {
        return CacheMultiLevel.class.isAssignableFrom(entityClass);
    }
}
