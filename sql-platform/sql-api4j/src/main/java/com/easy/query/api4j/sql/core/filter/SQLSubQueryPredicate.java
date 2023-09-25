package com.easy.query.api4j.sql.core.filter;

import com.easy.query.api4j.select.Queryable;
import com.easy.query.api4j.util.EasyLambdaUtil;
import com.easy.query.core.expression.lambda.Property;
import com.easy.query.core.expression.parser.core.EntitySQLTableOwner;
import com.easy.query.core.expression.parser.core.available.ChainCast;

/**
 * create time 2023/9/25 16:48
 * 文件说明
 *
 * @author xuejiaming
 */
public interface SQLSubQueryPredicate<T1, TChain> extends EntitySQLTableOwner<T1>, SQLWherePredicateAvailable<T1>, ChainCast<TChain> {
    default <TProperty> TChain gt(Property<T1,TProperty> property, Queryable<TProperty> subQuery) {
        return gt(true, property, subQuery);
    }

    default <TProperty> TChain gt(boolean condition, Property<T1,TProperty> property, Queryable<TProperty> subQuery) {
        if (condition) {
            getWherePredicate().gt(EasyLambdaUtil.getPropertyName(property), subQuery);
        }
        return castChain();
    }

    default <TProperty> TChain ge( Property<T1,TProperty> property, Queryable<TProperty> subQuery) {
        return ge(true, property, subQuery);
    }

    default <TProperty> TChain ge(boolean condition,  Property<T1,TProperty> property, Queryable<TProperty> subQuery) {
        if (condition) {
            getWherePredicate().ge(EasyLambdaUtil.getPropertyName(property), subQuery);
        }
        return castChain();
    }

    default <TProperty> TChain eq(Property<T1,TProperty> property, Queryable<TProperty> subQuery) {
        return eq(true, property, subQuery);
    }

    default <TProperty> TChain eq(boolean condition, Property<T1,TProperty> property, Queryable<TProperty> subQuery) {
        if (condition) {
            getWherePredicate().eq(EasyLambdaUtil.getPropertyName(property), subQuery);
        }
        return castChain();
    }

    default <TProperty> TChain ne(Property<T1,TProperty> property, Queryable<TProperty> subQuery) {
        return ne(true, property, subQuery);
    }

    default <TProperty> TChain ne(boolean condition, Property<T1,TProperty> property, Queryable<TProperty> subQuery) {
        if (condition) {
            getWherePredicate().ne(EasyLambdaUtil.getPropertyName(property), subQuery);
        }
        return castChain();
    }

    default <TProperty> TChain le(Property<T1,TProperty> property, Queryable<TProperty> subQuery) {
        return le(true, property, subQuery);
    }

    default <TProperty> TChain le(boolean condition, Property<T1,TProperty> property, Queryable<TProperty> subQuery) {
        if (condition) {
            getWherePredicate().le(EasyLambdaUtil.getPropertyName(property), subQuery);
        }
        return castChain();
    }

    default <TProperty> TChain lt(Property<T1,TProperty> property, Queryable<TProperty> subQuery) {
        return lt(true, property, subQuery);
    }

    default <TProperty> TChain lt(boolean condition, Property<T1,TProperty> property, Queryable<TProperty> subQuery) {
        if (condition) {
            getWherePredicate().lt(EasyLambdaUtil.getPropertyName(property), subQuery);
        }
        return castChain();
    }


    default <TProperty> TChain in(Property<T1, TProperty> column, Queryable<TProperty> subQueryable) {
        getWherePredicate().in(EasyLambdaUtil.getPropertyName(column), subQueryable);
        return castChain();
    }

    default <TProperty> TChain in(boolean condition, Property<T1, TProperty> column, Queryable<TProperty> subQueryable) {
        getWherePredicate().in(condition, EasyLambdaUtil.getPropertyName(column), subQueryable);
        return castChain();
    }


    default <TProperty> TChain notIn(Property<T1, TProperty> column, Queryable<TProperty> subQueryable) {
        getWherePredicate().notIn(EasyLambdaUtil.getPropertyName(column), subQueryable);
        return castChain();
    }

    default <TProperty> TChain notIn(boolean condition, Property<T1, TProperty> column, Queryable<TProperty> subQueryable) {
        getWherePredicate().notIn(condition, EasyLambdaUtil.getPropertyName(column), subQueryable);
        return castChain();
    }

    default <T2> TChain exists(Queryable<T2> subQueryable) {
        getWherePredicate().exists(subQueryable);
        return castChain();
    }

    default <T2> TChain exists(boolean condition, Queryable<T2> subQueryable) {
        getWherePredicate().exists(condition, subQueryable);
        return castChain();
    }

    default <T2> TChain notExists(Queryable<T2> subQueryable) {
        getWherePredicate().notExists(subQueryable);
        return castChain();
    }

    default <T2> TChain notExists(boolean condition, Queryable<T2> subQueryable) {
        getWherePredicate().notExists(condition, subQueryable);
        return castChain();
    }


}
