package com.easy.query.api.proxy.entity.select.extension.queryable7.override;

import com.easy.query.api.proxy.entity.select.EntityQueryable;
import com.easy.query.api.proxy.entity.select.EntityQueryable7;
import com.easy.query.api.proxy.entity.select.abstraction.AbstractEntityQueryable;
import com.easy.query.api.proxy.entity.select.extension.queryable7.EntityQueryable7Available;
import com.easy.query.api.proxy.sql.ProxyNavigateInclude;
import com.easy.query.core.api.dynamic.sort.ObjectSort;
import com.easy.query.core.basic.api.select.ClientQueryable7;
import com.easy.query.core.enums.sharding.ConnectionModeEnum;
import com.easy.query.core.expression.builder.core.ValueFilter;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.lambda.SQLFuncExpression1;
import com.easy.query.core.expression.lambda.SQLFuncExpression2;
import com.easy.query.core.proxy.ProxyEntity;
import com.easy.query.core.proxy.SQLAggregatePredicateExpression;
import com.easy.query.core.proxy.SQLGroupByExpression;
import com.easy.query.core.proxy.SQLOrderByExpression;

import java.util.Collection;
import java.util.function.Function;

/**
 * create time 2023/8/16 07:52
 * 文件说明
 *
 * @author xuejiaming
 */
