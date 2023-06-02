package com.easy.query.core.basic.api.select;

import com.easy.query.core.api.client.EasyObjectQuery;
import com.easy.query.core.basic.api.select.provider.SQLExpressionProvider;
import com.easy.query.core.enums.sharding.ConnectionModeEnum;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.lambda.SQLExpression4;
import com.easy.query.core.expression.parser.core.base.ColumnAsSelector;
import com.easy.query.core.expression.parser.core.base.ColumnResultSelector;
import com.easy.query.core.expression.parser.core.base.ColumnSelector;
import com.easy.query.core.expression.parser.core.base.GroupBySelector;
import com.easy.query.core.expression.parser.core.base.WherePredicate;

import java.math.BigDecimal;
import java.util.function.Function;

/**
 * @author xuejiaming
 * @FileName: Queryable4.java
 * @Description: 文件说明
 * @Date: 2023/3/9 12:10
 */
public interface ObjectQueryable4<T1, T2, T3, T4> extends ObjectQueryable<T1> {
    //region where
    default ObjectQueryable4<T1, T2, T3, T4> whereObject(Object object) {
        return whereObject(true, object);
    }

    ObjectQueryable4<T1, T2, T3, T4> whereObject(boolean condition, Object object);

    @Override
    default ObjectQueryable4<T1, T2, T3, T4> where(SQLExpression1<WherePredicate<T1>> whereExpression) {
        return where(true, whereExpression);
    }

    @Override
    ObjectQueryable4<T1, T2, T3, T4> where(boolean condition, SQLExpression1<WherePredicate<T1>> whereExpression);

    default ObjectQueryable4<T1, T2, T3, T4> where(SQLExpression4<WherePredicate<T1>, WherePredicate<T2>, WherePredicate<T3>, WherePredicate<T4>> whereExpression) {
        return where(true, whereExpression);
    }

    ObjectQueryable4<T1, T2, T3, T4> where(boolean condition, SQLExpression4<WherePredicate<T1>, WherePredicate<T2>, WherePredicate<T3>, WherePredicate<T4>> whereExpression);

    //endregion
    //region select
    <TR> ObjectQueryable<TR> select(Class<TR> resultClass, SQLExpression4<ColumnAsSelector<T1, TR>, ColumnAsSelector<T2, TR>, ColumnAsSelector<T3, TR>, ColumnAsSelector<T4, TR>> selectExpression);
    //endregion

    //region aggregate

    /**
     * 防止溢出
     *
     * @param columnSelectorExpression
     * @param <TMember>
     * @return
     */
    default <TMember extends Number> BigDecimal sumBigDecimalOrNull(SQLExpression4<ColumnResultSelector<T1>, ColumnResultSelector<T2>, ColumnResultSelector<T3>, ColumnResultSelector<T4>> columnSelectorExpression) {
        return sumBigDecimalOrDefault(columnSelectorExpression, null);
    }

    /**
     * 防止溢出
     *
     * @param columnSelectorExpression
     * @param <TMember>
     * @return
     */
    default <TMember extends Number> BigDecimal sumBigDecimalNotNull(SQLExpression4<ColumnResultSelector<T1>, ColumnResultSelector<T2>, ColumnResultSelector<T3>, ColumnResultSelector<T4>> columnSelectorExpression) {
        return sumBigDecimalOrDefault(columnSelectorExpression, BigDecimal.ZERO);
    }

    <TMember extends Number> BigDecimal sumBigDecimalOrDefault(SQLExpression4<ColumnResultSelector<T1>, ColumnResultSelector<T2>, ColumnResultSelector<T3>, ColumnResultSelector<T4>> columnSelectorExpression, BigDecimal def);

    default <TMember extends Number> TMember sumOrNull(SQLExpression4<ColumnResultSelector<T1>, ColumnResultSelector<T2>, ColumnResultSelector<T3>, ColumnResultSelector<T4>> columnSelectorExpression) {
        return sumOrDefault(columnSelectorExpression, null);
    }

    <TMember extends Number> TMember sumOrDefault(SQLExpression4<ColumnResultSelector<T1>, ColumnResultSelector<T2>, ColumnResultSelector<T3>, ColumnResultSelector<T4>> columnSelectorExpression, TMember def);

