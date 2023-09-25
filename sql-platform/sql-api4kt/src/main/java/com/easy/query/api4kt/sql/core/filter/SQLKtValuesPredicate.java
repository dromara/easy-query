package com.easy.query.api4kt.sql.core.filter;

import com.easy.query.api4kt.util.EasyKtLambdaUtil;
import com.easy.query.core.expression.parser.core.EntitySQLTableOwner;
import com.easy.query.core.expression.parser.core.available.ChainCast;
import kotlin.reflect.KProperty1;

import java.util.Collection;

/**
 * create time 2023/9/25 16:35
 * 集合判断
 *
 * @author xuejiaming
 */
public interface SQLKtValuesPredicate<T1, TChain> extends EntitySQLTableOwner<T1>, SQLKtWherePredicateAvailable<T1>, ChainCast<TChain> {

    /**
     * column in collection
     * 集合为空返回False
     */
    default <TProperty> TChain in(KProperty1<T1, TProperty> column, Collection<TProperty> collection) {
        getWherePredicate().in(EasyKtLambdaUtil.getPropertyName(column), collection);
        return castChain();
    }

    /**
     * column in collection
     * 集合为空返回False
     */
    default <TProperty> TChain in(boolean condition, KProperty1<T1, TProperty> column, Collection<TProperty> collection) {
        getWherePredicate().in(condition, EasyKtLambdaUtil.getPropertyName(column), collection);
        return castChain();
    }

    default <TProperty> TChain in(KProperty1<T1, TProperty> column, TProperty[] collection) {
        getWherePredicate().in(EasyKtLambdaUtil.getPropertyName(column), collection);
        return castChain();
    }

    default <TProperty> TChain in(boolean condition, KProperty1<T1, TProperty> column, TProperty[] collection) {
        getWherePredicate().in(condition, EasyKtLambdaUtil.getPropertyName(column), collection);
        return castChain();
    }
    /**
     * column not in collection
     * 集合为空返回True
     */
    default <TProperty> TChain notIn(KProperty1<T1, TProperty> column, Collection<TProperty> collection) {
        getWherePredicate().notIn(EasyKtLambdaUtil.getPropertyName(column), collection);
        return castChain();
    }

    /**
     * column not in collection
     * 集合为空返回True
     */
    default <TProperty> TChain notIn(boolean condition, KProperty1<T1, TProperty> column, Collection<TProperty> collection) {
        getWherePredicate().notIn(condition, EasyKtLambdaUtil.getPropertyName(column), collection);
        return castChain();
    }

    default <TProperty> TChain notIn(KProperty1<T1, TProperty> column, TProperty[] collection) {
        getWherePredicate().notIn(EasyKtLambdaUtil.getPropertyName(column), collection);
        return castChain();
    }

    default <TProperty> TChain notIn(boolean condition, KProperty1<T1, TProperty> column, TProperty[] collection) {
        getWherePredicate().notIn(condition, EasyKtLambdaUtil.getPropertyName(column), collection);
        return castChain();
    }
}
