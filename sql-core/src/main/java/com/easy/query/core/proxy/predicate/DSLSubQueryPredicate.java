package com.easy.query.core.proxy.predicate;

import com.easy.query.core.basic.api.select.Query;
import com.easy.query.core.proxy.SQLPredicate;
import com.easy.query.core.proxy.TablePropColumn;
import com.easy.query.core.proxy.impl.SQLPredicateImpl;

/**
 * create time 2023/12/2 14:40
 * 文件说明
 *
 * @author xuejiaming
 */
public interface DSLSubQueryPredicate<TProperty> extends TablePropColumn {
    default SQLPredicate ge(Query<TProperty> subQuery) {
        return ge(true, subQuery);
    }

    default SQLPredicate ge(boolean condition, Query<TProperty> subQuery) {
        if (condition) {
            return new SQLPredicateImpl(f -> f.ge(this.getTable(), this.value(), subQuery));
        }
        return SQLPredicate.empty;
    }
    default SQLPredicate gt(Query<TProperty> subQuery) {
        return gt(true, subQuery);
    }

    default SQLPredicate gt(boolean condition, Query<TProperty> subQuery) {
        if (condition) {
            return new SQLPredicateImpl(f -> f.gt(this.getTable(), this.value(), subQuery));
        }
        return SQLPredicate.empty;
    }
    default SQLPredicate eq(Query<TProperty> subQuery) {
        return eq(true, subQuery);
    }

    default SQLPredicate eq(boolean condition, Query<TProperty> subQuery) {
        if (condition) {
            return new SQLPredicateImpl(f -> f.eq(this.getTable(), this.value(), subQuery));
        }
        return SQLPredicate.empty;
    }
    default SQLPredicate ne(Query<TProperty> subQuery) {
        return ne(true, subQuery);
    }

    default SQLPredicate ne(boolean condition, Query<TProperty> subQuery) {
        if (condition) {
            return new SQLPredicateImpl(f -> f.ne(this.getTable(), this.value(), subQuery));
        }
        return SQLPredicate.empty;
    }
    default SQLPredicate le(Query<TProperty> subQuery) {
        return le(true, subQuery);
    }

    default SQLPredicate le(boolean condition, Query<TProperty> subQuery) {
        if (condition) {
            return new SQLPredicateImpl(f -> f.le(this.getTable(), this.value(), subQuery));
        }
        return SQLPredicate.empty;
    }
    default SQLPredicate lt(Query<TProperty> subQuery) {
        return lt(true, subQuery);
    }

    default SQLPredicate lt(boolean condition, Query<TProperty> subQuery) {
        if (condition) {
            return new SQLPredicateImpl(f -> f.lt(this.getTable(), this.value(), subQuery));
        }
        return SQLPredicate.empty;
    }
    default SQLPredicate in(Query<TProperty> subQuery) {
        return in(true, subQuery);
    }

    default SQLPredicate in(boolean condition, Query<TProperty> subQuery) {
        if (condition) {
            return new SQLPredicateImpl(f -> f.in(this.getTable(), this.value(), subQuery));
        }
        return SQLPredicate.empty;
    }
    default SQLPredicate notIn(Query<TProperty> subQuery) {
        return notIn(true, subQuery);
    }

    default SQLPredicate notIn(boolean condition, Query<TProperty> subQuery) {
        if (condition) {
            return new SQLPredicateImpl(f -> f.notIn(this.getTable(), this.value(), subQuery));
        }
        return SQLPredicate.empty;
    }
}
