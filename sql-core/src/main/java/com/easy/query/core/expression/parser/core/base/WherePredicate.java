package com.easy.query.core.expression.parser.core.base;

import com.easy.query.core.basic.api.select.Query;
import com.easy.query.core.enums.SQLLikeEnum;
import com.easy.query.core.enums.SQLPredicateCompare;
import com.easy.query.core.enums.SQLRangeEnum;
import com.easy.query.core.expression.SQLTableOwner;
import com.easy.query.core.expression.builder.Filter;
import com.easy.query.core.expression.func.ColumnPropertyFunction;
import com.easy.query.core.expression.lambda.SQLExpression1;

import java.util.Collection;

/**
 * @author xuejiaming
 * @FileName: WherePredicate.java
 * @Description: 文件说明
 * @Date: 2023/2/5 09:09
 */
public interface WherePredicate<T1> extends SQLTableOwner {
    Filter getFilter();

    /**
     * 大于 column > val
     */
    default WherePredicate<T1> gt(String property, Object val) {
        return gt(true, property, val);
    }

    /**
     * 大于 column > val
     *
     * @param condition 执行条件
     * @param property    字段
     * @param val       值
     * @return children
     */
    WherePredicate<T1> gt(boolean condition, String property, Object val);

    /**
     * 等于 column >= val
     */
    default WherePredicate<T1> ge(String property, Object val) {
        return ge(true, property, val);
    }

    /**
     * 等于 column >= val
     *
     * @param condition 执行条件
     * @param property  字段
     * @param val       值
     * @return children
     */
    WherePredicate<T1> ge(boolean condition, String property, Object val);

    /**
     * 等于 column = val
     */
    default WherePredicate<T1> eq(String property, Object val) {
        return eq(true, property, val);
    }

    /**
     * 等于 column = val
     *
     * @param condition 执行条件
     * @param property    字段
     * @param val       值
     * @return children
     */
    WherePredicate<T1> eq(boolean condition, String property, Object val);

    /**
     * 不等于 column <> val
     */
    default WherePredicate<T1> ne(String property, Object val) {
        return ne(true, property, val);
    }

    /**
     * 不等于 column <> val
     *
     * @param condition 执行条件
     * @param property    字段
     * @param val       值
     * @return children
     */
    WherePredicate<T1> ne(boolean condition, String property, Object val);

    /**
     * 小于等于 column <= val
     */
    default WherePredicate<T1> le(String property, Object val) {
        return le(true, property, val);
    }

    /**
     * 小于等于 column <= val
     *
     * @param condition 执行条件
     * @param property    字段
     * @param val       值
     * @return children
     */
    WherePredicate<T1> le(boolean condition, String property, Object val);

    /**
     * 小于 column < val
     */
    default WherePredicate<T1> lt(String property, Object val) {
        return lt(true, property, val);
    }

    /**
     * 小于 column < val
     *
     * @param condition 执行条件
     * @param property    字段
     * @param val       值
     * @return children
     */
    WherePredicate<T1> lt(boolean condition, String property, Object val);

    /**
     * column like val%
     * 列匹配前半部分
     * @param property
     * @param val
     * @return
     */
    default WherePredicate<T1> likeMatchLeft(String property, Object val) {
        return likeMatchLeft(true, property, val);
    }

    /**
     * column like val%
     * 列匹配前半部分
     * @param condition
     * @param property
     * @param val
     * @return
     */
    default WherePredicate<T1> likeMatchLeft(boolean condition, String property, Object val) {
        return like(condition, property, val, SQLLikeEnum.LIKE_PERCENT_RIGHT);
    }

    /**
     * column like %val
     * 列匹配后半部分
     * @param property
     * @param val
     * @return
     */
    default WherePredicate<T1> likeMatchRight(String property, Object val) {
        return likeMatchRight(true, property, val);
    }

    /**
     * column like %val
     * 列匹配后半部分
     * @param condition
     * @param property
     * @param val
     * @return
     */
    default WherePredicate<T1> likeMatchRight(boolean condition, String property, Object val) {
        return like(condition, property, val, SQLLikeEnum.LIKE_PERCENT_LEFT);
    }

    /**
     * column like %val%
     * 列全匹配
     */
    default WherePredicate<T1> like(String property, Object val) {
        return like(true, property, val);
    }

    /**
     * column like %val%
     * 列全匹配
     *
     * @param condition 执行条件
     * @param property    字段
     * @param val       值
     * @return children
     */
    default WherePredicate<T1> like(boolean condition, String property, Object val) {
        return like(condition, property, val, SQLLikeEnum.LIKE_PERCENT_ALL);
    }

