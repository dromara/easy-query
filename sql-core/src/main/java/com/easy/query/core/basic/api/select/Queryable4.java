package com.easy.query.core.basic.api.select;

import com.easy.query.core.basic.api.select.provider.EasyQuerySQLBuilderProvider4;
import com.easy.query.core.expression.lambda.SQLExpression1;
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
 * @FileName: Queryable4.java
 * @Description: 文件说明
 * @Date: 2023/3/9 12:10
 * @author xuejiaming
 */
public interface Queryable4<T1,T2,T3,T4> extends Queryable<T1> {
    //region where
    default Queryable4<T1, T2, T3,T4> whereObject(Object object){
        return whereObject(true,object);
    }
    Queryable4<T1, T2, T3,T4> whereObject(boolean condition, Object object);
    @Override
    default Queryable4<T1, T2, T3,T4> where(SQLExpression1<SQLWherePredicate<T1>> whereExpression) {
        return where(true, whereExpression);
    }

    @Override
    Queryable4<T1, T2, T3,T4> where(boolean condition, SQLExpression1<SQLWherePredicate<T1>> whereExpression);
    default Queryable4<T1,T2,T3,T4> where(SQLExpression4<SQLWherePredicate<T1>, SQLWherePredicate<T2>, SQLWherePredicate<T3>, SQLWherePredicate<T4>> whereExpression) {
        return where(true, whereExpression);
    }

    Queryable4<T1,T2,T3,T4> where(boolean condition, SQLExpression4<SQLWherePredicate<T1>, SQLWherePredicate<T2>, SQLWherePredicate<T3>, SQLWherePredicate<T4>> whereExpression);

    //endregion
    //region select
    <TR> Queryable<TR> select(Class<TR> resultClass, SQLExpression4<SQLColumnAsSelector<T1, TR>, SQLColumnAsSelector<T2, TR>, SQLColumnAsSelector<T3, TR>, SQLColumnAsSelector<T4, TR>> selectExpression);
    //endregion

    //region aggregate

    /**
     * 防止溢出
     *
     * @param columnSelectorExpression
     * @param <TMember>
     * @return
     */
    default <TMember extends Number> BigDecimal sumBigDecimalOrNull(SQLExpression4<SQLColumnResultSelector<T1, TMember>, SQLColumnResultSelector<T2, TMember>, SQLColumnResultSelector<T3, TMember>, SQLColumnResultSelector<T4, TMember>> columnSelectorExpression) {
        return sumBigDecimalOrDefault(columnSelectorExpression, null);
    }

    /**
     * 防止溢出
     *
     * @param columnSelectorExpression
     * @param <TMember>
     * @return
     */
    default <TMember extends Number> BigDecimal sumBigDecimalNotNull(SQLExpression4<SQLColumnResultSelector<T1, TMember>, SQLColumnResultSelector<T2, TMember>, SQLColumnResultSelector<T3, TMember>, SQLColumnResultSelector<T4, TMember>> columnSelectorExpression) {
        return sumBigDecimalOrDefault(columnSelectorExpression, BigDecimal.ZERO);
    }

    <TMember extends Number> BigDecimal sumBigDecimalOrDefault(SQLExpression4<SQLColumnResultSelector<T1, TMember>, SQLColumnResultSelector<T2, TMember>, SQLColumnResultSelector<T3, TMember>, SQLColumnResultSelector<T4, TMember>> columnSelectorExpression, BigDecimal def);

    default <TMember extends Number> TMember sumOrNull(SQLExpression4<SQLColumnResultSelector<T1, TMember>, SQLColumnResultSelector<T2, TMember>, SQLColumnResultSelector<T3, TMember>, SQLColumnResultSelector<T4, TMember>> columnSelectorExpression) {
        return sumOrDefault(columnSelectorExpression, null);
    }

    <TMember extends Number> TMember sumOrDefault(SQLExpression4<SQLColumnResultSelector<T1, TMember>, SQLColumnResultSelector<T2, TMember>, SQLColumnResultSelector<T3, TMember>, SQLColumnResultSelector<T4, TMember>> columnSelectorExpression, TMember def);

    default <TMember> TMember maxOrNull(SQLExpression4<SQLColumnResultSelector<T1, TMember>, SQLColumnResultSelector<T2, TMember>, SQLColumnResultSelector<T3, TMember>, SQLColumnResultSelector<T4, TMember>> columnSelectorExpression) {
        return maxOrDefault(columnSelectorExpression, null);
    }

    <TMember> TMember maxOrDefault(SQLExpression4<SQLColumnResultSelector<T1, TMember>, SQLColumnResultSelector<T2, TMember>, SQLColumnResultSelector<T3, TMember>, SQLColumnResultSelector<T4, TMember>> columnSelectorExpression, TMember def);

    default <TMember> TMember minOrNull(SQLExpression4<SQLColumnResultSelector<T1, TMember>, SQLColumnResultSelector<T2, TMember>, SQLColumnResultSelector<T3, TMember>, SQLColumnResultSelector<T4, TMember>> columnSelectorExpression) {
        return minOrDefault(columnSelectorExpression, null);
    }

    <TMember> TMember minOrDefault(SQLExpression4<SQLColumnResultSelector<T1, TMember>, SQLColumnResultSelector<T2, TMember>, SQLColumnResultSelector<T3, TMember>, SQLColumnResultSelector<T4, TMember>> columnSelectorExpression, TMember def);

