package com.easy.query.api.proxy.entity.select.abstraction;

import com.easy.query.api.proxy.entity.EntityQueryProxyManager;
import com.easy.query.api.proxy.entity.select.EntityQueryable;
import com.easy.query.api.proxy.entity.select.EntityQueryable2;
import com.easy.query.api.proxy.entity.select.impl.EasyEntityQueryable;
import com.easy.query.api.proxy.entity.select.impl.EasyEntityQueryable2;
import com.easy.query.api.proxy.sql.ProxyFilter;
import com.easy.query.api.proxy.sql.ProxySelector;
import com.easy.query.api.proxy.sql.impl.ProxyFilterImpl;
import com.easy.query.api.proxy.sql.impl.ProxySelectorImpl;
import com.easy.query.core.api.dynamic.sort.ObjectSort;
import com.easy.query.core.api.pagination.EasyPageResult;
import com.easy.query.core.basic.api.select.ClientQueryable;
import com.easy.query.core.basic.api.select.ClientQueryable2;
import com.easy.query.core.basic.jdbc.executor.internal.enumerable.JdbcStreamResult;
import com.easy.query.core.basic.jdbc.parameter.ToSQLContext;
import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.enums.sharding.ConnectionModeEnum;
import com.easy.query.core.expression.builder.core.ValueFilter;
import com.easy.query.core.expression.lambda.SQLConsumer;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.lambda.SQLExpression2;
import com.easy.query.core.expression.lambda.SQLFuncExpression1;
import com.easy.query.core.expression.lambda.SQLFuncExpression2;
import com.easy.query.core.expression.segment.ColumnSegment;
import com.easy.query.core.expression.sql.builder.EntityQueryExpressionBuilder;
import com.easy.query.core.proxy.ProxyEntity;
import com.easy.query.core.proxy.ProxyEntityAvailable;
import com.easy.query.core.proxy.SQLGroupByExpression;
import com.easy.query.core.proxy.SQLSelectAsExpression;
import com.easy.query.core.proxy.SQLSelectExpression;
import com.easy.query.core.util.EasyCollectionUtil;

