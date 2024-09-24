package com.easy.query.core.proxy.predicate.aggregate;

import com.easy.query.core.enums.SQLLikeEnum;
import com.easy.query.core.func.SQLFunc;
import com.easy.query.core.proxy.SQLColumn;
import com.easy.query.core.proxy.impl.SQLAggregatePredicateImpl;
import com.easy.query.core.proxy.predicate.DSLLikeColumnPredicate;

/**
 * create time 2023/12/14 22:52
 * 文件说明
 *
 * @author xuejiaming
 */
public interface DSLLikeColumnAggregatePredicate<TProperty> extends DSLLikeColumnPredicate<TProperty>,DSLSQLFunctionAvailable {
    @Override
    default <TProxy> void likeMatchLeft(boolean condition, SQLColumn<TProxy, String> column) {
        if (condition) {
            getEntitySQLContext().accept(new SQLAggregatePredicateImpl(f -> {
                SQLFunc fx = f.getRuntimeContext().fx();
                f.like(this.getTable(), func().apply(fx), column.getTable(),column.getValue(),true, SQLLikeEnum.LIKE_PERCENT_RIGHT);
            }, f -> {
                SQLFunc fx = f.getRuntimeContext().fx();
                f.like(this.getTable(), func().apply(fx),column.getTable(),column.getValue(),true, SQLLikeEnum.LIKE_PERCENT_RIGHT);
            }));
        }
    }

    @Override
    default <TProxy> void likeMatchRight(boolean condition, SQLColumn<TProxy, String> column) {
        if (condition) {
            getEntitySQLContext().accept(new SQLAggregatePredicateImpl(f -> {
                SQLFunc fx = f.getRuntimeContext().fx();
                f.like(this.getTable(), func().apply(fx), column.getTable(),column.getValue(),true, SQLLikeEnum.LIKE_PERCENT_LEFT);
            }, f -> {
                SQLFunc fx = f.getRuntimeContext().fx();
                f.like(this.getTable(), func().apply(fx),column.getTable(),column.getValue(),true, SQLLikeEnum.LIKE_PERCENT_LEFT);
            }));
        }
    }

    @Override
    default <TProxy> void like(boolean condition, SQLColumn<TProxy, String> column) {
        if (condition) {
            getEntitySQLContext().accept(new SQLAggregatePredicateImpl(f -> {
                SQLFunc fx = f.getRuntimeContext().fx();
                f.like(this.getTable(), func().apply(fx), column.getTable(),column.getValue(),true, SQLLikeEnum.LIKE_PERCENT_ALL);
            }, f -> {
                SQLFunc fx = f.getRuntimeContext().fx();
                f.like(this.getTable(), func().apply(fx),column.getTable(),column.getValue(),true, SQLLikeEnum.LIKE_PERCENT_ALL);
            }));
        }
    }

    @Override
    default <TProxy> void notLikeMatchLeft(boolean condition, SQLColumn<TProxy, String> column) {
        if (condition) {
            getEntitySQLContext().accept(new SQLAggregatePredicateImpl(f -> {
                SQLFunc fx = f.getRuntimeContext().fx();
                f.like(this.getTable(), func().apply(fx), column.getTable(),column.getValue(),false, SQLLikeEnum.LIKE_PERCENT_RIGHT);
            }, f -> {
                SQLFunc fx = f.getRuntimeContext().fx();
                f.like(this.getTable(), func().apply(fx),column.getTable(),column.getValue(),false, SQLLikeEnum.LIKE_PERCENT_RIGHT);
            }));
        }
    }

    @Override
    default <TProxy> void notLikeMatchRight(boolean condition, SQLColumn<TProxy, String> column) {
        if (condition) {
            getEntitySQLContext().accept(new SQLAggregatePredicateImpl(f -> {
                SQLFunc fx = f.getRuntimeContext().fx();
                f.like(this.getTable(), func().apply(fx), column.getTable(),column.getValue(),false, SQLLikeEnum.LIKE_PERCENT_LEFT);
            }, f -> {
                SQLFunc fx = f.getRuntimeContext().fx();
                f.like(this.getTable(), func().apply(fx),column.getTable(),column.getValue(),false, SQLLikeEnum.LIKE_PERCENT_LEFT);
            }));
        }
    }

    @Override
    default <TProxy> void notLike(boolean condition, SQLColumn<TProxy, String> column) {
        if (condition) {
            getEntitySQLContext().accept(new SQLAggregatePredicateImpl(f -> {
                SQLFunc fx = f.getRuntimeContext().fx();
                f.like(this.getTable(), func().apply(fx), column.getTable(),column.getValue(),false, SQLLikeEnum.LIKE_PERCENT_ALL);
            }, f -> {
                SQLFunc fx = f.getRuntimeContext().fx();
                f.like(this.getTable(), func().apply(fx),column.getTable(),column.getValue(),false, SQLLikeEnum.LIKE_PERCENT_ALL);
            }));
        }
    }
}
