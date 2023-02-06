package org.jdqc.sql.core.impl;

import org.jdqc.sql.core.abstraction.sql.base.Property;

/**
 * Copyright (c) 2021.biaodian.All Rights Reserved
 *
 * @FileName: SelectImpl.java
 * @Description: 文件说明
 * @Date: 2023/2/5 21:29
 * @Created by xuejiaming
 */
public class SelectImpl<T1, TR> extends SelectBase<T1, TR, Selecta<T1, TR>> implements org.jdqc.sql.core.abstraction.sql.base.SqlPredicate<T1, Selecta<T1, TR>>, org.jdqc.sql.core.abstraction.sql.base.SqlFetch<TR>, org.jdqc.sql.core.abstraction.sql.base.SqlFilter<Selecta<T1, TR>>, org.jdqc.sql.core.abstraction.sql.base.SqlSelector22<Selecta<T1, TR>, T1, TR>, org.jdqc.sql.core.abstraction.sql.base.SqlJoin<T1, TR> {

    public SelectImpl(SelectContext<T1, TR>  selectContext) {
        super(selectContext);
    }

    @Override
    public Selecta<T1, TR> select(Property<T1, ?> column) {
        return null;
    }

    @Override
    public Selecta<T1, TR> selectAs(Property<T1, ?> column, Property<TR, ?> alias) {
        return null;
    }

    @Override
    public Selecta<T1, TR> eq(boolean condition, Property<T1, ?> column, Object val) {
        return null;
    }

    @Override
    public Selecta<T1, TR> selectCount(Property<T1, ?> column, Property<TR, ?> alias) {
        return null;
    }

    @Override
    public Selecta<T1, TR> selectSum(Property<T1, ?> column, Property<TR, ?> alias) {
        return null;
    }

    @Override
    public Selecta<T1, TR> selectMax(Property<T1, ?> column, Property<TR, ?> alias) {
        return null;
    }

    @Override
    public Selecta<T1, TR> selectMin(Property<T1, ?> column, Property<TR, ?> alias) {
        return null;
    }

    @Override
    public Selecta<T1, TR> selectAvg(Property<T1, ?> column, Property<TR, ?> alias) {
        return null;
    }

    @Override
    public Selecta<T1, TR> selectLen(Property<T1, ?> column, Property<TR, ?> alias) {
        return null;
    }
}
