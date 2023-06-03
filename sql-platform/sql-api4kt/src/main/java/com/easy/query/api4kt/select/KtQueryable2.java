package com.easy.query.api4kt.select;

import com.easy.query.api4kt.sql.SQLKtColumnAsSelector;
import com.easy.query.api4kt.sql.SQLKtColumnResultSelector;
import com.easy.query.api4kt.sql.SQLKtColumnSelector;
import com.easy.query.api4kt.sql.SQLKtGroupBySelector;
import com.easy.query.api4kt.sql.SQLKtWherePredicate;
import com.easy.query.core.api.client.EasyQueryClient;
import com.easy.query.core.enums.sharding.ConnectionModeEnum;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.lambda.SQLExpression2;
import com.easy.query.core.expression.lambda.SQLExpression3;

import java.math.BigDecimal;
import java.util.function.Function;


/**
 * @author xuejiaming
 * @FileName: Select2.java
 * @Description: 文件说明
 * @Date: 2023/2/6 22:42
 */
public interface KtQueryable2<T1, T2> extends KtQueryable<T1> {
    <T3> KtQueryable3<T1, T2, T3> leftJoin(Class<T3> joinClass, SQLExpression3<SQLKtWherePredicate<T1>, SQLKtWherePredicate<T2>, SQLKtWherePredicate<T3>> on);

    <T3> KtQueryable3<T1, T2, T3> leftJoin(KtQueryable<T3> joinQueryable, SQLExpression3<SQLKtWherePredicate<T1>, SQLKtWherePredicate<T2>, SQLKtWherePredicate<T3>> on);

    <T3> KtQueryable3<T1, T2, T3> rightJoin(Class<T3> joinClass, SQLExpression3<SQLKtWherePredicate<T1>, SQLKtWherePredicate<T2>, SQLKtWherePredicate<T3>> on);

    <T3> KtQueryable3<T1, T2, T3> rightJoin(KtQueryable<T3> joinQueryable, SQLExpression3<SQLKtWherePredicate<T1>, SQLKtWherePredicate<T2>, SQLKtWherePredicate<T3>> on);

    <T3> KtQueryable3<T1, T2, T3> innerJoin(Class<T3> joinClass, SQLExpression3<SQLKtWherePredicate<T1>, SQLKtWherePredicate<T2>, SQLKtWherePredicate<T3>> on);

    <T3> KtQueryable3<T1, T2, T3> innerJoin(KtQueryable<T3> joinQueryable, SQLExpression3<SQLKtWherePredicate<T1>, SQLKtWherePredicate<T2>, SQLKtWherePredicate<T3>> on);

    //region where

    default KtQueryable2<T1, T2> whereObject(Object object) {
        return whereObject(true, object);
    }

    KtQueryable2<T1, T2> whereObject(boolean condition, Object object);

    @Override
    default KtQueryable2<T1, T2> where(SQLExpression1<SQLKtWherePredicate<T1>> whereExpression) {
        return where(true, whereExpression);
    }

    @Override
    KtQueryable2<T1, T2> where(boolean condition, SQLExpression1<SQLKtWherePredicate<T1>> whereExpression);

    default KtQueryable2<T1, T2> where(SQLExpression2<SQLKtWherePredicate<T1>, SQLKtWherePredicate<T2>> whereExpression) {
        return where(true, whereExpression);
    }

    KtQueryable2<T1, T2> where(boolean condition, SQLExpression2<SQLKtWherePredicate<T1>, SQLKtWherePredicate<T2>> whereExpression);

    //endregion

    //region select
    <TR> KtQueryable<TR> select(Class<TR> resultClass, SQLExpression2<SQLKtColumnAsSelector<T1, TR>, SQLKtColumnAsSelector<T2, TR>> selectExpression);
    //endregion
    //region aggregate

    /**
     * 防止溢出
     *
     * @param columnSelectorExpression
     * @param <TMember>
     * @return
     */
    default <TMember extends Number> BigDecimal sumBigDecimalOrNull(SQLExpression2<SQLKtColumnResultSelector<T1, TMember>, SQLKtColumnResultSelector<T2, TMember>> columnSelectorExpression) {
        return sumBigDecimalOrDefault(columnSelectorExpression, null);
    }

