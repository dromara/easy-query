package com.easy.query.api.proxy.select.abstraction;

import com.easy.query.api.proxy.core.ProxyQuery;
import com.easy.query.api.proxy.core.base.SQLColumn;
import com.easy.query.api.proxy.select.ProxyQueryable;
import com.easy.query.api.proxy.select.ProxyQueryable4;
import com.easy.query.api.proxy.select.impl.EasyProxyQueryable;
import com.easy.query.api.proxy.sql.ProxyAggregateFilter;
import com.easy.query.api.proxy.sql.ProxyAsSelector;
import com.easy.query.api.proxy.sql.ProxyFilter;
import com.easy.query.api.proxy.sql.ProxyGroupSelector;
import com.easy.query.api.proxy.sql.ProxyOrderSelector;
import com.easy.query.api.proxy.sql.impl.ProxyAsSelectorImpl;
import com.easy.query.core.basic.api.select.ClientQueryable;
import com.easy.query.core.basic.api.select.ClientQueryable4;
import com.easy.query.core.enums.sharding.ConnectionModeEnum;
import com.easy.query.core.expression.func.ColumnFunction;
import com.easy.query.core.expression.lambda.SQLExpression2;
import com.easy.query.core.expression.lambda.SQLExpression5;
import com.easy.query.core.expression.lambda.SQLFuncExpression4;
import com.easy.query.core.util.EasyCollectionUtil;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;

/**
 * create time 2023/6/23 22:08
 * 文件说明
 *
 * @author xuejiaming
 */
