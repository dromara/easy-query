package com.easy.query.core.basic.api.select;

import com.easy.query.core.basic.api.select.provider.EasyQuerySQLBuilderProvider3;
import com.easy.query.core.expression.lambda.SQLExpression;
import com.easy.query.core.expression.lambda.SQLExpression3;
import com.easy.query.core.expression.lambda.SQLExpression4;
import com.easy.query.core.expression.parser.core.SQLColumnResultSelector;
import com.easy.query.core.expression.parser.core.SQLWherePredicate;
import com.easy.query.core.expression.parser.core.SQLGroupBySelector;
import com.easy.query.core.expression.parser.core.SQLColumnSelector;
import com.easy.query.core.expression.parser.core.SQLColumnAsSelector;
import com.easy.query.core.enums.sharding.ConnectionModeEnum;

import java.math.BigDecimal;
import java.util.function.Function;


/**
 * @FileName: Select3.java
 * @Description: 文件说明
 * @Date: 2023/2/6 22:44
 * @author xuejiaming
 */
public interface Queryable3<T1, T2, T3> extends Queryable<T1> {

    <T4> Queryable4<T1, T2, T3,T4> leftJoin(Class<T4> joinClass, SQLExpression4<SQLWherePredicate<T1>, SQLWherePredicate<T2>, SQLWherePredicate<T3>, SQLWherePredicate<T4>> on);
    <T4> Queryable4<T1, T2, T3,T4> leftJoin(Queryable<T4> joinQueryable, SQLExpression4<SQLWherePredicate<T1>, SQLWherePredicate<T2>, SQLWherePredicate<T3>, SQLWherePredicate<T4>> on);
    <T4> Queryable4<T1, T2, T3,T4> rightJoin(Class<T4> joinClass, SQLExpression4<SQLWherePredicate<T1>, SQLWherePredicate<T2>, SQLWherePredicate<T3>, SQLWherePredicate<T4>> on);
    <T4> Queryable4<T1, T2, T3,T4> rightJoin(Queryable<T4> joinQueryable, SQLExpression4<SQLWherePredicate<T1>, SQLWherePredicate<T2>, SQLWherePredicate<T3>, SQLWherePredicate<T4>> on);

    <T4> Queryable4<T1, T2, T3,T4> innerJoin(Class<T4> joinClass, SQLExpression4<SQLWherePredicate<T1>, SQLWherePredicate<T2>, SQLWherePredicate<T3>, SQLWherePredicate<T4>> on);
    <T4> Queryable4<T1, T2, T3,T4> innerJoin(Queryable<T4> joinQueryable, SQLExpression4<SQLWherePredicate<T1>, SQLWherePredicate<T2>, SQLWherePredicate<T3>, SQLWherePredicate<T4>> on);

    //region where
    default Queryable3<T1, T2, T3> whereObject(Object object){
        return whereObject(true,object);
    }
    Queryable3<T1, T2, T3> whereObject(boolean condition, Object object);
    @Override
    default Queryable3<T1, T2, T3> where(SQLExpression<SQLWherePredicate<T1>> whereExpression) {
        return where(true, whereExpression);
    }

    @Override
    Queryable3<T1, T2, T3> where(boolean condition, SQLExpression<SQLWherePredicate<T1>> whereExpression);
    default Queryable3<T1, T2, T3> where(SQLExpression3<SQLWherePredicate<T1>, SQLWherePredicate<T2>, SQLWherePredicate<T3>> whereExpression) {
        return where(true, whereExpression);
    }

    Queryable3<T1, T2, T3> where(boolean condition, SQLExpression3<SQLWherePredicate<T1>, SQLWherePredicate<T2>, SQLWherePredicate<T3>> whereExpression);

    //endregion

    //region select
    <TR> Queryable<TR> select(Class<TR> resultClass, SQLExpression3<SQLColumnAsSelector<T1, TR>, SQLColumnAsSelector<T2, TR>, SQLColumnAsSelector<T3, TR>> selectExpression);
    //endregion

    //region aggregate

