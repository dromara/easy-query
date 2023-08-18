package com.easy.query.api4j.select.extension.queryable4;

import com.easy.query.api4j.select.Queryable;
import com.easy.query.api4j.select.Queryable5;
import com.easy.query.api4j.select.impl.EasyQueryable5;
import com.easy.query.api4j.sql.SQLWherePredicate;
import com.easy.query.api4j.sql.impl.SQLWherePredicateImpl;
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
public interface SQLJoinable4<T1, T2,T3,T4> extends ClientQueryable4Available<T1,T2,T3,T4> {

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
   default  <T5> Queryable5<T1, T2, T3,T4,T5> leftJoin(Class<T5> joinClass, SQLExpression5<SQLWherePredicate<T1>, SQLWherePredicate<T2>, SQLWherePredicate<T3>, SQLWherePredicate<T4>, SQLWherePredicate<T5>> on){
       ClientQueryable5<T1, T2, T3, T4, T5> entityQueryable5 = getClientQueryable4().leftJoin(joinClass, (where1, where2, where3, where4, where5) -> {
           on.apply(new SQLWherePredicateImpl<>(where1), new SQLWherePredicateImpl<>(where2), new SQLWherePredicateImpl<>(where3), new SQLWherePredicateImpl<>(where4), new SQLWherePredicateImpl<>(where5));
       });
       return new EasyQueryable5<>(entityQueryable5);
   }

  default   <T5> Queryable5<T1, T2, T3,T4,T5> leftJoin(Queryable<T5> joinQueryable, SQLExpression5<SQLWherePredicate<T1>, SQLWherePredicate<T2>, SQLWherePredicate<T3>, SQLWherePredicate<T4>, SQLWherePredicate<T5>> on){
      ClientQueryable5<T1, T2, T3, T4, T5> entityQueryable5 = getClientQueryable4().leftJoin(joinQueryable.getClientQueryable(), (where1, where2, where3, where4, where5) -> {
          on.apply(new SQLWherePredicateImpl<>(where1), new SQLWherePredicateImpl<>(where2), new SQLWherePredicateImpl<>(where3), new SQLWherePredicateImpl<>(where4), new SQLWherePredicateImpl<>(where5));
      });
      return new EasyQueryable5<>(entityQueryable5);
  }

   default  <T5> Queryable5<T1, T2, T3,T4,T5> rightJoin(Class<T5> joinClass, SQLExpression5<SQLWherePredicate<T1>, SQLWherePredicate<T2>, SQLWherePredicate<T3>, SQLWherePredicate<T4>, SQLWherePredicate<T5>> on){
       ClientQueryable5<T1, T2, T3, T4, T5> entityQueryable5 = getClientQueryable4().rightJoin(joinClass, (where1, where2, where3, where4, where5) -> {
           on.apply(new SQLWherePredicateImpl<>(where1), new SQLWherePredicateImpl<>(where2), new SQLWherePredicateImpl<>(where3), new SQLWherePredicateImpl<>(where4), new SQLWherePredicateImpl<>(where5));
       });
       return new EasyQueryable5<>(entityQueryable5);
   }

   default  <T5> Queryable5<T1, T2, T3,T4,T5> rightJoin(Queryable<T5> joinQueryable, SQLExpression5<SQLWherePredicate<T1>, SQLWherePredicate<T2>, SQLWherePredicate<T3>, SQLWherePredicate<T4>, SQLWherePredicate<T5>> on){
       ClientQueryable5<T1, T2, T3, T4, T5> entityQueryable5 = getClientQueryable4().rightJoin(joinQueryable.getClientQueryable(), (where1, where2, where3, where4, where5) -> {
           on.apply(new SQLWherePredicateImpl<>(where1), new SQLWherePredicateImpl<>(where2), new SQLWherePredicateImpl<>(where3), new SQLWherePredicateImpl<>(where4), new SQLWherePredicateImpl<>(where5));
       });
       return new EasyQueryable5<>(entityQueryable5);
   }

    default <T5> Queryable5<T1, T2, T3,T4,T5> innerJoin(Class<T5> joinClass, SQLExpression5<SQLWherePredicate<T1>, SQLWherePredicate<T2>, SQLWherePredicate<T3>, SQLWherePredicate<T4>, SQLWherePredicate<T5>> on){
        ClientQueryable5<T1, T2, T3, T4, T5> entityQueryable5 = getClientQueryable4().innerJoin(joinClass, (where1, where2, where3, where4, where5) -> {
            on.apply(new SQLWherePredicateImpl<>(where1), new SQLWherePredicateImpl<>(where2), new SQLWherePredicateImpl<>(where3), new SQLWherePredicateImpl<>(where4), new SQLWherePredicateImpl<>(where5));
        });
        return new EasyQueryable5<>(entityQueryable5);
    }

