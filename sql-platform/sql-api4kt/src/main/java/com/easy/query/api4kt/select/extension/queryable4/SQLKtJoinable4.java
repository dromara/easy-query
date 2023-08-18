package com.easy.query.api4kt.select.extension.queryable4;

import com.easy.query.api4kt.select.KtQueryable;
import com.easy.query.api4kt.select.KtQueryable5;
import com.easy.query.api4kt.select.impl.EasyKtQueryable5;
import com.easy.query.api4kt.sql.SQLKtWherePredicate;
import com.easy.query.api4kt.sql.impl.SQLKtWherePredicateImpl;
import com.easy.query.core.basic.api.select.ClientQueryable5;
import com.easy.query.core.common.tuple.Tuple5;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.lambda.SQLExpression5;

/**
 * create time 2023/8/16 09:11
 * 文件说明
 *
 * @author xuejiaming
 */
public interface SQLKtJoinable4<T1, T2, T3, T4> extends ClientKtQueryable4Available<T1, T2, T3, T4> {

    /**
     * <pre>{@code
     * leftJoin(Entity2.class, (t1, t2,t3,t4, t5) -> t.eq(t2, Entity::getId, Entity2::getId))
     * }</pre>
     * t代表from的表,t1代表第一次join的表,t2代表第二次join的表
     *
     * @param joinClass 和哪张表进行join
     * @param on        条件
     * @param <T5>
     * @return 返回可查询的表达式支持3表参数
     */
    default <T5> KtQueryable5<T1, T2, T3, T4, T5> leftJoin(Class<T5> joinClass, SQLExpression5<SQLKtWherePredicate<T1>, SQLKtWherePredicate<T2>, SQLKtWherePredicate<T3>, SQLKtWherePredicate<T4>, SQLKtWherePredicate<T5>> on) {
        ClientQueryable5<T1, T2, T3, T4, T5> entityQueryable5 = getClientQueryable4().leftJoin(joinClass, (where1, where2, where3, where4, where5) -> {
            on.apply(new SQLKtWherePredicateImpl<>(where1), new SQLKtWherePredicateImpl<>(where2), new SQLKtWherePredicateImpl<>(where3), new SQLKtWherePredicateImpl<>(where4), new SQLKtWherePredicateImpl<>(where5));
        });
        return new EasyKtQueryable5<>(entityQueryable5);
    }

    default <T5> KtQueryable5<T1, T2, T3, T4, T5> leftJoin(KtQueryable<T5> joinQueryable, SQLExpression5<SQLKtWherePredicate<T1>, SQLKtWherePredicate<T2>, SQLKtWherePredicate<T3>, SQLKtWherePredicate<T4>, SQLKtWherePredicate<T5>> on) {
        ClientQueryable5<T1, T2, T3, T4, T5> entityQueryable5 = getClientQueryable4().leftJoin(joinQueryable.getClientQueryable(), (where1, where2, where3, where4, where5) -> {
            on.apply(new SQLKtWherePredicateImpl<>(where1), new SQLKtWherePredicateImpl<>(where2), new SQLKtWherePredicateImpl<>(where3), new SQLKtWherePredicateImpl<>(where4), new SQLKtWherePredicateImpl<>(where5));
        });
        return new EasyKtQueryable5<>(entityQueryable5);
    }

    default <T5> KtQueryable5<T1, T2, T3, T4, T5> rightJoin(Class<T5> joinClass, SQLExpression5<SQLKtWherePredicate<T1>, SQLKtWherePredicate<T2>, SQLKtWherePredicate<T3>, SQLKtWherePredicate<T4>, SQLKtWherePredicate<T5>> on) {
        ClientQueryable5<T1, T2, T3, T4, T5> entityQueryable5 = getClientQueryable4().rightJoin(joinClass, (where1, where2, where3, where4, where5) -> {
            on.apply(new SQLKtWherePredicateImpl<>(where1), new SQLKtWherePredicateImpl<>(where2), new SQLKtWherePredicateImpl<>(where3), new SQLKtWherePredicateImpl<>(where4), new SQLKtWherePredicateImpl<>(where5));
        });
        return new EasyKtQueryable5<>(entityQueryable5);
    }

    default <T5> KtQueryable5<T1, T2, T3, T4, T5> rightJoin(KtQueryable<T5> joinQueryable, SQLExpression5<SQLKtWherePredicate<T1>, SQLKtWherePredicate<T2>, SQLKtWherePredicate<T3>, SQLKtWherePredicate<T4>, SQLKtWherePredicate<T5>> on) {
        ClientQueryable5<T1, T2, T3, T4, T5> entityQueryable5 = getClientQueryable4().rightJoin(joinQueryable.getClientQueryable(), (where1, where2, where3, where4, where5) -> {
            on.apply(new SQLKtWherePredicateImpl<>(where1), new SQLKtWherePredicateImpl<>(where2), new SQLKtWherePredicateImpl<>(where3), new SQLKtWherePredicateImpl<>(where4), new SQLKtWherePredicateImpl<>(where5));
        });
        return new EasyKtQueryable5<>(entityQueryable5);
    }

