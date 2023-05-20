package com.easy.query.core.basic.api.select;

import com.easy.query.core.basic.api.select.provider.EasyQuerySQLBuilderProvider2;
import com.easy.query.core.basic.api.select.provider.SQLExpressionProvider;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.lambda.SQLExpression2;
import com.easy.query.core.expression.lambda.SQLExpression3;
import com.easy.query.core.expression.parser.core.SQLColumnResultSelector;
import com.easy.query.core.expression.parser.core.SQLWherePredicate;
import com.easy.query.core.expression.parser.core.SQLGroupBySelector;
import com.easy.query.core.expression.parser.core.SQLColumnSelector;
import com.easy.query.core.expression.parser.core.SQLColumnAsSelector;
import com.easy.query.core.enums.sharding.ConnectionModeEnum;

import java.math.BigDecimal;
import java.util.function.Function;


/**
 * @FileName: Select2.java
 * @Description: 文件说明
 * @Date: 2023/2/6 22:42
 * @author xuejiaming
 */
public interface Queryable2<T1, T2> extends Queryable<T1> {
    <T3> Queryable3<T1, T2, T3> leftJoin(Class<T3> joinClass, SQLExpression3<SQLWherePredicate<T1>, SQLWherePredicate<T2>, SQLWherePredicate<T3>> on);
    <T3> Queryable3<T1, T2, T3> leftJoin(Queryable<T3> joinQueryable, SQLExpression3<SQLWherePredicate<T1>, SQLWherePredicate<T2>, SQLWherePredicate<T3>> on);
    <T3> Queryable3<T1, T2, T3> rightJoin(Class<T3> joinClass, SQLExpression3<SQLWherePredicate<T1>, SQLWherePredicate<T2>, SQLWherePredicate<T3>> on);
    <T3> Queryable3<T1, T2, T3> rightJoin(Queryable<T3> joinQueryable, SQLExpression3<SQLWherePredicate<T1>, SQLWherePredicate<T2>, SQLWherePredicate<T3>> on);

    <T3> Queryable3<T1, T2, T3> innerJoin(Class<T3> joinClass, SQLExpression3<SQLWherePredicate<T1>, SQLWherePredicate<T2>, SQLWherePredicate<T3>> on);
    <T3> Queryable3<T1, T2, T3> innerJoin(Queryable<T3> joinQueryable, SQLExpression3<SQLWherePredicate<T1>, SQLWherePredicate<T2>, SQLWherePredicate<T3>> on);

    //region where

    default Queryable2<T1, T2> whereObject(Object object){
        return whereObject(true,object);
    }
    Queryable2<T1, T2> whereObject(boolean condition, Object object);
    @Override
    default Queryable2<T1, T2> where(SQLExpression1<SQLWherePredicate<T1>> whereExpression) {
        return where(true, whereExpression);
    }

    @Override
    Queryable2<T1, T2> where(boolean condition, SQLExpression1<SQLWherePredicate<T1>> whereExpression);
    default Queryable2<T1, T2> where(SQLExpression2<SQLWherePredicate<T1>, SQLWherePredicate<T2>> whereExpression) {
        return where(true, whereExpression);
    }

    Queryable2<T1, T2> where(boolean condition, SQLExpression2<SQLWherePredicate<T1>, SQLWherePredicate<T2>> whereExpression);

    //endregion

    //region select
    <TR> Queryable<TR> select(Class<TR> resultClass, SQLExpression2<SQLColumnAsSelector<T1, TR>, SQLColumnAsSelector<T2, TR>> selectExpression);
    //endregion
    //region aggregate

    /**
     * 防止溢出
     *
     * @param columnSelectorExpression
     * @param <TMember>
     * @return
     */
    default <TMember extends Number> BigDecimal sumBigDecimalOrNull(SQLExpression2<SQLColumnResultSelector<T1, TMember>, SQLColumnResultSelector<T2, TMember>> columnSelectorExpression) {
        return sumBigDecimalOrDefault(columnSelectorExpression, null);
    }

    /**
     * 防止溢出
     *
     * @param columnSelectorExpression
     * @param <TMember>
     * @return
     */
    default <TMember extends Number> BigDecimal sumBigDecimalNotNull(SQLExpression2<SQLColumnResultSelector<T1, TMember>, SQLColumnResultSelector<T2, TMember>> columnSelectorExpression) {
        return sumBigDecimalOrDefault(columnSelectorExpression, BigDecimal.ZERO);
    }

    <TMember extends Number> BigDecimal sumBigDecimalOrDefault(SQLExpression2<SQLColumnResultSelector<T1, TMember>, SQLColumnResultSelector<T2, TMember>> columnSelectorExpression, BigDecimal def);

    default <TMember extends Number> TMember sumOrNull(SQLExpression2<SQLColumnResultSelector<T1, TMember>, SQLColumnResultSelector<T2, TMember>> columnSelectorExpression) {
        return sumOrDefault(columnSelectorExpression, null);
    }

    <TMember extends Number> TMember sumOrDefault(SQLExpression2<SQLColumnResultSelector<T1, TMember>, SQLColumnResultSelector<T2, TMember>> columnSelectorExpression, TMember def);

    default <TMember> TMember maxOrNull(SQLExpression2<SQLColumnResultSelector<T1, TMember>, SQLColumnResultSelector<T2, TMember>> columnSelectorExpression) {
        return maxOrDefault(columnSelectorExpression, null);
    }

    <TMember> TMember maxOrDefault(SQLExpression2<SQLColumnResultSelector<T1, TMember>, SQLColumnResultSelector<T2, TMember>> columnSelectorExpression, TMember def);

