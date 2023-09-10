package com.easy.query.api.proxy.select.extension.queryable2.override;

import com.easy.query.api.proxy.select.ProxyQueryable;
import com.easy.query.api.proxy.select.ProxyQueryable2;
import com.easy.query.api.proxy.select.abstraction.AbstractProxyQueryable;
import com.easy.query.api.proxy.select.extension.queryable2.ProxyQueryable2Available;
import com.easy.query.api.proxy.sql.ProxyAggregateFilter;
import com.easy.query.api.proxy.sql.ProxyFilter;
import com.easy.query.api.proxy.sql.ProxyGroupSelector;
import com.easy.query.api.proxy.sql.ProxyNavigateInclude;
import com.easy.query.api.proxy.sql.ProxyOrderSelector;
import com.easy.query.core.api.dynamic.sort.ObjectSort;
import com.easy.query.core.basic.api.select.ClientQueryable2;
import com.easy.query.core.enums.sharding.ConnectionModeEnum;
import com.easy.query.core.expression.builder.core.ConditionAccepter;
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
public abstract class AbstractOverrideProxyQueryable2<T1Proxy extends ProxyEntity<T1Proxy, T1>, T1, T2Proxy extends ProxyEntity<T2Proxy, T2>, T2> extends AbstractProxyQueryable<T1Proxy, T1> implements ProxyQueryable2Available<T1Proxy, T1, T2Proxy, T2> {

    protected final ClientQueryable2<T1, T2> entityQueryable2;

    public AbstractOverrideProxyQueryable2(T1Proxy t1Proxy,ClientQueryable2<T1, T2> entityQueryable2) {
        super(t1Proxy,entityQueryable2);
        this.entityQueryable2 = entityQueryable2;
    }

    @Override
    public ProxyQueryable2<T1Proxy, T1, T2Proxy, T2> orderBy(boolean condition, SQLExpression2<ProxyOrderSelector, T1Proxy> selectExpression, boolean asc) {
        super.orderBy(condition, selectExpression, asc);
        return getQueryable2();
    }

    @Override
    public ProxyQueryable2<T1Proxy, T1, T2Proxy, T2> orderByObject(boolean condition, ObjectSort configuration) {
        super.orderByObject(condition, configuration);
        return getQueryable2();
    }

    @Override
    public ProxyQueryable2<T1Proxy, T1, T2Proxy, T2> whereById(boolean condition, Object id) {
        super.whereById(condition, id);
        return getQueryable2();
    }

    @Override
    public <TProperty> ProxyQueryable2<T1Proxy, T1, T2Proxy, T2> whereByIds(boolean condition, Collection<TProperty> ids) {
        super.whereByIds(condition, ids);
        return getQueryable2();
    }

    @Override
    public ProxyQueryable2<T1Proxy, T1, T2Proxy, T2> whereObject(boolean condition, Object object) {
        super.whereObject(condition, object);
        return getQueryable2();
    }

    @Override
    public ProxyQueryable2<T1Proxy, T1, T2Proxy, T2> where(boolean condition, SQLExpression2<ProxyFilter,T1Proxy> whereExpression) {
        super.where(condition, whereExpression);
        return getQueryable2();
    }

    @Override
    public ProxyQueryable2<T1Proxy, T1, T2Proxy, T2> groupBy(boolean condition, SQLExpression2<ProxyGroupSelector, T1Proxy> selectExpression) {
        super.groupBy(condition, selectExpression);
        return getQueryable2();
    }

    @Override
    public ProxyQueryable2<T1Proxy, T1, T2Proxy, T2> having(boolean condition, SQLExpression2<ProxyAggregateFilter, T1Proxy> predicateExpression) {
        super.having(condition, predicateExpression);
        return getQueryable2();
    }

    @Override
    public ProxyQueryable2<T1Proxy, T1, T2Proxy, T2> orderByAsc(boolean condition, SQLExpression2<ProxyOrderSelector, T1Proxy> selectExpression) {
        super.orderByAsc(condition, selectExpression);
        return getQueryable2();
    }

    @Override
    public ProxyQueryable2<T1Proxy, T1, T2Proxy, T2> orderByDesc(boolean condition, SQLExpression2<ProxyOrderSelector, T1Proxy> selectExpression) {
        super.orderByDesc(condition, selectExpression);
        return getQueryable2();
    }

