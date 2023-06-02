package com.easy.query.core.basic.api.select;

import com.easy.query.core.api.client.EasyQueryClient;
import com.easy.query.core.basic.api.select.provider.SQLExpressionProvider;
import com.easy.query.core.enums.sharding.ConnectionModeEnum;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.lambda.SQLExpression2;
import com.easy.query.core.expression.lambda.SQLExpression3;
import com.easy.query.core.expression.parser.core.base.ColumnAsSelector;
import com.easy.query.core.expression.parser.core.base.ColumnResultSelector;
import com.easy.query.core.expression.parser.core.base.ColumnSelector;
import com.easy.query.core.expression.parser.core.base.GroupBySelector;
import com.easy.query.core.expression.parser.core.base.WherePredicate;

import java.math.BigDecimal;
import java.util.function.Function;


/**
 * @author xuejiaming
 * @FileName: Select2.java
 * @Description: 文件说明
 * @Date: 2023/2/6 22:42
 */
public interface ClientQueryable2<T1, T2> extends ClientQueryable<T1> {
    <T3> ClientQueryable3<T1, T2, T3> leftJoin(Class<T3> joinClass, SQLExpression3<WherePredicate<T1>, WherePredicate<T2>, WherePredicate<T3>> on);

    <T3> ClientQueryable3<T1, T2, T3> leftJoin(ClientQueryable<T3> joinQueryable, SQLExpression3<WherePredicate<T1>, WherePredicate<T2>, WherePredicate<T3>> on);

    <T3> ClientQueryable3<T1, T2, T3> rightJoin(Class<T3> joinClass, SQLExpression3<WherePredicate<T1>, WherePredicate<T2>, WherePredicate<T3>> on);

    <T3> ClientQueryable3<T1, T2, T3> rightJoin(ClientQueryable<T3> joinQueryable, SQLExpression3<WherePredicate<T1>, WherePredicate<T2>, WherePredicate<T3>> on);

    <T3> ClientQueryable3<T1, T2, T3> innerJoin(Class<T3> joinClass, SQLExpression3<WherePredicate<T1>, WherePredicate<T2>, WherePredicate<T3>> on);

    <T3> ClientQueryable3<T1, T2, T3> innerJoin(ClientQueryable<T3> joinQueryable, SQLExpression3<WherePredicate<T1>, WherePredicate<T2>, WherePredicate<T3>> on);

    //region where

    default ClientQueryable2<T1, T2> whereObject(Object object) {
        return whereObject(true, object);
    }

    ClientQueryable2<T1, T2> whereObject(boolean condition, Object object);

    @Override
    default ClientQueryable2<T1, T2> where(SQLExpression1<WherePredicate<T1>> whereExpression) {
        return where(true, whereExpression);
    }

    @Override
    ClientQueryable2<T1, T2> where(boolean condition, SQLExpression1<WherePredicate<T1>> whereExpression);

    default ClientQueryable2<T1, T2> where(SQLExpression2<WherePredicate<T1>, WherePredicate<T2>> whereExpression) {
        return where(true, whereExpression);
    }

    ClientQueryable2<T1, T2> where(boolean condition, SQLExpression2<WherePredicate<T1>, WherePredicate<T2>> whereExpression);

    //endregion

    //region select
    <TR> ClientQueryable<TR> select(Class<TR> resultClass, SQLExpression2<ColumnAsSelector<T1, TR>, ColumnAsSelector<T2, TR>> selectExpression);
    //endregion
    //region aggregate

    /**
     * 防止溢出
     *
     * @param columnSelectorExpression
     * @param <TMember>
     * @return
     */
    default <TMember extends Number> BigDecimal sumBigDecimalOrNull(SQLExpression2<ColumnResultSelector<T1>, ColumnResultSelector<T2>> columnSelectorExpression) {
        return sumBigDecimalOrDefault(columnSelectorExpression, null);
    }

