package com.easy.query.core.proxy;

import com.easy.query.api.proxy.client.BaseEntityClient;
import com.easy.query.api.proxy.entity.delete.EntityDeletable;
import com.easy.query.api.proxy.entity.delete.ExpressionDeletable;
import com.easy.query.api.proxy.entity.insert.EntityInsertable;
import com.easy.query.api.proxy.entity.save.EntitySavable;
import com.easy.query.api.proxy.entity.select.EntityQueryable;
import com.easy.query.api.proxy.entity.select.EntityQueryable2;
import com.easy.query.api.proxy.entity.update.EntityUpdatable;
import com.easy.query.api.proxy.entity.update.ExpressionUpdatable;
import com.easy.query.api.proxy.extension.tree.EntityTreeCTEConfigurer;
import com.easy.query.core.api.dynamic.sort.ObjectSort;
import com.easy.query.core.api.pagination.EasyPageResult;
import com.easy.query.core.basic.api.select.ClientQueryable;
import com.easy.query.core.basic.api.select.Query;
import com.easy.query.core.basic.api.select.executor.MethodQuery;
import com.easy.query.core.basic.jdbc.executor.internal.enumerable.JdbcStreamResult;
import com.easy.query.core.basic.jdbc.parameter.ToSQLContext;
import com.easy.query.core.common.Chunk;
import com.easy.query.core.enums.sharding.ConnectionModeEnum;
import com.easy.query.core.expression.builder.core.ValueFilter;
import com.easy.query.core.expression.lambda.SQLActionExpression1;
import com.easy.query.core.expression.lambda.SQLActionExpression2;
import com.easy.query.core.expression.lambda.SQLConsumer;
import com.easy.query.core.expression.lambda.SQLFuncExpression1;
import com.easy.query.core.expression.parser.core.base.tree.TreeCTEConfigurer;
import com.easy.query.core.expression.segment.ColumnSegment;
import com.easy.query.core.expression.sql.builder.EntityQueryExpressionBuilder;
import com.easy.query.core.expression.sql.builder.internal.ContextConfigurer;
import com.easy.query.core.metadata.EntityMetadata;
import com.easy.query.core.proxy.columns.SQLQueryable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.sql.Statement;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Stream;

/**
 * create time 2025/9/28 21:03
 * 文件说明
 *
 * @author xuejiaming
 */
public class EasyDbSet<TProxy extends ProxyEntity<TProxy, T>, T> implements DbSet<TProxy, T> {
    private final TProxy tProxy;
    private final BaseEntityClient baseEntityClient;

    public EasyDbSet(TProxy tProxy, BaseEntityClient baseEntityClient) {
        this.tProxy = tProxy;
        this.baseEntityClient = baseEntityClient;
    }

    @Override
    public EntityInsertable<TProxy, T> insertable(T entity) {
        return baseEntityClient.insertable(tProxy).insert(entity);
    }

    @Override
    public EntityInsertable<TProxy, T> insertable(Collection<T> entities) {
        return baseEntityClient.insertable(tProxy).insert(entities);
    }

    @Override
    public ExpressionUpdatable<TProxy, T> setColumns(SQLActionExpression1<TProxy> columnSetExpression) {
        return baseEntityClient.expressionUpdatable(tProxy).setColumns(columnSetExpression);
    }

    @Override
    public EntityUpdatable<TProxy, T> updatable(T entity) {
        EntityUpdatable<TProxy, T> updatable = baseEntityClient.entityUpdatable(tProxy);
        updatable.getEntities().add(entity);
        return updatable;
    }

    @Override
    public EntityUpdatable<TProxy, T> updatable(Collection<T> entities) {
        EntityUpdatable<TProxy, T> updatable = baseEntityClient.entityUpdatable(tProxy);
        updatable.getEntities().addAll(entities);
        return updatable;
    }

    @Override
    public EntityDeletable<TProxy, T> deletable(T entity) {
        EntityDeletable<TProxy, T> deletable = baseEntityClient.entityDeletable(tProxy);
        deletable.getEntities().add(entity);
        return deletable;
    }

    @Override
    public EntityDeletable<TProxy, T> deletable(Collection<T> entities) {
        EntityDeletable<TProxy, T> deletable = baseEntityClient.entityDeletable(tProxy);
        deletable.getEntities().addAll(entities);
        return deletable;
    }