    /**
     * column like ?val?
     * 列自定义匹配
     * @param condition
     * @param property
     * @param val
     * @param sqlLike
     * @return
     */
    WherePredicate<T1> like(boolean condition, String property, Object val, SQLLikeEnum sqlLike);

    /**
     * column not like val%
     * @param property
     * @param val
     * @return
     */
    default WherePredicate<T1> notLikeMatchLeft(String property, Object val) {
        return notLikeMatchLeft(true, property, val);
    }

    /**
     * column not like val%
     * @param condition
     * @param property
     * @param val
     * @return
     */
    default WherePredicate<T1> notLikeMatchLeft(boolean condition, String property, Object val) {
        return notLike(condition, property, val, SQLLikeEnum.LIKE_PERCENT_RIGHT);
    }

    /**
     * column not like %val
     * @param property
     * @param val
     * @return
     */
    default WherePredicate<T1> notLikeMatchRight(String property, Object val) {
        return notLikeMatchRight(true, property, val);
    }

    /**
     * column not like %val
     * @param condition
     * @param property
     * @param val
     * @return
     */
    default WherePredicate<T1> notLikeMatchRight(boolean condition, String property, Object val) {
        return notLike(condition, property, val, SQLLikeEnum.LIKE_PERCENT_LEFT);
    }

    /**
     * column not like %val%
     */
    default WherePredicate<T1> notLike(String property, Object val) {
        return notLike(true, property, val);
    }

    /**
     * column not like %val%
     *
     * @param condition 执行条件
     * @param property    字段
     * @param val       值
     * @return children
     */
    default WherePredicate<T1> notLike(boolean condition, String property, Object val) {
        return notLike(condition, property, val, SQLLikeEnum.LIKE_PERCENT_ALL);
    }

    WherePredicate<T1> notLike(boolean condition, String property, Object val, SQLLikeEnum sqlLike);

    /**
     * column is null
     */
    default WherePredicate<T1> isNull(String property) {
        return isNull(true, property);
    }

    /**
     * column is null
     *
     * @param condition 执行条件
     * @param property    字段
     * @return children
     */
    WherePredicate<T1> isNull(boolean condition, String property);

    /**
     * column is not null
     */
    default WherePredicate<T1> isNotNull(String property) {
        return isNotNull(true, property);
    }

    /**
     * column is not null
     *
     * @param condition 执行条件
     * @param property    字段
     * @return children
     */
    WherePredicate<T1> isNotNull(boolean condition, String property);

    /**
     * column in collection
     * 集合为空返回False
     */
    default WherePredicate<T1> in(String property, Collection<?> collection) {
        return in(true, property, collection);
    }

    /**
     * column in collection
     * 集合为空返回False
     */
    WherePredicate<T1> in(boolean condition, String property, Collection<?> collection);

    default <TProperty> WherePredicate<T1> in(String property, TProperty[] collection) {
        return in(true, property, collection);
    }
    <TProperty> WherePredicate<T1> in(boolean condition, String property, TProperty[] collection);



    default <TProperty> WherePredicate<T1> in(String property, Query<TProperty> subQuery) {
        return in(true, property, subQuery);
    }

    <TProperty> WherePredicate<T1> in(boolean condition, String property, Query<TProperty> subQuery);

    /**
     * column not in collection
     * 集合为空返回True
     */
    default WherePredicate<T1> notIn(String property, Collection<?> collection) {
        return notIn(true, property, collection);
    }

    /**
     * column not in collection
     * 集合为空返回True
     */
    WherePredicate<T1> notIn(boolean condition, String property, Collection<?> collection);


    default <TProperty> WherePredicate<T1> notIn(String property, TProperty[] collection) {
        return notIn(true, property, collection);
    }
    <TProperty> WherePredicate<T1> notIn(boolean condition, String property, TProperty[] collection);

    default <TProperty> WherePredicate<T1> notIn(String property, Query<TProperty> subQuery) {
        return notIn(true, property, subQuery);
    }

    <TProperty> WherePredicate<T1> notIn(boolean condition, String property, Query<TProperty> subQuery);

    default <T2> WherePredicate<T1> exists(Query<T2> subQuery) {
        return exists(true, subQuery);
    }

    <T2> WherePredicate<T1> exists(boolean condition, Query<T2> subQuery);