public abstract   class AbstractProxyQueryable4<T1Proxy extends ProxyQuery<T1Proxy, T1>, T1, T2Proxy extends ProxyQuery<T2Proxy, T2>, T2, T3Proxy extends ProxyQuery<T3Proxy, T3>, T3, T4Proxy extends ProxyQuery<T4Proxy, T4>, T4>
        extends AbstractProxyQueryable<T1Proxy, T1>
        implements ProxyQueryable4<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4> {


    protected final T2Proxy t2Proxy;
    protected final T3Proxy t3Proxy;
    protected final T4Proxy t4Proxy;
    protected final ClientQueryable4<T1, T2,T3,T4> entityQueryable4;

    public AbstractProxyQueryable4(T1Proxy t1Proxy, T2Proxy t2Proxy, T3Proxy t3Proxy,T4Proxy t4Proxy, ClientQueryable4<T1, T2,T3,T4> entityQueryable) {
        super(t1Proxy, entityQueryable);
        this.t2Proxy = t2Proxy.create(entityQueryable.getSQLEntityExpressionBuilder().getTable(1).getEntityTable());
        this.t3Proxy = t3Proxy.create(entityQueryable.getSQLEntityExpressionBuilder().getTable(2).getEntityTable());
        this.t4Proxy = t4Proxy.create(entityQueryable.getSQLEntityExpressionBuilder().getTable(3).getEntityTable());
        this.entityQueryable4 = entityQueryable;
    }
    @Override
    public T2Proxy get2Proxy() {
        return t2Proxy;
    }

    @Override
    public T3Proxy get3Proxy() {
        return t3Proxy;
    }
    @Override
    public T4Proxy get4Proxy() {
        return t4Proxy;
    }

    @Override
    public ClientQueryable4<T1, T2, T3,T4> getClientQueryable4() {
        return entityQueryable4;
    }

    @Override
    public ProxyQueryable4<T1Proxy, T1, T2Proxy, T2,T3Proxy,T3,T4Proxy,T4>where(boolean condition, SQLExpression2<T1Proxy, ProxyFilter> whereExpression) {
        super.where(condition, whereExpression);
        return this;
    }

    @Override
    public ProxyQueryable4<T1Proxy, T1, T2Proxy, T2,T3Proxy,T3,T4Proxy,T4>whereById(boolean condition, Object id) {
        super.whereById(condition, id);
        return this;
    }

    @Override
    public <TProperty> ProxyQueryable4<T1Proxy, T1, T2Proxy, T2,T3Proxy,T3,T4Proxy,T4>whereByIds(boolean condition, Collection<TProperty> ids) {
        super.whereByIds(condition, ids);
        return this;
    }

    @Override
    public ProxyQueryable4<T1Proxy, T1, T2Proxy, T2,T3Proxy,T3,T4Proxy,T4>whereObject(boolean condition, Object object) {
        super.whereObject(condition, object);
        return this;
    }

    @Override
    public <TRProxy extends ProxyQuery<TRProxy, TR>, TR> ProxyQueryable<TRProxy, TR> select(TRProxy trProxy, SQLExpression5<T1Proxy, T2Proxy,T3Proxy,T4Proxy, ProxyAsSelector<TRProxy, TR>> selectExpression) {
        ClientQueryable<TR> select = entityQueryable4.select(trProxy.getEntityClass(), (selector1, selector2, selector3, selector4) -> {
            selectExpression.apply(get1Proxy(), get2Proxy(),get3Proxy(),get4Proxy(), new ProxyAsSelectorImpl<>(selector4.getAsSelector()));
        });
        return new EasyProxyQueryable<>(trProxy, select);
    }

    @Override
    public <TMember extends Number> BigDecimal sumBigDecimalOrDefault(SQLFuncExpression4<T1Proxy, T2Proxy, T3Proxy,  T4Proxy, SQLColumn<TMember>> columnSelectorExpression, BigDecimal def) {
        SQLColumn<TMember> memberSQLColumn = columnSelectorExpression.apply(get1Proxy(), get2Proxy(), get3Proxy(),get4Proxy());
        ColumnFunction sumFunction = runtimeContext.getColumnFunctionFactory().createSumFunction(false);
        List<TMember> result = entityQueryable4.selectAggregateList(memberSQLColumn.getTable(), sumFunction, memberSQLColumn.value(), null);
        TMember resultMember = EasyCollectionUtil.firstOrNull(result);
        if (resultMember == null) {
            return def;
        }
        return new BigDecimal(resultMember.toString());
    }

    @Override
    public <TMember extends Number> TMember sumOrDefault(SQLFuncExpression4<T1Proxy, T2Proxy, T3Proxy,  T4Proxy, SQLColumn<TMember>> columnSelectorExpression, TMember def) {
        SQLColumn<TMember> memberSQLColumn = columnSelectorExpression.apply(get1Proxy(), get2Proxy(), get3Proxy(),get4Proxy());
        ColumnFunction sumFunction = runtimeContext.getColumnFunctionFactory().createSumFunction(false);
        List<TMember> result = entityQueryable4.selectAggregateList(memberSQLColumn.getTable(), sumFunction, memberSQLColumn.value(), null);
        return EasyCollectionUtil.firstOrDefault(result, def);
    }

    @Override
    public <TMember> TMember maxOrDefault(SQLFuncExpression4<T1Proxy, T2Proxy, T3Proxy,  T4Proxy, SQLColumn<TMember>> columnSelectorExpression, TMember def) {
        SQLColumn<TMember> memberSQLColumn = columnSelectorExpression.apply(get1Proxy(), get2Proxy(), get3Proxy(),get4Proxy());
        ColumnFunction maxFunction = runtimeContext.getColumnFunctionFactory().createMaxFunction();
        List<TMember> result = entityQueryable4.selectAggregateList(memberSQLColumn.getTable(), maxFunction, memberSQLColumn.value(), null);
        return EasyCollectionUtil.firstOrDefault(result, def);
    }

    @Override
    public <TMember> TMember minOrDefault(SQLFuncExpression4<T1Proxy, T2Proxy, T3Proxy,  T4Proxy, SQLColumn<TMember>> columnSelectorExpression, TMember def) {
        SQLColumn<TMember> memberSQLColumn = columnSelectorExpression.apply(get1Proxy(), get2Proxy(), get3Proxy(),get4Proxy());
        ColumnFunction minFunction = runtimeContext.getColumnFunctionFactory().createMinFunction();
        List<TMember> result = entityQueryable4.selectAggregateList(memberSQLColumn.getTable(), minFunction, memberSQLColumn.value(), null);
        return EasyCollectionUtil.firstOrDefault(result, def);
    }

    @Override
    public <TMember extends Number, TResult extends Number> TResult avgOrDefault(SQLFuncExpression4<T1Proxy, T2Proxy, T3Proxy,  T4Proxy, SQLColumn<TMember>> columnSelectorExpression, TResult def, Class<TResult> resultClass) {
        SQLColumn<TMember> memberSQLColumn = columnSelectorExpression.apply(get1Proxy(), get2Proxy(), get3Proxy(),get4Proxy());
        ColumnFunction avgFunction = runtimeContext.getColumnFunctionFactory().createAvgFunction(false);
        List<TResult> result = entityQueryable4.selectAggregateList(memberSQLColumn.getTable(), avgFunction, memberSQLColumn.value(), resultClass);
        return EasyCollectionUtil.firstOrDefault(result, def);
    }

    @Override
    public ProxyQueryable4<T1Proxy, T1, T2Proxy, T2,T3Proxy,T3,T4Proxy,T4>orderByAsc(boolean condition, SQLExpression2<T1Proxy, ProxyOrderSelector> selectExpression) {
        super.orderByAsc(condition, selectExpression);
        return this;
    }

    @Override
    public ProxyQueryable4<T1Proxy, T1, T2Proxy, T2,T3Proxy,T3,T4Proxy,T4>orderByDesc(boolean condition, SQLExpression2<T1Proxy, ProxyOrderSelector> selectExpression) {
        super.orderByDesc(condition, selectExpression);
        return this;
    }

    @Override
    public ProxyQueryable4<T1Proxy, T1, T2Proxy, T2,T3Proxy,T3,T4Proxy,T4>groupBy(boolean condition, SQLExpression2<T1Proxy, ProxyGroupSelector> selectExpression) {
        super.groupBy(condition, selectExpression);
        return this;
    }

    @Override
    public ProxyQueryable4<T1Proxy, T1, T2Proxy, T2,T3Proxy,T3,T4Proxy,T4>having(boolean condition, SQLExpression2<T1Proxy, ProxyAggregateFilter> aggregateFilterSQLExpression) {
        super.having(condition, aggregateFilterSQLExpression);
        return this;
    }

    @Override
    public ProxyQueryable4<T1Proxy, T1, T2Proxy, T2,T3Proxy,T3,T4Proxy,T4>limit(boolean condition, long offset, long rows) {
        super.limit(condition, offset, rows);
        return this;
    }

    @Override
    public ProxyQueryable4<T1Proxy, T1, T2Proxy, T2,T3Proxy,T3,T4Proxy,T4>distinct(boolean condition) {
        super.distinct(condition);
        return this;
    }

    @Override
    public ProxyQueryable4<T1Proxy, T1, T2Proxy, T2,T3Proxy,T3,T4Proxy,T4>disableLogicDelete() {
        super.disableLogicDelete();
        return this;
    }

    @Override
    public ProxyQueryable4<T1Proxy, T1, T2Proxy, T2,T3Proxy,T3,T4Proxy,T4>enableLogicDelete() {
        super.enableLogicDelete();
        return this;
    }

    @Override
    public ProxyQueryable4<T1Proxy, T1, T2Proxy, T2,T3Proxy,T3,T4Proxy,T4>useLogicDelete(boolean enable) {
        super.useLogicDelete(enable);
        return this;
    }

    @Override
    public ProxyQueryable4<T1Proxy, T1, T2Proxy, T2,T3Proxy,T3,T4Proxy,T4>noInterceptor() {
        super.noInterceptor();
        return this;
    }

    @Override
    public ProxyQueryable4<T1Proxy, T1, T2Proxy, T2,T3Proxy,T3,T4Proxy,T4>useInterceptor(String name) {
        super.useInterceptor(name);
        return this;
    }

    @Override
    public ProxyQueryable4<T1Proxy, T1, T2Proxy, T2,T3Proxy,T3,T4Proxy,T4>noInterceptor(String name) {
        super.noInterceptor(name);
        return this;
    }

    @Override
    public ProxyQueryable4<T1Proxy, T1, T2Proxy, T2,T3Proxy,T3,T4Proxy,T4>useInterceptor() {
        super.useInterceptor();
        return this;
    }

    @Override
    public ProxyQueryable4<T1Proxy, T1, T2Proxy, T2,T3Proxy,T3,T4Proxy,T4>asTracking() {
        super.asTracking();
        return this;
    }

    @Override
    public ProxyQueryable4<T1Proxy, T1, T2Proxy, T2,T3Proxy,T3,T4Proxy,T4>asNoTracking() {
        super.asNoTracking();
        return this;
    }

    @Override
    public ProxyQueryable4<T1Proxy, T1, T2Proxy, T2,T3Proxy,T3,T4Proxy,T4>queryLargeColumn(boolean queryLarge) {
        super.queryLargeColumn(queryLarge);
        return this;
    }

    @Override
    public ProxyQueryable4<T1Proxy, T1, T2Proxy, T2,T3Proxy,T3,T4Proxy,T4>useShardingConfigure(int maxShardingQueryLimit, ConnectionModeEnum connectionMode) {
        super.useShardingConfigure(maxShardingQueryLimit, connectionMode);
        return this;
    }

    @Override
    public ProxyQueryable4<T1Proxy, T1, T2Proxy, T2,T3Proxy,T3,T4Proxy,T4>useMaxShardingQueryLimit(int maxShardingQueryLimit) {
        super.useMaxShardingQueryLimit(maxShardingQueryLimit);
        return this;
    }

    @Override
    public ProxyQueryable4<T1Proxy, T1, T2Proxy, T2,T3Proxy,T3,T4Proxy,T4>useConnectionMode(ConnectionModeEnum connectionMode) {
        super.useConnectionMode(connectionMode);
        return this;
    }

    @Override
    public ProxyQueryable4<T1Proxy, T1, T2Proxy, T2,T3Proxy,T3,T4Proxy,T4> asTable(Function<String, String> tableNameAs) {
        super.asTable(tableNameAs);
        return this;
    }

    @Override
    public ProxyQueryable4<T1Proxy, T1, T2Proxy, T2,T3Proxy,T3,T4Proxy,T4>asSchema(Function<String, String> schemaAs) {
        super.asSchema(schemaAs);
        return this;
    }

    @Override
    public ProxyQueryable4<T1Proxy, T1, T2Proxy, T2,T3Proxy,T3,T4Proxy,T4>asAlias(String alias) {
        super.asAlias(alias);
        return this;
    }
}

