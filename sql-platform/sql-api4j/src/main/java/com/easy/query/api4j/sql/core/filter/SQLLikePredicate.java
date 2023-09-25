package com.easy.query.api4j.sql.core.filter;

import com.easy.query.api4j.util.EasyLambdaUtil;
import com.easy.query.core.enums.SQLLikeEnum;
import com.easy.query.core.expression.lambda.Property;
import com.easy.query.core.expression.parser.core.EntitySQLTableOwner;
import com.easy.query.core.expression.parser.core.available.ChainCast;

/**
 * create time 2023/9/25 17:15
 * 文件说明
 *
 * @author xuejiaming
 */
public interface SQLLikePredicate<T1, TChain> extends EntitySQLTableOwner<T1>, SQLWherePredicateAvailable<T1>, ChainCast<TChain> {

    /**
     * column like val%
     * 列匹配前半部分
     *
     * @param column
     * @param val
     * @return
     */
    default <TProperty> TChain likeMatchLeft(Property<T1, TProperty> column, TProperty val) {
        getWherePredicate().likeMatchLeft(EasyLambdaUtil.getPropertyName(column), val);
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
    default <TProperty> TChain likeMatchLeft(boolean condition, Property<T1, TProperty> column, TProperty val) {
        getWherePredicate().likeMatchLeft(condition, EasyLambdaUtil.getPropertyName(column), val);
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
    default <TProperty> TChain likeMatchRight(Property<T1, TProperty> column, TProperty val) {
        getWherePredicate().likeMatchRight(EasyLambdaUtil.getPropertyName(column), val);
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
    default <TProperty> TChain likeMatchRight(boolean condition, Property<T1, TProperty> column, TProperty val) {
        getWherePredicate().likeMatchRight(condition, EasyLambdaUtil.getPropertyName(column), val);
        return castChain();
    }

    /**
     * column like %val%
     * 列全匹配
     */
    default <TProperty> TChain like(Property<T1, TProperty> column, TProperty val) {
        getWherePredicate().like(EasyLambdaUtil.getPropertyName(column), val);
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
    default <TProperty> TChain like(boolean condition, Property<T1, TProperty> column, TProperty val) {
        getWherePredicate().like(condition, EasyLambdaUtil.getPropertyName(column), val);
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
    default <TProperty> TChain like(boolean condition, Property<T1, TProperty> column, TProperty val, SQLLikeEnum sqlLike) {
        getWherePredicate().like(condition, EasyLambdaUtil.getPropertyName(column), val, sqlLike);
        return castChain();
    }

    /**
     * column not like val%
     *
     * @param column
     * @param val
     * @return
     */
    default <TProperty> TChain notLikeMatchLeft(Property<T1, TProperty> column, TProperty val) {
        getWherePredicate().notLikeMatchLeft(EasyLambdaUtil.getPropertyName(column), val);
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
    default <TProperty> TChain notLikeMatchLeft(boolean condition, Property<T1, TProperty> column, TProperty val) {
        getWherePredicate().notLikeMatchLeft(condition, EasyLambdaUtil.getPropertyName(column), val);
        return castChain();
    }

    /**
     * column not like %val
     *
     * @param column
     * @param val
     * @return
     */
    default <TProperty> TChain notLikeMatchRight(Property<T1, TProperty> column, TProperty val) {
        getWherePredicate().notLikeMatchRight(EasyLambdaUtil.getPropertyName(column), val);
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
    default <TProperty> TChain notLikeMatchRight(boolean condition, Property<T1, TProperty> column, TProperty val) {
        getWherePredicate().notLikeMatchRight(condition, EasyLambdaUtil.getPropertyName(column), val);
        return castChain();
    }

    /**
     * column not like %val%
     */
    default <TProperty> TChain notLike(Property<T1, TProperty> column, TProperty val) {
        getWherePredicate().notLike(EasyLambdaUtil.getPropertyName(column), val);
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
    default <TProperty> TChain notLike(boolean condition, Property<T1, TProperty> column, TProperty val) {
        getWherePredicate().notLike(condition, EasyLambdaUtil.getPropertyName(column), val);
        return castChain();
    }

    default <TProperty> TChain notLike(boolean condition, Property<T1, TProperty> column, TProperty val, SQLLikeEnum sqlLike) {
        getWherePredicate().notLike(condition, EasyLambdaUtil.getPropertyName(column), val, sqlLike);
        return castChain();
    }

}
