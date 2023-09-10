package com.easy.query.api.proxy.select.extension.queryable4.override;

import com.easy.query.api.proxy.select.ProxyQueryable;
import com.easy.query.api.proxy.select.ProxyQueryable4;
import com.easy.query.api.proxy.select.abstraction.AbstractProxyQueryable;
import com.easy.query.api.proxy.select.extension.queryable.sql.MultiProxyFilter1;
import com.easy.query.api.proxy.select.extension.queryable4.ProxyQueryable4Available;
import com.easy.query.api.proxy.sql.ProxyAggregateFilter;
import com.easy.query.api.proxy.sql.ProxyGroupSelector;
import com.easy.query.api.proxy.sql.ProxyNavigateInclude;
import com.easy.query.api.proxy.sql.ProxyOrderSelector;
import com.easy.query.core.api.dynamic.sort.ObjectSort;
import com.easy.query.core.basic.api.select.ClientQueryable4;
import com.easy.query.core.enums.sharding.ConnectionModeEnum;
import com.easy.query.core.expression.builder.core.ConditionAccepter;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.lambda.SQLExpression2;
import com.easy.query.core.expression.lambda.SQLFuncExpression2;
import com.easy.query.core.proxy.ProxyEntity;

import java.util.Collection;
import java.util.function.Function;

/**
 * create time 2023/8/16 07:52
 * 文件说明
 *
 * @author xuejiaming
 */
