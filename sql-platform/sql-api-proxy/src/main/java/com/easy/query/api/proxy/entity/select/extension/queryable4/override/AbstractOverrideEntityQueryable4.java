package com.easy.query.api.proxy.entity.select.extension.queryable4.override;

import com.easy.query.api.proxy.entity.select.EntityQueryable;
import com.easy.query.api.proxy.entity.select.EntityQueryable2;
import com.easy.query.api.proxy.entity.select.EntityQueryable4;
import com.easy.query.api.proxy.entity.select.abstraction.AbstractEntityQueryable;
import com.easy.query.api.proxy.entity.select.extension.queryable4.EntityQueryable4Available;
import com.easy.query.core.api.dynamic.sort.ObjectSort;
import com.easy.query.core.basic.api.select.ClientQueryable4;
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
public abstract class AbstractOverrideEntityQueryable4<T1Proxy extends ProxyEntity<T1Proxy, T1>, T1,
        T2Proxy extends ProxyEntity<T2Proxy, T2>, T2,
        T3Proxy extends ProxyEntity<T3Proxy, T3>, T3,
        T4Proxy extends ProxyEntity<T4Proxy, T4>, T4> extends AbstractEntityQueryable<T1Proxy, T1>
        implements EntityQueryable4Available<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4> {

    protected final ClientQueryable4<T1, T2, T3, T4> entityQueryable4;

    public AbstractOverrideEntityQueryable4(T1Proxy t1Proxy, ClientQueryable4<T1, T2, T3, T4> entityQueryable4) {
        super(t1Proxy, entityQueryable4);
        this.entityQueryable4 = entityQueryable4;
    }

    @Override
    public EntityQueryable4<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4> orderBy(boolean condition, SQLActionExpression1<T1Proxy> selectExpression) {
        super.orderBy(condition, selectExpression);
        return getQueryable4();
    }

    @Override
    public EntityQueryable4<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4> orderByObject(boolean condition, ObjectSort configuration) {
        super.orderByObject(condition, configuration);
        return getQueryable4();
    }

    @Override
    public EntityQueryable4<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4> whereById(boolean condition, Object id) {
        super.whereById(condition, id);
        return getQueryable4();
    }

    @Override
    public <TProperty> EntityQueryable4<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4> whereByIds(boolean condition, Collection<TProperty> ids) {
        super.whereByIds(condition, ids);
        return getQueryable4();
    }

    @Override
    public EntityQueryable4<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4> whereObject(boolean condition, Object object) {
        super.whereObject(condition, object);
        return getQueryable4();
    }

    @Override
    public EntityQueryable4<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4> where(boolean condition, SQLActionExpression1<T1Proxy> whereExpression) {
        super.where(condition, whereExpression);
        return getQueryable4();
    }

    @Override
    public EntityQueryable4<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4> having(boolean condition, SQLActionExpression1<T1Proxy> predicateExpression) {
        super.having(condition, predicateExpression);
        return getQueryable4();
    }
    @Override
    public EntityQueryable4<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4> limit(boolean condition, long offset, long rows) {
        super.limit(condition, offset, rows);
        return getQueryable4();
    }

    @Override
    public EntityQueryable4<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4> distinct(boolean condition) {
        super.distinct(condition);
        return getQueryable4();
    }

    @Override
    public EntityQueryable4<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4> disableLogicDelete() {
        super.disableLogicDelete();
        return getQueryable4();
    }

    @Override
    public EntityQueryable4<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4> enableLogicDelete() {
        super.enableLogicDelete();
        return getQueryable4();
    }

    @Override
    public EntityQueryable4<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4> useLogicDelete(boolean enable) {
        super.useLogicDelete(enable);
        return getQueryable4();
    }

    @Override
    public EntityQueryable4<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4> noInterceptor() {
        super.noInterceptor();
        return getQueryable4();
    }

    @Override
    public EntityQueryable4<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4> useInterceptor(String name) {
        super.useInterceptor(name);
        return getQueryable4();
    }

    @Override
    public EntityQueryable4<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4> noInterceptor(String name) {
        super.noInterceptor(name);
        return getQueryable4();
    }

    @Override
    public EntityQueryable4<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4> useInterceptor() {
        super.useInterceptor();
        return getQueryable4();
    }

    @Override
    public EntityQueryable4<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4> asTracking() {
        super.asTracking();
        return getQueryable4();
    }

    @Override
    public EntityQueryable4<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4> asNoTracking() {
        super.asNoTracking();
        return getQueryable4();
    }
    @Override
    public EntityQueryable4<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4> useShardingConfigure(int maxShardingQueryLimit, ConnectionModeEnum connectionMode) {
        super.useShardingConfigure(maxShardingQueryLimit, connectionMode);
        return getQueryable4();
    }

    @Override
    public EntityQueryable4<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4> useMaxShardingQueryLimit(int maxShardingQueryLimit) {
        super.useMaxShardingQueryLimit(maxShardingQueryLimit);
        return getQueryable4();
    }

    @Override
    public EntityQueryable4<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4> useConnectionMode(ConnectionModeEnum connectionMode) {
        super.useConnectionMode(connectionMode);
        return getQueryable4();
    }

    @Override
    public EntityQueryable4<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4> asTable(Function<String, String> tableNameAs) {


        super.asTable(tableNameAs);
        return getQueryable4();
    }

    @Override
    public EntityQueryable4<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4> asSchema(Function<String, String> schemaAs) {
        super.asSchema(schemaAs);
        return getQueryable4();
    }

    @Override
    public EntityQueryable4<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4> asAlias(String alias) {
        super.asAlias(alias);
        return getQueryable4();
    }

    @Override
    public EntityQueryable4<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4> asTableLink(Function<String, String> linkAs) {
        super.asTableLink(linkAs);
        return getQueryable4();
    }
    @Override
    public EntityQueryable4<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4> asTableSegment(BiFunction<String, String, String> segmentAs) {
        super.asTableSegment(segmentAs);
        return getQueryable4();
    }

    @Override
    public EntityQueryable4<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4> filterConfigure(ValueFilter valueFilter) {
        super.filterConfigure(valueFilter);
        return getQueryable4();
    }
    @Override
    public EntityQueryable4<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4> tableLogicDelete(Supplier<Boolean> tableLogicDel) {
        super.tableLogicDelete(tableLogicDel);
        return getQueryable4();
    }
    @Override
    public EntityQueryable4<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4> configure(SQLActionExpression1<ContextConfigurer> configurer) {
        super.configure(configurer);
        return getQueryable4();
    }
    @Override
    public <TR2Proxy extends ProxyEntity<TR2Proxy, TR2>, TR2 extends ProxyEntityAvailable<TR2, TR2Proxy>> EntityQueryable2<T1Proxy, T1, TR2Proxy, TR2> leftJoin(Class<TR2> joinClass, SQLActionExpression2<T1Proxy, TR2Proxy> on) {
        throw new UnsupportedOperationException("plz use five-parameter lambda expression (a, b, c, d, e) -> {}");
    }

    @Override
    public <TR2Proxy extends ProxyEntity<TR2Proxy, TR2>, TR2> EntityQueryable2<T1Proxy, T1, TR2Proxy, TR2> leftJoin(TR2Proxy t2Proxy, SQLActionExpression2<T1Proxy, TR2Proxy> onExpression) {
        throw new UnsupportedOperationException("plz use five-parameter lambda expression (a, b, c, d, e) -> {}");
    }

    @Override
    public <TR2Proxy extends ProxyEntity<TR2Proxy, TR2>, TR2> EntityQueryable2<T1Proxy, T1, TR2Proxy, TR2> leftJoin(EntityQueryable<TR2Proxy, TR2> joinQueryable, SQLActionExpression2<T1Proxy, TR2Proxy> on) {
        throw new UnsupportedOperationException("plz use five-parameter lambda expression (a, b, c, d, e) -> {}");
    }

    @Override
    public <TR2Proxy extends ProxyEntity<TR2Proxy, TR2>, TR2 extends ProxyEntityAvailable<TR2, TR2Proxy>> EntityQueryable2<T1Proxy, T1, TR2Proxy, TR2> rightJoin(Class<TR2> joinClass, SQLActionExpression2<T1Proxy, TR2Proxy> on) {
        throw new UnsupportedOperationException("plz use five-parameter lambda expression (a, b, c, d, e) -> {}");
    }

    @Override
    public <TR2Proxy extends ProxyEntity<TR2Proxy, TR2>, TR2> EntityQueryable2<T1Proxy, T1, TR2Proxy, TR2> rightJoin(TR2Proxy t2Proxy, SQLActionExpression2<T1Proxy, TR2Proxy> onExpression) {
        throw new UnsupportedOperationException("plz use five-parameter lambda expression (a, b, c, d, e) -> {}");
    }

    @Override
    public <TR2Proxy extends ProxyEntity<TR2Proxy, TR2>, TR2> EntityQueryable2<T1Proxy, T1, TR2Proxy, TR2> rightJoin(EntityQueryable<TR2Proxy, TR2> joinQueryable, SQLActionExpression2<T1Proxy, TR2Proxy> on) {
        throw new UnsupportedOperationException("plz use five-parameter lambda expression (a, b, c, d, e) -> {}");
    }

    @Override
    public <TR2Proxy extends ProxyEntity<TR2Proxy, TR2>, TR2 extends ProxyEntityAvailable<TR2, TR2Proxy>> EntityQueryable2<T1Proxy, T1, TR2Proxy, TR2> innerJoin(Class<TR2> joinClass, SQLActionExpression2<T1Proxy, TR2Proxy> on) {
        throw new UnsupportedOperationException("plz use five-parameter lambda expression (a, b, c, d, e) -> {}");
    }

    @Override
    public <TR2Proxy extends ProxyEntity<TR2Proxy, TR2>, TR2> EntityQueryable2<T1Proxy, T1, TR2Proxy, TR2> innerJoin(TR2Proxy t2Proxy, SQLActionExpression2<T1Proxy, TR2Proxy> onExpression) {
        throw new UnsupportedOperationException("plz use five-parameter lambda expression (a, b, c, d, e) -> {}");
    }

    @Override
    public <TR2Proxy extends ProxyEntity<TR2Proxy, TR2>, TR2> EntityQueryable2<T1Proxy, T1, TR2Proxy, TR2> innerJoin(EntityQueryable<TR2Proxy, TR2> joinQueryable, SQLActionExpression2<T1Proxy, TR2Proxy> on) {
        throw new UnsupportedOperationException("plz use five-parameter lambda expression (a, b, c, d, e) -> {}");
    }
}
