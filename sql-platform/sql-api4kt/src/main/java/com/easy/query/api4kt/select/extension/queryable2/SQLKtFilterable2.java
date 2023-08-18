package com.easy.query.api4kt.select.extension.queryable2;

import com.easy.query.api4kt.select.KtQueryable2;
import com.easy.query.api4kt.sql.SQLKtWherePredicate;
import com.easy.query.api4kt.sql.impl.SQLKtWherePredicateImpl;
import com.easy.query.core.common.tuple.Tuple2;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.lambda.SQLExpression2;

/**
 * create time 2023/8/16 08:46
 * 文件说明
 *
 * @author xuejiaming
 */
public interface SQLKtFilterable2<T1,T2> extends ClientKtQueryable2Available<T1,T2>, KtQueryable2Available<T1,T2> {

    default KtQueryable2<T1, T2> where(SQLExpression2<SQLKtWherePredicate<T1>, SQLKtWherePredicate<T2>> whereExpression) {
        getClientQueryable2().where((wherePredicate1, wherePredicate2) -> {
            whereExpression.apply(new SQLKtWherePredicateImpl<>(wherePredicate1), new SQLKtWherePredicateImpl<>(wherePredicate2));
        });
        return getQueryable2();
    }

    default KtQueryable2<T1, T2> where(boolean condition, SQLExpression2<SQLKtWherePredicate<T1>, SQLKtWherePredicate<T2>> whereExpression) {
        getClientQueryable2().where(condition, (wherePredicate1, wherePredicate2) -> {
            whereExpression.apply(new SQLKtWherePredicateImpl<>(wherePredicate1), new SQLKtWherePredicateImpl<>(wherePredicate2));
        });
        return getQueryable2();
    }

    default KtQueryable2<T1, T2> whereMerge(SQLExpression1<Tuple2<SQLKtWherePredicate<T1>, SQLKtWherePredicate<T2>>> whereExpression) {
        return whereMerge(true, whereExpression);
    }

    default KtQueryable2<T1, T2> whereMerge(boolean condition, SQLExpression1<Tuple2<SQLKtWherePredicate<T1>, SQLKtWherePredicate<T2>>> whereExpression) {
        return where(condition, (t1, t2) -> {
            whereExpression.apply(new Tuple2<>(t1, t2));
        });
    }
}
