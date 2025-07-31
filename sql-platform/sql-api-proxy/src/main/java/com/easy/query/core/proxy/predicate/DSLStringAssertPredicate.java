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
           getCurrentEntitySQLContext().accept(new SQLPredicateImpl(f -> {
               SQLFunc fx = f.getRuntimeContext().fx();
               SQLFunction bank = fx.empty(getValue());

               f.sqlFunctionExecute(getTable(),bank);
           }));
        }
    }
    default void isNotEmpty() {
         isNotEmpty(true);
    }

    default void isNotEmpty(boolean condition){
        if(condition){
            getCurrentEntitySQLContext().accept(new SQLPredicateImpl(f -> {
               SQLFunc fx = f.getRuntimeContext().fx();
               SQLFunction bank = fx.notEmpty(getValue());
                f.sqlFunctionExecute(getTable(),bank);
           }));
        }
    }

    /**
     * 断言列为空字符串
     * eg. (column IS NULL OR column = '' OR LTRIM(column) = '')
     */
    default void isBlank() {
         isBlank(true);
    }

    /**
     * 断言列为空字符串
     * eg. (column IS NULL OR column = '' OR LTRIM(column) = '')
     */
    default void isBlank(boolean condition){
        if(condition){
            getCurrentEntitySQLContext().accept(new SQLPredicateImpl(f -> {
                SQLFunc fx = f.getRuntimeContext().fx();
                SQLFunction bank = fx.bank(getValue());
                f.sqlFunctionExecute(getTable(),bank);
            }));
        }
    }

    /**
     * 断言列不是空字符串
     * eg. (column IS NOT NULL AND column <> '' AND LTRIM(column) <> '')
     */
    default void isNotBlank() {
         isNotBlank(true);
    }

    /**
     * 断言列不是空字符串
     * eg. (column IS NOT NULL AND column <> '' AND LTRIM(column) <> '')
     */
    default void isNotBlank(boolean condition){
        if(condition){
            getCurrentEntitySQLContext().accept(new SQLPredicateImpl(f -> {
               SQLFunc fx = f.getRuntimeContext().fx();
               SQLFunction bank = fx.notBank(getValue());
                f.sqlFunctionExecute(getTable(), bank);
           }));
        }
    }
}
