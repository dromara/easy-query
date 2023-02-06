package org.jdqc.sql.core.impl;

import org.jdqc.sql.core.abstraction.sql.base.OnFunction;
import org.jdqc.sql.core.abstraction.sql.base.Property;
import org.jdqc.sql.core.abstraction.sql.base.SqlPredicate2;

/**
 * Copyright (c) 2021.biaodian.All Rights Reserved
 *
 * @FileName: Select2Impl.java
 * @Description: 文件说明
 * @Date: 2023/2/6 04:55
 * @Created by xuejiaming
 */
public class Select2Impl<T1, TR> extends SelectBase<T1, TR, Select2a<T1, TR>> implements Select2a<T1, TR> {


    public Select2Impl(SelectContext<T1, TR>  selectContext) {
        super(selectContext);

    }

    @Override
    public <T2> Select2a<T1, TR> leftJoin(Class<T2> joinClass, OnFunction<SqlPredicate2<T1, ?>> on) {
        leftJoin0(joinClass, on);
        return this;
    }


    @Override
    public <R, S> Select2a<T1, TR> eq(boolean condition, Property<R, ?> column, Property<S, ?> val) {
        return null;
    }

    @Override
    public <TSelect> Select2a<T1, TR> select(Property<TSelect, ?> column) {
        return null;
    }

    @Override
    public Select2a<T1, TR> selectAll(Class<?> selectClass) {
        return null;
    }

    @Override
    public <TSelect> Select2a<T1, TR> selectAs(Property<TSelect, ?> column, Property<TR, ?> alias) {
        return null;
    }

    @Override
    public <R> Select2a<T1, TR> eq(boolean condition, Property<R, ?> column, Object val) {
        return null;
    }

    @Override
    public <TSelect> Select2a<T1, TR> selectCount(Property<TSelect, ?> column, Property<TR, ?> alias) {
        return null;
    }

    @Override
    public <TSelect> Select2a<T1, TR> selectSum(Property<TSelect, ?> column, Property<TR, ?> alias) {
        return null;
    }

    @Override
    public <TSelect> Select2a<T1, TR> selectMax(Property<TSelect, ?> column, Property<TR, ?> alias) {
        return null;
    }

    @Override
    public <TSelect> Select2a<T1, TR> selectMin(Property<TSelect, ?> column, Property<TR, ?> alias) {
        return null;
    }

    @Override
    public <TSelect> Select2a<T1, TR> selectAvg(Property<TSelect, ?> column, Property<TR, ?> alias) {
        return null;
    }

    @Override
    public <TSelect> Select2a<T1, TR> selectLen(Property<TSelect, ?> column, Property<TR, ?> alias) {
        return null;
    }

    @Override
    public <TSelect> Select2a<T1, TR> groupBy(boolean condition, Property<TSelect, ?> column) {
        return null;
    }

    @Override
    public <TSelect> Select2a<T1, TR> orderByAsc(boolean condition, Property<TSelect, ?> column) {
        return null;
    }

    @Override
    public <TSelect> Select2a<T1, TR> orderByDesc(boolean condition, Property<TSelect, ?> column) {
        return null;
    }
}
