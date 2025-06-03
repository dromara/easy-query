package com.easy.query.test.cache;

import com.easy.query.cache.core.CacheAllEntity;
import com.easy.query.cache.core.CacheKvEntity;
import com.easy.query.cache.core.EasyCacheIndex;
import com.easy.query.cache.core.EasyCacheManager;
import com.easy.query.cache.core.EasyCacheOption;
import com.easy.query.cache.core.Pair;
import com.easy.query.cache.core.annotation.CacheEntitySchema;
import com.easy.query.cache.core.base.CacheMethodEnum;
import com.easy.query.cache.core.base.ClearParameter;
import com.easy.query.core.api.client.EasyQueryClient;
import com.easy.query.core.exception.EasyQueryInvalidOperationException;
import com.easy.query.core.metadata.ColumnMetadata;
import com.easy.query.core.metadata.EntityMetadata;
import com.easy.query.core.metadata.EntityMetadataManager;
import com.easy.query.core.util.EasyClassUtil;
import com.easy.query.core.util.EasyCollectionUtil;
import com.easy.query.core.util.EasyStringUtil;
import org.redisson.api.RBucket;
import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;

/**
 * create time 2024/1/25 16:16
 * 文件说明
 *
 * @author xuejiaming
 */
public class DefaultEasyRedisManager implements EasyCacheManager {
    private final RedissonClient redissonClient;
    private final EasyQueryClient easyQueryClient;
    private final EntityMetadataManager entityMetadataManager;
    private final EasyCacheOption easyCacheOption;
    private final long expireMillisSeconds;
    private final long valueNullExpireMillisSeconds;

    public DefaultEasyRedisManager(RedissonClient redissonClient, EasyQueryClient easyQueryClient, EasyCacheOption easyCacheOption) {
        this.redissonClient = redissonClient;
        this.easyQueryClient = easyQueryClient;
        this.easyCacheOption = easyCacheOption;
        this.entityMetadataManager = easyQueryClient.getRuntimeContext().getEntityMetadataManager();
        this.expireMillisSeconds = easyCacheOption.getExpireMillisSeconds();
        this.valueNullExpireMillisSeconds = easyCacheOption.getValueNullExpireMillisSeconds();
    }

    @Override
    public <T> List<Pair<String, T>> cache(Class<?> entityClass,Class<T> clazz, String entityKeyPrefix, String conditionKey, Set<String> ids, Function<Collection<String>, List<Pair<String, T>>> getDataFunc) {
        if (EasyCollectionUtil.isEmpty(ids)) {
            return new ArrayList<>(0);
        }
        if (expireMillisSeconds <= 0) {
            return getDataFunc.apply(ids);
        }
        HashMap<String, Pair<String, T>> ret = new HashMap<>(ids.size());
        Set<String> notExistsIds = new HashSet<>();
        for (String id : ids) {
            String realEntityKey = getRealEntityKey(id, entityKeyPrefix);
            RMap<String, String> map = redissonClient.getMap(realEntityKey);
            String cacheItemJson = map.get(conditionKey);
            if (cacheItemJson != null) {
                CacheItem cacheItem = JsonUtil.jsonStr2Object(cacheItemJson, CacheItem.class);
                if (!cacheItem.isExpired() && cacheItem.hasJson()) {
                    String json = cacheItem.getJson();
                    if (EasyStringUtil.isNotBlank(json)) {
                        T entity = JsonUtil.jsonStr2Object(json, clazz);
                        ret.put(id, new Pair<>(id, entity));
                    } else {
                        ret.put(id, new Pair<>(id, null));
                    }
                    continue;
                }
            }
            notExistsIds.add(id);
        }
        if (!notExistsIds.isEmpty()) {

            Collection<Pair<String, T>> datas = getDataFunc.apply(notExistsIds);

            for (Pair<String, T> data : datas) {
                String id = data.getObject1();
                if (!notExistsIds.contains(id)) {
                    throw new EasyQueryInvalidOperationException("use cache plz confirm getDataFunc result has cacheId value: " + id + " exists in input parameters: " + EasyStringUtil.join(notExistsIds, ",", true));
                }
                long expired = expireMillisSeconds;
                if (data.getObject2() instanceof EasyCacheIndex) {
                    EasyCacheIndex object2 = (EasyCacheIndex) data.getObject2();
                    if (EasyCollectionUtil.isEmpty(object2.getIndex())) {
                        expired = valueNullExpireMillisSeconds;
                    }
                }
                ret.put(id, new Pair<>(data.getObject1(), data.getObject2()));
                String json = JsonUtil.object2JsonStr(data.getObject2());
                String realEntityKey = getRealEntityKey(id, entityKeyPrefix);
                RMap<String, String> entityJsonMap = redissonClient.getMap(realEntityKey);
                boolean mapExists = !entityJsonMap.isExists();

                CacheItem cacheItem = new CacheItem();
                cacheItem.setExpire(System.currentTimeMillis() + expired);
                cacheItem.setJson(json);
                entityJsonMap.put(conditionKey, JsonUtil.object2JsonStr(cacheItem));
                if (!mapExists) {
                    entityJsonMap.expire(Duration.ofMillis(expireMillisSeconds));
                }
                notExistsIds.remove(id);
            }
            for (String cacheId : notExistsIds) {
                String realEntityKey = getRealEntityKey(cacheId, entityKeyPrefix);
                RMap<String, String> entityJsonMap = redissonClient.getMap(realEntityKey);
                boolean mapExists = !entityJsonMap.isExists();
                CacheItem cacheItem = new CacheItem();
                cacheItem.setExpire(System.currentTimeMillis() + valueNullExpireMillisSeconds);
                cacheItem.setJson(null);
                if (!mapExists) {
                    entityJsonMap.expire(Duration.ofMillis(expireMillisSeconds));
                }
                entityJsonMap.put(conditionKey, JsonUtil.object2JsonStr(cacheItem));
            }
        }
        return new ArrayList<>(ret.values());
    }

