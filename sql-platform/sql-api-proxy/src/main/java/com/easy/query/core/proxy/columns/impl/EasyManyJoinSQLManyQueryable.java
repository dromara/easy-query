package com.easy.query.core.proxy.columns.impl;

import com.easy.query.api.proxy.entity.select.EntityQueryable;
import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.lambda.SQLFuncExpression1;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.sql.builder.AnonymousManyGroupJoinEntityTableExpressionBuilder;
import com.easy.query.core.expression.sql.builder.EntityExpressionBuilder;
import com.easy.query.core.proxy.PropTypeColumn;
import com.easy.query.core.proxy.ProxyEntity;
import com.easy.query.core.proxy.SQLSelectAsExpression;
import com.easy.query.core.proxy.columns.SQLManyQueryable;
import com.easy.query.core.proxy.columns.SQLPredicateQueryable;
import com.easy.query.core.proxy.columns.SQLQueryable;
import com.easy.query.core.proxy.core.EntitySQLContext;
import com.easy.query.core.proxy.core.ProxyManyJoinFlatElementEntitySQLContext;
import com.easy.query.core.proxy.extension.functions.ColumnNumberFunctionAvailable;
import com.easy.query.core.proxy.extension.functions.executor.ColumnFunctionCompareComparableAnyChainExpression;
import com.easy.query.core.proxy.extension.functions.executor.ColumnFunctionCompareComparableBooleanChainExpression;
import com.easy.query.core.proxy.extension.functions.executor.ColumnFunctionCompareComparableNumberChainExpression;
import com.easy.query.core.proxy.extension.functions.executor.impl.ColumnFunctionCompareComparableAnyChainExpressionImpl;
import com.easy.query.core.proxy.extension.functions.executor.impl.ColumnFunctionCompareComparableNumberChainExpressionImpl;
import com.easy.query.core.proxy.grouping.DefaultSQLGroupQueryable;

import java.math.BigDecimal;

/**
 * create time 2024/6/5 08:28
 * 文件说明
 *
 * @author xuejiaming
 */
public class EasyManyJoinSQLManyQueryable<TProxy, T1Proxy extends ProxyEntity<T1Proxy, T1>, T1> implements SQLManyQueryable<TProxy, T1Proxy, T1> {

    private final ManyJoinPredicateToGroupProjectProvider<T1Proxy,T1> manyJoinPredicateToGroupProjectProvider;
    private TProxy tProxy;
    private boolean distinct = false;

    public EasyManyJoinSQLManyQueryable(EntitySQLContext entitySQLContext, AnonymousManyGroupJoinEntityTableExpressionBuilder manyGroupJoinEntityTableExpressionBuilder, EntityExpressionBuilder entityExpressionBuilder, TableAvailable originalTable, String navValue, T1Proxy t1Proxy) {
        this.manyJoinPredicateToGroupProjectProvider = new ManyJoinPredicateToGroupProjectProvider<>(entitySQLContext, manyGroupJoinEntityTableExpressionBuilder, entityExpressionBuilder, originalTable, navValue, t1Proxy);
    }

    @Override
    public SQLQueryable<T1Proxy, T1> distinct(boolean useDistinct) {
        this.distinct = useDistinct;
        return this;
    }

    @Override
    public EntitySQLContext getEntitySQLContext() {
        return manyJoinPredicateToGroupProjectProvider.getEntitySQLContext();
    }

    @Override
    public EntityQueryable<T1Proxy, T1> getQueryable() {
        throw new UnsupportedOperationException();
    }

    @Override
    public TableAvailable getOriginalTable() {
        return manyJoinPredicateToGroupProjectProvider.getOriginalTable();
    }

    @Override
    public String getNavValue() {
        return manyJoinPredicateToGroupProjectProvider.getNavValue();
    }

    @Override
    public T1Proxy getProxy() {
        return manyJoinPredicateToGroupProjectProvider.getT1Proxy();
    }

    @Override
    public SQLPredicateQueryable<T1Proxy, T1> where(SQLExpression1<T1Proxy> whereExpression) {
        return new EasyGroupSQLPredicateQueryable<>(whereExpression, manyJoinPredicateToGroupProjectProvider, distinct);
    }

    @Override
    public void any(SQLExpression1<T1Proxy> whereExpression) {
        this.manyJoinPredicateToGroupProjectProvider.anyValue(whereExpression).eq(true);

    }

    @Override
    public void any() {
        this.manyJoinPredicateToGroupProjectProvider.anyValue(null).eq(true);
    }

    @Override
    public void none(SQLExpression1<T1Proxy> whereExpression) {
        this.manyJoinPredicateToGroupProjectProvider.noneValue(whereExpression).eq(true);
    }

    @Override
    public void none() {
        this.manyJoinPredicateToGroupProjectProvider.noneValue(null).eq(true);
    }

    @Override
    public ColumnFunctionCompareComparableBooleanChainExpression<Boolean> anyValue() {
        return this.manyJoinPredicateToGroupProjectProvider.anyValue(null);
    }

    @Override
    public ColumnFunctionCompareComparableBooleanChainExpression<Boolean> noneValue() {
        return this.manyJoinPredicateToGroupProjectProvider.noneValue(null);
    }

    @Override
    public <TMember> ColumnFunctionCompareComparableNumberChainExpression<Long> count(SQLFuncExpression1<T1Proxy, PropTypeColumn<TMember>> columnSelector) {
        ColumnFunctionCompareComparableNumberChainExpression<Long> count = new DefaultSQLGroupQueryable<>(manyJoinPredicateToGroupProjectProvider.getT1Proxy(), this.getEntitySQLContext(), null).distinct(distinct).count(columnSelector);
        String alias = manyJoinPredicateToGroupProjectProvider.getOrAppendGroupProjects(count, "count");
        return new ColumnFunctionCompareComparableNumberChainExpressionImpl<>(this.getEntitySQLContext(), manyJoinPredicateToGroupProjectProvider.getManyGroupJoinTable(), alias, f -> f.anySQLFunction("{0}", c -> c.column(alias)), Long.class);
    }


