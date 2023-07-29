package com.easy.query.api4j.sql;

import com.easy.query.api4j.select.Queryable;
import com.easy.query.api4j.sql.impl.SQLColumnAsSelectorImpl;
import com.easy.query.api4j.sql.scec.SQLColumnConstExpressionContext;
import com.easy.query.api4j.sql.scec.SQLColumnConstExpressionContextImpl;
import com.easy.query.api4j.util.EasyLambdaUtil;
import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.expression.func.ColumnPropertyFunction;
import com.easy.query.core.expression.lambda.Property;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.lambda.SQLFuncExpression;
import com.easy.query.core.expression.parser.core.EntitySQLTableOwner;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.parser.core.base.ColumnAsSelector;
import com.easy.query.core.expression.segment.SQLColumnSegment;
import com.easy.query.core.expression.sql.builder.ExpressionContext;

import java.util.Collection;

/**
 * @author xuejiaming
 * @Description: 文件说明
 * @Date: 2023/2/6 22:58
 */
public interface SQLColumnAsSelector<T1, TR> extends EntitySQLTableOwner<T1> {
    ColumnAsSelector<T1, TR> getColumnAsSelector();

    default QueryRuntimeContext getRuntimeContext() {
        return getColumnAsSelector().getRuntimeContext();
    }
   default ExpressionContext getExpressionContext(){
        return getColumnAsSelector().getExpressionContext();
   }

    default TableAvailable getTable() {
        return getColumnAsSelector().getTable();
    }

    default SQLColumnAsSelector<T1, TR> column(Property<T1, ?> column) {
        getColumnAsSelector().column(EasyLambdaUtil.getPropertyName(column));
        return this;
    }

    default <TIncludeSource,TIncludeResult> SQLColumnAsSelector<T1, TR> columnInclude(Property<T1, TIncludeSource> column, Property<TR, TIncludeResult> aliasProperty){
        return columnInclude(true,column,aliasProperty, SQLColumnAsSelector::columnAll);
    }
    default <TIncludeSource,TIncludeResult> SQLColumnAsSelector<T1, TR> columnInclude(boolean condition,Property<T1, TIncludeSource> column, Property<TR, TIncludeResult> aliasProperty){
        return columnInclude(condition,column,aliasProperty, SQLColumnAsSelector::columnAll);
    }
    default <TIncludeSource,TIncludeResult> SQLColumnAsSelector<T1, TR> columnInclude(Property<T1, TIncludeSource> column, Property<TR, TIncludeResult> aliasProperty, SQLExpression1<SQLColumnAsSelector<TIncludeResult,TIncludeResult>> includeSelectorExpression){
        return columnInclude(true,column,aliasProperty,includeSelectorExpression);
    }
    default <TIncludeSource,TIncludeResult> SQLColumnAsSelector<T1, TR> columnInclude(boolean condition,Property<T1, TIncludeSource> column, Property<TR, TIncludeResult> aliasProperty, SQLExpression1<SQLColumnAsSelector<TIncludeResult,TIncludeResult>> includeSelectorExpression){
        if(condition){
            getColumnAsSelector().<TIncludeSource,TIncludeResult>columnInclude(EasyLambdaUtil.getPropertyName(column),EasyLambdaUtil.getPropertyName(aliasProperty),columnAsSelect->{
                includeSelectorExpression.apply(new SQLColumnAsSelectorImpl<>(columnAsSelect));
            });
        }
        return this;
    }
    default <TIncludeSource,TIncludeResult> SQLColumnAsSelector<T1, TR> columnIncludeMany(Property<T1, Collection<TIncludeSource>> column, Property<TR, Collection<TIncludeResult>> aliasProperty){
        return columnIncludeMany(true,column,aliasProperty, SQLColumnAsSelector::columnAll);
    }
    default <TIncludeSource,TIncludeResult> SQLColumnAsSelector<T1, TR> columnIncludeMany(boolean condition,Property<T1, Collection<TIncludeSource>> column, Property<TR, Collection<TIncludeResult>> aliasProperty){
        return columnIncludeMany(condition,column,aliasProperty, SQLColumnAsSelector::columnAll);
    }
    default <TIncludeSource,TIncludeResult> SQLColumnAsSelector<T1, TR> columnIncludeMany(Property<T1, Collection<TIncludeSource>> column, Property<TR, Collection<TIncludeResult>> aliasProperty, SQLExpression1<SQLColumnAsSelector<TIncludeResult,TIncludeResult>> includeSelectorExpression){
        return columnIncludeMany(true,column,aliasProperty,includeSelectorExpression);
    }
    default <TIncludeSource,TIncludeResult> SQLColumnAsSelector<T1, TR> columnIncludeMany(boolean condition,Property<T1, Collection<TIncludeSource>> column, Property<TR, Collection<TIncludeResult>> aliasProperty, SQLExpression1<SQLColumnAsSelector<TIncludeResult,TIncludeResult>> includeSelectorExpression){
        if(condition){
            getColumnAsSelector().<TIncludeSource,TIncludeResult>columnInclude(EasyLambdaUtil.getPropertyName(column),EasyLambdaUtil.getPropertyName(aliasProperty),columnAsSelect->{
                includeSelectorExpression.apply(new SQLColumnAsSelectorImpl<>(columnAsSelect));
            });
        }
        return this;
    }
//    default <TIncludeSource,TIncludeResult> SQLColumnAsSelector<T1, TR> columnIncludeMany(Property<T1, Collection<TIncludeSource>> column, Property<TR, Collection<TIncludeResult>> aliasProperty){
//        getColumnAsSelector().columnInclude(EasyLambdaUtil.getPropertyName(column),EasyLambdaUtil.getPropertyName(aliasProperty));
//        return this;
//    }
//    default SQLColumnAsSelector<T1, TR> columnConstAs(String columnConst, String alias) {
//        getColumnAsSelector().columnConstAs(columnConst,alias);
//        return this;
//    }
//    default SQLColumnAsSelector<T1, TR> columnConst(String columnConst) {
//        getColumnAsSelector().columnConstAs(columnConst,alias);
//        return this;
//    }

    default SQLColumnAsSelector<T1,TR> columnConst(String columnConst){
        return columnConst(columnConst,c->{});
    }
   default SQLColumnAsSelector<T1,TR> columnConst(String columnConst, SQLExpression1<SQLColumnConstExpressionContext<T1>> contextConsume){
       getColumnAsSelector().columnConst(columnConst,context->{
           contextConsume.apply(new SQLColumnConstExpressionContextImpl<>(context));
       });
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
    default SQLColumnAsSelector<T1,TR> sqlColumnAs(SQLColumnSegment sqlColumnSegment, Property<TR, ?> alias){
        getColumnAsSelector().sqlColumnAs(sqlColumnSegment,EasyLambdaUtil.getPropertyName(alias));
        return this;
    }

    default <T2> SQLColumnAsSelector<T2, TR> then(SQLColumnAsSelector<T2, TR> sub) {
        getColumnAsSelector().then(sub.getColumnAsSelector());
        return sub;
    }
}
