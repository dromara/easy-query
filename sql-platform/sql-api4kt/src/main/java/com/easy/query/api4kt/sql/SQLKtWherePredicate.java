package com.easy.query.api4kt.sql;

import com.easy.query.api4kt.select.KtQueryable;
import com.easy.query.api4kt.sql.core.SQLLambdaKtNative;
import com.easy.query.api4kt.util.EasyKtLambdaUtil;
import com.easy.query.core.enums.SQLLikeEnum;
import com.easy.query.core.enums.SQLPredicateCompare;
import com.easy.query.core.enums.SQLRangeEnum;
import com.easy.query.core.expression.func.ColumnPropertyFunction;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.lambda.SQLExpression2;
import com.easy.query.core.expression.parser.core.EntitySQLTableOwner;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.parser.core.base.WherePredicate;
import kotlin.reflect.KProperty1;

import java.util.Collection;

/**
 * @author xuejiaming
 * @FileName: WherePredicate.java
 * @Description: 文件说明
 * @Date: 2023/2/5 09:09
 */
public interface SQLKtWherePredicate<T1> extends EntitySQLTableOwner<T1>, SQLLambdaKtNative<T1, SQLKtWherePredicate<T1>> {
    WherePredicate<T1> getWherePredicate();

    default TableAvailable getTable() {
        return getWherePredicate().getTable();
    }

