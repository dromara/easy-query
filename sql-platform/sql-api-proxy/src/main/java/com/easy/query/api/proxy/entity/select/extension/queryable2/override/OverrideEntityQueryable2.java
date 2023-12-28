package com.easy.query.api.proxy.entity.select.extension.queryable2.override;

import com.easy.query.api.proxy.entity.select.EntityQueryable;
import com.easy.query.api.proxy.entity.select.EntityQueryable2;
import com.easy.query.api.proxy.entity.select.extension.queryable2.EntityQueryable2Available;
import com.easy.query.api.proxy.sql.ProxyEntityNavigateInclude;
import com.easy.query.core.api.client.EasyQueryClient;
import com.easy.query.core.enums.sharding.ConnectionModeEnum;
import com.easy.query.core.expression.builder.core.ValueFilter;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.lambda.SQLFuncExpression1;
import com.easy.query.core.expression.lambda.SQLFuncExpression2;
import com.easy.query.core.proxy.ProxyEntity;
import com.easy.query.core.proxy.ProxyEntityAvailable;
import com.easy.query.core.proxy.SQLGroupByExpression;

import java.util.Collection;
import java.util.function.Function;

/**
 * create time 2023/8/16 08:12
 * 文件说明
 *
 * @author xuejiaming
 */
public interface OverrideEntityQueryable2<T1Proxy extends ProxyEntity<T1Proxy, T1>, T1, T2Proxy extends ProxyEntity<T2Proxy, T2>, T2> extends EntityQueryable<T1Proxy, T1>, EntityQueryable2Available<T1Proxy, T1, T2Proxy, T2> {

    @Override
    EntityQueryable2<T1Proxy, T1, T2Proxy, T2> cloneQueryable();

    @Override
    default EntityQueryable2<T1Proxy, T1, T2Proxy, T2> whereById(Object id) {
        return whereById(true, id);
    }

    @Override
    EntityQueryable2<T1Proxy, T1, T2Proxy, T2> whereById(boolean condition, Object id);

    @Override
    <TProperty> EntityQueryable2<T1Proxy, T1, T2Proxy, T2> whereByIds(boolean condition, Collection<TProperty> ids);

    /**
     * 仅支持主表的动态对象查询
     *
     * @param object 对象查询的对象
     * @return
     */
    @Override
    default EntityQueryable2<T1Proxy, T1, T2Proxy, T2> whereObject(Object object) {
        return whereObject(true, object);
    }

    /**
     * 仅支持主表的动态对象查询
     *
     * @param condition 是否使用对象查询
     * @param object    对象查询的对象
     * @return
     */
    @Override
    EntityQueryable2<T1Proxy, T1, T2Proxy, T2> whereObject(boolean condition, Object object);


    @Override
    default EntityQueryable2<T1Proxy, T1, T2Proxy, T2> where(SQLExpression1<T1Proxy> whereExpression) {
        return where(true, whereExpression);
    }

    @Override
    EntityQueryable2<T1Proxy, T1, T2Proxy, T2> where(boolean condition, SQLExpression1<T1Proxy> whereExpression);

    @Override
    default EntityQueryable2<T1Proxy, T1, T2Proxy, T2> groupByFlat(SQLFuncExpression1<T1Proxy, SQLGroupByExpression> selectExpression) {
        return groupByFlat(true, selectExpression);
    }

    @Override
    EntityQueryable2<T1Proxy, T1, T2Proxy, T2> groupByFlat(boolean condition, SQLFuncExpression1<T1Proxy, SQLGroupByExpression> selectExpression);

    @Override
    default EntityQueryable2<T1Proxy, T1, T2Proxy, T2> having(SQLExpression1<T1Proxy> predicateExpression) {
        return having(true, predicateExpression);
    }

    @Override
    EntityQueryable2<T1Proxy, T1, T2Proxy, T2> having(boolean condition, SQLExpression1<T1Proxy> predicateExpression);

    @Override
    default EntityQueryable2<T1Proxy, T1, T2Proxy, T2> orderBy(SQLExpression1<T1Proxy> selectExpression) {
        return orderBy(true, selectExpression);
    }

    @Override
    EntityQueryable2<T1Proxy, T1, T2Proxy, T2> orderBy(boolean condition, SQLExpression1<T1Proxy> selectExpression);

    @Override
    default <TPropertyProxy extends ProxyEntity<TPropertyProxy, TProperty>, TProperty extends ProxyEntityAvailable<TProperty, TPropertyProxy>> EntityQueryable2<T1Proxy, T1, T2Proxy, T2> include(SQLFuncExpression2<ProxyEntityNavigateInclude<T1, T1Proxy>, T1Proxy, EntityQueryable<TPropertyProxy, TProperty>> navigateIncludeSQLExpression) {
        return include(true, navigateIncludeSQLExpression);
    }

