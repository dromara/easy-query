package com.easy.query.core.proxy.predicate;

import com.easy.query.core.enums.SQLLikeEnum;
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
public interface DSLContainsFunctionPredicate extends TablePropColumn, EntitySQLContextAvailable {
    /**
     * column like 'value%'
     *
     * @param column
     * @param <T>
     */
    default <T extends SQLTableOwner & DSLSQLFunctionAvailable> void startsWith(T column) {
        startsWith(true, column);
    }

    /**
     * column like 'value%'
     *
     * @param condition
     * @param column
     * @param <T>
     */
    default <T extends SQLTableOwner & DSLSQLFunctionAvailable> void startsWith(boolean condition, T column) {
        if (condition) {
            getCurrentEntitySQLContext().accept(new SQLPredicateImpl(f -> {
                SQLFunc fx = f.getRuntimeContext().fx();
                f.like(this.getTable(), this.getValue(), column.getTable(), column.func().apply(fx), true, SQLLikeEnum.LIKE_PERCENT_RIGHT);
            }));
        }
    }

    /**
     * column like '%value'
     *
     * @param column
     * @param <T>
     */
    default <T extends SQLTableOwner & DSLSQLFunctionAvailable> void endsWith(T column) {
        endsWith(true, column);
    }

    /**
     * column like '%value'
     *
     * @param condition
     * @param column
     * @param <T>
     */
    default <T extends SQLTableOwner & DSLSQLFunctionAvailable> void endsWith(boolean condition, T column) {
        if (condition) {
            getCurrentEntitySQLContext().accept(new SQLPredicateImpl(f -> {
                SQLFunc fx = f.getRuntimeContext().fx();
                f.like(this.getTable(), this.getValue(), column.getTable(), column.func().apply(fx), true, SQLLikeEnum.LIKE_PERCENT_LEFT);
            }));
        }
    }


    default <T extends SQLTableOwner & DSLSQLFunctionAvailable> void contains(T column) {
        contains(true, column);
    }

    default <T extends SQLTableOwner & DSLSQLFunctionAvailable> void contains(boolean condition, T column) {
        if (condition) {
            getCurrentEntitySQLContext().accept(new SQLPredicateImpl(f -> {
                SQLFunc fx = f.getRuntimeContext().fx();
                f.like(this.getTable(), this.getValue(), column.getTable(), column.func().apply(fx), true, SQLLikeEnum.LIKE_PERCENT_ALL);
            }));
        }
    }


    default <T extends SQLTableOwner & DSLSQLFunctionAvailable> void notStartsWith(T column) {
        notStartsWith(true, column);
    }

    default <T extends SQLTableOwner & DSLSQLFunctionAvailable> void notStartsWith(boolean condition, T column) {
        if (condition) {
            getCurrentEntitySQLContext().accept(new SQLPredicateImpl(f -> {
                SQLFunc fx = f.getRuntimeContext().fx();
                f.like(this.getTable(), this.getValue(), column.getTable(), column.func().apply(fx), false, SQLLikeEnum.LIKE_PERCENT_RIGHT);
            }));
        }
    }

    default <T extends SQLTableOwner & DSLSQLFunctionAvailable> void notEndsWith(T column) {
        notEndsWith(true, column);
    }

    default <T extends SQLTableOwner & DSLSQLFunctionAvailable> void notEndsWith(boolean condition, T column) {
        if (condition) {
            getCurrentEntitySQLContext().accept(new SQLPredicateImpl(f -> {
                SQLFunc fx = f.getRuntimeContext().fx();
                f.like(this.getTable(), this.getValue(), column.getTable(), column.func().apply(fx), false, SQLLikeEnum.LIKE_PERCENT_LEFT);
            }));
        }
    }


    default <T extends SQLTableOwner & DSLSQLFunctionAvailable> void notContains(T column) {
        notContains(true, column);
    }

    default <T extends SQLTableOwner & DSLSQLFunctionAvailable> void notContains(boolean condition, T column) {
        if (condition) {
            getCurrentEntitySQLContext().accept(new SQLPredicateImpl(f -> {
                SQLFunc fx = f.getRuntimeContext().fx();
                f.like(this.getTable(), this.getValue(), column.getTable(), column.func().apply(fx), false, SQLLikeEnum.LIKE_PERCENT_ALL);
            }));
        }
    }

}
