package com.easy.query.api4j.select.extension.queryable3;

import com.easy.query.api4j.select.Queryable;
import com.easy.query.api4j.select.Queryable4;
import com.easy.query.api4j.select.impl.EasyQueryable4;
import com.easy.query.api4j.sql.SQLWherePredicate;
import com.easy.query.api4j.sql.impl.SQLWherePredicateImpl;
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
public interface SQLJoinable3<T1, T2,T3> extends ClientQueryable3Available<T1,T2,T3> {

    /**
     * <pre>{@code
     * leftJoin(Entity2.class, (t, t1, t2,t3) -> t.eq(t2, Entity::getId, Entity2::getId))
     * }</pre>
     * t代表from的表,t1代表第一次join的表,t2代表第二次join的表
     *
     * @param joinClass 和哪张表进行join
     * @param on        条件
     * @param <T4>
     * @return 返回可查询的表达式支持3表参数
     */
   default  <T4> Queryable4<T1, T2, T3,T4> leftJoin(Class<T4> joinClass, SQLExpression4<SQLWherePredicate<T1>, SQLWherePredicate<T2>, SQLWherePredicate<T3>, SQLWherePredicate<T4>> on){
       ClientQueryable4<T1, T2, T3, T4> entityQueryable4 = getClientQueryable3().leftJoin(joinClass, (where1, where2, where3, where4) -> {
           on.apply(new SQLWherePredicateImpl<>(where1), new SQLWherePredicateImpl<>(where2), new SQLWherePredicateImpl<>(where3), new SQLWherePredicateImpl<>(where4));
       });
       return new EasyQueryable4<>(entityQueryable4);
   }

  default   <T4> Queryable4<T1, T2, T3,T4> leftJoin(Queryable<T4> joinQueryable, SQLExpression4<SQLWherePredicate<T1>, SQLWherePredicate<T2>, SQLWherePredicate<T3>, SQLWherePredicate<T4>> on){
      ClientQueryable4<T1, T2, T3, T4> entityQueryable4 = getClientQueryable3().leftJoin(joinQueryable.getClientQueryable(), (where1, where2, where3, where4) -> {
          on.apply(new SQLWherePredicateImpl<>(where1), new SQLWherePredicateImpl<>(where2), new SQLWherePredicateImpl<>(where3), new SQLWherePredicateImpl<>(where4));
      });
      return new EasyQueryable4<>(entityQueryable4);
  }

   default  <T4> Queryable4<T1, T2, T3,T4> rightJoin(Class<T4> joinClass, SQLExpression4<SQLWherePredicate<T1>, SQLWherePredicate<T2>, SQLWherePredicate<T3>, SQLWherePredicate<T4>> on){
       ClientQueryable4<T1, T2, T3, T4> entityQueryable4 = getClientQueryable3().rightJoin(joinClass, (where1, where2, where3, where4) -> {
           on.apply(new SQLWherePredicateImpl<>(where1), new SQLWherePredicateImpl<>(where2), new SQLWherePredicateImpl<>(where3), new SQLWherePredicateImpl<>(where4));
       });
       return new EasyQueryable4<>(entityQueryable4);
   }

   default  <T4> Queryable4<T1, T2, T3,T4> rightJoin(Queryable<T4> joinQueryable, SQLExpression4<SQLWherePredicate<T1>, SQLWherePredicate<T2>, SQLWherePredicate<T3>, SQLWherePredicate<T4>> on){
       ClientQueryable4<T1, T2, T3, T4> entityQueryable4 = getClientQueryable3().rightJoin(joinQueryable.getClientQueryable(), (where1, where2, where3, where4) -> {
           on.apply(new SQLWherePredicateImpl<>(where1), new SQLWherePredicateImpl<>(where2), new SQLWherePredicateImpl<>(where3), new SQLWherePredicateImpl<>(where4));
       });
       return new EasyQueryable4<>(entityQueryable4);
   }

