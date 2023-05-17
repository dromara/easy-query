package com.easy.query.core.expression.segment.condition;

import com.easy.query.core.expression.segment.SQLSegment;
import com.easy.query.core.expression.segment.condition.predicate.Predicate;

import java.util.List;

/**
 * create time 2023/4/24 08:26
 * 文件说明
 *
 * @author xuejiaming
 */
public interface ShardingPredicateSegment extends SQLSegment {
    List<PredicateSegment> getChildren();
    Predicate getPredicate();
    boolean isPredicate();
}
