package com.easy.query.api4kt.select.extension.queryable5;

import com.easy.query.api4kt.select.KtQueryable5;
import com.easy.query.api4kt.sql.SQLKtWherePredicate;
import com.easy.query.api4kt.sql.impl.SQLKtWherePredicateImpl;
import com.easy.query.core.common.tuple.Tuple5;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.lambda.SQLExpression5;

/**
 * create time 2023/8/18 09:21
 * 文件说明
 *
 * @author xuejiaming
 */
public interface SQLKtFilterable5<T1, T2, T3, T4, T5> extends ClientKtQueryable5Available<T1, T2, T3, T4, T5>, KtQueryable5Available<T1, T2, T3, T4, T5> {

    default KtQueryable5<T1, T2, T3, T4, T5> where(SQLExpression5<SQLKtWherePredicate<T1>, SQLKtWherePredicate<T2>, SQLKtWherePredicate<T3>, SQLKtWherePredicate<T4>, SQLKtWherePredicate<T5>> whereExpression) {
        getClientQueryable5().where((wherePredicate1, wherePredicate2, wherePredicate3, wherePredicate4, wherePredicate5) -> {
            whereExpression.apply(new SQLKtWherePredicateImpl<>(wherePredicate1), new SQLKtWherePredicateImpl<>(wherePredicate2), new SQLKtWherePredicateImpl<>(wherePredicate3), new SQLKtWherePredicateImpl<>(wherePredicate4), new SQLKtWherePredicateImpl<>(wherePredicate5));
        });
        return getQueryable5();
    }

    default KtQueryable5<T1, T2, T3, T4, T5> where(boolean condition, SQLExpression5<SQLKtWherePredicate<T1>, SQLKtWherePredicate<T2>, SQLKtWherePredicate<T3>, SQLKtWherePredicate<T4>, SQLKtWherePredicate<T5>> whereExpression) {
        getClientQueryable5().where(condition, (wherePredicate1, wherePredicate2, wherePredicate3, wherePredicate4, wherePredicate5) -> {
            whereExpression.apply(new SQLKtWherePredicateImpl<>(wherePredicate1), new SQLKtWherePredicateImpl<>(wherePredicate2), new SQLKtWherePredicateImpl<>(wherePredicate3), new SQLKtWherePredicateImpl<>(wherePredicate4), new SQLKtWherePredicateImpl<>(wherePredicate5));
        });
        return getQueryable5();
    }

    default KtQueryable5<T1, T2, T3, T4, T5> whereMerge(SQLExpression1<Tuple5<SQLKtWherePredicate<T1>, SQLKtWherePredicate<T2>, SQLKtWherePredicate<T3>, SQLKtWherePredicate<T4>, SQLKtWherePredicate<T5>>> whereExpression) {
        return whereMerge(true, whereExpression);
    }

    default KtQueryable5<T1, T2, T3, T4, T5> whereMerge(boolean condition, SQLExpression1<Tuple5<SQLKtWherePredicate<T1>, SQLKtWherePredicate<T2>, SQLKtWherePredicate<T3>, SQLKtWherePredicate<T4>, SQLKtWherePredicate<T5>>> whereExpression) {
        return where(condition, (t1, t2, t3, t4, t5) -> {
            whereExpression.apply(new Tuple5<>(t1, t2, t3, t4, t5));
        });
    }
}
