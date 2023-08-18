package com.easy.query.api4kt.select.extension.queryable7;

import com.easy.query.api4kt.select.KtQueryable7;
import com.easy.query.api4kt.sql.SQLKtWherePredicate;
import com.easy.query.api4kt.sql.impl.SQLKtWherePredicateImpl;
import com.easy.query.core.common.tuple.Tuple7;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.lambda.SQLExpression7;

/**
 * create time 2023/8/18 09:26
 * 文件说明
 *
 * @author xuejiaming
 */
public interface SQLKtFilterable7<T1, T2, T3, T4, T5, T6, T7> extends ClientKtQueryable7Available<T1, T2, T3, T4, T5, T6, T7>, KtQueryable7Available<T1, T2, T3, T4, T5, T6, T7> {

    default KtQueryable7<T1, T2, T3, T4, T5, T6, T7> where(SQLExpression7<SQLKtWherePredicate<T1>, SQLKtWherePredicate<T2>, SQLKtWherePredicate<T3>, SQLKtWherePredicate<T4>, SQLKtWherePredicate<T5>, SQLKtWherePredicate<T6>, SQLKtWherePredicate<T7>> whereExpression) {
        getClientQueryable7().where((wherePredicate1, wherePredicate2, wherePredicate3, wherePredicate4, wherePredicate5, wherePredicate6, wherePredicate7) -> {
            whereExpression.apply(new SQLKtWherePredicateImpl<>(wherePredicate1), new SQLKtWherePredicateImpl<>(wherePredicate2), new SQLKtWherePredicateImpl<>(wherePredicate3), new SQLKtWherePredicateImpl<>(wherePredicate4), new SQLKtWherePredicateImpl<>(wherePredicate5), new SQLKtWherePredicateImpl<>(wherePredicate6), new SQLKtWherePredicateImpl<>(wherePredicate7));
        });
        return getQueryable7();
    }

    default KtQueryable7<T1, T2, T3, T4, T5, T6, T7> where(boolean condition, SQLExpression7<SQLKtWherePredicate<T1>, SQLKtWherePredicate<T2>, SQLKtWherePredicate<T3>, SQLKtWherePredicate<T4>, SQLKtWherePredicate<T5>, SQLKtWherePredicate<T6>, SQLKtWherePredicate<T7>> whereExpression) {
        getClientQueryable7().where(condition, (wherePredicate1, wherePredicate2, wherePredicate3, wherePredicate4, wherePredicate5, wherePredicate6, wherePredicate7) -> {
            whereExpression.apply(new SQLKtWherePredicateImpl<>(wherePredicate1), new SQLKtWherePredicateImpl<>(wherePredicate2), new SQLKtWherePredicateImpl<>(wherePredicate3), new SQLKtWherePredicateImpl<>(wherePredicate4), new SQLKtWherePredicateImpl<>(wherePredicate5), new SQLKtWherePredicateImpl<>(wherePredicate6), new SQLKtWherePredicateImpl<>(wherePredicate7));
        });
        return getQueryable7();
    }

    default KtQueryable7<T1, T2, T3, T4, T5, T6, T7> whereMerge(SQLExpression1<Tuple7<SQLKtWherePredicate<T1>, SQLKtWherePredicate<T2>, SQLKtWherePredicate<T3>, SQLKtWherePredicate<T4>, SQLKtWherePredicate<T5>, SQLKtWherePredicate<T6>, SQLKtWherePredicate<T7>>> whereExpression) {
        return whereMerge(true, whereExpression);
    }

    default KtQueryable7<T1, T2, T3, T4, T5, T6, T7> whereMerge(boolean condition, SQLExpression1<Tuple7<SQLKtWherePredicate<T1>, SQLKtWherePredicate<T2>, SQLKtWherePredicate<T3>, SQLKtWherePredicate<T4>, SQLKtWherePredicate<T5>, SQLKtWherePredicate<T6>, SQLKtWherePredicate<T7>>> whereExpression) {
        return where(condition, (t1, t2,  t3, t4, t5, t6, t7) -> {
            whereExpression.apply(new Tuple7<>(t1, t2,  t3, t4, t5, t6, t7));
        });
    }
}
