package com.easy.query.api.proxy.entity.select.impl;

import com.easy.query.api.proxy.base.ListProxy;
import com.easy.query.api.proxy.entity.select.EntityQueryable;
import com.easy.query.api.proxy.entity.select.EntityQueryable2;
import com.easy.query.api.proxy.sql.ProxyFilter;
import com.easy.query.api.proxy.sql.ProxySelector;
import com.easy.query.core.api.dynamic.sort.ObjectSort;
import com.easy.query.core.api.pagination.EasyPageResult;
import com.easy.query.core.basic.api.select.ClientQueryable;
import com.easy.query.core.basic.api.select.Query;
import com.easy.query.core.basic.jdbc.executor.internal.enumerable.DraftResult;
import com.easy.query.core.basic.jdbc.executor.internal.enumerable.JdbcStreamResult;
import com.easy.query.core.basic.jdbc.parameter.ToSQLContext;
import com.easy.query.core.basic.pagination.EasyPageResultProvider;
import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.enums.sharding.ConnectionModeEnum;
import com.easy.query.core.expression.builder.core.ValueFilter;
import com.easy.query.core.expression.lambda.Property;
import com.easy.query.core.expression.lambda.SQLConsumer;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.lambda.SQLExpression2;
import com.easy.query.core.expression.lambda.SQLFuncExpression1;
import com.easy.query.core.expression.parser.core.base.tree.TreeCTEConfigurer;
import com.easy.query.core.expression.segment.ColumnSegment;
import com.easy.query.core.expression.sql.builder.EntityQueryExpressionBuilder;
import com.easy.query.core.expression.sql.builder.ExpressionContext;
import com.easy.query.core.metadata.EntityMetadata;
import com.easy.query.core.metadata.EntityMetadataManager;
import com.easy.query.core.metadata.NavigateMetadata;
import com.easy.query.core.proxy.PropTypeColumn;
import com.easy.query.core.proxy.ProxyEntity;
import com.easy.query.core.proxy.ProxyEntityAvailable;
import com.easy.query.core.proxy.SQLColumn;
import com.easy.query.core.proxy.SQLGroupByExpression;
import com.easy.query.core.proxy.SQLSelectAsExpression;
import com.easy.query.core.proxy.SQLSelectExpression;
import com.easy.query.core.proxy.columns.SQLQueryable;
import com.easy.query.core.proxy.core.draft.DraftFetcher;
import com.easy.query.core.util.EasyObjectUtil;

import java.math.BigDecimal;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * create time 2024/2/24 21:53
 * 文件说明
 *
 * @author xuejiaming
 */
public class EasySelectManyQueryable<TProxy extends ProxyEntity<TProxy, TEntity>, TEntity> implements EntityQueryable<ListProxy<TProxy, TEntity>, List<TEntity>> {

    private final ClientQueryable<?> queryable;
    private final ListProxy<TProxy, TEntity> listProxy;
    private final String navValue;
    private final NavigateMetadata navigateMetadata;
    private final QueryRuntimeContext runtimeContext;
    private final Property<Object, ?> navigateGetter;

