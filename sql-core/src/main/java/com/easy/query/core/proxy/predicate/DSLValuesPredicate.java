package com.easy.query.core.proxy.predicate;

import com.easy.query.core.proxy.SQLPredicateExpression;
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
    default SQLPredicateExpression in(Collection<? extends TProperty> collection) {
        return in(true, collection);
    }

    default SQLPredicateExpression in(boolean condition, Collection<? extends TProperty> collection){
        if(condition){
            return new SQLPredicateImpl(f -> f.in(this.getTable(), this.value(), collection));
        }
        return SQLPredicateExpression.empty;
    }
    default SQLPredicateExpression in(TProperty[] array) {
        return in(true, array);
    }

    default SQLPredicateExpression in(boolean condition, TProperty[] array){
        if(condition){
            return new SQLPredicateImpl(f -> f.in(this.getTable(), this.value(), array));
        }
        return SQLPredicateExpression.empty;
    }
    default SQLPredicateExpression notIn(Collection<? extends TProperty> collection) {
        return notIn(true, collection);
    }

    default SQLPredicateExpression notIn(boolean condition, Collection<? extends TProperty> collection){
        if(condition){
            return new SQLPredicateImpl(f -> f.notIn(this.getTable(), this.value(), collection));
        }
        return SQLPredicateExpression.empty;
    }
    default SQLPredicateExpression notIn(TProperty[] array) {
        return notIn(true, array);
    }

    default SQLPredicateExpression notIn(boolean condition, TProperty[] array){
        if(condition){
            return new SQLPredicateImpl(f -> f.notIn(this.getTable(), this.value(), array));
        }
        return SQLPredicateExpression.empty;
    }
}
