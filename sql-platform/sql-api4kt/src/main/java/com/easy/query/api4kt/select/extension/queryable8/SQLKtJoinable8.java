package com.easy.query.api4kt.select.extension.queryable8;

import com.easy.query.api4kt.select.KtQueryable;
import com.easy.query.api4kt.select.KtQueryable9;
import com.easy.query.api4kt.select.impl.EasyKtQueryable9;
import com.easy.query.api4kt.sql.SQLKtWherePredicate;
import com.easy.query.api4kt.sql.impl.SQLKtWherePredicateImpl;
import com.easy.query.core.basic.api.select.ClientQueryable9;
import com.easy.query.core.common.tuple.Tuple9;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.lambda.SQLExpression9;

/**
 * create time 2023/8/18 13:14
 * 文件说明
 *
 * @author xuejiaming
 */
public interface SQLKtJoinable8<T1, T2, T3, T4, T5, T6,T7,T8> extends ClientKtQueryable8Available<T1, T2, T3, T4, T5, T6,T7,T8> {

    default <T9> KtQueryable9<T1, T2, T3, T4, T5, T6, T7, T8, T9> leftJoin(Class<T9> joinClass, SQLExpression9<SQLKtWherePredicate<T1>, SQLKtWherePredicate<T2>, SQLKtWherePredicate<T3>, SQLKtWherePredicate<T4>, SQLKtWherePredicate<T5>, SQLKtWherePredicate<T6>, SQLKtWherePredicate<T7>, SQLKtWherePredicate<T8>, SQLKtWherePredicate<T9>> on) {
        ClientQueryable9<T1, T2, T3, T4, T5, T6, T7, T8, T9> entityQueryable7 = getClientQueryable8().leftJoin(joinClass, (where1, where2, where3, where4, where5, where6, where7, where8, where9) -> {
            on.apply(new SQLKtWherePredicateImpl<>(where1), new SQLKtWherePredicateImpl<>(where2), new SQLKtWherePredicateImpl<>(where3), new SQLKtWherePredicateImpl<>(where4), new SQLKtWherePredicateImpl<>(where5), new SQLKtWherePredicateImpl<>(where6), new SQLKtWherePredicateImpl<>(where7), new SQLKtWherePredicateImpl<>(where8), new SQLKtWherePredicateImpl<>(where9));
        });
        return new EasyKtQueryable9<>(entityQueryable7);
    }

    default <T9> KtQueryable9<T1, T2, T3, T4, T5, T6, T7, T8, T9> leftJoin(KtQueryable<T9> joinQueryable, SQLExpression9<SQLKtWherePredicate<T1>, SQLKtWherePredicate<T2>, SQLKtWherePredicate<T3>, SQLKtWherePredicate<T4>, SQLKtWherePredicate<T5>, SQLKtWherePredicate<T6>, SQLKtWherePredicate<T7>, SQLKtWherePredicate<T8>, SQLKtWherePredicate<T9>> on) {
        ClientQueryable9<T1, T2, T3, T4, T5, T6, T7, T8, T9> entityQueryable7 = getClientQueryable8().leftJoin(joinQueryable.getClientQueryable(), (where1, where2, where3, where4, where5, where6, where7, where8, where9) -> {
            on.apply(new SQLKtWherePredicateImpl<>(where1), new SQLKtWherePredicateImpl<>(where2), new SQLKtWherePredicateImpl<>(where3), new SQLKtWherePredicateImpl<>(where4), new SQLKtWherePredicateImpl<>(where5), new SQLKtWherePredicateImpl<>(where6), new SQLKtWherePredicateImpl<>(where7), new SQLKtWherePredicateImpl<>(where8), new SQLKtWherePredicateImpl<>(where9));
        });
        return new EasyKtQueryable9<>(entityQueryable7);
    }
    default <T9> KtQueryable9<T1, T2, T3, T4, T5, T6, T7, T8, T9> rightJoin(Class<T9> joinClass, SQLExpression9<SQLKtWherePredicate<T1>, SQLKtWherePredicate<T2>, SQLKtWherePredicate<T3>, SQLKtWherePredicate<T4>, SQLKtWherePredicate<T5>, SQLKtWherePredicate<T6>, SQLKtWherePredicate<T7>, SQLKtWherePredicate<T8>, SQLKtWherePredicate<T9>> on) {
        ClientQueryable9<T1, T2, T3, T4, T5, T6, T7, T8, T9> entityQueryable7 = getClientQueryable8().rightJoin(joinClass, (where1, where2, where3, where4, where5, where6, where7, where8, where9) -> {
            on.apply(new SQLKtWherePredicateImpl<>(where1), new SQLKtWherePredicateImpl<>(where2), new SQLKtWherePredicateImpl<>(where3), new SQLKtWherePredicateImpl<>(where4), new SQLKtWherePredicateImpl<>(where5), new SQLKtWherePredicateImpl<>(where6), new SQLKtWherePredicateImpl<>(where7), new SQLKtWherePredicateImpl<>(where8), new SQLKtWherePredicateImpl<>(where9));
        });
        return new EasyKtQueryable9<>(entityQueryable7);
    }

