package com.easy.query.core.expression.predicate;

import com.easy.query.core.expression.builder.Filter;

/**
 * create time 2025/9/8 16:54
 * 文件说明
 *
 * @author xuejiaming
 */
public interface SmartPredicateUnit {
    void invoke(Filter filter);
}
