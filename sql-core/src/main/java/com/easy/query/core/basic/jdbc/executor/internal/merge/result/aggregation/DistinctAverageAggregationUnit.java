package com.easy.query.core.basic.jdbc.executor.internal.merge.result.aggregation;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;

/**
 * create time 2023/4/28 21:00
 * 文件说明
 *
 * @author xuejiaming
 */
public final class DistinctAverageAggregationUnit implements AggregationUnit {

    private BigDecimal count;

    private BigDecimal sum;

    private final Collection<Comparable<?>> countValues = new LinkedHashSet<>();

    private final Collection<Comparable<?>> sumValues = new LinkedHashSet<>();

    @Override
    public void merge(final List<Comparable<?>> values) {
        if (null == values || null == values.get(0) || null == values.get(1)) {
            return;
        }
        if (countValues.add(values.get(0)) && sumValues.add(values.get(0))) {
            if (null == count) {
                count = new BigDecimal("0");
            }
            if (null == sum) {
                sum = new BigDecimal("0");
            }
            count = count.add(new BigDecimal(values.get(0).toString()));
            sum = sum.add(new BigDecimal(values.get(1).toString()));
        }
    }

    @Override
    public Comparable<?> getResult() {
        if (null == count || BigDecimal.ZERO.equals(count)) {
            return count;
        }
        // TODO use metadata to fetch float number precise for database field
        return sum.divide(count, 4, RoundingMode.HALF_UP);
    }
}
