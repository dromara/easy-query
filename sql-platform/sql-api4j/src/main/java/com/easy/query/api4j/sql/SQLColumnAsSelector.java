package com.easy.query.api4j.sql;

import com.easy.query.api4j.select.Queryable;
import com.easy.query.api4j.sql.impl.SQLWherePredicateImpl;
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
        return columnAs(column, EasyLambdaUtil.getPropertyName(alias));
    }

    default SQLColumnAsSelector<T1, TR> columnAs(Property<T1, ?> column, String alias) {
        getColumnAsSelector().columnAs(EasyLambdaUtil.getPropertyName(column), alias);
        return this;
    }

    default <TSubQuery> SQLColumnAsSelector<T1, TR> columnSubQueryAs(Function<SQLWherePredicate<T1>, Queryable<TSubQuery>> subQueryableFunc, String alias) {
        getColumnAsSelector().columnSubQueryAs(wherePredicate -> {
            return subQueryableFunc.apply(new SQLWherePredicateImpl<>(wherePredicate));
        }, alias);
        return this;
    }

    default <TSubQuery> SQLColumnAsSelector<T1, TR> columnSubQueryAs(Function<SQLWherePredicate<T1>, Queryable<TSubQuery>> subQueryableFunc, Property<TR, TSubQuery> alias) {
        return columnSubQueryAs(subQueryableFunc, EasyLambdaUtil.getPropertyName(alias));
    }

    default SQLColumnAsSelector<T1, TR> columnCount(Property<T1, ?> column) {
        getColumnAsSelector().columnCount(EasyLambdaUtil.getPropertyName(column));
        return this;
    }

    default SQLColumnAsSelector<T1, TR> columnCountAs(Property<T1, ?> column, String alias) {
        getColumnAsSelector().columnCountAs(EasyLambdaUtil.getPropertyName(column), alias);
        return this;
    }

    default SQLColumnAsSelector<T1, TR> columnCountAs(Property<T1, ?> column, Property<TR, ?> alias) {
        return columnCountAs(column, EasyLambdaUtil.getPropertyName(alias));
    }

    default SQLColumnAsSelector<T1, TR> columnCountDistinct(Property<T1, ?> column) {
        getColumnAsSelector().columnCountDistinct(EasyLambdaUtil.getPropertyName(column));
        return this;
    }

    default SQLColumnAsSelector<T1, TR> columnCountDistinctAs(Property<T1, ?> column, String alias) {
        getColumnAsSelector().columnCountDistinctAs(EasyLambdaUtil.getPropertyName(column), alias);
        return this;
    }

    default SQLColumnAsSelector<T1, TR> columnCountDistinctAs(Property<T1, ?> column, Property<TR, ?> alias) {
        return columnCountDistinctAs(column, EasyLambdaUtil.getPropertyName(alias));
    }

    default SQLColumnAsSelector<T1, TR> columnSum(Property<T1, Number> column) {
        getColumnAsSelector().columnSum(EasyLambdaUtil.getPropertyName(column));
        return this;
    }

    default SQLColumnAsSelector<T1, TR> columnSumAs(Property<T1, Number> column, Property<TR, Number> alias) {
        return columnSumAs(column, EasyLambdaUtil.getPropertyName(alias));
    }

    default SQLColumnAsSelector<T1, TR> columnSumAs(Property<T1, Number> column, String alias) {
        getColumnAsSelector().columnSumAs(EasyLambdaUtil.getPropertyName(column), alias);
        return this;
    }

    default SQLColumnAsSelector<T1, TR> columnSumDistinct(Property<T1, Number> column) {
        getColumnAsSelector().columnSumDistinct(EasyLambdaUtil.getPropertyName(column));
        return this;
    }

    default SQLColumnAsSelector<T1, TR> columnSumDistinctAs(Property<T1, Number> column, Property<TR, Number> alias) {
        return columnSumDistinctAs(column, EasyLambdaUtil.getPropertyName(alias));
    }

    default SQLColumnAsSelector<T1, TR> columnSumDistinctAs(Property<T1, Number> column, String alias) {
        getColumnAsSelector().columnSumDistinctAs(EasyLambdaUtil.getPropertyName(column), alias);
        return this;
    }

    default SQLColumnAsSelector<T1, TR> columnMax(Property<T1, ?> column) {
        getColumnAsSelector().columnMax(EasyLambdaUtil.getPropertyName(column));
        return this;
    }

    default SQLColumnAsSelector<T1, TR> columnMaxAs(Property<T1, ?> column, Property<TR, ?> alias) {
        return columnMaxAs(column, EasyLambdaUtil.getPropertyName(alias));
    }

    default SQLColumnAsSelector<T1, TR> columnMaxAs(Property<T1, ?> column, String alias) {
        getColumnAsSelector().columnMaxAs(EasyLambdaUtil.getPropertyName(column), alias);
        return this;
    }

    default SQLColumnAsSelector<T1, TR> columnMin(Property<T1, ?> column) {
        getColumnAsSelector().columnMin(EasyLambdaUtil.getPropertyName(column));
        return this;
    }

    default SQLColumnAsSelector<T1, TR> columnMinAs(Property<T1, ?> column, Property<TR, ?> alias) {
        return columnMinAs(column, EasyLambdaUtil.getPropertyName(alias));
    }

    default SQLColumnAsSelector<T1, TR> columnMinAs(Property<T1, ?> column, String alias) {
        getColumnAsSelector().columnMinAs(EasyLambdaUtil.getPropertyName(column), alias);
        return this;
    }

    default SQLColumnAsSelector<T1, TR> columnAvg(Property<T1, Number> column) {
        getColumnAsSelector().columnAvg(EasyLambdaUtil.getPropertyName(column));
        return this;
    }

    default SQLColumnAsSelector<T1, TR> columnAvgAs(Property<T1, Number> column, Property<TR, Number> alias) {
        return columnAvgAs(column, EasyLambdaUtil.getPropertyName(alias));
    }

    default SQLColumnAsSelector<T1, TR> columnAvgAs(Property<T1, Number> column, String alias) {
        getColumnAsSelector().columnAvgAs(EasyLambdaUtil.getPropertyName(column), alias);
        return this;
    }

    default SQLColumnAsSelector<T1, TR> columnAvgDistinct(Property<T1, Number> column) {
        getColumnAsSelector().columnAvgDistinct(EasyLambdaUtil.getPropertyName(column));
        return this;
    }

    default SQLColumnAsSelector<T1, TR> columnAvgDistinctAs(Property<T1, Number> column, Property<TR, Number> alias) {
        return columnAvgDistinctAs(column, EasyLambdaUtil.getPropertyName(alias));
    }

    default SQLColumnAsSelector<T1, TR> columnAvgDistinctAs(Property<T1, Number> column, String alias) {
        getColumnAsSelector().columnAvgDistinctAs(EasyLambdaUtil.getPropertyName(column), alias);
        return this;
    }

    default SQLColumnAsSelector<T1, TR> columnLen(Property<T1, ?> column) {
        getColumnAsSelector().columnLen(EasyLambdaUtil.getPropertyName(column));
        return this;
    }

    default SQLColumnAsSelector<T1, TR> columnLenAs(Property<T1, ?> column, Property<TR, ?> alias) {
        return columnLenAs(column, EasyLambdaUtil.getPropertyName(alias));
    }

    default SQLColumnAsSelector<T1, TR> columnLenAs(Property<T1, ?> column, String alias) {
        getColumnAsSelector().columnLenAs(EasyLambdaUtil.getPropertyName(column), alias);
        return this;
    }

    default SQLColumnAsSelector<T1, TR> columnFuncAs(ColumnPropertyFunction columnPropertyFunction, Property<TR, ?> alias) {
        return columnFuncAs(columnPropertyFunction, EasyLambdaUtil.getPropertyName(alias));
    }

    default SQLColumnAsSelector<T1, TR> columnFuncAs(ColumnPropertyFunction columnPropertyFunction, String alias) {
        getColumnAsSelector().columnFuncAs(columnPropertyFunction, alias);
        return this;
    }

    default <T2> SQLColumnAsSelector<T2, TR> then(SQLColumnAsSelector<T2, TR> sub) {
        getColumnAsSelector().then(sub.getColumnAsSelector());
        return sub;
    }
}
