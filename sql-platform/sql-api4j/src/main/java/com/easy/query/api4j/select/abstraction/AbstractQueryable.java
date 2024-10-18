package com.easy.query.api4j.select.abstraction;

import com.easy.query.api4j.select.Queryable;
import com.easy.query.api4j.select.Queryable2;
import com.easy.query.api4j.select.impl.EasyQueryable;
import com.easy.query.api4j.select.impl.EasyQueryable2;
import com.easy.query.api4j.sql.SQLColumnAsSelector;
import com.easy.query.api4j.sql.SQLColumnSelector;
import com.easy.query.api4j.sql.SQLGroupBySelector;
import com.easy.query.api4j.sql.SQLOrderBySelector;
import com.easy.query.api4j.sql.SQLWhereAggregatePredicate;
import com.easy.query.api4j.sql.SQLWherePredicate;
import com.easy.query.api4j.sql.impl.SQLColumnAsSelectorImpl;
import com.easy.query.api4j.sql.impl.SQLColumnSelectorImpl;
import com.easy.query.api4j.sql.impl.SQLGroupBySelectorImpl;
import com.easy.query.api4j.sql.impl.SQLOrderByColumnSelectorImpl;
import com.easy.query.api4j.sql.impl.SQLWhereAggregatePredicateImpl;
import com.easy.query.api4j.sql.impl.SQLWherePredicateImpl;
import com.easy.query.core.annotation.NotNull;
import com.easy.query.core.api.dynamic.sort.ObjectSort;
import com.easy.query.core.api.pagination.EasyPageResult;
import com.easy.query.core.basic.api.select.ClientQueryable;
import com.easy.query.core.basic.api.select.ClientQueryable2;
import com.easy.query.core.basic.api.select.executor.MethodQuery;
import com.easy.query.core.basic.jdbc.executor.internal.enumerable.JdbcStreamResult;
import com.easy.query.core.basic.jdbc.parameter.ToSQLContext;
import com.easy.query.core.enums.sharding.ConnectionModeEnum;
import com.easy.query.core.expression.builder.core.ValueFilter;
import com.easy.query.core.expression.lambda.SQLConsumer;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.lambda.SQLExpression2;
import com.easy.query.core.expression.segment.ColumnSegment;
import com.easy.query.core.expression.sql.builder.EntityQueryExpressionBuilder;
import com.easy.query.core.expression.sql.builder.internal.ContextConfigurer;
import com.easy.query.core.metadata.EntityMetadata;
import com.easy.query.core.util.EasyCollectionUtil;

import java.sql.Statement;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Stream;

/**
 * @author xuejiaming
 * @FileName: AbstractSelect0.java
 * @Description: 文件说明
 * @Date: 2023/2/6 23:44
 */
public abstract class AbstractQueryable<T1> implements Queryable<T1> {

    protected final ClientQueryable<T1> entityQueryable;

    @Override
    public Class<T1> queryClass() {
        return entityQueryable.queryClass();
    }

    @Override
    public EntityMetadata queryEntityMetadata() {
        return entityQueryable.queryEntityMetadata();
    }

    public AbstractQueryable(ClientQueryable<T1> entityQueryable) {
        this.entityQueryable = entityQueryable;
    }

    @Override
    public ClientQueryable<T1> getClientQueryable() {
        return entityQueryable;
    }
    @Override
    public Queryable<T1> getQueryable() {
        return this;
    }

    @Override
    public long count() {
        return entityQueryable.count();
    }

    @Override
    public long countDistinct(SQLExpression1<SQLColumnSelector<T1>> selectExpression) {
        return entityQueryable.countDistinct(columnSelector -> {
            selectExpression.apply(new SQLColumnSelectorImpl<>(columnSelector));
        });
    }

    @Override
    public boolean any() {
        return entityQueryable.any();
    }
    @Override
    public Queryable<Long> selectCount() {
        return this.selectCount(Long.class);
    }

    @Override
    public <TNumber extends Number> Queryable<TNumber> selectCount(Class<TNumber> numberClass) {
        ClientQueryable<TNumber> tNumberClientQueryable = getClientQueryable().selectCount(numberClass);
        return new EasyQueryable<>(tNumberClientQueryable);
    }

    @Override
    public <TR> TR firstOrNull(Class<TR> resultClass) {
        return entityQueryable.firstOrNull(resultClass);
    }

    @NotNull
    @Override
    public <TR> TR firstNotNull(Class<TR> resultClass, String msg, String code) {
        return entityQueryable.firstNotNull(resultClass, msg, code);
    }

    @Override
    public <TR> TR singleOrNull(Class<TR> resultClass) {
        return entityQueryable.singleOrNull(resultClass);
    }
    @Override
    public T1 findOrNull(Object id) {
        return entityQueryable.findOrNull(id);
    }