    default <T2> WherePredicate<T1> notExists(Query<T2> subQuery) {
        return notExists(true, subQuery);
    }

    <T2> WherePredicate<T1> notExists(boolean condition, Query<T2> subQuery);

    /**
     * 区间 (left..right] = {x | left < x <= right}
     * 一般用于范围比如时间,小的时间在前大的时间在后
     *
     * @param property
     * @param conditionLeft
     * @param valLeft
     * @param conditionRight
     * @param valRight
     * @return
     */
    default WherePredicate<T1> rangeOpenClosed(String property, boolean conditionLeft, Object valLeft, boolean conditionRight, Object valRight) {
        return rangeOpenClosed(true, property, conditionLeft, valLeft, conditionRight, valRight);
    }

    /**
     * 区间 (left..right] = {x | left < x <= right}
     * 一般用于范围比如时间,小的时间在前大的时间在后
     *
     * @param condition
     * @param property
     * @param conditionLeft
     * @param valLeft
     * @param conditionRight
     * @param valRight
     * @return
     */
    default WherePredicate<T1> rangeOpenClosed(boolean condition, String property, boolean conditionLeft, Object valLeft, boolean conditionRight, Object valRight) {
        return range(condition, property, conditionLeft, valLeft, conditionRight, valRight, SQLRangeEnum.OPEN_CLOSED);
    }

    /**
     * 区间 (left..right) = {x | left < x < right}
     * 一般用于范围比如时间,小的时间在前大的时间在后
     *
     * @param property 数据库列
     * @param valLeft  区间左侧值
     * @param valRight 区间右侧的值
     * @return
     */
    default WherePredicate<T1> rangeOpen(String property, Object valLeft, Object valRight) {
        return rangeOpen(true, property, true, valLeft, true, valRight);
    }

    /**
     * 区间 (left..right) = {x | left < x < right}
     * 一般用于范围比如时间,小的时间在前大的时间在后
     *
     * @param property       数据库列
     * @param conditionLeft  是否添加左侧条件
     * @param valLeft        区间左侧值
     * @param conditionRight 是否添加右侧条件
     * @param valRight       区间右侧的值
     * @return
     */
    default WherePredicate<T1> rangeOpen(String property, boolean conditionLeft, Object valLeft, boolean conditionRight, Object valRight) {
        return rangeOpen(true, property, conditionLeft, valLeft, conditionRight, valRight);
    }

    /**
     * 区间 (left..right) = {x | left < x < right}
     * 一般用于范围比如时间,小的时间在前大的时间在后
     *
     * @param condition
     * @param property
     * @param conditionLeft
     * @param valLeft
     * @param conditionRight
     * @param valRight
     * @return
     */
    default WherePredicate<T1> rangeOpen(boolean condition, String property, boolean conditionLeft, Object valLeft, boolean conditionRight, Object valRight) {
        return range(condition, property, conditionLeft, valLeft, conditionRight, valRight, SQLRangeEnum.OPEN);
    }

    /**
     * [left..right) = {x | left <= x < right}
     * 一般用于范围比如时间,小的时间在前大的时间在后
     *
     * @param property
     * @param conditionLeft
     * @param valLeft
     * @param conditionRight
     * @param valRight
     * @return
     */
    default WherePredicate<T1> rangeClosedOpen(String property, boolean conditionLeft, Object valLeft, boolean conditionRight, Object valRight) {
        return rangeClosedOpen(true, property, conditionLeft, valLeft, conditionRight, valRight);
    }

    /**
     * [left..right) = {x | left <= x < right}
     * 一般用于范围比如时间,小的时间在前大的时间在后
     *
     * @param condition
     * @param property
     * @param conditionLeft
     * @param valLeft
     * @param conditionRight
     * @param valRight
     * @return
     */
    default WherePredicate<T1> rangeClosedOpen(boolean condition, String property, boolean conditionLeft, Object valLeft, boolean conditionRight, Object valRight) {
        return range(condition, property, conditionLeft, valLeft, conditionRight, valRight, SQLRangeEnum.CLOSED_OPEN);
    }

    /**
     * [left..right] = {x | left <= x <= right}
     * 一般用于范围比如时间,小的时间在前大的时间在后
     *
     * @param property
     * @param valLeft
     * @param valRight
     * @return
     */
    default WherePredicate<T1> rangeClosed(String property, Object valLeft, Object valRight) {
        return rangeClosed(true, property, true, valLeft, true, valRight);
    }

