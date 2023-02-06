package org.jdqc.sql.core;

import java.util.List;

/**
 * Copyright (c) 2021.biaodian.All Rights Reserved
 *
 * @FileName: Select2Impl.java
 * @Description: 文件说明
 * @Date: 2023/2/6 04:55
 * @Created by xuejiaming
 */
public class Select2Impl<T1, TR> extends SelectBase<T1, TR,Select2<T1, TR>> implements Select2<T1, TR> {


    public Select2Impl(SelectContext<T1, TR>  selectContext) {
        super(selectContext);

    }

    @Override
    public <T2> Select2<T1, TR> leftJoin(Class<T2> joinClass, OnFunction<SqlPredicate2<T1, ?>> on) {
        leftJoin0(joinClass, on);
        return this;
    }

    @Override
    public Select2<T1, TR> select(Property<T1, ?>... columns) {
        return null;
    }

    @Override
    public <R, S> Select2<T1, TR> eq(boolean condition, Property<R, ?> column, Property<S, ?> val) {
        return null;
    }

    @Override
    public Select2<T1, TR> selectAll(Class<?> selectClass) {
        return null;
    }

    @Override
    public <TSelect> Select2<T1, TR> selectAs(Property<TSelect, ?> column, Property<TR, ?> alias) {
        return null;
    }

    @Override
    public <R> Select2<T1, TR> eq(boolean condition, Property<R, ?> column, Object val) {
        return null;
    }
}
