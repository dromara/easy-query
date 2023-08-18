package com.easy.query.api4kt.select.extension.queryable3;

import com.easy.query.api4kt.select.KtQueryable3;
import com.easy.query.api4kt.sql.SQLKtWherePredicate;
import com.easy.query.api4kt.sql.impl.SQLKtWherePredicateImpl;
import com.easy.query.core.common.tuple.Tuple3;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.lambda.SQLExpression3;

/**
 * create time 2023/8/16 08:46
 * 文件说明
 *
 * @author xuejiaming
 */
public interface SQLKtFilterable3<T1,T2,T3> extends ClientKtQueryable3Available<T1,T2,T3>, KtQueryable3Available<T1,T2,T3> {

    default KtQueryable3<T1, T2,T3> where(SQLExpression3<SQLKtWherePredicate<T1>, SQLKtWherePredicate<T2>, SQLKtWherePredicate<T3>> whereExpression) {
        getClientQueryable3().where((wherePredicate1, wherePredicate2, wherePredicate3) -> {
            whereExpression.apply(new SQLKtWherePredicateImpl<>(wherePredicate1), new SQLKtWherePredicateImpl<>(wherePredicate2), new SQLKtWherePredicateImpl<>(wherePredicate3));
        });
        return getQueryable3();
    }

    default KtQueryable3<T1, T2,T3> where(boolean condition, SQLExpression3<SQLKtWherePredicate<T1>, SQLKtWherePredicate<T2>, SQLKtWherePredicate<T3>> whereExpression) {
        getClientQueryable3().where(condition, (wherePredicate1, wherePredicate2, wherePredicate3) -> {
            whereExpression.apply(new SQLKtWherePredicateImpl<>(wherePredicate1), new SQLKtWherePredicateImpl<>(wherePredicate2), new SQLKtWherePredicateImpl<>(wherePredicate3));
        });
        return getQueryable3();
    }

    default KtQueryable3<T1, T2,T3> whereMerge(SQLExpression1<Tuple3<SQLKtWherePredicate<T1>, SQLKtWherePredicate<T2>, SQLKtWherePredicate<T3>>> whereExpression) {
        return whereMerge(true, whereExpression);
    }

    default KtQueryable3<T1, T2,T3> whereMerge(boolean condition, SQLExpression1<Tuple3<SQLKtWherePredicate<T1>, SQLKtWherePredicate<T2>, SQLKtWherePredicate<T3>>> whereExpression) {
        return where(condition, (t1,t2,t3) -> {
            whereExpression.apply(new Tuple3<>(t1,t2,t3));
        });
    }
}
