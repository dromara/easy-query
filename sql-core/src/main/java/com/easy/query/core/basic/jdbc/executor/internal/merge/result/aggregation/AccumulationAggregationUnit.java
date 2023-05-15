package com.easy.query.core.basic.jdbc.executor.internal.merge.result.aggregation;

import java.math.BigDecimal;
import java.util.List;

/**
 * create time 2023/4/28 21:03
 * 文件说明
 *
 * @author xuejiaming
 */
public final class AccumulationAggregationUnit implements AggregationUnit {

    private BigDecimal result;

    @Override
    public void merge(final List<Comparable<?>> values) {
        if (null == values || null == values.get(0)) {
            return;
        }
        if (null == result) {
            result = new BigDecimal("0");
        }
        result = result.add(new BigDecimal(values.get(0).toString()));
    }

    @Override
    public Comparable<?> getResult() {
        return result;
    }
}
