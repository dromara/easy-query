package com.easy.query.api.proxy.entity.select.extension.queryable5.override;

import com.easy.query.api.proxy.entity.select.EntityQueryable5;
import com.easy.query.api.proxy.entity.select.abstraction.AbstractEntityQueryable;
import com.easy.query.api.proxy.entity.select.extension.queryable5.EntityQueryable5Available;
import com.easy.query.core.api.dynamic.sort.ObjectSort;
import com.easy.query.core.basic.api.select.ClientQueryable;
import com.easy.query.core.basic.api.select.ClientQueryable5;
import com.easy.query.core.enums.sharding.ConnectionModeEnum;
import com.easy.query.core.expression.builder.core.ValueFilter;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.lambda.SQLFuncExpression1;
import com.easy.query.core.proxy.ProxyEntity;
import com.easy.query.core.proxy.ProxyEntityAvailable;
import com.easy.query.core.proxy.SQLGroupByExpression;

import java.util.Collection;
import java.util.function.Function;

/**
 * create time 2023/8/16 07:52
 * 文件说明
 *
 * @author xuejiaming
 */
public abstract class AbstractOverrideEntityQueryable5<T1Proxy extends ProxyEntity<T1Proxy, T1>, T1,
        T2Proxy extends ProxyEntity<T2Proxy, T2>, T2,
        T3Proxy extends ProxyEntity<T3Proxy, T3>, T3,
        T4Proxy extends ProxyEntity<T4Proxy, T4>, T4,
        T5Proxy extends ProxyEntity<T5Proxy, T5>, T5> extends AbstractEntityQueryable<T1Proxy, T1>
        implements EntityQueryable5Available<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5> {

    protected final ClientQueryable5<T1, T2, T3, T4, T5> entityQueryable5;

    public AbstractOverrideEntityQueryable5(T1Proxy t1Proxy, ClientQueryable5<T1, T2, T3, T4, T5> entityQueryable5) {
        super(t1Proxy, entityQueryable5);
        this.entityQueryable5 = entityQueryable5;
    }

    @Override
    public EntityQueryable5<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5> orderBy(boolean condition, SQLExpression1<T1Proxy> selectExpression) {
        super.orderBy(condition, selectExpression);
        return getQueryable5();
    }

    @Override
    public EntityQueryable5<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5> orderByObject(boolean condition, ObjectSort configuration) {
        super.orderByObject(condition, configuration);
        return getQueryable5();
    }

    @Override
    public EntityQueryable5<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5> whereById(boolean condition, Object id) {
        super.whereById(condition, id);
        return getQueryable5();
    }

    @Override
    public <TProperty> EntityQueryable5<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5> whereByIds(boolean condition, Collection<TProperty> ids) {
        super.whereByIds(condition, ids);
        return getQueryable5();
    }

    @Override
    public EntityQueryable5<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5> whereObject(boolean condition, Object object) {
        super.whereObject(condition, object);
        return getQueryable5();
    }

    @Override
    public EntityQueryable5<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5> where(boolean condition, SQLExpression1<T1Proxy> whereExpression) {
        super.where(condition, whereExpression);
        return getQueryable5();
    }

    @Override
    public EntityQueryable5<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5> groupBy(boolean condition, SQLFuncExpression1<T1Proxy, SQLGroupByExpression> selectExpression) {
        super.groupBy(condition, selectExpression);
        return getQueryable5();
    }

    @Override
    public EntityQueryable5<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5> having(boolean condition, SQLExpression1<T1Proxy> predicateExpression) {
        super.having(condition, predicateExpression);
        return getQueryable5();
    }

    @Override
    public <TPropertyProxy extends ProxyEntity<TPropertyProxy, TProperty>, TProperty extends ProxyEntityAvailable<TProperty,TPropertyProxy>> EntityQueryable5<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5> include(boolean condition, SQLFuncExpression1<T1Proxy, ClientQueryable<TProperty>> navigateIncludeSQLExpression) {
        super.include(condition, navigateIncludeSQLExpression);
        return getQueryable5();
    }

    @Override
    public EntityQueryable5<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5> limit(boolean condition, long offset, long rows) {
        super.limit(condition, offset, rows);
        return getQueryable5();
    }

    @Override
    public EntityQueryable5<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5> distinct(boolean condition) {
        super.distinct(condition);
        return getQueryable5();
    }

    @Override
    public EntityQueryable5<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5> disableLogicDelete() {
        super.disableLogicDelete();
        return getQueryable5();
    }

    @Override
    public EntityQueryable5<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5> enableLogicDelete() {
        super.enableLogicDelete();
        return getQueryable5();
    }

    @Override
    public EntityQueryable5<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5> useLogicDelete(boolean enable) {
        super.useLogicDelete(enable);
        return getQueryable5();
    }

    @Override
    public EntityQueryable5<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5> noInterceptor() {
        super.noInterceptor();
        return getQueryable5();
    }

    @Override
    public EntityQueryable5<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5> useInterceptor(String name) {
        super.useInterceptor(name);
        return getQueryable5();
    }

    @Override
    public EntityQueryable5<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5> noInterceptor(String name) {
        super.noInterceptor(name);
        return getQueryable5();
    }

    @Override
    public EntityQueryable5<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5> useInterceptor() {
        super.useInterceptor();
        return getQueryable5();
    }

    @Override
    public EntityQueryable5<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5> asTracking() {
        super.asTracking();
        return getQueryable5();
    }

    @Override
    public EntityQueryable5<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5> asNoTracking() {
        super.asNoTracking();
        return getQueryable5();
    }

    @Override
    public EntityQueryable5<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5> queryLargeColumn(boolean queryLarge) {
        super.queryLargeColumn(queryLarge);
        return getQueryable5();
    }

    @Override
    public EntityQueryable5<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5> useShardingConfigure(int maxShardingQueryLimit, ConnectionModeEnum connectionMode) {
        super.useShardingConfigure(maxShardingQueryLimit, connectionMode);
        return getQueryable5();
    }

    @Override
    public EntityQueryable5<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5> useMaxShardingQueryLimit(int maxShardingQueryLimit) {
        super.useMaxShardingQueryLimit(maxShardingQueryLimit);
        return getQueryable5();
    }

    @Override
    public EntityQueryable5<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5> useConnectionMode(ConnectionModeEnum connectionMode) {
        super.useConnectionMode(connectionMode);
        return getQueryable5();
    }

    @Override
    public EntityQueryable5<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5> asTable(Function<String, String> tableNameAs) {
        super.asTable(tableNameAs);
        return getQueryable5();
    }

    @Override
    public EntityQueryable5<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5> asSchema(Function<String, String> schemaAs) {
        super.asSchema(schemaAs);
        return getQueryable5();
    }

    @Override
    public EntityQueryable5<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5> asAlias(String alias) {
        super.asAlias(alias);
        return getQueryable5();
    }

    @Override
    public EntityQueryable5<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5> asTableLink(Function<String, String> linkAs) {
        super.asTableLink(linkAs);
        return getQueryable5();
    }

    @Override
    public EntityQueryable5<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5> filterConfigure(ValueFilter valueFilter) {
        super.filterConfigure(valueFilter);
        return getQueryable5();
    }
}
