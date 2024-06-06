package com.easy.query.api4j.select.extension.queryable9.override;

import com.easy.query.api4j.select.Queryable;
import com.easy.query.api4j.select.Queryable9;
import com.easy.query.api4j.select.extension.queryable9.Queryable9Available;
import com.easy.query.api4j.sql.SQLColumnSelector;
import com.easy.query.api4j.sql.SQLGroupBySelector;
import com.easy.query.api4j.sql.SQLNavigateInclude;
import com.easy.query.api4j.sql.SQLOrderBySelector;
import com.easy.query.api4j.sql.SQLWhereAggregatePredicate;
import com.easy.query.api4j.sql.SQLWherePredicate;
import com.easy.query.core.api.client.EasyQueryClient;
import com.easy.query.core.api.dynamic.sort.ObjectSort;
import com.easy.query.core.enums.sharding.ConnectionModeEnum;
import com.easy.query.core.exception.EasyQueryOrderByInvalidOperationException;
import com.easy.query.core.expression.builder.core.ValueFilter;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.lambda.SQLFuncExpression1;
import com.easy.query.core.expression.sql.builder.internal.EasyBehavior;

import java.util.Collection;
import java.util.function.Function;

/**
 * create time 2023/8/16 08:12
 * 文件说明
 *
 * @author xuejiaming
 */
public interface OverrideQueryable9<T1, T2, T3, T4, T5, T6, T7, T8, T9> extends Queryable<T1>, Queryable9Available<T1, T2, T3, T4, T5, T6, T7, T8, T9> {

    @Override
    Queryable9<T1, T2, T3, T4, T5, T6, T7, T8, T9> cloneQueryable();

    @Override
    default Queryable9<T1, T2, T3, T4, T5, T6, T7, T8, T9> whereById(Object id) {
        return whereById(true, id);
    }

    @Override
    Queryable9<T1, T2, T3, T4, T5, T6, T7, T8, T9> whereById(boolean condition, Object id);

    @Override
    default <TProperty> Queryable9<T1, T2, T3, T4, T5, T6, T7, T8, T9> whereByIds(Collection<TProperty> ids) {
        return whereByIds(true, ids);
    }

    @Override
    <TProperty> Queryable9<T1, T2, T3, T4, T5, T6, T7, T8, T9> whereByIds(boolean condition, Collection<TProperty> ids);

