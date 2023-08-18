package com.easy.query.api4kt.select.extension.queryable9;

import com.easy.query.api4kt.select.KtQueryable;
import com.easy.query.api4kt.select.KtQueryable10;
import com.easy.query.api4kt.select.impl.EasyKtQueryable10;
import com.easy.query.api4kt.sql.SQLKtWherePredicate;
import com.easy.query.api4kt.sql.impl.SQLKtWherePredicateImpl;
import com.easy.query.core.basic.api.select.ClientQueryable10;
import com.easy.query.core.common.tuple.Tuple10;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.lambda.SQLExpression10;

/**
 * create time 2023/8/18 13:14
 * 文件说明
 *
 * @author xuejiaming
 */
public interface SQLKtJoinable9<T1, T2, T3, T4, T5, T6,T7,T8,T9> extends ClientKtQueryable9Available<T1, T2, T3, T4, T5, T6,T7,T8,T9> {

    default <T10> KtQueryable10<T1, T2, T3, T4, T5, T6, T7, T8, T9,T10> leftJoin(Class<T10> joinClass, SQLExpression10<SQLKtWherePredicate<T1>, SQLKtWherePredicate<T2>, SQLKtWherePredicate<T3>, SQLKtWherePredicate<T4>, SQLKtWherePredicate<T5>, SQLKtWherePredicate<T6>, SQLKtWherePredicate<T7>, SQLKtWherePredicate<T8>, SQLKtWherePredicate<T9>, SQLKtWherePredicate<T10>> on) {
        ClientQueryable10<T1, T2, T3, T4, T5, T6, T7, T8, T9,T10> entityQueryable7 = getClientQueryable9().leftJoin(joinClass, (where1, where2, where3, where4, where5, where6, where7, where8, where9, where10) -> {
            on.apply(new SQLKtWherePredicateImpl<>(where1), new SQLKtWherePredicateImpl<>(where2), new SQLKtWherePredicateImpl<>(where3), new SQLKtWherePredicateImpl<>(where4), new SQLKtWherePredicateImpl<>(where5), new SQLKtWherePredicateImpl<>(where6), new SQLKtWherePredicateImpl<>(where7), new SQLKtWherePredicateImpl<>(where8), new SQLKtWherePredicateImpl<>(where9), new SQLKtWherePredicateImpl<>(where10));
        });
        return new EasyKtQueryable10<>(entityQueryable7);
    }

    default <T10> KtQueryable10<T1, T2, T3, T4, T5, T6, T7, T8, T9,T10> leftJoin(KtQueryable<T10> joinQueryable, SQLExpression10<SQLKtWherePredicate<T1>, SQLKtWherePredicate<T2>, SQLKtWherePredicate<T3>, SQLKtWherePredicate<T4>, SQLKtWherePredicate<T5>, SQLKtWherePredicate<T6>, SQLKtWherePredicate<T7>, SQLKtWherePredicate<T8>, SQLKtWherePredicate<T9>, SQLKtWherePredicate<T10>> on) {
        ClientQueryable10<T1, T2, T3, T4, T5, T6, T7, T8, T9,T10> entityQueryable7 = getClientQueryable9().leftJoin(joinQueryable.getClientQueryable(), (where1, where2, where3, where4, where5, where6, where7, where8, where9, where10) -> {
            on.apply(new SQLKtWherePredicateImpl<>(where1), new SQLKtWherePredicateImpl<>(where2), new SQLKtWherePredicateImpl<>(where3), new SQLKtWherePredicateImpl<>(where4), new SQLKtWherePredicateImpl<>(where5), new SQLKtWherePredicateImpl<>(where6), new SQLKtWherePredicateImpl<>(where7), new SQLKtWherePredicateImpl<>(where8), new SQLKtWherePredicateImpl<>(where9), new SQLKtWherePredicateImpl<>(where10));
        });
        return new EasyKtQueryable10<>(entityQueryable7);
    }
    default <T10> KtQueryable10<T1, T2, T3, T4, T5, T6, T7, T8, T9,T10> rightJoin(Class<T10> joinClass, SQLExpression10<SQLKtWherePredicate<T1>, SQLKtWherePredicate<T2>, SQLKtWherePredicate<T3>, SQLKtWherePredicate<T4>, SQLKtWherePredicate<T5>, SQLKtWherePredicate<T6>, SQLKtWherePredicate<T7>, SQLKtWherePredicate<T8>, SQLKtWherePredicate<T9>, SQLKtWherePredicate<T10>> on) {
        ClientQueryable10<T1, T2, T3, T4, T5, T6, T7, T8, T9,T10> entityQueryable7 = getClientQueryable9().rightJoin(joinClass, (where1, where2, where3, where4, where5, where6, where7, where8, where9, where10) -> {
            on.apply(new SQLKtWherePredicateImpl<>(where1), new SQLKtWherePredicateImpl<>(where2), new SQLKtWherePredicateImpl<>(where3), new SQLKtWherePredicateImpl<>(where4), new SQLKtWherePredicateImpl<>(where5), new SQLKtWherePredicateImpl<>(where6), new SQLKtWherePredicateImpl<>(where7), new SQLKtWherePredicateImpl<>(where8), new SQLKtWherePredicateImpl<>(where9), new SQLKtWherePredicateImpl<>(where10));
        });
        return new EasyKtQueryable10<>(entityQueryable7);
    }