    default <T9> KtQueryable9<T1, T2, T3, T4, T5, T6, T7, T8, T9> rightJoin(KtQueryable<T9> joinQueryable, SQLExpression9<SQLKtWherePredicate<T1>, SQLKtWherePredicate<T2>, SQLKtWherePredicate<T3>, SQLKtWherePredicate<T4>, SQLKtWherePredicate<T5>, SQLKtWherePredicate<T6>, SQLKtWherePredicate<T7>, SQLKtWherePredicate<T8>, SQLKtWherePredicate<T9>> on) {
        ClientQueryable9<T1, T2, T3, T4, T5, T6, T7, T8, T9> entityQueryable7 = getClientQueryable8().rightJoin(joinQueryable.getClientQueryable(), (where1, where2, where3, where4, where5, where6, where7, where8, where9) -> {
            on.apply(new SQLKtWherePredicateImpl<>(where1), new SQLKtWherePredicateImpl<>(where2), new SQLKtWherePredicateImpl<>(where3), new SQLKtWherePredicateImpl<>(where4), new SQLKtWherePredicateImpl<>(where5), new SQLKtWherePredicateImpl<>(where6), new SQLKtWherePredicateImpl<>(where7), new SQLKtWherePredicateImpl<>(where8), new SQLKtWherePredicateImpl<>(where9));
        });
        return new EasyKtQueryable9<>(entityQueryable7);
    }

    default <T9> KtQueryable9<T1, T2, T3, T4, T5, T6, T7, T8, T9> innerJoin(Class<T9> joinClass, SQLExpression9<SQLKtWherePredicate<T1>, SQLKtWherePredicate<T2>, SQLKtWherePredicate<T3>, SQLKtWherePredicate<T4>, SQLKtWherePredicate<T5>, SQLKtWherePredicate<T6>, SQLKtWherePredicate<T7>, SQLKtWherePredicate<T8>, SQLKtWherePredicate<T9>> on) {
        ClientQueryable9<T1, T2, T3, T4, T5, T6, T7, T8, T9> entityQueryable7 = getClientQueryable8().innerJoin(joinClass, (where1, where2, where3, where4, where5, where6, where7, where8, where9) -> {
            on.apply(new SQLKtWherePredicateImpl<>(where1), new SQLKtWherePredicateImpl<>(where2), new SQLKtWherePredicateImpl<>(where3), new SQLKtWherePredicateImpl<>(where4), new SQLKtWherePredicateImpl<>(where5), new SQLKtWherePredicateImpl<>(where6), new SQLKtWherePredicateImpl<>(where7), new SQLKtWherePredicateImpl<>(where8), new SQLKtWherePredicateImpl<>(where9));
        });
        return new EasyKtQueryable9<>(entityQueryable7);
    }

    default <T9> KtQueryable9<T1, T2, T3, T4, T5, T6, T7, T8, T9> innerJoin(KtQueryable<T9> joinQueryable, SQLExpression9<SQLKtWherePredicate<T1>, SQLKtWherePredicate<T2>, SQLKtWherePredicate<T3>, SQLKtWherePredicate<T4>, SQLKtWherePredicate<T5>, SQLKtWherePredicate<T6>, SQLKtWherePredicate<T7>, SQLKtWherePredicate<T8>, SQLKtWherePredicate<T9>> on) {
        ClientQueryable9<T1, T2, T3, T4, T5, T6, T7, T8, T9> entityQueryable7 = getClientQueryable8().innerJoin(joinQueryable.getClientQueryable(), (where1, where2, where3, where4, where5, where6, where7, where8, where9) -> {
            on.apply(new SQLKtWherePredicateImpl<>(where1), new SQLKtWherePredicateImpl<>(where2), new SQLKtWherePredicateImpl<>(where3), new SQLKtWherePredicateImpl<>(where4), new SQLKtWherePredicateImpl<>(where5), new SQLKtWherePredicateImpl<>(where6), new SQLKtWherePredicateImpl<>(where7), new SQLKtWherePredicateImpl<>(where8), new SQLKtWherePredicateImpl<>(where9));
        });
        return new EasyKtQueryable9<>(entityQueryable7);
    }