    public EasySelectManyQueryable(ClientQueryable<?> queryable, ListProxy<TProxy, TEntity> listProxy, String navValue) {

        this.listProxy = listProxy;
        this.navValue = navValue;
        this.runtimeContext = queryable.getSQLEntityExpressionBuilder().getRuntimeContext();
        EntityMetadata entityMetadata = listProxy.getSqlQueryable().getOriginalTable().getEntityMetadata();
        if (Objects.equals(entityMetadata.getEntityClass(), queryable.queryClass())) {
            this.navigateMetadata = entityMetadata.getNavigateNotNull(navValue);
            this.navigateGetter = this.navigateMetadata.getGetter();
            this.queryable = queryable;
        } else {
            ExpressionContext expressionContext = queryable.getSQLEntityExpressionBuilder().getExpressionContext();
            //如果没有include默认include
            if (!expressionContext.hasIncludes()) {
                EntityMetadataManager entityMetadataManager = runtimeContext.getEntityMetadataManager();
                EntityMetadata queryEntityMetadata = runtimeContext.getEntityMetadataManager().getEntityMetadata(queryable.queryClass());
                String[] navValueSplit = navValue.split("\\.");
                String firstNavValue = navValueSplit[0];
                NavigateMetadata currentNavigateMetadata = queryEntityMetadata.getNavigateNotNull(firstNavValue);
                EntityMetadata currentEntityMetadata = entityMetadataManager.getEntityMetadata(currentNavigateMetadata.getNavigatePropertyType());
                for (int i = 1; i < navValueSplit.length - 1; i++) {
                    String currentNavValue = navValueSplit[i];
                    currentNavigateMetadata = currentEntityMetadata.getNavigateNotNull(currentNavValue);
                    currentEntityMetadata = entityMetadataManager.getEntityMetadata(currentNavigateMetadata.getNavigatePropertyType());
                }
                String targetPropertyOrPrimary = currentNavigateMetadata.getTargetPropertyOrPrimary(runtimeContext);
                NavigateMetadata navigateMetadataResult = currentEntityMetadata.getNavigateNotNull(navValueSplit[navValueSplit.length - 1]);
                this.navigateMetadata = navigateMetadataResult;
                this.navigateGetter = navigateMetadataResult.getGetter();
                this.queryable = queryable.select(currentEntityMetadata.getEntityClass(), o -> o.getAsSelector().column(listProxy.getSqlQueryable().getOriginalTable(), targetPropertyOrPrimary))
                        .include(t -> t.with(navValueSplit[navValueSplit.length - 1]));
            } else {
                //如果存在include那么就只能一张表一张表走
                EntityMetadataManager entityMetadataManager = runtimeContext.getEntityMetadataManager();
                EntityMetadata queryEntityMetadata = runtimeContext.getEntityMetadataManager().getEntityMetadata(queryable.queryClass());
                String[] navValueSplit = navValue.split("\\.");
                String firstNavValue = navValueSplit[0];
                NavigateMetadata currentNavigateMetadata = queryEntityMetadata.getNavigateNotNull(firstNavValue);
                EntityMetadata currentEntityMetadata = entityMetadataManager.getEntityMetadata(currentNavigateMetadata.getNavigatePropertyType());
                List<Property<Object, ?>> replyExpressions = new ArrayList<>();
                replyExpressions.add(currentNavigateMetadata.getGetter());
                for (int i = 1; i < navValueSplit.length - 1; i++) {
                    String currentNavValue = navValueSplit[i];
                    currentNavigateMetadata = currentEntityMetadata.getNavigateNotNull(currentNavValue);
                    currentEntityMetadata = entityMetadataManager.getEntityMetadata(currentNavigateMetadata.getNavigatePropertyType());
                    replyExpressions.add(currentNavigateMetadata.getGetter());
                }
                NavigateMetadata navigateMetadataResult = currentEntityMetadata.getNavigateNotNull(navValueSplit[navValueSplit.length - 1]);
                replyExpressions.add(navigateMetadataResult.getGetter());
                this.navigateMetadata = navigateMetadataResult;
                this.navigateGetter = obj -> {
                    if (obj == null) {
                        return null;
                    }
                    Iterator<Property<Object, ?>> iterator = replyExpressions.iterator();
                    Property<Object, ?> first = iterator.next();
                    Object value = first.apply(obj);
                    while (iterator.hasNext()) {
                        if (value != null) {
                            value = iterator.next().apply(value);
                        } else {
                            return null;
                        }
                    }
                    return value;
                };
                this.queryable = queryable;
            }
        }
    }

    @Override
    public EntityQueryable<ListProxy<TProxy, TEntity>, List<TEntity>> noInterceptor() {
        queryable.noInterceptor();
        return this;
    }

    @Override
    public EntityQueryable<ListProxy<TProxy, TEntity>, List<TEntity>> useInterceptor(String name) {
        queryable.useInterceptor(name);
        return this;
    }

    @Override
    public EntityQueryable<ListProxy<TProxy, TEntity>, List<TEntity>> noInterceptor(String name) {
        queryable.noInterceptor(name);
        return this;
    }

    @Override
    public EntityQueryable<ListProxy<TProxy, TEntity>, List<TEntity>> useInterceptor() {
        queryable.useInterceptor();
        return this;
    }

