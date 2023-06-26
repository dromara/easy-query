package com.easy.query.core.expression.builder;

import com.easy.query.core.basic.api.select.Query;
import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.enums.SQLLikeEnum;
import com.easy.query.core.enums.SQLPredicateCompare;
import com.easy.query.core.enums.SQLRangeEnum;
import com.easy.query.core.expression.func.ColumnPropertyFunction;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.parser.core.available.TableAvailable;

import java.util.Collection;

/**
 * create time 2023/6/22 14:39
 * 文件说明
 *
 * @author xuejiaming
 */
public interface Filter {
    boolean getReverse();
    QueryRuntimeContext getRuntimeContext();

    /**
     * 大于 column > val
     *
     * @param property    字段
     * @param val       值
     * @return children
     */
    Filter gt(TableAvailable table, String property, Object val);

    /**
     * 等于 column >= val
     *
     * @param property  字段
     * @param val       值
     * @return children
     */
    Filter ge(TableAvailable table, String property, Object val);
    /**
     * 等于 column = val
     *
     * @param property    字段
     * @param val       值
     * @return children
     */
    Filter eq(TableAvailable table, String property, Object val);
    /**
     * 不等于 column <> val
     *
     * @param property    字段
     * @param val       值
     * @return children
     */
    Filter ne(TableAvailable table, String property, Object val);
    /**
     * 小于等于 column <= val
     *
     * @param property    字段
     * @param val       值
     * @return children
     */
    Filter le(TableAvailable table, String property, Object val);

    /**
     * 小于 column < val
     *
     * @param property    字段
     * @param val       值
     * @return children
     */
    Filter lt(TableAvailable table, String property, Object val);

    /**
     * column like ?val?
     * 列自定义匹配
     * @param property
     * @param val
     * @param sqlLike
     * @return
     */
    Filter like(TableAvailable table, String property, Object val, SQLLikeEnum sqlLike);
    Filter notLike(TableAvailable table, String property, Object val, SQLLikeEnum sqlLike);

    /**
     * column is null
     *
     * @param property    字段
     * @return children
     */
    Filter isNull(TableAvailable table, String property);

    /**
     * column is not null
     *
     * @param property    字段
     * @return children
     */
    Filter isNotNull(TableAvailable table, String property);

    /**
     * column in collection
     * 集合为空返回False
     */
    Filter in(TableAvailable table, String property, Collection<?> collection);

    <TProperty> Filter in(TableAvailable table, String property, TProperty[] collection);


    <TProperty> Filter in(TableAvailable table, String property, Query<TProperty> subQuery);

    /**
     * column not in collection
     * 集合为空返回True
     */
    Filter notIn(TableAvailable table, String property, Collection<?> collection);

    <TProperty> Filter notIn(TableAvailable table, String property, TProperty[] collection);


    <TProperty> Filter notIn(TableAvailable table, String property, Query<TProperty> subQuery);

    <T2> Filter exists(TableAvailable table,Query<T2> subQuery);


    <T2> Filter notExists(TableAvailable table,Query<T2> subQuery);

    /**
     * 自定义范围
     * 一般用于范围比如时间,小的时间在前大的时间在后
     *
     * @param property
     * @param conditionLeft
     * @param valLeft
     * @param conditionRight
     * @param valRight
     * @param sqlRange
     * @return
     */
    Filter range(TableAvailable table, String property, boolean conditionLeft, Object valLeft, boolean conditionRight, Object valRight, SQLRangeEnum sqlRange);


    Filter columnFunc(TableAvailable table,ColumnPropertyFunction columnPropertyFunction, SQLPredicateCompare sqlPredicateCompare, Object val);

    <T2> Filter eq(TableAvailable leftTable, String property1,TableAvailable rightTable, String property2);

    Filter and();


    Filter and(SQLExpression1<Filter> sqlWherePredicateSQLExpression);


    Filter or();


    Filter or(SQLExpression1<Filter> sqlWherePredicateSQLExpression);
}
