package com.easy.query.core.proxy.predicate;

import com.easy.query.core.basic.extension.conversion.ValueConverter;
import com.easy.query.core.enums.SQLLikeEnum;
import com.easy.query.core.proxy.TablePropColumn;
import com.easy.query.core.proxy.available.EntitySQLContextAvailable;
import com.easy.query.core.proxy.impl.SQLPredicateImpl;

/**
 * create time 2023/12/2 14:13
 * column like val,val必须是值不可以是函数
 *
 * @author xuejiaming
 */
public interface DSLLikePredicate<TProperty> extends TablePropColumn, EntitySQLContextAvailable {
    /**
     * column like 'value%'
     * @param val
     */
    default void likeMatchLeft(TProperty val) {
        likeMatchLeft(true, val);
    }

    /**
     * column like 'value%'
     * @param condition
     * @param val
     */
    default void likeMatchLeft(boolean condition, TProperty val) {
        if (condition) {
            getEntitySQLContext().accept(new SQLPredicateImpl(f -> f.like(this.getTable(), this.getValue(), val, SQLLikeEnum.LIKE_PERCENT_RIGHT)));
        }
    }

    /**
     * column like '%value'
     * @param val
     */
    default void likeMatchRight(TProperty val) {
        likeMatchRight(true, val);
    }

    /**
     * column like '%value'
     * @param condition
     * @param val
     */
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

    default void likeRawMatchLeft(Object val) {
        likeRawMatchLeft(true, val);
    }

    default void likeRawMatchLeft(boolean condition, Object val) {
        if (condition) {
            getEntitySQLContext().accept(new SQLPredicateImpl(f -> f.likeRaw(this.getTable(), this.getValue(), val, SQLLikeEnum.LIKE_PERCENT_RIGHT)));
        }
    }
    default void likeRawMatchRight(Object val) {
        likeRawMatchRight(true, val);
    }

    default void likeRawMatchRight(boolean condition, Object val) {
        if (condition) {
            getEntitySQLContext().accept(new SQLPredicateImpl(f -> f.likeRaw(this.getTable(), this.getValue(), val, SQLLikeEnum.LIKE_PERCENT_LEFT)));
        }
    }
    /**
     * 和传入的指定数据匹配因为普通匹配入参会经过{@link ValueConverter}处理
     * @param val
     */
    default void likeRaw(Object val) {
        likeRaw(true, val);
    }

    /**
     * 和传入的指定数据匹配因为普通匹配入参会经过{@link ValueConverter}处理
     * @param condition
     * @param val
     */
    default void likeRaw(boolean condition, Object val) {
        if (condition) {
            getEntitySQLContext().accept(new SQLPredicateImpl(f -> f.likeRaw(this.getTable(), this.getValue(), val, SQLLikeEnum.LIKE_PERCENT_ALL)));
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

    default void notLikeRawMatchLeft(Object val) {
        notLikeRawMatchLeft(true, val);
    }

    default void notLikeRawMatchLeft(boolean condition, Object val) {
        if (condition) {
            getEntitySQLContext().accept(new SQLPredicateImpl(f -> f.notLikeRaw(this.getTable(), this.getValue(), val, SQLLikeEnum.LIKE_PERCENT_RIGHT)));
        }
    }
    default void notLikeRawMatchRight(Object val) {
        notLikeRawMatchRight(true, val);
    }

    default void notLikeRawMatchRight(boolean condition, Object val) {
        if (condition) {
            getEntitySQLContext().accept(new SQLPredicateImpl(f -> f.notLikeRaw(this.getTable(), this.getValue(), val, SQLLikeEnum.LIKE_PERCENT_LEFT)));
        }
    }
    /**
     * 和传入的指定数据匹配因为普通匹配入参会经过{@link ValueConverter}处理
     * @param val
     */
    default void notLikeRaw(Object val) {
        notLikeRaw(true, val);
    }

    /**
     * 和传入的指定数据匹配因为普通匹配入参会经过{@link ValueConverter}处理
     * @param condition
     * @param val
     */
    default void notLikeRaw(boolean condition, Object val) {
        if (condition) {
            getEntitySQLContext().accept(new SQLPredicateImpl(f -> f.notLikeRaw(this.getTable(), this.getValue(), val, SQLLikeEnum.LIKE_PERCENT_ALL)));
        }
    }
}
