package com.easy.query.core.sharding.merge.executor.common;

import java.util.ArrayList;
import java.util.List;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.Consumer;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

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