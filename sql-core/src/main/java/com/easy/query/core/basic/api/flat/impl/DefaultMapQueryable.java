package com.easy.query.core.basic.api.flat.impl;

import com.easy.query.core.annotation.NotNull;
import com.easy.query.core.api.pagination.EasyPageResult;
import com.easy.query.core.basic.api.flat.MapQueryable;
import com.easy.query.core.basic.api.flat.provider.MapFilter;
import com.easy.query.core.basic.api.flat.provider.MapGroupSelector;
import com.easy.query.core.basic.api.flat.provider.MapHaving;
import com.easy.query.core.basic.api.flat.provider.MapOrderBy;
import com.easy.query.core.basic.api.flat.provider.MapSelector;
import com.easy.query.core.basic.api.flat.provider.impl.MapFilterImpl;
import com.easy.query.core.basic.api.flat.provider.impl.MapGroupSelectorImpl;
import com.easy.query.core.basic.api.flat.provider.impl.MapHavingImpl;
import com.easy.query.core.basic.api.flat.provider.impl.MapOnFilterImpl;
import com.easy.query.core.basic.api.flat.provider.impl.MapOrderByImpl;
import com.easy.query.core.basic.api.flat.provider.impl.MapSelectorImpl;
import com.easy.query.core.basic.api.select.ClientQueryable;
import com.easy.query.core.basic.api.select.Query;
import com.easy.query.core.basic.jdbc.executor.internal.enumerable.JdbcStreamResult;
import com.easy.query.core.basic.jdbc.parameter.ToSQLContext;
import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.enums.MultiTableTypeEnum;
import com.easy.query.core.enums.sharding.ConnectionModeEnum;
import com.easy.query.core.exception.EasyQueryInvalidOperationException;
import com.easy.query.core.expression.builder.core.ValueFilter;
import com.easy.query.core.expression.lambda.SQLConsumer;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.lambda.SQLFuncExpression2;
import com.easy.query.core.expression.sql.builder.EntityQueryExpressionBuilder;
import com.easy.query.core.expression.sql.builder.EntityTableExpressionBuilder;
import com.easy.query.core.metadata.EntityMetadata;
import com.easy.query.core.util.EasyClassUtil;
import com.easy.query.core.util.EasyCollectionUtil;
import com.easy.query.core.util.EasySQLExpressionUtil;

import java.sql.Statement;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * create time 2024/3/26 16:01
 * 文件说明
 *
 * @author xuejiaming
 */
public class DefaultMapQueryable implements MapQueryable {
    private final ClientQueryable<Map<String, Object>> queryable;
    private final Class<Map<String, Object>> queryClass;
    private final QueryRuntimeContext runtimeContext;

    public DefaultMapQueryable(ClientQueryable<Map<String, Object>> queryable) {

        this.queryable = queryable;
        this.queryClass = queryable.queryClass();
        this.runtimeContext = queryable.getSQLEntityExpressionBuilder().getRuntimeContext();
    }

    @Override
    public MapQueryable join(MultiTableTypeEnum joinTable, SQLExpression1<MapFilter> on) {
        EntityMetadata entityMetadata = runtimeContext.getEntityMetadataManager().getEntityMetadata(Map.class);
        EntityTableExpressionBuilder sqlTable = runtimeContext.getExpressionBuilderFactory().createEntityTableExpressionBuilder(entityMetadata, MultiTableTypeEnum.LEFT_JOIN, runtimeContext);
        queryable.getSQLEntityExpressionBuilder().addSQLEntityTableExpression(sqlTable);
        on.apply(new MapOnFilterImpl(this.queryable));
        return this;
    }

    @Override
    public MapQueryable join(MultiTableTypeEnum joinTable, MapQueryable mapQueryable, SQLExpression1<MapFilter> on) {

        ClientQueryable<Map<String, Object>> selectAllTQueryable = EasySQLExpressionUtil.cloneAndSelectAllQueryable(mapQueryable.getClientQueryable());
        queryable.getSQLEntityExpressionBuilder().getExpressionContext().extract(selectAllTQueryable.getSQLEntityExpressionBuilder().getExpressionContext());


        Class<Map<String, Object>> t2Class = mapQueryable.queryClass();

        EntityMetadata entityMetadata = runtimeContext.getEntityMetadataManager().getEntityMetadata(t2Class);
        EntityQueryExpressionBuilder joinQueryableSQLEntityExpressionBuilder = mapQueryable.getClientQueryable().getSQLEntityExpressionBuilder();

        EntityTableExpressionBuilder sqlTable = runtimeContext.getExpressionBuilderFactory().createAnonymousEntityTableExpressionBuilder(entityMetadata, joinTable, joinQueryableSQLEntityExpressionBuilder);
        queryable.getSQLEntityExpressionBuilder().addSQLEntityTableExpression(sqlTable);

        on.apply(new MapOnFilterImpl(this.queryable));
        return this;
    }

