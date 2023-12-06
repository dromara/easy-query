package com.easy.query.core.proxy.extension;

import com.easy.query.core.expression.parser.core.SQLTableOwner;
import com.easy.query.core.func.SQLFunc;
import com.easy.query.core.proxy.SQLOrderByExpression;
import com.easy.query.core.proxy.SQLPredicateExpression;
import com.easy.query.core.proxy.SQLSelectAsExpression;
import com.easy.query.core.proxy.TablePropColumn;
import com.easy.query.core.proxy.impl.SQLPredicateImpl;
import com.easy.query.core.proxy.impl.SQLSelectAsImpl;
import com.easy.query.core.proxy.predicate.aggregate.DSLSQLFunctionAssertPredicate;
import com.easy.query.core.proxy.predicate.aggregate.DSLSQLFunctionAvailable;
import com.easy.query.core.proxy.predicate.aggregate.DSLValueAggregatePredicate;

/**
 * create time 2023/12/3 09:49
 * 文件说明
 *
 * @author xuejiaming
 */
public interface ColumnFuncComparableExpression<T> extends ColumnComparableExpression<T>, SQLOrderByExpression,
        DSLValueAggregatePredicate<T> ,
        DSLSQLFunctionAssertPredicate<T> {
    @Override
    default SQLSelectAsExpression as(TablePropColumn propColumn) {
        return new SQLSelectAsImpl(s -> {
            SQLFunc fx = s.getRuntimeContext().fx();
            s.columnFunc(this.getTable(), func().apply(fx), propColumn.getValue());
        }, s -> {
            SQLFunc fx = s.getRuntimeContext().fx();
            s.columnFunc(this.getTable(), this.getValue(), func().apply(fx), propColumn.getValue(), () -> {
            });
        });
    }

    @Override
    default <T1 extends SQLTableOwner & DSLSQLFunctionAvailable> SQLPredicateExpression ge(boolean condition, T1 column) {
        if (condition) {
            return new SQLPredicateImpl(f -> {
                SQLFunc fx = f.getRuntimeContext().fx();
                f.ge(this.getTable(), func().apply(fx), column.getTable(), column.func().apply(fx));
            });
        }
        return SQLPredicateExpression.empty;
    }

    @Override
    default <T1 extends SQLTableOwner & DSLSQLFunctionAvailable> SQLPredicateExpression gt(boolean condition, T1 column) {
        if (condition) {
            return new SQLPredicateImpl(f -> {
                SQLFunc fx = f.getRuntimeContext().fx();
                f.gt(this.getTable(), func().apply(fx), column.getTable(), column.func().apply(fx));
            });
        }
        return SQLPredicateExpression.empty;
    }

    @Override
    default <T1 extends SQLTableOwner & DSLSQLFunctionAvailable> SQLPredicateExpression eq(boolean condition, T1 column) {
        if (condition) {
            return new SQLPredicateImpl(f -> {
                SQLFunc fx = f.getRuntimeContext().fx();
                f.eq(this.getTable(), func().apply(fx), column.getTable(), column.func().apply(fx));
            });
        }
        return SQLPredicateExpression.empty;
    }

    @Override
    default <T1 extends SQLTableOwner & DSLSQLFunctionAvailable> SQLPredicateExpression ne(boolean condition, T1 column) {
        if (condition) {
            return new SQLPredicateImpl(f -> {
                SQLFunc fx = f.getRuntimeContext().fx();
                f.ne(this.getTable(), func().apply(fx), column.getTable(), column.func().apply(fx));
            });
        }
        return SQLPredicateExpression.empty;
    }

    @Override
    default <T1 extends SQLTableOwner & DSLSQLFunctionAvailable> SQLPredicateExpression le(boolean condition, T1 column) {
        if (condition) {
            return new SQLPredicateImpl(f -> {
                SQLFunc fx = f.getRuntimeContext().fx();
                f.le(this.getTable(), func().apply(fx), column.getTable(), column.func().apply(fx));
            });
        }
        return SQLPredicateExpression.empty;
    }

    @Override
    default <T1 extends SQLTableOwner & DSLSQLFunctionAvailable> SQLPredicateExpression lt(boolean condition, T1 column) {
        if (condition) {
            return new SQLPredicateImpl(f -> {
                SQLFunc fx = f.getRuntimeContext().fx();
                f.lt(this.getTable(), func().apply(fx), column.getTable(), column.func().apply(fx));
            });
        }
        return SQLPredicateExpression.empty;
    }
}
