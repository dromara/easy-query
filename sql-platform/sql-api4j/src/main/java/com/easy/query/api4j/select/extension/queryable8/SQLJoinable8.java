package com.easy.query.api4j.select.extension.queryable8;

import com.easy.query.api4j.select.Queryable;
import com.easy.query.api4j.select.Queryable9;
import com.easy.query.api4j.select.impl.EasyQueryable9;
import com.easy.query.api4j.sql.SQLWherePredicate;
import com.easy.query.api4j.sql.impl.SQLWherePredicateImpl;
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
public interface SQLJoinable8<T1, T2, T3, T4, T5, T6,T7,T8> extends ClientQueryable8Available<T1, T2, T3, T4, T5, T6,T7,T8> {

    default <T9> Queryable9<T1, T2, T3, T4, T5, T6, T7, T8, T9> leftJoin(Class<T9> joinClass, SQLExpression9<SQLWherePredicate<T1>, SQLWherePredicate<T2>, SQLWherePredicate<T3>, SQLWherePredicate<T4>, SQLWherePredicate<T5>, SQLWherePredicate<T6>, SQLWherePredicate<T7>, SQLWherePredicate<T8>, SQLWherePredicate<T9>> on) {
        ClientQueryable9<T1, T2, T3, T4, T5, T6, T7, T8, T9> entityQueryable7 = getClientQueryable8().leftJoin(joinClass, (where1, where2, where3, where4, where5, where6, where7, where8, where9) -> {
            on.apply(new SQLWherePredicateImpl<>(where1), new SQLWherePredicateImpl<>(where2), new SQLWherePredicateImpl<>(where3), new SQLWherePredicateImpl<>(where4), new SQLWherePredicateImpl<>(where5), new SQLWherePredicateImpl<>(where6), new SQLWherePredicateImpl<>(where7), new SQLWherePredicateImpl<>(where8), new SQLWherePredicateImpl<>(where9));
        });
        return new EasyQueryable9<>(entityQueryable7);
    }

    default <T9> Queryable9<T1, T2, T3, T4, T5, T6, T7, T8, T9> leftJoin(Queryable<T9> joinQueryable, SQLExpression9<SQLWherePredicate<T1>, SQLWherePredicate<T2>, SQLWherePredicate<T3>, SQLWherePredicate<T4>, SQLWherePredicate<T5>, SQLWherePredicate<T6>, SQLWherePredicate<T7>, SQLWherePredicate<T8>, SQLWherePredicate<T9>> on) {
        ClientQueryable9<T1, T2, T3, T4, T5, T6, T7, T8, T9> entityQueryable7 = getClientQueryable8().leftJoin(joinQueryable.getClientQueryable(), (where1, where2, where3, where4, where5, where6, where7, where8, where9) -> {
            on.apply(new SQLWherePredicateImpl<>(where1), new SQLWherePredicateImpl<>(where2), new SQLWherePredicateImpl<>(where3), new SQLWherePredicateImpl<>(where4), new SQLWherePredicateImpl<>(where5), new SQLWherePredicateImpl<>(where6), new SQLWherePredicateImpl<>(where7), new SQLWherePredicateImpl<>(where8), new SQLWherePredicateImpl<>(where9));
        });
        return new EasyQueryable9<>(entityQueryable7);
    }
    default <T9> Queryable9<T1, T2, T3, T4, T5, T6, T7, T8, T9> rightJoin(Class<T9> joinClass, SQLExpression9<SQLWherePredicate<T1>, SQLWherePredicate<T2>, SQLWherePredicate<T3>, SQLWherePredicate<T4>, SQLWherePredicate<T5>, SQLWherePredicate<T6>, SQLWherePredicate<T7>, SQLWherePredicate<T8>, SQLWherePredicate<T9>> on) {
        ClientQueryable9<T1, T2, T3, T4, T5, T6, T7, T8, T9> entityQueryable7 = getClientQueryable8().rightJoin(joinClass, (where1, where2, where3, where4, where5, where6, where7, where8, where9) -> {
            on.apply(new SQLWherePredicateImpl<>(where1), new SQLWherePredicateImpl<>(where2), new SQLWherePredicateImpl<>(where3), new SQLWherePredicateImpl<>(where4), new SQLWherePredicateImpl<>(where5), new SQLWherePredicateImpl<>(where6), new SQLWherePredicateImpl<>(where7), new SQLWherePredicateImpl<>(where8), new SQLWherePredicateImpl<>(where9));
        });
        return new EasyQueryable9<>(entityQueryable7);
    }

