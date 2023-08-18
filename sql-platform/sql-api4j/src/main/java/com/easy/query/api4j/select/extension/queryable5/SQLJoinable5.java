package com.easy.query.api4j.select.extension.queryable5;

import com.easy.query.api4j.select.Queryable;
import com.easy.query.api4j.select.Queryable6;
import com.easy.query.api4j.select.impl.EasyQueryable6;
import com.easy.query.api4j.sql.SQLWherePredicate;
import com.easy.query.api4j.sql.impl.SQLWherePredicateImpl;
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
public interface SQLJoinable5<T1, T2, T3, T4, T5> extends ClientQueryable5Available<T1, T2, T3, T4, T5> {

    default <T6> Queryable6<T1, T2, T3, T4, T5, T6> leftJoin(Class<T6> joinClass, SQLExpression6<SQLWherePredicate<T1>, SQLWherePredicate<T2>, SQLWherePredicate<T3>, SQLWherePredicate<T4>, SQLWherePredicate<T5>, SQLWherePredicate<T6>> on) {
        ClientQueryable6<T1, T2, T3, T4, T5, T6> entityQueryable6 = getClientQueryable5().leftJoin(joinClass, (where1, where2, where3, where4, where5, where6) -> {
            on.apply(new SQLWherePredicateImpl<>(where1), new SQLWherePredicateImpl<>(where2), new SQLWherePredicateImpl<>(where3), new SQLWherePredicateImpl<>(where4), new SQLWherePredicateImpl<>(where5), new SQLWherePredicateImpl<>(where6));
        });
        return new EasyQueryable6<>(entityQueryable6);
    }

    default <T6> Queryable6<T1, T2, T3, T4, T5, T6> leftJoin(Queryable<T6> joinQueryable, SQLExpression6<SQLWherePredicate<T1>, SQLWherePredicate<T2>, SQLWherePredicate<T3>, SQLWherePredicate<T4>, SQLWherePredicate<T5>, SQLWherePredicate<T6>> on) {
        ClientQueryable6<T1, T2, T3, T4, T5, T6> entityQueryable6 = getClientQueryable5().leftJoin(joinQueryable.getClientQueryable(), (where1, where2, where3, where4, where5, where6) -> {
            on.apply(new SQLWherePredicateImpl<>(where1), new SQLWherePredicateImpl<>(where2), new SQLWherePredicateImpl<>(where3), new SQLWherePredicateImpl<>(where4), new SQLWherePredicateImpl<>(where5), new SQLWherePredicateImpl<>(where6));
        });
        return new EasyQueryable6<>(entityQueryable6);
    }

    default <T6> Queryable6<T1, T2, T3, T4, T5, T6> rightJoin(Class<T6> joinClass, SQLExpression6<SQLWherePredicate<T1>, SQLWherePredicate<T2>, SQLWherePredicate<T3>, SQLWherePredicate<T4>, SQLWherePredicate<T5>, SQLWherePredicate<T6>> on) {
        ClientQueryable6<T1, T2, T3, T4, T5, T6> entityQueryable6 = getClientQueryable5().rightJoin(joinClass, (where1, where2, where3, where4, where5, where6) -> {
            on.apply(new SQLWherePredicateImpl<>(where1), new SQLWherePredicateImpl<>(where2), new SQLWherePredicateImpl<>(where3), new SQLWherePredicateImpl<>(where4), new SQLWherePredicateImpl<>(where5), new SQLWherePredicateImpl<>(where6));
        });
        return new EasyQueryable6<>(entityQueryable6);
    }

    default <T6> Queryable6<T1, T2, T3, T4, T5, T6> rightJoin(Queryable<T6> joinQueryable, SQLExpression6<SQLWherePredicate<T1>, SQLWherePredicate<T2>, SQLWherePredicate<T3>, SQLWherePredicate<T4>, SQLWherePredicate<T5>, SQLWherePredicate<T6>> on) {
        ClientQueryable6<T1, T2, T3, T4, T5, T6> entityQueryable6 = getClientQueryable5().rightJoin(joinQueryable.getClientQueryable(), (where1, where2, where3, where4, where5, where6) -> {
            on.apply(new SQLWherePredicateImpl<>(where1), new SQLWherePredicateImpl<>(where2), new SQLWherePredicateImpl<>(where3), new SQLWherePredicateImpl<>(where4), new SQLWherePredicateImpl<>(where5), new SQLWherePredicateImpl<>(where6));
        });
        return new EasyQueryable6<>(entityQueryable6);
    }