    @Override
    public EntityQueryable<ListProxy<TProxy, TEntity>, List<TEntity>> useLogicDelete(boolean enable) {
        queryable.useLogicDelete(enable);
        return this;
    }

    @Override
    public EntityQueryable<ListProxy<TProxy, TEntity>, List<TEntity>> queryLargeColumn(boolean queryLarge) {
        queryable.queryLargeColumn(queryLarge);
        return this;
    }

    @Override
    public EntityQueryable<ListProxy<TProxy, TEntity>, List<TEntity>> asTable(Function<String, String> tableNameAs) {
        return this;
    }

    @Override
    public EntityQueryable<ListProxy<TProxy, TEntity>, List<TEntity>> asSchema(Function<String, String> schemaAs) {
        return this;
    }

    @Override
    public EntityQueryable<ListProxy<TProxy, TEntity>, List<TEntity>> asAlias(String alias) {
        return this;
    }

    @Override
    public EntityQueryable<ListProxy<TProxy, TEntity>, List<TEntity>> asTableLink(Function<String, String> linkAs) {
        return this;
    }

    @Override
    public EntityQueryExpressionBuilder getSQLEntityExpressionBuilder() {
        return queryable.getSQLEntityExpressionBuilder();
    }

    @Override
    public <TR> String toSQL(Class<TR> resultClass, ToSQLContext toSQLContext) {
        return queryable.toSQL(resultClass, toSQLContext);
    }

    @Override
    public long count() {
        return queryable.count();
    }

