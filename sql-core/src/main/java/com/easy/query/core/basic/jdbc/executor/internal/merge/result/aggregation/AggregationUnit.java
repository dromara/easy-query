package com.easy.query.core.basic.jdbc.executor.internal.merge.result.aggregation;

import java.util.List;

/**
 * create time 2023/4/28 17:26
 * 文件说明
 *
 * @author xuejiaming
 */
public interface AggregationUnit {
    void merge(List<Comparable<?>> values);
    Comparable<?> getResult();
}
