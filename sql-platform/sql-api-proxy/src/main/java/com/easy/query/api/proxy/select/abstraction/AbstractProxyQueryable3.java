package com.easy.query.api.proxy.select.abstraction;

import com.easy.query.api.proxy.select.ProxyQueryable;
import com.easy.query.api.proxy.select.ProxyQueryable3;
import com.easy.query.api.proxy.select.ProxyQueryable4;
import com.easy.query.api.proxy.select.impl.EasyProxyQueryable;
import com.easy.query.api.proxy.select.impl.EasyProxyQueryable4;
import com.easy.query.api.proxy.sql.ProxyAggregateFilter;
import com.easy.query.api.proxy.sql.ProxyAsSelector;
import com.easy.query.api.proxy.sql.ProxyFilter;
import com.easy.query.api.proxy.sql.ProxyGroupSelector;
import com.easy.query.api.proxy.sql.ProxyOrderSelector;
import com.easy.query.api.proxy.sql.impl.ProxyAsSelectorImpl;
import com.easy.query.api.proxy.sql.impl.ProxyFilterImpl;
import com.easy.query.core.basic.api.select.ClientQueryable;
import com.easy.query.core.basic.api.select.ClientQueryable3;
import com.easy.query.core.basic.api.select.ClientQueryable4;
import com.easy.query.core.enums.sharding.ConnectionModeEnum;
import com.easy.query.core.expression.func.ColumnFunction;
import com.easy.query.core.expression.lambda.SQLExpression2;
import com.easy.query.core.expression.lambda.SQLExpression4;
import com.easy.query.core.expression.lambda.SQLExpression5;
import com.easy.query.core.expression.lambda.SQLFuncExpression3;
import com.easy.query.core.proxy.ProxyQuery;
import com.easy.query.core.proxy.SQLColumn;
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
public abstract class AbstractProxyQueryable3<T1Proxy extends ProxyQuery<T1Proxy, T1>, T1, T2Proxy extends ProxyQuery<T2Proxy, T2>, T2, T3Proxy extends ProxyQuery<T3Proxy, T3>, T3>
        extends AbstractProxyQueryable<T1Proxy, T1>
        implements ProxyQueryable3<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3> {


    protected final T2Proxy t2Proxy;
    protected final T3Proxy t3Proxy;
    protected final ClientQueryable3<T1, T2, T3> entityQueryable3;

    public AbstractProxyQueryable3(T1Proxy t1Proxy, T2Proxy t2Proxy, T3Proxy t3Proxy, ClientQueryable3<T1, T2, T3> entityQueryable) {
        super(t1Proxy, entityQueryable);
        this.t2Proxy = t2Proxy.create(entityQueryable.getSQLEntityExpressionBuilder().getTable(1).getEntityTable());
        this.t3Proxy = t3Proxy.create(entityQueryable.getSQLEntityExpressionBuilder().getTable(2).getEntityTable());
        this.entityQueryable3 = entityQueryable;
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
    public ClientQueryable3<T1, T2, T3> getClientQueryable3() {
        return entityQueryable3;
    }

    @Override
    public <T4Proxy extends ProxyQuery<T4Proxy, T4>, T4> ProxyQueryable4<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4> leftJoin(T4Proxy joinProxy, SQLExpression5<ProxyFilter, T1Proxy, T2Proxy, T3Proxy, T4Proxy> on) {
        ClientQueryable4<T1, T2, T3, T4> entityQueryable4 = entityQueryable3.leftJoin(joinProxy.getEntityClass(), (wherePredicate1, wherePredicate2, wherePredicate3, wherePredicate4) -> {
            on.apply(new ProxyFilterImpl(wherePredicate4.getFilter()), get1Proxy(), get2Proxy(), get3Proxy(), joinProxy);
        });
        return new EasyProxyQueryable4<>(get1Proxy(), get2Proxy(), get3Proxy(), joinProxy, entityQueryable4);
    }

    @Override
    public <T4Proxy extends ProxyQuery<T4Proxy, T4>, T4> ProxyQueryable4<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4> leftJoin(ProxyQueryable<T4Proxy, T4> joinQueryable, SQLExpression5<ProxyFilter, T1Proxy, T2Proxy, T3Proxy, T4Proxy> on) {
        ClientQueryable4<T1, T2, T3, T4> entityQueryable4 = entityQueryable3.leftJoin(joinQueryable.getEntityQueryable(), (wherePredicate1, wherePredicate2, wherePredicate3, wherePredicate4) -> {
            on.apply(new ProxyFilterImpl(wherePredicate4.getFilter()), get1Proxy(), get2Proxy(), get3Proxy(), joinQueryable.get1Proxy());
        });
        return new EasyProxyQueryable4<>(get1Proxy(), get2Proxy(), get3Proxy(), joinQueryable.get1Proxy(), entityQueryable4);
    }

    @Override
    public <T4Proxy extends ProxyQuery<T4Proxy, T4>, T4> ProxyQueryable4<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4> rightJoin(T4Proxy joinProxy, SQLExpression5<ProxyFilter, T1Proxy, T2Proxy, T3Proxy, T4Proxy> on) {
        ClientQueryable4<T1, T2, T3, T4> entityQueryable4 = entityQueryable3.rightJoin(joinProxy.getEntityClass(), (wherePredicate1, wherePredicate2, wherePredicate3, wherePredicate4) -> {
            on.apply(new ProxyFilterImpl(wherePredicate4.getFilter()), get1Proxy(), get2Proxy(), get3Proxy(), joinProxy);
        });
        return new EasyProxyQueryable4<>(get1Proxy(), get2Proxy(), get3Proxy(), joinProxy, entityQueryable4);
    }

    @Override
    public <T4Proxy extends ProxyQuery<T4Proxy, T4>, T4> ProxyQueryable4<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4> rightJoin(ProxyQueryable<T4Proxy, T4> joinQueryable, SQLExpression5<ProxyFilter, T1Proxy, T2Proxy, T3Proxy, T4Proxy> on) {
        ClientQueryable4<T1, T2, T3, T4> entityQueryable4 = entityQueryable3.rightJoin(joinQueryable.getEntityQueryable(), (wherePredicate1, wherePredicate2, wherePredicate3, wherePredicate4) -> {
            on.apply(new ProxyFilterImpl(wherePredicate4.getFilter()), get1Proxy(), get2Proxy(), get3Proxy(), joinQueryable.get1Proxy());
        });
        return new EasyProxyQueryable4<>(get1Proxy(), get2Proxy(), get3Proxy(), joinQueryable.get1Proxy(), entityQueryable4);
    }

    @Override
    public <T4Proxy extends ProxyQuery<T4Proxy, T4>, T4> ProxyQueryable4<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4> innerJoin(T4Proxy joinProxy, SQLExpression5<ProxyFilter, T1Proxy, T2Proxy, T3Proxy, T4Proxy> on) {
        ClientQueryable4<T1, T2, T3, T4> entityQueryable4 = entityQueryable3.innerJoin(joinProxy.getEntityClass(), (wherePredicate1, wherePredicate2, wherePredicate3, wherePredicate4) -> {
            on.apply(new ProxyFilterImpl(wherePredicate4.getFilter()), get1Proxy(), get2Proxy(), get3Proxy(), joinProxy);
        });
        return new EasyProxyQueryable4<>(get1Proxy(), get2Proxy(), get3Proxy(), joinProxy, entityQueryable4);
    }

    @Override
    public <T4Proxy extends ProxyQuery<T4Proxy, T4>, T4> ProxyQueryable4<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4> innerJoin(ProxyQueryable<T4Proxy, T4> joinQueryable, SQLExpression5<ProxyFilter, T1Proxy, T2Proxy, T3Proxy, T4Proxy> on) {
        ClientQueryable4<T1, T2, T3, T4> entityQueryable4 = entityQueryable3.innerJoin(joinQueryable.getEntityQueryable(), (wherePredicate1, wherePredicate2, wherePredicate3, wherePredicate4) -> {
            on.apply(new ProxyFilterImpl(wherePredicate4.getFilter()), get1Proxy(), get2Proxy(), get3Proxy(), joinQueryable.get1Proxy());
        });
        return new EasyProxyQueryable4<>(get1Proxy(), get2Proxy(), get3Proxy(), joinQueryable.get1Proxy(), entityQueryable4);
    }

    @Override
    public ProxyQueryable3<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3> where(boolean condition, SQLExpression2<ProxyFilter, T1Proxy> whereExpression) {
        super.where(condition, whereExpression);
        return this;
    }

    @Override
    public ProxyQueryable3<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3> whereById(boolean condition, Object id) {
        super.whereById(condition, id);
        return this;
    }

    @Override
    public <TProperty> ProxyQueryable3<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3> whereByIds(boolean condition, Collection<TProperty> ids) {
        super.whereByIds(condition, ids);
        return this;
    }

    @Override
    public ProxyQueryable3<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3> whereObject(boolean condition, Object object) {
        super.whereObject(condition, object);
        return this;
    }

    @Override
    public <TRProxy extends ProxyQuery<TRProxy, TR>, TR> ProxyQueryable<TRProxy, TR> select(TRProxy trProxy, SQLExpression4<ProxyAsSelector<TRProxy, TR>, T1Proxy, T2Proxy, T3Proxy> selectExpression) {
        ClientQueryable<TR> select = entityQueryable3.select(trProxy.getEntityClass(), (selector1, selector2, selector3) -> {
            selectExpression.apply(new ProxyAsSelectorImpl<>(trProxy, selector3.getAsSelector()), get1Proxy(), get2Proxy(), get3Proxy());
        });
        return new EasyProxyQueryable<>(trProxy, select);
    }

    @Override
    public <TMember extends Number> BigDecimal sumBigDecimalOrDefault(SQLFuncExpression3<T1Proxy, T2Proxy, T3Proxy, SQLColumn<TMember>> columnSelectorExpression, BigDecimal def) {
        SQLColumn<TMember> memberSQLColumn = columnSelectorExpression.apply(get1Proxy(), get2Proxy(), get3Proxy());
        ColumnFunction sumFunction = runtimeContext.getColumnFunctionFactory().createSumFunction(false);
        List<TMember> result = entityQueryable3.selectAggregateList(memberSQLColumn.getTable(), sumFunction, memberSQLColumn.value(), null);
        TMember resultMember = EasyCollectionUtil.firstOrNull(result);
        if (resultMember == null) {
            return def;
        }
        return new BigDecimal(resultMember.toString());
    }

    @Override
    public <TMember extends Number> TMember sumOrDefault(SQLFuncExpression3<T1Proxy, T2Proxy, T3Proxy, SQLColumn<TMember>> columnSelectorExpression, TMember def) {
        SQLColumn<TMember> memberSQLColumn = columnSelectorExpression.apply(get1Proxy(), get2Proxy(), get3Proxy());
        ColumnFunction sumFunction = runtimeContext.getColumnFunctionFactory().createSumFunction(false);
        List<TMember> result = entityQueryable3.selectAggregateList(memberSQLColumn.getTable(), sumFunction, memberSQLColumn.value(), null);
        return EasyCollectionUtil.firstOrDefault(result, def);
    }

    @Override
    public <TMember> TMember maxOrDefault(SQLFuncExpression3<T1Proxy, T2Proxy, T3Proxy, SQLColumn<TMember>> columnSelectorExpression, TMember def) {
        SQLColumn<TMember> memberSQLColumn = columnSelectorExpression.apply(get1Proxy(), get2Proxy(), get3Proxy());
        ColumnFunction maxFunction = runtimeContext.getColumnFunctionFactory().createMaxFunction();
        List<TMember> result = entityQueryable3.selectAggregateList(memberSQLColumn.getTable(), maxFunction, memberSQLColumn.value(), null);
        return EasyCollectionUtil.firstOrDefault(result, def);
    }

    @Override
    public <TMember> TMember minOrDefault(SQLFuncExpression3<T1Proxy, T2Proxy, T3Proxy, SQLColumn<TMember>> columnSelectorExpression, TMember def) {
        SQLColumn<TMember> memberSQLColumn = columnSelectorExpression.apply(get1Proxy(), get2Proxy(), get3Proxy());
        ColumnFunction minFunction = runtimeContext.getColumnFunctionFactory().createMinFunction();
        List<TMember> result = entityQueryable3.selectAggregateList(memberSQLColumn.getTable(), minFunction, memberSQLColumn.value(), null);
        return EasyCollectionUtil.firstOrDefault(result, def);
    }

    @Override
    public <TMember extends Number, TResult extends Number> TResult avgOrDefault(SQLFuncExpression3<T1Proxy, T2Proxy, T3Proxy, SQLColumn<TMember>> columnSelectorExpression, TResult def, Class<TResult> resultClass) {
        SQLColumn<TMember> memberSQLColumn = columnSelectorExpression.apply(get1Proxy(), get2Proxy(), get3Proxy());
        ColumnFunction avgFunction = runtimeContext.getColumnFunctionFactory().createAvgFunction(false);
        List<TResult> result = entityQueryable3.selectAggregateList(memberSQLColumn.getTable(), avgFunction, memberSQLColumn.value(), resultClass);
        return EasyCollectionUtil.firstOrDefault(result, def);
    }

    @Override
    public ProxyQueryable3<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3> orderByAsc(boolean condition, SQLExpression2<ProxyOrderSelector, T1Proxy> selectExpression) {
        super.orderByAsc(condition, selectExpression);
        return this;
    }

    @Override
    public ProxyQueryable3<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3> orderByDesc(boolean condition, SQLExpression2<ProxyOrderSelector, T1Proxy> selectExpression) {
        super.orderByDesc(condition, selectExpression);
        return this;
    }

    @Override
    public ProxyQueryable3<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3> groupBy(boolean condition, SQLExpression2<ProxyGroupSelector, T1Proxy> selectExpression) {
        super.groupBy(condition, selectExpression);
        return this;
    }

    @Override
    public ProxyQueryable3<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3> having(boolean condition, SQLExpression2<ProxyAggregateFilter, T1Proxy> aggregateFilterSQLExpression) {
        super.having(condition, aggregateFilterSQLExpression);
        return this;
    }

    @Override
    public ProxyQueryable3<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3> limit(boolean condition, long offset, long rows) {
        super.limit(condition, offset, rows);
        return this;
    }

    @Override
    public ProxyQueryable3<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3> distinct(boolean condition) {
        super.distinct(condition);
        return this;
    }

    @Override
    public ProxyQueryable3<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3> disableLogicDelete() {
        super.disableLogicDelete();
        return this;
    }

    @Override
    public ProxyQueryable3<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3> enableLogicDelete() {
        super.enableLogicDelete();
        return this;
    }

    @Override
    public ProxyQueryable3<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3> useLogicDelete(boolean enable) {
        super.useLogicDelete(enable);
        return this;
    }

    @Override
    public ProxyQueryable3<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3> noInterceptor() {
        super.noInterceptor();
        return this;
    }

    @Override
    public ProxyQueryable3<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3> useInterceptor(String name) {
        super.useInterceptor(name);
        return this;
    }

    @Override
    public ProxyQueryable3<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3> noInterceptor(String name) {
        super.noInterceptor(name);
        return this;
    }

    @Override
    public ProxyQueryable3<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3> useInterceptor() {
        super.useInterceptor();
        return this;
    }

    @Override
    public ProxyQueryable3<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3> asTracking() {
        super.asTracking();
        return this;
    }

    @Override
    public ProxyQueryable3<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3> asNoTracking() {
        super.asNoTracking();
        return this;
    }

    @Override
    public ProxyQueryable3<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3> queryLargeColumn(boolean queryLarge) {
        super.queryLargeColumn(queryLarge);
        return this;
    }

    @Override
    public ProxyQueryable3<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3> useShardingConfigure(int maxShardingQueryLimit, ConnectionModeEnum connectionMode) {
        super.useShardingConfigure(maxShardingQueryLimit, connectionMode);
        return this;
    }

    @Override
    public ProxyQueryable3<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3> useMaxShardingQueryLimit(int maxShardingQueryLimit) {
        super.useMaxShardingQueryLimit(maxShardingQueryLimit);
        return this;
    }

    @Override
    public ProxyQueryable3<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3> useConnectionMode(ConnectionModeEnum connectionMode) {
        super.useConnectionMode(connectionMode);
        return this;
    }

    @Override
    public ProxyQueryable3<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3> asTable(Function<String, String> tableNameAs) {
        super.asTable(tableNameAs);
        return this;
    }

    @Override
    public ProxyQueryable3<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3> asSchema(Function<String, String> schemaAs) {
        super.asSchema(schemaAs);
        return this;
    }

    @Override
    public ProxyQueryable3<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3> asAlias(String alias) {
        super.asAlias(alias);
        return this;
    }
}
