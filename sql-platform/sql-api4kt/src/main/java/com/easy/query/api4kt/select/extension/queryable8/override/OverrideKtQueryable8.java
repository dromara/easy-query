package com.easy.query.api4kt.select.extension.queryable8.override;

import com.easy.query.api4kt.select.KtQueryable;
import com.easy.query.api4kt.select.KtQueryable8;
import com.easy.query.api4kt.select.extension.queryable8.KtQueryable8Available;
import com.easy.query.api4kt.sql.SQLKtGroupBySelector;
import com.easy.query.api4kt.sql.SQLKtNavigateInclude;
import com.easy.query.api4kt.sql.SQLKtOrderBySelector;
import com.easy.query.api4kt.sql.SQLKtWhereAggregatePredicate;
import com.easy.query.api4kt.sql.SQLKtWherePredicate;
import com.easy.query.core.api.client.EasyQueryClient;
import com.easy.query.core.api.dynamic.sort.ObjectSort;
import com.easy.query.core.enums.sharding.ConnectionModeEnum;
import com.easy.query.core.exception.EasyQueryOrderByInvalidOperationException;
import com.easy.query.core.expression.builder.core.ValueFilter;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.lambda.SQLFuncExpression1;

import java.util.Collection;
import java.util.function.Function;

/**
 * create time 2023/8/16 08:12
 * 文件说明
 *
 * @author xuejiaming
 */
public interface OverrideKtQueryable8<T1, T2, T3, T4, T5, T6, T7, T8> extends KtQueryable<T1>, KtQueryable8Available<T1, T2, T3, T4, T5, T6, T7, T8> {

    @Override
    KtQueryable8<T1, T2, T3, T4, T5, T6, T7, T8> cloneQueryable();

    @Override
    default KtQueryable8<T1, T2, T3, T4, T5, T6, T7, T8> whereById(Object id) {
        return whereById(true, id);
    }

    @Override
    KtQueryable8<T1, T2, T3, T4, T5, T6, T7, T8> whereById(boolean condition, Object id);

    @Override
   default  <TProperty> KtQueryable8<T1, T2, T3, T4, T5, T6, T7, T8> whereByIds(Collection<TProperty> ids){
        return whereByIds(true,ids);
    }

    @Override
    <TProperty> KtQueryable8<T1, T2, T3, T4, T5, T6, T7, T8> whereByIds(boolean condition, Collection<TProperty> ids);

