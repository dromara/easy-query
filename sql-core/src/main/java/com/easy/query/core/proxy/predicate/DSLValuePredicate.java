package com.easy.query.core.proxy.predicate;

import com.easy.query.core.proxy.SQLPredicateExpression;
import com.easy.query.core.proxy.TablePropColumn;
import com.easy.query.core.proxy.impl.SQLPredicateImpl;

/**
 * create time 2023/12/2 10:06
 * 文件说明
 *
 * @author xuejiaming
 */
public interface DSLValuePredicate<TProperty> extends TablePropColumn {

    default SQLPredicateExpression ge(TProperty val) {
        return ge(true, val);
    }

    default SQLPredicateExpression ge(boolean condition, TProperty val){
        if(condition){
            return new SQLPredicateImpl(f -> f.ge(this.getTable(), this.getValue(), val));
        }
        return SQLPredicateExpression.empty;
    }

    default SQLPredicateExpression gt(TProperty val) {
        return gt(true, val);
    }

   default SQLPredicateExpression gt(boolean condition, TProperty val){
       if(condition){
           return new SQLPredicateImpl(f -> f.gt(this.getTable(), this.getValue(), val));
       }
       return SQLPredicateExpression.empty;
   }

    default SQLPredicateExpression eq(TProperty val) {
        return eq(true, val);
    }

    default SQLPredicateExpression eq(boolean condition, TProperty val){
        if (condition) {
            return new SQLPredicateImpl(f -> {
                f.eq(this.getTable(), this.getValue(), val);
            });
        }
        return SQLPredicateExpression.empty;
    }

    default SQLPredicateExpression ne(TProperty val) {
        return ne(true, val);
    }

   default SQLPredicateExpression ne(boolean condition, TProperty val){
       if(condition){
           return new SQLPredicateImpl(f -> f.ne(this.getTable(), this.getValue(), val));
       }
       return SQLPredicateExpression.empty;
   }

    default SQLPredicateExpression le(TProperty val) {
        return le(true, val);
    }

    default SQLPredicateExpression le(boolean condition, TProperty val){
        if(condition){
            return new SQLPredicateImpl(f -> f.le(this.getTable(), this.getValue(), val));
        }
        return SQLPredicateExpression.empty;
    }

    default SQLPredicateExpression lt(TProperty val) {
        return lt(true, val);
    }

   default SQLPredicateExpression lt(boolean condition, TProperty val){
       if(condition){
           return new SQLPredicateImpl(f -> f.lt(this.getTable(), this.getValue(), val));
       }
       return SQLPredicateExpression.empty;
   }
}
