package com.easy.query.api.proxy.entity.update;

import com.easy.query.core.basic.api.internal.ConfigureVersionable;
import com.easy.query.core.basic.api.internal.WithVersionable;
import com.easy.query.core.basic.api.update.ClientExpressionUpdatable;
import com.easy.query.core.basic.api.update.Updatable;
import com.easy.query.core.basic.jdbc.parameter.ToSQLContext;
import com.easy.query.core.expression.lambda.SQLExpression2;
import com.easy.query.core.expression.lambda.SQLFuncExpression1;
import com.easy.query.core.expression.sql.builder.ExpressionContext;
import com.easy.query.core.proxy.ProxyEntity;
import com.easy.query.core.proxy.SQLColumn;
import com.easy.query.core.proxy.SQLPredicateExpression;
import com.easy.query.core.proxy.sql.scec.SQLNativeProxyExpressionContext;
import com.easy.query.core.proxy.sql.scec.SQLNativeProxyExpressionContextImpl;

import java.util.Collection;

/**
 * @author xuejiaming
 * @FileName: ExpressionUpdatable.java
 * @Description: 文件说明
 * @Date: 2023/2/24 23:21
 */
public interface ExpressionUpdatable<TProxy extends ProxyEntity<TProxy, T>, T> extends Updatable<T, ExpressionUpdatable<TProxy,T>>, WithVersionable<ExpressionUpdatable<TProxy,T>>, ConfigureVersionable<ExpressionUpdatable<TProxy,T>> {
    TProxy getProxy();
    ClientExpressionUpdatable<T> getClientUpdate();

    default ExpressionUpdatable<TProxy,T> set(SQLFuncExpression1<TProxy, SQLColumn<TProxy,?>> columnExpression, Object val) {
        return set(true,columnExpression,val);
    }

    default ExpressionUpdatable<TProxy,T> set(boolean condition, SQLFuncExpression1<TProxy, SQLColumn<TProxy,?>> columnExpression, Object val) {
        if(condition){
            SQLColumn<TProxy, ?> sqlColumn = columnExpression.apply(getProxy());
            getClientUpdate().set(true,sqlColumn.getValue(), val);
        }
        return this;
    }

    default ExpressionUpdatable<TProxy,T> setWithColumn(SQLFuncExpression1<TProxy, SQLColumn<TProxy,?>> columnExpression1,  SQLFuncExpression1<TProxy, SQLColumn<TProxy,?>> columnExpression2) {
        return setWithColumn(true,columnExpression1,columnExpression2);
    }

    default ExpressionUpdatable<TProxy,T> setWithColumn(boolean condition, SQLFuncExpression1<TProxy, SQLColumn<TProxy,?>> columnExpression1,  SQLFuncExpression1<TProxy, SQLColumn<TProxy,?>> columnExpression2) {
        if(condition){
            SQLColumn<TProxy, ?> sqlColumn1 = columnExpression1.apply(getProxy());
            SQLColumn<TProxy, ?> sqlColumn2 = columnExpression2.apply(getProxy());
            getClientUpdate().setWithColumn(true, sqlColumn1.getValue(), sqlColumn2.getValue());
        }
        return this;
    }
    // region 列++ --


    default ExpressionUpdatable<TProxy,T> setIncrement(SQLFuncExpression1<TProxy, SQLColumn<TProxy,Integer>> columnExpression) {
        return setIncrement(columnExpression);
    }

    default ExpressionUpdatable<TProxy,T> setIncrement(boolean condition, SQLFuncExpression1<TProxy, SQLColumn<TProxy,Integer>> columnExpression) {
        if(condition){
            SQLColumn<TProxy, Integer> sqlColumn = columnExpression.apply(getProxy());
            getClientUpdate().setIncrement(true, sqlColumn.getValue());
        }
        return this;
    }

    default ExpressionUpdatable<TProxy,T> setIncrement(SQLFuncExpression1<TProxy, SQLColumn<TProxy,Integer>> columnExpression, int val) {
        return setIncrement(true,columnExpression,val);
    }

