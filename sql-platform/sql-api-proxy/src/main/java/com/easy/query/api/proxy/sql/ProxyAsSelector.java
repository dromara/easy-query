package com.easy.query.api.proxy.sql;

import com.easy.query.api.proxy.select.ProxyQueryable;
import com.easy.query.api.proxy.sql.core.SQLProxyNative;
import com.easy.query.api.proxy.sql.scec.SQLNativeProxyExpressionContext;
import com.easy.query.api.proxy.sql.scec.SQLNativeProxyExpressionContextImpl;
import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.expression.builder.AsSelector;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.lambda.SQLFuncExpression;
import com.easy.query.core.expression.segment.CloneableSQLSegment;
import com.easy.query.core.expression.sql.builder.ExpressionContext;
import com.easy.query.core.proxy.ProxyEntity;
import com.easy.query.core.proxy.SQLColumn;

import java.util.function.Function;

/**
 * create time 2023/6/22 21:31
 * 文件说明
 *
 * @author xuejiaming
 */
public interface ProxyAsSelector<TRProxy extends ProxyEntity<TRProxy, TR>, TR> extends SQLProxyNative<ProxyAsSelector<TRProxy, TR>> {

    TRProxy getTRProxy();

    AsSelector getAsSelector();

   default QueryRuntimeContext getRuntimeContext(){
       return getAsSelector().getRuntimeContext();
   }
   default ExpressionContext getExpressionContext(){
       return getAsSelector().getExpressionContext();
   }

    default ProxyAsSelector<TRProxy, TR> columns(SQLColumn<?>... columns) {
        if (columns != null) {
            for (SQLColumn<?> column : columns) {
                column(column);
            }
        }
        return this;
    }
    default ProxyAsSelector<TRProxy, TR> column(SQLColumn<?> column) {
        getAsSelector().column(column.getTable(), column.value());
        return this;
    }
    default ProxyAsSelector<TRProxy, TR> sqlNativeSegment(String sqlSegment){
        return sqlNativeSegment(sqlSegment,c->{});
    }
    default ProxyAsSelector<TRProxy, TR> sqlNativeSegment(String sqlSegment, SQLExpression1<SQLNativeProxyExpressionContext> contextConsume){
        getAsSelector().sqlNativeSegment(sqlSegment,context->{
            contextConsume.apply(new SQLNativeProxyExpressionContextImpl(context));
        });
        return this;
    }

    default ProxyAsSelector<TRProxy, TR> columnIgnore(SQLColumn<?> column) {
        getAsSelector().columnIgnore(column.getTable(), column.value());
        return this;
    }

    /**
     * 映射到TR的所有列上,按ColumnName进行映射,如果TR上没有对应的列名那么将不会映射查询列
     *
     * @return
     */
    default <TProxy extends ProxyEntity<TProxy,TEntity>,TEntity> ProxyAsSelector<TRProxy, TR> columnAll(TProxy tableProxy) {
        getAsSelector().columnAll(tableProxy.getTable());
        return this;
    }

    default ProxyAsSelector<TRProxy, TR> columnAs(SQLColumn<?> column, Function<TRProxy, SQLColumn<?>> mapAlias) {
        SQLColumn<?> aliasColumn = mapAlias.apply(getTRProxy());
        return columnAs(column, aliasColumn.value());
    }

    default ProxyAsSelector<TRProxy, TR> columnAs(SQLColumn<?> column, String alias) {
        getAsSelector().columnAs(column.getTable(), column.value(), alias);
        return this;
    }

    default <TSubQueryProxy extends ProxyEntity<TSubQueryProxy, TSubQuery>, TSubQuery> ProxyAsSelector<TRProxy, TR> columnSubQueryAs(SQLFuncExpression<ProxyQueryable<TSubQueryProxy, TSubQuery>> subQueryableFunc, Function<TRProxy, SQLColumn<?>> mapAlias) {
        SQLColumn<?> sqlColumn = mapAlias.apply(getTRProxy());
        return columnSubQueryAs(subQueryableFunc, sqlColumn.value());
    }

