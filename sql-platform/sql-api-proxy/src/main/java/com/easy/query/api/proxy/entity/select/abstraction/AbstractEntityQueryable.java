package com.easy.query.api.proxy.entity.select.abstraction;

import com.easy.query.api.proxy.base.ListProxy;
import com.easy.query.api.proxy.entity.EntityQueryProxyManager;
import com.easy.query.api.proxy.entity.select.EntityQueryable;
import com.easy.query.api.proxy.entity.select.EntityQueryable2;
import com.easy.query.api.proxy.entity.select.impl.EasyEntityQueryable;
import com.easy.query.api.proxy.entity.select.impl.EasyEntityQueryable2;
import com.easy.query.api.proxy.entity.select.impl.EasySelectManyQueryable;
import com.easy.query.api.proxy.sql.ProxyFilter;
import com.easy.query.api.proxy.sql.ProxySelector;
import com.easy.query.api.proxy.sql.impl.ProxyFilterImpl;
import com.easy.query.api.proxy.sql.impl.ProxySelectorImpl;
import com.easy.query.core.api.dynamic.sort.ObjectSort;
import com.easy.query.core.api.pagination.EasyPageResult;
import com.easy.query.core.basic.api.select.ClientQueryable;
import com.easy.query.core.basic.api.select.ClientQueryable2;
import com.easy.query.core.basic.api.select.Query;
import com.easy.query.core.basic.api.select.impl.EasyClientQueryable;
import com.easy.query.core.basic.jdbc.executor.internal.enumerable.DraftResult;
import com.easy.query.core.basic.jdbc.executor.internal.enumerable.JdbcStreamResult;
import com.easy.query.core.basic.jdbc.parameter.ToSQLContext;
import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.enums.sharding.ConnectionModeEnum;
import com.easy.query.core.exception.EasyQueryInvalidOperationException;
import com.easy.query.core.expression.builder.core.ValueFilter;
import com.easy.query.core.expression.lambda.SQLConsumer;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.lambda.SQLExpression2;
import com.easy.query.core.expression.lambda.SQLFuncExpression1;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.parser.core.base.tree.TreeCTEConfigurer;
import com.easy.query.core.expression.segment.ColumnSegment;
import com.easy.query.core.expression.sql.builder.EntityQueryExpressionBuilder;
import com.easy.query.core.proxy.PropTypeColumn;
import com.easy.query.core.proxy.ProxyEntity;
import com.easy.query.core.proxy.ProxyEntityAvailable;
import com.easy.query.core.proxy.SQLColumn;
import com.easy.query.core.proxy.SQLGroupByExpression;
import com.easy.query.core.proxy.SQLSelectAsExpression;
import com.easy.query.core.proxy.SQLSelectExpression;
import com.easy.query.core.proxy.columns.SQLQueryable;
import com.easy.query.core.common.ValueHolder;
import com.easy.query.core.proxy.core.draft.DraftFetcher;
import com.easy.query.core.proxy.core.draft.proxy.DraftProxy;
import com.easy.query.core.util.EasyCollectionUtil;
import com.easy.query.core.util.EasyObjectUtil;
import com.easy.query.core.util.EasySQLSegmentUtil;

