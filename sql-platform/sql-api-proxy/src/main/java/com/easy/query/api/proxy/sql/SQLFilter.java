package com.easy.query.api.proxy.sql;

import com.easy.query.api.proxy.core.ProxyQuery;
import com.easy.query.api.proxy.core.base.SQLColumn;
import com.easy.query.api.proxy.select.ProxyQueryable;
import com.easy.query.core.enums.SQLLikeEnum;
import com.easy.query.core.enums.SQLPredicateCompare;
import com.easy.query.core.enums.SQLRangeEnum;
import com.easy.query.core.expression.builder.Filter;
import com.easy.query.core.expression.func.ColumnPropertyFunction;
import com.easy.query.core.expression.lambda.Property;
import com.easy.query.core.expression.lambda.SQLExpression1;

import java.util.Collection;

/**
 * @author xuejiaming
 * @FileName: WherePredicate.java
 * @Description: 文件说明
 * @Date: 2023/2/5 09:09
 */
public interface SQLFilter {
    Filter getFilter();

    /**
     * 大于 column > val
     */
    default SQLFilter gt(SQLColumn<?> column, Object val) {
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
    default SQLFilter gt(boolean condition, SQLColumn<?> column, Object val) {
        if (condition) {
            getFilter().gt(column.getTable(), column.value(), val);
        }
        return this;
    }

    /**
     * 等于 column >= val
     */
    default SQLFilter ge(SQLColumn<?> column, Object val) {
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
    default SQLFilter ge(boolean condition, SQLColumn<?> column, Object val) {
        if (condition) {
            getFilter().ge(column.getTable(), column.value(), val);
        }
        return this;
    }

    /**
     * 等于 column = val
     */
    default SQLFilter eq(SQLColumn<?> column, Object val) {
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
    default SQLFilter eq(boolean condition, SQLColumn<?> column, Object val) {
        if (condition) {
            getFilter().eq(column.getTable(), column.value(), val);
        }
        return this;
    }

    /**
     * 不等于 column <> val
     */
    default SQLFilter ne(SQLColumn<?> column, Object val) {
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
    default SQLFilter ne(boolean condition, SQLColumn<?> column, Object val) {
        if (condition) {
            getFilter().ne(column.getTable(), column.value(), val);
        }
        return this;
    }

    /**
     * 小于等于 column <= val
     */
    default SQLFilter le(SQLColumn<?> column, Object val) {
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
    default SQLFilter le(boolean condition, SQLColumn<?> column, Object val) {
        if (condition) {
            getFilter().le(column.getTable(), column.value(), val);
        }
        return this;
    }

    /**
     * 小于 column < val
     */
    default SQLFilter lt(SQLColumn<?> column, Object val) {
        return lt(column, val);
    }

    /**
     * 小于 column < val
     *
     * @param condition 执行条件
     * @param column    字段
     * @param val       值
     * @return children
     */
    default SQLFilter lt(boolean condition, SQLColumn<?> column, Object val) {
        if (condition) {
            getFilter().lt(column.getTable(), column.value(), val);
        }
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
    default SQLFilter likeMatchLeft(SQLColumn<?> column, Object val) {
        return likeMatchLeft(true, column, val);
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
    default SQLFilter likeMatchLeft(boolean condition, SQLColumn<?> column, Object val) {
        return like(condition, column, val, SQLLikeEnum.LIKE_PERCENT_RIGHT);
    }

    /**
     * column like %val
     * 列匹配后半部分
     *
     * @param column
     * @param val
     * @return
     */
    default SQLFilter likeMatchRight(SQLColumn<?> column, Object val) {
        return likeMatchRight(true, column, val);
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
    default SQLFilter likeMatchRight(boolean condition, SQLColumn<?> column, Object val) {
        return like(condition, column, val, SQLLikeEnum.LIKE_PERCENT_LEFT);
    }

    /**
     * column like %val%
     * 列全匹配
     */
    default SQLFilter like(SQLColumn<?> column, Object val) {
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
    default SQLFilter like(boolean condition, SQLColumn<?> column, Object val) {
        return like(condition, column, val, SQLLikeEnum.LIKE_PERCENT_ALL);
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
    default SQLFilter like(boolean condition, SQLColumn<?> column, Object val, SQLLikeEnum sqlLike) {
        if (condition) {
            getFilter().like(column.getTable(), column.value(), val, sqlLike);
        }
        return this;
    }

    /**
     * column not like val%
     *
     * @param column
     * @param val
     * @return
     */
    default SQLFilter notLikeMatchLeft(SQLColumn<?> column, Object val) {
        return notLikeMatchLeft(true, column, val);
    }

    /**
     * column not like val%
     *
     * @param condition
     * @param column
     * @param val
     * @return
     */
    default SQLFilter notLikeMatchLeft(boolean condition, SQLColumn<?> column, Object val) {
        return notLike(condition, column, val, SQLLikeEnum.LIKE_PERCENT_RIGHT);
    }

    /**
     * column not like %val
     *
     * @param column
     * @param val
     * @return
     */
    default SQLFilter notLikeMatchRight(SQLColumn<?> column, Object val) {
        return notLikeMatchRight(true, column, val);
    }

    /**
     * column not like %val
     *
     * @param condition
     * @param column
     * @param val
     * @return
     */
    default SQLFilter notLikeMatchRight(boolean condition, SQLColumn<?> column, Object val) {
        return notLike(condition, column, val, SQLLikeEnum.LIKE_PERCENT_LEFT);
    }

    /**
     * column not like %val%
     */
    default SQLFilter notLike(SQLColumn<?> column, Object val) {
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
    default SQLFilter notLike(boolean condition, SQLColumn<?> column, Object val) {
        return notLike(condition, column, val, SQLLikeEnum.LIKE_PERCENT_ALL);
    }

    default SQLFilter notLike(boolean condition, SQLColumn<?> column, Object val, SQLLikeEnum sqlLike) {
        if (condition) {
            getFilter().notLike(column.getTable(), column.value(), val, sqlLike);
        }
        return this;
    }

    /**
     * column is null
     */
    default SQLFilter isNull(SQLColumn<?> column) {
        return isNull(true, column);
    }

    /**
     * column is null
     *
     * @param condition 执行条件
     * @param column    字段
     * @return children
     */
    default SQLFilter isNull(boolean condition, SQLColumn<?> column) {
        if (condition) {
            getFilter().isNull(column.getTable(), column.value());
        }
        return this;
    }

    /**
     * column is not null
     */
    default SQLFilter isNotNull(SQLColumn<?> column) {
        return isNotNull(true,column);
    }

    /**
     * column is not null
     *
     * @param condition 执行条件
     * @param column    字段
     * @return children
     */
    default SQLFilter isNotNull(boolean condition, SQLColumn<?> column) {
        if(condition){
            getFilter().isNotNull(column.getTable(),column.value());
        }
        return this;
    }

    /**
     * column in collection
     * 集合为空返回False
     */
    default SQLFilter in(SQLColumn<?> column, Collection<?> collection) {
        return in(true,column,collection);
    }

    /**
     * column in collection
     * 集合为空返回False
     */
    default SQLFilter in(boolean condition, SQLColumn<?> column, Collection<?> collection) {
        if(condition){
            getFilter().in(column.getTable(), column.value(),collection);
        }
        return this;
    }

    default <TProperty> SQLFilter in(SQLColumn<?> column, TProperty[] collection) {
        return in(true,column,collection);
    }

    default <TProperty> SQLFilter in(boolean condition, SQLColumn<?> column, TProperty[] collection) {
        if(condition){
            getFilter().in(column.getTable(),column.value(),collection);
        }
        return this;
    }

    default <TProxy extends ProxyQuery<TProxy,TProperty>,TProperty> SQLFilter in(SQLColumn<TProperty> column, ProxyQueryable<TProxy,TProperty> subQueryable) {
        return in(true,column,subQueryable);
    }

    default  <TProxy extends ProxyQuery<TProxy,TProperty>,TProperty> SQLFilter in(boolean condition, SQLColumn<TProperty> column, ProxyQueryable<TProxy,TProperty> subQueryable) {
        if(condition){
            getFilter().in(column.getTable(),column.value(),subQueryable);
        }
        return this;
    }

    /**
     * column not in collection
     * 集合为空返回True
     */
    default SQLFilter notIn(SQLColumn<?> column, Collection<?> collection) {
        return notIn(true,column,collection);
    }

    /**
     * column not in collection
     * 集合为空返回True
     */
    default SQLFilter notIn(boolean condition, SQLColumn<?> column, Collection<?> collection) {
        if(condition){
            getFilter().notIn(column.getTable(), column.value(),collection);
        }
        return this;
    }

    default <TProperty> SQLFilter notIn(SQLColumn<?> column, TProperty[] collection) {
        return notIn(true,column,collection);
    }

    default <TProperty> SQLFilter notIn(boolean condition, SQLColumn<?> column, TProperty[] collection) {
        getWherePredicate().notIn(condition, EasyLambdaUtil.getPropertyName(column), collection);
        return this;
    }

    default <TProperty> SQLFilter notIn(SQLColumn<?> column, Queryable<TProperty> subQueryable) {
        getWherePredicate().notIn(EasyLambdaUtil.getPropertyName(column), subQueryable);
        return this;
    }

    default <TProperty> SQLFilter notIn(boolean condition, SQLColumn<?> column, Queryable<TProperty> subQueryable) {
        getWherePredicate().notIn(condition, EasyLambdaUtil.getPropertyName(column), subQueryable);
        return this;
    }

    default <T2> SQLFilter exists(Queryable<T2> subQueryable) {
        getWherePredicate().exists(subQueryable);
        return this;
    }

    default <T2> SQLFilter exists(boolean condition, Queryable<T2> subQueryable) {
        getWherePredicate().exists(condition, subQueryable);
        return this;
    }

    default <T2> SQLFilter notExists(Queryable<T2> subQueryable) {
        getWherePredicate().notExists(subQueryable);
        return this;
    }

    default <T2> SQLFilter notExists(boolean condition, Queryable<T2> subQueryable) {
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
    default SQLFilter rangeOpenClosed(SQLColumn<?> column, boolean conditionLeft, Object valLeft, boolean conditionRight, Object valRight) {
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
    default SQLFilter rangeOpenClosed(boolean condition, SQLColumn<?> column, boolean conditionLeft, Object valLeft, boolean conditionRight, Object valRight) {
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
    default SQLFilter rangeOpen(SQLColumn<?> column, Object valLeft, Object valRight) {
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
    default SQLFilter rangeOpen(SQLColumn<?> column, boolean conditionLeft, Object valLeft, boolean conditionRight, Object valRight) {
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
    default SQLFilter rangeOpen(boolean condition, SQLColumn<?> column, boolean conditionLeft, Object valLeft, boolean conditionRight, Object valRight) {
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
    default SQLFilter rangeClosedOpen(SQLColumn<?> column, boolean conditionLeft, Object valLeft, boolean conditionRight, Object valRight) {
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
    default SQLFilter rangeClosedOpen(boolean condition, SQLColumn<?> column, boolean conditionLeft, Object valLeft, boolean conditionRight, Object valRight) {
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
    default SQLFilter rangeClosed(SQLColumn<?> column, Object valLeft, Object valRight) {
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
    default SQLFilter rangeClosed(SQLColumn<?> column, boolean conditionLeft, Object valLeft, boolean conditionRight, Object valRight) {
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
    default SQLFilter rangeClosed(boolean condition, SQLColumn<?> column, boolean conditionLeft, Object valLeft, boolean conditionRight, Object valRight) {
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
    default SQLFilter range(boolean condition, SQLColumn<?> column, boolean conditionLeft, Object valLeft, boolean conditionRight, Object valRight, SQLRangeEnum sqlRange) {
        getWherePredicate().range(condition, EasyLambdaUtil.getPropertyName(column), conditionLeft, valLeft, conditionRight, valRight, sqlRange);
        return this;
    }


    default SQLFilter columnFunc(ColumnPropertyFunction columnPropertyFunction, SQLPredicateCompare sqlPredicateCompare, Object val) {
        return columnFunc(true, columnPropertyFunction, sqlPredicateCompare, val);
    }

    default SQLFilter columnFunc(boolean condition, ColumnPropertyFunction columnPropertyFunction, SQLPredicateCompare sqlPredicateCompare, Object val) {
        getWherePredicate().columnFunc(condition, columnPropertyFunction, sqlPredicateCompare, val);
        return this;
    }

    default <T2> SQLFilter eq(SQLWherePredicate<T2> sub, SQLColumn<?> column1, Property<T2, ?> column2) {
        return eq(true, sub, column1, column2);
    }

    default <T2> SQLFilter eq(boolean condition, SQLWherePredicate<T2> sub, SQLColumn<?> column1, Property<T2, ?> column2) {
        getWherePredicate().eq(condition, sub.getWherePredicate(), EasyLambdaUtil.getPropertyName(column1), EasyLambdaUtil.getPropertyName(column2));
        return this;
    }

    default <T2> SQLWherePredicate<T2> then(SQLWherePredicate<T2> sub) {
        getWherePredicate().then(sub.getWherePredicate());
        return sub;
    }

    default SQLFilter and() {
        return and(true);
    }

    default SQLFilter and(boolean condition) {
        getWherePredicate().and(condition);
        return this;
    }

    default SQLFilter and(SQLExpression1<SQLFilter> sqlWherePredicateSQLExpression) {
        return and(true, sqlWherePredicateSQLExpression);
    }

    SQLFilter and(boolean condition, SQLExpression1<SQLFilter> sqlWherePredicateSQLExpression);

    default SQLFilter or() {
        return or(true);
    }

    default SQLFilter or(boolean condition) {
        getWherePredicate().or(condition);
        return this;
    }

    default SQLFilter or(SQLExpression1<SQLFilter> sqlWherePredicateSQLExpression) {
        return or(true, sqlWherePredicateSQLExpression);
    }

    SQLFilter or(boolean condition, SQLExpression1<SQLFilter> sqlWherePredicateSQLExpression);
}
