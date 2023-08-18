package com.easy.query.api4kt.select.extension.queryable5;

import com.easy.query.api4kt.select.KtQueryable;
import com.easy.query.api4kt.select.KtQueryable6;
import com.easy.query.api4kt.select.impl.EasyKtQueryable6;
import com.easy.query.api4kt.sql.SQLKtWherePredicate;
import com.easy.query.api4kt.sql.impl.SQLKtWherePredicateImpl;
import com.easy.query.core.basic.api.select.ClientQueryable6;
import com.easy.query.core.common.tuple.Tuple6;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.lambda.SQLExpression6;

/**
 * create time 2023/8/18 13:13
 * 文件说明
 *
 * @author xuejiaming
 */
public interface SQLKtJoinable5<T1, T2, T3, T4, T5> extends ClientKtQueryable5Available<T1, T2, T3, T4, T5> {

    default <T6> KtQueryable6<T1, T2, T3, T4, T5, T6> leftJoin(Class<T6> joinClass, SQLExpression6<SQLKtWherePredicate<T1>, SQLKtWherePredicate<T2>, SQLKtWherePredicate<T3>, SQLKtWherePredicate<T4>, SQLKtWherePredicate<T5>, SQLKtWherePredicate<T6>> on) {
        ClientQueryable6<T1, T2, T3, T4, T5, T6> entityQueryable6 = getClientQueryable5().leftJoin(joinClass, (where1, where2, where3, where4, where5, where6) -> {
            on.apply(new SQLKtWherePredicateImpl<>(where1), new SQLKtWherePredicateImpl<>(where2), new SQLKtWherePredicateImpl<>(where3), new SQLKtWherePredicateImpl<>(where4), new SQLKtWherePredicateImpl<>(where5), new SQLKtWherePredicateImpl<>(where6));
        });
        return new EasyKtQueryable6<>(entityQueryable6);
    }

    default <T6> KtQueryable6<T1, T2, T3, T4, T5, T6> leftJoin(KtQueryable<T6> joinQueryable, SQLExpression6<SQLKtWherePredicate<T1>, SQLKtWherePredicate<T2>, SQLKtWherePredicate<T3>, SQLKtWherePredicate<T4>, SQLKtWherePredicate<T5>, SQLKtWherePredicate<T6>> on) {
        ClientQueryable6<T1, T2, T3, T4, T5, T6> entityQueryable6 = getClientQueryable5().leftJoin(joinQueryable.getClientQueryable(), (where1, where2, where3, where4, where5, where6) -> {
            on.apply(new SQLKtWherePredicateImpl<>(where1), new SQLKtWherePredicateImpl<>(where2), new SQLKtWherePredicateImpl<>(where3), new SQLKtWherePredicateImpl<>(where4), new SQLKtWherePredicateImpl<>(where5), new SQLKtWherePredicateImpl<>(where6));
        });
        return new EasyKtQueryable6<>(entityQueryable6);
    }

    default <T6> KtQueryable6<T1, T2, T3, T4, T5, T6> rightJoin(Class<T6> joinClass, SQLExpression6<SQLKtWherePredicate<T1>, SQLKtWherePredicate<T2>, SQLKtWherePredicate<T3>, SQLKtWherePredicate<T4>, SQLKtWherePredicate<T5>, SQLKtWherePredicate<T6>> on) {
        ClientQueryable6<T1, T2, T3, T4, T5, T6> entityQueryable6 = getClientQueryable5().rightJoin(joinClass, (where1, where2, where3, where4, where5, where6) -> {
            on.apply(new SQLKtWherePredicateImpl<>(where1), new SQLKtWherePredicateImpl<>(where2), new SQLKtWherePredicateImpl<>(where3), new SQLKtWherePredicateImpl<>(where4), new SQLKtWherePredicateImpl<>(where5), new SQLKtWherePredicateImpl<>(where6));
        });
        return new EasyKtQueryable6<>(entityQueryable6);
    }

    default <T6> KtQueryable6<T1, T2, T3, T4, T5, T6> rightJoin(KtQueryable<T6> joinQueryable, SQLExpression6<SQLKtWherePredicate<T1>, SQLKtWherePredicate<T2>, SQLKtWherePredicate<T3>, SQLKtWherePredicate<T4>, SQLKtWherePredicate<T5>, SQLKtWherePredicate<T6>> on) {
        ClientQueryable6<T1, T2, T3, T4, T5, T6> entityQueryable6 = getClientQueryable5().rightJoin(joinQueryable.getClientQueryable(), (where1, where2, where3, where4, where5, where6) -> {
            on.apply(new SQLKtWherePredicateImpl<>(where1), new SQLKtWherePredicateImpl<>(where2), new SQLKtWherePredicateImpl<>(where3), new SQLKtWherePredicateImpl<>(where4), new SQLKtWherePredicateImpl<>(where5), new SQLKtWherePredicateImpl<>(where6));
        });
        return new EasyKtQueryable6<>(entityQueryable6);
    }

