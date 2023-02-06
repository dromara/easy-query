package org.jdqc.sql.core.abstraction.sql;

import org.jdqc.sql.core.abstraction.sql.base.*;

/**
 * Copyright (c) 2021.biaodian.All Rights Reserved
 *
 * @FileName: Select11.java
 * @Description: 文件说明
 * @Date: 2023/2/6 22:00
 * @Created by xuejiaming
 */
public interface Select1<T1,TR> extends Select0<T1,TR, Select1<T1,TR>>, SqlFilter<Select1<T1,TR>> {
    <T2> Select2<T1,T2,TR> leftJoin(Class<T2> joinClass, SqlExpression2<WherePredicate<T1>,WherePredicate<T2>> on);
    <T2> Select2<T1,T2,TR> innerJoin(Class<T2> joinClass, SqlExpression2<WherePredicate<T1>,WherePredicate<T2>> on);
}
