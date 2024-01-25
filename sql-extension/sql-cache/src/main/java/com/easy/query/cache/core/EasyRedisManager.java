package com.easy.query.cache.core;

import com.easy.query.cache.core.base.ClearParameter;

import java.io.IOException;
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
public interface EasyRedisManager {
    /**
     * 缓存获取是否存在没有就调用 getDataFunc
     * @param clazz
     * @param entityKey
     * @param ids
     * @param timeoutMillisSeconds 过期时间小于等于0不进行缓存
     * @param nullValueTimeoutMillisSeconds 缓存过期时间小于等于0不进行缓存
     * @param getDataFunc
     * @param <T>
     * @return
     * @throws IOException
     */
    <T> List<Pair<String,T>> cache(Class<T> clazz, String entityKey, Set<String> ids, long timeoutMillisSeconds, long nullValueTimeoutMillisSeconds,
                                   Function<Collection<String>,List<Pair<String,T>>> getDataFunc);

    void clear(ClearParameter clearParameter);
}