    default <TMember> TMember minOrNull(SQLExpression2<SQLColumnResultSelector<T1, TMember>, SQLColumnResultSelector<T2, TMember>> columnSelectorExpression) {
        return minOrDefault(columnSelectorExpression, null);
    }

    <TMember> TMember minOrDefault(SQLExpression2<SQLColumnResultSelector<T1, TMember>, SQLColumnResultSelector<T2, TMember>> columnSelectorExpression, TMember def);

    default <TMember extends Number> TMember avgOrNull(SQLExpression2<SQLColumnResultSelector<T1, TMember>, SQLColumnResultSelector<T2, TMember>> columnSelectorExpression) {
        return avgOrDefault(columnSelectorExpression, null);
    }

    <TMember extends Number> TMember avgOrDefault(SQLExpression2<SQLColumnResultSelector<T1, TMember>, SQLColumnResultSelector<T2, TMember>> columnSelectorExpression, TMember def);
    default Integer lenOrNull(SQLExpression2<SQLColumnResultSelector<T1, ?>, SQLColumnResultSelector<T2, ?>> columnSelectorExpression) {
        return lenOrDefault(columnSelectorExpression, null);
    }

    Integer lenOrDefault(SQLExpression2<SQLColumnResultSelector<T1, ?>, SQLColumnResultSelector<T2, ?>> columnSelectorExpression, Integer def);
    //endregion

    //region group
    default Queryable2<T1, T2> groupBy(SQLExpression2<SQLGroupBySelector<T1>, SQLGroupBySelector<T2>> selectExpression) {
        return groupBy(true, selectExpression);
    }

    Queryable2<T1, T2> groupBy(boolean condition, SQLExpression2<SQLGroupBySelector<T1>, SQLGroupBySelector<T2>> selectExpression);

    //endregion
    //region order
    default Queryable2<T1, T2> orderByAsc(SQLExpression2<SQLColumnSelector<T1>, SQLColumnSelector<T2>> selectExpression) {
        return orderByAsc(true, selectExpression);
    }

    Queryable2<T1, T2> orderByAsc(boolean condition, SQLExpression2<SQLColumnSelector<T1>, SQLColumnSelector<T2>> selectExpression);

    default Queryable2<T1, T2> orderByDesc(SQLExpression2<SQLColumnSelector<T1>, SQLColumnSelector<T2>> selectExpression) {
        return orderByDesc(true, selectExpression);
    }

    Queryable2<T1, T2> orderByDesc(boolean condition, SQLExpression2<SQLColumnSelector<T1>, SQLColumnSelector<T2>> selectExpression);
    //endregion
    //region limit

    @Override
    default Queryable2<T1, T2> limit(long rows) {
        return limit(true, rows);
    }

    @Override
    default Queryable2<T1, T2> limit(boolean condition, long rows) {
        return limit(condition, 0, rows);
    }

    @Override
    default Queryable2<T1, T2> limit(long offset, long rows) {
        return limit(true, offset, rows);
    }

    @Override
    Queryable2<T1, T2> limit(boolean condition, long offset, long rows);

    default Queryable2<T1, T2> distinct() {
        return distinct(true);
    }

    Queryable2<T1, T2> distinct(boolean condition);
    //endregion
    @Override
   Queryable2<T1, T2> disableLogicDelete();

    @Override
    Queryable2<T1, T2> enableLogicDelete();
    @Override
    Queryable2<T1, T2> useLogicDelete(boolean enable);

    @Override
    Queryable2<T1, T2> noInterceptor();
    @Override
    Queryable2<T1, T2> useInterceptor(String name);
    @Override
    Queryable2<T1, T2> noInterceptor(String name);
    @Override
    Queryable2<T1, T2> useInterceptor();
    /**
     * 自动将查询结果集合全部添加到当前上下文追踪中,如果当前查询结果十分庞大,并且更新数据只有个别条数,建议不要使用
     * 追踪查询，可以通过开启追踪后使用普通的查询，然后添加到当前的追踪上下文中{@link com.easy.query.core.api.client.EasyQuery#addTracking(Object)},开始先数据追踪的差异更新
     * @return
     */
    @Override
    Queryable2<T1, T2> asTracking();
    @Override
    Queryable2<T1, T2> asNoTracking();

    @Override
    Queryable2<T1, T2> queryLargeColumn(boolean queryLarge);

    @Override
    Queryable2<T1, T2> useShardingConfigure(int maxShardingQueryLimit, ConnectionModeEnum connectionMode);
    @Override
    Queryable2<T1, T2> useMaxShardingQueryLimit(int maxShardingQueryLimit);
    @Override
    Queryable2<T1, T2> useConnectionMode(ConnectionModeEnum connectionMode);

    /**
     * 将当前表达式最近的一张表的表名修改成 {@param tableName}
     * 如果当前最近的表是正常的数据库表名,那么直接将表名改写
     * 如果当前最近的表是匿名表比如嵌套queryable的表那么将alias改成对应的表名
     * @param tableName
     * @return
     */
    @Override
    default Queryable2<T1, T2> asTable(String tableName){
        return asTable(old->tableName);
    }

    /**
     * 将当前表达式最近的一张表的表名修改成 {@param tableNameAs}返回的表名
     * 如果当前最近的表是正常的数据库表名,那么直接将表名改写
     * 如果当前最近的表是匿名表比如嵌套queryable的表那么将alias改成对应的表名
     * @param tableNameAs
     * @return
     */
    @Override
    Queryable2<T1, T2> asTable(Function<String,String> tableNameAs);

    SQLExpressionProvider<T2> getSqlExpressionProvider2();
}
