package com.easy.query.core.proxy.extension.functions.executor;

import com.easy.query.core.expression.parser.core.base.scec.core.SQLNativeChainExpressionContextImpl;
import com.easy.query.core.func.SQLFunc;
import com.easy.query.core.func.SQLFunction;
import com.easy.query.core.proxy.extension.functions.ColumnStringFunctionAvailable;
import com.easy.query.core.proxy.impl.SQLAggregateNativeSQLPredicateImpl;
import com.easy.query.core.proxy.predicate.DSLStringAssertPredicate;
import com.easy.query.core.proxy.predicate.aggregate.DSLSQLFunctionAvailable;
import com.easy.query.core.util.EasyObjectUtil;

/**
 * create time 2023/12/21 09:19
 * 文件说明
 *
 * @author xuejiaming
 */
public interface ColumnFunctionComparableStringChainExpression<T> extends ColumnFunctionComparableObjectChainExpression<T>,
        ColumnStringFunctionAvailable<T>,
        DSLStringAssertPredicate<T>,
        DSLSQLFunctionAvailable {

    @Override
    default <TR> ColumnFunctionComparableStringChainExpression<TR> setPropertyType(Class<TR> clazz) {
        ColumnFunctionComparableObjectChainExpression.super.setPropertyType(clazz);
        return EasyObjectUtil.typeCastNullable(this);
    }

    @Override
    default void isBank(boolean condition) {
        if(condition){
            getEntitySQLContext().accept(new SQLAggregateNativeSQLPredicateImpl(f -> {
                SQLFunc fx = f.getRuntimeContext().fx();
                SQLFunction bank = fx.bank(func().apply(fx));
                f.sqlNativeSegment(bank.sqlSegment(getTable()), c -> {
                    bank.consume(new SQLNativeChainExpressionContextImpl(getTable(), c));
                });
            }));
        }
    }

    @Override
    default void isNotBank(boolean condition) {
        if(condition){
            getEntitySQLContext().accept(new SQLAggregateNativeSQLPredicateImpl(f -> {
                SQLFunc fx = f.getRuntimeContext().fx();
                SQLFunction notBank = fx.notBank(func().apply(fx));
                f.sqlNativeSegment(notBank.sqlSegment(getTable()), c -> {
                    notBank.consume(new SQLNativeChainExpressionContextImpl(getTable(), c));
                });
            }));
        }
    }

    @Override
    default void isEmpty(boolean condition) {
        if(condition){
            getEntitySQLContext().accept(new SQLAggregateNativeSQLPredicateImpl(f -> {
                SQLFunc fx = f.getRuntimeContext().fx();
                SQLFunction empty = fx.empty(func().apply(fx));
                f.sqlNativeSegment(empty.sqlSegment(getTable()), c -> {
                    empty.consume(new SQLNativeChainExpressionContextImpl(getTable(), c));
                });
            }));
        }
    }

    @Override
    default void isNotEmpty(boolean condition) {
        if(condition){
            getEntitySQLContext().accept(new SQLAggregateNativeSQLPredicateImpl(f -> {
                SQLFunc fx = f.getRuntimeContext().fx();
                SQLFunction notEmpty = fx.notEmpty(func().apply(fx));
                f.sqlNativeSegment(notEmpty.sqlSegment(getTable()), c -> {
                    notEmpty.consume(new SQLNativeChainExpressionContextImpl(getTable(), c));
                });
            }));
        }
    }
}
