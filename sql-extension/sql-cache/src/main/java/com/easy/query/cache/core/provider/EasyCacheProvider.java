package com.easy.query.cache.core.provider;

import com.easy.query.cache.core.Pair;
import com.easy.query.cache.core.common.CacheKey;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.function.Function;

/**
 * create time 2024/1/24 20:54
 * 文件说明
 *
 * @author xuejiaming
 */
public interface EasyCacheProvider {
    <T> List<Pair<String, T>> cache(Class<?> entityClass, Class<T> clazz, String conditionKey, Set<String> ids, Function<Collection<String>, List<Pair<String, T>>> getDataFunc);
    void deleteBy(CacheKey cacheKey);
}
