package org.jdqc.sql.core.abstraction.sql;

import org.jdqc.sql.core.abstraction.lambda.SqlExpression;
import org.jdqc.sql.core.abstraction.sql.base.*;

import java.util.List;

/**
 *
 * @FileName: Select0.java
 * @Description: 文件说明
 * @Date: 2023/2/6 21:28
 * @Created by xuejiaming
 */
public interface Select0<T1,TR, TChain> {
    int count();
    boolean any();
    TR firstOrNull();
    List<TR> toList();
    String toSql();
   default TChain where(SqlExpression<SqlPredicate<T1>> whereExpression){
       return where(true,whereExpression);
   }
    TChain where(boolean condition, SqlExpression<SqlPredicate<T1>> whereExpression);
    default TChain select(SqlExpression<SqlSelectColumnSelector<T1,TR>> selectExpression){
        return select(true,selectExpression);
    }
    TChain select(boolean condition,SqlExpression<SqlSelectColumnSelector<T1,TR>> selectExpression);
   default TChain groupBy(SqlExpression<SqlColumnSelector<T1>> selectExpression){
       return groupBy(true,selectExpression);
   }
    TChain groupBy(boolean condition, SqlExpression<SqlColumnSelector<T1>> selectExpression);
    default TChain orderByAsc(SqlExpression<SqlColumnSelector<T1>> selectExpression){
        return orderByAsc(true,selectExpression);
    }
    TChain orderByAsc(boolean condition, SqlExpression<SqlColumnSelector<T1>> selectExpression);
    default TChain orderByDesc(SqlExpression<SqlColumnSelector<T1>> selectExpression){
        return orderByDesc(true,selectExpression);
    }
    TChain orderByDesc(boolean condition, SqlExpression<SqlColumnSelector<T1>> selectExpression);
    default TChain skip(int skip){
        return skip(true,skip);
    }
    TChain skip(boolean condition, int skip);

    default TChain take(int take){
        return take(true,take);
    }
    TChain take(boolean condition, int take);
}
