package com.easy.query.core.expression.segment;

import com.easy.query.core.expression.func.AggregationType;

/**
 * create time 2023/5/20 18:50
 * 文件说明
 *
 * @author xuejiaming
 */
public interface MaybeAggregateColumnSegment extends ColumnSegment{
    boolean isAggregateColumn();
    AggregationType getAggregationType();
}
