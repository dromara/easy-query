package com.easy.query.core.expression.segment.condition;

import com.easy.query.core.expression.segment.condition.predicate.Predicate;
import com.easy.query.core.expression.segment.index.SegmentIndex;
import com.easy.query.core.util.EasyMapUtil;

import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/**
 * @FileName: EasyPredicateContext.java
 * @Description: 文件说明
 * @Date: 2023/3/15 09:39
 * @author xuejiaming
 */
public class EasyPredicateIndexContext implements PredicateIndexContext, SegmentIndex {
    private final Map<Class<?>, Set<String>> entityProperties=new LinkedHashMap<>();
    public void add(Predicate predicate){
        Set<String> propertySet = EasyMapUtil.computeIfAbsent(entityProperties,predicate.getTable().getEntityClass(), key -> new HashSet<>());
        propertySet.add(predicate.getPropertyName());
    }
    public boolean contains(Class<?> entityClass, String propertyName){
        Set<String> propertySet = entityProperties.get(entityClass);
        if (propertySet != null) {
            return propertySet.contains(propertyName);
        }
        return false;
    }
}
