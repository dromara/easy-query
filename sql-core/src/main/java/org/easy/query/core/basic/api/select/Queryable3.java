package org.easy.query.core.basic.api.select;

import org.easy.query.core.basic.api.select.provider.EasyQuerySqlBuilderProvider3;
import org.easy.query.core.expression.lambda.SqlExpression3;
import org.easy.query.core.expression.parser.abstraction.SqlColumnAsSelector;
import org.easy.query.core.expression.parser.abstraction.SqlColumnSelector;
import org.easy.query.core.expression.parser.abstraction.SqlPredicate;
import org.easy.query.core.expression.parser.abstraction.SqlColumnResultSelector;

import java.math.BigDecimal;


/**
 *
 * @FileName: Select3.java
 * @Description: 文件说明
 * @Date: 2023/2/6 22:44
 * @Created by xuejiaming
 */
public interface Queryable3<T1,T2,T3> extends Queryable<T1> {
    //region where
   default Queryable3<T1,T2,T3> where(SqlExpression3<SqlPredicate<T1>,SqlPredicate<T2>,SqlPredicate<T3>> whereExpression)
   {
       return where(true,whereExpression);
   }
    Queryable3<T1,T2,T3> where(boolean condition, SqlExpression3<SqlPredicate<T1>,SqlPredicate<T2>,SqlPredicate<T3>> whereExpression);

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
    default Queryable3<T1,T2,T3> groupBy(SqlExpression3<SqlColumnSelector<T1>,SqlColumnSelector<T2>,SqlColumnSelector<T3>> selectExpression)
    {
        return groupBy(true,selectExpression);
    }
    Queryable3<T1,T2,T3> groupBy(boolean condition, SqlExpression3<SqlColumnSelector<T1>,SqlColumnSelector<T2>,SqlColumnSelector<T3>> selectExpression);
    //endregion
    //region order
    default Queryable3<T1,T2,T3> orderByAsc(SqlExpression3<SqlColumnSelector<T1>,SqlColumnSelector<T2>,SqlColumnSelector<T3>> selectExpression)
    {
        return orderByAsc(true,selectExpression);
    }
     Queryable3<T1,T2,T3> orderByAsc(boolean condition, SqlExpression3<SqlColumnSelector<T1>,SqlColumnSelector<T2>,SqlColumnSelector<T3>> selectExpression);
    default Queryable3<T1,T2,T3> orderByDesc(SqlExpression3<SqlColumnSelector<T1>,SqlColumnSelector<T2>,SqlColumnSelector<T3>> selectExpression)
    {
        return orderByDesc(true,selectExpression);
    }
     Queryable3<T1,T2,T3> orderByDesc(boolean condition, SqlExpression3<SqlColumnSelector<T1>,SqlColumnSelector<T2>,SqlColumnSelector<T3>> selectExpression);
    //endregion
    //region limit

    @Override
    default Queryable3<T1,T2,T3> limit(long rows) {
        return limit(true, rows);
    }

    @Override
    default Queryable3<T1,T2,T3> limit(boolean condition, long rows) {
        return limit(condition, 0, rows);
    }

    @Override
    default Queryable3<T1,T2,T3> limit(long offset, long rows) {
        return limit(true, offset, rows);
    }

    @Override
    Queryable3<T1,T2,T3> limit(boolean condition, long offset, long rows);

    //endregion

    @Override
    Queryable3<T1,T2,T3> disableLogicDelete();

    @Override
    Queryable3<T1,T2,T3> enableLogicDelete();

    @Override
    Queryable3<T1,T2,T3> noQueryFilter();

    @Override
    Queryable3<T1,T2,T3> useQueryFilter();
    EasyQuerySqlBuilderProvider3<T1, T2, T3> getSqlBuilderProvider3();
}
