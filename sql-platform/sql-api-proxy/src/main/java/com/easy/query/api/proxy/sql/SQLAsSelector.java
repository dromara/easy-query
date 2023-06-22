package com.easy.query.api.proxy.sql;

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
public interface SQLAsSelector {

    AsSelector getAsSelector();

    default SQLAsSelector columns(SQLColumn<?>... column) {
        getColumnAsSelector().column(EasyLambdaUtil.getPropertyName(column));
        return this;
    }
    default SQLAsSelector column(SQLColumn<?> column) {
        getColumnAsSelector().column(EasyLambdaUtil.getPropertyName(column));
        return this;
    }

    default SQLAsSelector columnConstAs(String columnConst, String alias) {
        getColumnAsSelector().columnConstAs(columnConst, alias);
        return this;
    }

    default SQLAsSelector columnIgnore(SQLColumn<?> column) {
        getColumnAsSelector().columnIgnore(EasyLambdaUtil.getPropertyName(column));
        return this;
    }

    /**
     * 映射到TR的所有列上,按ColumnName进行映射,如果TR上没有对应的列名那么将不会映射查询列
     *
     * @return
     */
    default SQLAsSelector columnAll() {
        getColumnAsSelector().columnAll();
        return this;
    }

    default SQLAsSelector columnAs(SQLColumn<?> column, SQLColumn<?> alias) {
        return columnAs(column, EasyLambdaUtil.getPropertyName(alias));
    }

    default SQLAsSelector columnAs(Property<T1, ?> column, String alias) {
        getColumnAsSelector().columnAs(EasyLambdaUtil.getPropertyName(column), alias);
        return this;
    }

    default <TSubQuery> SQLAsSelector columnSubQueryAs(Function<SQLWherePredicate<T1>, Queryable<TSubQuery>> subQueryableFunc, String alias) {
        getColumnAsSelector().columnSubQueryAs(wherePredicate -> {
            return subQueryableFunc.apply(new SQLWherePredicateImpl<>(wherePredicate));
        }, alias);
        return this;
    }

    default <TSubQuery> SQLAsSelector columnSubQueryAs(Function<SQLWherePredicate<T1>, Queryable<TSubQuery>> subQueryableFunc, Property<TR, TSubQuery> alias) {
        return columnSubQueryAs(subQueryableFunc, EasyLambdaUtil.getPropertyName(alias));
    }

    default SQLAsSelector columnCount(Property<T1, ?> column) {
        getColumnAsSelector().columnCount(EasyLambdaUtil.getPropertyName(column));
        return this;
    }

    default SQLAsSelector columnCountAs(Property<T1, ?> column, String alias) {
        getColumnAsSelector().columnCountAs(EasyLambdaUtil.getPropertyName(column), alias);
        return this;
    }

    default SQLAsSelector columnCountAs(Property<T1, ?> column, Property<TR, ?> alias) {
        return columnCountAs(column, EasyLambdaUtil.getPropertyName(alias));
    }

    default SQLAsSelector columnCountDistinct(Property<T1, ?> column) {
        getColumnAsSelector().columnCountDistinct(EasyLambdaUtil.getPropertyName(column));
        return this;
    }

    default SQLAsSelector columnCountDistinctAs(Property<T1, ?> column, String alias) {
        getColumnAsSelector().columnCountDistinctAs(EasyLambdaUtil.getPropertyName(column), alias);
        return this;
    }

    default SQLAsSelector columnCountDistinctAs(Property<T1, ?> column, Property<TR, ?> alias) {
        return columnCountDistinctAs(column, EasyLambdaUtil.getPropertyName(alias));
    }

    default SQLAsSelector columnSum(Property<T1, Number> column) {
        getColumnAsSelector().columnSum(EasyLambdaUtil.getPropertyName(column));
        return this;
    }

    default SQLAsSelector columnSumAs(Property<T1, Number> column, Property<TR, Number> alias) {
        return columnSumAs(column, EasyLambdaUtil.getPropertyName(alias));
    }

    default SQLAsSelector columnSumAs(Property<T1, Number> column, String alias) {
        getColumnAsSelector().columnSumAs(EasyLambdaUtil.getPropertyName(column), alias);
        return this;
    }

