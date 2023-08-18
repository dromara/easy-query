package com.easy.query.api4kt.select.extension.queryable7;

import com.easy.query.api4kt.select.KtQueryable;
import com.easy.query.api4kt.select.KtQueryable8;
import com.easy.query.api4kt.select.impl.EasyKtQueryable8;
import com.easy.query.api4kt.sql.SQLKtWherePredicate;
import com.easy.query.api4kt.sql.impl.SQLKtWherePredicateImpl;
import com.easy.query.core.basic.api.select.ClientQueryable8;
import com.easy.query.core.common.tuple.Tuple8;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.lambda.SQLExpression8;

/**
 * create time 2023/8/18 13:14
 * 文件说明
 *
 * @author xuejiaming
 */
public interface SQLKtJoinable7<T1, T2, T3, T4, T5, T6,T7> extends ClientKtQueryable7Available<T1, T2, T3, T4, T5, T6,T7> {

    default <T8> KtQueryable8<T1, T2, T3, T4, T5, T6, T7, T8> leftJoin(Class<T8> joinClass, SQLExpression8<SQLKtWherePredicate<T1>, SQLKtWherePredicate<T2>, SQLKtWherePredicate<T3>, SQLKtWherePredicate<T4>, SQLKtWherePredicate<T5>, SQLKtWherePredicate<T6>, SQLKtWherePredicate<T7>, SQLKtWherePredicate<T8>> on) {
        ClientQueryable8<T1, T2, T3, T4, T5, T6, T7, T8> entityQueryable7 = getClientQueryable7().leftJoin(joinClass, (where1, where2, where3, where4, where5, where6, where7, where8) -> {
            on.apply(new SQLKtWherePredicateImpl<>(where1), new SQLKtWherePredicateImpl<>(where2), new SQLKtWherePredicateImpl<>(where3), new SQLKtWherePredicateImpl<>(where4), new SQLKtWherePredicateImpl<>(where5), new SQLKtWherePredicateImpl<>(where6), new SQLKtWherePredicateImpl<>(where7), new SQLKtWherePredicateImpl<>(where8));
        });
        return new EasyKtQueryable8<>(entityQueryable7);
    }

    default <T8> KtQueryable8<T1, T2, T3, T4, T5, T6, T7, T8> leftJoin(KtQueryable<T8> joinQueryable, SQLExpression8<SQLKtWherePredicate<T1>, SQLKtWherePredicate<T2>, SQLKtWherePredicate<T3>, SQLKtWherePredicate<T4>, SQLKtWherePredicate<T5>, SQLKtWherePredicate<T6>, SQLKtWherePredicate<T7>, SQLKtWherePredicate<T8>> on) {
        ClientQueryable8<T1, T2, T3, T4, T5, T6, T7, T8> entityQueryable7 = getClientQueryable7().leftJoin(joinQueryable.getClientQueryable(), (where1, where2, where3, where4, where5, where6, where7, where8) -> {
            on.apply(new SQLKtWherePredicateImpl<>(where1), new SQLKtWherePredicateImpl<>(where2), new SQLKtWherePredicateImpl<>(where3), new SQLKtWherePredicateImpl<>(where4), new SQLKtWherePredicateImpl<>(where5), new SQLKtWherePredicateImpl<>(where6), new SQLKtWherePredicateImpl<>(where7), new SQLKtWherePredicateImpl<>(where8));
        });
        return new EasyKtQueryable8<>(entityQueryable7);
    }
    default <T8> KtQueryable8<T1, T2, T3, T4, T5, T6, T7, T8> rightJoin(Class<T8> joinClass, SQLExpression8<SQLKtWherePredicate<T1>, SQLKtWherePredicate<T2>, SQLKtWherePredicate<T3>, SQLKtWherePredicate<T4>, SQLKtWherePredicate<T5>, SQLKtWherePredicate<T6>, SQLKtWherePredicate<T7>, SQLKtWherePredicate<T8>> on) {
        ClientQueryable8<T1, T2, T3, T4, T5, T6, T7, T8> entityQueryable7 = getClientQueryable7().rightJoin(joinClass, (where1, where2, where3, where4, where5, where6, where7, where8) -> {
            on.apply(new SQLKtWherePredicateImpl<>(where1), new SQLKtWherePredicateImpl<>(where2), new SQLKtWherePredicateImpl<>(where3), new SQLKtWherePredicateImpl<>(where4), new SQLKtWherePredicateImpl<>(where5), new SQLKtWherePredicateImpl<>(where6), new SQLKtWherePredicateImpl<>(where7), new SQLKtWherePredicateImpl<>(where8));
        });
        return new EasyKtQueryable8<>(entityQueryable7);
    }

