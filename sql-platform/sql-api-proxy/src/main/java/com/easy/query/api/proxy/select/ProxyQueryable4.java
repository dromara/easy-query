package com.easy.query.api.proxy.select;

import com.easy.query.api.proxy.sql.ProxyAggregateFilter;
import com.easy.query.api.proxy.sql.ProxyAsSelector;
import com.easy.query.api.proxy.sql.ProxyFilter;
import com.easy.query.api.proxy.sql.ProxyGroupSelector;
import com.easy.query.api.proxy.sql.ProxyOrderSelector;
import com.easy.query.api.proxy.sql.impl.ProxyAggregateFilterImpl;
import com.easy.query.api.proxy.sql.impl.ProxyFilterImpl;
import com.easy.query.api.proxy.sql.impl.ProxyGroupSelectorImpl;
import com.easy.query.api.proxy.sql.impl.ProxyOrderSelectorImpl;
import com.easy.query.core.api.client.EasyQueryClient;
import com.easy.query.core.basic.api.select.ClientQueryable4;
import com.easy.query.core.enums.sharding.ConnectionModeEnum;
import com.easy.query.core.expression.lambda.SQLExpression2;
import com.easy.query.core.expression.lambda.SQLExpression5;
import com.easy.query.core.expression.lambda.SQLFuncExpression4;
import com.easy.query.core.proxy.ProxyEntity;
import com.easy.query.core.proxy.SQLColumn;

import java.math.BigDecimal;
import java.util.function.Function;

/**
 * create time 2023/6/23 15:26
 * 文件说明
 *
 * @author xuejiaming
 */
