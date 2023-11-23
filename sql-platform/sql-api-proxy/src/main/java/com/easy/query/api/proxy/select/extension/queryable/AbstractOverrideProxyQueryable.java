package com.easy.query.api.proxy.select.extension.queryable;

import com.easy.query.api.proxy.select.ProxyQueryable;
import com.easy.query.api.proxy.select.impl.EasyProxyQueryable;
import com.easy.query.api.proxy.sql.ProxyFilter;
import com.easy.query.api.proxy.sql.ProxySelector;
import com.easy.query.api.proxy.sql.impl.ProxyFilterImpl;
import com.easy.query.api.proxy.sql.impl.ProxySelectorImpl;
import com.easy.query.core.api.dynamic.sort.ObjectSort;
import com.easy.query.core.api.pagination.EasyPageResult;
import com.easy.query.core.basic.api.select.ClientQueryable;
import com.easy.query.core.basic.api.select.executor.MethodQuery;
import com.easy.query.core.basic.jdbc.executor.internal.enumerable.JdbcStreamResult;
import com.easy.query.core.basic.jdbc.parameter.ToSQLContext;
import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.enums.sharding.ConnectionModeEnum;
import com.easy.query.core.expression.builder.core.ValueFilter;
import com.easy.query.core.expression.lambda.SQLConsumer;
import com.easy.query.core.expression.lambda.SQLExpression2;
import com.easy.query.core.expression.segment.ColumnSegment;
import com.easy.query.core.expression.sql.builder.EntityQueryExpressionBuilder;
import com.easy.query.core.proxy.ProxyEntity;
import com.easy.query.core.util.EasyObjectUtil;

import java.sql.Statement;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * create time 2023/9/10 14:26
 * 文件说明
 *
 * @author xuejiaming
 */
public abstract class AbstractOverrideProxyQueryable<T1Proxy extends ProxyEntity<T1Proxy, T1>, T1> implements ProxyQueryable<T1Proxy, T1> {
    protected final QueryRuntimeContext runtimeContext;
    protected final ClientQueryable<T1> entityQueryable;

    public AbstractOverrideProxyQueryable(ClientQueryable<T1> entityQueryable) {
        EntityQueryExpressionBuilder sqlEntityExpressionBuilder = entityQueryable.getSQLEntityExpressionBuilder();
        this.runtimeContext = sqlEntityExpressionBuilder.getRuntimeContext();
        this.entityQueryable = entityQueryable;
    }

    @Override
    public QueryRuntimeContext getRuntimeContext() {
        return runtimeContext;
    }

    @Override
    public ClientQueryable<T1> getClientQueryable() {
        return entityQueryable;
    }