    /**
     * 防止溢出
     *
     * @param columnSelectorExpression
     * @param <TMember>
     * @return
     */
    default <TMember extends Number> BigDecimal sumBigDecimalNotNull(SQLExpression2<ColumnResultSelector<T1>, ColumnResultSelector<T2>> columnSelectorExpression) {
        return sumBigDecimalOrDefault(columnSelectorExpression, BigDecimal.ZERO);
    }

    <TMember extends Number> BigDecimal sumBigDecimalOrDefault(SQLExpression2<ColumnResultSelector<T1>, ColumnResultSelector<T2>> columnSelectorExpression, BigDecimal def);

    default <TMember extends Number> TMember sumOrNull(SQLExpression2<ColumnResultSelector<T1>, ColumnResultSelector<T2>> columnSelectorExpression) {
        return sumOrDefault(columnSelectorExpression, null);
    }

    <TMember extends Number> TMember sumOrDefault(SQLExpression2<ColumnResultSelector<T1>, ColumnResultSelector<T2>> columnSelectorExpression, TMember def);

    default <TMember> TMember maxOrNull(SQLExpression2<ColumnResultSelector<T1>, ColumnResultSelector<T2>> columnSelectorExpression) {
        return maxOrDefault(columnSelectorExpression, null);
    }

    <TMember> TMember maxOrDefault(SQLExpression2<ColumnResultSelector<T1>, ColumnResultSelector<T2>> columnSelectorExpression, TMember def);

    default <TMember> TMember minOrNull(SQLExpression2<ColumnResultSelector<T1>, ColumnResultSelector<T2>> columnSelectorExpression) {
        return minOrDefault(columnSelectorExpression, null);
    }

    <TMember> TMember minOrDefault(SQLExpression2<ColumnResultSelector<T1>, ColumnResultSelector<T2>> columnSelectorExpression, TMember def);

    default <TMember extends Number> TMember avgOrNull(SQLExpression2<ColumnResultSelector<T1>, ColumnResultSelector<T2>> columnSelectorExpression) {
        return avgOrDefault(columnSelectorExpression, null);
    }

    <TMember extends Number> TMember avgOrDefault(SQLExpression2<ColumnResultSelector<T1>, ColumnResultSelector<T2>> columnSelectorExpression, TMember def);

    default Integer lenOrNull(SQLExpression2<ColumnResultSelector<T1>, ColumnResultSelector<T2>> columnSelectorExpression) {
        return lenOrDefault(columnSelectorExpression, null);
    }

    Integer lenOrDefault(SQLExpression2<ColumnResultSelector<T1>, ColumnResultSelector<T2>> columnSelectorExpression, Integer def);
    //endregion

    //region group
    @Override
    default ClientQueryable2<T1, T2> groupBy(SQLExpression1<GroupBySelector<T1>> selectExpression) {
        return groupBy(true, selectExpression);
    }

    @Override
    ClientQueryable2<T1, T2> groupBy(boolean condition, SQLExpression1<GroupBySelector<T1>> selectExpression);

    default ClientQueryable2<T1, T2> groupBy(SQLExpression2<GroupBySelector<T1>, GroupBySelector<T2>> selectExpression) {
        return groupBy(true, selectExpression);
    }

    ClientQueryable2<T1, T2> groupBy(boolean condition, SQLExpression2<GroupBySelector<T1>, GroupBySelector<T2>> selectExpression);

    //endregion
    //region order
    @Override
    default ClientQueryable2<T1, T2> orderByAsc(SQLExpression1<ColumnSelector<T1>> selectExpression) {
        return orderByAsc(true, selectExpression);
    }

    @Override
    ClientQueryable2<T1, T2> orderByAsc(boolean condition, SQLExpression1<ColumnSelector<T1>> selectExpression);

    default ClientQueryable2<T1, T2> orderByAsc(SQLExpression2<ColumnSelector<T1>, ColumnSelector<T2>> selectExpression) {
        return orderByAsc(true, selectExpression);
    }