    default <T5> Queryable5<T1, T2, T3,T4,T5> innerJoin(Queryable<T5> joinQueryable, SQLExpression5<SQLWherePredicate<T1>, SQLWherePredicate<T2>, SQLWherePredicate<T3>, SQLWherePredicate<T4>, SQLWherePredicate<T5>> on){
        ClientQueryable5<T1, T2, T3, T4, T5> entityQueryable5 = getClientQueryable4().innerJoin(joinQueryable.getClientQueryable(), (where1, where2, where3, where4, where5) -> {
            on.apply(new SQLWherePredicateImpl<>(where1), new SQLWherePredicateImpl<>(where2), new SQLWherePredicateImpl<>(where3), new SQLWherePredicateImpl<>(where4), new SQLWherePredicateImpl<>(where5));
        });
        return new EasyQueryable5<>(entityQueryable5);
    }


    default <T5> Queryable5<T1, T2, T3,T4,T5> leftJoinMerge(Class<T5> joinClass, SQLExpression1<Tuple5<SQLWherePredicate<T1>, SQLWherePredicate<T2>, SQLWherePredicate<T3>, SQLWherePredicate<T4>, SQLWherePredicate<T5>>> on) {
        return leftJoin(joinClass, (t1, t2,t3,t4, t5) -> {
            on.apply(new Tuple5<>(t1, t2,t3,t4, t5));
        });
    }

    default <T5> Queryable5<T1, T2, T3,T4,T5> leftJoinMerge(Queryable<T5> joinQueryable, SQLExpression1<Tuple5<SQLWherePredicate<T1>, SQLWherePredicate<T2>, SQLWherePredicate<T3>, SQLWherePredicate<T4>, SQLWherePredicate<T5>>> on) {
        return leftJoin(joinQueryable, (t1, t2,t3,t4, t5) -> {
            on.apply(new Tuple5<>(t1, t2,t3,t4, t5));
        });
    }

    default <T5> Queryable5<T1, T2, T3,T4,T5> rightJoinMerge(Class<T5> joinClass, SQLExpression1<Tuple5<SQLWherePredicate<T1>, SQLWherePredicate<T2>, SQLWherePredicate<T3>, SQLWherePredicate<T4>, SQLWherePredicate<T5>>> on) {
        return rightJoin(joinClass, (t1, t2,t3,t4, t5) -> {
            on.apply(new Tuple5<>(t1, t2,t3,t4, t5));
        });
    }

    default <T5> Queryable5<T1, T2, T3,T4,T5> rightJoinMerge(Queryable<T5> joinQueryable, SQLExpression1<Tuple5<SQLWherePredicate<T1>, SQLWherePredicate<T2>, SQLWherePredicate<T3>, SQLWherePredicate<T4>, SQLWherePredicate<T5>>> on) {
        return rightJoin(joinQueryable, (t1, t2,t3,t4, t5) -> {
            on.apply(new Tuple5<>(t1, t2,t3,t4, t5));
        });
    }

    default <T5> Queryable5<T1, T2, T3,T4,T5> innerJoinMerge(Class<T5> joinClass, SQLExpression1<Tuple5<SQLWherePredicate<T1>, SQLWherePredicate<T2>, SQLWherePredicate<T3>, SQLWherePredicate<T4>, SQLWherePredicate<T5>>> on) {
        return innerJoin(joinClass, (t1, t2,t3,t4, t5) -> {
            on.apply(new Tuple5<>(t1, t2,t3,t4, t5));
        });
    }

    default <T5> Queryable5<T1, T2, T3,T4,T5> innerJoinMerge(Queryable<T5> joinQueryable, SQLExpression1<Tuple5<SQLWherePredicate<T1>, SQLWherePredicate<T2>, SQLWherePredicate<T3>, SQLWherePredicate<T4>, SQLWherePredicate<T5>>> on) {
        return innerJoin(joinQueryable, (t1, t2,t3,t4, t5) -> {
            on.apply(new Tuple5<>(t1, t2,t3,t4, t5));
        });
    }
}
