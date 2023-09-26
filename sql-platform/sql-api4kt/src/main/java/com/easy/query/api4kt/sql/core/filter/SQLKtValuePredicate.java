package com.easy.query.api4kt.sql.core.filter;

import com.easy.query.api4kt.util.EasyKtLambdaUtil;
import com.easy.query.core.expression.parser.core.EntitySQLTableOwner;
import com.easy.query.core.expression.parser.core.available.ChainCast;
import kotlin.reflect.KProperty1;

/**
 * create time 2023/9/25 16:35
 * 文件说明
 *
 * @author xuejiaming
 */
public interface SQLKtValuePredicate<T1, TChain> extends EntitySQLTableOwner<T1>, SQLKtWherePredicateAvailable<T1>, ChainCast<TChain> {

    /**
     * 大于 column > val
     */
    default <TProperty> TChain gt(KProperty1<? super T1, TProperty> column, TProperty val) {
        getWherePredicate().gt(true, EasyKtLambdaUtil.getPropertyName(column), val);
        return castChain();
    }

    /**
     * 大于 column > val
     *
     * @param condition 执行条件
     * @param column    字段
     * @param val       值
     * @return children
     */
    default <TProperty> TChain gt(boolean condition, KProperty1<? super T1, TProperty> column, TProperty val) {
        getWherePredicate().gt(condition, EasyKtLambdaUtil.getPropertyName(column), val);
        return castChain();
    }

    /**
     * 等于 column >= val
     */
    default <TProperty> TChain ge(KProperty1<? super T1, TProperty> column, TProperty val) {
        getWherePredicate().ge(EasyKtLambdaUtil.getPropertyName(column), val);
        return castChain();
    }

    /**
     * 等于 column >= val
     *
     * @param condition 执行条件
     * @param column    字段
     * @param val       值
     * @return children
     */
    default <TProperty> TChain ge(boolean condition, KProperty1<? super T1, TProperty> column, TProperty val) {
        getWherePredicate().ge(condition, EasyKtLambdaUtil.getPropertyName(column), val);
        return castChain();
    }

    /**
     * 等于 column = val
     */
    default <TProperty> TChain eq(KProperty1<? super T1, TProperty> column, TProperty val) {
        getWherePredicate().eq(EasyKtLambdaUtil.getPropertyName(column), val);
        return castChain();
    }

    /**
     * 等于 column = val
     *
     * @param condition 执行条件
     * @param column    字段
     * @param val       值
     * @return children
     */
    default <TProperty> TChain eq(boolean condition, KProperty1<? super T1, TProperty> column, TProperty val) {
        getWherePredicate().eq(condition, EasyKtLambdaUtil.getPropertyName(column), val);
        return castChain();
    }

    /**
     * 不等于 column <> val
     */
    default <TProperty> TChain ne(KProperty1<? super T1, TProperty> column, TProperty val) {
        getWherePredicate().ne(EasyKtLambdaUtil.getPropertyName(column), val);
        return castChain();
    }

    /**
     * 不等于 column <> val
     *
     * @param condition 执行条件
     * @param column    字段
     * @param val       值
     * @return children
     */
    default <TProperty> TChain ne(boolean condition, KProperty1<? super T1, TProperty> column, TProperty val) {
        getWherePredicate().ne(condition, EasyKtLambdaUtil.getPropertyName(column), val);
        return castChain();
    }

    /**
     * 小于等于 column <= val
     */
    default <TProperty> TChain le(KProperty1<? super T1, TProperty> column, TProperty val) {
        getWherePredicate().le(EasyKtLambdaUtil.getPropertyName(column), val);
        return castChain();
    }

    /**
     * 小于等于 column <= val
     *
     * @param condition 执行条件
     * @param column    字段
     * @param val       值
     * @return children
     */
    default <TProperty> TChain le(boolean condition, KProperty1<? super T1, TProperty> column, TProperty val) {
        getWherePredicate().le(condition, EasyKtLambdaUtil.getPropertyName(column), val);
        return castChain();
    }

    /**
     * 小于 column < val
     */
    default <TProperty> TChain lt(KProperty1<? super T1, TProperty> column, TProperty val) {
        getWherePredicate().lt(EasyKtLambdaUtil.getPropertyName(column), val);
        return castChain();
    }

    /**
     * 小于 column < val
     *
     * @param condition 执行条件
     * @param column    字段
     * @param val       值
     * @return children
     */
    default <TProperty> TChain lt(boolean condition, KProperty1<? super T1, TProperty> column, TProperty val) {
        getWherePredicate().lt(condition, EasyKtLambdaUtil.getPropertyName(column), val);
        return castChain();
    }


}
