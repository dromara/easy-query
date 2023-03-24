package com.easy.query.core.expression.segment.condition;

/**
 * @FileName: PredicateIndex.java
 * @Description: 文件说明
 * @Date: 2023/3/15 09:20
 * @author xuejiaming
 */
public interface PredicateIndex {
    boolean contains(Class<?> entityClass, String propertyName);
}
