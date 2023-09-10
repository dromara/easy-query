package com.easy.query.api.proxy.select.extension.queryable6.override;

import com.easy.query.api.proxy.select.ProxyQueryable;
import com.easy.query.api.proxy.select.ProxyQueryable6;
import com.easy.query.api.proxy.select.abstraction.AbstractProxyQueryable;
import com.easy.query.api.proxy.select.extension.queryable.sql.MultiProxyFilter1;
import com.easy.query.api.proxy.select.extension.queryable6.ProxyQueryable6Available;
import com.easy.query.api.proxy.sql.ProxyAggregateFilter;
import com.easy.query.api.proxy.sql.ProxyGroupSelector;
import com.easy.query.api.proxy.sql.ProxyNavigateInclude;
import com.easy.query.api.proxy.sql.ProxyOrderSelector;
import com.easy.query.core.api.dynamic.sort.ObjectSort;
import com.easy.query.core.basic.api.select.ClientQueryable6;
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
public abstract class AbstractOverrideProxyQueryable6<T1Proxy extends ProxyEntity<T1Proxy, T1>, T1,
        T2Proxy extends ProxyEntity<T2Proxy, T2>, T2,
        T3Proxy extends ProxyEntity<T3Proxy, T3>, T3,
        T4Proxy extends ProxyEntity<T4Proxy, T4>, T4,
        T5Proxy extends ProxyEntity<T5Proxy, T5>, T5,
        T6Proxy extends ProxyEntity<T6Proxy, T6>, T6> extends AbstractProxyQueryable<T1Proxy, T1>
        implements ProxyQueryable6Available<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6> {

    protected final ClientQueryable6<T1, T2, T3, T4, T5, T6> entityQueryable6;

    public AbstractOverrideProxyQueryable6(T1Proxy t1Proxy,  ClientQueryable6<T1, T2, T3, T4, T5, T6> entityQueryable6) {
        super(t1Proxy, entityQueryable6);
        this.entityQueryable6 = entityQueryable6;
    }

    @Override
    public ProxyQueryable6<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6> orderBy(boolean condition, SQLExpression2<ProxyOrderSelector, T1Proxy> selectExpression, boolean asc) {
        super.orderBy(condition, selectExpression, asc);
        return getQueryable6();
    }

    @Override
    public ProxyQueryable6<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6> orderByObject(boolean condition, ObjectSort configuration) {
        super.orderByObject(condition, configuration);
        return getQueryable6();
    }

    @Override
    public ProxyQueryable6<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6> whereById(boolean condition, Object id) {
        super.whereById(condition, id);
        return getQueryable6();
    }

    @Override
    public <TProperty> ProxyQueryable6<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6> whereByIds(boolean condition, Collection<TProperty> ids) {
        super.whereByIds(condition, ids);
        return getQueryable6();
    }

    @Override
    public ProxyQueryable6<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6> whereObject(boolean condition, Object object) {
        super.whereObject(condition, object);
        return getQueryable6();
    }

    @Override
    public ProxyQueryable6<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6> where(boolean condition, SQLExpression1<MultiProxyFilter1<T1Proxy>> whereExpression) {
        super.where(condition, whereExpression);
        return getQueryable6();
    }

    @Override
    public ProxyQueryable6<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6> groupBy(boolean condition, SQLExpression2<ProxyGroupSelector, T1Proxy> selectExpression) {
        super.groupBy(condition, selectExpression);
        return getQueryable6();
    }

    @Override
    public ProxyQueryable6<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6> having(boolean condition, SQLExpression2<ProxyAggregateFilter, T1Proxy> predicateExpression) {
        super.having(condition, predicateExpression);
        return getQueryable6();
    }

    @Override
    public ProxyQueryable6<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6> orderByAsc(boolean condition, SQLExpression2<ProxyOrderSelector, T1Proxy> selectExpression) {
        super.orderByAsc(condition, selectExpression);
        return getQueryable6();
    }

    @Override
    public ProxyQueryable6<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6> orderByDesc(boolean condition, SQLExpression2<ProxyOrderSelector, T1Proxy> selectExpression) {
        super.orderByDesc(condition, selectExpression);
        return getQueryable6();
    }

    @Override
    public <TPropertyProxy extends ProxyEntity<TPropertyProxy, TProperty>, TProperty> ProxyQueryable6<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6> include(boolean condition, SQLFuncExpression2<ProxyNavigateInclude<T1>, T1Proxy, ProxyQueryable<TPropertyProxy, TProperty>> navigateIncludeSQLExpression) {
        super.include(condition, navigateIncludeSQLExpression);
        return getQueryable6();
    }

    @Override
    public ProxyQueryable6<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6> limit(boolean condition, long offset, long rows) {
        super.limit(condition, offset, rows);
        return getQueryable6();
    }

    @Override
    public ProxyQueryable6<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6> distinct(boolean condition) {
        super.distinct(condition);
        return getQueryable6();
    }

    @Override
    public ProxyQueryable6<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6> disableLogicDelete() {
        super.disableLogicDelete();
        return getQueryable6();
    }

    @Override
    public ProxyQueryable6<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6> enableLogicDelete() {
        super.enableLogicDelete();
        return getQueryable6();
    }

    @Override
    public ProxyQueryable6<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6> useLogicDelete(boolean enable) {
        super.useLogicDelete(enable);
        return getQueryable6();
    }

    @Override
    public ProxyQueryable6<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6> noInterceptor() {
        super.noInterceptor();
        return getQueryable6();
    }

    @Override
    public ProxyQueryable6<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6> useInterceptor(String name) {
        super.useInterceptor(name);
        return getQueryable6();
    }

    @Override
    public ProxyQueryable6<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6> noInterceptor(String name) {
        super.noInterceptor(name);
        return getQueryable6();
    }

    @Override
    public ProxyQueryable6<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6> useInterceptor() {
        super.useInterceptor();
        return getQueryable6();
    }

    @Override
    public ProxyQueryable6<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6> asTracking() {
        super.asTracking();
        return getQueryable6();
    }

    @Override
    public ProxyQueryable6<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6> asNoTracking() {
        super.asNoTracking();
        return getQueryable6();
    }

    @Override
    public ProxyQueryable6<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6> queryLargeColumn(boolean queryLarge) {
        super.queryLargeColumn(queryLarge);
        return getQueryable6();
    }

    @Override
    public ProxyQueryable6<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6> useShardingConfigure(int maxShardingQueryLimit, ConnectionModeEnum connectionMode) {
        super.useShardingConfigure(maxShardingQueryLimit, connectionMode);
        return getQueryable6();
    }

    @Override
    public ProxyQueryable6<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6> useMaxShardingQueryLimit(int maxShardingQueryLimit) {
        super.useMaxShardingQueryLimit(maxShardingQueryLimit);
        return getQueryable6();
    }

    @Override
    public ProxyQueryable6<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6> useConnectionMode(ConnectionModeEnum connectionMode) {
        super.useConnectionMode(connectionMode);
        return getQueryable6();
    }

    @Override
    public ProxyQueryable6<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6> asTable(Function<String, String> tableNameAs) {
        super.asTable(tableNameAs);
        return getQueryable6();
    }

    @Override
    public ProxyQueryable6<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6> asSchema(Function<String, String> schemaAs) {
        super.asSchema(schemaAs);
        return getQueryable6();
    }

    @Override
    public ProxyQueryable6<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6> asAlias(String alias) {
        super.asAlias(alias);
        return getQueryable6();
    }

    @Override
    public ProxyQueryable6<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6> asTableLink(Function<String, String> linkAs) {
        super.asTableLink(linkAs);
        return getQueryable6();
    }

    @Override
    public ProxyQueryable6<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6> conditionConfigure(ConditionAccepter conditionAccepter) {
        super.conditionConfigure(conditionAccepter);
        return getQueryable6();
    }
}
