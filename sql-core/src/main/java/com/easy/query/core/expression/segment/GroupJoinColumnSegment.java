package com.easy.query.core.expression.segment;

import com.easy.query.core.expression.segment.condition.PredicateSegment;

/**
 * create time 2025/7/26 15:37
 * 文件说明
 *
 * @author xuejiaming
 */
public interface GroupJoinColumnSegment extends ColumnSegment{
    PredicateSegment getPredicateSegment();
}
