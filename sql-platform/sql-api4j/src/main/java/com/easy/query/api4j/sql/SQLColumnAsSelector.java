package com.easy.query.api4j.sql;

import com.easy.query.api4j.select.Queryable;
import com.easy.query.api4j.sql.core.SQLAsLambdaNative;
import com.easy.query.api4j.sql.core.available.LambdaSQLFuncAvailable;
import com.easy.query.api4j.sql.impl.SQLColumnAsSelectorImpl;
import com.easy.query.api4j.util.EasyLambdaUtil;
import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.expression.func.ColumnPropertyFunction;
import com.easy.query.core.expression.lambda.Property;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.lambda.SQLFuncExpression;
import com.easy.query.core.expression.parser.core.EntitySQLTableOwner;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.parser.core.base.ColumnAsSelector;
import com.easy.query.core.expression.segment.CloneableSQLSegment;
import com.easy.query.core.expression.sql.builder.ExpressionContext;
import com.easy.query.core.func.ACSSelector;
import com.easy.query.core.util.EasyCollectionUtil;

import java.util.Collection;

/**
 * @author xuejiaming
 * @Description: 文件说明
 * @Date: 2023/2/6 22:58
 */
public interface SQLColumnAsSelector<T1, TR> extends EntitySQLTableOwner<T1>, LambdaSQLFuncAvailable<T1>, SQLAsLambdaNative<T1, TR, SQLColumnAsSelector<T1, TR>> {
    ColumnAsSelector<T1, TR> getColumnAsSelector();

    default QueryRuntimeContext getRuntimeContext() {
        return getColumnAsSelector().getRuntimeContext();
    }

    default ExpressionContext getExpressionContext() {
        return getColumnAsSelector().getExpressionContext();
    }

    default TableAvailable getTable() {
        return getColumnAsSelector().getTable();
    }

    /**
     * 从group的列里面快速获取对应的列
     *
     * @param index
     * @return
     */
    default SQLColumnAsSelector<T1, TR> groupKeys(int index) {
        getColumnAsSelector().groupKeys(index);
        return this;
    }

    default <TProperty> SQLColumnAsSelector<T1, TR> groupKeysAs(int index, Property<TR, TProperty> alias) {
        getColumnAsSelector().groupKeysAs(index, EasyLambdaUtil.getPropertyName(alias));
        return this;
    }

    default SQLColumnAsSelector<T1, TR> groupKeysAs(int index, String alias) {
        getColumnAsSelector().groupKeysAs(index, alias);
        return this;
    }

    default <TProperty> SQLColumnAsSelector<T1, TR> columns(Collection<Property<T1, TProperty>> columns) {
        if (EasyCollectionUtil.isNotEmpty(columns)) {
            for (Property<T1, TProperty> column : columns) {
                this.column(column);
            }
        }
        return this;
    }

    default <TProperty> SQLColumnAsSelector<T1, TR> column(Property<T1, TProperty> column) {
        getColumnAsSelector().column(EasyLambdaUtil.getPropertyName(column));
        return this;
    }

    default <TIncludeSource, TIncludeResult> SQLColumnAsSelector<T1, TR> columnInclude(Property<T1, TIncludeSource> column, Property<TR, TIncludeResult> aliasProperty) {
        return columnInclude(true, column, aliasProperty, SQLColumnAsSelector::columnAll);
    }

    default <TIncludeSource, TIncludeResult> SQLColumnAsSelector<T1, TR> columnInclude(boolean condition, Property<T1, TIncludeSource> column, Property<TR, TIncludeResult> aliasProperty) {
        return columnInclude(condition, column, aliasProperty, SQLColumnAsSelector::columnAll);
    }

    default <TIncludeSource, TIncludeResult> SQLColumnAsSelector<T1, TR> columnInclude(Property<T1, TIncludeSource> column, Property<TR, TIncludeResult> aliasProperty, SQLExpression1<SQLColumnAsSelector<TIncludeResult, TIncludeResult>> includeSelectorExpression) {
        return columnInclude(true, column, aliasProperty, includeSelectorExpression);
    }