    default ExpressionUpdatable<TProxy,T> setIncrement(boolean condition, SQLFuncExpression1<TProxy, SQLColumn<TProxy,Integer>> columnExpression, int val) {
        if(condition){
            SQLColumn<TProxy, Integer> sqlColumn = columnExpression.apply(getProxy());
            getClientUpdate().setIncrement(true, sqlColumn.getValue(),val);
        }
        return this;
    }

    default ExpressionUpdatable<TProxy,T> setIncrement(SQLFuncExpression1<TProxy, SQLColumn<TProxy,Long>> columnExpression, long val) {
        return setIncrement(true,columnExpression,val);
    }

    default ExpressionUpdatable<TProxy,T> setIncrement(boolean condition, SQLFuncExpression1<TProxy, SQLColumn<TProxy,Long>> columnExpression, long val) {
        if(condition){
            SQLColumn<TProxy, Long> sqlColumn = columnExpression.apply(getProxy());
            getClientUpdate().setIncrement(true, sqlColumn.getValue(),val);
        }
        return this;
    }


    default ExpressionUpdatable<TProxy,T> setIncrement(SQLFuncExpression1<TProxy, SQLColumn<TProxy,? extends Number>> columnExpression, Number val) {
        return setIncrementNumber(true,columnExpression,val);
    }

    default ExpressionUpdatable<TProxy,T> setIncrementNumber(boolean condition, SQLFuncExpression1<TProxy, SQLColumn<TProxy,? extends Number>> columnExpression, Number val) {
        if(condition){
            SQLColumn<TProxy, ?> sqlColumn = columnExpression.apply(getProxy());
            getClientUpdate().setIncrementNumber(true, sqlColumn.getValue(),val);
        }
        return this;
    }

    default ExpressionUpdatable<TProxy,T> setDecrement(SQLFuncExpression1<TProxy, SQLColumn<TProxy,Integer>> columnExpression) {
        return setDecrement(true,columnExpression);
    }

    default ExpressionUpdatable<TProxy,T> setDecrement(boolean condition, SQLFuncExpression1<TProxy, SQLColumn<TProxy,Integer>> columnExpression) {
        if(condition){
            SQLColumn<TProxy, Integer> sqlColumn = columnExpression.apply(getProxy());
            getClientUpdate().setDecrement(true, sqlColumn.getValue());
        }
        return this;
    }

    default ExpressionUpdatable<TProxy,T> setDecrement(SQLFuncExpression1<TProxy, SQLColumn<TProxy,Integer>> columnExpression, int val) {
        return setDecrement(true,columnExpression,val);
    }

    default ExpressionUpdatable<TProxy,T> setDecrement(boolean condition, SQLFuncExpression1<TProxy, SQLColumn<TProxy,Integer>> columnExpression, int val) {
        if(condition){
            SQLColumn<TProxy, Integer> sqlColumn = columnExpression.apply(getProxy());
            getClientUpdate().setDecrement(true, sqlColumn.getValue(), val);
        }
        return this;
    }

    default ExpressionUpdatable<TProxy,T> setDecrement(SQLFuncExpression1<TProxy, SQLColumn<TProxy,Long>> columnExpression, long val) {
        return setDecrement(true,columnExpression,val);
    }

    default ExpressionUpdatable<TProxy,T> setDecrement(boolean condition, SQLFuncExpression1<TProxy, SQLColumn<TProxy,Long>> columnExpression, long val) {
        if(condition){
            SQLColumn<TProxy, Long> sqlColumn = columnExpression.apply(getProxy());
            getClientUpdate().setDecrement(true, sqlColumn.getValue(), val);
        }
        return this;
    }


    default ExpressionUpdatable<TProxy,T> setDecrement(SQLFuncExpression1<TProxy, SQLColumn<TProxy,? extends Number>> columnExpression, Number val) {
        return setDecrement(true,columnExpression,val);
    }

