package com.easy.query.api4kt.select.extension.queryable3;

import com.easy.query.api4kt.select.KtQueryable;
import com.easy.query.api4kt.select.KtQueryable4;
import com.easy.query.api4kt.select.impl.EasyKtQueryable4;
import com.easy.query.api4kt.sql.SQLKtWherePredicate;
import com.easy.query.api4kt.sql.impl.SQLKtWherePredicateImpl;
import com.easy.query.core.basic.api.select.ClientQueryable4;
import com.easy.query.core.common.tuple.Tuple4;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.lambda.SQLExpression4;

/**
 * create time 2023/8/16 09:11
 * 文件说明
 *
 * @author xuejiaming
 */
public interface SQLKtJoinable3<T1, T2, T3> extends ClientKtQueryable3Available<T1, T2, T3> {

    /**
     * <pre>{@code
     * leftJoin(Entity2.class, (t1, t2,t3, t4) -> t.eq(t2, Entity::getId, Entity2::getId))
     * }</pre>
     * t代表from的表,t1代表第一次join的表,t2代表第二次join的表
     *
     * @param joinClass 和哪张表进行join
     * @param on        条件
     * @param <T4>
     * @return 返回可查询的表达式支持3表参数
     */
    default <T4> KtQueryable4<T1, T2, T3, T4> leftJoin(Class<T4> joinClass, SQLExpression4<SQLKtWherePredicate<T1>, SQLKtWherePredicate<T2>, SQLKtWherePredicate<T3>, SQLKtWherePredicate<T4>> on) {
        ClientQueryable4<T1, T2, T3, T4> entityQueryable4 = getClientQueryable3().leftJoin(joinClass, (where1, where2, where3, where4) -> {
            on.apply(new SQLKtWherePredicateImpl<>(where1), new SQLKtWherePredicateImpl<>(where2), new SQLKtWherePredicateImpl<>(where3), new SQLKtWherePredicateImpl<>(where4));
        });
        return new EasyKtQueryable4<>(entityQueryable4);
    }

    default <T4> KtQueryable4<T1, T2, T3, T4> leftJoin(KtQueryable<T4> joinQueryable, SQLExpression4<SQLKtWherePredicate<T1>, SQLKtWherePredicate<T2>, SQLKtWherePredicate<T3>, SQLKtWherePredicate<T4>> on) {
        ClientQueryable4<T1, T2, T3, T4> entityQueryable4 = getClientQueryable3().leftJoin(joinQueryable.getClientQueryable(), (where1, where2, where3, where4) -> {
            on.apply(new SQLKtWherePredicateImpl<>(where1), new SQLKtWherePredicateImpl<>(where2), new SQLKtWherePredicateImpl<>(where3), new SQLKtWherePredicateImpl<>(where4));
        });
        return new EasyKtQueryable4<>(entityQueryable4);
    }

    default <T4> KtQueryable4<T1, T2, T3, T4> rightJoin(Class<T4> joinClass, SQLExpression4<SQLKtWherePredicate<T1>, SQLKtWherePredicate<T2>, SQLKtWherePredicate<T3>, SQLKtWherePredicate<T4>> on) {
        ClientQueryable4<T1, T2, T3, T4> entityQueryable4 = getClientQueryable3().rightJoin(joinClass, (where1, where2, where3, where4) -> {
            on.apply(new SQLKtWherePredicateImpl<>(where1), new SQLKtWherePredicateImpl<>(where2), new SQLKtWherePredicateImpl<>(where3), new SQLKtWherePredicateImpl<>(where4));
        });
        return new EasyKtQueryable4<>(entityQueryable4);
    }

    default <T4> KtQueryable4<T1, T2, T3, T4> rightJoin(KtQueryable<T4> joinQueryable, SQLExpression4<SQLKtWherePredicate<T1>, SQLKtWherePredicate<T2>, SQLKtWherePredicate<T3>, SQLKtWherePredicate<T4>> on) {
        ClientQueryable4<T1, T2, T3, T4> entityQueryable4 = getClientQueryable3().rightJoin(joinQueryable.getClientQueryable(), (where1, where2, where3, where4) -> {
            on.apply(new SQLKtWherePredicateImpl<>(where1), new SQLKtWherePredicateImpl<>(where2), new SQLKtWherePredicateImpl<>(where3), new SQLKtWherePredicateImpl<>(where4));
        });
        return new EasyKtQueryable4<>(entityQueryable4);
    }

