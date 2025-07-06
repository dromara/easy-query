package com.easy.query.cache.core.util;

import com.easy.query.cache.core.CacheAllEntity;
import com.easy.query.cache.core.CacheEntity;
import com.easy.query.cache.core.CacheKvEntity;
import com.easy.query.cache.core.annotation.CacheEntitySchema;
import com.easy.query.core.exception.EasyQueryInvalidOperationException;
import com.easy.query.core.util.EasyClassUtil;
import com.easy.query.core.util.EasyStringUtil;

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
    public static boolean isCacheKvEntity(Class<?> entityClass) {
        return CacheKvEntity.class.isAssignableFrom(entityClass);
    }
    public static boolean isCacheAllEntity(Class<?> entityClass) {
        return CacheAllEntity.class.isAssignableFrom(entityClass);
    }

    public static CacheEntitySchema getCacheEntitySchema(Class<?> entityClass) {
        CacheEntitySchema cacheEntitySchema = EasyClassUtil.getAnnotation(entityClass, CacheEntitySchema.class);
        if (cacheEntitySchema == null) {
            throw new EasyQueryInvalidOperationException("entity:[%s] not found annotation @CacheEntitySchema");
        }
        if(EasyStringUtil.isNotBlank(cacheEntitySchema.value())){
            throw new EasyQueryInvalidOperationException("entity:[%s] @CacheEntitySchema value must be empty");
        }
        return cacheEntitySchema;
    }
}
