package com.easy.query.api4j.select.extension.queryable7;

import com.easy.query.api4j.select.Queryable;
import com.easy.query.api4j.select.Queryable8;
import com.easy.query.api4j.select.impl.EasyQueryable8;
import com.easy.query.api4j.sql.SQLWherePredicate;
import com.easy.query.api4j.sql.impl.SQLWherePredicateImpl;
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
public interface SQLJoinable7<T1, T2, T3, T4, T5, T6,T7> extends ClientQueryable7Available<T1, T2, T3, T4, T5, T6,T7> {

    default <T8> Queryable8<T1, T2, T3, T4, T5, T6, T7, T8> leftJoin(Class<T8> joinClass, SQLExpression8<SQLWherePredicate<T1>, SQLWherePredicate<T2>, SQLWherePredicate<T3>, SQLWherePredicate<T4>, SQLWherePredicate<T5>, SQLWherePredicate<T6>, SQLWherePredicate<T7>, SQLWherePredicate<T8>> on) {
        ClientQueryable8<T1, T2, T3, T4, T5, T6, T7, T8> entityQueryable7 = getClientQueryable7().leftJoin(joinClass, (where1, where2, where3, where4, where5, where6, where7, where8) -> {
            on.apply(new SQLWherePredicateImpl<>(where1), new SQLWherePredicateImpl<>(where2), new SQLWherePredicateImpl<>(where3), new SQLWherePredicateImpl<>(where4), new SQLWherePredicateImpl<>(where5), new SQLWherePredicateImpl<>(where6), new SQLWherePredicateImpl<>(where7), new SQLWherePredicateImpl<>(where8));
        });
        return new EasyQueryable8<>(entityQueryable7);
    }

    default <T8> Queryable8<T1, T2, T3, T4, T5, T6, T7, T8> leftJoin(Queryable<T8> joinQueryable, SQLExpression8<SQLWherePredicate<T1>, SQLWherePredicate<T2>, SQLWherePredicate<T3>, SQLWherePredicate<T4>, SQLWherePredicate<T5>, SQLWherePredicate<T6>, SQLWherePredicate<T7>, SQLWherePredicate<T8>> on) {
        ClientQueryable8<T1, T2, T3, T4, T5, T6, T7, T8> entityQueryable7 = getClientQueryable7().leftJoin(joinQueryable.getClientQueryable(), (where1, where2, where3, where4, where5, where6, where7, where8) -> {
            on.apply(new SQLWherePredicateImpl<>(where1), new SQLWherePredicateImpl<>(where2), new SQLWherePredicateImpl<>(where3), new SQLWherePredicateImpl<>(where4), new SQLWherePredicateImpl<>(where5), new SQLWherePredicateImpl<>(where6), new SQLWherePredicateImpl<>(where7), new SQLWherePredicateImpl<>(where8));
        });
        return new EasyQueryable8<>(entityQueryable7);
    }
    default <T8> Queryable8<T1, T2, T3, T4, T5, T6, T7, T8> rightJoin(Class<T8> joinClass, SQLExpression8<SQLWherePredicate<T1>, SQLWherePredicate<T2>, SQLWherePredicate<T3>, SQLWherePredicate<T4>, SQLWherePredicate<T5>, SQLWherePredicate<T6>, SQLWherePredicate<T7>, SQLWherePredicate<T8>> on) {
        ClientQueryable8<T1, T2, T3, T4, T5, T6, T7, T8> entityQueryable7 = getClientQueryable7().rightJoin(joinClass, (where1, where2, where3, where4, where5, where6, where7, where8) -> {
            on.apply(new SQLWherePredicateImpl<>(where1), new SQLWherePredicateImpl<>(where2), new SQLWherePredicateImpl<>(where3), new SQLWherePredicateImpl<>(where4), new SQLWherePredicateImpl<>(where5), new SQLWherePredicateImpl<>(where6), new SQLWherePredicateImpl<>(where7), new SQLWherePredicateImpl<>(where8));
        });
        return new EasyQueryable8<>(entityQueryable7);
    }