    @Override
    public ExpressionDeletable<TProxy, T> deleteBy(SQLActionExpression1<TProxy> whereExpression) {
        return baseEntityClient.expressionDeletable(tProxy).where(whereExpression);
    }

    @Override
    public EntitySavable<TProxy, T> savable(T entity) {
        EntitySavable<TProxy, T> savable = baseEntityClient.savable(tProxy);
        savable.getEntities().add(entity);
        return savable;
    }

    @Override
    public EntitySavable<TProxy, T> savable(Collection<T> entities) {
        EntitySavable<TProxy, T> savable = baseEntityClient.savable(tProxy);
        savable.getEntities().addAll(entities);
        return savable;
    }

    @Override
    public ClientQueryable<T> getClientQueryable() {
        return baseEntityClient.queryable(tProxy).getClientQueryable();
    }

    @NotNull
    @Override
    public EntityQueryable<TProxy, T> cloneQueryable() {
        return baseEntityClient.queryable(tProxy).cloneQueryable();
    }

    @NotNull
    @Override
    public EntityQueryable<TProxy, T> toCteAs(String tableName) {
        return baseEntityClient.queryable(tProxy).toCteAs(tableName);
    }

    @NotNull
    @Override
    public EntityQueryExpressionBuilder getSQLEntityExpressionBuilder() {
        return baseEntityClient.queryable(tProxy).getSQLEntityExpressionBuilder();
    }

    @NotNull
    @Override
    public EntityQueryable<TProxy, T> select(@NotNull String columns) {
        return baseEntityClient.queryable(tProxy).select(columns);
    }

    @NotNull
    @Override
    public <TR> String toSQL(@NotNull Class<TR> resultClass, @NotNull ToSQLContext toSQLContext) {
        return baseEntityClient.queryable(tProxy).toSQL(resultClass, toSQLContext);
    }

    @Override
    public long count() {
        return baseEntityClient.queryable(tProxy).count();
    }

    @Override
    public boolean any() {
        return baseEntityClient.queryable(tProxy).any();
    }

    @NotNull
    @Override
    public EntityQueryable<TProxy, T> distinct(boolean condition) {
        return baseEntityClient.queryable(tProxy).distinct(condition);
    }

    @NotNull
    @Override
    public EntityQueryable<TProxy, T> limit(boolean condition, long offset, long rows) {
        return baseEntityClient.queryable(tProxy).limit(condition, offset, rows);
    }

    @Override
    public TProxy get1Proxy() {
        return tProxy;
    }

    @Override
    public <TRProxy extends ProxyEntity<TRProxy, TR>, TR> List<TR> toList(SQLFuncExpression1<TProxy, TRProxy> fetchResultExpression) {
        return baseEntityClient.queryable(tProxy).toList(fetchResultExpression);
    }

    @Override
    public <TNumber extends Number> Query<TNumber> selectCount(Class<TNumber> tNumberClass) {
        return baseEntityClient.queryable(tProxy).selectCount(tNumberClass);
    }

    @Override
    public <TMember> Query<TMember> selectCount(SQLFuncExpression1<TProxy, PropTypeColumn<TMember>> selectExpression, boolean distinct) {
        return baseEntityClient.queryable(tProxy).selectCount(selectExpression, distinct);
    }

    @Override
    public EntityQueryable<TProxy, T> where(boolean condition, SQLActionExpression1<TProxy> whereExpression) {
        return baseEntityClient.queryable(tProxy).where(condition, whereExpression);
    }

    @Override
    public EntityQueryable<TProxy, T> whereById(boolean condition, Object id) {
        return baseEntityClient.queryable(tProxy).whereById(condition, id);
    }

    @Override
    public <TProperty> EntityQueryable<TProxy, T> whereByIds(boolean condition, Collection<TProperty> ids) {
        return baseEntityClient.queryable(tProxy).whereByIds(condition, ids);
    }

    @Override
    public EntityQueryable<TProxy, T> whereObject(boolean condition, Object object) {
        return baseEntityClient.queryable(tProxy).whereObject(condition, object);
    }

    @Override
    public EntityQueryable<TProxy, T> having(boolean condition, SQLActionExpression1<TProxy> aggregateFilterSQLExpression) {
        return baseEntityClient.queryable(tProxy).having(condition, aggregateFilterSQLExpression);
    }

