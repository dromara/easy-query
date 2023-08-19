package com.easy.query.api4kt.select.extension.queryable5.override;

import com.easy.query.api4kt.select.KtQueryable;
import com.easy.query.api4kt.select.KtQueryable5;
import com.easy.query.api4kt.select.extension.queryable5.KtQueryable5Available;
import com.easy.query.api4kt.sql.SQLKtGroupBySelector;
import com.easy.query.api4kt.sql.SQLKtNavigateInclude;
import com.easy.query.api4kt.sql.SQLKtOrderBySelector;
import com.easy.query.api4kt.sql.SQLKtWhereAggregatePredicate;
import com.easy.query.api4kt.sql.SQLKtWherePredicate;
import com.easy.query.core.api.client.EasyQueryClient;
import com.easy.query.core.enums.sharding.ConnectionModeEnum;
import com.easy.query.core.expression.builder.core.ConditionAccepter;
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
public interface OverrideKtQueryable5<T1, T2, T3,T4,T5> extends KtQueryable<T1>, KtQueryable5Available<T1, T2,T3,T4,T5> {

    @Override
    KtQueryable5<T1,T2,T3,T4,T5> cloneQueryable();

    @Override
    default KtQueryable5<T1,T2,T3,T4,T5> whereById(Object id) {
        return whereById(true, id);
    }

    @Override
    KtQueryable5<T1,T2,T3,T4,T5> whereById(boolean condition, Object id);

    @Override
    <TProperty> KtQueryable5<T1,T2,T3,T4,T5> whereByIds(boolean condition, Collection<TProperty> ids);

