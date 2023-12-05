package com.easy.query.core.proxy.predicate;

import com.easy.query.core.enums.SQLLikeEnum;
import com.easy.query.core.proxy.SQLPredicateExpression;
import com.easy.query.core.proxy.TablePropColumn;
import com.easy.query.core.proxy.impl.SQLPredicateImpl;

/**
 * create time 2023/12/2 14:13
 * 文件说明
 *
 * @author xuejiaming
 */
public interface DSLLikePredicate<TProperty> extends TablePropColumn {
    default SQLPredicateExpression likeMatchLeft(TProperty val) {
        return likeMatchLeft(true, val);
    }

    default SQLPredicateExpression likeMatchLeft(boolean condition, TProperty val) {
        if (condition) {
            return new SQLPredicateImpl(f -> f.like(this.getTable(), this.value(), val, SQLLikeEnum.LIKE_PERCENT_RIGHT));
        }
        return SQLPredicateExpression.empty;
    }
    default SQLPredicateExpression likeMatchRight(TProperty val) {
        return likeMatchLeft(true, val);
    }

    default SQLPredicateExpression likeMatchRight(boolean condition, TProperty val) {
        if (condition) {
            return new SQLPredicateImpl(f -> f.like(this.getTable(), this.value(), val, SQLLikeEnum.LIKE_PERCENT_LEFT));
        }
        return SQLPredicateExpression.empty;
    }
    default SQLPredicateExpression like(TProperty val) {
        return like(true, val);
    }

    default SQLPredicateExpression like(boolean condition, TProperty val) {
        if (condition) {
            return new SQLPredicateImpl(f -> f.like(this.getTable(), this.value(), val, SQLLikeEnum.LIKE_PERCENT_ALL));
        }
        return SQLPredicateExpression.empty;
    }
    default SQLPredicateExpression notLikeMatchLeft(TProperty val) {
        return notLikeMatchLeft(true, val);
    }

    default SQLPredicateExpression notLikeMatchLeft(boolean condition, TProperty val) {
        if (condition) {
            return new SQLPredicateImpl(f -> f.notLike(this.getTable(), this.value(), val, SQLLikeEnum.LIKE_PERCENT_RIGHT));
        }
        return SQLPredicateExpression.empty;
    }
    default SQLPredicateExpression notLikeMatchRight(TProperty val) {
        return notLikeMatchRight(true, val);
    }

    default SQLPredicateExpression notLikeMatchRight(boolean condition, TProperty val) {
        if (condition) {
            return new SQLPredicateImpl(f -> f.notLike(this.getTable(), this.value(), val, SQLLikeEnum.LIKE_PERCENT_LEFT));
        }
        return SQLPredicateExpression.empty;
    }
    default SQLPredicateExpression notLike(TProperty val) {
        return notLike(true, val);
    }

    default SQLPredicateExpression notLike(boolean condition, TProperty val) {
        if (condition) {
            return new SQLPredicateImpl(f -> f.notLike(this.getTable(), this.value(), val, SQLLikeEnum.LIKE_PERCENT_ALL));
        }
        return SQLPredicateExpression.empty;
    }
}
