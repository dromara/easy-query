package com.easy.query.core.proxy.predicate;

import com.easy.query.core.proxy.SQLPredicate;
import com.easy.query.core.proxy.TablePropColumn;
import com.easy.query.core.proxy.impl.SQLPredicateImpl;

/**
 * create time 2023/12/2 14:18
 * 文件说明
 *
 * @author xuejiaming
 */
public interface DSLOtherPredicate<TProperty> extends TablePropColumn {
    default SQLPredicate ge(DSLOtherPredicate<TProperty> column) {
        return ge(true, column);
    }

    default SQLPredicate ge(boolean condition, DSLOtherPredicate<TProperty> column) {
        if (condition) {
            return new SQLPredicateImpl(f -> f.ge(this.getTable(), this.value(), column.getTable(), column.value()));
        }
        return SQLPredicate.empty;
    }
    default SQLPredicate gt(DSLOtherPredicate<TProperty> column) {
        return gt(true, column);
    }

    default SQLPredicate gt(boolean condition, DSLOtherPredicate<TProperty> column) {
        if (condition) {
            return new SQLPredicateImpl(f -> f.gt(this.getTable(), this.value(), column.getTable(), column.value()));
        }
        return SQLPredicate.empty;
    }


    default SQLPredicate eq(DSLOtherPredicate<TProperty> column) {
        return eq(true, column);
    }

    default SQLPredicate eq(boolean condition, DSLOtherPredicate<TProperty> column) {
        if (condition) {
            return new SQLPredicateImpl(f -> f.eq(this.getTable(), this.value(), column.getTable(), column.value()));
        }
        return SQLPredicate.empty;
    }



    default SQLPredicate ne(DSLOtherPredicate<TProperty> column) {
        return ne(true, column);
    }

    default SQLPredicate ne(boolean condition, DSLOtherPredicate<TProperty> column) {
        if (condition) {
            return new SQLPredicateImpl(f -> f.ne(this.getTable(), this.value(), column.getTable(), column.value()));
        }
        return SQLPredicate.empty;
    }

    default SQLPredicate le(DSLOtherPredicate<TProperty> column) {
        return le(true, column);
    }

    default SQLPredicate le(boolean condition, DSLOtherPredicate<TProperty> column) {
        if (condition) {
            return new SQLPredicateImpl(f -> f.le(this.getTable(), this.value(), column.getTable(), column.value()));
        }
        return SQLPredicate.empty;
    }

    default SQLPredicate lt(DSLOtherPredicate<TProperty> column) {
        return lt(true, column);
    }

    default SQLPredicate lt(boolean condition, DSLOtherPredicate<TProperty> column) {
        if (condition) {
            return new SQLPredicateImpl(f -> f.lt(this.getTable(), this.value(), column.getTable(), column.value()));
        }
        return SQLPredicate.empty;
    }

}
