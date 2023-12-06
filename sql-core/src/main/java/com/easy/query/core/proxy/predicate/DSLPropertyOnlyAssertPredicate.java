package com.easy.query.core.proxy.predicate;

import com.easy.query.core.expression.parser.core.base.scec.core.SQLNativeChainExpressionContextImpl;
import com.easy.query.core.func.SQLFunc;
import com.easy.query.core.func.SQLFunction;
import com.easy.query.core.proxy.SQLPredicateExpression;
import com.easy.query.core.proxy.TablePropColumn;
import com.easy.query.core.proxy.impl.SQLPredicateImpl;

/**
 * create time 2023/12/6 21:24
 * 文件说明
 *
 * @author xuejiaming
 */
public interface DSLPropertyOnlyAssertPredicate<TProperty> extends TablePropColumn {
    default SQLPredicateExpression isEmpty() {
        return isEmpty(true);
    }

    default SQLPredicateExpression isEmpty(boolean condition){
        if(condition){
            return new SQLPredicateImpl(f -> {
                SQLFunc fx = f.getRuntimeContext().fx();
                SQLFunction bank = fx.empty(getValue());
                f.sqlNativeSegment(bank.sqlSegment(getTable()),c->{
                    bank.consume(new SQLNativeChainExpressionContextImpl(getTable(),c));
                });
            });
        }
        return SQLPredicateExpression.empty;
    }
    default SQLPredicateExpression isNotEmpty() {
        return isNotEmpty(true);
    }

    default SQLPredicateExpression isNotEmpty(boolean condition){
        if(condition){
            return new SQLPredicateImpl(f -> {
                SQLFunc fx = f.getRuntimeContext().fx();
                SQLFunction bank = fx.notEmpty(getValue());
                f.sqlNativeSegment(bank.sqlSegment(getTable()),c->{
                    bank.consume(new SQLNativeChainExpressionContextImpl(getTable(),c));
                });
            });
        }
        return SQLPredicateExpression.empty;
    }
    default SQLPredicateExpression isBank() {
        return isBank(true);
    }

    default SQLPredicateExpression isBank(boolean condition){
        if(condition){
            return new SQLPredicateImpl(f -> {
                SQLFunc fx = f.getRuntimeContext().fx();
                SQLFunction bank = fx.bank(getValue());
                f.sqlNativeSegment(bank.sqlSegment(getTable()),c->{
                    bank.consume(new SQLNativeChainExpressionContextImpl(getTable(),c));
                });
            });
        }
        return SQLPredicateExpression.empty;
    }
    default SQLPredicateExpression isNotBank() {
        return isNotBank(true);
    }

    default SQLPredicateExpression isNotBank(boolean condition){
        if(condition){
            return new SQLPredicateImpl(f -> {
                SQLFunc fx = f.getRuntimeContext().fx();
                SQLFunction bank = fx.notBank(getValue());
                f.sqlNativeSegment(bank.sqlSegment(getTable()),c->{
                    bank.consume(new SQLNativeChainExpressionContextImpl(getTable(),c));
                });
            });
        }
        return SQLPredicateExpression.empty;
    }
}
