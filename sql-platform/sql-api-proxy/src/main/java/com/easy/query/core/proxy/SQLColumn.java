package com.easy.query.core.proxy;

import com.easy.query.core.proxy.extension.ColumnComparableExpression;
import com.easy.query.core.proxy.predicate.DSLPropertyOnlyAssertPredicate;

/**
 * create time 2023/6/22 13:12
 * 文件说明
 *
 * @author xuejiaming
 */
public interface SQLColumn<TProxy, TProperty> extends ColumnComparableExpression<TProperty>,
        PropTypeColumn<TProperty>,
        DSLPropertyOnlyAssertPredicate<TProperty>{
}
