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

    default void ge(TProperty val) {
        ge(true, val);
    }

    default void ge(boolean condition, TProperty val) {
        if (condition) {
            getEntitySQLContext().accept(new SQLPredicateImpl(f -> f.ge(this.getTable(), this.getValue(), val)));
        }
    }

    default void gt(TProperty val) {
        gt(true, val);
    }

    default void gt(boolean condition, TProperty val) {
        if (condition) {
            getEntitySQLContext().accept(new SQLPredicateImpl(f -> f.gt(this.getTable(), this.getValue(), val)));
        }
    }

    default void eq(TProperty val) {
        eq(true, val);
    }

    default void eq(boolean condition, TProperty val) {
        if (condition) {
            getEntitySQLContext().accept(new SQLPredicateImpl(f -> f.eq(this.getTable(), this.getValue(), val)));
        }
    }

    default void ne(TProperty val) {
        ne(true, val);
    }

    default void ne(boolean condition, TProperty val) {
        if (condition) {
            getEntitySQLContext().accept(new SQLPredicateImpl(f -> f.ne(this.getTable(), this.getValue(), val)));
        }
    }

    default void le(TProperty val) {
        le(true, val);
    }

    default void le(boolean condition, TProperty val) {
        if (condition) {
            getEntitySQLContext().accept(new SQLPredicateImpl(f -> f.le(this.getTable(), this.getValue(), val)));
        }
    }

    default void lt(TProperty val) {
        lt(true, val);
    }

    default void lt(boolean condition, TProperty val) {
        if (condition) {
            getEntitySQLContext().accept(new SQLPredicateImpl(f -> f.lt(this.getTable(), this.getValue(), val)));
        }
    }
}
