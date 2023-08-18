package com.easy.query.api4j.select.extension.queryable9;

import com.easy.query.api4j.select.Queryable;
import com.easy.query.api4j.select.Queryable10;
import com.easy.query.api4j.select.impl.EasyQueryable10;
import com.easy.query.api4j.sql.SQLWherePredicate;
import com.easy.query.api4j.sql.impl.SQLWherePredicateImpl;
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
public interface SQLJoinable9<T1, T2, T3, T4, T5, T6, T7, T8, T9> extends ClientQueryable9Available<T1, T2, T3, T4, T5, T6, T7, T8, T9> {

    default <T10> Queryable10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> leftJoin(Class<T10> joinClass, SQLExpression10<SQLWherePredicate<T1>, SQLWherePredicate<T2>, SQLWherePredicate<T3>, SQLWherePredicate<T4>, SQLWherePredicate<T5>, SQLWherePredicate<T6>, SQLWherePredicate<T7>, SQLWherePredicate<T8>, SQLWherePredicate<T9>, SQLWherePredicate<T10>> on) {
        ClientQueryable10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> entityQueryable7 = getClientQueryable9().leftJoin(joinClass, (where1, where2, where3, where4, where5, where6, where7, where8, where9, where10) -> {
            on.apply(new SQLWherePredicateImpl<>(where1), new SQLWherePredicateImpl<>(where2), new SQLWherePredicateImpl<>(where3), new SQLWherePredicateImpl<>(where4), new SQLWherePredicateImpl<>(where5), new SQLWherePredicateImpl<>(where6), new SQLWherePredicateImpl<>(where7), new SQLWherePredicateImpl<>(where8), new SQLWherePredicateImpl<>(where9), new SQLWherePredicateImpl<>(where10));
        });
        return new EasyQueryable10<>(entityQueryable7);
    }

    default <T10> Queryable10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> leftJoin(Queryable<T10> joinQueryable, SQLExpression10<SQLWherePredicate<T1>, SQLWherePredicate<T2>, SQLWherePredicate<T3>, SQLWherePredicate<T4>, SQLWherePredicate<T5>, SQLWherePredicate<T6>, SQLWherePredicate<T7>, SQLWherePredicate<T8>, SQLWherePredicate<T9>, SQLWherePredicate<T10>> on) {
        ClientQueryable10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> entityQueryable7 = getClientQueryable9().leftJoin(joinQueryable.getClientQueryable(), (where1, where2, where3, where4, where5, where6, where7, where8, where9, where10) -> {
            on.apply(new SQLWherePredicateImpl<>(where1), new SQLWherePredicateImpl<>(where2), new SQLWherePredicateImpl<>(where3), new SQLWherePredicateImpl<>(where4), new SQLWherePredicateImpl<>(where5), new SQLWherePredicateImpl<>(where6), new SQLWherePredicateImpl<>(where7), new SQLWherePredicateImpl<>(where8), new SQLWherePredicateImpl<>(where9), new SQLWherePredicateImpl<>(where10));
        });
        return new EasyQueryable10<>(entityQueryable7);
    }

    default <T10> Queryable10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> rightJoin(Class<T10> joinClass, SQLExpression10<SQLWherePredicate<T1>, SQLWherePredicate<T2>, SQLWherePredicate<T3>, SQLWherePredicate<T4>, SQLWherePredicate<T5>, SQLWherePredicate<T6>, SQLWherePredicate<T7>, SQLWherePredicate<T8>, SQLWherePredicate<T9>, SQLWherePredicate<T10>> on) {
        ClientQueryable10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> entityQueryable7 = getClientQueryable9().rightJoin(joinClass, (where1, where2, where3, where4, where5, where6, where7, where8, where9, where10) -> {
            on.apply(new SQLWherePredicateImpl<>(where1), new SQLWherePredicateImpl<>(where2), new SQLWherePredicateImpl<>(where3), new SQLWherePredicateImpl<>(where4), new SQLWherePredicateImpl<>(where5), new SQLWherePredicateImpl<>(where6), new SQLWherePredicateImpl<>(where7), new SQLWherePredicateImpl<>(where8), new SQLWherePredicateImpl<>(where9), new SQLWherePredicateImpl<>(where10));
        });
        return new EasyQueryable10<>(entityQueryable7);
    }

