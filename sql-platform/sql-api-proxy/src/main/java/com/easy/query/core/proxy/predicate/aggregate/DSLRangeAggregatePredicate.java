package com.easy.query.core.proxy.predicate.aggregate;

import com.easy.query.core.enums.SQLPredicateCompareEnum;
import com.easy.query.core.func.SQLFunc;
import com.easy.query.core.proxy.impl.SQLAggregatePredicateImpl;
import com.easy.query.core.proxy.predicate.DSLRangePredicate;

/**
 * create time 2023/12/15 17:17
 * 文件说明
 *
 * @author xuejiaming
 */
public interface DSLRangeAggregatePredicate<TProperty> extends DSLRangePredicate<TProperty>,DSLSQLFunctionAvailable {

    @Override
    default void rangeOpenClosed(boolean conditionLeft, TProperty valLeft, boolean conditionRight, TProperty valRight) {
        if (conditionLeft||conditionRight) {
            getEntitySQLContext().accept(new SQLAggregatePredicateImpl(f -> {
                SQLFunc fx = f.getRuntimeContext().fx();
                f.gt(getTable(),func().apply(fx), _toFunctionSerializeValue(valLeft));
                f.le(getTable(),func().apply(fx), _toFunctionSerializeValue(valRight));
            }, f -> {
                SQLFunc fx = f.getRuntimeContext().fx();
                f.func(this.getTable(), func().apply(fx), SQLPredicateCompareEnum.GT, _toFunctionSerializeValue(valLeft));
                f.func(this.getTable(), func().apply(fx), SQLPredicateCompareEnum.LE, _toFunctionSerializeValue(valRight));
            }));
        }
    }

    @Override
    default void rangeOpen(boolean conditionLeft, TProperty valLeft, boolean conditionRight, TProperty valRight) {
        if (conditionLeft||conditionRight) {
            getEntitySQLContext().accept(new SQLAggregatePredicateImpl(f -> {
                SQLFunc fx = f.getRuntimeContext().fx();
                f.gt(getTable(),func().apply(fx), _toFunctionSerializeValue(valLeft));
                f.lt(getTable(),func().apply(fx), _toFunctionSerializeValue(valRight));
            }, f -> {
                SQLFunc fx = f.getRuntimeContext().fx();
                f.func(this.getTable(), func().apply(fx), SQLPredicateCompareEnum.GT, _toFunctionSerializeValue(valLeft));
                f.func(this.getTable(), func().apply(fx), SQLPredicateCompareEnum.LT, _toFunctionSerializeValue(valRight));
            }));
        }
    }

    @Override
    default void rangeClosedOpen(boolean conditionLeft, TProperty valLeft, boolean conditionRight, TProperty valRight) {
        if (conditionLeft||conditionRight) {
            getEntitySQLContext().accept(new SQLAggregatePredicateImpl(f -> {
                SQLFunc fx = f.getRuntimeContext().fx();
                f.ge(getTable(),func().apply(fx), _toFunctionSerializeValue(valLeft));
                f.lt(getTable(),func().apply(fx), _toFunctionSerializeValue(valRight));
            }, f -> {
                SQLFunc fx = f.getRuntimeContext().fx();
                f.func(this.getTable(), func().apply(fx), SQLPredicateCompareEnum.GE, _toFunctionSerializeValue(valLeft));
                f.func(this.getTable(), func().apply(fx), SQLPredicateCompareEnum.LT, _toFunctionSerializeValue(valRight));
            }));
        }
    }

    @Override
    default void rangeClosed(boolean conditionLeft, TProperty valLeft, boolean conditionRight, TProperty valRight) {
        if (conditionLeft||conditionRight) {
            getEntitySQLContext().accept(new SQLAggregatePredicateImpl(f -> {
                SQLFunc fx = f.getRuntimeContext().fx();
                f.ge(getTable(),func().apply(fx), _toFunctionSerializeValue(valLeft));
                f.le(getTable(),func().apply(fx), _toFunctionSerializeValue(valRight));
            }, f -> {
                SQLFunc fx = f.getRuntimeContext().fx();
                f.func(this.getTable(), func().apply(fx), SQLPredicateCompareEnum.GE, _toFunctionSerializeValue(valLeft));
                f.func(this.getTable(), func().apply(fx), SQLPredicateCompareEnum.LE, _toFunctionSerializeValue(valRight));
            }));
        }
    }
}