    default <TMember> TMember maxOrNull(SQLExpression4<ColumnResultSelector<T1>, ColumnResultSelector<T2>, ColumnResultSelector<T3>, ColumnResultSelector<T4>> columnSelectorExpression) {
        return maxOrDefault(columnSelectorExpression, null);
    }

    <TMember> TMember maxOrDefault(SQLExpression4<ColumnResultSelector<T1>, ColumnResultSelector<T2>, ColumnResultSelector<T3>, ColumnResultSelector<T4>> columnSelectorExpression, TMember def);

    default <TMember> TMember minOrNull(SQLExpression4<ColumnResultSelector<T1>, ColumnResultSelector<T2>, ColumnResultSelector<T3>, ColumnResultSelector<T4>> columnSelectorExpression) {
        return minOrDefault(columnSelectorExpression, null);
    }

    <TMember> TMember minOrDefault(SQLExpression4<ColumnResultSelector<T1>, ColumnResultSelector<T2>, ColumnResultSelector<T3>, ColumnResultSelector<T4>> columnSelectorExpression, TMember def);

    default <TMember> TMember avgOrNull(SQLExpression4<ColumnResultSelector<T1>, ColumnResultSelector<T2>, ColumnResultSelector<T3>, ColumnResultSelector<T4>> columnSelectorExpression) {
        return avgOrDefault(columnSelectorExpression, null);
    }

    <TMember> TMember avgOrDefault(SQLExpression4<ColumnResultSelector<T1>, ColumnResultSelector<T2>, ColumnResultSelector<T3>, ColumnResultSelector<T4>> columnSelectorExpression, TMember def);

    default Integer lenOrNull(SQLExpression4<ColumnResultSelector<T1>, ColumnResultSelector<T2>, ColumnResultSelector<T3>, ColumnResultSelector<T4>> columnSelectorExpression) {
        return lenOrDefault(columnSelectorExpression, null);
    }

    Integer lenOrDefault(SQLExpression4<ColumnResultSelector<T1>, ColumnResultSelector<T2>, ColumnResultSelector<T3>, ColumnResultSelector<T4>> columnSelectorExpression, Integer def);
    //endregion


    //region group
    @Override
    default ObjectQueryable4<T1, T2, T3, T4> groupBy(SQLExpression1<GroupBySelector<T1>> selectExpression) {
        return groupBy(true, selectExpression);
    }

    @Override
    ObjectQueryable4<T1, T2, T3, T4> groupBy(boolean condition, SQLExpression1<GroupBySelector<T1>> selectExpression);

    default ObjectQueryable4<T1, T2, T3, T4> groupBy(SQLExpression4<GroupBySelector<T1>, GroupBySelector<T2>, GroupBySelector<T3>, GroupBySelector<T4>> selectExpression) {
        return groupBy(true, selectExpression);
    }

    ObjectQueryable4<T1, T2, T3, T4> groupBy(boolean condition, SQLExpression4<GroupBySelector<T1>, GroupBySelector<T2>, GroupBySelector<T3>, GroupBySelector<T4>> selectExpression);

    //endregion
    //region order
    @Override
    default ObjectQueryable4<T1, T2, T3, T4> orderByAsc(SQLExpression1<ColumnSelector<T1>> selectExpression) {
        return orderByAsc(true, selectExpression);
    }

    @Override
    ObjectQueryable4<T1, T2, T3, T4> orderByAsc(boolean condition, SQLExpression1<ColumnSelector<T1>> selectExpression);

    default ObjectQueryable4<T1, T2, T3, T4> orderByAsc(SQLExpression4<ColumnSelector<T1>, ColumnSelector<T2>, ColumnSelector<T3>, ColumnSelector<T4>> selectExpression) {
        return orderByAsc(true, selectExpression);
    }

    ObjectQueryable4<T1, T2, T3, T4> orderByAsc(boolean condition, SQLExpression4<ColumnSelector<T1>, ColumnSelector<T2>, ColumnSelector<T3>, ColumnSelector<T4>> selectExpression);