    default <TSubQueryProxy extends ProxyEntity<TSubQueryProxy, TSubQuery>, TSubQuery> ProxyAsSelector<TRProxy, TR> columnSubQueryAs(SQLFuncExpression<ProxyQueryable<TSubQueryProxy, TSubQuery>> subQueryableFunc, String alias) {
        getAsSelector().columnSubQueryAs(subQueryableFunc::apply, alias);
        return this;
    }


    default ProxyAsSelector<TRProxy, TR> columnCount(SQLColumn<?> column) {
        getAsSelector().columnCount(column.getTable(), column.value());
        return this;
    }

    default ProxyAsSelector<TRProxy, TR> columnCountAs(SQLColumn<?> column, Function<TRProxy, SQLColumn<?>> mapAlias) {
        SQLColumn<?> sqlColumn = mapAlias.apply(getTRProxy());
        return columnCountAs(column, sqlColumn.value());
    }

    default ProxyAsSelector<TRProxy, TR> columnCountAs(SQLColumn<?> column, String alias) {
        getAsSelector().columnCountAs(column.getTable(), column.value(), alias);
        return this;
    }


    default ProxyAsSelector<TRProxy, TR> columnCountDistinct(SQLColumn<?> column) {
        getAsSelector().columnCountDistinct(column.getTable(), column.value());
        return this;
    }

    default ProxyAsSelector<TRProxy, TR> columnCountDistinctAs(SQLColumn<?> column, String alias) {
        getAsSelector().columnCountDistinctAs(column.getTable(), column.value(), alias);
        return this;
    }

    default ProxyAsSelector<TRProxy, TR> columnCountDistinctAs(SQLColumn<?> column, Function<TRProxy, SQLColumn<?>> mapAlias) {
        SQLColumn<?> sqlColumn = mapAlias.apply(getTRProxy());
        return columnCountDistinctAs(column,sqlColumn.value());
    }

    default ProxyAsSelector<TRProxy, TR> columnSum(SQLColumn<? extends Number> column) {
        getAsSelector().columnSum(column.getTable(), column.value());
        return this;
    }

    default ProxyAsSelector<TRProxy, TR> columnSumAs(SQLColumn<? extends Number> column, Function<TRProxy, SQLColumn<? extends Number>> mapAlias) {
        SQLColumn<?> sqlColumn = mapAlias.apply(getTRProxy());
        return columnSumAs(column,sqlColumn.value());
    }

    default ProxyAsSelector<TRProxy, TR> columnSumAs(SQLColumn<? extends Number> column, String alias) {
        getAsSelector().columnSumAs(column.getTable(), column.value(), alias);
        return this;
    }

    default ProxyAsSelector<TRProxy, TR> columnSumDistinct(SQLColumn<? extends Number> column) {
        getAsSelector().columnSumDistinct(column.getTable(), column.value());
        return this;
    }

    default ProxyAsSelector<TRProxy, TR> columnSumDistinctAs(SQLColumn<? extends Number> column, Function<TRProxy, SQLColumn<? extends Number>> mapAlias) {
        SQLColumn<?> sqlColumn = mapAlias.apply(getTRProxy());
        return columnSumDistinctAs(column,sqlColumn.value());
    }

    default ProxyAsSelector<TRProxy, TR> columnSumDistinctAs(SQLColumn<? extends Number> column, String alias) {
        getAsSelector().columnSumDistinctAs(column.getTable(), column.value(), alias);
        return this;
    }

    default ProxyAsSelector<TRProxy, TR> columnMax(SQLColumn<?> column) {
        getAsSelector().columnMax(column.getTable(), column.value());
        return this;
    }

    default <TProperty> ProxyAsSelector<TRProxy, TR> columnMaxAs(SQLColumn<TProperty> column, Function<TRProxy, SQLColumn<TProperty>> mapAlias) {
        SQLColumn<TProperty> sqlColumn = mapAlias.apply(getTRProxy());
        return columnMaxAs(column,sqlColumn.value());
    }

    default ProxyAsSelector<TRProxy, TR> columnMaxAs(SQLColumn<?> column, String alias) {
        getAsSelector().columnMaxAs(column.getTable(), column.value(), alias);
        return this;
    }