    default <T4> KtQueryable4<T1, T2, T3, T4> innerJoin(Class<T4> joinClass, SQLExpression4<SQLKtWherePredicate<T1>, SQLKtWherePredicate<T2>, SQLKtWherePredicate<T3>, SQLKtWherePredicate<T4>> on) {
        ClientQueryable4<T1, T2, T3, T4> entityQueryable4 = getClientQueryable3().innerJoin(joinClass, (where1, where2, where3, where4) -> {
            on.apply(new SQLKtWherePredicateImpl<>(where1), new SQLKtWherePredicateImpl<>(where2), new SQLKtWherePredicateImpl<>(where3), new SQLKtWherePredicateImpl<>(where4));
        });
        return new EasyKtQueryable4<>(entityQueryable4);
    }

    default <T4> KtQueryable4<T1, T2, T3, T4> innerJoin(KtQueryable<T4> joinQueryable, SQLExpression4<SQLKtWherePredicate<T1>, SQLKtWherePredicate<T2>, SQLKtWherePredicate<T3>, SQLKtWherePredicate<T4>> on) {
        ClientQueryable4<T1, T2, T3, T4> entityQueryable4 = getClientQueryable3().innerJoin(joinQueryable.getClientQueryable(), (where1, where2, where3, where4) -> {
            on.apply(new SQLKtWherePredicateImpl<>(where1), new SQLKtWherePredicateImpl<>(where2), new SQLKtWherePredicateImpl<>(where3), new SQLKtWherePredicateImpl<>(where4));
        });
        return new EasyKtQueryable4<>(entityQueryable4);
    }


    default <T4> KtQueryable4<T1, T2, T3, T4> leftJoinMerge(Class<T4> joinClass, SQLExpression1<Tuple4<SQLKtWherePredicate<T1>, SQLKtWherePredicate<T2>, SQLKtWherePredicate<T3>, SQLKtWherePredicate<T4>>> on) {
        return leftJoin(joinClass, (t1, t2, t3, t4) -> {
            on.apply(new Tuple4<>(t1, t2, t3, t4));
        });
    }

    default <T4> KtQueryable4<T1, T2, T3, T4> leftJoinMerge(KtQueryable<T4> joinQueryable, SQLExpression1<Tuple4<SQLKtWherePredicate<T1>, SQLKtWherePredicate<T2>, SQLKtWherePredicate<T3>, SQLKtWherePredicate<T4>>> on) {
        return leftJoin(joinQueryable, (t1, t2, t3, t4) -> {
            on.apply(new Tuple4<>(t1, t2, t3, t4));
        });
    }

    default <T4> KtQueryable4<T1, T2, T3, T4> rightJoinMerge(Class<T4> joinClass, SQLExpression1<Tuple4<SQLKtWherePredicate<T1>, SQLKtWherePredicate<T2>, SQLKtWherePredicate<T3>, SQLKtWherePredicate<T4>>> on) {
        return rightJoin(joinClass, (t1, t2, t3, t4) -> {
            on.apply(new Tuple4<>(t1, t2, t3, t4));
        });
    }

    default <T4> KtQueryable4<T1, T2, T3, T4> rightJoinMerge(KtQueryable<T4> joinQueryable, SQLExpression1<Tuple4<SQLKtWherePredicate<T1>, SQLKtWherePredicate<T2>, SQLKtWherePredicate<T3>, SQLKtWherePredicate<T4>>> on) {
        return rightJoin(joinQueryable, (t1, t2, t3, t4) -> {
            on.apply(new Tuple4<>(t1, t2, t3, t4));
        });
    }

    default <T4> KtQueryable4<T1, T2, T3, T4> innerJoinMerge(Class<T4> joinClass, SQLExpression1<Tuple4<SQLKtWherePredicate<T1>, SQLKtWherePredicate<T2>, SQLKtWherePredicate<T3>, SQLKtWherePredicate<T4>>> on) {
        return innerJoin(joinClass, (t1, t2, t3, t4) -> {
            on.apply(new Tuple4<>(t1, t2, t3, t4));
        });
    }

    default <T4> KtQueryable4<T1, T2, T3, T4> innerJoinMerge(KtQueryable<T4> joinQueryable, SQLExpression1<Tuple4<SQLKtWherePredicate<T1>, SQLKtWherePredicate<T2>, SQLKtWherePredicate<T3>, SQLKtWherePredicate<T4>>> on) {
        return innerJoin(joinQueryable, (t1, t2, t3, t4) -> {
            on.apply(new Tuple4<>(t1, t2, t3, t4));
        });
    }

}
