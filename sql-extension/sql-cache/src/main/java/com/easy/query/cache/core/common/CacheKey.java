package com.easy.query.cache.core.common;

/**
 * create time 2025/7/5 16:09
 * 文件说明
 *
 * @author xuejiaming
 */
public interface CacheKey {
    Class<?> getEntityClass();
    String getKey();
}
