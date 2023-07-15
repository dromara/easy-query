package com.easy.query.core.basic.jdbc.executor.internal.merge.result.impl;

import com.easy.query.core.basic.jdbc.executor.internal.merge.result.aggregation.AggregationUnit;

import java.util.ArrayList;
import java.util.List;

/**
 * create time 2023/4/28 22:39
 * 文件说明
 *
 * @author xuejiaming
 */
public final class AggregateValue {
    private final int columnIndex;
    private final AggregationUnit aggregationUnit;
    private List<AggregateValue> aggregateValues;

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

    public void addAggregateValue(AggregateValue aggregateValue){
        if(aggregateValues==null){
            this.aggregateValues=new ArrayList<>(2);
        }
        this.aggregateValues.add(aggregateValue);
    }

    public List<AggregateValue> getAggregateValues() {
        return aggregateValues;
    }
}
