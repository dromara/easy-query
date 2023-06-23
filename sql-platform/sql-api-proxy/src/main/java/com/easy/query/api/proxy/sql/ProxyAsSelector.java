package com.easy.query.api.proxy.sql;

import com.easy.query.api.proxy.core.ProxyQuery;
import com.easy.query.api.proxy.core.base.SQLColumn;
import com.easy.query.core.expression.builder.AsSelector;
import com.easy.query.core.expression.func.ColumnPropertyFunction;
import com.easy.query.core.expression.lambda.Property;

import java.util.function.Function;

/**
 * create time 2023/6/22 21:31
 * 文件说明
 *
 * @author xuejiaming
 */
public interface ProxyAsSelector<TRProxy extends ProxyQuery<TRProxy, TR>, TR> {

    AsSelector getAsSelector();

    default ProxyAsSelector<TRProxy, TR> columns(SQLColumn<?>... column) {
        getColumnAsSelector().column(EasyLambdaUtil.getPropertyName(column));
        return this;
    }

    default ProxyAsSelector<TRProxy, TR> column(SQLColumn<?> column) {
        getColumnAsSelector().column(EasyLambdaUtil.getPropertyName(column));
        return this;
    }

    default ProxyAsSelector<TRProxy, TR> columnConstAs(String columnConst, String alias) {
        getColumnAsSelector().columnConstAs(columnConst, alias);
        return this;
    }

    default ProxyAsSelector<TRProxy, TR> columnIgnore(SQLColumn<?> column) {
        getColumnAsSelector().columnIgnore(EasyLambdaUtil.getPropertyName(column));
        return this;
    }

    /**
     * 映射到TR的所有列上,按ColumnName进行映射,如果TR上没有对应的列名那么将不会映射查询列
     *
     * @return
     */
    default ProxyAsSelector<TRProxy, TR> columnAll() {
        getColumnAsSelector().columnAll();
        return this;
    }

    default ProxyAsSelector<TRProxy, TR> columnAs(SQLColumn<?> column, Function<TRProxy, SQLColumn<?>> mapAlias) {
        return columnAs(column, EasyLambdaUtil.getPropertyName(alias));
    }

    default ProxyAsSelector<TRProxy, TR> columnAs(Property<T1, ?> column, String alias) {
        getColumnAsSelector().columnAs(EasyLambdaUtil.getPropertyName(column), alias);
        return this;
    }

    default <TSubQuery> ProxyAsSelector<TRProxy, TR> columnSubQueryAs(Function<SQLWherePredicate<T1>, Queryable<TSubQuery>> subQueryableFunc, String alias) {
        getColumnAsSelector().columnSubQueryAs(wherePredicate -> {
            return subQueryableFunc.apply(new SQLWherePredicateImpl<>(wherePredicate));
        }, alias);
        return this;
    }

    default <TSubQuery> ProxyAsSelector<TRProxy, TR> columnSubQueryAs(Function<SQLWherePredicate<T1>, Queryable<TSubQuery>> subQueryableFunc, Property<TRProxy, TSubQuery> alias) {
        return columnSubQueryAs(subQueryableFunc, EasyLambdaUtil.getPropertyName(alias));
    }

    default ProxyAsSelector<TRProxy, TR> columnCount(Property<T1, ?> column) {
        getColumnAsSelector().columnCount(EasyLambdaUtil.getPropertyName(column));
        return this;
    }

    default ProxyAsSelector<TRProxy, TR> columnCountAs(Property<T1, ?> column, String alias) {
        getColumnAsSelector().columnCountAs(EasyLambdaUtil.getPropertyName(column), alias);
        return this;
    }

    default ProxyAsSelector<TRProxy, TR> columnCountAs(Property<T1, ?> column, Property<TRProxy, ?> alias) {
        return columnCountAs(column, EasyLambdaUtil.getPropertyName(alias));
    }

    default ProxyAsSelector<TRProxy, TR> columnCountDistinct(Property<T1, ?> column) {
        getColumnAsSelector().columnCountDistinct(EasyLambdaUtil.getPropertyName(column));
        return this;
    }

    default ProxyAsSelector<TRProxy, TR> columnCountDistinctAs(Property<T1, ?> column, String alias) {
        getColumnAsSelector().columnCountDistinctAs(EasyLambdaUtil.getPropertyName(column), alias);
        return this;
    }

    default ProxyAsSelector<TRProxy, TR> columnCountDistinctAs(Property<T1, ?> column, Property<TRProxy, ?> alias) {
        return columnCountDistinctAs(column, EasyLambdaUtil.getPropertyName(alias));
    }

    default ProxyAsSelector<TRProxy, TR> columnSum(Property<T1, Number> column) {
        getColumnAsSelector().columnSum(EasyLambdaUtil.getPropertyName(column));
        return this;
    }

    default ProxyAsSelector<TRProxy, TR> columnSumAs(Property<T1, Number> column, Property<TRProxy, Number> alias) {
        return columnSumAs(column, EasyLambdaUtil.getPropertyName(alias));
    }

