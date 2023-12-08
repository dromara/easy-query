package com.easy.query.core.proxy.predicate;

import com.easy.query.core.proxy.SQLPredicateExpression;
import com.easy.query.core.proxy.TablePropColumn;
import com.easy.query.core.proxy.available.EntitySQLContextAvailable;
import com.easy.query.core.proxy.impl.SQLPredicateImpl;

import java.util.Collection;

/**
 * create time 2023/12/2 14:21
 * 文件说明
 *
 * @author xuejiaming
 */
public interface DSLValuesPredicate<TProperty> extends TablePropColumn, EntitySQLContextAvailable {
    default void in(Collection<? extends TProperty> collection) {
         in(true, collection);
    }

    default void in(boolean condition, Collection<? extends TProperty> collection){
        if(condition){
            getEntitySQLContext().accept(new SQLPredicateImpl(f -> f.in(this.getTable(), this.getValue(), collection)));
        }
    }
    default void in(TProperty[] array) {
         in(true, array);
    }

    default void in(boolean condition, TProperty[] array){
        if(condition){
           getEntitySQLContext().accept(new SQLPredicateImpl(f -> f.in(this.getTable(), this.getValue(), array)));
        }
    }
    default void notIn(Collection<? extends TProperty> collection) {
         notIn(true, collection);
    }

    default void notIn(boolean condition, Collection<? extends TProperty> collection){
        if(condition){
            getEntitySQLContext().accept(new SQLPredicateImpl(f -> f.notIn(this.getTable(), this.getValue(), collection)));
        }
    }
    default void notIn(TProperty[] array) {
         notIn(true, array);
    }

    default void notIn(boolean condition, TProperty[] array){
        if(condition){
            getEntitySQLContext().accept(new SQLPredicateImpl(f -> f.notIn(this.getTable(), this.getValue(), array)));
        }
    }
}
