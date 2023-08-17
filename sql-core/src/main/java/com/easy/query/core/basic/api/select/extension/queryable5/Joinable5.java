package com.easy.query.core.basic.api.select.extension.queryable5;

import com.easy.query.core.basic.api.select.ClientQueryable;
import com.easy.query.core.basic.api.select.ClientQueryable6;
import com.easy.query.core.common.tuple.Tuple6;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.lambda.SQLExpression6;
import com.easy.query.core.expression.parser.core.base.WherePredicate;

/**
 * create time 2023/8/16 09:11
 * 文件说明
 *
 * @author xuejiaming
 */
public interface Joinable5<T1, T2,T3,T4,T5> {
    <T6> ClientQueryable6<T1, T2, T3,T4,T5,T6> leftJoin(Class<T6> joinClass, SQLExpression6<WherePredicate<T1>, WherePredicate<T2>, WherePredicate<T3>, WherePredicate<T4>, WherePredicate<T5>, WherePredicate<T6>> on);

    <T6> ClientQueryable6<T1, T2, T3,T4,T5,T6> leftJoin(ClientQueryable<T6> joinQueryable, SQLExpression6<WherePredicate<T1>, WherePredicate<T2>, WherePredicate<T3>, WherePredicate<T4>, WherePredicate<T5>, WherePredicate<T6>> on);

    <T6> ClientQueryable6<T1, T2, T3,T4,T5,T6> rightJoin(Class<T6> joinClass, SQLExpression6<WherePredicate<T1>, WherePredicate<T2>, WherePredicate<T3>, WherePredicate<T4>, WherePredicate<T5>, WherePredicate<T6>> on);

    <T6> ClientQueryable6<T1, T2, T3,T4,T5,T6> rightJoin(ClientQueryable<T6> joinQueryable, SQLExpression6<WherePredicate<T1>, WherePredicate<T2>, WherePredicate<T3>, WherePredicate<T4>, WherePredicate<T5>, WherePredicate<T6>> on);

    <T6> ClientQueryable6<T1, T2, T3,T4,T5,T6> innerJoin(Class<T6> joinClass, SQLExpression6<WherePredicate<T1>, WherePredicate<T2>, WherePredicate<T3>, WherePredicate<T4>, WherePredicate<T5>, WherePredicate<T6>> on);

    <T6> ClientQueryable6<T1, T2, T3,T4,T5,T6> innerJoin(ClientQueryable<T6> joinQueryable, SQLExpression6<WherePredicate<T1>, WherePredicate<T2>, WherePredicate<T3>, WherePredicate<T4>, WherePredicate<T5>, WherePredicate<T6>> on);


    default <T6> ClientQueryable6<T1, T2, T3,T4,T5,T6> leftJoinMerge(Class<T6> joinClass, SQLExpression1<Tuple6<WherePredicate<T1>, WherePredicate<T2>, WherePredicate<T3>, WherePredicate<T4>, WherePredicate<T5>, WherePredicate<T6>>> on){
        return leftJoin(joinClass,(t,t1,t2,t3,t4,t5)->{
            on.apply(new Tuple6<>(t,t1,t2,t3,t4,t5));
        });
    }

    default <T6> ClientQueryable6<T1, T2, T3,T4,T5,T6> leftJoinMerge(ClientQueryable<T6> joinQueryable, SQLExpression1<Tuple6<WherePredicate<T1>, WherePredicate<T2>, WherePredicate<T3>, WherePredicate<T4>, WherePredicate<T5>, WherePredicate<T6>>> on){
        return leftJoin(joinQueryable,(t,t1,t2,t3,t4,t5)->{
            on.apply(new Tuple6<>(t,t1,t2,t3,t4,t5));
        });
    }

    default <T6> ClientQueryable6<T1, T2, T3,T4,T5,T6> rightJoinMerge(Class<T6> joinClass, SQLExpression1<Tuple6<WherePredicate<T1>, WherePredicate<T2>, WherePredicate<T3>, WherePredicate<T4>, WherePredicate<T5>, WherePredicate<T6>>> on){
        return rightJoin(joinClass,(t,t1,t2,t3,t4,t5)->{
            on.apply(new Tuple6<>(t,t1,t2,t3,t4,t5));
        });
    }

    default <T6> ClientQueryable6<T1, T2, T3,T4,T5,T6> rightJoinMerge(ClientQueryable<T6> joinQueryable, SQLExpression1<Tuple6<WherePredicate<T1>, WherePredicate<T2>, WherePredicate<T3>, WherePredicate<T4>, WherePredicate<T5>, WherePredicate<T6>>> on){
        return rightJoin(joinQueryable,(t,t1,t2,t3,t4,t5)->{
            on.apply(new Tuple6<>(t,t1,t2,t3,t4,t5));
        });
    }

    default <T6> ClientQueryable6<T1, T2, T3,T4,T5,T6> innerJoinMerge(Class<T6> joinClass, SQLExpression1<Tuple6<WherePredicate<T1>, WherePredicate<T2>, WherePredicate<T3>, WherePredicate<T4>, WherePredicate<T5>, WherePredicate<T6>>> on){
        return innerJoin(joinClass,(t,t1,t2,t3,t4,t5)->{
            on.apply(new Tuple6<>(t,t1,t2,t3,t4,t5));
        });
    }

    default <T6> ClientQueryable6<T1, T2, T3,T4,T5,T6> innerJoinMerge(ClientQueryable<T6> joinQueryable, SQLExpression1<Tuple6<WherePredicate<T1>, WherePredicate<T2>, WherePredicate<T3>, WherePredicate<T4>, WherePredicate<T5>, WherePredicate<T6>>> on){
        return innerJoin(joinQueryable,(t,t1,t2,t3,t4,t5)->{
            on.apply(new Tuple6<>(t,t1,t2,t3,t4,t5));
        });
    }

}
