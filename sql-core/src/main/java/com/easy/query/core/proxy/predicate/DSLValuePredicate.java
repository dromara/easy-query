package com.easy.query.core.proxy.predicate;

import com.easy.query.core.proxy.SQLPredicate;
import com.easy.query.core.proxy.TablePropColumn;
import com.easy.query.core.proxy.impl.SQLPredicateImpl;

/**
 * create time 2023/12/2 10:06
 * 文件说明
 *
 * @author xuejiaming
 */
public interface DSLValuePredicate<TProperty> extends TablePropColumn {

    default SQLPredicate ge(TProperty val) {
        return ge(true, val);
    }

    default SQLPredicate ge(boolean condition, TProperty val){
        if(condition){
            return new SQLPredicateImpl(f -> f.ge(this.getTable(), this.value(), val));
        }
        return SQLPredicate.empty;
    }

    default SQLPredicate gt(TProperty val) {
        return gt(true, val);
    }

   default SQLPredicate gt(boolean condition, TProperty val){
       if(condition){
           return new SQLPredicateImpl(f -> f.gt(this.getTable(), this.value(), val));
       }
       return SQLPredicate.empty;
   }

    default SQLPredicate eq(TProperty val) {
        return eq(true, val);
    }

    default SQLPredicate eq(boolean condition, TProperty val){
        if (condition) {
            return new SQLPredicateImpl(f -> {
                f.eq(this.getTable(), this.value(), val);
            });
        }
        return SQLPredicate.empty;
    }

    default SQLPredicate ne(TProperty val) {
        return ne(true, val);
    }

   default SQLPredicate ne(boolean condition, TProperty val){
       if(condition){
           return new SQLPredicateImpl(f -> f.ne(this.getTable(), this.value(), val));
       }
       return SQLPredicate.empty;
   }

    default SQLPredicate le(TProperty val) {
        return le(true, val);
    }

    default SQLPredicate le(boolean condition, TProperty val){
        if(condition){
            return new SQLPredicateImpl(f -> f.le(this.getTable(), this.value(), val));
        }
        return SQLPredicate.empty;
    }

    default SQLPredicate lt(TProperty val) {
        return lt(true, val);
    }

   default SQLPredicate lt(boolean condition, TProperty val){
       if(condition){
           return new SQLPredicateImpl(f -> f.lt(this.getTable(), this.value(), val));
       }
       return SQLPredicate.empty;
   }
}
