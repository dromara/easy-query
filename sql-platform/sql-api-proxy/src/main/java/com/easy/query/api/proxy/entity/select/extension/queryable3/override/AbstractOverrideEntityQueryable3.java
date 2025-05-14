package com.easy.query.api.proxy.entity.select.extension.queryable3.override;

import com.easy.query.api.proxy.entity.select.EntityQueryable3;
import com.easy.query.api.proxy.entity.select.abstraction.AbstractEntityQueryable;
import com.easy.query.api.proxy.entity.select.extension.queryable3.EntityQueryable3Available;
import com.easy.query.core.api.dynamic.sort.ObjectSort;
import com.easy.query.core.basic.api.select.ClientQueryable3;
import com.easy.query.core.enums.sharding.ConnectionModeEnum;
import com.easy.query.core.expression.builder.core.ValueFilter;
import com.easy.query.core.expression.lambda.SQLActionExpression1;
import com.easy.query.core.expression.sql.builder.internal.ContextConfigurer;
import com.easy.query.core.proxy.ProxyEntity;

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
public abstract class AbstractOverrideEntityQueryable3<T1Proxy extends ProxyEntity<T1Proxy, T1>, T1, T2Proxy extends ProxyEntity<T2Proxy, T2>, T2, T3Proxy extends ProxyEntity<T3Proxy, T3>, T3> extends AbstractEntityQueryable<T1Proxy, T1> implements EntityQueryable3Available<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3> {

    protected final ClientQueryable3<T1, T2 ,T3> entityQueryable3;

    public AbstractOverrideEntityQueryable3(T1Proxy t1Proxy, ClientQueryable3<T1, T2 ,T3> entityQueryable3) {
        super(t1Proxy,entityQueryable3);
        this.entityQueryable3 = entityQueryable3;
    }

    @Override
    public EntityQueryable3<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3> orderBy(boolean condition, SQLActionExpression1<T1Proxy> selectExpression) {
        super.orderBy(condition, selectExpression);
        return getQueryable3();
    }

    @Override
    public EntityQueryable3<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3> orderByObject(boolean condition, ObjectSort configuration) {
        super.orderByObject(condition, configuration);
        return getQueryable3();
    }

    @Override
    public EntityQueryable3<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3> whereById(boolean condition, Object id) {
        super.whereById(condition, id);
        return getQueryable3();
    }

    @Override
    public <TProperty> EntityQueryable3<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3> whereByIds(boolean condition, Collection<TProperty> ids) {
        super.whereByIds(condition, ids);
        return getQueryable3();
    }

    @Override
    public EntityQueryable3<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3> whereObject(boolean condition, Object object) {
        super.whereObject(condition, object);
        return getQueryable3();
    }

    @Override
    public EntityQueryable3<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3> where(boolean condition, SQLActionExpression1<T1Proxy> whereExpression) {
        super.where(condition, whereExpression);
        return getQueryable3();
    }

    @Override
    public EntityQueryable3<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3> having(boolean condition, SQLActionExpression1<T1Proxy> predicateExpression) {
        super.having(condition, predicateExpression);
        return getQueryable3();
    }

//    @Override
//    public <TPropertyProxy extends ProxyEntity<TPropertyProxy, TProperty>, TProperty> EntityQueryable3<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3> include(boolean condition, SQLFuncExpression2<ProxyEntityNavigateInclude<T1, T1Proxy>, T1Proxy, EntityQueryable<TPropertyProxy, TProperty>> navigateIncludeSQLExpression) {
//        super.include(condition, navigateIncludeSQLExpression);
//        return getQueryable3();
//    }

    @Override
    public EntityQueryable3<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3> limit(boolean condition, long offset, long rows) {
        super.limit(condition, offset, rows);
        return getQueryable3();
    }

    @Override
    public EntityQueryable3<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3> distinct(boolean condition) {
        super.distinct(condition);
        return getQueryable3();
    }

    @Override
    public EntityQueryable3<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3> disableLogicDelete() {
        super.disableLogicDelete();
        return getQueryable3();
    }

    @Override
    public EntityQueryable3<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3> enableLogicDelete() {
        super.enableLogicDelete();
        return getQueryable3();
    }

    @Override
    public EntityQueryable3<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3> useLogicDelete(boolean enable) {
        super.useLogicDelete(enable);
        return getQueryable3();
    }

    @Override
    public EntityQueryable3<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3> noInterceptor() {
        super.noInterceptor();
        return getQueryable3();
    }

    @Override
    public EntityQueryable3<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3> useInterceptor(String name) {
        super.useInterceptor(name);
        return getQueryable3();
    }

    @Override
    public EntityQueryable3<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3> noInterceptor(String name) {
        super.noInterceptor(name);
        return getQueryable3();
    }

    @Override
    public EntityQueryable3<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3> useInterceptor() {
        super.useInterceptor();
        return getQueryable3();
    }

    @Override
    public EntityQueryable3<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3> asTracking() {
        super.asTracking();
        return getQueryable3();
    }

    @Override
    public EntityQueryable3<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3> asNoTracking() {
        super.asNoTracking();
        return getQueryable3();
    }
    @Override
    public EntityQueryable3<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3> useShardingConfigure(int maxShardingQueryLimit, ConnectionModeEnum connectionMode) {
        super.useShardingConfigure(maxShardingQueryLimit, connectionMode);
        return getQueryable3();
    }

    @Override
    public EntityQueryable3<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3> useMaxShardingQueryLimit(int maxShardingQueryLimit) {
        super.useMaxShardingQueryLimit(maxShardingQueryLimit);
        return getQueryable3();
    }

    @Override
    public EntityQueryable3<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3> useConnectionMode(ConnectionModeEnum connectionMode) {
        super.useConnectionMode(connectionMode);
        return getQueryable3();
    }

    @Override
    public EntityQueryable3<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3> asTable(Function<String, String> tableNameAs) {
        super.asTable(tableNameAs);
        return getQueryable3();
    }

    @Override
    public EntityQueryable3<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3> asSchema(Function<String, String> schemaAs) {
        super.asSchema(schemaAs);
        return getQueryable3();
    }

    @Override
    public EntityQueryable3<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3> asAlias(String alias) {
        super.asAlias(alias);
        return getQueryable3();
    }

    @Override
    public EntityQueryable3<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3> asTableLink(Function<String, String> linkAs) {
        super.asTableLink(linkAs);
        return getQueryable3();
    }
    @Override
    public EntityQueryable3<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3> asTableSegment(BiFunction<String, String, String> segmentAs) {
        super.asTableSegment(segmentAs);
        return getQueryable3();
    }

    @Override
    public EntityQueryable3<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3> filterConfigure(ValueFilter valueFilter) {
        super.filterConfigure(valueFilter);
        return getQueryable3();
    }
    @Override
    public EntityQueryable3<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3> tableLogicDelete(Supplier<Boolean> tableLogicDel) {
        super.tableLogicDelete(tableLogicDel);
        return getQueryable3();
    }
    @Override
    public EntityQueryable3<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3> configure(SQLActionExpression1<ContextConfigurer> configurer) {
        super.configure(configurer);
        return getQueryable3();
    }
}
