package com.easy.query.core.common.cache;

import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

/**
 * @FileName: DefaultMemoryCache.java
 * @Description: 文件说明
 * @Date: 2023/2/11 10:14
 * @author xuejiaming
 */
public class DefaultMemoryCache<K,V> implements Cache<K,V> {
    private final ConcurrentHashMap<K, V> map = new ConcurrentHashMap<K, V>();

    @Override
    public V get(K key) {
        return map.get(key);
    }

    @Override
    public void remove(K key) {
        map.remove(key);
    }

    @Override
    public void put(K key, V value) {
        map.put(key, value);
    }

    @Override
    public void clearAll() {
        map.clear();
    }

    @Override
    public V putIfAbsent(K key, V value) {
        return map.putIfAbsent(key, value);
    }

    @Override
    public V computeIfAbsent(K key, Function<? super K, ? extends V> mappingFunction) {
        return map.computeIfAbsent(key,mappingFunction);
    }
}