    default ProxyAsSelector<TRProxy, TR> columnSumAs(Property<T1, Number> column, String alias) {
        getColumnAsSelector().columnSumAs(EasyLambdaUtil.getPropertyName(column), alias);
        return this;
    }

    default ProxyAsSelector<TRProxy, TR> columnSumDistinct(Property<T1, Number> column) {
        getColumnAsSelector().columnSumDistinct(EasyLambdaUtil.getPropertyName(column));
        return this;
    }

    default ProxyAsSelector<TRProxy, TR> columnSumDistinctAs(Property<T1, Number> column, Property<TRProxy, Number> alias) {
        return columnSumDistinctAs(column, EasyLambdaUtil.getPropertyName(alias));
    }

    default ProxyAsSelector<TRProxy, TR> columnSumDistinctAs(Property<T1, Number> column, String alias) {
        getColumnAsSelector().columnSumDistinctAs(EasyLambdaUtil.getPropertyName(column), alias);
        return this;
    }

    default ProxyAsSelector<TRProxy, TR> columnMax(Property<T1, ?> column) {
        getColumnAsSelector().columnMax(EasyLambdaUtil.getPropertyName(column));
        return this;
    }

    default ProxyAsSelector<TRProxy, TR> columnMaxAs(Property<T1, ?> column, Property<TRProxy, ?> alias) {
        return columnMaxAs(column, EasyLambdaUtil.getPropertyName(alias));
    }

    default ProxyAsSelector<TRProxy, TR> columnMaxAs(Property<T1, ?> column, String alias) {
        getColumnAsSelector().columnMaxAs(EasyLambdaUtil.getPropertyName(column), alias);
        return this;
    }

    default ProxyAsSelector<TRProxy, TR> columnMin(Property<T1, ?> column) {
        getColumnAsSelector().columnMin(EasyLambdaUtil.getPropertyName(column));
        return this;
    }

    default ProxyAsSelector<TRProxy, TR> columnMinAs(Property<T1, ?> column, Property<TRProxy, ?> alias) {
        return columnMinAs(column, EasyLambdaUtil.getPropertyName(alias));
    }

    default ProxyAsSelector<TRProxy, TR> columnMinAs(Property<T1, ?> column, String alias) {
        getColumnAsSelector().columnMinAs(EasyLambdaUtil.getPropertyName(column), alias);
        return this;
    }

    default ProxyAsSelector<TRProxy, TR> columnAvg(Property<T1, Number> column) {
        getColumnAsSelector().columnAvg(EasyLambdaUtil.getPropertyName(column));
        return this;
    }

    default ProxyAsSelector<TRProxy, TR> columnAvgAs(Property<T1, Number> column, Property<TRProxy, Number> alias) {
        return columnAvgAs(column, EasyLambdaUtil.getPropertyName(alias));
    }

    default ProxyAsSelector<TRProxy, TR> columnAvgAs(Property<T1, Number> column, String alias) {
        getColumnAsSelector().columnAvgAs(EasyLambdaUtil.getPropertyName(column), alias);
        return this;
    }

    default ProxyAsSelector<TRProxy, TR> columnAvgDistinct(Property<T1, Number> column) {
        getColumnAsSelector().columnAvgDistinct(EasyLambdaUtil.getPropertyName(column));
        return this;
    }

    default ProxyAsSelector<TRProxy, TR> columnAvgDistinctAs(Property<T1, Number> column, Property<TRProxy, Number> alias) {
        return columnAvgDistinctAs(column, EasyLambdaUtil.getPropertyName(alias));
    }

    default ProxyAsSelector<TRProxy, TR> columnAvgDistinctAs(Property<T1, Number> column, String alias) {
        getColumnAsSelector().columnAvgDistinctAs(EasyLambdaUtil.getPropertyName(column), alias);
        return this;
    }

    default ProxyAsSelector<TRProxy, TR> columnLen(Property<T1, ?> column) {
        getColumnAsSelector().columnLen(EasyLambdaUtil.getPropertyName(column));
        return this;
    }

    default ProxyAsSelector<TRProxy, TR> columnLenAs(Property<T1, ?> column, Property<TRProxy, ?> alias) {
        return columnLenAs(column, EasyLambdaUtil.getPropertyName(alias));
    }

    default ProxyAsSelector<TRProxy, TR> columnLenAs(Property<T1, ?> column, String alias) {
        getColumnAsSelector().columnLenAs(EasyLambdaUtil.getPropertyName(column), alias);
        return this;
    }

    default ProxyAsSelector<TRProxy, TR> columnFuncAs(ColumnPropertyFunction columnPropertyFunction, Property<TRProxy, ?> alias) {
        return columnFuncAs(columnPropertyFunction, EasyLambdaUtil.getPropertyName(alias));
    }

    default ProxyAsSelector<TRProxy, TR> columnFuncAs(ColumnPropertyFunction columnPropertyFunction, String alias) {
        getColumnAsSelector().columnFuncAs(columnPropertyFunction, alias);
        return this;
    }
}
