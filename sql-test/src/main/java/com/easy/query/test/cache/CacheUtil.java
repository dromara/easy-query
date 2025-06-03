package com.easy.query.test.cache;

import com.easy.query.cache.core.CacheEntity;

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
    public static boolean isMultiCacheEntity(Class<?> entityClass) {
        return CacheMultiLevel.class.isAssignableFrom(entityClass);
    }
}