    @Override
    public <TPropertyProxy extends ProxyEntity<TPropertyProxy, TProperty>, TProperty> ProxyQueryable2<T1Proxy, T1, T2Proxy, T2> include(boolean condition, SQLFuncExpression2<ProxyNavigateInclude<T1>,T1Proxy, ProxyQueryable<TPropertyProxy, TProperty>> navigateIncludeSQLExpression) {
        super.include(condition, navigateIncludeSQLExpression);
        return getQueryable2();
    }

    @Override
    public ProxyQueryable2<T1Proxy, T1, T2Proxy, T2> limit(boolean condition, long offset, long rows) {
        super.limit(condition, offset, rows);
        return getQueryable2();
    }

    @Override
    public ProxyQueryable2<T1Proxy, T1, T2Proxy, T2> distinct(boolean condition) {
        super.distinct(condition);
        return getQueryable2();
    }

    @Override
    public ProxyQueryable2<T1Proxy, T1, T2Proxy, T2> disableLogicDelete() {
        super.disableLogicDelete();
        return getQueryable2();
    }

    @Override
    public ProxyQueryable2<T1Proxy, T1, T2Proxy, T2> enableLogicDelete() {
        super.enableLogicDelete();
        return getQueryable2();
    }

    @Override
    public ProxyQueryable2<T1Proxy, T1, T2Proxy, T2> useLogicDelete(boolean enable) {
        super.useLogicDelete(enable);
        return getQueryable2();
    }

    @Override
    public ProxyQueryable2<T1Proxy, T1, T2Proxy, T2> noInterceptor() {
        super.noInterceptor();
        return getQueryable2();
    }

    @Override
    public ProxyQueryable2<T1Proxy, T1, T2Proxy, T2> useInterceptor(String name) {
        super.useInterceptor(name);
        return getQueryable2();
    }

    @Override
    public ProxyQueryable2<T1Proxy, T1, T2Proxy, T2> noInterceptor(String name) {
        super.noInterceptor(name);
        return getQueryable2();
    }

    @Override
    public ProxyQueryable2<T1Proxy, T1, T2Proxy, T2> useInterceptor() {
        super.useInterceptor();
        return getQueryable2();
    }

    @Override
    public ProxyQueryable2<T1Proxy, T1, T2Proxy, T2> asTracking() {
        super.asTracking();
        return getQueryable2();
    }

    @Override
    public ProxyQueryable2<T1Proxy, T1, T2Proxy, T2> asNoTracking() {
        super.asNoTracking();
        return getQueryable2();
    }

    @Override
    public ProxyQueryable2<T1Proxy, T1, T2Proxy, T2> queryLargeColumn(boolean queryLarge) {
        super.queryLargeColumn(queryLarge);
        return getQueryable2();
    }

    @Override
    public ProxyQueryable2<T1Proxy, T1, T2Proxy, T2> useShardingConfigure(int maxShardingQueryLimit, ConnectionModeEnum connectionMode) {
        super.useShardingConfigure(maxShardingQueryLimit, connectionMode);
        return getQueryable2();
    }

    @Override
    public ProxyQueryable2<T1Proxy, T1, T2Proxy, T2> useMaxShardingQueryLimit(int maxShardingQueryLimit) {
        super.useMaxShardingQueryLimit(maxShardingQueryLimit);
        return getQueryable2();
    }

    @Override
    public ProxyQueryable2<T1Proxy, T1, T2Proxy, T2> useConnectionMode(ConnectionModeEnum connectionMode) {
        super.useConnectionMode(connectionMode);
        return getQueryable2();
    }

    @Override
    public ProxyQueryable2<T1Proxy, T1, T2Proxy, T2> asTable(Function<String, String> tableNameAs) {
        super.asTable(tableNameAs);
        return getQueryable2();
    }

    @Override
    public ProxyQueryable2<T1Proxy, T1, T2Proxy, T2> asSchema(Function<String, String> schemaAs) {
        super.asSchema(schemaAs);
        return getQueryable2();
    }

    @Override
    public ProxyQueryable2<T1Proxy, T1, T2Proxy, T2> asAlias(String alias) {
        super.asAlias(alias);
        return getQueryable2();
    }

    @Override
    public ProxyQueryable2<T1Proxy, T1, T2Proxy, T2> asTableLink(Function<String, String> linkAs) {
        super.asTableLink(linkAs);
        return getQueryable2();
    }

    @Override
    public ProxyQueryable2<T1Proxy, T1, T2Proxy, T2> conditionConfigure(ConditionAccepter conditionAccepter) {
        super.conditionConfigure(conditionAccepter);
        return getQueryable2();
    }
}
