package com.easy.query.api4kt.select.extension.queryable2;

import com.easy.query.api4kt.select.KtQueryable;
import com.easy.query.api4kt.select.KtQueryable3;
import com.easy.query.api4kt.select.impl.EasyKtQueryable3;
import com.easy.query.api4kt.sql.SQLKtWherePredicate;
import com.easy.query.api4kt.sql.impl.SQLKtWherePredicateImpl;
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
public interface SQLKtJoinable2<T1, T2> extends ClientKtQueryable2Available<T1, T2> {

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
    default <T3> KtQueryable3<T1, T2, T3> leftJoin(Class<T3> joinClass, SQLExpression3<SQLKtWherePredicate<T1>, SQLKtWherePredicate<T2>, SQLKtWherePredicate<T3>> on) {
        ClientQueryable3<T1, T2, T3> entityQueryable3 = getClientQueryable2().leftJoin(joinClass, (where1, where2, where3) -> {
            on.apply(new SQLKtWherePredicateImpl<>(where1), new SQLKtWherePredicateImpl<>(where2), new SQLKtWherePredicateImpl<>(where3));
        });
        return new EasyKtQueryable3<>(entityQueryable3);
    }

    default <T3> KtQueryable3<T1, T2, T3> leftJoin(KtQueryable<T3> joinQueryable, SQLExpression3<SQLKtWherePredicate<T1>, SQLKtWherePredicate<T2>, SQLKtWherePredicate<T3>> on) {
        ClientQueryable3<T1, T2, T3> entityQueryable3 = getClientQueryable2().leftJoin(joinQueryable.getClientQueryable(), (where1, where2, where3) -> {
            on.apply(new SQLKtWherePredicateImpl<>(where1), new SQLKtWherePredicateImpl<>(where2), new SQLKtWherePredicateImpl<>(where3));
        });
        return new EasyKtQueryable3<>(entityQueryable3);
    }

    default <T3> KtQueryable3<T1, T2, T3> rightJoin(Class<T3> joinClass, SQLExpression3<SQLKtWherePredicate<T1>, SQLKtWherePredicate<T2>, SQLKtWherePredicate<T3>> on) {
        ClientQueryable3<T1, T2, T3> entityQueryable3 = getClientQueryable2().rightJoin(joinClass, (where1, where2, where3) -> {
            on.apply(new SQLKtWherePredicateImpl<>(where1), new SQLKtWherePredicateImpl<>(where2), new SQLKtWherePredicateImpl<>(where3));
        });
        return new EasyKtQueryable3<>(entityQueryable3);
    }

    default <T3> KtQueryable3<T1, T2, T3> rightJoin(KtQueryable<T3> joinQueryable, SQLExpression3<SQLKtWherePredicate<T1>, SQLKtWherePredicate<T2>, SQLKtWherePredicate<T3>> on) {
        ClientQueryable3<T1, T2, T3> entityQueryable3 = getClientQueryable2().rightJoin(joinQueryable.getClientQueryable(), (where1, where2, where3) -> {
            on.apply(new SQLKtWherePredicateImpl<>(where1), new SQLKtWherePredicateImpl<>(where2), new SQLKtWherePredicateImpl<>(where3));
        });
        return new EasyKtQueryable3<>(entityQueryable3);
    }

    default <T3> KtQueryable3<T1, T2, T3> innerJoin(Class<T3> joinClass, SQLExpression3<SQLKtWherePredicate<T1>, SQLKtWherePredicate<T2>, SQLKtWherePredicate<T3>> on) {
        ClientQueryable3<T1, T2, T3> entityQueryable3 = getClientQueryable2().innerJoin(joinClass, (where1, where2, where3) -> {
            on.apply(new SQLKtWherePredicateImpl<>(where1), new SQLKtWherePredicateImpl<>(where2), new SQLKtWherePredicateImpl<>(where3));
        });
        return new EasyKtQueryable3<>(entityQueryable3);
    }

    default <T3> KtQueryable3<T1, T2, T3> innerJoin(KtQueryable<T3> joinQueryable, SQLExpression3<SQLKtWherePredicate<T1>, SQLKtWherePredicate<T2>, SQLKtWherePredicate<T3>> on) {
        ClientQueryable3<T1, T2, T3> entityQueryable3 = getClientQueryable2().innerJoin(joinQueryable.getClientQueryable(), (where1, where2, where3) -> {
            on.apply(new SQLKtWherePredicateImpl<>(where1), new SQLKtWherePredicateImpl<>(where2), new SQLKtWherePredicateImpl<>(where3));
        });
        return new EasyKtQueryable3<>(entityQueryable3);
    }

    default <T3> KtQueryable3<T1, T2, T3> leftJoinMerge(Class<T3> joinClass, SQLExpression1<Tuple3<SQLKtWherePredicate<T1>, SQLKtWherePredicate<T2>, SQLKtWherePredicate<T3>>> on) {
        return leftJoin(joinClass, (t1, t2, t3) -> {
            on.apply(new Tuple3<>(t1, t2, t3));
        });
    }

    default <T3> KtQueryable3<T1, T2, T3> leftJoinMerge(KtQueryable<T3> joinQueryable, SQLExpression1<Tuple3<SQLKtWherePredicate<T1>, SQLKtWherePredicate<T2>, SQLKtWherePredicate<T3>>> on) {
        return leftJoin(joinQueryable, (t1, t2, t3) -> {
            on.apply(new Tuple3<>(t1, t2, t3));
        });
    }

    default <T3> KtQueryable3<T1, T2, T3> rightJoinMerge(Class<T3> joinClass, SQLExpression1<Tuple3<SQLKtWherePredicate<T1>, SQLKtWherePredicate<T2>, SQLKtWherePredicate<T3>>> on) {
        return rightJoin(joinClass, (t1, t2, t3) -> {
            on.apply(new Tuple3<>(t1, t2, t3));
        });
    }

    default <T3> KtQueryable3<T1, T2, T3> rightJoinMerge(KtQueryable<T3> joinQueryable, SQLExpression1<Tuple3<SQLKtWherePredicate<T1>, SQLKtWherePredicate<T2>, SQLKtWherePredicate<T3>>> on) {
        return rightJoin(joinQueryable, (t1, t2, t3) -> {
            on.apply(new Tuple3<>(t1, t2, t3));
        });
    }

    default <T3> KtQueryable3<T1, T2, T3> innerJoinMerge(Class<T3> joinClass, SQLExpression1<Tuple3<SQLKtWherePredicate<T1>, SQLKtWherePredicate<T2>, SQLKtWherePredicate<T3>>> on) {
        return innerJoin(joinClass, (t1, t2, t3) -> {
            on.apply(new Tuple3<>(t1, t2, t3));
        });
    }

    default <T3> KtQueryable3<T1, T2, T3> innerJoinMerge(KtQueryable<T3> joinQueryable, SQLExpression1<Tuple3<SQLKtWherePredicate<T1>, SQLKtWherePredicate<T2>, SQLKtWherePredicate<T3>>> on) {
        return innerJoin(joinQueryable, (t1, t2, t3) -> {
            on.apply(new Tuple3<>(t1, t2, t3));
        });
    }

}
