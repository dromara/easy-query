package com.easy.query.core.proxy.predicate.aggregate;

import com.easy.query.core.basic.api.select.Query;
import com.easy.query.core.enums.SQLPredicateCompareEnum;
import com.easy.query.core.func.SQLFunc;
import com.easy.query.core.proxy.impl.SQLAggregatePredicateImpl;
import com.easy.query.core.proxy.predicate.DSLSubQueryPredicate;

/**
 * create time 2023/12/14 23:14
 * 文件说明
 *
 * @author xuejiaming
 */
public interface DSLSubQueryAggregatePredicate<TProperty> extends DSLSubQueryPredicate<TProperty>,DSLSQLFunctionAvailable {

    @Override
    default void ge(boolean condition, Query<TProperty> subQuery) {
        if (condition) {
            getCurrentEntitySQLContext().accept(new SQLAggregatePredicateImpl(f -> {
                SQLFunc fx = f.getRuntimeContext().fx();
                f.ge(this.getTable(), func().apply(fx), subQuery);
            }, f -> {
                SQLFunc fx = f.getRuntimeContext().fx();
                f.func(this.getTable(), func().apply(fx), SQLPredicateCompareEnum.GE,subQuery);
            }));
        }
    }

    @Override
    default void gt(boolean condition, Query<TProperty> subQuery) {
        if (condition) {
            getCurrentEntitySQLContext().accept(new SQLAggregatePredicateImpl(f -> {
                SQLFunc fx = f.getRuntimeContext().fx();
                f.gt(this.getTable(), func().apply(fx), subQuery);
            }, f -> {
                SQLFunc fx = f.getRuntimeContext().fx();
                f.func(this.getTable(), func().apply(fx), SQLPredicateCompareEnum.GT,subQuery);
            }));
        }
    }

    @Override
    default void eq(boolean condition, Query<TProperty> subQuery) {
        if (condition) {
            getCurrentEntitySQLContext().accept(new SQLAggregatePredicateImpl(f -> {
                SQLFunc fx = f.getRuntimeContext().fx();
                f.eq(this.getTable(), func().apply(fx), subQuery);
            }, f -> {
                SQLFunc fx = f.getRuntimeContext().fx();
                f.func(this.getTable(), func().apply(fx), SQLPredicateCompareEnum.EQ,subQuery);
            }));
        }
    }

    @Override
    default void ne(boolean condition, Query<TProperty> subQuery) {
        if (condition) {
            getCurrentEntitySQLContext().accept(new SQLAggregatePredicateImpl(f -> {
                SQLFunc fx = f.getRuntimeContext().fx();
                f.ne(this.getTable(), func().apply(fx), subQuery);
            }, f -> {
                SQLFunc fx = f.getRuntimeContext().fx();
                f.func(this.getTable(), func().apply(fx), SQLPredicateCompareEnum.NE,subQuery);
            }));
        }
    }

    @Override
    default void le(boolean condition, Query<TProperty> subQuery) {
        if (condition) {
            getCurrentEntitySQLContext().accept(new SQLAggregatePredicateImpl(f -> {
                SQLFunc fx = f.getRuntimeContext().fx();
                f.le(this.getTable(), func().apply(fx), subQuery);
            }, f -> {
                SQLFunc fx = f.getRuntimeContext().fx();
                f.func(this.getTable(), func().apply(fx), SQLPredicateCompareEnum.LE,subQuery);
            }));
        }
    }

    @Override
    default void lt(boolean condition, Query<TProperty> subQuery) {
        if (condition) {
            getCurrentEntitySQLContext().accept(new SQLAggregatePredicateImpl(f -> {
                SQLFunc fx = f.getRuntimeContext().fx();
                f.lt(this.getTable(), func().apply(fx), subQuery);
            }, f -> {
                SQLFunc fx = f.getRuntimeContext().fx();
                f.func(this.getTable(), func().apply(fx), SQLPredicateCompareEnum.LT,subQuery);
            }));
        }
    }

    @Override
    default void in(boolean condition, Query<TProperty> subQuery) {
        if (condition) {
            getCurrentEntitySQLContext().accept(new SQLAggregatePredicateImpl(f -> {
                SQLFunc fx = f.getRuntimeContext().fx();
                f.funcSubQueryFilter(this.getTable(), func().apply(fx), subQuery,SQLPredicateCompareEnum.IN);
            }, f -> {
                SQLFunc fx = f.getRuntimeContext().fx();
                f.func(this.getTable(), func().apply(fx), SQLPredicateCompareEnum.IN,subQuery);
            }));
        }
    }

    @Override
    default void notIn(boolean condition, Query<TProperty> subQuery) {
        if (condition) {
            getCurrentEntitySQLContext().accept(new SQLAggregatePredicateImpl(f -> {
                SQLFunc fx = f.getRuntimeContext().fx();
                f.funcSubQueryFilter(this.getTable(), func().apply(fx), subQuery,SQLPredicateCompareEnum.NOT_IN);
            }, f -> {
                SQLFunc fx = f.getRuntimeContext().fx();
                f.func(this.getTable(), func().apply(fx), SQLPredicateCompareEnum.NOT_IN,subQuery);
            }));
        }
    }
}
