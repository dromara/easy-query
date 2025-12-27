package com.easy.query.api.proxy.entity.select.abstraction;

import com.easy.query.api.proxy.entity.EntityQueryProxyManager;
import com.easy.query.api.proxy.entity.select.EntityQueryable;
import com.easy.query.api.proxy.entity.select.EntityQueryable2;
import com.easy.query.api.proxy.entity.select.impl.EasyEntityQueryable;
import com.easy.query.api.proxy.entity.select.impl.EasySelectFlatQueryable;
import com.easy.query.api.proxy.entity.select.join.join2.CrossJoinExpressionJoiner2;
import com.easy.query.api.proxy.entity.select.join.join2.InnerJoinExpressionJoiner2;
import com.easy.query.api.proxy.entity.select.join.join2.LeftJoinExpressionJoiner2;
import com.easy.query.api.proxy.entity.select.join.join2.RightJoinExpressionJoiner2;
import com.easy.query.api.proxy.extension.tree.EntityTreeCTEConfigurer;
import com.easy.query.api.proxy.extension.tree.EntityTreeCTEConfigurerImpl;
import com.easy.query.api.proxy.util.EasyProxyUtil;
import com.easy.query.core.basic.api.select.ResultSetContext;
import com.easy.query.core.basic.api.select.executor.MethodQuery;
import com.easy.query.core.common.Chunk;
import com.easy.query.core.common.ValueHolder2;
import com.easy.query.core.expression.lambda.SQLResultSetFunc;
import com.easy.query.core.metadata.EndNavigateParams;
import com.easy.query.core.proxy.core.FlatEntitySQLContext;
import com.easy.query.core.proxy.sql.include.NavigatePathAvailable;
import org.jetbrains.annotations.NotNull;
import com.easy.query.core.api.dynamic.executor.query.ConfigureArgument;
import com.easy.query.core.api.dynamic.sort.ObjectSort;
import com.easy.query.core.api.pagination.EasyPageResult;
import com.easy.query.core.basic.api.select.ClientQueryable;
import com.easy.query.core.basic.api.select.Query;
import com.easy.query.core.basic.api.select.impl.EasyClientQueryable;
import com.easy.query.core.basic.jdbc.executor.ResultColumnMetadata;
import com.easy.query.core.basic.jdbc.executor.internal.enumerable.JdbcStreamResult;
import com.easy.query.core.basic.jdbc.parameter.ToSQLContext;
import com.easy.query.core.common.ValueHolder;
import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.enums.sharding.ConnectionModeEnum;
import com.easy.query.core.exception.EasyQueryInvalidOperationException;
import com.easy.query.core.expression.builder.core.ValueFilter;
import com.easy.query.core.expression.lambda.SQLConsumer;
import com.easy.query.core.expression.lambda.SQLActionExpression1;
import com.easy.query.core.expression.lambda.SQLActionExpression2;
import com.easy.query.core.expression.lambda.SQLFuncExpression1;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.parser.core.base.tree.TreeCTEConfigurer;
import com.easy.query.core.expression.segment.ColumnSegment;
import com.easy.query.core.expression.sql.builder.EntityQueryExpressionBuilder;
import com.easy.query.core.expression.sql.builder.internal.ContextConfigurer;
import com.easy.query.core.func.def.DistinctDefaultSQLFunction;
import com.easy.query.core.metadata.EntityMetadata;
import com.easy.query.core.metadata.IncludeNavigateParams;
import com.easy.query.core.metadata.NavigateMetadata;
import com.easy.query.core.proxy.ManyPropColumn;
import com.easy.query.core.proxy.PropTypeColumn;
import com.easy.query.core.proxy.ProxyEntity;
import com.easy.query.core.proxy.ProxyEntityAvailable;
import com.easy.query.core.proxy.SQLColumn;
import com.easy.query.core.proxy.SQLGroupByExpression;
import com.easy.query.core.proxy.SQLSelectAsExpression;
import com.easy.query.core.proxy.columns.SQLQueryable;
import com.easy.query.core.proxy.fetcher.EntityFetcher;
import com.easy.query.core.proxy.sql.Select;
import com.easy.query.core.util.EasyCollectionUtil;
import com.easy.query.core.util.EasyNavigateUtil;
import com.easy.query.core.util.EasyObjectUtil;
import com.easy.query.core.util.EasySQLSegmentUtil;

import java.sql.Statement;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Stream;

/**
 * create time 2023/6/23 22:08
 * 文件说明
 *
 * @author xuejiaming
 */
public abstract class AbstractEntityQueryable<T1Proxy extends ProxyEntity<T1Proxy, T1>, T1> implements EntityQueryable<T1Proxy, T1> {

    protected final T1Proxy t1Proxy;
    protected final QueryRuntimeContext runtimeContext;
    protected final ClientQueryable<T1> clientQueryable;

    public AbstractEntityQueryable(T1Proxy t1Proxy, ClientQueryable<T1> clientQueryable) {
        EntityQueryExpressionBuilder sqlEntityExpressionBuilder = clientQueryable.getSQLEntityExpressionBuilder();
        this.runtimeContext = sqlEntityExpressionBuilder.getRuntimeContext();
        this.t1Proxy = t1Proxy.create(sqlEntityExpressionBuilder.getTable(0).getEntityTable(), sqlEntityExpressionBuilder, sqlEntityExpressionBuilder.getRuntimeContext());
        this.clientQueryable = clientQueryable;
    }

    @Override
    public T1Proxy get1Proxy() {
        return t1Proxy;
    }

    @NotNull
    @Override
    public QueryRuntimeContext getRuntimeContext() {
        return runtimeContext;
    }

    @Override
    public ClientQueryable<T1> getClientQueryable() {
        return clientQueryable;
    }

    @NotNull
    @Override
    public EntityQueryable<T1Proxy, T1> toCteAs(String tableName) {
        return new EasyEntityQueryable<>(get1Proxy(), getClientQueryable().toCteAs(tableName));
    }

