package com.easy.query.api4kt.sql;

import com.easy.query.api4kt.select.KtQueryable;
import com.easy.query.api4kt.sql.impl.SQLKtWherePredicateImpl;
import com.easy.query.api4kt.util.EasyKtLambdaUtil;
import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.expression.func.ColumnPropertyFunction;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.parser.core.base.ColumnAsSelector;
import kotlin.reflect.KProperty1;

import java.util.function.Function;

/**
 * @author xuejiaming
 * @Description: 文件说明
 * @Date: 2023/2/6 22:58
 */
public interface SQLKtColumnAsSelector<T1, TR> {
    ColumnAsSelector<T1, TR> getColumnAsSelector();

    default QueryRuntimeContext getRuntimeContext() {
        return getColumnAsSelector().getRuntimeContext();
    }

    default TableAvailable getTable() {
        return getColumnAsSelector().getTable();
    }

    default SQLKtColumnAsSelector<T1, TR> column(KProperty1<T1, ?> column) {
        getColumnAsSelector().column(EasyKtLambdaUtil.getPropertyName(column));
        return this;
    }
    default SQLKtColumnAsSelector<T1, TR> columnConstAs(String columnConst,String alias) {
        getColumnAsSelector().columnConstAs(columnConst,alias);
        return this;
    }

    default SQLKtColumnAsSelector<T1, TR> columnIgnore(KProperty1<T1, ?> column) {
        getColumnAsSelector().columnIgnore(EasyKtLambdaUtil.getPropertyName(column));
        return this;
    }

    /**
     * 映射到TR的所有列上,按ColumnName进行映射,如果TR上没有对应的列名那么将不会映射查询列
     *
     * @return
     */
    default SQLKtColumnAsSelector<T1, TR> columnAll() {
        getColumnAsSelector().columnAll();
        return this;
    }

    default SQLKtColumnAsSelector<T1, TR> columnAs(KProperty1<T1, ?> column, KProperty1<TR, ?> alias) {
        getColumnAsSelector().columnAs(EasyKtLambdaUtil.getPropertyName(column), EasyKtLambdaUtil.getPropertyName(alias));
        return this;
    }

    default <TSubQuery> SQLKtColumnAsSelector<T1, TR> columnSubQueryAs(Function<SQLKtWherePredicate<T1>, KtQueryable<TSubQuery>> subQueryableFunc, KProperty1<TR, TSubQuery> alias) {
        return columnSubQueryAs(subQueryableFunc, EasyKtLambdaUtil.getPropertyName(alias));
    }

    default <TSubQuery> SQLKtColumnAsSelector<T1, TR> columnSubQueryAs(Function<SQLKtWherePredicate<T1>, KtQueryable<TSubQuery>> subQueryableFunc, String alias) {
        getColumnAsSelector().columnSubQueryAs(wherePredicate -> {
            return subQueryableFunc.apply(new SQLKtWherePredicateImpl<>(wherePredicate));
        }, alias);
        return this;
    }

    default SQLKtColumnAsSelector<T1, TR> columnCount(KProperty1<T1, ?> column) {
        getColumnAsSelector().columnCount(EasyKtLambdaUtil.getPropertyName(column));
        return this;
    }

    default SQLKtColumnAsSelector<T1, TR> columnCountAs(KProperty1<T1, ?> column, KProperty1<TR, ?> alias) {
        return columnCountAs(column, EasyKtLambdaUtil.getPropertyName(alias));
    }

    default SQLKtColumnAsSelector<T1, TR> columnCountAs(KProperty1<T1, ?> column, String alias) {
        getColumnAsSelector().columnCountAs(EasyKtLambdaUtil.getPropertyName(column), alias);
        return this;
    }

    default SQLKtColumnAsSelector<T1, TR> columnCountDistinct(KProperty1<T1, ?> column) {
        getColumnAsSelector().columnCountDistinct(EasyKtLambdaUtil.getPropertyName(column));
        return this;
    }

    default SQLKtColumnAsSelector<T1, TR> columnCountDistinctAs(KProperty1<T1, ?> column, KProperty1<TR, ?> alias) {
        return columnCountDistinctAs(column, EasyKtLambdaUtil.getPropertyName(alias));
    }

    default SQLKtColumnAsSelector<T1, TR> columnCountDistinctAs(KProperty1<T1, ?> column, String alias) {
        getColumnAsSelector().columnCountDistinctAs(EasyKtLambdaUtil.getPropertyName(column), alias);
        return this;
    }

    default SQLKtColumnAsSelector<T1, TR> columnSum(KProperty1<T1, Number> column) {
        getColumnAsSelector().columnSum(EasyKtLambdaUtil.getPropertyName(column));
        return this;
    }

    default SQLKtColumnAsSelector<T1, TR> columnSumAs(KProperty1<T1, Number> column, KProperty1<TR, Number> alias) {
        return columnSumAs(column, EasyKtLambdaUtil.getPropertyName(alias));
    }

    default SQLKtColumnAsSelector<T1, TR> columnSumAs(KProperty1<T1, Number> column, String alias) {
        getColumnAsSelector().columnSumAs(EasyKtLambdaUtil.getPropertyName(column), alias);
        return this;
    }

