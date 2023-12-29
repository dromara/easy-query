package com.easy.query.core.proxy.set;

import com.easy.query.core.basic.api.select.Query;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.proxy.PropTypeColumn;
import com.easy.query.core.proxy.SQLColumn;
import com.easy.query.core.proxy.SQLSelectExpression;
import com.easy.query.core.proxy.TablePropColumn;
import com.easy.query.core.proxy.available.EntitySQLContextAvailable;
import com.easy.query.core.proxy.impl.SQLColumnSetColumnImpl;
import com.easy.query.core.proxy.impl.SQLColumnSetImpl;
import com.easy.query.core.proxy.impl.SQLColumnSetNativeSQLImpl;
import com.easy.query.core.proxy.impl.SQLColumnSetSQLFunctionValueImpl;
import com.easy.query.core.proxy.impl.SQLColumnSetSubQueryImpl;
import com.easy.query.core.proxy.impl.SQLColumnSetValueImpl;
import com.easy.query.core.proxy.predicate.aggregate.DSLSQLFunctionAvailable;
import com.easy.query.core.proxy.sql.scec.SQLNativeProxyExpressionContext;

/**
 * create time 2023/12/8 10:46
 * 文件说明
 *
 * @author xuejiaming
 */
public interface DSLColumnSet<TProperty> extends TablePropColumn, EntitySQLContextAvailable {
    default void set(TProperty val) {
        set(true, val);
    }

    default void set(boolean condition, TProperty val) {
        if (condition) {
            getEntitySQLContext().accept(new SQLColumnSetValueImpl(getTable(), getValue(), val));
        }
    }

    default void set(SQLColumn<?, TProperty> column) {
        set(true, column);
    }

    default void set(boolean condition, SQLColumn<?, TProperty> column) {
        if (condition) {
            getEntitySQLContext().accept(new SQLColumnSetColumnImpl(getTable(), getValue(), column));
        }
    }

    /**
     * 支持function函数
     * @param val
     * @param <TResult>
     */
    default <TResult extends DSLSQLFunctionAvailable & PropTypeColumn<TProperty>> void set(TResult val) {
        set(true, val);
    }

    default <TResult extends DSLSQLFunctionAvailable & PropTypeColumn<TProperty>> void set(boolean condition, TResult val) {
        if (condition) {
            getEntitySQLContext().accept(new SQLColumnSetSQLFunctionValueImpl(getTable(), getValue(), val));
        }
    }

    default void setSQL(String sqlSegment) {
        setSQL(sqlSegment, c -> {
        });
    }

    default void setSQL(String sqlSegment, SQLExpression1<SQLNativeProxyExpressionContext> contextConsume) {
        setSQL(true, sqlSegment, contextConsume);
    }


    default void setSQL(boolean condition, String sqlSegment, SQLExpression1<SQLNativeProxyExpressionContext> contextConsume) {
        if (condition) {
            getEntitySQLContext().accept(new SQLColumnSetNativeSQLImpl(getTable(), getValue(), sqlSegment, contextConsume));
        }
    }


    default void setSubQuery(Query<TProperty> subQuery) {
        setSubQuery(true, subQuery);
    }

    default void setSubQuery(boolean condition, Query<TProperty> subQuery) {
        if (condition) {
            getEntitySQLContext().accept(new SQLColumnSetSubQueryImpl(getTable(), getValue(), subQuery));
        }
    }
    default void setExpression(SQLSelectExpression sqlSelectExpression) {
        setExpression(true, sqlSelectExpression);
    }

    default void setExpression(boolean condition, SQLSelectExpression sqlSelectExpression) {
        if (condition) {
            getEntitySQLContext().accept(sqlSelectExpression.as(getValue()));
        }
    }

    default void increment() {
        increment(true);
    }

    default void increment(boolean condition) {
        if (condition) {
            getEntitySQLContext().accept(new SQLColumnSetImpl(x -> {
                x.setIncrement(getTable(), getValue());
            }));
        }
    }

    default void decrement() {
        decrement(true);
    }

    default void decrement(boolean condition) {
        if (condition) {
            getEntitySQLContext().accept(new SQLColumnSetImpl(x -> {
                x.setDecrement(getTable(), getValue());
            }));
        }
    }


    default <T extends Number> void increment(Number val) {
        increment(true, val);
    }

    default <T extends Number> void increment(boolean condition, Number val) {
        if (condition) {
            getEntitySQLContext().accept(new SQLColumnSetImpl(x -> {
                x.setIncrementNumber(true, getTable(), getValue(), val);
            }));
        }
    }

    default <T extends Number> void decrement(Number val) {
        decrement(true, val);
    }

    default <T extends Number> void decrement(boolean condition, Number val) {
        if (condition) {
            getEntitySQLContext().accept(new SQLColumnSetImpl(x -> {
                x.setDecrementNumber(true, getTable(), getValue(), val);
            }));
        }
    }

}
