package com.easy.query.core.basic.jdbc.executor.internal.common;

import java.util.stream.Stream;

/**
 * create time 2023/4/14 17:00
 * 文件说明
 *
 * @author xuejiaming
 */
public interface GroupByStreamValue<K, V> {
    K key();
    Stream<V> values();
}