    default ProxyAsSelector<TRProxy, TR> columnMin(SQLColumn<?> column) {
        getAsSelector().columnMin(column.getTable(), column.value());
        return this;
    }

    default <TProperty> ProxyAsSelector<TRProxy, TR> columnMinAs(SQLColumn<TProperty> column, Function<TRProxy, SQLColumn<TProperty>> mapAlias) {
        SQLColumn<?> sqlColumn = mapAlias.apply(getTRProxy());
        return columnMinAs(column,sqlColumn.value());
    }

    default ProxyAsSelector<TRProxy, TR> columnMinAs(SQLColumn<?> column, String alias) {
        getAsSelector().columnMinAs(column.getTable(), column.value(), alias);
        return this;
    }

    default ProxyAsSelector<TRProxy, TR> columnAvg(SQLColumn<? extends Number> column) {
        getAsSelector().columnAvg(column.getTable(), column.value());
        return this;
    }

    default ProxyAsSelector<TRProxy, TR> columnAvgAs(SQLColumn<? extends Number> column, Function<TRProxy, SQLColumn<? extends Number>> mapAlias) {
        SQLColumn<?> sqlColumn = mapAlias.apply(getTRProxy());
        return columnAvgAs(column,sqlColumn.value());
    }

    default ProxyAsSelector<TRProxy, TR> columnAvgAs(SQLColumn<? extends Number> column, String alias) {
        getAsSelector().columnAvgAs(column.getTable(), column.value(), alias);
        return this;
    }

    default ProxyAsSelector<TRProxy, TR> columnAvgDistinct(SQLColumn<? extends Number> column) {
        getAsSelector().columnAvgDistinct(column.getTable(), column.value());
        return this;
    }

    default ProxyAsSelector<TRProxy, TR> columnAvgDistinctAs(SQLColumn<? extends Number> column, Function<TRProxy, SQLColumn<? extends Number>> mapAlias) {
        SQLColumn<?> sqlColumn = mapAlias.apply(getTRProxy());
        return columnAvgDistinctAs(column,sqlColumn.value());
    }

    default ProxyAsSelector<TRProxy, TR> columnAvgDistinctAs(SQLColumn<? extends Number> column, String alias) {
        getAsSelector().columnAvgDistinctAs(column.getTable(), column.value(),alias);
        return this;
    }

    default ProxyAsSelector<TRProxy, TR> columnLen(SQLColumn<?> column) {
        getAsSelector().columnLen(column.getTable(), column.value());
        return this;
    }

    default ProxyAsSelector<TRProxy, TR> columnLenAs(SQLColumn<?> column, Function<TRProxy, SQLColumn<?>> mapAlias) {
        SQLColumn<?> sqlColumn = mapAlias.apply(getTRProxy());
        return columnLenAs(column,sqlColumn.value());
    }

    default ProxyAsSelector<TRProxy, TR> columnLenAs(SQLColumn<?> column, String alias) {
        getAsSelector().columnLenAs(column.getTable(), column.value(),alias);
        return this;
    }

    default ProxyAsSelector<TRProxy, TR> columnFuncAs(ProxyColumnPropertyFunction proxyColumnPropertyFunction, Function<TRProxy, SQLColumn<?>> mapAlias) {
        SQLColumn<?> sqlColumn = mapAlias.apply(getTRProxy());
        return columnFuncAs(proxyColumnPropertyFunction,sqlColumn.value());
    }

    default ProxyAsSelector<TRProxy, TR> columnFuncAs(ProxyColumnPropertyFunction proxyColumnPropertyFunction, String alias) {
        getAsSelector().columnFuncAs(proxyColumnPropertyFunction.getColumn().getTable(), proxyColumnPropertyFunction.getColumnPropertyFunction(),alias);
        return this;
    }
    default ProxyAsSelector<TRProxy,TR> sqlSegmentAs(CloneableSQLSegment sqlColumnSegment, Function<TRProxy, SQLColumn<?>> mapAlias){
        SQLColumn<?> sqlColumn = mapAlias.apply(getTRProxy());
        getAsSelector().sqlColumnAs(sqlColumnSegment, sqlColumn.value());
        return this;
    }
}
