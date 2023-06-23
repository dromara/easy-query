package com.easy.query.api.proxy.select;

import com.easy.query.api.proxy.core.ProxyQuery;
import com.easy.query.api.proxy.core.base.SQLColumn;
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
import com.easy.query.core.basic.api.select.ClientQueryable2;
import com.easy.query.core.enums.sharding.ConnectionModeEnum;
import com.easy.query.core.expression.lambda.SQLExpression2;
import com.easy.query.core.expression.lambda.SQLExpression3;
import com.easy.query.core.expression.lambda.SQLExpression4;
import com.easy.query.core.expression.lambda.SQLFuncExpression2;

import java.math.BigDecimal;
import java.util.function.Function;

/**
 * create time 2023/6/23 15:26
 * 文件说明
 *
 * @author xuejiaming
 */
public interface ProxyQueryable2<T1Proxy extends ProxyQuery<T1Proxy, T1>, T1, T2Proxy extends ProxyQuery<T2Proxy, T2>, T2> extends ProxyQueryable<T1Proxy, T1> {

    T2Proxy get2Proxy();

    ClientQueryable2<T1, T2> getClientQueryable2();

    <T3Proxy extends ProxyQuery<T3Proxy, T3>, T3> ProxyQueryable3<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3> leftJoin(T3Proxy t3Proxy, SQLExpression4<T1Proxy, T2Proxy, T3Proxy, ProxyFilter> on);

    <T3Proxy extends ProxyQuery<T3Proxy, T3>, T3> ProxyQueryable3<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3> leftJoin(ProxyQueryable<T3Proxy, T3> joinQueryable, SQLExpression4<T1Proxy, T2Proxy, T3Proxy, ProxyFilter> on);

    <T3Proxy extends ProxyQuery<T3Proxy, T3>, T3> ProxyQueryable3<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3> rightJoin(T3Proxy t3Proxy, SQLExpression4<T1Proxy, T2Proxy, T3Proxy, ProxyFilter> on);

    <T3Proxy extends ProxyQuery<T3Proxy, T3>, T3> ProxyQueryable3<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3> rightJoin(ProxyQueryable<T3Proxy, T3> joinQueryable, SQLExpression4<T1Proxy, T2Proxy, T3Proxy, ProxyFilter> on);

    <T3Proxy extends ProxyQuery<T3Proxy, T3>, T3> ProxyQueryable3<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3> innerJoin(T3Proxy t3Proxy, SQLExpression4<T1Proxy, T2Proxy, T3Proxy, ProxyFilter> on);

    <T3Proxy extends ProxyQuery<T3Proxy, T3>, T3> ProxyQueryable3<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3> innerJoin(ProxyQueryable<T3Proxy, T3> joinQueryable, SQLExpression4<T1Proxy, T2Proxy, T3Proxy, ProxyFilter> on);

    //region where
    default ProxyQueryable2<T1Proxy, T1, T2Proxy, T2> whereById(Object id) {
        return whereById(true, id);
    }

    ProxyQueryable2<T1Proxy, T1, T2Proxy, T2> whereById(boolean condition, Object id);

    default ProxyQueryable2<T1Proxy, T1, T2Proxy, T2> whereObject(Object object) {
        return whereObject(true, object);
    }

    ProxyQueryable2<T1Proxy, T1, T2Proxy, T2> whereObject(boolean condition, Object object);

    @Override
    default ProxyQueryable2<T1Proxy, T1, T2Proxy, T2> where(SQLExpression2<T1Proxy, ProxyFilter> whereExpression) {
        return where(true, whereExpression);
    }

    @Override
    ProxyQueryable2<T1Proxy, T1, T2Proxy, T2> where(boolean condition, SQLExpression2<T1Proxy, ProxyFilter> whereExpression);

    default ProxyQueryable2<T1Proxy, T1, T2Proxy, T2> where(SQLExpression3<T1Proxy, T2Proxy, ProxyFilter> whereExpression) {
        return where(true,whereExpression);
    }

    default ProxyQueryable2<T1Proxy, T1, T2Proxy, T2> where(boolean condition, SQLExpression3<T1Proxy, T2Proxy, ProxyFilter> whereExpression) {
        if(condition){
            getClientQueryable2().where( (wherePredicate1, wherePredicate2) -> {
                whereExpression.apply(get1Proxy(), get2Proxy(), new ProxyFilterImpl(wherePredicate2.getFilter()));
            });
        }
        return this;
    }

