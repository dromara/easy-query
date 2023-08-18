package com.easy.query.api4kt.select.extension.queryable6;

import com.easy.query.api4kt.select.KtQueryable;
import com.easy.query.api4kt.select.KtQueryable7;
import com.easy.query.api4kt.select.impl.EasyKtQueryable7;
import com.easy.query.api4kt.sql.SQLKtWherePredicate;
import com.easy.query.api4kt.sql.impl.SQLKtWherePredicateImpl;
import com.easy.query.core.basic.api.select.ClientQueryable7;
import com.easy.query.core.common.tuple.Tuple7;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.lambda.SQLExpression7;

/**
 * create time 2023/8/18 13:14
 * 文件说明
 *
 * @author xuejiaming
 */
public interface SQLKtJoinable6<T1, T2, T3, T4, T5, T6> extends ClientKtQueryable6Available<T1, T2, T3, T4, T5, T6> {

    default <T7> KtQueryable7<T1, T2, T3, T4, T5, T6, T7> leftJoin(Class<T7> joinClass, SQLExpression7<SQLKtWherePredicate<T1>, SQLKtWherePredicate<T2>, SQLKtWherePredicate<T3>, SQLKtWherePredicate<T4>, SQLKtWherePredicate<T5>, SQLKtWherePredicate<T6>, SQLKtWherePredicate<T7>> on) {
        ClientQueryable7<T1, T2, T3, T4, T5, T6, T7> entityQueryable7 = getClientQueryable6().leftJoin(joinClass, (where1, where2, where3, where4, where5, where6, where7) -> {
            on.apply(new SQLKtWherePredicateImpl<>(where1), new SQLKtWherePredicateImpl<>(where2), new SQLKtWherePredicateImpl<>(where3), new SQLKtWherePredicateImpl<>(where4), new SQLKtWherePredicateImpl<>(where5), new SQLKtWherePredicateImpl<>(where6), new SQLKtWherePredicateImpl<>(where7));
        });
        return new EasyKtQueryable7<>(entityQueryable7);
    }

    default <T7> KtQueryable7<T1, T2, T3, T4, T5, T6, T7> leftJoin(KtQueryable<T7> joinQueryable, SQLExpression7<SQLKtWherePredicate<T1>, SQLKtWherePredicate<T2>, SQLKtWherePredicate<T3>, SQLKtWherePredicate<T4>, SQLKtWherePredicate<T5>, SQLKtWherePredicate<T6>, SQLKtWherePredicate<T7>> on) {
        ClientQueryable7<T1, T2, T3, T4, T5, T6, T7> entityQueryable7 = getClientQueryable6().leftJoin(joinQueryable.getClientQueryable(), (where1, where2, where3, where4, where5, where6, where7) -> {
            on.apply(new SQLKtWherePredicateImpl<>(where1), new SQLKtWherePredicateImpl<>(where2), new SQLKtWherePredicateImpl<>(where3), new SQLKtWherePredicateImpl<>(where4), new SQLKtWherePredicateImpl<>(where5), new SQLKtWherePredicateImpl<>(where6), new SQLKtWherePredicateImpl<>(where7));
        });
        return new EasyKtQueryable7<>(entityQueryable7);
    }
    default <T7> KtQueryable7<T1, T2, T3, T4, T5, T6, T7> rightJoin(Class<T7> joinClass, SQLExpression7<SQLKtWherePredicate<T1>, SQLKtWherePredicate<T2>, SQLKtWherePredicate<T3>, SQLKtWherePredicate<T4>, SQLKtWherePredicate<T5>, SQLKtWherePredicate<T6>, SQLKtWherePredicate<T7>> on) {
        ClientQueryable7<T1, T2, T3, T4, T5, T6, T7> entityQueryable7 = getClientQueryable6().rightJoin(joinClass, (where1, where2, where3, where4, where5, where6, where7) -> {
            on.apply(new SQLKtWherePredicateImpl<>(where1), new SQLKtWherePredicateImpl<>(where2), new SQLKtWherePredicateImpl<>(where3), new SQLKtWherePredicateImpl<>(where4), new SQLKtWherePredicateImpl<>(where5), new SQLKtWherePredicateImpl<>(where6), new SQLKtWherePredicateImpl<>(where7));
        });
        return new EasyKtQueryable7<>(entityQueryable7);
    }

