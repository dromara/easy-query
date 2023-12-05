package com.easy.query.core.proxy.predicate;

import com.easy.query.core.proxy.SQLPredicateExpression;
import com.easy.query.core.proxy.TablePropColumn;
import com.easy.query.core.proxy.impl.SQLPredicateImpl;

/**
 * create time 2023/12/2 14:18
 * 文件说明
 *
 * @author xuejiaming
 */
public interface DSLOtherPredicate<TProperty> extends TablePropColumn {
    default SQLPredicateExpression ge(DSLOtherPredicate<TProperty> column) {
        return ge(true, column);
    }

    default SQLPredicateExpression ge(boolean condition, DSLOtherPredicate<TProperty> column) {
        if (condition) {
            return new SQLPredicateImpl(f -> f.ge(this.getTable(), this.value(), column.getTable(), column.value()));
        }
        return SQLPredicateExpression.empty;
    }
    default SQLPredicateExpression gt(DSLOtherPredicate<TProperty> column) {
        return gt(true, column);
    }

    default SQLPredicateExpression gt(boolean condition, DSLOtherPredicate<TProperty> column) {
        if (condition) {
            return new SQLPredicateImpl(f -> f.gt(this.getTable(), this.value(), column.getTable(), column.value()));
        }
        return SQLPredicateExpression.empty;
    }


    default SQLPredicateExpression eq(DSLOtherPredicate<TProperty> column) {
        return eq(true, column);
    }

    default SQLPredicateExpression eq(boolean condition, DSLOtherPredicate<TProperty> column) {
        if (condition) {
            return new SQLPredicateImpl(f -> f.eq(this.getTable(), this.value(), column.getTable(), column.value()));
        }
        return SQLPredicateExpression.empty;
    }



    default SQLPredicateExpression ne(DSLOtherPredicate<TProperty> column) {
        return ne(true, column);
    }

    default SQLPredicateExpression ne(boolean condition, DSLOtherPredicate<TProperty> column) {
        if (condition) {
            return new SQLPredicateImpl(f -> f.ne(this.getTable(), this.value(), column.getTable(), column.value()));
        }
        return SQLPredicateExpression.empty;
    }

    default SQLPredicateExpression le(DSLOtherPredicate<TProperty> column) {
        return le(true, column);
    }

    default SQLPredicateExpression le(boolean condition, DSLOtherPredicate<TProperty> column) {
        if (condition) {
            return new SQLPredicateImpl(f -> f.le(this.getTable(), this.value(), column.getTable(), column.value()));
        }
        return SQLPredicateExpression.empty;
    }

    default SQLPredicateExpression lt(DSLOtherPredicate<TProperty> column) {
        return lt(true, column);
    }

    default SQLPredicateExpression lt(boolean condition, DSLOtherPredicate<TProperty> column) {
        if (condition) {
            return new SQLPredicateImpl(f -> f.lt(this.getTable(), this.value(), column.getTable(), column.value()));
        }
        return SQLPredicateExpression.empty;
    }

}
