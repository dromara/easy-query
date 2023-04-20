package com.easy.query.core.expression.segment.condition;

import com.easy.query.core.expression.segment.SqlSegment;
import com.easy.query.core.expression.segment.condition.predicate.Predicate;

/**
 * @FileName: PredicateSegment.java
 * @Description: 文件说明
 * @Date: 2023/3/2 22:04
 * @author xuejiaming
 */
public interface PredicateSegment extends SqlSegment {
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
    PredicateIndex buildPredicateIndex();

    void copyTo(PredicateSegment predicateSegment);

}
