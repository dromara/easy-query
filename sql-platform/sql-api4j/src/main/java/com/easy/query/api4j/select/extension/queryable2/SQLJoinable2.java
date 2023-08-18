package com.easy.query.api4j.select.extension.queryable2;

import com.easy.query.api4j.select.Queryable;
import com.easy.query.api4j.select.Queryable3;
import com.easy.query.api4j.select.impl.EasyQueryable3;
import com.easy.query.api4j.sql.SQLWherePredicate;
import com.easy.query.api4j.sql.impl.SQLWherePredicateImpl;
import com.easy.query.core.basic.api.select.ClientQueryable3;
import com.easy.query.core.common.tuple.Tuple3;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.lambda.SQLExpression3;

/**
 * create time 2023/8/16 09:11
 * 文件说明
 *
 * @author xuejiaming
 */
public interface SQLJoinable2<T1, T2> extends ClientQueryable2Available<T1, T2> {

    /**
     * <pre>{@code
     * leftJoin(Entity2.class, (t1, t2,t3) -> t.eq(t2, Entity::getId, Entity2::getId))
     * }</pre>
     * t代表from的表,t1代表第一次join的表,t2代表第二次join的表
     *
     * @param joinClass 和哪张表进行join
     * @param on        条件
     * @param <T3>
     * @return 返回可查询的表达式支持3表参数
     */
    default <T3> Queryable3<T1, T2, T3> leftJoin(Class<T3> joinClass, SQLExpression3<SQLWherePredicate<T1>, SQLWherePredicate<T2>, SQLWherePredicate<T3>> on) {
        ClientQueryable3<T1, T2, T3> entityQueryable3 = getClientQueryable2().leftJoin(joinClass, (where1, where2, where3) -> {
            on.apply(new SQLWherePredicateImpl<>(where1), new SQLWherePredicateImpl<>(where2), new SQLWherePredicateImpl<>(where3));
        });
        return new EasyQueryable3<>(entityQueryable3);
    }

    default <T3> Queryable3<T1, T2, T3> leftJoin(Queryable<T3> joinQueryable, SQLExpression3<SQLWherePredicate<T1>, SQLWherePredicate<T2>, SQLWherePredicate<T3>> on) {
        ClientQueryable3<T1, T2, T3> entityQueryable3 = getClientQueryable2().leftJoin(joinQueryable.getClientQueryable(), (where1, where2, where3) -> {
            on.apply(new SQLWherePredicateImpl<>(where1), new SQLWherePredicateImpl<>(where2), new SQLWherePredicateImpl<>(where3));
        });
        return new EasyQueryable3<>(entityQueryable3);
    }

    default <T3> Queryable3<T1, T2, T3> rightJoin(Class<T3> joinClass, SQLExpression3<SQLWherePredicate<T1>, SQLWherePredicate<T2>, SQLWherePredicate<T3>> on) {
        ClientQueryable3<T1, T2, T3> entityQueryable3 = getClientQueryable2().rightJoin(joinClass, (where1, where2, where3) -> {
            on.apply(new SQLWherePredicateImpl<>(where1), new SQLWherePredicateImpl<>(where2), new SQLWherePredicateImpl<>(where3));
        });
        return new EasyQueryable3<>(entityQueryable3);
    }

    default <T3> Queryable3<T1, T2, T3> rightJoin(Queryable<T3> joinQueryable, SQLExpression3<SQLWherePredicate<T1>, SQLWherePredicate<T2>, SQLWherePredicate<T3>> on) {
        ClientQueryable3<T1, T2, T3> entityQueryable3 = getClientQueryable2().rightJoin(joinQueryable.getClientQueryable(), (where1, where2, where3) -> {
            on.apply(new SQLWherePredicateImpl<>(where1), new SQLWherePredicateImpl<>(where2), new SQLWherePredicateImpl<>(where3));
        });
        return new EasyQueryable3<>(entityQueryable3);
    }

    default <T3> Queryable3<T1, T2, T3> innerJoin(Class<T3> joinClass, SQLExpression3<SQLWherePredicate<T1>, SQLWherePredicate<T2>, SQLWherePredicate<T3>> on) {
        ClientQueryable3<T1, T2, T3> entityQueryable3 = getClientQueryable2().innerJoin(joinClass, (where1, where2, where3) -> {
            on.apply(new SQLWherePredicateImpl<>(where1), new SQLWherePredicateImpl<>(where2), new SQLWherePredicateImpl<>(where3));
        });
        return new EasyQueryable3<>(entityQueryable3);
    }

    default <T3> Queryable3<T1, T2, T3> innerJoin(Queryable<T3> joinQueryable, SQLExpression3<SQLWherePredicate<T1>, SQLWherePredicate<T2>, SQLWherePredicate<T3>> on) {
        ClientQueryable3<T1, T2, T3> entityQueryable3 = getClientQueryable2().innerJoin(joinQueryable.getClientQueryable(), (where1, where2, where3) -> {
            on.apply(new SQLWherePredicateImpl<>(where1), new SQLWherePredicateImpl<>(where2), new SQLWherePredicateImpl<>(where3));
        });
        return new EasyQueryable3<>(entityQueryable3);
    }

    default <T3> Queryable3<T1, T2, T3> leftJoinMerge(Class<T3> joinClass, SQLExpression1<Tuple3<SQLWherePredicate<T1>, SQLWherePredicate<T2>, SQLWherePredicate<T3>>> on) {
        return leftJoin(joinClass, (t1, t2,t3) -> {
            on.apply(new Tuple3<>(t1, t2,t3));
        });
    }

    default <T3> Queryable3<T1, T2, T3> leftJoinMerge(Queryable<T3> joinQueryable, SQLExpression1<Tuple3<SQLWherePredicate<T1>, SQLWherePredicate<T2>, SQLWherePredicate<T3>>> on) {
        return leftJoin(joinQueryable, (t1, t2,t3) -> {
            on.apply(new Tuple3<>(t1, t2,t3));
        });
    }

    default <T3> Queryable3<T1, T2, T3> rightJoinMerge(Class<T3> joinClass, SQLExpression1<Tuple3<SQLWherePredicate<T1>, SQLWherePredicate<T2>, SQLWherePredicate<T3>>> on) {
        return rightJoin(joinClass, (t1, t2,t3) -> {
            on.apply(new Tuple3<>(t1, t2,t3));
        });
    }

    default <T3> Queryable3<T1, T2, T3> rightJoinMerge(Queryable<T3> joinQueryable, SQLExpression1<Tuple3<SQLWherePredicate<T1>, SQLWherePredicate<T2>, SQLWherePredicate<T3>>> on) {
        return rightJoin(joinQueryable, (t1, t2,t3) -> {
            on.apply(new Tuple3<>(t1, t2,t3));
        });
    }

    default <T3> Queryable3<T1, T2, T3> innerJoinMerge(Class<T3> joinClass, SQLExpression1<Tuple3<SQLWherePredicate<T1>, SQLWherePredicate<T2>, SQLWherePredicate<T3>>> on) {
        return innerJoin(joinClass, (t1, t2,t3) -> {
            on.apply(new Tuple3<>(t1, t2,t3));
        });
    }

    default <T3> Queryable3<T1, T2, T3> innerJoinMerge(Queryable<T3> joinQueryable, SQLExpression1<Tuple3<SQLWherePredicate<T1>, SQLWherePredicate<T2>, SQLWherePredicate<T3>>> on) {
        return innerJoin(joinQueryable, (t1, t2,t3) -> {
            on.apply(new Tuple3<>(t1, t2,t3));
        });
    }

}