    default <T8> KtQueryable8<T1, T2, T3, T4, T5, T6, T7, T8> rightJoin(KtQueryable<T8> joinQueryable, SQLExpression8<SQLKtWherePredicate<T1>, SQLKtWherePredicate<T2>, SQLKtWherePredicate<T3>, SQLKtWherePredicate<T4>, SQLKtWherePredicate<T5>, SQLKtWherePredicate<T6>, SQLKtWherePredicate<T7>, SQLKtWherePredicate<T8>> on) {
        ClientQueryable8<T1, T2, T3, T4, T5, T6, T7, T8> entityQueryable7 = getClientQueryable7().rightJoin(joinQueryable.getClientQueryable(), (where1, where2, where3, where4, where5, where6, where7, where8) -> {
            on.apply(new SQLKtWherePredicateImpl<>(where1), new SQLKtWherePredicateImpl<>(where2), new SQLKtWherePredicateImpl<>(where3), new SQLKtWherePredicateImpl<>(where4), new SQLKtWherePredicateImpl<>(where5), new SQLKtWherePredicateImpl<>(where6), new SQLKtWherePredicateImpl<>(where7), new SQLKtWherePredicateImpl<>(where8));
        });
        return new EasyKtQueryable8<>(entityQueryable7);
    }

    default <T8> KtQueryable8<T1, T2, T3, T4, T5, T6, T7, T8> innerJoin(Class<T8> joinClass, SQLExpression8<SQLKtWherePredicate<T1>, SQLKtWherePredicate<T2>, SQLKtWherePredicate<T3>, SQLKtWherePredicate<T4>, SQLKtWherePredicate<T5>, SQLKtWherePredicate<T6>, SQLKtWherePredicate<T7>, SQLKtWherePredicate<T8>> on) {
        ClientQueryable8<T1, T2, T3, T4, T5, T6, T7, T8> entityQueryable7 = getClientQueryable7().innerJoin(joinClass, (where1, where2, where3, where4, where5, where6, where7, where8) -> {
            on.apply(new SQLKtWherePredicateImpl<>(where1), new SQLKtWherePredicateImpl<>(where2), new SQLKtWherePredicateImpl<>(where3), new SQLKtWherePredicateImpl<>(where4), new SQLKtWherePredicateImpl<>(where5), new SQLKtWherePredicateImpl<>(where6), new SQLKtWherePredicateImpl<>(where7), new SQLKtWherePredicateImpl<>(where8));
        });
        return new EasyKtQueryable8<>(entityQueryable7);
    }

    default <T8> KtQueryable8<T1, T2, T3, T4, T5, T6, T7, T8> innerJoin(KtQueryable<T8> joinQueryable, SQLExpression8<SQLKtWherePredicate<T1>, SQLKtWherePredicate<T2>, SQLKtWherePredicate<T3>, SQLKtWherePredicate<T4>, SQLKtWherePredicate<T5>, SQLKtWherePredicate<T6>, SQLKtWherePredicate<T7>, SQLKtWherePredicate<T8>> on) {
        ClientQueryable8<T1, T2, T3, T4, T5, T6, T7, T8> entityQueryable7 = getClientQueryable7().innerJoin(joinQueryable.getClientQueryable(), (where1, where2, where3, where4, where5, where6, where7, where8) -> {
            on.apply(new SQLKtWherePredicateImpl<>(where1), new SQLKtWherePredicateImpl<>(where2), new SQLKtWherePredicateImpl<>(where3), new SQLKtWherePredicateImpl<>(where4), new SQLKtWherePredicateImpl<>(where5), new SQLKtWherePredicateImpl<>(where6), new SQLKtWherePredicateImpl<>(where7), new SQLKtWherePredicateImpl<>(where8));
        });
        return new EasyKtQueryable8<>(entityQueryable7);
    }

