package com.easy.query.core.basic.api.select.extension.queryable2;

import com.easy.query.core.basic.api.select.ClientQueryable;
import com.easy.query.core.basic.api.select.ClientQueryable3;
import com.easy.query.core.common.tuple.Tuple3;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.lambda.SQLExpression3;
import com.easy.query.core.expression.parser.core.base.WherePredicate;

/**
 * create time 2023/8/16 09:11
 * 文件说明
 *
 * @author xuejiaming
 */
public interface SQLJoin2Extension<T1, T2> {
    <T3> ClientQueryable3<T1, T2, T3> leftJoin(Class<T3> joinClass, SQLExpression3<WherePredicate<T1>, WherePredicate<T2>, WherePredicate<T3>> on);

    <T3> ClientQueryable3<T1, T2, T3> leftJoin(ClientQueryable<T3> joinQueryable, SQLExpression3<WherePredicate<T1>, WherePredicate<T2>, WherePredicate<T3>> on);

    <T3> ClientQueryable3<T1, T2, T3> rightJoin(Class<T3> joinClass, SQLExpression3<WherePredicate<T1>, WherePredicate<T2>, WherePredicate<T3>> on);

    <T3> ClientQueryable3<T1, T2, T3> rightJoin(ClientQueryable<T3> joinQueryable, SQLExpression3<WherePredicate<T1>, WherePredicate<T2>, WherePredicate<T3>> on);

    <T3> ClientQueryable3<T1, T2, T3> innerJoin(Class<T3> joinClass, SQLExpression3<WherePredicate<T1>, WherePredicate<T2>, WherePredicate<T3>> on);

    <T3> ClientQueryable3<T1, T2, T3> innerJoin(ClientQueryable<T3> joinQueryable, SQLExpression3<WherePredicate<T1>, WherePredicate<T2>, WherePredicate<T3>> on);


    default <T3> ClientQueryable3<T1, T2, T3> leftJoinMerge(Class<T3> joinClass, SQLExpression1<Tuple3<WherePredicate<T1>, WherePredicate<T2>, WherePredicate<T3>>> on) {
        return leftJoin(joinClass, (t, t1, t2) -> {
            on.apply(new Tuple3<>(t, t1, t2));
        });
    }

    default <T3> ClientQueryable3<T1, T2, T3> leftJoinMerge(ClientQueryable<T3> joinQueryable, SQLExpression1<Tuple3<WherePredicate<T1>, WherePredicate<T2>, WherePredicate<T3>>> on) {
        return leftJoin(joinQueryable, (t, t1, t2) -> {
            on.apply(new Tuple3<>(t, t1, t2));
        });
    }

    default <T3> ClientQueryable3<T1, T2, T3> rightJoinMerge(Class<T3> joinClass, SQLExpression1<Tuple3<WherePredicate<T1>, WherePredicate<T2>, WherePredicate<T3>>> on) {
        return rightJoin(joinClass, (t, t1, t2) -> {
            on.apply(new Tuple3<>(t, t1, t2));
        });
    }

    default <T3> ClientQueryable3<T1, T2, T3> rightJoinMerge(ClientQueryable<T3> joinQueryable, SQLExpression1<Tuple3<WherePredicate<T1>, WherePredicate<T2>, WherePredicate<T3>>> on) {
        return rightJoin(joinQueryable, (t, t1, t2) -> {
            on.apply(new Tuple3<>(t, t1, t2));
        });
    }

    default <T3> ClientQueryable3<T1, T2, T3> innerJoinMerge(Class<T3> joinClass, SQLExpression1<Tuple3<WherePredicate<T1>, WherePredicate<T2>, WherePredicate<T3>>> on) {
        return innerJoin(joinClass, (t, t1, t2) -> {
            on.apply(new Tuple3<>(t, t1, t2));
        });
    }

    default <T3> ClientQueryable3<T1, T2, T3> innerJoinMerge(ClientQueryable<T3> joinQueryable, SQLExpression1<Tuple3<WherePredicate<T1>, WherePredicate<T2>, WherePredicate<T3>>> on) {
        return innerJoin(joinQueryable, (t, t1, t2) -> {
            on.apply(new Tuple3<>(t, t1, t2));
        });
    }

}