    default <T9> Queryable9<T1, T2, T3, T4, T5, T6, T7, T8, T9> rightJoin(Queryable<T9> joinQueryable, SQLExpression9<SQLWherePredicate<T1>, SQLWherePredicate<T2>, SQLWherePredicate<T3>, SQLWherePredicate<T4>, SQLWherePredicate<T5>, SQLWherePredicate<T6>, SQLWherePredicate<T7>, SQLWherePredicate<T8>, SQLWherePredicate<T9>> on) {
        ClientQueryable9<T1, T2, T3, T4, T5, T6, T7, T8, T9> entityQueryable7 = getClientQueryable8().rightJoin(joinQueryable.getClientQueryable(), (where1, where2, where3, where4, where5, where6, where7, where8, where9) -> {
            on.apply(new SQLWherePredicateImpl<>(where1), new SQLWherePredicateImpl<>(where2), new SQLWherePredicateImpl<>(where3), new SQLWherePredicateImpl<>(where4), new SQLWherePredicateImpl<>(where5), new SQLWherePredicateImpl<>(where6), new SQLWherePredicateImpl<>(where7), new SQLWherePredicateImpl<>(where8), new SQLWherePredicateImpl<>(where9));
        });
        return new EasyQueryable9<>(entityQueryable7);
    }

    default <T9> Queryable9<T1, T2, T3, T4, T5, T6, T7, T8, T9> innerJoin(Class<T9> joinClass, SQLExpression9<SQLWherePredicate<T1>, SQLWherePredicate<T2>, SQLWherePredicate<T3>, SQLWherePredicate<T4>, SQLWherePredicate<T5>, SQLWherePredicate<T6>, SQLWherePredicate<T7>, SQLWherePredicate<T8>, SQLWherePredicate<T9>> on) {
        ClientQueryable9<T1, T2, T3, T4, T5, T6, T7, T8, T9> entityQueryable7 = getClientQueryable8().innerJoin(joinClass, (where1, where2, where3, where4, where5, where6, where7, where8, where9) -> {
            on.apply(new SQLWherePredicateImpl<>(where1), new SQLWherePredicateImpl<>(where2), new SQLWherePredicateImpl<>(where3), new SQLWherePredicateImpl<>(where4), new SQLWherePredicateImpl<>(where5), new SQLWherePredicateImpl<>(where6), new SQLWherePredicateImpl<>(where7), new SQLWherePredicateImpl<>(where8), new SQLWherePredicateImpl<>(where9));
        });
        return new EasyQueryable9<>(entityQueryable7);
    }

    default <T9> Queryable9<T1, T2, T3, T4, T5, T6, T7, T8, T9> innerJoin(Queryable<T9> joinQueryable, SQLExpression9<SQLWherePredicate<T1>, SQLWherePredicate<T2>, SQLWherePredicate<T3>, SQLWherePredicate<T4>, SQLWherePredicate<T5>, SQLWherePredicate<T6>, SQLWherePredicate<T7>, SQLWherePredicate<T8>, SQLWherePredicate<T9>> on) {
        ClientQueryable9<T1, T2, T3, T4, T5, T6, T7, T8, T9> entityQueryable7 = getClientQueryable8().innerJoin(joinQueryable.getClientQueryable(), (where1, where2, where3, where4, where5, where6, where7, where8, where9) -> {
            on.apply(new SQLWherePredicateImpl<>(where1), new SQLWherePredicateImpl<>(where2), new SQLWherePredicateImpl<>(where3), new SQLWherePredicateImpl<>(where4), new SQLWherePredicateImpl<>(where5), new SQLWherePredicateImpl<>(where6), new SQLWherePredicateImpl<>(where7), new SQLWherePredicateImpl<>(where8), new SQLWherePredicateImpl<>(where9));
        });
        return new EasyQueryable9<>(entityQueryable7);
    }

