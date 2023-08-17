package com.easy.query.core.basic.api.select.extension.queryable6;

import com.easy.query.core.basic.api.select.ClientQueryable;
import com.easy.query.core.basic.api.select.ClientQueryable7;
import com.easy.query.core.common.tuple.Tuple7;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.lambda.SQLExpression7;
import com.easy.query.core.expression.parser.core.base.WherePredicate;

/**
 * create time 2023/8/16 09:11
 * 文件说明
 *
 * @author xuejiaming
 */
public interface Joinable6<T1, T2,T3,T4,T5,T6> {
    <T7> ClientQueryable7<T1, T2, T3,T4,T5,T6,T7> leftJoin(Class<T7> joinClass, SQLExpression7<WherePredicate<T1>, WherePredicate<T2>, WherePredicate<T3>, WherePredicate<T4>, WherePredicate<T5>, WherePredicate<T6>, WherePredicate<T7>> on);

    <T7> ClientQueryable7<T1, T2, T3,T4,T5,T6,T7> leftJoin(ClientQueryable<T7> joinQueryable, SQLExpression7<WherePredicate<T1>, WherePredicate<T2>, WherePredicate<T3>, WherePredicate<T4>, WherePredicate<T5>, WherePredicate<T6>, WherePredicate<T7>> on);

    <T7> ClientQueryable7<T1, T2, T3,T4,T5,T6,T7> rightJoin(Class<T7> joinClass, SQLExpression7<WherePredicate<T1>, WherePredicate<T2>, WherePredicate<T3>, WherePredicate<T4>, WherePredicate<T5>, WherePredicate<T6>, WherePredicate<T7>> on);

    <T7> ClientQueryable7<T1, T2, T3,T4,T5,T6,T7> rightJoin(ClientQueryable<T7> joinQueryable, SQLExpression7<WherePredicate<T1>, WherePredicate<T2>, WherePredicate<T3>, WherePredicate<T4>, WherePredicate<T5>, WherePredicate<T6>, WherePredicate<T7>> on);

    <T7> ClientQueryable7<T1, T2, T3,T4,T5,T6,T7> innerJoin(Class<T7> joinClass, SQLExpression7<WherePredicate<T1>, WherePredicate<T2>, WherePredicate<T3>, WherePredicate<T4>, WherePredicate<T5>, WherePredicate<T6>, WherePredicate<T7>> on);

    <T7> ClientQueryable7<T1, T2, T3,T4,T5,T6,T7> innerJoin(ClientQueryable<T7> joinQueryable, SQLExpression7<WherePredicate<T1>, WherePredicate<T2>, WherePredicate<T3>, WherePredicate<T4>, WherePredicate<T5>, WherePredicate<T6>, WherePredicate<T7>> on);


    default <T7> ClientQueryable7<T1, T2, T3,T4,T5,T6,T7> leftJoinMerge(Class<T7> joinClass, SQLExpression1<Tuple7<WherePredicate<T1>, WherePredicate<T2>, WherePredicate<T3>, WherePredicate<T4>, WherePredicate<T5>, WherePredicate<T6>, WherePredicate<T7>>> on){
        return leftJoin(joinClass,(t,t1,t2,t3,t4,t5,t6)->{
            on.apply(new Tuple7<>(t,t1,t2,t3,t4,t5,t6));
        });
    }

    default <T7> ClientQueryable7<T1, T2, T3,T4,T5,T6,T7> leftJoinMerge(ClientQueryable<T7> joinQueryable, SQLExpression1<Tuple7<WherePredicate<T1>, WherePredicate<T2>, WherePredicate<T3>, WherePredicate<T4>, WherePredicate<T5>, WherePredicate<T6>, WherePredicate<T7>>> on){
        return leftJoin(joinQueryable,(t,t1,t2,t3,t4,t5,t6)->{
            on.apply(new Tuple7<>(t,t1,t2,t3,t4,t5,t6));
        });
    }

    default <T7> ClientQueryable7<T1, T2, T3,T4,T5,T6,T7> rightJoinMerge(Class<T7> joinClass, SQLExpression1<Tuple7<WherePredicate<T1>, WherePredicate<T2>, WherePredicate<T3>, WherePredicate<T4>, WherePredicate<T5>, WherePredicate<T6>, WherePredicate<T7>>> on){
        return rightJoin(joinClass,(t,t1,t2,t3,t4,t5,t6)->{
            on.apply(new Tuple7<>(t,t1,t2,t3,t4,t5,t6));
        });
    }

    default <T7> ClientQueryable7<T1, T2, T3,T4,T5,T6,T7> rightJoinMerge(ClientQueryable<T7> joinQueryable, SQLExpression1<Tuple7<WherePredicate<T1>, WherePredicate<T2>, WherePredicate<T3>, WherePredicate<T4>, WherePredicate<T5>, WherePredicate<T6>, WherePredicate<T7>>> on){
        return rightJoin(joinQueryable,(t,t1,t2,t3,t4,t5,t6)->{
            on.apply(new Tuple7<>(t,t1,t2,t3,t4,t5,t6));
        });
    }

    default <T7> ClientQueryable7<T1, T2, T3,T4,T5,T6,T7> innerJoinMerge(Class<T7> joinClass, SQLExpression1<Tuple7<WherePredicate<T1>, WherePredicate<T2>, WherePredicate<T3>, WherePredicate<T4>, WherePredicate<T5>, WherePredicate<T6>, WherePredicate<T7>>> on){
        return innerJoin(joinClass,(t,t1,t2,t3,t4,t5,t6)->{
            on.apply(new Tuple7<>(t,t1,t2,t3,t4,t5,t6));
        });
    }

    default <T7> ClientQueryable7<T1, T2, T3,T4,T5,T6,T7> innerJoinMerge(ClientQueryable<T7> joinQueryable, SQLExpression1<Tuple7<WherePredicate<T1>, WherePredicate<T2>, WherePredicate<T3>, WherePredicate<T4>, WherePredicate<T5>, WherePredicate<T6>, WherePredicate<T7>>> on){
        return innerJoin(joinQueryable,(t,t1,t2,t3,t4,t5,t6)->{
            on.apply(new Tuple7<>(t,t1,t2,t3,t4,t5,t6));
        });
    }

}
