package com.easy.query.api.proxy.sql;

import com.easy.query.api.proxy.select.ProxyQueryable;
import com.easy.query.api.proxy.sql.core.SQLAsProxyNative;
import com.easy.query.api.proxy.sql.core.available.ProxySQLFuncAvailable;
import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.expression.builder.AsSelector;
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
public interface ProxyAsSelector<TRProxy extends ProxyEntity<TRProxy, TR>, TR> extends SQLAsProxyNative<TRProxy,TR,ProxyAsSelector<TRProxy, TR>>, ProxySQLFuncAvailable {

    TRProxy tr();

    AsSelector getAsSelector();

   default QueryRuntimeContext getRuntimeContext(){
       return getAsSelector().getRuntimeContext();
   }
   default ExpressionContext getExpressionContext(){
       return getAsSelector().getExpressionContext();
   }

    default ProxyAsSelector<TRProxy, TR> columns(SQLColumn<?,?>... columns) {
        if (columns != null) {
            for (SQLColumn<?,?> sqlColumn : columns) {
                getAsSelector().column(sqlColumn.getTable(), sqlColumn.value());
            }
        }
        return this;
    }
    default <TProxy extends ProxyEntity<TProxy,T>,T,TProperty> ProxyAsSelector<TRProxy, TR> column(SQLColumn<TProxy,TProperty> column) {
        getAsSelector().column(column.getTable(), column.value());
        return this;
    }

    default ProxyAsSelector<TRProxy, TR> groupKeys(int index) {
        getAsSelector().groupKeys(index);
        return this;
    }
    default <TProperty> ProxyAsSelector<TRProxy, TR> groupKeysAs(int index, SQLColumn<TRProxy,TProperty> aliasColumn) {
        getAsSelector().groupKeysAs(index,aliasColumn.value());
        return this;
    }
    default ProxyAsSelector<TRProxy, TR> groupKeysAs(int index, String alias) {
        getAsSelector().groupKeysAs(index,alias);
        return this;
    }