    //endregion

    //region select
    <TRProxy extends ProxyQuery<TRProxy, TR>, TR> ProxyQueryable<TRProxy, TR> select(TRProxy trProxy, SQLExpression3<T1Proxy, T2Proxy, ProxyAsSelector<TRProxy, TR>> selectExpression);
    //endregion
    //region aggregate

    /**
     * 防止溢出
     *
     * @param columnSelectorExpression
     * @param <TMember>
     * @return
     */
    default <TMember extends Number> BigDecimal sumBigDecimalOrNull(SQLFuncExpression2<T1Proxy, T2Proxy, SQLColumn<TMember>> columnSelectorExpression) {

        return sumBigDecimalOrDefault(columnSelectorExpression, null);
    }


    <TMember extends Number> BigDecimal sumBigDecimalOrDefault(SQLFuncExpression2<T1Proxy, T2Proxy, SQLColumn<TMember>> columnSelectorExpression, BigDecimal def);

    default <TMember extends Number> TMember sumOrNull(SQLFuncExpression2<T1Proxy, T2Proxy, SQLColumn<TMember>> columnSelectorExpression) {
        return sumOrDefault(columnSelectorExpression, null);
    }

    <TMember extends Number> TMember sumOrDefault(SQLFuncExpression2<T1Proxy, T2Proxy, SQLColumn<TMember>> columnSelectorExpression, TMember def);

    default <TMember> TMember maxOrNull(SQLFuncExpression2<T1Proxy, T2Proxy, SQLColumn<TMember>> columnSelectorExpression) {
        return maxOrDefault(columnSelectorExpression, null);
    }

    <TMember> TMember maxOrDefault(SQLFuncExpression2<T1Proxy, T2Proxy, SQLColumn<TMember>> columnSelectorExpression, TMember def);

    default <TMember> TMember minOrNull(SQLFuncExpression2<T1Proxy, T2Proxy, SQLColumn<TMember>> columnSelectorExpression) {
        return minOrDefault(columnSelectorExpression, null);
    }

    <TMember> TMember minOrDefault(SQLFuncExpression2<T1Proxy, T2Proxy, SQLColumn<TMember>> columnSelectorExpression, TMember def);

    default <TMember extends Number> Double avgOrNull(SQLFuncExpression2<T1Proxy, T2Proxy, SQLColumn<TMember>> columnSelectorExpression) {
        return avgOrDefault(columnSelectorExpression, null, Double.class);
    }

    default <TMember extends Number> BigDecimal avgBigDecimalOrNull(SQLFuncExpression2<T1Proxy, T2Proxy, SQLColumn<TMember>> columnSelectorExpression) {
        return avgBigDecimalOrDefault(columnSelectorExpression, null);
    }

    default <TMember extends Number> Float avgFloatOrNull(SQLFuncExpression2<T1Proxy, T2Proxy, SQLColumn<TMember>> columnSelectorExpression) {
        return avgFloatOrDefault(columnSelectorExpression, null);
    }

    default <TMember extends Number> Double avgOrDefault(SQLFuncExpression2<T1Proxy, T2Proxy, SQLColumn<TMember>> columnSelectorExpression, Double def){
        return avgOrDefault(columnSelectorExpression, def, Double.class);
    }

    default <TMember extends Number> BigDecimal avgBigDecimalOrDefault(SQLFuncExpression2<T1Proxy, T2Proxy, SQLColumn<TMember>> columnSelectorExpression, BigDecimal def) {
        return avgOrDefault(columnSelectorExpression, def, BigDecimal.class);
    }

    default <TMember extends Number> Float avgFloatOrDefault(SQLFuncExpression2<T1Proxy, T2Proxy, SQLColumn<TMember>> columnSelectorExpression, Float def) {
        return avgOrDefault(columnSelectorExpression, def, Float.class);
    }

    <TMember extends Number, TResult extends Number> TResult avgOrDefault(SQLFuncExpression2<T1Proxy, T2Proxy, SQLColumn<TMember>> columnSelectorExpression, TResult def, Class<TResult> resultClass);

    //endregion