    default SQLKtColumnAsSelector<T1, TR> columnSumDistinct(KProperty1<T1, Number> column) {
        getColumnAsSelector().columnSumDistinct(EasyKtLambdaUtil.getPropertyName(column));
        return this;
    }

    default SQLKtColumnAsSelector<T1, TR> columnSumDistinctAs(KProperty1<T1, Number> column, KProperty1<TR, Number> alias) {
        return columnSumDistinctAs(column, EasyKtLambdaUtil.getPropertyName(alias));
    }

    default SQLKtColumnAsSelector<T1, TR> columnSumDistinctAs(KProperty1<T1, Number> column, String alias) {
        getColumnAsSelector().columnSumDistinctAs(EasyKtLambdaUtil.getPropertyName(column), alias);
        return this;
    }

    default SQLKtColumnAsSelector<T1, TR> columnMax(KProperty1<T1, ?> column) {
        getColumnAsSelector().columnMax(EasyKtLambdaUtil.getPropertyName(column));
        return this;
    }

    default SQLKtColumnAsSelector<T1, TR> columnMaxAs(KProperty1<T1, ?> column, KProperty1<TR, ?> alias) {
        return columnMaxAs(column, EasyKtLambdaUtil.getPropertyName(alias));
    }

    default SQLKtColumnAsSelector<T1, TR> columnMaxAs(KProperty1<T1, ?> column, String alias) {
        getColumnAsSelector().columnMaxAs(EasyKtLambdaUtil.getPropertyName(column), alias);
        return this;
    }

    default SQLKtColumnAsSelector<T1, TR> columnMin(KProperty1<T1, ?> column) {
        getColumnAsSelector().columnMin(EasyKtLambdaUtil.getPropertyName(column));
        return this;
    }

    default SQLKtColumnAsSelector<T1, TR> columnMinAs(KProperty1<T1, ?> column, KProperty1<TR, ?> alias) {
        return columnMinAs(column, EasyKtLambdaUtil.getPropertyName(alias));
    }

    default SQLKtColumnAsSelector<T1, TR> columnMinAs(KProperty1<T1, ?> column, String alias) {
        getColumnAsSelector().columnMinAs(EasyKtLambdaUtil.getPropertyName(column), alias);
        return this;
    }

    default SQLKtColumnAsSelector<T1, TR> columnAvg(KProperty1<T1, Number> column) {
        getColumnAsSelector().columnAvg(EasyKtLambdaUtil.getPropertyName(column));
        return this;
    }

    default SQLKtColumnAsSelector<T1, TR> columnAvgAs(KProperty1<T1, Number> column, KProperty1<TR, Number> alias) {
        return columnAvgAs(column, EasyKtLambdaUtil.getPropertyName(alias));
    }

    default SQLKtColumnAsSelector<T1, TR> columnAvgAs(KProperty1<T1, Number> column, String alias) {
        getColumnAsSelector().columnAvgAs(EasyKtLambdaUtil.getPropertyName(column), alias);
        return this;
    }

    default SQLKtColumnAsSelector<T1, TR> columnAvgDistinct(KProperty1<T1, Number> column) {
        getColumnAsSelector().columnAvgDistinct(EasyKtLambdaUtil.getPropertyName(column));
        return this;
    }

    default SQLKtColumnAsSelector<T1, TR> columnAvgDistinctAs(KProperty1<T1, Number> column, KProperty1<TR, Number> alias) {
        return columnAvgDistinctAs(column, EasyKtLambdaUtil.getPropertyName(alias));
    }

    default SQLKtColumnAsSelector<T1, TR> columnAvgDistinctAs(KProperty1<T1, Number> column, String alias) {
        getColumnAsSelector().columnAvgDistinctAs(EasyKtLambdaUtil.getPropertyName(column), alias);
        return this;
    }

    default SQLKtColumnAsSelector<T1, TR> columnLen(KProperty1<T1, ?> column) {
        getColumnAsSelector().columnLen(EasyKtLambdaUtil.getPropertyName(column));
        return this;
    }

    default SQLKtColumnAsSelector<T1, TR> columnLenAs(KProperty1<T1, ?> column, KProperty1<TR, ?> alias) {
        return columnLenAs(column, EasyKtLambdaUtil.getPropertyName(alias));
    }

    default SQLKtColumnAsSelector<T1, TR> columnLenAs(KProperty1<T1, ?> column, String alias) {
        getColumnAsSelector().columnLenAs(EasyKtLambdaUtil.getPropertyName(column), alias);
        return this;
    }

    default SQLKtColumnAsSelector<T1, TR> columnFuncAs(ColumnPropertyFunction columnPropertyFunction, KProperty1<TR, ?> alias) {
        return columnFuncAs(columnPropertyFunction, EasyKtLambdaUtil.getPropertyName(alias));
    }

    default SQLKtColumnAsSelector<T1, TR> columnFuncAs(ColumnPropertyFunction columnPropertyFunction, String alias) {
        getColumnAsSelector().columnFuncAs(columnPropertyFunction, alias);
        return this;
    }

    default <T2> SQLKtColumnAsSelector<T2, TR> then(SQLKtColumnAsSelector<T2, TR> sub) {
        getColumnAsSelector().then(sub.getColumnAsSelector());
        return sub;
    }
}