    @Override
    public EntityQueryable<T1Proxy, T1> getQueryable() {
        return this;
    }

    @NotNull
    @Override
    public Class<T1> queryClass() {
        return clientQueryable.queryClass();
    }

    @NotNull
    @Override
    public EntityMetadata queryEntityMetadata() {
        return clientQueryable.queryEntityMetadata();
    }

    @Override
    public long count() {
        return clientQueryable.count();
    }

    @Override
    public boolean any() {
        return getClientQueryable().any();
    }

    @Override
    public <TR> TR firstOrNull(Class<TR> resultClass) {
        return getClientQueryable().firstOrNull(resultClass);
    }

    @Override
    public <TR> TR singleOrNull(@NotNull Class<TR> resultClass) {
        return getClientQueryable().singleOrNull(resultClass);
    }

    @Override
    public T1 findOrNull(@NotNull Object id) {
        return getClientQueryable().findOrNull(id);
    }


    @NotNull
    @Override
    public <TR> TR firstNotNull(@NotNull Class<TR> resultClass, String msg, String code) {
        return getClientQueryable().firstNotNull(resultClass, msg, code);
    }

    @NotNull
    @Override
    public <TR> TR firstNotNull(Class<TR> resultClass, Supplier<RuntimeException> throwFunc) {
        return getClientQueryable().firstNotNull(resultClass, throwFunc);
    }

    @NotNull
    @Override
    public <TR> TR singleNotNull(@NotNull Class<TR> resultClass, @NotNull Supplier<RuntimeException> throwFunc) {
        return getClientQueryable().singleNotNull(resultClass, throwFunc);
    }

    @NotNull
    @Override
    public T1 findNotNull(@NotNull Object id, @NotNull Supplier<RuntimeException> throwFunc) {
        return getClientQueryable().findNotNull(id, throwFunc);
    }

    @NotNull
    @Override
    public Map<String, Object> toMap() {
        return getClientQueryable().toMap();
    }

    @NotNull
    @Override
    public List<Map<String, Object>> toMaps() {
        return getClientQueryable().toMaps();
    }

    @NotNull
    @Override
    public <TR> List<TR> toList(@NotNull Class<TR> resultClass) {
        return clientQueryable.toList(resultClass);
    }

    @NotNull
    @Override
    public <TR> List<TR> toList(@NotNull Class<TR> resultClass, @NotNull EntityMetadata resultEntityMetadata) {
        return clientQueryable.toList(resultClass, resultEntityMetadata);
    }

    @Override
    public <TR> TR toResultSet(SQLResultSetFunc<ResultSetContext, TR> produce) {
        return clientQueryable.toResultSet(produce);
    }

    @NotNull
    @Override
    public List<T1> toTreeList(boolean ignore) {
        return clientQueryable.toTreeList(ignore);
    }

    @NotNull
    @Override
    public <TR> JdbcStreamResult<TR> toStreamResult(@NotNull Class<TR> resultClass, @NotNull SQLConsumer<Statement> configurer) {
        return clientQueryable.toStreamResult(resultClass, configurer);
    }

    @Override
    public void toChunkIf(int size, Predicate<List<T1>> chunk) {
        clientQueryable.toChunkIf(size, chunk);
    }

    @Override
    public void offsetChunk(int size, SQLFuncExpression1<Chunk<T1>, Chunk.Offset> chunk) {
        clientQueryable.offsetChunk(size, chunk);
    }

    @NotNull
    @Override
    public <TR> TR streamBy(@NotNull Function<Stream<T1>, TR> fetcher, @NotNull SQLConsumer<Statement> configurer) {
        return clientQueryable.streamBy(fetcher, configurer);
    }

    @NotNull
    @Override
    public <TR> String toSQL(@NotNull Class<TR> resultClass, @NotNull ToSQLContext toSQLContext) {
        return clientQueryable.toSQL(resultClass, toSQLContext);
    }

    @Override
    public <TNumber extends Number> Query<TNumber> selectCount(Class<TNumber> numberClass) {
        return getClientQueryable().selectCount(numberClass);
    }

    @Override
    public MethodQuery<T1> forEach(Consumer<T1> mapConfigure) {
        return getClientQueryable().forEach(mapConfigure);
    }

    @Override
    public <TMember> Query<TMember> selectCount(SQLFuncExpression1<T1Proxy, PropTypeColumn<TMember>> selectExpression, boolean distinct) {
        PropTypeColumn<TMember> column = selectExpression.apply(get1Proxy());
        Class<TMember> propertyType = EasyObjectUtil.typeCastNullable(column.getPropertyType());
        return getClientQueryable().select(propertyType, s -> {
            DistinctDefaultSQLFunction count = s.fx().count(x -> {
                PropTypeColumn.columnFuncSelector(x, column);
            }).distinct(distinct);
            s.sqlFunc(count);
        });
    }

