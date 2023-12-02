package com.easy.query.core.proxy.predicate;

import com.easy.query.core.enums.SQLLikeEnum;
import com.easy.query.core.proxy.SQLPredicate;
import com.easy.query.core.proxy.TablePropColumn;
import com.easy.query.core.proxy.impl.SQLPredicateImpl;

/**
 * create time 2023/12/2 14:13
 * 文件说明
 *
 * @author xuejiaming
 */
public interface DSLLikePredicate<TProperty> extends TablePropColumn {
    default SQLPredicate likeMatchLeft(TProperty val) {
        return likeMatchLeft(true, val);
    }

    default SQLPredicate likeMatchLeft(boolean condition, TProperty val) {
        if (condition) {
            return new SQLPredicateImpl(f -> f.like(this.getTable(), this.value(), val, SQLLikeEnum.LIKE_PERCENT_RIGHT));
        }
        return SQLPredicate.empty;
    }
    default SQLPredicate likeMatchRight(TProperty val) {
        return likeMatchLeft(true, val);
    }

    default SQLPredicate likeMatchRight(boolean condition, TProperty val) {
        if (condition) {
            return new SQLPredicateImpl(f -> f.like(this.getTable(), this.value(), val, SQLLikeEnum.LIKE_PERCENT_LEFT));
        }
        return SQLPredicate.empty;
    }
    default SQLPredicate like(TProperty val) {
        return like(true, val);
    }

    default SQLPredicate like(boolean condition, TProperty val) {
        if (condition) {
            return new SQLPredicateImpl(f -> f.like(this.getTable(), this.value(), val, SQLLikeEnum.LIKE_PERCENT_ALL));
        }
        return SQLPredicate.empty;
    }
    default SQLPredicate notLikeMatchLeft(TProperty val) {
        return notLikeMatchLeft(true, val);
    }

    default SQLPredicate notLikeMatchLeft(boolean condition, TProperty val) {
        if (condition) {
            return new SQLPredicateImpl(f -> f.notLike(this.getTable(), this.value(), val, SQLLikeEnum.LIKE_PERCENT_RIGHT));
        }
        return SQLPredicate.empty;
    }
    default SQLPredicate notLikeMatchRight(TProperty val) {
        return notLikeMatchRight(true, val);
    }

    default SQLPredicate notLikeMatchRight(boolean condition, TProperty val) {
        if (condition) {
            return new SQLPredicateImpl(f -> f.notLike(this.getTable(), this.value(), val, SQLLikeEnum.LIKE_PERCENT_LEFT));
        }
        return SQLPredicate.empty;
    }
    default SQLPredicate notLike(TProperty val) {
        return notLike(true, val);
    }

    default SQLPredicate notLike(boolean condition, TProperty val) {
        if (condition) {
            return new SQLPredicateImpl(f -> f.notLike(this.getTable(), this.value(), val, SQLLikeEnum.LIKE_PERCENT_ALL));
        }
        return SQLPredicate.empty;
    }
}