    default <T10> KtQueryable10<T1, T2, T3, T4, T5, T6, T7, T8, T9,T10> rightJoin(KtQueryable<T10> joinQueryable, SQLExpression10<SQLKtWherePredicate<T1>, SQLKtWherePredicate<T2>, SQLKtWherePredicate<T3>, SQLKtWherePredicate<T4>, SQLKtWherePredicate<T5>, SQLKtWherePredicate<T6>, SQLKtWherePredicate<T7>, SQLKtWherePredicate<T8>, SQLKtWherePredicate<T9>, SQLKtWherePredicate<T10>> on) {
        ClientQueryable10<T1, T2, T3, T4, T5, T6, T7, T8, T9,T10> entityQueryable7 = getClientQueryable9().rightJoin(joinQueryable.getClientQueryable(), (where1, where2, where3, where4, where5, where6, where7, where8, where9, where10) -> {
            on.apply(new SQLKtWherePredicateImpl<>(where1), new SQLKtWherePredicateImpl<>(where2), new SQLKtWherePredicateImpl<>(where3), new SQLKtWherePredicateImpl<>(where4), new SQLKtWherePredicateImpl<>(where5), new SQLKtWherePredicateImpl<>(where6), new SQLKtWherePredicateImpl<>(where7), new SQLKtWherePredicateImpl<>(where8), new SQLKtWherePredicateImpl<>(where9), new SQLKtWherePredicateImpl<>(where10));
        });
        return new EasyKtQueryable10<>(entityQueryable7);
    }

    default <T10> KtQueryable10<T1, T2, T3, T4, T5, T6, T7, T8, T9,T10> innerJoin(Class<T10> joinClass, SQLExpression10<SQLKtWherePredicate<T1>, SQLKtWherePredicate<T2>, SQLKtWherePredicate<T3>, SQLKtWherePredicate<T4>, SQLKtWherePredicate<T5>, SQLKtWherePredicate<T6>, SQLKtWherePredicate<T7>, SQLKtWherePredicate<T8>, SQLKtWherePredicate<T9>, SQLKtWherePredicate<T10>> on) {
        ClientQueryable10<T1, T2, T3, T4, T5, T6, T7, T8, T9,T10> entityQueryable7 = getClientQueryable9().innerJoin(joinClass, (where1, where2, where3, where4, where5, where6, where7, where8, where9, where10) -> {
            on.apply(new SQLKtWherePredicateImpl<>(where1), new SQLKtWherePredicateImpl<>(where2), new SQLKtWherePredicateImpl<>(where3), new SQLKtWherePredicateImpl<>(where4), new SQLKtWherePredicateImpl<>(where5), new SQLKtWherePredicateImpl<>(where6), new SQLKtWherePredicateImpl<>(where7), new SQLKtWherePredicateImpl<>(where8), new SQLKtWherePredicateImpl<>(where9), new SQLKtWherePredicateImpl<>(where10));
        });
        return new EasyKtQueryable10<>(entityQueryable7);
    }

    default <T10> KtQueryable10<T1, T2, T3, T4, T5, T6, T7, T8, T9,T10> innerJoin(KtQueryable<T10> joinQueryable, SQLExpression10<SQLKtWherePredicate<T1>, SQLKtWherePredicate<T2>, SQLKtWherePredicate<T3>, SQLKtWherePredicate<T4>, SQLKtWherePredicate<T5>, SQLKtWherePredicate<T6>, SQLKtWherePredicate<T7>, SQLKtWherePredicate<T8>, SQLKtWherePredicate<T9>, SQLKtWherePredicate<T10>> on) {
        ClientQueryable10<T1, T2, T3, T4, T5, T6, T7, T8, T9,T10> entityQueryable7 = getClientQueryable9().innerJoin(joinQueryable.getClientQueryable(), (where1, where2, where3, where4, where5, where6, where7, where8, where9, where10) -> {
            on.apply(new SQLKtWherePredicateImpl<>(where1), new SQLKtWherePredicateImpl<>(where2), new SQLKtWherePredicateImpl<>(where3), new SQLKtWherePredicateImpl<>(where4), new SQLKtWherePredicateImpl<>(where5), new SQLKtWherePredicateImpl<>(where6), new SQLKtWherePredicateImpl<>(where7), new SQLKtWherePredicateImpl<>(where8), new SQLKtWherePredicateImpl<>(where9), new SQLKtWherePredicateImpl<>(where10));
        });
        return new EasyKtQueryable10<>(entityQueryable7);
    }