    private String getRealEntityKey(String id, String entityKey) {
        return entityKey + ":" + id;
    }

    @Override
    public void clear(ClearParameter clearParameter) {
        LocalDateTime now = LocalDateTime.now().plusSeconds(1);


        List<EntityMetadata> entityMetadataList = entityMetadataManager.getEntityMetadataList(clearParameter.getTableName());
        if (entityMetadataList != null) {
            for (EntityMetadata entityMetadata : entityMetadataList) {
                clear0(clearParameter, now, entityMetadata);
            }
        }
    }

    protected void clear0(ClearParameter clearParameter, LocalDateTime now, EntityMetadata entityMetadata) {
        CacheMethodEnum clearMethod = clearParameter.getClearMethod();

        Class<?> entityClass = entityMetadata.getEntityClass();
        String entityKey = easyCacheOption.getEntityKey(entityClass);
        if (CacheMethodEnum.INSERT == clearMethod) {
            if (CacheKvEntity.class.isAssignableFrom(entityClass)) {
                String cacheId = clearParameter.getCacheId();
                String realEntityKey = getRealEntityKey(cacheId, entityKey);
                redissonClient.getBucket(realEntityKey).delete();
            } else if (CacheAllEntity.class.isAssignableFrom(entityClass)) {
                String realEntityKey = getRealEntityKey(easyCacheOption.getCacheIndex(), entityKey);
                redissonClient.getBucket(realEntityKey).delete();
            }
        } else if (CacheMethodEnum.UPDATE == clearMethod) {

            String cacheId = clearParameter.getCacheId();
            if (EasyStringUtil.isNotBlank(cacheId)) {
                String realEntityKey = getRealEntityKey(cacheId, entityKey);
                redissonClient.getBucket(realEntityKey).delete();
            } else {
                LocalDateTime beforeTime = clearParameter.getBeforeTime().plusSeconds(-1);
                CacheEntitySchema cacheEntitySchema = EasyClassUtil.getAnnotation(entityClass, CacheEntitySchema.class);
                if (cacheEntitySchema == null) {
                    throw new EasyQueryInvalidOperationException("entity:[%s] not found annotation @CacheEntitySchema");
                }
                List<?> list = easyQueryClient.queryable(entityClass)
                        .where(o -> o.ge("updateTime", beforeTime).le("updateTime", now))
                        .select(o -> {
                            o.column(cacheEntitySchema.value());
                        })
                        .toList();
                ColumnMetadata columnMetadata = entityMetadata.getColumnNotNull(cacheEntitySchema.value());
                for (Object o : list) {
                    String cacheKey = getCacheEntityCacheKey(columnMetadata, o, cacheEntitySchema);
                    String realEntityKey = getRealEntityKey(cacheKey, entityKey);
                    redissonClient.getBucket(realEntityKey).delete();
                }
            }
        } else if (CacheMethodEnum.DELETE == clearMethod) {
            String cacheId = clearParameter.getCacheId();
            if (EasyStringUtil.isNotBlank(cacheId)) {
                redissonClient.getBucket(getRealEntityKey(cacheId, entityKey)).delete();
                if (CacheAllEntity.class.isAssignableFrom(entityClass)) {
                    String cacheIndexKey = easyCacheOption.getCacheIndex();
                    redissonClient.getBucket(getRealEntityKey(cacheIndexKey, entityKey)).delete();
                }
            } else {
                if (CacheAllEntity.class.isAssignableFrom(entityClass)) {
                    String cacheIndexKey = easyCacheOption.getCacheIndex();
                    redissonClient.getBucket(getRealEntityKey(cacheIndexKey, entityKey)).delete();
                }
                CacheEntitySchema cacheEntitySchema = EasyClassUtil.getAnnotation(entityClass, CacheEntitySchema.class);
                if (cacheEntitySchema == null) {
                    throw new EasyQueryInvalidOperationException("entity:[%s] not found annotation @CacheEntitySchema");
                }
//                CacheMultiEntitySchema cacheMultiEntitySchema = EasyClassUtil.getAnnotation(entityClass, CacheMultiEntitySchema.class);
                LocalDateTime beforeTime = clearParameter.getBeforeTime().plusSeconds(-1);
                List<?> list = easyQueryClient.queryable(entityClass)
                        .disableLogicDelete()
                        .where(o -> o.ge("updateTime", beforeTime).le("updateTime", now).eq("deleted", true))
                        .select(t -> {
                            if (cacheEntitySchema.value() != null) {
                                t.column(cacheEntitySchema.value());
                            }
//                            if (cacheMultiEntitySchema.value() != null) {
//                                t.column(cacheMultiEntitySchema.value());
//                            }
                        })
                        .toList();
                ColumnMetadata columnMetadata = entityMetadata.getColumnNotNull(cacheEntitySchema.value());
                for (Object o : list) {
                    String cacheKey = getCacheEntityCacheKey(columnMetadata, o, cacheEntitySchema);
                    redissonClient.getBucket(getRealEntityKey(cacheKey, entityKey)).delete();
                }
            }
        }
    }

    private String getCacheEntityCacheKey(ColumnMetadata columnMetadata, Object cacheEntity, CacheEntitySchema cacheEntitySchema) {
        Object val = columnMetadata.getGetterCaller().apply(cacheEntity);
        if (val == null) {
            throw new IllegalArgumentException(String.format("CacheEntitySchema.value [%s] is null", cacheEntitySchema.value()));
        }
        return val.toString();
    }
}
