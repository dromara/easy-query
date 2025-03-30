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
public interface DSLLikeFunctionPredicate<TProperty> extends TablePropColumn, EntitySQLContextAvailable {
    /**
     * column like 'value%'
     * @param column
     * @param <T>
     */
    default <T extends SQLTableOwner&DSLSQLFunctionAvailable> void likeMatchLeft(T column) {
        likeMatchLeft(true, column);
    }

    /**
     * column like 'value%'
     * @param condition
     * @param column
     * @param <T>
     */
    default <T extends SQLTableOwner&DSLSQLFunctionAvailable> void   likeMatchLeft(boolean condition, T column) {
        if (condition) {
            getCurrentEntitySQLContext().accept(new SQLPredicateImpl(f -> {
                SQLFunc fx = f.getRuntimeContext().fx();
                f.like(this.getTable(), this.getValue(),column.getTable(),column.func().apply(fx),true, SQLLikeEnum.LIKE_PERCENT_RIGHT);
            }));
        }
    }

    /**
     * column like '%value'
     * @param column
     * @param <T>
     */
    default <T extends SQLTableOwner&DSLSQLFunctionAvailable> void  likeMatchRight(T column) {
        likeMatchRight(true, column);
    }

    /**
     * column like '%value'
     * @param condition
     * @param column
     * @param <T>
     */
    default <T extends SQLTableOwner&DSLSQLFunctionAvailable> void  likeMatchRight(boolean condition, T column) {
        if (condition) {
            getCurrentEntitySQLContext().accept(new SQLPredicateImpl(f -> {
                SQLFunc fx = f.getRuntimeContext().fx();
                f.like(this.getTable(), this.getValue(), column.getTable(), column.func().apply(fx),true,SQLLikeEnum.LIKE_PERCENT_LEFT);
            }));
        }
    }


    default <T extends SQLTableOwner&DSLSQLFunctionAvailable> void  like(T column) {
        like(true, column);
    }

    default <T extends SQLTableOwner&DSLSQLFunctionAvailable> void  like(boolean condition, T column) {
        if (condition) {
            getCurrentEntitySQLContext().accept(new SQLPredicateImpl(f -> {
                SQLFunc fx = f.getRuntimeContext().fx();
                f.like(this.getTable(), this.getValue(), column.getTable(), column.func().apply(fx),true,SQLLikeEnum.LIKE_PERCENT_ALL);
            }));
        }
    }


    default <T extends SQLTableOwner&DSLSQLFunctionAvailable> void notLikeMatchLeft(T column) {
        notLikeMatchLeft(true, column);
    }

    default <T extends SQLTableOwner&DSLSQLFunctionAvailable> void   notLikeMatchLeft(boolean condition, T column) {
        if (condition) {
            getCurrentEntitySQLContext().accept(new SQLPredicateImpl(f -> {
                SQLFunc fx = f.getRuntimeContext().fx();
                f.like(this.getTable(), this.getValue(),column.getTable(),column.func().apply(fx),false, SQLLikeEnum.LIKE_PERCENT_RIGHT);
            }));
        }
    }
    default <T extends SQLTableOwner&DSLSQLFunctionAvailable> void   notLikeMatchRight(T column) {
        notLikeMatchRight(true, column);
    }

    default <T extends SQLTableOwner&DSLSQLFunctionAvailable> void  notLikeMatchRight(boolean condition, T column) {
        if (condition) {
            getCurrentEntitySQLContext().accept(new SQLPredicateImpl(f -> {
                SQLFunc fx = f.getRuntimeContext().fx();
                f.like(this.getTable(), this.getValue(), column.getTable(), column.func().apply(fx),false,SQLLikeEnum.LIKE_PERCENT_LEFT);
            }));
        }
    }


    default <T extends SQLTableOwner&DSLSQLFunctionAvailable> void  notLike(T column) {
        notLike(true, column);
    }

    default <T extends SQLTableOwner&DSLSQLFunctionAvailable> void  notLike(boolean condition, T column) {
        if (condition) {
            getCurrentEntitySQLContext().accept(new SQLPredicateImpl(f -> {
                SQLFunc fx = f.getRuntimeContext().fx();
                f.like(this.getTable(), this.getValue(), column.getTable(), column.func().apply(fx),false,SQLLikeEnum.LIKE_PERCENT_ALL);
            }));
        }
    }

}
