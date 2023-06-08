package com.easy.query.core.basic.api.select;

import com.easy.query.core.api.client.EasyQueryClient;
import com.easy.query.core.basic.api.select.provider.SQLExpressionProvider;
import com.easy.query.core.enums.sharding.ConnectionModeEnum;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.lambda.SQLExpression3;
import com.easy.query.core.expression.lambda.SQLExpression4;
import com.easy.query.core.expression.parser.core.base.*;

import java.math.BigDecimal;
import java.util.function.Function;


/**
 * @author xuejiaming
 * @FileName: Select3.java
 * @Description: 文件说明
 * @Date: 2023/2/6 22:44
 */
public interface ClientQueryable3<T1, T2, T3> extends ClientQueryable<T1> {

    <T4> ClientQueryable4<T1, T2, T3, T4> leftJoin(Class<T4> joinClass, SQLExpression4<WherePredicate<T1>, WherePredicate<T2>, WherePredicate<T3>, WherePredicate<T4>> on);

    <T4> ClientQueryable4<T1, T2, T3, T4> leftJoin(ClientQueryable<T4> joinQueryable, SQLExpression4<WherePredicate<T1>, WherePredicate<T2>, WherePredicate<T3>, WherePredicate<T4>> on);

    <T4> ClientQueryable4<T1, T2, T3, T4> rightJoin(Class<T4> joinClass, SQLExpression4<WherePredicate<T1>, WherePredicate<T2>, WherePredicate<T3>, WherePredicate<T4>> on);

    <T4> ClientQueryable4<T1, T2, T3, T4> rightJoin(ClientQueryable<T4> joinQueryable, SQLExpression4<WherePredicate<T1>, WherePredicate<T2>, WherePredicate<T3>, WherePredicate<T4>> on);

    <T4> ClientQueryable4<T1, T2, T3, T4> innerJoin(Class<T4> joinClass, SQLExpression4<WherePredicate<T1>, WherePredicate<T2>, WherePredicate<T3>, WherePredicate<T4>> on);

    <T4> ClientQueryable4<T1, T2, T3, T4> innerJoin(ClientQueryable<T4> joinQueryable, SQLExpression4<WherePredicate<T1>, WherePredicate<T2>, WherePredicate<T3>, WherePredicate<T4>> on);

    //region where
    default ClientQueryable3<T1, T2, T3> whereObject(Object object) {
        return whereObject(true, object);
    }

    ClientQueryable3<T1, T2, T3> whereObject(boolean condition, Object object);

    @Override
    default ClientQueryable3<T1, T2, T3> where(SQLExpression1<WherePredicate<T1>> whereExpression) {
        return where(true, whereExpression);
    }

    @Override
    ClientQueryable3<T1, T2, T3> where(boolean condition, SQLExpression1<WherePredicate<T1>> whereExpression);

    default ClientQueryable3<T1, T2, T3> where(SQLExpression3<WherePredicate<T1>, WherePredicate<T2>, WherePredicate<T3>> whereExpression) {
        return where(true, whereExpression);
    }

    ClientQueryable3<T1, T2, T3> where(boolean condition, SQLExpression3<WherePredicate<T1>, WherePredicate<T2>, WherePredicate<T3>> whereExpression);

    //endregion

    //region select
    <TR> ClientQueryable<TR> select(Class<TR> resultClass, SQLExpression3<ColumnAsSelector<T1, TR>, ColumnAsSelector<T2, TR>, ColumnAsSelector<T3, TR>> selectExpression);
    //endregion

    //region aggregate

    /**
     * 防止溢出
     *
     * @param columnSelectorExpression
     * @param <TMember>
     * @return
     */
    default <TMember extends Number> BigDecimal sumBigDecimalOrNull(SQLExpression3<ColumnResultSelector<T1>, ColumnResultSelector<T2>, ColumnResultSelector<T3>> columnSelectorExpression) {
        return sumBigDecimalOrDefault(columnSelectorExpression, null);
    }

    <TMember extends Number> BigDecimal sumBigDecimalOrDefault(SQLExpression3<ColumnResultSelector<T1>, ColumnResultSelector<T2>, ColumnResultSelector<T3>> columnSelectorExpression, BigDecimal def);

    default <TMember extends Number> TMember sumOrNull(SQLExpression3<ColumnResultSelector<T1>, ColumnResultSelector<T2>, ColumnResultSelector<T3>> columnSelectorExpression) {
        return sumOrDefault(columnSelectorExpression, null);
    }

    <TMember extends Number> TMember sumOrDefault(SQLExpression3<ColumnResultSelector<T1>, ColumnResultSelector<T2>, ColumnResultSelector<T3>> columnSelectorExpression, TMember def);

