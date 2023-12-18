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
    default void likeMatchLeft(TProperty val) {
         likeMatchLeft(true, val);
    }

    default void likeMatchLeft(boolean condition, TProperty val) {
        if (condition) {
            getEntitySQLContext().accept(new SQLPredicateImpl(f -> f.like(this.getTable(), this.getValue(), val, SQLLikeEnum.LIKE_PERCENT_RIGHT)));
        }
    }
    default void likeMatchRight(TProperty val) {
        likeMatchRight(true, val);
    }

    default void likeMatchRight(boolean condition, TProperty val) {
        if (condition) {
            getEntitySQLContext().accept(new SQLPredicateImpl(f -> f.like(this.getTable(), this.getValue(), val, SQLLikeEnum.LIKE_PERCENT_LEFT)));
        }
    }
    default void like(TProperty val) {
         like(true, val);
    }

    default void like(boolean condition, TProperty val) {
        if (condition) {
           getEntitySQLContext().accept(new SQLPredicateImpl(f -> f.like(this.getTable(), this.getValue(), val, SQLLikeEnum.LIKE_PERCENT_ALL)));
        }
    }
    default void notLikeMatchLeft(TProperty val) {
         notLikeMatchLeft(true, val);
    }

    default void notLikeMatchLeft(boolean condition, TProperty val) {
        if (condition) {
            getEntitySQLContext().accept(new SQLPredicateImpl(f -> f.notLike(this.getTable(), this.getValue(), val, SQLLikeEnum.LIKE_PERCENT_RIGHT)));
        }
    }
    default void notLikeMatchRight(TProperty val) {
        notLikeMatchRight(true, val);
    }

    default void notLikeMatchRight(boolean condition, TProperty val) {
        if (condition) {
            getEntitySQLContext().accept(new SQLPredicateImpl(f -> f.notLike(this.getTable(), this.getValue(), val, SQLLikeEnum.LIKE_PERCENT_LEFT)));
        }
    }
    default void notLike(TProperty val) {
         notLike(true, val);
    }

    default void notLike(boolean condition, TProperty val) {
        if (condition) {
           getEntitySQLContext().accept(new SQLPredicateImpl(f -> f.notLike(this.getTable(), this.getValue(), val, SQLLikeEnum.LIKE_PERCENT_ALL)));
        }
    }
}
