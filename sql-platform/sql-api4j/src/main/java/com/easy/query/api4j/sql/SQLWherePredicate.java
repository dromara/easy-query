package com.easy.query.api4j.sql;

import com.easy.query.api4j.select.Queryable;
import com.easy.query.core.enums.SQLLikeEnum;
import com.easy.query.core.enums.SQLPredicateCompare;
import com.easy.query.core.enums.SQLRangeEnum;
import com.easy.query.core.expression.func.ColumnPropertyFunction;
import com.easy.query.core.expression.lambda.Property;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.parser.core.base.WherePredicate;
import com.easy.query.core.util.EasyLambdaUtil;

import java.util.Collection;

/**
 * @author xuejiaming
 * @FileName: WherePredicate.java
 * @Description: 文件说明
 * @Date: 2023/2/5 09:09
 */
public interface SQLWherePredicate<T1> {
    WherePredicate<T1> getWherePredicate();

    default TableAvailable getTable() {
        return getWherePredicate().getTable();
    }

    /**
     * 大于 column > val
     */
    default SQLWherePredicate<T1> gt(Property<T1, ?> column, Object val) {
        getWherePredicate().gt(true, EasyLambdaUtil.getPropertyName(column), val);
        return this;
    }

    /**
     * 大于 column > val
     *
     * @param condition 执行条件
     * @param column    字段
     * @param val       值
     * @return children
     */
    default SQLWherePredicate<T1> gt(boolean condition, Property<T1, ?> column, Object val) {
        getWherePredicate().gt(condition, EasyLambdaUtil.getPropertyName(column), val);
        return this;
    }

    /**
     * 等于 column >= val
     */
    default SQLWherePredicate<T1> ge(Property<T1, ?> column, Object val) {
        getWherePredicate().ge(EasyLambdaUtil.getPropertyName(column), val);
        return this;
    }

    /**
     * 等于 column >= val
     *
     * @param condition 执行条件
     * @param column    字段
     * @param val       值
     * @return children
     */
    default SQLWherePredicate<T1> ge(boolean condition, Property<T1, ?> column, Object val) {
        getWherePredicate().ge(condition, EasyLambdaUtil.getPropertyName(column), val);
        return this;
    }

    /**
     * 等于 column = val
     */
    default SQLWherePredicate<T1> eq(Property<T1, ?> column, Object val) {
        getWherePredicate().eq(EasyLambdaUtil.getPropertyName(column), val);
        return this;
    }

    /**
     * 等于 column = val
     *
     * @param condition 执行条件
     * @param column    字段
     * @param val       值
     * @return children
     */
    default SQLWherePredicate<T1> eq(boolean condition, Property<T1, ?> column, Object val) {
        getWherePredicate().eq(condition, EasyLambdaUtil.getPropertyName(column), val);
        return this;
    }

    /**
     * 不等于 column <> val
     */
    default SQLWherePredicate<T1> ne(Property<T1, ?> column, Object val) {
        getWherePredicate().ne(EasyLambdaUtil.getPropertyName(column), val);
        return this;
    }

    /**
     * 不等于 column <> val
     *
     * @param condition 执行条件
     * @param column    字段
     * @param val       值
     * @return children
     */
    default SQLWherePredicate<T1> ne(boolean condition, Property<T1, ?> column, Object val) {
        getWherePredicate().ne(condition, EasyLambdaUtil.getPropertyName(column), val);
        return this;
    }

    /**
     * 小于等于 column <= val
     */
    default SQLWherePredicate<T1> le(Property<T1, ?> column, Object val) {
        getWherePredicate().le(EasyLambdaUtil.getPropertyName(column), val);
        return this;
    }

    /**
     * 小于等于 column <= val
     *
     * @param condition 执行条件
     * @param column    字段
     * @param val       值
     * @return children
     */
    default SQLWherePredicate<T1> le(boolean condition, Property<T1, ?> column, Object val) {
        getWherePredicate().le(condition, EasyLambdaUtil.getPropertyName(column), val);
        return this;
    }

    /**
     * 小于 column < val
     */
    default SQLWherePredicate<T1> lt(Property<T1, ?> column, Object val) {
        getWherePredicate().lt(EasyLambdaUtil.getPropertyName(column), val);
        return this;
    }

    /**
     * 小于 column < val
     *
     * @param condition 执行条件
     * @param column    字段
     * @param val       值
     * @return children
     */
    default SQLWherePredicate<T1> lt(boolean condition, Property<T1, ?> column, Object val) {
        getWherePredicate().lt(condition, EasyLambdaUtil.getPropertyName(column), val);
        return this;
    }

