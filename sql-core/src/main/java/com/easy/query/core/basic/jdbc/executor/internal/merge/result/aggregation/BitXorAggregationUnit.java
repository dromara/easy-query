package com.easy.query.core.basic.jdbc.executor.internal.merge.result.aggregation;

import java.math.BigInteger;
import java.util.List;

/**
 * create time 2023/4/28 21:04
 * 文件说明
 *
 * @author xuejiaming
 */
public final class BitXorAggregationUnit implements AggregationUnit {

    private BigInteger result;

    @Override
    public void merge(final List<Comparable<?>> values) {
        if (null == values || null == values.get(0)) {
            return;
        }
        if (null == result) {
            result = BigInteger.ZERO;
        }
        result = result.xor(new BigInteger(values.get(0).toString()));
    }

    @Override
    public Comparable<?> getResult() {
        return result;
    }
}
