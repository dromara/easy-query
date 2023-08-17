package com.easy.query.core.basic.api.select.extension.queryable7;

import com.easy.query.core.basic.api.select.ClientQueryable;
import com.easy.query.core.basic.api.select.ClientQueryable8;
import com.easy.query.core.common.tuple.Tuple8;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.lambda.SQLExpression8;
import com.easy.query.core.expression.parser.core.base.WherePredicate;

/**
 * create time 2023/8/16 09:11
 * 文件说明
 *
 * @author xuejiaming
 */
public interface Joinable7<T1, T2,T3,T4,T5,T6,T7> {
    <T8> ClientQueryable8<T1, T2, T3,T4,T5,T6,T7,T8> leftJoin(Class<T8> joinClass, SQLExpression8<WherePredicate<T1>, WherePredicate<T2>, WherePredicate<T3>, WherePredicate<T4>, WherePredicate<T5>, WherePredicate<T6>, WherePredicate<T7>, WherePredicate<T8>> on);

    <T8> ClientQueryable8<T1, T2, T3,T4,T5,T6,T7,T8> leftJoin(ClientQueryable<T8> joinQueryable, SQLExpression8<WherePredicate<T1>, WherePredicate<T2>, WherePredicate<T3>, WherePredicate<T4>, WherePredicate<T5>, WherePredicate<T6>, WherePredicate<T7>, WherePredicate<T8>> on);

    <T8> ClientQueryable8<T1, T2, T3,T4,T5,T6,T7,T8> rightJoin(Class<T8> joinClass, SQLExpression8<WherePredicate<T1>, WherePredicate<T2>, WherePredicate<T3>, WherePredicate<T4>, WherePredicate<T5>, WherePredicate<T6>, WherePredicate<T7>, WherePredicate<T8>> on);

    <T8> ClientQueryable8<T1, T2, T3,T4,T5,T6,T7,T8> rightJoin(ClientQueryable<T8> joinQueryable, SQLExpression8<WherePredicate<T1>, WherePredicate<T2>, WherePredicate<T3>, WherePredicate<T4>, WherePredicate<T5>, WherePredicate<T6>, WherePredicate<T7>, WherePredicate<T8>> on);

    <T8> ClientQueryable8<T1, T2, T3,T4,T5,T6,T7,T8> innerJoin(Class<T8> joinClass, SQLExpression8<WherePredicate<T1>, WherePredicate<T2>, WherePredicate<T3>, WherePredicate<T4>, WherePredicate<T5>, WherePredicate<T6>, WherePredicate<T7>, WherePredicate<T8>> on);

    <T8> ClientQueryable8<T1, T2, T3,T4,T5,T6,T7,T8> innerJoin(ClientQueryable<T8> joinQueryable, SQLExpression8<WherePredicate<T1>, WherePredicate<T2>, WherePredicate<T3>, WherePredicate<T4>, WherePredicate<T5>, WherePredicate<T6>, WherePredicate<T7>, WherePredicate<T8>> on);


    default <T8> ClientQueryable8<T1, T2, T3,T4,T5,T6,T7,T8> leftJoinMerge(Class<T8> joinClass, SQLExpression1<Tuple8<WherePredicate<T1>, WherePredicate<T2>, WherePredicate<T3>, WherePredicate<T4>, WherePredicate<T5>, WherePredicate<T6>, WherePredicate<T7>, WherePredicate<T8>>> on){
        return leftJoin(joinClass,(t,t1,t2,t3,t4,t5,t6,t7)->{
            on.apply(new Tuple8<>(t,t1,t2,t3,t4,t5,t6,t7));
        });
    }

    default <T8> ClientQueryable8<T1, T2, T3,T4,T5,T6,T7,T8> leftJoinMerge(ClientQueryable<T8> joinQueryable, SQLExpression1<Tuple8<WherePredicate<T1>, WherePredicate<T2>, WherePredicate<T3>, WherePredicate<T4>, WherePredicate<T5>, WherePredicate<T6>, WherePredicate<T7>, WherePredicate<T8>>> on){
        return leftJoin(joinQueryable,(t,t1,t2,t3,t4,t5,t6,t7)->{
            on.apply(new Tuple8<>(t,t1,t2,t3,t4,t5,t6,t7));
        });
    }

    default <T8> ClientQueryable8<T1, T2, T3,T4,T5,T6,T7,T8> rightJoinMerge(Class<T8> joinClass, SQLExpression1<Tuple8<WherePredicate<T1>, WherePredicate<T2>, WherePredicate<T3>, WherePredicate<T4>, WherePredicate<T5>, WherePredicate<T6>, WherePredicate<T7>, WherePredicate<T8>>> on){
        return rightJoin(joinClass,(t,t1,t2,t3,t4,t5,t6,t7)->{
            on.apply(new Tuple8<>(t,t1,t2,t3,t4,t5,t6,t7));
        });
    }

    default <T8> ClientQueryable8<T1, T2, T3,T4,T5,T6,T7,T8> rightJoinMerge(ClientQueryable<T8> joinQueryable, SQLExpression1<Tuple8<WherePredicate<T1>, WherePredicate<T2>, WherePredicate<T3>, WherePredicate<T4>, WherePredicate<T5>, WherePredicate<T6>, WherePredicate<T7>, WherePredicate<T8>>> on){
        return rightJoin(joinQueryable,(t,t1,t2,t3,t4,t5,t6,t7)->{
            on.apply(new Tuple8<>(t,t1,t2,t3,t4,t5,t6,t7));
        });
    }

    default <T8> ClientQueryable8<T1, T2, T3,T4,T5,T6,T7,T8> innerJoinMerge(Class<T8> joinClass, SQLExpression1<Tuple8<WherePredicate<T1>, WherePredicate<T2>, WherePredicate<T3>, WherePredicate<T4>, WherePredicate<T5>, WherePredicate<T6>, WherePredicate<T7>, WherePredicate<T8>>> on){
        return innerJoin(joinClass,(t,t1,t2,t3,t4,t5,t6,t7)->{
            on.apply(new Tuple8<>(t,t1,t2,t3,t4,t5,t6,t7));
        });
    }

    default <T8> ClientQueryable8<T1, T2, T3,T4,T5,T6,T7,T8> innerJoinMerge(ClientQueryable<T8> joinQueryable, SQLExpression1<Tuple8<WherePredicate<T1>, WherePredicate<T2>, WherePredicate<T3>, WherePredicate<T4>, WherePredicate<T5>, WherePredicate<T6>, WherePredicate<T7>, WherePredicate<T8>>> on){
        return innerJoin(joinQueryable,(t,t1,t2,t3,t4,t5,t6,t7)->{
            on.apply(new Tuple8<>(t,t1,t2,t3,t4,t5,t6,t7));
        });
    }

}
