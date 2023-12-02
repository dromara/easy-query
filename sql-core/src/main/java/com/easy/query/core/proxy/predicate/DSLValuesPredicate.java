package com.easy.query.core.proxy.predicate;

import com.easy.query.core.proxy.SQLPredicate;
import com.easy.query.core.proxy.TablePropColumn;
import com.easy.query.core.proxy.impl.SQLPredicateImpl;

import java.util.Collection;

/**
 * create time 2023/12/2 14:21
 * 文件说明
 *
 * @author xuejiaming
 */
public interface DSLValuesPredicate<TProperty> extends TablePropColumn {
    default SQLPredicate in(Collection<? extends TProperty> collection) {
        return in(true, collection);
    }

    default SQLPredicate in(boolean condition, Collection<? extends TProperty> collection){
        if(condition){
            return new SQLPredicateImpl(f -> f.in(this.getTable(), this.value(), collection));
        }
        return SQLPredicate.empty;
    }
    default SQLPredicate in(TProperty[] array) {
        return in(true, array);
    }

    default SQLPredicate in(boolean condition, TProperty[] array){
        if(condition){
            return new SQLPredicateImpl(f -> f.in(this.getTable(), this.value(), array));
        }
        return SQLPredicate.empty;
    }
    default SQLPredicate notIn(Collection<? extends TProperty> collection) {
        return notIn(true, collection);
    }

    default SQLPredicate notIn(boolean condition, Collection<? extends TProperty> collection){
        if(condition){
            return new SQLPredicateImpl(f -> f.notIn(this.getTable(), this.value(), collection));
        }
        return SQLPredicate.empty;
    }
    default SQLPredicate notIn(TProperty[] array) {
        return notIn(true, array);
    }

    default SQLPredicate notIn(boolean condition, TProperty[] array){
        if(condition){
            return new SQLPredicateImpl(f -> f.notIn(this.getTable(), this.value(), array));
        }
        return SQLPredicate.empty;
    }
}
