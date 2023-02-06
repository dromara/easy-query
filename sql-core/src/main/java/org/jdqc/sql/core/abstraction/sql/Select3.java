package org.jdqc.sql.core.abstraction.sql;

import org.jdqc.sql.core.abstraction.sql.base.*;

/**
 * Copyright (c) 2021.biaodian.All Rights Reserved
 *
 * @FileName: Select3.java
 * @Description: 文件说明
 * @Date: 2023/2/6 22:44
 * @Created by xuejiaming
 */
public interface Select3<T1,T2,T3,TR> extends Select0<T1,TR,Select3<T1,T2,T3,TR>> {
    Select3<T1,T2,T3,TR> where(SqlExpression3<WherePredicate<T1>,WherePredicate<T2>,WherePredicate<T3>> whereExpression);
    Select3<T1,T2,T3,TR> select(SqlExpression3<SqlSelector<T1,TR>,SqlSelector<T2,TR>,SqlSelector<T3,TR>> selectExpression);
    Select3<T1,T2,T3,TR> groupBy(SqlExpression3<SqlColumnSelector<T1>,SqlColumnSelector<T2>,SqlColumnSelector<T3>> selectExpression);
     Select3<T1,T2,T3,TR>  orderByAsc(SqlExpression3<SqlColumnSelector<T1>,SqlColumnSelector<T2>,SqlColumnSelector<T3>> selectExpression);
     Select3<T1,T2,T3,TR>  orderByDesc(SqlExpression3<SqlColumnSelector<T1>,SqlColumnSelector<T2>,SqlColumnSelector<T3>> selectExpression);
}
