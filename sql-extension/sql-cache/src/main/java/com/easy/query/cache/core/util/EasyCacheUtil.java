package com.easy.query.cache.core.util;

import com.easy.query.cache.core.CacheEntity;

/**
 * create time 2024/1/26 23:42
 * 文件说明
 *
 * @author xuejiaming
 */
public class EasyCacheUtil {
    public static boolean isCacheEntity(Class<?> entityClass) {
        return CacheEntity.class.isAssignableFrom(entityClass);
    }
}
