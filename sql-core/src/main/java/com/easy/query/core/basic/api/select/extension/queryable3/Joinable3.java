package com.easy.query.core.basic.api.select.extension.queryable3;

import com.easy.query.core.basic.api.select.ClientQueryable;
import com.easy.query.core.basic.api.select.ClientQueryable4;
import com.easy.query.core.common.tuple.Tuple4;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.lambda.SQLExpression4;
import com.easy.query.core.expression.parser.core.base.WherePredicate;

/**
 * create time 2023/8/16 09:11
 * 文件说明
 *
 * @author xuejiaming
 */
public interface Joinable3<T1, T2,T3> {
    <T4> ClientQueryable4<T1, T2, T3,T4> leftJoin(Class<T4> joinClass, SQLExpression4<WherePredicate<T1>, WherePredicate<T2>, WherePredicate<T3>, WherePredicate<T4>> on);

    <T4> ClientQueryable4<T1, T2, T3,T4> leftJoin(ClientQueryable<T4> joinQueryable, SQLExpression4<WherePredicate<T1>, WherePredicate<T2>, WherePredicate<T3>, WherePredicate<T4>> on);

    <T4> ClientQueryable4<T1, T2, T3,T4> rightJoin(Class<T4> joinClass, SQLExpression4<WherePredicate<T1>, WherePredicate<T2>, WherePredicate<T3>, WherePredicate<T4>> on);

    <T4> ClientQueryable4<T1, T2, T3,T4> rightJoin(ClientQueryable<T4> joinQueryable, SQLExpression4<WherePredicate<T1>, WherePredicate<T2>, WherePredicate<T3>, WherePredicate<T4>> on);

    <T4> ClientQueryable4<T1, T2, T3,T4> innerJoin(Class<T4> joinClass, SQLExpression4<WherePredicate<T1>, WherePredicate<T2>, WherePredicate<T3>, WherePredicate<T4>> on);

    <T4> ClientQueryable4<T1, T2, T3,T4> innerJoin(ClientQueryable<T4> joinQueryable, SQLExpression4<WherePredicate<T1>, WherePredicate<T2>, WherePredicate<T3>, WherePredicate<T4>> on);


    default <T4> ClientQueryable4<T1, T2, T3,T4> leftJoinMerge(Class<T4> joinClass, SQLExpression1<Tuple4<WherePredicate<T1>, WherePredicate<T2>, WherePredicate<T3>, WherePredicate<T4>>> on){
        return leftJoin(joinClass,(t,t1,t2,t3)->{
            on.apply(new Tuple4<>(t,t1,t2,t3));
        });
    }

    default <T4> ClientQueryable4<T1, T2, T3,T4> leftJoinMerge(ClientQueryable<T4> joinQueryable, SQLExpression1<Tuple4<WherePredicate<T1>, WherePredicate<T2>, WherePredicate<T3>, WherePredicate<T4>>> on){
        return leftJoin(joinQueryable,(t,t1,t2,t3)->{
            on.apply(new Tuple4<>(t,t1,t2,t3));
        });
    }

    default <T4> ClientQueryable4<T1, T2, T3,T4> rightJoinMerge(Class<T4> joinClass, SQLExpression1<Tuple4<WherePredicate<T1>, WherePredicate<T2>, WherePredicate<T3>, WherePredicate<T4>>> on){
        return rightJoin(joinClass,(t,t1,t2,t3)->{
            on.apply(new Tuple4<>(t,t1,t2,t3));
        });
    }

    default <T4> ClientQueryable4<T1, T2, T3,T4> rightJoinMerge(ClientQueryable<T4> joinQueryable, SQLExpression1<Tuple4<WherePredicate<T1>, WherePredicate<T2>, WherePredicate<T3>, WherePredicate<T4>>> on){
        return rightJoin(joinQueryable,(t,t1,t2,t3)->{
            on.apply(new Tuple4<>(t,t1,t2,t3));
        });
    }

    default <T4> ClientQueryable4<T1, T2, T3,T4> innerJoinMerge(Class<T4> joinClass, SQLExpression1<Tuple4<WherePredicate<T1>, WherePredicate<T2>, WherePredicate<T3>, WherePredicate<T4>>> on){
        return innerJoin(joinClass,(t,t1,t2,t3)->{
            on.apply(new Tuple4<>(t,t1,t2,t3));
        });
    }

    default <T4> ClientQueryable4<T1, T2, T3,T4> innerJoinMerge(ClientQueryable<T4> joinQueryable, SQLExpression1<Tuple4<WherePredicate<T1>, WherePredicate<T2>, WherePredicate<T3>, WherePredicate<T4>>> on){
        return innerJoin(joinQueryable,(t,t1,t2,t3)->{
            on.apply(new Tuple4<>(t,t1,t2,t3));
        });
    }

}