    default <T8> Queryable8<T1, T2, T3, T4, T5, T6, T7, T8> rightJoin(Queryable<T8> joinQueryable, SQLExpression8<SQLWherePredicate<T1>, SQLWherePredicate<T2>, SQLWherePredicate<T3>, SQLWherePredicate<T4>, SQLWherePredicate<T5>, SQLWherePredicate<T6>, SQLWherePredicate<T7>, SQLWherePredicate<T8>> on) {
        ClientQueryable8<T1, T2, T3, T4, T5, T6, T7, T8> entityQueryable7 = getClientQueryable7().rightJoin(joinQueryable.getClientQueryable(), (where1, where2, where3, where4, where5, where6, where7, where8) -> {
            on.apply(new SQLWherePredicateImpl<>(where1), new SQLWherePredicateImpl<>(where2), new SQLWherePredicateImpl<>(where3), new SQLWherePredicateImpl<>(where4), new SQLWherePredicateImpl<>(where5), new SQLWherePredicateImpl<>(where6), new SQLWherePredicateImpl<>(where7), new SQLWherePredicateImpl<>(where8));
        });
        return new EasyQueryable8<>(entityQueryable7);
    }

    default <T8> Queryable8<T1, T2, T3, T4, T5, T6, T7, T8> innerJoin(Class<T8> joinClass, SQLExpression8<SQLWherePredicate<T1>, SQLWherePredicate<T2>, SQLWherePredicate<T3>, SQLWherePredicate<T4>, SQLWherePredicate<T5>, SQLWherePredicate<T6>, SQLWherePredicate<T7>, SQLWherePredicate<T8>> on) {
        ClientQueryable8<T1, T2, T3, T4, T5, T6, T7, T8> entityQueryable7 = getClientQueryable7().innerJoin(joinClass, (where1, where2, where3, where4, where5, where6, where7, where8) -> {
            on.apply(new SQLWherePredicateImpl<>(where1), new SQLWherePredicateImpl<>(where2), new SQLWherePredicateImpl<>(where3), new SQLWherePredicateImpl<>(where4), new SQLWherePredicateImpl<>(where5), new SQLWherePredicateImpl<>(where6), new SQLWherePredicateImpl<>(where7), new SQLWherePredicateImpl<>(where8));
        });
        return new EasyQueryable8<>(entityQueryable7);
    }

    default <T8> Queryable8<T1, T2, T3, T4, T5, T6, T7, T8> innerJoin(Queryable<T8> joinQueryable, SQLExpression8<SQLWherePredicate<T1>, SQLWherePredicate<T2>, SQLWherePredicate<T3>, SQLWherePredicate<T4>, SQLWherePredicate<T5>, SQLWherePredicate<T6>, SQLWherePredicate<T7>, SQLWherePredicate<T8>> on) {
        ClientQueryable8<T1, T2, T3, T4, T5, T6, T7, T8> entityQueryable7 = getClientQueryable7().innerJoin(joinQueryable.getClientQueryable(), (where1, where2, where3, where4, where5, where6, where7, where8) -> {
            on.apply(new SQLWherePredicateImpl<>(where1), new SQLWherePredicateImpl<>(where2), new SQLWherePredicateImpl<>(where3), new SQLWherePredicateImpl<>(where4), new SQLWherePredicateImpl<>(where5), new SQLWherePredicateImpl<>(where6), new SQLWherePredicateImpl<>(where7), new SQLWherePredicateImpl<>(where8));
        });
        return new EasyQueryable8<>(entityQueryable7);
    }

