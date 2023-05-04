package com.easy.query.core.basic.api.select;

import com.easy.query.core.basic.api.select.provider.EasyQuerySqlBuilderProvider2;
import com.easy.query.core.expression.lambda.SqlExpression;
import com.easy.query.core.expression.parser.core.SqlColumnAsSelector;
import com.easy.query.core.expression.parser.core.SqlColumnSelector;
import com.easy.query.core.expression.lambda.SqlExpression2;
import com.easy.query.core.expression.lambda.SqlExpression3;
import com.easy.query.core.expression.parser.core.SqlColumnResultSelector;
import com.easy.query.core.expression.parser.core.SqlPredicate;
import com.easy.query.core.expression.parser.core.SqlGroupBySelector;

import java.math.BigDecimal;
import java.util.function.Function;


/**
 * @FileName: Select2.java
 * @Description: 文件说明
 * @Date: 2023/2/6 22:42
 * @author xuejiaming
 */
public interface Queryable2<T1, T2> extends Queryable<T1> {
    <T3> Queryable3<T1, T2, T3> leftJoin(Class<T3> joinClass, SqlExpression3<SqlPredicate<T1>, SqlPredicate<T2>, SqlPredicate<T3>> on);
    <T3> Queryable3<T1, T2, T3> leftJoin(Queryable<T3> joinQueryable, SqlExpression3<SqlPredicate<T1>, SqlPredicate<T2>, SqlPredicate<T3>> on);
    <T3> Queryable3<T1, T2, T3> rightJoin(Class<T3> joinClass, SqlExpression3<SqlPredicate<T1>, SqlPredicate<T2>, SqlPredicate<T3>> on);
    <T3> Queryable3<T1, T2, T3> rightJoin(Queryable<T3> joinQueryable, SqlExpression3<SqlPredicate<T1>, SqlPredicate<T2>, SqlPredicate<T3>> on);

    <T3> Queryable3<T1, T2, T3> innerJoin(Class<T3> joinClass, SqlExpression3<SqlPredicate<T1>, SqlPredicate<T2>, SqlPredicate<T3>> on);
    <T3> Queryable3<T1, T2, T3> innerJoin(Queryable<T3> joinQueryable, SqlExpression3<SqlPredicate<T1>, SqlPredicate<T2>, SqlPredicate<T3>> on);

    //region where

    default Queryable2<T1, T2> whereObject(Object object){
        return whereObject(true,object);
    }
    Queryable2<T1, T2> whereObject(boolean condition, Object object);
    @Override
    default Queryable2<T1, T2> where(SqlExpression<SqlPredicate<T1>> whereExpression) {
        return where(true, whereExpression);
    }

    @Override
    Queryable2<T1, T2> where(boolean condition, SqlExpression<SqlPredicate<T1>> whereExpression);
    default Queryable2<T1, T2> where(SqlExpression2<SqlPredicate<T1>, SqlPredicate<T2>> whereExpression) {
        return where(true, whereExpression);
    }

    Queryable2<T1, T2> where(boolean condition, SqlExpression2<SqlPredicate<T1>, SqlPredicate<T2>> whereExpression);

    //endregion

    //region select
    <TR> Queryable<TR> select(Class<TR> resultClass, SqlExpression2<SqlColumnAsSelector<T1, TR>, SqlColumnAsSelector<T2, TR>> selectExpression);
    //endregion
    //region aggregate

    /**
     * 防止溢出
     *
     * @param columnSelectorExpression
     * @param <TMember>
     * @return
     */
    default <TMember extends Number> BigDecimal sumBigDecimalOrNull(SqlExpression2<SqlColumnResultSelector<T1, TMember>, SqlColumnResultSelector<T2, TMember>> columnSelectorExpression) {
        return sumBigDecimalOrDefault(columnSelectorExpression, null);
    }

    /**
     * 防止溢出
     *
     * @param columnSelectorExpression
     * @param <TMember>
     * @return
     */
    default <TMember extends Number> BigDecimal sumBigDecimalNotNull(SqlExpression2<SqlColumnResultSelector<T1, TMember>, SqlColumnResultSelector<T2, TMember>> columnSelectorExpression) {
        return sumBigDecimalOrDefault(columnSelectorExpression, BigDecimal.ZERO);
    }