    default <T8> KtQueryable8<T1, T2, T3, T4, T5, T6, T7, T8> leftJoinMerge(Class<T8> joinClass, SQLExpression1<Tuple8<SQLKtWherePredicate<T1>, SQLKtWherePredicate<T2>, SQLKtWherePredicate<T3>, SQLKtWherePredicate<T4>, SQLKtWherePredicate<T5>, SQLKtWherePredicate<T6>, SQLKtWherePredicate<T7>, SQLKtWherePredicate<T8>>> on) {
        return leftJoin(joinClass, (t1, t2, t3, t4, t5, t6, t7, t8) -> {
            on.apply(new Tuple8<>(t1, t2, t3, t4, t5, t6, t7, t8));
        });
    }

    default <T8> KtQueryable8<T1, T2, T3, T4, T5, T6, T7, T8> leftJoinMerge(KtQueryable<T8> joinQueryable, SQLExpression1<Tuple8<SQLKtWherePredicate<T1>, SQLKtWherePredicate<T2>, SQLKtWherePredicate<T3>, SQLKtWherePredicate<T4>, SQLKtWherePredicate<T5>, SQLKtWherePredicate<T6>, SQLKtWherePredicate<T7>, SQLKtWherePredicate<T8>>> on) {
        return leftJoin(joinQueryable, (t1, t2, t3, t4, t5, t6, t7, t8) -> {
            on.apply(new Tuple8<>(t1, t2, t3, t4, t5, t6, t7, t8));
        });
    }

    default <T8> KtQueryable8<T1, T2, T3, T4, T5, T6, T7, T8> rightJoinMerge(Class<T8> joinClass, SQLExpression1<Tuple8<SQLKtWherePredicate<T1>, SQLKtWherePredicate<T2>, SQLKtWherePredicate<T3>, SQLKtWherePredicate<T4>, SQLKtWherePredicate<T5>, SQLKtWherePredicate<T6>, SQLKtWherePredicate<T7>, SQLKtWherePredicate<T8>>> on) {
        return rightJoin(joinClass, (t1, t2, t3, t4, t5, t6, t7, t8) -> {
            on.apply(new Tuple8<>(t1, t2, t3, t4, t5, t6, t7, t8));
        });
    }

    default <T8> KtQueryable8<T1, T2, T3, T4, T5, T6, T7, T8> rightJoinMerge(KtQueryable<T8> joinQueryable, SQLExpression1<Tuple8<SQLKtWherePredicate<T1>, SQLKtWherePredicate<T2>, SQLKtWherePredicate<T3>, SQLKtWherePredicate<T4>, SQLKtWherePredicate<T5>, SQLKtWherePredicate<T6>, SQLKtWherePredicate<T7>, SQLKtWherePredicate<T8>>> on) {
        return rightJoin(joinQueryable, (t1, t2, t3, t4, t5, t6, t7, t8) -> {
            on.apply(new Tuple8<>(t1, t2, t3, t4, t5, t6, t7, t8));
        });
    }

    default <T8> KtQueryable8<T1, T2, T3, T4, T5, T6, T7, T8> innerJoinMerge(Class<T8> joinClass, SQLExpression1<Tuple8<SQLKtWherePredicate<T1>, SQLKtWherePredicate<T2>, SQLKtWherePredicate<T3>, SQLKtWherePredicate<T4>, SQLKtWherePredicate<T5>, SQLKtWherePredicate<T6>, SQLKtWherePredicate<T7>, SQLKtWherePredicate<T8>>> on) {
        return innerJoin(joinClass, (t1, t2, t3, t4, t5, t6, t7, t8) -> {
            on.apply(new Tuple8<>(t1, t2, t3, t4, t5, t6, t7, t8));
        });
    }

    default <T8> KtQueryable8<T1, T2, T3, T4, T5, T6, T7, T8> innerJoinMerge(KtQueryable<T8> joinQueryable, SQLExpression1<Tuple8<SQLKtWherePredicate<T1>, SQLKtWherePredicate<T2>, SQLKtWherePredicate<T3>, SQLKtWherePredicate<T4>, SQLKtWherePredicate<T5>, SQLKtWherePredicate<T6>, SQLKtWherePredicate<T7>, SQLKtWherePredicate<T8>>> on) {
        return innerJoin(joinQueryable, (t1, t2, t3, t4, t5, t6, t7, t8) -> {
            on.apply(new Tuple8<>(t1, t2, t3, t4, t5, t6, t7, t8));
        });
    }
}