package org.easy.query.core.basic.api.select;

import org.easy.query.core.abstraction.EasyQuerySqlBuilderProvider;
import org.easy.query.core.expression.lambda.Property;
import org.easy.query.core.expression.lambda.SqlExpression;
import org.easy.query.core.abstraction.sql.PageResult;
import org.easy.query.core.expression.lambda.SqlExpression2;
import org.easy.query.core.expression.parser.abstraction.SqlAggregatePredicate;
import org.easy.query.core.expression.parser.abstraction.SqlColumnAsSelector;
import org.easy.query.core.expression.parser.abstraction.SqlColumnSelector;
import org.easy.query.core.expression.parser.abstraction.SqlPredicate;

import java.math.BigDecimal;
import java.util.List;

/**
 * @FileName: Select0.java
 * @Description: 文件说明
 * @Date: 2023/2/6 21:28
 * @Created by xuejiaming
 */
public interface Queryable<T1> extends Query<T1> {
    Queryable<T1> cloneQueryable();
    long count();

    long countDistinct(SqlExpression<SqlColumnSelector<T1>> selectExpression);

    boolean any();

    /**
     * 防止溢出
     *
     * @param column
     * @param <TMember>
     * @return
     */
    default <TMember extends Number> BigDecimal sumBigDecimalOrNull(Property<T1, TMember> column) {
        return sumBigDecimalOrDefault(column, null);
    }

    /**
     * 防止溢出
     *
     * @param column
     * @param <TMember>
     * @return
     */
    default <TMember extends Number> BigDecimal sumBigDecimalNotNull(Property<T1, TMember> column) {
        return sumBigDecimalOrDefault(column, BigDecimal.ZERO);
    }

    <TMember extends Number> BigDecimal sumBigDecimalOrDefault(Property<T1, TMember> column, BigDecimal def);

    default <TMember extends Number> TMember sumOrNull(Property<T1, TMember> column) {
        return sumOrDefault(column, null);
    }

    <TMember extends Number> TMember sumOrDefault(Property<T1, TMember> column, TMember def);

    default <TMember> TMember maxOrNull(Property<T1, TMember> column) {
        return maxOrDefault(column, null);
    }

    <TMember> TMember maxOrDefault(Property<T1, TMember> column, TMember def);

    default <TMember> TMember minOrNull(Property<T1, TMember> column) {
        return minOrDefault(column, null);
    }

    <TMember> TMember minOrDefault(Property<T1, TMember> column, TMember def);

    default <TMember> TMember avgOrNull(Property<T1, TMember> column) {
        return avgOrDefault(column, null);
    }

    <TMember> TMember avgOrDefault(Property<T1, TMember> column, TMember def);


    T1 firstOrNull();

//    /**
//     * 查询某些字段
//     *
//     * @param selectExpression
//     * @return
//     */
//    T1 firstOrNull(SqlExpression<SqlColumnSelector<T1>> selectExpression);
//
//    /**
//     * 结果转换成某个对象
//     *
//     * @param resultClass
//     * @param <TR>
//     * @return
//     */
//    <TR> TR firstOrNull(Class<TR> resultClass);
//
//    /**
//     * 转换成某个对象并且映射字段
//     *
//     * @param resultClass
//     * @param selectExpression
//     * @param <TR>
//     * @return
//     */
//    <TR> TR firstOrNull(Class<TR> resultClass, SqlExpression<SqlColumnAsSelector<T1, TR>> selectExpression);

    List<T1> toList();

//    List<T1> toList(SqlExpression<SqlColumnSelector<T1>> selectExpression);
//
//    <TR> List<TR> toList(Class<TR> resultClass);
//
//    <TR> List<TR> toList(Class<TR> resultClass, SqlExpression<SqlColumnAsSelector<T1, TR>> selectExpression);

    <TR> List<TR> toList(Class<TR> resultClass);
    String toSql();
//
//    String toSql(SqlExpression<SqlColumnSelector<T1>> selectExpression);
//
//    <TR> String toSql(Class<TR> resultClass, SqlExpression<SqlColumnAsSelector<T1, TR>> selectExpression);
//
//    String toSql(String columns);