    @Override
    default ObjectQueryable4<T1, T2, T3, T4> orderByDesc(SQLExpression1<ColumnSelector<T1>> selectExpression) {
        return orderByDesc(true, selectExpression);
    }

    @Override
    ObjectQueryable4<T1, T2, T3, T4> orderByDesc(boolean condition, SQLExpression1<ColumnSelector<T1>> selectExpression);

    default ObjectQueryable4<T1, T2, T3, T4> orderByDesc(SQLExpression4<ColumnSelector<T1>, ColumnSelector<T2>, ColumnSelector<T3>, ColumnSelector<T4>> selectExpression) {
        return orderByDesc(true, selectExpression);
    }

    ObjectQueryable4<T1, T2, T3, T4> orderByDesc(boolean condition, SQLExpression4<ColumnSelector<T1>, ColumnSelector<T2>, ColumnSelector<T3>, ColumnSelector<T4>> selectExpression);
    //endregion
    //region limit

    @Override
    default ObjectQueryable4<T1, T2, T3, T4> limit(long rows) {
        return limit(true, rows);
    }

    @Override
    default ObjectQueryable4<T1, T2, T3, T4> limit(boolean condition, long rows) {
        return limit(condition, 0, rows);
    }

    @Override
    default ObjectQueryable4<T1, T2, T3, T4> limit(long offset, long rows) {
        return limit(true, offset, rows);
    }

    @Override
    ObjectQueryable4<T1, T2, T3, T4> limit(boolean condition, long offset, long rows);

    default ObjectQueryable4<T1, T2, T3, T4> distinct() {
        return distinct(true);
    }

    ObjectQueryable4<T1, T2, T3, T4> distinct(boolean condition);
    //endregion

    @Override
    ObjectQueryable4<T1, T2, T3, T4> disableLogicDelete();

    @Override
    ObjectQueryable4<T1, T2, T3, T4> enableLogicDelete();

    @Override
    ObjectQueryable4<T1, T2, T3, T4> useLogicDelete(boolean enable);

    @Override
    ObjectQueryable4<T1, T2, T3, T4> noInterceptor();

    @Override
    ObjectQueryable4<T1, T2, T3, T4> useInterceptor();

    /**
     * 自动将查询结果集合全部添加到当前上下文追踪中,如果当前查询结果十分庞大,并且更新数据只有个别条数,建议不要使用
     * 追踪查询，可以通过开启追踪后使用普通的查询，然后添加到当前的追踪上下文中{@link EasyObjectQuery#addTracking(Object)},开始先数据追踪的差异更新
     *
     * @return
     */
    @Override
    ObjectQueryable4<T1, T2, T3, T4> asTracking();

    @Override
    ObjectQueryable4<T1, T2, T3, T4> asNoTracking();

    @Override
    ObjectQueryable4<T1, T2, T3, T4> queryLargeColumn(boolean queryLarge);

    @Override
    ObjectQueryable4<T1, T2, T3, T4> useShardingConfigure(int maxShardingQueryLimit, ConnectionModeEnum connectionMode);

    @Override
    ObjectQueryable4<T1, T2, T3, T4> useMaxShardingQueryLimit(int maxShardingQueryLimit);

    @Override
    ObjectQueryable4<T1, T2, T3, T4> useConnectionMode(ConnectionModeEnum connectionMode);

    /**
     * 将当前表达式最近的一张表的表名修改成 {@param tableName}
     * 如果当前最近的表是正常的数据库表名,那么直接将表名改写
     * 如果当前最近的表是匿名表比如嵌套queryable的表那么将alias改成对应的表名
     *
     * @param tableName
     * @return
     */
    @Override
    default ObjectQueryable4<T1, T2, T3, T4> asTable(String tableName) {
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
    ObjectQueryable4<T1, T2, T3, T4> asTable(Function<String, String> tableNameAs);

    @Override
    ObjectQueryable4<T1, T2, T3, T4> asAlias(String alias);

    SQLExpressionProvider<T2> getSQLExpressionProvider2();

    SQLExpressionProvider<T3> getSQLExpressionProvider3();

    SQLExpressionProvider<T4> getSQLExpressionProvider4();
}
