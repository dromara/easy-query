package org.jdqc.sql.core.abstraction.sql;

import org.jdqc.sql.core.abstraction.lambda.SqlExpression2;
import org.jdqc.sql.core.abstraction.lambda.SqlExpression3;
import org.jdqc.sql.core.abstraction.sql.base.*;

/**
 *
 * @FileName: Select2.java
 * @Description: 文件说明
 * @Date: 2023/2/6 22:42
 * @Created by xuejiaming
 */
public interface Select2<T1,T2,TR>extends Select0<T1,TR, Select2<T1,T2,TR>> {
    <T3> Select3<T1,T2,T3,TR> leftJoin(Class<T3> joinClass, SqlExpression3<SqlPredicate<T1>,SqlPredicate<T2>,SqlPredicate<T3>> on);
    <T3> Select3<T1,T2,T3,TR> innerJoin(Class<T3> joinClass, SqlExpression3<SqlPredicate<T1>,SqlPredicate<T2>,SqlPredicate<T3>> on);
   default Select2<T1,T2,TR> where(SqlExpression2<SqlPredicate<T1>,SqlPredicate<T2>> whereExpression){
       return where(true,whereExpression);
   }
    Select2<T1,T2,TR> where(boolean condition,SqlExpression2<SqlPredicate<T1>,SqlPredicate<T2>> whereExpression);
    default Select2<T1,T2,TR> select(SqlExpression2<SqlSelectColumnSelector<T1,TR>, SqlSelectColumnSelector<T2,TR>> selectExpression){
        return select(true,selectExpression);
    }
    Select2<T1,T2,TR> select(boolean condition,SqlExpression2<SqlSelectColumnSelector<T1,TR>, SqlSelectColumnSelector<T2,TR>> selectExpression);
   default Select2<T1,T2,TR> groupBy(SqlExpression2<SqlColumnSelector<T1>,SqlColumnSelector<T2>> selectExpression){
       return groupBy(true,selectExpression);
   }
    Select2<T1,T2,TR> groupBy(boolean condition,SqlExpression2<SqlColumnSelector<T1>,SqlColumnSelector<T2>> selectExpression);
    default Select2<T1,T2,TR> orderByAsc(SqlExpression2<SqlColumnSelector<T1>,SqlColumnSelector<T2>> selectExpression){
        return orderByAsc(true,selectExpression);
    }
    Select2<T1,T2,TR> orderByAsc(boolean condition,SqlExpression2<SqlColumnSelector<T1>,SqlColumnSelector<T2>> selectExpression);
    default Select2<T1,T2,TR> orderByDesc(SqlExpression2<SqlColumnSelector<T1>,SqlColumnSelector<T2>> selectExpression){
        return orderByDesc(true,selectExpression);
    }
    Select2<T1,T2,TR>  orderByDesc(boolean condition,SqlExpression2<SqlColumnSelector<T1>,SqlColumnSelector<T2>> selectExpression);
}
