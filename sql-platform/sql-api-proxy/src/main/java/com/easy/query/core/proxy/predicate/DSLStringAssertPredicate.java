package com.easy.query.core.proxy.predicate;

import com.easy.query.core.expression.parser.core.base.scec.core.SQLNativeChainExpressionContextImpl;
import com.easy.query.core.func.SQLFunc;
import com.easy.query.core.func.SQLFunction;
import com.easy.query.core.proxy.TablePropColumn;
import com.easy.query.core.proxy.available.EntitySQLContextAvailable;
import com.easy.query.core.proxy.impl.SQLPredicateImpl;

/**
 * create time 2023/12/6 21:24
 * 文件说明
 *
 * @author xuejiaming
 */
public interface DSLStringAssertPredicate<TProperty> extends TablePropColumn, EntitySQLContextAvailable {
    default void isEmpty() {
         isEmpty(true);
    }

    default void isEmpty(boolean condition){
        if(condition){
           getEntitySQLContext().accept(new SQLPredicateImpl(f -> {
               SQLFunc fx = f.getRuntimeContext().fx();
               SQLFunction bank = fx.empty(getValue());
               f.sqlNativeSegment(bank.sqlSegment(getTable()),c->{
                   bank.consume(new SQLNativeChainExpressionContextImpl(getTable(),c));
               });
           }));
        }
    }
    default void isNotEmpty() {
         isNotEmpty(true);
    }

    default void isNotEmpty(boolean condition){
        if(condition){
           getEntitySQLContext().accept(new SQLPredicateImpl(f -> {
               SQLFunc fx = f.getRuntimeContext().fx();
               SQLFunction bank = fx.notEmpty(getValue());
               f.sqlNativeSegment(bank.sqlSegment(getTable()),c->{
                   bank.consume(new SQLNativeChainExpressionContextImpl(getTable(),c));
               });
           }));
        }
    }
    default void isBlank() {
         isBlank(true);
    }

    default void isBlank(boolean condition){
        if(condition){
            getEntitySQLContext().accept(new SQLPredicateImpl(f -> {
                SQLFunc fx = f.getRuntimeContext().fx();
                SQLFunction bank = fx.bank(getValue());
                f.sqlNativeSegment(bank.sqlSegment(getTable()),c->{
                    bank.consume(new SQLNativeChainExpressionContextImpl(getTable(),c));
                });
            }));
        }
    }
    default void isNotBlank() {
         isNotBlank(true);
    }

    default void isNotBlank(boolean condition){
        if(condition){
           getEntitySQLContext().accept(new SQLPredicateImpl(f -> {
               SQLFunc fx = f.getRuntimeContext().fx();
               SQLFunction bank = fx.notBank(getValue());
               f.sqlNativeSegment(bank.sqlSegment(getTable()),c->{
                   bank.consume(new SQLNativeChainExpressionContextImpl(getTable(),c));
               });
           }));
        }
    }
}
