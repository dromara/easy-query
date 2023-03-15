package com.easy.query.core.expression.segment.condition;

import com.easy.query.core.expression.segment.condition.predicate.Predicate;

/**
 * @FileName: PredicateContext.java
 * @Description: 文件说明
 * @Date: 2023/3/15 08:43
 * @Created by xuejiaming
 */
public interface PredicateContext {
     void add(Predicate predicate);
}