    /**
     * 防止溢出
     *
     * @param columnSelectorExpression
     * @param <TMember>
     * @return
     */
    default <TMember extends Number> BigDecimal sumBigDecimalOrNull(SQLExpression3<SQLColumnResultSelector<T1, TMember>, SQLColumnResultSelector<T2, TMember>, SQLColumnResultSelector<T3, TMember>> columnSelectorExpression) {
        return sumBigDecimalOrDefault(columnSelectorExpression, null);
    }

    /**
     * 防止溢出
     *
     * @param columnSelectorExpression
     * @param <TMember>
     * @return
     */
    default <TMember extends Number> BigDecimal sumBigDecimalNotNull(SQLExpression3<SQLColumnResultSelector<T1, TMember>, SQLColumnResultSelector<T2, TMember>, SQLColumnResultSelector<T3, TMember>> columnSelectorExpression) {
        return sumBigDecimalOrDefault(columnSelectorExpression, BigDecimal.ZERO);
    }

    <TMember extends Number> BigDecimal sumBigDecimalOrDefault(SQLExpression3<SQLColumnResultSelector<T1, TMember>, SQLColumnResultSelector<T2, TMember>, SQLColumnResultSelector<T3, TMember>> columnSelectorExpression, BigDecimal def);

    default <TMember extends Number> TMember sumOrNull(SQLExpression3<SQLColumnResultSelector<T1, TMember>, SQLColumnResultSelector<T2, TMember>, SQLColumnResultSelector<T3, TMember>> columnSelectorExpression) {
        return sumOrDefault(columnSelectorExpression, null);
    }

    <TMember extends Number> TMember sumOrDefault(SQLExpression3<SQLColumnResultSelector<T1, TMember>, SQLColumnResultSelector<T2, TMember>, SQLColumnResultSelector<T3, TMember>> columnSelectorExpression, TMember def);

    default <TMember> TMember maxOrNull(SQLExpression3<SQLColumnResultSelector<T1, TMember>, SQLColumnResultSelector<T2, TMember>, SQLColumnResultSelector<T3, TMember>> columnSelectorExpression) {
        return maxOrDefault(columnSelectorExpression, null);
    }

    <TMember> TMember maxOrDefault(SQLExpression3<SQLColumnResultSelector<T1, TMember>, SQLColumnResultSelector<T2, TMember>, SQLColumnResultSelector<T3, TMember>> columnSelectorExpression, TMember def);

    default <TMember> TMember minOrNull(SQLExpression3<SQLColumnResultSelector<T1, TMember>, SQLColumnResultSelector<T2, TMember>, SQLColumnResultSelector<T3, TMember>> columnSelectorExpression) {
        return minOrDefault(columnSelectorExpression, null);
    }

    <TMember> TMember minOrDefault(SQLExpression3<SQLColumnResultSelector<T1, TMember>, SQLColumnResultSelector<T2, TMember>, SQLColumnResultSelector<T3, TMember>> columnSelectorExpression, TMember def);

    default <TMember> TMember avgOrNull(SQLExpression3<SQLColumnResultSelector<T1, TMember>, SQLColumnResultSelector<T2, TMember>, SQLColumnResultSelector<T3, TMember>> columnSelectorExpression) {
        return avgOrDefault(columnSelectorExpression, null);
    }

    <TMember> TMember avgOrDefault(SQLExpression3<SQLColumnResultSelector<T1, TMember>, SQLColumnResultSelector<T2, TMember>, SQLColumnResultSelector<T3, TMember>> columnSelectorExpression, TMember def);

    default Integer lenOrNull(SQLExpression3<SQLColumnResultSelector<T1, ?>, SQLColumnResultSelector<T2, ?>, SQLColumnResultSelector<T3, ?>> columnSelectorExpression) {
        return lenOrDefault(columnSelectorExpression, null);
    }

    Integer lenOrDefault(SQLExpression3<SQLColumnResultSelector<T1, ?>, SQLColumnResultSelector<T2, ?>, SQLColumnResultSelector<T3, ?>> columnSelectorExpression, Integer def);
    //endregion

