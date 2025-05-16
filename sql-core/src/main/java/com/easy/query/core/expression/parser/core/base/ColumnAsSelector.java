package com.easy.query.core.expression.parser.core.base;

import com.easy.query.core.basic.api.select.Query;
import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.expression.builder.AsSelector;
import com.easy.query.core.expression.lambda.SQLActionExpression1;
import com.easy.query.core.expression.lambda.SQLFuncExpression;
import com.easy.query.core.expression.parser.core.EntitySQLTableOwner;
import com.easy.query.core.expression.parser.core.available.SQLFxAvailable;
import com.easy.query.core.expression.parser.core.base.core.SQLAsPropertyNative;
import com.easy.query.core.expression.segment.CloneableSQLSegment;
import com.easy.query.core.expression.sql.builder.ExpressionContext;
import com.easy.query.core.func.ACSSelector;

/**
 * @author xuejiaming
 * @Description: 文件说明
 * create time 2023/2/6 22:58
 */
public interface ColumnAsSelector<T1, TR> extends EntitySQLTableOwner<T1>, SQLAsPropertyNative<ColumnAsSelector<T1, TR>>, SQLFxAvailable {
    AsSelector getAsSelector();

    default QueryRuntimeContext getRuntimeContext() {
        return getAsSelector().getRuntimeContext();
    }

    ExpressionContext getExpressionContext();

    default ColumnAsSelector<T1, TR> groupKeys(int index) {
        getAsSelector().groupKeys(index);
        return this;
    }

    default ColumnAsSelector<T1, TR> groupKeysAs(int index, String alias) {
        getAsSelector().groupKeysAs(index, alias);
        return this;
    }

    ColumnAsSelector<T1, TR> column(String property);

    default ColumnAsSelector<T1, TR> columnInclude(String property, String aliasProperty) {
        return columnInclude(true, property, aliasProperty);
    }

    default ColumnAsSelector<T1, TR> columnInclude(boolean condition, String property, String aliasProperty) {
        return columnInclude(condition, property, aliasProperty, ColumnAsSelector::columnAll);
    }

    default <TIncludeSource, TIncludeResult> ColumnAsSelector<T1, TR> columnInclude(String property, String aliasProperty, SQLActionExpression1<ColumnAsSelector<TIncludeResult, TIncludeResult>> includeSelectorExpression) {
        return columnInclude(true, property, aliasProperty, includeSelectorExpression);
    }

    <TIncludeSource, TIncludeResult> ColumnAsSelector<T1, TR> columnInclude(boolean condition, String property, String aliasProperty, SQLActionExpression1<ColumnAsSelector<TIncludeResult, TIncludeResult>> includeSelectorExpression);

    ColumnAsSelector<T1, TR> columnIgnore(String property);

    /**
     * 映射到TR的所有列上,按ColumnName进行映射,如果TR上没有对应的列名那么将不会映射查询列
     *
     * @return
     */
    ColumnAsSelector<T1, TR> columnAll();

    ColumnAsSelector<T1, TR> columnAs(String property, String propertyAlias);

    <TSubQuery> ColumnAsSelector<T1, TR> columnSubQueryAs(SQLFuncExpression<Query<TSubQuery>> subQueryableFunc, String propertyAlias);

    default ColumnAsSelector<T1, TR> columnCount(String property) {
        getAsSelector().columnCount(getTable(), property);
        return this;
    }

    default ColumnAsSelector<T1, TR> columnCount(String property, SQLActionExpression1<ACSSelector> sqlExpression) {
        getAsSelector().columnCount(getTable(), property, sqlExpression);
        return this;
    }

    default ColumnAsSelector<T1, TR> columnCountAs(String property, String propertyAlias) {
        getAsSelector().columnCountAs(getTable(), property, propertyAlias);
        return this;
    }

    default ColumnAsSelector<T1, TR> columnCountAs(String property, String propertyAlias, SQLActionExpression1<ACSSelector> sqlExpression) {
        getAsSelector().columnCountAs(getTable(), property, propertyAlias, sqlExpression);
        return this;
    }

    default ColumnAsSelector<T1, TR> columnSum(String property) {
        getAsSelector().columnSum(getTable(), property);
        return this;
    }

    default ColumnAsSelector<T1, TR> columnSum(String property, SQLActionExpression1<ACSSelector> sqlExpression) {
        getAsSelector().columnSum(getTable(), property, sqlExpression);
        return this;
    }

    default ColumnAsSelector<T1, TR> columnSumAs(String property, String propertyAlias) {
        getAsSelector().columnSumAs(getTable(), property, propertyAlias);
        return this;
    }

    default ColumnAsSelector<T1, TR> columnSumAs(String property, String propertyAlias, SQLActionExpression1<ACSSelector> sqlExpression) {
        getAsSelector().columnSumAs(getTable(), property, propertyAlias, sqlExpression);
        return this;
    }

    default ColumnAsSelector<T1, TR> columnMax(String property) {
        getAsSelector().columnMax(getTable(), property);
        return this;
    }

    default ColumnAsSelector<T1, TR> columnMaxAs(String property, String propertyAlias) {
        getAsSelector().columnMaxAs(getTable(), property, propertyAlias);
        return this;
    }

    default ColumnAsSelector<T1, TR> columnMin(String property) {
        getAsSelector().columnMin(getTable(), property);
        return this;
    }

    default ColumnAsSelector<T1, TR> columnMinAs(String property, String propertyAlias) {
        getAsSelector().columnMinAs(getTable(), property, propertyAlias);
        return this;
    }

    default ColumnAsSelector<T1, TR> columnAvg(String property) {
        getAsSelector().columnAvg(getTable(), property);
        return this;
    }

    default ColumnAsSelector<T1, TR> columnAvg(String property, SQLActionExpression1<ACSSelector> sqlExpression) {
        getAsSelector().columnAvg(getTable(), property, sqlExpression);
        return this;
    }

    default ColumnAsSelector<T1, TR> columnAvgAs(String property, String propertyAlias) {
        getAsSelector().columnAvgAs(getTable(), property, propertyAlias);
        return this;
    }

    default ColumnAsSelector<T1, TR> columnAvgAs(String property, String propertyAlias, SQLActionExpression1<ACSSelector> sqlExpression) {
        getAsSelector().columnAvgAs(getTable(), property, propertyAlias, sqlExpression);
        return this;
    }
    ColumnAsSelector<T1, TR> sqlSegmentAs(CloneableSQLSegment sqlColumnSegment, String propertyAlias);

    default <T2> ColumnAsSelector<T2, TR> then(ColumnAsSelector<T2, TR> sub) {
        return sub;
    }
}
