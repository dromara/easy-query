package com.easy.query.core.basic.api.select.extension.queryable8;

import com.easy.query.core.basic.api.select.ClientQueryable;
import com.easy.query.core.basic.api.select.ClientQueryable9;
import com.easy.query.core.common.tuple.EasyTuple9;
import com.easy.query.core.expression.lambda.SQLActionExpression1;
import com.easy.query.core.expression.lambda.SQLActionExpression9;
import com.easy.query.core.expression.parser.core.base.WherePredicate;

/**
 * create time 2023/8/16 09:11
 * 文件说明
 *
 * @author xuejiaming
 */
public interface Joinable8<T1, T2,T3,T4,T5,T6,T7,T8> {
    <T9> ClientQueryable9<T1, T2, T3,T4,T5,T6,T7,T8,T9> leftJoin(Class<T9> joinClass, SQLActionExpression9<WherePredicate<T1>, WherePredicate<T2>, WherePredicate<T3>, WherePredicate<T4>, WherePredicate<T5>, WherePredicate<T6>, WherePredicate<T7>, WherePredicate<T8>, WherePredicate<T9>> on);

    <T9> ClientQueryable9<T1, T2, T3,T4,T5,T6,T7,T8,T9> leftJoin(ClientQueryable<T9> joinQueryable, SQLActionExpression9<WherePredicate<T1>, WherePredicate<T2>, WherePredicate<T3>, WherePredicate<T4>, WherePredicate<T5>, WherePredicate<T6>, WherePredicate<T7>, WherePredicate<T8>, WherePredicate<T9>> on);

    <T9> ClientQueryable9<T1, T2, T3,T4,T5,T6,T7,T8,T9> rightJoin(Class<T9> joinClass, SQLActionExpression9<WherePredicate<T1>, WherePredicate<T2>, WherePredicate<T3>, WherePredicate<T4>, WherePredicate<T5>, WherePredicate<T6>, WherePredicate<T7>, WherePredicate<T8>, WherePredicate<T9>> on);

    <T9> ClientQueryable9<T1, T2, T3,T4,T5,T6,T7,T8,T9> rightJoin(ClientQueryable<T9> joinQueryable, SQLActionExpression9<WherePredicate<T1>, WherePredicate<T2>, WherePredicate<T3>, WherePredicate<T4>, WherePredicate<T5>, WherePredicate<T6>, WherePredicate<T7>, WherePredicate<T8>, WherePredicate<T9>> on);

    <T9> ClientQueryable9<T1, T2, T3,T4,T5,T6,T7,T8,T9> innerJoin(Class<T9> joinClass, SQLActionExpression9<WherePredicate<T1>, WherePredicate<T2>, WherePredicate<T3>, WherePredicate<T4>, WherePredicate<T5>, WherePredicate<T6>, WherePredicate<T7>, WherePredicate<T8>, WherePredicate<T9>> on);

    <T9> ClientQueryable9<T1, T2, T3,T4,T5,T6,T7,T8,T9> innerJoin(ClientQueryable<T9> joinQueryable, SQLActionExpression9<WherePredicate<T1>, WherePredicate<T2>, WherePredicate<T3>, WherePredicate<T4>, WherePredicate<T5>, WherePredicate<T6>, WherePredicate<T7>, WherePredicate<T8>, WherePredicate<T9>> on);


    default <T9> ClientQueryable9<T1, T2, T3,T4,T5,T6,T7,T8,T9> leftJoinMerge(Class<T9> joinClass, SQLActionExpression1<EasyTuple9<WherePredicate<T1>, WherePredicate<T2>, WherePredicate<T3>, WherePredicate<T4>, WherePredicate<T5>, WherePredicate<T6>, WherePredicate<T7>, WherePredicate<T8>, WherePredicate<T9>>> on){
        return leftJoin(joinClass,(t,t1,t2,t3,t4,t5,t6,t7,t8)->{
            on.apply(new EasyTuple9<>(t,t1,t2,t3,t4,t5,t6,t7,t8));
        });
    }

    default <T9> ClientQueryable9<T1, T2, T3,T4,T5,T6,T7,T8,T9> leftJoinMerge(ClientQueryable<T9> joinQueryable, SQLActionExpression1<EasyTuple9<WherePredicate<T1>, WherePredicate<T2>, WherePredicate<T3>, WherePredicate<T4>, WherePredicate<T5>, WherePredicate<T6>, WherePredicate<T7>, WherePredicate<T8>, WherePredicate<T9>>> on){
        return leftJoin(joinQueryable,(t,t1,t2,t3,t4,t5,t6,t7,t8)->{
            on.apply(new EasyTuple9<>(t,t1,t2,t3,t4,t5,t6,t7,t8));
        });
    }

    default <T9> ClientQueryable9<T1, T2, T3,T4,T5,T6,T7,T8,T9> rightJoinMerge(Class<T9> joinClass, SQLActionExpression1<EasyTuple9<WherePredicate<T1>, WherePredicate<T2>, WherePredicate<T3>, WherePredicate<T4>, WherePredicate<T5>, WherePredicate<T6>, WherePredicate<T7>, WherePredicate<T8>, WherePredicate<T9>>> on){
        return rightJoin(joinClass,(t,t1,t2,t3,t4,t5,t6,t7,t8)->{
            on.apply(new EasyTuple9<>(t,t1,t2,t3,t4,t5,t6,t7,t8));
        });
    }

    default <T9> ClientQueryable9<T1, T2, T3,T4,T5,T6,T7,T8,T9> rightJoinMerge(ClientQueryable<T9> joinQueryable, SQLActionExpression1<EasyTuple9<WherePredicate<T1>, WherePredicate<T2>, WherePredicate<T3>, WherePredicate<T4>, WherePredicate<T5>, WherePredicate<T6>, WherePredicate<T7>, WherePredicate<T8>, WherePredicate<T9>>> on){
        return rightJoin(joinQueryable,(t,t1,t2,t3,t4,t5,t6,t7,t8)->{
            on.apply(new EasyTuple9<>(t,t1,t2,t3,t4,t5,t6,t7,t8));
        });
    }

    default <T9> ClientQueryable9<T1, T2, T3,T4,T5,T6,T7,T8,T9> innerJoinMerge(Class<T9> joinClass, SQLActionExpression1<EasyTuple9<WherePredicate<T1>, WherePredicate<T2>, WherePredicate<T3>, WherePredicate<T4>, WherePredicate<T5>, WherePredicate<T6>, WherePredicate<T7>, WherePredicate<T8>, WherePredicate<T9>>> on){
        return innerJoin(joinClass,(t,t1,t2,t3,t4,t5,t6,t7,t8)->{
            on.apply(new EasyTuple9<>(t,t1,t2,t3,t4,t5,t6,t7,t8));
        });
    }

    default <T9> ClientQueryable9<T1, T2, T3,T4,T5,T6,T7,T8,T9> innerJoinMerge(ClientQueryable<T9> joinQueryable, SQLActionExpression1<EasyTuple9<WherePredicate<T1>, WherePredicate<T2>, WherePredicate<T3>, WherePredicate<T4>, WherePredicate<T5>, WherePredicate<T6>, WherePredicate<T7>, WherePredicate<T8>, WherePredicate<T9>>> on){
        return innerJoin(joinQueryable,(t,t1,t2,t3,t4,t5,t6,t7,t8)->{
            on.apply(new EasyTuple9<>(t,t1,t2,t3,t4,t5,t6,t7,t8));
        });
    }

}