    @Override
    public ColumnFunctionCompareComparableNumberChainExpression<Long> count() {
        ColumnFunctionCompareComparableNumberChainExpression<Long> count = new DefaultSQLGroupQueryable<>(manyJoinPredicateToGroupProjectProvider.getT1Proxy(), this.getEntitySQLContext(), null).distinct(distinct).count();
        String alias = manyJoinPredicateToGroupProjectProvider.getOrAppendGroupProjects(count, "count");
        return new ColumnFunctionCompareComparableNumberChainExpressionImpl<>(this.getEntitySQLContext(), manyJoinPredicateToGroupProjectProvider.getManyGroupJoinTable(), alias, f -> f.anySQLFunction("{0}", c -> c.column(alias)), Long.class);
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
        ColumnFunctionCompareComparableNumberChainExpression<TMember> sum = new DefaultSQLGroupQueryable<>(manyJoinPredicateToGroupProjectProvider.getT1Proxy(), this.getEntitySQLContext(), null).distinct(distinct).sum(columnSelector);
        String alias = manyJoinPredicateToGroupProjectProvider.getOrAppendGroupProjects(sum, "sum");
        return new ColumnFunctionCompareComparableNumberChainExpressionImpl<>(this.getEntitySQLContext(), manyJoinPredicateToGroupProjectProvider.getManyGroupJoinTable(), alias, f -> f.anySQLFunction("{0}", c -> c.column(alias)), BigDecimal.class);

    }

    @Override
    public <TMember extends Number> ColumnFunctionCompareComparableNumberChainExpression<BigDecimal> sumBigDecimal(SQLFuncExpression1<T1Proxy, ColumnNumberFunctionAvailable<TMember>> columnSelector) {
        return sum(columnSelector).asAnyType(BigDecimal.class);
    }

    @Override
    public <TMember extends Number> ColumnFunctionCompareComparableNumberChainExpression<BigDecimal> avg(SQLFuncExpression1<T1Proxy, ColumnNumberFunctionAvailable<TMember>> columnSelector) {
        ColumnFunctionCompareComparableNumberChainExpression<BigDecimal> avg = new DefaultSQLGroupQueryable<>(manyJoinPredicateToGroupProjectProvider.getT1Proxy(), this.getEntitySQLContext(), null).distinct(distinct).avg(columnSelector);
        String alias = manyJoinPredicateToGroupProjectProvider.getOrAppendGroupProjects(avg, "avg");
        return new ColumnFunctionCompareComparableNumberChainExpressionImpl<>(this.getEntitySQLContext(), manyJoinPredicateToGroupProjectProvider.getManyGroupJoinTable(), alias, f -> f.anySQLFunction("{0}", c -> c.column(alias)), BigDecimal.class);

    }

    @Override
    public <TMember> ColumnFunctionCompareComparableAnyChainExpression<TMember> max(SQLFuncExpression1<T1Proxy, PropTypeColumn<TMember>> columnSelector) {
        ColumnFunctionCompareComparableAnyChainExpression<TMember> max = new DefaultSQLGroupQueryable<>(manyJoinPredicateToGroupProjectProvider.getT1Proxy(), this.getEntitySQLContext(), null).max(columnSelector);
        String alias = manyJoinPredicateToGroupProjectProvider.getOrAppendGroupProjects(max, "max");
        return new ColumnFunctionCompareComparableAnyChainExpressionImpl<>(this.getEntitySQLContext(), manyJoinPredicateToGroupProjectProvider.getManyGroupJoinTable(), alias, f -> f.anySQLFunction("{0}", c -> c.column(alias)), max.getPropertyType());
    }

    @Override
    public <TMember> ColumnFunctionCompareComparableAnyChainExpression<TMember> min(SQLFuncExpression1<T1Proxy, PropTypeColumn<TMember>> columnSelector) {
        ColumnFunctionCompareComparableAnyChainExpression<TMember> min = new DefaultSQLGroupQueryable<>(manyJoinPredicateToGroupProjectProvider.getT1Proxy(), this.getEntitySQLContext(), null).min(columnSelector);
        String alias = manyJoinPredicateToGroupProjectProvider.getOrAppendGroupProjects(min, "min");
        return new ColumnFunctionCompareComparableAnyChainExpressionImpl<>(this.getEntitySQLContext(), manyJoinPredicateToGroupProjectProvider.getManyGroupJoinTable(), alias, f -> f.anySQLFunction("{0}", c -> c.column(alias)), min.getPropertyType());
    }

    @Override
    public SQLQueryable<T1Proxy, T1> useLogicDelete(boolean enable) {
        return this;
    }

    @Override
    public void _setProxy(TProxy tProxy) {
        this.tProxy = tProxy;
    }


    @Override
    public T1Proxy flatElement(SQLFuncExpression1<T1Proxy, SQLSelectAsExpression> flatAdapterExpression) {

        QueryRuntimeContext runtimeContext = this.getEntitySQLContext().getRuntimeContext();
        T1Proxy tPropertyProxy = getProxy().create(getProxy().getTable(), new ProxyManyJoinFlatElementEntitySQLContext(manyJoinPredicateToGroupProjectProvider, this.getEntitySQLContext().getEntityExpressionBuilder(), runtimeContext, flatAdapterExpression));
        tPropertyProxy.setNavValue(getNavValue());
        return tPropertyProxy;
    }
}