public abstract class AbstractOverrideProxyQueryable4<T1Proxy extends ProxyEntity<T1Proxy, T1>, T1,
        T2Proxy extends ProxyEntity<T2Proxy, T2>, T2,
        T3Proxy extends ProxyEntity<T3Proxy, T3>, T3,
        T4Proxy extends ProxyEntity<T4Proxy, T4>, T4> extends AbstractProxyQueryable<T1Proxy, T1>
        implements ProxyQueryable4Available<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4> {

    protected final ClientQueryable4<T1, T2, T3, T4> entityQueryable4;

    public AbstractOverrideProxyQueryable4(T1Proxy t1Proxy, ClientQueryable4<T1, T2, T3, T4> entityQueryable4) {
        super(t1Proxy, entityQueryable4);
        this.entityQueryable4 = entityQueryable4;
    }

    @Override
    public ProxyQueryable4<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4> orderBy(boolean condition, SQLExpression2<ProxyOrderSelector, T1Proxy> selectExpression, boolean asc) {
        super.orderBy(condition, selectExpression, asc);
        return getQueryable4();
    }

    @Override
    public ProxyQueryable4<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4> orderByObject(boolean condition, ObjectSort configuration) {
        super.orderByObject(condition, configuration);
        return getQueryable4();
    }

    @Override
    public ProxyQueryable4<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4> whereById(boolean condition, Object id) {
        super.whereById(condition, id);
        return getQueryable4();
    }

    @Override
    public <TProperty> ProxyQueryable4<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4> whereByIds(boolean condition, Collection<TProperty> ids) {
        super.whereByIds(condition, ids);
        return getQueryable4();
    }

    @Override
    public ProxyQueryable4<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4> whereObject(boolean condition, Object object) {
        super.whereObject(condition, object);
        return getQueryable4();
    }

    @Override
    public ProxyQueryable4<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4> where(boolean condition, SQLExpression1<MultiProxyFilter1<T1Proxy>> whereExpression) {
        super.where(condition, whereExpression);
        return getQueryable4();
    }

    @Override
    public ProxyQueryable4<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4> groupBy(boolean condition, SQLExpression2<ProxyGroupSelector, T1Proxy> selectExpression) {
        super.groupBy(condition, selectExpression);
        return getQueryable4();
    }

    @Override
    public ProxyQueryable4<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4> having(boolean condition, SQLExpression2<ProxyAggregateFilter, T1Proxy> predicateExpression) {
        super.having(condition, predicateExpression);
        return getQueryable4();
    }

    @Override
    public ProxyQueryable4<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4> orderByAsc(boolean condition, SQLExpression2<ProxyOrderSelector, T1Proxy> selectExpression) {
        super.orderByAsc(condition, selectExpression);
        return getQueryable4();
    }

    @Override
    public ProxyQueryable4<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4> orderByDesc(boolean condition, SQLExpression2<ProxyOrderSelector, T1Proxy> selectExpression) {
        super.orderByDesc(condition, selectExpression);
        return getQueryable4();
    }

    @Override
    public <TPropertyProxy extends ProxyEntity<TPropertyProxy, TProperty>, TProperty> ProxyQueryable4<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4> include(boolean condition, SQLFuncExpression2<ProxyNavigateInclude<T1>, T1Proxy, ProxyQueryable<TPropertyProxy, TProperty>> navigateIncludeSQLExpression) {
        super.include(condition, navigateIncludeSQLExpression);
        return getQueryable4();
    }

    @Override
    public ProxyQueryable4<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4> limit(boolean condition, long offset, long rows) {
        super.limit(condition, offset, rows);
        return getQueryable4();
    }

    @Override
    public ProxyQueryable4<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4> distinct(boolean condition) {
        super.distinct(condition);
        return getQueryable4();
    }

    @Override
    public ProxyQueryable4<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4> disableLogicDelete() {
        super.disableLogicDelete();
        return getQueryable4();
    }

    @Override
    public ProxyQueryable4<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4> enableLogicDelete() {
        super.enableLogicDelete();
        return getQueryable4();
    }

    @Override
    public ProxyQueryable4<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4> useLogicDelete(boolean enable) {
        super.useLogicDelete(enable);
        return getQueryable4();
    }

    @Override
    public ProxyQueryable4<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4> noInterceptor() {
        super.noInterceptor();
        return getQueryable4();
    }

    @Override
    public ProxyQueryable4<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4> useInterceptor(String name) {
        super.useInterceptor(name);
        return getQueryable4();
    }

    @Override
    public ProxyQueryable4<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4> noInterceptor(String name) {
        super.noInterceptor(name);
        return getQueryable4();
    }

    @Override
    public ProxyQueryable4<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4> useInterceptor() {
        super.useInterceptor();
        return getQueryable4();
    }

    @Override
    public ProxyQueryable4<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4> asTracking() {
        super.asTracking();
        return getQueryable4();
    }

    @Override
    public ProxyQueryable4<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4> asNoTracking() {
        super.asNoTracking();
        return getQueryable4();
    }

    @Override
    public ProxyQueryable4<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4> queryLargeColumn(boolean queryLarge) {
        super.queryLargeColumn(queryLarge);
        return getQueryable4();
    }

    @Override
    public ProxyQueryable4<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4> useShardingConfigure(int maxShardingQueryLimit, ConnectionModeEnum connectionMode) {
        super.useShardingConfigure(maxShardingQueryLimit, connectionMode);
        return getQueryable4();
    }

    @Override
    public ProxyQueryable4<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4> useMaxShardingQueryLimit(int maxShardingQueryLimit) {
        super.useMaxShardingQueryLimit(maxShardingQueryLimit);
        return getQueryable4();
    }

    @Override
    public ProxyQueryable4<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4> useConnectionMode(ConnectionModeEnum connectionMode) {
        super.useConnectionMode(connectionMode);
        return getQueryable4();
    }

    @Override
    public ProxyQueryable4<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4> asTable(Function<String, String> tableNameAs) {
        super.asTable(tableNameAs);
        return getQueryable4();
    }

    @Override
    public ProxyQueryable4<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4> asSchema(Function<String, String> schemaAs) {
        super.asSchema(schemaAs);
        return getQueryable4();
    }

    @Override
    public ProxyQueryable4<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4> asAlias(String alias) {
        super.asAlias(alias);
        return getQueryable4();
    }

    @Override
    public ProxyQueryable4<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4> asTableLink(Function<String, String> linkAs) {
        super.asTableLink(linkAs);
        return getQueryable4();
    }

    @Override
    public ProxyQueryable4<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4> conditionConfigure(ConditionAccepter conditionAccepter) {
        super.conditionConfigure(conditionAccepter);
        return getQueryable4();
    }
}