    /**
     * [left..right] = {x | left <= x <= right}
     * 一般用于范围比如时间,小的时间在前大的时间在后
     *
     * @param property
     * @param conditionLeft
     * @param valLeft
     * @param conditionRight
     * @param valRight
     * @return
     */
    default WherePredicate<T1> rangeClosed(String property, boolean conditionLeft, Object valLeft, boolean conditionRight, Object valRight) {
        return rangeClosed(true, property, conditionLeft, valLeft, conditionRight, valRight);
    }

    /**
     * [left..right] = {x | left <= x <= right}
     * 一般用于范围比如时间,小的时间在前大的时间在后
     *
     * @param condition
     * @param property
     * @param conditionLeft
     * @param valLeft
     * @param conditionRight
     * @param valRight
     * @return
     */
    default WherePredicate<T1> rangeClosed(boolean condition, String property, boolean conditionLeft, Object valLeft, boolean conditionRight, Object valRight) {
        return range(condition, property, conditionLeft, valLeft, conditionRight, valRight, SQLRangeEnum.CLOSED);
    }

    /**
     * 自定义范围
     * 一般用于范围比如时间,小的时间在前大的时间在后
     *
     * @param condition
     * @param property
     * @param conditionLeft
     * @param valLeft
     * @param conditionRight
     * @param valRight
     * @param sqlRange
     * @return
     */
    WherePredicate<T1> range(boolean condition, String property, boolean conditionLeft, Object valLeft, boolean conditionRight, Object valRight, SQLRangeEnum sqlRange);


    default WherePredicate<T1> columnFunc(ColumnPropertyFunction columnPropertyFunction, SQLPredicateCompare sqlPredicateCompare, Object val) {
        return columnFunc(true, columnPropertyFunction, sqlPredicateCompare, val);
    }

    WherePredicate<T1> columnFunc(boolean condition, ColumnPropertyFunction columnPropertyFunction, SQLPredicateCompare sqlPredicateCompare, Object val);

    default <T2> WherePredicate<T1> gt(WherePredicate<T2> sub, String property1, String property2) {
        return gt(true, sub, property1, property2);
    }

    <T2> WherePredicate<T1> gt(boolean condition, WherePredicate<T2> sub, String property1, String property2);
    default <T2> WherePredicate<T1> ge(WherePredicate<T2> sub, String property1, String property2) {
        return ge(true, sub, property1, property2);
    }

    <T2> WherePredicate<T1> ge(boolean condition, WherePredicate<T2> sub, String property1, String property2);
    default <T2> WherePredicate<T1> eq(WherePredicate<T2> sub, String property1, String property2) {
        return eq(true, sub, property1, property2);
    }

    <T2> WherePredicate<T1> eq(boolean condition, WherePredicate<T2> sub, String property1, String property2);
    default <T2> WherePredicate<T1> ne(WherePredicate<T2> sub, String property1, String property2) {
        return ne(true, sub, property1, property2);
    }

    <T2> WherePredicate<T1> ne(boolean condition, WherePredicate<T2> sub, String property1, String property2);
    default <T2> WherePredicate<T1> le(WherePredicate<T2> sub, String property1, String property2) {
        return le(true, sub, property1, property2);
    }

    <T2> WherePredicate<T1> le(boolean condition, WherePredicate<T2> sub, String property1, String property2);
    default <T2> WherePredicate<T1> lt(WherePredicate<T2> sub, String property1, String property2) {
        return lt(true, sub, property1, property2);
    }

    <T2> WherePredicate<T1> lt(boolean condition, WherePredicate<T2> sub, String property1, String property2);

    <T2> WherePredicate<T2> then(WherePredicate<T2> sub);

    default WherePredicate<T1> and() {
        return and(true);
    }

    WherePredicate<T1> and(boolean condition);

    default WherePredicate<T1> and(SQLExpression1<WherePredicate<T1>> sqlWherePredicateSQLExpression) {
        return and(true, sqlWherePredicateSQLExpression);
    }

    WherePredicate<T1> and(boolean condition, SQLExpression1<WherePredicate<T1>> sqlWherePredicateSQLExpression);

    default WherePredicate<T1> or() {
        return or(true);
    }

    WherePredicate<T1> or(boolean condition);

    default WherePredicate<T1> or(SQLExpression1<WherePredicate<T1>> sqlWherePredicateSQLExpression) {
        return or(true, sqlWherePredicateSQLExpression);
    }

    WherePredicate<T1> or(boolean condition, SQLExpression1<WherePredicate<T1>> sqlWherePredicateSQLExpression);
}
