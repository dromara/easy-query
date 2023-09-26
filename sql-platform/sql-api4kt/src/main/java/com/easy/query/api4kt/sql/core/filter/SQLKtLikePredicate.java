package com.easy.query.api4kt.sql.core.filter;

import com.easy.query.api4kt.util.EasyKtLambdaUtil;
import com.easy.query.core.enums.SQLLikeEnum;
import com.easy.query.core.expression.parser.core.EntitySQLTableOwner;
import com.easy.query.core.expression.parser.core.available.ChainCast;
import kotlin.reflect.KProperty1;

/**
 * create time 2023/9/25 17:15
 * 文件说明
 *
 * @author xuejiaming
 */
public interface SQLKtLikePredicate<T1, TChain> extends EntitySQLTableOwner<T1>, SQLKtWherePredicateAvailable<T1>, ChainCast<TChain> {

    /**
     * column like val%
     * 列匹配前半部分
     *
     * @param column
     * @param val
     * @return
     */
    default <TProperty> TChain likeMatchLeft(KProperty1<? super T1, TProperty> column, TProperty val) {
        getWherePredicate().likeMatchLeft(EasyKtLambdaUtil.getPropertyName(column), val);
        return castChain();
    }

    /**
     * column like val%
     * 列匹配前半部分
     *
     * @param condition
     * @param column
     * @param val
     * @return
     */
    default <TProperty> TChain likeMatchLeft(boolean condition, KProperty1<? super T1, TProperty> column, TProperty val) {
        getWherePredicate().likeMatchLeft(condition, EasyKtLambdaUtil.getPropertyName(column), val);
        return castChain();
    }

    /**
     * column like %val
     * 列匹配后半部分
     *
     * @param column
     * @param val
     * @return
     */
    default <TProperty> TChain likeMatchRight(KProperty1<? super T1, TProperty> column, TProperty val) {
        getWherePredicate().likeMatchRight(EasyKtLambdaUtil.getPropertyName(column), val);
        return castChain();
    }

    /**
     * column like %val
     * 列匹配后半部分
     *
     * @param condition
     * @param column
     * @param val
     * @return
     */
    default <TProperty> TChain likeMatchRight(boolean condition, KProperty1<? super T1, TProperty> column, TProperty val) {
        getWherePredicate().likeMatchRight(condition, EasyKtLambdaUtil.getPropertyName(column), val);
        return castChain();
    }

    /**
     * column like %val%
     * 列全匹配
     */
    default <TProperty> TChain like(KProperty1<? super T1, TProperty> column, TProperty val) {
        getWherePredicate().like(EasyKtLambdaUtil.getPropertyName(column), val);
        return castChain();
    }

    /**
     * column like %val%
     * 列全匹配
     *
     * @param condition 执行条件
     * @param column    字段
     * @param val       值
     * @return children
     */
    default <TProperty> TChain like(boolean condition, KProperty1<? super T1, TProperty> column, TProperty val) {
        getWherePredicate().like(condition, EasyKtLambdaUtil.getPropertyName(column), val);
        return castChain();
    }

    /**
     * column like ?val?
     * 列自定义匹配
     *
     * @param condition
     * @param column
     * @param val
     * @param sqlLike
     * @return
     */
    default <TProperty> TChain like(boolean condition, KProperty1<? super T1, TProperty> column, TProperty val, SQLLikeEnum sqlLike) {
        getWherePredicate().like(condition, EasyKtLambdaUtil.getPropertyName(column), val, sqlLike);
        return castChain();
    }

    /**
     * column not like val%
     *
     * @param column
     * @param val
     * @return
     */
    default <TProperty> TChain notLikeMatchLeft(KProperty1<? super T1, TProperty> column, TProperty val) {
        getWherePredicate().notLikeMatchLeft(EasyKtLambdaUtil.getPropertyName(column), val);
        return castChain();
    }

    /**
     * column not like val%
     *
     * @param condition
     * @param column
     * @param val
     * @return
     */
    default <TProperty> TChain notLikeMatchLeft(boolean condition, KProperty1<? super T1, TProperty> column, TProperty val) {
        getWherePredicate().notLikeMatchLeft(condition, EasyKtLambdaUtil.getPropertyName(column), val);
        return castChain();
    }

    /**
     * column not like %val
     *
     * @param column
     * @param val
     * @return
     */
    default <TProperty> TChain notLikeMatchRight(KProperty1<? super T1, TProperty> column, TProperty val) {
        getWherePredicate().notLikeMatchRight(EasyKtLambdaUtil.getPropertyName(column), val);
        return castChain();
    }

    /**
     * column not like %val
     *
     * @param condition
     * @param column
     * @param val
     * @return
     */
    default <TProperty> TChain notLikeMatchRight(boolean condition, KProperty1<? super T1, TProperty> column, TProperty val) {
        getWherePredicate().notLikeMatchRight(condition, EasyKtLambdaUtil.getPropertyName(column), val);
        return castChain();
    }

    /**
     * column not like %val%
     */
    default <TProperty> TChain notLike(KProperty1<? super T1, TProperty> column, TProperty val) {
        getWherePredicate().notLike(EasyKtLambdaUtil.getPropertyName(column), val);
        return castChain();
    }

    /**
     * column not like %val%
     *
     * @param condition 执行条件
     * @param column    字段
     * @param val       值
     * @return children
     */
    default <TProperty> TChain notLike(boolean condition, KProperty1<? super T1, TProperty> column, TProperty val) {
        getWherePredicate().notLike(condition, EasyKtLambdaUtil.getPropertyName(column), val);
        return castChain();
    }

    default <TProperty> TChain notLike(boolean condition, KProperty1<? super T1, TProperty> column, TProperty val, SQLLikeEnum sqlLike) {
        getWherePredicate().notLike(condition, EasyKtLambdaUtil.getPropertyName(column), val, sqlLike);
        return castChain();
    }

}