    default <TMember> TMember maxOrNull(SQLExpression3<ColumnResultSelector<T1>, ColumnResultSelector<T2>, ColumnResultSelector<T3>> columnSelectorExpression) {
        return maxOrDefault(columnSelectorExpression, null);
    }

    <TMember> TMember maxOrDefault(SQLExpression3<ColumnResultSelector<T1>, ColumnResultSelector<T2>, ColumnResultSelector<T3>> columnSelectorExpression, TMember def);

    default <TMember> TMember minOrNull(SQLExpression3<ColumnResultSelector<T1>, ColumnResultSelector<T2>, ColumnResultSelector<T3>> columnSelectorExpression) {
        return minOrDefault(columnSelectorExpression, null);
    }

    <TMember> TMember minOrDefault(SQLExpression3<ColumnResultSelector<T1>, ColumnResultSelector<T2>, ColumnResultSelector<T3>> columnSelectorExpression, TMember def);

    default <TMember extends Number> Double avgOrNull(SQLExpression3<ColumnResultSelector<T1>, ColumnResultSelector<T2>, ColumnResultSelector<T3>> columnSelectorExpression) {
        return avgOrDefault(columnSelectorExpression, null, Double.class);
    }

    default <TMember extends Number> BigDecimal avgBigDecimalOrNull(SQLExpression3<ColumnResultSelector<T1>, ColumnResultSelector<T2>, ColumnResultSelector<T3>> columnSelectorExpression) {
        return avgOrDefault(columnSelectorExpression, null, BigDecimal.class);
    }

    default <TMember extends Number> Float avgFloatOrNull(SQLExpression3<ColumnResultSelector<T1>, ColumnResultSelector<T2>, ColumnResultSelector<T3>> columnSelectorExpression) {
        return avgOrDefault(columnSelectorExpression, null, Float.class);
    }

    default <TMember extends Number> Double avgOrDefault(SQLExpression3<ColumnResultSelector<T1>, ColumnResultSelector<T2>, ColumnResultSelector<T3>> columnSelectorExpression, Double def) {
        return avgOrDefault(columnSelectorExpression, def, Double.class);
    }

    default <TMember extends Number> BigDecimal avgBigDecimalOrDefault(SQLExpression3<ColumnResultSelector<T1>, ColumnResultSelector<T2>, ColumnResultSelector<T3>> columnSelectorExpression, BigDecimal def) {
        return avgOrDefault(columnSelectorExpression, def, BigDecimal.class);
    }

    default <TMember extends Number> Float avgFloatOrDefault(SQLExpression3<ColumnResultSelector<T1>, ColumnResultSelector<T2>, ColumnResultSelector<T3>> columnSelectorExpression, Float def) {
        return avgOrDefault(columnSelectorExpression, def, Float.class);
    }

    <TMember extends Number, TResult extends Number> TResult avgOrDefault(SQLExpression3<ColumnResultSelector<T1>, ColumnResultSelector<T2>, ColumnResultSelector<T3>> columnSelectorExpression, TResult def, Class<TResult> resultClass);

    //endregion

    //region group
    @Override
    default ClientQueryable3<T1, T2, T3> groupBy(SQLExpression1<GroupBySelector<T1>> selectExpression) {
        return groupBy(true, selectExpression);
    }

    @Override
    ClientQueryable3<T1, T2, T3> groupBy(boolean condition, SQLExpression1<GroupBySelector<T1>> selectExpression);

    default ClientQueryable3<T1, T2, T3> groupBy(SQLExpression3<GroupBySelector<T1>, GroupBySelector<T2>, GroupBySelector<T3>> selectExpression) {
        return groupBy(true, selectExpression);
    }

    ClientQueryable3<T1, T2, T3> groupBy(boolean condition, SQLExpression3<GroupBySelector<T1>, GroupBySelector<T2>, GroupBySelector<T3>> selectExpression);


    @Override
    default ClientQueryable3<T1, T2, T3> having(SQLExpression1<WhereAggregatePredicate<T1>> predicateExpression) {
        return having(true, predicateExpression);
    }

    @Override
    ClientQueryable3<T1, T2, T3> having(boolean condition, SQLExpression1<WhereAggregatePredicate<T1>> predicateExpression);

    default ClientQueryable3<T1, T2, T3> having(SQLExpression3<WhereAggregatePredicate<T1>, WhereAggregatePredicate<T2>, WhereAggregatePredicate<T3>> predicateExpression) {
        return having(true, predicateExpression);
    }

    ClientQueryable3<T1, T2, T3> having(boolean condition, SQLExpression3<WhereAggregatePredicate<T1>, WhereAggregatePredicate<T2>, WhereAggregatePredicate<T3>> predicateExpression);

    //endregion
    //region order
    @Override
    default ClientQueryable3<T1, T2, T3> orderByAsc(SQLExpression1<ColumnSelector<T1>> selectExpression) {
        return orderByAsc(true, selectExpression);
    }