    @Override
    <TPropertyProxy extends ProxyEntity<TPropertyProxy, TProperty>, TProperty extends ProxyEntityAvailable<TProperty, TPropertyProxy>> EntityQueryable2<T1Proxy, T1, T2Proxy, T2> include(boolean condition, SQLFuncExpression2<ProxyEntityNavigateInclude<T1, T1Proxy>, T1Proxy, EntityQueryable<TPropertyProxy, TProperty>> navigateIncludeSQLExpression);

    @Override
    default EntityQueryable2<T1Proxy, T1, T2Proxy, T2> limit(long rows) {
        return limit(true, rows);
    }

    @Override
    default EntityQueryable2<T1Proxy, T1, T2Proxy, T2> limit(boolean condition, long rows) {
        return limit(condition, 0, rows);
    }

    @Override
    default EntityQueryable2<T1Proxy, T1, T2Proxy, T2> limit(long offset, long rows) {
        return limit(true, offset, rows);
    }

    @Override
    EntityQueryable2<T1Proxy, T1, T2Proxy, T2> limit(boolean condition, long offset, long rows);

    default EntityQueryable2<T1Proxy, T1, T2Proxy, T2> distinct() {
        return distinct(true);
    }

    @Override
    EntityQueryable2<T1Proxy, T1, T2Proxy, T2> distinct(boolean condition);

    @Override
    EntityQueryable2<T1Proxy, T1, T2Proxy, T2> disableLogicDelete();

    @Override
    EntityQueryable2<T1Proxy, T1, T2Proxy, T2> enableLogicDelete();

    @Override
    EntityQueryable2<T1Proxy, T1, T2Proxy, T2> useLogicDelete(boolean enable);

    @Override
    EntityQueryable2<T1Proxy, T1, T2Proxy, T2> noInterceptor();

    @Override
    EntityQueryable2<T1Proxy, T1, T2Proxy, T2> useInterceptor(String name);

    @Override
    EntityQueryable2<T1Proxy, T1, T2Proxy, T2> noInterceptor(String name);

    @Override
    EntityQueryable2<T1Proxy, T1, T2Proxy, T2> useInterceptor();

    /**
     * 自动将查询结果集合全部添加到当前上下文追踪中,如果当前查询结果十分庞大,并且更新数据只有个别条数,建议不要使用
     * 追踪查询，可以通过开启追踪后使用普通的查询，然后添加到当前的追踪上下文中{@link EasyQueryClient#addTracking(Object)},开始先数据追踪的差异更新
     *
     * @return
     */
    @Override
    EntityQueryable2<T1Proxy, T1, T2Proxy, T2> asTracking();

    @Override
    EntityQueryable2<T1Proxy, T1, T2Proxy, T2> asNoTracking();

    @Override
    EntityQueryable2<T1Proxy, T1, T2Proxy, T2> queryLargeColumn(boolean queryLarge);

    @Override
    EntityQueryable2<T1Proxy, T1, T2Proxy, T2> useShardingConfigure(int maxShardingQueryLimit, ConnectionModeEnum connectionMode);

    @Override
    EntityQueryable2<T1Proxy, T1, T2Proxy, T2> useMaxShardingQueryLimit(int maxShardingQueryLimit);

    @Override
    EntityQueryable2<T1Proxy, T1, T2Proxy, T2> useConnectionMode(ConnectionModeEnum connectionMode);

    /**
     * 将当前表达式最近的一张表的表名修改成 {@param tableName}
     * 如果当前最近的表是正常的数据库表名,那么直接将表名改写
     * 如果当前最近的表是匿名表比如嵌套queryable的表那么将alias改成对应的表名
     *
     * @param tableName
     * @return
     */
    @Override
    default EntityQueryable2<T1Proxy, T1, T2Proxy, T2> asTable(String tableName) {
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
    EntityQueryable2<T1Proxy, T1, T2Proxy, T2> asTable(Function<String, String> tableNameAs);

    @Override
    default EntityQueryable2<T1Proxy, T1, T2Proxy, T2> asSchema(String schema) {
        return asSchema(old -> schema);
    }

    @Override
    EntityQueryable2<T1Proxy, T1, T2Proxy, T2> asSchema(Function<String, String> schemaAs);

    @Override
    EntityQueryable2<T1Proxy, T1, T2Proxy, T2> asAlias(String alias);

    /**
     * @param linkAs 别名 FROM | LEFT JOIN | RIGHT JOIN
     * @return
     */
    @Override
    default EntityQueryable2<T1Proxy, T1, T2Proxy, T2> asTableLink(String linkAs) {
        return asTableLink(o -> linkAs);
    }


    @Override
    EntityQueryable2<T1Proxy, T1, T2Proxy, T2> asTableLink(Function<String, String> linkAs);

    @Override
    EntityQueryable2<T1Proxy, T1, T2Proxy, T2> filterConfigure(ValueFilter valueFilter);
}
