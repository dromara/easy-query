package com.easy.query.api4j.sql;

import com.easy.query.api4j.select.Queryable;
import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.expression.func.ColumnPropertyFunction;
import com.easy.query.core.expression.lambda.Property;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.parser.core.base.ColumnAsSelector;
import com.easy.query.core.util.EasyLambdaUtil;

import java.util.function.Function;

/**
 * @author xuejiaming
 * @Description: 文件说明
 * @Date: 2023/2/6 22:58
 */
public interface SQLColumnAsSelector<T1, TR> {
    ColumnAsSelector<T1, TR> getColumnAsSelector();

    default QueryRuntimeContext getRuntimeContext() {
        return getColumnAsSelector().getRuntimeContext();
    }

    default TableAvailable getTable() {
        return getColumnAsSelector().getTable();
    }

    default SQLColumnAsSelector<T1, TR> column(Property<T1, ?> column) {
        getColumnAsSelector().column(EasyLambdaUtil.getPropertyName(column));
        return this;
    }

    default SQLColumnAsSelector<T1, TR> columnIgnore(Property<T1, ?> column) {
        getColumnAsSelector().columnIgnore(EasyLambdaUtil.getPropertyName(column));
        return this;
    }

    /**
     * 映射到TR的所有列上,按ColumnName进行映射,如果TR上没有对应的列名那么将不会映射查询列
     *
     * @return
     */
    default SQLColumnAsSelector<T1, TR> columnAll() {
        getColumnAsSelector().columnAll();
        return this;
    }

    default SQLColumnAsSelector<T1, TR> columnAs(Property<T1, ?> column, Property<TR, ?> alias) {
        getColumnAsSelector().columnAs(EasyLambdaUtil.getPropertyName(column), EasyLambdaUtil.getPropertyName(alias));
        return this;
    }

    <TSubQuery> SQLColumnAsSelector<T1, TR> columnSubQueryAs(Function<SQLWherePredicate<T1>, Queryable<TSubQuery>> subQueryableFunc, Property<TR, TSubQuery> alias);

    default SQLColumnAsSelector<T1, TR> columnCount(Property<T1, ?> column) {
        return columnCountAs(column, null);
    }

    default SQLColumnAsSelector<T1, TR> columnCountAs(Property<T1, ?> column, Property<TR, ?> alias) {
        getColumnAsSelector().columnCountAs(EasyLambdaUtil.getPropertyName(column), EasyLambdaUtil.getPropertyName(alias));
        return this;
    }

    default SQLColumnAsSelector<T1, TR> columnCountDistinct(Property<T1, ?> column) {
        getColumnAsSelector().columnCountDistinct(EasyLambdaUtil.getPropertyName(column));
        return this;
    }

    default SQLColumnAsSelector<T1, TR> columnCountDistinctAs(Property<T1, ?> column, Property<TR, ?> alias) {
        getColumnAsSelector().columnCountDistinctAs(EasyLambdaUtil.getPropertyName(column), EasyLambdaUtil.getPropertyName(alias));
        return this;
    }

    default SQLColumnAsSelector<T1, TR> columnSum(Property<T1, Number> column) {
        return columnSumAs(column, null);
    }

    default SQLColumnAsSelector<T1, TR> columnSumAs(Property<T1, Number> column, Property<TR, Number> alias) {
        getColumnAsSelector().columnSumAs(EasyLambdaUtil.getPropertyName(column), EasyLambdaUtil.getPropertyName(alias));
        return this;
    }

    default SQLColumnAsSelector<T1, TR> columnSumDistinct(Property<T1, Number> column) {
        return columnSumDistinctAs(column, null);
    }

    default SQLColumnAsSelector<T1, TR> columnSumDistinctAs(Property<T1, Number> column, Property<TR, Number> alias) {
        getColumnAsSelector().columnSumDistinctAs(EasyLambdaUtil.getPropertyName(column), EasyLambdaUtil.getPropertyName(alias));
        return this;
    }

    default SQLColumnAsSelector<T1, TR> columnMax(Property<T1, ?> column) {
        return columnMaxAs(column, null);
    }

    default SQLColumnAsSelector<T1, TR> columnMaxAs(Property<T1, ?> column, Property<TR, ?> alias) {
        getColumnAsSelector().columnMaxAs(EasyLambdaUtil.getPropertyName(column), EasyLambdaUtil.getPropertyName(alias));
        return this;
    }

    default SQLColumnAsSelector<T1, TR> columnMin(Property<T1, ?> column) {
        return columnMinAs(column, null);
    }

    default SQLColumnAsSelector<T1, TR> columnMinAs(Property<T1, ?> column, Property<TR, ?> alias) {
        getColumnAsSelector().columnMinAs(EasyLambdaUtil.getPropertyName(column), EasyLambdaUtil.getPropertyName(alias));
        return this;
    }

    default SQLColumnAsSelector<T1, TR> columnAvg(Property<T1, Number> column) {
        return columnAvgAs(column, null);
    }

    default SQLColumnAsSelector<T1, TR> columnAvgAs(Property<T1, Number> column, Property<TR, Number> alias) {
        getColumnAsSelector().columnAvgAs(EasyLambdaUtil.getPropertyName(column), EasyLambdaUtil.getPropertyName(alias));
        return this;
    }

    default SQLColumnAsSelector<T1, TR> columnAvgDistinct(Property<T1, Number> column) {
        return columnAvgDistinctAs(column, null);
    }

    default SQLColumnAsSelector<T1, TR> columnAvgDistinctAs(Property<T1, Number> column, Property<TR, Number> alias) {
        getColumnAsSelector().columnAvgDistinctAs(EasyLambdaUtil.getPropertyName(column), EasyLambdaUtil.getPropertyName(alias));
        return this;
    }

    default SQLColumnAsSelector<T1, TR> columnLen(Property<T1, ?> column) {
        return columnLenAs(column, null);
    }

    default SQLColumnAsSelector<T1, TR> columnLenAs(Property<T1, ?> column, Property<TR, ?> alias) {
        getColumnAsSelector().columnLenAs(EasyLambdaUtil.getPropertyName(column), EasyLambdaUtil.getPropertyName(alias));
        return this;
    }

    default SQLColumnAsSelector<T1, TR> columnFuncAs(ColumnPropertyFunction columnPropertyFunction, Property<TR, ?> alias) {
        getColumnAsSelector().columnFuncAs(columnPropertyFunction, EasyLambdaUtil.getPropertyName(alias));
        return this;
    }

    default <T2> SQLColumnAsSelector<T2, TR> then(SQLColumnAsSelector<T2, TR> sub) {
        getColumnAsSelector().then(sub.getColumnAsSelector());
        return sub;
    }
}
