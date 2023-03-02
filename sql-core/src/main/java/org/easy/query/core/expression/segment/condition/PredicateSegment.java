package org.easy.query.core.expression.segment.condition;

import org.easy.query.core.expression.segment.SqlSegment;
import org.easy.query.core.expression.segment.condition.predicate.Predicate;

/**
 * @FileName: PredicateSegment.java
 * @Description: 文件说明
 * @Date: 2023/3/2 22:04
 * @Created by xuejiaming
 */
public interface PredicateSegment extends SqlSegment {
    boolean isEmpty();
    default boolean isNotEmpty() {
        return !isEmpty();
    }

     void setPredicate(Predicate predicate);

     void addPredicateSegment(PredicateSegment predicateSegment);
}
