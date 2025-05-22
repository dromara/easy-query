package com.easy.query.api.proxy.entity.select.extension.queryable6.override;

import com.easy.query.api.proxy.entity.select.EntityQueryable;
import com.easy.query.api.proxy.entity.select.EntityQueryable2;
import com.easy.query.api.proxy.entity.select.EntityQueryable6;
import com.easy.query.api.proxy.entity.select.abstraction.AbstractEntityQueryable;
import com.easy.query.api.proxy.entity.select.extension.queryable6.EntityQueryable6Available;
import com.easy.query.core.api.dynamic.sort.ObjectSort;
import com.easy.query.core.basic.api.select.ClientQueryable6;
import com.easy.query.core.enums.sharding.ConnectionModeEnum;
import com.easy.query.core.expression.builder.core.ValueFilter;
import com.easy.query.core.expression.lambda.SQLActionExpression1;
import com.easy.query.core.expression.lambda.SQLActionExpression2;
import com.easy.query.core.expression.sql.builder.internal.ContextConfigurer;
import com.easy.query.core.proxy.ProxyEntity;
import com.easy.query.core.proxy.ProxyEntityAvailable;

import java.util.Collection;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * create time 2023/8/16 07:52
 * 文件说明
 *
 * @author xuejiaming
 */
public abstract class AbstractOverrideEntityQueryable6<T1Proxy extends ProxyEntity<T1Proxy, T1>, T1,
        T2Proxy extends ProxyEntity<T2Proxy, T2>, T2,
        T3Proxy extends ProxyEntity<T3Proxy, T3>, T3,
        T4Proxy extends ProxyEntity<T4Proxy, T4>, T4,
        T5Proxy extends ProxyEntity<T5Proxy, T5>, T5,
        T6Proxy extends ProxyEntity<T6Proxy, T6>, T6> extends AbstractEntityQueryable<T1Proxy, T1>
        implements EntityQueryable6Available<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6> {

    protected final ClientQueryable6<T1, T2, T3, T4, T5, T6> entityQueryable6;

    public AbstractOverrideEntityQueryable6(T1Proxy t1Proxy, ClientQueryable6<T1, T2, T3, T4, T5, T6> entityQueryable6) {
        super(t1Proxy, entityQueryable6);
        this.entityQueryable6 = entityQueryable6;
    }

    @Override
    public EntityQueryable6<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6> orderBy(boolean condition, SQLActionExpression1<T1Proxy> selectExpression) {
        super.orderBy(condition, selectExpression);
        return getQueryable6();
    }

    @Override
    public EntityQueryable6<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6> orderByObject(boolean condition, ObjectSort configuration) {
        super.orderByObject(condition, configuration);
        return getQueryable6();
    }

    @Override
    public EntityQueryable6<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6> whereById(boolean condition, Object id) {
        super.whereById(condition, id);
        return getQueryable6();
    }

    @Override
    public <TProperty> EntityQueryable6<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6> whereByIds(boolean condition, Collection<TProperty> ids) {
        super.whereByIds(condition, ids);
        return getQueryable6();
    }

    @Override
    public EntityQueryable6<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6> whereObject(boolean condition, Object object) {
        super.whereObject(condition, object);
        return getQueryable6();
    }

    @Override
    public EntityQueryable6<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6> where(boolean condition, SQLActionExpression1<T1Proxy> whereExpression) {
        super.where(condition, whereExpression);
        return getQueryable6();
    }

    @Override
    public EntityQueryable6<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6> having(boolean condition, SQLActionExpression1<T1Proxy> predicateExpression) {
        super.having(condition, predicateExpression);
        return getQueryable6();
    }

    @Override
    public EntityQueryable6<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6> limit(boolean condition, long offset, long rows) {
        super.limit(condition, offset, rows);
        return getQueryable6();
    }

    @Override
    public EntityQueryable6<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6> distinct(boolean condition) {
        super.distinct(condition);
        return getQueryable6();
    }

    @Override
    public EntityQueryable6<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6> disableLogicDelete() {
        super.disableLogicDelete();
        return getQueryable6();
    }

    @Override
    public EntityQueryable6<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6> enableLogicDelete() {
        super.enableLogicDelete();
        return getQueryable6();
    }

    @Override
    public EntityQueryable6<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6> useLogicDelete(boolean enable) {
        super.useLogicDelete(enable);
        return getQueryable6();
    }

    @Override
    public EntityQueryable6<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6> noInterceptor() {
        super.noInterceptor();
        return getQueryable6();
    }

    @Override
    public EntityQueryable6<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6> useInterceptor(String name) {
        super.useInterceptor(name);
        return getQueryable6();
    }

    @Override
    public EntityQueryable6<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6> noInterceptor(String name) {
        super.noInterceptor(name);
        return getQueryable6();
    }

    @Override
    public EntityQueryable6<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6> useInterceptor() {
        super.useInterceptor();
        return getQueryable6();
    }

    @Override
    public EntityQueryable6<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6> asTracking() {
        super.asTracking();
        return getQueryable6();
    }

    @Override
    public EntityQueryable6<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6> asNoTracking() {
        super.asNoTracking();
        return getQueryable6();
    }

    @Override
    public EntityQueryable6<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6> useShardingConfigure(int maxShardingQueryLimit, ConnectionModeEnum connectionMode) {
        super.useShardingConfigure(maxShardingQueryLimit, connectionMode);
        return getQueryable6();
    }

    @Override
    public EntityQueryable6<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6> useMaxShardingQueryLimit(int maxShardingQueryLimit) {
        super.useMaxShardingQueryLimit(maxShardingQueryLimit);
        return getQueryable6();
    }

    @Override
    public EntityQueryable6<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6> useConnectionMode(ConnectionModeEnum connectionMode) {
        super.useConnectionMode(connectionMode);
        return getQueryable6();
    }

    @Override
    public EntityQueryable6<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6> asTable(Function<String, String> tableNameAs) {
        super.asTable(tableNameAs);
        return getQueryable6();
    }

    @Override
    public EntityQueryable6<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6> asSchema(Function<String, String> schemaAs) {
        super.asSchema(schemaAs);
        return getQueryable6();
    }

    @Override
    public EntityQueryable6<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6> asAlias(String alias) {
        super.asAlias(alias);
        return getQueryable6();
    }

    @Override
    public EntityQueryable6<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6> asTableLink(Function<String, String> linkAs) {
        super.asTableLink(linkAs);
        return getQueryable6();
    }
    @Override
    public EntityQueryable6<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6> asTableSegment(BiFunction<String, String, String> segmentAs) {
        super.asTableSegment(segmentAs);
        return getQueryable6();
    }

    @Override
    public EntityQueryable6<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6> filterConfigure(ValueFilter valueFilter) {
        super.filterConfigure(valueFilter);
        return getQueryable6();
    }
    @Override
    public EntityQueryable6<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6> tableLogicDelete(Supplier<Boolean> tableLogicDel) {
        super.tableLogicDelete(tableLogicDel);
        return getQueryable6();
    }
    @Override
    public EntityQueryable6<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6> configure(SQLActionExpression1<ContextConfigurer> configurer){
        super.configure(configurer);
        return getQueryable6();
    }
}
