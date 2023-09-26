package com.easy.query.api4kt.sql.core.filter;

import com.easy.query.api4kt.util.EasyKtLambdaUtil;
import com.easy.query.core.expression.parser.core.EntitySQLTableOwner;
import com.easy.query.core.expression.parser.core.available.ChainCast;
import kotlin.reflect.KProperty1;

/**
 * create time 2023/9/25 16:43
 * 文件说明
 *
 * @author xuejiaming
 */
public interface SQLKtSelfPredicate<T1,TChain> extends SQLKtWherePredicateAvailable<T1>,ChainCast<TChain> {

    default <T2, TProperty> TChain gt(EntitySQLTableOwner<T2> sub, KProperty1<? super T1, TProperty> column1, KProperty1<T2, TProperty> column2) {
        return gt(true, sub, column1, column2);
    }

    default <T2, TProperty> TChain gt(boolean condition, EntitySQLTableOwner<T2> sub, KProperty1<? super T1, TProperty> column1, KProperty1<T2, TProperty> column2) {
        getWherePredicate().gt(condition, sub, EasyKtLambdaUtil.getPropertyName(column1), EasyKtLambdaUtil.getPropertyName(column2));
        return castChain();
    }

    default <T2, TProperty> TChain ge(EntitySQLTableOwner<T2> sub, KProperty1<? super T1, TProperty> column1, KProperty1<T2, TProperty> column2) {
        return ge(true, sub, column1, column2);
    }

    default <T2, TProperty> TChain ge(boolean condition, EntitySQLTableOwner<T2> sub, KProperty1<? super T1, TProperty> column1, KProperty1<T2, TProperty> column2) {
        getWherePredicate().ge(condition, sub, EasyKtLambdaUtil.getPropertyName(column1), EasyKtLambdaUtil.getPropertyName(column2));
        return castChain();
    }

    default <T2, TProperty> TChain eq(EntitySQLTableOwner<T2> sub, KProperty1<? super T1, TProperty> column1, KProperty1<T2, TProperty> column2) {
        return eq(true, sub, column1, column2);
    }

    default <T2, TProperty> TChain eq(boolean condition, EntitySQLTableOwner<T2> sub, KProperty1<? super T1, TProperty> column1, KProperty1<T2, TProperty> column2) {
        getWherePredicate().eq(condition, sub, EasyKtLambdaUtil.getPropertyName(column1), EasyKtLambdaUtil.getPropertyName(column2));
        return castChain();
    }

    default <T2, TProperty> TChain ne(EntitySQLTableOwner<T2> sub, KProperty1<? super T1, TProperty> column1, KProperty1<T2, TProperty> column2) {
        return ne(true, sub, column1, column2);
    }

    default <T2, TProperty> TChain ne(boolean condition, EntitySQLTableOwner<T2> sub, KProperty1<? super T1, TProperty> column1, KProperty1<T2, TProperty> column2) {
        getWherePredicate().ne(condition, sub, EasyKtLambdaUtil.getPropertyName(column1), EasyKtLambdaUtil.getPropertyName(column2));
        return castChain();
    }

    default <T2, TProperty> TChain le(EntitySQLTableOwner<T2> sub, KProperty1<? super T1, TProperty> column1, KProperty1<T2, TProperty> column2) {
        return le(true, sub, column1, column2);
    }

    default <T2, TProperty> TChain le(boolean condition, EntitySQLTableOwner<T2> sub, KProperty1<? super T1, TProperty> column1, KProperty1<T2, TProperty> column2) {
        getWherePredicate().le(condition, sub, EasyKtLambdaUtil.getPropertyName(column1), EasyKtLambdaUtil.getPropertyName(column2));
        return castChain();
    }

    default <T2, TProperty> TChain lt(EntitySQLTableOwner<T2> sub, KProperty1<? super T1, TProperty> column1, KProperty1<T2, TProperty> column2) {
        return lt(true, sub, column1, column2);
    }

    default <T2, TProperty> TChain lt(boolean condition, EntitySQLTableOwner<T2> sub, KProperty1<? super T1, TProperty> column1, KProperty1<T2, TProperty> column2) {
        getWherePredicate().lt(condition, sub, EasyKtLambdaUtil.getPropertyName(column1), EasyKtLambdaUtil.getPropertyName(column2));
        return castChain();
    }
}
