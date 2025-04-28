package com.easy.query.core.proxy.predicate;

import com.easy.query.core.enums.SQLLikeEnum;
import com.easy.query.core.func.SQLFunc;
import com.easy.query.core.func.SQLFunction;
import com.easy.query.core.proxy.PropTypeColumn;
import com.easy.query.core.proxy.TablePropColumn;
import com.easy.query.core.proxy.available.EntitySQLContextAvailable;
import com.easy.query.core.proxy.impl.SQLPredicateImpl;

/**
 * create time 2023/12/2 14:13
 * column like val,val必须是值不可以是函数
 *
 * @author xuejiaming
 */
public interface DSLContainsPropPredicate extends TablePropColumn, EntitySQLContextAvailable {
    /**
     * column like 'value%'
     *
     * @param val
     */
    default void startsWith(PropTypeColumn<String> val) {
        startsWith(true, val);
    }

    /**
     * column like 'value%'
     *
     * @param condition
     * @param val
     */
    default void startsWith(boolean condition, PropTypeColumn<String> val) {
        if (condition) {
            getCurrentEntitySQLContext().accept(new SQLPredicateImpl(f -> {
                SQLFunc fx = f.getRuntimeContext().fx();
                SQLFunction likeSQLFunction = fx.like(s -> {
                    s.column(this);
                    PropTypeColumn.columnFuncSelector(s, val);
                }, true, SQLLikeEnum.LIKE_PERCENT_RIGHT);
                f.sqlFunctionExecute(getTable(), likeSQLFunction);
            }));
        }
    }

    /**
     * column like '%value'
     *
     * @param val
     */
    default void endsWith(PropTypeColumn<String> val) {
        endsWith(true, val);
    }

    /**
     * column like '%value'
     *
     * @param condition
     * @param val
     */
    default void endsWith(boolean condition, PropTypeColumn<String> val) {
        if (condition) {

            getCurrentEntitySQLContext().accept(new SQLPredicateImpl(f -> {
                SQLFunc fx = f.getRuntimeContext().fx();
                SQLFunction likeSQLFunction = fx.like(s -> {
                    s.column(this);
                    PropTypeColumn.columnFuncSelector(s, val);
                }, true, SQLLikeEnum.LIKE_PERCENT_LEFT);
                f.sqlFunctionExecute(getTable(), likeSQLFunction);
            }));
        }
    }

    default void contains(PropTypeColumn<String> val) {
        contains(true, val);
    }

    default void contains(boolean condition, PropTypeColumn<String> val) {
        if (condition) {
            getCurrentEntitySQLContext().accept(new SQLPredicateImpl(f -> {
                SQLFunc fx = f.getRuntimeContext().fx();
                SQLFunction likeSQLFunction = fx.like(s -> {
                    s.column(this);
                    PropTypeColumn.columnFuncSelector(s, val);
                }, true, SQLLikeEnum.LIKE_PERCENT_ALL);
                f.sqlFunctionExecute(getTable(), likeSQLFunction);
            }));
        }
    }

    default void notStartsWith(PropTypeColumn<String> val) {
        notStartsWith(true, val);
    }

    default void notStartsWith(boolean condition, PropTypeColumn<String> val) {
        if (condition) {
            getCurrentEntitySQLContext().accept(new SQLPredicateImpl(f -> {
                SQLFunc fx = f.getRuntimeContext().fx();
                SQLFunction likeSQLFunction = fx.like(s -> {
                    s.column(this);
                    PropTypeColumn.columnFuncSelector(s, val);
                }, false, SQLLikeEnum.LIKE_PERCENT_RIGHT);
                f.sqlFunctionExecute(getTable(), likeSQLFunction);
            }));
        }
    }

    default void notEndsWith(PropTypeColumn<String> val) {
        notEndsWith(true, val);
    }

    default void notEndsWith(boolean condition, PropTypeColumn<String> val) {
        if (condition) {
            getCurrentEntitySQLContext().accept(new SQLPredicateImpl(f -> {
                SQLFunc fx = f.getRuntimeContext().fx();
                SQLFunction likeSQLFunction = fx.like(s -> {
                    s.column(this);
                    PropTypeColumn.columnFuncSelector(s, val);
                }, false, SQLLikeEnum.LIKE_PERCENT_LEFT);
                f.sqlFunctionExecute(getTable(), likeSQLFunction);
            }));
        }
    }

    /**
     * 不包含生成not like
     * @param val 被比较的表达式列
     */
    default void notContains(PropTypeColumn<String> val) {
        notContains(true, val);
    }

    /**
     * 不包含 生成not like
     * @param condition true表示该函数会被执行
     * @param val
     */
    default void notContains(boolean condition, PropTypeColumn<String> val) {
        if (condition) {
            getCurrentEntitySQLContext().accept(new SQLPredicateImpl(f -> {
                SQLFunc fx = f.getRuntimeContext().fx();
                SQLFunction likeSQLFunction = fx.like(s -> {
                    s.column(this);
                    PropTypeColumn.columnFuncSelector(s, val);
                }, false, SQLLikeEnum.LIKE_PERCENT_ALL);
                f.sqlFunctionExecute(getTable(), likeSQLFunction);
            }));
        }
    }
}