    /**
     * 防止溢出
     *
     * @param columnSelectorExpression
     * @param <TMember>
     * @return
     */
    default <TMember extends Number> BigDecimal sumBigDecimalNotNull(SQLExpression2<SQLKtColumnResultSelector<T1, TMember>, SQLKtColumnResultSelector<T2, TMember>> columnSelectorExpression) {
        return sumBigDecimalOrDefault(columnSelectorExpression, BigDecimal.ZERO);
    }

    <TMember extends Number> BigDecimal sumBigDecimalOrDefault(SQLExpression2<SQLKtColumnResultSelector<T1, TMember>, SQLKtColumnResultSelector<T2, TMember>> columnSelectorExpression, BigDecimal def);

    default <TMember extends Number> TMember sumOrNull(SQLExpression2<SQLKtColumnResultSelector<T1, TMember>, SQLKtColumnResultSelector<T2, TMember>> columnSelectorExpression) {
        return sumOrDefault(columnSelectorExpression, null);
    }

    <TMember extends Number> TMember sumOrDefault(SQLExpression2<SQLKtColumnResultSelector<T1, TMember>, SQLKtColumnResultSelector<T2, TMember>> columnSelectorExpression, TMember def);

    default <TMember> TMember maxOrNull(SQLExpression2<SQLKtColumnResultSelector<T1, TMember>, SQLKtColumnResultSelector<T2, TMember>> columnSelectorExpression) {
        return maxOrDefault(columnSelectorExpression, null);
    }

    <TMember> TMember maxOrDefault(SQLExpression2<SQLKtColumnResultSelector<T1, TMember>, SQLKtColumnResultSelector<T2, TMember>> columnSelectorExpression, TMember def);

    default <TMember> TMember minOrNull(SQLExpression2<SQLKtColumnResultSelector<T1, TMember>, SQLKtColumnResultSelector<T2, TMember>> columnSelectorExpression) {
        return minOrDefault(columnSelectorExpression, null);
    }

    <TMember> TMember minOrDefault(SQLExpression2<SQLKtColumnResultSelector<T1, TMember>, SQLKtColumnResultSelector<T2, TMember>> columnSelectorExpression, TMember def);

    default <TMember extends Number> TMember avgOrNull(SQLExpression2<SQLKtColumnResultSelector<T1, TMember>, SQLKtColumnResultSelector<T2, TMember>> columnSelectorExpression) {
        return avgOrDefault(columnSelectorExpression, null);
    }

    <TMember extends Number> TMember avgOrDefault(SQLExpression2<SQLKtColumnResultSelector<T1, TMember>, SQLKtColumnResultSelector<T2, TMember>> columnSelectorExpression, TMember def);

    default Integer lenOrNull(SQLExpression2<SQLKtColumnResultSelector<T1, ?>, SQLKtColumnResultSelector<T2, ?>> columnSelectorExpression) {
        return lenOrDefault(columnSelectorExpression, null);
    }

    Integer lenOrDefault(SQLExpression2<SQLKtColumnResultSelector<T1, ?>, SQLKtColumnResultSelector<T2, ?>> columnSelectorExpression, Integer def);
    //endregion

    //region group
    @Override
    default KtQueryable2<T1, T2> groupBy(SQLExpression1<SQLKtGroupBySelector<T1>> selectExpression) {
        return groupBy(true, selectExpression);
    }

    @Override
    KtQueryable2<T1, T2> groupBy(boolean condition, SQLExpression1<SQLKtGroupBySelector<T1>> selectExpression);

    default KtQueryable2<T1, T2> groupBy(SQLExpression2<SQLKtGroupBySelector<T1>, SQLKtGroupBySelector<T2>> selectExpression) {
        return groupBy(true, selectExpression);
    }

    KtQueryable2<T1, T2> groupBy(boolean condition, SQLExpression2<SQLKtGroupBySelector<T1>, SQLKtGroupBySelector<T2>> selectExpression);

    //endregion
    //region order
    @Override
    default KtQueryable2<T1, T2> orderByAsc(SQLExpression1<SQLKtColumnSelector<T1>> selectExpression) {
        return orderByAsc(true, selectExpression);
    }