    default <T6> Queryable6<T1, T2, T3, T4, T5, T6> innerJoin(Class<T6> joinClass, SQLExpression6<SQLWherePredicate<T1>, SQLWherePredicate<T2>, SQLWherePredicate<T3>, SQLWherePredicate<T4>, SQLWherePredicate<T5>, SQLWherePredicate<T6>> on) {
        ClientQueryable6<T1, T2, T3, T4, T5, T6> entityQueryable6 = getClientQueryable5().innerJoin(joinClass, (where1, where2, where3, where4, where5, where6) -> {
            on.apply(new SQLWherePredicateImpl<>(where1), new SQLWherePredicateImpl<>(where2), new SQLWherePredicateImpl<>(where3), new SQLWherePredicateImpl<>(where4), new SQLWherePredicateImpl<>(where5), new SQLWherePredicateImpl<>(where6));
        });
        return new EasyQueryable6<>(entityQueryable6);
    }

    default <T6> Queryable6<T1, T2, T3, T4, T5, T6> innerJoin(Queryable<T6> joinQueryable, SQLExpression6<SQLWherePredicate<T1>, SQLWherePredicate<T2>, SQLWherePredicate<T3>, SQLWherePredicate<T4>, SQLWherePredicate<T5>, SQLWherePredicate<T6>> on) {
        ClientQueryable6<T1, T2, T3, T4, T5, T6> entityQueryable6 = getClientQueryable5().innerJoin(joinQueryable.getClientQueryable(), (where1, where2, where3, where4, where5, where6) -> {
            on.apply(new SQLWherePredicateImpl<>(where1), new SQLWherePredicateImpl<>(where2), new SQLWherePredicateImpl<>(where3), new SQLWherePredicateImpl<>(where4), new SQLWherePredicateImpl<>(where5), new SQLWherePredicateImpl<>(where6));
        });
        return new EasyQueryable6<>(entityQueryable6);
    }

    default <T6> Queryable6<T1, T2, T3, T4, T5, T6> leftJoinMerge(Class<T6> joinClass, SQLExpression1<Tuple6<SQLWherePredicate<T1>, SQLWherePredicate<T2>, SQLWherePredicate<T3>, SQLWherePredicate<T4>, SQLWherePredicate<T5>, SQLWherePredicate<T6>>> on) {
        return leftJoin(joinClass, (t, t1, t2, t3, t4, t5) -> {
            on.apply(new Tuple6<>(t, t1, t2, t3, t4, t5));
        });
    }

    default <T6> Queryable6<T1, T2, T3, T4, T5, T6> leftJoinMerge(Queryable<T6> joinQueryable, SQLExpression1<Tuple6<SQLWherePredicate<T1>, SQLWherePredicate<T2>, SQLWherePredicate<T3>, SQLWherePredicate<T4>, SQLWherePredicate<T5>, SQLWherePredicate<T6>>> on) {
        return leftJoin(joinQueryable, (t, t1, t2, t3, t4, t5) -> {
            on.apply(new Tuple6<>(t, t1, t2, t3, t4, t5));
        });
    }

    default <T6> Queryable6<T1, T2, T3, T4, T5, T6> rightJoinMerge(Class<T6> joinClass, SQLExpression1<Tuple6<SQLWherePredicate<T1>, SQLWherePredicate<T2>, SQLWherePredicate<T3>, SQLWherePredicate<T4>, SQLWherePredicate<T5>, SQLWherePredicate<T6>>> on) {
        return rightJoin(joinClass, (t, t1, t2, t3, t4, t5) -> {
            on.apply(new Tuple6<>(t, t1, t2, t3, t4, t5));
        });
    }

    default <T6> Queryable6<T1, T2, T3, T4, T5, T6> rightJoinMerge(Queryable<T6> joinQueryable, SQLExpression1<Tuple6<SQLWherePredicate<T1>, SQLWherePredicate<T2>, SQLWherePredicate<T3>, SQLWherePredicate<T4>, SQLWherePredicate<T5>, SQLWherePredicate<T6>>> on) {
        return rightJoin(joinQueryable, (t, t1, t2, t3, t4, t5) -> {
            on.apply(new Tuple6<>(t, t1, t2, t3, t4, t5));
        });
    }

    default <T6> Queryable6<T1, T2, T3, T4, T5, T6> innerJoinMerge(Class<T6> joinClass, SQLExpression1<Tuple6<SQLWherePredicate<T1>, SQLWherePredicate<T2>, SQLWherePredicate<T3>, SQLWherePredicate<T4>, SQLWherePredicate<T5>, SQLWherePredicate<T6>>> on) {
        return innerJoin(joinClass, (t, t1, t2, t3, t4, t5) -> {
            on.apply(new Tuple6<>(t, t1, t2, t3, t4, t5));
        });
    }

    default <T6> Queryable6<T1, T2, T3, T4, T5, T6> innerJoinMerge(Queryable<T6> joinQueryable, SQLExpression1<Tuple6<SQLWherePredicate<T1>, SQLWherePredicate<T2>, SQLWherePredicate<T3>, SQLWherePredicate<T4>, SQLWherePredicate<T5>, SQLWherePredicate<T6>>> on) {
        return innerJoin(joinQueryable, (t, t1, t2, t3, t4, t5) -> {
            on.apply(new Tuple6<>(t, t1, t2, t3, t4, t5));
        });
    }
}

