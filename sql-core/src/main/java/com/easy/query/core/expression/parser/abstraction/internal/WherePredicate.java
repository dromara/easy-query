package com.easy.query.core.expression.parser.abstraction.internal;

import com.easy.query.core.enums.SqlLikeEnum;
import com.easy.query.core.enums.SqlRangeEnum;
import com.easy.query.core.expression.lambda.Property;
import com.easy.query.core.expression.lambda.SqlExpression;

import java.util.Collection;

/**
 * @FileName: WherePredicate.java
 * @Description: 文件说明
 * @Date: 2023/2/5 09:09
 * @author xuejiaming
 */
public interface WherePredicate<T1, TChain> extends IndexAware {
    /**
     * 大于 column > val
     */
    default TChain gt(Property<T1, ?> column, Object val) {
        return gt(true, column, val);
    }

    /**
     * 大于 column > val
     *
     * @param condition 执行条件
     * @param column    字段
     * @param val       值
     * @return children
     */
    TChain gt(boolean condition, Property<T1, ?> column, Object val);

    /**
     * 等于 column >= val
     */
    default TChain ge(Property<T1, ?> column, Object val) {
        return ge(true, column, val);
    }

    /**
     * 等于 column >= val
     *
     * @param condition 执行条件
     * @param column    字段
     * @param val       值
     * @return children
     */
    TChain ge(boolean condition, Property<T1, ?> column, Object val);

    /**
     * 等于 column = val
     */
    default TChain eq(Property<T1, ?> column, Object val) {
        return eq(true, column, val);
    }

    /**
     * 等于 column = val
     *
     * @param condition 执行条件
     * @param column    字段
     * @param val       值
     * @return children
     */
    TChain eq(boolean condition, Property<T1, ?> column, Object val);

    /**
     * 不等于 column <> val
     */
    default TChain ne(Property<T1, ?> column, Object val) {
        return ne(true, column, val);
    }

    /**
     * 不等于 column <> val
     *
     * @param condition 执行条件
     * @param column    字段
     * @param val       值
     * @return children
     */
    TChain ne(boolean condition, Property<T1, ?> column, Object val);

    /**
     * 小于等于 column <= val
     */
    default TChain le(Property<T1, ?> column, Object val) {
        return le(true, column, val);
    }

    /**
     * 小于等于 column <= val
     *
     * @param condition 执行条件
     * @param column    字段
     * @param val       值
     * @return children
     */
    TChain le(boolean condition, Property<T1, ?> column, Object val);

    /**
     * 小于 column < val
     */
    default TChain lt(Property<T1, ?> column, Object val) {
        return lt(true, column, val);
    }

    /**
     * 小于 column < val
     *
     * @param condition 执行条件
     * @param column    字段
     * @param val       值
     * @return children
     */
    TChain lt(boolean condition, Property<T1, ?> column, Object val);

    /**
     * column like val%
     * 列匹配前半部分
     * @param column
     * @param val
     * @return
     */
    default TChain likeMatchLeft(Property<T1, ?> column, Object val) {
        return likeMatchLeft(true, column, val);
    }

    /**
     * column like val%
     * 列匹配前半部分
     * @param condition
     * @param column
     * @param val
     * @return
     */
    default TChain likeMatchLeft(boolean condition, Property<T1, ?> column, Object val) {
        return like(condition, column, val, SqlLikeEnum.LIKE_START);
    }

    /**
     * column like %val
     * 列匹配后半部分
     * @param column
     * @param val
     * @return
     */
    default TChain likeMatchRight(Property<T1, ?> column, Object val) {
        return likeMatchRight(true, column, val);
    }

    /**
     * column like %val
     * 列匹配后半部分
     * @param condition
     * @param column
     * @param val
     * @return
     */
    default TChain likeMatchRight(boolean condition, Property<T1, ?> column, Object val) {
        return like(condition, column, val, SqlLikeEnum.LIKE_END);
    }

