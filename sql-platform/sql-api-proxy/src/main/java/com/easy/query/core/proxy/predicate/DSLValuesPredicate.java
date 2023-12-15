package com.easy.query.core.proxy.predicate;

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

    default void in(boolean condition, Collection<? extends TProperty> collections){
        if(condition){
            getEntitySQLContext().accept(new SQLPredicateImpl(f -> f.in(this.getTable(), this.getValue(), collections)));
        }
    }
    default void in(TProperty[] arrays) {
         in(true, arrays);
    }

    default void in(boolean condition, TProperty[] arrays){
        if(condition){
           getEntitySQLContext().accept(new SQLPredicateImpl(f -> f.in(this.getTable(), this.getValue(), arrays)));
        }
    }
    default void notIn(Collection<? extends TProperty> collections) {
         notIn(true, collections);
    }

    default void notIn(boolean condition, Collection<? extends TProperty> collections){
        if(condition){
            getEntitySQLContext().accept(new SQLPredicateImpl(f -> f.notIn(this.getTable(), this.getValue(), collections)));
        }
    }
    default void notIn(TProperty[] arrays) {
         notIn(true, arrays);
    }

    default void notIn(boolean condition, TProperty[] arrays){
        if(condition){
            getEntitySQLContext().accept(new SQLPredicateImpl(f -> f.notIn(this.getTable(), this.getValue(), arrays)));
        }
    }
}
