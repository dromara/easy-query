package com.easy.query.core.basic.jdbc.executor.internal.merge.result.aggregation;

import java.util.List;

/**
 * create time 2023/4/28 21:01
 * 文件说明
 *
 * @author xuejiaming
 */
public final class ComparableAggregationUnit implements AggregationUnit {

    private final boolean asc;

    private Comparable<?> result;

    public ComparableAggregationUnit(boolean asc) {
        this.asc = asc;
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    @Override
    public void merge(final List<Comparable<?>> values) {
        if (null == values || null == values.get(0)) {
            return;
        }
        if (null == result) {
            result = values.get(0);
            return;
        }
        int comparedValue = ((Comparable) values.get(0)).compareTo(result);
        if (asc ? comparedValue < 0 : comparedValue > 0) {
            result = values.get(0);
        }
    }

    @Override
    public Comparable<?> getResult() {
        return result;
    }
}