    @Override
    public <TRProxy extends ProxyEntity<TRProxy, TR>, TR> EntityQueryable<TRProxy, TR> select(SQLFuncExpression1<T1Proxy, TRProxy> selectExpression) {

        TRProxy resultProxy = selectExpression.apply(get1Proxy());
        if (resultProxy instanceof EntityFetcher) {
            EntityFetcher resultProxy1 = (EntityFetcher) resultProxy;
            return Select.selectProxy(EasyObjectUtil.typeCastNullable(resultProxy1.fetchProxy()), getClientQueryable());
        }
        EntityQueryable<TRProxy, TR> trProxyTREntityQueryable = Select.selectProxy(resultProxy, getClientQueryable());
        trProxyTREntityQueryable.get1Proxy().getEntitySQLContext().setContextHolder(get1Proxy().getEntitySQLContext().getContextHolder());
        return trProxyTREntityQueryable;
//
//
//        Objects.requireNonNull(resultProxy, "select null result class");
//
//        if (resultProxy instanceof ListProxy) {
//            return Select.selectList((ListProxy<TRProxy, TR>) resultProxy,getQueryable().getClientQueryable());
//        }
//
//        SQLSelectAsExpression selectAsExpression = resultProxy.getEntitySQLContext().getSelectAsExpression();
//        if (selectAsExpression == null) {//全属性映射
//            TableAvailable tableOrNull = resultProxy.getTableOrNull();
//            if (tableOrNull == null) {
//                ClientQueryable<TR> select = getClientQueryable().select(resultProxy.getEntityClass());
//
//                Select.setDraftPropTypes(select, resultProxy);
//                return new EasyEntityQueryable<>(resultProxy, select);
//            } else {
//                ClientQueryable<TR> select = getClientQueryable().select(resultProxy.getEntityClass(), columnAsSelector -> {
//                    columnAsSelector.getAsSelector().columnAll(tableOrNull);
//                });
//                Select.setDraftPropTypes(select, resultProxy);
//                return new EasyEntityQueryable<>(resultProxy, select);
//            }
//        } else {
//            ClientQueryable<TR> select = getClientQueryable().select(resultProxy.getEntityClass(), columnAsSelector -> {
//                selectAsExpression.accept(columnAsSelector.getAsSelector());
//            });
//            Select.setDraftPropTypes(select, resultProxy);
//            return new EasyEntityQueryable<>(resultProxy, select);
//        }
    }


    @Override
    public <TR> Query<TR> selectColumn(SQLFuncExpression1<T1Proxy, PropTypeColumn<TR>> selectExpression) {
        PropTypeColumn<TR> column = selectExpression.apply(get1Proxy());
        Objects.requireNonNull(column, "select column null result class");

        ResultColumnMetadata resultColumnMetadata = column.getTableResultColumnMetadataOrNull(0);
        if (resultColumnMetadata != null) {
            ResultColumnMetadata[] resultColumnMetadatas = new ResultColumnMetadata[]{resultColumnMetadata};
            clientQueryable.getSQLEntityExpressionBuilder().getExpressionContext().setResultPropTypes(resultColumnMetadatas);
        }
        ClientQueryable<?> select = clientQueryable.select(column.getPropertyType(), o -> {
            PropTypeColumn.selectColumn(o.getAsSelector(), column);
        });
        return EasyObjectUtil.typeCastNullable(select);
    }

//    @Override
//    public <TRProxy extends ProxyEntity<TRProxy, TR>, TR> Query<List<TR>> selectMany(SQLFuncExpression1<T1Proxy, SQLQueryable<TRProxy, TR>> selectExpression) {
//        SQLQueryable<TRProxy, TR> sqlQueryable = selectExpression.apply(get1Proxy());
//        Objects.requireNonNull(sqlQueryable, "select columns null result class");
//        return new EasyColumnsQueryable<>(getClientQueryable(), sqlQueryable.getNavValue());
//    }

    @Override
    public <TR> Query<TR> select(Class<TR> resultClass) {
        return clientQueryable.select(resultClass);
    }

    @Override
    public <TR> Query<TR> select(Class<TR> resultClass, SQLFuncExpression1<T1Proxy, SQLSelectAsExpression> selectExpression) {
        SQLSelectAsExpression sqlSelectAsExpression = selectExpression.apply(get1Proxy());
        return getClientQueryable().select(resultClass, columnAsSelector -> {
            sqlSelectAsExpression.accept(columnAsSelector.getAsSelector());
        });
    }
//
//    @Override
//    public <TR> Query<TR> selectAutoInclude(Class<TR> resultClass, boolean replace) {
//        return clientQueryable.selectAutoInclude(resultClass, replace);
//    }

    @Override
    public <TR> Query<TR> selectAutoInclude(Class<TR> resultClass, SQLFuncExpression1<T1Proxy, SQLSelectAsExpression> extraSelectExpression, boolean replace) {
        T1Proxy queryProxy = get1Proxy();
        return clientQueryable.selectAutoInclude(resultClass, columnAsSelector -> {
            if (extraSelectExpression != null) {
                SQLSelectAsExpression sqlSelectAsExpression = extraSelectExpression.apply(queryProxy);
                if (sqlSelectAsExpression != null) {
                    sqlSelectAsExpression.accept(columnAsSelector.getAsSelector());
                }
            }
        }, replace);
    }
    //    private void selectAutoInclude0(EntityMetadataManager entityMetadataManager, ClientQueryable<?> clientQueryable, EntityMetadata entityMetadata, EntityMetadata resultEntityMetadata) {
//        Collection<NavigateMetadata> resultNavigateMetadatas = resultEntityMetadata.getNavigateMetadatas();
//        if(EasyCollectionUtil.isEmpty(resultNavigateMetadatas)){
//            return;
//        }
//        for (NavigateMetadata resultNavigateMetadata : resultNavigateMetadatas) {
//            NavigateMetadata entityNavigateMetadata = entityMetadata.getNavigateOrNull(resultNavigateMetadata.getPropertyName());
//            if (entityNavigateMetadata == null) {
//                continue;
//            }
//
//            clientQueryable
//                    .include(t -> {
//                        ClientQueryable<Object> with = t.with(resultNavigateMetadata.getPropertyName());
//                        EntityMetadata entityEntityMetadata = entityMetadataManager.getEntityMetadata(entityNavigateMetadata.getNavigatePropertyType());
//                        EntityMetadata navigateEntityMetadata = entityMetadataManager.getEntityMetadata(resultNavigateMetadata.getNavigatePropertyType());
//                        selectAutoInclude0(entityMetadataManager,with, entityEntityMetadata,navigateEntityMetadata);
//                        return with;
//                    });
//        }
//    }