    @NotNull
    @Override
    public <TR> TR singleNotNull(Class<TR> resultClass, String msg, String code) {
        return entityQueryable.singleNotNull(resultClass, msg, code);
    }

    @NotNull
    @Override
    public <TR> TR firstNotNull(Class<TR> resultClass, Supplier<RuntimeException> throwFunc) {
        return entityQueryable.firstNotNull(resultClass,throwFunc);
    }

    @NotNull
    @Override
    public <TR> TR singleNotNull(Class<TR> resultClass, Supplier<RuntimeException> throwFunc) {
        return entityQueryable.singleNotNull(resultClass,throwFunc);
    }
    @NotNull
    @Override
    public T1 findNotNull(Object id, Supplier<RuntimeException> throwFunc) {
        return entityQueryable.findNotNull(id,throwFunc);
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
    public <TR> List<TR> toList(Class<TR> resultClass) {
        return entityQueryable.toList(resultClass);
    }

    @Override
    public <TR> List<TR> toList(Class<TR> resultClass, EntityMetadata resultEntityMetadata) {
        return entityQueryable.toList(resultClass,resultEntityMetadata);
    }

    @Override
    public <TR> JdbcStreamResult<TR> toStreamResult(Class<TR> resultClass, SQLConsumer<Statement> configurer) {
        return entityQueryable.toStreamResult(resultClass,configurer);
    }

    @Override
    public void toChunk(int size, Predicate<List<T1>> chunk) {
        entityQueryable.toChunk(size,chunk);
    }

    @Override
    public <TR> TR streamBy(Function<Stream<T1>, TR> fetcher, SQLConsumer<Statement> configurer) {
        return entityQueryable.streamBy(fetcher,configurer);
    }

    @Override
    public <TR> String toSQL(Class<TR> resultClass, ToSQLContext toSQLContext) {
        return entityQueryable.toSQL(resultClass, toSQLContext);
    }

    /**
     * 只有select操作运行操作当前projects
     *
     * @param selectExpression
     * @return
     */
    @Override
    public Queryable<T1> select(SQLExpression1<SQLColumnSelector<T1>> selectExpression) {
        ClientQueryable<T1> select = entityQueryable.select(columnSelector -> {
            selectExpression.apply(new SQLColumnSelectorImpl<>(columnSelector));
        });
        return new EasyQueryable<>(select);
    }

    @Override
    public <TR> Queryable<TR> select(Class<TR> resultClass, SQLExpression1<SQLColumnAsSelector<T1, TR>> selectExpression) {
        ClientQueryable<TR> select = entityQueryable.select(resultClass, columnAsSelector -> {
            selectExpression.apply(new SQLColumnAsSelectorImpl<>(columnAsSelector));
        });
        return new EasyQueryable<>(select);
    }

    @Override
    public Queryable<T1> select(String columns) {
        entityQueryable.select(columns);
        return this;
    }

    @Override
    public Queryable<T1> select(Collection<ColumnSegment> columnSegments, boolean clearAll) {
        entityQueryable.select(columnSegments, clearAll);
        return this;
    }

    @Override
    public <TR> Queryable<TR> select(Class<TR> resultClass) {
        ClientQueryable<TR> select = entityQueryable.select(resultClass);
        return new EasyQueryable<>(select);
    }

    @Override
    public Queryable<T1> where(boolean condition, SQLExpression1<SQLWherePredicate<T1>> whereExpression) {
        if (condition) {
            entityQueryable.where(wherePredicate -> {
                whereExpression.apply(new SQLWherePredicateImpl<>(wherePredicate));
            });
        }
        return this;
    }

    @Override
    public Queryable<T1> whereById(boolean condition, Object id) {
        if (condition) {

            entityQueryable.whereById(id);
        }
        return this;
    }

    @Override
    public <TProperty> Queryable<T1> whereByIds(boolean condition, Collection<TProperty> ids) {

        if (condition) {
            entityQueryable.whereByIds(ids);
        }
        return this;
    }

    @Override
    public Queryable<T1> whereObject(boolean condition, Object object) {
        if (condition) {
            entityQueryable.whereObject(object);
        }
        return this;
    }

    @Override
    public Queryable<T1> groupBy(boolean condition, SQLExpression1<SQLGroupBySelector<T1>> selectExpression) {
        if (condition) {
            entityQueryable.groupBy(groupBySelector -> {
                selectExpression.apply(new SQLGroupBySelectorImpl<>(groupBySelector));
            });
        }
        return this;
    }

    @Override
    public Queryable<T1> having(boolean condition, SQLExpression1<SQLWhereAggregatePredicate<T1>> predicateExpression) {

        if (condition) {
            entityQueryable.having(whereAggregatePredicate -> {
                predicateExpression.apply(new SQLWhereAggregatePredicateImpl<>(whereAggregatePredicate));
            });
        }
        return this;
    }

    @Override
    public Queryable<T1> orderBy(boolean condition, SQLExpression1<SQLOrderBySelector<T1>> selectExpression, boolean asc) {
        if (condition) {
            entityQueryable.orderBy(columnSelector -> {
                selectExpression.apply(new SQLOrderByColumnSelectorImpl<>(columnSelector));
            }, asc);
        }
        return this;
    }

    @Override
    public Queryable<T1> orderByObject(boolean condition, ObjectSort objectSort) {

        if (condition) {
            entityQueryable.orderByObject(objectSort);
        }
        return this;
    }

    @Override
    public Queryable<T1> limit(boolean condition, long offset, long rows) {
        if (condition) {
            entityQueryable.limit(offset, rows);
        }
        return this;
    }

    @Override
    public Queryable<T1> distinct(boolean condition) {
        if (condition) {
            entityQueryable.distinct();
        }
        return this;
    }

    @Override
    public <TResult> EasyPageResult<TResult> toPageResult(Class<TResult> tResultClass, long pageIndex, long pageSize, long pageTotal) {
        return entityQueryable.toPageResult(tResultClass,pageIndex, pageSize, pageTotal);
    }

    @Override
    public <TResult> EasyPageResult<TResult> toShardingPageResult(Class<TResult> tResultClass, long pageIndex, long pageSize, List<Long> totalLines) {
        return entityQueryable.toShardingPageResult(tResultClass,pageIndex, pageSize, totalLines);
    }

    @Override
    public EntityQueryExpressionBuilder getSQLEntityExpressionBuilder() {
        return entityQueryable.getSQLEntityExpressionBuilder();
    }

    @Override
    public <T2> Queryable2<T1, T2> leftJoin(Class<T2> joinClass, SQLExpression2<SQLWherePredicate<T1>, SQLWherePredicate<T2>> on) {
        ClientQueryable2<T1, T2> entityQueryable2 = entityQueryable.leftJoin(joinClass, (wherePredicate1, wherePredicate2) -> {
            on.apply(new SQLWherePredicateImpl<>(wherePredicate1), new SQLWherePredicateImpl<>(wherePredicate2));
        });
        return new EasyQueryable2<>(entityQueryable2);
    }

    @Override
    public <T2> Queryable2<T1, T2> leftJoin(Queryable<T2> joinQueryable, SQLExpression2<SQLWherePredicate<T1>, SQLWherePredicate<T2>> on) {
        ClientQueryable2<T1, T2> entityQueryable2 = entityQueryable.leftJoin(joinQueryable.getClientQueryable(), (wherePredicate1, wherePredicate2) -> {
            on.apply(new SQLWherePredicateImpl<>(wherePredicate1), new SQLWherePredicateImpl<>(wherePredicate2));
        });
        return new EasyQueryable2<>(entityQueryable2);
    }

    @Override
    public <T2> Queryable2<T1, T2> rightJoin(Class<T2> joinClass, SQLExpression2<SQLWherePredicate<T1>, SQLWherePredicate<T2>> on) {
        ClientQueryable2<T1, T2> entityQueryable2 = entityQueryable.rightJoin(joinClass, (wherePredicate1, wherePredicate2) -> {
            on.apply(new SQLWherePredicateImpl<>(wherePredicate1), new SQLWherePredicateImpl<>(wherePredicate2));
        });
        return new EasyQueryable2<>(entityQueryable2);
    }

    @Override
    public <T2> Queryable2<T1, T2> rightJoin(Queryable<T2> joinQueryable, SQLExpression2<SQLWherePredicate<T1>, SQLWherePredicate<T2>> on) {
        ClientQueryable2<T1, T2> entityQueryable2 = entityQueryable.rightJoin(joinQueryable.getClientQueryable(), (wherePredicate1, wherePredicate2) -> {
            on.apply(new SQLWherePredicateImpl<>(wherePredicate1), new SQLWherePredicateImpl<>(wherePredicate2));
        });
        return new EasyQueryable2<>(entityQueryable2);
    }

    @Override
    public <T2> Queryable2<T1, T2> innerJoin(Class<T2> joinClass, SQLExpression2<SQLWherePredicate<T1>, SQLWherePredicate<T2>> on) {
        ClientQueryable2<T1, T2> entityQueryable2 = entityQueryable.innerJoin(joinClass, (wherePredicate1, wherePredicate2) -> {
            on.apply(new SQLWherePredicateImpl<>(wherePredicate1), new SQLWherePredicateImpl<>(wherePredicate2));
        });
        return new EasyQueryable2<>(entityQueryable2);
    }

    @Override
    public <T2> Queryable2<T1, T2> innerJoin(Queryable<T2> joinQueryable, SQLExpression2<SQLWherePredicate<T1>, SQLWherePredicate<T2>> on) {
        ClientQueryable2<T1, T2> entityQueryable2 = entityQueryable.innerJoin(joinQueryable.getClientQueryable(), (wherePredicate1, wherePredicate2) -> {
            on.apply(new SQLWherePredicateImpl<>(wherePredicate1), new SQLWherePredicateImpl<>(wherePredicate2));
        });
        return new EasyQueryable2<>(entityQueryable2);
    }

    @Override
    public Queryable<T1> union(Collection<Queryable<T1>> unionQueries) {
        if (EasyCollectionUtil.isEmpty(unionQueries)) {
            return this;
        }
        ClientQueryable<T1> unionQueryable = entityQueryable.union(EasyCollectionUtil.select(unionQueries, (queryable, i) -> queryable.getClientQueryable()));
        return new EasyQueryable<>(unionQueryable);
    }

    @Override
    public Queryable<T1> unionAll(Collection<Queryable<T1>> unionQueries) {
        if (EasyCollectionUtil.isEmpty(unionQueries)) {
            return this;
        }
        ClientQueryable<T1> unionQueryable = entityQueryable.unionAll(EasyCollectionUtil.select(unionQueries, (queryable, i) -> queryable.getClientQueryable()));
        return new EasyQueryable<>(unionQueryable);
    }

    @Override
    public Queryable<T1> useLogicDelete(boolean enable) {
        entityQueryable.useLogicDelete(enable);
        return this;
    }
    @Override
    public Queryable<T1> configure(SQLExpression1<ContextConfigurer> configurer) {
        entityQueryable.configure(configurer);
        return this;
    }

    @Override
    public Queryable<T1> noInterceptor() {
        entityQueryable.noInterceptor();
        return this;
    }

    @Override
    public Queryable<T1> useInterceptor(String name) {
        entityQueryable.useInterceptor(name);
        return this;
    }

    @Override
    public Queryable<T1> noInterceptor(String name) {
        entityQueryable.noInterceptor(name);
        return this;
    }

    @Override
    public Queryable<T1> useInterceptor() {
        entityQueryable.useInterceptor();
        return this;
    }

    @Override
    public Queryable<T1> asTracking() {
        entityQueryable.asTracking();
        return this;
    }

    @Override
    public Queryable<T1> asNoTracking() {
        entityQueryable.asNoTracking();
        return this;
    }

    @Override
    public Queryable<T1> asTable(Function<String, String> tableNameAs) {
        entityQueryable.asTable(tableNameAs);
        return this;
    }

    @Override
    public Queryable<T1> asSchema(Function<String, String> schemaAs) {
        entityQueryable.asSchema(schemaAs);
        return this;
    }

    @Override
    public Queryable<T1> asAlias(String alias) {
        entityQueryable.asAlias(alias);
        return this;
    }


    @Override
    public Queryable<T1> asTableLink(Function<String, String> linkAs) {
        entityQueryable.asTableLink(linkAs);
        return this;
    }

    @Override
    public Queryable<T1> asTableSegment(BiFunction<String, String, String> segmentAs) {
        entityQueryable.asTableSegment(segmentAs);
        return this;
    }

    @Override
    public Queryable<T1> useShardingConfigure(int maxShardingQueryLimit, ConnectionModeEnum connectionMode) {
        entityQueryable.useShardingConfigure(maxShardingQueryLimit, connectionMode);
        return this;
    }

    @Override
    public Queryable<T1> useMaxShardingQueryLimit(int maxShardingQueryLimit) {
        entityQueryable.useMaxShardingQueryLimit(maxShardingQueryLimit);
        return this;
    }

    @Override
    public Queryable<T1> useConnectionMode(ConnectionModeEnum connectionMode) {
        entityQueryable.useConnectionMode(connectionMode);
        return this;
    }

    @Override
    public Queryable<T1> queryLargeColumn(boolean queryLarge) {
        entityQueryable.queryLargeColumn(queryLarge);
        return this;
    }

    @Override
    public Queryable<T1> filterConfigure(ValueFilter valueFilter) {
        entityQueryable.filterConfigure(valueFilter);
        return this;
    }

    @Override
    public MethodQuery<T1> forEach(Consumer<T1> mapConfigure) {
        entityQueryable.forEach(mapConfigure);
        return this;
    }
}
