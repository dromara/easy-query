package com.easy.query.api4j.select.extension.queryable3.override;

import com.easy.query.api4j.select.Queryable;
import com.easy.query.api4j.select.Queryable3;
import com.easy.query.api4j.select.extension.queryable3.Queryable3Available;
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
import com.easy.query.core.expression.sql.builder.internal.ContextConfigurer;
import com.easy.query.core.expression.sql.builder.internal.EasyBehavior;

import java.util.Collection;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * create time 2023/8/16 08:12
 * 文件说明
 *
 * @author xuejiaming
 */
public interface OverrideQueryable3<T1, T2,T3> extends Queryable<T1>, Queryable3Available<T1, T2,T3> {

    @Override
    Queryable3<T1,T2,T3> cloneQueryable();

    @Override
    default Queryable3<T1,T2,T3> whereById(Object id) {
        return whereById(true, id);
    }

    @Override
    Queryable3<T1,T2,T3> whereById(boolean condition, Object id);

    @Override
   default <TProperty> Queryable3<T1,T2,T3> whereByIds(Collection<TProperty> ids){
        return whereByIds(true,ids);
    }

    @Override
    <TProperty> Queryable3<T1,T2,T3> whereByIds(boolean condition, Collection<TProperty> ids);

    /**
     * 仅支持主表的动态对象查询
     *
     * @param object 对象查询的对象
     * @return
     */
    @Override
    default Queryable3<T1,T2,T3> whereObject(Object object) {
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
    Queryable3<T1,T2,T3> whereObject(boolean condition, Object object);


    @Override
    default Queryable3<T1,T2,T3> where(SQLExpression1<SQLWherePredicate<T1>> whereExpression) {
        return where(true, whereExpression);
    }

    @Override
    Queryable3<T1,T2,T3> where(boolean condition, SQLExpression1<SQLWherePredicate<T1>> whereExpression);

    @Override
    default Queryable3<T1,T2,T3> groupBy(SQLExpression1<SQLGroupBySelector<T1>> selectExpression) {
        return groupBy(true, selectExpression);
    }

    @Override
    Queryable3<T1,T2,T3> groupBy(boolean condition, SQLExpression1<SQLGroupBySelector<T1>> selectExpression);

    @Override
    default Queryable3<T1,T2,T3> having(SQLExpression1<SQLWhereAggregatePredicate<T1>> predicateExpression) {
        return having(true, predicateExpression);
    }

    @Override
    Queryable3<T1,T2,T3> having(boolean condition, SQLExpression1<SQLWhereAggregatePredicate<T1>> predicateExpression);

    @Override
    default Queryable3<T1,T2,T3> orderByAsc(SQLExpression1<SQLOrderBySelector<T1>> selectExpression) {
        return orderByAsc(true, selectExpression);
    }

    @Override
    Queryable3<T1,T2,T3> orderByAsc(boolean condition, SQLExpression1<SQLOrderBySelector<T1>> selectExpression);

    @Override
    default Queryable3<T1,T2,T3> orderByDesc(SQLExpression1<SQLOrderBySelector<T1>> selectExpression) {
        return orderByDesc(true, selectExpression);
    }

    @Override
    Queryable3<T1,T2,T3> orderByDesc(boolean condition, SQLExpression1<SQLOrderBySelector<T1>> selectExpression);


    /**
     * @param configuration
     * @return
     * @throws EasyQueryOrderByInvalidOperationException 当配置{@link ObjectSort} 为{@code  DynamicModeEnum.STRICT}排序设置的属性不存在当前排序对象里面或者当前查询对象无法获取 {@link SQLColumnSelector}
     */
    @Override
    default Queryable3<T1,T2,T3> orderByObject(ObjectSort configuration) {
        return orderByObject(true, configuration);
    }

    /**
     * @param condition
     * @param configuration
     * @return
     * @throws EasyQueryOrderByInvalidOperationException 当配置{@link ObjectSort} 为{@code  DynamicModeEnum.STRICT}排序设置的属性不存在当前排序对象里面或者当前查询对象无法获取 {@link SQLColumnSelector}
     */
    @Override
    Queryable3<T1,T2,T3> orderByObject(boolean condition, ObjectSort configuration);

    @Override
    default <TREntity> Queryable3<T1,T2,T3> include(SQLFuncExpression1<SQLNavigateInclude<T1>, Queryable<TREntity>> navigateIncludeSQLExpression) {
        return include(true, navigateIncludeSQLExpression);
    }

    @Override
    <TREntity> Queryable3<T1,T2,T3> include(boolean condition, SQLFuncExpression1<SQLNavigateInclude<T1>, Queryable<TREntity>> navigateIncludeSQLExpression);

    @Override
    default Queryable3<T1,T2,T3> limit(long rows) {
        return limit(true, rows);
    }

    @Override
    default Queryable3<T1,T2,T3> limit(boolean condition, long rows) {
        return limit(condition, 0, rows);
    }

    @Override
    default Queryable3<T1,T2,T3> limit(long offset, long rows) {
        return limit(true, offset, rows);
    }

    @Override
    Queryable3<T1,T2,T3> limit(boolean condition, long offset, long rows);

    default Queryable3<T1,T2,T3> distinct() {
        return distinct(true);
    }

    @Override
    Queryable3<T1,T2,T3> distinct(boolean condition);

    @Override
    Queryable3<T1,T2,T3> disableLogicDelete();

    @Override
    Queryable3<T1,T2,T3> enableLogicDelete();

    @Override
    Queryable3<T1,T2,T3> useLogicDelete(boolean enable);

    @Override
    Queryable3<T1,T2,T3> noInterceptor();

    @Override
    Queryable3<T1,T2,T3> useInterceptor(String name);

    @Override
    Queryable3<T1,T2,T3> noInterceptor(String name);

    @Override
    Queryable3<T1,T2,T3> useInterceptor();

    /**
     * 自动将查询结果集合全部添加到当前上下文追踪中,如果当前查询结果十分庞大,并且更新数据只有个别条数,建议不要使用
     * 追踪查询，可以通过开启追踪后使用普通的查询，然后添加到当前的追踪上下文中{@link EasyQueryClient#addTracking(Object)},开始先数据追踪的差异更新
     *
     * @return
     */
    @Override
    Queryable3<T1,T2,T3> asTracking();

    @Override
    Queryable3<T1,T2,T3> asNoTracking();

    @Override
    Queryable3<T1,T2,T3> queryLargeColumn(boolean queryLarge);

    @Override
    Queryable3<T1,T2,T3> useShardingConfigure(int maxShardingQueryLimit, ConnectionModeEnum connectionMode);

    @Override
    Queryable3<T1,T2,T3> useMaxShardingQueryLimit(int maxShardingQueryLimit);

    @Override
    Queryable3<T1,T2,T3> useConnectionMode(ConnectionModeEnum connectionMode);

    /**
     * 将当前表达式最近的一张表的表名修改成 {@param tableName}
     * 如果当前最近的表是正常的数据库表名,那么直接将表名改写
     * 如果当前最近的表是匿名表比如嵌套queryable的表那么将alias改成对应的表名
     *
     * @param tableName
     * @return
     */
    @Override
    default Queryable3<T1,T2,T3> asTable(String tableName) {
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
    Queryable3<T1,T2,T3> asTable(Function<String, String> tableNameAs);

    @Override
    default Queryable3<T1,T2,T3> asSchema(String schema) {
        return asSchema(old -> schema);
    }

    @Override
    Queryable3<T1,T2,T3> asSchema(Function<String, String> schemaAs);

    @Override
    Queryable3<T1,T2,T3> asAlias(String alias);

    /**
     * @param linkAs 别名 FROM | LEFT JOIN | RIGHT JOIN
     * @return
     */
    @Override
    default Queryable3<T1,T2,T3> asTableLink(String linkAs) {
        return asTableLink(o -> linkAs);
    }


    @Override
    Queryable3<T1,T2,T3> asTableLink(Function<String, String> linkAs);
    @Override
    Queryable3<T1,T2,T3> asTableSegment(BiFunction<String, String, String> segmentAs);
    @Override
    Queryable3<T1,T2,T3>  filterConfigure(ValueFilter valueFilter);
    @Override
    Queryable3<T1,T2,T3>  configure(SQLExpression1<ContextConfigurer> configurer);
}
