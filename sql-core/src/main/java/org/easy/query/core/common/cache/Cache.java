package org.easy.query.core.common.cache;

import java.util.function.Function;
import java.util.function.Supplier;

/**
 * @FileName: Cache.java
 * @Description: 文件说明
 * @Date: 2023/2/11 10:08
 * @Created by xuejiaming
 */
public interface Cache<K, V> {

    /**
     * 判断是否存在对应的key
     * @param key 键
     * @return true表示存在 false表示不存在
     */
    default boolean containsKey(K key){
        return get(key)!=null;
    }
    /**
     * 根据key返回值
     * @param key 键
     * @return 缓存键对应的值,没有返回null
     */
    V get(K key);

    /**
     * 删除缓存
     * @param key 键
     */
    void remove(K key);

    /**
     * 添加缓存
     * @param key 键
     * @param value 值
     */
    void put(K key, V value);

    /**
     * 清除缓存
     */
    void clearAll();

    /**
     * 当缓存不存在已有值时，才存入
     * @param key 键
     * @param value 值
     * @return 如果已有值，返回上一个存入值，否则返回新存入值
     */
    V putIfAbsent(K key, V value);
    V computeIfAbsent(K key, Function<? super K, ? extends V> mappingFunction);
}