    default <TIncludeSource, TIncludeResult> SQLColumnAsSelector<T1, TR> columnInclude(boolean condition, Property<T1, TIncludeSource> column, Property<TR, TIncludeResult> aliasProperty, SQLExpression1<SQLColumnAsSelector<TIncludeResult, TIncludeResult>> includeSelectorExpression) {
        if (condition) {
            getColumnAsSelector().<TIncludeSource, TIncludeResult>columnInclude(EasyLambdaUtil.getPropertyName(column), EasyLambdaUtil.getPropertyName(aliasProperty), columnAsSelect -> {
                includeSelectorExpression.apply(new SQLColumnAsSelectorImpl<>(columnAsSelect));
            });
        }
        return this;
    }

    default <TIncludeSource, TIncludeResult> SQLColumnAsSelector<T1, TR> columnIncludeMany(Property<T1, Collection<TIncludeSource>> column, Property<TR, Collection<TIncludeResult>> aliasProperty) {
        return columnIncludeMany(true, column, aliasProperty, SQLColumnAsSelector::columnAll);
    }

    default <TIncludeSource, TIncludeResult> SQLColumnAsSelector<T1, TR> columnIncludeMany(boolean condition, Property<T1, Collection<TIncludeSource>> column, Property<TR, Collection<TIncludeResult>> aliasProperty) {
        return columnIncludeMany(condition, column, aliasProperty, SQLColumnAsSelector::columnAll);
    }

    default <TIncludeSource, TIncludeResult> SQLColumnAsSelector<T1, TR> columnIncludeMany(Property<T1, Collection<TIncludeSource>> column, Property<TR, Collection<TIncludeResult>> aliasProperty, SQLExpression1<SQLColumnAsSelector<TIncludeResult, TIncludeResult>> includeSelectorExpression) {
        return columnIncludeMany(true, column, aliasProperty, includeSelectorExpression);
    }

