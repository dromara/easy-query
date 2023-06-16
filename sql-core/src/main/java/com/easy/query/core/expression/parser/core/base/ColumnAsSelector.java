package com.easy.query.core.expression.parser.core.base;

import com.easy.query.core.basic.api.select.Query;
import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.expression.func.ColumnFunction;
import com.easy.query.core.expression.func.ColumnPropertyFunction;
import com.easy.query.core.expression.func.DefaultColumnPropertyFunction;
import com.easy.query.core.expression.parser.core.available.TableAvailable;

import java.util.function.Function;

/**
 * @author xuejiaming
 * @Description: 文件说明
 * @Date: 2023/2/6 22:58
 */
public interface ColumnAsSelector<T1, TR> {
    QueryRuntimeContext getRuntimeContext();

    TableAvailable getTable();

    ColumnAsSelector<T1, TR> column(String property);
    ColumnAsSelector<T1, TR> columnConst(String columnConst,String alias);

    ColumnAsSelector<T1, TR> columnIgnore(String property);

    /**
     * 映射到TR的所有列上,按ColumnName进行映射,如果TR上没有对应的列名那么将不会映射查询列
     *
     * @return
     */
    ColumnAsSelector<T1, TR> columnAll();

    ColumnAsSelector<T1, TR> columnAs(String property, String propertyAlias);

    <TSubQuery> ColumnAsSelector<T1, TR> columnSubQueryAs(Function<WherePredicate<T1>, Query<TSubQuery>> subQueryableFunc, String propertyAlias);

    default ColumnAsSelector<T1, TR> columnCount(String property) {
        return columnCountAs(property, null);
    }

    default ColumnAsSelector<T1, TR> columnCountAs(String property, String propertyAlias) {
        ColumnFunction countFunction = getRuntimeContext().getColumnFunctionFactory().createCountFunction(false);
        ColumnPropertyFunction columnPropertyFunction = DefaultColumnPropertyFunction.createDefault(property, countFunction);
        return columnFuncAs(columnPropertyFunction, propertyAlias);
    }

    default ColumnAsSelector<T1, TR> columnCountDistinct(String property) {
        return columnCountDistinctAs(property, null);
    }

    default ColumnAsSelector<T1, TR> columnCountDistinctAs(String property, String propertyAlias) {
        ColumnFunction countFunction = getRuntimeContext().getColumnFunctionFactory().createCountFunction(true);
        ColumnPropertyFunction columnPropertyFunction = DefaultColumnPropertyFunction.createDefault(property, countFunction);
        return columnFuncAs(columnPropertyFunction, propertyAlias);
    }

    default ColumnAsSelector<T1, TR> columnSum(String property) {
        return columnSumAs(property, null);
    }

    default ColumnAsSelector<T1, TR> columnSumAs(String property, String propertyAlias) {
        ColumnFunction countFunction = getRuntimeContext().getColumnFunctionFactory().createSumFunction(false);
        ColumnPropertyFunction columnPropertyFunction = DefaultColumnPropertyFunction.createDefault(property, countFunction);
        return columnFuncAs(columnPropertyFunction, propertyAlias);
    }

    default ColumnAsSelector<T1, TR> columnSumDistinct(String property) {
        return columnSumDistinctAs(property, null);
    }

    default ColumnAsSelector<T1, TR> columnSumDistinctAs(String property, String propertyAlias) {
        ColumnFunction countFunction = getRuntimeContext().getColumnFunctionFactory().createSumFunction(true);
        ColumnPropertyFunction columnPropertyFunction = DefaultColumnPropertyFunction.createDefault(property, countFunction);
        return columnFuncAs(columnPropertyFunction, propertyAlias);
    }

    default ColumnAsSelector<T1, TR> columnMax(String property) {
        return columnMaxAs(property, null);
    }

    default ColumnAsSelector<T1, TR> columnMaxAs(String property, String propertyAlias) {
        ColumnFunction countFunction = getRuntimeContext().getColumnFunctionFactory().createMaxFunction();
        ColumnPropertyFunction columnPropertyFunction = DefaultColumnPropertyFunction.createDefault(property, countFunction);
        return columnFuncAs(columnPropertyFunction, propertyAlias);
    }

    default ColumnAsSelector<T1, TR> columnMin(String property) {
        return columnMinAs(property, null);
    }

    default ColumnAsSelector<T1, TR> columnMinAs(String property, String propertyAlias) {
        ColumnFunction countFunction = getRuntimeContext().getColumnFunctionFactory().createMinFunction();
        ColumnPropertyFunction columnPropertyFunction = DefaultColumnPropertyFunction.createDefault(property, countFunction);
        return columnFuncAs(columnPropertyFunction, propertyAlias);
    }

    default ColumnAsSelector<T1, TR> columnAvg(String property) {
        return columnAvgAs(property, null);
    }

    default ColumnAsSelector<T1, TR> columnAvgAs(String property, String propertyAlias) {
        ColumnFunction countFunction = getRuntimeContext().getColumnFunctionFactory().createAvgFunction(false);
        ColumnPropertyFunction columnPropertyFunction = DefaultColumnPropertyFunction.createDefault(property, countFunction);
        return columnFuncAs(columnPropertyFunction, propertyAlias);
    }

    default ColumnAsSelector<T1, TR> columnAvgDistinct(String property) {
        return columnAvgDistinctAs(property, null);
    }

    default ColumnAsSelector<T1, TR> columnAvgDistinctAs(String property, String propertyAlias) {
        ColumnFunction countFunction = getRuntimeContext().getColumnFunctionFactory().createAvgFunction(true);
        ColumnPropertyFunction columnPropertyFunction = DefaultColumnPropertyFunction.createDefault(property, countFunction);
        return columnFuncAs(columnPropertyFunction, propertyAlias);
    }

    default ColumnAsSelector<T1, TR> columnLen(String property) {
        return columnLenAs(property, null);
    }

    default ColumnAsSelector<T1, TR> columnLenAs(String property, String propertyAlias) {
        ColumnFunction countFunction = getRuntimeContext().getColumnFunctionFactory().createLenFunction();
        ColumnPropertyFunction columnPropertyFunction = DefaultColumnPropertyFunction.createDefault(property, countFunction);
        return columnFuncAs(columnPropertyFunction, propertyAlias);
    }

    ColumnAsSelector<T1, TR> columnFuncAs(ColumnPropertyFunction columnPropertyFunction, String propertyAlias);

    default <T2> ColumnAsSelector<T2, TR> then(ColumnAsSelector<T2, TR> sub) {
        return sub;
    }
}
