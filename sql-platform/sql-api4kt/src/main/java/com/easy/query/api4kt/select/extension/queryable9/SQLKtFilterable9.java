package com.easy.query.api4kt.select.extension.queryable9;

import com.easy.query.api4kt.select.KtQueryable9;
import com.easy.query.api4kt.sql.SQLKtWherePredicate;
import com.easy.query.api4kt.sql.impl.SQLKtWherePredicateImpl;
import com.easy.query.core.common.tuple.Tuple9;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.lambda.SQLExpression9;

/**
 * create time 2023/8/18 09:26
 * 文件说明
 *
 * @author xuejiaming
 */
public interface SQLKtFilterable9<T1, T2, T3, T4, T5, T6, T7, T8, T9> extends ClientKtQueryable9Available<T1, T2, T3, T4, T5, T6, T7, T8, T9>, KtQueryable9Available<T1, T2, T3, T4, T5, T6, T7, T8, T9> {

    default KtQueryable9<T1, T2, T3, T4, T5, T6, T7, T8, T9> where(SQLExpression9<SQLKtWherePredicate<T1>, SQLKtWherePredicate<T2>, SQLKtWherePredicate<T3>, SQLKtWherePredicate<T4>, SQLKtWherePredicate<T5>, SQLKtWherePredicate<T6>, SQLKtWherePredicate<T7>, SQLKtWherePredicate<T8>, SQLKtWherePredicate<T9>> whereExpression) {
        getClientQueryable9().where((wherePredicate1, wherePredicate2, wherePredicate3, wherePredicate4, wherePredicate5, wherePredicate6, wherePredicate7, wherePredicate8, wherePredicate9) -> {
            whereExpression.apply(new SQLKtWherePredicateImpl<>(wherePredicate1), new SQLKtWherePredicateImpl<>(wherePredicate2), new SQLKtWherePredicateImpl<>(wherePredicate3), new SQLKtWherePredicateImpl<>(wherePredicate4), new SQLKtWherePredicateImpl<>(wherePredicate5), new SQLKtWherePredicateImpl<>(wherePredicate6), new SQLKtWherePredicateImpl<>(wherePredicate7), new SQLKtWherePredicateImpl<>(wherePredicate8), new SQLKtWherePredicateImpl<>(wherePredicate9));
        });
        return getQueryable9();
    }

    default KtQueryable9<T1, T2, T3, T4, T5, T6, T7, T8, T9> where(boolean condition, SQLExpression9<SQLKtWherePredicate<T1>, SQLKtWherePredicate<T2>, SQLKtWherePredicate<T3>, SQLKtWherePredicate<T4>, SQLKtWherePredicate<T5>, SQLKtWherePredicate<T6>, SQLKtWherePredicate<T7>, SQLKtWherePredicate<T8>, SQLKtWherePredicate<T9>> whereExpression) {
        getClientQueryable9().where(condition, (wherePredicate1, wherePredicate2, wherePredicate3, wherePredicate4, wherePredicate5, wherePredicate6, wherePredicate7, wherePredicate8, wherePredicate9) -> {
            whereExpression.apply(new SQLKtWherePredicateImpl<>(wherePredicate1), new SQLKtWherePredicateImpl<>(wherePredicate2), new SQLKtWherePredicateImpl<>(wherePredicate3), new SQLKtWherePredicateImpl<>(wherePredicate4), new SQLKtWherePredicateImpl<>(wherePredicate5), new SQLKtWherePredicateImpl<>(wherePredicate6), new SQLKtWherePredicateImpl<>(wherePredicate7), new SQLKtWherePredicateImpl<>(wherePredicate8), new SQLKtWherePredicateImpl<>(wherePredicate9));
        });
        return getQueryable9();
    }

    default KtQueryable9<T1, T2, T3, T4, T5, T6, T7, T8, T9> whereMerge(SQLExpression1<Tuple9<SQLKtWherePredicate<T1>, SQLKtWherePredicate<T2>, SQLKtWherePredicate<T3>, SQLKtWherePredicate<T4>, SQLKtWherePredicate<T5>, SQLKtWherePredicate<T6>, SQLKtWherePredicate<T7>, SQLKtWherePredicate<T8>, SQLKtWherePredicate<T9>>> whereExpression) {
        return whereMerge(true, whereExpression);
    }

    default KtQueryable9<T1, T2, T3, T4, T5, T6, T7, T8, T9> whereMerge(boolean condition, SQLExpression1<Tuple9<SQLKtWherePredicate<T1>, SQLKtWherePredicate<T2>, SQLKtWherePredicate<T3>, SQLKtWherePredicate<T4>, SQLKtWherePredicate<T5>, SQLKtWherePredicate<T6>, SQLKtWherePredicate<T7>, SQLKtWherePredicate<T8>, SQLKtWherePredicate<T9>>> whereExpression) {
        return where(condition, (t1, t2, t3, t4, t5, t6, t7, t8, t9) -> {
            whereExpression.apply(new Tuple9<>(t1, t2, t3, t4, t5, t6, t7, t8, t9));
        });
    }
}
