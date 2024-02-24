package com.easy.query.api.proxy.entity.select.impl;

import com.easy.query.core.api.pagination.EasyPageResult;
import com.easy.query.core.basic.api.select.Query;
import com.easy.query.core.basic.jdbc.executor.internal.enumerable.JdbcStreamResult;
import com.easy.query.core.basic.jdbc.parameter.ToSQLContext;
import com.easy.query.core.basic.pagination.EasyPageResultProvider;
import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.enums.sharding.ConnectionModeEnum;
import com.easy.query.core.expression.lambda.SQLConsumer;
import com.easy.query.core.expression.sql.builder.EntityQueryExpressionBuilder;
import com.easy.query.core.metadata.EntityMetadata;
import com.easy.query.core.metadata.EntityMetadataManager;
import com.easy.query.core.metadata.NavigateMetadata;
import com.easy.query.core.util.EasyObjectUtil;

import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * create time 2024/2/24 21:53
 * 文件说明
 *
 * @author xuejiaming
 */
public class EasyColumnsQueryable<TEntity> implements Query<List<TEntity>> {

    private final Query<?> queryable;
    private final String navValue;
    private final NavigateMetadata navigateMetadata;
    private final QueryRuntimeContext runtimeContext;

    public EasyColumnsQueryable(Query<?> queryable, String navValue) {

        this.queryable = queryable;
        this.navValue = navValue;
        this.runtimeContext = queryable.getSQLEntityExpressionBuilder().getRuntimeContext();
        EntityMetadataManager entityMetadataManager = runtimeContext.getEntityMetadataManager();
        this.navigateMetadata = entityMetadataManager.getEntityMetadata(queryable.queryClass()).getNavigateNotNull(navValue);
    }

    @Override
    public Query<List<TEntity>> cloneQueryable() {
        return new EasyColumnsQueryable<>(queryable.cloneQueryable(),navValue);
    }

    @Override
    public EntityQueryExpressionBuilder getSQLEntityExpressionBuilder() {
        return queryable.getSQLEntityExpressionBuilder();
    }

    @Override
    public Query<List<TEntity>> select(String columns) {
        throw new UnsupportedOperationException();
    }

    @Override
    public <TR> String toSQL(Class<TR> resultClass, ToSQLContext toSQLContext) {
        throw new UnsupportedOperationException();
    }

    @Override
    public long count() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean any() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Query<List<TEntity>> distinct(boolean condition) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Query<List<TEntity>> limit(boolean condition, long offset, long rows) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Query<List<TEntity>> asTracking() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Query<List<TEntity>> asNoTracking() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Query<List<TEntity>> useShardingConfigure(int maxShardingQueryLimit, ConnectionModeEnum connectionMode) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Query<List<TEntity>> useMaxShardingQueryLimit(int maxShardingQueryLimit) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Query<List<TEntity>> useConnectionMode(ConnectionModeEnum connectionMode) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Class<List<TEntity>> queryClass() {
        return EasyObjectUtil.typeCastNullable(List.class);
    }

    @Override
    public List<TEntity> findOrNull(Object id) {
        Object entity = queryable.findOrNull(id);
        return getNavigates(entity);
    }

    private <TResult> TResult getNavigates(Object entity) {
        if (entity != null) {
            Collection<?> values = EasyObjectUtil.typeCastNullable(navigateMetadata.getGetter().apply(entity));
            if (values == null) {
                return null;
            }
            return EasyObjectUtil.typeCastNullable(new ArrayList<>(values));
        }
        return null;
    }

    @Override
    public List<TEntity> findNotNull(Object id, Supplier<RuntimeException> throwFunc) {
        Object entity = queryable.findNotNull(id, throwFunc);
        return getNavigates(entity);
    }

    @Override
    public <TR> TR firstOrNull(Class<TR> resultClass) {
        Object entity = queryable.firstOrNull(queryable.queryClass());
        return getNavigates(entity);
    }

    @Override
    public <TR> TR firstNotNull(Class<TR> resultClass, Supplier<RuntimeException> throwFunc) {
        Object entity = queryable.firstNotNull(queryable.queryClass(), throwFunc);
        return getNavigates(entity);
    }

    @Override
    public <TR> List<TR> toList(Class<TR> resultClass) {
        List<?> entities = queryable.toList(queryable.queryClass());
        return entities.stream().map(o -> {
            return this.<TR>getNavigates(o);
        }).collect(Collectors.toList());
    }

    @Override
    public Map<String, Object> toMap() {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<Map<String, Object>> toMaps() {
        throw new UnsupportedOperationException();
    }

    @Override
    public <TResult> EasyPageResult<TResult> toPageResult(Class<TResult> tResultClass, long pageIndex, long pageSize, long pageTotal) {

        EasyPageResult<?> pageResult = queryable.toPageResult(queryable.queryClass(), pageIndex, pageIndex, pageTotal);
        EasyPageResultProvider easyPageResultProvider = runtimeContext.getEasyPageResultProvider();
        List<TResult> data = pageResult.getData().stream().map(o -> {
            return this.<TResult>getNavigates(o);
        }).collect(Collectors.toList());
        return easyPageResultProvider.createPageResult(pageIndex, pageSize, pageResult.getTotal(), data);
    }

    @Override
    public <TResult> EasyPageResult<TResult> toShardingPageResult(Class<TResult> tResultClass, long pageIndex, long pageSize, List<Long> totalLines) {
        throw new UnsupportedOperationException();
    }

    @Override
    public <TR> TR singleOrNull(Class<TR> resultClass) {
        Object entity = queryable.singleOrNull(queryable.queryClass());
        return getNavigates(entity);
    }

    @Override
    public <TR> TR singleNotNull(Class<TR> resultClass, Supplier<RuntimeException> throwFunc) {
        Object entity = queryable.singleNotNull(queryable.queryClass(),throwFunc);
        return getNavigates(entity);
    }

    @Override
    public <TR> JdbcStreamResult<TR> toStreamResult(Class<TR> resultClass, SQLConsumer<Statement> configurer) {
        throw new UnsupportedOperationException();
    }
}
