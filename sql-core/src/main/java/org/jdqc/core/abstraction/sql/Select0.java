package org.jdqc.core.abstraction.sql;

import org.jdqc.core.abstraction.lambda.SqlExpression;
import org.jdqc.core.abstraction.sql.base.SqlColumnAsSelector;
import org.jdqc.core.abstraction.sql.base.SqlColumnSelector;
import org.jdqc.core.abstraction.sql.base.SqlPredicate;

import java.util.List;

/**
 * @FileName: Select0.java
 * @Description: 文件说明
 * @Date: 2023/2/6 21:28
 * @Created by xuejiaming
 */
public interface Select0<T1, TChain> {
    int count();

    boolean any();

    T1 firstOrNull();

    /**
     * 查询某些字段
     *
     * @param selectExpression
     * @return
     */
    T1 firstOrNull(SqlExpression<SqlColumnSelector<T1>> selectExpression);

    /**
     * 结果转换成某个对象
     *
     * @param resultClass
     * @param <TR>
     * @return
     */
    <TR> TR firstOrNull(Class<TR> resultClass);

    /**
     * 转换成某个对象并且映射字段
     *
     * @param resultClass
     * @param selectExpression
     * @param <TR>
     * @return
     */
    <TR> TR firstOrNull(Class<TR> resultClass, SqlExpression<SqlColumnAsSelector<T1, TR>> selectExpression);

    List<T1> toList();

    List<T1> toList(SqlExpression<SqlColumnSelector<T1>> selectExpression);

    <TR> List<TR> toList(Class<TR> resultClass);

    <TR> List<TR> toList(Class<TR> resultClass, SqlExpression<SqlColumnAsSelector<T1, TR>> selectExpression);

    //    <TR> List<TR> toList(Class<TR> resultClass);
    String toSql();
    String toSql(SqlExpression<SqlColumnSelector<T1>> selectExpression);

    <TR> String toSql(Class<TR> resultClass, SqlExpression<SqlColumnAsSelector<T1, TR>> selectExpression);
    String toSql(String columns);


    default TChain where(SqlExpression<SqlPredicate<T1>> whereExpression) {
        return where(true, whereExpression);
    }

    TChain where(boolean condition, SqlExpression<SqlPredicate<T1>> whereExpression);

    //    default TChain select(SqlExpression<SqlSelectColumnSelector<T1,TR>> selectExpression){
//        return select(true,selectExpression);
//    }
//    TChain select(boolean condition,SqlExpression<SqlSelectColumnSelector<T1,TR>> selectExpression);
    default TChain groupBy(SqlExpression<SqlColumnSelector<T1>> selectExpression) {
        return groupBy(true, selectExpression);
    }

    TChain groupBy(boolean condition, SqlExpression<SqlColumnSelector<T1>> selectExpression);

    default TChain orderByAsc(SqlExpression<SqlColumnSelector<T1>> selectExpression) {
        return orderByAsc(true, selectExpression);
    }

    TChain orderByAsc(boolean condition, SqlExpression<SqlColumnSelector<T1>> selectExpression);

    default TChain orderByDesc(SqlExpression<SqlColumnSelector<T1>> selectExpression) {
        return orderByDesc(true, selectExpression);
    }

    TChain orderByDesc(boolean condition, SqlExpression<SqlColumnSelector<T1>> selectExpression);

    default TChain skip(int skip) {
        return skip(true, skip);
    }

    TChain skip(boolean condition, int skip);

    default TChain take(int take) {
        return take(true, take);
    }

    TChain take(boolean condition, int take);
}