    default <T5> KtQueryable5<T1, T2, T3, T4, T5> innerJoin(Class<T5> joinClass, SQLExpression5<SQLKtWherePredicate<T1>, SQLKtWherePredicate<T2>, SQLKtWherePredicate<T3>, SQLKtWherePredicate<T4>, SQLKtWherePredicate<T5>> on) {
        ClientQueryable5<T1, T2, T3, T4, T5> entityQueryable5 = getClientQueryable4().innerJoin(joinClass, (where1, where2, where3, where4, where5) -> {
            on.apply(new SQLKtWherePredicateImpl<>(where1), new SQLKtWherePredicateImpl<>(where2), new SQLKtWherePredicateImpl<>(where3), new SQLKtWherePredicateImpl<>(where4), new SQLKtWherePredicateImpl<>(where5));
        });
        return new EasyKtQueryable5<>(entityQueryable5);
    }

    default <T5> KtQueryable5<T1, T2, T3, T4, T5> innerJoin(KtQueryable<T5> joinQueryable, SQLExpression5<SQLKtWherePredicate<T1>, SQLKtWherePredicate<T2>, SQLKtWherePredicate<T3>, SQLKtWherePredicate<T4>, SQLKtWherePredicate<T5>> on) {
        ClientQueryable5<T1, T2, T3, T4, T5> entityQueryable5 = getClientQueryable4().innerJoin(joinQueryable.getClientQueryable(), (where1, where2, where3, where4, where5) -> {
            on.apply(new SQLKtWherePredicateImpl<>(where1), new SQLKtWherePredicateImpl<>(where2), new SQLKtWherePredicateImpl<>(where3), new SQLKtWherePredicateImpl<>(where4), new SQLKtWherePredicateImpl<>(where5));
        });
        return new EasyKtQueryable5<>(entityQueryable5);
    }


    default <T5> KtQueryable5<T1, T2, T3, T4, T5> leftJoinMerge(Class<T5> joinClass, SQLExpression1<Tuple5<SQLKtWherePredicate<T1>, SQLKtWherePredicate<T2>, SQLKtWherePredicate<T3>, SQLKtWherePredicate<T4>, SQLKtWherePredicate<T5>>> on) {
        return leftJoin(joinClass, (t1, t2, t3, t4, t5) -> {
            on.apply(new Tuple5<>(t1, t2, t3, t4, t5));
        });
    }

    default <T5> KtQueryable5<T1, T2, T3, T4, T5> leftJoinMerge(KtQueryable<T5> joinQueryable, SQLExpression1<Tuple5<SQLKtWherePredicate<T1>, SQLKtWherePredicate<T2>, SQLKtWherePredicate<T3>, SQLKtWherePredicate<T4>, SQLKtWherePredicate<T5>>> on) {
        return leftJoin(joinQueryable, (t1, t2, t3, t4, t5) -> {
            on.apply(new Tuple5<>(t1, t2, t3, t4, t5));
        });
    }

    default <T5> KtQueryable5<T1, T2, T3, T4, T5> rightJoinMerge(Class<T5> joinClass, SQLExpression1<Tuple5<SQLKtWherePredicate<T1>, SQLKtWherePredicate<T2>, SQLKtWherePredicate<T3>, SQLKtWherePredicate<T4>, SQLKtWherePredicate<T5>>> on) {
        return rightJoin(joinClass, (t1, t2, t3, t4, t5) -> {
            on.apply(new Tuple5<>(t1, t2, t3, t4, t5));
        });
    }

    default <T5> KtQueryable5<T1, T2, T3, T4, T5> rightJoinMerge(KtQueryable<T5> joinQueryable, SQLExpression1<Tuple5<SQLKtWherePredicate<T1>, SQLKtWherePredicate<T2>, SQLKtWherePredicate<T3>, SQLKtWherePredicate<T4>, SQLKtWherePredicate<T5>>> on) {
        return rightJoin(joinQueryable, (t1, t2, t3, t4, t5) -> {
            on.apply(new Tuple5<>(t1, t2, t3, t4, t5));
        });
    }

    default <T5> KtQueryable5<T1, T2, T3, T4, T5> innerJoinMerge(Class<T5> joinClass, SQLExpression1<Tuple5<SQLKtWherePredicate<T1>, SQLKtWherePredicate<T2>, SQLKtWherePredicate<T3>, SQLKtWherePredicate<T4>, SQLKtWherePredicate<T5>>> on) {
        return innerJoin(joinClass, (t1, t2, t3, t4, t5) -> {
            on.apply(new Tuple5<>(t1, t2, t3, t4, t5));
        });
    }

    default <T5> KtQueryable5<T1, T2, T3, T4, T5> innerJoinMerge(KtQueryable<T5> joinQueryable, SQLExpression1<Tuple5<SQLKtWherePredicate<T1>, SQLKtWherePredicate<T2>, SQLKtWherePredicate<T3>, SQLKtWherePredicate<T4>, SQLKtWherePredicate<T5>>> on) {
        return innerJoin(joinQueryable, (t1, t2, t3, t4, t5) -> {
            on.apply(new Tuple5<>(t1, t2, t3, t4, t5));
        });
    }
}
