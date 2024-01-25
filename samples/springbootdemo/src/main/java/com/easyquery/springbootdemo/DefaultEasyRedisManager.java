package com.easyquery.springbootdemo;

import com.easy.query.cache.core.EasyCacheIndex;
import com.easy.query.cache.core.EasyRedisManager;
import com.easy.query.cache.core.Pair;
import com.easy.query.core.util.EasyCollectionUtil;
import com.easy.query.core.util.EasyStringUtil;
import com.easyquery.springbootdemo.json.JsonUtil;
import org.apache.tomcat.util.buf.StringUtils;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;

import java.time.Duration;
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

    public DefaultEasyRedisManager(RedissonClient redissonClient) {
        this.redissonClient = redissonClient;
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
            if (entityJsonBucket.isExists()) {
                String json = entityJsonBucket.get();
                if (EasyStringUtil.isNotBlank(json)) {
                    T entity = JsonUtil.jsonStr2Object(json, clazz);
                    ret.put(id, new Pair<>(id, entity));
                }else{
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
                    throw new RuntimeException("使用 cache 请确认 getDataFunc 返回值 EntityCacheOutput[] 中的 cacheId 值: " + id + " 存在于 输入参数: " + StringUtils.join(notExistsIds, ','));
                }
                long expired = timeoutMillisSeconds;
                if(data.getObject2() instanceof EasyCacheIndex){
                    EasyCacheIndex object2 = (EasyCacheIndex) data.getObject2();
                    if(EasyCollectionUtil.isEmpty(object2.getIndex())){
                        expired=nullValueTimeoutMillisSeconds;
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
}