    default <TProxy extends ProxyEntity<TProxy,T>,T,TProperty> ProxyAsSelector<TRProxy, TR> columnIgnore(SQLColumn<TProxy,TProperty> column) {
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

    default <TProxy extends ProxyEntity<TProxy,TEntity>,TEntity,TProperty> ProxyAsSelector<TRProxy, TR> columnAs(SQLColumn<TProxy,TProperty> column, SQLColumn<TRProxy,TProperty> aliasColumn) {
        getAsSelector().columnAs(column.getTable(), column.value(), aliasColumn.value());
        return this;
    }

    default <TSubQueryProxy extends ProxyEntity<TSubQueryProxy, TSubQuery>, TSubQuery> ProxyAsSelector<TRProxy, TR> columnSubQueryAs(SQLFuncExpression<ProxyQueryable<TSubQueryProxy, TSubQuery>> subQueryableFunc, SQLColumn<TRProxy,TSubQuery> aliasColumn) {
        return columnSubQueryAs(subQueryableFunc, aliasColumn.value());
    }

    default <TSubQueryProxy extends ProxyEntity<TSubQueryProxy, TSubQuery>, TSubQuery> ProxyAsSelector<TRProxy, TR> columnSubQueryAs(SQLFuncExpression<ProxyQueryable<TSubQueryProxy, TSubQuery>> subQueryableFunc, String alias) {
        getAsSelector().columnSubQueryAs(subQueryableFunc::apply, alias);
        return this;
    }


    default  <TProxy extends ProxyEntity<TProxy,TEntity>,TEntity,TProperty> ProxyAsSelector<TRProxy, TR> columnCount(SQLColumn<TProxy,TProperty> column) {
        getAsSelector().columnCount(column.getTable(), column.value());
        return this;
    }

    default <TProxy extends ProxyEntity<TProxy,TEntity>,TEntity,TProperty> ProxyAsSelector<TRProxy, TR> columnCountAs(SQLColumn<TProxy,TProperty> column, SQLColumn<TRProxy,TProperty> aliasColumn) {
        return columnCountAs(column, aliasColumn.value());
    }

    default <TProxy extends ProxyEntity<TProxy,TEntity>,TEntity,TProperty> ProxyAsSelector<TRProxy, TR> columnCountAs(SQLColumn<TProxy,TProperty> column, String alias) {
        getAsSelector().columnCountAs(column.getTable(), column.value(), alias);
        return this;
    }


    default <TProxy extends ProxyEntity<TProxy,TEntity>,TEntity,TProperty> ProxyAsSelector<TRProxy, TR> columnCountDistinct(SQLColumn<TProxy,TProperty> column) {
        getAsSelector().columnCountDistinct(column.getTable(), column.value());
        return this;
    }

    default <TProxy extends ProxyEntity<TProxy,TEntity>,TEntity,TProperty> ProxyAsSelector<TRProxy, TR> columnCountDistinctAs(SQLColumn<TProxy,TProperty> column, String alias) {
        getAsSelector().columnCountDistinctAs(column.getTable(), column.value(), alias);
        return this;
    }

    default  <TProxy extends ProxyEntity<TProxy,TEntity>,TEntity,TProperty> ProxyAsSelector<TRProxy, TR> columnCountDistinctAs(SQLColumn<TProxy,TProperty> column, SQLColumn<TRProxy,TProperty> aliasColumn) {
        return columnCountDistinctAs(column,aliasColumn.value());
    }

    default <TProxy extends ProxyEntity<TProxy,TEntity>,TEntity,TProperty extends Number> ProxyAsSelector<TRProxy, TR> columnSum(SQLColumn<TProxy,TProperty> column) {
        getAsSelector().columnSum(column.getTable(), column.value());
        return this;
    }

    default <TProxy extends ProxyEntity<TProxy,TEntity>,TEntity,TProperty extends Number> ProxyAsSelector<TRProxy, TR> columnSumAs(SQLColumn<TProxy,TProperty> column, SQLColumn<TRProxy,TProperty> aliasColumn) {
        return columnSumAs(column,aliasColumn.value());
    }

    default <TProxy extends ProxyEntity<TProxy,TEntity>,TEntity,TProperty extends Number> ProxyAsSelector<TRProxy, TR> columnSumAs(SQLColumn<TProxy,TProperty> column, String alias) {
        getAsSelector().columnSumAs(column.getTable(), column.value(), alias);
        return this;
    }

    default <TProxy extends ProxyEntity<TProxy,TEntity>,TEntity,TProperty extends Number> ProxyAsSelector<TRProxy, TR> columnSumDistinct(SQLColumn<TProxy,TProperty> column) {
        getAsSelector().columnSumDistinct(column.getTable(), column.value());
        return this;
    }

    default <TProxy extends ProxyEntity<TProxy,TEntity>,TEntity,TProperty extends Number> ProxyAsSelector<TRProxy, TR> columnSumDistinctAs(SQLColumn<TProxy,TProperty> column, SQLColumn<TRProxy,TProperty> aliasColumn) {
        return columnSumDistinctAs(column,aliasColumn.value());
    }

    default <TProxy extends ProxyEntity<TProxy,TEntity>,TEntity,TProperty extends Number> ProxyAsSelector<TRProxy, TR> columnSumDistinctAs(SQLColumn<TProxy,TProperty> column, String alias) {
        getAsSelector().columnSumDistinctAs(column.getTable(), column.value(), alias);
        return this;
    }

    default <TProxy extends ProxyEntity<TProxy,TEntity>,TEntity,TProperty extends Comparable<TProperty>> ProxyAsSelector<TRProxy, TR> columnMax(SQLColumn<TProxy,TProperty> column) {
        getAsSelector().columnMax(column.getTable(), column.value());
        return this;
    }

    default <TProxy extends ProxyEntity<TProxy,TEntity>,TEntity,TProperty extends Comparable<TProperty>>  ProxyAsSelector<TRProxy, TR> columnMaxAs(SQLColumn<TProxy,TProperty> column, SQLColumn<TRProxy,TProperty> aliasColumn) {
        return columnMaxAs(column,aliasColumn.value());
    }

    default <TProxy extends ProxyEntity<TProxy,TEntity>,TEntity,TProperty extends Comparable<TProperty>>  ProxyAsSelector<TRProxy, TR> columnMaxAs(SQLColumn<TProxy,TProperty> column, String alias) {
        getAsSelector().columnMaxAs(column.getTable(), column.value(), alias);
        return this;
    }

    default <TProxy extends ProxyEntity<TProxy,TEntity>,TEntity,TProperty extends Comparable<TProperty>> ProxyAsSelector<TRProxy, TR> columnMin(SQLColumn<TProxy,TProperty> column) {
        getAsSelector().columnMin(column.getTable(), column.value());
        return this;
    }

    default <TProxy extends ProxyEntity<TProxy,TEntity>,TEntity,TProperty extends Comparable<TProperty>> ProxyAsSelector<TRProxy, TR> columnMinAs(SQLColumn<TProxy,TProperty> column, SQLColumn<TRProxy,TProperty> aliasColumn) {
        return columnMinAs(column,aliasColumn.value());
    }

    default <TProxy extends ProxyEntity<TProxy,TEntity>,TEntity,TProperty extends Comparable<TProperty>> ProxyAsSelector<TRProxy, TR> columnMinAs(SQLColumn<TProxy,TProperty> column, String alias) {
        getAsSelector().columnMinAs(column.getTable(), column.value(), alias);
        return this;
    }

    default <TProxy extends ProxyEntity<TProxy,TEntity>,TEntity,TProperty extends Number> ProxyAsSelector<TRProxy, TR> columnAvg(SQLColumn<TProxy,TProperty> column) {
        getAsSelector().columnAvg(column.getTable(), column.value());
        return this;
    }

    default <TProxy extends ProxyEntity<TProxy,TEntity>,TEntity,TProperty extends Number> ProxyAsSelector<TRProxy, TR> columnAvgAs(SQLColumn<TProxy,TProperty> column, Function<TRProxy, SQLColumn<TProxy,TProperty>> mapAlias) {
        SQLColumn<TProxy,TProperty> sqlColumn = mapAlias.apply(tr());
        return columnAvgAs(column,sqlColumn.value());
    }

    default <TProxy extends ProxyEntity<TProxy,TEntity>,TEntity,TProperty extends Number> ProxyAsSelector<TRProxy, TR> columnAvgAs(SQLColumn<TProxy,TProperty> column, String alias) {
        getAsSelector().columnAvgAs(column.getTable(), column.value(), alias);
        return this;
    }

    default <TProxy extends ProxyEntity<TProxy,TEntity>,TEntity,TProperty extends Number> ProxyAsSelector<TRProxy, TR> columnAvgDistinct(SQLColumn<TProxy,TProperty> column) {
        getAsSelector().columnAvgDistinct(column.getTable(), column.value());
        return this;
    }

    default <TProxy extends ProxyEntity<TProxy,TEntity>,TEntity,TProperty extends Number> ProxyAsSelector<TRProxy, TR> columnAvgDistinctAs(SQLColumn<TProxy,TProperty> column, Function<TRProxy, SQLColumn<TProxy,TProperty>> mapAlias) {
        SQLColumn<TProxy,TProperty> sqlColumn = mapAlias.apply(tr());
        return columnAvgDistinctAs(column,sqlColumn.value());
    }

    default <TProxy extends ProxyEntity<TProxy,TEntity>,TEntity,TProperty extends Number> ProxyAsSelector<TRProxy, TR> columnAvgDistinctAs(SQLColumn<TProxy,TProperty> column, String alias) {
        getAsSelector().columnAvgDistinctAs(column.getTable(), column.value(),alias);
        return this;
    }

    default <TProxy extends ProxyEntity<TProxy,TEntity>,TEntity,TProperty> ProxyAsSelector<TRProxy, TR> columnLen(SQLColumn<TProxy,TProperty> column) {
        getAsSelector().columnLen(column.getTable(), column.value());
        return this;
    }

    default <TProxy extends ProxyEntity<TProxy,TEntity>,TEntity,TProperty> ProxyAsSelector<TRProxy, TR> columnLenAs(SQLColumn<TProxy,TProperty> column, Function<TRProxy, SQLColumn<TProxy,TProperty>> mapAlias) {
        SQLColumn<TProxy,TProperty> sqlColumn = mapAlias.apply(tr());
        return columnLenAs(column,sqlColumn.value());
    }

    default <TProxy extends ProxyEntity<TProxy,TEntity>,TEntity,TProperty> ProxyAsSelector<TRProxy, TR> columnLenAs(SQLColumn<TProxy,TProperty> column, String alias) {
        getAsSelector().columnLenAs(column.getTable(), column.value(),alias);
        return this;
    }

    default ProxyAsSelector<TRProxy, TR> columnFuncAs(ProxyColumnPropertyFunction proxyColumnPropertyFunction, SQLColumn<TRProxy,?> aliasColumn) {
        return columnFuncAs(proxyColumnPropertyFunction,aliasColumn.value());
    }

    default ProxyAsSelector<TRProxy, TR> columnFuncAs(ProxyColumnPropertyFunction proxyColumnPropertyFunction, String alias) {
        getAsSelector().columnFuncAs(proxyColumnPropertyFunction.getColumn().getTable(), proxyColumnPropertyFunction.getColumnPropertyFunction(),alias);
        return this;
    }
    default ProxyAsSelector<TRProxy,TR> sqlSegmentAs(CloneableSQLSegment sqlColumnSegment, SQLColumn<TRProxy,?> aliasColumn){
        getAsSelector().sqlSegmentAs(sqlColumnSegment, aliasColumn.value());
        return this;
    }
}
