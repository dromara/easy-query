package org.easy.query.core.basic.api.select;

import org.easy.query.core.basic.api.select.provider.EasyQuerySqlBuilderProvider;
import org.easy.query.core.exception.EasyQueryConcurrentException;
import org.easy.query.core.expression.lambda.Property;
import org.easy.query.core.expression.lambda.SqlExpression;
import org.easy.query.core.api.pagination.PageResult;
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
    /**
     * 只clone表达式共享上下文
     * @return
     */
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

    default <TMember extends Number> TMember avgOrNull(Property<T1, TMember> column) {
        return avgOrDefault(column, null);
    }

    <TMember extends Number> TMember avgOrDefault(Property<T1, TMember> column, TMember def);
    default Integer lenOrNull(Property<T1, ?> column) {
        return lenOrDefault(column, null);
    }

    Integer lenOrDefault(Property<T1, ?> column, Integer def);


   default T1 firstOrNull(){
       return firstOrNull(queryClass());
   }
    <TR> TR firstOrNull(Class<TR> resultClass);

    /**
     * 当未查询到结果 将会抛出 {@link EasyQueryConcurrentException}
     * @param msg
     * @return
     */
   default T1 firstNotNull(String msg){
       return firstNotNull(msg,null);
   }

    /**
     *  当未查询到结果 将会抛出 {@link EasyQueryConcurrentException}
     * @param msg
     * @param code
     * @return
     */
   default T1 firstNotNull(String msg,String code){
       return firstNotNull(queryClass(),msg,code);
   }
    default <TR> TR firstNotNull(Class<TR> resultClass,String msg){
        return firstNotNull(resultClass,msg,null);
    }
    <TR> TR firstNotNull(Class<TR> resultClass,String msg,String code);

    List<T1> toList();


    <TR> List<TR> toList(Class<TR> resultClass);

    /**
     * 返回执行sql
     * @return
     */
   default String toSql(){
       return toSql(queryClass());
   }
    <TR> String toSql(Class<TR> resultClass);
//
    Queryable<T1> select(SqlExpression<SqlColumnSelector<T1>> selectExpression);
    <TR> Queryable<TR> select(Class<TR> resultClass);
    <TR> Queryable<TR> select(Class<TR> resultClass, SqlExpression<SqlColumnAsSelector<T1, TR>> selectExpression);
    Queryable<T1> select(String columns);

    default Queryable<T1> where(SqlExpression<SqlPredicate<T1>> whereExpression) {
        return where(true, whereExpression);
    }

    Queryable<T1> where(boolean condition, SqlExpression<SqlPredicate<T1>> whereExpression);
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
    <T2> Queryable2<T1, T2> leftJoin(Queryable<T2> joinQueryable, SqlExpression2<SqlPredicate<T1>, SqlPredicate<T2>> on);
    <T2> Queryable2<T1, T2> rightJoin(Class<T2> joinClass, SqlExpression2<SqlPredicate<T1>, SqlPredicate<T2>> on);
    <T2> Queryable2<T1, T2> rightJoin(Queryable<T2> joinQueryable, SqlExpression2<SqlPredicate<T1>, SqlPredicate<T2>> on);

    <T2> Queryable2<T1, T2> innerJoin(Class<T2> joinClass, SqlExpression2<SqlPredicate<T1>, SqlPredicate<T2>> on);
    <T2> Queryable2<T1, T2> innerJoin(Queryable<T2> joinQueryable, SqlExpression2<SqlPredicate<T1>, SqlPredicate<T2>> on);

    EasyQuerySqlBuilderProvider<T1> getSqlBuilderProvider1();

    Queryable<T1> disableLogicDelete();
    Queryable<T1> enableLogicDelete();
    Queryable<T1> noQueryFilter();
    Queryable<T1> useQueryFilter();

}
