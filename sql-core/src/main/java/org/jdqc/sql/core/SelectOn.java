package org.jdqc.sql.core;

/**
 * Copyright (c) 2021.biaodian.All Rights Reserved
 *
 * @FileName: SelectOn.java
 * @Description: 文件说明
 * @Date: 2023/2/5 21:57
 * @Created by xuejiaming
 */
public interface SelectOn<T1,TR> {
    Select<T1> on(OnFunction<SqlPredicate<T1,?>> onPredicate);
}
