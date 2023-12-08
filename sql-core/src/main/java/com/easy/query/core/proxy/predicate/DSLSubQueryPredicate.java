package com.easy.query.core.proxy.predicate;

import com.easy.query.core.basic.api.select.Query;
import com.easy.query.core.proxy.TablePropColumn;
import com.easy.query.core.proxy.available.EntitySQLContextAvailable;
import com.easy.query.core.proxy.impl.SQLPredicateImpl;

/**
 * create time 2023/12/2 14:40
 * 文件说明
 *
 * @author xuejiaming
 */
public interface DSLSubQueryPredicate<TProperty> extends TablePropColumn, EntitySQLContextAvailable {
    default void ge(Query<TProperty> subQuery) {
        ge(true, subQuery);
    }

    default void ge(boolean condition, Query<TProperty> subQuery) {
        if (condition) {
            getEntitySQLContext().accept(new SQLPredicateImpl(f -> f.ge(this.getTable(), this.getValue(), subQuery)));
        }
    }

    default void gt(Query<TProperty> subQuery) {
        gt(true, subQuery);
    }

    default void gt(boolean condition, Query<TProperty> subQuery) {
        if (condition) {
            getEntitySQLContext().accept(new SQLPredicateImpl(f -> f.gt(this.getTable(), this.getValue(), subQuery)));
        }
    }

    default void eq(Query<TProperty> subQuery) {
        eq(true, subQuery);
    }

    default void eq(boolean condition, Query<TProperty> subQuery) {
        if (condition) {
            getEntitySQLContext().accept(new SQLPredicateImpl(f -> f.eq(this.getTable(), this.getValue(), subQuery)));
        }
    }

    default void ne(Query<TProperty> subQuery) {
        ne(true, subQuery);
    }

    default void ne(boolean condition, Query<TProperty> subQuery) {
        if (condition) {
            getEntitySQLContext().accept(new SQLPredicateImpl(f -> f.ne(this.getTable(), this.getValue(), subQuery)));
        }
    }

    default void le(Query<TProperty> subQuery) {
        le(true, subQuery);
    }

    default void le(boolean condition, Query<TProperty> subQuery) {
        if (condition) {
            getEntitySQLContext().accept(new SQLPredicateImpl(f -> f.le(this.getTable(), this.getValue(), subQuery)));
        }
    }

    default void lt(Query<TProperty> subQuery) {
        lt(true, subQuery);
    }

    default void lt(boolean condition, Query<TProperty> subQuery) {
        if (condition) {
            getEntitySQLContext().accept(new SQLPredicateImpl(f -> f.lt(this.getTable(), this.getValue(), subQuery)));
        }
    }

    default void in(Query<TProperty> subQuery) {
         in(true, subQuery);
    }

    default void in(boolean condition, Query<TProperty> subQuery) {
        if (condition) {
            getEntitySQLContext().accept(new SQLPredicateImpl(f -> f.in(this.getTable(), this.getValue(), subQuery)));
        }
    }

    default void notIn(Query<TProperty> subQuery) {
         notIn(true, subQuery);
    }

    default void notIn(boolean condition, Query<TProperty> subQuery) {
        if (condition) {
            getEntitySQLContext().accept(new SQLPredicateImpl(f -> f.notIn(this.getTable(), this.getValue(), subQuery)));
        }
    }
}
