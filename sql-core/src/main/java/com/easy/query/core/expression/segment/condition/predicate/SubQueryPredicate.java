package com.easy.query.core.expression.segment.condition.predicate;

import com.easy.query.core.basic.api.select.Queryable;

/**
 * create time 2023/4/27 09:24
 * 文件说明
 *
 * @author xuejiaming
 */
public interface SubQueryPredicate extends Predicate{
    Queryable<?> getSubQueryable();
}
