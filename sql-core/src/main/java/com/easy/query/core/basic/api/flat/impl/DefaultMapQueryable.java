package com.easy.query.core.basic.api.flat.impl;

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
import com.easy.query.core.basic.api.select.ResultSetContext;
import com.easy.query.core.basic.jdbc.executor.internal.enumerable.JdbcStreamResult;
import com.easy.query.core.basic.jdbc.parameter.ToSQLContext;
import com.easy.query.core.common.Chunk;
import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.enums.MultiTableTypeEnum;
import com.easy.query.core.enums.sharding.ConnectionModeEnum;
import com.easy.query.core.exception.EasyQueryInvalidOperationException;
import com.easy.query.core.expression.builder.core.ValueFilter;
import com.easy.query.core.expression.lambda.SQLConsumer;
import com.easy.query.core.expression.lambda.SQLActionExpression1;
import com.easy.query.core.expression.lambda.SQLFuncExpression1;
import com.easy.query.core.expression.lambda.SQLResultSetFunc;
import com.easy.query.core.expression.sql.builder.EntityQueryExpressionBuilder;
import com.easy.query.core.expression.sql.builder.EntityTableExpressionBuilder;
import com.easy.query.core.expression.sql.builder.ExpressionContext;
import com.easy.query.core.metadata.EntityMetadata;
import com.easy.query.core.util.EasyClassUtil;
import com.easy.query.core.util.EasyCollectionUtil;
import com.easy.query.core.util.EasySQLExpressionUtil;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import java.sql.Statement;
import java.util.function.Predicate;
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
    private final ExpressionContext expressionContext;

    public DefaultMapQueryable(ClientQueryable<Map<String, Object>> queryable) {

        this.queryable = queryable;
        this.queryClass = queryable.queryClass();
        this.expressionContext = queryable.getSQLEntityExpressionBuilder().getExpressionContext();
        this.runtimeContext = queryable.getSQLEntityExpressionBuilder().getRuntimeContext();
    }

    @Override
    public MapQueryable join(MultiTableTypeEnum joinTable, SQLActionExpression1<MapFilter> on) {
        EntityMetadata entityMetadata = runtimeContext.getEntityMetadataManager().getEntityMetadata(Map.class);
        EntityTableExpressionBuilder sqlTable = runtimeContext.getExpressionBuilderFactory().createEntityTableExpressionBuilder(entityMetadata, MultiTableTypeEnum.LEFT_JOIN, expressionContext);
        queryable.getSQLEntityExpressionBuilder().addSQLEntityTableExpression(sqlTable);
        on.apply(new MapOnFilterImpl(this.queryable));
        return this;
    }

    @Override
    public MapQueryable join(MultiTableTypeEnum joinTable, MapQueryable mapQueryable, SQLActionExpression1<MapFilter> on) {

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

    @NotNull
    @Override
    public MapQueryable cloneQueryable() {
        return new DefaultMapQueryable(queryable.cloneQueryable());
    }

    @NotNull
    @Override
    public MapQueryable distinct() {
        this.queryable.distinct();
        return this;
    }

    @NotNull
    @Override
    public MapQueryable distinct(boolean condition) {
        this.queryable.distinct(condition);
        return this;
    }

    @NotNull
    @Override
    public MapQueryable limit(long offset, long rows) {
        this.queryable.limit(offset, rows);
        return this;
    }

    @NotNull
    @Override
    public MapQueryable limit(boolean condition, long offset, long rows) {
        this.queryable.limit(condition, offset, rows);
        return this;
    }

    @NotNull
    @Override
    public MapQueryable asTracking() {
        this.queryable.asTracking();
        return this;
    }

    @NotNull
    @Override
    public MapQueryable asNoTracking() {
        this.queryable.asNoTracking();
        return this;
    }

    @NotNull
    @Override
    public MapQueryable useShardingConfigure(int maxShardingQueryLimit, ConnectionModeEnum connectionMode) {
        this.queryable.useShardingConfigure(maxShardingQueryLimit, connectionMode);
        return this;
    }

    @NotNull
    @Override
    public MapQueryable useMaxShardingQueryLimit(int maxShardingQueryLimit) {
        this.queryable.useMaxShardingQueryLimit(maxShardingQueryLimit);
        return this;
    }

    @NotNull
    @Override
    public MapQueryable useConnectionMode(ConnectionModeEnum connectionMode) {
        this.queryable.useConnectionMode(connectionMode);
        return this;
    }

    @NotNull
    @Override
    public EntityQueryExpressionBuilder getSQLEntityExpressionBuilder() {
        return queryable.getSQLEntityExpressionBuilder();
    }

    @NotNull
    @Override
    public Query<Map<String, Object>> select(@NotNull String columns) {
        queryable.select(columns);
        return this;
    }

    @NotNull
    @Override
    public <TR> String toSQL(@NotNull Class<TR> resultClass, @NotNull ToSQLContext toSQLContext) {
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


    @NotNull
    @Override
    public Class<Map<String, Object>> queryClass() {
        return queryClass;
    }

    @NotNull
    @Override
    public EntityMetadata queryEntityMetadata() {
        return queryable.queryEntityMetadata();
    }

    @Override
    public Map<String, Object> findOrNull(@NotNull Object id) {
        return this.queryable.findOrNull(id);
    }

    @NotNull
    @Override
    public Map<String, Object> findNotNull(@NotNull Object id, @NotNull Supplier<RuntimeException> throwFunc) {
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

    @NotNull
    @Override
    public <TR> List<TR> toList(@NotNull Class<TR> resultClass) {
        return queryable.toList(resultClass);
    }

    @NotNull
    @Override
    public <TR> List<TR> toList(@NotNull Class<TR> resultClass, @NotNull EntityMetadata resultEntityMetadata) {
        return queryable.toList(resultClass, resultEntityMetadata);
    }

    @NotNull
    @Override
    public Map<String, Object> toMap() {
        return this.queryable.toMap();
    }

    @NotNull
    @Override
    public List<Map<String, Object>> toMaps() {
        return this.queryable.toMaps();
    }

    @NotNull
    @Override
    public <TResult> EasyPageResult<TResult> toPageResult(@NotNull Class<TResult> tResultClass, long pageIndex, long pageSize, long pageTotal) {
        return this.queryable.toPageResult(tResultClass, pageIndex, pageSize, pageTotal);
    }

    @NotNull
    @Override
    public <TResult> EasyPageResult<TResult> toShardingPageResult(Class<TResult> tResultClass, long pageIndex, long pageSize, List<Long> totalLines) {
        return this.queryable.toShardingPageResult(tResultClass, pageIndex, pageSize, totalLines);
    }

    @Override
    public <TR> TR singleOrNull(@NotNull Class<TR> resultClass) {
        return this.queryable.singleOrNull(resultClass);
    }

    @NotNull
    @Override
    public <TR> TR singleNotNull(@NotNull Class<TR> resultClass, @NotNull Supplier<RuntimeException> throwFunc) {
        return this.queryable.singleNotNull(resultClass, throwFunc);
    }

    @NotNull
    @Override
    public <TR> JdbcStreamResult<TR> toStreamResult(@NotNull Class<TR> resultClass, @NotNull SQLConsumer<Statement> configurer) {
        return this.queryable.toStreamResult(resultClass, configurer);
    }

    @Override
    public MapQueryable where(SQLActionExpression1<MapFilter> whereExpression) {
        whereExpression.apply(new MapFilterImpl(this.queryable));
        return this;
    }

    @Override
    public MapQueryable select(SQLActionExpression1<MapSelector> selectExpression) {
        selectExpression.apply(new MapSelectorImpl(this.queryable));
        ClientQueryable<Map<String, Object>> anonymousQueryable = runtimeContext.getSQLClientApiFactory().createQueryable(queryClass, this.queryable.getSQLEntityExpressionBuilder());
        return new DefaultMapQueryable(anonymousQueryable);
    }

    @Override
    public ClientQueryable<Map<String, Object>> getClientQueryable() {
        return queryable;
    }

    @Override
    public MapQueryable groupBy(SQLActionExpression1<MapGroupSelector> selectExpression) {
        selectExpression.apply(new MapGroupSelectorImpl(this.queryable));
        return this;
    }

    @Override
    public MapQueryable having(SQLActionExpression1<MapHaving> havingExpression) {
        havingExpression.apply(new MapHavingImpl(this.queryable));
        return this;
    }

    @Override
    public MapQueryable orderBy(SQLActionExpression1<MapOrderBy> orderByExpression, boolean asc) {
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

    @NotNull
    @Override
    public <TR> TR streamBy(@NotNull Function<Stream<Map<String, Object>>, TR> fetcher, @NotNull SQLConsumer<Statement> configurer) {
        return this.queryable.streamBy(fetcher, configurer);
    }

    @Override
    public void toChunkIf(int size, Predicate<List<Map<String, Object>>> chunk) {
        int offset = 0;
        while (true) {
            MapQueryable cloneQueryable = this.cloneQueryable();
            List<Map<String, Object>> list = cloneQueryable.limit(offset, size).toList();
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
    public void offsetChunk(int size, SQLFuncExpression1<Chunk<List<Map<String, Object>>>, Chunk.Offset> chunk) {

        int offset = 0;
        long fetchSize = 0L;
        long nextSize = size;
        while (true) {

            MapQueryable cloneQueryable = this.cloneQueryable();
            List<Map<String, Object>> list = cloneQueryable.limit(offset, nextSize).toList();
            if (EasyCollectionUtil.isEmpty(list)) {
                break;
            }
            boolean hasNext = list.size() == nextSize;
            fetchSize += nextSize;
            Chunk<List<Map<String, Object>>> chunkItem = new Chunk<>(list, fetchSize);
            Chunk.Offset offsetItem = chunk.apply(chunkItem);
            if (chunkItem.isBreakChunk()) {
                break;
            }

            offset += offsetItem.offset;
            if (!hasNext) {
                break;
            }
            if (fetchSize >= chunkItem.getMaxFetchSize()) {
                break;
            }

            nextSize = Math.min(chunkItem.getMaxFetchSize() - fetchSize, nextSize);
        }
    }

    @NotNull
    @Override
    public List<Map<String, Object>> toTreeList(boolean ignore) {
        if (!ignore) {
            throw new EasyQueryInvalidOperationException("Unable to find a Navigate property where children is a reference to itself:[" + EasyClassUtil.getSimpleName(this.queryClass()) + "].");
        }
        return toList();
    }

    @Override
    public <T> T toResultSet(SQLResultSetFunc<ResultSetContext, T> produce) {
        return this.queryable.toResultSet(produce);
    }
}
