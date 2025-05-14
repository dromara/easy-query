package com.easy.query.core.expression.segment.condition;

import com.easy.query.core.expression.segment.condition.predicate.Predicate;

/**
 * @FileName: PredicateContext.java
 * @Description: 文件说明
 * create time 2023/3/15 08:43
 * @author xuejiaming
 */
public interface PredicateIndexContext {
     void add(Predicate predicate);
}