    default SQLAsSelector columnSumDistinct(Property<T1, Number> column) {
        getColumnAsSelector().columnSumDistinct(EasyLambdaUtil.getPropertyName(column));
        return this;
    }

    default SQLAsSelector columnSumDistinctAs(Property<T1, Number> column, Property<TR, Number> alias) {
        return columnSumDistinctAs(column, EasyLambdaUtil.getPropertyName(alias));
    }

    default SQLAsSelector columnSumDistinctAs(Property<T1, Number> column, String alias) {
        getColumnAsSelector().columnSumDistinctAs(EasyLambdaUtil.getPropertyName(column), alias);
        return this;
    }

    default SQLAsSelector columnMax(Property<T1, ?> column) {
        getColumnAsSelector().columnMax(EasyLambdaUtil.getPropertyName(column));
        return this;
    }

    default SQLAsSelector columnMaxAs(Property<T1, ?> column, Property<TR, ?> alias) {
        return columnMaxAs(column, EasyLambdaUtil.getPropertyName(alias));
    }

    default SQLAsSelector columnMaxAs(Property<T1, ?> column, String alias) {
        getColumnAsSelector().columnMaxAs(EasyLambdaUtil.getPropertyName(column), alias);
        return this;
    }

    default SQLAsSelector columnMin(Property<T1, ?> column) {
        getColumnAsSelector().columnMin(EasyLambdaUtil.getPropertyName(column));
        return this;
    }

    default SQLAsSelector columnMinAs(Property<T1, ?> column, Property<TR, ?> alias) {
        return columnMinAs(column, EasyLambdaUtil.getPropertyName(alias));
    }

    default SQLAsSelector columnMinAs(Property<T1, ?> column, String alias) {
        getColumnAsSelector().columnMinAs(EasyLambdaUtil.getPropertyName(column), alias);
        return this;
    }

    default SQLAsSelector columnAvg(Property<T1, Number> column) {
        getColumnAsSelector().columnAvg(EasyLambdaUtil.getPropertyName(column));
        return this;
    }

    default SQLAsSelector columnAvgAs(Property<T1, Number> column, Property<TR, Number> alias) {
        return columnAvgAs(column, EasyLambdaUtil.getPropertyName(alias));
    }

    default SQLAsSelector columnAvgAs(Property<T1, Number> column, String alias) {
        getColumnAsSelector().columnAvgAs(EasyLambdaUtil.getPropertyName(column), alias);
        return this;
    }

    default SQLAsSelector columnAvgDistinct(Property<T1, Number> column) {
        getColumnAsSelector().columnAvgDistinct(EasyLambdaUtil.getPropertyName(column));
        return this;
    }

    default SQLAsSelector columnAvgDistinctAs(Property<T1, Number> column, Property<TR, Number> alias) {
        return columnAvgDistinctAs(column, EasyLambdaUtil.getPropertyName(alias));
    }

    default SQLAsSelector columnAvgDistinctAs(Property<T1, Number> column, String alias) {
        getColumnAsSelector().columnAvgDistinctAs(EasyLambdaUtil.getPropertyName(column), alias);
        return this;
    }

    default SQLAsSelector columnLen(Property<T1, ?> column) {
        getColumnAsSelector().columnLen(EasyLambdaUtil.getPropertyName(column));
        return this;
    }

    default SQLAsSelector columnLenAs(Property<T1, ?> column, Property<TR, ?> alias) {
        return columnLenAs(column, EasyLambdaUtil.getPropertyName(alias));
    }

    default SQLAsSelector columnLenAs(Property<T1, ?> column, String alias) {
        getColumnAsSelector().columnLenAs(EasyLambdaUtil.getPropertyName(column), alias);
        return this;
    }

    default SQLAsSelector columnFuncAs(ColumnPropertyFunction columnPropertyFunction, Property<TR, ?> alias) {
        return columnFuncAs(columnPropertyFunction, EasyLambdaUtil.getPropertyName(alias));
    }

    default SQLAsSelector columnFuncAs(ColumnPropertyFunction columnPropertyFunction, String alias) {
        getColumnAsSelector().columnFuncAs(columnPropertyFunction, alias);
        return this;
    }
}
