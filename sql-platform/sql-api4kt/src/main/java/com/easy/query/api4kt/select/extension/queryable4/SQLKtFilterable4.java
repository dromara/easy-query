package com.easy.query.api4kt.select.extension.queryable4;

import com.easy.query.api4kt.select.KtQueryable4;
import com.easy.query.api4kt.sql.SQLKtWherePredicate;
import com.easy.query.api4kt.sql.impl.SQLKtWherePredicateImpl;
import com.easy.query.core.common.tuple.Tuple4;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.lambda.SQLExpression4;

/**
 * create time 2023/8/16 08:46
 * 文件说明
 *
 * @author xuejiaming
 */
public interface SQLKtFilterable4<T1, T2, T3, T4> extends ClientKtQueryable4Available<T1, T2, T3, T4>, KtQueryable4Available<T1, T2, T3, T4> {

    default KtQueryable4<T1, T2, T3, T4> where(SQLExpression4<SQLKtWherePredicate<T1>, SQLKtWherePredicate<T2>, SQLKtWherePredicate<T3>, SQLKtWherePredicate<T4>> whereExpression) {
        getClientQueryable4().where((wherePredicate1, wherePredicate2, wherePredicate3, wherePredicate4) -> {
            whereExpression.apply(new SQLKtWherePredicateImpl<>(wherePredicate1), new SQLKtWherePredicateImpl<>(wherePredicate2), new SQLKtWherePredicateImpl<>(wherePredicate3), new SQLKtWherePredicateImpl<>(wherePredicate4));
        });
        return getQueryable4();
    }

    default KtQueryable4<T1, T2, T3, T4> where(boolean condition, SQLExpression4<SQLKtWherePredicate<T1>, SQLKtWherePredicate<T2>, SQLKtWherePredicate<T3>, SQLKtWherePredicate<T4>> whereExpression) {
        getClientQueryable4().where(condition, (wherePredicate1, wherePredicate2, wherePredicate3, wherePredicate4) -> {
            whereExpression.apply(new SQLKtWherePredicateImpl<>(wherePredicate1), new SQLKtWherePredicateImpl<>(wherePredicate2), new SQLKtWherePredicateImpl<>(wherePredicate3), new SQLKtWherePredicateImpl<>(wherePredicate4));
        });
        return getQueryable4();
    }

    default KtQueryable4<T1, T2, T3, T4> whereMerge(SQLExpression1<Tuple4<SQLKtWherePredicate<T1>, SQLKtWherePredicate<T2>, SQLKtWherePredicate<T3>, SQLKtWherePredicate<T4>>> whereExpression) {
        return whereMerge(true, whereExpression);
    }

    default KtQueryable4<T1, T2, T3, T4> whereMerge(boolean condition, SQLExpression1<Tuple4<SQLKtWherePredicate<T1>, SQLKtWherePredicate<T2>, SQLKtWherePredicate<T3>, SQLKtWherePredicate<T4>>> whereExpression) {
        return where(condition, (t1, t2, t3, t4) -> {
            whereExpression.apply(new Tuple4<>(t1, t2, t3, t4));
        });
    }
}