    default <T7> KtQueryable7<T1, T2, T3, T4, T5, T6, T7> rightJoin(KtQueryable<T7> joinQueryable, SQLExpression7<SQLKtWherePredicate<T1>, SQLKtWherePredicate<T2>, SQLKtWherePredicate<T3>, SQLKtWherePredicate<T4>, SQLKtWherePredicate<T5>, SQLKtWherePredicate<T6>, SQLKtWherePredicate<T7>> on) {
        ClientQueryable7<T1, T2, T3, T4, T5, T6, T7> entityQueryable7 = getClientQueryable6().rightJoin(joinQueryable.getClientQueryable(), (where1, where2, where3, where4, where5, where6, where7) -> {
            on.apply(new SQLKtWherePredicateImpl<>(where1), new SQLKtWherePredicateImpl<>(where2), new SQLKtWherePredicateImpl<>(where3), new SQLKtWherePredicateImpl<>(where4), new SQLKtWherePredicateImpl<>(where5), new SQLKtWherePredicateImpl<>(where6), new SQLKtWherePredicateImpl<>(where7));
        });
        return new EasyKtQueryable7<>(entityQueryable7);
    }

    default <T7> KtQueryable7<T1, T2, T3, T4, T5, T6, T7> innerJoin(Class<T7> joinClass, SQLExpression7<SQLKtWherePredicate<T1>, SQLKtWherePredicate<T2>, SQLKtWherePredicate<T3>, SQLKtWherePredicate<T4>, SQLKtWherePredicate<T5>, SQLKtWherePredicate<T6>, SQLKtWherePredicate<T7>> on) {
        ClientQueryable7<T1, T2, T3, T4, T5, T6, T7> entityQueryable7 = getClientQueryable6().innerJoin(joinClass, (where1, where2, where3, where4, where5, where6, where7) -> {
            on.apply(new SQLKtWherePredicateImpl<>(where1), new SQLKtWherePredicateImpl<>(where2), new SQLKtWherePredicateImpl<>(where3), new SQLKtWherePredicateImpl<>(where4), new SQLKtWherePredicateImpl<>(where5), new SQLKtWherePredicateImpl<>(where6), new SQLKtWherePredicateImpl<>(where7));
        });
        return new EasyKtQueryable7<>(entityQueryable7);
    }

    default <T7> KtQueryable7<T1, T2, T3, T4, T5, T6, T7> innerJoin(KtQueryable<T7> joinQueryable, SQLExpression7<SQLKtWherePredicate<T1>, SQLKtWherePredicate<T2>, SQLKtWherePredicate<T3>, SQLKtWherePredicate<T4>, SQLKtWherePredicate<T5>, SQLKtWherePredicate<T6>, SQLKtWherePredicate<T7>> on) {
        ClientQueryable7<T1, T2, T3, T4, T5, T6, T7> entityQueryable7 = getClientQueryable6().innerJoin(joinQueryable.getClientQueryable(), (where1, where2, where3, where4, where5, where6, where7) -> {
            on.apply(new SQLKtWherePredicateImpl<>(where1), new SQLKtWherePredicateImpl<>(where2), new SQLKtWherePredicateImpl<>(where3), new SQLKtWherePredicateImpl<>(where4), new SQLKtWherePredicateImpl<>(where5), new SQLKtWherePredicateImpl<>(where6), new SQLKtWherePredicateImpl<>(where7));
        });
        return new EasyKtQueryable7<>(entityQueryable7);
    }

