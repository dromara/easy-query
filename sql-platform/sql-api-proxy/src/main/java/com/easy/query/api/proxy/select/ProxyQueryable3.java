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
import com.easy.query.core.basic.api.select.ClientQueryable3;
import com.easy.query.core.enums.sharding.ConnectionModeEnum;
import com.easy.query.core.expression.lambda.SQLExpression2;
import com.easy.query.core.expression.lambda.SQLExpression4;
import com.easy.query.core.expression.lambda.SQLExpression5;
import com.easy.query.core.expression.lambda.SQLFuncExpression3;
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
public interface ProxyQueryable3<T1Proxy extends ProxyEntity<T1Proxy, T1>, T1,
        T2Proxy extends ProxyEntity<T2Proxy, T2>, T2,
        T3Proxy extends ProxyEntity<T3Proxy, T3>, T3> extends ProxyQueryable<T1Proxy, T1> {

    T2Proxy get2Proxy();

    T3Proxy get3Proxy();

    ClientQueryable3<T1, T2, T3> getClientQueryable3();

    <T4Proxy extends ProxyEntity<T4Proxy, T4>, T4> ProxyQueryable4<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4> leftJoin(T4Proxy joinProxy, SQLExpression5<ProxyFilter, T1Proxy, T2Proxy, T3Proxy, T4Proxy> on);

    <T4Proxy extends ProxyEntity<T4Proxy, T4>, T4> ProxyQueryable4<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4> leftJoin(ProxyQueryable<T4Proxy, T4> joinQueryable, SQLExpression5<ProxyFilter, T1Proxy, T2Proxy, T3Proxy, T4Proxy> on);

    <T4Proxy extends ProxyEntity<T4Proxy, T4>, T4> ProxyQueryable4<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4> rightJoin(T4Proxy joinProxy, SQLExpression5<ProxyFilter, T1Proxy, T2Proxy, T3Proxy, T4Proxy> on);

    <T4Proxy extends ProxyEntity<T4Proxy, T4>, T4> ProxyQueryable4<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4> rightJoin(ProxyQueryable<T4Proxy, T4> joinQueryable, SQLExpression5<ProxyFilter, T1Proxy, T2Proxy, T3Proxy, T4Proxy> on);

    <T4Proxy extends ProxyEntity<T4Proxy, T4>, T4> ProxyQueryable4<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4> innerJoin(T4Proxy joinProxy, SQLExpression5<ProxyFilter, T1Proxy, T2Proxy, T3Proxy, T4Proxy> on);

    <T4Proxy extends ProxyEntity<T4Proxy, T4>, T4> ProxyQueryable4<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4> innerJoin(ProxyQueryable<T4Proxy, T4> joinQueryable, SQLExpression5<ProxyFilter, T1Proxy, T2Proxy, T3Proxy, T4Proxy> on);

    //region where
    default ProxyQueryable3<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3> whereById(Object id) {
        return whereById(true, id);
    }

    ProxyQueryable3<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3> whereById(boolean condition, Object id);

    default ProxyQueryable3<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3> whereObject(Object object) {
        return whereObject(true, object);
    }

    ProxyQueryable3<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3> whereObject(boolean condition, Object object);

    @Override
    default ProxyQueryable3<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3> where(SQLExpression2<ProxyFilter,T1Proxy> whereExpression) {
        return where(true, whereExpression);
    }

    @Override
    ProxyQueryable3<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3> where(boolean condition, SQLExpression2<ProxyFilter,T1Proxy> whereExpression);

    default ProxyQueryable3<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3> where(SQLExpression4<ProxyFilter, T1Proxy, T2Proxy, T3Proxy> whereExpression) {
        return where(true, whereExpression);
    }

    default ProxyQueryable3<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3> where(boolean condition, SQLExpression4<ProxyFilter, T1Proxy, T2Proxy, T3Proxy> whereExpression) {
        if (condition) {
            getClientQueryable3().where((wherePredicate1, wherePredicate2, wherePredicate3) -> {
                whereExpression.apply(new ProxyFilterImpl(wherePredicate3.getFilter()),get1Proxy(), get2Proxy(), get3Proxy());
            });
        }
        return this;
    }

    //endregion

    //region select
    <TRProxy extends ProxyEntity<TRProxy, TR>, TR> ProxyQueryable<TRProxy, TR> select(TRProxy trProxy, SQLExpression4<ProxyAsSelector<TRProxy, TR>, T1Proxy, T2Proxy, T3Proxy> selectExpression);
    //endregion

    //region aggregate

    /**
     * 防止溢出
     *
     * @param columnSelectorExpression
     * @param <TMember>
     * @return
     */
    default <TMember extends Number> BigDecimal sumBigDecimalOrNull(SQLFuncExpression3<T1Proxy, T2Proxy, T3Proxy, SQLColumn<TMember>> columnSelectorExpression) {
        return sumBigDecimalOrDefault(columnSelectorExpression, null);
    }


    <TMember extends Number> BigDecimal sumBigDecimalOrDefault(SQLFuncExpression3<T1Proxy, T2Proxy, T3Proxy, SQLColumn<TMember>> columnSelectorExpression, BigDecimal def);

    default <TMember extends Number> TMember sumOrNull(SQLFuncExpression3<T1Proxy, T2Proxy, T3Proxy, SQLColumn<TMember>> columnSelectorExpression) {
        return sumOrDefault(columnSelectorExpression, null);
    }

    <TMember extends Number> TMember sumOrDefault(SQLFuncExpression3<T1Proxy, T2Proxy, T3Proxy, SQLColumn<TMember>> columnSelectorExpression, TMember def);

    default <TMember> TMember maxOrNull(SQLFuncExpression3<T1Proxy, T2Proxy, T3Proxy, SQLColumn<TMember>> columnSelectorExpression) {
        return maxOrDefault(columnSelectorExpression, null);
    }

    <TMember> TMember maxOrDefault(SQLFuncExpression3<T1Proxy, T2Proxy, T3Proxy, SQLColumn<TMember>> columnSelectorExpression, TMember def);

    default <TMember> TMember minOrNull(SQLFuncExpression3<T1Proxy, T2Proxy, T3Proxy, SQLColumn<TMember>> columnSelectorExpression) {
        return minOrDefault(columnSelectorExpression, null);
    }

    <TMember> TMember minOrDefault(SQLFuncExpression3<T1Proxy, T2Proxy, T3Proxy, SQLColumn<TMember>> columnSelectorExpression, TMember def);

    default <TMember extends Number> Double avgOrNull(SQLFuncExpression3<T1Proxy, T2Proxy, T3Proxy, SQLColumn<TMember>> columnSelectorExpression) {
        return avgOrDefault(columnSelectorExpression, null, Double.class);
    }

    default <TMember extends Number> BigDecimal avgBigDecimalOrNull(SQLFuncExpression3<T1Proxy, T2Proxy, T3Proxy, SQLColumn<TMember>> columnSelectorExpression) {
        return avgBigDecimalOrDefault(columnSelectorExpression, null);
    }

    default <TMember extends Number> Float avgFloatOrNull(SQLFuncExpression3<T1Proxy, T2Proxy, T3Proxy, SQLColumn<TMember>> columnSelectorExpression) {
        return avgFloatOrDefault(columnSelectorExpression, null);
    }

    default <TMember extends Number> Double avgOrDefault(SQLFuncExpression3<T1Proxy, T2Proxy, T3Proxy, SQLColumn<TMember>> columnSelectorExpression, Double def) {
        return avgOrDefault(columnSelectorExpression, def, Double.class);
    }

    default <TMember extends Number> BigDecimal avgBigDecimalOrDefault(SQLFuncExpression3<T1Proxy, T2Proxy, T3Proxy, SQLColumn<TMember>> columnSelectorExpression, BigDecimal def) {
        return avgOrDefault(columnSelectorExpression, def, BigDecimal.class);
    }

    default <TMember extends Number> Float avgFloatOrDefault(SQLFuncExpression3<T1Proxy, T2Proxy, T3Proxy, SQLColumn<TMember>> columnSelectorExpression, Float def) {
        return avgOrDefault(columnSelectorExpression, def, Float.class);
    }

    <TMember extends Number, TResult extends Number> TResult avgOrDefault(SQLFuncExpression3<T1Proxy, T2Proxy, T3Proxy, SQLColumn<TMember>> columnSelectorExpression, TResult def, Class<TResult> resultClass);
    //endregion

    //region group
    @Override
    default ProxyQueryable3<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3> groupBy(SQLExpression2<ProxyGroupSelector, T1Proxy> selectExpression) {
        return groupBy(true, selectExpression);
    }

    @Override
    ProxyQueryable3<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3> groupBy(boolean condition, SQLExpression2<ProxyGroupSelector, T1Proxy> selectExpression);

    default ProxyQueryable3<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3> groupBy(SQLExpression4<ProxyGroupSelector, T1Proxy, T2Proxy, T3Proxy> selectExpression) {
        return groupBy(true, selectExpression);
    }

    default ProxyQueryable3<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3> groupBy(boolean condition, SQLExpression4<ProxyGroupSelector, T1Proxy, T2Proxy, T3Proxy> selectExpression){
        if (condition) {
            getClientQueryable3().groupBy((selector1, selector2, selector3) -> {
                selectExpression.apply(new ProxyGroupSelectorImpl(selector3.getGroupSelector()), get1Proxy(), get2Proxy(),get3Proxy());
            });
        }
        return this;
    }


    @Override
    default ProxyQueryable3<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3> having(SQLExpression2<ProxyAggregateFilter, T1Proxy> predicateExpression) {
        return having(true, predicateExpression);
    }

    @Override
    ProxyQueryable3<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3> having(boolean condition, SQLExpression2<ProxyAggregateFilter, T1Proxy> predicateExpression);

    default ProxyQueryable3<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3> having(SQLExpression4<ProxyAggregateFilter, T1Proxy, T2Proxy, T3Proxy> aggregateFilterExpression) {
        return having(true,aggregateFilterExpression);
    }

    default ProxyQueryable3<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3> having(boolean condition, SQLExpression4<ProxyAggregateFilter, T1Proxy, T2Proxy, T3Proxy> aggregateFilterExpression) {
        if(condition){
            getClientQueryable3().having((predicate1, predicate2, predicate3) -> {
                aggregateFilterExpression.apply(new ProxyAggregateFilterImpl(predicate3.getAggregateFilter()),get1Proxy(),get2Proxy(),get3Proxy());
            });
        }
        return this;
    }

    //endregion
    //region order
    @Override
    default ProxyQueryable3<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3> orderByAsc(SQLExpression2<ProxyOrderSelector, T1Proxy> selectExpression) {
        return orderByAsc(true, selectExpression);
    }

    @Override
    ProxyQueryable3<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3> orderByAsc(boolean condition, SQLExpression2<ProxyOrderSelector, T1Proxy> selectExpression);

    default ProxyQueryable3<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3> orderByAsc(SQLExpression4<ProxyOrderSelector, T1Proxy, T2Proxy, T3Proxy> selectExpression) {
        return orderByAsc(true, selectExpression);
    }

   default ProxyQueryable3<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3> orderByAsc(boolean condition, SQLExpression4<ProxyOrderSelector, T1Proxy, T2Proxy, T3Proxy> selectExpression){
       if(condition){
           getClientQueryable3().orderByAsc((selector1, selector2, selector3) -> {
               selectExpression.apply(new ProxyOrderSelectorImpl(selector3.getOrderSelector()),get1Proxy(),get2Proxy(),get3Proxy());
           });
       }
       return this;
   }

    @Override
    default ProxyQueryable3<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3> orderByDesc(SQLExpression2<ProxyOrderSelector, T1Proxy> selectExpression) {
        return orderByDesc(true, selectExpression);
    }

    @Override
    ProxyQueryable3<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3> orderByDesc(boolean condition, SQLExpression2<ProxyOrderSelector, T1Proxy> selectExpression);

    default ProxyQueryable3<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3> orderByDesc(SQLExpression4<ProxyOrderSelector, T1Proxy, T2Proxy, T3Proxy> selectExpression) {
        return orderByDesc(true, selectExpression);
    }

   default ProxyQueryable3<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3> orderByDesc(boolean condition, SQLExpression4<ProxyOrderSelector, T1Proxy, T2Proxy, T3Proxy> selectExpression){
       if(condition){
           getClientQueryable3().orderByDesc((selector1, selector2, selector3) -> {
               selectExpression.apply(new ProxyOrderSelectorImpl(selector3.getOrderSelector()),get1Proxy(),get2Proxy(),get3Proxy());
           });
       }
       return this;
   }
    //endregion
    //region limit

    @Override
    default ProxyQueryable3<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3> limit(long rows) {
        return limit(true, rows);
    }

    @Override
    default ProxyQueryable3<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3> limit(boolean condition, long rows) {
        return limit(condition, 0, rows);
    }

    @Override
    default ProxyQueryable3<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3> limit(long offset, long rows) {
        return limit(true, offset, rows);
    }

    @Override
    ProxyQueryable3<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3> limit(boolean condition, long offset, long rows);

    default ProxyQueryable3<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3> distinct() {
        return distinct(true);
    }

    ProxyQueryable3<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3> distinct(boolean condition);
    //endregion

    @Override
    ProxyQueryable3<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3> disableLogicDelete();

    @Override
    ProxyQueryable3<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3> enableLogicDelete();

    @Override
    ProxyQueryable3<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3> useLogicDelete(boolean enable);

    @Override
    ProxyQueryable3<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3> noInterceptor();

    @Override
    ProxyQueryable3<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3> useInterceptor(String name);

    @Override
    ProxyQueryable3<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3> noInterceptor(String name);

    @Override
    ProxyQueryable3<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3> useInterceptor();

    /**
     * 自动将查询结果集合全部添加到当前上下文追踪中,如果当前查询结果十分庞大,并且更新数据只有个别条数,建议不要使用
     * 追踪查询，可以通过开启追踪后使用普通的查询，然后添加到当前的追踪上下文中{@link EasyQueryClient#addTracking(Object)},开始先数据追踪的差异更新
     *
     * @return
     */
    @Override
    ProxyQueryable3<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3> asTracking();

    @Override
    ProxyQueryable3<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3> asNoTracking();

    @Override
    ProxyQueryable3<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3> queryLargeColumn(boolean queryLarge);

    @Override
    ProxyQueryable3<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3> useShardingConfigure(int maxShardingQueryLimit, ConnectionModeEnum connectionMode);

    @Override
    ProxyQueryable3<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3> useMaxShardingQueryLimit(int maxShardingQueryLimit);

    @Override
    ProxyQueryable3<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3> useConnectionMode(ConnectionModeEnum connectionMode);

    /**
     * 将当前表达式最近的一张表的表名修改成 {@param tableName}
     * 如果当前最近的表是正常的数据库表名,那么直接将表名改写
     * 如果当前最近的表是匿名表比如嵌套queryable的表那么将alias改成对应的表名
     *
     * @param tableName
     * @return
     */
    @Override
    default ProxyQueryable3<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3> asTable(String tableName) {
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
    ProxyQueryable3<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3> asTable(Function<String, String> tableNameAs);

    @Override
    default ProxyQueryable3<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3> asSchema(String tableName) {
        return asSchema(old -> tableName);
    }

    @Override
    ProxyQueryable3<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3> asSchema(Function<String, String> schemaAs);

    @Override
    ProxyQueryable3<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3> asAlias(String alias);
}