    @Override
    public <TPropertyProxy extends ProxyEntity<TPropertyProxy, TProperty>, TProperty> EntityQueryable<TProxy, T> include(boolean condition, SQLFuncExpression1<TProxy, TPropertyProxy> navigateIncludeSQLExpression, SQLActionExpression1<EntityQueryable<TPropertyProxy, TProperty>> includeAdapterExpression, Integer groupSize) {
        return baseEntityClient.queryable(tProxy).include(condition, navigateIncludeSQLExpression, includeAdapterExpression, groupSize);
    }

    @Override
    public <TPropertyProxy extends ProxyEntity<TPropertyProxy, TProperty>, TProperty> EntityQueryable<TProxy, T> includes(boolean condition, SQLFuncExpression1<TProxy, SQLQueryable<TPropertyProxy, TProperty>> navigateIncludeSQLExpression, SQLActionExpression1<EntityQueryable<TPropertyProxy, TProperty>> includeAdapterExpression, Integer groupSize) {
        return baseEntityClient.queryable(tProxy).includes(condition, navigateIncludeSQLExpression, includeAdapterExpression, groupSize);
    }

    @Override
    public <T2Proxy extends ProxyEntity<T2Proxy, T2>, T2 extends ProxyEntityAvailable<T2, T2Proxy>> EntityQueryable2<TProxy, T, T2Proxy, T2> leftJoin(Class<T2> joinClass, SQLActionExpression2<TProxy, T2Proxy> onExpression) {
        return baseEntityClient.queryable(tProxy).leftJoin(joinClass, onExpression);
    }

    @Override
    public <T2Proxy extends ProxyEntity<T2Proxy, T2>, T2> EntityQueryable2<TProxy, T, T2Proxy, T2> leftJoin(T2Proxy t2Proxy, SQLActionExpression2<TProxy, T2Proxy> onExpression) {
        return baseEntityClient.queryable(tProxy).leftJoin(t2Proxy, onExpression);
    }

    @Override
    public <T2Proxy extends ProxyEntity<T2Proxy, T2>, T2> EntityQueryable2<TProxy, T, T2Proxy, T2> leftJoin(EntityQueryable<T2Proxy, T2> joinQueryable, SQLActionExpression2<TProxy, T2Proxy> onExpression) {
        return baseEntityClient.queryable(tProxy).leftJoin(joinQueryable, onExpression);
    }

    @Override
    public <T2Proxy extends ProxyEntity<T2Proxy, T2>, T2 extends ProxyEntityAvailable<T2, T2Proxy>> EntityQueryable2<TProxy, T, T2Proxy, T2> rightJoin(Class<T2> joinClass, SQLActionExpression2<TProxy, T2Proxy> onExpression) {
        return baseEntityClient.queryable(tProxy).rightJoin(joinClass, onExpression);
    }

    @Override
    public <T2Proxy extends ProxyEntity<T2Proxy, T2>, T2> EntityQueryable2<TProxy, T, T2Proxy, T2> rightJoin(T2Proxy t2Proxy, SQLActionExpression2<TProxy, T2Proxy> onExpression) {
        return baseEntityClient.queryable(tProxy).rightJoin(t2Proxy, onExpression);
    }

    @Override
    public <T2Proxy extends ProxyEntity<T2Proxy, T2>, T2> EntityQueryable2<TProxy, T, T2Proxy, T2> rightJoin(EntityQueryable<T2Proxy, T2> joinQueryable, SQLActionExpression2<TProxy, T2Proxy> onExpression) {
        return baseEntityClient.queryable(tProxy).rightJoin(joinQueryable, onExpression);
    }

    @Override
    public <T2Proxy extends ProxyEntity<T2Proxy, T2>, T2 extends ProxyEntityAvailable<T2, T2Proxy>> EntityQueryable2<TProxy, T, T2Proxy, T2> innerJoin(Class<T2> joinClass, SQLActionExpression2<TProxy, T2Proxy> onExpression) {
        return baseEntityClient.queryable(tProxy).innerJoin(joinClass, onExpression);
    }

    @Override
    public <T2Proxy extends ProxyEntity<T2Proxy, T2>, T2> EntityQueryable2<TProxy, T, T2Proxy, T2> innerJoin(T2Proxy t2Proxy, SQLActionExpression2<TProxy, T2Proxy> onExpression) {
        return baseEntityClient.queryable(tProxy).innerJoin(t2Proxy, onExpression);
    }

