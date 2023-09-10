package com.easy.query.api.proxy.select.extension.queryable3.override;

import com.easy.query.api.proxy.select.ProxyQueryable;
import com.easy.query.api.proxy.select.ProxyQueryable3;
import com.easy.query.api.proxy.select.abstraction.AbstractProxyQueryable;
import com.easy.query.api.proxy.select.extension.queryable3.ProxyQueryable3Available;
import com.easy.query.api.proxy.sql.ProxyAggregateFilter;
import com.easy.query.api.proxy.sql.ProxyFilter;
import com.easy.query.api.proxy.sql.ProxyGroupSelector;
import com.easy.query.api.proxy.sql.ProxyNavigateInclude;
import com.easy.query.api.proxy.sql.ProxyOrderSelector;
import com.easy.query.core.api.dynamic.sort.ObjectSort;
import com.easy.query.core.basic.api.select.ClientQueryable3;
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
public abstract class AbstractOverrideProxyQueryable3<T1Proxy extends ProxyEntity<T1Proxy, T1>, T1, T2Proxy extends ProxyEntity<T2Proxy, T2>, T2, T3Proxy extends ProxyEntity<T3Proxy, T3>, T3> extends AbstractProxyQueryable<T1Proxy, T1> implements ProxyQueryable3Available<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3> {

    protected final ClientQueryable3<T1, T2 ,T3> entityQueryable3;

    public AbstractOverrideProxyQueryable3(T1Proxy t1Proxy,ClientQueryable3<T1, T2 ,T3> entityQueryable3) {
        super(t1Proxy,entityQueryable3);
        this.entityQueryable3 = entityQueryable3;
    }

    @Override
    public ProxyQueryable3<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3> orderBy(boolean condition, SQLExpression2<ProxyOrderSelector, T1Proxy> selectExpression, boolean asc) {
        super.orderBy(condition, selectExpression, asc);
        return getQueryable3();
    }

    @Override
    public ProxyQueryable3<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3> orderByObject(boolean condition, ObjectSort configuration) {
        super.orderByObject(condition, configuration);
        return getQueryable3();
    }

    @Override
    public ProxyQueryable3<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3> whereById(boolean condition, Object id) {
        super.whereById(condition, id);
        return getQueryable3();
    }

    @Override
    public <TProperty> ProxyQueryable3<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3> whereByIds(boolean condition, Collection<TProperty> ids) {
        super.whereByIds(condition, ids);
        return getQueryable3();
    }

    @Override
    public ProxyQueryable3<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3> whereObject(boolean condition, Object object) {
        super.whereObject(condition, object);
        return getQueryable3();
    }

    @Override
    public ProxyQueryable3<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3> where(boolean condition, SQLExpression2<ProxyFilter,T1Proxy> whereExpression) {
        super.where(condition, whereExpression);
        return getQueryable3();
    }

    @Override
    public ProxyQueryable3<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3> groupBy(boolean condition, SQLExpression2<ProxyGroupSelector, T1Proxy> selectExpression) {
        super.groupBy(condition, selectExpression);
        return getQueryable3();
    }

    @Override
    public ProxyQueryable3<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3> having(boolean condition, SQLExpression2<ProxyAggregateFilter, T1Proxy> predicateExpression) {
        super.having(condition, predicateExpression);
        return getQueryable3();
    }

    @Override
    public ProxyQueryable3<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3> orderByAsc(boolean condition, SQLExpression2<ProxyOrderSelector, T1Proxy> selectExpression) {
        super.orderByAsc(condition, selectExpression);
        return getQueryable3();
    }

    @Override
    public ProxyQueryable3<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3> orderByDesc(boolean condition, SQLExpression2<ProxyOrderSelector, T1Proxy> selectExpression) {
        super.orderByDesc(condition, selectExpression);
        return getQueryable3();
    }

    @Override
    public <TPropertyProxy extends ProxyEntity<TPropertyProxy, TProperty>, TProperty> ProxyQueryable3<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3> include(boolean condition, SQLFuncExpression2<ProxyNavigateInclude<T1>,T1Proxy, ProxyQueryable<TPropertyProxy, TProperty>> navigateIncludeSQLExpression) {
        super.include(condition, navigateIncludeSQLExpression);
        return getQueryable3();
    }

    @Override
    public ProxyQueryable3<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3> limit(boolean condition, long offset, long rows) {
        super.limit(condition, offset, rows);
        return getQueryable3();
    }

    @Override
    public ProxyQueryable3<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3> distinct(boolean condition) {
        super.distinct(condition);
        return getQueryable3();
    }

    @Override
    public ProxyQueryable3<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3> disableLogicDelete() {
        super.disableLogicDelete();
        return getQueryable3();
    }

    @Override
    public ProxyQueryable3<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3> enableLogicDelete() {
        super.enableLogicDelete();
        return getQueryable3();
    }

    @Override
    public ProxyQueryable3<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3> useLogicDelete(boolean enable) {
        super.useLogicDelete(enable);
        return getQueryable3();
    }

    @Override
    public ProxyQueryable3<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3> noInterceptor() {
        super.noInterceptor();
        return getQueryable3();
    }

    @Override
    public ProxyQueryable3<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3> useInterceptor(String name) {
        super.useInterceptor(name);
        return getQueryable3();
    }

    @Override
    public ProxyQueryable3<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3> noInterceptor(String name) {
        super.noInterceptor(name);
        return getQueryable3();
    }

    @Override
    public ProxyQueryable3<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3> useInterceptor() {
        super.useInterceptor();
        return getQueryable3();
    }

    @Override
    public ProxyQueryable3<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3> asTracking() {
        super.asTracking();
        return getQueryable3();
    }

    @Override
    public ProxyQueryable3<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3> asNoTracking() {
        super.asNoTracking();
        return getQueryable3();
    }

    @Override
    public ProxyQueryable3<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3> queryLargeColumn(boolean queryLarge) {
        super.queryLargeColumn(queryLarge);
        return getQueryable3();
    }

    @Override
    public ProxyQueryable3<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3> useShardingConfigure(int maxShardingQueryLimit, ConnectionModeEnum connectionMode) {
        super.useShardingConfigure(maxShardingQueryLimit, connectionMode);
        return getQueryable3();
    }

    @Override
    public ProxyQueryable3<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3> useMaxShardingQueryLimit(int maxShardingQueryLimit) {
        super.useMaxShardingQueryLimit(maxShardingQueryLimit);
        return getQueryable3();
    }

    @Override
    public ProxyQueryable3<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3> useConnectionMode(ConnectionModeEnum connectionMode) {
        super.useConnectionMode(connectionMode);
        return getQueryable3();
    }

    @Override
    public ProxyQueryable3<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3> asTable(Function<String, String> tableNameAs) {
        super.asTable(tableNameAs);
        return getQueryable3();
    }

    @Override
    public ProxyQueryable3<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3> asSchema(Function<String, String> schemaAs) {
        super.asSchema(schemaAs);
        return getQueryable3();
    }

    @Override
    public ProxyQueryable3<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3> asAlias(String alias) {
        super.asAlias(alias);
        return getQueryable3();
    }

    @Override
    public ProxyQueryable3<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3> asTableLink(Function<String, String> linkAs) {
        super.asTableLink(linkAs);
        return getQueryable3();
    }

    @Override
    public ProxyQueryable3<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3> conditionConfigure(ConditionAccepter conditionAccepter) {
        super.conditionConfigure(conditionAccepter);
        return getQueryable3();
    }
}
