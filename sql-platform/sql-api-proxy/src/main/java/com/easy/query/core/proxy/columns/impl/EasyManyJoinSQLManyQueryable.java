package com.easy.query.core.proxy.columns.impl;

import com.easy.query.api.proxy.entity.select.EntityQueryable;
import com.easy.query.core.basic.api.internal.ExpressionConfigurable;
import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.lambda.SQLFuncExpression1;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.sql.builder.AnonymousManyJoinEntityTableExpressionBuilder;
import com.easy.query.core.proxy.PropTypeColumn;
import com.easy.query.core.proxy.ProxyEntity;
import com.easy.query.core.proxy.SQLSelectAsExpression;
import com.easy.query.core.proxy.columns.SQLQueryable;
import com.easy.query.core.proxy.columns.SubQueryContext;
import com.easy.query.core.proxy.core.EntitySQLContext;
import com.easy.query.core.proxy.core.ProxyManyJoinFlatElementEntitySQLContext;
import com.easy.query.core.proxy.extension.functions.ColumnNumberFunctionAvailable;
import com.easy.query.core.proxy.extension.functions.executor.ColumnFunctionCompareComparableAnyChainExpression;
import com.easy.query.core.proxy.extension.functions.executor.ColumnFunctionCompareComparableBooleanChainExpression;
import com.easy.query.core.proxy.extension.functions.executor.ColumnFunctionCompareComparableNumberChainExpression;
import com.easy.query.core.proxy.extension.functions.executor.ColumnFunctionCompareComparableStringChainExpression;
import com.easy.query.core.proxy.extension.functions.executor.impl.ColumnFunctionCompareComparableAnyChainExpressionImpl;
import com.easy.query.core.proxy.extension.functions.executor.impl.ColumnFunctionCompareComparableNumberChainExpressionImpl;
import com.easy.query.core.proxy.extension.functions.executor.impl.ColumnFunctionCompareComparableStringChainExpressionImpl;
import com.easy.query.core.proxy.grouping.DefaultSQLGroupQueryable;

import java.math.BigDecimal;

/**
 * create time 2024/6/5 08:28
 * 文件说明
 *
 * @author xuejiaming
 */
public class EasyManyJoinSQLManyQueryable<T1Proxy extends ProxyEntity<T1Proxy, T1>, T1> implements SQLQueryable<T1Proxy, T1> {

    private final RewritePredicteToSelectProvider<T1Proxy,T1> rewritePredicteToSelectProvider;

    public EasyManyJoinSQLManyQueryable(SubQueryContext<T1Proxy, T1> subQueryContext, AnonymousManyJoinEntityTableExpressionBuilder manyGroupJoinEntityTableExpressionBuilder, T1Proxy propertyProxy) {
        this.rewritePredicteToSelectProvider = new RewritePredicteToSelectProvider<>(subQueryContext, manyGroupJoinEntityTableExpressionBuilder, propertyProxy);
    }

    @Override
    public SQLQueryable<T1Proxy, T1> distinct(boolean useDistinct) {
        rewritePredicteToSelectProvider.getSubQueryContext().distinct(useDistinct);
        return this;
    }

    @Override
    public SQLQueryable<T1Proxy, T1> orderBy(boolean condition, SQLExpression1<T1Proxy> orderExpression) {
        rewritePredicteToSelectProvider.getSubQueryContext().appendOrderByExpression(orderExpression);
       return this;
    }

    @Override
    public EntitySQLContext getEntitySQLContext() {
        return rewritePredicteToSelectProvider.getEntitySQLContext();
    }

    @Override
    public SubQueryContext<T1Proxy, T1> getSubQueryContext() {
        return rewritePredicteToSelectProvider.getSubQueryContext();
    }

    @Override
    public TableAvailable getOriginalTable() {
        return rewritePredicteToSelectProvider.getOriginalTable();
    }

//    @Override
//    public T1Proxy element(int index) {
//        return null;
//    }

    @Override
    public String getNavValue() {
        return rewritePredicteToSelectProvider.getNavValue();
    }

    @Override
    public T1Proxy getProxy() {
        return rewritePredicteToSelectProvider.getPropertyProxy();
    }

    @Override
    public SQLQueryable<T1Proxy, T1> where(SQLExpression1<T1Proxy> whereExpression) {
        rewritePredicteToSelectProvider.getSubQueryContext().appendWhereExpression(whereExpression);
        return this;
    }

    @Override
    public void any(SQLExpression1<T1Proxy> whereExpression) {
        this.rewritePredicteToSelectProvider.getSubQueryContext().appendWhereExpression(whereExpression);
        this.rewritePredicteToSelectProvider.anyValue().eq(true);

    }

