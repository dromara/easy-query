package com.easy.query.core.proxy.predicate.aggregate;

import com.easy.query.core.enums.AggregatePredicateCompare;
import com.easy.query.core.func.SQLFunc;
import com.easy.query.core.proxy.impl.SQLAggregatePredicateImpl;
import com.easy.query.core.proxy.predicate.DSLValuePredicate;

/**
 * create time 2023/12/2 10:06
 * 文件说明
 *
 * @author xuejiaming
 */
public interface DSLValueAggregatePredicate<TProperty> extends DSLValuePredicate<TProperty>,DSLSQLFunctionAvailable {

    @Override
    default void ge(boolean condition, Object val){
        if (condition) {
            getEntitySQLContext().accept(new SQLAggregatePredicateImpl(f -> {
                SQLFunc fx = f.getRuntimeContext().fx();
                f.ge(this.getTable(), func().apply(fx), val);
            }, f -> {
                SQLFunc fx = f.getRuntimeContext().fx();
                f.func(this.getTable(), func().apply(fx), AggregatePredicateCompare.GE, val);
            }));
        }
    }


   @Override
    default void gt(boolean condition, Object val){
       if (condition) {
           getEntitySQLContext().accept(new SQLAggregatePredicateImpl(f -> {
               SQLFunc fx = f.getRuntimeContext().fx();
               f.gt(this.getTable(), func().apply(fx), val);
           }, f -> {
               SQLFunc fx = f.getRuntimeContext().fx();
               f.func(this.getTable(), func().apply(fx), AggregatePredicateCompare.GT, val);
           }));
       }
   }

    @Override
    default void eq(boolean condition, Object val){
        if (condition) {
            getEntitySQLContext().accept(new SQLAggregatePredicateImpl(f -> {
                SQLFunc fx = f.getRuntimeContext().fx();
                f.eq(this.getTable(), func().apply(fx), val);
            }, f -> {
                SQLFunc fx = f.getRuntimeContext().fx();
                f.func(this.getTable(), func().apply(fx), AggregatePredicateCompare.EQ, val);
            }));
        }
    }

   @Override
    default void ne(boolean condition, Object val){
       if (condition) {
           getEntitySQLContext().accept(new SQLAggregatePredicateImpl(f -> {
               SQLFunc fx = f.getRuntimeContext().fx();
               f.ne(this.getTable(), func().apply(fx), val);
           }, f -> {
               SQLFunc fx = f.getRuntimeContext().fx();
               f.func(this.getTable(), func().apply(fx), AggregatePredicateCompare.NE, val);
           }));
       }
   }

    @Override
    default void le(boolean condition, Object val){
        if (condition) {
            getEntitySQLContext().accept(new SQLAggregatePredicateImpl(f -> {
                SQLFunc fx = f.getRuntimeContext().fx();
                f.le(this.getTable(), func().apply(fx), val);
            }, f -> {
                SQLFunc fx = f.getRuntimeContext().fx();
                f.func(this.getTable(), func().apply(fx), AggregatePredicateCompare.LE, val);
            }));
        }
    }

   @Override
    default void lt(boolean condition, Object val){
       if (condition) {
           getEntitySQLContext().accept(new SQLAggregatePredicateImpl(f -> {
               SQLFunc fx = f.getRuntimeContext().fx();
               f.lt(this.getTable(), func().apply(fx), val);
           }, f -> {
               SQLFunc fx = f.getRuntimeContext().fx();
               f.func(this.getTable(), func().apply(fx), AggregatePredicateCompare.LT, val);
           }));
       }
   }
}
