package com.easy.query.core.expression.segment.condition;

import com.easy.query.core.expression.lambda.BreakConsumer;
import com.easy.query.core.expression.segment.SQLSegment;
import com.easy.query.core.expression.segment.condition.predicate.Predicate;
import com.easy.query.core.expression.segment.index.SegmentIndex;

/**
 * @FileName: PredicateSegment.java
 * @Description: 文件说明
 * create time 2023/3/2 22:04
 * @author xuejiaming
 */
public interface PredicateSegment extends SQLSegment {
    boolean isRoot();
    boolean isEmpty();

    default boolean isNotEmpty() {
        return !isEmpty();
    }

    void setPredicate(Predicate predicate);

    void addPredicateSegment(PredicateSegment predicateSegment);

    /**
     * 默认如果是单个条件判断可以用这个,他会遍历条件多叉树只要返回一次符合的即存在
     * 如果有多个或者循环 需要判断请用buildPredicateIndex方法构建一个索引去判断
     * @param entityClass
     * @param propertyName
     * @return
     */
     boolean containsOnce(Class<?> entityClass, String propertyName);

    /**
     * 遍历条件
     * @param consumer 返回true被中断不需要返回
     * @return 返回true表示是被中断的
     */
    boolean forEach(BreakConsumer<Predicate> consumer);
    SegmentIndex buildPredicateIndex();

    void copyTo(PredicateSegment predicateSegment);
    PredicateSegment clonePredicateSegment();

}
