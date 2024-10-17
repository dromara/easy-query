package com.easy.query.core.expression.parser.core.base.core.filter;

import com.easy.query.core.enums.SQLPredicateCompare;
import com.easy.query.core.enums.SQLPredicateCompareEnum;
import com.easy.query.core.expression.parser.core.EntitySQLTableOwner;
import com.easy.query.core.expression.parser.core.available.ChainCast;
import com.easy.query.core.expression.parser.core.base.WherePredicate;

/**
 * create time 2023/9/25 16:43
 * 文件说明
 *
 * @author xuejiaming
 */
public interface SelfPredicate<T1,TChain> extends ChainCast<TChain> {
    default <T2> WherePredicate<T1> gt(EntitySQLTableOwner<T2> sub, String property1, String property2) {
        return gt(true, sub, property1, property2);
    }

    <T2> WherePredicate<T1> gt(boolean condition, EntitySQLTableOwner<T2> sub, String property1, String property2);

    default <T2> WherePredicate<T1> ge(EntitySQLTableOwner<T2> sub, String property1, String property2) {
        return ge(true, sub, property1, property2);
    }

    <T2> WherePredicate<T1> ge(boolean condition, EntitySQLTableOwner<T2> sub, String property1, String property2);

    /**
     * 表与表的列进行比较
     * @param sub
     * @param property1
     * @param property2
     * @return
     * @param <T2>
     */
    default <T2> WherePredicate<T1> eq(EntitySQLTableOwner<T2> sub, String property1, String property2) {
        return eq(true, sub, property1, property2);
    }

    default <T2> WherePredicate<T1> eq(boolean condition, EntitySQLTableOwner<T2> sub, String property1, String property2) {
        return compareSelf(condition, sub, property1, property2, SQLPredicateCompareEnum.EQ);
    }
    <T2> WherePredicate<T1> multiEq(boolean condition, EntitySQLTableOwner<T2> sub, String[] properties1, String[] properties2);

    default <T2> WherePredicate<T1> ne(EntitySQLTableOwner<T2> sub, String property1, String property2) {
        return ne(true, sub, property1, property2);
    }

    <T2> WherePredicate<T1> ne(boolean condition, EntitySQLTableOwner<T2> sub, String property1, String property2);

    default <T2> WherePredicate<T1> le(EntitySQLTableOwner<T2> sub, String property1, String property2) {
        return le(true, sub, property1, property2);
    }

    <T2> WherePredicate<T1> le(boolean condition, EntitySQLTableOwner<T2> sub, String property1, String property2);

    default <T2> WherePredicate<T1> lt(EntitySQLTableOwner<T2> sub, String property1, String property2) {
        return lt(true, sub, property1, property2);
    }

    <T2> WherePredicate<T1> lt(boolean condition, EntitySQLTableOwner<T2> sub, String property1, String property2);
    <T2> WherePredicate<T1> compareSelf(boolean condition, EntitySQLTableOwner<T2> sub, String property1, String property2, SQLPredicateCompare sqlPredicateCompare);
}