    ClientQueryable2<T1, T2> orderByAsc(boolean condition, SQLExpression2<ColumnSelector<T1>, ColumnSelector<T2>> selectExpression);

    @Override
    default ClientQueryable2<T1, T2> orderByDesc(SQLExpression1<ColumnSelector<T1>> selectExpression) {
        return orderByDesc(true, selectExpression);
    }

    @Override
    ClientQueryable2<T1, T2> orderByDesc(boolean condition, SQLExpression1<ColumnSelector<T1>> selectExpression);

    default ClientQueryable2<T1, T2> orderByDesc(SQLExpression2<ColumnSelector<T1>, ColumnSelector<T2>> selectExpression) {
        return orderByDesc(true, selectExpression);
    }

    ClientQueryable2<T1, T2> orderByDesc(boolean condition, SQLExpression2<ColumnSelector<T1>, ColumnSelector<T2>> selectExpression);
    //endregion
    //region limit

    @Override
    default ClientQueryable2<T1, T2> limit(long rows) {
        return limit(true, rows);
    }

    @Override
    default ClientQueryable2<T1, T2> limit(boolean condition, long rows) {
        return limit(condition, 0, rows);
    }

    @Override
    default ClientQueryable2<T1, T2> limit(long offset, long rows) {
        return limit(true, offset, rows);
    }

    @Override
    ClientQueryable2<T1, T2> limit(boolean condition, long offset, long rows);

    default ClientQueryable2<T1, T2> distinct() {
        return distinct(true);
    }

    ClientQueryable2<T1, T2> distinct(boolean condition);

    //endregion
    @Override
    ClientQueryable2<T1, T2> disableLogicDelete();

    @Override
    ClientQueryable2<T1, T2> enableLogicDelete();

    @Override
    ClientQueryable2<T1, T2> useLogicDelete(boolean enable);

    @Override
    ClientQueryable2<T1, T2> noInterceptor();

    @Override
    ClientQueryable2<T1, T2> useInterceptor(String name);

    @Override
    ClientQueryable2<T1, T2> noInterceptor(String name);

    @Override
    ClientQueryable2<T1, T2> useInterceptor();

    /**
     * 自动将查询结果集合全部添加到当前上下文追踪中,如果当前查询结果十分庞大,并且更新数据只有个别条数,建议不要使用
     * 追踪查询，可以通过开启追踪后使用普通的查询，然后添加到当前的追踪上下文中{@link EasyQueryClient#addTracking(Object)},开始先数据追踪的差异更新
     *
     * @return
     */
    @Override
    ClientQueryable2<T1, T2> asTracking();

    @Override
    ClientQueryable2<T1, T2> asNoTracking();

    @Override
    ClientQueryable2<T1, T2> queryLargeColumn(boolean queryLarge);

    @Override
    ClientQueryable2<T1, T2> useShardingConfigure(int maxShardingQueryLimit, ConnectionModeEnum connectionMode);

    @Override
    ClientQueryable2<T1, T2> useMaxShardingQueryLimit(int maxShardingQueryLimit);

    @Override
    ClientQueryable2<T1, T2> useConnectionMode(ConnectionModeEnum connectionMode);

    /**
     * 将当前表达式最近的一张表的表名修改成 {@param tableName}
     * 如果当前最近的表是正常的数据库表名,那么直接将表名改写
     * 如果当前最近的表是匿名表比如嵌套queryable的表那么将alias改成对应的表名
     *
     * @param tableName
     * @return
     */
    @Override
    default ClientQueryable2<T1, T2> asTable(String tableName) {
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
    ClientQueryable2<T1, T2> asTable(Function<String, String> tableNameAs);

    @Override
    ClientQueryable2<T1, T2> asAlias(String alias);

    SQLExpressionProvider<T2> getSQLExpressionProvider2();
}