    default <T9> KtQueryable9<T1, T2, T3, T4, T5, T6, T7, T8, T9> leftJoinMerge(Class<T9> joinClass, SQLExpression1<Tuple9<SQLKtWherePredicate<T1>, SQLKtWherePredicate<T2>, SQLKtWherePredicate<T3>, SQLKtWherePredicate<T4>, SQLKtWherePredicate<T5>, SQLKtWherePredicate<T6>, SQLKtWherePredicate<T7>, SQLKtWherePredicate<T8>, SQLKtWherePredicate<T9>>> on) {
        return leftJoin(joinClass, (t1, t2, t3, t4, t5, t6, t7, t8, t9) -> {
            on.apply(new Tuple9<>(t1, t2, t3, t4, t5, t6, t7, t8, t9));
        });
    }

    default <T9> KtQueryable9<T1, T2, T3, T4, T5, T6, T7, T8, T9> leftJoinMerge(KtQueryable<T9> joinQueryable, SQLExpression1<Tuple9<SQLKtWherePredicate<T1>, SQLKtWherePredicate<T2>, SQLKtWherePredicate<T3>, SQLKtWherePredicate<T4>, SQLKtWherePredicate<T5>, SQLKtWherePredicate<T6>, SQLKtWherePredicate<T7>, SQLKtWherePredicate<T8>, SQLKtWherePredicate<T9>>> on) {
        return leftJoin(joinQueryable, (t1, t2, t3, t4, t5, t6, t7, t8, t9) -> {
            on.apply(new Tuple9<>(t1, t2, t3, t4, t5, t6, t7, t8, t9));
        });
    }

    default <T9> KtQueryable9<T1, T2, T3, T4, T5, T6, T7, T8, T9> rightJoinMerge(Class<T9> joinClass, SQLExpression1<Tuple9<SQLKtWherePredicate<T1>, SQLKtWherePredicate<T2>, SQLKtWherePredicate<T3>, SQLKtWherePredicate<T4>, SQLKtWherePredicate<T5>, SQLKtWherePredicate<T6>, SQLKtWherePredicate<T7>, SQLKtWherePredicate<T8>, SQLKtWherePredicate<T9>>> on) {
        return rightJoin(joinClass, (t1, t2, t3, t4, t5, t6, t7, t8, t9) -> {
            on.apply(new Tuple9<>(t1, t2, t3, t4, t5, t6, t7, t8, t9));
        });
    }

    default <T9> KtQueryable9<T1, T2, T3, T4, T5, T6, T7, T8, T9> rightJoinMerge(KtQueryable<T9> joinQueryable, SQLExpression1<Tuple9<SQLKtWherePredicate<T1>, SQLKtWherePredicate<T2>, SQLKtWherePredicate<T3>, SQLKtWherePredicate<T4>, SQLKtWherePredicate<T5>, SQLKtWherePredicate<T6>, SQLKtWherePredicate<T7>, SQLKtWherePredicate<T8>, SQLKtWherePredicate<T9>>> on) {
        return rightJoin(joinQueryable, (t1, t2, t3, t4, t5, t6, t7, t8, t9) -> {
            on.apply(new Tuple9<>(t1, t2, t3, t4, t5, t6, t7, t8, t9));
        });
    }

    default <T9> KtQueryable9<T1, T2, T3, T4, T5, T6, T7, T8, T9> innerJoinMerge(Class<T9> joinClass, SQLExpression1<Tuple9<SQLKtWherePredicate<T1>, SQLKtWherePredicate<T2>, SQLKtWherePredicate<T3>, SQLKtWherePredicate<T4>, SQLKtWherePredicate<T5>, SQLKtWherePredicate<T6>, SQLKtWherePredicate<T7>, SQLKtWherePredicate<T8>, SQLKtWherePredicate<T9>>> on) {
        return innerJoin(joinClass, (t1, t2, t3, t4, t5, t6, t7, t8, t9) -> {
            on.apply(new Tuple9<>(t1, t2, t3, t4, t5, t6, t7, t8, t9));
        });
    }

    default <T9> KtQueryable9<T1, T2, T3, T4, T5, T6, T7, T8, T9> innerJoinMerge(KtQueryable<T9> joinQueryable, SQLExpression1<Tuple9<SQLKtWherePredicate<T1>, SQLKtWherePredicate<T2>, SQLKtWherePredicate<T3>, SQLKtWherePredicate<T4>, SQLKtWherePredicate<T5>, SQLKtWherePredicate<T6>, SQLKtWherePredicate<T7>, SQLKtWherePredicate<T8>, SQLKtWherePredicate<T9>>> on) {
        return innerJoin(joinQueryable, (t1, t2, t3, t4, t5, t6, t7, t8, t9) -> {
            on.apply(new Tuple9<>(t1, t2, t3, t4, t5, t6, t7, t8, t9));
        });
    }
}