    default <T6> KtQueryable6<T1, T2, T3, T4, T5, T6> innerJoin(Class<T6> joinClass, SQLExpression6<SQLKtWherePredicate<T1>, SQLKtWherePredicate<T2>, SQLKtWherePredicate<T3>, SQLKtWherePredicate<T4>, SQLKtWherePredicate<T5>, SQLKtWherePredicate<T6>> on) {
        ClientQueryable6<T1, T2, T3, T4, T5, T6> entityQueryable6 = getClientQueryable5().innerJoin(joinClass, (where1, where2, where3, where4, where5, where6) -> {
            on.apply(new SQLKtWherePredicateImpl<>(where1), new SQLKtWherePredicateImpl<>(where2), new SQLKtWherePredicateImpl<>(where3), new SQLKtWherePredicateImpl<>(where4), new SQLKtWherePredicateImpl<>(where5), new SQLKtWherePredicateImpl<>(where6));
        });
        return new EasyKtQueryable6<>(entityQueryable6);
    }

    default <T6> KtQueryable6<T1, T2, T3, T4, T5, T6> innerJoin(KtQueryable<T6> joinQueryable, SQLExpression6<SQLKtWherePredicate<T1>, SQLKtWherePredicate<T2>, SQLKtWherePredicate<T3>, SQLKtWherePredicate<T4>, SQLKtWherePredicate<T5>, SQLKtWherePredicate<T6>> on) {
        ClientQueryable6<T1, T2, T3, T4, T5, T6> entityQueryable6 = getClientQueryable5().innerJoin(joinQueryable.getClientQueryable(), (where1, where2, where3, where4, where5, where6) -> {
            on.apply(new SQLKtWherePredicateImpl<>(where1), new SQLKtWherePredicateImpl<>(where2), new SQLKtWherePredicateImpl<>(where3), new SQLKtWherePredicateImpl<>(where4), new SQLKtWherePredicateImpl<>(where5), new SQLKtWherePredicateImpl<>(where6));
        });
        return new EasyKtQueryable6<>(entityQueryable6);
    }

    default <T6> KtQueryable6<T1, T2, T3, T4, T5, T6> leftJoinMerge(Class<T6> joinClass, SQLExpression1<Tuple6<SQLKtWherePredicate<T1>, SQLKtWherePredicate<T2>, SQLKtWherePredicate<T3>, SQLKtWherePredicate<T4>, SQLKtWherePredicate<T5>, SQLKtWherePredicate<T6>>> on) {
        return leftJoin(joinClass, (t1, t2, t3, t4, t5, t6) -> {
            on.apply(new Tuple6<>(t1, t2, t3, t4, t5, t6));
        });
    }

    default <T6> KtQueryable6<T1, T2, T3, T4, T5, T6> leftJoinMerge(KtQueryable<T6> joinQueryable, SQLExpression1<Tuple6<SQLKtWherePredicate<T1>, SQLKtWherePredicate<T2>, SQLKtWherePredicate<T3>, SQLKtWherePredicate<T4>, SQLKtWherePredicate<T5>, SQLKtWherePredicate<T6>>> on) {
        return leftJoin(joinQueryable, (t1, t2, t3, t4, t5, t6) -> {
            on.apply(new Tuple6<>(t1, t2, t3, t4, t5, t6));
        });
    }

    default <T6> KtQueryable6<T1, T2, T3, T4, T5, T6> rightJoinMerge(Class<T6> joinClass, SQLExpression1<Tuple6<SQLKtWherePredicate<T1>, SQLKtWherePredicate<T2>, SQLKtWherePredicate<T3>, SQLKtWherePredicate<T4>, SQLKtWherePredicate<T5>, SQLKtWherePredicate<T6>>> on) {
        return rightJoin(joinClass, (t1, t2, t3, t4, t5, t6) -> {
            on.apply(new Tuple6<>(t1, t2, t3, t4, t5, t6));
        });
    }

    default <T6> KtQueryable6<T1, T2, T3, T4, T5, T6> rightJoinMerge(KtQueryable<T6> joinQueryable, SQLExpression1<Tuple6<SQLKtWherePredicate<T1>, SQLKtWherePredicate<T2>, SQLKtWherePredicate<T3>, SQLKtWherePredicate<T4>, SQLKtWherePredicate<T5>, SQLKtWherePredicate<T6>>> on) {
        return rightJoin(joinQueryable, (t1, t2, t3, t4, t5, t6) -> {
            on.apply(new Tuple6<>(t1, t2, t3, t4, t5, t6));
        });
    }

    default <T6> KtQueryable6<T1, T2, T3, T4, T5, T6> innerJoinMerge(Class<T6> joinClass, SQLExpression1<Tuple6<SQLKtWherePredicate<T1>, SQLKtWherePredicate<T2>, SQLKtWherePredicate<T3>, SQLKtWherePredicate<T4>, SQLKtWherePredicate<T5>, SQLKtWherePredicate<T6>>> on) {
        return innerJoin(joinClass, (t1, t2, t3, t4, t5, t6) -> {
            on.apply(new Tuple6<>(t1, t2, t3, t4, t5, t6));
        });
    }

    default <T6> KtQueryable6<T1, T2, T3, T4, T5, T6> innerJoinMerge(KtQueryable<T6> joinQueryable, SQLExpression1<Tuple6<SQLKtWherePredicate<T1>, SQLKtWherePredicate<T2>, SQLKtWherePredicate<T3>, SQLKtWherePredicate<T4>, SQLKtWherePredicate<T5>, SQLKtWherePredicate<T6>>> on) {
        return innerJoin(joinQueryable, (t1, t2, t3, t4, t5, t6) -> {
            on.apply(new Tuple6<>(t1, t2, t3, t4, t5, t6));
        });
    }
}

