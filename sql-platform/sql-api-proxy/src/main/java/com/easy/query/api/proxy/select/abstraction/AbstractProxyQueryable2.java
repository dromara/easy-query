package com.easy.query.api.proxy.select.abstraction;

import com.easy.query.api.proxy.core.ProxyQuery;
import com.easy.query.api.proxy.core.base.SQLColumn;
import com.easy.query.api.proxy.select.ProxyQueryable;
import com.easy.query.api.proxy.select.ProxyQueryable2;
import com.easy.query.api.proxy.select.ProxyQueryable3;
import com.easy.query.api.proxy.select.impl.EasyProxyQueryable;
import com.easy.query.api.proxy.sql.ProxyAggregateFilter;
import com.easy.query.api.proxy.sql.ProxyAsSelector;
import com.easy.query.api.proxy.sql.ProxyFilter;
import com.easy.query.api.proxy.sql.ProxyGroupSelector;
import com.easy.query.api.proxy.sql.ProxyOrderSelector;
import com.easy.query.api.proxy.sql.impl.ProxyAsSelectorImpl;
import com.easy.query.core.basic.api.select.ClientQueryable;
import com.easy.query.core.basic.api.select.ClientQueryable2;
import com.easy.query.core.enums.sharding.ConnectionModeEnum;
import com.easy.query.core.expression.func.ColumnFunction;
import com.easy.query.core.expression.lambda.SQLExpression2;
import com.easy.query.core.expression.lambda.SQLExpression3;
import com.easy.query.core.expression.lambda.SQLExpression4;
import com.easy.query.core.expression.lambda.SQLFuncExpression2;
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
public abstract class AbstractProxyQueryable2<T1Proxy extends ProxyQuery<T1Proxy, T1>, T1, T2Proxy extends ProxyQuery<T2Proxy, T2>, T2>
        extends AbstractProxyQueryable<T1Proxy, T1>
        implements ProxyQueryable2<T1Proxy, T1, T2Proxy, T2> {


    protected final T2Proxy t2Proxy;
    protected final ClientQueryable2<T1, T2> entityQueryable2;

    public AbstractProxyQueryable2(T1Proxy t1Proxy, T2Proxy t2Proxy, ClientQueryable2<T1, T2> entityQueryable) {
        super(t1Proxy, entityQueryable);
        this.t2Proxy = t2Proxy.create(entityQueryable.getSQLEntityExpressionBuilder().getTable(1).getEntityTable());
        this.entityQueryable2 = entityQueryable;
    }


    @Override
    public T2Proxy get2Proxy() {
        return t2Proxy;
    }

    @Override
    public ClientQueryable2<T1, T2> getClientQueryable2() {
        return entityQueryable2;
    }

    @Override
    public <T3Proxy extends ProxyQuery<T3Proxy, T3>, T3> ProxyQueryable3<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3> leftJoin(T3Proxy t3Proxy, SQLExpression4<T1Proxy, T2Proxy, T3Proxy, ProxyFilter> on) {
        return null;
    }

    @Override
    public <T3Proxy extends ProxyQuery<T3Proxy, T3>, T3> ProxyQueryable3<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3> leftJoin(ProxyQueryable<T3Proxy, T3> joinQueryable, SQLExpression4<T1Proxy, T2Proxy, T3Proxy, ProxyFilter> on) {
        return null;
    }

    @Override
    public <T3Proxy extends ProxyQuery<T3Proxy, T3>, T3> ProxyQueryable3<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3> rightJoin(T3Proxy t3Proxy, SQLExpression4<T1Proxy, T2Proxy, T3Proxy, ProxyFilter> on) {
        return null;
    }

    @Override
    public <T3Proxy extends ProxyQuery<T3Proxy, T3>, T3> ProxyQueryable3<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3> rightJoin(ProxyQueryable<T3Proxy, T3> joinQueryable, SQLExpression4<T1Proxy, T2Proxy, T3Proxy, ProxyFilter> on) {
        return null;
    }

    @Override
    public <T3Proxy extends ProxyQuery<T3Proxy, T3>, T3> ProxyQueryable3<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3> innerJoin(T3Proxy t3Proxy, SQLExpression4<T1Proxy, T2Proxy, T3Proxy, ProxyFilter> on) {
        return null;
    }

    @Override
    public <T3Proxy extends ProxyQuery<T3Proxy, T3>, T3> ProxyQueryable3<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3> innerJoin(ProxyQueryable<T3Proxy, T3> joinQueryable, SQLExpression4<T1Proxy, T2Proxy, T3Proxy, ProxyFilter> on) {
        return null;
    }

    @Override
    public ProxyQueryable2<T1Proxy, T1, T2Proxy, T2> where(boolean condition, SQLExpression2<T1Proxy, ProxyFilter> whereExpression) {
        super.where(condition, whereExpression);
        return this;
    }

    @Override
    public ProxyQueryable2<T1Proxy, T1, T2Proxy, T2> whereById(boolean condition, Object id) {
        super.whereById(condition, id);
        return this;
    }

    @Override
    public <TProperty> ProxyQueryable2<T1Proxy, T1, T2Proxy, T2> whereByIds(boolean condition, Collection<TProperty> ids) {
        super.whereByIds(condition, ids);
        return this;
    }

    @Override
    public ProxyQueryable2<T1Proxy, T1, T2Proxy, T2> whereObject(boolean condition, Object object) {
        super.whereObject(condition, object);
        return this;
    }

    @Override
    public <TRProxy extends ProxyQuery<TRProxy, TR>, TR> ProxyQueryable<TRProxy, TR> select(TRProxy trProxy, SQLExpression3<T1Proxy, T2Proxy, ProxyAsSelector<TRProxy, TR>> selectExpression) {
        ClientQueryable<TR> select = entityQueryable2.select(trProxy.getEntityClass(), (selector1, selector2) -> {
            selectExpression.apply(get1Proxy(), get2Proxy(), new ProxyAsSelectorImpl<>(selector2.getAsSelector()));
        });
        return new EasyProxyQueryable<>(trProxy, select);
    }

    @Override
    public <TMember extends Number> BigDecimal sumBigDecimalOrDefault(SQLFuncExpression2<T1Proxy, T2Proxy, SQLColumn<TMember>> columnSelectorExpression, BigDecimal def) {
        SQLColumn<TMember> memberSQLColumn = columnSelectorExpression.apply(get1Proxy(), get2Proxy());
        ColumnFunction sumFunction = runtimeContext.getColumnFunctionFactory().createSumFunction(false);
        List<TMember> result = entityQueryable2.selectAggregateList(memberSQLColumn.getTable(), sumFunction, memberSQLColumn.value(), null);
        TMember resultMember = EasyCollectionUtil.firstOrNull(result);
        if (resultMember == null) {
            return def;
        }
        return new BigDecimal(resultMember.toString());
    }

    @Override
    public <TMember extends Number> TMember sumOrDefault(SQLFuncExpression2<T1Proxy, T2Proxy, SQLColumn<TMember>> columnSelectorExpression, TMember def) {
        SQLColumn<TMember> memberSQLColumn = columnSelectorExpression.apply(get1Proxy(), get2Proxy());
        ColumnFunction sumFunction = runtimeContext.getColumnFunctionFactory().createSumFunction(false);
        List<TMember> result = entityQueryable2.selectAggregateList(memberSQLColumn.getTable(), sumFunction, memberSQLColumn.value(), null);
        return EasyCollectionUtil.firstOrDefault(result, def);
    }

    @Override
    public <TMember> TMember maxOrDefault(SQLFuncExpression2<T1Proxy, T2Proxy, SQLColumn<TMember>> columnSelectorExpression, TMember def) {
        SQLColumn<TMember> memberSQLColumn = columnSelectorExpression.apply(get1Proxy(), get2Proxy());
        ColumnFunction maxFunction = runtimeContext.getColumnFunctionFactory().createMaxFunction();
        List<TMember> result = entityQueryable2.selectAggregateList(memberSQLColumn.getTable(), maxFunction, memberSQLColumn.value(), null);
        return EasyCollectionUtil.firstOrDefault(result, def);
    }

    @Override
    public <TMember> TMember minOrDefault(SQLFuncExpression2<T1Proxy, T2Proxy, SQLColumn<TMember>> columnSelectorExpression, TMember def) {
        SQLColumn<TMember> memberSQLColumn = columnSelectorExpression.apply(get1Proxy(), get2Proxy());
        ColumnFunction minFunction = runtimeContext.getColumnFunctionFactory().createMinFunction();
        List<TMember> result = entityQueryable2.selectAggregateList(memberSQLColumn.getTable(), minFunction, memberSQLColumn.value(), null);
        return EasyCollectionUtil.firstOrDefault(result, def);
    }

    @Override
    public <TMember extends Number, TResult extends Number> TResult avgOrDefault(SQLFuncExpression2<T1Proxy, T2Proxy, SQLColumn<TMember>> columnSelectorExpression, TResult def, Class<TResult> resultClass) {
        SQLColumn<TMember> memberSQLColumn = columnSelectorExpression.apply(get1Proxy(), get2Proxy());
        ColumnFunction avgFunction = runtimeContext.getColumnFunctionFactory().createAvgFunction(false);
        List<TResult> result = entityQueryable2.selectAggregateList(memberSQLColumn.getTable(), avgFunction, memberSQLColumn.value(), resultClass);
        return EasyCollectionUtil.firstOrDefault(result, def);
    }

    @Override
    public ProxyQueryable2<T1Proxy, T1, T2Proxy, T2> orderByAsc(boolean condition, SQLExpression2<T1Proxy, ProxyOrderSelector> selectExpression) {
        super.orderByAsc(condition, selectExpression);
        return this;
    }

    @Override
    public ProxyQueryable2<T1Proxy, T1, T2Proxy, T2> orderByDesc(boolean condition, SQLExpression2<T1Proxy, ProxyOrderSelector> selectExpression) {
        super.orderByDesc(condition, selectExpression);
        return this;
    }

    @Override
    public ProxyQueryable2<T1Proxy, T1, T2Proxy, T2> groupBy(boolean condition, SQLExpression2<T1Proxy, ProxyGroupSelector> selectExpression) {
         super.groupBy(condition, selectExpression);
        return this;
    }

    @Override
    public ProxyQueryable2<T1Proxy, T1, T2Proxy, T2> having(boolean condition, SQLExpression2<T1Proxy, ProxyAggregateFilter> aggregateFilterSQLExpression) {
         super.having(condition, aggregateFilterSQLExpression);
        return this;
    }

    @Override
    public ProxyQueryable2<T1Proxy, T1, T2Proxy, T2> limit(boolean condition, long offset, long rows) {
        super.limit(condition, offset, rows);
        return this;
    }

    @Override
    public ProxyQueryable2<T1Proxy, T1, T2Proxy, T2> distinct(boolean condition) {
        super.distinct(condition);
        return this;
    }

    @Override
    public ProxyQueryable2<T1Proxy, T1, T2Proxy, T2> disableLogicDelete() {
        super.disableLogicDelete();
        return this;
    }

    @Override
    public ProxyQueryable2<T1Proxy, T1, T2Proxy, T2> enableLogicDelete() {
        super.enableLogicDelete();
        return this;
    }

    @Override
    public ProxyQueryable2<T1Proxy, T1, T2Proxy, T2> useLogicDelete(boolean enable) {
        super.useLogicDelete(enable);
        return this;
    }

    @Override
    public ProxyQueryable2<T1Proxy, T1, T2Proxy, T2> noInterceptor() {
        super.noInterceptor();
        return this;
    }

    @Override
    public ProxyQueryable2<T1Proxy, T1, T2Proxy, T2> useInterceptor(String name) {
        super.useInterceptor(name);
        return this;
    }

    @Override
    public ProxyQueryable2<T1Proxy, T1, T2Proxy, T2> noInterceptor(String name) {
        super.noInterceptor(name);
        return this;
    }

    @Override
    public ProxyQueryable2<T1Proxy, T1, T2Proxy, T2> useInterceptor() {
        super.useInterceptor();
        return this;
    }

    @Override
    public ProxyQueryable2<T1Proxy, T1, T2Proxy, T2> asTracking() {
        super.asTracking();
        return this;
    }

    @Override
    public ProxyQueryable2<T1Proxy, T1, T2Proxy, T2> asNoTracking() {
        super.asNoTracking();
        return this;
    }

    @Override
    public ProxyQueryable2<T1Proxy, T1, T2Proxy, T2> queryLargeColumn(boolean queryLarge) {
        super.queryLargeColumn(queryLarge);
        return this;
    }

    @Override
    public ProxyQueryable2<T1Proxy, T1, T2Proxy, T2> useShardingConfigure(int maxShardingQueryLimit, ConnectionModeEnum connectionMode) {
        super.useShardingConfigure(maxShardingQueryLimit, connectionMode);
        return this;
    }

    @Override
    public ProxyQueryable2<T1Proxy, T1, T2Proxy, T2> useMaxShardingQueryLimit(int maxShardingQueryLimit) {
        super.useMaxShardingQueryLimit(maxShardingQueryLimit);
        return this;
    }

    @Override
    public ProxyQueryable2<T1Proxy, T1, T2Proxy, T2> useConnectionMode(ConnectionModeEnum connectionMode) {
        super.useConnectionMode(connectionMode);
        return this;
    }

    @Override
    public ProxyQueryable2<T1Proxy, T1, T2Proxy, T2> asTable(Function<String, String> tableNameAs) {
        super.asTable(tableNameAs);
        return this;
    }

    @Override
    public ProxyQueryable2<T1Proxy, T1, T2Proxy, T2> asSchema(Function<String, String> schemaAs) {
        super.asSchema(schemaAs);
        return this;
    }

    @Override
    public ProxyQueryable2<T1Proxy, T1, T2Proxy, T2> asAlias(String alias) {
        super.asAlias(alias);
        return this;
    }
}
