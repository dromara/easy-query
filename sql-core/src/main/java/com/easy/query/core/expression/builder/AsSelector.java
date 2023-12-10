package com.easy.query.core.expression.builder;

import com.easy.query.core.basic.api.select.Query;
import com.easy.query.core.expression.builder.core.SQLNative;
import com.easy.query.core.expression.func.ColumnFunction;
import com.easy.query.core.expression.func.ColumnPropertyFunction;
import com.easy.query.core.expression.func.DefaultColumnPropertyFunction;
import com.easy.query.core.expression.lambda.SQLActionExpression;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.lambda.SQLFuncExpression;
import com.easy.query.core.expression.parser.core.available.RuntimeContextAvailable;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.segment.CloneableSQLSegment;
import com.easy.query.core.expression.sql.builder.EntityQueryExpressionBuilder;
import com.easy.query.core.expression.sql.builder.ExpressionContext;
import com.easy.query.core.func.ACSSelector;
import com.easy.query.core.func.DistinctDefaultSettingImpl;
import com.easy.query.core.func.SQLFunction;
import com.easy.query.core.func.def.DistinctDefaultSQLFunction;

/**
 * create time 2023/6/22 20:47
 * 文件说明
 *
 * @author xuejiaming
 */
public interface AsSelector extends SQLNative<AsSelector>, RuntimeContextAvailable {

    ExpressionContext getExpressionContext();

    EntityQueryExpressionBuilder getEntityQueryExpressionBuilder();

    AsSelector columnKeys(TableAvailable table);
    AsSelector column(TableAvailable table, String property);

    /**
     * 快速选择之前group的列,不需要重新再写一遍
     *
     * @param index
     * @return
     */
    AsSelector groupKeys(int index);

    /**
     * 快速选择之前group的列,不需要重新再写一遍
     *
     * @param index
     * @param alias
     * @return
     */
    AsSelector groupKeysAs(int index, String alias);

    /**
     * 哪张表的目标属性
     *
     * @param table
     * @param selfProperty
     * @param aliasProperty
     * @param includeSelectorExpression
     * @return
     */
    AsSelector columnInclude(TableAvailable table, String selfProperty, String aliasProperty, SQLExpression1<AsSelector> includeSelectorExpression);

    AsSelector columnIgnore(TableAvailable table, String property);

    /**
     * 映射到TR的所有列上,按ColumnName进行映射,如果TR上没有对应的列名那么将不会映射查询列
     *
     * @return
     */
    AsSelector columnAll(TableAvailable table);

    AsSelector columnAs(TableAvailable table, String property, String propertyAlias);

    <TSubQuery> AsSelector columnSubQueryAs(SQLFuncExpression<Query<TSubQuery>> subQueryableFunc, String propertyAlias);

    default AsSelector columnCount(TableAvailable table, String property) {
        return columnCountAs(table, property, null);
    }

    default AsSelector columnCount(TableAvailable table, String property, SQLExpression1<ACSSelector> sqlExpression) {
        return columnCountAs(table, property, null, sqlExpression);
    }

    default AsSelector columnCountAs(TableAvailable table, String property, String propertyAlias) {
        return columnCountAs(table, property, propertyAlias, c -> {
        });
    }

    default AsSelector columnCountAs(TableAvailable table, String property, String propertyAlias, SQLExpression1<ACSSelector> sqlExpression) {
        DistinctDefaultSQLFunction count = getRuntimeContext().fx().count(o -> o.column(table, property));
        return columnFunc(table, property, count, propertyAlias, () -> {
            sqlExpression.apply(new DistinctDefaultSettingImpl(count));
        });
    }

    default AsSelector columnSum(TableAvailable table, String property) {
        return columnSumAs(table, property, null);
    }

    default AsSelector columnSum(TableAvailable table, String property, SQLExpression1<ACSSelector> sqlExpression) {
        return columnSumAs(table, property, null, sqlExpression);
    }

    default AsSelector columnSumAs(TableAvailable table, String property, String propertyAlias) {
        return columnSumAs(table, property, propertyAlias, c -> {
        });
    }

    default AsSelector columnSumAs(TableAvailable table, String property, String propertyAlias, SQLExpression1<ACSSelector> sqlExpression) {
        DistinctDefaultSQLFunction sum = getRuntimeContext().fx().sum(o -> o.column(table, property));
        return columnFunc(table, property, sum, propertyAlias, () -> {
            sqlExpression.apply(new DistinctDefaultSettingImpl(sum));
        });
    }

    default AsSelector columnMax(TableAvailable table, String property) {
        return columnMaxAs(table, property, null);
    }

    default AsSelector columnMaxAs(TableAvailable table, String property, String propertyAlias) {
        SQLFunction max = getRuntimeContext().fx().max(o -> o.column(table, property));
        return columnFunc(table, property, max, propertyAlias, () -> {
        });
    }

    default AsSelector columnMin(TableAvailable table, String property) {
        return columnMinAs(table, property, null);
    }

    default AsSelector columnMinAs(TableAvailable table, String property, String propertyAlias) {
        SQLFunction min = getRuntimeContext().fx().min(o -> o.column(table, property));
        return columnFunc(table, property, min, propertyAlias, () -> {
        });
    }

    default AsSelector columnAvg(TableAvailable table, String property) {
        return columnAvg(table, property, c -> {
        });
    }

    default AsSelector columnAvg(TableAvailable table, String property, SQLExpression1<ACSSelector> sqlExpression) {
        return columnAvgAs(table, property, null, sqlExpression);
    }

    default AsSelector columnAvgAs(TableAvailable table, String property, String propertyAlias) {
        return columnAvgAs(table, property, propertyAlias, c -> {
        });
    }

    default AsSelector columnAvgAs(TableAvailable table, String property, String propertyAlias, SQLExpression1<ACSSelector> sqlExpression) {
        DistinctDefaultSQLFunction avg = getRuntimeContext().fx().avg(o -> o.column(table, property));
        return columnFunc(table, property, avg, propertyAlias, () -> {
            sqlExpression.apply(new DistinctDefaultSettingImpl(avg));
        });
    }

    default AsSelector columnLen(TableAvailable table, String property) {
        return columnLenAs(table, property, null);
    }

    default AsSelector columnLenAs(TableAvailable table, String property, String propertyAlias) {
        ColumnFunction countFunction = getRuntimeContext().getColumnFunctionFactory().createLenFunction();
        ColumnPropertyFunction columnPropertyFunction = DefaultColumnPropertyFunction.createDefault(property, countFunction);
        return columnFuncAs(table, columnPropertyFunction, propertyAlias);
    }

    AsSelector columnFunc(TableAvailable table, String property, SQLFunction sqlFunction, String propertyAlias, SQLActionExpression sqlActionExpression);

    AsSelector columnFuncAs(TableAvailable table, ColumnPropertyFunction columnPropertyFunction, String propertyAlias);

    //    <T extends SQLFunction> void columnAppendSQLFunction(TableAvailable table, String property, T sqlFunction, String propertyAlias);
    AsSelector sqlSegmentAs(CloneableSQLSegment sqlColumnSegment, String propertyAlias);
}