    default <T10> Queryable10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> rightJoin(Queryable<T10> joinQueryable, SQLExpression10<SQLWherePredicate<T1>, SQLWherePredicate<T2>, SQLWherePredicate<T3>, SQLWherePredicate<T4>, SQLWherePredicate<T5>, SQLWherePredicate<T6>, SQLWherePredicate<T7>, SQLWherePredicate<T8>, SQLWherePredicate<T9>, SQLWherePredicate<T10>> on) {
        ClientQueryable10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> entityQueryable7 = getClientQueryable9().rightJoin(joinQueryable.getClientQueryable(), (where1, where2, where3, where4, where5, where6, where7, where8, where9, where10) -> {
            on.apply(new SQLWherePredicateImpl<>(where1), new SQLWherePredicateImpl<>(where2), new SQLWherePredicateImpl<>(where3), new SQLWherePredicateImpl<>(where4), new SQLWherePredicateImpl<>(where5), new SQLWherePredicateImpl<>(where6), new SQLWherePredicateImpl<>(where7), new SQLWherePredicateImpl<>(where8), new SQLWherePredicateImpl<>(where9), new SQLWherePredicateImpl<>(where10));
        });
        return new EasyQueryable10<>(entityQueryable7);
    }

    default <T10> Queryable10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> innerJoin(Class<T10> joinClass, SQLExpression10<SQLWherePredicate<T1>, SQLWherePredicate<T2>, SQLWherePredicate<T3>, SQLWherePredicate<T4>, SQLWherePredicate<T5>, SQLWherePredicate<T6>, SQLWherePredicate<T7>, SQLWherePredicate<T8>, SQLWherePredicate<T9>, SQLWherePredicate<T10>> on) {
        ClientQueryable10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> entityQueryable7 = getClientQueryable9().innerJoin(joinClass, (where1, where2, where3, where4, where5, where6, where7, where8, where9, where10) -> {
            on.apply(new SQLWherePredicateImpl<>(where1), new SQLWherePredicateImpl<>(where2), new SQLWherePredicateImpl<>(where3), new SQLWherePredicateImpl<>(where4), new SQLWherePredicateImpl<>(where5), new SQLWherePredicateImpl<>(where6), new SQLWherePredicateImpl<>(where7), new SQLWherePredicateImpl<>(where8), new SQLWherePredicateImpl<>(where9), new SQLWherePredicateImpl<>(where10));
        });
        return new EasyQueryable10<>(entityQueryable7);
    }

    default <T10> Queryable10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> innerJoin(Queryable<T10> joinQueryable, SQLExpression10<SQLWherePredicate<T1>, SQLWherePredicate<T2>, SQLWherePredicate<T3>, SQLWherePredicate<T4>, SQLWherePredicate<T5>, SQLWherePredicate<T6>, SQLWherePredicate<T7>, SQLWherePredicate<T8>, SQLWherePredicate<T9>, SQLWherePredicate<T10>> on) {
        ClientQueryable10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> entityQueryable7 = getClientQueryable9().innerJoin(joinQueryable.getClientQueryable(), (where1, where2, where3, where4, where5, where6, where7, where8, where9, where10) -> {
            on.apply(new SQLWherePredicateImpl<>(where1), new SQLWherePredicateImpl<>(where2), new SQLWherePredicateImpl<>(where3), new SQLWherePredicateImpl<>(where4), new SQLWherePredicateImpl<>(where5), new SQLWherePredicateImpl<>(where6), new SQLWherePredicateImpl<>(where7), new SQLWherePredicateImpl<>(where8), new SQLWherePredicateImpl<>(where9), new SQLWherePredicateImpl<>(where10));
        });
        return new EasyQueryable10<>(entityQueryable7);
    }

