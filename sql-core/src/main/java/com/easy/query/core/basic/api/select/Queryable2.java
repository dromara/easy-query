package com.easy.query.core.basic.api.select;

import com.easy.query.core.basic.api.select.provider.EasyQuerySqlBuilderProvider2;
import com.easy.query.core.expression.lambda.SqlExpression;
import com.easy.query.core.expression.parser.abstraction.SqlColumnAsSelector;
import com.easy.query.core.expression.parser.abstraction.SqlColumnSelector;
import com.easy.query.core.expression.lambda.SqlExpression2;
import com.easy.query.core.expression.lambda.SqlExpression3;
import com.easy.query.core.expression.parser.abstraction.SqlPredicate;
import com.easy.query.core.expression.parser.abstraction.SqlColumnResultSelector;

import java.math.BigDecimal;


/**
 * @FileName: Select2.java
 * @Description: 文件说明
 * @Date: 2023/2/6 22:42
 * @Created by xuejiaming
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
    default Queryable2<T1, T2> groupBy(SqlExpression2<SqlColumnSelector<T1>, SqlColumnSelector<T2>> selectExpression) {
        return groupBy(true, selectExpression);
    }

    Queryable2<T1, T2> groupBy(boolean condition, SqlExpression2<SqlColumnSelector<T1>, SqlColumnSelector<T2>> selectExpression);

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
    Queryable2<T1, T2> noInterceptor();

    @Override
    Queryable2<T1, T2> useInterceptor();
    @Override
    Queryable2<T1, T2> asTracking();
    @Override
    Queryable2<T1, T2> asNoTracking();

    EasyQuerySqlBuilderProvider2<T1, T2> getSqlBuilderProvider2();
}