    //region group
    default Queryable3<T1, T2, T3> groupBy(SQLExpression3<SQLGroupBySelector<T1>, SQLGroupBySelector<T2>, SQLGroupBySelector<T3>> selectExpression) {
        return groupBy(true, selectExpression);
    }

    Queryable3<T1, T2, T3> groupBy(boolean condition, SQLExpression3<SQLGroupBySelector<T1>, SQLGroupBySelector<T2>, SQLGroupBySelector<T3>> selectExpression);

    //endregion
    //region order
    default Queryable3<T1, T2, T3> orderByAsc(SQLExpression3<SQLColumnSelector<T1>, SQLColumnSelector<T2>, SQLColumnSelector<T3>> selectExpression) {
        return orderByAsc(true, selectExpression);
    }

    Queryable3<T1, T2, T3> orderByAsc(boolean condition, SQLExpression3<SQLColumnSelector<T1>, SQLColumnSelector<T2>, SQLColumnSelector<T3>> selectExpression);

    default Queryable3<T1, T2, T3> orderByDesc(SQLExpression3<SQLColumnSelector<T1>, SQLColumnSelector<T2>, SQLColumnSelector<T3>> selectExpression) {
        return orderByDesc(true, selectExpression);
    }

    Queryable3<T1, T2, T3> orderByDesc(boolean condition, SQLExpression3<SQLColumnSelector<T1>, SQLColumnSelector<T2>, SQLColumnSelector<T3>> selectExpression);
    //endregion
    //region limit

    @Override
    default Queryable3<T1, T2, T3> limit(long rows) {
        return limit(true, rows);
    }

    @Override
    default Queryable3<T1, T2, T3> limit(boolean condition, long rows) {
        return limit(condition, 0, rows);
    }

    @Override
    default Queryable3<T1, T2, T3> limit(long offset, long rows) {
        return limit(true, offset, rows);
    }

    @Override
    Queryable3<T1, T2, T3> limit(boolean condition, long offset, long rows);

    default Queryable3<T1, T2, T3> distinct() {
        return distinct(true);
    }

    Queryable3<T1, T2, T3> distinct(boolean condition);
    //endregion

    @Override
    Queryable3<T1, T2, T3> disableLogicDelete();

    @Override
    Queryable3<T1, T2, T3> enableLogicDelete();
    @Override
    Queryable3<T1, T2, T3> useLogicDelete(boolean enable);

    @Override
    Queryable3<T1, T2, T3> noInterceptor();
    @Override
    Queryable3<T1, T2, T3> useInterceptor(String name);
    @Override
    Queryable3<T1, T2, T3> noInterceptor(String name);
    @Override
    Queryable3<T1, T2, T3> useInterceptor();

    /**
     * 自动将查询结果集合全部添加到当前上下文追踪中,如果当前查询结果十分庞大,并且更新数据只有个别条数,建议不要使用
     * 追踪查询，可以通过开启追踪后使用普通的查询，然后添加到当前的追踪上下文中{@link com.easy.query.core.api.client.EasyQuery#addTracking(Object)},开始先数据追踪的差异更新
     * @return
     */
    @Override
    Queryable3<T1, T2, T3> asTracking();
    @Override
    Queryable3<T1, T2, T3> asNoTracking();

    @Override
    Queryable3<T1, T2, T3> useShardingConfigure(int maxShardingQueryLimit, ConnectionModeEnum connectionMode);
    @Override
    Queryable3<T1, T2, T3> useMaxShardingQueryLimit(int maxShardingQueryLimit);
    @Override
    Queryable3<T1, T2, T3> useConnectionMode(ConnectionModeEnum connectionMode);
    /**
     * 将当前表达式最近的一张表的表名修改成 {@param tableName}
     * 如果当前最近的表是正常的数据库表名,那么直接将表名改写
     * 如果当前最近的表是匿名表比如嵌套queryable的表那么将alias改成对应的表名
     * @param tableName
     * @return
     */
    @Override
    default Queryable3<T1, T2, T3> asTable(String tableName){
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
    Queryable3<T1, T2, T3> asTable(Function<String,String> tableNameAs);

    EasyQuerySQLBuilderProvider3<T1, T2, T3> getSQLBuilderProvider3();
}
