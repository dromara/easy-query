package com.easy.query.core.basic.api.select;

import com.easy.query.core.basic.api.select.provider.EasyQuerySqlBuilderProvider4;
import com.easy.query.core.expression.lambda.SqlExpression;
import com.easy.query.core.expression.lambda.SqlExpression4;
import com.easy.query.core.expression.parser.abstraction.SqlColumnAsSelector;
import com.easy.query.core.expression.parser.abstraction.SqlColumnResultSelector;
import com.easy.query.core.expression.parser.abstraction.SqlColumnSelector;
import com.easy.query.core.expression.parser.abstraction.SqlPredicate;

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
    default Queryable4<T1, T2, T3,T4> where(SqlExpression<SqlPredicate<T1>> whereExpression) {
        return where(true, whereExpression);
    }

    @Override
    Queryable4<T1, T2, T3,T4> where(boolean condition, SqlExpression<SqlPredicate<T1>> whereExpression);
    default Queryable4<T1,T2,T3,T4> where(SqlExpression4<SqlPredicate<T1>, SqlPredicate<T2>, SqlPredicate<T3>, SqlPredicate<T4>> whereExpression) {
        return where(true, whereExpression);
    }

    Queryable4<T1,T2,T3,T4> where(boolean condition, SqlExpression4<SqlPredicate<T1>, SqlPredicate<T2>, SqlPredicate<T3>, SqlPredicate<T4>> whereExpression);

    //endregion
    //region select
    <TR> Queryable<TR> select(Class<TR> resultClass, SqlExpression4<SqlColumnAsSelector<T1, TR>, SqlColumnAsSelector<T2, TR>, SqlColumnAsSelector<T3, TR>, SqlColumnAsSelector<T4, TR>> selectExpression);
    //endregion

    //region aggregate

    /**
     * 防止溢出
     *
     * @param columnSelectorExpression
     * @param <TMember>
     * @return
     */
    default <TMember extends Number> BigDecimal sumBigDecimalOrNull(SqlExpression4<SqlColumnResultSelector<T1, TMember>, SqlColumnResultSelector<T2, TMember>, SqlColumnResultSelector<T3, TMember>, SqlColumnResultSelector<T4, TMember>> columnSelectorExpression) {
        return sumBigDecimalOrDefault(columnSelectorExpression, null);
    }

    /**
     * 防止溢出
     *
     * @param columnSelectorExpression
     * @param <TMember>
     * @return
     */
    default <TMember extends Number> BigDecimal sumBigDecimalNotNull(SqlExpression4<SqlColumnResultSelector<T1, TMember>, SqlColumnResultSelector<T2, TMember>, SqlColumnResultSelector<T3, TMember>, SqlColumnResultSelector<T4, TMember>> columnSelectorExpression) {
        return sumBigDecimalOrDefault(columnSelectorExpression, BigDecimal.ZERO);
    }

    <TMember extends Number> BigDecimal sumBigDecimalOrDefault(SqlExpression4<SqlColumnResultSelector<T1, TMember>, SqlColumnResultSelector<T2, TMember>, SqlColumnResultSelector<T3, TMember>, SqlColumnResultSelector<T4, TMember>> columnSelectorExpression, BigDecimal def);

    default <TMember extends Number> TMember sumOrNull(SqlExpression4<SqlColumnResultSelector<T1, TMember>, SqlColumnResultSelector<T2, TMember>, SqlColumnResultSelector<T3, TMember>, SqlColumnResultSelector<T4, TMember>> columnSelectorExpression) {
        return sumOrDefault(columnSelectorExpression, null);
    }

    <TMember extends Number> TMember sumOrDefault(SqlExpression4<SqlColumnResultSelector<T1, TMember>, SqlColumnResultSelector<T2, TMember>, SqlColumnResultSelector<T3, TMember>, SqlColumnResultSelector<T4, TMember>> columnSelectorExpression, TMember def);

    default <TMember> TMember maxOrNull(SqlExpression4<SqlColumnResultSelector<T1, TMember>, SqlColumnResultSelector<T2, TMember>, SqlColumnResultSelector<T3, TMember>, SqlColumnResultSelector<T4, TMember>> columnSelectorExpression) {
        return maxOrDefault(columnSelectorExpression, null);
    }

    <TMember> TMember maxOrDefault(SqlExpression4<SqlColumnResultSelector<T1, TMember>, SqlColumnResultSelector<T2, TMember>, SqlColumnResultSelector<T3, TMember>, SqlColumnResultSelector<T4, TMember>> columnSelectorExpression, TMember def);

    default <TMember> TMember minOrNull(SqlExpression4<SqlColumnResultSelector<T1, TMember>, SqlColumnResultSelector<T2, TMember>, SqlColumnResultSelector<T3, TMember>, SqlColumnResultSelector<T4, TMember>> columnSelectorExpression) {
        return minOrDefault(columnSelectorExpression, null);
    }

    <TMember> TMember minOrDefault(SqlExpression4<SqlColumnResultSelector<T1, TMember>, SqlColumnResultSelector<T2, TMember>, SqlColumnResultSelector<T3, TMember>, SqlColumnResultSelector<T4, TMember>> columnSelectorExpression, TMember def);

    default <TMember> TMember avgOrNull(SqlExpression4<SqlColumnResultSelector<T1, TMember>, SqlColumnResultSelector<T2, TMember>, SqlColumnResultSelector<T3, TMember>, SqlColumnResultSelector<T4, TMember>> columnSelectorExpression) {
        return avgOrDefault(columnSelectorExpression, null);
    }

    <TMember> TMember avgOrDefault(SqlExpression4<SqlColumnResultSelector<T1, TMember>, SqlColumnResultSelector<T2, TMember>, SqlColumnResultSelector<T3, TMember>, SqlColumnResultSelector<T4, TMember>> columnSelectorExpression, TMember def);

    default Integer lenOrNull(SqlExpression4<SqlColumnResultSelector<T1, ?>, SqlColumnResultSelector<T2, ?>, SqlColumnResultSelector<T3, ?>, SqlColumnResultSelector<T4, ?>> columnSelectorExpression) {
        return lenOrDefault(columnSelectorExpression, null);
    }

    Integer lenOrDefault(SqlExpression4<SqlColumnResultSelector<T1, ?>, SqlColumnResultSelector<T2, ?>, SqlColumnResultSelector<T3, ?>, SqlColumnResultSelector<T4, ?>> columnSelectorExpression, Integer def);
    //endregion


    //region group
    default Queryable4<T1, T2, T3,T4> groupBy(SqlExpression4<SqlColumnSelector<T1>, SqlColumnSelector<T2>, SqlColumnSelector<T3>, SqlColumnSelector<T4>> selectExpression) {
        return groupBy(true, selectExpression);
    }

    Queryable4<T1, T2, T3,T4> groupBy(boolean condition, SqlExpression4<SqlColumnSelector<T1>, SqlColumnSelector<T2>, SqlColumnSelector<T3>, SqlColumnSelector<T4>> selectExpression);

    //endregion
    //region order
    default Queryable4<T1, T2, T3,T4> orderByAsc(SqlExpression4<SqlColumnSelector<T1>, SqlColumnSelector<T2>, SqlColumnSelector<T3>, SqlColumnSelector<T4>> selectExpression) {
        return orderByAsc(true, selectExpression);
    }

    Queryable4<T1, T2, T3,T4> orderByAsc(boolean condition, SqlExpression4<SqlColumnSelector<T1>, SqlColumnSelector<T2>, SqlColumnSelector<T3>, SqlColumnSelector<T4>> selectExpression);

    default Queryable4<T1, T2, T3,T4> orderByDesc(SqlExpression4<SqlColumnSelector<T1>, SqlColumnSelector<T2>, SqlColumnSelector<T3>, SqlColumnSelector<T4>> selectExpression) {
        return orderByDesc(true, selectExpression);
    }

    Queryable4<T1, T2, T3,T4> orderByDesc(boolean condition, SqlExpression4<SqlColumnSelector<T1>, SqlColumnSelector<T2>, SqlColumnSelector<T3>, SqlColumnSelector<T4>> selectExpression);
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

    EasyQuerySqlBuilderProvider4<T1, T2, T3, T4> getSqlBuilderProvider4();
}
