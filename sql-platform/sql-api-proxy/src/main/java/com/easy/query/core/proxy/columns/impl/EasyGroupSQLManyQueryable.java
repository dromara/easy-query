package com.easy.query.core.proxy.columns.impl;

import com.easy.query.api.proxy.entity.select.EntityQueryable;
import com.easy.query.core.basic.api.select.Query;
import com.easy.query.core.basic.jdbc.parameter.DefaultToSQLContext;
import com.easy.query.core.basic.jdbc.parameter.ToSQLContext;
import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.expression.builder.impl.AsSelectorImpl;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.lambda.SQLFuncExpression1;
import com.easy.query.core.expression.many2group.ManyGroupJoinProjectKey;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.segment.SQLEntityAliasSegment;
import com.easy.query.core.expression.segment.SQLSegment;
import com.easy.query.core.expression.segment.builder.ProjectSQLBuilderSegmentImpl;
import com.easy.query.core.expression.sql.builder.AnonymousEntityTableExpressionBuilder;
import com.easy.query.core.expression.sql.builder.AnonymousManyGroupJoinEntityTableExpressionBuilder;
import com.easy.query.core.expression.sql.builder.EntityExpressionBuilder;
import com.easy.query.core.expression.sql.builder.EntityQueryExpressionBuilder;
import com.easy.query.core.expression.sql.builder.EntityTableExpressionBuilder;
import com.easy.query.core.proxy.PropTypeColumn;
import com.easy.query.core.proxy.ProxyEntity;
import com.easy.query.core.proxy.SQLSelectAsExpression;
import com.easy.query.core.proxy.columns.SQLManyQueryable;
import com.easy.query.core.proxy.columns.SQLPredicateQueryable;
import com.easy.query.core.proxy.columns.SQLQueryable;
import com.easy.query.core.proxy.core.EntitySQLContext;
import com.easy.query.core.proxy.core.ProxyFlatElementEntitySQLContext;
import com.easy.query.core.proxy.extension.functions.ColumnNumberFunctionAvailable;
import com.easy.query.core.proxy.extension.functions.executor.ColumnFunctionCompareComparableAnyChainExpression;
import com.easy.query.core.proxy.extension.functions.executor.ColumnFunctionCompareComparableBooleanChainExpression;
import com.easy.query.core.proxy.extension.functions.executor.ColumnFunctionCompareComparableNumberChainExpression;
import com.easy.query.core.proxy.extension.functions.executor.impl.ColumnFunctionCompareComparableAnyChainExpressionImpl;
import com.easy.query.core.proxy.extension.functions.executor.impl.ColumnFunctionCompareComparableNumberChainExpressionImpl;
import com.easy.query.core.proxy.grouping.DefaultSQLGroupQueryable;
import com.easy.query.core.proxy.impl.SQLColumnIncludeColumn2Impl;
import com.easy.query.core.util.EasySQLUtil;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;

/**
 * create time 2024/6/5 08:28
 * 文件说明
 *
 * @author xuejiaming
 */
public class EasyGroupSQLManyQueryable<TProxy, T1Proxy extends ProxyEntity<T1Proxy, T1>, T1> implements SQLManyQueryable<TProxy, T1Proxy, T1> {

    private final ManyGroupJoinContext<T1Proxy,T1> manyGroupJoinContext;
    private TProxy tProxy;
    private boolean distinct = false;

    public EasyGroupSQLManyQueryable(EntitySQLContext entitySQLContext, AnonymousManyGroupJoinEntityTableExpressionBuilder manyGroupJoinEntityTableExpressionBuilder, EntityExpressionBuilder entityExpressionBuilder, TableAvailable originalTable, String navValue, T1Proxy t1Proxy) {
        this.manyGroupJoinContext = new ManyGroupJoinContext<>(entitySQLContext, manyGroupJoinEntityTableExpressionBuilder, entityExpressionBuilder, originalTable, navValue, t1Proxy);
    }