    @Override
    public boolean any() {
        return queryable.any();
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

    @Override
    public List<TEntity> findNotNull(Object id, Supplier<RuntimeException> throwFunc) {
        Object entity = queryable.findNotNull(id, throwFunc);
        return getNavigates(entity);
    }

    @Override
    public <TR> TR firstOrNull(Class<TR> resultClass) {
        Object entity = queryable.firstOrNull(resultClass);
        return getNavigates(entity);
    }

    @Override
    public <TR> TR firstNotNull(Class<TR> resultClass, Supplier<RuntimeException> throwFunc) {
        Object entity = queryable.firstNotNull(resultClass, throwFunc);
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
        Object entity = queryable.singleOrNull(resultClass);
        return getNavigates(entity);
    }

    @Override
    public <TR> TR singleNotNull(Class<TR> resultClass, Supplier<RuntimeException> throwFunc) {
        Object entity = queryable.singleNotNull(resultClass, throwFunc);
        return getNavigates(entity);
    }

    @Override
    public <TR> JdbcStreamResult<TR> toStreamResult(Class<TR> resultClass, SQLConsumer<Statement> configurer) {
        throw new UnsupportedOperationException();
    }

    @Override
    public ClientQueryable<List<TEntity>> getClientQueryable() {
        throw new UnsupportedOperationException();
    }

    @Override
    public <TRProxy extends ProxyEntity<TRProxy, TR>, TR> List<TR> toList(TRProxy trProxy) {
        throw new UnsupportedOperationException();
    }

    @Override
    public EntityQueryable<ListProxy<TProxy, TEntity>, List<TEntity>> cloneQueryable() {
        return new EasySelectManyQueryable<>(queryable.cloneQueryable(), listProxy, navValue);
    }

    @Override
    public long countDistinct(SQLExpression2<ProxySelector, ListProxy<TProxy, TEntity>> selectExpression) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean all(SQLExpression2<ProxyFilter, ListProxy<TProxy, TEntity>> whereExpression) {
        throw new UnsupportedOperationException();
    }

    @Override
    public EntityQueryable<ListProxy<TProxy, TEntity>, List<TEntity>> select(String columns) {
        throw new UnsupportedOperationException();
    }

    @Override
    public EntityQueryable<ListProxy<TProxy, TEntity>, List<TEntity>> distinct(boolean condition) {
        throw new UnsupportedOperationException();
    }

    @Override
    public EntityQueryable<ListProxy<TProxy, TEntity>, List<TEntity>> limit(boolean condition, long offset, long rows) {
        throw new UnsupportedOperationException();
    }

    @Override
    public EntityQueryable<ListProxy<TProxy, TEntity>, List<TEntity>> asTracking() {
        return this;
    }

    @Override
    public EntityQueryable<ListProxy<TProxy, TEntity>, List<TEntity>> asNoTracking() {
        return this;
    }

    @Override
    public EntityQueryable<ListProxy<TProxy, TEntity>, List<TEntity>> useShardingConfigure(int maxShardingQueryLimit, ConnectionModeEnum connectionMode) {
        return this;
    }

    @Override
    public EntityQueryable<ListProxy<TProxy, TEntity>, List<TEntity>> useMaxShardingQueryLimit(int maxShardingQueryLimit) {
        return this;
    }

    @Override
    public EntityQueryable<ListProxy<TProxy, TEntity>, List<TEntity>> useConnectionMode(ConnectionModeEnum connectionMode) {
        return this;
    }

    @Override
    public EntityQueryable<ListProxy<TProxy, TEntity>, List<TEntity>> filterConfigure(ValueFilter valueFilter) {
        return this;
    }

    @Override
    public ListProxy<TProxy, TEntity> get1Proxy() {
        return listProxy;
    }

    @Override
    public QueryRuntimeContext getRuntimeContext() {
        return runtimeContext;
    }

    @Override
    public <TNumber extends Number> Query<TNumber> selectCount(Class<TNumber> numberClass) {
        throw new UnsupportedOperationException();
    }

    @Override
    public <TMember extends Number> Query<TMember> selectSum(SQLFuncExpression1<ListProxy<TProxy, TEntity>, SQLColumn<ListProxy<TProxy, TEntity>, TMember>> columnSelector) {

        throw new UnsupportedOperationException();
    }

    @Override
    public <TMember extends Number> Query<BigDecimal> selectAvg(SQLFuncExpression1<ListProxy<TProxy, TEntity>, SQLColumn<ListProxy<TProxy, TEntity>, TMember>> columnSelector) {

        throw new UnsupportedOperationException();
    }

    @Override
    public <TMember> Query<TMember> selectMax(SQLFuncExpression1<ListProxy<TProxy, TEntity>, SQLColumn<ListProxy<TProxy, TEntity>, TMember>> columnSelector) {

        throw new UnsupportedOperationException();
    }

    @Override
    public <TMember> Query<TMember> selectMin(SQLFuncExpression1<ListProxy<TProxy, TEntity>, SQLColumn<ListProxy<TProxy, TEntity>, TMember>> columnSelector) {

        throw new UnsupportedOperationException();
    }

    @Override
    public EntityQueryable<ListProxy<TProxy, TEntity>, List<TEntity>> where(boolean condition, SQLExpression1<ListProxy<TProxy, TEntity>> whereExpression) {
        return this;
    }

    @Override
    public EntityQueryable<ListProxy<TProxy, TEntity>, List<TEntity>> whereById(boolean condition, Object id) {
        return this;
    }

    @Override
    public <TProperty> EntityQueryable<ListProxy<TProxy, TEntity>, List<TEntity>> whereByIds(boolean condition, Collection<TProperty> ids) {
        return this;
    }

    @Override
    public EntityQueryable<ListProxy<TProxy, TEntity>, List<TEntity>> whereObject(boolean condition, Object object) {
        return this;
    }

    @Override
    public <TRProxy extends ProxyEntity<TRProxy, TR> & SQLGroupByExpression, TR> EntityQueryable<TRProxy, TR> groupBy(SQLFuncExpression1<ListProxy<TProxy, TEntity>, SQLFuncExpression1<ListProxy<TProxy, TEntity>, TRProxy>> selectExpression) {

        throw new UnsupportedOperationException();
    }

    @Override
    public EntityQueryable<ListProxy<TProxy, TEntity>, List<TEntity>> having(boolean condition, SQLExpression1<ListProxy<TProxy, TEntity>> aggregateFilterSQLExpression) {
        return this;
    }

    @Override
    public <TPropertyProxy extends ProxyEntity<TPropertyProxy, TProperty>, TProperty extends ProxyEntityAvailable<TProperty, TPropertyProxy>> EntityQueryable<ListProxy<TProxy, TEntity>, List<TEntity>> include(boolean condition,SQLFuncExpression1<ListProxy<TProxy, TEntity>, TPropertyProxy> navigateIncludeSQLExpression, SQLExpression1<EntityQueryable<TPropertyProxy, TProperty>> includeAdapterExpression, Integer groupSize) {
        throw new UnsupportedOperationException();
    }

    @Override
    public <TPropertyProxy extends ProxyEntity<TPropertyProxy, TProperty>, TProperty extends ProxyEntityAvailable<TProperty, TPropertyProxy>> EntityQueryable<ListProxy<TProxy, TEntity>, List<TEntity>> includes(boolean condition,SQLFuncExpression1<ListProxy<TProxy, TEntity>, SQLQueryable<TPropertyProxy, TProperty>> navigateIncludeSQLExpression, SQLExpression1<EntityQueryable<TPropertyProxy, TProperty>> includeAdapterExpression, Integer groupSize) {
        throw new UnsupportedOperationException();
    }

    @Override
    public <T2Proxy extends ProxyEntity<T2Proxy, T2>, T2 extends ProxyEntityAvailable<T2, T2Proxy>> EntityQueryable2<ListProxy<TProxy, TEntity>, List<TEntity>, T2Proxy, T2> leftJoin(Class<T2> joinClass, SQLExpression2<ListProxy<TProxy, TEntity>, T2Proxy> onExpression) {

        throw new UnsupportedOperationException();
    }

    @Override
    public <T2Proxy extends ProxyEntity<T2Proxy, T2>, T2> EntityQueryable2<ListProxy<TProxy, TEntity>, List<TEntity>, T2Proxy, T2> leftJoin(EntityQueryable<T2Proxy, T2> joinQueryable, SQLExpression2<ListProxy<TProxy, TEntity>, T2Proxy> onExpression) {

        throw new UnsupportedOperationException();
    }

    @Override
    public <T2Proxy extends ProxyEntity<T2Proxy, T2>, T2 extends ProxyEntityAvailable<T2, T2Proxy>> EntityQueryable2<ListProxy<TProxy, TEntity>, List<TEntity>, T2Proxy, T2> rightJoin(Class<T2> joinClass, SQLExpression2<ListProxy<TProxy, TEntity>, T2Proxy> onExpression) {

        throw new UnsupportedOperationException();
    }

    @Override
    public <T2Proxy extends ProxyEntity<T2Proxy, T2>, T2> EntityQueryable2<ListProxy<TProxy, TEntity>, List<TEntity>, T2Proxy, T2> rightJoin(EntityQueryable<T2Proxy, T2> joinQueryable, SQLExpression2<ListProxy<TProxy, TEntity>, T2Proxy> onExpression) {

        throw new UnsupportedOperationException();
    }

    @Override
    public <T2Proxy extends ProxyEntity<T2Proxy, T2>, T2 extends ProxyEntityAvailable<T2, T2Proxy>> EntityQueryable2<ListProxy<TProxy, TEntity>, List<TEntity>, T2Proxy, T2> innerJoin(Class<T2> joinClass, SQLExpression2<ListProxy<TProxy, TEntity>, T2Proxy> onExpression) {

        throw new UnsupportedOperationException();
    }

    @Override
    public <T2Proxy extends ProxyEntity<T2Proxy, T2>, T2> EntityQueryable2<ListProxy<TProxy, TEntity>, List<TEntity>, T2Proxy, T2> innerJoin(EntityQueryable<T2Proxy, T2> joinQueryable, SQLExpression2<ListProxy<TProxy, TEntity>, T2Proxy> onExpression) {

        throw new UnsupportedOperationException();
    }

    @Override
    public EntityQueryable<ListProxy<TProxy, TEntity>, List<TEntity>> orderBy(boolean condition, SQLExpression1<ListProxy<TProxy, TEntity>> selectExpression) {

        throw new UnsupportedOperationException();
    }

    @Override
    public EntityQueryable<ListProxy<TProxy, TEntity>, List<TEntity>> orderByObject(boolean condition, ObjectSort objectSort) {

        throw new UnsupportedOperationException();
    }

    @Override
    public EntityQueryable<ListProxy<TProxy, TEntity>, List<TEntity>> getQueryable() {
        return this;
    }

    @Override
    public EntityQueryable<ListProxy<TProxy, TEntity>, List<TEntity>> fetchBy(SQLFuncExpression1<ListProxy<TProxy, TEntity>, SQLSelectExpression> selectExpression) {

        throw new UnsupportedOperationException();
    }

    @Override
    public <TRProxy extends ProxyEntity<TRProxy, TR>, TR> EntityQueryable<TRProxy, TR> select(SQLFuncExpression1<ListProxy<TProxy, TEntity>, TRProxy> selectExpression) {

        throw new UnsupportedOperationException();
    }

    @Override
    public <TR> Query<TR> selectColumn(SQLFuncExpression1<ListProxy<TProxy, TEntity>, PropTypeColumn<TR>> selectExpression) {

        throw new UnsupportedOperationException();
    }

    @Override
    public <TR> Query<TR> select(Class<TR> resultClass) {
        throw new UnsupportedOperationException();
    }

    @Override
    public <TR> Query<TR> select(Class<TR> resultClass, SQLFuncExpression1<ListProxy<TProxy, TEntity>, SQLSelectAsExpression> selectExpression) {

        throw new UnsupportedOperationException();
    }

    @Override
    public <TR> Query<TR> selectAutoInclude(Class<TR> resultClass) {
        throw new UnsupportedOperationException();
    }

    @Override
    public <TRProxy extends ProxyEntity<TRProxy, TR>, TR extends ProxyEntityAvailable<TR, TRProxy> & DraftResult> EntityQueryable<TRProxy, TR> selectDraft(SQLFuncExpression1<ListProxy<TProxy, TEntity>, DraftFetcher<TR, TRProxy>> selectExpression) {

        throw new UnsupportedOperationException();
    }

    @Override
    public EntityQueryable<ListProxy<TProxy, TEntity>, List<TEntity>> select(Collection<ColumnSegment> columnSegments, boolean clearAll) {

        throw new UnsupportedOperationException();
    }

    @Override
    public EntityQueryable<ListProxy<TProxy, TEntity>, List<TEntity>> asTreeCTE(SQLFuncExpression1<ListProxy<TProxy, TEntity>, SQLColumn<ListProxy<TProxy, TEntity>, ?>> codePropertyExpression, SQLFuncExpression1<ListProxy<TProxy, TEntity>, SQLColumn<ListProxy<TProxy, TEntity>, ?>> parentCodePropertyExpression, SQLExpression1<TreeCTEConfigurer> treeExpression) {

        throw new UnsupportedOperationException();
    }

    @Override
    public EntityQueryable<ListProxy<TProxy, TEntity>, List<TEntity>> union(Collection<EntityQueryable<ListProxy<TProxy, TEntity>, List<TEntity>>> unionQueries) {

        throw new UnsupportedOperationException();
    }

    @Override
    public EntityQueryable<ListProxy<TProxy, TEntity>, List<TEntity>> unionAll(Collection<EntityQueryable<ListProxy<TProxy, TEntity>, List<TEntity>>> unionQueries) {

        throw new UnsupportedOperationException();
    }

    //
//    @Override
//    public Query<List<TEntity>> cloneQueryable() {
//        return new EasySelectManyQueryable<>(queryable.cloneQueryable(),navValue);
//    }
//
//    @Override
//    public EntityQueryExpressionBuilder getSQLEntityExpressionBuilder() {
//        return queryable.getSQLEntityExpressionBuilder();
//    }
//
//    @Override
//    public <TR> String toSQL(Class<TR> resultClass, ToSQLContext toSQLContext) {
//        throw new UnsupportedOperationException();
//    }
//
//    @Override
//    public long count() {
//        throw new UnsupportedOperationException();
//    }
//
//    @Override
//    public boolean any() {
//        throw new UnsupportedOperationException();
//    }
//
//    @Override
//    public Query<TEntity> distinct(boolean condition) {
//        throw new UnsupportedOperationException();
//    }
//
//    @Override
//    public Query<List<TEntity>> limit(boolean condition, long offset, long rows) {
//        throw new UnsupportedOperationException();
//    }
//
//    @Override
//    public Query<List<TEntity>> asTracking() {
//        throw new UnsupportedOperationException();
//    }
//
//    @Override
//    public Query<List<TEntity>> asNoTracking() {
//        throw new UnsupportedOperationException();
//    }
//
//    @Override
//    public Query<List<TEntity>> useShardingConfigure(int maxShardingQueryLimit, ConnectionModeEnum connectionMode) {
//        throw new UnsupportedOperationException();
//    }
//
//    @Override
//    public Query<List<TEntity>> useMaxShardingQueryLimit(int maxShardingQueryLimit) {
//        throw new UnsupportedOperationException();
//    }
//
//    @Override
//    public Query<List<TEntity>> useConnectionMode(ConnectionModeEnum connectionMode) {
//        throw new UnsupportedOperationException();
//    }
//
//    @Override
//    public Class<List<TEntity>> queryClass() {
//        return EasyObjectUtil.typeCastNullable(List.class);
//    }
//
//    @Override
//    public List<TEntity> findOrNull(Object id) {
//        Object entity = queryable.findOrNull(id);
//        return getNavigates(entity);
//    }
//
    private <TResult> TResult getNavigates(Object entity) {
        if (entity != null) {
            Collection<?> values = EasyObjectUtil.typeCastNullable(navigateGetter.apply(entity));
            if (values == null) {
                return null;
            }
            return EasyObjectUtil.typeCastNullable(new ArrayList<>(values));
        }
        return null;
    }

    @Override
    public EntityQueryable<ListProxy<TProxy, TEntity>, List<TEntity>> tableLogicDelete(Supplier<Boolean> tableLogicDel) {
        throw new UnsupportedOperationException();
    }
//
//    @Override
//    public List<TEntity> findNotNull(Object id, Supplier<RuntimeException> throwFunc) {
//        Object entity = queryable.findNotNull(id, throwFunc);
//        return getNavigates(entity);
//    }
//
//    @Override
//    public <TR> TR firstOrNull(Class<TR> resultClass) {
//        Object entity = queryable.firstOrNull(queryable.queryClass());
//        return getNavigates(entity);
//    }
//
//    @Override
//    public <TR> TR firstNotNull(Class<TR> resultClass, Supplier<RuntimeException> throwFunc) {
//        Object entity = queryable.firstNotNull(queryable.queryClass(), throwFunc);
//        return getNavigates(entity);
//    }
//
//    @Override
//    public <TR> List<TR> toList(Class<TR> resultClass) {
//        List<?> entities = queryable.toList(queryable.queryClass());
//        return entities.stream().map(o -> {
//            return this.<TR>getNavigates(o);
//        }).collect(Collectors.toList());
//    }
//
//    @Override
//    public Map<String, Object> toMap() {
//        throw new UnsupportedOperationException();
//    }
//
//    @Override
//    public List<Map<String, Object>> toMaps() {
//        throw new UnsupportedOperationException();
//    }
//
//    @Override
//    public <TResult> EasyPageResult<TResult> toPageResult(Class<TResult> tResultClass, long pageIndex, long pageSize, long pageTotal) {
//
//        EasyPageResult<?> pageResult = queryable.toPageResult(queryable.queryClass(), pageIndex, pageIndex, pageTotal);
//        EasyPageResultProvider easyPageResultProvider = runtimeContext.getEasyPageResultProvider();
//        List<TResult> data = pageResult.getData().stream().map(o -> {
//            return this.<TResult>getNavigates(o);
//        }).collect(Collectors.toList());
//        return easyPageResultProvider.createPageResult(pageIndex, pageSize, pageResult.getTotal(), data);
//    }
//
//    @Override
//    public <TResult> EasyPageResult<TResult> toShardingPageResult(Class<TResult> tResultClass, long pageIndex, long pageSize, List<Long> totalLines) {
//        throw new UnsupportedOperationException();
//    }
//
//    @Override
//    public <TR> TR singleOrNull(Class<TR> resultClass) {
//        Object entity = queryable.singleOrNull(queryable.queryClass());
//        return getNavigates(entity);
//    }
//
//    @Override
//    public <TR> TR singleNotNull(Class<TR> resultClass, Supplier<RuntimeException> throwFunc) {
//        Object entity = queryable.singleNotNull(queryable.queryClass(),throwFunc);
//        return getNavigates(entity);
//    }
//
//    @Override
//    public <TR> JdbcStreamResult<TR> toStreamResult(Class<TR> resultClass, SQLConsumer<Statement> configurer) {
//        throw new UnsupportedOperationException();
//    }
}
