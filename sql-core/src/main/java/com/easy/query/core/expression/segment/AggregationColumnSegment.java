package com.easy.query.core.expression.segment;

import com.easy.query.core.expression.func.AggregationType;

/**
 * create time 2023/4/28 21:33
 * 文件说明
 *
 * @author xuejiaming
 */
public interface AggregationColumnSegment extends ColumnSegment {
    AggregationType getAggregationType();
    @Override
    AggregationColumnSegment cloneSQLEntitySegment();
}