    /**
     * 仅支持主表的动态对象查询
     *
     * @param object 对象查询的对象
     * @return
     */
    @Override
    default KtQueryable8<T1, T2, T3, T4, T5, T6, T7, T8> whereObject(Object object) {
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
    KtQueryable8<T1, T2, T3, T4, T5, T6, T7, T8> whereObject(boolean condition, Object object);


    @Override
    default KtQueryable8<T1, T2, T3, T4, T5, T6, T7, T8> where(SQLExpression1<SQLKtWherePredicate<T1>> whereExpression) {
        return where(true, whereExpression);
    }

    @Override
    KtQueryable8<T1, T2, T3, T4, T5, T6, T7, T8> where(boolean condition, SQLExpression1<SQLKtWherePredicate<T1>> whereExpression);

    @Override
    default KtQueryable8<T1, T2, T3, T4, T5, T6, T7, T8> groupBy(SQLExpression1<SQLKtGroupBySelector<T1>> selectExpression) {
        return groupBy(true, selectExpression);
    }

    @Override
    KtQueryable8<T1, T2, T3, T4, T5, T6, T7, T8> groupBy(boolean condition, SQLExpression1<SQLKtGroupBySelector<T1>> selectExpression);

    @Override
    default KtQueryable8<T1, T2, T3, T4, T5, T6, T7, T8> having(SQLExpression1<SQLKtWhereAggregatePredicate<T1>> predicateExpression) {
        return having(true, predicateExpression);
    }

    @Override
    KtQueryable8<T1, T2, T3, T4, T5, T6, T7, T8> having(boolean condition, SQLExpression1<SQLKtWhereAggregatePredicate<T1>> predicateExpression);

    @Override
    default KtQueryable8<T1, T2, T3, T4, T5, T6, T7, T8> orderByAsc(SQLExpression1<SQLKtOrderBySelector<T1>> selectExpression) {
        return orderByAsc(true, selectExpression);
    }

    @Override
    KtQueryable8<T1, T2, T3, T4, T5, T6, T7, T8> orderByAsc(boolean condition, SQLExpression1<SQLKtOrderBySelector<T1>> selectExpression);

    @Override
    default KtQueryable8<T1, T2, T3, T4, T5, T6, T7, T8> orderByDesc(SQLExpression1<SQLKtOrderBySelector<T1>> selectExpression) {
        return orderByDesc(true, selectExpression);
    }

    @Override
    KtQueryable8<T1, T2, T3, T4, T5, T6, T7, T8> orderByDesc(boolean condition, SQLExpression1<SQLKtOrderBySelector<T1>> selectExpression);

    /**
     * @param configuration
     * @return
     * @throws EasyQueryOrderByInvalidOperationException 当配置{@link ObjectSort} 为{@code  DynamicModeEnum.STRICT}排序设置的属性不存在当前排序对象里面或者当前查询对象无法获取}
     */
    @Override
    default KtQueryable8<T1, T2, T3, T4, T5, T6, T7, T8> orderByObject(ObjectSort configuration) {
        return orderByObject(true, configuration);
    }

    /**
     * @param condition
     * @param configuration
     * @return
     * @throws EasyQueryOrderByInvalidOperationException 当配置{@link ObjectSort} 为{@code  DynamicModeEnum.STRICT}排序设置的属性不存在当前排序对象里面或者当前查询对象无法获取
     */
    @Override
    KtQueryable8<T1, T2, T3, T4, T5, T6, T7, T8> orderByObject(boolean condition, ObjectSort configuration);

    @Override
    default <TREntity> KtQueryable8<T1, T2, T3, T4, T5, T6, T7, T8> include(SQLFuncExpression1<SQLKtNavigateInclude<T1>, KtQueryable<TREntity>> navigateIncludeSQLExpression) {
        return include(true, navigateIncludeSQLExpression);
    }

    @Override
    <TREntity> KtQueryable8<T1, T2, T3, T4, T5, T6, T7, T8> include(boolean condition, SQLFuncExpression1<SQLKtNavigateInclude<T1>, KtQueryable<TREntity>> navigateIncludeSQLExpression);

    @Override
    default KtQueryable8<T1, T2, T3, T4, T5, T6, T7, T8> limit(long rows) {
        return limit(true, rows);
    }

    @Override
    default KtQueryable8<T1, T2, T3, T4, T5, T6, T7, T8> limit(boolean condition, long rows) {
        return limit(condition, 0, rows);
    }

    @Override
    default KtQueryable8<T1, T2, T3, T4, T5, T6, T7, T8> limit(long offset, long rows) {
        return limit(true, offset, rows);
    }

    @Override
    KtQueryable8<T1, T2, T3, T4, T5, T6, T7, T8> limit(boolean condition, long offset, long rows);

    default KtQueryable8<T1, T2, T3, T4, T5, T6, T7, T8> distinct() {
        return distinct(true);
    }

    @Override
    KtQueryable8<T1, T2, T3, T4, T5, T6, T7, T8> distinct(boolean condition);

    @Override
    KtQueryable8<T1, T2, T3, T4, T5, T6, T7, T8> disableLogicDelete();

    @Override
    KtQueryable8<T1, T2, T3, T4, T5, T6, T7, T8> enableLogicDelete();

    @Override
    KtQueryable8<T1, T2, T3, T4, T5, T6, T7, T8> useLogicDelete(boolean enable);

    @Override
    KtQueryable8<T1, T2, T3, T4, T5, T6, T7, T8> noInterceptor();

    @Override
    KtQueryable8<T1, T2, T3, T4, T5, T6, T7, T8> useInterceptor(String name);

    @Override
    KtQueryable8<T1, T2, T3, T4, T5, T6, T7, T8> noInterceptor(String name);

    @Override
    KtQueryable8<T1, T2, T3, T4, T5, T6, T7, T8> useInterceptor();

    /**
     * 自动将查询结果集合全部添加到当前上下文追踪中,如果当前查询结果十分庞大,并且更新数据只有个别条数,建议不要使用
     * 追踪查询，可以通过开启追踪后使用普通的查询，然后添加到当前的追踪上下文中{@link EasyQueryClient#addTracking(Object)},开始先数据追踪的差异更新
     *
     * @return
     */
    @Override
    KtQueryable8<T1, T2, T3, T4, T5, T6, T7, T8> asTracking();

    @Override
    KtQueryable8<T1, T2, T3, T4, T5, T6, T7, T8> asNoTracking();

    @Override
    KtQueryable8<T1, T2, T3, T4, T5, T6, T7, T8> queryLargeColumn(boolean queryLarge);

    @Override
    KtQueryable8<T1, T2, T3, T4, T5, T6, T7, T8> useShardingConfigure(int maxShardingQueryLimit, ConnectionModeEnum connectionMode);

    @Override
    KtQueryable8<T1, T2, T3, T4, T5, T6, T7, T8> useMaxShardingQueryLimit(int maxShardingQueryLimit);

    @Override
    KtQueryable8<T1, T2, T3, T4, T5, T6, T7, T8> useConnectionMode(ConnectionModeEnum connectionMode);

    /**
     * 将当前表达式最近的一张表的表名修改成 {@param tableName}
     * 如果当前最近的表是正常的数据库表名,那么直接将表名改写
     * 如果当前最近的表是匿名表比如嵌套queryable的表那么将alias改成对应的表名
     *
     * @param tableName
     * @return
     */
    @Override
    default KtQueryable8<T1, T2, T3, T4, T5, T6, T7, T8> asTable(String tableName) {
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
    KtQueryable8<T1, T2, T3, T4, T5, T6, T7, T8> asTable(Function<String, String> tableNameAs);

    @Override
    default KtQueryable8<T1, T2, T3, T4, T5, T6, T7, T8> asSchema(String schema) {
        return asSchema(old -> schema);
    }

    @Override
    KtQueryable8<T1, T2, T3, T4, T5, T6, T7, T8> asSchema(Function<String, String> schemaAs);

    @Override
    KtQueryable8<T1, T2, T3, T4, T5, T6, T7, T8> asAlias(String alias);

    /**
     * @param linkAs 别名 FROM | LEFT JOIN | RIGHT JOIN
     * @return
     */
    @Override
    default KtQueryable8<T1, T2, T3, T4, T5, T6, T7, T8> asTableLink(String linkAs) {
        return asTableLink(o -> linkAs);
    }


    @Override
    KtQueryable8<T1, T2, T3, T4, T5, T6, T7, T8> asTableLink(Function<String, String> linkAs);
    @Override
    KtQueryable8<T1, T2, T3, T4, T5, T6, T7, T8> filterConfigure(ValueFilter valueFilter);
}
