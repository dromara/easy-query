package com.easy.query.api.proxy.sql;

import com.easy.query.api.proxy.core.ProxyQuery;
import com.easy.query.api.proxy.core.base.SQLColumn;
import com.easy.query.api.proxy.select.ProxyQueryable;
import com.easy.query.api.proxy.sql.impl.ProxyFilterImpl;
import com.easy.query.core.enums.SQLLikeEnum;
import com.easy.query.core.enums.SQLPredicateCompare;
import com.easy.query.core.enums.SQLRangeEnum;
import com.easy.query.core.expression.builder.Filter;
import com.easy.query.core.expression.lambda.SQLExpression1;

import java.util.Collection;

/**
 * @author xuejiaming
 * @FileName: WherePredicate.java
 * @Description: 文件说明
 * @Date: 2023/2/5 09:09
 */
public interface ProxyFilter {
    Filter getFilter();

    /**
     * 大于 column > val
     */
    default ProxyFilter gt(SQLColumn<?> column, Object val) {
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
    default ProxyFilter gt(boolean condition, SQLColumn<?> column, Object val) {
        if (condition) {
            getFilter().gt(column.getTable(), column.value(), val);
        }
        return this;
    }

    /**
     * 等于 column >= val
     */
    default ProxyFilter ge(SQLColumn<?> column, Object val) {
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
    default ProxyFilter ge(boolean condition, SQLColumn<?> column, Object val) {
        if (condition) {
            getFilter().ge(column.getTable(), column.value(), val);
        }
        return this;
    }

    /**
     * 等于 column = val
     */
    default ProxyFilter eq(SQLColumn<?> column, Object val) {
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
    default ProxyFilter eq(boolean condition, SQLColumn<?> column, Object val) {
        if (condition) {
            getFilter().eq(column.getTable(), column.value(), val);
        }
        return this;
    }

    /**
     * 不等于 column <> val
     */
    default ProxyFilter ne(SQLColumn<?> column, Object val) {
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
    default ProxyFilter ne(boolean condition, SQLColumn<?> column, Object val) {
        if (condition) {
            getFilter().ne(column.getTable(), column.value(), val);
        }
        return this;
    }

    /**
     * 小于等于 column <= val
     */
    default ProxyFilter le(SQLColumn<?> column, Object val) {
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
    default ProxyFilter le(boolean condition, SQLColumn<?> column, Object val) {
        if (condition) {
            getFilter().le(column.getTable(), column.value(), val);
        }
        return this;
    }

    /**
     * 小于 column < val
     */
    default ProxyFilter lt(SQLColumn<?> column, Object val) {
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
    default ProxyFilter lt(boolean condition, SQLColumn<?> column, Object val) {
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
    default ProxyFilter likeMatchLeft(SQLColumn<?> column, Object val) {
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
    default ProxyFilter likeMatchLeft(boolean condition, SQLColumn<?> column, Object val) {
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
    default ProxyFilter likeMatchRight(SQLColumn<?> column, Object val) {
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
    default ProxyFilter likeMatchRight(boolean condition, SQLColumn<?> column, Object val) {
        return like(condition, column, val, SQLLikeEnum.LIKE_PERCENT_LEFT);
    }

    /**
     * column like %val%
     * 列全匹配
     */
    default ProxyFilter like(SQLColumn<?> column, Object val) {
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
    default ProxyFilter like(boolean condition, SQLColumn<?> column, Object val) {
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
    default ProxyFilter like(boolean condition, SQLColumn<?> column, Object val, SQLLikeEnum sqlLike) {
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
    default ProxyFilter notLikeMatchLeft(SQLColumn<?> column, Object val) {
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
    default ProxyFilter notLikeMatchLeft(boolean condition, SQLColumn<?> column, Object val) {
        return notLike(condition, column, val, SQLLikeEnum.LIKE_PERCENT_RIGHT);
    }

    /**
     * column not like %val
     *
     * @param column
     * @param val
     * @return
     */
    default ProxyFilter notLikeMatchRight(SQLColumn<?> column, Object val) {
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
    default ProxyFilter notLikeMatchRight(boolean condition, SQLColumn<?> column, Object val) {
        return notLike(condition, column, val, SQLLikeEnum.LIKE_PERCENT_LEFT);
    }

    /**
     * column not like %val%
     */
    default ProxyFilter notLike(SQLColumn<?> column, Object val) {
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
    default ProxyFilter notLike(boolean condition, SQLColumn<?> column, Object val) {
        return notLike(condition, column, val, SQLLikeEnum.LIKE_PERCENT_ALL);
    }

    default ProxyFilter notLike(boolean condition, SQLColumn<?> column, Object val, SQLLikeEnum sqlLike) {
        if (condition) {
            getFilter().notLike(column.getTable(), column.value(), val, sqlLike);
        }
        return this;
    }

    /**
     * column is null
     */
    default ProxyFilter isNull(SQLColumn<?> column) {
        return isNull(true, column);
    }

    /**
     * column is null
     *
     * @param condition 执行条件
     * @param column    字段
     * @return children
     */
    default ProxyFilter isNull(boolean condition, SQLColumn<?> column) {
        if (condition) {
            getFilter().isNull(column.getTable(), column.value());
        }
        return this;
    }

    /**
     * column is not null
     */
    default ProxyFilter isNotNull(SQLColumn<?> column) {
        return isNotNull(true, column);
    }

    /**
     * column is not null
     *
     * @param condition 执行条件
     * @param column    字段
     * @return children
     */
    default ProxyFilter isNotNull(boolean condition, SQLColumn<?> column) {
        if (condition) {
            getFilter().isNotNull(column.getTable(), column.value());
        }
        return this;
    }

    /**
     * column in collection
     * 集合为空返回False
     */
    default ProxyFilter in(SQLColumn<?> column, Collection<?> collection) {
        return in(true, column, collection);
    }

    /**
     * column in collection
     * 集合为空返回False
     */
    default ProxyFilter in(boolean condition, SQLColumn<?> column, Collection<?> collection) {
        if (condition) {
            getFilter().in(column.getTable(), column.value(), collection);
        }
        return this;
    }

    default <TProperty> ProxyFilter in(SQLColumn<?> column, TProperty[] collection) {
        return in(true, column, collection);
    }

    default <TProperty> ProxyFilter in(boolean condition, SQLColumn<?> column, TProperty[] collection) {
        if (condition) {
            getFilter().in(column.getTable(), column.value(), collection);
        }
        return this;
    }

    default <TProxy extends ProxyQuery<TProxy, TProperty>, TProperty> ProxyFilter in(SQLColumn<TProperty> column, ProxyQueryable<TProxy, TProperty> subQueryable) {
        return in(true, column, subQueryable);
    }

    default <TProxy extends ProxyQuery<TProxy, TProperty>, TProperty> ProxyFilter in(boolean condition, SQLColumn<TProperty> column, ProxyQueryable<TProxy, TProperty> subQueryable) {
        if (condition) {
            getFilter().in(column.getTable(), column.value(), subQueryable);
        }
        return this;
    }

    /**
     * column not in collection
     * 集合为空返回True
     */
    default ProxyFilter notIn(SQLColumn<?> column, Collection<?> collection) {
        return notIn(true, column, collection);
    }

    /**
     * column not in collection
     * 集合为空返回True
     */
    default ProxyFilter notIn(boolean condition, SQLColumn<?> column, Collection<?> collection) {
        if (condition) {
            getFilter().notIn(column.getTable(), column.value(), collection);
        }
        return this;
    }

    default <TProperty> ProxyFilter notIn(SQLColumn<?> column, TProperty[] collection) {
        return notIn(true, column, collection);
    }

    default <TProperty> ProxyFilter notIn(boolean condition, SQLColumn<?> column, TProperty[] collection) {
        if(condition){
            getFilter().notIn(column.getTable(),column.value(),collection);
        }
        return this;
    }

    default <TPropertyProxy extends ProxyQuery<TPropertyProxy,TProperty>,TProperty> ProxyFilter notIn(SQLColumn<?> column,ProxyQueryable<TPropertyProxy,TProperty> subQueryable) {
        return notIn(true,column,subQueryable);
    }

    default <TPropertyProxy extends ProxyQuery<TPropertyProxy,TProperty>,TProperty> ProxyFilter notIn(boolean condition, SQLColumn<?> column, ProxyQueryable<TPropertyProxy,TProperty> subQueryable) {
        if(condition){
            getFilter().notIn(column.getTable(),column.value(),subQueryable);
        }
        return this;
    }

    default <T1Proxy extends ProxyQuery<T1Proxy,T1>,T1,T2Proxy extends ProxyQuery<T2Proxy,T2>,T2> ProxyFilter exists(T1Proxy tableProxy,ProxyQueryable<T2Proxy,T2> subQueryable) {
        return exists(true,tableProxy,subQueryable);
    }

    default <T1Proxy extends ProxyQuery<T1Proxy,T1>,T1,T2Proxy extends ProxyQuery<T2Proxy,T2>,T2> ProxyFilter exists(boolean condition,T1Proxy tableProxy, ProxyQueryable<T2Proxy,T2> subQueryable) {
        if(condition){
            getFilter().exists(tableProxy.getTable(),subQueryable);
        }
        return this;
    }

    default <T1Proxy extends ProxyQuery<T1Proxy,T1>,T1,T2Proxy extends ProxyQuery<T2Proxy,T2>,T2> ProxyFilter notExists(T1Proxy tableProxy,ProxyQueryable<T2Proxy,T2> subQueryable) {
        return notExists(true,tableProxy,subQueryable);
    }

    default <T1Proxy extends ProxyQuery<T1Proxy,T1>,T1,T2Proxy extends ProxyQuery<T2Proxy,T2>,T2> ProxyFilter notExists(boolean condition,T1Proxy tableProxy, ProxyQueryable<T2Proxy,T2> subQueryable) {
        if(condition){
            getFilter().notExists(tableProxy.getTable(),subQueryable);
        }
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
    default ProxyFilter rangeOpenClosed(SQLColumn<?> column, boolean conditionLeft, Object valLeft, boolean conditionRight, Object valRight) {
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
    default ProxyFilter rangeOpenClosed(boolean condition, SQLColumn<?> column, boolean conditionLeft, Object valLeft, boolean conditionRight, Object valRight) {
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
    default ProxyFilter rangeOpen(SQLColumn<?> column, Object valLeft, Object valRight) {
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
    default ProxyFilter rangeOpen(SQLColumn<?> column, boolean conditionLeft, Object valLeft, boolean conditionRight, Object valRight) {
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
    default ProxyFilter rangeOpen(boolean condition, SQLColumn<?> column, boolean conditionLeft, Object valLeft, boolean conditionRight, Object valRight) {
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
    default ProxyFilter rangeClosedOpen(SQLColumn<?> column, boolean conditionLeft, Object valLeft, boolean conditionRight, Object valRight) {
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
    default ProxyFilter rangeClosedOpen(boolean condition, SQLColumn<?> column, boolean conditionLeft, Object valLeft, boolean conditionRight, Object valRight) {
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
    default ProxyFilter rangeClosed(SQLColumn<?> column, Object valLeft, Object valRight) {
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
    default ProxyFilter rangeClosed(SQLColumn<?> column, boolean conditionLeft, Object valLeft, boolean conditionRight, Object valRight) {
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
    default ProxyFilter rangeClosed(boolean condition, SQLColumn<?> column, boolean conditionLeft, Object valLeft, boolean conditionRight, Object valRight) {
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
    default ProxyFilter range(boolean condition, SQLColumn<?> column, boolean conditionLeft, Object valLeft, boolean conditionRight, Object valRight, SQLRangeEnum sqlRange) {
        if (condition) {
            getFilter().range(column.getTable(), column.value(), conditionLeft, valLeft, conditionRight, valRight, sqlRange);
        }
        return this;
    }


    default ProxyFilter columnFunc(ProxyColumnPropertyFunction columnPropertyFunction, SQLPredicateCompare sqlPredicateCompare, Object val) {
        return columnFunc(true, columnPropertyFunction, sqlPredicateCompare, val);
    }

    default ProxyFilter columnFunc(boolean condition, ProxyColumnPropertyFunction columnPropertyFunction, SQLPredicateCompare sqlPredicateCompare, Object val) {
        if (condition) {
            getFilter().columnFunc(columnPropertyFunction.getColumn().getTable(), columnPropertyFunction.getColumnPropertyFunction(), sqlPredicateCompare, val);
        }
        return this;
    }

    default <T2Proxy extends ProxyQuery<T2Proxy, T2>, T2> ProxyFilter eq(SQLColumn<?> column1, SQLColumn<?> column2) {
        return eq(true, column1, column2);
    }

    default <T2> ProxyFilter eq(boolean condition, SQLColumn<?> column1, SQLColumn<?> column2) {
        if (condition) {
            getFilter().eq(column1.getTable(), column1.value(), column2.getTable(), column2.value());
        }
        return this;
    }

    default ProxyFilter and() {
        return and(true);
    }

    default ProxyFilter and(boolean condition) {
        if (condition) {
            getFilter().and();
        }
        return this;
    }

    default ProxyFilter and(SQLExpression1<ProxyFilter> proxyFilterExpression) {
        return and(true, proxyFilterExpression);
    }

    default ProxyFilter and(boolean condition, SQLExpression1<ProxyFilter> proxyFilterExpression) {
        if (condition) {
            getFilter().and(filter -> {
                proxyFilterExpression.apply(new ProxyFilterImpl(filter));
            });
        }
        return this;
    }

    default ProxyFilter or() {
        return or(true);
    }

    default ProxyFilter or(boolean condition) {
        if (condition) {
            getFilter().or();
        }
        return this;
    }

    default ProxyFilter or(SQLExpression1<ProxyFilter> sqlWherePredicateSQLExpression) {
        return or(true, sqlWherePredicateSQLExpression);
    }

    default ProxyFilter or(boolean condition, SQLExpression1<ProxyFilter> proxyFilterExpression) {
        if (condition) {
            getFilter().or(filter -> {
                proxyFilterExpression.apply(new ProxyFilterImpl(filter));
            });
        }
        return this;
    }
}