    @Override
    KtQueryable2<T1, T2> orderByAsc(boolean condition, SQLExpression1<SQLKtColumnSelector<T1>> selectExpression);

    default KtQueryable2<T1, T2> orderByAsc(SQLExpression2<SQLKtColumnSelector<T1>, SQLKtColumnSelector<T2>> selectExpression) {
        return orderByAsc(true, selectExpression);
    }

    KtQueryable2<T1, T2> orderByAsc(boolean condition, SQLExpression2<SQLKtColumnSelector<T1>, SQLKtColumnSelector<T2>> selectExpression);

    @Override
    default KtQueryable2<T1, T2> orderByDesc(SQLExpression1<SQLKtColumnSelector<T1>> selectExpression) {
        return orderByDesc(true, selectExpression);
    }

    @Override
    KtQueryable2<T1, T2> orderByDesc(boolean condition, SQLExpression1<SQLKtColumnSelector<T1>> selectExpression);

    default KtQueryable2<T1, T2> orderByDesc(SQLExpression2<SQLKtColumnSelector<T1>, SQLKtColumnSelector<T2>> selectExpression) {
        return orderByDesc(true, selectExpression);
    }

    KtQueryable2<T1, T2> orderByDesc(boolean condition, SQLExpression2<SQLKtColumnSelector<T1>, SQLKtColumnSelector<T2>> selectExpression);
    //endregion
    //region limit

    @Override
    default KtQueryable2<T1, T2> limit(long rows) {
        return limit(true, rows);
    }

    @Override
    default KtQueryable2<T1, T2> limit(boolean condition, long rows) {
        return limit(condition, 0, rows);
    }

    @Override
    default KtQueryable2<T1, T2> limit(long offset, long rows) {
        return limit(true, offset, rows);
    }

    @Override
    KtQueryable2<T1, T2> limit(boolean condition, long offset, long rows);

    default KtQueryable2<T1, T2> distinct() {
        return distinct(true);
    }

    KtQueryable2<T1, T2> distinct(boolean condition);

    //endregion
    @Override
    KtQueryable2<T1, T2> disableLogicDelete();

    @Override
    KtQueryable2<T1, T2> enableLogicDelete();

    @Override
    KtQueryable2<T1, T2> useLogicDelete(boolean enable);

    @Override
    KtQueryable2<T1, T2> noInterceptor();

    @Override
    KtQueryable2<T1, T2> useInterceptor(String name);

    @Override
    KtQueryable2<T1, T2> noInterceptor(String name);

    @Override
    KtQueryable2<T1, T2> useInterceptor();

    /**
     * 自动将查询结果集合全部添加到当前上下文追踪中,如果当前查询结果十分庞大,并且更新数据只有个别条数,建议不要使用
     * 追踪查询，可以通过开启追踪后使用普通的查询，然后添加到当前的追踪上下文中{@link EasyQueryClient#addTracking(Object)},开始先数据追踪的差异更新
     *
     * @return
     */
    @Override
    KtQueryable2<T1, T2> asTracking();

    @Override
    KtQueryable2<T1, T2> asNoTracking();

    @Override
    KtQueryable2<T1, T2> queryLargeColumn(boolean queryLarge);

    @Override
    KtQueryable2<T1, T2> useShardingConfigure(int maxShardingQueryLimit, ConnectionModeEnum connectionMode);

    @Override
    KtQueryable2<T1, T2> useMaxShardingQueryLimit(int maxShardingQueryLimit);

    @Override
    KtQueryable2<T1, T2> useConnectionMode(ConnectionModeEnum connectionMode);

    /**
     * 将当前表达式最近的一张表的表名修改成 {@param tableName}
     * 如果当前最近的表是正常的数据库表名,那么直接将表名改写
     * 如果当前最近的表是匿名表比如嵌套queryable的表那么将alias改成对应的表名
     *
     * @param tableName
     * @return
     */
    @Override
    default KtQueryable2<T1, T2> asTable(String tableName) {
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
    KtQueryable2<T1, T2> asTable(Function<String, String> tableNameAs);

    @Override
    KtQueryable2<T1, T2> asAlias(String alias);
}