    default <T9> Queryable9<T1, T2, T3, T4, T5, T6, T7, T8, T9> leftJoinMerge(Class<T9> joinClass, SQLExpression1<Tuple9<SQLWherePredicate<T1>, SQLWherePredicate<T2>, SQLWherePredicate<T3>, SQLWherePredicate<T4>, SQLWherePredicate<T5>, SQLWherePredicate<T6>, SQLWherePredicate<T7>, SQLWherePredicate<T8>, SQLWherePredicate<T9>>> on) {
        return leftJoin(joinClass, (t1, t2, t3, t4, t5, t6, t7, t8, t9) -> {
            on.apply(new Tuple9<>(t1, t2, t3, t4, t5, t6, t7, t8, t9));
        });
    }

    default <T9> Queryable9<T1, T2, T3, T4, T5, T6, T7, T8, T9> leftJoinMerge(Queryable<T9> joinQueryable, SQLExpression1<Tuple9<SQLWherePredicate<T1>, SQLWherePredicate<T2>, SQLWherePredicate<T3>, SQLWherePredicate<T4>, SQLWherePredicate<T5>, SQLWherePredicate<T6>, SQLWherePredicate<T7>, SQLWherePredicate<T8>, SQLWherePredicate<T9>>> on) {
        return leftJoin(joinQueryable, (t1, t2, t3, t4, t5, t6, t7, t8, t9) -> {
            on.apply(new Tuple9<>(t1, t2, t3, t4, t5, t6, t7, t8, t9));
        });
    }

    default <T9> Queryable9<T1, T2, T3, T4, T5, T6, T7, T8, T9> rightJoinMerge(Class<T9> joinClass, SQLExpression1<Tuple9<SQLWherePredicate<T1>, SQLWherePredicate<T2>, SQLWherePredicate<T3>, SQLWherePredicate<T4>, SQLWherePredicate<T5>, SQLWherePredicate<T6>, SQLWherePredicate<T7>, SQLWherePredicate<T8>, SQLWherePredicate<T9>>> on) {
        return rightJoin(joinClass, (t1, t2, t3, t4, t5, t6, t7, t8, t9) -> {
            on.apply(new Tuple9<>(t1, t2, t3, t4, t5, t6, t7, t8, t9));
        });
    }

    default <T9> Queryable9<T1, T2, T3, T4, T5, T6, T7, T8, T9> rightJoinMerge(Queryable<T9> joinQueryable, SQLExpression1<Tuple9<SQLWherePredicate<T1>, SQLWherePredicate<T2>, SQLWherePredicate<T3>, SQLWherePredicate<T4>, SQLWherePredicate<T5>, SQLWherePredicate<T6>, SQLWherePredicate<T7>, SQLWherePredicate<T8>, SQLWherePredicate<T9>>> on) {
        return rightJoin(joinQueryable, (t1, t2, t3, t4, t5, t6, t7, t8, t9) -> {
            on.apply(new Tuple9<>(t1, t2, t3, t4, t5, t6, t7, t8, t9));
        });
    }

    default <T9> Queryable9<T1, T2, T3, T4, T5, T6, T7, T8, T9> innerJoinMerge(Class<T9> joinClass, SQLExpression1<Tuple9<SQLWherePredicate<T1>, SQLWherePredicate<T2>, SQLWherePredicate<T3>, SQLWherePredicate<T4>, SQLWherePredicate<T5>, SQLWherePredicate<T6>, SQLWherePredicate<T7>, SQLWherePredicate<T8>, SQLWherePredicate<T9>>> on) {
        return innerJoin(joinClass, (t1, t2, t3, t4, t5, t6, t7, t8, t9) -> {
            on.apply(new Tuple9<>(t1, t2, t3, t4, t5, t6, t7, t8, t9));
        });
    }

    default <T9> Queryable9<T1, T2, T3, T4, T5, T6, T7, T8, T9> innerJoinMerge(Queryable<T9> joinQueryable, SQLExpression1<Tuple9<SQLWherePredicate<T1>, SQLWherePredicate<T2>, SQLWherePredicate<T3>, SQLWherePredicate<T4>, SQLWherePredicate<T5>, SQLWherePredicate<T6>, SQLWherePredicate<T7>, SQLWherePredicate<T8>, SQLWherePredicate<T9>>> on) {
        return innerJoin(joinQueryable, (t1, t2, t3, t4, t5, t6, t7, t8, t9) -> {
            on.apply(new Tuple9<>(t1, t2, t3, t4, t5, t6, t7, t8, t9));
        });
    }
}