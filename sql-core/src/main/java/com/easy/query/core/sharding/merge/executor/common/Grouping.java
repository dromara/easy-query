package com.easy.query.core.sharding.merge.executor.common;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

/**
 * create time 2023/4/14 17:00
 * 文件说明
 *
 * @author xuejiaming
 */
public interface Grouping<K, V> {
    K key();
    Stream<V> values();
}
