package com.easy.query.api.proxy.select.extension.queryable8.override;

import com.easy.query.api.proxy.select.ProxyQueryable;
import com.easy.query.api.proxy.select.ProxyQueryable8;
import com.easy.query.api.proxy.select.abstraction.AbstractProxyQueryable;
import com.easy.query.api.proxy.select.extension.queryable8.ProxyQueryable8Available;
import com.easy.query.api.proxy.sql.ProxyAggregateFilter;
import com.easy.query.api.proxy.sql.ProxyFilter;
import com.easy.query.api.proxy.sql.ProxyGroupSelector;
import com.easy.query.api.proxy.sql.ProxyNavigateInclude;
import com.easy.query.api.proxy.sql.ProxyOrderSelector;
import com.easy.query.core.api.dynamic.sort.ObjectSort;
import com.easy.query.core.basic.api.select.ClientQueryable8;
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
public abstract class AbstractOverrideProxyQueryable8<T1Proxy extends ProxyEntity<T1Proxy, T1>, T1,
        T2Proxy extends ProxyEntity<T2Proxy, T2>, T2,
        T3Proxy extends ProxyEntity<T3Proxy, T3>, T3,
        T4Proxy extends ProxyEntity<T4Proxy, T4>, T4,
        T5Proxy extends ProxyEntity<T5Proxy, T5>, T5,
        T6Proxy extends ProxyEntity<T6Proxy, T6>, T6,
        T7Proxy extends ProxyEntity<T7Proxy, T7>, T7,
        T8Proxy extends ProxyEntity<T8Proxy, T8>, T8> extends AbstractProxyQueryable<T1Proxy, T1>
        implements ProxyQueryable8Available<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6, T7Proxy, T7, T8Proxy, T8> {

    protected final ClientQueryable8<T1, T2, T3, T4, T5, T6, T7, T8> entityQueryable8;

    public AbstractOverrideProxyQueryable8(T1Proxy t1Proxy, ClientQueryable8<T1, T2, T3, T4, T5, T6, T7, T8> entityQueryable8) {
        super(t1Proxy, entityQueryable8);
        this.entityQueryable8 = entityQueryable8;
    }

    @Override
    public ProxyQueryable8<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6, T7Proxy, T7, T8Proxy, T8> orderBy(boolean condition, SQLExpression2<ProxyOrderSelector, T1Proxy> selectExpression, boolean asc) {
        super.orderBy(condition, selectExpression, asc);
        return getQueryable8();
    }

    @Override
    public ProxyQueryable8<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6, T7Proxy, T7, T8Proxy, T8> orderByObject(boolean condition, ObjectSort configuration) {
        super.orderByObject(condition, configuration);
        return getQueryable8();
    }

    @Override
    public ProxyQueryable8<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6, T7Proxy, T7, T8Proxy, T8> whereById(boolean condition, Object id) {
        super.whereById(condition, id);
        return getQueryable8();
    }

    @Override
    public <TProperty> ProxyQueryable8<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6, T7Proxy, T7, T8Proxy, T8> whereByIds(boolean condition, Collection<TProperty> ids) {
        super.whereByIds(condition, ids);
        return getQueryable8();
    }

    @Override
    public ProxyQueryable8<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6, T7Proxy, T7, T8Proxy, T8> whereObject(boolean condition, Object object) {
        super.whereObject(condition, object);
        return getQueryable8();
    }

    @Override
    public ProxyQueryable8<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6, T7Proxy, T7, T8Proxy, T8> where(boolean condition, SQLExpression2<ProxyFilter, T1Proxy> whereExpression) {
        super.where(condition, whereExpression);
        return getQueryable8();
    }

    @Override
    public ProxyQueryable8<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6, T7Proxy, T7, T8Proxy, T8> groupBy(boolean condition, SQLExpression2<ProxyGroupSelector, T1Proxy> selectExpression) {
        super.groupBy(condition, selectExpression);
        return getQueryable8();
    }

    @Override
    public ProxyQueryable8<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6, T7Proxy, T7, T8Proxy, T8> having(boolean condition, SQLExpression2<ProxyAggregateFilter, T1Proxy> predicateExpression) {
        super.having(condition, predicateExpression);
        return getQueryable8();
    }

    @Override
    public ProxyQueryable8<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6, T7Proxy, T7, T8Proxy, T8> orderByAsc(boolean condition, SQLExpression2<ProxyOrderSelector, T1Proxy> selectExpression) {
        super.orderByAsc(condition, selectExpression);
        return getQueryable8();
    }

    @Override
    public ProxyQueryable8<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6, T7Proxy, T7, T8Proxy, T8> orderByDesc(boolean condition, SQLExpression2<ProxyOrderSelector, T1Proxy> selectExpression) {
        super.orderByDesc(condition, selectExpression);
        return getQueryable8();
    }

    @Override
    public <TPropertyProxy extends ProxyEntity<TPropertyProxy, TProperty>, TProperty> ProxyQueryable8<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6, T7Proxy, T7, T8Proxy, T8> include(boolean condition, SQLFuncExpression2<ProxyNavigateInclude<T1>, T1Proxy, ProxyQueryable<TPropertyProxy, TProperty>> navigateIncludeSQLExpression) {
        super.include(condition, navigateIncludeSQLExpression);
        return getQueryable8();
    }

    @Override
    public ProxyQueryable8<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6, T7Proxy, T7, T8Proxy, T8> limit(boolean condition, long offset, long rows) {
        super.limit(condition, offset, rows);
        return getQueryable8();
    }

    @Override
    public ProxyQueryable8<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6, T7Proxy, T7, T8Proxy, T8> distinct(boolean condition) {
        super.distinct(condition);
        return getQueryable8();
    }

    @Override
    public ProxyQueryable8<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6, T7Proxy, T7, T8Proxy, T8> disableLogicDelete() {
        super.disableLogicDelete();
        return getQueryable8();
    }

    @Override
    public ProxyQueryable8<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6, T7Proxy, T7, T8Proxy, T8> enableLogicDelete() {
        super.enableLogicDelete();
        return getQueryable8();
    }

    @Override
    public ProxyQueryable8<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6, T7Proxy, T7, T8Proxy, T8> useLogicDelete(boolean enable) {
        super.useLogicDelete(enable);
        return getQueryable8();
    }

    @Override
    public ProxyQueryable8<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6, T7Proxy, T7, T8Proxy, T8> noInterceptor() {
        super.noInterceptor();
        return getQueryable8();
    }

    @Override
    public ProxyQueryable8<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6, T7Proxy, T7, T8Proxy, T8> useInterceptor(String name) {
        super.useInterceptor(name);
        return getQueryable8();
    }

    @Override
    public ProxyQueryable8<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6, T7Proxy, T7, T8Proxy, T8> noInterceptor(String name) {
        super.noInterceptor(name);
        return getQueryable8();
    }

    @Override
    public ProxyQueryable8<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6, T7Proxy, T7, T8Proxy, T8> useInterceptor() {
        super.useInterceptor();
        return getQueryable8();
    }

    @Override
    public ProxyQueryable8<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6, T7Proxy, T7, T8Proxy, T8> asTracking() {
        super.asTracking();
        return getQueryable8();
    }

    @Override
    public ProxyQueryable8<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6, T7Proxy, T7, T8Proxy, T8> asNoTracking() {
        super.asNoTracking();
        return getQueryable8();
    }

    @Override
    public ProxyQueryable8<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6, T7Proxy, T7, T8Proxy, T8> queryLargeColumn(boolean queryLarge) {
        super.queryLargeColumn(queryLarge);
        return getQueryable8();
    }

    @Override
    public ProxyQueryable8<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6, T7Proxy, T7, T8Proxy, T8> useShardingConfigure(int maxShardingQueryLimit, ConnectionModeEnum connectionMode) {
        super.useShardingConfigure(maxShardingQueryLimit, connectionMode);
        return getQueryable8();
    }

    @Override
    public ProxyQueryable8<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6, T7Proxy, T7, T8Proxy, T8> useMaxShardingQueryLimit(int maxShardingQueryLimit) {
        super.useMaxShardingQueryLimit(maxShardingQueryLimit);
        return getQueryable8();
    }

    @Override
    public ProxyQueryable8<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6, T7Proxy, T7, T8Proxy, T8> useConnectionMode(ConnectionModeEnum connectionMode) {
        super.useConnectionMode(connectionMode);
        return getQueryable8();
    }

    @Override
    public ProxyQueryable8<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6, T7Proxy, T7, T8Proxy, T8> asTable(Function<String, String> tableNameAs) {
        super.asTable(tableNameAs);
        return getQueryable8();
    }

    @Override
    public ProxyQueryable8<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6, T7Proxy, T7, T8Proxy, T8> asSchema(Function<String, String> schemaAs) {
        super.asSchema(schemaAs);
        return getQueryable8();
    }

    @Override
    public ProxyQueryable8<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6, T7Proxy, T7, T8Proxy, T8> asAlias(String alias) {
        super.asAlias(alias);
        return getQueryable8();
    }

    @Override
    public ProxyQueryable8<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6, T7Proxy, T7, T8Proxy, T8> asTableLink(Function<String, String> linkAs) {
        super.asTableLink(linkAs);
        return getQueryable8();
    }

    @Override
    public ProxyQueryable8<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6, T7Proxy, T7, T8Proxy, T8> conditionConfigure(ConditionAccepter conditionAccepter) {
        super.conditionConfigure(conditionAccepter);
        return getQueryable8();
    }
}
