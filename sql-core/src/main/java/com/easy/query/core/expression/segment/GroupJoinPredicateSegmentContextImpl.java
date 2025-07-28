package com.easy.query.core.expression.segment;

import com.easy.query.core.expression.segment.condition.PredicateSegment;

import java.util.function.Function;

/**
 * create time 2025/7/28 14:14
 * 文件说明
 *
 * @author xuejiaming
 */
public class GroupJoinPredicateSegmentContextImpl implements GroupJoinPredicateSegmentContext {
    private final PredicateSegment predicateSegment;
    private Function<PredicateSegment, PredicateSegment> predicateSegmentAs;

    public GroupJoinPredicateSegmentContextImpl(PredicateSegment predicateSegment){
        this.predicateSegment = predicateSegment;
    }
    @Override
    public PredicateSegment getPredicateSegment() {
        return predicateSegment;
    }

    @Override
    public PredicateSegment getToSQLPredicateSegment() {
        if(predicateSegmentAs==null){
            return predicateSegment;
        }
        return predicateSegmentAs.apply(predicateSegment);
    }

    @Override
    public void setPredicateSegmentAs(Function<PredicateSegment, PredicateSegment> predicateSegmentAs) {
        this.predicateSegmentAs=predicateSegmentAs;
    }

    @Override
    public Function<PredicateSegment, PredicateSegment> getPredicateSegmentAs() {
        return predicateSegmentAs;
    }
}
