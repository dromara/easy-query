package com.easy.query.api.proxy.entity.select.extension.queryable4.override;

import com.easy.query.api.proxy.entity.select.EntityQueryable;
import com.easy.query.api.proxy.entity.select.EntityQueryable4;
import com.easy.query.api.proxy.entity.select.extension.queryable4.EntityQueryable4Available;
import com.easy.query.core.api.client.EasyQueryClient;
import com.easy.query.core.api.dynamic.sort.ObjectSort;
import com.easy.query.core.enums.sharding.ConnectionModeEnum;
import com.easy.query.core.exception.EasyQueryOrderByInvalidOperationException;
import com.easy.query.core.expression.builder.core.ValueFilter;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.sql.builder.internal.EasyBehavior;
import com.easy.query.core.proxy.ProxyEntity;

import java.util.Collection;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * create time 2023/8/16 08:12
 * 文件说明
 *
 * @author xuejiaming
 */
public interface OverrideEntityQueryable4<T1Proxy extends ProxyEntity<T1Proxy, T1>, T1,
        T2Proxy extends ProxyEntity<T2Proxy, T2>, T2,
        T3Proxy extends ProxyEntity<T3Proxy, T3>, T3,
        T4Proxy extends ProxyEntity<T4Proxy, T4>, T4> extends EntityQueryable<T1Proxy, T1>,
        EntityQueryable4Available<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4> {

    @Override
    EntityQueryable4<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4> cloneQueryable();

    @Override
    default EntityQueryable4<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4> whereById(Object id) {
        return whereById(true, id);
    }

    @Override
    EntityQueryable4<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4> whereById(boolean condition, Object id);

    @Override
    <TProperty> EntityQueryable4<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4> whereByIds(boolean condition, Collection<TProperty> ids);

    /**
     * 仅支持主表的动态对象查询
     *
     * @param object 对象查询的对象
     * @return
     */
    @Override
    default EntityQueryable4<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4> whereObject(Object object) {
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
    EntityQueryable4<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4> whereObject(boolean condition, Object object);


    @Override
    default EntityQueryable4<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4> where(SQLExpression1<T1Proxy> whereExpression) {
        return where(true, whereExpression);
    }

    @Override
    EntityQueryable4<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4> where(boolean condition, SQLExpression1<T1Proxy> whereExpression);

    @Override
    default EntityQueryable4<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4> having(SQLExpression1<T1Proxy> predicateExpression) {
        return having(true, predicateExpression);
    }

    @Override
    EntityQueryable4<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4> having(boolean condition, SQLExpression1<T1Proxy> predicateExpression);

    @Override
    default EntityQueryable4<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4> orderBy(SQLExpression1<T1Proxy> selectExpression) {
        return orderBy(true, selectExpression);
    }

    @Override
    EntityQueryable4<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4> orderBy(boolean condition, SQLExpression1<T1Proxy> selectExpression);


    /**
     * @param objectSort
     * @return
     * @throws EasyQueryOrderByInvalidOperationException 当配置{@link ObjectSort} 为{@code  DynamicModeEnum.STRICT}排序设置的属性不存在当前排序对象里面或者当前查询对象无法获取
     */
    @Override
    default EntityQueryable4<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4> orderByObject(ObjectSort objectSort) {
        return orderByObject(true, objectSort);
    }

    /**
     * @param condition
     * @param objectSort
     * @return
     * @throws EasyQueryOrderByInvalidOperationException 当配置{@link ObjectSort} 为{@code  DynamicModeEnum.STRICT}排序设置的属性不存在当前排序对象里面或者当前查询对象无法获取
     */
    @Override
    EntityQueryable4<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4> orderByObject(boolean condition, ObjectSort objectSort);

    @Override
    default EntityQueryable4<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4> limit(long rows) {
        return limit(true, rows);
    }

    @Override
    default EntityQueryable4<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4> limit(boolean condition, long rows) {
        return limit(condition, 0, rows);
    }

    @Override
    default EntityQueryable4<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4> limit(long offset, long rows) {
        return limit(true, offset, rows);
    }

    @Override
    EntityQueryable4<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4> limit(boolean condition, long offset, long rows);

    default EntityQueryable4<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4> distinct() {
        return distinct(true);
    }

    @Override
    EntityQueryable4<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4> distinct(boolean condition);

    @Override
    EntityQueryable4<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4> disableLogicDelete();

    @Override
    EntityQueryable4<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4> enableLogicDelete();

    @Override
    EntityQueryable4<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4> useLogicDelete(boolean enable);

    @Override
    EntityQueryable4<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4> noInterceptor();

    @Override
    EntityQueryable4<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4> useInterceptor(String name);

    @Override
    EntityQueryable4<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4> noInterceptor(String name);

    @Override
    EntityQueryable4<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4> useInterceptor();

    /**
     * 自动将查询结果集合全部添加到当前上下文追踪中,如果当前查询结果十分庞大,并且更新数据只有个别条数,建议不要使用
     * 追踪查询，可以通过开启追踪后使用普通的查询，然后添加到当前的追踪上下文中{@link EasyQueryClient#addTracking(Object)},开始先数据追踪的差异更新
     *
     * @return
     */
    @Override
    EntityQueryable4<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4> asTracking();

    @Override
    EntityQueryable4<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4> asNoTracking();

    @Override
    EntityQueryable4<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4> queryLargeColumn(boolean queryLarge);

    @Override
    EntityQueryable4<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4> useShardingConfigure(int maxShardingQueryLimit, ConnectionModeEnum connectionMode);

    @Override
    EntityQueryable4<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4> useMaxShardingQueryLimit(int maxShardingQueryLimit);

    @Override
    EntityQueryable4<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4> useConnectionMode(ConnectionModeEnum connectionMode);

    /**
     * 将当前表达式最近的一张表的表名修改成 {@param tableName}
     * 如果当前最近的表是正常的数据库表名,那么直接将表名改写
     * 如果当前最近的表是匿名表比如嵌套queryable的表那么将alias改成对应的表名
     *
     * @param tableName
     * @return
     */
    @Override
    default EntityQueryable4<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4> asTable(String tableName) {
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
    EntityQueryable4<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4> asTable(Function<String, String> tableNameAs);

    @Override
    default EntityQueryable4<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4> asSchema(String schema) {
        return asSchema(old -> schema);
    }

    @Override
    EntityQueryable4<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4> asSchema(Function<String, String> schemaAs);

    @Override
    EntityQueryable4<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4> asAlias(String alias);

    /**
     * @param linkAs 别名 FROM | LEFT JOIN | RIGHT JOIN
     * @return
     */
    @Override
    default EntityQueryable4<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4> asTableLink(String linkAs) {
        return asTableLink(o -> linkAs);
    }


    @Override
    EntityQueryable4<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4> asTableLink(Function<String, String> linkAs);

    @Override
    EntityQueryable4<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4> filterConfigure(ValueFilter valueFilter);

    @Override
    EntityQueryable4<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4> tableLogicDelete(Supplier<Boolean> tableLogicDel);
    @Override
    EntityQueryable4<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4> behaviorConfigure(SQLExpression1<EasyBehavior> configure);

}