    Queryable<T1> select(SqlExpression<SqlColumnSelector<T1>> selectExpression);
    <TR> Queryable<TR> select(Class<TR> resultClass);
//    <TR> Queryable<TR> select(Class<TR> resultClass,String columns);
    <TR> Queryable<TR> select(Class<TR> resultClass, SqlExpression<SqlColumnAsSelector<T1, TR>> selectExpression);
    Queryable<T1> select(String columns);

    default Queryable<T1> where(SqlExpression<SqlPredicate<T1>> whereExpression) {
        return where(true, whereExpression);
    }

    Queryable<T1> where(boolean condition, SqlExpression<SqlPredicate<T1>> whereExpression);

    //    default TChain select(SqlExpression<SqlSelectColumnSelector<T1,TR>> selectExpression){
//        return select(true,selectExpression);
//    }
//    TChain select(boolean condition,SqlExpression<SqlSelectColumnSelector<T1,TR>> selectExpression);
    default Queryable<T1> groupBy(SqlExpression<SqlColumnSelector<T1>> selectExpression) {
        return groupBy(true, selectExpression);
    }

    Queryable<T1> groupBy(boolean condition, SqlExpression<SqlColumnSelector<T1>> selectExpression);

    default Queryable<T1> having(SqlExpression<SqlAggregatePredicate<T1>> predicateExpression) {
        return having(true, predicateExpression);
    }

    Queryable<T1> having(boolean condition, SqlExpression<SqlAggregatePredicate<T1>> predicateExpression);

    default Queryable<T1> orderByAsc(SqlExpression<SqlColumnSelector<T1>> selectExpression) {
        return orderByAsc(true, selectExpression);
    }

    Queryable<T1> orderByAsc(boolean condition, SqlExpression<SqlColumnSelector<T1>> selectExpression);

    default Queryable<T1> orderByDesc(SqlExpression<SqlColumnSelector<T1>> selectExpression) {
        return orderByDesc(true, selectExpression);
    }

    Queryable<T1> orderByDesc(boolean condition, SqlExpression<SqlColumnSelector<T1>> selectExpression);


    default Queryable<T1> limit(long rows) {
        return limit(true, rows);
    }

    default Queryable<T1> limit(boolean condition, long rows) {
        return limit(condition, 0, rows);
    }

    default Queryable<T1> limit(long offset, long rows) {
        return limit(true, offset, rows);
    }

    Queryable<T1> limit(boolean condition, long offset, long rows);

    PageResult<T1> toPageResult(long pageIndex, long pageSize);
    PageResult<T1> toPageResult(long pageIndex, long pageSize, SqlExpression<SqlColumnSelector<T1>> selectExpression);
    <TR> PageResult<TR> toPageResult(long pageIndex, long pageSize, Class<TR> clazz);
    <TR> PageResult<TR> toPageResult(long pageIndex, long pageSize, Class<TR> clazz, SqlExpression<SqlColumnAsSelector<T1,TR>> selectExpression);
    <T2> Queryable2<T1, T2> leftJoin(Class<T2> joinClass, SqlExpression2<SqlPredicate<T1>, SqlPredicate<T2>> on);
    <T2> Queryable2<T1, T2> leftJoin(Queryable<T2> t2Queryable, SqlExpression2<SqlPredicate<T1>, SqlPredicate<T2>> on);

    <T2> Queryable2<T1, T2> innerJoin(Class<T2> joinClass, SqlExpression2<SqlPredicate<T1>, SqlPredicate<T2>> on);
    <T2> Queryable2<T1, T2> innerJoin(Queryable<T2> t2Queryable, SqlExpression2<SqlPredicate<T1>, SqlPredicate<T2>> on);

    EasyQuerySqlBuilderProvider<T1> getSqlBuilderProvider1();

    Queryable<T1> disableLogicDelete();
    Queryable<T1> enableLogicDelete();
    Queryable<T1> noQueryFilter();
    Queryable<T1> useQueryFilter();

}
