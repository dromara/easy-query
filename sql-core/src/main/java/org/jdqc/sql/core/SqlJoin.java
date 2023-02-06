package org.jdqc.sql.core;

/**
 * Copyright (c) 2021.biaodian.All Rights Reserved
 *
 * @FileName: SqlJoin.java
 * @Description: 文件说明
 * @Date: 2023/2/6 04:43
 * @Created by xuejiaming
 */
public interface SqlJoin<T1,TR> {
    <T2> Select2<T1,TR> leftJoin(Class<T2> joinClass,OnFunction<SqlPredicate2<T1, ?>> on);
}