    default <TMember> TMember avgOrNull(SQLExpression4<SQLColumnResultSelector<T1, TMember>, SQLColumnResultSelector<T2, TMember>, SQLColumnResultSelector<T3, TMember>, SQLColumnResultSelector<T4, TMember>> columnSelectorExpression) {
        return avgOrDefault(columnSelectorExpression, null);
    }

    <TMember> TMember avgOrDefault(SQLExpression4<SQLColumnResultSelector<T1, TMember>, SQLColumnResultSelector<T2, TMember>, SQLColumnResultSelector<T3, TMember>, SQLColumnResultSelector<T4, TMember>> columnSelectorExpression, TMember def);

    default Integer lenOrNull(SQLExpression4<SQLColumnResultSelector<T1, ?>, SQLColumnResultSelector<T2, ?>, SQLColumnResultSelector<T3, ?>, SQLColumnResultSelector<T4, ?>> columnSelectorExpression) {
        return lenOrDefault(columnSelectorExpression, null);
    }

    Integer lenOrDefault(SQLExpression4<SQLColumnResultSelector<T1, ?>, SQLColumnResultSelector<T2, ?>, SQLColumnResultSelector<T3, ?>, SQLColumnResultSelector<T4, ?>> columnSelectorExpression, Integer def);
    //endregion


    //region group
    default Queryable4<T1, T2, T3,T4> groupBy(SQLExpression4<SQLGroupBySelector<T1>, SQLGroupBySelector<T2>, SQLGroupBySelector<T3>, SQLGroupBySelector<T4>> selectExpression) {
        return groupBy(true, selectExpression);
    }

    Queryable4<T1, T2, T3,T4> groupBy(boolean condition, SQLExpression4<SQLGroupBySelector<T1>, SQLGroupBySelector<T2>, SQLGroupBySelector<T3>, SQLGroupBySelector<T4>> selectExpression);

    //endregion
    //region order
    default Queryable4<T1, T2, T3,T4> orderByAsc(SQLExpression4<SQLColumnSelector<T1>, SQLColumnSelector<T2>, SQLColumnSelector<T3>, SQLColumnSelector<T4>> selectExpression) {
        return orderByAsc(true, selectExpression);
    }

    Queryable4<T1, T2, T3,T4> orderByAsc(boolean condition, SQLExpression4<SQLColumnSelector<T1>, SQLColumnSelector<T2>, SQLColumnSelector<T3>, SQLColumnSelector<T4>> selectExpression);

    default Queryable4<T1, T2, T3,T4> orderByDesc(SQLExpression4<SQLColumnSelector<T1>, SQLColumnSelector<T2>, SQLColumnSelector<T3>, SQLColumnSelector<T4>> selectExpression) {
        return orderByDesc(true, selectExpression);
    }

    Queryable4<T1, T2, T3,T4> orderByDesc(boolean condition, SQLExpression4<SQLColumnSelector<T1>, SQLColumnSelector<T2>, SQLColumnSelector<T3>, SQLColumnSelector<T4>> selectExpression);
    //endregion
    //region limit

    @Override
    default Queryable4<T1, T2, T3,T4> limit(long rows) {
        return limit(true, rows);
    }

    @Override
    default Queryable4<T1, T2, T3,T4> limit(boolean condition, long rows) {
        return limit(condition, 0, rows);
    }

    @Override
    default Queryable4<T1, T2, T3,T4> limit(long offset, long rows) {
        return limit(true, offset, rows);
    }

    @Override
    Queryable4<T1, T2, T3,T4> limit(boolean condition, long offset, long rows);

    default Queryable4<T1, T2, T3,T4> distinct() {
        return distinct(true);
    }

    Queryable4<T1, T2, T3,T4> distinct(boolean condition);
    //endregion

    @Override
    Queryable4<T1, T2, T3,T4> disableLogicDelete();

    @Override
    Queryable4<T1, T2, T3,T4> enableLogicDelete();
    @Override
    Queryable4<T1, T2, T3,T4> useLogicDelete(boolean enable);

    @Override
    Queryable4<T1, T2, T3,T4> noInterceptor();
    @Override
    Queryable4<T1, T2, T3,T4> useInterceptor();
    /**
     * 自动将查询结果集合全部添加到当前上下文追踪中,如果当前查询结果十分庞大,并且更新数据只有个别条数,建议不要使用
     * 追踪查询，可以通过开启追踪后使用普通的查询，然后添加到当前的追踪上下文中{@link com.easy.query.core.api.client.EasyQuery#addTracking(Object)},开始先数据追踪的差异更新
     * @return
     */
    @Override
    Queryable4<T1, T2, T3,T4> asTracking();
    @Override
    Queryable4<T1, T2, T3,T4> asNoTracking();
    @Override
    Queryable4<T1, T2, T3,T4> useShardingConfigure(int maxShardingQueryLimit, ConnectionModeEnum connectionMode);
    @Override
    Queryable4<T1, T2, T3,T4> useMaxShardingQueryLimit(int maxShardingQueryLimit);
    @Override
    Queryable4<T1, T2, T3,T4> useConnectionMode(ConnectionModeEnum connectionMode);
    /**
     * 将当前表达式最近的一张表的表名修改成 {@param tableName}
     * 如果当前最近的表是正常的数据库表名,那么直接将表名改写
     * 如果当前最近的表是匿名表比如嵌套queryable的表那么将alias改成对应的表名
     * @param tableName
     * @return
     */
    @Override
    default Queryable4<T1, T2, T3,T4> asTable(String tableName){
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
    Queryable4<T1, T2, T3,T4> asTable(Function<String,String> tableNameAs);

    EasyQuerySQLBuilderProvider4<T1, T2, T3, T4> getSQLBuilderProvider4();
}