    @Override
    public <TPropertyProxy extends ProxyEntity<TPropertyProxy, TProperty>, TProperty> EntityQueryable<T1Proxy, T1> include(boolean condition, SQLFuncExpression1<T1Proxy, NavigatePathAvailable<TPropertyProxy, TProperty>> navigateIncludeSQLExpression, SQLActionExpression1<EntityQueryable<TPropertyProxy, TProperty>> includeAdapterExpression, Integer groupSize) {
        if (condition) {

            T1Proxy proxy = getQueryable().get1Proxy();
            ValueHolder2<TPropertyProxy,String> valueHolder = new ValueHolder2<>();
            proxy.getEntitySQLContext()._include(() -> {
                NavigatePathAvailable<TPropertyProxy, TProperty> navigateColumn = navigateIncludeSQLExpression.apply(proxy);
                valueHolder.setValue2(navigateColumn.getNavValue());
                TPropertyProxy empty = navigateColumn.__createNavigatePathEmpty();
                empty.setNavValue(null);
                valueHolder.setValue1(empty);
            });

//        getSQLEntityExpressionBuilder().removeRelationEntityTableExpression(new RelationTableKey(proxy.getEntityClass(),navigateColumn.getEntityClass()));
            return include0(valueHolder.getValue1(),valueHolder.getValue2(), includeAdapterExpression, groupSize);
        }
        return this;
    }

    @Override
    public <TPropertyProxy extends ProxyEntity<TPropertyProxy, TProperty>, TProperty> EntityQueryable<T1Proxy, T1> includes(boolean condition, SQLFuncExpression1<T1Proxy, SQLQueryable<TPropertyProxy, TProperty>> navigateIncludeSQLExpression, SQLActionExpression1<EntityQueryable<TPropertyProxy, TProperty>> includeAdapterExpression, Integer groupSize) {
        if (condition) {

            T1Proxy proxy = getQueryable().get1Proxy();
            ValueHolder2<TPropertyProxy,String> valueHolder = new ValueHolder2<>();
            proxy.getEntitySQLContext()._include(() -> {
                SQLQueryable<TPropertyProxy, TProperty> navigateColumnQueryable = navigateIncludeSQLExpression.apply(proxy);
//                TPropertyProxy navigateColumn = navigateColumnQueryable.getQueryable().get1Proxy();
                valueHolder.setValue2(navigateColumnQueryable.getNavValue());
                TPropertyProxy empty = navigateColumnQueryable.getProxy().createEmpty();
                empty.setNavValue(null);
                valueHolder.setValue1(empty);
            });
            return include0(valueHolder.getValue1(),valueHolder.getValue2(), includeAdapterExpression, groupSize);
        }
        return this;
    }

    private <TPropertyProxy extends ProxyEntity<TPropertyProxy, TProperty>, TProperty> EntityQueryable<T1Proxy, T1> include0(TPropertyProxy propertyProxy,String navValue, SQLActionExpression1<EntityQueryable<TPropertyProxy, TProperty>> includeAdapterExpression, Integer groupSize) {

        Objects.requireNonNull(navValue, "include [navValue] is null");

        ConfigureArgument configureArgument = getQueryable().getSQLEntityExpressionBuilder().getExpressionContext().getConfigureArgument();
        getClientQueryable().include(navigateInclude -> {


            ClientQueryable<TProperty> queryable = navigateInclude.with(navValue, groupSize);

            IncludeNavigateParams includeNavigateParams = navigateInclude.getIncludeNavigateParams();
            NavigateMetadata navigateMetadata = includeNavigateParams.getNavigateMetadata();

            if (includeAdapterExpression != null) {
                includeNavigateParams.setAdapterExpression(innerQueryable -> {
                    ClientQueryable<TProperty> cq = EasyObjectUtil.typeCastNullable(innerQueryable);
                    EasyEntityQueryable<TPropertyProxy, TProperty> entityQueryable = new EasyEntityQueryable<>(propertyProxy, cq);
                    includeAdapterExpression.apply(entityQueryable);
                });
                includeNavigateParams.getAdapterExpression().apply(queryable);
            }

            ClientQueryable<TProperty> clientQueryable = EasyNavigateUtil.navigateOrderBy(
                    queryable,
                    new EndNavigateParams(navigateMetadata),
                    includeNavigateParams,
                    runtimeContext.getEntityMetadataManager().getEntityMetadata(navigateMetadata.getNavigatePropertyType()),
                    configureArgument,
                    runtimeContext);

            return clientQueryable;
        });

        return getQueryable();
    }

    @Override
    public <T2Proxy extends ProxyEntity<T2Proxy, T2>, T2 extends ProxyEntityAvailable<T2, T2Proxy>> EntityQueryable<T1Proxy, T1> subQueryToGroupJoin(boolean condition, SQLFuncExpression1<T1Proxy, ManyPropColumn<T2Proxy, T2>> manyPropColumnExpression) {
        if (condition) {

            T1Proxy proxy = getQueryable().get1Proxy();
            ValueHolder<ManyPropColumn<T2Proxy, T2>> valueHolder = new ValueHolder<>();
            proxy.getEntitySQLContext()._include(() -> {
                ManyPropColumn<T2Proxy, T2> value = manyPropColumnExpression.apply(proxy);
                valueHolder.setValue(value);
            });
            TableAvailable table = valueHolder.getValue().getOriginalTable();
            String value = valueHolder.getValue().getNavValue();
            getClientQueryable().subQueryToGroupJoin(manyJoinSelector -> manyJoinSelector.subQueryProperty(table, value));
        }
        return getQueryable();
    }

