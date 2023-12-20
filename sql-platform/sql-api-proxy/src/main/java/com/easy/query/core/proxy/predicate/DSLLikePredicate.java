package com.easy.query.core.proxy.predicate;

import com.easy.query.core.enums.SQLLikeEnum;
import com.easy.query.core.proxy.TablePropColumn;
import com.easy.query.core.proxy.available.EntitySQLContextAvailable;
import com.easy.query.core.proxy.impl.SQLPredicateImpl;

/**
 * create time 2023/12/2 14:13
 * 文件说明
 *
 * @author xuejiaming
 */
public interface DSLLikePredicate<TProperty> extends TablePropColumn, EntitySQLContextAvailable {
    default void likeMatchLeft(Object val) {
         likeMatchLeft(true, val);
    }

    default void likeMatchLeft(boolean condition, Object val) {
        if (condition) {
            getEntitySQLContext().accept(new SQLPredicateImpl(f -> f.like(this.getTable(), this.getValue(), val, SQLLikeEnum.LIKE_PERCENT_RIGHT)));
        }
    }
    default void likeMatchRight(Object val) {
        likeMatchRight(true, val);
    }

    default void likeMatchRight(boolean condition, Object val) {
        if (condition) {
            getEntitySQLContext().accept(new SQLPredicateImpl(f -> f.like(this.getTable(), this.getValue(), val, SQLLikeEnum.LIKE_PERCENT_LEFT)));
        }
    }
    default void like(Object val) {
         like(true, val);
    }

    default void like(boolean condition, Object val) {
        if (condition) {
           getEntitySQLContext().accept(new SQLPredicateImpl(f -> f.like(this.getTable(), this.getValue(), val, SQLLikeEnum.LIKE_PERCENT_ALL)));
        }
    }
    default void notLikeMatchLeft(Object val) {
         notLikeMatchLeft(true, val);
    }

    default void notLikeMatchLeft(boolean condition, Object val) {
        if (condition) {
            getEntitySQLContext().accept(new SQLPredicateImpl(f -> f.notLike(this.getTable(), this.getValue(), val, SQLLikeEnum.LIKE_PERCENT_RIGHT)));
        }
    }
    default void notLikeMatchRight(Object val) {
        notLikeMatchRight(true, val);
    }

    default void notLikeMatchRight(boolean condition, Object val) {
        if (condition) {
            getEntitySQLContext().accept(new SQLPredicateImpl(f -> f.notLike(this.getTable(), this.getValue(), val, SQLLikeEnum.LIKE_PERCENT_LEFT)));
        }
    }
    default void notLike(Object val) {
         notLike(true, val);
    }

    default void notLike(boolean condition, Object val) {
        if (condition) {
           getEntitySQLContext().accept(new SQLPredicateImpl(f -> f.notLike(this.getTable(), this.getValue(), val, SQLLikeEnum.LIKE_PERCENT_ALL)));
        }
    }
}
