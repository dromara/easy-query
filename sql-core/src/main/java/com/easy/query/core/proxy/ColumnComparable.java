package com.easy.query.core.proxy;

import com.easy.query.core.proxy.predicate.DSLAssertPredicate;
import com.easy.query.core.proxy.predicate.DSLLikePredicate;
import com.easy.query.core.proxy.predicate.DSLOtherPredicate;
import com.easy.query.core.proxy.predicate.DSLRangePredicate;
import com.easy.query.core.proxy.predicate.DSLSubQueryPredicate;
import com.easy.query.core.proxy.predicate.DSLValuePredicate;
import com.easy.query.core.proxy.predicate.DSLValuesPredicate;

/**
 * create time 2023/7/12 21:42
 * 文件说明
 *
 * @author xuejiaming
 */
public interface ColumnComparable<T> extends SQLSelectAs
        , DSLValuePredicate<T>
        , DSLAssertPredicate<T>
        , DSLLikePredicate<T>
        , DSLValuesPredicate<T>
        , DSLSubQueryPredicate<T>
        , DSLRangePredicate<T>
        , DSLOtherPredicate<T> {
}
