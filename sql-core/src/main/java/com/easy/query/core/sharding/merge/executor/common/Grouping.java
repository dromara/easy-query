package com.easy.query.core.sharding.merge.executor.common;

import java.util.ArrayList;
import java.util.List;

/**
 * create time 2023/4/14 17:00
 * 文件说明
 *
 * @author xuejiaming
 */
public class Grouping<K, V> {
    private final K key;
    private final List<V> values;

    public Grouping(K key, List<V> values) {
        this.key = key;
        this.values = values;
    }

    public K getKey() {
        return key;
    }

    public List<V> getValues() {
        return values;
    }

    @Override
    public String toString() {
        return "Grouping{" +
                "key=" + key +
                ", values=" + values +
                '}';
    }
}
