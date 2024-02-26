package com.easy.query.api.proxy.entity.select.extension.queryable9.override;

import com.easy.query.api.proxy.entity.select.EntityQueryable9;
import com.easy.query.api.proxy.entity.select.abstraction.AbstractEntityQueryable;
import com.easy.query.api.proxy.entity.select.extension.queryable9.EntityQueryable9Available;
import com.easy.query.core.api.dynamic.sort.ObjectSort;
import com.easy.query.core.basic.api.select.ClientQueryable9;
import com.easy.query.core.enums.sharding.ConnectionModeEnum;
import com.easy.query.core.expression.builder.core.ValueFilter;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.proxy.ProxyEntity;

import java.util.Collection;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * create time 2023/8/16 07:52
 * 文件说明
 *
 * @author xuejiaming
 */
public abstract class AbstractOverrideEntityQueryable9<T1Proxy extends ProxyEntity<T1Proxy, T1>, T1,
        T2Proxy extends ProxyEntity<T2Proxy, T2>, T2,
        T3Proxy extends ProxyEntity<T3Proxy, T3>, T3,
        T4Proxy extends ProxyEntity<T4Proxy, T4>, T4,
        T5Proxy extends ProxyEntity<T5Proxy, T5>, T5,
        T6Proxy extends ProxyEntity<T6Proxy, T6>, T6,
        T7Proxy extends ProxyEntity<T7Proxy, T7>, T7,
        T8Proxy extends ProxyEntity<T8Proxy, T8>, T8,
        T9Proxy extends ProxyEntity<T9Proxy, T9>, T9> extends AbstractEntityQueryable<T1Proxy, T1>
        implements EntityQueryable9Available<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6, T7Proxy, T7, T8Proxy, T8, T9Proxy, T9> {

    protected final ClientQueryable9<T1, T2, T3, T4, T5, T6, T7, T8, T9> entityQueryable9;

    public AbstractOverrideEntityQueryable9(T1Proxy t1Proxy, ClientQueryable9<T1, T2, T3, T4, T5, T6, T7, T8, T9> entityQueryable9) {
        super(t1Proxy, entityQueryable9);
        this.entityQueryable9 = entityQueryable9;
    }

    @Override
    public EntityQueryable9<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6, T7Proxy, T7, T8Proxy, T8, T9Proxy, T9> orderBy(boolean condition, SQLExpression1<T1Proxy> selectExpression) {
        super.orderBy(condition, selectExpression);
        return getQueryable9();
    }

    @Override
    public EntityQueryable9<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6, T7Proxy, T7, T8Proxy, T8, T9Proxy, T9> orderByObject(boolean condition, ObjectSort configuration) {
        super.orderByObject(condition, configuration);
        return getQueryable9();
    }

    @Override
    public EntityQueryable9<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6, T7Proxy, T7, T8Proxy, T8, T9Proxy, T9> whereById(boolean condition, Object id) {
        super.whereById(condition, id);
        return getQueryable9();
    }

    @Override
    public <TProperty> EntityQueryable9<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6, T7Proxy, T7, T8Proxy, T8, T9Proxy, T9> whereByIds(boolean condition, Collection<TProperty> ids) {
        super.whereByIds(condition, ids);
        return getQueryable9();
    }

    @Override
    public EntityQueryable9<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6, T7Proxy, T7, T8Proxy, T8, T9Proxy, T9> whereObject(boolean condition, Object object) {
        super.whereObject(condition, object);
        return getQueryable9();
    }

    @Override
    public EntityQueryable9<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6, T7Proxy, T7, T8Proxy, T8, T9Proxy, T9> where(boolean condition, SQLExpression1<T1Proxy> whereExpression) {
        super.where(condition, whereExpression);
        return getQueryable9();
    }
    @Override
    public EntityQueryable9<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6, T7Proxy, T7, T8Proxy, T8, T9Proxy, T9> having(boolean condition, SQLExpression1<T1Proxy> predicateExpression) {
        super.having(condition, predicateExpression);
        return getQueryable9();
    }

//    @Override
//    public <TPropertyProxy extends ProxyEntity<TPropertyProxy, TProperty>, TProperty> EntityQueryable9<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6, T7Proxy, T7, T8Proxy, T8, T9Proxy, T9> include(boolean condition, SQLFuncExpression2<ProxyEntityNavigateInclude<T1, T1Proxy>, T1Proxy, EntityQueryable<TPropertyProxy, TProperty>> navigateIncludeSQLExpression) {
//        super.include(condition, navigateIncludeSQLExpression);
//        return getQueryable9();
//    }


    @Override
    public EntityQueryable9<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6, T7Proxy, T7, T8Proxy, T8, T9Proxy, T9> limit(boolean condition, long offset, long rows) {
        super.limit(condition, offset, rows);
        return getQueryable9();
    }

    @Override
    public EntityQueryable9<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6, T7Proxy, T7, T8Proxy, T8, T9Proxy, T9> distinct(boolean condition) {
        super.distinct(condition);
        return getQueryable9();
    }

    @Override
    public EntityQueryable9<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6, T7Proxy, T7, T8Proxy, T8, T9Proxy, T9> disableLogicDelete() {
        super.disableLogicDelete();
        return getQueryable9();
    }

    @Override
    public EntityQueryable9<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6, T7Proxy, T7, T8Proxy, T8, T9Proxy, T9> enableLogicDelete() {
        super.enableLogicDelete();
        return getQueryable9();
    }

    @Override
    public EntityQueryable9<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6, T7Proxy, T7, T8Proxy, T8, T9Proxy, T9> useLogicDelete(boolean enable) {
        super.useLogicDelete(enable);
        return getQueryable9();
    }

    @Override
    public EntityQueryable9<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6, T7Proxy, T7, T8Proxy, T8, T9Proxy, T9> noInterceptor() {
        super.noInterceptor();
        return getQueryable9();
    }

    @Override
    public EntityQueryable9<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6, T7Proxy, T7, T8Proxy, T8, T9Proxy, T9> useInterceptor(String name) {
        super.useInterceptor(name);
        return getQueryable9();
    }

    @Override
    public EntityQueryable9<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6, T7Proxy, T7, T8Proxy, T8, T9Proxy, T9> noInterceptor(String name) {
        super.noInterceptor(name);
        return getQueryable9();
    }

    @Override
    public EntityQueryable9<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6, T7Proxy, T7, T8Proxy, T8, T9Proxy, T9> useInterceptor() {
        super.useInterceptor();
        return getQueryable9();
    }

    @Override
    public EntityQueryable9<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6, T7Proxy, T7, T8Proxy, T8, T9Proxy, T9> asTracking() {
        super.asTracking();
        return getQueryable9();
    }

    @Override
    public EntityQueryable9<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6, T7Proxy, T7, T8Proxy, T8, T9Proxy, T9> asNoTracking() {
        super.asNoTracking();
        return getQueryable9();
    }

    @Override
    public EntityQueryable9<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6, T7Proxy, T7, T8Proxy, T8, T9Proxy, T9> queryLargeColumn(boolean queryLarge) {
        super.queryLargeColumn(queryLarge);
        return getQueryable9();
    }

    @Override
    public EntityQueryable9<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6, T7Proxy, T7, T8Proxy, T8, T9Proxy, T9> useShardingConfigure(int maxShardingQueryLimit, ConnectionModeEnum connectionMode) {
        super.useShardingConfigure(maxShardingQueryLimit, connectionMode);
        return getQueryable9();
    }

    @Override
    public EntityQueryable9<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6, T7Proxy, T7, T8Proxy, T8, T9Proxy, T9> useMaxShardingQueryLimit(int maxShardingQueryLimit) {
        super.useMaxShardingQueryLimit(maxShardingQueryLimit);
        return getQueryable9();
    }

    @Override
    public EntityQueryable9<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6, T7Proxy, T7, T8Proxy, T8, T9Proxy, T9> useConnectionMode(ConnectionModeEnum connectionMode) {
        super.useConnectionMode(connectionMode);
        return getQueryable9();
    }

    @Override
    public EntityQueryable9<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6, T7Proxy, T7, T8Proxy, T8, T9Proxy, T9> asTable(Function<String, String> tableNameAs) {
        super.asTable(tableNameAs);
        return getQueryable9();
    }

    @Override
    public EntityQueryable9<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6, T7Proxy, T7, T8Proxy, T8, T9Proxy, T9> asSchema(Function<String, String> schemaAs) {
        super.asSchema(schemaAs);
        return getQueryable9();
    }

    @Override
    public EntityQueryable9<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6, T7Proxy, T7, T8Proxy, T8, T9Proxy, T9> asAlias(String alias) {
        super.asAlias(alias);
        return getQueryable9();
    }

    @Override
    public EntityQueryable9<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6, T7Proxy, T7, T8Proxy, T8, T9Proxy, T9> asTableLink(Function<String, String> linkAs) {
        super.asTableLink(linkAs);
        return getQueryable9();
    }

    @Override
    public EntityQueryable9<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6, T7Proxy, T7, T8Proxy, T8, T9Proxy, T9> filterConfigure(ValueFilter valueFilter) {
        super.filterConfigure(valueFilter);
        return getQueryable9();
    }
    @Override
    public EntityQueryable9<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6, T7Proxy, T7, T8Proxy, T8, T9Proxy, T9> tableLogicDelete(Supplier<Boolean> tableLogicDel) {
        super.tableLogicDelete(tableLogicDel);
        return getQueryable9();
    }
}
