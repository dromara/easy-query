package com.easy.query.api4j.select.extension.queryable6;

import com.easy.query.api4j.select.Queryable;
import com.easy.query.api4j.select.Queryable7;
import com.easy.query.api4j.select.impl.EasyQueryable7;
import com.easy.query.api4j.sql.SQLWherePredicate;
import com.easy.query.api4j.sql.impl.SQLWherePredicateImpl;
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
public interface SQLJoinable6<T1, T2, T3, T4, T5, T6> extends ClientQueryable6Available<T1, T2, T3, T4, T5, T6> {

    default <T7> Queryable7<T1, T2, T3, T4, T5, T6, T7> leftJoin(Class<T7> joinClass, SQLExpression7<SQLWherePredicate<T1>, SQLWherePredicate<T2>, SQLWherePredicate<T3>, SQLWherePredicate<T4>, SQLWherePredicate<T5>, SQLWherePredicate<T6>, SQLWherePredicate<T7>> on) {
        ClientQueryable7<T1, T2, T3, T4, T5, T6, T7> entityQueryable7 = getClientQueryable6().leftJoin(joinClass, (where1, where2, where3, where4, where5, where6, where7) -> {
            on.apply(new SQLWherePredicateImpl<>(where1), new SQLWherePredicateImpl<>(where2), new SQLWherePredicateImpl<>(where3), new SQLWherePredicateImpl<>(where4), new SQLWherePredicateImpl<>(where5), new SQLWherePredicateImpl<>(where6), new SQLWherePredicateImpl<>(where7));
        });
        return new EasyQueryable7<>(entityQueryable7);
    }

    default <T7> Queryable7<T1, T2, T3, T4, T5, T6, T7> leftJoin(Queryable<T7> joinQueryable, SQLExpression7<SQLWherePredicate<T1>, SQLWherePredicate<T2>, SQLWherePredicate<T3>, SQLWherePredicate<T4>, SQLWherePredicate<T5>, SQLWherePredicate<T6>, SQLWherePredicate<T7>> on) {
        ClientQueryable7<T1, T2, T3, T4, T5, T6, T7> entityQueryable7 = getClientQueryable6().leftJoin(joinQueryable.getClientQueryable(), (where1, where2, where3, where4, where5, where6, where7) -> {
            on.apply(new SQLWherePredicateImpl<>(where1), new SQLWherePredicateImpl<>(where2), new SQLWherePredicateImpl<>(where3), new SQLWherePredicateImpl<>(where4), new SQLWherePredicateImpl<>(where5), new SQLWherePredicateImpl<>(where6), new SQLWherePredicateImpl<>(where7));
        });
        return new EasyQueryable7<>(entityQueryable7);
    }
    default <T7> Queryable7<T1, T2, T3, T4, T5, T6, T7> rightJoin(Class<T7> joinClass, SQLExpression7<SQLWherePredicate<T1>, SQLWherePredicate<T2>, SQLWherePredicate<T3>, SQLWherePredicate<T4>, SQLWherePredicate<T5>, SQLWherePredicate<T6>, SQLWherePredicate<T7>> on) {
        ClientQueryable7<T1, T2, T3, T4, T5, T6, T7> entityQueryable7 = getClientQueryable6().rightJoin(joinClass, (where1, where2, where3, where4, where5, where6, where7) -> {
            on.apply(new SQLWherePredicateImpl<>(where1), new SQLWherePredicateImpl<>(where2), new SQLWherePredicateImpl<>(where3), new SQLWherePredicateImpl<>(where4), new SQLWherePredicateImpl<>(where5), new SQLWherePredicateImpl<>(where6), new SQLWherePredicateImpl<>(where7));
        });
        return new EasyQueryable7<>(entityQueryable7);
    }

    default <T7> Queryable7<T1, T2, T3, T4, T5, T6, T7> rightJoin(Queryable<T7> joinQueryable, SQLExpression7<SQLWherePredicate<T1>, SQLWherePredicate<T2>, SQLWherePredicate<T3>, SQLWherePredicate<T4>, SQLWherePredicate<T5>, SQLWherePredicate<T6>, SQLWherePredicate<T7>> on) {
        ClientQueryable7<T1, T2, T3, T4, T5, T6, T7> entityQueryable7 = getClientQueryable6().rightJoin(joinQueryable.getClientQueryable(), (where1, where2, where3, where4, where5, where6, where7) -> {
            on.apply(new SQLWherePredicateImpl<>(where1), new SQLWherePredicateImpl<>(where2), new SQLWherePredicateImpl<>(where3), new SQLWherePredicateImpl<>(where4), new SQLWherePredicateImpl<>(where5), new SQLWherePredicateImpl<>(where6), new SQLWherePredicateImpl<>(where7));
        });
        return new EasyQueryable7<>(entityQueryable7);
    }

    default <T7> Queryable7<T1, T2, T3, T4, T5, T6, T7> innerJoin(Class<T7> joinClass, SQLExpression7<SQLWherePredicate<T1>, SQLWherePredicate<T2>, SQLWherePredicate<T3>, SQLWherePredicate<T4>, SQLWherePredicate<T5>, SQLWherePredicate<T6>, SQLWherePredicate<T7>> on) {
        ClientQueryable7<T1, T2, T3, T4, T5, T6, T7> entityQueryable7 = getClientQueryable6().innerJoin(joinClass, (where1, where2, where3, where4, where5, where6, where7) -> {
            on.apply(new SQLWherePredicateImpl<>(where1), new SQLWherePredicateImpl<>(where2), new SQLWherePredicateImpl<>(where3), new SQLWherePredicateImpl<>(where4), new SQLWherePredicateImpl<>(where5), new SQLWherePredicateImpl<>(where6), new SQLWherePredicateImpl<>(where7));
        });
        return new EasyQueryable7<>(entityQueryable7);
    }

