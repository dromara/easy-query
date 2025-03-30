package com.easy.query.core.proxy.predicate.aggregate;

import com.easy.query.core.enums.SQLPredicateCompareEnum;
import com.easy.query.core.func.SQLFunc;
import com.easy.query.core.proxy.SQLColumn;
import com.easy.query.core.proxy.impl.SQLAggregatePredicateImpl;
import com.easy.query.core.proxy.predicate.DSLColumnComparePredicate;

/**
 * create time 2023/12/14 22:52
 * 文件说明
 *
 * @author xuejiaming
 */
public interface DSLColumnCompareAggregatePredicate<TProperty> extends DSLColumnComparePredicate<TProperty>,DSLSQLFunctionAvailable {
    @Override
    default <TProxy, TProp> void ge(boolean condition, SQLColumn<TProxy, TProp> column) {
        if (condition) {
            getCurrentEntitySQLContext().accept(new SQLAggregatePredicateImpl(f -> {
                SQLFunc fx = f.getRuntimeContext().fx();
                f.ge(this.getTable(), func().apply(fx), column.getTable(),column.getValue());
            }, f -> {
                SQLFunc fx = f.getRuntimeContext().fx();
                f.func(this.getTable(), func().apply(fx), SQLPredicateCompareEnum.GE,column.getTable(),column.getValue());
            }));
        }
    }

    @Override
    default <TProxy, TProp> void gt(boolean condition, SQLColumn<TProxy, TProp> column) {
        if (condition) {
            getCurrentEntitySQLContext().accept(new SQLAggregatePredicateImpl(f -> {
                SQLFunc fx = f.getRuntimeContext().fx();
                f.gt(this.getTable(), func().apply(fx), column.getTable(),column.getValue());
            }, f -> {
                SQLFunc fx = f.getRuntimeContext().fx();
                f.func(this.getTable(), func().apply(fx), SQLPredicateCompareEnum.GT,column.getTable(),column.getValue());
            }));
        }
    }

    @Override
    default <TProxy, TProp> void eq(boolean condition, SQLColumn<TProxy, TProp> column) {
        if (condition) {
            getCurrentEntitySQLContext().accept(new SQLAggregatePredicateImpl(f -> {
                SQLFunc fx = f.getRuntimeContext().fx();
                f.eq(this.getTable(), func().apply(fx), column.getTable(),column.getValue());
            }, f -> {
                SQLFunc fx = f.getRuntimeContext().fx();
                f.func(this.getTable(), func().apply(fx), SQLPredicateCompareEnum.EQ,column.getTable(),column.getValue());
            }));
        }
    }

    @Override
    default <TProxy, TProp> void ne(boolean condition, SQLColumn<TProxy, TProp> column) {
        if (condition) {
            getCurrentEntitySQLContext().accept(new SQLAggregatePredicateImpl(f -> {
                SQLFunc fx = f.getRuntimeContext().fx();
                f.ne(this.getTable(), func().apply(fx), column.getTable(),column.getValue());
            }, f -> {
                SQLFunc fx = f.getRuntimeContext().fx();
                f.func(this.getTable(), func().apply(fx), SQLPredicateCompareEnum.NE,column.getTable(),column.getValue());
            }));
        }
    }

    @Override
    default <TProxy, TProp> void le(boolean condition, SQLColumn<TProxy, TProp> column) {
        if (condition) {
            getCurrentEntitySQLContext().accept(new SQLAggregatePredicateImpl(f -> {
                SQLFunc fx = f.getRuntimeContext().fx();
                f.le(this.getTable(), func().apply(fx), column.getTable(),column.getValue());
            }, f -> {
                SQLFunc fx = f.getRuntimeContext().fx();
                f.func(this.getTable(), func().apply(fx), SQLPredicateCompareEnum.LE,column.getTable(),column.getValue());
            }));
        }
    }

    @Override
    default <TProxy, TProp> void lt(boolean condition, SQLColumn<TProxy, TProp> column) {
        if (condition) {
            getCurrentEntitySQLContext().accept(new SQLAggregatePredicateImpl(f -> {
                SQLFunc fx = f.getRuntimeContext().fx();
                f.lt(this.getTable(), func().apply(fx), column.getTable(),column.getValue());
            }, f -> {
                SQLFunc fx = f.getRuntimeContext().fx();
                f.func(this.getTable(), func().apply(fx), SQLPredicateCompareEnum.LT,column.getTable(),column.getValue());
            }));
        }
    }
}
