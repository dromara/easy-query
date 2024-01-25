package com.easyquery.springbootdemo.cache;

import com.easy.query.cache.core.CacheAllEntity;
import com.easy.query.cache.core.CacheEntity;
import com.easy.query.cache.core.CacheKvEntity;
import com.easy.query.cache.core.CacheMultiEntity;
import com.easy.query.cache.core.EasyCacheIndex;
import com.easy.query.cache.core.EasyCacheOption;
import com.easy.query.cache.core.EasyRedisManager;
import com.easy.query.cache.core.Pair;
import com.easy.query.cache.core.base.CacheMethodEnum;
import com.easy.query.cache.core.base.ClearParameter;
import com.easy.query.core.api.client.EasyQueryClient;
import com.easy.query.core.util.EasyClassUtil;
import com.easy.query.core.util.EasyCollectionUtil;
import com.easy.query.core.util.EasyStringUtil;
import com.easyquery.springbootdemo.json.JsonUtil;
import org.apache.tomcat.util.buf.StringUtils;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;

/**
 * create time 2024/1/25 16:16
 * 文件说明
 *
 * @author xuejiaming
 */
public class DefaultEasyRedisManager implements EasyRedisManager {
    private final RedissonClient redissonClient;
    private final EasyQueryClient easyQueryClient;
    private final EasyCacheOption easyCacheOption;

    public DefaultEasyRedisManager(RedissonClient redissonClient, EasyQueryClient easyQueryClient, EasyCacheOption easyCacheOption) {
        this.redissonClient = redissonClient;
        this.easyQueryClient = easyQueryClient;
        this.easyCacheOption = easyCacheOption;
    }

    @Override
    public <T> List<Pair<String, T>> cache(Class<T> clazz, String entityKey, Set<String> ids, long timeoutMillisSeconds, long nullValueTimeoutMillisSeconds, Function<Collection<String>, List<Pair<String, T>>> getDataFunc) {
        if (EasyCollectionUtil.isEmpty(ids)) {
            return new ArrayList<>(0);
        }
        if (timeoutMillisSeconds <= 0) {
            return getDataFunc.apply(ids);
        }
        HashMap<String, Pair<String, T>> ret = new HashMap<>(ids.size());
        Set<String> notExistsIds = new HashSet<>();
        for (String id : ids) {
            String realEntityKey = getRealEntityKey(id, entityKey);
            RBucket<String> entityJsonBucket = redissonClient.getBucket(realEntityKey);
            String json = entityJsonBucket.get();
            if(json!=null){
                if (EasyStringUtil.isNotBlank(json)) {
                    T entity = JsonUtil.jsonStr2Object(json, clazz);
                    ret.put(id, new Pair<>(id, entity));
                } else {
                    ret.put(id, new Pair<>(id, null));
                }
                continue;
            }
            notExistsIds.add(id);
        }
        if (notExistsIds.size() > 0) {

            Collection<Pair<String, T>> datas = getDataFunc.apply(notExistsIds);

            for (Pair<String, T> data : datas) {
                String id = data.getObject1();
                if (!notExistsIds.contains(id)) {
                    throw new RuntimeException("使用 cache 请确认 getDataFunc 返回值 中的 cacheId 值: " + id + " 存在于 输入参数: " + StringUtils.join(notExistsIds, ','));
                }
                long expired = timeoutMillisSeconds;
                if (data.getObject2() instanceof EasyCacheIndex) {
                    EasyCacheIndex object2 = (EasyCacheIndex) data.getObject2();
                    if (EasyCollectionUtil.isEmpty(object2.getIndex())) {
                        expired = nullValueTimeoutMillisSeconds;
                    }
                }
                ret.put(id, new Pair<>(data.getObject1(), data.getObject2()));
                String json = JsonUtil.object2JsonStr(data.getObject2());
                String realEntityKey = getRealEntityKey(id, entityKey);
                RBucket<String> entityJsonBucket = redissonClient.getBucket(realEntityKey);
                entityJsonBucket.set(json, Duration.ofMillis(expired));
                notExistsIds.remove(id);
            }
            for (String cacheId : notExistsIds) {
                String realEntityKey = getRealEntityKey(cacheId, entityKey);
                RBucket<String> entityJsonBucket = redissonClient.getBucket(realEntityKey);
                entityJsonBucket.set("", Duration.ofMillis(nullValueTimeoutMillisSeconds));
            }
        }
        return new ArrayList<>(ret.values());

    }

