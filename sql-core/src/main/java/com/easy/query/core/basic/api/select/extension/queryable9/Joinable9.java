package com.easy.query.core.basic.api.select.extension.queryable9;

import com.easy.query.core.basic.api.select.ClientQueryable;
import com.easy.query.core.basic.api.select.ClientQueryable10;
import com.easy.query.core.common.tuple.EasyTuple10;
import com.easy.query.core.expression.lambda.SQLActionExpression1;
import com.easy.query.core.expression.lambda.SQLActionExpression10;
import com.easy.query.core.expression.parser.core.base.WherePredicate;

/**
 * create time 2023/8/16 09:11
 * 文件说明
 *
 * @author xuejiaming
 */
public interface Joinable9<T1, T2,T3,T4,T5,T6,T7,T8,T9> {
    <T10> ClientQueryable10<T1, T2, T3,T4,T5,T6,T7,T8,T9,T10> leftJoin(Class<T10> joinClass, SQLActionExpression10<WherePredicate<T1>, WherePredicate<T2>, WherePredicate<T3>, WherePredicate<T4>, WherePredicate<T5>, WherePredicate<T6>, WherePredicate<T7>, WherePredicate<T8>, WherePredicate<T9>, WherePredicate<T10>> on);

    <T10> ClientQueryable10<T1, T2, T3,T4,T5,T6,T7,T8,T9,T10> leftJoin(ClientQueryable<T10> joinQueryable, SQLActionExpression10<WherePredicate<T1>, WherePredicate<T2>, WherePredicate<T3>, WherePredicate<T4>, WherePredicate<T5>, WherePredicate<T6>, WherePredicate<T7>, WherePredicate<T8>, WherePredicate<T9>, WherePredicate<T10>> on);

    <T10> ClientQueryable10<T1, T2, T3,T4,T5,T6,T7,T8,T9,T10> rightJoin(Class<T10> joinClass, SQLActionExpression10<WherePredicate<T1>, WherePredicate<T2>, WherePredicate<T3>, WherePredicate<T4>, WherePredicate<T5>, WherePredicate<T6>, WherePredicate<T7>, WherePredicate<T8>, WherePredicate<T9>, WherePredicate<T10>> on);

    <T10> ClientQueryable10<T1, T2, T3,T4,T5,T6,T7,T8,T9,T10> rightJoin(ClientQueryable<T10> joinQueryable, SQLActionExpression10<WherePredicate<T1>, WherePredicate<T2>, WherePredicate<T3>, WherePredicate<T4>, WherePredicate<T5>, WherePredicate<T6>, WherePredicate<T7>, WherePredicate<T8>, WherePredicate<T9>, WherePredicate<T10>> on);

    <T10> ClientQueryable10<T1, T2, T3,T4,T5,T6,T7,T8,T9,T10> innerJoin(Class<T10> joinClass, SQLActionExpression10<WherePredicate<T1>, WherePredicate<T2>, WherePredicate<T3>, WherePredicate<T4>, WherePredicate<T5>, WherePredicate<T6>, WherePredicate<T7>, WherePredicate<T8>, WherePredicate<T9>, WherePredicate<T10>> on);

    <T10> ClientQueryable10<T1, T2, T3,T4,T5,T6,T7,T8,T9,T10> innerJoin(ClientQueryable<T10> joinQueryable, SQLActionExpression10<WherePredicate<T1>, WherePredicate<T2>, WherePredicate<T3>, WherePredicate<T4>, WherePredicate<T5>, WherePredicate<T6>, WherePredicate<T7>, WherePredicate<T8>, WherePredicate<T9>, WherePredicate<T10>> on);


    default <T10> ClientQueryable10<T1, T2, T3,T4,T5,T6,T7,T8,T9,T10> leftJoinMerge(Class<T10> joinClass, SQLActionExpression1<EasyTuple10<WherePredicate<T1>, WherePredicate<T2>, WherePredicate<T3>, WherePredicate<T4>, WherePredicate<T5>, WherePredicate<T6>, WherePredicate<T7>, WherePredicate<T8>, WherePredicate<T9>, WherePredicate<T10>>> on){
        return leftJoin(joinClass,(t,t1,t2,t3,t4,t5,t6,t7,t8,t9)->{
            on.apply(new EasyTuple10<>(t,t1,t2,t3,t4,t5,t6,t7,t8,t9));
        });
    }