import java.math.BigDecimal;
import java.sql.Statement;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Supplier;

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

    @Override
    public QueryRuntimeContext getRuntimeContext() {
        return runtimeContext;
    }

    @Override
    public ClientQueryable<T1> getClientQueryable() {
        return clientQueryable;
    }

    @Override
    public EntityQueryable<T1Proxy, T1> getQueryable() {
        return this;
    }

    @Override
    public Class<T1> queryClass() {
        return clientQueryable.queryClass();
    }

    @Override
    public long count() {
        return clientQueryable.count();
    }

    @Override
    public long countDistinct(SQLExpression2<ProxySelector, T1Proxy> selectExpression) {
        return getClientQueryable().countDistinct(selector -> {
            selectExpression.apply(new ProxySelectorImpl(selector.getSelector()), get1Proxy());
        });
    }

    @Override
    public boolean any() {
        return getClientQueryable().any();
    }

    @Override
    public boolean all(SQLExpression2<ProxyFilter, T1Proxy> whereExpression) {
        return getClientQueryable().all(where -> {
            whereExpression.apply(new ProxyFilterImpl(where.getFilter()), get1Proxy());
        });
    }


    @Override
    public <TR> TR firstOrNull(Class<TR> resultClass) {
        return getClientQueryable().firstOrNull(resultClass);
    }

    @Override
    public <TR> TR singleOrNull(Class<TR> resultClass) {
        return getClientQueryable().singleOrNull(resultClass);
    }

    @Override
    public T1 findOrNull(Object id) {
        return getClientQueryable().findOrNull(id);
    }


    @Override
    public <TR> TR firstNotNull(Class<TR> resultClass, String msg, String code) {
        return getClientQueryable().firstNotNull(resultClass, msg, code);
    }

    @Override
    public <TR> TR firstNotNull(Class<TR> resultClass, Supplier<RuntimeException> throwFunc) {
        return getClientQueryable().firstNotNull(resultClass, throwFunc);
    }

    @Override
    public <TR> TR singleNotNull(Class<TR> resultClass, Supplier<RuntimeException> throwFunc) {
        return getClientQueryable().singleNotNull(resultClass, throwFunc);
    }

    @Override
    public T1 findNotNull(Object id, Supplier<RuntimeException> throwFunc) {
        return getClientQueryable().findNotNull(id, throwFunc);
    }

    @Override
    public Map<String, Object> toMap() {
        return getClientQueryable().toMap();
    }

    @Override
    public List<Map<String, Object>> toMaps() {
        return getClientQueryable().toMaps();
    }

    @Override
    public <TRProxy extends ProxyEntity<TRProxy, TR>, TR> List<TR> toList(TRProxy trProxy) {
        return toList(trProxy.getEntityClass());
    }

    @Override
    public <TR> List<TR> toList(Class<TR> resultClass) {
        return clientQueryable.toList(resultClass);
    }

    @Override
    public <TR> JdbcStreamResult<TR> toStreamResult(Class<TR> resultClass, SQLConsumer<Statement> configurer) {
        return clientQueryable.toStreamResult(resultClass, configurer);
    }

    @Override
    public <TR> String toSQL(Class<TR> resultClass, ToSQLContext toSQLContext) {
        return clientQueryable.toSQL(resultClass, toSQLContext);
    }

    @Override
    public <TNumber extends Number> Query<TNumber> selectCount(Class<TNumber> numberClass) {
        return getClientQueryable().selectCount(numberClass);
    }

    @Override
    public <TMember extends Number> Query<TMember> selectSum(SQLFuncExpression1<T1Proxy, SQLColumn<T1Proxy, TMember>> columnSelector) {
        SQLColumn<T1Proxy, TMember> sqlColumn = columnSelector.apply(get1Proxy());
        return getClientQueryable().selectSum(EasyObjectUtil.typeCastNullable(sqlColumn.getPropertyType()), sqlColumn.getValue());
    }

    @Override
    public <TMember extends Number> Query<BigDecimal> selectAvg(SQLFuncExpression1<T1Proxy, SQLColumn<T1Proxy, TMember>> columnSelector) {
        SQLColumn<T1Proxy, TMember> sqlColumn = columnSelector.apply(get1Proxy());
        return getClientQueryable().selectAvg(sqlColumn.getValue());
    }

    @Override
    public <TMember> Query<TMember> selectMax(SQLFuncExpression1<T1Proxy, SQLColumn<T1Proxy, TMember>> columnSelector) {
        SQLColumn<T1Proxy, TMember> sqlColumn = columnSelector.apply(get1Proxy());
        return getClientQueryable().selectMax(EasyObjectUtil.typeCastNullable(sqlColumn.getPropertyType()), sqlColumn.getValue());
    }

    @Override
    public <TMember> Query<TMember> selectMin(SQLFuncExpression1<T1Proxy, SQLColumn<T1Proxy, TMember>> columnSelector) {
        SQLColumn<T1Proxy, TMember> sqlColumn = columnSelector.apply(get1Proxy());
        return getClientQueryable().selectMin(EasyObjectUtil.typeCastNullable(sqlColumn.getPropertyType()), sqlColumn.getValue());
    }

    @Override
    public EntityQueryable<T1Proxy, T1> fetchBy(SQLFuncExpression1<T1Proxy, SQLSelectExpression> selectExpression) {
        ClientQueryable<T1> select = getClientQueryable().select(get1Proxy().getEntityClass(), columnSelector -> {
            SQLSelectExpression sqlSelect = selectExpression.apply(get1Proxy());
            sqlSelect.accept(columnSelector.getAsSelector());
        });
        return new EasyEntityQueryable<>(get1Proxy(), select);
    }

    @Override
    public <TRProxy extends ProxyEntity<TRProxy, TR>, TR> EntityQueryable<TRProxy, TR> select(SQLFuncExpression1<T1Proxy, TRProxy> selectExpression) {
        TRProxy resultProxy = selectExpression.apply(get1Proxy());
        Objects.requireNonNull(resultProxy, "select null result class");
        if(resultProxy instanceof ListProxy){
            ListProxy<TRProxy, TR> listProxy = (ListProxy<TRProxy, TR>) resultProxy;
            SQLQueryable<TRProxy, TR> sqlQueryable = listProxy.getSqlQueryable();
            Objects.requireNonNull(sqlQueryable, "select columns null result class");
            EntityQueryable<ListProxy<TRProxy,TR>, List<TR>> listProxyListEntityQueryable = new EasySelectManyQueryable<>(getClientQueryable(), listProxy, sqlQueryable.getNavValue());
            return EasyObjectUtil.typeCastNullable(listProxyListEntityQueryable);
//            return new EasyColumnsQueryable<>(getClientQueryable(), sqlQueryable.getNavValue());
        }

        SQLSelectAsExpression selectAsExpression = resultProxy.getEntitySQLContext().getSelectAsExpression();
        if (selectAsExpression == null) {//全属性映射
            TableAvailable tableOrNull = resultProxy.getTableOrNull();
            if (tableOrNull == null) {
                ClientQueryable<TR> select = getClientQueryable().select(resultProxy.getEntityClass());
                setDraftPropTypes(select, resultProxy);
                return new EasyEntityQueryable<>(resultProxy, select);
            } else {
                ClientQueryable<TR> select = getClientQueryable().select(resultProxy.getEntityClass(), columnAsSelector -> {
                    columnAsSelector.getAsSelector().columnAll(tableOrNull);
                });
                setDraftPropTypes(select, resultProxy);
                return new EasyEntityQueryable<>(resultProxy, select);
            }
        } else {
            ClientQueryable<TR> select = getClientQueryable().select(resultProxy.getEntityClass(), columnAsSelector -> {
                selectAsExpression.accept(columnAsSelector.getAsSelector());
            });
            setDraftPropTypes(select, resultProxy);
            return new EasyEntityQueryable<>(resultProxy, select);
        }
    }

    private <TR, TRProxy> void setDraftPropTypes(ClientQueryable<TR> select, TRProxy trProxy) {
        if (trProxy instanceof DraftProxy) {
            DraftProxy draftProxy = (DraftProxy) trProxy;
            select.getSQLEntityExpressionBuilder().getExpressionContext().setDraftPropTypes(draftProxy.getDraftPropTypes());
        }
    }

    @Override
    public <TR> Query<TR> selectColumn(SQLFuncExpression1<T1Proxy, PropTypeColumn<TR>> selectExpression) {
        PropTypeColumn<TR> column = selectExpression.apply(get1Proxy());
        Objects.requireNonNull(column, "select column null result class");

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
    public <TPropertyProxy extends ProxyEntity<TPropertyProxy, TProperty>, TProperty extends ProxyEntityAvailable<TProperty, TPropertyProxy>> EntityQueryable<T1Proxy, T1> include(SQLFuncExpression1<T1Proxy, TPropertyProxy> navigateIncludeSQLExpression, SQLExpression1<EntityQueryable<TPropertyProxy, TProperty>> includeAdapterExpression, Integer groupSize) {
        T1Proxy proxy = getQueryable().get1Proxy();
        ValueHolder<TPropertyProxy> valueHolder = new ValueHolder<>();
        proxy.getEntitySQLContext()._include(() -> {
            TPropertyProxy navigateColumn = navigateIncludeSQLExpression.apply(proxy);
            valueHolder.setValue(navigateColumn);
        });

//        getSQLEntityExpressionBuilder().removeRelationEntityTableExpression(new RelationTableKey(proxy.getEntityClass(),navigateColumn.getEntityClass()));
        return include0(valueHolder.getValue(), includeAdapterExpression, groupSize);
    }

    @Override
    public <TPropertyProxy extends ProxyEntity<TPropertyProxy, TProperty>, TProperty extends ProxyEntityAvailable<TProperty, TPropertyProxy>> EntityQueryable<T1Proxy, T1> includes(SQLFuncExpression1<T1Proxy, SQLQueryable<TPropertyProxy, TProperty>> navigateIncludeSQLExpression, SQLExpression1<EntityQueryable<TPropertyProxy, TProperty>> includeAdapterExpression, Integer groupSize) {
        T1Proxy proxy = getQueryable().get1Proxy();
        ValueHolder<TPropertyProxy> valueHolder = new ValueHolder<>();
        proxy.getEntitySQLContext()._include(() -> {
            SQLQueryable<TPropertyProxy, TProperty> navigateColumnQueryable = navigateIncludeSQLExpression.apply(proxy);
            TPropertyProxy navigateColumn = navigateColumnQueryable.getQueryable().get1Proxy();
            valueHolder.setValue(navigateColumn);
        });

        return include0(valueHolder.getValue(), includeAdapterExpression, groupSize);
    }

    private <TPropertyProxy extends ProxyEntity<TPropertyProxy, TProperty>, TProperty extends ProxyEntityAvailable<TProperty, TPropertyProxy>> EntityQueryable<T1Proxy, T1> include0(TPropertyProxy navigateColumn, SQLExpression1<EntityQueryable<TPropertyProxy, TProperty>> includeAdapterExpression, Integer groupSize) {

        Objects.requireNonNull(navigateColumn.getNavValue(), "include [navValue] is null");
        getClientQueryable().<TProperty>include(navigateInclude -> {
            ClientQueryable<TProperty> clientQueryable = navigateInclude.with(navigateColumn.getNavValue(), groupSize);
            TPropertyProxy tPropertyProxy = EntityQueryProxyManager.create(clientQueryable.queryClass());
            EasyEntityQueryable<TPropertyProxy, TProperty> entityQueryable = new EasyEntityQueryable<>(tPropertyProxy, clientQueryable);
            includeAdapterExpression.apply(entityQueryable);
            return entityQueryable.getClientQueryable();
        });

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
    public EntityQueryable<T1Proxy, T1> asTreeCTE(SQLFuncExpression1<T1Proxy, SQLColumn<T1Proxy, ?>> codePropertyExpression, SQLFuncExpression1<T1Proxy, SQLColumn<T1Proxy, ?>> parentCodePropertyExpression, SQLExpression1<TreeCTEConfigurer> treeExpression) {
        ClientQueryable<T1> clientQueryable = getClientQueryable().asTreeCTE(codePropertyExpression.apply(get1Proxy()).getValue(), parentCodePropertyExpression.apply(get1Proxy()).getValue(), treeExpression);
        return new EasyEntityQueryable<>(get1Proxy(), clientQueryable);
    }

    @Override
    public <TRProxy extends ProxyEntity<TRProxy, TR>, TR extends ProxyEntityAvailable<TR, TRProxy> & DraftResult> EntityQueryable<TRProxy, TR> selectDraft(SQLFuncExpression1<T1Proxy, DraftFetcher<TR, TRProxy>> selectExpression) {
        DraftFetcher<TR, TRProxy> draftFetcher = selectExpression.apply(get1Proxy());
        ClientQueryable<TR> select = clientQueryable.select(EasyObjectUtil.typeCastNullable(draftFetcher.getDraft().getClass()), columnAsSelector -> {
            draftFetcher.accept(columnAsSelector.getAsSelector());
        });
        TRProxy draftProxy = draftFetcher.getDraftProxy();
        select.getSQLEntityExpressionBuilder().getExpressionContext().setDraftPropTypes(draftFetcher.getDraftPropTypes());
        return new EasyEntityQueryable<>(draftProxy, select);
    }

    @Override
    public EntityQueryable<T1Proxy, T1> select(String columns) {
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
    public EntityQueryable<T1Proxy, T1> where(boolean condition, SQLExpression1<T1Proxy> whereExpression) {
        if (condition) {
            clientQueryable.where(wherePredicate -> {
                t1Proxy.getEntitySQLContext()._where(wherePredicate.getFilter(), () -> {
                    whereExpression.apply(t1Proxy);
                });
//                t1Proxy.__setFilter(wherePredicate.getFilter());
//                whereExpression.apply(t1Proxy);
//                t1Proxy.__setFilter(null);
//                SQLPredicateExpression sqlPredicate = whereExpression.apply(t1Proxy);
//                sqlPredicate.accept(wherePredicate.getFilter());
            });
        }
        return this;
    }

//    @Override
//    public EntityQueryable<T1Proxy, T1> where1(SQLExpression1<T1Proxy> whereExpression) {
//        entityQueryable.where(wherePredicate -> {
//            t1Proxy.getEntitySQLContext().accept(new PredicateEntityExpressionAccept(wherePredicate.getFilter()),()->{
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
    public EntityQueryable<T1Proxy, T1> having(boolean condition, SQLExpression1<T1Proxy> aggregateFilterSQLExpression) {
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
    public EntityQueryable<T1Proxy, T1> orderBy(boolean condition, SQLExpression1<T1Proxy> selectExpression) {
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

    @Override
    public EntityQueryable<T1Proxy, T1> distinct(boolean condition) {
        if (condition) {
            clientQueryable.distinct();
        }
        return this;
    }

    @Override
    public EntityQueryable<T1Proxy, T1> limit(boolean condition, long offset, long rows) {
        if (condition) {
            clientQueryable.limit(offset, rows);
        }
        return this;
    }

    @Override
    public <TResult> EasyPageResult<TResult> toPageResult(Class<TResult> tResultClass, long pageIndex, long pageSize, long pageTotal) {
        return clientQueryable.toPageResult(tResultClass, pageIndex, pageSize, pageTotal);
    }

    @Override
    public <TResult> EasyPageResult<TResult> toShardingPageResult(Class<TResult> tResultClass, long pageIndex, long pageSize, List<Long> totalLines) {
        return clientQueryable.toShardingPageResult(tResultClass, pageIndex, pageSize, totalLines);
    }

    @Override
    public EntityQueryExpressionBuilder getSQLEntityExpressionBuilder() {
        return clientQueryable.getSQLEntityExpressionBuilder();
    }

//    @Override
//    public <T2Proxy extends ProxyEntity<T2Proxy, T2>, T2> ProxyQueryable2<T1Proxy, T1, T2Proxy, T2> leftJoin(T2Proxy joinProxy, SQLExpression3<ProxyFilter, T1Proxy, T2Proxy> on) {
//        ClientQueryable2<T1, T2> entityQueryable2 = entityQueryable.leftJoin(joinProxy.getEntityClass(), (t, t1) -> {
//            on.apply(new ProxyFilterImpl(t.getFilter()), t1Proxy, joinProxy.create(t1.getTable()));
//        });
//        return new EasyProxyQueryable2<>(t1Proxy, joinProxy, entityQueryable2);
//    }
//
//    @Override
//    public <T2Proxy extends ProxyEntity<T2Proxy, T2>, T2> ProxyQueryable2<T1Proxy, T1, T2Proxy, T2> leftJoin(ProxyQueryable<T2Proxy, T2> joinQueryable, SQLExpression3<ProxyFilter, T1Proxy, T2Proxy> on) {
//        ClientQueryable2<T1, T2> entityQueryable2 = entityQueryable.leftJoin(joinQueryable.getClientQueryable(), (t, t1) -> {
//            on.apply(new ProxyFilterImpl(t.getFilter()), t1Proxy, joinQueryable.get1Proxy());
//        });
//        return new EasyProxyQueryable2<>(t1Proxy, joinQueryable.get1Proxy(), entityQueryable2);
//    }
//
//    @Override
//    public <T2Proxy extends ProxyEntity<T2Proxy, T2>, T2> ProxyQueryable2<T1Proxy, T1, T2Proxy, T2> rightJoin(T2Proxy joinProxy, SQLExpression3<ProxyFilter, T1Proxy, T2Proxy> on) {
//
//        ClientQueryable2<T1, T2> entityQueryable2 = entityQueryable.rightJoin(joinProxy.getEntityClass(), (t, t1) -> {
//            on.apply(new ProxyFilterImpl(t.getFilter()), t1Proxy, joinProxy.create(t1.getTable()));
//        });
//        return new EasyProxyQueryable2<>(t1Proxy, joinProxy, entityQueryable2);
//    }
//
//    @Override
//    public <T2Proxy extends ProxyEntity<T2Proxy, T2>, T2> ProxyQueryable2<T1Proxy, T1, T2Proxy, T2> rightJoin(ProxyQueryable<T2Proxy, T2> joinQueryable, SQLExpression3<ProxyFilter, T1Proxy, T2Proxy> on) {
//        ClientQueryable2<T1, T2> entityQueryable2 = entityQueryable.rightJoin(joinQueryable.getClientQueryable(), (t, t1) -> {
//            on.apply(new ProxyFilterImpl(t.getFilter()), t1Proxy, joinQueryable.get1Proxy());
//        });
//        return new EasyProxyQueryable2<>(t1Proxy, joinQueryable.get1Proxy(), entityQueryable2);
//    }
//
//    @Override
//    public <T2Proxy extends ProxyEntity<T2Proxy, T2>, T2> ProxyQueryable2<T1Proxy, T1, T2Proxy, T2> innerJoin(T2Proxy joinProxy, SQLExpression3<ProxyFilter, T1Proxy, T2Proxy> on) {
//
//        ClientQueryable2<T1, T2> entityQueryable2 = entityQueryable.innerJoin(joinProxy.getEntityClass(), (t, t1) -> {
//            on.apply(new ProxyFilterImpl(t.getFilter()), t1Proxy, joinProxy.create(t1.getTable()));
//        });
//        return new EasyProxyQueryable2<>(t1Proxy, joinProxy, entityQueryable2);
//    }
//
//    @Override
//    public <T2Proxy extends ProxyEntity<T2Proxy, T2>, T2> ProxyQueryable2<T1Proxy, T1, T2Proxy, T2> innerJoin(ProxyQueryable<T2Proxy, T2> joinQueryable, SQLExpression3<ProxyFilter, T1Proxy, T2Proxy> on) {
//        ClientQueryable2<T1, T2> entityQueryable2 = entityQueryable.innerJoin(joinQueryable.getClientQueryable(), (t, t1) -> {
//            on.apply(new ProxyFilterImpl(t.getFilter()), t1Proxy, joinQueryable.get1Proxy());
//        });
//        return new EasyProxyQueryable2<>(t1Proxy, joinQueryable.get1Proxy(), entityQueryable2);
//    }


    @Override
    public <T2Proxy extends ProxyEntity<T2Proxy, T2>, T2 extends ProxyEntityAvailable<T2, T2Proxy>> EntityQueryable2<T1Proxy, T1, T2Proxy, T2> leftJoin(Class<T2> joinClass, SQLExpression2<T1Proxy, T2Proxy> on) {
        T2Proxy t2Proxy = EntityQueryProxyManager.create(joinClass);
        ClientQueryable2<T1, T2> entityQueryable2 = clientQueryable.leftJoin(joinClass, (t, t1) -> {
            t1Proxy.getEntitySQLContext()._where(t.getFilter(), () -> {
                on.apply(t1Proxy, t2Proxy.create(t1.getTable(), t1Proxy.getEntitySQLContext()));
            });
        });
        return new EasyEntityQueryable2<>(t1Proxy, t2Proxy, entityQueryable2);
    }

    @Override
    public <T2Proxy extends ProxyEntity<T2Proxy, T2>, T2> EntityQueryable2<T1Proxy, T1, T2Proxy, T2> leftJoin(EntityQueryable<T2Proxy, T2> joinQueryable, SQLExpression2<T1Proxy, T2Proxy> on) {
        ClientQueryable<T2> clientQueryable = joinQueryable.getClientQueryable();
        ClientQueryable2<T1, T2> entityQueryable2 = this.clientQueryable.leftJoin(clientQueryable, (t, t1) -> {
            t1Proxy.getEntitySQLContext()._where(t.getFilter(), () -> {
                on.apply(t1Proxy, joinQueryable.get1Proxy().create(t1.getTable(), t1Proxy.getEntitySQLContext()));
            });
        });
        return new EasyEntityQueryable2<>(t1Proxy, joinQueryable.get1Proxy(), entityQueryable2);
    }

    @Override
    public <T2Proxy extends ProxyEntity<T2Proxy, T2>, T2 extends ProxyEntityAvailable<T2, T2Proxy>> EntityQueryable2<T1Proxy, T1, T2Proxy, T2> rightJoin(Class<T2> joinClass, SQLExpression2<T1Proxy, T2Proxy> on) {
        T2Proxy t2Proxy = EntityQueryProxyManager.create(joinClass);
        ClientQueryable2<T1, T2> entityQueryable2 = clientQueryable.rightJoin(joinClass, (t, t1) -> {
            t1Proxy.getEntitySQLContext()._where(t.getFilter(), () -> {
                on.apply(t1Proxy, t2Proxy.create(t1.getTable(), t1Proxy.getEntitySQLContext()));
            });
        });
        return new EasyEntityQueryable2<>(t1Proxy, t2Proxy, entityQueryable2);
    }

    @Override
    public <T2Proxy extends ProxyEntity<T2Proxy, T2>, T2> EntityQueryable2<T1Proxy, T1, T2Proxy, T2> rightJoin(EntityQueryable<T2Proxy, T2> joinQueryable, SQLExpression2<T1Proxy, T2Proxy> on) {
        ClientQueryable<T2> clientQueryable = joinQueryable.getClientQueryable();
        ClientQueryable2<T1, T2> entityQueryable2 = this.clientQueryable.rightJoin(clientQueryable, (t, t1) -> {
            t1Proxy.getEntitySQLContext()._where(t.getFilter(), () -> {
                on.apply(t1Proxy, joinQueryable.get1Proxy().create(t1.getTable(), t1Proxy.getEntitySQLContext()));
            });
        });
        return new EasyEntityQueryable2<>(t1Proxy, joinQueryable.get1Proxy(), entityQueryable2);
    }

    @Override
    public <T2Proxy extends ProxyEntity<T2Proxy, T2>, T2 extends ProxyEntityAvailable<T2, T2Proxy>> EntityQueryable2<T1Proxy, T1, T2Proxy, T2> innerJoin(Class<T2> joinClass, SQLExpression2<T1Proxy, T2Proxy> on) {
        T2Proxy t2Proxy = EntityQueryProxyManager.create(joinClass);
        ClientQueryable2<T1, T2> entityQueryable2 = clientQueryable.innerJoin(joinClass, (t, t1) -> {
            t1Proxy.getEntitySQLContext()._where(t.getFilter(), () -> {
                on.apply(t1Proxy, t2Proxy.create(t1.getTable(), t1Proxy.getEntitySQLContext()));
            });
        });
        return new EasyEntityQueryable2<>(t1Proxy, t2Proxy, entityQueryable2);
    }

    @Override
    public <T2Proxy extends ProxyEntity<T2Proxy, T2>, T2> EntityQueryable2<T1Proxy, T1, T2Proxy, T2> innerJoin(EntityQueryable<T2Proxy, T2> joinQueryable, SQLExpression2<T1Proxy, T2Proxy> on) {
        ClientQueryable<T2> clientQueryable = joinQueryable.getClientQueryable();
        ClientQueryable2<T1, T2> entityQueryable2 = this.clientQueryable.innerJoin(clientQueryable, (t, t1) -> {
            t1Proxy.getEntitySQLContext()._where(t.getFilter(), () -> {
                on.apply(t1Proxy, joinQueryable.get1Proxy().create(t1.getTable(), t1Proxy.getEntitySQLContext()));
            });
        });
        return new EasyEntityQueryable2<>(t1Proxy, joinQueryable.get1Proxy(), entityQueryable2);
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

    @Override
    public EntityQueryable<T1Proxy, T1> asTracking() {
        clientQueryable.asTracking();
        return this;
    }

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
    public EntityQueryable<T1Proxy, T1> useShardingConfigure(int maxShardingQueryLimit, ConnectionModeEnum connectionMode) {
        clientQueryable.useShardingConfigure(maxShardingQueryLimit, connectionMode);
        return this;
    }

    @Override
    public EntityQueryable<T1Proxy, T1> useMaxShardingQueryLimit(int maxShardingQueryLimit) {
        clientQueryable.useMaxShardingQueryLimit(maxShardingQueryLimit);
        return this;
    }

    @Override
    public EntityQueryable<T1Proxy, T1> useConnectionMode(ConnectionModeEnum connectionMode) {
        clientQueryable.useConnectionMode(connectionMode);
        return this;
    }

    @Override
    public EntityQueryable<T1Proxy, T1> queryLargeColumn(boolean queryLarge) {
        clientQueryable.queryLargeColumn(queryLarge);
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

}
