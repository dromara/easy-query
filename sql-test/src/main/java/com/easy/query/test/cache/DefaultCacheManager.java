package com.easy.query.test.cache;

import com.easy.query.cache.core.EasyCacheOption;
import com.easy.query.cache.core.common.CacheItem;
import com.easy.query.cache.core.common.CacheKey;
import com.easy.query.cache.core.manager.AbstractCacheManager;
import com.easy.query.core.exception.EasyQueryInvalidOperationException;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;

import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

/**
 * create time 2025/7/5 16:21
 * 文件说明
 *
 * @author xuejiaming
 */
public class DefaultCacheManager extends AbstractCacheManager {
    private final RedissonClient redissonClient;
    public final Cache<String, Map<String, CacheItem>> caffeineCache;

    public DefaultCacheManager(RedissonClient redissonClient, EasyCacheOption easyCacheOption, CacheMultiOption cacheMultiOption) {
        super(easyCacheOption);
        this.redissonClient = redissonClient;
        this.caffeineCache = Caffeine.newBuilder()
                //初始数量
                .initialCapacity(cacheMultiOption.getInitialCapacity())
                //最大条数
                .maximumSize(cacheMultiOption.getMaximumSize())
                //最后一次写操作后经过指定时间过期
                .expireAfterWrite(cacheMultiOption.getExpireMillisSeconds(), TimeUnit.MILLISECONDS)
                .build();
    }

    @Nullable
    @Override
    public CacheItem getCacheItem(String cacheKey, String conditionKey, Class<?> entityClass) {
        String entityCacheKey = getCacheKey(entityClass, cacheKey);
        return getCacheItem(entityCacheKey, conditionKey, CacheUtil.isMultiCacheEntity(entityClass));
    }
    private CacheItem getCacheItem(String key, String conditionKey, boolean multiCacheEntity) {
        if (multiCacheEntity) {
            Map<String, CacheItem> cacheItemMap = getMemoryCache(key);
            CacheItem cacheItem = cacheItemMap.get(conditionKey);
            if (cacheItem == null) {
                CacheItem redissonCacheItem = getRedissonCacheItem(key, conditionKey);
                if (redissonCacheItem != null && !redissonCacheItem.cacheIsExpired()) {
                    cacheItemMap.put(conditionKey, redissonCacheItem);
                    return redissonCacheItem;
                }
            }
            return cacheItem;
        }
        return getRedissonCacheItem(key, conditionKey);
    }

    private CacheItem getRedissonCacheItem(String key, String conditionKey) {
        RMap<String, String> map = redissonClient.getMap(key);
        String cacheItemJson = map.get(conditionKey);
        if (cacheItemJson != null) {
            return fromJson(cacheItemJson, CacheItem.class);
        }
        return null;
    }

    @NotNull
    private Map<String, CacheItem> getMemoryCache(String key) {
        Map<String, CacheItem> cacheItemMap = caffeineCache.get(key, k -> {
            return new ConcurrentHashMap<>();
        });
        if (cacheItemMap == null) {
            throw new EasyQueryInvalidOperationException("cache item is null");
        }
        return cacheItemMap;
    }

    @Override
    public void setCacheItem(String cacheKey, String conditionKey, CacheItem cacheItem, Class<?> entityClass, long expireMillisSeconds) {
        String entityCacheKey = getCacheKey(entityClass, cacheKey);
        boolean multiCacheEntity = CacheUtil.isMultiCacheEntity(entityClass);
        RMap<String, String> entityJsonMap = redissonClient.getMap(entityCacheKey);
        boolean mapExists = entityJsonMap.isExists();
        if (multiCacheEntity) {
            Map<String, CacheItem> cacheItemMap = getMemoryCache(entityCacheKey);
            cacheItemMap.put(conditionKey, cacheItem);
        }
        entityJsonMap.put(conditionKey, toJson(cacheItem));
        if (!mapExists) {
            entityJsonMap.expire(Duration.ofMillis(expireMillisSeconds));
        }
    }

    @Override
    public <T> String toJson(T object) {
        return JsonUtil.object2JsonStr(object);
    }

    @Override
    public <T> T fromJson(String json, Class<T> clazz) {
        return JsonUtil.jsonStr2Object(json,clazz);
    }

    @Override
    public void deleteBy(CacheKey cacheKey) {
        boolean multiCacheEntity = CacheUtil.isMultiCacheEntity(cacheKey.getEntityClass());
        String deleteCacheKey = getCacheKey(cacheKey.getEntityClass(), cacheKey.getKey());
        if (multiCacheEntity) {
            caffeineCache.invalidate(deleteCacheKey);
        }
        redissonClient.getMap(deleteCacheKey).delete();
    }
}
