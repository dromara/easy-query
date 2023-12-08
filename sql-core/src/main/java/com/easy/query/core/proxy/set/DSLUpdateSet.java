package com.easy.query.core.proxy.set;

import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.func.SQLFunc;
import com.easy.query.core.func.SQLFunction;
import com.easy.query.core.proxy.SQLColumn;
import com.easy.query.core.proxy.TablePropColumn;
import com.easy.query.core.proxy.available.EntitySQLContextAvailable;
import com.easy.query.core.proxy.impl.SQLColumnSetImpl;
import com.easy.query.core.proxy.predicate.aggregate.DSLSQLFunctionAvailable;
import com.easy.query.core.proxy.sql.scec.SQLNativeProxyExpressionContext;
import com.easy.query.core.proxy.sql.scec.SQLNativeProxyExpressionContextImpl;

import java.util.function.Function;

/**
 * create time 2023/12/8 10:46
 * 文件说明
 *
 * @author xuejiaming
 */
public interface DSLUpdateSet extends TablePropColumn, EntitySQLContextAvailable {
    default void set(Object val) {
         set(true, val);
    }

    default void set(boolean condition, Object val) {
        if (condition) {
            getEntitySQLContext().accept(new SQLColumnSetImpl(x -> {
                x.set(getTable(), getValue(), val);
            }));
        }
    }
    default void set(DSLSQLFunctionAvailable val) {
         set(true, val);
    }

    default void set(boolean condition, DSLSQLFunctionAvailable val) {
        if (condition) {
            getEntitySQLContext().accept(new SQLColumnSetImpl(x -> {
                Function<SQLFunc, SQLFunction> func = val.func();
                SQLFunc fx = x.getRuntimeContext().fx();
                x.setFunc(getTable(), getValue(), func.apply(fx));
            }));
        }
    }

    default void setSQLNativeSegment(String sqlSegment) {
        setSQLNativeSegment(sqlSegment, c->{});
    }
    default void setSQLNativeSegment(String sqlSegment, SQLExpression1<SQLNativeProxyExpressionContext> contextConsume) {
         setSQLNativeSegment(true, sqlSegment, contextConsume);
    }


    default void setSQLNativeSegment(boolean condition, String sqlSegment, SQLExpression1<SQLNativeProxyExpressionContext> contextConsume) {
        if (condition) {
           getEntitySQLContext().accept(new SQLColumnSetImpl(x -> {
               x.sqlNativeSegment(getTable(), getValue(), sqlSegment, c->{
                   contextConsume.apply(new SQLNativeProxyExpressionContextImpl(c));
               });
           }));
        }
    }

    default void set(SQLColumn<?,?> column) {
         setWithColumn(true, column);
    }

    default void setWithColumn(boolean condition, SQLColumn<?,?> column) {
        if (condition) {
            getEntitySQLContext().accept(new SQLColumnSetImpl(x -> {
                x.setWithColumn(getTable(), getValue(), column.getValue());
            }));
        }
    }

    default void increment() {
         increment(true);
    }

    default void increment(boolean condition) {
        if (condition) {
           getEntitySQLContext().accept( new SQLColumnSetImpl(x -> {
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