    default <T7> KtQueryable7<T1, T2, T3, T4, T5, T6, T7> leftJoinMerge(Class<T7> joinClass, SQLExpression1<Tuple7<SQLKtWherePredicate<T1>, SQLKtWherePredicate<T2>, SQLKtWherePredicate<T3>, SQLKtWherePredicate<T4>, SQLKtWherePredicate<T5>, SQLKtWherePredicate<T6>, SQLKtWherePredicate<T7>>> on) {
        return leftJoin(joinClass, (t1, t2, t3, t4, t5, t6, t7) -> {
            on.apply(new Tuple7<>(t1, t2, t3, t4, t5, t6, t7));
        });
    }

    default <T7> KtQueryable7<T1, T2, T3, T4, T5, T6, T7> leftJoinMerge(KtQueryable<T7> joinQueryable, SQLExpression1<Tuple7<SQLKtWherePredicate<T1>, SQLKtWherePredicate<T2>, SQLKtWherePredicate<T3>, SQLKtWherePredicate<T4>, SQLKtWherePredicate<T5>, SQLKtWherePredicate<T6>, SQLKtWherePredicate<T7>>> on) {
        return leftJoin(joinQueryable, (t1, t2, t3, t4, t5, t6, t7) -> {
            on.apply(new Tuple7<>(t1, t2, t3, t4, t5, t6, t7));
        });
    }

    default <T7> KtQueryable7<T1, T2, T3, T4, T5, T6, T7> rightJoinMerge(Class<T7> joinClass, SQLExpression1<Tuple7<SQLKtWherePredicate<T1>, SQLKtWherePredicate<T2>, SQLKtWherePredicate<T3>, SQLKtWherePredicate<T4>, SQLKtWherePredicate<T5>, SQLKtWherePredicate<T6>, SQLKtWherePredicate<T7>>> on) {
        return rightJoin(joinClass, (t1, t2, t3, t4, t5, t6, t7) -> {
            on.apply(new Tuple7<>(t1, t2, t3, t4, t5, t6, t7));
        });
    }

    default <T7> KtQueryable7<T1, T2, T3, T4, T5, T6, T7> rightJoinMerge(KtQueryable<T7> joinQueryable, SQLExpression1<Tuple7<SQLKtWherePredicate<T1>, SQLKtWherePredicate<T2>, SQLKtWherePredicate<T3>, SQLKtWherePredicate<T4>, SQLKtWherePredicate<T5>, SQLKtWherePredicate<T6>, SQLKtWherePredicate<T7>>> on) {
        return rightJoin(joinQueryable, (t1, t2, t3, t4, t5, t6, t7) -> {
            on.apply(new Tuple7<>(t1, t2, t3, t4, t5, t6, t7));
        });
    }

    default <T7> KtQueryable7<T1, T2, T3, T4, T5, T6, T7> innerJoinMerge(Class<T7> joinClass, SQLExpression1<Tuple7<SQLKtWherePredicate<T1>, SQLKtWherePredicate<T2>, SQLKtWherePredicate<T3>, SQLKtWherePredicate<T4>, SQLKtWherePredicate<T5>, SQLKtWherePredicate<T6>, SQLKtWherePredicate<T7>>> on) {
        return innerJoin(joinClass, (t1, t2, t3, t4, t5, t6, t7) -> {
            on.apply(new Tuple7<>(t1, t2, t3, t4, t5, t6, t7));
        });
    }

    default <T7> KtQueryable7<T1, T2, T3, T4, T5, T6, T7> innerJoinMerge(KtQueryable<T7> joinQueryable, SQLExpression1<Tuple7<SQLKtWherePredicate<T1>, SQLKtWherePredicate<T2>, SQLKtWherePredicate<T3>, SQLKtWherePredicate<T4>, SQLKtWherePredicate<T5>, SQLKtWherePredicate<T6>, SQLKtWherePredicate<T7>>> on) {
        return innerJoin(joinQueryable, (t1, t2, t3, t4, t5, t6, t7) -> {
            on.apply(new Tuple7<>(t1, t2, t3, t4, t5, t6, t7));
        });
    }
}