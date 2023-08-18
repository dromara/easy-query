package com.easy.query.api4kt.select.extension.queryable8;

import com.easy.query.api4kt.select.KtQueryable8;
import com.easy.query.api4kt.sql.SQLKtWherePredicate;
import com.easy.query.api4kt.sql.impl.SQLKtWherePredicateImpl;
import com.easy.query.core.common.tuple.Tuple8;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.lambda.SQLExpression8;

/**
 * create time 2023/8/18 09:26
 * 文件说明
 *
 * @author xuejiaming
 */
public interface SQLKtFilterable8<T1, T2, T3, T4, T5, T6, T7, T8> extends ClientKtQueryable8Available<T1, T2, T3, T4, T5, T6, T7, T8>, KtQueryable8Available<T1, T2, T3, T4, T5, T6, T7, T8> {

    default KtQueryable8<T1, T2, T3, T4, T5, T6, T7, T8> where(SQLExpression8<SQLKtWherePredicate<T1>, SQLKtWherePredicate<T2>, SQLKtWherePredicate<T3>, SQLKtWherePredicate<T4>, SQLKtWherePredicate<T5>, SQLKtWherePredicate<T6>, SQLKtWherePredicate<T7>, SQLKtWherePredicate<T8>> whereExpression) {
        getClientQueryable8().where((wherePredicate1, wherePredicate2, wherePredicate3, wherePredicate4, wherePredicate5, wherePredicate6, wherePredicate7, wherePredicate8) -> {
            whereExpression.apply(new SQLKtWherePredicateImpl<>(wherePredicate1), new SQLKtWherePredicateImpl<>(wherePredicate2), new SQLKtWherePredicateImpl<>(wherePredicate3), new SQLKtWherePredicateImpl<>(wherePredicate4), new SQLKtWherePredicateImpl<>(wherePredicate5), new SQLKtWherePredicateImpl<>(wherePredicate6), new SQLKtWherePredicateImpl<>(wherePredicate7), new SQLKtWherePredicateImpl<>(wherePredicate8));
        });
        return getQueryable8();
    }

    default KtQueryable8<T1, T2, T3, T4, T5, T6, T7, T8> where(boolean condition, SQLExpression8<SQLKtWherePredicate<T1>, SQLKtWherePredicate<T2>, SQLKtWherePredicate<T3>, SQLKtWherePredicate<T4>, SQLKtWherePredicate<T5>, SQLKtWherePredicate<T6>, SQLKtWherePredicate<T7>, SQLKtWherePredicate<T8>> whereExpression) {
        getClientQueryable8().where(condition, (wherePredicate1, wherePredicate2, wherePredicate3, wherePredicate4, wherePredicate5, wherePredicate6, wherePredicate7, wherePredicate8) -> {
            whereExpression.apply(new SQLKtWherePredicateImpl<>(wherePredicate1), new SQLKtWherePredicateImpl<>(wherePredicate2), new SQLKtWherePredicateImpl<>(wherePredicate3), new SQLKtWherePredicateImpl<>(wherePredicate4), new SQLKtWherePredicateImpl<>(wherePredicate5), new SQLKtWherePredicateImpl<>(wherePredicate6), new SQLKtWherePredicateImpl<>(wherePredicate7), new SQLKtWherePredicateImpl<>(wherePredicate8));
        });
        return getQueryable8();
    }

    default KtQueryable8<T1, T2, T3, T4, T5, T6, T7, T8> whereMerge(SQLExpression1<Tuple8<SQLKtWherePredicate<T1>, SQLKtWherePredicate<T2>, SQLKtWherePredicate<T3>, SQLKtWherePredicate<T4>, SQLKtWherePredicate<T5>, SQLKtWherePredicate<T6>, SQLKtWherePredicate<T7>, SQLKtWherePredicate<T8>>> whereExpression) {
        return whereMerge(true, whereExpression);
    }

    default KtQueryable8<T1, T2, T3, T4, T5, T6, T7, T8> whereMerge(boolean condition, SQLExpression1<Tuple8<SQLKtWherePredicate<T1>, SQLKtWherePredicate<T2>, SQLKtWherePredicate<T3>, SQLKtWherePredicate<T4>, SQLKtWherePredicate<T5>, SQLKtWherePredicate<T6>, SQLKtWherePredicate<T7>, SQLKtWherePredicate<T8>>> whereExpression) {
        return where(condition, (t1,t2, t3, t4, t5, t6, t7, t8) -> {
            whereExpression.apply(new Tuple8<>(t1,t2, t3, t4, t5, t6, t7, t8));
        });
    }
}