package com.easy.query.api.proxy.entity.select.extension.queryable2.override;

import com.easy.query.api.proxy.entity.select.EntityQueryable;
import com.easy.query.api.proxy.entity.select.EntityQueryable2;
import com.easy.query.api.proxy.entity.select.abstraction.AbstractEntityQueryable;
import com.easy.query.api.proxy.entity.select.extension.queryable2.EntityQueryable2Available;
import com.easy.query.api.proxy.sql.ProxyNavigateInclude;
import com.easy.query.core.api.dynamic.sort.ObjectSort;
import com.easy.query.core.basic.api.select.ClientQueryable2;
import com.easy.query.core.enums.sharding.ConnectionModeEnum;
import com.easy.query.core.expression.builder.core.ValueFilter;
import com.easy.query.core.expression.lambda.SQLFuncExpression1;
import com.easy.query.core.expression.lambda.SQLFuncExpression2;
import com.easy.query.core.proxy.ProxyEntity;
import com.easy.query.core.proxy.SQLAggregatePredicateExpression;
import com.easy.query.core.proxy.SQLGroupByExpression;
import com.easy.query.core.proxy.SQLOrderByExpression;
import com.easy.query.core.proxy.SQLPredicateExpression;

import java.util.Collection;
import java.util.function.Function;

/**
 * create time 2023/8/16 07:52
 * 文件说明
 *
 * @author xuejiaming
 */
public abstract class AbstractOverrideEntityQueryable2<T1Proxy extends ProxyEntity<T1Proxy, T1>, T1, T2Proxy extends ProxyEntity<T2Proxy, T2>, T2> extends AbstractEntityQueryable<T1Proxy, T1> implements EntityQueryable2Available<T1Proxy, T1, T2Proxy, T2> {

    protected final ClientQueryable2<T1, T2> entityQueryable2;

    public AbstractOverrideEntityQueryable2(T1Proxy t1Proxy, ClientQueryable2<T1, T2> entityQueryable2) {
        super(t1Proxy, entityQueryable2);
        this.entityQueryable2 = entityQueryable2;
    }

    @Override
    public EntityQueryable2<T1Proxy, T1, T2Proxy, T2> orderBy(boolean condition, SQLFuncExpression1<T1Proxy, SQLOrderByExpression> selectExpression) {
        super.orderBy(condition, selectExpression);
        return getQueryable2();
    }

    @Override
    public EntityQueryable2<T1Proxy, T1, T2Proxy, T2> orderByObject(boolean condition, ObjectSort configuration) {
        super.orderByObject(condition, configuration);
        return getQueryable2();
    }

    @Override
    public EntityQueryable2<T1Proxy, T1, T2Proxy, T2> whereById(boolean condition, Object id) {
        super.whereById(condition, id);
        return getQueryable2();
    }

    @Override
    public <TProperty> EntityQueryable2<T1Proxy, T1, T2Proxy, T2> whereByIds(boolean condition, Collection<TProperty> ids) {
        super.whereByIds(condition, ids);
        return getQueryable2();
    }

    @Override
    public EntityQueryable2<T1Proxy, T1, T2Proxy, T2> whereObject(boolean condition, Object object) {
        super.whereObject(condition, object);
        return getQueryable2();
    }

    @Override
    public EntityQueryable2<T1Proxy, T1, T2Proxy, T2> where(boolean condition, SQLFuncExpression1<T1Proxy, SQLPredicateExpression> whereExpression) {
        super.where(condition, whereExpression);
        return getQueryable2();
    }

    @Override
    public EntityQueryable2<T1Proxy, T1, T2Proxy, T2> groupBy(boolean condition, SQLFuncExpression1<T1Proxy, SQLGroupByExpression> selectExpression) {
        super.groupBy(condition, selectExpression);
        return getQueryable2();
    }

    @Override
    public EntityQueryable2<T1Proxy, T1, T2Proxy, T2> having(boolean condition, SQLFuncExpression1<T1Proxy, SQLAggregatePredicateExpression> predicateExpression) {
        super.having(condition, predicateExpression);
        return getQueryable2();
    }