    default <T4> Queryable4<T1, T2, T3,T4> innerJoin(Class<T4> joinClass, SQLExpression4<SQLWherePredicate<T1>, SQLWherePredicate<T2>, SQLWherePredicate<T3>, SQLWherePredicate<T4>> on){
        ClientQueryable4<T1, T2, T3, T4> entityQueryable4 = getClientQueryable3().innerJoin(joinClass, (where1, where2, where3, where4) -> {
            on.apply(new SQLWherePredicateImpl<>(where1), new SQLWherePredicateImpl<>(where2), new SQLWherePredicateImpl<>(where3), new SQLWherePredicateImpl<>(where4));
        });
        return new EasyQueryable4<>(entityQueryable4);
    }

    default <T4> Queryable4<T1, T2, T3,T4> innerJoin(Queryable<T4> joinQueryable, SQLExpression4<SQLWherePredicate<T1>, SQLWherePredicate<T2>, SQLWherePredicate<T3>, SQLWherePredicate<T4>> on){
        ClientQueryable4<T1, T2, T3, T4> entityQueryable4 = getClientQueryable3().innerJoin(joinQueryable.getClientQueryable(), (where1, where2, where3, where4) -> {
            on.apply(new SQLWherePredicateImpl<>(where1), new SQLWherePredicateImpl<>(where2), new SQLWherePredicateImpl<>(where3), new SQLWherePredicateImpl<>(where4));
        });
        return new EasyQueryable4<>(entityQueryable4);
    }


    default <T4> Queryable4<T1, T2, T3,T4> leftJoinMerge(Class<T4> joinClass, SQLExpression1<Tuple4<SQLWherePredicate<T1>, SQLWherePredicate<T2>, SQLWherePredicate<T3>, SQLWherePredicate<T4>>> on) {
        return leftJoin(joinClass, (t, t1, t2,t3) -> {
            on.apply(new Tuple4<>(t, t1, t2,t3));
        });
    }

    default <T4> Queryable4<T1, T2, T3,T4> leftJoinMerge(Queryable<T4> joinQueryable, SQLExpression1<Tuple4<SQLWherePredicate<T1>, SQLWherePredicate<T2>, SQLWherePredicate<T3>, SQLWherePredicate<T4>>> on) {
        return leftJoin(joinQueryable, (t, t1, t2,t3) -> {
            on.apply(new Tuple4<>(t, t1, t2,t3));
        });
    }

    default <T4> Queryable4<T1, T2, T3,T4> rightJoinMerge(Class<T4> joinClass, SQLExpression1<Tuple4<SQLWherePredicate<T1>, SQLWherePredicate<T2>, SQLWherePredicate<T3>, SQLWherePredicate<T4>>> on) {
        return rightJoin(joinClass, (t, t1, t2,t3) -> {
            on.apply(new Tuple4<>(t, t1, t2,t3));
        });
    }

    default <T4> Queryable4<T1, T2, T3,T4> rightJoinMerge(Queryable<T4> joinQueryable, SQLExpression1<Tuple4<SQLWherePredicate<T1>, SQLWherePredicate<T2>, SQLWherePredicate<T3>, SQLWherePredicate<T4>>> on) {
        return rightJoin(joinQueryable, (t, t1, t2,t3) -> {
            on.apply(new Tuple4<>(t, t1, t2,t3));
        });
    }

    default <T4> Queryable4<T1, T2, T3,T4> innerJoinMerge(Class<T4> joinClass, SQLExpression1<Tuple4<SQLWherePredicate<T1>, SQLWherePredicate<T2>, SQLWherePredicate<T3>, SQLWherePredicate<T4>>> on) {
        return innerJoin(joinClass, (t, t1, t2,t3) -> {
            on.apply(new Tuple4<>(t, t1, t2,t3));
        });
    }

    default <T4> Queryable4<T1, T2, T3,T4> innerJoinMerge(Queryable<T4> joinQueryable, SQLExpression1<Tuple4<SQLWherePredicate<T1>, SQLWherePredicate<T2>, SQLWherePredicate<T3>, SQLWherePredicate<T4>>> on) {
        return innerJoin(joinQueryable, (t, t1, t2,t3) -> {
            on.apply(new Tuple4<>(t, t1, t2,t3));
        });
    }

}
