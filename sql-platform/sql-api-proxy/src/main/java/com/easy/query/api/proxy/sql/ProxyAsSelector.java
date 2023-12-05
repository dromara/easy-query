package com.easy.query.api.proxy.sql;

import com.easy.query.api.proxy.select.ProxyQueryable;
import com.easy.query.api.proxy.sql.core.SQLAsProxyNative;
import com.easy.query.api.proxy.sql.core.available.ProxySQLFuncAvailable;
import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.expression.builder.AsSelector;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.lambda.SQLFuncExpression;
import com.easy.query.core.expression.segment.CloneableSQLSegment;
import com.easy.query.core.expression.sql.builder.ExpressionContext;
import com.easy.query.core.func.ACSSelector;
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
                getAsSelector().column(sqlColumn.getTable(), sqlColumn.getValue());
            }
        }
        return this;
    }
    default <TProxy extends ProxyEntity<TProxy,T>,T,TProperty> ProxyAsSelector<TRProxy, TR> column(SQLColumn<TProxy,TProperty> column) {
        getAsSelector().column(column.getTable(), column.getValue());
        return this;
    }

    default ProxyAsSelector<TRProxy, TR> groupKeys(int index) {
        getAsSelector().groupKeys(index);
        return this;
    }
    default <TProperty> ProxyAsSelector<TRProxy, TR> groupKeysAs(int index, SQLColumn<TRProxy,TProperty> aliasColumn) {
        getAsSelector().groupKeysAs(index,aliasColumn.getValue());
        return this;
    }
    default ProxyAsSelector<TRProxy, TR> groupKeysAs(int index, String alias) {
        getAsSelector().groupKeysAs(index,alias);
        return this;
    }

    default <TProxy extends ProxyEntity<TProxy,T>,T,TProperty> ProxyAsSelector<TRProxy, TR> columnIgnore(SQLColumn<TProxy,TProperty> column) {
        getAsSelector().columnIgnore(column.getTable(), column.getValue());
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
        getAsSelector().columnAs(column.getTable(), column.getValue(), aliasColumn.getValue());
        return this;
    }

    default <TSubQueryProxy extends ProxyEntity<TSubQueryProxy, TSubQuery>, TSubQuery> ProxyAsSelector<TRProxy, TR> columnSubQueryAs(SQLFuncExpression<ProxyQueryable<TSubQueryProxy, TSubQuery>> subQueryableFunc, SQLColumn<TRProxy,TSubQuery> aliasColumn) {
        return columnSubQueryAs(subQueryableFunc, aliasColumn.getValue());
    }

    default <TSubQueryProxy extends ProxyEntity<TSubQueryProxy, TSubQuery>, TSubQuery> ProxyAsSelector<TRProxy, TR> columnSubQueryAs(SQLFuncExpression<ProxyQueryable<TSubQueryProxy, TSubQuery>> subQueryableFunc, String alias) {
        getAsSelector().columnSubQueryAs(subQueryableFunc::apply, alias);
        return this;
    }


    default  <TProxy extends ProxyEntity<TProxy,TEntity>,TEntity,TProperty> ProxyAsSelector<TRProxy, TR> columnCount(SQLColumn<TProxy,TProperty> column) {
        getAsSelector().columnCount(column.getTable(), column.getValue());
        return this;
    }

    default  <TProxy extends ProxyEntity<TProxy,TEntity>,TEntity,TProperty> ProxyAsSelector<TRProxy, TR> columnCount(SQLColumn<TProxy,TProperty> column, SQLExpression1<ACSSelector> sqlExpression) {
        getAsSelector().columnCount(column.getTable(), column.getValue(),sqlExpression);
        return this;
    }

    default <TProxy extends ProxyEntity<TProxy,TEntity>,TEntity,TProperty> ProxyAsSelector<TRProxy, TR> columnCountAs(SQLColumn<TProxy,TProperty> column, SQLColumn<TRProxy,TProperty> aliasColumn) {
        getAsSelector().columnCountAs(column.getTable(), column.getValue(), aliasColumn.getValue());
        return this;
    }
    default <TProxy extends ProxyEntity<TProxy,TEntity>,TEntity,TProperty> ProxyAsSelector<TRProxy, TR> columnCountAs(SQLColumn<TProxy,TProperty> column, SQLColumn<TRProxy,TProperty> aliasColumn, SQLExpression1<ACSSelector> sqlExpression) {
        getAsSelector().columnCountAs(column.getTable(), column.getValue(), aliasColumn.getValue(),sqlExpression);
        return this;
    }

    default <TProxy extends ProxyEntity<TProxy,TEntity>,TEntity,TProperty extends Number> ProxyAsSelector<TRProxy, TR> columnSum(SQLColumn<TProxy,TProperty> column) {
        getAsSelector().columnSum(column.getTable(), column.getValue());
        return this;
    }
    default <TProxy extends ProxyEntity<TProxy,TEntity>,TEntity,TProperty extends Number> ProxyAsSelector<TRProxy, TR> columnSum(SQLColumn<TProxy,TProperty> column, SQLExpression1<ACSSelector> sqlExpression) {
        getAsSelector().columnSum(column.getTable(), column.getValue(),sqlExpression);
        return this;
    }

    default <TProxy extends ProxyEntity<TProxy,TEntity>,TEntity,TProperty extends Number> ProxyAsSelector<TRProxy, TR> columnSumAs(SQLColumn<TProxy,TProperty> column, SQLColumn<TRProxy,TProperty> aliasColumn) {
        getAsSelector().columnSumAs(column.getTable(), column.getValue(),aliasColumn.getValue());
        return this;
    }
    default <TProxy extends ProxyEntity<TProxy,TEntity>,TEntity,TProperty extends Number> ProxyAsSelector<TRProxy, TR> columnSumAs(SQLColumn<TProxy,TProperty> column, SQLColumn<TRProxy,TProperty> aliasColumn, SQLExpression1<ACSSelector> sqlExpression) {
        getAsSelector().columnSumAs(column.getTable(), column.getValue(),aliasColumn.getValue(),sqlExpression);
        return this;
    }

    default <TProxy extends ProxyEntity<TProxy,TEntity>,TEntity,TProperty extends Comparable<TProperty>> ProxyAsSelector<TRProxy, TR> columnMax(SQLColumn<TProxy,TProperty> column) {
        getAsSelector().columnMax(column.getTable(), column.getValue());
        return this;
    }

    default <TProxy extends ProxyEntity<TProxy,TEntity>,TEntity,TProperty extends Comparable<TProperty>>  ProxyAsSelector<TRProxy, TR> columnMaxAs(SQLColumn<TProxy,TProperty> column, SQLColumn<TRProxy,TProperty> aliasColumn) {
        getAsSelector().columnMaxAs(column.getTable(), column.getValue(), aliasColumn.getValue());
        return this;
    }

    default <TProxy extends ProxyEntity<TProxy,TEntity>,TEntity,TProperty extends Comparable<TProperty>> ProxyAsSelector<TRProxy, TR> columnMin(SQLColumn<TProxy,TProperty> column) {
        getAsSelector().columnMin(column.getTable(), column.getValue());
        return this;
    }

    default <TProxy extends ProxyEntity<TProxy,TEntity>,TEntity,TProperty extends Comparable<TProperty>> ProxyAsSelector<TRProxy, TR> columnMinAs(SQLColumn<TProxy,TProperty> column, SQLColumn<TRProxy,TProperty> aliasColumn) {
        getAsSelector().columnMinAs(column.getTable(), column.getValue(),aliasColumn.getValue());
        return this;
    }

    default <TProxy extends ProxyEntity<TProxy,TEntity>,TEntity,TProperty extends Number> ProxyAsSelector<TRProxy, TR> columnAvg(SQLColumn<TProxy,TProperty> column) {
        getAsSelector().columnAvg(column.getTable(), column.getValue());
        return this;
    }

    default <TProxy extends ProxyEntity<TProxy,TEntity>,TEntity,TProperty extends Number> ProxyAsSelector<TRProxy, TR> columnAvg(SQLColumn<TProxy,TProperty> column,SQLExpression1<ACSSelector> sqlExpression) {
        getAsSelector().columnAvg(column.getTable(), column.getValue(),sqlExpression);
        return this;
    }

    default <TProxy extends ProxyEntity<TProxy,TEntity>,TEntity,TProperty extends Number> ProxyAsSelector<TRProxy, TR> columnAvgAs(SQLColumn<TProxy,TProperty> column, SQLColumn<TRProxy,TProperty> aliasColumn) {
        getAsSelector().columnAvgAs(column.getTable(), column.getValue(),aliasColumn.getValue());
        return this;
    }
    default <TProxy extends ProxyEntity<TProxy,TEntity>,TEntity,TProperty extends Number> ProxyAsSelector<TRProxy, TR> columnAvgAs(SQLColumn<TProxy,TProperty> column, SQLColumn<TRProxy,TProperty> aliasColumn,SQLExpression1<ACSSelector> sqlExpression) {
        getAsSelector().columnAvgAs(column.getTable(), column.getValue(),aliasColumn.getValue(),sqlExpression);
        return this;
    }

    default <TProxy extends ProxyEntity<TProxy,TEntity>,TEntity,TProperty> ProxyAsSelector<TRProxy, TR> columnLen(SQLColumn<TProxy,TProperty> column) {
        getAsSelector().columnLen(column.getTable(), column.getValue());
        return this;
    }

    default <TProxy extends ProxyEntity<TProxy,TEntity>,TEntity,TProperty> ProxyAsSelector<TRProxy, TR> columnLenAs(SQLColumn<TProxy,TProperty> column, Function<TRProxy, SQLColumn<TProxy,TProperty>> mapAlias) {
        SQLColumn<TProxy,TProperty> sqlColumn = mapAlias.apply(tr());
        return columnLenAs(column,sqlColumn.getValue());
    }

    default <TProxy extends ProxyEntity<TProxy,TEntity>,TEntity,TProperty> ProxyAsSelector<TRProxy, TR> columnLenAs(SQLColumn<TProxy,TProperty> column, String alias) {
        getAsSelector().columnLenAs(column.getTable(), column.getValue(),alias);
        return this;
    }

    default ProxyAsSelector<TRProxy, TR> columnFuncAs(ProxyColumnPropertyFunction proxyColumnPropertyFunction, SQLColumn<TRProxy,?> aliasColumn) {
        return columnFuncAs(proxyColumnPropertyFunction,aliasColumn.getValue());
    }

    default ProxyAsSelector<TRProxy, TR> columnFuncAs(ProxyColumnPropertyFunction proxyColumnPropertyFunction, String alias) {
        getAsSelector().columnFuncAs(proxyColumnPropertyFunction.getColumn().getTable(), proxyColumnPropertyFunction.getColumnPropertyFunction(),alias);
        return this;
    }
    default ProxyAsSelector<TRProxy,TR> sqlSegmentAs(CloneableSQLSegment sqlColumnSegment, SQLColumn<TRProxy,?> aliasColumn){
        getAsSelector().sqlSegmentAs(sqlColumnSegment, aliasColumn.getValue());
        return this;
    }
}
