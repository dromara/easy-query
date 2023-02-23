package org.easy.query.core.abstraction.sql.base;

import org.easy.query.core.abstraction.lambda.Property;
import org.easy.query.core.abstraction.lambda.SqlExpression;

import java.io.Serializable;
import java.util.Collection;

/**
 *
 * @FileName: Predicate.java
 * @Description: 文件说明
 * @Date: 2023/2/5 09:09
 * @Created by xuejiaming
 */
public interface WherePredicate<T1, TChain> extends Serializable, IndexAware {
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
     * column like %val%
     */
    default TChain like(Property<T1, ?> column, Object val) {
        return like(true, column, val);
    }

    /**
     * column like %val%
     *
     * @param condition 执行条件
     * @param column    字段
     * @param val       值
     * @return children
     */
    TChain like(boolean condition, Property<T1, ?> column, Object val);
    /**
     *  column not like %val%
     */
    default TChain notLike(Property<T1, ?> column, Object val) {
        return notLike(true, column, val);
    }

    /**
     *  column not like %val%
     *
     * @param condition 执行条件
     * @param column    字段
     * @param val       值
     * @return children
     */
    TChain notLike(boolean condition, Property<T1, ?> column, Object val);
    /**
     *  column is null
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
     *  column is not null
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
     *  column in collection
     */
    default TChain in(Property<T1, ?> column, Collection<?> collection) {
        return in(true, column,collection);
    }

    /**
     * column in collection
     */
    TChain in(boolean condition, Property<T1, ?> column, Collection<?> collection);
    /**
     *  column in collection
     */
    default TChain notIn(Property<T1, ?> column, Collection<?> collection) {
        return notIn(true, column,collection);
    }

    /**
     * column in collection
     */
    TChain notIn(boolean condition, Property<T1, ?> column, Collection<?> collection);

    default <T2, TChain2> TChain eq(WherePredicate<T2, TChain2> sub, Property<T1, ?> column1, Property<T2, ?> column2) {
        return eq(true, sub, column1, column2);
    }

    <T2, TChain2> TChain eq(boolean condition, WherePredicate<T2, TChain2> sub, Property<T1, ?> column1, Property<T2, ?> column2);


    <T2, TChain2> WherePredicate<T2, TChain2> then(WherePredicate<T2, TChain2> sub);
    default TChain and(){
        return and(true);
    }
    TChain and(boolean condition);
    default TChain and(SqlExpression<TChain> predicateSqlExpression){
        return and(true,predicateSqlExpression);
    }
    TChain and(boolean condition,SqlExpression<TChain> predicateSqlExpression);
    default TChain or(){
        return or(true);
    }
    TChain or(boolean condition);

    default TChain or(SqlExpression<TChain> predicateSqlExpression){
        return or(true,predicateSqlExpression);
    }
    TChain or(boolean condition,SqlExpression<TChain> predicateSqlExpression);
}
