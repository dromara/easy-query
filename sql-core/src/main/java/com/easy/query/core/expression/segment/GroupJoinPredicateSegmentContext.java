package com.easy.query.core.expression.segment;

import com.easy.query.core.expression.segment.condition.PredicateSegment;

import java.util.function.Function;

/**
 * create time 2025/7/28 14:14
 * 文件说明
 *
 * @author xuejiaming
 */
public interface GroupJoinPredicateSegmentContext {
    PredicateSegment getPredicateSegment();
    PredicateSegment getOriginalPredicateSegment();
    PredicateSegment getToSQLPredicateSegment();

    void setPredicateSegmentAs(Function<PredicateSegment,PredicateSegment> predicateSegmentAs);
    Function<PredicateSegment,PredicateSegment> getPredicateSegmentAs();

    GroupJoinPredicateSegmentContext cloneGroupJoinPredicateSegmentContext();
}