    @Override
    public SQLQueryable<T1Proxy, T1> distinct(boolean useDistinct) {
        this.distinct = useDistinct;
        return this;
    }

    @Override
    public EntitySQLContext getEntitySQLContext() {
        return manyGroupJoinContext.getEntitySQLContext();
    }

    @Override
    public EntityQueryable<T1Proxy, T1> getQueryable() {
        throw new UnsupportedOperationException();
    }

    @Override
    public TableAvailable getOriginalTable() {
        return manyGroupJoinContext.getOriginalTable();
    }

    @Override
    public String getNavValue() {
        return manyGroupJoinContext.getNavValue();
    }

    @Override
    public T1Proxy getProxy() {
        return manyGroupJoinContext.getT1Proxy();
    }

    @Override
    public SQLPredicateQueryable<T1Proxy, T1> where(SQLExpression1<T1Proxy> whereExpression) {
        return new EasyGroupSQLPredicateQueryable<>(whereExpression, manyGroupJoinContext, distinct);
    }

    @Override
    public void any(SQLExpression1<T1Proxy> whereExpression) {
        this.manyGroupJoinContext.anyValue(whereExpression).eq(true);

    }

    @Override
    public void any() {
        this.manyGroupJoinContext.anyValue(null).eq(true);
    }

    @Override
    public void none(SQLExpression1<T1Proxy> whereExpression) {
        this.manyGroupJoinContext.noneValue(whereExpression).eq(true);
    }

    @Override
    public void none() {
        this.manyGroupJoinContext.noneValue(null).eq(true);
    }

    @Override
    public ColumnFunctionCompareComparableBooleanChainExpression<Boolean> anyValue() {
        return this.manyGroupJoinContext.anyValue(null);
    }

    @Override
    public ColumnFunctionCompareComparableBooleanChainExpression<Boolean> noneValue() {
        return this.manyGroupJoinContext.noneValue(null);
    }

    @Override
    public <TMember> ColumnFunctionCompareComparableNumberChainExpression<Long> count(SQLFuncExpression1<T1Proxy, PropTypeColumn<TMember>> columnSelector) {
        ColumnFunctionCompareComparableNumberChainExpression<Long> count = new DefaultSQLGroupQueryable<>(manyGroupJoinContext.getT1Proxy(), this.getEntitySQLContext(), null).distinct(distinct).count(columnSelector);
        String alias = manyGroupJoinContext.getOrAppendGroupProjects(count, "count");
        return new ColumnFunctionCompareComparableNumberChainExpressionImpl<>(this.getEntitySQLContext(), manyGroupJoinContext.getManyGroupJoinTable(), alias, f -> f.anySQLFunction("{0}", c -> c.column(alias)), Long.class);
    }


    @Override
    public ColumnFunctionCompareComparableNumberChainExpression<Long> count() {
        ColumnFunctionCompareComparableNumberChainExpression<Long> count = new DefaultSQLGroupQueryable<>(manyGroupJoinContext.getT1Proxy(), this.getEntitySQLContext(), null).distinct(distinct).count();
        String alias = manyGroupJoinContext.getOrAppendGroupProjects(count, "count");
        return new ColumnFunctionCompareComparableNumberChainExpressionImpl<>(this.getEntitySQLContext(), manyGroupJoinContext.getManyGroupJoinTable(), alias, f -> f.anySQLFunction("{0}", c -> c.column(alias)), Long.class);
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
        ColumnFunctionCompareComparableNumberChainExpression<TMember> sum = new DefaultSQLGroupQueryable<>(manyGroupJoinContext.getT1Proxy(), this.getEntitySQLContext(), null).distinct(distinct).sum(columnSelector);
        String alias = manyGroupJoinContext.getOrAppendGroupProjects(sum, "sum");
        return new ColumnFunctionCompareComparableNumberChainExpressionImpl<>(this.getEntitySQLContext(), manyGroupJoinContext.getManyGroupJoinTable(), alias, f -> f.anySQLFunction("{0}", c -> c.column(alias)), BigDecimal.class);

    }