    //region
    @Override
    default ProxyQueryable2<T1Proxy, T1, T2Proxy, T2> groupBy(SQLExpression2<T1Proxy, ProxyGroupSelector> selectExpression) {
        return groupBy(true, selectExpression);
    }

    @Override
    ProxyQueryable2<T1Proxy, T1, T2Proxy, T2> groupBy(boolean condition, SQLExpression2<T1Proxy, ProxyGroupSelector> selectExpression);

    default ProxyQueryable2<T1Proxy, T1, T2Proxy, T2> groupBy(SQLExpression3<T1Proxy, T2Proxy, ProxyGroupSelector> selectExpression) {
        return groupBy(true, selectExpression);
    }

    default ProxyQueryable2<T1Proxy, T1, T2Proxy, T2> groupBy(boolean condition, SQLExpression3<T1Proxy, T2Proxy, ProxyGroupSelector> selectExpression) {
        if (condition) {
            getClientQueryable2().groupBy((selector1, selector2) -> {
                selectExpression.apply(get1Proxy(), get2Proxy(), new ProxyGroupSelectorImpl(selector2.getGroupSelector()));
            });
        }
        return this;
    }

    @Override
    default ProxyQueryable2<T1Proxy, T1, T2Proxy, T2> having(SQLExpression2<T1Proxy, ProxyAggregateFilter> aggregateFilterExpression) {
        return having(true, aggregateFilterExpression);
    }

    @Override
    ProxyQueryable2<T1Proxy, T1, T2Proxy, T2> having(boolean condition, SQLExpression2<T1Proxy, ProxyAggregateFilter> aggregateFilterExpression);

    default ProxyQueryable2<T1Proxy, T1, T2Proxy, T2> having(SQLExpression3<T1Proxy, T2Proxy, ProxyAggregateFilter> aggregateFilterExpression) {
        return having(true,aggregateFilterExpression);
    }

    default ProxyQueryable2<T1Proxy, T1, T2Proxy, T2> having(boolean condition, SQLExpression3<T1Proxy, T2Proxy, ProxyAggregateFilter> aggregateFilterExpression) {
        if(condition){
            getClientQueryable2().having((predicate1, predicate2) -> {
                aggregateFilterExpression.apply(get1Proxy(),get2Proxy(),new ProxyAggregateFilterImpl(predicate2.getAggregateFilter()));
            });
        }
        return this;
    }

    //endregion
    //region order
    @Override
    default ProxyQueryable2<T1Proxy, T1, T2Proxy, T2> orderByAsc(SQLExpression2<T1Proxy, ProxyOrderSelector> selectExpression) {
        return orderByAsc(true, selectExpression);
    }

    @Override
    ProxyQueryable2<T1Proxy, T1, T2Proxy, T2> orderByAsc(boolean condition, SQLExpression2<T1Proxy, ProxyOrderSelector> selectExpression);

    default ProxyQueryable2<T1Proxy, T1, T2Proxy, T2> orderByAsc(SQLExpression3<T1Proxy, T2Proxy, ProxyOrderSelector> selectExpression) {
        return orderByAsc(true,selectExpression);
    }

    default ProxyQueryable2<T1Proxy, T1, T2Proxy, T2> orderByAsc(boolean condition, SQLExpression3<T1Proxy, T2Proxy, ProxyOrderSelector> selectExpression) {
        if(condition){

            getClientQueryable2().orderByAsc((selector1, selector2) -> {
                selectExpression.apply(get1Proxy(),get2Proxy(),new ProxyOrderSelectorImpl(selector2.getOrderSelector()));
            });
        }
        return this;
    }

    @Override
    default ProxyQueryable2<T1Proxy, T1, T2Proxy, T2> orderByDesc(SQLExpression2<T1Proxy, ProxyOrderSelector> selectExpression) {
        return orderByDesc(true, selectExpression);
    }

    @Override
    ProxyQueryable2<T1Proxy, T1, T2Proxy, T2> orderByDesc(boolean condition, SQLExpression2<T1Proxy, ProxyOrderSelector> selectExpression);

    default ProxyQueryable2<T1Proxy, T1, T2Proxy, T2> orderByDesc(SQLExpression3<T1Proxy, T2Proxy, ProxyOrderSelector> selectExpression) {
        return orderByDesc(true,selectExpression);
    }

