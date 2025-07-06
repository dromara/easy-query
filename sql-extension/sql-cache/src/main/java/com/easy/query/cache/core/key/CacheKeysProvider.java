package com.easy.query.cache.core.key;

import com.easy.query.cache.core.base.CacheMethodEnum;
import com.easy.query.cache.core.common.CacheKey;
import com.easy.query.core.metadata.EntityMetadata;

import java.time.LocalDateTime;
import java.util.List;

/**
 * create time 2025/7/6 17:19
 * 文件说明
 *
 * @author xuejiaming
 */
public interface CacheKeysProvider {
    List<CacheKey> getCacheKeys(LocalDateTime triggerTime, LocalDateTime receivedTime, EntityMetadata entityMetadata, CacheMethodEnum cacheMethod, String cacheKey);
    List<CacheKey> getCacheKeys(LocalDateTime triggerTime, LocalDateTime receivedTime, String tableName, CacheMethodEnum clearMethod, String cacheKey);
}