    @Override
    ClientQueryable3<T1, T2, T3> orderByAsc(boolean condition, SQLExpression1<ColumnSelector<T1>> selectExpression);

    default ClientQueryable3<T1, T2, T3> orderByAsc(SQLExpression3<ColumnSelector<T1>, ColumnSelector<T2>, ColumnSelector<T3>> selectExpression) {
        return orderByAsc(true, selectExpression);
    }

    ClientQueryable3<T1, T2, T3> orderByAsc(boolean condition, SQLExpression3<ColumnSelector<T1>, ColumnSelector<T2>, ColumnSelector<T3>> selectExpression);

    @Override
    default ClientQueryable3<T1, T2, T3> orderByDesc(SQLExpression1<ColumnSelector<T1>> selectExpression) {
        return orderByDesc(true, selectExpression);
    }

    @Override
    ClientQueryable3<T1, T2, T3> orderByDesc(boolean condition, SQLExpression1<ColumnSelector<T1>> selectExpression);

    default ClientQueryable3<T1, T2, T3> orderByDesc(SQLExpression3<ColumnSelector<T1>, ColumnSelector<T2>, ColumnSelector<T3>> selectExpression) {
        return orderByDesc(true, selectExpression);
    }

    ClientQueryable3<T1, T2, T3> orderByDesc(boolean condition, SQLExpression3<ColumnSelector<T1>, ColumnSelector<T2>, ColumnSelector<T3>> selectExpression);
    //endregion
    //region limit

    @Override
    default ClientQueryable3<T1, T2, T3> limit(long rows) {
        return limit(true, rows);
    }

    @Override
    default ClientQueryable3<T1, T2, T3> limit(boolean condition, long rows) {
        return limit(condition, 0, rows);
    }

    @Override
    default ClientQueryable3<T1, T2, T3> limit(long offset, long rows) {
        return limit(true, offset, rows);
    }

    @Override
    ClientQueryable3<T1, T2, T3> limit(boolean condition, long offset, long rows);

    default ClientQueryable3<T1, T2, T3> distinct() {
        return distinct(true);
    }

    ClientQueryable3<T1, T2, T3> distinct(boolean condition);
    //endregion

    @Override
    ClientQueryable3<T1, T2, T3> disableLogicDelete();

    @Override
    ClientQueryable3<T1, T2, T3> enableLogicDelete();

    @Override
    ClientQueryable3<T1, T2, T3> useLogicDelete(boolean enable);

    @Override
    ClientQueryable3<T1, T2, T3> noInterceptor();

    @Override
    ClientQueryable3<T1, T2, T3> useInterceptor(String name);

    @Override
    ClientQueryable3<T1, T2, T3> noInterceptor(String name);

    @Override
    ClientQueryable3<T1, T2, T3> useInterceptor();

    /**
     * 自动将查询结果集合全部添加到当前上下文追踪中,如果当前查询结果十分庞大,并且更新数据只有个别条数,建议不要使用
     * 追踪查询，可以通过开启追踪后使用普通的查询，然后添加到当前的追踪上下文中{@link EasyQueryClient#addTracking(Object)},开始先数据追踪的差异更新
     *
     * @return
     */
    @Override
    ClientQueryable3<T1, T2, T3> asTracking();

    @Override
    ClientQueryable3<T1, T2, T3> asNoTracking();

    @Override
    ClientQueryable3<T1, T2, T3> queryLargeColumn(boolean queryLarge);

    @Override
    ClientQueryable3<T1, T2, T3> useShardingConfigure(int maxShardingQueryLimit, ConnectionModeEnum connectionMode);

    @Override
    ClientQueryable3<T1, T2, T3> useMaxShardingQueryLimit(int maxShardingQueryLimit);

    @Override
    ClientQueryable3<T1, T2, T3> useConnectionMode(ConnectionModeEnum connectionMode);

    /**
     * 将当前表达式最近的一张表的表名修改成 {@param tableName}
     * 如果当前最近的表是正常的数据库表名,那么直接将表名改写
     * 如果当前最近的表是匿名表比如嵌套queryable的表那么将alias改成对应的表名
     *
     * @param tableName
     * @return
     */
    @Override
    default ClientQueryable3<T1, T2, T3> asTable(String tableName) {
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
    ClientQueryable3<T1, T2, T3> asTable(Function<String, String> tableNameAs);

    @Override
    default ClientQueryable3<T1, T2, T3> asSchema(String schema) {
        return asSchema(old -> schema);
    }

    @Override
    ClientQueryable3<T1, T2, T3> asSchema(Function<String, String> schemaAs);

    @Override
    ClientQueryable3<T1, T2, T3> asAlias(String alias);

    SQLExpressionProvider<T2> getSQLExpressionProvider2();

    SQLExpressionProvider<T3> getSQLExpressionProvider3();
}
