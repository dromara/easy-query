package com.easy.query.core.basic.jdbc.executor.internal.common;

import java.util.List;

/**
 * create time 2023/4/14 17:00
 * 文件说明
 *
 * @author xuejiaming
 */
public interface GroupByValue<K, V> {
    K key();
    List<V> values();
}