    default <T10> Queryable10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> leftJoinMerge(Class<T10> joinClass, SQLExpression1<Tuple10<SQLWherePredicate<T1>, SQLWherePredicate<T2>, SQLWherePredicate<T3>, SQLWherePredicate<T4>, SQLWherePredicate<T5>, SQLWherePredicate<T6>, SQLWherePredicate<T7>, SQLWherePredicate<T8>, SQLWherePredicate<T9>, SQLWherePredicate<T10>>> on) {
        return leftJoin(joinClass, (t1, t2, t3, t4, t5, t6, t7, t8, t9, t10) -> {
            on.apply(new Tuple10<>(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10));
        });
    }

    default <T10> Queryable10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> leftJoinMerge(Queryable<T10> joinQueryable, SQLExpression1<Tuple10<SQLWherePredicate<T1>, SQLWherePredicate<T2>, SQLWherePredicate<T3>, SQLWherePredicate<T4>, SQLWherePredicate<T5>, SQLWherePredicate<T6>, SQLWherePredicate<T7>, SQLWherePredicate<T8>, SQLWherePredicate<T9>, SQLWherePredicate<T10>>> on) {
        return leftJoin(joinQueryable, (t1, t2, t3, t4, t5, t6, t7, t8, t9, t10) -> {
            on.apply(new Tuple10<>(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10));
        });
    }

    default <T10> Queryable10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> rightJoinMerge(Class<T10> joinClass, SQLExpression1<Tuple10<SQLWherePredicate<T1>, SQLWherePredicate<T2>, SQLWherePredicate<T3>, SQLWherePredicate<T4>, SQLWherePredicate<T5>, SQLWherePredicate<T6>, SQLWherePredicate<T7>, SQLWherePredicate<T8>, SQLWherePredicate<T9>, SQLWherePredicate<T10>>> on) {
        return rightJoin(joinClass, (t1, t2, t3, t4, t5, t6, t7, t8, t9, t10) -> {
            on.apply(new Tuple10<>(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10));
        });
    }

    default <T10> Queryable10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> rightJoinMerge(Queryable<T10> joinQueryable, SQLExpression1<Tuple10<SQLWherePredicate<T1>, SQLWherePredicate<T2>, SQLWherePredicate<T3>, SQLWherePredicate<T4>, SQLWherePredicate<T5>, SQLWherePredicate<T6>, SQLWherePredicate<T7>, SQLWherePredicate<T8>, SQLWherePredicate<T9>, SQLWherePredicate<T10>>> on) {
        return rightJoin(joinQueryable, (t1, t2, t3, t4, t5, t6, t7, t8, t9, t10) -> {
            on.apply(new Tuple10<>(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10));
        });
    }

    default <T10> Queryable10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> innerJoinMerge(Class<T10> joinClass, SQLExpression1<Tuple10<SQLWherePredicate<T1>, SQLWherePredicate<T2>, SQLWherePredicate<T3>, SQLWherePredicate<T4>, SQLWherePredicate<T5>, SQLWherePredicate<T6>, SQLWherePredicate<T7>, SQLWherePredicate<T8>, SQLWherePredicate<T9>, SQLWherePredicate<T10>>> on) {
        return innerJoin(joinClass, (t1, t2, t3, t4, t5, t6, t7, t8, t9, t10) -> {
            on.apply(new Tuple10<>(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10));
        });
    }

    default <T10> Queryable10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> innerJoinMerge(Queryable<T10> joinQueryable, SQLExpression1<Tuple10<SQLWherePredicate<T1>, SQLWherePredicate<T2>, SQLWherePredicate<T3>, SQLWherePredicate<T4>, SQLWherePredicate<T5>, SQLWherePredicate<T6>, SQLWherePredicate<T7>, SQLWherePredicate<T8>, SQLWherePredicate<T9>, SQLWherePredicate<T10>>> on) {
        return innerJoin(joinQueryable, (t1, t2, t3, t4, t5, t6, t7, t8, t9, t10) -> {
            on.apply(new Tuple10<>(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10));
        });
    }
}