    @Override
    public MapQueryable asTable(Function<String, String> tableNameAs) {
        queryable.asTable(tableNameAs);
        return this;
    }

    @Override
    public MapQueryable asSchema(Function<String, String> schemaAs) {
        queryable.asSchema(schemaAs);
        return this;
    }

    @Override
    public MapQueryable asAlias(String alias) {
        queryable.asAlias(alias);
        return this;
    }

    @Override
    public MapQueryable asTableLink(Function<String, String> linkAs) {
        queryable.asTableLink(linkAs);
        return this;
    }
    @Override
    public MapQueryable asTableSegment(BiFunction<String, String, String> segmentAs) {
        queryable.asTableSegment(segmentAs);
        return this;
    }

    @Override
    public MapQueryable cloneQueryable() {
        return new DefaultMapQueryable(queryable.cloneQueryable());
    }

    @Override
    public MapQueryable distinct() {
        this.queryable.distinct();
        return this;
    }

    @Override
    public MapQueryable distinct(boolean condition) {
        this.queryable.distinct(condition);
        return this;
    }

    @Override
    public MapQueryable limit(long offset, long rows) {
        this.queryable.limit(offset, rows);
        return this;
    }

    @Override
    public MapQueryable limit(boolean condition, long offset, long rows) {
        this.queryable.limit(condition, offset, rows);
        return this;
    }

    @Override
    public MapQueryable asTracking() {
        this.queryable.asTracking();
        return this;
    }

    @Override
    public MapQueryable asNoTracking() {
        this.queryable.asNoTracking();
        return this;
    }

    @Override
    public MapQueryable useShardingConfigure(int maxShardingQueryLimit, ConnectionModeEnum connectionMode) {
        this.queryable.useShardingConfigure(maxShardingQueryLimit, connectionMode);
        return this;
    }

    @Override
    public MapQueryable useMaxShardingQueryLimit(int maxShardingQueryLimit) {
        this.queryable.useMaxShardingQueryLimit(maxShardingQueryLimit);
        return this;
    }

    @Override
    public MapQueryable useConnectionMode(ConnectionModeEnum connectionMode) {
        this.queryable.useConnectionMode(connectionMode);
        return this;
    }

    @Override
    public EntityQueryExpressionBuilder getSQLEntityExpressionBuilder() {
        return queryable.getSQLEntityExpressionBuilder();
    }

    @Override
    public Query<Map<String, Object>> select(String columns) {
        queryable.select(columns);
        return this;
    }

    @Override
    public <TR> String toSQL(Class<TR> resultClass, ToSQLContext toSQLContext) {
        return this.queryable.toSQL(resultClass, toSQLContext);
    }

    @Override
    public long count() {
        return this.queryable.count();
    }

    @Override
    public boolean any() {
        return this.queryable.any();
    }


    @Override
    public Class<Map<String, Object>> queryClass() {
        return queryClass;
    }

    @Override
    public EntityMetadata queryEntityMetadata() {
        return queryable.queryEntityMetadata();
    }

    @Override
    public Map<String, Object> findOrNull(Object id) {
        return this.queryable.findOrNull(id);
    }

    @Override
    public Map<String, Object> findNotNull(Object id, Supplier<RuntimeException> throwFunc) {
        return this.queryable.findNotNull(id, throwFunc);
    }

    @Override
    public <TR> TR firstOrNull(Class<TR> resultClass) {
        return this.queryable.firstOrNull(resultClass);
    }

    @NotNull
    @Override
    public <TR> TR firstNotNull(Class<TR> resultClass, Supplier<RuntimeException> throwFunc) {
        return this.queryable.firstNotNull(resultClass, throwFunc);
    }

    @Override
    public <TR> List<TR> toList(Class<TR> resultClass) {
        return queryable.toList(resultClass);
    }

    @Override
    public <TR> List<TR> toList(Class<TR> resultClass, EntityMetadata resultEntityMetadata) {
        return queryable.toList(resultClass, resultEntityMetadata);
    }

    @Override
    public Map<String, Object> toMap() {
        return this.queryable.toMap();
    }

    @Override
    public List<Map<String, Object>> toMaps() {
        return this.queryable.toMaps();
    }

    @Override
    public <TResult> EasyPageResult<TResult> toPageResult(Class<TResult> tResultClass, long pageIndex, long pageSize, long pageTotal) {
        return this.queryable.toPageResult(tResultClass, pageIndex, pageSize, pageTotal);
    }

    @Override
    public <TResult> EasyPageResult<TResult> toShardingPageResult(Class<TResult> tResultClass, long pageIndex, long pageSize, List<Long> totalLines) {
        return this.queryable.toShardingPageResult(tResultClass, pageIndex, pageSize, totalLines);
    }

