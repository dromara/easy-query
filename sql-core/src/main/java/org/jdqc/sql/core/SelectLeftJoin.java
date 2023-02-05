package org.jdqc.sql.core;

/**
 * Copyright (c) 2021.biaodian.All Rights Reserved
 *
 * @FileName: SelectLeftJoin.java
 * @Description: 文件说明
 * @Date: 2023/2/5 15:30
 * @Created by xuejiaming
 */
public interface SelectLeftJoin<T1,TR> {
    Select<T1,TR> on(OnFunction<SqlPredicate<T1,?>> onPredicate);
}