    @Override
    public ProxyQueryable<T1Proxy, T1> getQueryable() {
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
    public long countDistinct(SQLExpression2<ProxySelector, T1Proxy> selectExpression) {
        return entityQueryable.countDistinct(selector -> {
            selectExpression.apply(new ProxySelectorImpl(selector.getSelector()), get1Proxy());
        });
    }

    @Override
    public boolean any() {
        return entityQueryable.any();
    }

    @Override
    public boolean all(SQLExpression2<ProxyFilter,T1Proxy> whereExpression) {
        return entityQueryable.all(where -> {
            whereExpression.apply(new ProxyFilterImpl(where.getFilter()), get1Proxy());
        });
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
    public Map<String, Object> toMap() {
        return entityQueryable.toMap();
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
    public <TR> JdbcStreamResult<TR> toStreamResult(Class<TR> resultClass, SQLConsumer<Statement> configurer) {
        return entityQueryable.toStreamResult(resultClass,configurer);
    }

    @Override
    public <TR> String toSQL(Class<TR> resultClass, ToSQLContext toSQLContext) {
        return entityQueryable.toSQL(resultClass, toSQLContext);
    }

    @Override
    public ProxyQueryable<T1Proxy, T1> select(String columns) {
        entityQueryable.select(columns);
        return this;
    }

    @Override
    public ProxyQueryable<T1Proxy, T1> select(Collection<ColumnSegment> columnSegments, boolean clearAll) {
        entityQueryable.select(columnSegments, clearAll);
        return this;
    }

    @Override
    public <TRProxy extends ProxyEntity<TRProxy, TR>, TR> ProxyQueryable<TRProxy, TR> select(ProxyEntity<TRProxy, TR> trProxy) {
        ClientQueryable<TR> select = entityQueryable.select(trProxy.getEntityClass());
        return new EasyProxyQueryable<>(EasyObjectUtil.typeCastNullable(trProxy), select);
    }

//    @Override
//    public ProxyQueryable<T1Proxy, T1> where1(SQLFuncExpression1<T1Proxy, SQLPredicate> whereExpression) {
//        SQLPredicate sqlPredicate = whereExpression.apply(get1Proxy());
//        entityQueryable.where(wherePredicate -> {
//            sqlPredicate.accept(wherePredicate.getFilter());
//        });
//        return this;
//    }

    @Override
    public ProxyQueryable<T1Proxy, T1> whereById(boolean condition, Object id) {
        if (condition) {
            entityQueryable.whereById(id);
        }
        return this;
    }

    @Override
    public <TProperty> ProxyQueryable<T1Proxy, T1> whereByIds(boolean condition, Collection<TProperty> ids) {

        if (condition) {
            entityQueryable.whereByIds(ids);
        }
        return this;
    }

    @Override
    public ProxyQueryable<T1Proxy, T1> whereObject(boolean condition, Object object) {
        if (condition) {
            entityQueryable.whereObject(object);
        }
        return this;
    }

    @Override
    public ProxyQueryable<T1Proxy, T1> orderByObject(boolean condition, ObjectSort objectSort) {
        if (condition) {
            entityQueryable.orderByObject(objectSort);
        }
        return this;
    }

    @Override
    public ProxyQueryable<T1Proxy, T1> distinct(boolean condition) {
        if (condition) {
            entityQueryable.distinct();
        }
        return this;
    }

    @Override
    public ProxyQueryable<T1Proxy, T1> limit(boolean condition, long offset, long rows) {
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
    public ProxyQueryable<T1Proxy, T1> useLogicDelete(boolean enable) {
        entityQueryable.useLogicDelete(enable);
        return this;
    }

    @Override
    public ProxyQueryable<T1Proxy, T1> noInterceptor() {
        entityQueryable.noInterceptor();
        return this;
    }

    @Override
    public ProxyQueryable<T1Proxy, T1> useInterceptor(String name) {
        entityQueryable.useInterceptor(name);
        return this;
    }

    @Override
    public ProxyQueryable<T1Proxy, T1> noInterceptor(String name) {
        entityQueryable.noInterceptor(name);
        return this;
    }

    @Override
    public ProxyQueryable<T1Proxy, T1> useInterceptor() {
        entityQueryable.useInterceptor();
        return this;
    }

    @Override
    public ProxyQueryable<T1Proxy, T1> asTracking() {
        entityQueryable.asTracking();
        return this;
    }

    @Override
    public ProxyQueryable<T1Proxy, T1> asNoTracking() {
        entityQueryable.asNoTracking();
        return this;
    }

    @Override
    public ProxyQueryable<T1Proxy, T1> asTable(Function<String, String> tableNameAs) {
        entityQueryable.asTable(tableNameAs);
        return this;
    }

    @Override
    public ProxyQueryable<T1Proxy, T1> asSchema(Function<String, String> schemaAs) {
        entityQueryable.asSchema(schemaAs);
        return this;
    }

    @Override
    public ProxyQueryable<T1Proxy, T1> asAlias(String alias) {
        entityQueryable.asAlias(alias);
        return this;
    }

    @Override
    public ProxyQueryable<T1Proxy, T1> asTableLink(Function<String, String> linkAs) {
        entityQueryable.asTableLink(linkAs);
        return this;
    }

    @Override
    public ProxyQueryable<T1Proxy, T1> useShardingConfigure(int maxShardingQueryLimit, ConnectionModeEnum connectionMode) {
        entityQueryable.useShardingConfigure(maxShardingQueryLimit, connectionMode);
        return this;
    }

    @Override
    public ProxyQueryable<T1Proxy, T1> useMaxShardingQueryLimit(int maxShardingQueryLimit) {
        entityQueryable.useMaxShardingQueryLimit(maxShardingQueryLimit);
        return this;
    }

    @Override
    public ProxyQueryable<T1Proxy, T1> useConnectionMode(ConnectionModeEnum connectionMode) {
        entityQueryable.useConnectionMode(connectionMode);
        return this;
    }

    @Override
    public ProxyQueryable<T1Proxy, T1> queryLargeColumn(boolean queryLarge) {
        entityQueryable.queryLargeColumn(queryLarge);
        return this;
    }

    @Override
    public ProxyQueryable<T1Proxy, T1> filterConfigure(ValueFilter valueFilter) {
        entityQueryable.filterConfigure(valueFilter);
        return this;
    }

    @Override
    public MethodQuery<T1> forEach(Consumer<T1> mapConfigure) {
        entityQueryable.forEach(mapConfigure);
        return this;
    }
}
