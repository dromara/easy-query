package com.easy.query.api.proxy.select.abstraction;

import com.easy.query.api.proxy.select.ProxyQueryable;
import com.easy.query.api.proxy.select.ProxyQueryable2;
import com.easy.query.api.proxy.select.impl.EasyProxyQueryable;
import com.easy.query.api.proxy.select.impl.EasyProxyQueryable2;
import com.easy.query.api.proxy.sql.ProxyAggregateFilter;
import com.easy.query.api.proxy.sql.ProxyAsSelector;
import com.easy.query.api.proxy.sql.ProxyFilter;
import com.easy.query.api.proxy.sql.ProxyGroupSelector;
import com.easy.query.api.proxy.sql.ProxyOrderSelector;
import com.easy.query.api.proxy.sql.ProxySelector;
import com.easy.query.api.proxy.sql.impl.ProxyAggregateFilterImpl;
import com.easy.query.api.proxy.sql.impl.ProxyAsSelectorImpl;
import com.easy.query.api.proxy.sql.impl.ProxyFilterImpl;
import com.easy.query.api.proxy.sql.impl.ProxyGroupSelectorImpl;
import com.easy.query.api.proxy.sql.impl.ProxyOrderSelectorImpl;
import com.easy.query.api.proxy.sql.impl.ProxySelectorImpl;
import com.easy.query.core.api.dynamic.sort.ObjectSort;
import com.easy.query.core.api.pagination.EasyPageResult;
import com.easy.query.core.basic.api.select.ClientQueryable;
import com.easy.query.core.basic.api.select.ClientQueryable2;
import com.easy.query.core.basic.jdbc.executor.internal.enumerable.JdbcStreamResult;
import com.easy.query.core.basic.jdbc.parameter.ToSQLContext;
import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.enums.sharding.ConnectionModeEnum;
import com.easy.query.core.expression.lambda.SQLExpression2;
import com.easy.query.core.expression.lambda.SQLExpression3;
import com.easy.query.core.expression.lambda.SQLFuncExpression1;
import com.easy.query.core.expression.segment.ColumnSegment;
import com.easy.query.core.expression.sql.builder.EntityQueryExpressionBuilder;
import com.easy.query.core.proxy.ProxyEntity;
import com.easy.query.core.proxy.SQLColumn;
import com.easy.query.core.util.EasyCollectionUtil;
import com.easy.query.core.util.EasyObjectUtil;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 * create time 2023/6/23 22:08
 * 文件说明
 *
 * @author xuejiaming
 */
public abstract class AbstractProxyQueryable<T1Proxy extends ProxyEntity<T1Proxy, T1>, T1> implements ProxyQueryable<T1Proxy, T1> {

    protected final T1Proxy t1Proxy;
    protected final QueryRuntimeContext runtimeContext;
    protected final ClientQueryable<T1> entityQueryable;

    public AbstractProxyQueryable(T1Proxy t1Proxy, ClientQueryable<T1> entityQueryable) {
        EntityQueryExpressionBuilder sqlEntityExpressionBuilder = entityQueryable.getSQLEntityExpressionBuilder();
        this.runtimeContext = sqlEntityExpressionBuilder.getRuntimeContext();
        this.t1Proxy = t1Proxy.create(sqlEntityExpressionBuilder.getTable(0).getEntityTable());
        this.entityQueryable = entityQueryable;
    }

    @Override
    public T1Proxy get1Proxy() {
        return t1Proxy;
    }

