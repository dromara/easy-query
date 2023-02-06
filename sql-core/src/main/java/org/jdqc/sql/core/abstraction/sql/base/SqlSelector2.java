package org.jdqc.sql.core.abstraction.sql.base;

/**
 * Copyright (c) 2021.biaodian.All Rights Reserved
 *
 * @FileName: Selector.java
 * @Description: 文件说明
 * @Date: 2023/2/6 04:42
 * @Created by xuejiaming
 */
public interface SqlSelector2<Children,TR> {
    <TSelect> Children select(Property<TSelect,?> column);
    Children selectAll(Class<?> selectClass);
   <TSelect> Children selectAs(Property<TSelect,?> column, Property<TR, ?> alias);

    <TSelect> Children selectCount(Property<TSelect, ?> column, Property<TR, ?> alias);

    <TSelect> Children selectSum(Property<TSelect, ?> column, Property<TR, ?> alias);

    <TSelect> Children selectMax(Property<TSelect, ?> column, Property<TR, ?> alias);

    <TSelect> Children selectMin(Property<TSelect, ?> column, Property<TR, ?> alias);

    <TSelect> Children selectAvg(Property<TSelect, ?> column, Property<TR, ?> alias);

    <TSelect> Children selectLen(Property<TSelect, ?> column, Property<TR, ?> alias);
}
