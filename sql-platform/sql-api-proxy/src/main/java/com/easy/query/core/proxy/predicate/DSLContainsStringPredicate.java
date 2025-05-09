package com.easy.query.core.proxy.predicate;

import com.easy.query.core.enums.SQLLikeEnum;
import com.easy.query.core.func.SQLFunc;
import com.easy.query.core.func.SQLFunction;
import com.easy.query.core.proxy.TablePropColumn;
import com.easy.query.core.proxy.available.EntitySQLContextAvailable;
import com.easy.query.core.proxy.core.Expression;
import com.easy.query.core.proxy.impl.SQLPredicateImpl;

/**
 * create time 2023/12/2 14:13
 * column like val,val必须是值不可以是函数
 *
 * @author xuejiaming
 */
public interface DSLContainsStringPredicate extends TablePropColumn, EntitySQLContextAvailable {
    /**
     * column like 'value%'
     *
     * @param val
     */
    default void startsWith(String val) {
        startsWith(true, val);
    }

    /**
     * column like 'value%'
     *
     * @param condition
     * @param val
     */
    default void startsWith(boolean condition, String val) {
        if (condition) {
            getCurrentEntitySQLContext().accept(new SQLPredicateImpl(f -> {
                if(f.conditionAppend(this,val)){
                    SQLFunc fx = f.getRuntimeContext().fx();
                    SQLFunction likeSQLFunction = fx.like(s->{
                        s.column(this);
                        s.value(val);
                    },true, SQLLikeEnum.LIKE_PERCENT_RIGHT);
                    f.sqlFunctionExecute(getTable(),likeSQLFunction);
                }
            }));
        }
    }

    /**
     * column like '%value'
     *
     * @param val
     */
    default void endsWith(String val) {
        endsWith(true, val);
    }

    /**
     * column like '%value'
     *
     * @param condition
     * @param val
     */
    default void endsWith(boolean condition, String val) {
        if (condition) {

            getCurrentEntitySQLContext().accept(new SQLPredicateImpl(f -> {
                if(f.conditionAppend(this,val)){
                    SQLFunc fx = f.getRuntimeContext().fx();
                    SQLFunction likeSQLFunction = fx.like(s->{
                        s.column(this);
                        s.value(val);
                    },true, SQLLikeEnum.LIKE_PERCENT_LEFT);
                    f.sqlFunctionExecute(getTable(),likeSQLFunction);
                }
            }));
        }
    }

    /**
     * 包含val值
     * @param val 比较值
     */
    default void contains(String val) {
        contains(true, val);
    }

    /**
     * 包含val值
     * @param condition 如果为false则该条件不生效
     * @param val 比较值
     */
    default void contains(boolean condition, String val) {
        if (condition) {

            getCurrentEntitySQLContext().accept(new SQLPredicateImpl(f -> {
                if(f.conditionAppend(this,val)){
                    SQLFunc fx = f.getRuntimeContext().fx();
                    SQLFunction likeSQLFunction = fx.like(s->{
                        s.column(this);
                        s.value(val);
                    },true, SQLLikeEnum.LIKE_PERCENT_ALL);
                    f.sqlFunctionExecute(getTable(),likeSQLFunction);
                }
            }));
        }
    }

    default void notStartsWith(String val) {
        notStartsWith(true, val);
    }

    default void notStartsWith(boolean condition, String val) {
        if (condition) {
            SQLFunction sqlFunction = Expression.of(getEntitySQLContext()).constant(val).func().apply(getEntitySQLContext().getRuntimeContext().fx());

            getCurrentEntitySQLContext().accept(new SQLPredicateImpl(f -> {
                if(f.conditionAppend(this,val)){
                    SQLFunc fx = f.getRuntimeContext().fx();
                    SQLFunction likeSQLFunction = fx.like(s->{
                        s.column(this);
                        s.sqlFunc(sqlFunction);
                    },false, SQLLikeEnum.LIKE_PERCENT_RIGHT);
                    f.sqlFunctionExecute(getTable(),likeSQLFunction);
                }
            }));
        }
    }

    default void notEndsWith(String val) {
        notEndsWith(true, val);
    }

    default void notEndsWith(boolean condition, String val) {
        if (condition) {

            getCurrentEntitySQLContext().accept(new SQLPredicateImpl(f -> {
                if(f.conditionAppend(this,val)){
                    SQLFunc fx = f.getRuntimeContext().fx();
                    SQLFunction likeSQLFunction = fx.like(s->{
                        s.column(this);
                        s.value(val);
                    },false, SQLLikeEnum.LIKE_PERCENT_LEFT);
                    f.sqlFunctionExecute(getTable(),likeSQLFunction);
                }
            }));
        }
    }

    default void notContains(String val) {
        notContains(true, val);
    }

    default void notContains(boolean condition, String val) {
        if (condition) {
            SQLFunction sqlFunction = Expression.of(getEntitySQLContext()).constant(val).func().apply(getEntitySQLContext().getRuntimeContext().fx());
            getCurrentEntitySQLContext().accept(new SQLPredicateImpl(f -> {
                if (f.conditionAppend(this, val)) {
                    SQLFunc fx = f.getRuntimeContext().fx();
                    SQLFunction likeSQLFunction = fx.like(s->{
                        s.column(this);
                        s.sqlFunc(sqlFunction);
                    },false, SQLLikeEnum.LIKE_PERCENT_ALL);
                    f.sqlFunctionExecute(getTable(),likeSQLFunction);
                }
            }));
        }
    }
}