    @Override
    public <TR> TR singleOrNull(Class<TR> resultClass) {
        return this.queryable.singleOrNull(resultClass);
    }

    @NotNull
    @Override
    public <TR> TR singleNotNull(Class<TR> resultClass, Supplier<RuntimeException> throwFunc) {
        return this.queryable.singleNotNull(resultClass, throwFunc);
    }

    @Override
    public <TR> JdbcStreamResult<TR> toStreamResult(Class<TR> resultClass, SQLConsumer<Statement> configurer) {
        return this.queryable.toStreamResult(resultClass, configurer);
    }

    @Override
    public MapQueryable where(SQLExpression1<MapFilter> whereExpression) {
        whereExpression.apply(new MapFilterImpl(this.queryable));
        return this;
    }

    @Override
    public MapQueryable select(SQLExpression1<MapSelector> selectExpression) {
        selectExpression.apply(new MapSelectorImpl(this.queryable));
        ClientQueryable<Map<String, Object>> anonymousQueryable = runtimeContext.getSQLClientApiFactory().createQueryable(queryClass, this.queryable.getSQLEntityExpressionBuilder());
        return new DefaultMapQueryable(anonymousQueryable);
    }

    @Override
    public ClientQueryable<Map<String, Object>> getClientQueryable() {
        return queryable;
    }

    @Override
    public MapQueryable groupBy(SQLExpression1<MapGroupSelector> selectExpression) {
        selectExpression.apply(new MapGroupSelectorImpl(this.queryable));
        return this;
    }

    @Override
    public MapQueryable having(SQLExpression1<MapHaving> havingExpression) {
        havingExpression.apply(new MapHavingImpl(this.queryable));
        return this;
    }

    @Override
    public MapQueryable orderBy(SQLExpression1<MapOrderBy> orderByExpression, boolean asc) {
        orderByExpression.apply(new MapOrderByImpl(this.queryable, asc));
        return this;
    }

    @Override
    public MapQueryable filterConfigure(ValueFilter valueFilter) {
        this.queryable.filterConfigure(valueFilter);
        return this;
    }

    @Override
    public MapQueryable noInterceptor() {
        this.queryable.noInterceptor();
        return this;
    }

    @Override
    public MapQueryable useInterceptor(String name) {
        this.queryable.useInterceptor(name);
        return this;
    }

    @Override
    public MapQueryable noInterceptor(String name) {
        this.queryable.noInterceptor(name);
        return this;
    }

    @Override
    public MapQueryable useInterceptor() {
        this.queryable.useInterceptor();
        return this;
    }

    @Override
    public MapQueryable useLogicDelete(boolean enable) {
        this.queryable.useLogicDelete(enable);
        return this;
    }

    @Override
    public MapQueryable queryLargeColumn(boolean queryLarge) {
        this.queryable.queryLargeColumn(queryLarge);
        return this;
    }

    @Override
    public MapQueryable tableLogicDelete(Supplier<Boolean> tableLogicDel) {
        this.queryable.tableLogicDelete(tableLogicDel);
        return this;
    }

    @Override
    public MapQueryable union(Collection<MapQueryable> mapQueryables) {
        ClientQueryable<Map<String, Object>> unionQueryable = this.queryable.union(mapQueryables.stream().map(o -> o.getClientQueryable()).collect(Collectors.toList()));
        return new DefaultMapQueryable(unionQueryable);
    }

    @Override
    public MapQueryable unionAll(Collection<MapQueryable> mapQueryables) {
        ClientQueryable<Map<String, Object>> unionQueryable = this.queryable.unionAll(mapQueryables.stream().map(o -> o.getClientQueryable()).collect(Collectors.toList()));
        return new DefaultMapQueryable(unionQueryable);
    }

    @Override
    public <TR> TR streamBy(Function<Stream<Map<String, Object>>, TR> fetcher, SQLConsumer<Statement> configurer) {
        return this.queryable.streamBy(fetcher, configurer);
    }

    @Override
    public void toChunk(int size, Predicate<List<Map<String, Object>>> chunk) {
        int offset = 0;
        while (true) {
            MapQueryable cloneQueryable = this.cloneQueryable();
            List<Map<String, Object>> list = cloneQueryable.limit(offset,size).toList();
            offset += size;
            if (EasyCollectionUtil.isEmpty(list)) {
                break;
            }
            boolean hasNext = list.size() == size;
            if (!chunk.test(list) || !hasNext) {
                break;
            }
        }
    }

    @Override
    public List<Map<String, Object>> toTreeList(boolean ignore) {
        if(!ignore){
            throw new EasyQueryInvalidOperationException("Unable to find a Navigate property where children is a reference to itself:[" + EasyClassUtil.getSimpleName(this.queryClass()) + "].");
        }
        return toList();
    }
}