    @Override
    public <T2Proxy extends ProxyEntity<T2Proxy, T2>, T2> EntityQueryable2<TProxy, T, T2Proxy, T2> innerJoin(EntityQueryable<T2Proxy, T2> joinQueryable, SQLActionExpression2<TProxy, T2Proxy> onExpression) {
        return baseEntityClient.queryable(tProxy).innerJoin(joinQueryable, onExpression);
    }

    @Override
    public EntityQueryable<TProxy, T> orderBy(boolean condition, SQLActionExpression1<TProxy> selectExpression) {
        return baseEntityClient.queryable(tProxy).orderBy(condition, selectExpression);
    }

    @Override
    public EntityQueryable<TProxy, T> orderByObject(boolean condition, ObjectSort objectSort) {
        return baseEntityClient.queryable(tProxy).orderByObject(condition, objectSort);
    }

    @Override
    public EntityQueryable<TProxy, T> getQueryable() {
        return baseEntityClient.queryable(tProxy).getQueryable();
    }

    @Override
    public <TRProxy extends ProxyEntity<TRProxy, TR>, TR> EntityQueryable<TRProxy, TR> select(SQLFuncExpression1<TProxy, TRProxy> selectExpression) {
        return baseEntityClient.queryable(tProxy).select(selectExpression);
    }

    @Override
    public <TR> Query<TR> selectColumn(SQLFuncExpression1<TProxy, PropTypeColumn<TR>> selectExpression) {
        return baseEntityClient.queryable(tProxy).selectColumn(selectExpression);
    }

    @Override
    public <TR> Query<TR> select(Class<TR> resultClass) {
        return baseEntityClient.queryable(tProxy).select(resultClass);
    }

    @Override
    public <TR> Query<TR> select(Class<TR> resultClass, SQLFuncExpression1<TProxy, SQLSelectAsExpression> selectExpression) {
        return baseEntityClient.queryable(tProxy).select(resultClass, selectExpression);
    }

    @Override
    public <TR> Query<TR> selectAutoInclude(Class<TR> resultClass, SQLFuncExpression1<TProxy, SQLSelectAsExpression> extraSelectExpression, boolean replace) {
        return baseEntityClient.queryable(tProxy).selectAutoInclude(resultClass, extraSelectExpression, replace);
    }

    @Override
    public EntityQueryable<TProxy, T> select(Collection<ColumnSegment> columnSegments, boolean clearAll) {
        return baseEntityClient.queryable(tProxy).select(columnSegments, clearAll);
    }

    @Override
    public <T2Proxy extends ProxyEntity<T2Proxy, T2>, T2> EntityQueryable<TProxy, T> subQueryConfigure(boolean condition, SQLFuncExpression1<TProxy, ManyPropColumn<T2Proxy, T2>> manyPropColumnExpression, SQLFuncExpression1<EntityQueryable<T2Proxy, T2>, EntityQueryable<T2Proxy, T2>> adapterExpression) {
        return baseEntityClient.queryable(tProxy).subQueryConfigure(condition, manyPropColumnExpression, adapterExpression);
    }

    @Override
    public <T2Proxy extends ProxyEntity<T2Proxy, T2>, T2 extends ProxyEntityAvailable<T2, T2Proxy>> EntityQueryable<TProxy, T> subQueryToGroupJoin(boolean condition, SQLFuncExpression1<TProxy, ManyPropColumn<T2Proxy, T2>> manyPropColumnExpression) {
        return baseEntityClient.queryable(tProxy).subQueryToGroupJoin(condition, manyPropColumnExpression);
    }

    @Override
    public EntityQueryable<TProxy, T> thisConfigure(SQLFuncExpression1<EntityQueryable<TProxy, T>, EntityQueryable<TProxy, T>> thisConfigureExpression) {
        return baseEntityClient.queryable(tProxy).thisConfigure(thisConfigureExpression);
    }

    @Override
    public <TRProxy extends ProxyEntity<TRProxy, TR> & SQLGroupByExpression, TR> EntityQueryable<TRProxy, TR> groupBy(SQLFuncExpression1<TProxy, SQLFuncExpression1<TProxy, TRProxy>> selectExpression) {
        return baseEntityClient.queryable(tProxy).groupBy(selectExpression);
    }

    @Override
    public EntityQueryable<TProxy, T> asTreeCTE(SQLActionExpression1<EntityTreeCTEConfigurer<TProxy, T>> treeExpression) {
        return baseEntityClient.queryable(tProxy).asTreeCTE(treeExpression);
    }