    @Override
    public <TMember extends Number> ColumnFunctionCompareComparableNumberChainExpression<BigDecimal> sumBigDecimal(SQLFuncExpression1<T1Proxy, ColumnNumberFunctionAvailable<TMember>> columnSelector) {
        return sum(columnSelector).asAnyType(BigDecimal.class);
    }

    @Override
    public <TMember extends Number> ColumnFunctionCompareComparableNumberChainExpression<BigDecimal> avg(SQLFuncExpression1<T1Proxy, ColumnNumberFunctionAvailable<TMember>> columnSelector) {
        ColumnFunctionCompareComparableNumberChainExpression<BigDecimal> avg = new DefaultSQLGroupQueryable<>(manyGroupJoinContext.getT1Proxy(), this.getEntitySQLContext(), null).distinct(distinct).avg(columnSelector);
        String alias = manyGroupJoinContext.getOrAppendGroupProjects(avg, "avg");
        return new ColumnFunctionCompareComparableNumberChainExpressionImpl<>(this.getEntitySQLContext(), manyGroupJoinContext.getManyGroupJoinTable(), alias, f -> f.anySQLFunction("{0}", c -> c.column(alias)), BigDecimal.class);

    }

    @Override
    public <TMember> ColumnFunctionCompareComparableAnyChainExpression<TMember> max(SQLFuncExpression1<T1Proxy, PropTypeColumn<TMember>> columnSelector) {
        ColumnFunctionCompareComparableAnyChainExpression<TMember> max = new DefaultSQLGroupQueryable<>(manyGroupJoinContext.getT1Proxy(), this.getEntitySQLContext(), null).max(columnSelector);
        String alias = manyGroupJoinContext.getOrAppendGroupProjects(max, "max");
        return new ColumnFunctionCompareComparableAnyChainExpressionImpl<>(this.getEntitySQLContext(), manyGroupJoinContext.getManyGroupJoinTable(), alias, f -> f.anySQLFunction("{0}", c -> c.column(alias)), max.getPropertyType());
    }

    @Override
    public <TMember> ColumnFunctionCompareComparableAnyChainExpression<TMember> min(SQLFuncExpression1<T1Proxy, PropTypeColumn<TMember>> columnSelector) {
        ColumnFunctionCompareComparableAnyChainExpression<TMember> min = new DefaultSQLGroupQueryable<>(manyGroupJoinContext.getT1Proxy(), this.getEntitySQLContext(), null).min(columnSelector);
        String alias = manyGroupJoinContext.getOrAppendGroupProjects(min, "min");
        return new ColumnFunctionCompareComparableAnyChainExpressionImpl<>(this.getEntitySQLContext(), manyGroupJoinContext.getManyGroupJoinTable(), alias, f -> f.anySQLFunction("{0}", c -> c.column(alias)), min.getPropertyType());
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
    public <TPropertyProxy extends ProxyEntity<TPropertyProxy, TProperty>, TProperty> TProxy set(SQLQueryable<TPropertyProxy, TProperty> columnProxy, SQLFuncExpression1<TPropertyProxy, ProxyEntity<T1Proxy, T1>> navigateSelectExpression) {
        getEntitySQLContext().accept(new SQLColumnIncludeColumn2Impl<>(columnProxy.getOriginalTable(), columnProxy.getNavValue(), getNavValue(), columnProxy.getQueryable().get1Proxy(), navigateSelectExpression));
        return this.tProxy;
    }

    @Override
    public T1Proxy flatElement(SQLFuncExpression1<T1Proxy, SQLSelectAsExpression> flatAdapterExpression) {

        QueryRuntimeContext runtimeContext = this.getEntitySQLContext().getRuntimeContext();
        T1Proxy tPropertyProxy = getProxy().create(getProxy().getTable(), new ProxyFlatElementEntitySQLContext(this, runtimeContext, flatAdapterExpression));
        tPropertyProxy.setNavValue(getNavValue());
        return tPropertyProxy;
    }
}
