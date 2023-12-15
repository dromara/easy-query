package com.easy.query.core.proxy.predicate.aggregate;

import com.easy.query.core.enums.SQLPredicateCompareEnum;
import com.easy.query.core.func.SQLFunc;
import com.easy.query.core.proxy.impl.SQLAggregatePredicateImpl;
import com.easy.query.core.proxy.predicate.DSLValuesPredicate;

import java.util.Collection;

/**
 * create time 2023/12/14 23:36
 * 文件说明
 *
 * @author xuejiaming
 */
public interface DSLValuesAggregatePredicate<TProperty> extends DSLValuesPredicate<TProperty>,DSLSQLFunctionAvailable {

    @Override
    default void in(boolean condition, Collection<? extends TProperty> collections) {
        if (condition) {
            getEntitySQLContext().accept(new SQLAggregatePredicateImpl(f -> {
                SQLFunc fx = f.getRuntimeContext().fx();
                f.funcInFilter(this.getTable(), func().apply(fx), collections, SQLPredicateCompareEnum.IN);
            }, f -> {
                SQLFunc fx = f.getRuntimeContext().fx();
                f.func(this.getTable(), func().apply(fx), SQLPredicateCompareEnum.IN,collections);
            }));
        }
    }

    @Override
    default void in(boolean condition, TProperty[] array) {
        if (condition) {
            getEntitySQLContext().accept(new SQLAggregatePredicateImpl(f -> {
                SQLFunc fx = f.getRuntimeContext().fx();
                f.funcInFilter(this.getTable(), func().apply(fx), array,SQLPredicateCompareEnum.IN);
            }, f -> {
                SQLFunc fx = f.getRuntimeContext().fx();
                f.func(this.getTable(), func().apply(fx), SQLPredicateCompareEnum.IN,array);
            }));
        }
    }

    @Override
    default void notIn(boolean condition, Collection<? extends TProperty> collections) {
        if (condition) {
            getEntitySQLContext().accept(new SQLAggregatePredicateImpl(f -> {
                SQLFunc fx = f.getRuntimeContext().fx();
                f.funcInFilter(this.getTable(), func().apply(fx), collections,SQLPredicateCompareEnum.NOT_IN);
            }, f -> {
                SQLFunc fx = f.getRuntimeContext().fx();
                f.func(this.getTable(), func().apply(fx), SQLPredicateCompareEnum.NOT_IN,collections);
            }));
        }
    }

    @Override
    default void notIn(boolean condition, TProperty[] arrays) {
        if (condition) {
            getEntitySQLContext().accept(new SQLAggregatePredicateImpl(f -> {
                SQLFunc fx = f.getRuntimeContext().fx();
                f.funcInFilter(this.getTable(), func().apply(fx), arrays,SQLPredicateCompareEnum.NOT_IN);
            }, f -> {
                SQLFunc fx = f.getRuntimeContext().fx();
                f.func(this.getTable(), func().apply(fx), SQLPredicateCompareEnum.NOT_IN,arrays);
            }));
        }
    }
}