    @Override
    public EntityQueryable<TProxy, T> asTreeCTECustom(SQLFuncExpression1<TProxy, SQLColumn<TProxy, ?>> codePropertyExpression, SQLFuncExpression1<TProxy, SQLColumn<TProxy, ?>> parentCodePropertyExpression, SQLActionExpression1<TreeCTEConfigurer> treeExpression) {
        return baseEntityClient.queryable(tProxy).asTreeCTECustom(codePropertyExpression,parentCodePropertyExpression,treeExpression);
    }

    @Override
    public EntityQueryable<TProxy, T> union(Collection<EntityQueryable<TProxy, T>> unionQueries) {
        return baseEntityClient.queryable(tProxy).union(unionQueries);
    }

    @Override
    public EntityQueryable<TProxy, T> unionAll(Collection<EntityQueryable<TProxy, T>> unionQueries) {
        return baseEntityClient.queryable(tProxy).unionAll(unionQueries);
    }

    @Override
    public EntityQueryable<TProxy, T> asTracking() {
        return baseEntityClient.queryable(tProxy).asTracking();
    }

    @Override
    public EntityQueryable<TProxy, T> asNoTracking() {
        return baseEntityClient.queryable(tProxy).asNoTracking();
    }

    @Override
    public EntityQueryable<TProxy, T> useShardingConfigure(int maxShardingQueryLimit, ConnectionModeEnum connectionMode) {
        return baseEntityClient.queryable(tProxy).useShardingConfigure(maxShardingQueryLimit,connectionMode);
    }

    @Override
    public EntityQueryable<TProxy, T> useMaxShardingQueryLimit(int maxShardingQueryLimit) {
        return baseEntityClient.queryable(tProxy).useMaxShardingQueryLimit(maxShardingQueryLimit);
    }

    @Override
    public EntityQueryable<TProxy, T> useConnectionMode(ConnectionModeEnum connectionMode) {
        return baseEntityClient.queryable(tProxy).useConnectionMode(connectionMode);
    }

    @Override
    public EntityQueryable<TProxy, T> filterConfigure(ValueFilter valueFilter) {
        return baseEntityClient.queryable(tProxy).filterConfigure(valueFilter);
    }

    @Override
    public EntityQueryable<TProxy, T> configure(SQLActionExpression1<ContextConfigurer> configurer) {
        return baseEntityClient.queryable(tProxy).configure(configurer);
    }

    @Override
    public EntityQueryable<TProxy, T> noInterceptor() {
        return baseEntityClient.queryable(tProxy).noInterceptor();
    }

    @Override
    public EntityQueryable<TProxy, T> useInterceptor(String name) {
        return baseEntityClient.queryable(tProxy).useInterceptor(name);
    }

    @Override
    public EntityQueryable<TProxy, T> noInterceptor(String name) {
        return baseEntityClient.queryable(tProxy).noInterceptor(name);
    }

    @Override
    public EntityQueryable<TProxy, T> useInterceptor() {
        return baseEntityClient.queryable(tProxy).useInterceptor();
    }

    @Override
    public EntityQueryable<TProxy, T> useLogicDelete(boolean enable) {
        return baseEntityClient.queryable(tProxy).useLogicDelete(enable);
    }

    @Override
    public EntityQueryable<TProxy, T> tableLogicDelete(Supplier<Boolean> tableLogicDel) {
        return baseEntityClient.queryable(tProxy).tableLogicDelete(tableLogicDel);
    }

    @Override
    public EntityQueryable<TProxy, T> asTable(Function<String, String> tableNameAs) {
        return baseEntityClient.queryable(tProxy).asTable(tableNameAs);
    }

    @Override
    public EntityQueryable<TProxy, T> asSchema(Function<String, String> schemaAs) {
        return baseEntityClient.queryable(tProxy).asSchema(schemaAs);
    }

    @Override
    public EntityQueryable<TProxy, T> asAlias(String alias) {
        return baseEntityClient.queryable(tProxy).asAlias(alias);
    }

    @Override
    public EntityQueryable<TProxy, T> asTableLink(Function<String, String> linkAs) {
        return baseEntityClient.queryable(tProxy).asTableLink(linkAs);
    }

    @Override
    public EntityQueryable<TProxy, T> asTableSegment(BiFunction<String, String, String> segmentAs) {
        return baseEntityClient.queryable(tProxy).asTableSegment(segmentAs);
    }