    default <T7> Queryable7<T1, T2, T3, T4, T5, T6, T7> innerJoin(Queryable<T7> joinQueryable, SQLExpression7<SQLWherePredicate<T1>, SQLWherePredicate<T2>, SQLWherePredicate<T3>, SQLWherePredicate<T4>, SQLWherePredicate<T5>, SQLWherePredicate<T6>, SQLWherePredicate<T7>> on) {
        ClientQueryable7<T1, T2, T3, T4, T5, T6, T7> entityQueryable7 = getClientQueryable6().innerJoin(joinQueryable.getClientQueryable(), (where1, where2, where3, where4, where5, where6, where7) -> {
            on.apply(new SQLWherePredicateImpl<>(where1), new SQLWherePredicateImpl<>(where2), new SQLWherePredicateImpl<>(where3), new SQLWherePredicateImpl<>(where4), new SQLWherePredicateImpl<>(where5), new SQLWherePredicateImpl<>(where6), new SQLWherePredicateImpl<>(where7));
        });
        return new EasyQueryable7<>(entityQueryable7);
    }

    default <T7> Queryable7<T1, T2, T3, T4, T5, T6, T7> leftJoinMerge(Class<T7> joinClass, SQLExpression1<Tuple7<SQLWherePredicate<T1>, SQLWherePredicate<T2>, SQLWherePredicate<T3>, SQLWherePredicate<T4>, SQLWherePredicate<T5>, SQLWherePredicate<T6>, SQLWherePredicate<T7>>> on) {
        return leftJoin(joinClass, (t1, t2, t3, t4, t5, t6, t7) -> {
            on.apply(new Tuple7<>(t1, t2, t3, t4, t5, t6, t7));
        });
    }

    default <T7> Queryable7<T1, T2, T3, T4, T5, T6, T7> leftJoinMerge(Queryable<T7> joinQueryable, SQLExpression1<Tuple7<SQLWherePredicate<T1>, SQLWherePredicate<T2>, SQLWherePredicate<T3>, SQLWherePredicate<T4>, SQLWherePredicate<T5>, SQLWherePredicate<T6>, SQLWherePredicate<T7>>> on) {
        return leftJoin(joinQueryable, (t1, t2, t3, t4, t5, t6, t7) -> {
            on.apply(new Tuple7<>(t1, t2, t3, t4, t5, t6, t7));
        });
    }

    default <T7> Queryable7<T1, T2, T3, T4, T5, T6, T7> rightJoinMerge(Class<T7> joinClass, SQLExpression1<Tuple7<SQLWherePredicate<T1>, SQLWherePredicate<T2>, SQLWherePredicate<T3>, SQLWherePredicate<T4>, SQLWherePredicate<T5>, SQLWherePredicate<T6>, SQLWherePredicate<T7>>> on) {
        return rightJoin(joinClass, (t1, t2, t3, t4, t5, t6, t7) -> {
            on.apply(new Tuple7<>(t1, t2, t3, t4, t5, t6, t7));
        });
    }

    default <T7> Queryable7<T1, T2, T3, T4, T5, T6, T7> rightJoinMerge(Queryable<T7> joinQueryable, SQLExpression1<Tuple7<SQLWherePredicate<T1>, SQLWherePredicate<T2>, SQLWherePredicate<T3>, SQLWherePredicate<T4>, SQLWherePredicate<T5>, SQLWherePredicate<T6>, SQLWherePredicate<T7>>> on) {
        return rightJoin(joinQueryable, (t1, t2, t3, t4, t5, t6, t7) -> {
            on.apply(new Tuple7<>(t1, t2, t3, t4, t5, t6, t7));
        });
    }

    default <T7> Queryable7<T1, T2, T3, T4, T5, T6, T7> innerJoinMerge(Class<T7> joinClass, SQLExpression1<Tuple7<SQLWherePredicate<T1>, SQLWherePredicate<T2>, SQLWherePredicate<T3>, SQLWherePredicate<T4>, SQLWherePredicate<T5>, SQLWherePredicate<T6>, SQLWherePredicate<T7>>> on) {
        return innerJoin(joinClass, (t1, t2, t3, t4, t5, t6, t7) -> {
            on.apply(new Tuple7<>(t1, t2, t3, t4, t5, t6, t7));
        });
    }

    default <T7> Queryable7<T1, T2, T3, T4, T5, T6, T7> innerJoinMerge(Queryable<T7> joinQueryable, SQLExpression1<Tuple7<SQLWherePredicate<T1>, SQLWherePredicate<T2>, SQLWherePredicate<T3>, SQLWherePredicate<T4>, SQLWherePredicate<T5>, SQLWherePredicate<T6>, SQLWherePredicate<T7>>> on) {
        return innerJoin(joinQueryable, (t1, t2, t3, t4, t5, t6, t7) -> {
            on.apply(new Tuple7<>(t1, t2, t3, t4, t5, t6, t7));
        });
    }
}