public interface ProxyQueryable4<T1Proxy extends ProxyEntity<T1Proxy, T1>,
        T1, T2Proxy extends ProxyEntity<T2Proxy, T2>, T2,
        T3Proxy extends ProxyEntity<T3Proxy, T3>, T3,
        T4Proxy extends ProxyEntity<T4Proxy, T4>, T4> extends ProxyQueryable<T1Proxy, T1> {

    T2Proxy get2Proxy();

    T3Proxy get3Proxy();

    T4Proxy get4Proxy();

    ClientQueryable4<T1, T2, T3, T4> getClientQueryable4();

    //region where
    default ProxyQueryable4<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4> whereById(Object id) {
        return whereById(true, id);
    }

    ProxyQueryable4<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4> whereById(boolean condition, Object id);

    default ProxyQueryable4<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4> whereObject(Object object) {
        return whereObject(true, object);
    }

    ProxyQueryable4<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4> whereObject(boolean condition, Object object);

    @Override
    default ProxyQueryable4<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4> where(SQLExpression2<ProxyFilter, T1Proxy> whereExpression) {
        return where(true, whereExpression);
    }

    @Override
    ProxyQueryable4<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4> where(boolean condition, SQLExpression2<ProxyFilter, T1Proxy> whereExpression);

    default ProxyQueryable4<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4> where(SQLExpression5<ProxyFilter, T1Proxy, T2Proxy, T3Proxy, T4Proxy> whereExpression) {
        return where(true, whereExpression);
    }

    default ProxyQueryable4<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4> where(boolean condition, SQLExpression5<ProxyFilter, T1Proxy, T2Proxy, T3Proxy, T4Proxy> whereExpression) {
        if (condition) {
            getClientQueryable4().where((wherePredicate1, wherePredicate2, wherePredicate3, wherePredicate4) -> {
                whereExpression.apply(new ProxyFilterImpl(wherePredicate2.getFilter()), get1Proxy(), get2Proxy(), get3Proxy(), get4Proxy());
            });
        }
        return this;
    }

    //endregion
    //region select
    <TRProxy extends ProxyEntity<TRProxy, TR>, TR> ProxyQueryable<TRProxy, TR> select(TRProxy trProxy, SQLExpression5<ProxyAsSelector<TRProxy, TR>, T1Proxy, T2Proxy, T3Proxy, T4Proxy> selectExpression);
    //endregion

    //region aggregate

    /**
     * 防止溢出
     *
     * @param columnSelectorExpression
     * @param <TMember>
     * @return
     */
    default <TMember extends Number> BigDecimal sumBigDecimalOrNull(SQLFuncExpression4<T1Proxy, T2Proxy, T3Proxy, T4Proxy, SQLColumn<TMember>> columnSelectorExpression) {
        return sumBigDecimalOrDefault(columnSelectorExpression, null);
    }


    <TMember extends Number> BigDecimal sumBigDecimalOrDefault(SQLFuncExpression4<T1Proxy, T2Proxy, T3Proxy, T4Proxy, SQLColumn<TMember>> columnSelectorExpression, BigDecimal def);

    default <TMember extends Number> TMember sumOrNull(SQLFuncExpression4<T1Proxy, T2Proxy, T3Proxy, T4Proxy, SQLColumn<TMember>> columnSelectorExpression) {
        return sumOrDefault(columnSelectorExpression, null);
    }

    <TMember extends Number> TMember sumOrDefault(SQLFuncExpression4<T1Proxy, T2Proxy, T3Proxy, T4Proxy, SQLColumn<TMember>> columnSelectorExpression, TMember def);

    default <TMember> TMember maxOrNull(SQLFuncExpression4<T1Proxy, T2Proxy, T3Proxy, T4Proxy, SQLColumn<TMember>> columnSelectorExpression) {
        return maxOrDefault(columnSelectorExpression, null);
    }

    <TMember> TMember maxOrDefault(SQLFuncExpression4<T1Proxy, T2Proxy, T3Proxy, T4Proxy, SQLColumn<TMember>> columnSelectorExpression, TMember def);

    default <TMember> TMember minOrNull(SQLFuncExpression4<T1Proxy, T2Proxy, T3Proxy, T4Proxy, SQLColumn<TMember>> columnSelectorExpression) {
        return minOrDefault(columnSelectorExpression, null);
    }

    <TMember> TMember minOrDefault(SQLFuncExpression4<T1Proxy, T2Proxy, T3Proxy, T4Proxy, SQLColumn<TMember>> columnSelectorExpression, TMember def);

    default <TMember extends Number> Double avgOrNull(SQLFuncExpression4<T1Proxy, T2Proxy, T3Proxy, T4Proxy, SQLColumn<TMember>> columnSelectorExpression) {
        return avgOrDefault(columnSelectorExpression, null, Double.class);
    }

    default <TMember extends Number> BigDecimal avgBigDecimalOrNull(SQLFuncExpression4<T1Proxy, T2Proxy, T3Proxy, T4Proxy, SQLColumn<TMember>> columnSelectorExpression) {
        return avgBigDecimalOrDefault(columnSelectorExpression, null);
    }

    default <TMember extends Number> Float avgFloatOrNull(SQLFuncExpression4<T1Proxy, T2Proxy, T3Proxy, T4Proxy, SQLColumn<TMember>> columnSelectorExpression) {
        return avgFloatOrDefault(columnSelectorExpression, null);
    }

    default <TMember extends Number> Double avgOrDefault(SQLFuncExpression4<T1Proxy, T2Proxy, T3Proxy, T4Proxy, SQLColumn<TMember>> columnSelectorExpression, Double def) {
        return avgOrDefault(columnSelectorExpression, def, Double.class);
    }

    default <TMember extends Number> BigDecimal avgBigDecimalOrDefault(SQLFuncExpression4<T1Proxy, T2Proxy, T3Proxy, T4Proxy, SQLColumn<TMember>> columnSelectorExpression, BigDecimal def) {
        return avgOrDefault(columnSelectorExpression, def, BigDecimal.class);
    }

    default <TMember extends Number> Float avgFloatOrDefault(SQLFuncExpression4<T1Proxy, T2Proxy, T3Proxy, T4Proxy, SQLColumn<TMember>> columnSelectorExpression, Float def) {
        return avgOrDefault(columnSelectorExpression, def, Float.class);
    }

    <TMember extends Number, TResult extends Number> TResult avgOrDefault(SQLFuncExpression4<T1Proxy, T2Proxy, T3Proxy, T4Proxy, SQLColumn<TMember>> columnSelectorExpression, TResult def, Class<TResult> resultClass);
    //endregion


    //region group
    @Override
    default ProxyQueryable4<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4> groupBy(SQLExpression2<ProxyGroupSelector, T1Proxy> selectExpression) {
        return groupBy(true, selectExpression);
    }

    @Override
    ProxyQueryable4<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4> groupBy(boolean condition, SQLExpression2<ProxyGroupSelector, T1Proxy> selectExpression);

    default ProxyQueryable4<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4> groupBy(SQLExpression5<ProxyGroupSelector, T1Proxy, T2Proxy, T3Proxy, T4Proxy> selectExpression) {
        return groupBy(true, selectExpression);
    }

    default ProxyQueryable4<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4> groupBy(boolean condition, SQLExpression5<ProxyGroupSelector, T1Proxy, T2Proxy, T3Proxy, T4Proxy> selectExpression) {
        if (condition) {
            getClientQueryable4().groupBy((selector1, selector2, selector3, selector4) -> {
                selectExpression.apply(new ProxyGroupSelectorImpl(selector4.getGroupSelector()), get1Proxy(), get2Proxy(), get3Proxy(), get4Proxy());
            });
        }
        return this;
    }


    @Override
    default ProxyQueryable4<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4> having(SQLExpression2<ProxyAggregateFilter, T1Proxy> predicateExpression) {
        return having(true, predicateExpression);
    }

    @Override
    ProxyQueryable4<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4> having(boolean condition, SQLExpression2<ProxyAggregateFilter, T1Proxy> predicateExpression);

    default ProxyQueryable4<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4> having(SQLExpression5<ProxyAggregateFilter, T1Proxy, T2Proxy, T3Proxy, T4Proxy> aggregateFilterExpression) {
        return having(true, aggregateFilterExpression);
    }

    default ProxyQueryable4<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4> having(boolean condition, SQLExpression5<ProxyAggregateFilter, T1Proxy, T2Proxy, T3Proxy, T4Proxy> aggregateFilterExpression) {
        if (condition) {
            getClientQueryable4().having((predicate1, predicate2, predicate3, predicate4) -> {
                aggregateFilterExpression.apply(new ProxyAggregateFilterImpl(predicate4.getAggregateFilter()), get1Proxy(), get2Proxy(), get3Proxy(), get4Proxy());
            });
        }
        return this;
    }

    //endregion
    //region order
    @Override
    default ProxyQueryable4<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4> orderByAsc(SQLExpression2<ProxyOrderSelector, T1Proxy> selectExpression) {
        return orderByAsc(true, selectExpression);
    }

    @Override
    ProxyQueryable4<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4> orderByAsc(boolean condition, SQLExpression2<ProxyOrderSelector, T1Proxy> selectExpression);

    default ProxyQueryable4<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4> orderByAsc(SQLExpression5<ProxyOrderSelector, T1Proxy, T2Proxy, T3Proxy, T4Proxy> selectExpression) {
        return orderByAsc(true, selectExpression);
    }

    default ProxyQueryable4<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4> orderByAsc(boolean condition, SQLExpression5<ProxyOrderSelector, T1Proxy, T2Proxy, T3Proxy, T4Proxy> selectExpression) {

        if (condition) {
            getClientQueryable4().orderByAsc((selector1, selector2, selector3, selector4) -> {
                selectExpression.apply(new ProxyOrderSelectorImpl(selector4.getOrderSelector()), get1Proxy(), get2Proxy(), get3Proxy(), get4Proxy());
            });
        }
        return this;
    }

    @Override
    default ProxyQueryable4<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4> orderByDesc(SQLExpression2<ProxyOrderSelector, T1Proxy> selectExpression) {
        return orderByDesc(true, selectExpression);
    }

    @Override
    ProxyQueryable4<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4> orderByDesc(boolean condition, SQLExpression2<ProxyOrderSelector, T1Proxy> selectExpression);

    default ProxyQueryable4<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4> orderByDesc(SQLExpression5<ProxyOrderSelector, T1Proxy, T2Proxy, T3Proxy, T4Proxy> selectExpression) {
        return orderByDesc(true, selectExpression);
    }

    default ProxyQueryable4<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4> orderByDesc(boolean condition, SQLExpression5<ProxyOrderSelector, T1Proxy, T2Proxy, T3Proxy, T4Proxy> selectExpression) {
        if (condition) {
            getClientQueryable4().orderByDesc((selector1, selector2, selector3, selector4) -> {
                selectExpression.apply(new ProxyOrderSelectorImpl(selector4.getOrderSelector()), get1Proxy(), get2Proxy(), get3Proxy(), get4Proxy());
            });
        }
        return this;
    }
    //endregion
    //region limit

    @Override
    default ProxyQueryable4<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4> limit(long rows) {
        return limit(true, rows);
    }

    @Override
    default ProxyQueryable4<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4> limit(boolean condition, long rows) {
        return limit(condition, 0, rows);
    }

    @Override
    default ProxyQueryable4<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4> limit(long offset, long rows) {
        return limit(true, offset, rows);
    }

    @Override
    ProxyQueryable4<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4> limit(boolean condition, long offset, long rows);

    default ProxyQueryable4<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4> distinct() {
        return distinct(true);
    }

    ProxyQueryable4<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4> distinct(boolean condition);
    //endregion

    @Override
    ProxyQueryable4<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4> disableLogicDelete();

    @Override
    ProxyQueryable4<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4> enableLogicDelete();

    @Override
    ProxyQueryable4<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4> useLogicDelete(boolean enable);

    @Override
    ProxyQueryable4<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4> noInterceptor();

    @Override
    ProxyQueryable4<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4> useInterceptor();

    /**
     * 自动将查询结果集合全部添加到当前上下文追踪中,如果当前查询结果十分庞大,并且更新数据只有个别条数,建议不要使用
     * 追踪查询，可以通过开启追踪后使用普通的查询，然后添加到当前的追踪上下文中{@link EasyQueryClient#addTracking(Object)},开始先数据追踪的差异更新
     *
     * @return
     */
    @Override
    ProxyQueryable4<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4> asTracking();

    @Override
    ProxyQueryable4<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4> asNoTracking();

    @Override
    ProxyQueryable4<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4> queryLargeColumn(boolean queryLarge);

    @Override
    ProxyQueryable4<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4> useShardingConfigure(int maxShardingQueryLimit, ConnectionModeEnum connectionMode);

    @Override
    ProxyQueryable4<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4> useMaxShardingQueryLimit(int maxShardingQueryLimit);

    @Override
    ProxyQueryable4<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4> useConnectionMode(ConnectionModeEnum connectionMode);

    /**
     * 将当前表达式最近的一张表的表名修改成 {@param tableName}
     * 如果当前最近的表是正常的数据库表名,那么直接将表名改写
     * 如果当前最近的表是匿名表比如嵌套queryable的表那么将alias改成对应的表名
     *
     * @param tableName
     * @return
     */
    @Override
    default ProxyQueryable4<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4> asTable(String tableName) {
        return asTable(old -> tableName);
    }

    /**
     * 将当前表达式最近的一张表的表名修改成 {@param tableNameAs}返回的表名
     * 如果当前最近的表是正常的数据库表名,那么直接将表名改写
     * 如果当前最近的表是匿名表比如嵌套queryable的表那么将alias改成对应的表名
     *
     * @param tableNameAs
     * @return
     */
    @Override
    ProxyQueryable4<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4> asTable(Function<String, String> tableNameAs);

    @Override
    default ProxyQueryable4<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4> asSchema(String tableName) {
        return asSchema(old -> tableName);
    }

    @Override
    ProxyQueryable4<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4> asSchema(Function<String, String> schemaAs);

    @Override
    ProxyQueryable4<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4> asAlias(String alias);
    @Override
    default ProxyQueryable4<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4> asTableLink(String linkAs) {
        return asTableLink(o->linkAs);
    }

    @Override
    ProxyQueryable4<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4> asTableLink(Function<String, String> linkAs);
}