    /**
     * 大于 column > val
     *
     * @param column
     * @param val
     * @return
     */
    default <TProperty> SQLKtWherePredicate<T1> gt(KProperty1<? super T1, TProperty> column, TProperty val) {
        getWherePredicate().gt(true, EasyKtLambdaUtil.getPropertyName(column), val);
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
    default <TProperty> SQLKtWherePredicate<T1> gt(boolean condition, KProperty1<? super T1, TProperty> column, TProperty val) {
        getWherePredicate().gt(condition, EasyKtLambdaUtil.getPropertyName(column), val);
        return this;
    }

    /**
     * 等于 column >= val
     */
    default <TProperty> SQLKtWherePredicate<T1> ge(KProperty1<? super T1, TProperty> column, TProperty val) {
        getWherePredicate().ge(EasyKtLambdaUtil.getPropertyName(column), val);
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
    default <TProperty> SQLKtWherePredicate<T1> ge(boolean condition, KProperty1<? super T1, TProperty> column, TProperty val) {
        getWherePredicate().ge(condition, EasyKtLambdaUtil.getPropertyName(column), val);
        return this;
    }

    /**
     * 等于 column = val
     */
    default <TProperty> SQLKtWherePredicate<T1> eq(KProperty1<? super T1, TProperty> column, TProperty val) {
        getWherePredicate().eq(EasyKtLambdaUtil.getPropertyName(column), val);
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
    default <TProperty> SQLKtWherePredicate<T1> eq(boolean condition, KProperty1<? super T1, TProperty> column, TProperty val) {
        getWherePredicate().eq(condition, EasyKtLambdaUtil.getPropertyName(column), val);
        return this;
    }

    /**
     * 不等于 column <> val
     */
    default <TProperty> SQLKtWherePredicate<T1> ne(KProperty1<? super T1, TProperty> column, TProperty val) {
        getWherePredicate().ne(EasyKtLambdaUtil.getPropertyName(column), val);
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
    default <TProperty> SQLKtWherePredicate<T1> ne(boolean condition, KProperty1<? super T1, TProperty> column, TProperty val) {
        getWherePredicate().ne(condition, EasyKtLambdaUtil.getPropertyName(column), val);
        return this;
    }

    /**
     * 小于等于 column <= val
     */
    default <TProperty> SQLKtWherePredicate<T1> le(KProperty1<? super T1, TProperty> column, TProperty val) {
        getWherePredicate().le(EasyKtLambdaUtil.getPropertyName(column), val);
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
    default <TProperty> SQLKtWherePredicate<T1> le(boolean condition, KProperty1<? super T1, TProperty> column, TProperty val) {
        getWherePredicate().le(condition, EasyKtLambdaUtil.getPropertyName(column), val);
        return this;
    }

    /**
     * 小于 column < val
     */
    default <TProperty> SQLKtWherePredicate<T1> lt(KProperty1<? super T1, TProperty> column, TProperty val) {
        getWherePredicate().lt(EasyKtLambdaUtil.getPropertyName(column), val);
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
    default <TProperty> SQLKtWherePredicate<T1> lt(boolean condition, KProperty1<? super T1, TProperty> column, TProperty val) {
        getWherePredicate().lt(condition, EasyKtLambdaUtil.getPropertyName(column), val);
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
    default <TProperty> SQLKtWherePredicate<T1> likeMatchLeft(KProperty1<? super T1, TProperty> column, TProperty val) {
        getWherePredicate().likeMatchLeft(EasyKtLambdaUtil.getPropertyName(column), val);
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
    default <TProperty> SQLKtWherePredicate<T1> likeMatchLeft(boolean condition, KProperty1<? super T1, TProperty> column, TProperty val) {
        getWherePredicate().likeMatchLeft(condition, EasyKtLambdaUtil.getPropertyName(column), val);
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
    default <TProperty> SQLKtWherePredicate<T1> likeMatchRight(KProperty1<? super T1, TProperty> column, TProperty val) {
        getWherePredicate().likeMatchRight(EasyKtLambdaUtil.getPropertyName(column), val);
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
    default <TProperty> SQLKtWherePredicate<T1> likeMatchRight(boolean condition, KProperty1<? super T1, TProperty> column, TProperty val) {
        getWherePredicate().likeMatchRight(condition, EasyKtLambdaUtil.getPropertyName(column), val);
        return this;
    }

    /**
     * column like %val%
     * 列全匹配
     */
    default <TProperty> SQLKtWherePredicate<T1> like(KProperty1<? super T1, TProperty> column, TProperty val) {
        getWherePredicate().like(EasyKtLambdaUtil.getPropertyName(column), val);
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
    default <TProperty> SQLKtWherePredicate<T1> like(boolean condition, KProperty1<? super T1, TProperty> column, TProperty val) {
        getWherePredicate().like(condition, EasyKtLambdaUtil.getPropertyName(column), val);
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
    default <TProperty> SQLKtWherePredicate<T1> like(boolean condition, KProperty1<? super T1, TProperty> column, TProperty val, SQLLikeEnum sqlLike) {
        getWherePredicate().like(condition, EasyKtLambdaUtil.getPropertyName(column), val, sqlLike);
        return this;
    }

    /**
     * column not like val%
     *
     * @param column
     * @param val
     * @return
     */
    default <TProperty> SQLKtWherePredicate<T1> notLikeMatchLeft(KProperty1<? super T1, TProperty> column, TProperty val) {
        getWherePredicate().notLikeMatchLeft(EasyKtLambdaUtil.getPropertyName(column), val);
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
    default <TProperty> SQLKtWherePredicate<T1> notLikeMatchLeft(boolean condition, KProperty1<? super T1, TProperty> column, TProperty val) {
        getWherePredicate().notLikeMatchLeft(condition, EasyKtLambdaUtil.getPropertyName(column), val);
        return this;
    }

    /**
     * column not like %val
     *
     * @param column
     * @param val
     * @return
     */
    default <TProperty> SQLKtWherePredicate<T1> notLikeMatchRight(KProperty1<? super T1, TProperty> column, TProperty val) {
        getWherePredicate().notLikeMatchRight(EasyKtLambdaUtil.getPropertyName(column), val);
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
    default <TProperty> SQLKtWherePredicate<T1> notLikeMatchRight(boolean condition, KProperty1<? super T1, TProperty> column, TProperty val) {
        getWherePredicate().notLikeMatchRight(condition, EasyKtLambdaUtil.getPropertyName(column), val);
        return this;
    }

    /**
     * column not like %val%
     */
    default <TProperty> SQLKtWherePredicate<T1> notLike(KProperty1<? super T1, TProperty> column, TProperty val) {
        getWherePredicate().notLike(EasyKtLambdaUtil.getPropertyName(column), val);
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
    default <TProperty> SQLKtWherePredicate<T1> notLike(boolean condition, KProperty1<? super T1, TProperty> column, TProperty val) {
        getWherePredicate().notLike(condition, EasyKtLambdaUtil.getPropertyName(column), val);
        return this;
    }

    default <TProperty> SQLKtWherePredicate<T1> notLike(boolean condition, KProperty1<? super T1, TProperty> column, TProperty val, SQLLikeEnum sqlLike) {
        getWherePredicate().notLike(condition, EasyKtLambdaUtil.getPropertyName(column), val, sqlLike);
        return this;
    }

    /**
     * column is null
     */
    default <TProperty> SQLKtWherePredicate<T1> isNull(KProperty1<? super T1, TProperty> column) {
        getWherePredicate().isNull(EasyKtLambdaUtil.getPropertyName(column));
        return this;
    }

    /**
     * column is null
     *
     * @param condition 执行条件
     * @param column    字段
     * @return children
     */
    default <TProperty> SQLKtWherePredicate<T1> isNull(boolean condition, KProperty1<? super T1, TProperty> column) {
        getWherePredicate().isNull(condition, EasyKtLambdaUtil.getPropertyName(column));
        return this;
    }

    /**
     * column is not null
     */
    default <TProperty> SQLKtWherePredicate<T1> isNotNull(KProperty1<? super T1, TProperty> column) {
        getWherePredicate().isNotNull(EasyKtLambdaUtil.getPropertyName(column));
        return this;
    }

    /**
     * column is not null
     *
     * @param condition 执行条件
     * @param column    字段
     * @return children
     */
    default <TProperty> SQLKtWherePredicate<T1> isNotNull(boolean condition, KProperty1<? super T1, TProperty> column) {
        getWherePredicate().isNotNull(condition, EasyKtLambdaUtil.getPropertyName(column));
        return this;
    }

    /**
     * column in collection
     * 集合为空返回False
     */
    default <TProperty> SQLKtWherePredicate<T1> in(KProperty1<? super T1, TProperty> column, Collection<TProperty> collection) {
        getWherePredicate().in(EasyKtLambdaUtil.getPropertyName(column), collection);
        return this;
    }

    /**
     * column in collection
     * 集合为空返回False
     */
    default <TProperty> SQLKtWherePredicate<T1> in(boolean condition, KProperty1<? super T1, TProperty> column, Collection<TProperty> collection) {
        getWherePredicate().in(condition, EasyKtLambdaUtil.getPropertyName(column), collection);
        return this;
    }

    default <TProperty> SQLKtWherePredicate<T1> in(KProperty1<? super T1, TProperty> column, TProperty[] collection) {
        getWherePredicate().in(EasyKtLambdaUtil.getPropertyName(column), collection);
        return this;
    }

    default <TProperty> SQLKtWherePredicate<T1> in(boolean condition, KProperty1<? super T1, TProperty> column, TProperty[] collection) {
        getWherePredicate().in(condition, EasyKtLambdaUtil.getPropertyName(column), collection);
        return this;
    }

    default <TProperty> SQLKtWherePredicate<T1> in(KProperty1<? super T1, TProperty> column, KtQueryable<TProperty> subQueryable) {
        getWherePredicate().in(EasyKtLambdaUtil.getPropertyName(column), subQueryable);
        return this;
    }

    default <TProperty> SQLKtWherePredicate<T1> in(boolean condition, KProperty1<? super T1, TProperty> column, KtQueryable<TProperty> subQueryable) {
        getWherePredicate().in(condition, EasyKtLambdaUtil.getPropertyName(column), subQueryable);
        return this;
    }

    /**
     * column not in collection
     * 集合为空返回True
     */
    default <TProperty> SQLKtWherePredicate<T1> notIn(KProperty1<? super T1, TProperty> column, Collection<TProperty> collection) {
        getWherePredicate().notIn(EasyKtLambdaUtil.getPropertyName(column), collection);
        return this;
    }

    /**
     * column not in collection
     * 集合为空返回True
     */
    default <TProperty> SQLKtWherePredicate<T1> notIn(boolean condition, KProperty1<? super T1, TProperty> column, Collection<TProperty> collection) {
        getWherePredicate().notIn(condition, EasyKtLambdaUtil.getPropertyName(column), collection);
        return this;
    }

    default <TProperty> SQLKtWherePredicate<T1> notIn(KProperty1<? super T1, TProperty> column, TProperty[] collection) {
        getWherePredicate().notIn(EasyKtLambdaUtil.getPropertyName(column), collection);
        return this;
    }

    default <TProperty> SQLKtWherePredicate<T1> notIn(boolean condition, KProperty1<? super T1, TProperty> column, TProperty[] collection) {
        getWherePredicate().notIn(condition, EasyKtLambdaUtil.getPropertyName(column), collection);
        return this;
    }

    default <TProperty> SQLKtWherePredicate<T1> notIn(KProperty1<? super T1, TProperty> column, KtQueryable<TProperty> subQueryable) {
        getWherePredicate().notIn(EasyKtLambdaUtil.getPropertyName(column), subQueryable);
        return this;
    }

    default <TProperty> SQLKtWherePredicate<T1> notIn(boolean condition, KProperty1<? super T1, TProperty> column, KtQueryable<TProperty> subQueryable) {
        getWherePredicate().notIn(condition, EasyKtLambdaUtil.getPropertyName(column), subQueryable);
        return this;
    }

    default <T2> SQLKtWherePredicate<T1> exists(KtQueryable<T2> subQueryable) {
        getWherePredicate().exists(subQueryable);
        return this;
    }

    default <T2> SQLKtWherePredicate<T1> exists(boolean condition, KtQueryable<T2> subQueryable) {
        getWherePredicate().exists(condition, subQueryable);
        return this;
    }

    default <T2> SQLKtWherePredicate<T1> notExists(KtQueryable<T2> subQueryable) {
        getWherePredicate().notExists(subQueryable);
        return this;
    }

    default <T2> SQLKtWherePredicate<T1> notExists(boolean condition, KtQueryable<T2> subQueryable) {
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
    default <TProperty> SQLKtWherePredicate<T1> rangeOpenClosed(KProperty1<? super T1, TProperty> column, boolean conditionLeft, TProperty valLeft, boolean conditionRight, TProperty valRight) {
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
    default <TProperty> SQLKtWherePredicate<T1> rangeOpenClosed(boolean condition, KProperty1<? super T1, TProperty> column, boolean conditionLeft, TProperty valLeft, boolean conditionRight, TProperty valRight) {
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
    default <TProperty> SQLKtWherePredicate<T1> rangeOpen(KProperty1<? super T1, TProperty> column, TProperty valLeft, TProperty valRight) {
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
    default <TProperty> SQLKtWherePredicate<T1> rangeOpen(KProperty1<? super T1, TProperty> column, boolean conditionLeft, TProperty valLeft, boolean conditionRight, TProperty valRight) {
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
    default <TProperty> SQLKtWherePredicate<T1> rangeOpen(boolean condition, KProperty1<? super T1, TProperty> column, boolean conditionLeft, TProperty valLeft, boolean conditionRight, TProperty valRight) {
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
    default <TProperty> SQLKtWherePredicate<T1> rangeClosedOpen(KProperty1<? super T1, TProperty> column, boolean conditionLeft, TProperty valLeft, boolean conditionRight, TProperty valRight) {
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
    default <TProperty> SQLKtWherePredicate<T1> rangeClosedOpen(boolean condition, KProperty1<? super T1, TProperty> column, boolean conditionLeft, TProperty valLeft, boolean conditionRight, TProperty valRight) {
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
    default <TProperty> SQLKtWherePredicate<T1> rangeClosed(KProperty1<? super T1, TProperty> column, TProperty valLeft, TProperty valRight) {
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
    default <TProperty> SQLKtWherePredicate<T1> rangeClosed(KProperty1<? super T1, TProperty> column, boolean conditionLeft, TProperty valLeft, boolean conditionRight, TProperty valRight) {
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
    default <TProperty> SQLKtWherePredicate<T1> rangeClosed(boolean condition, KProperty1<? super T1, TProperty> column, boolean conditionLeft, TProperty valLeft, boolean conditionRight, TProperty valRight) {
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
    default <TProperty> SQLKtWherePredicate<T1> range(boolean condition, KProperty1<? super T1, TProperty> column, boolean conditionLeft, TProperty valLeft, boolean conditionRight, TProperty valRight, SQLRangeEnum sqlRange) {
        getWherePredicate().range(condition, EasyKtLambdaUtil.getPropertyName(column), conditionLeft, valLeft, conditionRight, valRight, sqlRange);
        return this;
    }


    default <TProperty> SQLKtWherePredicate<T1> columnFunc(ColumnPropertyFunction columnPropertyFunction, SQLPredicateCompare sqlPredicateCompare, TProperty val) {
        return columnFunc(true, columnPropertyFunction, sqlPredicateCompare, val);
    }

    default <TProperty> SQLKtWherePredicate<T1> columnFunc(boolean condition, ColumnPropertyFunction columnPropertyFunction, SQLPredicateCompare sqlPredicateCompare, TProperty val) {
        getWherePredicate().columnFunc(condition, columnPropertyFunction, sqlPredicateCompare, val);
        return this;
    }

    default <T2, TProperty> SQLKtWherePredicate<T1> gt(SQLKtWherePredicate<T2> sub, KProperty1<? super T1, TProperty> column1, KProperty1<? super T2, TProperty> column2) {
        return gt(true, sub, column1, column2);
    }

    default <T2, TProperty> SQLKtWherePredicate<T1> gt(boolean condition, SQLKtWherePredicate<T2> sub, KProperty1<? super T1, TProperty> column1, KProperty1<? super T2, TProperty> column2) {
        getWherePredicate().gt(condition, sub.getWherePredicate(), EasyKtLambdaUtil.getPropertyName(column1), EasyKtLambdaUtil.getPropertyName(column2));
        return this;
    }

    default <T2,TProperty> SQLKtWherePredicate<T1> ge(SQLKtWherePredicate<T2> sub, KProperty1<? super T1, TProperty> column1, KProperty1<? super T2, TProperty> column2) {
        return ge(true, sub, column1, column2);
    }

    default <T2,TProperty> SQLKtWherePredicate<T1> ge(boolean condition, SQLKtWherePredicate<T2> sub, KProperty1<? super T1, TProperty> column1, KProperty1<? super T2, TProperty> column2) {
        getWherePredicate().ge(condition, sub.getWherePredicate(), EasyKtLambdaUtil.getPropertyName(column1), EasyKtLambdaUtil.getPropertyName(column2));
        return this;
    }

    default <T2,TProperty> SQLKtWherePredicate<T1> eq(SQLKtWherePredicate<T2> sub, KProperty1<? super T1, TProperty> column1, KProperty1<? super T2, TProperty> column2) {
        return eq(true, sub, column1, column2);
    }

    default <T2,TProperty> SQLKtWherePredicate<T1> eq(boolean condition, SQLKtWherePredicate<T2> sub, KProperty1<? super T1, TProperty> column1, KProperty1<? super T2, TProperty> column2) {
        getWherePredicate().eq(condition, sub.getWherePredicate(), EasyKtLambdaUtil.getPropertyName(column1), EasyKtLambdaUtil.getPropertyName(column2));
        return this;
    }

    default <T2,TProperty> SQLKtWherePredicate<T1> ne(SQLKtWherePredicate<T2> sub, KProperty1<? super T1, TProperty> column1, KProperty1<? super T2, TProperty> column2) {
        return ne(true, sub, column1, column2);
    }

    default <T2,TProperty> SQLKtWherePredicate<T1> ne(boolean condition, SQLKtWherePredicate<T2> sub, KProperty1<? super T1, TProperty> column1, KProperty1<? super T2, TProperty> column2) {
        getWherePredicate().ne(condition, sub.getWherePredicate(), EasyKtLambdaUtil.getPropertyName(column1), EasyKtLambdaUtil.getPropertyName(column2));
        return this;
    }

    default <T2,TProperty> SQLKtWherePredicate<T1> le(SQLKtWherePredicate<T2> sub, KProperty1<? super T1, TProperty> column1, KProperty1<? super T2, TProperty> column2) {
        return le(true, sub, column1, column2);
    }

    default <T2,TProperty> SQLKtWherePredicate<T1> le(boolean condition, SQLKtWherePredicate<T2> sub, KProperty1<? super T1, TProperty> column1, KProperty1<? super T2, TProperty> column2) {
        getWherePredicate().le(condition, sub.getWherePredicate(), EasyKtLambdaUtil.getPropertyName(column1), EasyKtLambdaUtil.getPropertyName(column2));
        return this;
    }

    default <T2,TProperty> SQLKtWherePredicate<T1> lt(SQLKtWherePredicate<T2> sub, KProperty1<? super T1, TProperty> column1, KProperty1<? super T2, TProperty> column2) {
        return lt(true, sub, column1, column2);
    }

    default <T2,TProperty> SQLKtWherePredicate<T1> lt(boolean condition, SQLKtWherePredicate<T2> sub, KProperty1<? super T1, TProperty> column1, KProperty1<? super T2, TProperty> column2) {
        getWherePredicate().lt(condition, sub.getWherePredicate(), EasyKtLambdaUtil.getPropertyName(column1), EasyKtLambdaUtil.getPropertyName(column2));
        return this;
    }

    default <T2> SQLKtWherePredicate<T2> then(SQLKtWherePredicate<T2> sub) {
        getWherePredicate().then(sub.getWherePredicate());
        return sub;
    }

    default SQLKtWherePredicate<T1> and() {
        return and(true);
    }

    default SQLKtWherePredicate<T1> and(boolean condition) {
        getWherePredicate().and(condition);
        return this;
    }

    default SQLKtWherePredicate<T1> and(SQLExpression1<SQLKtWherePredicate<T1>> sqlWherePredicateSQLExpression) {
        return and(true, sqlWherePredicateSQLExpression);
    }

    SQLKtWherePredicate<T1> and(boolean condition, SQLExpression1<SQLKtWherePredicate<T1>> sqlWherePredicateSQLExpression);

    default <T2> SQLKtWherePredicate<T1> and(SQLKtWherePredicate<T2> t2SQLKtWherePredicate,SQLExpression2<SQLKtWherePredicate<T1>,SQLKtWherePredicate<T2>> sqlWherePredicateSQLExpression) {
        return and(true,t2SQLKtWherePredicate, sqlWherePredicateSQLExpression);
    }

    <T2> SQLKtWherePredicate<T1> and(boolean condition,SQLKtWherePredicate<T2> t2SQLKtWherePredicate, SQLExpression2<SQLKtWherePredicate<T1>,SQLKtWherePredicate<T2>> sqlWherePredicateSQLExpression);

    default SQLKtWherePredicate<T1> or() {
        return or(true);
    }

    default SQLKtWherePredicate<T1> or(boolean condition) {
        getWherePredicate().or(condition);
        return this;
    }

    default SQLKtWherePredicate<T1> or(SQLExpression1<SQLKtWherePredicate<T1>> sqlWherePredicateSQLExpression) {
        return or(true, sqlWherePredicateSQLExpression);
    }

    SQLKtWherePredicate<T1> or(boolean condition, SQLExpression1<SQLKtWherePredicate<T1>> sqlWherePredicateSQLExpression);


    default <T2> SQLKtWherePredicate<T1> or(SQLKtWherePredicate<T2> t2SQLKtWherePredicate,SQLExpression2<SQLKtWherePredicate<T1>,SQLKtWherePredicate<T2>> sqlWherePredicateSQLExpression) {
        return or(true,t2SQLKtWherePredicate, sqlWherePredicateSQLExpression);
    }

    <T2> SQLKtWherePredicate<T1> or(boolean condition,SQLKtWherePredicate<T2> t2SQLKtWherePredicate, SQLExpression2<SQLKtWherePredicate<T1>,SQLKtWherePredicate<T2>> sqlWherePredicateSQLExpression);
}