    @Override
    public ClientQueryable<T1> getClientQueryable() {
        return entityQueryable;
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
    public <TR> TR firstNotNull(Class<TR> resultClass, String msg, String code) {
        return entityQueryable.firstNotNull(resultClass, msg, code);
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
    public ProxyQueryable<T1Proxy, T1> select(SQLExpression2<ProxySelector, T1Proxy> selectExpression) {
        ClientQueryable<T1> select = entityQueryable.select(columnSelector -> {
            selectExpression.apply(new ProxySelectorImpl(columnSelector.getSelector()), t1Proxy);
        });
        return new EasyProxyQueryable<>(t1Proxy, select);
    }

    @Override
    public <TRProxy extends ProxyEntity<TRProxy, TR>, TR> ProxyQueryable<TRProxy, TR> select(TRProxy trProxy, SQLExpression2<ProxyAsSelector<TRProxy, TR>, T1Proxy> selectExpression) {

        ClientQueryable<TR> select = entityQueryable.select(trProxy.getEntityClass(), columnAsSelector -> {
            selectExpression.apply(new ProxyAsSelectorImpl<>(trProxy, columnAsSelector.getAsSelector()), get1Proxy());
        });
        return new EasyProxyQueryable<>(trProxy, select);
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


    @Override
    public ProxyQueryable<T1Proxy, T1> where(boolean condition, SQLExpression2<ProxyFilter, T1Proxy> whereExpression) {
        if (condition) {
            entityQueryable.where(wherePredicate -> {
                whereExpression.apply(new ProxyFilterImpl(wherePredicate.getFilter()), get1Proxy());
            });
        }
        return this;
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
    public ProxyQueryable<T1Proxy, T1> groupBy(boolean condition, SQLExpression2<ProxyGroupSelector, T1Proxy> selectExpression) {
        if (condition) {
            entityQueryable.groupBy(groupBySelector -> {
                selectExpression.apply(new ProxyGroupSelectorImpl(groupBySelector.getGroupSelector()), t1Proxy);
            });
        }
        return this;
    }

    @Override
    public ProxyQueryable<T1Proxy, T1> having(boolean condition, SQLExpression2<ProxyAggregateFilter, T1Proxy> aggregateFilterSQLExpression) {

        if (condition) {
            entityQueryable.having(whereAggregatePredicate -> {
                aggregateFilterSQLExpression.apply(new ProxyAggregateFilterImpl(whereAggregatePredicate.getAggregateFilter()), get1Proxy());
            });
        }
        return this;
    }

    @Override
    public ProxyQueryable<T1Proxy, T1> orderBy(boolean condition, SQLExpression2<ProxyOrderSelector, T1Proxy> selectExpression, boolean asc) {
        if (condition) {
            entityQueryable.orderBy(columnSelector -> {
                selectExpression.apply(new ProxyOrderSelectorImpl(columnSelector.getOrderSelector()), get1Proxy());
            }, asc);
        }
        return this;
    }

    @Override
    public ProxyQueryable<T1Proxy, T1> orderBy(boolean condition, SQLFuncExpression1<T1Proxy, SQLColumn<?>> selectExpression, boolean asc) {
        if(condition){
            SQLColumn<?> sqlColumn = selectExpression.apply(get1Proxy());
            entityQueryable.orderBy(columnSelector->{
                columnSelector.column(sqlColumn.value());
            },asc);
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
    public <T2Proxy extends ProxyEntity<T2Proxy, T2>, T2> ProxyQueryable2<T1Proxy, T1, T2Proxy, T2> leftJoin(T2Proxy joinProxy, SQLExpression3<ProxyFilter, T1Proxy, T2Proxy> on) {
        ClientQueryable2<T1, T2> entityQueryable2 = entityQueryable.leftJoin(joinProxy.getEntityClass(), (wherePredicate1, wherePredicate2) -> {
            on.apply(new ProxyFilterImpl(wherePredicate2.getFilter()), t1Proxy, joinProxy.create(wherePredicate2.getTable()));
        });
        return new EasyProxyQueryable2<>(t1Proxy, joinProxy, entityQueryable2);
    }

    @Override
    public <T2Proxy extends ProxyEntity<T2Proxy, T2>, T2> ProxyQueryable2<T1Proxy, T1, T2Proxy, T2> leftJoin(ProxyQueryable<T2Proxy, T2> joinQueryable, SQLExpression3<ProxyFilter, T1Proxy, T2Proxy> on) {
        ClientQueryable2<T1, T2> entityQueryable2 = entityQueryable.leftJoin(joinQueryable.getClientQueryable(), (wherePredicate1, wherePredicate2) -> {
            on.apply(new ProxyFilterImpl(wherePredicate2.getFilter()), t1Proxy, joinQueryable.get1Proxy());
        });
        return new EasyProxyQueryable2<>(t1Proxy, joinQueryable.get1Proxy(), entityQueryable2);
    }

    @Override
    public <T2Proxy extends ProxyEntity<T2Proxy, T2>, T2> ProxyQueryable2<T1Proxy, T1, T2Proxy, T2> rightJoin(T2Proxy joinProxy, SQLExpression3<ProxyFilter, T1Proxy, T2Proxy> on) {

        ClientQueryable2<T1, T2> entityQueryable2 = entityQueryable.rightJoin(joinProxy.getEntityClass(), (wherePredicate1, wherePredicate2) -> {
            on.apply(new ProxyFilterImpl(wherePredicate2.getFilter()), t1Proxy, joinProxy.create(wherePredicate2.getTable()));
        });
        return new EasyProxyQueryable2<>(t1Proxy, joinProxy, entityQueryable2);
    }

    @Override
    public <T2Proxy extends ProxyEntity<T2Proxy, T2>, T2> ProxyQueryable2<T1Proxy, T1, T2Proxy, T2> rightJoin(ProxyQueryable<T2Proxy, T2> joinQueryable, SQLExpression3<ProxyFilter, T1Proxy, T2Proxy> on) {
        ClientQueryable2<T1, T2> entityQueryable2 = entityQueryable.rightJoin(joinQueryable.getClientQueryable(), (wherePredicate1, wherePredicate2) -> {
            on.apply(new ProxyFilterImpl(wherePredicate2.getFilter()), t1Proxy, joinQueryable.get1Proxy());
        });
        return new EasyProxyQueryable2<>(t1Proxy, joinQueryable.get1Proxy(), entityQueryable2);
    }

    @Override
    public <T2Proxy extends ProxyEntity<T2Proxy, T2>, T2> ProxyQueryable2<T1Proxy, T1, T2Proxy, T2> innerJoin(T2Proxy joinProxy, SQLExpression3<ProxyFilter, T1Proxy, T2Proxy> on) {

        ClientQueryable2<T1, T2> entityQueryable2 = entityQueryable.innerJoin(joinProxy.getEntityClass(), (wherePredicate1, wherePredicate2) -> {
            on.apply(new ProxyFilterImpl(wherePredicate2.getFilter()), t1Proxy, joinProxy.create(wherePredicate2.getTable()));
        });
        return new EasyProxyQueryable2<>(t1Proxy, joinProxy, entityQueryable2);
    }

    @Override
    public <T2Proxy extends ProxyEntity<T2Proxy, T2>, T2> ProxyQueryable2<T1Proxy, T1, T2Proxy, T2> innerJoin(ProxyQueryable<T2Proxy, T2> joinQueryable, SQLExpression3<ProxyFilter, T1Proxy, T2Proxy> on) {
        ClientQueryable2<T1, T2> entityQueryable2 = entityQueryable.innerJoin(joinQueryable.getClientQueryable(), (wherePredicate1, wherePredicate2) -> {
            on.apply(new ProxyFilterImpl(wherePredicate2.getFilter()), t1Proxy, joinQueryable.get1Proxy());
        });
        return new EasyProxyQueryable2<>(t1Proxy, joinQueryable.get1Proxy(), entityQueryable2);
    }

    @Override
    public ProxyQueryable<T1Proxy, T1> union(Collection<ProxyQueryable<T1Proxy, T1>> unionQueries) {

        if (EasyCollectionUtil.isEmpty(unionQueries)) {
            return this;
        }
        ClientQueryable<T1> unionQueryable = entityQueryable.union(EasyCollectionUtil.select(unionQueries, (queryable, i) -> queryable.getClientQueryable()));
        return new EasyProxyQueryable<>(t1Proxy, unionQueryable);
    }

    @Override
    public ProxyQueryable<T1Proxy, T1> unionAll(Collection<ProxyQueryable<T1Proxy, T1>> unionQueries) {
        if (EasyCollectionUtil.isEmpty(unionQueries)) {
            return this;
        }
        ClientQueryable<T1> unionQueryable = entityQueryable.unionAll(EasyCollectionUtil.select(unionQueries, (queryable, i) -> queryable.getClientQueryable()));
        return new EasyProxyQueryable<>(t1Proxy, unionQueryable);
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

}
