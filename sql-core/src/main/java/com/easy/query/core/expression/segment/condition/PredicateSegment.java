package com.easy.query.core.expression.segment.condition;

import com.easy.query.core.expression.segment.SqlSegment;
import com.easy.query.core.expression.segment.condition.predicate.Predicate;

import java.util.Map;
import java.util.Set;

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

    /**
     * 
     *
     * @param entityClass
     * @param propertyName
     * @return
     */
    default boolean contains(Class<?> entityClass, String propertyName){
        Map<Class<?>, Set<String>> entityProperties = getEntityProperties();
        Set<String> propertySet = entityProperties.get(entityClass);
        if(propertySet!=null){
            return propertySet.contains(propertyName);
        }
        return false;
    }

    Map<Class<?>, Set<String>> getEntityProperties();

    void copyTo(PredicateSegment predicateSegment);

}
