package com.easy.query.core.basic.jdbc.executor.internal.common;

import java.util.stream.Stream;

/**
 * create time 2023/4/15 16:58
 * 文件说明
 *
 * @author xuejiaming
 */
public class GroupingImpl<K, V> implements Grouping<K, V> {
    private final K key;
    private final Stream<V> values;

    public GroupingImpl(K key, Stream<V> values) {
        this.key = key;
        this.values = values;
    }

    @Override
    public K key() {
        return key;
    }

    @Override
    public Stream<V> values() {
        return values;
    }
}