    /**
     * 仅支持主表的动态对象查询
     *
     * @param object 对象查询的对象
     * @return
     */
    @Override
    default Queryable9<T1, T2, T3, T4, T5, T6, T7, T8, T9> whereObject(Object object) {
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
    Queryable9<T1, T2, T3, T4, T5, T6, T7, T8, T9> whereObject(boolean condition, Object object);


    @Override
    default Queryable9<T1, T2, T3, T4, T5, T6, T7, T8, T9> where(SQLExpression1<SQLWherePredicate<T1>> whereExpression) {
        return where(true, whereExpression);
    }

    @Override
    Queryable9<T1, T2, T3, T4, T5, T6, T7, T8, T9> where(boolean condition, SQLExpression1<SQLWherePredicate<T1>> whereExpression);

    @Override
    default Queryable9<T1, T2, T3, T4, T5, T6, T7, T8, T9> groupBy(SQLExpression1<SQLGroupBySelector<T1>> selectExpression) {
        return groupBy(true, selectExpression);
    }

    @Override
    Queryable9<T1, T2, T3, T4, T5, T6, T7, T8, T9> groupBy(boolean condition, SQLExpression1<SQLGroupBySelector<T1>> selectExpression);

    @Override
    default Queryable9<T1, T2, T3, T4, T5, T6, T7, T8, T9> having(SQLExpression1<SQLWhereAggregatePredicate<T1>> predicateExpression) {
        return having(true, predicateExpression);
    }

    @Override
    Queryable9<T1, T2, T3, T4, T5, T6, T7, T8, T9> having(boolean condition, SQLExpression1<SQLWhereAggregatePredicate<T1>> predicateExpression);

    @Override
    default Queryable9<T1, T2, T3, T4, T5, T6, T7, T8, T9> orderByAsc(SQLExpression1<SQLOrderBySelector<T1>> selectExpression) {
        return orderByAsc(true, selectExpression);
    }

    @Override
    Queryable9<T1, T2, T3, T4, T5, T6, T7, T8, T9> orderByAsc(boolean condition, SQLExpression1<SQLOrderBySelector<T1>> selectExpression);

    @Override
    default Queryable9<T1, T2, T3, T4, T5, T6, T7, T8, T9> orderByDesc(SQLExpression1<SQLOrderBySelector<T1>> selectExpression) {
        return orderByDesc(true, selectExpression);
    }

    @Override
    Queryable9<T1, T2, T3, T4, T5, T6, T7, T8, T9> orderByDesc(boolean condition, SQLExpression1<SQLOrderBySelector<T1>> selectExpression);

    /**
     * @param configuration
     * @return
     * @throws EasyQueryOrderByInvalidOperationException 当配置{@link ObjectSort} 为{@code  DynamicModeEnum.STRICT}排序设置的属性不存在当前排序对象里面或者当前查询对象无法获取 {@link SQLColumnSelector}
     */
    @Override
    default Queryable9<T1, T2, T3, T4, T5, T6, T7, T8, T9> orderByObject(ObjectSort configuration) {
        return orderByObject(true, configuration);
    }

    /**
     * @param condition
     * @param configuration
     * @return
     * @throws EasyQueryOrderByInvalidOperationException 当配置{@link ObjectSort} 为{@code  DynamicModeEnum.STRICT}排序设置的属性不存在当前排序对象里面或者当前查询对象无法获取 {@link SQLColumnSelector}
     */
    @Override
    Queryable9<T1, T2, T3, T4, T5, T6, T7, T8, T9> orderByObject(boolean condition, ObjectSort configuration);

    @Override
    default <TREntity> Queryable9<T1, T2, T3, T4, T5, T6, T7, T8, T9> include(SQLFuncExpression1<SQLNavigateInclude<T1>, Queryable<TREntity>> navigateIncludeSQLExpression) {
        return include(true, navigateIncludeSQLExpression);
    }

    @Override
    <TREntity> Queryable9<T1, T2, T3, T4, T5, T6, T7, T8, T9> include(boolean condition, SQLFuncExpression1<SQLNavigateInclude<T1>, Queryable<TREntity>> navigateIncludeSQLExpression);

    @Override
    default Queryable9<T1, T2, T3, T4, T5, T6, T7, T8, T9> limit(long rows) {
        return limit(true, rows);
    }

    @Override
    default Queryable9<T1, T2, T3, T4, T5, T6, T7, T8, T9> limit(boolean condition, long rows) {
        return limit(condition, 0, rows);
    }

    @Override
    default Queryable9<T1, T2, T3, T4, T5, T6, T7, T8, T9> limit(long offset, long rows) {
        return limit(true, offset, rows);
    }

    @Override
    Queryable9<T1, T2, T3, T4, T5, T6, T7, T8, T9> limit(boolean condition, long offset, long rows);

    default Queryable9<T1, T2, T3, T4, T5, T6, T7, T8, T9> distinct() {
        return distinct(true);
    }

    @Override
    Queryable9<T1, T2, T3, T4, T5, T6, T7, T8, T9> distinct(boolean condition);

    @Override
    Queryable9<T1, T2, T3, T4, T5, T6, T7, T8, T9> disableLogicDelete();

    @Override
    Queryable9<T1, T2, T3, T4, T5, T6, T7, T8, T9> enableLogicDelete();

    @Override
    Queryable9<T1, T2, T3, T4, T5, T6, T7, T8, T9> useLogicDelete(boolean enable);

    @Override
    Queryable9<T1, T2, T3, T4, T5, T6, T7, T8, T9> noInterceptor();

    @Override
    Queryable9<T1, T2, T3, T4, T5, T6, T7, T8, T9> useInterceptor(String name);

    @Override
    Queryable9<T1, T2, T3, T4, T5, T6, T7, T8, T9> noInterceptor(String name);

    @Override
    Queryable9<T1, T2, T3, T4, T5, T6, T7, T8, T9> useInterceptor();

    /**
     * 自动将查询结果集合全部添加到当前上下文追踪中,如果当前查询结果十分庞大,并且更新数据只有个别条数,建议不要使用
     * 追踪查询，可以通过开启追踪后使用普通的查询，然后添加到当前的追踪上下文中{@link EasyQueryClient#addTracking(Object)},开始先数据追踪的差异更新
     *
     * @return
     */
    @Override
    Queryable9<T1, T2, T3, T4, T5, T6, T7, T8, T9> asTracking();

    @Override
    Queryable9<T1, T2, T3, T4, T5, T6, T7, T8, T9> asNoTracking();

    @Override
    Queryable9<T1, T2, T3, T4, T5, T6, T7, T8, T9> queryLargeColumn(boolean queryLarge);

    @Override
    Queryable9<T1, T2, T3, T4, T5, T6, T7, T8, T9> useShardingConfigure(int maxShardingQueryLimit, ConnectionModeEnum connectionMode);

    @Override
    Queryable9<T1, T2, T3, T4, T5, T6, T7, T8, T9> useMaxShardingQueryLimit(int maxShardingQueryLimit);

    @Override
    Queryable9<T1, T2, T3, T4, T5, T6, T7, T8, T9> useConnectionMode(ConnectionModeEnum connectionMode);

    /**
     * 将当前表达式最近的一张表的表名修改成 {@param tableName}
     * 如果当前最近的表是正常的数据库表名,那么直接将表名改写
     * 如果当前最近的表是匿名表比如嵌套queryable的表那么将alias改成对应的表名
     *
     * @param tableName
     * @return
     */
    @Override
    default Queryable9<T1, T2, T3, T4, T5, T6, T7, T8, T9> asTable(String tableName) {
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
    Queryable9<T1, T2, T3, T4, T5, T6, T7, T8, T9> asTable(Function<String, String> tableNameAs);

    @Override
    default Queryable9<T1, T2, T3, T4, T5, T6, T7, T8, T9> asSchema(String schema) {
        return asSchema(old -> schema);
    }

    @Override
    Queryable9<T1, T2, T3, T4, T5, T6, T7, T8, T9> asSchema(Function<String, String> schemaAs);

    @Override
    Queryable9<T1, T2, T3, T4, T5, T6, T7, T8, T9> asAlias(String alias);

    /**
     * @param linkAs 别名 FROM | LEFT JOIN | RIGHT JOIN
     * @return
     */
    @Override
    default Queryable9<T1, T2, T3, T4, T5, T6, T7, T8, T9> asTableLink(String linkAs) {
        return asTableLink(o -> linkAs);
    }


    @Override
    Queryable9<T1, T2, T3, T4, T5, T6, T7, T8, T9> asTableLink(Function<String, String> linkAs);

    @Override
    Queryable9<T1, T2, T3, T4, T5, T6, T7, T8, T9> filterConfigure(ValueFilter valueFilter);

    @Override
    Queryable9<T1, T2, T3, T4, T5, T6, T7, T8, T9> behaviorConfigure(SQLExpression1<EasyBehavior> configure);
}