    default ProxyQueryable2<T1Proxy, T1, T2Proxy, T2> orderByDesc(boolean condition, SQLExpression3<T1Proxy, T2Proxy, ProxyOrderSelector> selectExpression) {
        if(condition){
            getClientQueryable2().orderByDesc((selector1, selector2) -> {
                selectExpression.apply(get1Proxy(),get2Proxy(),new ProxyOrderSelectorImpl(selector2.getOrderSelector()));
            });
        }
        return this;
    }
    //endregion
    //region limit

    @Override
    default ProxyQueryable2<T1Proxy, T1, T2Proxy, T2> limit(long rows) {
        return limit(true, rows);
    }

    @Override
    default ProxyQueryable2<T1Proxy, T1, T2Proxy, T2> limit(boolean condition, long rows) {
        return limit(condition, 0, rows);
    }

    @Override
    default ProxyQueryable2<T1Proxy, T1, T2Proxy, T2> limit(long offset, long rows) {
        return limit(true, offset, rows);
    }

    @Override
    ProxyQueryable2<T1Proxy, T1, T2Proxy, T2> limit(boolean condition, long offset, long rows);

    default ProxyQueryable2<T1Proxy, T1, T2Proxy, T2> distinct() {
        return distinct(true);
    }

    ProxyQueryable2<T1Proxy, T1, T2Proxy, T2> distinct(boolean condition);

    //endregion
    @Override
    ProxyQueryable2<T1Proxy, T1, T2Proxy, T2> disableLogicDelete();

    @Override
    ProxyQueryable2<T1Proxy, T1, T2Proxy, T2> enableLogicDelete();

    @Override
    ProxyQueryable2<T1Proxy, T1, T2Proxy, T2> useLogicDelete(boolean enable);

    @Override
    ProxyQueryable2<T1Proxy, T1, T2Proxy, T2> noInterceptor();

    @Override
    ProxyQueryable2<T1Proxy, T1, T2Proxy, T2> useInterceptor(String name);

    @Override
    ProxyQueryable2<T1Proxy, T1, T2Proxy, T2> noInterceptor(String name);

    @Override
    ProxyQueryable2<T1Proxy, T1, T2Proxy, T2> useInterceptor();

    /**
     * 自动将查询结果集合全部添加到当前上下文追踪中,如果当前查询结果十分庞大,并且更新数据只有个别条数,建议不要使用
     * 追踪查询，可以通过开启追踪后使用普通的查询，然后添加到当前的追踪上下文中{@link EasyQueryClient#addTracking(Object)},开始先数据追踪的差异更新
     *
     * @return
     */
    @Override
    ProxyQueryable2<T1Proxy, T1, T2Proxy, T2> asTracking();

    @Override
    ProxyQueryable2<T1Proxy, T1, T2Proxy, T2> asNoTracking();

    @Override
    ProxyQueryable2<T1Proxy, T1, T2Proxy, T2> queryLargeColumn(boolean queryLarge);

    @Override
    ProxyQueryable2<T1Proxy, T1, T2Proxy, T2> useShardingConfigure(int maxShardingQueryLimit, ConnectionModeEnum connectionMode);

    @Override
    ProxyQueryable2<T1Proxy, T1, T2Proxy, T2> useMaxShardingQueryLimit(int maxShardingQueryLimit);

    @Override
    ProxyQueryable2<T1Proxy, T1, T2Proxy, T2> useConnectionMode(ConnectionModeEnum connectionMode);

    /**
     * 将当前表达式最近的一张表的表名修改成 {@param tableName}
     * 如果当前最近的表是正常的数据库表名,那么直接将表名改写
     * 如果当前最近的表是匿名表比如嵌套queryable的表那么将alias改成对应的表名
     *
     * @param tableName
     * @return
     */
    @Override
    default ProxyQueryable2<T1Proxy, T1, T2Proxy, T2> asTable(String tableName) {
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
    ProxyQueryable2<T1Proxy, T1, T2Proxy, T2> asTable(Function<String, String> tableNameAs);

    @Override
    default ProxyQueryable2<T1Proxy, T1, T2Proxy, T2> asSchema(String tableName) {
        return asSchema(old -> tableName);
    }

    @Override
    ProxyQueryable2<T1Proxy, T1, T2Proxy, T2> asSchema(Function<String, String> schemaAs);

    @Override
    ProxyQueryable2<T1Proxy, T1, T2Proxy, T2> asAlias(String alias);
}
