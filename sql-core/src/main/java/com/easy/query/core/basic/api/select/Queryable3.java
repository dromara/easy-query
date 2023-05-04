package com.easy.query.core.basic.api.select;

import com.easy.query.core.basic.api.select.provider.EasyQuerySqlBuilderProvider3;
import com.easy.query.core.expression.lambda.SqlExpression;
import com.easy.query.core.expression.lambda.SqlExpression3;
import com.easy.query.core.expression.lambda.SqlExpression4;
import com.easy.query.core.expression.parser.core.SqlColumnResultSelector;
import com.easy.query.core.expression.parser.core.SqlPredicate;
import com.easy.query.core.expression.parser.core.SqlGroupBySelector;
import com.easy.query.core.expression.parser.core.SqlColumnSelector;
import com.easy.query.core.expression.parser.core.SqlColumnAsSelector;

import java.math.BigDecimal;
import java.util.function.Function;


/**
 * @FileName: Select3.java
 * @Description: 文件说明
 * @Date: 2023/2/6 22:44
 * @author xuejiaming
 */
public interface Queryable3<T1, T2, T3> extends Queryable<T1> {

    <T4> Queryable4<T1, T2, T3,T4> leftJoin(Class<T4> joinClass, SqlExpression4<SqlPredicate<T1>, SqlPredicate<T2>, SqlPredicate<T3>, SqlPredicate<T4>> on);
    <T4> Queryable4<T1, T2, T3,T4> leftJoin(Queryable<T4> joinQueryable, SqlExpression4<SqlPredicate<T1>, SqlPredicate<T2>, SqlPredicate<T3>, SqlPredicate<T4>> on);
    <T4> Queryable4<T1, T2, T3,T4> rightJoin(Class<T4> joinClass, SqlExpression4<SqlPredicate<T1>, SqlPredicate<T2>, SqlPredicate<T3>, SqlPredicate<T4>> on);
    <T4> Queryable4<T1, T2, T3,T4> rightJoin(Queryable<T4> joinQueryable, SqlExpression4<SqlPredicate<T1>, SqlPredicate<T2>, SqlPredicate<T3>, SqlPredicate<T4>> on);

    <T4> Queryable4<T1, T2, T3,T4> innerJoin(Class<T4> joinClass, SqlExpression4<SqlPredicate<T1>, SqlPredicate<T2>, SqlPredicate<T3>, SqlPredicate<T4>> on);
    <T4> Queryable4<T1, T2, T3,T4> innerJoin(Queryable<T4> joinQueryable, SqlExpression4<SqlPredicate<T1>, SqlPredicate<T2>, SqlPredicate<T3>, SqlPredicate<T4>> on);

    //region where
    default Queryable3<T1, T2, T3> whereObject(Object object){
        return whereObject(true,object);
    }
    Queryable3<T1, T2, T3> whereObject(boolean condition, Object object);
    @Override
    default Queryable3<T1, T2, T3> where(SqlExpression<SqlPredicate<T1>> whereExpression) {
        return where(true, whereExpression);
    }

    @Override
    Queryable3<T1, T2, T3> where(boolean condition, SqlExpression<SqlPredicate<T1>> whereExpression);
    default Queryable3<T1, T2, T3> where(SqlExpression3<SqlPredicate<T1>, SqlPredicate<T2>, SqlPredicate<T3>> whereExpression) {
        return where(true, whereExpression);
    }

    Queryable3<T1, T2, T3> where(boolean condition, SqlExpression3<SqlPredicate<T1>, SqlPredicate<T2>, SqlPredicate<T3>> whereExpression);

    //endregion

    //region select
    <TR> Queryable<TR> select(Class<TR> resultClass, SqlExpression3<SqlColumnAsSelector<T1, TR>, SqlColumnAsSelector<T2, TR>, SqlColumnAsSelector<T3, TR>> selectExpression);
    //endregion

    //region aggregate

    /**
     * 防止溢出
     *
     * @param columnSelectorExpression
     * @param <TMember>
     * @return
     */
    default <TMember extends Number> BigDecimal sumBigDecimalOrNull(SqlExpression3<SqlColumnResultSelector<T1, TMember>, SqlColumnResultSelector<T2, TMember>, SqlColumnResultSelector<T3, TMember>> columnSelectorExpression) {
        return sumBigDecimalOrDefault(columnSelectorExpression, null);
    }

    /**
     * 防止溢出
     *
     * @param columnSelectorExpression
     * @param <TMember>
     * @return
     */
    default <TMember extends Number> BigDecimal sumBigDecimalNotNull(SqlExpression3<SqlColumnResultSelector<T1, TMember>, SqlColumnResultSelector<T2, TMember>, SqlColumnResultSelector<T3, TMember>> columnSelectorExpression) {
        return sumBigDecimalOrDefault(columnSelectorExpression, BigDecimal.ZERO);
    }