    @Override
    public void any() {
        this.rewritePredicteToSelectProvider.anyValue().eq(true);
    }

    @Override
    public void none(SQLExpression1<T1Proxy> whereExpression) {
        this.rewritePredicteToSelectProvider.getSubQueryContext().appendWhereExpression(whereExpression);
        this.rewritePredicteToSelectProvider.noneValue().eq(true);
    }

    @Override
    public void none() {
        this.rewritePredicteToSelectProvider.noneValue().eq(true);
    }

    @Override
    public ColumnFunctionCompareComparableBooleanChainExpression<Boolean> anyValue() {
        return this.rewritePredicteToSelectProvider.anyValue();
    }

    @Override
    public ColumnFunctionCompareComparableBooleanChainExpression<Boolean> noneValue() {
        return this.rewritePredicteToSelectProvider.noneValue();
    }

    @Override
    public <TMember> ColumnFunctionCompareComparableNumberChainExpression<Long> count(SQLFuncExpression1<T1Proxy, PropTypeColumn<TMember>> columnSelector) {
        ColumnFunctionCompareComparableNumberChainExpression<Long> count = new DefaultSQLGroupQueryable<>(rewritePredicteToSelectProvider.getPropertyProxy(), rewritePredicteToSelectProvider.getPropertyProxy().getEntitySQLContext(), rewritePredicteToSelectProvider.getSubQueryContext().getWhereExpression()).distinct(rewritePredicteToSelectProvider.getSubQueryContext().isDistinct()).count(columnSelector);
        String alias = rewritePredicteToSelectProvider.getOrAppendGroupProjects(count, "count");
        return new ColumnFunctionCompareComparableNumberChainExpressionImpl<>(this.getEntitySQLContext(), rewritePredicteToSelectProvider.getManyGroupJoinTable(), alias, f -> f.nullOrDefault(c->c.column(alias).format(0)), Long.class);
    }


    @Override
    public ColumnFunctionCompareComparableNumberChainExpression<Long> count() {
        ColumnFunctionCompareComparableNumberChainExpression<Long> count = new DefaultSQLGroupQueryable<>(rewritePredicteToSelectProvider.getPropertyProxy(), rewritePredicteToSelectProvider.getPropertyProxy().getEntitySQLContext(), rewritePredicteToSelectProvider.getSubQueryContext().getWhereExpression()).distinct(rewritePredicteToSelectProvider.getSubQueryContext().isDistinct()).count();
        String alias = rewritePredicteToSelectProvider.getOrAppendGroupProjects(count, "count");
        return new ColumnFunctionCompareComparableNumberChainExpressionImpl<>(this.getEntitySQLContext(), rewritePredicteToSelectProvider.getManyGroupJoinTable(), alias, f -> f.nullOrDefault(c->c.column(alias).format(0)), Long.class);
    }

    @Override
    public <TMember> ColumnFunctionCompareComparableNumberChainExpression<Integer> intCount(SQLFuncExpression1<T1Proxy, PropTypeColumn<TMember>> columnSelector) {
        return count(columnSelector).asAnyType(Integer.class);
    }

    @Override
    public ColumnFunctionCompareComparableNumberChainExpression<Integer> intCount() {
        return count().asAnyType(Integer.class);
    }

    @Override
    public <TMember extends Number> ColumnFunctionCompareComparableNumberChainExpression<TMember> sum(SQLFuncExpression1<T1Proxy, ColumnNumberFunctionAvailable<TMember>> columnSelector) {
        ColumnFunctionCompareComparableNumberChainExpression<TMember> sum = new DefaultSQLGroupQueryable<>(rewritePredicteToSelectProvider.getPropertyProxy(), rewritePredicteToSelectProvider.getPropertyProxy().getEntitySQLContext(), rewritePredicteToSelectProvider.getSubQueryContext().getWhereExpression()).distinct(rewritePredicteToSelectProvider.getSubQueryContext().isDistinct()).sum(columnSelector);
        String alias = rewritePredicteToSelectProvider.getOrAppendGroupProjects(sum, "sum");
        return new ColumnFunctionCompareComparableNumberChainExpressionImpl<>(this.getEntitySQLContext(), rewritePredicteToSelectProvider.getManyGroupJoinTable(), alias, f -> f.nullOrDefault(c->c.column(alias).format(0)), BigDecimal.class);

    }

    @Override
    public <TMember extends Number> ColumnFunctionCompareComparableNumberChainExpression<BigDecimal> sumBigDecimal(SQLFuncExpression1<T1Proxy, ColumnNumberFunctionAvailable<TMember>> columnSelector) {
        return sum(columnSelector).asAnyType(BigDecimal.class);
    }