    <TMember extends Number> BigDecimal sumBigDecimalOrDefault(SqlExpression2<SqlColumnResultSelector<T1, TMember>, SqlColumnResultSelector<T2, TMember>> columnSelectorExpression, BigDecimal def);

    default <TMember extends Number> TMember sumOrNull(SqlExpression2<SqlColumnResultSelector<T1, TMember>, SqlColumnResultSelector<T2, TMember>> columnSelectorExpression) {
        return sumOrDefault(columnSelectorExpression, null);
    }

    <TMember extends Number> TMember sumOrDefault(SqlExpression2<SqlColumnResultSelector<T1, TMember>, SqlColumnResultSelector<T2, TMember>> columnSelectorExpression, TMember def);

    default <TMember> TMember maxOrNull(SqlExpression2<SqlColumnResultSelector<T1, TMember>, SqlColumnResultSelector<T2, TMember>> columnSelectorExpression) {
        return maxOrDefault(columnSelectorExpression, null);
    }

    <TMember> TMember maxOrDefault(SqlExpression2<SqlColumnResultSelector<T1, TMember>, SqlColumnResultSelector<T2, TMember>> columnSelectorExpression, TMember def);

    default <TMember> TMember minOrNull(SqlExpression2<SqlColumnResultSelector<T1, TMember>, SqlColumnResultSelector<T2, TMember>> columnSelectorExpression) {
        return minOrDefault(columnSelectorExpression, null);
    }

    <TMember> TMember minOrDefault(SqlExpression2<SqlColumnResultSelector<T1, TMember>, SqlColumnResultSelector<T2, TMember>> columnSelectorExpression, TMember def);

    default <TMember extends Number> TMember avgOrNull(SqlExpression2<SqlColumnResultSelector<T1, TMember>, SqlColumnResultSelector<T2, TMember>> columnSelectorExpression) {
        return avgOrDefault(columnSelectorExpression, null);
    }

    <TMember extends Number> TMember avgOrDefault(SqlExpression2<SqlColumnResultSelector<T1, TMember>, SqlColumnResultSelector<T2, TMember>> columnSelectorExpression, TMember def);
    default Integer lenOrNull(SqlExpression2<SqlColumnResultSelector<T1, ?>, SqlColumnResultSelector<T2, ?>> columnSelectorExpression) {
        return lenOrDefault(columnSelectorExpression, null);
    }

    Integer lenOrDefault(SqlExpression2<SqlColumnResultSelector<T1, ?>, SqlColumnResultSelector<T2, ?>> columnSelectorExpression, Integer def);
    //endregion

    //region group
    default Queryable2<T1, T2> groupBy(SqlExpression2<SqlGroupBySelector<T1>, SqlGroupBySelector<T2>> selectExpression) {
        return groupBy(true, selectExpression);
    }

    Queryable2<T1, T2> groupBy(boolean condition, SqlExpression2<SqlGroupBySelector<T1>, SqlGroupBySelector<T2>> selectExpression);

    //endregion
    //region order
    default Queryable2<T1, T2> orderByAsc(SqlExpression2<SqlColumnSelector<T1>, SqlColumnSelector<T2>> selectExpression) {
        return orderByAsc(true, selectExpression);
    }

    Queryable2<T1, T2> orderByAsc(boolean condition, SqlExpression2<SqlColumnSelector<T1>, SqlColumnSelector<T2>> selectExpression);

    default Queryable2<T1, T2> orderByDesc(SqlExpression2<SqlColumnSelector<T1>, SqlColumnSelector<T2>> selectExpression) {
        return orderByDesc(true, selectExpression);
    }

    Queryable2<T1, T2> orderByDesc(boolean condition, SqlExpression2<SqlColumnSelector<T1>, SqlColumnSelector<T2>> selectExpression);
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

    EasyQuerySqlBuilderProvider2<T1, T2> getSqlBuilderProvider2();
}
