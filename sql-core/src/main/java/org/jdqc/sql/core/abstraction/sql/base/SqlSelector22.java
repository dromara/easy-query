package org.jdqc.sql.core.abstraction.sql.base;

/**
 * Copyright (c) 2021.biaodian.All Rights Reserved
 *
 * @FileName: Selector.java
 * @Description: 文件说明
 * @Date: 2023/2/6 04:42
 * @Created by xuejiaming
 */
public interface SqlSelector22<Children,T1,TR> {
    Children select(Property<T1,?> column);

    Children selectAs(Property<T1,?> column, Property<TR, ?> alias);
    Children selectCount(Property<T1,?> column, Property<TR,?> alias);
    Children selectSum(Property<T1,?> column,Property<TR,?> alias);
    Children selectMax(Property<T1,?> column,Property<TR,?> alias);
    Children selectMin(Property<T1,?> column,Property<TR,?> alias);
    Children selectAvg(Property<T1,?> column,Property<TR,?> alias);
    Children selectLen(Property<T1,?> column,Property<TR,?> alias);
}
