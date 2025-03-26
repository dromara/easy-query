package com.easy.query.core.proxy.predicate;

import com.easy.query.core.expression.parser.core.SQLTableOwner;
import com.easy.query.core.func.SQLFunc;
import com.easy.query.core.proxy.TablePropColumn;
import com.easy.query.core.proxy.available.EntitySQLContextAvailable;
import com.easy.query.core.proxy.impl.SQLPredicateImpl;
import com.easy.query.core.proxy.predicate.aggregate.DSLSQLFunctionAvailable;

/**
 * create time 2023/12/2 14:18
 * 文件说明
 *
 * @author xuejiaming
 */
public interface DSLFunctionComparePredicate<TProperty> extends TablePropColumn, EntitySQLContextAvailable {
    default <T extends SQLTableOwner & DSLSQLFunctionAvailable> void ge(T column) {
        ge(true, column);
    }

    default <T extends SQLTableOwner & DSLSQLFunctionAvailable> void ge(boolean condition, T column) {
        if (condition) {
            getEntitySQLContext().accept(new SQLPredicateImpl(f -> {
                SQLFunc fx = f.getRuntimeContext().fx();
                f.ge(this.getTable(), this.getValue(), column.getTable(), column.func().apply(fx));
            }));
        }
    }

    default <T extends SQLTableOwner & DSLSQLFunctionAvailable> void gt(T column) {
        gt(true, column);
    }

    default <T extends SQLTableOwner & DSLSQLFunctionAvailable> void gt(boolean condition, T column) {
        if (condition) {
            getEntitySQLContext().accept(new SQLPredicateImpl(f -> {
                SQLFunc fx = f.getRuntimeContext().fx();
                f.gt(this.getTable(), this.getValue(), column.getTable(), column.func().apply(fx));
            }));
        }
    }


    default <T extends SQLTableOwner & DSLSQLFunctionAvailable> void eq(T column) {
        eq(true, column);
    }

    default <T extends SQLTableOwner & DSLSQLFunctionAvailable> void eq(boolean condition, T column) {
        if (condition) {
            getEntitySQLContext().accept(new SQLPredicateImpl(f -> {
                SQLFunc fx = f.getRuntimeContext().fx();
                f.eq(this.getTable(), this.getValue(), column.getTable(), column.func().apply(fx));
            }));
        }
    }


    default <T extends SQLTableOwner & DSLSQLFunctionAvailable> void ne(T column) {
        ne(true, column);
    }

    default <T extends SQLTableOwner & DSLSQLFunctionAvailable> void ne(boolean condition, T column) {
        if (condition) {
            getEntitySQLContext().accept(new SQLPredicateImpl(f -> {
                SQLFunc fx = f.getRuntimeContext().fx();
                f.ne(this.getTable(), this.getValue(), column.getTable(), column.func().apply(fx));
            }));
        }
    }

    default <T extends SQLTableOwner & DSLSQLFunctionAvailable> void le(T column) {
        le(true, column);
    }

    default <T extends SQLTableOwner & DSLSQLFunctionAvailable> void le(boolean condition, T column) {
        if (condition) {
            getEntitySQLContext().accept(new SQLPredicateImpl(f -> {
                SQLFunc fx = f.getRuntimeContext().fx();
                f.le(this.getTable(), this.getValue(), column.getTable(), column.func().apply(fx));
            }));
        }
    }

    default <T extends SQLTableOwner & DSLSQLFunctionAvailable> void lt(T column) {
        lt(true, column);
    }

    default <T extends SQLTableOwner & DSLSQLFunctionAvailable> void lt(boolean condition, T column) {
        if (condition) {
            getEntitySQLContext().accept(new SQLPredicateImpl(f -> {
                SQLFunc fx = f.getRuntimeContext().fx();
                f.lt(this.getTable(), this.getValue(), column.getTable(), column.func().apply(fx));
            }));
        }
    }

}
