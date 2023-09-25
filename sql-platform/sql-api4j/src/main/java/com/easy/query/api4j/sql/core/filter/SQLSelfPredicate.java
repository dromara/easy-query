package com.easy.query.api4j.sql.core.filter;

import com.easy.query.api4j.util.EasyLambdaUtil;
import com.easy.query.core.expression.lambda.Property;
import com.easy.query.core.expression.parser.core.EntitySQLTableOwner;
import com.easy.query.core.expression.parser.core.available.ChainCast;

/**
 * create time 2023/9/25 16:43
 * 文件说明
 *
 * @author xuejiaming
 */
public interface SQLSelfPredicate<T1,TChain> extends SQLWherePredicateAvailable<T1>,ChainCast<TChain> {

    default <T2, TProperty> TChain gt(EntitySQLTableOwner<T2> sub, Property<T1, TProperty> column1, Property<T2, TProperty> column2) {
        return gt(true, sub, column1, column2);
    }

    default <T2, TProperty> TChain gt(boolean condition, EntitySQLTableOwner<T2> sub, Property<T1, TProperty> column1, Property<T2, TProperty> column2) {
        getWherePredicate().gt(condition, sub, EasyLambdaUtil.getPropertyName(column1), EasyLambdaUtil.getPropertyName(column2));
        return castChain();
    }

    default <T2, TProperty> TChain ge(EntitySQLTableOwner<T2> sub, Property<T1, TProperty> column1, Property<T2, TProperty> column2) {
        return ge(true, sub, column1, column2);
    }

    default <T2, TProperty> TChain ge(boolean condition, EntitySQLTableOwner<T2> sub, Property<T1, TProperty> column1, Property<T2, TProperty> column2) {
        getWherePredicate().ge(condition, sub, EasyLambdaUtil.getPropertyName(column1), EasyLambdaUtil.getPropertyName(column2));
        return castChain();
    }

    default <T2, TProperty> TChain eq(EntitySQLTableOwner<T2> sub, Property<T1, TProperty> column1, Property<T2, TProperty> column2) {
        return eq(true, sub, column1, column2);
    }

    default <T2, TProperty> TChain eq(boolean condition, EntitySQLTableOwner<T2> sub, Property<T1, TProperty> column1, Property<T2, TProperty> column2) {
        getWherePredicate().eq(condition, sub, EasyLambdaUtil.getPropertyName(column1), EasyLambdaUtil.getPropertyName(column2));
        return castChain();
    }

    default <T2, TProperty> TChain ne(EntitySQLTableOwner<T2> sub, Property<T1, TProperty> column1, Property<T2, TProperty> column2) {
        return ne(true, sub, column1, column2);
    }

    default <T2, TProperty> TChain ne(boolean condition, EntitySQLTableOwner<T2> sub, Property<T1, TProperty> column1, Property<T2, TProperty> column2) {
        getWherePredicate().ne(condition, sub, EasyLambdaUtil.getPropertyName(column1), EasyLambdaUtil.getPropertyName(column2));
        return castChain();
    }

    default <T2, TProperty> TChain le(EntitySQLTableOwner<T2> sub, Property<T1, TProperty> column1, Property<T2, TProperty> column2) {
        return le(true, sub, column1, column2);
    }

    default <T2, TProperty> TChain le(boolean condition, EntitySQLTableOwner<T2> sub, Property<T1, TProperty> column1, Property<T2, TProperty> column2) {
        getWherePredicate().le(condition, sub, EasyLambdaUtil.getPropertyName(column1), EasyLambdaUtil.getPropertyName(column2));
        return castChain();
    }

    default <T2, TProperty> TChain lt(EntitySQLTableOwner<T2> sub, Property<T1, TProperty> column1, Property<T2, TProperty> column2) {
        return lt(true, sub, column1, column2);
    }

    default <T2, TProperty> TChain lt(boolean condition, EntitySQLTableOwner<T2> sub, Property<T1, TProperty> column1, Property<T2, TProperty> column2) {
        getWherePredicate().lt(condition, sub, EasyLambdaUtil.getPropertyName(column1), EasyLambdaUtil.getPropertyName(column2));
        return castChain();
    }
}
