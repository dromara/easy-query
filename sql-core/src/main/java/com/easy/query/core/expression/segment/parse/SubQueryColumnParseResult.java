package com.easy.query.core.expression.segment.parse;

import com.easy.query.core.expression.func.AggregationType;

/**
 * create time 2023/5/20 17:48
 * 文件说明
 *
 * @author xuejiaming
 */
public final class SubQueryColumnParseResult {
    public static final SubQueryColumnParseResult DEFAULT=new SubQueryColumnParseResult(false,AggregationType.UNKNOWN);
    private final boolean isAggregateColumn;
    private final AggregationType aggregationType;

    public SubQueryColumnParseResult(boolean isAggregateColumn, AggregationType aggregationType){

        this.isAggregateColumn = isAggregateColumn;
        this.aggregationType = aggregationType;
    }

    public boolean isAggregateColumn() {
        return isAggregateColumn;
    }

    public AggregationType getAggregationType() {
        return aggregationType;
    }
}
