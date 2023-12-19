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

    default void ge(Object val) {
        ge(true, val);
    }

    default void ge(boolean condition, Object val) {
        if (condition) {
            getEntitySQLContext().accept(new SQLPredicateImpl(f -> f.ge(this.getTable(), this.getValue(), val)));
        }
    }

    default void gt(Object val) {
        gt(true, val);
    }

    default void gt(boolean condition, Object val) {
        if (condition) {
            getEntitySQLContext().accept(new SQLPredicateImpl(f -> f.gt(this.getTable(), this.getValue(), val)));
        }
    }

    default void eq(Object val) {
        eq(true, val);
    }

    default void eq(boolean condition, Object val) {
        if (condition) {
            getEntitySQLContext().accept(new SQLPredicateImpl(f -> f.eq(this.getTable(), this.getValue(), val)));
        }
    }

    default void ne(Object val) {
        ne(true, val);
    }

    default void ne(boolean condition, Object val) {
        if (condition) {
            getEntitySQLContext().accept(new SQLPredicateImpl(f -> f.ne(this.getTable(), this.getValue(), val)));
        }
    }

    default void le(Object val) {
        le(true, val);
    }

    default void le(boolean condition, Object val) {
        if (condition) {
            getEntitySQLContext().accept(new SQLPredicateImpl(f -> f.le(this.getTable(), this.getValue(), val)));
        }
    }

    default void lt(Object val) {
        lt(true, val);
    }

    default void lt(boolean condition, Object val) {
        if (condition) {
            getEntitySQLContext().accept(new SQLPredicateImpl(f -> f.lt(this.getTable(), this.getValue(), val)));
        }
    }
}
