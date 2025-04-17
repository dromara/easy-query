package com.easy.query.core.expression.parser.core.extra;

import com.easy.query.core.expression.parser.core.base.WherePredicate;

/**
 * create time 2025/4/16 10:18
 * 文件说明
 *
 * @author xuejiaming
 */
public interface ExtraWhere {
    void where(WherePredicate<?> wherePredicate);
}
