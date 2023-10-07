package com.easy.query.api.proxy.select.extension.queryable4;

import com.easy.query.api.proxy.select.ProxyQueryable;
import com.easy.query.api.proxy.select.ProxyQueryable4;
import com.easy.query.api.proxy.select.impl.EasyProxyQueryable;
import com.easy.query.core.api.pagination.EasyPageResult;
import com.easy.query.core.basic.api.select.ClientQueryable;
import com.easy.query.core.basic.api.select.ClientQueryable4;
import com.easy.query.core.basic.jdbc.executor.internal.enumerable.JdbcStreamResult;
import com.easy.query.core.basic.jdbc.parameter.ToSQLContext;
import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.enums.sharding.ConnectionModeEnum;
import com.easy.query.core.expression.builder.core.ValueFilter;
import com.easy.query.core.expression.segment.ColumnSegment;
import com.easy.query.core.expression.sql.builder.EntityQueryExpressionBuilder;
import com.easy.query.core.proxy.ProxyEntity;
import com.easy.query.core.util.EasyObjectUtil;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 * create time 2023/9/10 14:18
 * 文件说明
 *
 * @author xuejiaming
 */
public abstract class AbstractOverrideProxyQueryable4<T1Proxy extends ProxyEntity<T1Proxy, T1>, T1,
        T2Proxy extends ProxyEntity<T2Proxy, T2>, T2,
        T3Proxy extends ProxyEntity<T3Proxy, T3>, T3,
        T4Proxy extends ProxyEntity<T4Proxy, T4>, T4> implements ProxyQueryable4<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4> {

    protected final QueryRuntimeContext runtimeContext;
    protected final ClientQueryable4<T1,T2,T3,T4> entityQueryable;

    public AbstractOverrideProxyQueryable4(ClientQueryable4<T1,T2,T3,T4> entityQueryable) {
        EntityQueryExpressionBuilder sqlEntityExpressionBuilder = entityQueryable.getSQLEntityExpressionBuilder();
        this.runtimeContext = sqlEntityExpressionBuilder.getRuntimeContext();
        this.entityQueryable = entityQueryable;
    }


    @Override
    public QueryRuntimeContext getRuntimeContext() {
        return runtimeContext;
    }

    @Override
    public ClientQueryable4<T1,T2,T3,T4> getClientQueryable4() {
        return entityQueryable;
    }

    @Override
    public ClientQueryable<T1> getClientQueryable() {
        return entityQueryable;
    }

    @Override
    public ProxyQueryable4<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4> getQueryable4() {
        return this;
    }


    @Override
    public Class<T1> queryClass() {
        return entityQueryable.queryClass();
    }

    @Override
    public long count() {
        return entityQueryable.count();
    }


    @Override
    public boolean any() {
        return entityQueryable.any();
    }


    @Override
    public <TR> TR firstOrNull(Class<TR> resultClass) {
        return entityQueryable.firstOrNull(resultClass);
    }

    @Override
    public <TR> TR firstNotNull(Class<TR> resultClass, String msg, String code) {
        return entityQueryable.firstNotNull(resultClass, msg, code);
    }

    @Override
    public <TR> TR singleOrNull(Class<TR> resultClass) {
        return entityQueryable.singleOrNull(resultClass);
    }

    @Override
    public <TR> TR singleNotNull(Class<TR> resultClass, String msg, String code) {
        return entityQueryable.singleNotNull(resultClass, msg, code);
    }

    @Override
    public List<Map<String, Object>> toMaps() {
        return entityQueryable.toMaps();
    }

    @Override
    public <TRProxy extends ProxyEntity<TRProxy, TR>, TR> List<TR> toList(TRProxy trProxy) {
        return toList(trProxy.getEntityClass());
    }

    @Override
    public <TR> List<TR> toList(Class<TR> resultClass) {
        return entityQueryable.toList(resultClass);
    }

    @Override
    public <TR> JdbcStreamResult<TR> toStreamResult(Class<TR> resultClass) {
        return entityQueryable.toStreamResult(resultClass);
    }

    @Override
    public <TR> String toSQL(Class<TR> resultClass, ToSQLContext toSQLContext) {
        return entityQueryable.toSQL(resultClass, toSQLContext);
    }

    @Override
    public ProxyQueryable<T1Proxy, T1> select(String columns) {
        entityQueryable.select(columns);
        return new EasyProxyQueryable<>(get1Proxy(), entityQueryable);
    }

    @Override
    public ProxyQueryable<T1Proxy, T1> select(Collection<ColumnSegment> columnSegments, boolean clearAll) {
        entityQueryable.select(columnSegments, clearAll);
        return new EasyProxyQueryable<>(get1Proxy(), entityQueryable);
    }

    @Override
    public <TRProxy extends ProxyEntity<TRProxy, TR>, TR> ProxyQueryable<TRProxy, TR> select(ProxyEntity<TRProxy, TR> trProxy) {
        ClientQueryable<TR> select = entityQueryable.select(trProxy.getEntityClass());
        return new EasyProxyQueryable<>(EasyObjectUtil.typeCastNullable(trProxy), select);
    }

    @Override
    public ProxyQueryable4<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4> distinct(boolean condition) {
        if (condition) {
            entityQueryable.distinct();
        }
        return this;
    }

    @Override
    public ProxyQueryable4<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4> limit(boolean condition, long offset, long rows) {
        if (condition) {
            entityQueryable.limit(offset, rows);
        }
        return this;
    }

    @Override
    public EasyPageResult<T1> toPageResult(long pageIndex, long pageSize, long pageTotal) {
        return entityQueryable.toPageResult(pageIndex, pageSize, pageTotal);
    }

    @Override
    public EasyPageResult<T1> toShardingPageResult(long pageIndex, long pageSize, List<Long> totalLines) {
        return entityQueryable.toShardingPageResult(pageIndex, pageSize, totalLines);
    }

    @Override
    public EntityQueryExpressionBuilder getSQLEntityExpressionBuilder() {
        return entityQueryable.getSQLEntityExpressionBuilder();
    }

    @Override
    public ProxyQueryable4<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4> useLogicDelete(boolean enable) {
        entityQueryable.useLogicDelete(enable);
        return this;
    }

    @Override
    public ProxyQueryable4<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4> noInterceptor() {
        entityQueryable.noInterceptor();
        return this;
    }

    @Override
    public ProxyQueryable4<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4> useInterceptor(String name) {
        entityQueryable.useInterceptor(name);
        return this;
    }

    @Override
    public ProxyQueryable4<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4> noInterceptor(String name) {
        entityQueryable.noInterceptor(name);
        return this;
    }

    @Override
    public ProxyQueryable4<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4> useInterceptor() {
        entityQueryable.useInterceptor();
        return this;
    }

    @Override
    public ProxyQueryable4<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4> asTracking() {
        entityQueryable.asTracking();
        return this;
    }

    @Override
    public ProxyQueryable4<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4> asNoTracking() {
        entityQueryable.asNoTracking();
        return this;
    }

    @Override
    public ProxyQueryable4<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4> asTable(Function<String, String> tableNameAs) {
        entityQueryable.asTable(tableNameAs);
        return this;
    }

    @Override
    public ProxyQueryable4<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4> asSchema(Function<String, String> schemaAs) {
        entityQueryable.asSchema(schemaAs);
        return this;
    }

    @Override
    public ProxyQueryable4<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4> asAlias(String alias) {
        entityQueryable.asAlias(alias);
        return this;
    }

    @Override
    public ProxyQueryable4<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4> asTableLink(Function<String, String> linkAs) {
        entityQueryable.asTableLink(linkAs);
        return this;
    }

    @Override
    public ProxyQueryable4<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4> useShardingConfigure(int maxShardingQueryLimit, ConnectionModeEnum connectionMode) {
        entityQueryable.useShardingConfigure(maxShardingQueryLimit, connectionMode);
        return this;
    }

    @Override
    public ProxyQueryable4<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4> useMaxShardingQueryLimit(int maxShardingQueryLimit) {
        entityQueryable.useMaxShardingQueryLimit(maxShardingQueryLimit);
        return this;
    }

    @Override
    public ProxyQueryable4<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4> useConnectionMode(ConnectionModeEnum connectionMode) {
        entityQueryable.useConnectionMode(connectionMode);
        return this;
    }

    @Override
    public ProxyQueryable4<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4> queryLargeColumn(boolean queryLarge) {
        entityQueryable.queryLargeColumn(queryLarge);
        return this;
    }

    @Override
    public ProxyQueryable4<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4> filterConfigure(ValueFilter valueFilter) {
        entityQueryable.filterConfigure(valueFilter);
        return this;
    }
}