    default <T10> KtQueryable10<T1, T2, T3, T4, T5, T6, T7, T8, T9,T10> leftJoinMerge(Class<T10> joinClass, SQLExpression1<Tuple10<SQLKtWherePredicate<T1>, SQLKtWherePredicate<T2>, SQLKtWherePredicate<T3>, SQLKtWherePredicate<T4>, SQLKtWherePredicate<T5>, SQLKtWherePredicate<T6>, SQLKtWherePredicate<T7>, SQLKtWherePredicate<T8>, SQLKtWherePredicate<T9>, SQLKtWherePredicate<T10>>> on) {
        return leftJoin(joinClass, (t1, t2, t3, t4, t5, t6, t7, t8,t9,t10) -> {
            on.apply(new Tuple10<>(t1, t2, t3, t4, t5, t6, t7, t8,t9,t10));
        });
    }

    default <T10> KtQueryable10<T1, T2, T3, T4, T5, T6, T7, T8, T9,T10> leftJoinMerge(KtQueryable<T10> joinQueryable, SQLExpression1<Tuple10<SQLKtWherePredicate<T1>, SQLKtWherePredicate<T2>, SQLKtWherePredicate<T3>, SQLKtWherePredicate<T4>, SQLKtWherePredicate<T5>, SQLKtWherePredicate<T6>, SQLKtWherePredicate<T7>, SQLKtWherePredicate<T8>, SQLKtWherePredicate<T9>, SQLKtWherePredicate<T10>>> on) {
        return leftJoin(joinQueryable, (t1, t2, t3, t4, t5, t6, t7, t8,t9,t10) -> {
            on.apply(new Tuple10<>(t1, t2, t3, t4, t5, t6, t7, t8,t9,t10));
        });
    }

    default <T10> KtQueryable10<T1, T2, T3, T4, T5, T6, T7, T8, T9,T10> rightJoinMerge(Class<T10> joinClass, SQLExpression1<Tuple10<SQLKtWherePredicate<T1>, SQLKtWherePredicate<T2>, SQLKtWherePredicate<T3>, SQLKtWherePredicate<T4>, SQLKtWherePredicate<T5>, SQLKtWherePredicate<T6>, SQLKtWherePredicate<T7>, SQLKtWherePredicate<T8>, SQLKtWherePredicate<T9>, SQLKtWherePredicate<T10>>> on) {
        return rightJoin(joinClass, (t1, t2, t3, t4, t5, t6, t7, t8,t9,t10) -> {
            on.apply(new Tuple10<>(t1, t2, t3, t4, t5, t6, t7, t8,t9,t10));
        });
    }

    default <T10> KtQueryable10<T1, T2, T3, T4, T5, T6, T7, T8, T9,T10> rightJoinMerge(KtQueryable<T10> joinQueryable, SQLExpression1<Tuple10<SQLKtWherePredicate<T1>, SQLKtWherePredicate<T2>, SQLKtWherePredicate<T3>, SQLKtWherePredicate<T4>, SQLKtWherePredicate<T5>, SQLKtWherePredicate<T6>, SQLKtWherePredicate<T7>, SQLKtWherePredicate<T8>, SQLKtWherePredicate<T9>, SQLKtWherePredicate<T10>>> on) {
        return rightJoin(joinQueryable, (t1, t2, t3, t4, t5, t6, t7, t8,t9,t10) -> {
            on.apply(new Tuple10<>(t1, t2, t3, t4, t5, t6, t7, t8,t9,t10));
        });
    }

    default <T10> KtQueryable10<T1, T2, T3, T4, T5, T6, T7, T8, T9,T10> innerJoinMerge(Class<T10> joinClass, SQLExpression1<Tuple10<SQLKtWherePredicate<T1>, SQLKtWherePredicate<T2>, SQLKtWherePredicate<T3>, SQLKtWherePredicate<T4>, SQLKtWherePredicate<T5>, SQLKtWherePredicate<T6>, SQLKtWherePredicate<T7>, SQLKtWherePredicate<T8>, SQLKtWherePredicate<T9>, SQLKtWherePredicate<T10>>> on) {
        return innerJoin(joinClass, (t1, t2, t3, t4, t5, t6, t7, t8,t9,t10) -> {
            on.apply(new Tuple10<>(t1, t2, t3, t4, t5, t6, t7, t8,t9,t10));
        });
    }

    default <T10> KtQueryable10<T1, T2, T3, T4, T5, T6, T7, T8, T9,T10> innerJoinMerge(KtQueryable<T10> joinQueryable, SQLExpression1<Tuple10<SQLKtWherePredicate<T1>, SQLKtWherePredicate<T2>, SQLKtWherePredicate<T3>, SQLKtWherePredicate<T4>, SQLKtWherePredicate<T5>, SQLKtWherePredicate<T6>, SQLKtWherePredicate<T7>, SQLKtWherePredicate<T8>, SQLKtWherePredicate<T9>, SQLKtWherePredicate<T10>>> on) {
        return innerJoin(joinQueryable, (t1, t2, t3, t4, t5, t6, t7, t8,t9,t10) -> {
            on.apply(new Tuple10<>(t1, t2, t3, t4, t5, t6, t7, t8,t9,t10));
        });
    }
}