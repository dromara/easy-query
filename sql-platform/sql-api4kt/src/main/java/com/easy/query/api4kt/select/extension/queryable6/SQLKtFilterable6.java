package com.easy.query.api4kt.select.extension.queryable6;

import com.easy.query.api4kt.select.KtQueryable6;
import com.easy.query.api4kt.sql.SQLKtWherePredicate;
import com.easy.query.api4kt.sql.impl.SQLKtWherePredicateImpl;
import com.easy.query.core.common.tuple.Tuple6;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.lambda.SQLExpression6;

/**
 * create time 2023/8/18 09:25
 * 文件说明
 *
 * @author xuejiaming
 */
public interface SQLKtFilterable6<T1, T2, T3, T4, T5, T6> extends ClientKtQueryable6Available<T1, T2, T3, T4, T5, T6>, KtQueryable6Available<T1, T2, T3, T4, T5, T6> {

    default KtQueryable6<T1, T2, T3, T4, T5, T6> where(SQLExpression6<SQLKtWherePredicate<T1>, SQLKtWherePredicate<T2>, SQLKtWherePredicate<T3>, SQLKtWherePredicate<T4>, SQLKtWherePredicate<T5>, SQLKtWherePredicate<T6>> whereExpression) {
        getClientQueryable6().where((wherePredicate1, wherePredicate2, wherePredicate3, wherePredicate4, wherePredicate5, wherePredicate6) -> {
            whereExpression.apply(new SQLKtWherePredicateImpl<>(wherePredicate1), new SQLKtWherePredicateImpl<>(wherePredicate2), new SQLKtWherePredicateImpl<>(wherePredicate3), new SQLKtWherePredicateImpl<>(wherePredicate4), new SQLKtWherePredicateImpl<>(wherePredicate5), new SQLKtWherePredicateImpl<>(wherePredicate6));
        });
        return getQueryable6();
    }

    default KtQueryable6<T1, T2, T3, T4, T5, T6> where(boolean condition, SQLExpression6<SQLKtWherePredicate<T1>, SQLKtWherePredicate<T2>, SQLKtWherePredicate<T3>, SQLKtWherePredicate<T4>, SQLKtWherePredicate<T5>, SQLKtWherePredicate<T6>> whereExpression) {
        getClientQueryable6().where(condition, (wherePredicate1, wherePredicate2, wherePredicate3, wherePredicate4, wherePredicate5, wherePredicate6) -> {
            whereExpression.apply(new SQLKtWherePredicateImpl<>(wherePredicate1), new SQLKtWherePredicateImpl<>(wherePredicate2), new SQLKtWherePredicateImpl<>(wherePredicate3), new SQLKtWherePredicateImpl<>(wherePredicate4), new SQLKtWherePredicateImpl<>(wherePredicate5), new SQLKtWherePredicateImpl<>(wherePredicate6));
        });
        return getQueryable6();
    }

    default KtQueryable6<T1, T2, T3, T4, T5, T6> whereMerge(SQLExpression1<Tuple6<SQLKtWherePredicate<T1>, SQLKtWherePredicate<T2>, SQLKtWherePredicate<T3>, SQLKtWherePredicate<T4>, SQLKtWherePredicate<T5>, SQLKtWherePredicate<T6>>> whereExpression) {
        return whereMerge(true, whereExpression);
    }

    default KtQueryable6<T1, T2, T3, T4, T5, T6> whereMerge(boolean condition, SQLExpression1<Tuple6<SQLKtWherePredicate<T1>, SQLKtWherePredicate<T2>, SQLKtWherePredicate<T3>, SQLKtWherePredicate<T4>, SQLKtWherePredicate<T5>, SQLKtWherePredicate<T6>>> whereExpression) {
        return where(condition, (t1, t2, t3, t4, t5, t6) -> {
            whereExpression.apply(new Tuple6<>(t1, t2, t3, t4, t5, t6));
        });
    }
}