    default <T8> Queryable8<T1, T2, T3, T4, T5, T6, T7, T8> leftJoinMerge(Class<T8> joinClass, SQLExpression1<Tuple8<SQLWherePredicate<T1>, SQLWherePredicate<T2>, SQLWherePredicate<T3>, SQLWherePredicate<T4>, SQLWherePredicate<T5>, SQLWherePredicate<T6>, SQLWherePredicate<T7>, SQLWherePredicate<T8>>> on) {
        return leftJoin(joinClass, (t, t1, t2, t3, t4, t5, t6, t7) -> {
            on.apply(new Tuple8<>(t, t1, t2, t3, t4, t5, t6, t7));
        });
    }

    default <T8> Queryable8<T1, T2, T3, T4, T5, T6, T7, T8> leftJoinMerge(Queryable<T8> joinQueryable, SQLExpression1<Tuple8<SQLWherePredicate<T1>, SQLWherePredicate<T2>, SQLWherePredicate<T3>, SQLWherePredicate<T4>, SQLWherePredicate<T5>, SQLWherePredicate<T6>, SQLWherePredicate<T7>, SQLWherePredicate<T8>>> on) {
        return leftJoin(joinQueryable, (t, t1, t2, t3, t4, t5, t6, t7) -> {
            on.apply(new Tuple8<>(t, t1, t2, t3, t4, t5, t6, t7));
        });
    }

    default <T8> Queryable8<T1, T2, T3, T4, T5, T6, T7, T8> rightJoinMerge(Class<T8> joinClass, SQLExpression1<Tuple8<SQLWherePredicate<T1>, SQLWherePredicate<T2>, SQLWherePredicate<T3>, SQLWherePredicate<T4>, SQLWherePredicate<T5>, SQLWherePredicate<T6>, SQLWherePredicate<T7>, SQLWherePredicate<T8>>> on) {
        return rightJoin(joinClass, (t, t1, t2, t3, t4, t5, t6, t7) -> {
            on.apply(new Tuple8<>(t, t1, t2, t3, t4, t5, t6, t7));
        });
    }

    default <T8> Queryable8<T1, T2, T3, T4, T5, T6, T7, T8> rightJoinMerge(Queryable<T8> joinQueryable, SQLExpression1<Tuple8<SQLWherePredicate<T1>, SQLWherePredicate<T2>, SQLWherePredicate<T3>, SQLWherePredicate<T4>, SQLWherePredicate<T5>, SQLWherePredicate<T6>, SQLWherePredicate<T7>, SQLWherePredicate<T8>>> on) {
        return rightJoin(joinQueryable, (t, t1, t2, t3, t4, t5, t6, t7) -> {
            on.apply(new Tuple8<>(t, t1, t2, t3, t4, t5, t6, t7));
        });
    }

    default <T8> Queryable8<T1, T2, T3, T4, T5, T6, T7, T8> innerJoinMerge(Class<T8> joinClass, SQLExpression1<Tuple8<SQLWherePredicate<T1>, SQLWherePredicate<T2>, SQLWherePredicate<T3>, SQLWherePredicate<T4>, SQLWherePredicate<T5>, SQLWherePredicate<T6>, SQLWherePredicate<T7>, SQLWherePredicate<T8>>> on) {
        return innerJoin(joinClass, (t, t1, t2, t3, t4, t5, t6, t7) -> {
            on.apply(new Tuple8<>(t, t1, t2, t3, t4, t5, t6, t7));
        });
    }

    default <T8> Queryable8<T1, T2, T3, T4, T5, T6, T7, T8> innerJoinMerge(Queryable<T8> joinQueryable, SQLExpression1<Tuple8<SQLWherePredicate<T1>, SQLWherePredicate<T2>, SQLWherePredicate<T3>, SQLWherePredicate<T4>, SQLWherePredicate<T5>, SQLWherePredicate<T6>, SQLWherePredicate<T7>, SQLWherePredicate<T8>>> on) {
        return innerJoin(joinQueryable, (t, t1, t2, t3, t4, t5, t6, t7) -> {
            on.apply(new Tuple8<>(t, t1, t2, t3, t4, t5, t6, t7));
        });
    }
}