    <TMember extends Number> BigDecimal sumBigDecimalOrDefault(SqlExpression3<SqlColumnResultSelector<T1, TMember>, SqlColumnResultSelector<T2, TMember>, SqlColumnResultSelector<T3, TMember>> columnSelectorExpression, BigDecimal def);

    default <TMember extends Number> TMember sumOrNull(SqlExpression3<SqlColumnResultSelector<T1, TMember>, SqlColumnResultSelector<T2, TMember>, SqlColumnResultSelector<T3, TMember>> columnSelectorExpression) {
        return sumOrDefault(columnSelectorExpression, null);
    }

    <TMember extends Number> TMember sumOrDefault(SqlExpression3<SqlColumnResultSelector<T1, TMember>, SqlColumnResultSelector<T2, TMember>, SqlColumnResultSelector<T3, TMember>> columnSelectorExpression, TMember def);

    default <TMember> TMember maxOrNull(SqlExpression3<SqlColumnResultSelector<T1, TMember>, SqlColumnResultSelector<T2, TMember>, SqlColumnResultSelector<T3, TMember>> columnSelectorExpression) {
        return maxOrDefault(columnSelectorExpression, null);
    }

    <TMember> TMember maxOrDefault(SqlExpression3<SqlColumnResultSelector<T1, TMember>, SqlColumnResultSelector<T2, TMember>, SqlColumnResultSelector<T3, TMember>> columnSelectorExpression, TMember def);

    default <TMember> TMember minOrNull(SqlExpression3<SqlColumnResultSelector<T1, TMember>, SqlColumnResultSelector<T2, TMember>, SqlColumnResultSelector<T3, TMember>> columnSelectorExpression) {
        return minOrDefault(columnSelectorExpression, null);
    }

    <TMember> TMember minOrDefault(SqlExpression3<SqlColumnResultSelector<T1, TMember>, SqlColumnResultSelector<T2, TMember>, SqlColumnResultSelector<T3, TMember>> columnSelectorExpression, TMember def);

    default <TMember> TMember avgOrNull(SqlExpression3<SqlColumnResultSelector<T1, TMember>, SqlColumnResultSelector<T2, TMember>, SqlColumnResultSelector<T3, TMember>> columnSelectorExpression) {
        return avgOrDefault(columnSelectorExpression, null);
    }

    <TMember> TMember avgOrDefault(SqlExpression3<SqlColumnResultSelector<T1, TMember>, SqlColumnResultSelector<T2, TMember>, SqlColumnResultSelector<T3, TMember>> columnSelectorExpression, TMember def);

    default Integer lenOrNull(SqlExpression3<SqlColumnResultSelector<T1, ?>, SqlColumnResultSelector<T2, ?>, SqlColumnResultSelector<T3, ?>> columnSelectorExpression) {
        return lenOrDefault(columnSelectorExpression, null);
    }

    Integer lenOrDefault(SqlExpression3<SqlColumnResultSelector<T1, ?>, SqlColumnResultSelector<T2, ?>, SqlColumnResultSelector<T3, ?>> columnSelectorExpression, Integer def);
    //endregion

    //region group
    default Queryable3<T1, T2, T3> groupBy(SqlExpression3<SqlGroupBySelector<T1>, SqlGroupBySelector<T2>, SqlGroupBySelector<T3>> selectExpression) {
        return groupBy(true, selectExpression);
    }

    Queryable3<T1, T2, T3> groupBy(boolean condition, SqlExpression3<SqlGroupBySelector<T1>, SqlGroupBySelector<T2>, SqlGroupBySelector<T3>> selectExpression);

    //endregion
    //region order
    default Queryable3<T1, T2, T3> orderByAsc(SqlExpression3<SqlColumnSelector<T1>, SqlColumnSelector<T2>, SqlColumnSelector<T3>> selectExpression) {
        return orderByAsc(true, selectExpression);
    }

    Queryable3<T1, T2, T3> orderByAsc(boolean condition, SqlExpression3<SqlColumnSelector<T1>, SqlColumnSelector<T2>, SqlColumnSelector<T3>> selectExpression);

    default Queryable3<T1, T2, T3> orderByDesc(SqlExpression3<SqlColumnSelector<T1>, SqlColumnSelector<T2>, SqlColumnSelector<T3>> selectExpression) {
        return orderByDesc(true, selectExpression);
    }

    Queryable3<T1, T2, T3> orderByDesc(boolean condition, SqlExpression3<SqlColumnSelector<T1>, SqlColumnSelector<T2>, SqlColumnSelector<T3>> selectExpression);
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

    EasyQuerySqlBuilderProvider3<T1, T2, T3> getSqlBuilderProvider3();
}
