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
    default SQLSelectAsExpression alias(TablePropColumn propColumn) {
        return alias(propColumn.getValue());
    }
    @Override
    default SQLSelectAsExpression alias(String propertyAlias) {
        return new SQLSelectAsImpl(s -> {
            SQLFunc fx = s.getRuntimeContext().fx();
            s.columnFunc(this.getTable(), func().apply(fx), propertyAlias);
        }, s -> {
            SQLFunc fx = s.getRuntimeContext().fx();
            s.columnFunc(this.getTable(), this.getValue(), func().apply(fx), propertyAlias, () -> {
            });
        }, s -> {
            throw new UnsupportedOperationException();
        });
    }

    @Override
    default <T1 extends SQLTableOwner & DSLSQLFunctionAvailable> void ge(boolean condition, T1 column) {
        if (condition) {
            getEntitySQLContext().accept(new SQLPredicateImpl(f -> {
                SQLFunc fx = f.getRuntimeContext().fx();
                f.ge(this.getTable(), func().apply(fx), column.getTable(), column.func().apply(fx));
            }));
        }
    }

    @Override
    default <T1 extends SQLTableOwner & DSLSQLFunctionAvailable> void gt(boolean condition, T1 column) {
        if (condition) {
            getEntitySQLContext().accept(new SQLPredicateImpl(f -> {
                SQLFunc fx = f.getRuntimeContext().fx();
                f.gt(this.getTable(), func().apply(fx), column.getTable(), column.func().apply(fx));
            }));
        }
    }

    @Override
    default <T1 extends SQLTableOwner & DSLSQLFunctionAvailable> void eq(boolean condition, T1 column) {
        if (condition) {
            getEntitySQLContext().accept(new SQLPredicateImpl(f -> {
                SQLFunc fx = f.getRuntimeContext().fx();
                f.eq(this.getTable(), func().apply(fx), column.getTable(), column.func().apply(fx));
            }));
        }
    }

    @Override
    default <T1 extends SQLTableOwner & DSLSQLFunctionAvailable> void ne(boolean condition, T1 column) {
        if (condition) {
            getEntitySQLContext().accept(new SQLPredicateImpl(f -> {
                SQLFunc fx = f.getRuntimeContext().fx();
                f.ne(this.getTable(), func().apply(fx), column.getTable(), column.func().apply(fx));
            }));
        }
    }

    @Override
    default <T1 extends SQLTableOwner & DSLSQLFunctionAvailable> void le(boolean condition, T1 column) {
        if (condition) {
           getEntitySQLContext().accept(new SQLPredicateImpl(f -> {
               SQLFunc fx = f.getRuntimeContext().fx();
               f.le(this.getTable(), func().apply(fx), column.getTable(), column.func().apply(fx));
           }));
        }
    }

    @Override
    default <T1 extends SQLTableOwner & DSLSQLFunctionAvailable> void lt(boolean condition, T1 column) {
        if (condition) {
            getEntitySQLContext().accept(new SQLPredicateImpl(f -> {
                SQLFunc fx = f.getRuntimeContext().fx();
                f.lt(this.getTable(), func().apply(fx), column.getTable(), column.func().apply(fx));
            }));
        }
    }
}
