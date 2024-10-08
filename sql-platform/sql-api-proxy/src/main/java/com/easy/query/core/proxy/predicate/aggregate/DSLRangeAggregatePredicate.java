package com.easy.query.core.proxy.predicate.aggregate;

import com.easy.query.core.enums.SQLPredicateCompareEnum;
import com.easy.query.core.enums.SQLRangeEnum;
import com.easy.query.core.expression.builder.AggregateFilter;
import com.easy.query.core.expression.builder.Filter;
import com.easy.query.core.expression.builder.core.ValueFilter;
import com.easy.query.core.func.SQLFunc;
import com.easy.query.core.func.SQLFunction;
import com.easy.query.core.proxy.PropTypeColumn;
import com.easy.query.core.proxy.SQLColumn;
import com.easy.query.core.proxy.core.EntitySQLContext;
import com.easy.query.core.proxy.impl.SQLAggregatePredicateImpl;
import com.easy.query.core.proxy.predicate.DSLRangePredicate;
import com.easy.query.core.util.EasyClassUtil;

/**
 * create time 2023/12/15 17:17
 * 文件说明
 *
 * @author xuejiaming
 */
public interface DSLRangeAggregatePredicate<TProperty> extends DSLRangePredicate<TProperty>, DSLSQLFunctionAvailable {

    @Override
    default void rangeOpenClosed(boolean conditionLeft, TProperty valLeft, boolean conditionRight, TProperty valRight) {
        rangeFilter(getEntitySQLContext(),this,conditionLeft,_toFunctionSerializeValue(valLeft),conditionRight,_toFunctionSerializeValue(valRight),SQLRangeEnum.OPEN_CLOSED);
    }

    @Override
    default void rangeOpen(boolean conditionLeft, TProperty valLeft, boolean conditionRight, TProperty valRight) {
        rangeFilter(getEntitySQLContext(),this,conditionLeft,_toFunctionSerializeValue(valLeft),conditionRight,_toFunctionSerializeValue(valRight),SQLRangeEnum.OPEN);
    }

    @Override
    default void rangeClosedOpen(boolean conditionLeft, TProperty valLeft, boolean conditionRight, TProperty valRight) {
        rangeFilter(getEntitySQLContext(),this,conditionLeft,_toFunctionSerializeValue(valLeft),conditionRight,_toFunctionSerializeValue(valRight),SQLRangeEnum.CLOSED_OPEN);
    }

    @Override
    default void rangeClosed(boolean conditionLeft, TProperty valLeft, boolean conditionRight, TProperty valRight) {

        rangeFilter(getEntitySQLContext(),this,conditionLeft,_toFunctionSerializeValue(valLeft),conditionRight,_toFunctionSerializeValue(valRight),SQLRangeEnum.CLOSED);
    }


    static <TProp> void rangeFilter(EntitySQLContext entitySQLContext, DSLSQLFunctionAvailable dslSQLFunction, boolean conditionLeft, TProp valLeft, boolean conditionRight, TProp valRight, SQLRangeEnum sqlRange) {
        ValueFilter valueFilter = entitySQLContext.getEntityExpressionBuilder().getExpressionContext().getValueFilter();
        rangeFilter0(entitySQLContext,dslSQLFunction,conditionLeft && valueFilter.accept(dslSQLFunction.getTable(), null, valLeft),valLeft,conditionRight && valueFilter.accept(dslSQLFunction.getTable(), null, valRight),valRight,sqlRange);
    }

    static <TProp> void rangeFilter0(EntitySQLContext entitySQLContext, DSLSQLFunctionAvailable dslSQLFunction, boolean conditionLeft, TProp valLeft, boolean conditionRight, TProp valRight, SQLRangeEnum sqlRange) {
        if (conditionLeft && conditionRight) {
            entitySQLContext._whereAnd(()->{
                entitySQLContext.accept(new SQLAggregatePredicateImpl(filter -> {
                    boolean openFirst = SQLRangeEnum.openFirst(sqlRange);
                    rangeCompareFilter(filter, dslSQLFunction, openFirst ? SQLPredicateCompareEnum.GT : SQLPredicateCompareEnum.GE, valLeft);
                    boolean openEnd = SQLRangeEnum.openEnd(sqlRange);
                    rangeCompareFilter(filter, dslSQLFunction, openEnd ? SQLPredicateCompareEnum.LT : SQLPredicateCompareEnum.LE, valRight);
                }, aggregateFilter -> {
                    boolean openFirst = SQLRangeEnum.openFirst(sqlRange);
                    rangeCompareAggregateFilter(aggregateFilter, dslSQLFunction, openFirst ? SQLPredicateCompareEnum.GT : SQLPredicateCompareEnum.GE, valLeft);
                    boolean openEnd = SQLRangeEnum.openEnd(sqlRange);
                    rangeCompareAggregateFilter(aggregateFilter, dslSQLFunction, openEnd ? SQLPredicateCompareEnum.LT : SQLPredicateCompareEnum.LE, valRight);
                }));
            });
        } else {
            if (conditionLeft) {
                entitySQLContext.accept(new SQLAggregatePredicateImpl(filter -> {
                    boolean openFirst = SQLRangeEnum.openFirst(sqlRange);
                    rangeCompareFilter(filter, dslSQLFunction, openFirst ? SQLPredicateCompareEnum.GT : SQLPredicateCompareEnum.GE, valLeft);
                }, aggregateFilter -> {
                    boolean openFirst = SQLRangeEnum.openFirst(sqlRange);
                    rangeCompareAggregateFilter(aggregateFilter, dslSQLFunction, openFirst ? SQLPredicateCompareEnum.GT : SQLPredicateCompareEnum.GE, valLeft);
                }));
            }
            if (conditionRight) {
                entitySQLContext.accept(new SQLAggregatePredicateImpl(filter -> {
                    boolean openEnd = SQLRangeEnum.openEnd(sqlRange);
                    rangeCompareFilter(filter, dslSQLFunction, openEnd ? SQLPredicateCompareEnum.LT : SQLPredicateCompareEnum.LE, valRight);
                }, aggregateFilter -> {
                    boolean openEnd = SQLRangeEnum.openEnd(sqlRange);
                    rangeCompareAggregateFilter(aggregateFilter, dslSQLFunction, openEnd ? SQLPredicateCompareEnum.LT : SQLPredicateCompareEnum.LE, valRight);
                }));
            }
        }
    }

    static <TProp> void rangeCompareFilter(Filter filter, DSLSQLFunctionAvailable dslSQLFunction, SQLPredicateCompareEnum sqlPredicateCompare, TProp val) {
        SQLFunc fx = filter.getRuntimeContext().fx();
        SQLFunction sqlFunction = dslSQLFunction.func().apply(fx);
        filter.funcValueFilter(dslSQLFunction.getTable(), sqlFunction, val, sqlPredicateCompare);
    }

    static <TProp> void rangeCompareAggregateFilter(AggregateFilter aggregateFilter, DSLSQLFunctionAvailable dslSQLFunction, SQLPredicateCompareEnum sqlPredicateCompare, TProp val) {
        SQLFunc fx = aggregateFilter.getRuntimeContext().fx();
        SQLFunction sqlFunction = dslSQLFunction.func().apply(fx);
        aggregateFilter.func(dslSQLFunction.getTable(), sqlFunction, sqlPredicateCompare, val);
    }
}