    /**
     * 仅支持主表的动态对象查询
     *
     * @param object 对象查询的对象
     * @return
     */
    @Override
    default KtQueryable5<T1,T2,T3,T4,T5> whereObject(Object object) {
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
    KtQueryable5<T1,T2,T3,T4,T5> whereObject(boolean condition, Object object);


    @Override
    default KtQueryable5<T1,T2,T3,T4,T5> where(SQLExpression1<SQLKtWherePredicate<T1>> whereExpression) {
        return where(true, whereExpression);
    }

    @Override
    KtQueryable5<T1,T2,T3,T4,T5> where(boolean condition, SQLExpression1<SQLKtWherePredicate<T1>> whereExpression);

    @Override
    default KtQueryable5<T1,T2,T3,T4,T5> groupBy(SQLExpression1<SQLKtGroupBySelector<T1>> selectExpression) {
        return groupBy(true, selectExpression);
    }

    @Override
    KtQueryable5<T1,T2,T3,T4,T5> groupBy(boolean condition, SQLExpression1<SQLKtGroupBySelector<T1>> selectExpression);

    @Override
    default KtQueryable5<T1,T2,T3,T4,T5> having(SQLExpression1<SQLKtWhereAggregatePredicate<T1>> predicateExpression) {
        return having(true, predicateExpression);
    }

    @Override
    KtQueryable5<T1,T2,T3,T4,T5> having(boolean condition, SQLExpression1<SQLKtWhereAggregatePredicate<T1>> predicateExpression);

    @Override
    default KtQueryable5<T1,T2,T3,T4,T5> orderByAsc(SQLExpression1<SQLKtOrderBySelector<T1>> selectExpression) {
        return orderByAsc(true, selectExpression);
    }

    @Override
    KtQueryable5<T1,T2,T3,T4,T5> orderByAsc(boolean condition, SQLExpression1<SQLKtOrderBySelector<T1>> selectExpression);

    @Override
    default KtQueryable5<T1,T2,T3,T4,T5> orderByDesc(SQLExpression1<SQLKtOrderBySelector<T1>> selectExpression) {
        return orderByDesc(true, selectExpression);
    }

    @Override
    KtQueryable5<T1,T2,T3,T4,T5> orderByDesc(boolean condition, SQLExpression1<SQLKtOrderBySelector<T1>> selectExpression);

    @Override
    default <TREntity> KtQueryable5<T1,T2,T3,T4,T5> include(SQLFuncExpression1<SQLKtNavigateInclude<T1>, KtQueryable<TREntity>> navigateIncludeSQLExpression) {
        return include(true, navigateIncludeSQLExpression);
    }

    @Override
    <TREntity> KtQueryable5<T1,T2,T3,T4,T5> include(boolean condition, SQLFuncExpression1<SQLKtNavigateInclude<T1>, KtQueryable<TREntity>> navigateIncludeSQLExpression);

    @Override
    default KtQueryable5<T1,T2,T3,T4,T5> limit(long rows) {
        return limit(true, rows);
    }

    @Override
    default KtQueryable5<T1,T2,T3,T4,T5> limit(boolean condition, long rows) {
        return limit(condition, 0, rows);
    }

    @Override
    default KtQueryable5<T1,T2,T3,T4,T5> limit(long offset, long rows) {
        return limit(true, offset, rows);
    }

    @Override
    KtQueryable5<T1,T2,T3,T4,T5> limit(boolean condition, long offset, long rows);

    default KtQueryable5<T1,T2,T3,T4,T5> distinct() {
        return distinct(true);
    }

    @Override
    KtQueryable5<T1,T2,T3,T4,T5> distinct(boolean condition);

    @Override
    KtQueryable5<T1,T2,T3,T4,T5> disableLogicDelete();

    @Override
    KtQueryable5<T1,T2,T3,T4,T5> enableLogicDelete();

    @Override
    KtQueryable5<T1,T2,T3,T4,T5> useLogicDelete(boolean enable);

    @Override
    KtQueryable5<T1,T2,T3,T4,T5> noInterceptor();

    @Override
    KtQueryable5<T1,T2,T3,T4,T5> useInterceptor(String name);

    @Override
    KtQueryable5<T1,T2,T3,T4,T5> noInterceptor(String name);

    @Override
    KtQueryable5<T1,T2,T3,T4,T5> useInterceptor();

    /**
     * 自动将查询结果集合全部添加到当前上下文追踪中,如果当前查询结果十分庞大,并且更新数据只有个别条数,建议不要使用
     * 追踪查询，可以通过开启追踪后使用普通的查询，然后添加到当前的追踪上下文中{@link EasyQueryClient#addTracking(Object)},开始先数据追踪的差异更新
     *
     * @return
     */
    @Override
    KtQueryable5<T1,T2,T3,T4,T5> asTracking();

    @Override
    KtQueryable5<T1,T2,T3,T4,T5> asNoTracking();

    @Override
    KtQueryable5<T1,T2,T3,T4,T5> queryLargeColumn(boolean queryLarge);

    @Override
    KtQueryable5<T1,T2,T3,T4,T5> useShardingConfigure(int maxShardingQueryLimit, ConnectionModeEnum connectionMode);

    @Override
    KtQueryable5<T1,T2,T3,T4,T5> useMaxShardingQueryLimit(int maxShardingQueryLimit);

    @Override
    KtQueryable5<T1,T2,T3,T4,T5> useConnectionMode(ConnectionModeEnum connectionMode);

    /**
     * 将当前表达式最近的一张表的表名修改成 {@param tableName}
     * 如果当前最近的表是正常的数据库表名,那么直接将表名改写
     * 如果当前最近的表是匿名表比如嵌套queryable的表那么将alias改成对应的表名
     *
     * @param tableName
     * @return
     */
    @Override
    default KtQueryable5<T1,T2,T3,T4,T5> asTable(String tableName) {
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
    KtQueryable5<T1,T2,T3,T4,T5> asTable(Function<String, String> tableNameAs);

    @Override
    default KtQueryable5<T1,T2,T3,T4,T5> asSchema(String schema) {
        return asSchema(old -> schema);
    }

    @Override
    KtQueryable5<T1,T2,T3,T4,T5> asSchema(Function<String, String> schemaAs);

    @Override
    KtQueryable5<T1,T2,T3,T4,T5> asAlias(String alias);

    /**
     * @param linkAs 别名 FROM | LEFT JOIN | RIGHT JOIN
     * @return
     */
    @Override
    default KtQueryable5<T1,T2,T3,T4,T5> asTableLink(String linkAs) {
        return asTableLink(o -> linkAs);
    }


    @Override
    KtQueryable5<T1,T2,T3,T4,T5> asTableLink(Function<String, String> linkAs);
    @Override
    KtQueryable5<T1,T2,T3,T4,T5> conditionConfigure(ConditionAccepter conditionAccepter);
}
