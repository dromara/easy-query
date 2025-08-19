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
    private PredicateSegment mergePredicateSegment;
    private Function<PredicateSegment, PredicateSegment> predicateSegmentAs;

    public GroupJoinPredicateSegmentContextImpl(PredicateSegment predicateSegment) {
        this.predicateSegment = predicateSegment;
        if(predicateSegment!=null){
            this.mergePredicateSegment = predicateSegment.clonePredicateSegment();
        }
    }

    @Override
    public PredicateSegment getPredicateSegment() {
        return mergePredicateSegment;
    }

    @Override
    public PredicateSegment getOriginalPredicateSegment() {
        return predicateSegment;
    }

    @Override
    public PredicateSegment getToSQLPredicateSegment() {
        if (predicateSegmentAs == null) {
            return mergePredicateSegment;
        }
        return predicateSegmentAs.apply(mergePredicateSegment);
    }

    @Override
    public void setPredicateSegmentAs(Function<PredicateSegment, PredicateSegment> predicateSegmentAs) {
        this.predicateSegmentAs = predicateSegmentAs;
    }

    @Override
    public Function<PredicateSegment, PredicateSegment> getPredicateSegmentAs() {
        return predicateSegmentAs;
    }

    @Override
    public GroupJoinPredicateSegmentContext cloneGroupJoinPredicateSegmentContext() {
        if (predicateSegment == null) {
            return this;
        }
        this.mergePredicateSegment=predicateSegment.clonePredicateSegment();
        return this;
    }
}