    default <TIncludeSource, TIncludeResult> SQLColumnAsSelector<T1, TR> columnIncludeMany(boolean condition, Property<T1, Collection<TIncludeSource>> column, Property<TR, Collection<TIncludeResult>> aliasProperty, SQLExpression1<SQLColumnAsSelector<TIncludeResult, TIncludeResult>> includeSelectorExpression) {
        if (condition) {
            getColumnAsSelector().<TIncludeSource, TIncludeResult>columnInclude(EasyLambdaUtil.getPropertyName(column), EasyLambdaUtil.getPropertyName(aliasProperty), columnAsSelect -> {
                includeSelectorExpression.apply(new SQLColumnAsSelectorImpl<>(columnAsSelect));
            });
        }
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


    /**
     * 如果是map结果或者基本类型那么alias就是别名
     * 如果是其他情况则为TR类型的属性名
     *
     * @param column
     * @param alias
     * @return
     */
    default SQLColumnAsSelector<T1, TR> columnAs(Property<T1, ?> column, String alias) {
        getColumnAsSelector().columnAs(EasyLambdaUtil.getPropertyName(column), alias);
        return this;
    }

    default <TSubQuery> SQLColumnAsSelector<T1, TR> columnSubQueryAs(SQLFuncExpression<Queryable<TSubQuery>> subQueryableFunc, String alias) {
        getColumnAsSelector().columnSubQueryAs(subQueryableFunc::apply, alias);
        return this;
    }

    default <TSubQuery> SQLColumnAsSelector<T1, TR> columnSubQueryAs(SQLFuncExpression<Queryable<TSubQuery>> subQueryableFunc, Property<TR, TSubQuery> alias) {
        return columnSubQueryAs(subQueryableFunc, EasyLambdaUtil.getPropertyName(alias));
    }

//    default <T2,TSubQuery> SQLColumnAsSelector<T1, TR> columnSubQueryAs(SQLFuncExpression2<SQLWherePredicate<T1>,SQLWherePredicate<T2>, Queryable<TSubQuery>> subQueryableFunc, Property<TR, TSubQuery> alias) {
//        return columnSubQueryAs(subQueryableFunc, EasyLambdaUtil.getPropertyName(alias));
//    }

    default SQLColumnAsSelector<T1, TR> columnCount(Property<T1, ?> column) {
        getColumnAsSelector().columnCount(EasyLambdaUtil.getPropertyName(column));
        return this;
    }

    default SQLColumnAsSelector<T1, TR> columnCount(Property<T1, ?> column, SQLExpression1<ACSSelector> sqlExpression) {
        getColumnAsSelector().columnCount(EasyLambdaUtil.getPropertyName(column), sqlExpression);
        return this;
    }

    default SQLColumnAsSelector<T1, TR> columnCountAs(Property<T1, ?> column, Property<TR, ?> alias) {
        getColumnAsSelector().columnCountAs(EasyLambdaUtil.getPropertyName(column), EasyLambdaUtil.getPropertyName(alias));
        return this;
    }

    default SQLColumnAsSelector<T1, TR> columnCountAs(Property<T1, ?> column, Property<TR, ?> alias, SQLExpression1<ACSSelector> sqlExpression) {
        getColumnAsSelector().columnCountAs(EasyLambdaUtil.getPropertyName(column), EasyLambdaUtil.getPropertyName(alias), sqlExpression);
        return this;
    }

    /**
     * columnSum(BlogEntity::getScore) SUM(t.`score`)
     * columnSum(BlogEntity::getScore,c->c.distinct(true)) SUM(DISTINCT t.`score`)
     * columnSum(BlogEntity::getScore,c->c.nullDefault(BigDecimal.ZERO)) SUM(IFNULL(t.`score`,?))
     * columnSum(BlogEntity::getScore,c->c.distinct(true).nullDefault(BigDecimal.ZERO)) SUM(DISTINCT IFNULL(t.`score`,?))
     *
     * @param column 属性列
     * @return 当前列选择器
     */
    default SQLColumnAsSelector<T1, TR> columnSum(Property<T1, Number> column) {
        getColumnAsSelector().columnSum(EasyLambdaUtil.getPropertyName(column));
        return this;
    }

    /**
     * columnSum(BlogEntity::getScore) SUM(t.`score`)
     * columnSum(BlogEntity::getScore,c->c.distinct(true)) SUM(DISTINCT t.`score`)
     * columnSum(BlogEntity::getScore,c->c.nullDefault(BigDecimal.ZERO)) SUM(IFNULL(t.`score`,?))
     * columnSum(BlogEntity::getScore,c->c.distinct(true).nullDefault(BigDecimal.ZERO)) SUM(DISTINCT IFNULL(t.`score`,?))
     *
     * @param column 属性列
     * @param sqlExpression distinct|null default
     * @return 当前列选择器
     */
    default SQLColumnAsSelector<T1, TR> columnSum(Property<T1, Number> column, SQLExpression1<ACSSelector> sqlExpression) {
        getColumnAsSelector().columnSum(EasyLambdaUtil.getPropertyName(column), sqlExpression);
        return this;
    }

    /**
     * columnSum(BlogEntity::getScore) SUM(t.`score`)
     * columnSum(BlogEntity::getScore,c->c.distinct(true)) SUM(DISTINCT t.`score`)
     * columnSum(BlogEntity::getScore,c->c.nullDefault(BigDecimal.ZERO)) SUM(IFNULL(t.`score`,?))
     * columnSum(BlogEntity::getScore,c->c.distinct(true).nullDefault(BigDecimal.ZERO)) SUM(DISTINCT IFNULL(t.`score`,?))
     *
     * @param column 属性列
     * @param alias 别名
     * @return 当前列选择器
     */
    default SQLColumnAsSelector<T1, TR> columnSumAs(Property<T1, Number> column, Property<TR, Number> alias) {
        getColumnAsSelector().columnSumAs(EasyLambdaUtil.getPropertyName(column), EasyLambdaUtil.getPropertyName(alias));
        return this;
    }

    /**
     * {@code columnSum(BlogEntity::getScore) SUM(t.`score`)}
     *
     * {@code columnSum(BlogEntity::getScore,c->c.distinct(true)) SUM(DISTINCT t.`score`)}
     *
     * {@code columnSum(BlogEntity::getScore,c->c.nullDefault(BigDecimal.ZERO)) SUM(IFNULL(t.`score`,?))}
     *
     * {@code columnSum(BlogEntity::getScore,c->c.distinct(true).nullDefault(BigDecimal.ZERO)) SUM(DISTINCT IFNULL(t.`score`,?))}
     *
     * @param column 属性列
     * @param alias 别名
     * @param sqlExpression distinct|null default
     * @return 当前列选择器
     */
    default SQLColumnAsSelector<T1, TR> columnSumAs(Property<T1, Number> column, Property<TR, Number> alias, SQLExpression1<ACSSelector> sqlExpression) {
        getColumnAsSelector().columnSumAs(EasyLambdaUtil.getPropertyName(column), EasyLambdaUtil.getPropertyName(alias), sqlExpression);
        return this;
    }

    default SQLColumnAsSelector<T1, TR> columnMax(Property<T1, ?> column) {
        getColumnAsSelector().columnMax(EasyLambdaUtil.getPropertyName(column));
        return this;
    }

    default SQLColumnAsSelector<T1, TR> columnMaxAs(Property<T1, ?> column, Property<TR, ?> alias) {
        getColumnAsSelector().columnMaxAs(EasyLambdaUtil.getPropertyName(column), EasyLambdaUtil.getPropertyName(alias));
        return this;
    }


    default SQLColumnAsSelector<T1, TR> columnMin(Property<T1, ?> column) {
        getColumnAsSelector().columnMin(EasyLambdaUtil.getPropertyName(column));
        return this;
    }

    default SQLColumnAsSelector<T1, TR> columnMinAs(Property<T1, ?> column, Property<TR, ?> alias) {
        getColumnAsSelector().columnMinAs(EasyLambdaUtil.getPropertyName(column), EasyLambdaUtil.getPropertyName(alias));
        return this;
    }

    default SQLColumnAsSelector<T1, TR> columnAvg(Property<T1, Number> column) {
        getColumnAsSelector().columnAvg(EasyLambdaUtil.getPropertyName(column));
        return this;
    }

    default SQLColumnAsSelector<T1, TR> columnAvg(Property<T1, Number> column, SQLExpression1<ACSSelector> sqlExpression) {
        getColumnAsSelector().columnAvg(EasyLambdaUtil.getPropertyName(column), sqlExpression);
        return this;
    }

    default SQLColumnAsSelector<T1, TR> columnAvgAs(Property<T1, Number> column, Property<TR, Number> alias) {
        getColumnAsSelector().columnAvgAs(EasyLambdaUtil.getPropertyName(column), EasyLambdaUtil.getPropertyName(alias));
        return this;
    }

    default SQLColumnAsSelector<T1, TR> columnAvgAs(Property<T1, Number> column, Property<TR, Number> alias, SQLExpression1<ACSSelector> sqlExpression) {
        getColumnAsSelector().columnAvgAs(EasyLambdaUtil.getPropertyName(column), EasyLambdaUtil.getPropertyName(alias), sqlExpression);
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

    default SQLColumnAsSelector<T1, TR> sqlSegmentAs(CloneableSQLSegment sqlColumnSegment, Property<TR, ?> alias) {
        getColumnAsSelector().sqlSegmentAs(sqlColumnSegment, EasyLambdaUtil.getPropertyName(alias));
        return this;
    }

    default <T2> SQLColumnAsSelector<T2, TR> then(SQLColumnAsSelector<T2, TR> sub) {
        getColumnAsSelector().then(sub.getColumnAsSelector());
        return sub;
    }
}