    default ExpressionUpdatable<TProxy,T> setDecrement(boolean condition, SQLFuncExpression1<TProxy, SQLColumn<TProxy,? extends Number>> columnExpression, Number val) {
        if(condition){
            SQLColumn<TProxy, ?> sqlColumn = columnExpression.apply(getProxy());
            getClientUpdate().setDecrementNumber(true, sqlColumn.getValue(), val);
        }
        return this;
    }


    default ExpressionUpdatable<TProxy,T> setSQLSegment(SQLFuncExpression1<TProxy, SQLColumn<TProxy,?>> columnExpression, String sqlSegment, SQLExpression2<TProxy, SQLNativeProxyExpressionContext> contextConsume) {
        return setSQLSegment(true, columnExpression, sqlSegment, contextConsume);
    }

    default ExpressionUpdatable<TProxy,T> setSQLSegment(boolean condition, SQLFuncExpression1<TProxy, SQLColumn<TProxy,?>> columnExpression, String sqlSegment, SQLExpression2<TProxy, SQLNativeProxyExpressionContext> contextConsume) {
        if (condition) {
            SQLColumn<TProxy, ?> sqlSetColumn = columnExpression.apply(getProxy());
            getClientUpdate().setSQLSegment(sqlSetColumn.getValue(), sqlSegment, (context) -> {
                contextConsume.apply(getProxy(),new SQLNativeProxyExpressionContextImpl(context.getSQLNativeChainExpressionContext().getSQLNativeExpressionContext()));
            });
        }
        return this;
    }
    // endregion

    /**
     * where(o->o.id().eq().and(o.id().eq()))
     * where(o->Predicate.and(
     *      o.id().eq(),
     *      o.id().eq(),
     *      Predicate.or(
     *          o.id().eq(),
     *          o.id().eq()
     *      )
     * ))
     *
     * @param whereExpression
     * @return
     */
    default ExpressionUpdatable<TProxy,T> where(SQLFuncExpression1<TProxy, SQLPredicateExpression> whereExpression) {
        getClientUpdate().where(where -> {
            SQLPredicateExpression sqlPredicate = whereExpression.apply(getProxy());
            sqlPredicate.accept(where.getFilter());
        });
        return this;
    }

    /**
     * where(o->o.id().eq().and(o.id().eq()))
     * where(o->Predicate.and(
     *      o.id().eq(),
     *      o.id().eq(),
     *      Predicate.or(
     *          o.id().eq(),
     *          o.id().eq()
     *      )
     * ))
     * @param condition
     * @param whereExpression
     * @return
     */
    default ExpressionUpdatable<TProxy,T> where(boolean condition, SQLFuncExpression1<TProxy, SQLPredicateExpression> whereExpression) {
        if(condition){
            getClientUpdate().where(true,where -> {
                SQLPredicateExpression sqlPredicate = whereExpression.apply(getProxy());
                sqlPredicate.accept(where.getFilter());
            });
        }
        return this;
    }

    default ExpressionUpdatable<TProxy,T> whereById(Object id) {
        getClientUpdate().whereById(id);
        return this;
    }

    default ExpressionUpdatable<TProxy,T> whereById(boolean condition, Object id) {
        getClientUpdate().whereById(condition, id);
        return this;
    }


    default <TProperty> ExpressionUpdatable<TProxy,T> whereByIds(Collection<TProperty> ids) {
        getClientUpdate().whereByIds(ids);
        return this;
    }

    default <TProperty> ExpressionUpdatable<TProxy,T> whereByIds(boolean condition, Collection<TProperty> ids) {
        getClientUpdate().whereByIds(condition, ids);
        return this;
    }

    default ExpressionContext getExpressionContext() {
        return getClientUpdate().getExpressionContext();
    }

    default String toSQL() {
        return getClientUpdate().toSQL();
    }

    default String toSQL(ToSQLContext toSQLContext) {
        return getClientUpdate().toSQL(toSQLContext);
    }
}

