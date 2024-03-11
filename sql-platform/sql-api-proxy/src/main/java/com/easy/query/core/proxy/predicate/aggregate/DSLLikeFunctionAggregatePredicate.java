package com.easy.query.core.proxy.predicate.aggregate;

import com.easy.query.core.enums.SQLLikeEnum;
import com.easy.query.core.expression.parser.core.SQLTableOwner;
import com.easy.query.core.func.SQLFunc;
import com.easy.query.core.proxy.impl.SQLAggregatePredicateImpl;
import com.easy.query.core.proxy.predicate.DSLLikeFunctionPredicate;

/**
 * create time 2023/12/14 22:53
 * 文件说明
 *
 * @author xuejiaming
 */
public interface DSLLikeFunctionAggregatePredicate<TProperty> extends DSLLikeFunctionPredicate<TProperty>,  DSLSQLFunctionAvailable {
    @Override
    default <T1 extends SQLTableOwner & DSLSQLFunctionAvailable> void likeMatchLeft(boolean condition, T1 column) {
        if (condition) {

            getEntitySQLContext().accept(new SQLAggregatePredicateImpl(f -> {
                SQLFunc fx = f.getRuntimeContext().fx();
                f.like(this.getTable(), func().apply(fx), column.getTable(), column.func().apply(fx),true, SQLLikeEnum.LIKE_PERCENT_RIGHT);
            }, f -> {
                SQLFunc fx = f.getRuntimeContext().fx();
                f.like(this.getTable(), func().apply(fx),  column.getTable(), column.func().apply(fx),true, SQLLikeEnum.LIKE_PERCENT_RIGHT);
            }));
        }
    }

    @Override
    default <T1 extends SQLTableOwner & DSLSQLFunctionAvailable> void likeMatchRight(boolean condition, T1 column) {
        if (condition) {
            getEntitySQLContext().accept(new SQLAggregatePredicateImpl(f -> {
                SQLFunc fx = f.getRuntimeContext().fx();
                f.like(this.getTable(), func().apply(fx), column.getTable(), column.func().apply(fx),true,SQLLikeEnum.LIKE_PERCENT_LEFT);
            }, f -> {
                SQLFunc fx = f.getRuntimeContext().fx();
                f.like(this.getTable(), func().apply(fx),column.getTable(), column.func().apply(fx),true,SQLLikeEnum.LIKE_PERCENT_LEFT);
            }));
        }
    }

    @Override
    default <T1 extends SQLTableOwner & DSLSQLFunctionAvailable> void like(boolean condition, T1 column) {
        if (condition) {
            getEntitySQLContext().accept(new SQLAggregatePredicateImpl(f -> {
                SQLFunc fx = f.getRuntimeContext().fx();
                f.like(this.getTable(), func().apply(fx), column.getTable(), column.func().apply(fx),true,SQLLikeEnum.LIKE_PERCENT_ALL);
            }, f -> {
                SQLFunc fx = f.getRuntimeContext().fx();
                f.like(this.getTable(), func().apply(fx), column.getTable(), column.func().apply(fx),true,SQLLikeEnum.LIKE_PERCENT_ALL);
            }));
        }
    }

    @Override
    default <T1 extends SQLTableOwner & DSLSQLFunctionAvailable> void notLikeMatchLeft(boolean condition, T1 column) {
        if (condition) {

            getEntitySQLContext().accept(new SQLAggregatePredicateImpl(f -> {
                SQLFunc fx = f.getRuntimeContext().fx();
                f.like(this.getTable(), func().apply(fx), column.getTable(), column.func().apply(fx),false, SQLLikeEnum.LIKE_PERCENT_RIGHT);
            }, f -> {
                SQLFunc fx = f.getRuntimeContext().fx();
                f.like(this.getTable(), func().apply(fx),  column.getTable(), column.func().apply(fx),false, SQLLikeEnum.LIKE_PERCENT_RIGHT);
            }));
        }
    }

    @Override
    default <T1 extends SQLTableOwner & DSLSQLFunctionAvailable> void notLikeMatchRight(boolean condition, T1 column) {
        if (condition) {
            getEntitySQLContext().accept(new SQLAggregatePredicateImpl(f -> {
                SQLFunc fx = f.getRuntimeContext().fx();
                f.like(this.getTable(), func().apply(fx), column.getTable(), column.func().apply(fx),false,SQLLikeEnum.LIKE_PERCENT_LEFT);
            }, f -> {
                SQLFunc fx = f.getRuntimeContext().fx();
                f.like(this.getTable(), func().apply(fx),column.getTable(), column.func().apply(fx),false,SQLLikeEnum.LIKE_PERCENT_LEFT);
            }));
        }
    }

    @Override
    default <T1 extends SQLTableOwner & DSLSQLFunctionAvailable> void notLike(boolean condition, T1 column) {
        if (condition) {
            getEntitySQLContext().accept(new SQLAggregatePredicateImpl(f -> {
                SQLFunc fx = f.getRuntimeContext().fx();
                f.like(this.getTable(), func().apply(fx), column.getTable(), column.func().apply(fx),false,SQLLikeEnum.LIKE_PERCENT_ALL);
            }, f -> {
                SQLFunc fx = f.getRuntimeContext().fx();
                f.like(this.getTable(), func().apply(fx), column.getTable(), column.func().apply(fx),false,SQLLikeEnum.LIKE_PERCENT_ALL);
            }));
        }
    }
}
