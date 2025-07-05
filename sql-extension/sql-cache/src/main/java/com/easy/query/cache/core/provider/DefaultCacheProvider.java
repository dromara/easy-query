package com.easy.query.cache.core.provider;

import com.easy.query.cache.core.EasyCacheIndex;
import com.easy.query.cache.core.EasyCacheOption;
import com.easy.query.cache.core.Pair;
import com.easy.query.cache.core.common.CacheItem;
import com.easy.query.cache.core.common.CacheKey;
import com.easy.query.cache.core.manager.EasyCacheManager;
import com.easy.query.core.exception.EasyQueryInvalidOperationException;
import com.easy.query.core.util.EasyCollectionUtil;
import com.easy.query.core.util.EasyStringUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;

/**
 * create time 2025/7/5 16:12
 * 文件说明
 *
 * @author xuejiaming
 */
public class DefaultCacheProvider implements EasyCacheProvider {
    private final long expireMillisSeconds;
    private final long valueNullExpireMillisSeconds;
    public final EasyCacheManager cacheItemProvider;

    public DefaultCacheProvider(EasyCacheManager easyCacheManager, EasyCacheOption easyCacheOption) {

        this.expireMillisSeconds = easyCacheOption.getExpireMillisSeconds();
        this.valueNullExpireMillisSeconds = easyCacheOption.getValueNullExpireMillisSeconds();
        this.cacheItemProvider = easyCacheManager;
    }
    @Override
    public <T> List<Pair<String, T>> cache(Class<?> entityClass, Class<T> clazz, String conditionKey, Set<String> ids, Function<Collection<String>, List<Pair<String, T>>> getDataFunc) {
        if (EasyCollectionUtil.isEmpty(ids)) {
            return new ArrayList<>(0);
        }
        if (expireMillisSeconds <= 0) {
            return getDataFunc.apply(ids);
        }
        HashMap<String, Pair<String, T>> ret = new HashMap<>(ids.size());
        Set<String> notExistsIds = new HashSet<>();
        for (String id : ids) {
            CacheItem cacheItem = cacheItemProvider.getCacheItem(id, conditionKey, entityClass);
            if (cacheItem != null) {
                if (!cacheItem.cacheIsExpired() && cacheItem.hasJson()) {
                    String json = cacheItem.getJson();
                    if (EasyStringUtil.isNotBlank(json)) {
                        T entity = cacheItemProvider.fromJson(json, clazz);
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
                String json = cacheItemProvider.toJson(data.getObject2());

                CacheItem cacheItem = new CacheItem();
                cacheItem.setExpire(System.currentTimeMillis() + expired);
                cacheItem.setJson(json);
                cacheItemProvider.setCacheItem(id, conditionKey, cacheItem, entityClass, expireMillisSeconds);

                notExistsIds.remove(id);

            }
            for (String cacheId : notExistsIds) {

                CacheItem cacheItem = new CacheItem();
                cacheItem.setExpire(System.currentTimeMillis() + valueNullExpireMillisSeconds);
                cacheItem.setJson(null);

                cacheItemProvider.setCacheItem(cacheId, conditionKey, cacheItem, entityClass, expireMillisSeconds);
            }
        }
        return new ArrayList<>(ret.values());

    }

    @Override
    public void deleteBy(CacheKey cacheKey) {
        cacheItemProvider.deleteBy(cacheKey);
    }
}
