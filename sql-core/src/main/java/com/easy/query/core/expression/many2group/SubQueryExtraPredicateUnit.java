package com.easy.query.core.expression.many2group;

import com.easy.query.core.expression.parser.core.base.WherePredicate;

/**
 * create time 2025/8/24 08:33
 * 文件说明
 *
 * @author xuejiaming
 */
public interface SubQueryExtraPredicateUnit {

    void invoke(WherePredicate<Object> wherePredicate);
}