    @Override
    public <T2Proxy extends ProxyEntity<T2Proxy, T2>, T2> EntityQueryable<T1Proxy, T1> subQueryConfigure(boolean condition, SQLFuncExpression1<T1Proxy, ManyPropColumn<T2Proxy, T2>> manyPropColumnExpression, SQLFuncExpression1<EntityQueryable<T2Proxy, T2>, EntityQueryable<T2Proxy, T2>> adapterExpression) {
        if (condition) {

            T1Proxy proxy = getQueryable().get1Proxy();
            ValueHolder<ManyPropColumn<T2Proxy, T2>> valueHolder = new ValueHolder<>();
            proxy.getEntitySQLContext()._include(() -> {
                ManyPropColumn<T2Proxy, T2> value = manyPropColumnExpression.apply(proxy);
                valueHolder.setValue(value);
            });
            TableAvailable table = valueHolder.getValue().getOriginalTable();
            String value = valueHolder.getValue().getNavValue();
            T2Proxy t2Proxy = valueHolder.getValue().getProxy().createEmpty();
            t2Proxy.setNavValue(null);
            getClientQueryable().subQueryConfigure(manyJoinSelector -> manyJoinSelector.subQueryProperty(table, value), cq -> {
                ClientQueryable<T2> innerClientQueryable = EasyObjectUtil.typeCastNotNull(cq);
                EasyEntityQueryable<T2Proxy, T2> entityQueryable = new EasyEntityQueryable<>(t2Proxy, innerClientQueryable);
                adapterExpression.apply(entityQueryable);
                return entityQueryable.getClientQueryable();
            });
        }
        return getQueryable();
    }

//    @Override
//    public <TR> Query<TR> select(Class<TR> resultClass, SQLFuncExpression1<T1Proxy, SQLSelectAsExpression> selectExpression) {
//        SQLSelectAsExpression sqlSelectAsExpression = selectExpression.apply(get1Proxy());
//        return entityQueryable.select(resultClass, columnAsSelector -> {
//            sqlSelectAsExpression.accept(columnAsSelector.getAsSelector());
//        });
//    }
    //    @Override
//    public <TRProxy extends ProxyEntity<TRProxy, TR>, TR> ProxyQueryable<TRProxy, TR> select(TRProxy trProxy, SQLExpression2<ProxyAsSelector<TRProxy, TR>, T1Proxy> selectExpression) {
//
//        ClientQueryable<TR> select = entityQueryable.select(trProxy.getEntityClass(), columnAsSelector -> {
//            selectExpression.apply(new ProxyAsSelectorImpl<>(trProxy, columnAsSelector.getAsSelector()), get1Proxy());
//        });
//        return new EasyProxyQueryable<>(trProxy, select);
//    }
//    @Override
//    public <TRProxy extends ProxyEntity<TRProxy, TR>, TR extends ProxyEntityAvailable<TR, TRProxy>> EntityQueryable<TRProxy, TR> selectAs(Class<TR> resultEntityClass) {
//        ClientQueryable<TR> select = entityQueryable.select(resultEntityClass);
//        TRProxy trProxy = EntityQueryProxyManager.create(resultEntityClass);
//        return new EasyEntityQueryable<>(trProxy, select);
//    }
//
//    @Override
//    public <TRProxy extends ProxyEntity<TRProxy, TR>, TR extends ProxyEntityAvailable<TR, TRProxy>> EntityQueryable<TRProxy, TR> selectAs(Class<TR> resultEntityClass, SQLFuncExpression2<T1Proxy, TRProxy, SQLSelectAsExpression> selectExpression) {
//        TRProxy trProxy = EntityQueryProxyManager.create(resultEntityClass);
//        ClientQueryable<TR> select = entityQueryable.select(resultEntityClass, columnAsSelector -> {
//            SQLSelectAsExpression sqlSelectAs = selectExpression.apply(t1Proxy, trProxy);
//            sqlSelectAs.accept(columnAsSelector.getAsSelector());
//        });
//        return new EasyEntityQueryable<>(trProxy, select);
//    }
//
//    @Override
//    public <TRProxy extends ProxyEntity<TRProxy, TR>, TR> EntityQueryable<TRProxy, TR> selectProxy(TRProxy trProxy) {
//        ClientQueryable<TR> select = entityQueryable.select(trProxy.getEntityClass());
//        return new EasyEntityQueryable<>(trProxy, select);
//    }
//
//    @Override
//    public <TRProxy extends ProxyEntity<TRProxy, TR>, TR> EntityQueryable<TRProxy, TR> selectAs(TRProxy trProxy, SQLFuncExpression1<T1Proxy, SQLSelectAsExpression> selectExpression) {
//        ClientQueryable<TR> select = entityQueryable.select(trProxy.getEntityClass(), columnAsSelector -> {
//            SQLSelectAsExpression sqlSelectAs = selectExpression.apply(t1Proxy);
//            sqlSelectAs.accept(columnAsSelector.getAsSelector());
//        });
//        return new EasyEntityQueryable<>(trProxy, select);
//    }

    @Override
    public EntityQueryable<T1Proxy, T1> asTreeCTE(SQLActionExpression1<EntityTreeCTEConfigurer<T1Proxy, T1>> treeExpression) {
        ClientQueryable<T1> clientQueryable = getClientQueryable().asTreeCTE(treeConfigurer -> {
            treeExpression.apply(new EntityTreeCTEConfigurerImpl<>(t1Proxy, treeConfigurer));
        });
        return new EasyEntityQueryable<>(get1Proxy(), clientQueryable);
    }

    @Override
    public EntityQueryable<T1Proxy, T1> asTreeCTECustom(SQLFuncExpression1<T1Proxy, SQLColumn<T1Proxy, ?>> codePropertyExpression, SQLFuncExpression1<T1Proxy, SQLColumn<T1Proxy, ?>> parentCodePropertyExpression, SQLActionExpression1<TreeCTEConfigurer> treeExpression) {
        ClientQueryable<T1> clientQueryable = getClientQueryable().asTreeCTECustom(codePropertyExpression.apply(get1Proxy()).getValue(), parentCodePropertyExpression.apply(get1Proxy()).getValue(), treeExpression);
        return new EasyEntityQueryable<>(get1Proxy(), clientQueryable);
    }

    @NotNull
    @Override
    public EntityQueryable<T1Proxy, T1> select(@NotNull String columns) {
        clientQueryable.select(columns);
        return this;
    }

    @Override
    public EntityQueryable<T1Proxy, T1> select(Collection<ColumnSegment> columnSegments, boolean clearAll) {
        clientQueryable.select(columnSegments, clearAll);
        return this;
    }

