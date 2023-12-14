package com.easy.query.core.proxy.predicate.aggregate;

import com.easy.query.core.expression.parser.core.SQLTableOwner;
import com.easy.query.core.func.SQLFunc;
import com.easy.query.core.proxy.impl.SQLPredicateImpl;
import com.easy.query.core.proxy.predicate.DSLFunctionPredicate;

/**
 * create time 2023/12/14 22:53
 * 文件说明
 *
 * @author xuejiaming
 */
public interface DSLFunctionAggregatePredicate<TProperty> extends DSLFunctionPredicate<TProperty>,DSLSQLFunctionAvailable {
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