    @Override
    public void toChunkIf(int size, Predicate<List<T>> chunk) {
         baseEntityClient.queryable(tProxy).toChunkIf(size,chunk);
    }

    @Override
    public void offsetChunk(int size, SQLFuncExpression1<Chunk<List<T>>, Chunk.Offset> chunk) {
        baseEntityClient.queryable(tProxy).offsetChunk(size,chunk);

    }

    @Nullable
    @Override
    public T findOrNull(@NotNull Object id) {
        return baseEntityClient.queryable(tProxy).findOrNull(id);
    }

    @NotNull
    @Override
    public T findNotNull(@NotNull Object id, @NotNull Supplier<RuntimeException> throwFunc) {
        return baseEntityClient.queryable(tProxy).findNotNull(id,throwFunc);
    }

    @Nullable
    @Override
    public <TR> TR firstOrNull(Class<TR> resultClass) {
        return baseEntityClient.queryable(tProxy).firstOrNull(resultClass);
    }

    @NotNull
    @Override
    public <TR> TR firstNotNull(Class<TR> resultClass, Supplier<RuntimeException> throwFunc) {
        return baseEntityClient.queryable(tProxy).firstNotNull(resultClass,throwFunc);
    }

    @NotNull
    @Override
    public <TR> List<TR> toList(@NotNull Class<TR> resultClass) {
        return baseEntityClient.queryable(tProxy).toList(resultClass);
    }

    @NotNull
    @Override
    public <TR> List<TR> toList(@NotNull Class<TR> resultClass, @NotNull EntityMetadata resultEntityMetadata) {
        return baseEntityClient.queryable(tProxy).toList(resultClass,resultEntityMetadata);
    }

    @NotNull
    @Override
    public Map<String, Object> toMap() {
        return baseEntityClient.queryable(tProxy).toMap();
    }

    @NotNull
    @Override
    public List<Map<String, Object>> toMaps() {
        return baseEntityClient.queryable(tProxy).toMaps();
    }

    @NotNull
    @Override
    public <TResult> EasyPageResult<TResult> toPageResult(@NotNull Class<TResult> tResultClass, long pageIndex, long pageSize, long pageTotal) {
        return baseEntityClient.queryable(tProxy).toPageResult(tResultClass,pageIndex,pageSize,pageTotal);
    }

    @NotNull
    @Override
    public <TResult> EasyPageResult<TResult> toShardingPageResult(Class<TResult> tResultClass, long pageIndex, long pageSize, List<Long> totalLines) {
        return baseEntityClient.queryable(tProxy).toShardingPageResult(tResultClass,pageIndex,pageSize,totalLines);
    }

    @Nullable
    @Override
    public <TR> TR singleOrNull(@NotNull Class<TR> resultClass) {
        return baseEntityClient.queryable(tProxy).singleOrNull(resultClass);
    }

    @NotNull
    @Override
    public <TR> TR singleNotNull(@NotNull Class<TR> resultClass, @NotNull Supplier<RuntimeException> throwFunc) {
        return baseEntityClient.queryable(tProxy).singleNotNull(resultClass,throwFunc);
    }

    @NotNull
    @Override
    public <TR> TR streamBy(@NotNull Function<Stream<T>, TR> fetcher, @NotNull SQLConsumer<Statement> configurer) {
        return baseEntityClient.queryable(tProxy).streamBy(fetcher,configurer);
    }

    @NotNull
    @Override
    public <TR> JdbcStreamResult<TR> toStreamResult(@NotNull Class<TR> resultClass, @NotNull SQLConsumer<Statement> configurer) {
        return baseEntityClient.queryable(tProxy).toStreamResult(resultClass,configurer);
    }

    @NotNull
    @Override
    public List<T> toTreeList(boolean ignore) {
        return baseEntityClient.queryable(tProxy).toTreeList(ignore);
    }

    @NotNull
    @Override
    public Class<T> queryClass() {
        return baseEntityClient.queryable(tProxy).queryClass();
    }

    @NotNull
    @Override
    public EntityMetadata queryEntityMetadata() {
        return baseEntityClient.queryable(tProxy).queryEntityMetadata();
    }

    @Override
    public MethodQuery<T> forEach(Consumer<T> mapConfigure) {
        return baseEntityClient.queryable(tProxy).forEach(mapConfigure);
    }
}
