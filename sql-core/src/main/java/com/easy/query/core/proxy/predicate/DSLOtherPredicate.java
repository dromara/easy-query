package com.easy.query.core.proxy.predicate;

import com.easy.query.core.proxy.SQLColumn;
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
    default <TProxy,TProp> SQLPredicateExpression ge(SQLColumn<TProxy,TProp> column) {
        return ge(true, column);
    }

    default <TProxy,TProp> SQLPredicateExpression ge(boolean condition, SQLColumn<TProxy,TProp> column) {
        if (condition) {
            return new SQLPredicateImpl(f -> f.ge(this.getTable(), this.getValue(), column.getTable(), column.getValue()));
        }
        return SQLPredicateExpression.empty;
    }
    default <TProxy,TProp> SQLPredicateExpression gt(SQLColumn<TProxy,TProp> column) {
        return gt(true, column);
    }

    default <TProxy,TProp> SQLPredicateExpression gt(boolean condition, SQLColumn<TProxy,TProp> column) {
        if (condition) {
            return new SQLPredicateImpl(f -> f.gt(this.getTable(), this.getValue(), column.getTable(), column.getValue()));
        }
        return SQLPredicateExpression.empty;
    }


    default <TProxy,TProp> SQLPredicateExpression eq(SQLColumn<TProxy,TProp> column) {
        return eq(true, column);
    }

    default <TProxy,TProp> SQLPredicateExpression eq(boolean condition, SQLColumn<TProxy,TProp> column) {
        if (condition) {
            return new SQLPredicateImpl(f -> f.eq(this.getTable(), this.getValue(), column.getTable(), column.getValue()));
        }
        return SQLPredicateExpression.empty;
    }



    default <TProxy,TProp> SQLPredicateExpression ne(SQLColumn<TProxy,TProp> column) {
        return ne(true, column);
    }

    default <TProxy,TProp> SQLPredicateExpression ne(boolean condition, SQLColumn<TProxy,TProp> column) {
        if (condition) {
            return new SQLPredicateImpl(f -> f.ne(this.getTable(), this.getValue(), column.getTable(), column.getValue()));
        }
        return SQLPredicateExpression.empty;
    }

    default <TProxy,TProp> SQLPredicateExpression le(SQLColumn<TProxy,TProp> column) {
        return le(true, column);
    }

    default <TProxy,TProp> SQLPredicateExpression le(boolean condition, SQLColumn<TProxy,TProp> column) {
        if (condition) {
            return new SQLPredicateImpl(f -> f.le(this.getTable(), this.getValue(), column.getTable(), column.getValue()));
        }
        return SQLPredicateExpression.empty;
    }

    default <TProxy,TProp> SQLPredicateExpression lt(SQLColumn<TProxy,TProp> column) {
        return lt(true, column);
    }

    default <TProxy,TProp> SQLPredicateExpression lt(boolean condition, SQLColumn<TProxy,TProp> column) {
        if (condition) {
            return new SQLPredicateImpl(f -> f.lt(this.getTable(), this.getValue(), column.getTable(), column.getValue()));
        }
        return SQLPredicateExpression.empty;
    }

}