    //    @Override
//    public EntityQueryable<T1Proxy, T1> where(boolean condition, SQLExpression2<ProxyFilter, T1Proxy> whereExpression) {
//        if (condition) {
//            entityQueryable.where(wherePredicate -> {
//                whereExpression.apply(new ProxyFilterImpl(wherePredicate.getFilter()), get1Proxy());
//            });
//        }
//        return this;
//    }

    @Override
    public EntityQueryable<T1Proxy, T1> where(boolean condition, SQLActionExpression1<T1Proxy> whereExpression) {
        if (condition) {
            clientQueryable.where(wherePredicate -> {
                t1Proxy.getEntitySQLContext()._where(wherePredicate.getFilter(), () -> {
                    whereExpression.apply(t1Proxy);
                });
            });
        }
        return this;
    }

//    @Override
//    public EntityQueryable<T1Proxy, T1> where1(SQLExpression1<T1Proxy> whereExpression) {
//        entityQueryable.where(wherePredicate -> {
//            t1Proxy.getCurrentEntitySQLContext().accept(new PredicateEntityExpressionAccept(wherePredicate.getFilter()),()->{
//                whereExpression.apply(t1Proxy);
//            });
//        });
//        return this;
//    }

    @Override
    public EntityQueryable<T1Proxy, T1> whereById(boolean condition, Object id) {
        if (condition) {
            clientQueryable.whereById(id);
        }
        return this;
    }

    @Override
    public <TProperty> EntityQueryable<T1Proxy, T1> whereByIds(boolean condition, Collection<TProperty> ids) {

        if (condition) {
            clientQueryable.whereByIds(ids);
        }
        return this;
    }

    @Override
    public EntityQueryable<T1Proxy, T1> whereObject(boolean condition, Object object) {
        if (condition) {
            clientQueryable.whereObject(object);
        }
        return this;
    }

    @Override
    public <TRProxy extends ProxyEntity<TRProxy, TR> & SQLGroupByExpression, TR> EntityQueryable<TRProxy, TR> groupBy(SQLFuncExpression1<T1Proxy, SQLFuncExpression1<T1Proxy, TRProxy>> selectExpression) {

        SQLFuncExpression1<T1Proxy, TRProxy> keysExpression = selectExpression.apply(get1Proxy());
        Objects.requireNonNull(keysExpression, "groupBy result expression is null");
        TRProxy grouping1Proxy = keysExpression.apply(get1Proxy());
        Objects.requireNonNull(grouping1Proxy, "groupBy result is null");
        EntityQueryExpressionBuilder sqlEntityExpressionBuilder = clientQueryable.getSQLEntityExpressionBuilder();
        if (EasySQLSegmentUtil.isNotEmpty(sqlEntityExpressionBuilder.getGroup()) && EasySQLSegmentUtil.isEmpty(sqlEntityExpressionBuilder.getProjects())) {
            throw new EasyQueryInvalidOperationException("ENG:The [select] statement should be used between two consecutive [groupBy] statements to determine the query results of the preceding [groupBy].CN:连续两个[groupBy]之间应该使用[select]来确定前一次[groupBy]的查询结果");
        }
        clientQueryable.groupBy(groupBySelector -> {
            grouping1Proxy.accept(groupBySelector.getGroupSelector());
        });
        TRProxy groupProxy = grouping1Proxy.create(null, t1Proxy.getEntitySQLContext());
        EasyClientQueryable<TR> groupQueryable = new EasyClientQueryable<>(grouping1Proxy.getEntityClass(), clientQueryable.getSQLEntityExpressionBuilder());
        return new EasyEntityQueryable<>(groupProxy, groupQueryable);
    }

    @Override
    public EntityQueryable<T1Proxy, T1> having(boolean condition, SQLActionExpression1<T1Proxy> aggregateFilterSQLExpression) {
        if (condition) {
            clientQueryable.having(whereAggregatePredicate -> {
                get1Proxy().getEntitySQLContext()._having(whereAggregatePredicate.getAggregateFilter(), () -> {
                    aggregateFilterSQLExpression.apply(t1Proxy);
                });

            });
        }
        return this;
    }

    @Override
    public EntityQueryable<T1Proxy, T1> orderBy(boolean condition, SQLActionExpression1<T1Proxy> selectExpression) {
        if (condition) {
            clientQueryable.orderBy(columnSelector -> {
                get1Proxy().getEntitySQLContext()._orderBy(columnSelector.getOrderSelector(), () -> {
                    selectExpression.apply(t1Proxy);
                });
            }, false);
        }
        return this;
    }

    @Override
    public EntityQueryable<T1Proxy, T1> orderByObject(boolean condition, ObjectSort objectSort) {
        if (condition) {
            clientQueryable.orderByObject(objectSort);
        }
        return this;
    }

    @NotNull
    @Override
    public EntityQueryable<T1Proxy, T1> distinct(boolean condition) {
        if (condition) {
            clientQueryable.distinct();
        }
        return this;
    }

    @NotNull
    @Override
    public EntityQueryable<T1Proxy, T1> limit(boolean condition, long offset, long rows) {
        if (condition) {
            clientQueryable.limit(offset, rows);
        }
        return this;
    }

    @NotNull
    @Override
    public <TResult> EasyPageResult<TResult> toPageResult(@NotNull Class<TResult> tResultClass, long pageIndex, long pageSize, long pageTotal) {
        return clientQueryable.toPageResult(tResultClass, pageIndex, pageSize, pageTotal);
    }

    @NotNull
    @Override
    public <TResult> EasyPageResult<TResult> toShardingPageResult(Class<TResult> tResultClass, long pageIndex, long pageSize, List<Long> totalLines) {
        return clientQueryable.toShardingPageResult(tResultClass, pageIndex, pageSize, totalLines);
    }

    @NotNull
    @Override
    public EntityQueryExpressionBuilder getSQLEntityExpressionBuilder() {
        return clientQueryable.getSQLEntityExpressionBuilder();
    }


