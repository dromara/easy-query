package com.easy.query.api4j.sql.core.filter;

import com.easy.query.api4j.util.EasyLambdaUtil;
import com.easy.query.core.expression.lambda.Property;
import com.easy.query.core.expression.parser.core.EntitySQLTableOwner;
import com.easy.query.core.expression.parser.core.available.ChainCast;

/**
 * create time 2023/9/25 16:35
 * 文件说明
 *
 * @author xuejiaming
 */
public interface SQLValuePredicate<T1, TChain> extends EntitySQLTableOwner<T1>, SQLWherePredicateAvailable<T1>, ChainCast<TChain> {

    /**
     * 大于 column > val
     */
    default <TProperty, TValue> TChain gt(Property<T1, TProperty> column, TValue val) {
        getWherePredicate().gt(true, EasyLambdaUtil.getPropertyName(column), val);
        return castChain();
    }

    /**
     * 大于 column > val
     *
     * @param condition 执行条件
     * @param column    字段
     * @param val       值
     * @return children
     */
    default <TProperty, TValue> TChain gt(boolean condition, Property<T1, TProperty> column, TValue val) {
        getWherePredicate().gt(condition, EasyLambdaUtil.getPropertyName(column), val);
        return castChain();
    }

    /**
     * 等于 column >= val
     */
    default <TProperty, TValue> TChain ge(Property<T1, TProperty> column, TValue val) {
        getWherePredicate().ge(EasyLambdaUtil.getPropertyName(column), val);
        return castChain();
    }

    /**
     * 等于 column >= val
     *
     * @param condition 执行条件
     * @param column    字段
     * @param val       值
     * @return children
     */
    default <TProperty, TValue> TChain ge(boolean condition, Property<T1, TProperty> column, TValue val) {
        getWherePredicate().ge(condition, EasyLambdaUtil.getPropertyName(column), val);
        return castChain();
    }

    /**
     * 等于 column = val
     */
    default <TProperty, TValue> TChain eq(Property<T1, TProperty> column, TValue val) {
        getWherePredicate().eq(EasyLambdaUtil.getPropertyName(column), val);
        return castChain();
    }

    /**
     * 等于 column = val
     *
     * @param condition 执行条件
     * @param column    字段
     * @param val       值
     * @return children
     */
    default <TProperty, TValue> TChain eq(boolean condition, Property<T1, TProperty> column, TValue val) {
        getWherePredicate().eq(condition, EasyLambdaUtil.getPropertyName(column), val);
        return castChain();
    }

    /**
     * 不等于 column <> val
     */
    default <TProperty, TValue> TChain ne(Property<T1, TProperty> column, TValue val) {
        getWherePredicate().ne(EasyLambdaUtil.getPropertyName(column), val);
        return castChain();
    }

    /**
     * 不等于 column <> val
     *
     * @param condition 执行条件
     * @param column    字段
     * @param val       值
     * @return children
     */
    default <TProperty, TValue> TChain ne(boolean condition, Property<T1, TProperty> column, TValue val) {
        getWherePredicate().ne(condition, EasyLambdaUtil.getPropertyName(column), val);
        return castChain();
    }

    /**
     * 小于等于 column <= val
     */
    default <TProperty, TValue> TChain le(Property<T1, TProperty> column, TValue val) {
        getWherePredicate().le(EasyLambdaUtil.getPropertyName(column), val);
        return castChain();
    }

    /**
     * 小于等于 column <= val
     *
     * @param condition 执行条件
     * @param column    字段
     * @param val       值
     * @return children
     */
    default <TProperty, TValue> TChain le(boolean condition, Property<T1, TProperty> column, TValue val) {
        getWherePredicate().le(condition, EasyLambdaUtil.getPropertyName(column), val);
        return castChain();
    }

    /**
     * 小于 column < val
     */
    default <TProperty, TValue> TChain lt(Property<T1, TProperty> column, TValue val) {
        getWherePredicate().lt(EasyLambdaUtil.getPropertyName(column), val);
        return castChain();
    }

    /**
     * 小于 column < val
     *
     * @param condition 执行条件
     * @param column    字段
     * @param val       值
     * @return children
     */
    default <TProperty, TValue> TChain lt(boolean condition, Property<T1, TProperty> column, TValue val) {
        getWherePredicate().lt(condition, EasyLambdaUtil.getPropertyName(column), val);
        return castChain();
    }


}
