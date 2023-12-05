package com.easy.query.core.proxy.predicate;

import com.easy.query.core.basic.api.select.Query;
import com.easy.query.core.proxy.SQLPredicateExpression;
import com.easy.query.core.proxy.TablePropColumn;
import com.easy.query.core.proxy.impl.SQLPredicateImpl;

/**
 * create time 2023/12/2 14:40
 * 文件说明
 *
 * @author xuejiaming
 */
public interface DSLSubQueryPredicate<TProperty> extends TablePropColumn {
    default SQLPredicateExpression ge(Query<TProperty> subQuery) {
        return ge(true, subQuery);
    }

    default SQLPredicateExpression ge(boolean condition, Query<TProperty> subQuery) {
        if (condition) {
            return new SQLPredicateImpl(f -> f.ge(this.getTable(), this.value(), subQuery));
        }
        return SQLPredicateExpression.empty;
    }
    default SQLPredicateExpression gt(Query<TProperty> subQuery) {
        return gt(true, subQuery);
    }

    default SQLPredicateExpression gt(boolean condition, Query<TProperty> subQuery) {
        if (condition) {
            return new SQLPredicateImpl(f -> f.gt(this.getTable(), this.value(), subQuery));
        }
        return SQLPredicateExpression.empty;
    }
    default SQLPredicateExpression eq(Query<TProperty> subQuery) {
        return eq(true, subQuery);
    }

    default SQLPredicateExpression eq(boolean condition, Query<TProperty> subQuery) {
        if (condition) {
            return new SQLPredicateImpl(f -> f.eq(this.getTable(), this.value(), subQuery));
        }
        return SQLPredicateExpression.empty;
    }
    default SQLPredicateExpression ne(Query<TProperty> subQuery) {
        return ne(true, subQuery);
    }

    default SQLPredicateExpression ne(boolean condition, Query<TProperty> subQuery) {
        if (condition) {
            return new SQLPredicateImpl(f -> f.ne(this.getTable(), this.value(), subQuery));
        }
        return SQLPredicateExpression.empty;
    }
    default SQLPredicateExpression le(Query<TProperty> subQuery) {
        return le(true, subQuery);
    }

    default SQLPredicateExpression le(boolean condition, Query<TProperty> subQuery) {
        if (condition) {
            return new SQLPredicateImpl(f -> f.le(this.getTable(), this.value(), subQuery));
        }
        return SQLPredicateExpression.empty;
    }
    default SQLPredicateExpression lt(Query<TProperty> subQuery) {
        return lt(true, subQuery);
    }

    default SQLPredicateExpression lt(boolean condition, Query<TProperty> subQuery) {
        if (condition) {
            return new SQLPredicateImpl(f -> f.lt(this.getTable(), this.value(), subQuery));
        }
        return SQLPredicateExpression.empty;
    }
    default SQLPredicateExpression in(Query<TProperty> subQuery) {
        return in(true, subQuery);
    }

    default SQLPredicateExpression in(boolean condition, Query<TProperty> subQuery) {
        if (condition) {
            return new SQLPredicateImpl(f -> f.in(this.getTable(), this.value(), subQuery));
        }
        return SQLPredicateExpression.empty;
    }
    default SQLPredicateExpression notIn(Query<TProperty> subQuery) {
        return notIn(true, subQuery);
    }

    default SQLPredicateExpression notIn(boolean condition, Query<TProperty> subQuery) {
        if (condition) {
            return new SQLPredicateImpl(f -> f.notIn(this.getTable(), this.value(), subQuery));
        }
        return SQLPredicateExpression.empty;
    }
}