    @Override
    public <T2Proxy extends ProxyEntity<T2Proxy, T2>, T2 extends ProxyEntityAvailable<T2, T2Proxy>> EntityQueryable2<T1Proxy, T1, T2Proxy, T2> leftJoin(Class<T2> joinClass, SQLActionExpression2<T1Proxy, T2Proxy> on) {
        T2Proxy t2Proxy = EntityQueryProxyManager.create(joinClass);
        return leftJoin(t2Proxy, on);
    }

    @Override
    public <T2Proxy extends ProxyEntity<T2Proxy, T2>, T2> EntityQueryable2<T1Proxy, T1, T2Proxy, T2> leftJoin(T2Proxy t2Proxy, SQLActionExpression2<T1Proxy, T2Proxy> onExpression) {
        return new LeftJoinExpressionJoiner2<>(t1Proxy, this.clientQueryable).join(t2Proxy, onExpression);
    }

    @Override
    public <T2Proxy extends ProxyEntity<T2Proxy, T2>, T2> EntityQueryable2<T1Proxy, T1, T2Proxy, T2> leftJoin(EntityQueryable<T2Proxy, T2> joinQueryable, SQLActionExpression2<T1Proxy, T2Proxy> onExpression) {
        return new LeftJoinExpressionJoiner2<>(t1Proxy, this.clientQueryable).join(joinQueryable, onExpression);
    }

    @Override
    public <T2Proxy extends ProxyEntity<T2Proxy, T2>, T2 extends ProxyEntityAvailable<T2, T2Proxy>> EntityQueryable2<T1Proxy, T1, T2Proxy, T2> rightJoin(Class<T2> joinClass, SQLActionExpression2<T1Proxy, T2Proxy> on) {
        T2Proxy t2Proxy = EntityQueryProxyManager.create(joinClass);
        return rightJoin(t2Proxy, on);
    }

    @Override
    public <T2Proxy extends ProxyEntity<T2Proxy, T2>, T2> EntityQueryable2<T1Proxy, T1, T2Proxy, T2> rightJoin(T2Proxy t2Proxy, SQLActionExpression2<T1Proxy, T2Proxy> onExpression) {
        return new RightJoinExpressionJoiner2<>(t1Proxy, this.clientQueryable).join(t2Proxy, onExpression);
    }

    @Override
    public <T2Proxy extends ProxyEntity<T2Proxy, T2>, T2> EntityQueryable2<T1Proxy, T1, T2Proxy, T2> rightJoin(EntityQueryable<T2Proxy, T2> joinQueryable, SQLActionExpression2<T1Proxy, T2Proxy> onExpression) {
        return new RightJoinExpressionJoiner2<>(t1Proxy, this.clientQueryable).join(joinQueryable, onExpression);
    }

    @Override
    public <T2Proxy extends ProxyEntity<T2Proxy, T2>, T2 extends ProxyEntityAvailable<T2, T2Proxy>> EntityQueryable2<T1Proxy, T1, T2Proxy, T2> innerJoin(Class<T2> joinClass, SQLActionExpression2<T1Proxy, T2Proxy> on) {
        T2Proxy t2Proxy = EntityQueryProxyManager.create(joinClass);
        return innerJoin(t2Proxy, on);
    }

    @Override
    public <T2Proxy extends ProxyEntity<T2Proxy, T2>, T2> EntityQueryable2<T1Proxy, T1, T2Proxy, T2> innerJoin(T2Proxy t2Proxy, SQLActionExpression2<T1Proxy, T2Proxy> onExpression) {
        return new InnerJoinExpressionJoiner2<>(t1Proxy, this.clientQueryable).join(t2Proxy, onExpression);
    }

    @Override
    public <T2Proxy extends ProxyEntity<T2Proxy, T2>, T2> EntityQueryable2<T1Proxy, T1, T2Proxy, T2> innerJoin(EntityQueryable<T2Proxy, T2> joinQueryable, SQLActionExpression2<T1Proxy, T2Proxy> onExpression) {
        return new InnerJoinExpressionJoiner2<>(t1Proxy, this.clientQueryable).join(joinQueryable, onExpression);
    }

    @Override
    public <T2Proxy extends ProxyEntity<T2Proxy, T2>, T2 extends ProxyEntityAvailable<T2, T2Proxy>> EntityQueryable2<T1Proxy, T1, T2Proxy, T2> crossJoin(Class<T2> joinClass, SQLActionExpression2<T1Proxy, T2Proxy> on) {
        T2Proxy t2Proxy = EntityQueryProxyManager.create(joinClass);
        return crossJoin(t2Proxy, on);
    }

    @Override
    public <T2Proxy extends ProxyEntity<T2Proxy, T2>, T2> EntityQueryable2<T1Proxy, T1, T2Proxy, T2> crossJoin(T2Proxy t2Proxy, SQLActionExpression2<T1Proxy, T2Proxy> onExpression) {
        return new CrossJoinExpressionJoiner2<>(t1Proxy, this.clientQueryable).join(t2Proxy, onExpression);
    }

    @Override
    public <T2Proxy extends ProxyEntity<T2Proxy, T2>, T2> EntityQueryable2<T1Proxy, T1, T2Proxy, T2> crossJoin(EntityQueryable<T2Proxy, T2> joinQueryable, SQLActionExpression2<T1Proxy, T2Proxy> onExpression) {
        return new CrossJoinExpressionJoiner2<>(t1Proxy, this.clientQueryable).join(joinQueryable, onExpression);
    }

    @Override
    public EntityQueryable<T1Proxy, T1> union(Collection<EntityQueryable<T1Proxy, T1>> unionQueries) {

        if (EasyCollectionUtil.isEmpty(unionQueries)) {
            return this;
        }
        ClientQueryable<T1> unionQueryable = clientQueryable.union(EasyCollectionUtil.select(unionQueries, (queryable, i) -> queryable.getClientQueryable()));
        return new EasyEntityQueryable<>(t1Proxy, unionQueryable);
    }

