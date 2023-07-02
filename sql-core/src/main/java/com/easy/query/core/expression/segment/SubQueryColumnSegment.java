package com.easy.query.core.expression.segment;

import com.easy.query.core.basic.api.select.Query;

/**
 * create time 2023/5/20 10:07
 * 文件说明
 *
 * @author xuejiaming
 */
public interface SubQueryColumnSegment extends MaybeAggregateColumnSegment{
    Query<?> getSubQuery();

    @Override
    SubQueryColumnSegment cloneSQLColumnSegment();
}
