package com.easy.query.core.proxy.predicate;

import com.easy.query.core.proxy.TablePropColumn;
import com.easy.query.core.proxy.available.EntitySQLContextAvailable;
import com.easy.query.core.proxy.impl.SQLPredicateImpl;

/**
 * create time 2023/12/2 10:06
 * 文件说明
 *
 * @author xuejiaming
 */
public interface DSLValuePredicate<TProperty> extends TablePropColumn, EntitySQLContextAvailable {

    /**
     * column >= val
     * @param val 比较值
     */
    default void ge(TProperty val) {
        ge(true, val);
    }

    /**
     * column >= val
     * @param condition 是否要添加当前条件
     * @param val 比较值
     */
    default void ge(boolean condition, TProperty val) {
        if (condition) {
            getCurrentEntitySQLContext().accept(new SQLPredicateImpl(f -> f.ge(this.getTable(), this.getValue(), val)));
        }
    }

    /**
     * column > val
     * @param val 比较值
     */
    default void gt(TProperty val) {
        gt(true, val);
    }

    /**
     * column > val
     * @param condition 是否要添加当前条件
     * @param val 比较值
     */
    default void gt(boolean condition, TProperty val) {
        if (condition) {
            getCurrentEntitySQLContext().accept(new SQLPredicateImpl(f -> f.gt(this.getTable(), this.getValue(), val)));
        }
    }

    /**
     * column = val
     * @param val 比较值
     */
    default void eq(TProperty val) {
        eq(true, val);
    }

    /**
     * column = val
     * @param condition 是否要添加当前条件
     * @param val 比较值
     */
    default void eq(boolean condition, TProperty val) {
        if (condition) {
            getCurrentEntitySQLContext().accept(new SQLPredicateImpl(f -> f.eq(this.getTable(), this.getValue(), val)));
        }
    }

    /**
     * column <> val
     * @param val 比较值
     */
    default void ne(TProperty val) {
        ne(true, val);
    }

    /**
     * column <> val
     * @param condition 是否要添加当前条件
     * @param val 比较值
     */
    default void ne(boolean condition, TProperty val) {
        if (condition) {
            getCurrentEntitySQLContext().accept(new SQLPredicateImpl(f -> f.ne(this.getTable(), this.getValue(), val)));
        }
    }

    /**
     * column <= val
     * @param val 比较值
     */
    default void le(TProperty val) {
        le(true, val);
    }

    /**
     * column <= val
     * @param condition 是否要添加当前条件
     * @param val 比较值
     */
    default void le(boolean condition, TProperty val) {
        if (condition) {
            getCurrentEntitySQLContext().accept(new SQLPredicateImpl(f -> f.le(this.getTable(), this.getValue(), val)));
        }
    }

    /**
     * column < val
     * @param val 比较值
     */
    default void lt(TProperty val) {
        lt(true, val);
    }

    /**
     * column < val
     * @param condition 是否要添加当前条件
     * @param val 比较值
     */
    default void lt(boolean condition, TProperty val) {
        if (condition) {
            getCurrentEntitySQLContext().accept(new SQLPredicateImpl(f -> f.lt(this.getTable(), this.getValue(), val)));
        }
    }
}
