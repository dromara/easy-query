package com.easy.query.api4j.sql.core.filter;

import com.easy.query.core.expression.parser.core.base.WherePredicate;

/**
 * create time 2023/9/25 22:09
 * 文件说明
 *
 * @author xuejiaming
 */
public interface SQLWherePredicateAvailable<T1> {
    WherePredicate<T1> getWherePredicate();
}
