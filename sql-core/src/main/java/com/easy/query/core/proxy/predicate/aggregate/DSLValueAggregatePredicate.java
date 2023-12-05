package com.easy.query.core.proxy.predicate.aggregate;

import com.easy.query.core.enums.AggregatePredicateCompare;
import com.easy.query.core.func.SQLFunc;
import com.easy.query.core.proxy.SQLAggregatePredicateExpression;
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
    default SQLAggregatePredicateExpression ge(TProperty val) {
        return ge(true, val);
    }

    @Override
    default SQLAggregatePredicateExpression ge(boolean condition, TProperty val){
        if (condition) {
            return new SQLAggregatePredicateImpl(f -> {
                SQLFunc fx = f.getRuntimeContext().fx();
                f.ge(this.getTable(), func().apply(fx), val);
            }, f -> {
                SQLFunc fx = f.getRuntimeContext().fx();
                f.func(this.getTable(), func().apply(fx), AggregatePredicateCompare.GE, val);
            });
        }
        return SQLAggregatePredicateExpression.empty;
    }

    @Override
    default SQLAggregatePredicateExpression gt(TProperty val) {
        return gt(true, val);
    }

   @Override
    default SQLAggregatePredicateExpression gt(boolean condition, TProperty val){
       if (condition) {
           return new SQLAggregatePredicateImpl(f -> {
               SQLFunc fx = f.getRuntimeContext().fx();
               f.gt(this.getTable(), func().apply(fx), val);
           }, f -> {
               SQLFunc fx = f.getRuntimeContext().fx();
               f.func(this.getTable(), func().apply(fx), AggregatePredicateCompare.GT, val);
           });
       }
       return SQLAggregatePredicateExpression.empty;
   }

    @Override
    default SQLAggregatePredicateExpression eq(TProperty val) {
        return eq(true, val);
    }

    @Override
    default SQLAggregatePredicateExpression eq(boolean condition, TProperty val){
        if (condition) {
            return new SQLAggregatePredicateImpl(f -> {
                SQLFunc fx = f.getRuntimeContext().fx();
                f.eq(this.getTable(), func().apply(fx), val);
            }, f -> {
                SQLFunc fx = f.getRuntimeContext().fx();
                f.func(this.getTable(), func().apply(fx), AggregatePredicateCompare.EQ, val);
            });
        }
        return SQLAggregatePredicateExpression.empty;
    }

    @Override
    default SQLAggregatePredicateExpression ne(TProperty val) {
        return ne(true, val);
    }

   @Override
    default SQLAggregatePredicateExpression ne(boolean condition, TProperty val){
       if (condition) {
           return new SQLAggregatePredicateImpl(f -> {
               SQLFunc fx = f.getRuntimeContext().fx();
               f.ne(this.getTable(), func().apply(fx), val);
           }, f -> {
               SQLFunc fx = f.getRuntimeContext().fx();
               f.func(this.getTable(), func().apply(fx), AggregatePredicateCompare.NE, val);
           });
       }
       return SQLAggregatePredicateExpression.empty;
   }

    @Override
    default SQLAggregatePredicateExpression le(TProperty val) {
        return le(true, val);
    }

    @Override
    default SQLAggregatePredicateExpression le(boolean condition, TProperty val){
        if (condition) {
            return new SQLAggregatePredicateImpl(f -> {
                SQLFunc fx = f.getRuntimeContext().fx();
                f.le(this.getTable(), func().apply(fx), val);
            }, f -> {
                SQLFunc fx = f.getRuntimeContext().fx();
                f.func(this.getTable(), func().apply(fx), AggregatePredicateCompare.LE, val);
            });
        }
        return SQLAggregatePredicateExpression.empty;
    }

    @Override
    default SQLAggregatePredicateExpression lt(TProperty val) {
        return lt(true, val);
    }

   @Override
    default SQLAggregatePredicateExpression lt(boolean condition, TProperty val){
       if (condition) {
           return new SQLAggregatePredicateImpl(f -> {
               SQLFunc fx = f.getRuntimeContext().fx();
               f.lt(this.getTable(), func().apply(fx), val);
           }, f -> {
               SQLFunc fx = f.getRuntimeContext().fx();
               f.func(this.getTable(), func().apply(fx), AggregatePredicateCompare.LT, val);
           });
       }
       return SQLAggregatePredicateExpression.empty;
   }
}
