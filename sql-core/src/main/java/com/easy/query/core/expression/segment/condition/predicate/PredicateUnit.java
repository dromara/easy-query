package com.easy.query.core.expression.segment.condition.predicate;

import com.easy.query.core.expression.segment.GroupJoinPredicateSegmentContext;
import com.easy.query.core.expression.segment.condition.PredicateSegment;

/**
 * create time 2025/7/26 20:58
 * 文件说明
 *
 * @author xuejiaming
 */
public class PredicateUnit{
    public final String key;
    public final PredicateSegment predicateSegment;
    public final GroupJoinPredicateSegmentContext groupJoinPredicateSegmentContext;

    public PredicateUnit(String sql, String parameter, PredicateSegment predicateSegment, GroupJoinPredicateSegmentContext groupJoinPredicateSegmentContext){
        this.key = String.format("sql:%s:params:%s",sql,parameter);
        this.predicateSegment = predicateSegment;
        this.groupJoinPredicateSegmentContext = groupJoinPredicateSegmentContext;
    }
}