import java.sql.Statement;
import java.util.Collection;
import java.util.List;
import java.util.Map;
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
    protected final ClientQueryable<T1> entityQueryable;

    public AbstractEntityQueryable(T1Proxy t1Proxy, ClientQueryable<T1> entityQueryable) {
        EntityQueryExpressionBuilder sqlEntityExpressionBuilder = entityQueryable.getSQLEntityExpressionBuilder();
        this.runtimeContext = sqlEntityExpressionBuilder.getRuntimeContext();
        this.t1Proxy = t1Proxy.create(sqlEntityExpressionBuilder.getTable(0).getEntityTable(),sqlEntityExpressionBuilder.getRuntimeContext());
        this.entityQueryable = entityQueryable;
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
        return entityQueryable;
    }

    @Override
    public EntityQueryable<T1Proxy, T1> getQueryable() {
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
    public boolean all(SQLExpression2<ProxyFilter, T1Proxy> whereExpression) {
        return entityQueryable.all(where -> {
            whereExpression.apply(new ProxyFilterImpl(where.getFilter()), get1Proxy());
        });
    }


    @Override
    public <TR> TR firstOrNull(Class<TR> resultClass) {
        return entityQueryable.firstOrNull(resultClass);
    }

    @Override
    public <TR> TR singleOrNull(Class<TR> resultClass) {
        return entityQueryable.singleOrNull(resultClass);
    }

    @Override
    public <TR> TR firstNotNull(Class<TR> resultClass, String msg, String code) {
        return entityQueryable.firstNotNull(resultClass, msg, code);
    }

    @Override
    public <TR> TR firstNotNull(Class<TR> resultClass, Supplier<RuntimeException> throwFunc) {
        return entityQueryable.firstNotNull(resultClass, throwFunc);
    }

    @Override
    public <TR> TR singleNotNull(Class<TR> resultClass, Supplier<RuntimeException> throwFunc) {
        return entityQueryable.singleNotNull(resultClass, throwFunc);
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
        return entityQueryable.toStreamResult(resultClass, configurer);
    }

    @Override
    public <TR> String toSQL(Class<TR> resultClass, ToSQLContext toSQLContext) {
        return entityQueryable.toSQL(resultClass, toSQLContext);
    }

    @Override
    public EntityQueryable<T1Proxy, T1> select(SQLFuncExpression1<T1Proxy, SQLSelectExpression> selectExpression) {
        ClientQueryable<T1> select = entityQueryable.select(get1Proxy().getEntityClass(),columnSelector -> {
            SQLSelectExpression sqlSelect = selectExpression.apply(t1Proxy);
            sqlSelect.accept(columnSelector.getAsSelector());
        });
        return new EasyEntityQueryable<>(t1Proxy, select);
//        ClientQueryable<T1> select = entityQueryable.select(columnSelector -> {
//            SQLSelectExpression sqlSelect = selectExpression.apply(t1Proxy);
//            sqlSelect.accept(columnSelector.getSelector());
//        });
//        return new EasyEntityQueryable<>(t1Proxy, select);
    }

    //    @Override
//    public <TRProxy extends ProxyEntity<TRProxy, TR>, TR> ProxyQueryable<TRProxy, TR> select(TRProxy trProxy, SQLExpression2<ProxyAsSelector<TRProxy, TR>, T1Proxy> selectExpression) {
//
//        ClientQueryable<TR> select = entityQueryable.select(trProxy.getEntityClass(), columnAsSelector -> {
//            selectExpression.apply(new ProxyAsSelectorImpl<>(trProxy, columnAsSelector.getAsSelector()), get1Proxy());
//        });
//        return new EasyProxyQueryable<>(trProxy, select);
//    }
    @Override
    public <TRProxy extends ProxyEntity<TRProxy, TR>, TR extends ProxyEntityAvailable<TR, TRProxy>> EntityQueryable<TRProxy, TR> select(Class<TR> resultEntityClass) {
        ClientQueryable<TR> select = entityQueryable.select(resultEntityClass);
        TRProxy trProxy = EntityQueryProxyManager.create(resultEntityClass);
        return new EasyEntityQueryable<>(trProxy, select);
    }

    @Override
    public <TRProxy extends ProxyEntity<TRProxy, TR>, TR extends ProxyEntityAvailable<TR, TRProxy>> EntityQueryable<TRProxy, TR> select(Class<TR> resultEntityClass, SQLFuncExpression2<T1Proxy, TRProxy, SQLSelectAsExpression> selectExpression) {
        TRProxy trProxy = EntityQueryProxyManager.create(resultEntityClass);
        ClientQueryable<TR> select = entityQueryable.select(resultEntityClass, columnAsSelector -> {
            SQLSelectAsExpression sqlSelectAs = selectExpression.apply(t1Proxy, trProxy);
            sqlSelectAs.accept(columnAsSelector.getAsSelector());
        });
        return new EasyEntityQueryable<>(trProxy, select);
    }

    @Override
    public <TRProxy extends ProxyEntity<TRProxy, TR>, TR> EntityQueryable<TRProxy, TR> select(TRProxy trProxy, SQLFuncExpression1<T1Proxy, SQLSelectAsExpression> selectExpression) {
        ClientQueryable<TR> select = entityQueryable.select(trProxy.getEntityClass(), columnAsSelector -> {
            SQLSelectAsExpression sqlSelectAs = selectExpression.apply(t1Proxy);
            sqlSelectAs.accept(columnAsSelector.getAsSelector());
        });
        return new EasyEntityQueryable<>(trProxy, select);
    }

    @Override
    public EntityQueryable<T1Proxy, T1> select(String columns) {
        entityQueryable.select(columns);
        return this;
    }

    @Override
    public EntityQueryable<T1Proxy, T1> select(Collection<ColumnSegment> columnSegments, boolean clearAll) {
        entityQueryable.select(columnSegments, clearAll);
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
            entityQueryable.where(wherePredicate -> {
                t1Proxy.getEntitySQLContext()._where(wherePredicate.getFilter(),()->{
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
            entityQueryable.whereById(id);
        }
        return this;
    }

    @Override
    public <TProperty> EntityQueryable<T1Proxy, T1> whereByIds(boolean condition, Collection<TProperty> ids) {

        if (condition) {
            entityQueryable.whereByIds(ids);
        }
        return this;
    }

    @Override
    public EntityQueryable<T1Proxy, T1> whereObject(boolean condition, Object object) {
        if (condition) {
            entityQueryable.whereObject(object);
        }
        return this;
    }

//    @Override
//    public EntityQueryable<T1Proxy, T1> groupBy(boolean condition, SQLExpression2<ProxyGroupSelector, T1Proxy> selectExpression) {
//        if (condition) {
//            entityQueryable.groupBy(groupBySelector -> {
//                selectExpression.apply(new ProxyGroupSelectorImpl(groupBySelector.getGroupSelector()), t1Proxy);
//            });
//        }
//        return this;
//    }

    @Override
    public EntityQueryable<T1Proxy, T1> groupBy(boolean condition, SQLFuncExpression1<T1Proxy, SQLGroupByExpression> selectExpression) {
        if (condition) {
            entityQueryable.groupBy(groupBySelector -> {
                SQLGroupByExpression groupSelect = selectExpression.apply(t1Proxy);
                groupSelect.accept(groupBySelector.getGroupSelector());
            });
        }
        return this;
    }



    @Override
    public EntityQueryable<T1Proxy, T1> having(boolean condition, SQLExpression1<T1Proxy> aggregateFilterSQLExpression) {
        if (condition) {
            entityQueryable.having(whereAggregatePredicate -> {
                get1Proxy().getEntitySQLContext()._having(whereAggregatePredicate.getAggregateFilter(),()->{
                    aggregateFilterSQLExpression.apply(t1Proxy);
                });

            });
        }
        return this;
    }

    @Override
    public EntityQueryable<T1Proxy, T1> orderBy(boolean condition, SQLExpression1<T1Proxy> selectExpression) {
        if (condition) {
            entityQueryable.orderBy(columnSelector -> {
                get1Proxy().getEntitySQLContext()._orderBy(columnSelector.getOrderSelector(),()->{
                    selectExpression.apply(t1Proxy);
                });
            }, false);
        }
        return this;
    }

    @Override
    public EntityQueryable<T1Proxy, T1> orderByObject(boolean condition, ObjectSort objectSort) {
        if (condition) {
            entityQueryable.orderByObject(objectSort);
        }
        return this;
    }

    @Override
    public EntityQueryable<T1Proxy, T1> distinct(boolean condition) {
        if (condition) {
            entityQueryable.distinct();
        }
        return this;
    }

    @Override
    public EntityQueryable<T1Proxy, T1> limit(boolean condition, long offset, long rows) {
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
        ClientQueryable2<T1, T2> entityQueryable2 = entityQueryable.leftJoin(joinClass, (t, t1) -> {
            t1Proxy.getEntitySQLContext()._where(t.getFilter(),()->{
                on.apply(t1Proxy, t2Proxy.create(t1.getTable(),runtimeContext));
            });
        });
        return new EasyEntityQueryable2<>(t1Proxy, t2Proxy, entityQueryable2);
    }

    @Override
    public <T2Proxy extends ProxyEntity<T2Proxy, T2>, T2 extends ProxyEntityAvailable<T2, T2Proxy>> EntityQueryable2<T1Proxy, T1, T2Proxy, T2> leftJoin(EntityQueryable<T2Proxy, T2> joinQueryable, SQLExpression2<T1Proxy, T2Proxy> on) {
        ClientQueryable<T2> clientQueryable = joinQueryable.getClientQueryable();
        ClientQueryable2<T1, T2> entityQueryable2 = entityQueryable.leftJoin(clientQueryable, (t, t1) -> {
            t1Proxy.getEntitySQLContext()._where(t.getFilter(),()->{
                on.apply(t1Proxy, joinQueryable.get1Proxy());
            });
        });
        return new EasyEntityQueryable2<>(t1Proxy, joinQueryable.get1Proxy(), entityQueryable2);
    }

    @Override
    public <T2Proxy extends ProxyEntity<T2Proxy, T2>, T2 extends ProxyEntityAvailable<T2, T2Proxy>> EntityQueryable2<T1Proxy, T1, T2Proxy, T2> rightJoin(Class<T2> joinClass, SQLExpression2<T1Proxy, T2Proxy> on) {
        T2Proxy t2Proxy = EntityQueryProxyManager.create(joinClass);
        ClientQueryable2<T1, T2> entityQueryable2 = entityQueryable.rightJoin(joinClass, (t, t1) -> {
            t1Proxy.getEntitySQLContext()._where(t.getFilter(),()->{
                on.apply(t1Proxy, t2Proxy.create(t1.getTable(),runtimeContext));
            });
        });
        return new EasyEntityQueryable2<>(t1Proxy, t2Proxy, entityQueryable2);
    }

    @Override
    public <T2Proxy extends ProxyEntity<T2Proxy, T2>, T2 extends ProxyEntityAvailable<T2, T2Proxy>> EntityQueryable2<T1Proxy, T1, T2Proxy, T2> rightJoin(EntityQueryable<T2Proxy, T2> joinQueryable, SQLExpression2<T1Proxy, T2Proxy> on) {
        ClientQueryable<T2> clientQueryable = joinQueryable.getClientQueryable();
        ClientQueryable2<T1, T2> entityQueryable2 = entityQueryable.rightJoin(clientQueryable, (t, t1) -> {
            t1Proxy.getEntitySQLContext()._where(t.getFilter(),()->{
                on.apply(t1Proxy, joinQueryable.get1Proxy());
            });
        });
        return new EasyEntityQueryable2<>(t1Proxy, joinQueryable.get1Proxy(), entityQueryable2);
    }

    @Override
    public <T2Proxy extends ProxyEntity<T2Proxy, T2>, T2 extends ProxyEntityAvailable<T2, T2Proxy>> EntityQueryable2<T1Proxy, T1, T2Proxy, T2> innerJoin(Class<T2> joinClass, SQLExpression2<T1Proxy, T2Proxy> on) {
        T2Proxy t2Proxy = EntityQueryProxyManager.create(joinClass);
        ClientQueryable2<T1, T2> entityQueryable2 = entityQueryable.innerJoin(joinClass, (t, t1) -> {
            t1Proxy.getEntitySQLContext()._where(t.getFilter(),()->{
                on.apply(t1Proxy, t2Proxy.create(t1.getTable(),runtimeContext));
            });
        });
        return new EasyEntityQueryable2<>(t1Proxy, t2Proxy, entityQueryable2);
    }

    @Override
    public <T2Proxy extends ProxyEntity<T2Proxy, T2>, T2 extends ProxyEntityAvailable<T2, T2Proxy>> EntityQueryable2<T1Proxy, T1, T2Proxy, T2> innerJoin(EntityQueryable<T2Proxy, T2> joinQueryable, SQLExpression2<T1Proxy, T2Proxy> on) {
        ClientQueryable<T2> clientQueryable = joinQueryable.getClientQueryable();
        ClientQueryable2<T1, T2> entityQueryable2 = entityQueryable.innerJoin(clientQueryable, (t, t1) -> {
            t1Proxy.getEntitySQLContext()._where(t.getFilter(),()->{
                on.apply(t1Proxy, joinQueryable.get1Proxy());
            });
        });
        return new EasyEntityQueryable2<>(t1Proxy, joinQueryable.get1Proxy(), entityQueryable2);
    }

    @Override
    public EntityQueryable<T1Proxy, T1> union(Collection<EntityQueryable<T1Proxy, T1>> unionQueries) {

        if (EasyCollectionUtil.isEmpty(unionQueries)) {
            return this;
        }
        ClientQueryable<T1> unionQueryable = entityQueryable.union(EasyCollectionUtil.select(unionQueries, (queryable, i) -> queryable.getClientQueryable()));
        return new EasyEntityQueryable<>(t1Proxy, unionQueryable);
    }

    @Override
    public EntityQueryable<T1Proxy, T1> unionAll(Collection<EntityQueryable<T1Proxy, T1>> unionQueries) {
        if (EasyCollectionUtil.isEmpty(unionQueries)) {
            return this;
        }
        ClientQueryable<T1> unionQueryable = entityQueryable.unionAll(EasyCollectionUtil.select(unionQueries, (queryable, i) -> queryable.getClientQueryable()));
        return new EasyEntityQueryable<>(t1Proxy, unionQueryable);
    }

    @Override
    public EntityQueryable<T1Proxy, T1> useLogicDelete(boolean enable) {
        entityQueryable.useLogicDelete(enable);
        return this;
    }

    @Override
    public EntityQueryable<T1Proxy, T1> noInterceptor() {
        entityQueryable.noInterceptor();
        return this;
    }

    @Override
    public EntityQueryable<T1Proxy, T1> useInterceptor(String name) {
        entityQueryable.useInterceptor(name);
        return this;
    }

    @Override
    public EntityQueryable<T1Proxy, T1> noInterceptor(String name) {
        entityQueryable.noInterceptor(name);
        return this;
    }

    @Override
    public EntityQueryable<T1Proxy, T1> useInterceptor() {
        entityQueryable.useInterceptor();
        return this;
    }

    @Override
    public EntityQueryable<T1Proxy, T1> asTracking() {
        entityQueryable.asTracking();
        return this;
    }

    @Override
    public EntityQueryable<T1Proxy, T1> asNoTracking() {
        entityQueryable.asNoTracking();
        return this;
    }

    @Override
    public EntityQueryable<T1Proxy, T1> asTable(Function<String, String> tableNameAs) {
        entityQueryable.asTable(tableNameAs);
        return this;
    }

    @Override
    public EntityQueryable<T1Proxy, T1> asSchema(Function<String, String> schemaAs) {
        entityQueryable.asSchema(schemaAs);
        return this;
    }

    @Override
    public EntityQueryable<T1Proxy, T1> asAlias(String alias) {
        entityQueryable.asAlias(alias);
        return this;
    }

    @Override
    public EntityQueryable<T1Proxy, T1> asTableLink(Function<String, String> linkAs) {
        entityQueryable.asTableLink(linkAs);
        return this;
    }

    @Override
    public EntityQueryable<T1Proxy, T1> useShardingConfigure(int maxShardingQueryLimit, ConnectionModeEnum connectionMode) {
        entityQueryable.useShardingConfigure(maxShardingQueryLimit, connectionMode);
        return this;
    }

    @Override
    public EntityQueryable<T1Proxy, T1> useMaxShardingQueryLimit(int maxShardingQueryLimit) {
        entityQueryable.useMaxShardingQueryLimit(maxShardingQueryLimit);
        return this;
    }

    @Override
    public EntityQueryable<T1Proxy, T1> useConnectionMode(ConnectionModeEnum connectionMode) {
        entityQueryable.useConnectionMode(connectionMode);
        return this;
    }

    @Override
    public EntityQueryable<T1Proxy, T1> queryLargeColumn(boolean queryLarge) {
        entityQueryable.queryLargeColumn(queryLarge);
        return this;
    }

//    @Override
//    public EntityQueryable<T1Proxy, T1> conditionConfigure(ConditionAccepter conditionAccepter) {
//        entityQueryable.conditionConfigure(conditionAccepter);
//        return this;
//    }

    @Override
    public EntityQueryable<T1Proxy, T1> filterConfigure(ValueFilter valueFilter) {
        entityQueryable.filterConfigure(valueFilter);
        return this;
    }

}
