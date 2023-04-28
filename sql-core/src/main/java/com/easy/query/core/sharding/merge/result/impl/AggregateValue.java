package com.easy.query.core.sharding.merge.result.impl;

import com.easy.query.core.sharding.merge.result.aggregation.AggregationUnit;

/**
 * create time 2023/4/28 22:39
 * 文件说明
 *
 * @author xuejiaming
 */
public final class AggregateValue {
    private final int columnIndex;
    private final AggregationUnit aggregationUnit;

    public AggregateValue(int columnIndex, AggregationUnit aggregationUnit){
        this.columnIndex = columnIndex;

        this.aggregationUnit = aggregationUnit;
    }

    public int getColumnIndex() {
        return columnIndex;
    }

    public AggregationUnit getAggregationUnit() {
        return aggregationUnit;
    }
}
