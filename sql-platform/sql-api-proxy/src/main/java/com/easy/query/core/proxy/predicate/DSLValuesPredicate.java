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
    /**
     * column in (collection)
     * 当集合{@param collection}为空时生成条件为[1=2]
     * @param collection 集合
     */
    default void in(Collection<? extends TProperty> collection) {
         in(true, collection);
    }

    /**
     *  column in (collection)
     * 当集合{@param collection}为空时生成条件为[1=2]
     * @param condition 是否采用当前条件 true:使用in,false不使用in
     * @param collections 集合
     */
    default void in(boolean condition, Collection<? extends TProperty> collections){
        if(condition){
            getCurrentEntitySQLContext().accept(new SQLPredicateImpl(f -> f.in(this.getTable(), this.getValue(), collections)));
        }
    }

    /**
     * column in (arrays)
     * 当数组{@param arrays}为空时生成条件为[1=2]
     * @param arrays 数组
     */
    default void in(TProperty[] arrays) {
         in(true, arrays);
    }

    /**
     * column in (arrays)
     * 当数组{@param arrays}为空时生成条件为[1=2]
     * @param condition 是否采用当前条件 true:使用in,false不使用in
     * @param arrays 数组
     */
    default void in(boolean condition, TProperty[] arrays){
        if(condition){
           getCurrentEntitySQLContext().accept(new SQLPredicateImpl(f -> f.in(this.getTable(), this.getValue(), arrays)));
        }
    }

    /**
     * column in (collection)
     * 当集合{@param collection}为空时生成条件为[1=1]
     * @param collections 集合
     */
    default void notIn(Collection<? extends TProperty> collections) {
         notIn(true, collections);
    }

    /**
     * column in (collection)
     * 当集合{@param collection}为空时生成条件为[1=1]
     * @param condition 是否采用当前条件 true:使用in,false不使用in
     * @param collections 集合
     */
    default void notIn(boolean condition, Collection<? extends TProperty> collections){
        if(condition){
            getCurrentEntitySQLContext().accept(new SQLPredicateImpl(f -> f.notIn(this.getTable(), this.getValue(), collections)));
        }
    }

    /**
     * column in (arrays)
     * 当数组{@param arrays}为空时生成条件为[1=1]
     * @param arrays 数组
     */
    default void notIn(TProperty[] arrays) {
         notIn(true, arrays);
    }

    /**
     * column in (arrays)
     * 当数组{@param arrays}为空时生成条件为[1=1]
     * @param condition 是否采用当前条件 true:使用in,false不使用in
     * @param arrays 数组
     */
    default void notIn(boolean condition, TProperty[] arrays){
        if(condition){
            getCurrentEntitySQLContext().accept(new SQLPredicateImpl(f -> f.notIn(this.getTable(), this.getValue(), arrays)));
        }
    }
}