    @Override
    public <TMember extends Number> ColumnFunctionCompareComparableNumberChainExpression<BigDecimal> avg(SQLFuncExpression1<T1Proxy, ColumnNumberFunctionAvailable<TMember>> columnSelector) {
        ColumnFunctionCompareComparableNumberChainExpression<BigDecimal> avg = new DefaultSQLGroupQueryable<>(rewritePredicteToSelectProvider.getPropertyProxy(), rewritePredicteToSelectProvider.getPropertyProxy().getEntitySQLContext(), rewritePredicteToSelectProvider.getSubQueryContext().getWhereExpression()).distinct(rewritePredicteToSelectProvider.getSubQueryContext().isDistinct()).avg(columnSelector);
        String alias = rewritePredicteToSelectProvider.getOrAppendGroupProjects(avg, "avg");
        return new ColumnFunctionCompareComparableNumberChainExpressionImpl<>(this.getEntitySQLContext(), rewritePredicteToSelectProvider.getManyGroupJoinTable(), alias, f -> f.nullOrDefault(c->c.column(alias).format(0)), BigDecimal.class);

    }

    @Override
    public <TMember> ColumnFunctionCompareComparableAnyChainExpression<TMember> max(SQLFuncExpression1<T1Proxy, PropTypeColumn<TMember>> columnSelector) {
        ColumnFunctionCompareComparableAnyChainExpression<TMember> max = new DefaultSQLGroupQueryable<>(rewritePredicteToSelectProvider.getPropertyProxy(), rewritePredicteToSelectProvider.getPropertyProxy().getEntitySQLContext(), rewritePredicteToSelectProvider.getSubQueryContext().getWhereExpression()).max(columnSelector);
        String alias = rewritePredicteToSelectProvider.getOrAppendGroupProjects(max, "max");
        return new ColumnFunctionCompareComparableAnyChainExpressionImpl<>(this.getEntitySQLContext(), rewritePredicteToSelectProvider.getManyGroupJoinTable(), alias, f -> f.anySQLFunction("{0}", c -> c.column(alias)), max.getPropertyType());
    }

    @Override
    public <TMember> ColumnFunctionCompareComparableAnyChainExpression<TMember> min(SQLFuncExpression1<T1Proxy, PropTypeColumn<TMember>> columnSelector) {
        ColumnFunctionCompareComparableAnyChainExpression<TMember> min = new DefaultSQLGroupQueryable<>(rewritePredicteToSelectProvider.getPropertyProxy(), rewritePredicteToSelectProvider.getPropertyProxy().getEntitySQLContext(), rewritePredicteToSelectProvider.getSubQueryContext().getWhereExpression()).min(columnSelector);
        String alias = rewritePredicteToSelectProvider.getOrAppendGroupProjects(min, "min");
        return new ColumnFunctionCompareComparableAnyChainExpressionImpl<>(this.getEntitySQLContext(), rewritePredicteToSelectProvider.getManyGroupJoinTable(), alias, f -> f.anySQLFunction("{0}", c -> c.column(alias)), min.getPropertyType());
    }

    @Override
    public ColumnFunctionCompareComparableStringChainExpression<String> joining(SQLFuncExpression1<T1Proxy, PropTypeColumn<String>> columnSelector, String delimiter) {
        ColumnFunctionCompareComparableStringChainExpression<String> joining = new DefaultSQLGroupQueryable<>(rewritePredicteToSelectProvider.getPropertyProxy(), rewritePredicteToSelectProvider.getPropertyProxy().getEntitySQLContext(), rewritePredicteToSelectProvider.getSubQueryContext().getWhereExpression()).distinct(rewritePredicteToSelectProvider.getSubQueryContext().isDistinct()).joining(columnSelector,delimiter);
        String alias = rewritePredicteToSelectProvider.getOrAppendGroupProjects(joining, "joining");
        return new ColumnFunctionCompareComparableStringChainExpressionImpl<>(this.getEntitySQLContext(), rewritePredicteToSelectProvider.getManyGroupJoinTable(), alias, f -> f.anySQLFunction("{0}", c -> c.column(alias)), String.class);
    }

    @Override
    public T1Proxy flatElement(SQLFuncExpression1<T1Proxy, SQLSelectAsExpression> flatAdapterExpression) {

        QueryRuntimeContext runtimeContext = this.getEntitySQLContext().getRuntimeContext();
        T1Proxy tPropertyProxy = getProxy().create(getProxy().getTable(), new ProxyManyJoinFlatElementEntitySQLContext(rewritePredicteToSelectProvider, this.getEntitySQLContext().getEntityExpressionBuilder(), runtimeContext, flatAdapterExpression));
        tPropertyProxy.setNavValue(getNavValue());
        return tPropertyProxy;
    }

    @Override
    public String getValue() {
        return getSubQueryContext().getProperty();
    }
}