    /**
     * column like %val%
     * 列全匹配
     */
    default TChain like(Property<T1, ?> column, Object val) {
        return like(true, column, val);
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
    default TChain like(boolean condition, Property<T1, ?> column, Object val) {
        return like(condition, column, val, SqlLikeEnum.LIKE_ALL);
    }

    /**
     * column like ?val?
     * 列自定义匹配
     * @param condition
     * @param column
     * @param val
     * @param sqlLike
     * @return
     */
    TChain like(boolean condition, Property<T1, ?> column, Object val, SqlLikeEnum sqlLike);

    /**
     * column not like val%
     * @param column
     * @param val
     * @return
     */
    default TChain notLikeStart(Property<T1, ?> column, Object val) {
        return notLikeStart(true, column, val);
    }

    /**
     * column not like val%
     * @param condition
     * @param column
     * @param val
     * @return
     */
    default TChain notLikeStart(boolean condition, Property<T1, ?> column, Object val) {
        return notLike(condition, column, val, SqlLikeEnum.LIKE_ALL);
    }

    /**
     * column not like %val
     * @param column
     * @param val
     * @return
     */
    default TChain notLikeEnd(Property<T1, ?> column, Object val) {
        return notLikeEnd(true, column, val);
    }

    /**
     * column not like %val
     * @param condition
     * @param column
     * @param val
     * @return
     */
    default TChain notLikeEnd(boolean condition, Property<T1, ?> column, Object val) {
        return notLike(condition, column, val, SqlLikeEnum.LIKE_END);
    }

    /**
     * column not like %val%
     */
    default TChain notLike(Property<T1, ?> column, Object val) {
        return notLike(true, column, val);
    }

    /**
     * column not like %val%
     *
     * @param condition 执行条件
     * @param column    字段
     * @param val       值
     * @return children
     */
    default TChain notLike(boolean condition, Property<T1, ?> column, Object val) {
        return notLike(condition, column, val, SqlLikeEnum.LIKE_ALL);
    }

    TChain notLike(boolean condition, Property<T1, ?> column, Object val, SqlLikeEnum sqlLike);

    /**
     * column is null
     */
    default TChain isNull(Property<T1, ?> column) {
        return isNull(true, column);
    }

    /**
     * column is null
     *
     * @param condition 执行条件
     * @param column    字段
     * @return children
     */
    TChain isNull(boolean condition, Property<T1, ?> column);

    /**
     * column is not null
     */
    default TChain isNotNull(Property<T1, ?> column) {
        return isNotNull(true, column);
    }

    /**
     * column is not null
     *
     * @param condition 执行条件
     * @param column    字段
     * @return children
     */
    TChain isNotNull(boolean condition, Property<T1, ?> column);

    /**
     * column in collection
     */
    default TChain in(Property<T1, ?> column, Collection<?> collection) {
        return in(true, column, collection);
    }

    /**
     * column in collection
     */
    TChain in(boolean condition, Property<T1, ?> column, Collection<?> collection);

    /**
     * column not in collection
     */
    default TChain notIn(Property<T1, ?> column, Collection<?> collection) {
        return notIn(true, column, collection);
    }

    /**
     * column not in collection
     */
    TChain notIn(boolean condition, Property<T1, ?> column, Collection<?> collection);

    /**
     * 区间 (left..right) = {x | left < x < right}
     * 一般用于范围比如时间,小的时间在前大的时间在后
     * @param column
     * @param conditionLeft
     * @param valLeft
     * @param conditionRight
     * @param valRight
     * @return
     */
    default TChain rangeOpen(Property<T1, ?> column, boolean conditionLeft, Object valLeft, boolean conditionRight, Object valRight) {
        return rangeOpen(true, column, conditionLeft, valLeft, conditionRight, valRight);
    }

    /**
     * 区间 (left..right) = {x | left < x < right}
     * 一般用于范围比如时间,小的时间在前大的时间在后
     * @param condition
     * @param column
     * @param conditionLeft
     * @param valLeft
     * @param conditionRight
     * @param valRight
     * @return
     */
    default TChain rangeOpen(boolean condition, Property<T1, ?> column, boolean conditionLeft, Object valLeft, boolean conditionRight, Object valRight) {
        return range(condition, column, conditionLeft, valLeft, conditionRight, valRight, SqlRangeEnum.Open);
    }

    /**
     * [left..right] = {x | left <= x <= right}
     * 一般用于范围比如时间,小的时间在前大的时间在后
     * @param column
     * @param conditionLeft
     * @param valLeft
     * @param conditionRight
     * @param valRight
     * @return
     */
    default TChain rangeClosed(Property<T1, ?> column, boolean conditionLeft, Object valLeft, boolean conditionRight, Object valRight) {
        return rangeClosed(true, column, conditionLeft, valLeft, conditionRight, valRight);
    }

    /**
     * [left..right] = {x | left <= x <= right}
     * 一般用于范围比如时间,小的时间在前大的时间在后
     * @param condition
     * @param column
     * @param conditionLeft
     * @param valLeft
     * @param conditionRight
     * @param valRight
     * @return
     */
    default TChain rangeClosed(boolean condition, Property<T1, ?> column, boolean conditionLeft, Object valLeft, boolean conditionRight, Object valRight) {
        return range(condition, column, conditionLeft, valLeft, conditionRight, valRight, SqlRangeEnum.Closed);
    }

    /**
     * 自定义范围
     * 一般用于范围比如时间,小的时间在前大的时间在后
     * @param condition
     * @param column
     * @param conditionLeft
     * @param valLeft
     * @param conditionRight
     * @param valRight
     * @param sqlRange
     * @return
     */
    TChain range(boolean condition, Property<T1, ?> column, boolean conditionLeft, Object valLeft, boolean conditionRight, Object valRight, SqlRangeEnum sqlRange);


    default <T2, TChain2> TChain eq(WherePredicate<T2, TChain2> sub, Property<T1, ?> column1, Property<T2, ?> column2) {
        return eq(true, sub, column1, column2);
    }

    <T2, TChain2> TChain eq(boolean condition, WherePredicate<T2, TChain2> sub, Property<T1, ?> column1, Property<T2, ?> column2);

    <T2, TChain2> WherePredicate<T2, TChain2> then(WherePredicate<T2, TChain2> sub);

    default TChain and() {
        return and(true);
    }

    TChain and(boolean condition);

    default TChain and(SqlExpression<TChain> predicateSqlExpression) {
        return and(true, predicateSqlExpression);
    }

    TChain and(boolean condition, SqlExpression<TChain> predicateSqlExpression);

    default TChain or() {
        return or(true);
    }

    TChain or(boolean condition);

    default TChain or(SqlExpression<TChain> predicateSqlExpression) {
        return or(true, predicateSqlExpression);
    }

    TChain or(boolean condition, SqlExpression<TChain> predicateSqlExpression);
}