    default <T10> ClientQueryable10<T1, T2, T3,T4,T5,T6,T7,T8,T9,T10> leftJoinMerge(ClientQueryable<T10> joinQueryable, SQLActionExpression1<EasyTuple10<WherePredicate<T1>, WherePredicate<T2>, WherePredicate<T3>, WherePredicate<T4>, WherePredicate<T5>, WherePredicate<T6>, WherePredicate<T7>, WherePredicate<T8>, WherePredicate<T9>, WherePredicate<T10>>> on){
        return leftJoin(joinQueryable,(t,t1,t2,t3,t4,t5,t6,t7,t8,t9)->{
            on.apply(new EasyTuple10<>(t,t1,t2,t3,t4,t5,t6,t7,t8,t9));
        });
    }

    default <T10> ClientQueryable10<T1, T2, T3,T4,T5,T6,T7,T8,T9,T10> rightJoinMerge(Class<T10> joinClass, SQLActionExpression1<EasyTuple10<WherePredicate<T1>, WherePredicate<T2>, WherePredicate<T3>, WherePredicate<T4>, WherePredicate<T5>, WherePredicate<T6>, WherePredicate<T7>, WherePredicate<T8>, WherePredicate<T9>, WherePredicate<T10>>> on){
        return rightJoin(joinClass,(t,t1,t2,t3,t4,t5,t6,t7,t8,t9)->{
            on.apply(new EasyTuple10<>(t,t1,t2,t3,t4,t5,t6,t7,t8,t9));
        });
    }

    default <T10> ClientQueryable10<T1, T2, T3,T4,T5,T6,T7,T8,T9,T10> rightJoinMerge(ClientQueryable<T10> joinQueryable, SQLActionExpression1<EasyTuple10<WherePredicate<T1>, WherePredicate<T2>, WherePredicate<T3>, WherePredicate<T4>, WherePredicate<T5>, WherePredicate<T6>, WherePredicate<T7>, WherePredicate<T8>, WherePredicate<T9>, WherePredicate<T10>>> on){
        return rightJoin(joinQueryable,(t,t1,t2,t3,t4,t5,t6,t7,t8,t9)->{
            on.apply(new EasyTuple10<>(t,t1,t2,t3,t4,t5,t6,t7,t8,t9));
        });
    }

    default <T10> ClientQueryable10<T1, T2, T3,T4,T5,T6,T7,T8,T9,T10> innerJoinMerge(Class<T10> joinClass, SQLActionExpression1<EasyTuple10<WherePredicate<T1>, WherePredicate<T2>, WherePredicate<T3>, WherePredicate<T4>, WherePredicate<T5>, WherePredicate<T6>, WherePredicate<T7>, WherePredicate<T8>, WherePredicate<T9>, WherePredicate<T10>>> on){
        return innerJoin(joinClass,(t,t1,t2,t3,t4,t5,t6,t7,t8,t9)->{
            on.apply(new EasyTuple10<>(t,t1,t2,t3,t4,t5,t6,t7,t8,t9));
        });
    }

    default <T10> ClientQueryable10<T1, T2, T3,T4,T5,T6,T7,T8,T9,T10> innerJoinMerge(ClientQueryable<T10> joinQueryable, SQLActionExpression1<EasyTuple10<WherePredicate<T1>, WherePredicate<T2>, WherePredicate<T3>, WherePredicate<T4>, WherePredicate<T5>, WherePredicate<T6>, WherePredicate<T7>, WherePredicate<T8>, WherePredicate<T9>, WherePredicate<T10>>> on){
        return innerJoin(joinQueryable,(t,t1,t2,t3,t4,t5,t6,t7,t8,t9)->{
            on.apply(new EasyTuple10<>(t,t1,t2,t3,t4,t5,t6,t7,t8,t9));
        });
    }

}
