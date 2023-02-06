package org.jdqc.sql.core.abstraction.sql.base;

/**
 * Copyright (c) 2021.biaodian.All Rights Reserved
 *
 * @FileName: OnJoinPredicate.java
 * @Description: 文件说明
 * @Date: 2023/2/6 22:07
 * @Created by xuejiaming
 */
public interface OnAndPredicate<T1,T2,Children> {
    OnPredicate2<T1,Children> eq(Property<T1,?> column1,Property<T2,?> column2);
}
