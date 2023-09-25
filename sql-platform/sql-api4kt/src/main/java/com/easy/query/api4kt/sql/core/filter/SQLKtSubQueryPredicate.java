package com.easy.query.api4kt.sql.core.filter;

import com.easy.query.api4kt.select.KtQueryable;
import com.easy.query.api4kt.util.EasyKtLambdaUtil;
import com.easy.query.core.expression.parser.core.EntitySQLTableOwner;
import com.easy.query.core.expression.parser.core.available.ChainCast;
import kotlin.reflect.KProperty1;

/**
 * create time 2023/9/25 16:48
 * 文件说明
 *
 * @author xuejiaming
 */
public interface SQLKtSubQueryPredicate<T1, TChain> extends EntitySQLTableOwner<T1>, SQLKtWherePredicateAvailable<T1>, ChainCast<TChain> {
    default <TProperty> TChain gt(KProperty1<T1, TProperty> property, KtQueryable<TProperty> subQuery) {
        return gt(true, property, subQuery);
    }

    default <TProperty> TChain gt(boolean condition, KProperty1<T1, TProperty> property, KtQueryable<TProperty> subQuery) {
        if (condition) {
            getWherePredicate().gt(EasyKtLambdaUtil.getPropertyName(property), subQuery);
        }
        return castChain();
    }

    default <TProperty> TChain ge(KProperty1<T1, TProperty> property, KtQueryable<TProperty> subQuery) {
        return ge(true, property, subQuery);
    }

    default <TProperty> TChain ge(boolean condition, KProperty1<T1, TProperty> property, KtQueryable<TProperty> subQuery) {
        if (condition) {
            getWherePredicate().ge(EasyKtLambdaUtil.getPropertyName(property), subQuery);
        }
        return castChain();
    }

    default <TProperty> TChain eq(KProperty1<T1, TProperty> property, KtQueryable<TProperty> subQuery) {
        return eq(true, property, subQuery);
    }

    default <TProperty> TChain eq(boolean condition, KProperty1<T1, TProperty> property, KtQueryable<TProperty> subQuery) {
        if (condition) {
            getWherePredicate().eq(EasyKtLambdaUtil.getPropertyName(property), subQuery);
        }
        return castChain();
    }

    default <TProperty> TChain ne(KProperty1<T1, TProperty> property, KtQueryable<TProperty> subQuery) {
        return ne(true, property, subQuery);
    }

    default <TProperty> TChain ne(boolean condition, KProperty1<T1, TProperty> property, KtQueryable<TProperty> subQuery) {
        if (condition) {
            getWherePredicate().ne(EasyKtLambdaUtil.getPropertyName(property), subQuery);
        }
        return castChain();
    }

    default <TProperty> TChain le(KProperty1<T1, TProperty> property, KtQueryable<TProperty> subQuery) {
        return le(true, property, subQuery);
    }

    default <TProperty> TChain le(boolean condition, KProperty1<T1, TProperty> property, KtQueryable<TProperty> subQuery) {
        if (condition) {
            getWherePredicate().le(EasyKtLambdaUtil.getPropertyName(property), subQuery);
        }
        return castChain();
    }

    default <TProperty> TChain lt(KProperty1<T1, TProperty> property, KtQueryable<TProperty> subQuery) {
        return lt(true, property, subQuery);
    }

    default <TProperty> TChain lt(boolean condition, KProperty1<T1, TProperty> property, KtQueryable<TProperty> subQuery) {
        if (condition) {
            getWherePredicate().lt(EasyKtLambdaUtil.getPropertyName(property), subQuery);
        }
        return castChain();
    }


    default <TProperty> TChain in(KProperty1<T1, TProperty> column, KtQueryable<TProperty> subQueryable) {
        getWherePredicate().in(EasyKtLambdaUtil.getPropertyName(column), subQueryable);
        return castChain();
    }

    default <TProperty> TChain in(boolean condition, KProperty1<T1, TProperty> column, KtQueryable<TProperty> subQueryable) {
        getWherePredicate().in(condition, EasyKtLambdaUtil.getPropertyName(column), subQueryable);
        return castChain();
    }


    default <TProperty> TChain notIn(KProperty1<T1, TProperty> column, KtQueryable<TProperty> subQueryable) {
        getWherePredicate().notIn(EasyKtLambdaUtil.getPropertyName(column), subQueryable);
        return castChain();
    }

    default <TProperty> TChain notIn(boolean condition, KProperty1<T1, TProperty> column, KtQueryable<TProperty> subQueryable) {
        getWherePredicate().notIn(condition, EasyKtLambdaUtil.getPropertyName(column), subQueryable);
        return castChain();
    }

    default <T2> TChain exists(KtQueryable<T2> subQueryable) {
        getWherePredicate().exists(subQueryable);
        return castChain();
    }

    default <T2> TChain exists(boolean condition, KtQueryable<T2> subQueryable) {
        getWherePredicate().exists(condition, subQueryable);
        return castChain();
    }

    default <T2> TChain notExists(KtQueryable<T2> subQueryable) {
        getWherePredicate().notExists(subQueryable);
        return castChain();
    }

    default <T2> TChain notExists(boolean condition, KtQueryable<T2> subQueryable) {
        getWherePredicate().notExists(condition, subQueryable);
        return castChain();
    }


}
