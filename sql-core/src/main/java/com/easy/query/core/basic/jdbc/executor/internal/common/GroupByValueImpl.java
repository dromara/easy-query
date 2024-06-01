package com.easy.query.core.basic.jdbc.executor.internal.common;

import java.util.List;

/**
 * create time 2023/4/15 16:58
 * 文件说明
 *
 * @author xuejiaming
 */
public class GroupByValueImpl<K, V> implements GroupByValue<K, V> {
    private final K key;
    private final List<V> values;

    public GroupByValueImpl(K key, List<V> values) {
        this.key = key;
        this.values = values;
    }

    @Override
    public K key() {
        return key;
    }

    @Override
    public List<V> values() {
        return values;
    }
}