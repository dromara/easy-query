package org.easy.query.core.basic.api;

import org.easy.query.core.expression.parser.abstraction.SqlColumnSelector;
import org.easy.query.core.expression.lambda.SqlExpression2;
import org.easy.query.core.expression.lambda.SqlExpression3;
import org.easy.query.core.expression.parser.abstraction.SqlPredicate;


/**
 *
 * @FileName: Select2.java
 * @Description: 文件说明
 * @Date: 2023/2/6 22:42
 * @Created by xuejiaming
 */
public interface Select2<T1,T2>extends Select0<T1, Select2<T1,T2>> {
    <T3> Select3<T1,T2,T3> leftJoin(Class<T3> joinClass, SqlExpression3<SqlPredicate<T1>,SqlPredicate<T2>,SqlPredicate<T3>> on);
    <T3> Select3<T1,T2,T3> innerJoin(Class<T3> joinClass, SqlExpression3<SqlPredicate<T1>,SqlPredicate<T2>,SqlPredicate<T3>> on);


   default Select2<T1,T2> where(SqlExpression2<SqlPredicate<T1>,SqlPredicate<T2>> whereExpression){
       return where(true,whereExpression);
   }
    Select2<T1,T2> where(boolean condition,SqlExpression2<SqlPredicate<T1>,SqlPredicate<T2>> whereExpression);
   default Select2<T1,T2> groupBy(SqlExpression2<SqlColumnSelector<T1>,SqlColumnSelector<T2>> selectExpression){
       return groupBy(true,selectExpression);
   }
    Select2<T1,T2> groupBy(boolean condition,SqlExpression2<SqlColumnSelector<T1>,SqlColumnSelector<T2>> selectExpression);
    default Select2<T1,T2> orderByAsc(SqlExpression2<SqlColumnSelector<T1>,SqlColumnSelector<T2>> selectExpression){
        return orderByAsc(true,selectExpression);
    }
    Select2<T1,T2> orderByAsc(boolean condition,SqlExpression2<SqlColumnSelector<T1>,SqlColumnSelector<T2>> selectExpression);
    default Select2<T1,T2> orderByDesc(SqlExpression2<SqlColumnSelector<T1>,SqlColumnSelector<T2>> selectExpression){
        return orderByDesc(true,selectExpression);
    }
    Select2<T1,T2>  orderByDesc(boolean condition,SqlExpression2<SqlColumnSelector<T1>,SqlColumnSelector<T2>> selectExpression);
}
