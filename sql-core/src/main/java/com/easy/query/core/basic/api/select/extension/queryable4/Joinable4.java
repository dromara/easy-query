package com.easy.query.core.basic.api.select.extension.queryable4;

import com.easy.query.core.basic.api.select.ClientQueryable;
import com.easy.query.core.basic.api.select.ClientQueryable5;
import com.easy.query.core.common.tuple.EasyTuple5;
import com.easy.query.core.expression.lambda.SQLActionExpression1;
import com.easy.query.core.expression.lambda.SQLActionExpression5;
import com.easy.query.core.expression.parser.core.base.WherePredicate;

/**
 * create time 2023/8/16 09:11
 * 文件说明
 *
 * @author xuejiaming
 */
public interface Joinable4<T1, T2,T3,T4> {
    <T5> ClientQueryable5<T1, T2, T3,T4,T5> leftJoin(Class<T5> joinClass, SQLActionExpression5<WherePredicate<T1>, WherePredicate<T2>, WherePredicate<T3>, WherePredicate<T4>, WherePredicate<T5>> on);

    <T5> ClientQueryable5<T1, T2, T3,T4,T5> leftJoin(ClientQueryable<T5> joinQueryable, SQLActionExpression5<WherePredicate<T1>, WherePredicate<T2>, WherePredicate<T3>, WherePredicate<T4>, WherePredicate<T5>> on);

    <T5> ClientQueryable5<T1, T2, T3,T4,T5> rightJoin(Class<T5> joinClass, SQLActionExpression5<WherePredicate<T1>, WherePredicate<T2>, WherePredicate<T3>, WherePredicate<T4>, WherePredicate<T5>> on);

    <T5> ClientQueryable5<T1, T2, T3,T4,T5> rightJoin(ClientQueryable<T5> joinQueryable, SQLActionExpression5<WherePredicate<T1>, WherePredicate<T2>, WherePredicate<T3>, WherePredicate<T4>, WherePredicate<T5>> on);

    <T5> ClientQueryable5<T1, T2, T3,T4,T5> innerJoin(Class<T5> joinClass, SQLActionExpression5<WherePredicate<T1>, WherePredicate<T2>, WherePredicate<T3>, WherePredicate<T4>, WherePredicate<T5>> on);

    <T5> ClientQueryable5<T1, T2, T3,T4,T5> innerJoin(ClientQueryable<T5> joinQueryable, SQLActionExpression5<WherePredicate<T1>, WherePredicate<T2>, WherePredicate<T3>, WherePredicate<T4>, WherePredicate<T5>> on);


    default <T5> ClientQueryable5<T1, T2, T3,T4,T5> leftJoinMerge(Class<T5> joinClass, SQLActionExpression1<EasyTuple5<WherePredicate<T1>, WherePredicate<T2>, WherePredicate<T3>, WherePredicate<T4>, WherePredicate<T5>>> on){
        return leftJoin(joinClass,(t,t1,t2,t3,t4)->{
            on.apply(new EasyTuple5<>(t,t1,t2,t3,t4));
        });
    }

    default <T5> ClientQueryable5<T1, T2, T3,T4,T5> leftJoinMerge(ClientQueryable<T5> joinQueryable, SQLActionExpression1<EasyTuple5<WherePredicate<T1>, WherePredicate<T2>, WherePredicate<T3>, WherePredicate<T4>, WherePredicate<T5>>> on){
        return leftJoin(joinQueryable,(t,t1,t2,t3,t4)->{
            on.apply(new EasyTuple5<>(t,t1,t2,t3,t4));
        });
    }

    default <T5> ClientQueryable5<T1, T2, T3,T4,T5> rightJoinMerge(Class<T5> joinClass, SQLActionExpression1<EasyTuple5<WherePredicate<T1>, WherePredicate<T2>, WherePredicate<T3>, WherePredicate<T4>, WherePredicate<T5>>> on){
        return rightJoin(joinClass,(t,t1,t2,t3,t4)->{
            on.apply(new EasyTuple5<>(t,t1,t2,t3,t4));
        });
    }

    default <T5> ClientQueryable5<T1, T2, T3,T4,T5> rightJoinMerge(ClientQueryable<T5> joinQueryable, SQLActionExpression1<EasyTuple5<WherePredicate<T1>, WherePredicate<T2>, WherePredicate<T3>, WherePredicate<T4>, WherePredicate<T5>>> on){
        return rightJoin(joinQueryable,(t,t1,t2,t3,t4)->{
            on.apply(new EasyTuple5<>(t,t1,t2,t3,t4));
        });
    }

    default <T5> ClientQueryable5<T1, T2, T3,T4,T5> innerJoinMerge(Class<T5> joinClass, SQLActionExpression1<EasyTuple5<WherePredicate<T1>, WherePredicate<T2>, WherePredicate<T3>, WherePredicate<T4>, WherePredicate<T5>>> on){
        return innerJoin(joinClass,(t,t1,t2,t3,t4)->{
            on.apply(new EasyTuple5<>(t,t1,t2,t3,t4));
        });
    }

    default <T5> ClientQueryable5<T1, T2, T3,T4,T5> innerJoinMerge(ClientQueryable<T5> joinQueryable, SQLActionExpression1<EasyTuple5<WherePredicate<T1>, WherePredicate<T2>, WherePredicate<T3>, WherePredicate<T4>, WherePredicate<T5>>> on){
        return innerJoin(joinQueryable,(t,t1,t2,t3,t4)->{
            on.apply(new EasyTuple5<>(t,t1,t2,t3,t4));
        });
    }

}