    @Override
    public <TPropertyProxy extends ProxyEntity<TPropertyProxy, TProperty>, TProperty> EntityQueryable2<T1Proxy, T1, T2Proxy, T2> include(boolean condition, SQLFuncExpression2<ProxyNavigateInclude<T1>, T1Proxy, EntityQueryable<TPropertyProxy, TProperty>> navigateIncludeSQLExpression) {
        super.include(condition, navigateIncludeSQLExpression);
        return getQueryable2();
    }

    @Override
    public EntityQueryable2<T1Proxy, T1, T2Proxy, T2> limit(boolean condition, long offset, long rows) {
        super.limit(condition, offset, rows);
        return getQueryable2();
    }

    @Override
    public EntityQueryable2<T1Proxy, T1, T2Proxy, T2> distinct(boolean condition) {
        super.distinct(condition);
        return getQueryable2();
    }

    @Override
    public EntityQueryable2<T1Proxy, T1, T2Proxy, T2> disableLogicDelete() {
        super.disableLogicDelete();
        return getQueryable2();
    }

    @Override
    public EntityQueryable2<T1Proxy, T1, T2Proxy, T2> enableLogicDelete() {
        super.enableLogicDelete();
        return getQueryable2();
    }

    @Override
    public EntityQueryable2<T1Proxy, T1, T2Proxy, T2> useLogicDelete(boolean enable) {
        super.useLogicDelete(enable);
        return getQueryable2();
    }

    @Override
    public EntityQueryable2<T1Proxy, T1, T2Proxy, T2> noInterceptor() {
        super.noInterceptor();
        return getQueryable2();
    }

    @Override
    public EntityQueryable2<T1Proxy, T1, T2Proxy, T2> useInterceptor(String name) {
        super.useInterceptor(name);
        return getQueryable2();
    }

    @Override
    public EntityQueryable2<T1Proxy, T1, T2Proxy, T2> noInterceptor(String name) {
        super.noInterceptor(name);
        return getQueryable2();
    }

    @Override
    public EntityQueryable2<T1Proxy, T1, T2Proxy, T2> useInterceptor() {
        super.useInterceptor();
        return getQueryable2();
    }

    @Override
    public EntityQueryable2<T1Proxy, T1, T2Proxy, T2> asTracking() {
        super.asTracking();
        return getQueryable2();
    }

    @Override
    public EntityQueryable2<T1Proxy, T1, T2Proxy, T2> asNoTracking() {
        super.asNoTracking();
        return getQueryable2();
    }

    @Override
    public EntityQueryable2<T1Proxy, T1, T2Proxy, T2> queryLargeColumn(boolean queryLarge) {
        super.queryLargeColumn(queryLarge);
        return getQueryable2();
    }

    @Override
    public EntityQueryable2<T1Proxy, T1, T2Proxy, T2> useShardingConfigure(int maxShardingQueryLimit, ConnectionModeEnum connectionMode) {
        super.useShardingConfigure(maxShardingQueryLimit, connectionMode);
        return getQueryable2();
    }

    @Override
    public EntityQueryable2<T1Proxy, T1, T2Proxy, T2> useMaxShardingQueryLimit(int maxShardingQueryLimit) {
        super.useMaxShardingQueryLimit(maxShardingQueryLimit);
        return getQueryable2();
    }

    @Override
    public EntityQueryable2<T1Proxy, T1, T2Proxy, T2> useConnectionMode(ConnectionModeEnum connectionMode) {
        super.useConnectionMode(connectionMode);
        return getQueryable2();
    }

    @Override
    public EntityQueryable2<T1Proxy, T1, T2Proxy, T2> asTable(Function<String, String> tableNameAs) {
        super.asTable(tableNameAs);
        return getQueryable2();
    }

    @Override
    public EntityQueryable2<T1Proxy, T1, T2Proxy, T2> asSchema(Function<String, String> schemaAs) {
        super.asSchema(schemaAs);
        return getQueryable2();
    }

    @Override
    public EntityQueryable2<T1Proxy, T1, T2Proxy, T2> asAlias(String alias) {
        super.asAlias(alias);
        return getQueryable2();
    }

    @Override
    public EntityQueryable2<T1Proxy, T1, T2Proxy, T2> asTableLink(Function<String, String> linkAs) {
        super.asTableLink(linkAs);
        return getQueryable2();
    }

    @Override
    public EntityQueryable2<T1Proxy, T1, T2Proxy, T2> filterConfigure(ValueFilter valueFilter) {
        super.filterConfigure(valueFilter);
        return getQueryable2();
    }
}