    @Override
    public EntityQueryable<T1Proxy, T1> unionAll(Collection<EntityQueryable<T1Proxy, T1>> unionQueries) {
        if (EasyCollectionUtil.isEmpty(unionQueries)) {
            return this;
        }
        ClientQueryable<T1> unionQueryable = clientQueryable.unionAll(EasyCollectionUtil.select(unionQueries, (queryable, i) -> queryable.getClientQueryable()));
        return new EasyEntityQueryable<>(t1Proxy, unionQueryable);
    }

    @Override
    public EntityQueryable<T1Proxy, T1> useLogicDelete(boolean enable) {
        clientQueryable.useLogicDelete(enable);
        return this;
    }

    @Override
    public EntityQueryable<T1Proxy, T1> noInterceptor() {
        clientQueryable.noInterceptor();
        return this;
    }

    @Override
    public EntityQueryable<T1Proxy, T1> useInterceptor(String name) {
        clientQueryable.useInterceptor(name);
        return this;
    }

    @Override
    public EntityQueryable<T1Proxy, T1> noInterceptor(String name) {
        clientQueryable.noInterceptor(name);
        return this;
    }

    @Override
    public EntityQueryable<T1Proxy, T1> useInterceptor() {
        clientQueryable.useInterceptor();
        return this;
    }

    @NotNull
    @Override
    public EntityQueryable<T1Proxy, T1> asTracking() {
        clientQueryable.asTracking();
        return this;
    }

    @NotNull
    @Override
    public EntityQueryable<T1Proxy, T1> asNoTracking() {
        clientQueryable.asNoTracking();
        return this;
    }

    @Override
    public EntityQueryable<T1Proxy, T1> asTable(Function<String, String> tableNameAs) {
        clientQueryable.asTable(tableNameAs);
        return this;
    }

    @Override
    public EntityQueryable<T1Proxy, T1> asSchema(Function<String, String> schemaAs) {
        clientQueryable.asSchema(schemaAs);
        return this;
    }

    @Override
    public EntityQueryable<T1Proxy, T1> asAlias(String alias) {
        clientQueryable.asAlias(alias);
        return this;
    }

    @Override
    public EntityQueryable<T1Proxy, T1> asTableLink(Function<String, String> linkAs) {
        clientQueryable.asTableLink(linkAs);
        return this;
    }

    @Override
    public EntityQueryable<T1Proxy, T1> asTableSegment(BiFunction<String, String, String> segmentAs) {
        clientQueryable.asTableSegment(segmentAs);
        return this;
    }

    @NotNull
    @Override
    public EntityQueryable<T1Proxy, T1> useShardingConfigure(int maxShardingQueryLimit, ConnectionModeEnum connectionMode) {
        clientQueryable.useShardingConfigure(maxShardingQueryLimit, connectionMode);
        return this;
    }

    @NotNull
    @Override
    public EntityQueryable<T1Proxy, T1> useMaxShardingQueryLimit(int maxShardingQueryLimit) {
        clientQueryable.useMaxShardingQueryLimit(maxShardingQueryLimit);
        return this;
    }

    @NotNull
    @Override
    public EntityQueryable<T1Proxy, T1> useConnectionMode(ConnectionModeEnum connectionMode) {
        clientQueryable.useConnectionMode(connectionMode);
        return this;
    }

//    @Override
//    public EntityQueryable<T1Proxy, T1> conditionConfigure(ConditionAccepter conditionAccepter) {
//        entityQueryable.conditionConfigure(conditionAccepter);
//        return this;
//    }

    @Override
    public EntityQueryable<T1Proxy, T1> filterConfigure(ValueFilter valueFilter) {
        clientQueryable.filterConfigure(valueFilter);
        return this;
    }

    @Override
    public EntityQueryable<T1Proxy, T1> tableLogicDelete(Supplier<Boolean> tableLogicDel) {
        clientQueryable.tableLogicDelete(tableLogicDel);
        return this;
    }

    @Override
    public EntityQueryable<T1Proxy, T1> configure(SQLActionExpression1<ContextConfigurer> configurer) {
        clientQueryable.configure(configurer);
        return this;
    }

    @Override
    public <TRProxy extends ProxyEntity<TRProxy, TR>, TR> List<TR> toList(SQLFuncExpression1<T1Proxy, TRProxy> fetchResultExpression) {
        TRProxy resultProxy = fetchResultExpression.apply(get1Proxy());
        String navValue = EasyProxyUtil.getNavValue(resultProxy);
        if (resultProxy.getEntitySQLContext() instanceof FlatEntitySQLContext) {
            return new EasySelectFlatQueryable<>(clientQueryable, navValue, resultProxy).toList();
        } else {
            return this.selectColumn(s -> {
                TRProxy columnProxy = fetchResultExpression.apply(s);
                if (columnProxy instanceof PropTypeColumn) {
                    return (PropTypeColumn<TR>) columnProxy;
                }
                throw new EasyQueryInvalidOperationException("column proxy must be PropTypeColumn");
            }).toList();
        }
    }

    @Override
    public EntityQueryable<T1Proxy, T1> thisConfigure(SQLFuncExpression1<EntityQueryable<T1Proxy, T1>, EntityQueryable<T1Proxy, T1>> thisConfigureExpression) {
        return thisConfigureExpression.apply(this);
    }
    //
//    private <TRProxy extends ProxyEntity<TRProxy, TR>, TR> String getNavValue(TRProxy resultProxy) {
//         String navValue = resultProxy.getNavValue();
//        if(navValue==null){
//            EntitySQLContext entitySQLContext = resultProxy.getEntitySQLContext();
//            if(entitySQLContext instanceof FlatEntitySQLContext){
//                FlatEntitySQLContext flatEntitySQLContext = (FlatEntitySQLContext) entitySQLContext;
//                navValue = flatEntitySQLContext.getNavValue();
//            }
//        }
//        return navValue;
//    }
}
