package com.easy.query.api4kt.select.abstraction;

import com.easy.query.api4kt.select.KtQueryable;
import com.easy.query.api4kt.select.KtQueryable2;
import com.easy.query.api4kt.select.impl.EasyKtQueryable;
import com.easy.query.api4kt.select.impl.EasyKtQueryable2;
import com.easy.query.api4kt.sql.SQLKtColumnAsSelector;
import com.easy.query.api4kt.sql.SQLKtColumnSelector;
import com.easy.query.api4kt.sql.SQLKtGroupBySelector;
import com.easy.query.api4kt.sql.SQLKtOrderBySelector;
import com.easy.query.api4kt.sql.SQLKtWhereAggregatePredicate;
import com.easy.query.api4kt.sql.SQLKtWherePredicate;
import com.easy.query.api4kt.sql.impl.SQLKtColumnAsSelectorImpl;
import com.easy.query.api4kt.sql.impl.SQLKtColumnSelectorImpl;
import com.easy.query.api4kt.sql.impl.SQLKtGroupBySelectorImpl;
import com.easy.query.api4kt.sql.impl.SQLKtOrderByColumnSelectorImpl;
import com.easy.query.api4kt.sql.impl.SQLKtWhereAggregatePredicateImpl;
import com.easy.query.api4kt.sql.impl.SQLKtWherePredicateImpl;
import com.easy.query.core.api.dynamic.sort.ObjectSort;
import com.easy.query.core.api.pagination.EasyPageResult;
import com.easy.query.core.basic.api.select.ClientQueryable;
import com.easy.query.core.basic.api.select.ClientQueryable2;
import com.easy.query.core.basic.api.select.executor.MethodQuery;
import com.easy.query.core.basic.jdbc.executor.internal.enumerable.JdbcStreamResult;
import com.easy.query.core.basic.jdbc.parameter.ToSQLContext;
import com.easy.query.core.enums.sharding.ConnectionModeEnum;
import com.easy.query.core.expression.builder.core.ValueFilter;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.lambda.SQLExpression2;
import com.easy.query.core.expression.segment.ColumnSegment;
import com.easy.query.core.expression.sql.builder.EntityQueryExpressionBuilder;
import com.easy.query.core.util.EasyCollectionUtil;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * @author xuejiaming
 * @FileName: AbstractSelect0.java
 * @Description: 文件说明
 * @Date: 2023/2/6 23:44
 */
public abstract class AbstractKtQueryable<T1> implements KtQueryable<T1> {

    protected final ClientQueryable<T1> entityQueryable;

    @Override
    public Class<T1> queryClass() {
        return entityQueryable.queryClass();
    }

    public AbstractKtQueryable(ClientQueryable<T1> entityQueryable) {
        this.entityQueryable = entityQueryable;
    }

    @Override
    public ClientQueryable<T1> getClientQueryable() {
        return entityQueryable;
    }

    @Override
    public KtQueryable<T1> getQueryable() {
        return this;
    }

    @Override
    public long count() {
        return entityQueryable.count();
    }

    @Override
    public long countDistinct(SQLExpression1<SQLKtColumnSelector<T1>> selectExpression) {
        return entityQueryable.countDistinct(columnSelector -> {
            selectExpression.apply(new SQLKtColumnSelectorImpl<>(columnSelector));
        });
    }

    @Override
    public boolean any() {
        return entityQueryable.any();
    }

    @Override
    public boolean all(SQLExpression1<SQLKtWherePredicate<T1>> whereExpression) {
        return entityQueryable.all(wherePredicate -> {
            whereExpression.apply(new SQLKtWherePredicateImpl<>(wherePredicate));
        });
    }

    @Override
    public KtQueryable<Long> selectCount() {
        return this.selectCount(Long.class);
    }

