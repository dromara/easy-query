package com.easy.query.core.proxy.predicate;

import com.easy.query.core.expression.parser.core.SQLTableOwner;
import com.easy.query.core.func.SQLFunc;
import com.easy.query.core.proxy.SQLPredicateExpression;
import com.easy.query.core.proxy.TablePropColumn;
import com.easy.query.core.proxy.impl.SQLPredicateImpl;
import com.easy.query.core.proxy.predicate.aggregate.DSLSQLFunctionAvailable;

/**
 * create time 2023/12/2 14:18
 * 文件说明
 *
 * @author xuejiaming
 */
public interface DSLFunctionPredicate<TProperty> extends TablePropColumn {
    default <T extends SQLTableOwner&DSLSQLFunctionAvailable> SQLPredicateExpression ge(T column) {
        return ge(true, column);
    }

    default <T extends SQLTableOwner&DSLSQLFunctionAvailable> SQLPredicateExpression  ge(boolean condition, T column) {
        if (condition) {
            return new SQLPredicateImpl(f -> {
                SQLFunc fx = f.getRuntimeContext().fx();
                f.ge(this.getTable(), this.getValue(),column.getTable(),column.func().apply(fx));
            });
        }
        return SQLPredicateExpression.empty;
    }
    default <T extends SQLTableOwner&DSLSQLFunctionAvailable> SQLPredicateExpression  gt(T column) {
        return gt(true, column);
    }

    default <T extends SQLTableOwner&DSLSQLFunctionAvailable> SQLPredicateExpression  gt(boolean condition, T column) {
        if (condition) {
            return new SQLPredicateImpl(f -> {
                SQLFunc fx = f.getRuntimeContext().fx();
                f.gt(this.getTable(), this.getValue(), column.getTable(), column.func().apply(fx));
            });
        }
        return SQLPredicateExpression.empty;
    }


    default <T extends SQLTableOwner&DSLSQLFunctionAvailable> SQLPredicateExpression  eq(T column) {
        return eq(true, column);
    }

    default <T extends SQLTableOwner&DSLSQLFunctionAvailable> SQLPredicateExpression  eq(boolean condition, T column) {
        if (condition) {
            return new SQLPredicateImpl(f -> {
                SQLFunc fx = f.getRuntimeContext().fx();
                f.eq(this.getTable(), this.getValue(), column.getTable(), column.func().apply(fx));
            });
        }
        return SQLPredicateExpression.empty;
    }



    default <T extends SQLTableOwner&DSLSQLFunctionAvailable> SQLPredicateExpression  ne(T column) {
        return ne(true, column);
    }

    default <T extends SQLTableOwner&DSLSQLFunctionAvailable> SQLPredicateExpression  ne(boolean condition, T column) {
        if (condition) {
            return new SQLPredicateImpl(f -> {
                SQLFunc fx = f.getRuntimeContext().fx();
                f.ne(this.getTable(), this.getValue(), column.getTable(), column.func().apply(fx));
            });
        }
        return SQLPredicateExpression.empty;
    }

    default <T extends SQLTableOwner&DSLSQLFunctionAvailable> SQLPredicateExpression  le(T column) {
        return le(true, column);
    }

    default <T extends SQLTableOwner&DSLSQLFunctionAvailable> SQLPredicateExpression  le(boolean condition, T column) {
        if (condition) {
            return new SQLPredicateImpl(f -> {
                SQLFunc fx = f.getRuntimeContext().fx();
                f.le(this.getTable(), this.getValue(), column.getTable(), column.func().apply(fx));
            });
        }
        return SQLPredicateExpression.empty;
    }

    default <T extends SQLTableOwner&DSLSQLFunctionAvailable> SQLPredicateExpression  lt(T column) {
        return lt(true, column);
    }

    default <T extends SQLTableOwner&DSLSQLFunctionAvailable> SQLPredicateExpression  lt(boolean condition, T column) {
        if (condition) {
            return new SQLPredicateImpl(f -> {
                SQLFunc fx = f.getRuntimeContext().fx();
                f.lt(this.getTable(), this.getValue(), column.getTable(), column.func().apply(fx));
            });
        }
        return SQLPredicateExpression.empty;
    }

}