public abstract class AbstractOverrideEntityQueryable7<T1Proxy extends ProxyEntity<T1Proxy, T1>, T1,
        T2Proxy extends ProxyEntity<T2Proxy, T2>, T2,
        T3Proxy extends ProxyEntity<T3Proxy, T3>, T3,
        T4Proxy extends ProxyEntity<T4Proxy, T4>, T4,
        T5Proxy extends ProxyEntity<T5Proxy, T5>, T5,
        T6Proxy extends ProxyEntity<T6Proxy, T6>, T6,
        T7Proxy extends ProxyEntity<T7Proxy, T7>, T7> extends AbstractEntityQueryable<T1Proxy, T1>
        implements EntityQueryable7Available<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6, T7Proxy, T7> {
    protected final ClientQueryable7<T1, T2, T3, T4, T5, T6, T7> entityQueryable7;

    public AbstractOverrideEntityQueryable7(T1Proxy t1Proxy, ClientQueryable7<T1, T2, T3, T4, T5, T6, T7> entityQueryable7) {
        super(t1Proxy, entityQueryable7);
        this.entityQueryable7 = entityQueryable7;
    }

    @Override
    public EntityQueryable7<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6, T7Proxy, T7> orderBy(boolean condition, SQLFuncExpression1<T1Proxy, SQLOrderByExpression> selectExpression) {
        super.orderBy(condition, selectExpression);
        return getQueryable7();
    }

    @Override
    public EntityQueryable7<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6, T7Proxy, T7> orderByObject(boolean condition, ObjectSort configuration) {
        super.orderByObject(condition, configuration);
        return getQueryable7();
    }

    @Override
    public EntityQueryable7<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6, T7Proxy, T7> whereById(boolean condition, Object id) {
        super.whereById(condition, id);
        return getQueryable7();
    }

    @Override
    public <TProperty> EntityQueryable7<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6, T7Proxy, T7> whereByIds(boolean condition, Collection<TProperty> ids) {
        super.whereByIds(condition, ids);
        return getQueryable7();
    }

    @Override
    public EntityQueryable7<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6, T7Proxy, T7> whereObject(boolean condition, Object object) {
        super.whereObject(condition, object);
        return getQueryable7();
    }

    @Override
    public EntityQueryable7<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6, T7Proxy, T7> where(boolean condition, SQLExpression1<T1Proxy> whereExpression) {
        super.where(condition, whereExpression);
        return getQueryable7();
    }

    @Override
    public EntityQueryable7<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6, T7Proxy, T7> groupBy(boolean condition, SQLFuncExpression1<T1Proxy, SQLGroupByExpression> selectExpression) {
        super.groupBy(condition, selectExpression);
        return getQueryable7();
    }

    @Override
    public EntityQueryable7<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6, T7Proxy, T7> having(boolean condition, SQLFuncExpression1<T1Proxy, SQLAggregatePredicateExpression> predicateExpression) {
        super.having(condition, predicateExpression);
        return getQueryable7();
    }

    @Override
    public <TPropertyProxy extends ProxyEntity<TPropertyProxy, TProperty>, TProperty> EntityQueryable7<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6, T7Proxy, T7> include(boolean condition, SQLFuncExpression2<ProxyNavigateInclude<T1>, T1Proxy, EntityQueryable<TPropertyProxy, TProperty>> navigateIncludeSQLExpression) {
        super.include(condition, navigateIncludeSQLExpression);
        return getQueryable7();
    }

    @Override
    public EntityQueryable7<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6, T7Proxy, T7> limit(boolean condition, long offset, long rows) {
        super.limit(condition, offset, rows);
        return getQueryable7();
    }

    @Override
    public EntityQueryable7<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6, T7Proxy, T7> distinct(boolean condition) {
        super.distinct(condition);
        return getQueryable7();
    }

    @Override
    public EntityQueryable7<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6, T7Proxy, T7> disableLogicDelete() {
        super.disableLogicDelete();
        return getQueryable7();
    }

    @Override
    public EntityQueryable7<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6, T7Proxy, T7> enableLogicDelete() {
        super.enableLogicDelete();
        return getQueryable7();
    }

    @Override
    public EntityQueryable7<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6, T7Proxy, T7> useLogicDelete(boolean enable) {
        super.useLogicDelete(enable);
        return getQueryable7();
    }

    @Override
    public EntityQueryable7<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6, T7Proxy, T7> noInterceptor() {
        super.noInterceptor();
        return getQueryable7();
    }

    @Override
    public EntityQueryable7<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6, T7Proxy, T7> useInterceptor(String name) {
        super.useInterceptor(name);
        return getQueryable7();
    }

    @Override
    public EntityQueryable7<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6, T7Proxy, T7> noInterceptor(String name) {
        super.noInterceptor(name);
        return getQueryable7();
    }

    @Override
    public EntityQueryable7<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6, T7Proxy, T7> useInterceptor() {
        super.useInterceptor();
        return getQueryable7();
    }

    @Override
    public EntityQueryable7<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6, T7Proxy, T7> asTracking() {
        super.asTracking();
        return getQueryable7();
    }

    @Override
    public EntityQueryable7<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6, T7Proxy, T7> asNoTracking() {
        super.asNoTracking();
        return getQueryable7();
    }

    @Override
    public EntityQueryable7<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6, T7Proxy, T7> queryLargeColumn(boolean queryLarge) {
        super.queryLargeColumn(queryLarge);
        return getQueryable7();
    }

    @Override
    public EntityQueryable7<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6, T7Proxy, T7> useShardingConfigure(int maxShardingQueryLimit, ConnectionModeEnum connectionMode) {
        super.useShardingConfigure(maxShardingQueryLimit, connectionMode);
        return getQueryable7();
    }

    @Override
    public EntityQueryable7<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6, T7Proxy, T7> useMaxShardingQueryLimit(int maxShardingQueryLimit) {
        super.useMaxShardingQueryLimit(maxShardingQueryLimit);
        return getQueryable7();
    }

    @Override
    public EntityQueryable7<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6, T7Proxy, T7> useConnectionMode(ConnectionModeEnum connectionMode) {
        super.useConnectionMode(connectionMode);
        return getQueryable7();
    }

    @Override
    public EntityQueryable7<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6, T7Proxy, T7> asTable(Function<String, String> tableNameAs) {
        super.asTable(tableNameAs);
        return getQueryable7();
    }

    @Override
    public EntityQueryable7<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6, T7Proxy, T7> asSchema(Function<String, String> schemaAs) {
        super.asSchema(schemaAs);
        return getQueryable7();
    }

    @Override
    public EntityQueryable7<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6, T7Proxy, T7> asAlias(String alias) {
        super.asAlias(alias);
        return getQueryable7();
    }

    @Override
    public EntityQueryable7<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6, T7Proxy, T7> asTableLink(Function<String, String> linkAs) {
        super.asTableLink(linkAs);
        return getQueryable7();
    }

    @Override
    public EntityQueryable7<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6, T7Proxy, T7> filterConfigure(ValueFilter valueFilter) {
        super.filterConfigure(valueFilter);
        return getQueryable7();
    }
}
