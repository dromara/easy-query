package org.jdqc.sql.core.abstraction.sql;

import org.jdqc.sql.core.abstraction.lambda.SqlExpression2;
import org.jdqc.sql.core.abstraction.lambda.SqlExpression3;
import org.jdqc.sql.core.abstraction.sql.base.*;

/**
 * Copyright (c) 2021.biaodian.All Rights Reserved
 *
 * @FileName: Select2.java
 * @Description: 文件说明
 * @Date: 2023/2/6 22:42
 * @Created by xuejiaming
 */
public interface Select2<T1,T2,TR>extends Select0<T1,TR, Select2<T1,T2,TR>> {
    <T3> Select3<T1,T2,T3,TR> leftJoin(Class<T3> joinClass, SqlExpression3<WherePredicate<T1>,WherePredicate<T2>,WherePredicate<T3>> on);
    <T3> Select3<T1,T2,T3,TR> innerJoin(Class<T3> joinClass, SqlExpression3<WherePredicate<T1>,WherePredicate<T2>,WherePredicate<T3>> on);
    Select2<T1,T2,TR> where(SqlExpression2<WherePredicate<T1>,WherePredicate<T2>> whereExpression);
    Select2<T1,T2,TR> select(SqlExpression2<SqlSelector<T1,TR>,SqlSelector<T2,TR>> selectExpression);
    Select2<T1,T2,TR> groupBy(SqlExpression2<SqlColumnSelector<T1>,SqlColumnSelector<T2>> selectExpression);
    Select2<T1,T2,TR> orderByAsc(SqlExpression2<SqlColumnSelector<T1>,SqlColumnSelector<T2>> selectExpression);
    Select2<T1,T2,TR> orderByDesc(SqlExpression2<SqlColumnSelector<T1>,SqlColumnSelector<T2>> selectExpression);
}