    private String getRealEntityKey(String id, String entityKey) {
        return entityKey + ":" + id;
    }

    @Override
    public void clear(ClearParameter clearParameter) {

        CacheMethodEnum clearMethod = clearParameter.getClearMethod();
        Class<?> entityClass = EasyClassUtil.getClassForName(clearParameter.getEntityName());
        String entityKey = easyCacheOption.getEntityKey(entityClass);
        if (CacheMethodEnum.INSERT == clearMethod) {
            if (CacheKvEntity.class.isAssignableFrom(entityClass)) {
                String cacheId = clearParameter.getCacheId();
                String realEntityKey = getRealEntityKey(cacheId, entityKey);
                redissonClient.getBucket(realEntityKey).delete();
            } else if (CacheMultiEntity.class.isAssignableFrom(entityClass)) {
                String cacheIndexId = clearParameter.getCacheIndexId();
                String realEntityKey = getRealEntityKey(easyCacheOption.getCacheIndex() + ":" + cacheIndexId, entityKey);
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
                LocalDateTime beforeTime = clearParameter.getBeforeTime();
                List<?> list = easyQueryClient.queryable(entityClass)
                        .where(o -> o.ge("updateTime", beforeTime))
                        .toList();
                for (Object o : list) {
                    CacheEntity cacheEntity = (CacheEntity) o;
                    String realEntityKey = getRealEntityKey(cacheEntity.cacheIdValue(), entityKey);
                    redissonClient.getBucket(realEntityKey).delete();
                }
            }
        } else if (CacheMethodEnum.DELETE == clearMethod) {
            String cacheId = clearParameter.getCacheId();
            if (EasyStringUtil.isNotBlank(cacheId)) {
                redissonClient.getBucket(getRealEntityKey(cacheId, entityKey)).delete();
                if (CacheMultiEntity.class.isAssignableFrom(entityClass)) {
                    String cacheIndexId = clearParameter.getCacheIndexId();
                    String cacheIndexKey = easyCacheOption.getCacheIndex() + ":" + cacheIndexId;
                    redissonClient.getBucket(getRealEntityKey(cacheIndexKey, entityKey)).delete();
                }
                if (CacheAllEntity.class.isAssignableFrom(entityClass)) {
                    String cacheIndexKey = easyCacheOption.getCacheIndex();
                    redissonClient.getBucket(getRealEntityKey(cacheIndexKey, entityKey)).delete();
                }
            } else {
                if (CacheAllEntity.class.isAssignableFrom(entityClass)) {
                    String cacheIndexKey = easyCacheOption.getCacheIndex();
                    redissonClient.getBucket(getRealEntityKey(cacheIndexKey, entityKey)).delete();
                }
                LocalDateTime beforeTime = clearParameter.getBeforeTime();
                List<?> list = easyQueryClient.queryable(entityClass)
                        .disableLogicDelete()
                        .where(o -> o.ge("updateTime", beforeTime).eq("deleted", true))
                        .toList();
                for (Object o : list) {
                    CacheEntity cacheEntity = (CacheEntity) o;
                    redissonClient.getBucket(getRealEntityKey(cacheEntity.cacheIdValue(), entityKey)).delete();
                    if (CacheMultiEntity.class.isAssignableFrom(entityClass)) {
                        CacheMultiEntity cacheMultiEntity = (CacheMultiEntity) o;
                        String cacheIndexId = cacheMultiEntity.cacheIndexId();
                        String cacheIndexKey = easyCacheOption.getCacheIndex() + ":" + cacheIndexId;
                        redissonClient.getBucket(getRealEntityKey(cacheIndexKey, entityKey)).delete();
                    }
                }
            }
        }
    }
}