    /**
     * column like val%
     * 列匹配前半部分
     *
     * @param column
     * @param val
     * @return
     */
    default SQLWherePredicate<T1> likeMatchLeft(Property<T1, ?> column, Object val) {
        getWherePredicate().likeMatchLeft(EasyLambdaUtil.getPropertyName(column), val);
        return this;
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
    default SQLWherePredicate<T1> likeMatchLeft(boolean condition, Property<T1, ?> column, Object val) {
        getWherePredicate().likeMatchLeft(condition, EasyLambdaUtil.getPropertyName(column), val);
        return this;
    }

    /**
     * column like %val
     * 列匹配后半部分
     *
     * @param column
     * @param val
     * @return
     */
    default SQLWherePredicate<T1> likeMatchRight(Property<T1, ?> column, Object val) {
        getWherePredicate().likeMatchRight(EasyLambdaUtil.getPropertyName(column), val);
        return this;
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
    default SQLWherePredicate<T1> likeMatchRight(boolean condition, Property<T1, ?> column, Object val) {
        getWherePredicate().likeMatchRight(condition, EasyLambdaUtil.getPropertyName(column), val);
        return this;
    }

    /**
     * column like %val%
     * 列全匹配
     */
    default SQLWherePredicate<T1> like(Property<T1, ?> column, Object val) {
        getWherePredicate().like(EasyLambdaUtil.getPropertyName(column), val);
        return this;
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
    default SQLWherePredicate<T1> like(boolean condition, Property<T1, ?> column, Object val) {
        getWherePredicate().like(condition, EasyLambdaUtil.getPropertyName(column), val);
        return this;
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
    default SQLWherePredicate<T1> like(boolean condition, Property<T1, ?> column, Object val, SQLLikeEnum sqlLike) {
        getWherePredicate().like(condition, EasyLambdaUtil.getPropertyName(column), val, sqlLike);
        return this;
    }

    /**
     * column not like val%
     *
     * @param column
     * @param val
     * @return
     */
    default SQLWherePredicate<T1> notLikeMatchLeft(Property<T1, ?> column, Object val) {
        getWherePredicate().notLikeMatchLeft(EasyLambdaUtil.getPropertyName(column), val);
        return this;
    }

    /**
     * column not like val%
     *
     * @param condition
     * @param column
     * @param val
     * @return
     */
    default SQLWherePredicate<T1> notLikeMatchLeft(boolean condition, Property<T1, ?> column, Object val) {
        getWherePredicate().notLikeMatchLeft(condition, EasyLambdaUtil.getPropertyName(column), val);
        return this;
    }

    /**
     * column not like %val
     *
     * @param column
     * @param val
     * @return
     */
    default SQLWherePredicate<T1> notLikeMatchRight(Property<T1, ?> column, Object val) {
        getWherePredicate().notLikeMatchRight(EasyLambdaUtil.getPropertyName(column), val);
        return this;
    }

    /**
     * column not like %val
     *
     * @param condition
     * @param column
     * @param val
     * @return
     */
    default SQLWherePredicate<T1> notLikeMatchRight(boolean condition, Property<T1, ?> column, Object val) {
        getWherePredicate().notLikeMatchRight(condition, EasyLambdaUtil.getPropertyName(column), val);
        return this;
    }

    /**
     * column not like %val%
     */
    default SQLWherePredicate<T1> notLike(Property<T1, ?> column, Object val) {
        getWherePredicate().notLike(EasyLambdaUtil.getPropertyName(column), val);
        return this;
    }

    /**
     * column not like %val%
     *
     * @param condition 执行条件
     * @param column    字段
     * @param val       值
     * @return children
     */
    default SQLWherePredicate<T1> notLike(boolean condition, Property<T1, ?> column, Object val) {
        getWherePredicate().notLike(condition, EasyLambdaUtil.getPropertyName(column), val);
        return this;
    }

    default SQLWherePredicate<T1> notLike(boolean condition, Property<T1, ?> column, Object val, SQLLikeEnum sqlLike) {
        getWherePredicate().notLike(condition, EasyLambdaUtil.getPropertyName(column), val, sqlLike);
        return this;
    }

    /**
     * column is null
     */
    default SQLWherePredicate<T1> isNull(Property<T1, ?> column) {
        getWherePredicate().isNull(EasyLambdaUtil.getPropertyName(column));
        return this;
    }

    /**
     * column is null
     *
     * @param condition 执行条件
     * @param column    字段
     * @return children
     */
    default SQLWherePredicate<T1> isNull(boolean condition, Property<T1, ?> column) {
        getWherePredicate().isNull(condition, EasyLambdaUtil.getPropertyName(column));
        return this;
    }

    /**
     * column is not null
     */
    default SQLWherePredicate<T1> isNotNull(Property<T1, ?> column) {
        getWherePredicate().isNotNull(EasyLambdaUtil.getPropertyName(column));
        return this;
    }

    /**
     * column is not null
     *
     * @param condition 执行条件
     * @param column    字段
     * @return children
     */
    default SQLWherePredicate<T1> isNotNull(boolean condition, Property<T1, ?> column) {
        getWherePredicate().isNotNull(condition, EasyLambdaUtil.getPropertyName(column));
        return this;
    }

    /**
     * column in collection
     * 集合为空返回False
     */
    default SQLWherePredicate<T1> in(Property<T1, ?> column, Collection<?> collection) {
        getWherePredicate().in(EasyLambdaUtil.getPropertyName(column), collection);
        return this;
    }

    /**
     * column in collection
     * 集合为空返回False
     */
    default SQLWherePredicate<T1> in(boolean condition, Property<T1, ?> column, Collection<?> collection) {
        getWherePredicate().in(condition, EasyLambdaUtil.getPropertyName(column), collection);
        return this;
    }

    default <TProperty> SQLWherePredicate<T1> in(Property<T1, ?> column, TProperty[] collection) {
        getWherePredicate().in(EasyLambdaUtil.getPropertyName(column), collection);
        return this;
    }
    default <TProperty> SQLWherePredicate<T1> in(boolean condition, Property<T1, ?> column, TProperty[] collection) {
        getWherePredicate().in(condition, EasyLambdaUtil.getPropertyName(column), collection);
        return this;
    }

    default <TProperty> SQLWherePredicate<T1> in(Property<T1, TProperty> column, Queryable<TProperty> subQueryable) {
        getWherePredicate().in(EasyLambdaUtil.getPropertyName(column), subQueryable);
        return this;
    }

    default <TProperty> SQLWherePredicate<T1> in(boolean condition, Property<T1, TProperty> column, Queryable<TProperty> subQueryable) {
        getWherePredicate().in(condition, EasyLambdaUtil.getPropertyName(column), subQueryable);
        return this;
    }

    /**
     * column not in collection
     * 集合为空返回True
     */
    default SQLWherePredicate<T1> notIn(Property<T1, ?> column, Collection<?> collection) {
        getWherePredicate().notIn(EasyLambdaUtil.getPropertyName(column), collection);
        return this;
    }

    /**
     * column not in collection
     * 集合为空返回True
     */
    default SQLWherePredicate<T1> notIn(boolean condition, Property<T1, ?> column, Collection<?> collection) {
        getWherePredicate().notIn(condition, EasyLambdaUtil.getPropertyName(column), collection);
        return this;
    }
    default <TProperty> SQLWherePredicate<T1> notIn(Property<T1, ?> column, TProperty[] collection) {
        getWherePredicate().notIn(EasyLambdaUtil.getPropertyName(column), collection);
        return this;
    }
    default <TProperty> SQLWherePredicate<T1> notIn(boolean condition, Property<T1, ?> column, TProperty[] collection) {
        getWherePredicate().notIn(condition, EasyLambdaUtil.getPropertyName(column), collection);
        return this;
    }

    default <TProperty> SQLWherePredicate<T1> notIn(Property<T1, ?> column, Queryable<TProperty> subQueryable) {
        getWherePredicate().notIn(EasyLambdaUtil.getPropertyName(column), subQueryable);
        return this;
    }

    default <TProperty> SQLWherePredicate<T1> notIn(boolean condition, Property<T1, ?> column, Queryable<TProperty> subQueryable) {
        getWherePredicate().notIn(condition, EasyLambdaUtil.getPropertyName(column), subQueryable);
        return this;
    }

    default <T2> SQLWherePredicate<T1> exists(Queryable<T2> subQueryable) {
        getWherePredicate().exists(subQueryable);
        return this;
    }

    default <T2> SQLWherePredicate<T1> exists(boolean condition, Queryable<T2> subQueryable) {
        getWherePredicate().exists(condition, subQueryable);
        return this;
    }

    default <T2> SQLWherePredicate<T1> notExists(Queryable<T2> subQueryable) {
        getWherePredicate().notExists(subQueryable);
        return this;
    }

    default <T2> SQLWherePredicate<T1> notExists(boolean condition, Queryable<T2> subQueryable) {
        getWherePredicate().notExists(condition, subQueryable);
        return this;
    }

    /**
     * 区间 (left..right] = {x | left < x <= right}
     * 一般用于范围比如时间,小的时间在前大的时间在后
     *
     * @param column
     * @param conditionLeft
     * @param valLeft
     * @param conditionRight
     * @param valRight
     * @return
     */
    default SQLWherePredicate<T1> rangeOpenClosed(Property<T1, ?> column, boolean conditionLeft, Object valLeft, boolean conditionRight, Object valRight) {
        return rangeOpenClosed(true, column, conditionLeft, valLeft, conditionRight, valRight);
    }

    /**
     * 区间 (left..right] = {x | left < x <= right}
     * 一般用于范围比如时间,小的时间在前大的时间在后
     *
     * @param condition
     * @param column
     * @param conditionLeft
     * @param valLeft
     * @param conditionRight
     * @param valRight
     * @return
     */
    default SQLWherePredicate<T1> rangeOpenClosed(boolean condition, Property<T1, ?> column, boolean conditionLeft, Object valLeft, boolean conditionRight, Object valRight) {
        return range(condition, column, conditionLeft, valLeft, conditionRight, valRight, SQLRangeEnum.OPEN_CLOSED);
    }

    /**
     * 区间 (left..right) = {x | left < x < right}
     * 一般用于范围比如时间,小的时间在前大的时间在后
     *
     * @param column   数据库列
     * @param valLeft  区间左侧值
     * @param valRight 区间右侧的值
     * @return
     */
    default SQLWherePredicate<T1> rangeOpen(Property<T1, ?> column, Object valLeft, Object valRight) {
        return rangeOpen(true, column, true, valLeft, true, valRight);
    }

    /**
     * 区间 (left..right) = {x | left < x < right}
     * 一般用于范围比如时间,小的时间在前大的时间在后
     *
     * @param column         数据库列
     * @param conditionLeft  是否添加左侧条件
     * @param valLeft        区间左侧值
     * @param conditionRight 是否添加右侧条件
     * @param valRight       区间右侧的值
     * @return
     */
    default SQLWherePredicate<T1> rangeOpen(Property<T1, ?> column, boolean conditionLeft, Object valLeft, boolean conditionRight, Object valRight) {
        return rangeOpen(true, column, conditionLeft, valLeft, conditionRight, valRight);
    }

    /**
     * 区间 (left..right) = {x | left < x < right}
     * 一般用于范围比如时间,小的时间在前大的时间在后
     *
     * @param condition
     * @param column
     * @param conditionLeft
     * @param valLeft
     * @param conditionRight
     * @param valRight
     * @return
     */
    default SQLWherePredicate<T1> rangeOpen(boolean condition, Property<T1, ?> column, boolean conditionLeft, Object valLeft, boolean conditionRight, Object valRight) {
        return range(condition, column, conditionLeft, valLeft, conditionRight, valRight, SQLRangeEnum.OPEN);
    }

    /**
     * [left..right) = {x | left <= x < right}
     * 一般用于范围比如时间,小的时间在前大的时间在后
     *
     * @param column
     * @param conditionLeft
     * @param valLeft
     * @param conditionRight
     * @param valRight
     * @return
     */
    default SQLWherePredicate<T1> rangeClosedOpen(Property<T1, ?> column, boolean conditionLeft, Object valLeft, boolean conditionRight, Object valRight) {
        return rangeClosedOpen(true, column, conditionLeft, valLeft, conditionRight, valRight);
    }

    /**
     * [left..right) = {x | left <= x < right}
     * 一般用于范围比如时间,小的时间在前大的时间在后
     *
     * @param condition
     * @param column
     * @param conditionLeft
     * @param valLeft
     * @param conditionRight
     * @param valRight
     * @return
     */
    default SQLWherePredicate<T1> rangeClosedOpen(boolean condition, Property<T1, ?> column, boolean conditionLeft, Object valLeft, boolean conditionRight, Object valRight) {
        return range(condition, column, conditionLeft, valLeft, conditionRight, valRight, SQLRangeEnum.CLOSED_OPEN);
    }

    /**
     * [left..right] = {x | left <= x <= right}
     * 一般用于范围比如时间,小的时间在前大的时间在后
     *
     * @param column
     * @param valLeft
     * @param valRight
     * @return
     */
    default SQLWherePredicate<T1> rangeClosed(Property<T1, ?> column, Object valLeft, Object valRight) {
        return rangeClosed(true, column, true, valLeft, true, valRight);
    }

    /**
     * [left..right] = {x | left <= x <= right}
     * 一般用于范围比如时间,小的时间在前大的时间在后
     *
     * @param column
     * @param conditionLeft
     * @param valLeft
     * @param conditionRight
     * @param valRight
     * @return
     */
    default SQLWherePredicate<T1> rangeClosed(Property<T1, ?> column, boolean conditionLeft, Object valLeft, boolean conditionRight, Object valRight) {
        return rangeClosed(true, column, conditionLeft, valLeft, conditionRight, valRight);
    }

    /**
     * [left..right] = {x | left <= x <= right}
     * 一般用于范围比如时间,小的时间在前大的时间在后
     *
     * @param condition
     * @param column
     * @param conditionLeft
     * @param valLeft
     * @param conditionRight
     * @param valRight
     * @return
     */
    default SQLWherePredicate<T1> rangeClosed(boolean condition, Property<T1, ?> column, boolean conditionLeft, Object valLeft, boolean conditionRight, Object valRight) {
        return range(condition, column, conditionLeft, valLeft, conditionRight, valRight, SQLRangeEnum.CLOSED);
    }

    /**
     * 自定义范围
     * 一般用于范围比如时间,小的时间在前大的时间在后
     *
     * @param condition
     * @param column
     * @param conditionLeft
     * @param valLeft
     * @param conditionRight
     * @param valRight
     * @param sqlRange
     * @return
     */
    default SQLWherePredicate<T1> range(boolean condition, Property<T1, ?> column, boolean conditionLeft, Object valLeft, boolean conditionRight, Object valRight, SQLRangeEnum sqlRange) {
        getWherePredicate().range(condition, EasyLambdaUtil.getPropertyName(column), conditionLeft, valLeft, conditionRight, valRight, sqlRange);
        return this;
    }


    default SQLWherePredicate<T1> columnFunc(ColumnPropertyFunction columnPropertyFunction, SQLPredicateCompare sqlPredicateCompare, Object val) {
        return columnFunc(true, columnPropertyFunction, sqlPredicateCompare, val);
    }

    default SQLWherePredicate<T1> columnFunc(boolean condition, ColumnPropertyFunction columnPropertyFunction, SQLPredicateCompare sqlPredicateCompare, Object val) {
        getWherePredicate().columnFunc(condition, columnPropertyFunction, sqlPredicateCompare, val);
        return this;
    }

    default <T2> SQLWherePredicate<T1> eq(SQLWherePredicate<T2> sub, Property<T1, ?> column1, Property<T2, ?> column2) {
        return eq(true, sub, column1, column2);
    }

    default <T2> SQLWherePredicate<T1> eq(boolean condition, SQLWherePredicate<T2> sub, Property<T1, ?> column1, Property<T2, ?> column2) {
        getWherePredicate().eq(condition, sub.getWherePredicate(), EasyLambdaUtil.getPropertyName(column1), EasyLambdaUtil.getPropertyName(column2));
        return this;
    }

    default <T2> SQLWherePredicate<T2> then(SQLWherePredicate<T2> sub) {
        getWherePredicate().then(sub.getWherePredicate());
        return sub;
    }

    default SQLWherePredicate<T1> and() {
        return and(true);
    }

    default SQLWherePredicate<T1> and(boolean condition) {
        getWherePredicate().and(condition);
        return this;
    }

    default SQLWherePredicate<T1> and(SQLExpression1<SQLWherePredicate<T1>> sqlWherePredicateSQLExpression) {
        return and(true, sqlWherePredicateSQLExpression);
    }

    SQLWherePredicate<T1> and(boolean condition, SQLExpression1<SQLWherePredicate<T1>> sqlWherePredicateSQLExpression);

    default SQLWherePredicate<T1> or() {
        return or(true);
    }

    default SQLWherePredicate<T1> or(boolean condition) {
        getWherePredicate().or(condition);
        return this;
    }

    default SQLWherePredicate<T1> or(SQLExpression1<SQLWherePredicate<T1>> sqlWherePredicateSQLExpression) {
        return or(true, sqlWherePredicateSQLExpression);
    }

    SQLWherePredicate<T1> or(boolean condition, SQLExpression1<SQLWherePredicate<T1>> sqlWherePredicateSQLExpression);
}