    @Override
    public <TNumber extends Number> KtQueryable<TNumber> selectCount(Class<TNumber> numberClass) {
        ClientQueryable<TNumber> tNumberClientQueryable = getClientQueryable().selectCount(numberClass);
        return new EasyKtQueryable<>(tNumberClientQueryable);
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
    public <TR> TR singleOrNull(Class<TR> resultClass) {
        return entityQueryable.singleOrNull(resultClass);
    }

    @Override
    public <TR> TR singleNotNull(Class<TR> resultClass, String msg, String code) {
        return entityQueryable.singleNotNull(resultClass, msg, code);
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
    public <TR> JdbcStreamResult<TR> toStreamResult(Class<TR> resultClass) {
        return entityQueryable.toStreamResult(resultClass);
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
    public KtQueryable<T1> select(SQLExpression1<SQLKtColumnSelector<T1>> selectExpression) {
        ClientQueryable<T1> select = entityQueryable.select(columnSelector -> {
            selectExpression.apply(new SQLKtColumnSelectorImpl<>(columnSelector));
        });
        return new EasyKtQueryable<>(select);
    }

    @Override
    public <TR> KtQueryable<TR> select(Class<TR> resultClass, SQLExpression1<SQLKtColumnAsSelector<T1, TR>> selectExpression) {
        ClientQueryable<TR> select = entityQueryable.select(resultClass, columnAsSelector -> {
            selectExpression.apply(new SQLKtColumnAsSelectorImpl<>(columnAsSelector));
        });
        return new EasyKtQueryable<>(select);
    }

    @Override
    public KtQueryable<T1> select(String columns) {
        entityQueryable.select(columns);
        return this;
    }

    @Override
    public KtQueryable<T1> select(Collection<ColumnSegment> columnSegments, boolean clearAll) {
        entityQueryable.select(columnSegments, clearAll);
        return this;
    }

    @Override
    public <TR> KtQueryable<TR> select(Class<TR> resultClass) {
        ClientQueryable<TR> select = entityQueryable.select(resultClass);
        return new EasyKtQueryable<>(select);
    }

    @Override
    public KtQueryable<T1> where(boolean condition, SQLExpression1<SQLKtWherePredicate<T1>> whereExpression) {
        if (condition) {
            entityQueryable.where(wherePredicate -> {
                whereExpression.apply(new SQLKtWherePredicateImpl<>(wherePredicate));
            });
        }
        return this;
    }

    @Override
    public KtQueryable<T1> whereById(boolean condition, Object id) {
        if (condition) {

            entityQueryable.whereById(id);
        }
        return this;
    }

    @Override
    public <TProperty> KtQueryable<T1> whereByIds(boolean condition, Collection<TProperty> ids) {

        if (condition) {
            entityQueryable.whereByIds(ids);
        }
        return this;
    }

    @Override
    public KtQueryable<T1> whereObject(boolean condition, Object object) {
        if (condition) {
            entityQueryable.whereObject(object);
        }
        return this;
    }

    @Override
    public KtQueryable<T1> groupBy(boolean condition, SQLExpression1<SQLKtGroupBySelector<T1>> selectExpression) {
        if (condition) {
            entityQueryable.groupBy(groupBySelector -> {
                selectExpression.apply(new SQLKtGroupBySelectorImpl<>(groupBySelector));
            });
        }
        return this;
    }

    @Override
    public KtQueryable<T1> having(boolean condition, SQLExpression1<SQLKtWhereAggregatePredicate<T1>> predicateExpression) {

        if (condition) {
            entityQueryable.having(whereAggregatePredicate -> {
                predicateExpression.apply(new SQLKtWhereAggregatePredicateImpl<>(whereAggregatePredicate));
            });
        }
        return this;
    }

    @Override
    public KtQueryable<T1> orderBy(boolean condition, SQLExpression1<SQLKtOrderBySelector<T1>> selectExpression, boolean asc) {
        if (condition) {
            entityQueryable.orderBy(columnSelector -> {
                selectExpression.apply(new SQLKtOrderByColumnSelectorImpl<>(columnSelector));
            }, asc);
        }
        return this;
    }

    @Override
    public KtQueryable<T1> orderByObject(boolean condition, ObjectSort objectSort) {

        if (condition) {
            entityQueryable.orderByObject(objectSort);
        }
        return this;
    }

    @Override
    public KtQueryable<T1> limit(boolean condition, long offset, long rows) {
        if (condition) {
            entityQueryable.limit(offset, rows);
        }
        return this;
    }

    @Override
    public KtQueryable<T1> distinct(boolean condition) {
        if (condition) {
            entityQueryable.distinct();
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
    public <T2> KtQueryable2<T1, T2> leftJoin(Class<T2> joinClass, SQLExpression2<SQLKtWherePredicate<T1>, SQLKtWherePredicate<T2>> on) {
        ClientQueryable2<T1, T2> entityQueryable2 = entityQueryable.leftJoin(joinClass, (wherePredicate1, wherePredicate2) -> {
            on.apply(new SQLKtWherePredicateImpl<>(wherePredicate1), new SQLKtWherePredicateImpl<>(wherePredicate2));
        });
        return new EasyKtQueryable2<>(entityQueryable2);
    }

    @Override
    public <T2> KtQueryable2<T1, T2> leftJoin(KtQueryable<T2> joinQueryable, SQLExpression2<SQLKtWherePredicate<T1>, SQLKtWherePredicate<T2>> on) {
        ClientQueryable2<T1, T2> entityQueryable2 = entityQueryable.leftJoin(joinQueryable.getClientQueryable(), (wherePredicate1, wherePredicate2) -> {
            on.apply(new SQLKtWherePredicateImpl<>(wherePredicate1), new SQLKtWherePredicateImpl<>(wherePredicate2));
        });
        return new EasyKtQueryable2<>(entityQueryable2);
    }

    @Override
    public <T2> KtQueryable2<T1, T2> rightJoin(Class<T2> joinClass, SQLExpression2<SQLKtWherePredicate<T1>, SQLKtWherePredicate<T2>> on) {
        ClientQueryable2<T1, T2> entityQueryable2 = entityQueryable.rightJoin(joinClass, (wherePredicate1, wherePredicate2) -> {
            on.apply(new SQLKtWherePredicateImpl<>(wherePredicate1), new SQLKtWherePredicateImpl<>(wherePredicate2));
        });
        return new EasyKtQueryable2<>(entityQueryable2);
    }

    @Override
    public <T2> KtQueryable2<T1, T2> rightJoin(KtQueryable<T2> joinQueryable, SQLExpression2<SQLKtWherePredicate<T1>, SQLKtWherePredicate<T2>> on) {
        ClientQueryable2<T1, T2> entityQueryable2 = entityQueryable.rightJoin(joinQueryable.getClientQueryable(), (wherePredicate1, wherePredicate2) -> {
            on.apply(new SQLKtWherePredicateImpl<>(wherePredicate1), new SQLKtWherePredicateImpl<>(wherePredicate2));
        });
        return new EasyKtQueryable2<>(entityQueryable2);
    }

    @Override
    public <T2> KtQueryable2<T1, T2> innerJoin(Class<T2> joinClass, SQLExpression2<SQLKtWherePredicate<T1>, SQLKtWherePredicate<T2>> on) {
        ClientQueryable2<T1, T2> entityQueryable2 = entityQueryable.innerJoin(joinClass, (wherePredicate1, wherePredicate2) -> {
            on.apply(new SQLKtWherePredicateImpl<>(wherePredicate1), new SQLKtWherePredicateImpl<>(wherePredicate2));
        });
        return new EasyKtQueryable2<>(entityQueryable2);
    }

    @Override
    public <T2> KtQueryable2<T1, T2> innerJoin(KtQueryable<T2> joinQueryable, SQLExpression2<SQLKtWherePredicate<T1>, SQLKtWherePredicate<T2>> on) {
        ClientQueryable2<T1, T2> entityQueryable2 = entityQueryable.innerJoin(joinQueryable.getClientQueryable(), (wherePredicate1, wherePredicate2) -> {
            on.apply(new SQLKtWherePredicateImpl<>(wherePredicate1), new SQLKtWherePredicateImpl<>(wherePredicate2));
        });
        return new EasyKtQueryable2<>(entityQueryable2);
    }

    @Override
    public KtQueryable<T1> union(Collection<KtQueryable<T1>> unionQueries) {
        if (EasyCollectionUtil.isEmpty(unionQueries)) {
            return this;
        }
        ClientQueryable<T1> unionQueryable = entityQueryable.union(EasyCollectionUtil.select(unionQueries, (queryable, i) -> queryable.getClientQueryable()));
        return new EasyKtQueryable<>(unionQueryable);
    }

    @Override
    public KtQueryable<T1> unionAll(Collection<KtQueryable<T1>> unionQueries) {
        if (EasyCollectionUtil.isEmpty(unionQueries)) {
            return this;
        }
        ClientQueryable<T1> unionQueryable = entityQueryable.unionAll(EasyCollectionUtil.select(unionQueries, (queryable, i) -> queryable.getClientQueryable()));
        return new EasyKtQueryable<>(unionQueryable);
    }

    @Override
    public KtQueryable<T1> useLogicDelete(boolean enable) {
        entityQueryable.useLogicDelete(enable);
        return this;
    }

    @Override
    public KtQueryable<T1> noInterceptor() {
        entityQueryable.noInterceptor();
        return this;
    }

    @Override
    public KtQueryable<T1> useInterceptor(String name) {
        entityQueryable.useInterceptor(name);
        return this;
    }

    @Override
    public KtQueryable<T1> noInterceptor(String name) {
        entityQueryable.noInterceptor(name);
        return this;
    }

    @Override
    public KtQueryable<T1> useInterceptor() {
        entityQueryable.useInterceptor();
        return this;
    }

    @Override
    public KtQueryable<T1> asTracking() {
        entityQueryable.asTracking();
        return this;
    }

    @Override
    public KtQueryable<T1> asNoTracking() {
        entityQueryable.asNoTracking();
        return this;
    }

    @Override
    public KtQueryable<T1> asTable(Function<String, String> tableNameAs) {
        entityQueryable.asTable(tableNameAs);
        return this;
    }

    @Override
    public KtQueryable<T1> asSchema(Function<String, String> schemaAs) {
        entityQueryable.asSchema(schemaAs);
        return this;
    }

    @Override
    public KtQueryable<T1> asAlias(String alias) {
        entityQueryable.asAlias(alias);
        return this;
    }

    @Override
    public KtQueryable<T1> asTableLink(Function<String, String> linkAs) {
        entityQueryable.asTableLink(linkAs);
        return this;
    }

    @Override
    public KtQueryable<T1> useShardingConfigure(int maxShardingQueryLimit, ConnectionModeEnum connectionMode) {
        entityQueryable.useShardingConfigure(maxShardingQueryLimit, connectionMode);
        return this;
    }

    @Override
    public KtQueryable<T1> useMaxShardingQueryLimit(int maxShardingQueryLimit) {
        entityQueryable.useMaxShardingQueryLimit(maxShardingQueryLimit);
        return this;
    }

    @Override
    public KtQueryable<T1> useConnectionMode(ConnectionModeEnum connectionMode) {
        entityQueryable.useConnectionMode(connectionMode);
        return this;
    }

    @Override
    public KtQueryable<T1> queryLargeColumn(boolean queryLarge) {
        entityQueryable.queryLargeColumn(queryLarge);
        return this;
    }

    @Override
    public KtQueryable<T1> filterConfigure(ValueFilter valueFilter) {
        entityQueryable.filterConfigure(valueFilter);
        return this;
    }

    @Override
    public MethodQuery<T1> forEach(Consumer<T1> mapConfigure) {
        entityQueryable.forEach(mapConfigure);
        return this;
    }
}
