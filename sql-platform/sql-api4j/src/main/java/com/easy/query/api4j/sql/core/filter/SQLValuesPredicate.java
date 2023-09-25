package com.easy.query.api4j.sql.core.filter;

import com.easy.query.api4j.util.EasyLambdaUtil;
import com.easy.query.core.expression.lambda.Property;
import com.easy.query.core.expression.parser.core.EntitySQLTableOwner;
import com.easy.query.core.expression.parser.core.available.ChainCast;

import java.util.Collection;

/**
 * create time 2023/9/25 16:35
 * 集合判断
 *
 * @author xuejiaming
 */
public interface SQLValuesPredicate<T1, TChain> extends EntitySQLTableOwner<T1>, SQLWherePredicateAvailable<T1>, ChainCast<TChain> {

    /**
     * column in collection
     * 集合为空返回False
     */
    default <TProperty> TChain in(Property<T1, TProperty> column, Collection<TProperty> collection) {
        getWherePredicate().in(EasyLambdaUtil.getPropertyName(column), collection);
        return castChain();
    }

    /**
     * column in collection
     * 集合为空返回False
     */
    default <TProperty> TChain in(boolean condition, Property<T1, TProperty> column, Collection<TProperty> collection) {
        getWherePredicate().in(condition, EasyLambdaUtil.getPropertyName(column), collection);
        return castChain();
    }

    default <TProperty> TChain in(Property<T1, TProperty> column, TProperty[] collection) {
        getWherePredicate().in(EasyLambdaUtil.getPropertyName(column), collection);
        return castChain();
    }

    default <TProperty> TChain in(boolean condition, Property<T1, TProperty> column, TProperty[] collection) {
        getWherePredicate().in(condition, EasyLambdaUtil.getPropertyName(column), collection);
        return castChain();
    }
    /**
     * column not in collection
     * 集合为空返回True
     */
    default <TProperty> TChain notIn(Property<T1, TProperty> column, Collection<TProperty> collection) {
        getWherePredicate().notIn(EasyLambdaUtil.getPropertyName(column), collection);
        return castChain();
    }

    /**
     * column not in collection
     * 集合为空返回True
     */
    default <TProperty> TChain notIn(boolean condition, Property<T1, TProperty> column, Collection<TProperty> collection) {
        getWherePredicate().notIn(condition, EasyLambdaUtil.getPropertyName(column), collection);
        return castChain();
    }

    default <TProperty> TChain notIn(Property<T1, TProperty> column, TProperty[] collection) {
        getWherePredicate().notIn(EasyLambdaUtil.getPropertyName(column), collection);
        return castChain();
    }

    default <TProperty> TChain notIn(boolean condition, Property<T1, TProperty> column, TProperty[] collection) {
        getWherePredicate().notIn(condition, EasyLambdaUtil.getPropertyName(column), collection);
        return castChain();
    }
}
